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
import com.example.news.aggregator.util.SourceDiversityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsAggregatorServiceImpl implements NewsAggregatorService {

  private final NewsApiClient newsApiClient;
  private final SentimentAnalysisStrategy sentimentAnalysisStrategy;
  private final SourceCredibilityAnalysisStrategy sourceCredibilityAnalysisStrategy;
  private final CacheClient cacheClient;
  private final Map<String, Integer> sourceCounts = new HashMap<>();

  @Value("${breaking.news.hours:24}")
  private int breakingNewsHours;

  @Value("${negative.sentiment.threshold:0.6}")
  private double negativeSentimentThreshold;

  @Value("${diverse.viewpoints.threshold:0.3}")
  private double diverseViewpointThreshold;

  @Value("${limited.source.diversity.threshold:3.0}")
  private double limitedSourceDiversityThreshold;

  @Value("${news.api.page-size:100}")
  private int newsApiPageSize;

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
      if (LocalDateTime.parse(newsItem.publishedAt.substring(0, Constants.LOCAL_DATE_TIME_LENGTH))
          .isAfter(LocalDateTime.now().minusHours(breakingNewsHours))) {
        breakingNewsFlag = true;
      }
    }
    newsAggregatorResponse.setSourceList(sourceCounts.keySet().stream().toList());
    newsAggregatorResponse.setSourceDiversityScore(
        SourceDiversityUtil.analyseSourceDiversity(newsAggregatorResponse, sourceCounts));
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
    if ((double) negativeArticlesCount / totalArticles > negativeSentimentThreshold) {
      alerts.add(Constants.OVERALL_NEGATIVE_SENTIMENT_ALERT);
    }
    if ((double) negativeArticlesCount / totalArticles > diverseViewpointThreshold
        && (double) positiveArticlesCount / totalArticles > diverseViewpointThreshold) {
      alerts.add(Constants.DIVERSE_VIEWPOINTS_ALERT);
    }
    if (newsAggregatorResponse.sourceDiversityScore < limitedSourceDiversityThreshold) {
      alerts.add(Constants.LIMITED_SOURCE_DIVERSITY_ALERT);
    }
    newsAggregatorResponse.setAlerts(alerts);
  }

  private Integer calculateNextPage(Integer currentPage, NewsApiResponse newsApiResponse) {
    if (currentPage * newsApiPageSize >= newsApiResponse.totalResults) {
      return null;
    }
    return currentPage + 1;
  }
}
