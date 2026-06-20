package com.example.news.aggregator.service;

import com.example.news.aggregator.client.NewsApiClient;
import com.example.news.aggregator.config.CacheClient;
import com.example.news.aggregator.constant.Constants;
import com.example.news.aggregator.constant.DataFreshnessIndicator;
import com.example.news.aggregator.constant.Sentiment;
import com.example.news.aggregator.mapper.NewsAggregatorResponseMapper;
import com.example.news.aggregator.model.NewsAggregatorResponse;
import com.example.news.aggregator.model.NewsItem;
import com.example.news.aggregator.model.newsapi.NewsApiResponse;
import com.example.news.aggregator.service.strategy.SentimentAnalysisStrategy;
import com.example.news.aggregator.service.strategy.SourceCredibilityAnalysisStrategy;
import com.example.news.aggregator.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsAggregatorService {

  private final NewsApiClient newsApiClient;
  private final SentimentAnalysisStrategy sentimentAnalysisStrategy;
  private final SourceCredibilityAnalysisStrategy sourceCredibilityAnalysisStrategy;
  private final CacheClient cacheClient;
  private final Map<String, Integer> sourceCounts = new HashMap<>();

  public NewsAggregatorResponse fetchNews(String query, Integer page) {
    log.atInfo().log("Service: fetchNews called with query: {} and page: {}", query, page);

    var cachedResponse = cacheClient.fetchResponseFromCache(query, page);
    if (cachedResponse != null) {
      cachedResponse.setDataFreshnessIndicator(DataFreshnessIndicator.CACHED);
      log.atInfo().log("Service: fetchNews returned cachedResponse");
      return cachedResponse;
    }

    var newsApiResponse = newsApiClient.fetchNews(query, page);
    log.atInfo().log(
        "NewsApiClient: response fetched with status: {}, totalResults: {} and articles in response: {}",
        newsApiResponse.status,
        newsApiResponse.totalResults,
        newsApiResponse.articles.size());
    var newsAggregatorResponse =
        NewsAggregatorResponse.builder()
            .newsItems(
                NewsAggregatorResponseMapper.INSTANCE.mapToNewsItems(newsApiResponse.articles))
            .build();
    log.atInfo().log("Service: Analysing articles");
    var positiveArticlesCount = 0;
    var negativeArticlesCount = 0;
    var breakingNewsFlag = false;
    for (NewsItem newsItem : newsAggregatorResponse.getNewsItems()) {
      newsItem.setId(CommonUtils.generateUUID());
      analyseNewsItem(newsItem);

      sourceCounts.merge(newsItem.source, 1, Integer::sum);
      if (newsItem.sentiment == Sentiment.POSITIVE) {
        positiveArticlesCount++;
      } else if (newsItem.sentiment == Sentiment.NEGATIVE) {
        negativeArticlesCount++;
      }
      if (LocalDateTime.parse(newsItem.publishedAt.substring(0, 19))
          .isAfter(LocalDateTime.now().minusHours(4))) {
        breakingNewsFlag = true;
      }
    }
    newsAggregatorResponse.setSourceList(sourceCounts.keySet().stream().toList());
    newsAggregatorResponse.setSourceDiversityScore(analyseSourceDiversity(newsAggregatorResponse));
    setAlerts(
        newsAggregatorResponse, positiveArticlesCount, negativeArticlesCount, breakingNewsFlag);
    newsAggregatorResponse.setNextPage(calculateNextPage(page, newsApiResponse));

    cacheClient.setResponseInCache(query, page, newsAggregatorResponse);
    newsAggregatorResponse.setDataFreshnessIndicator(DataFreshnessIndicator.LIVE);
    return newsAggregatorResponse;
  }

  private void analyseNewsItem(NewsItem newsItem) {
    sentimentAnalysisStrategy.analyse(newsItem);
    sourceCredibilityAnalysisStrategy.analyse(newsItem);
  }

  // Use Shannon-Wiener Index: (H = -\sum p_{i} \ln(p_{i})\) to calculate diversity. Greater than
  // 3.0 is diverse
  private double analyseSourceDiversity(NewsAggregatorResponse newsAggregatorResponse) {
    if (newsAggregatorResponse.newsItems.isEmpty()) {
      return 0.0;
    }

    double totalArticles = newsAggregatorResponse.newsItems.size();
    double shannonIndexSum = 0.0;

    for (Map.Entry<String, Integer> entry : sourceCounts.entrySet()) {
      double pi = entry.getValue() / totalArticles;
      shannonIndexSum += pi * Math.log(pi);
    }

    log.atInfo().log("Source diversity score: {}", -shannonIndexSum);
    shannonIndexSum = -(Math.round(shannonIndexSum * 10.0) / 10.0);
    return shannonIndexSum;
  }

  private void setAlerts(
      NewsAggregatorResponse newsAggregatorResponse,
      int positiveArticlesCount,
      int negativeArticlesCount,
      boolean breakingNewsFlag) {
    var alerts = new ArrayList<String>();
    int totalArticles = newsAggregatorResponse.newsItems.size();
    if (breakingNewsFlag) {
      alerts.add(Constants.BREAKING_NEWS_ALERT);
    }
    if ((double) negativeArticlesCount / totalArticles > 0.6) {
      alerts.add(Constants.OVERALL_NEGATIVE_SENTIMENT_ALERT);
    }
    if ((double) negativeArticlesCount / totalArticles > 0.3
        && (double) positiveArticlesCount / totalArticles > 0.3) {
      alerts.add(Constants.DIVERSE_VIEWPOINTS_ALERT);
    }
    if (newsAggregatorResponse.sourceDiversityScore < 0.5) {
      alerts.add(Constants.LIMITED_SOURCE_DIVERSITY_ALERT);
    }
    newsAggregatorResponse.setAlerts(alerts);
  }

  private Integer calculateNextPage(Integer currentPage, NewsApiResponse newsApiResponse) {
    if (currentPage * 100 >= newsApiResponse.totalResults) {
      return null;
    }
    return currentPage + 1;
  }
}
