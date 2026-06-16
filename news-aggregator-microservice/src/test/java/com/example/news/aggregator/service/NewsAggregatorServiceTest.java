package com.example.news.aggregator.service;

import com.example.news.aggregator.client.NewsApiClient;
import com.example.news.aggregator.constant.Constants;
import com.example.news.aggregator.model.newsapi.Article;
import com.example.news.aggregator.model.newsapi.NewsApiResponse;
import com.example.news.aggregator.model.newsapi.Source;
import com.example.news.aggregator.service.strategy.SentimentAnalysisStrategy;
import com.example.news.aggregator.service.strategy.SourceCredibilityAnalysisStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class NewsAggregatorServiceTest {
  @InjectMocks private NewsAggregatorService newsAggregatorService;
  @Mock private NewsApiClient newsApiClient;
  @Mock private CacheManager cacheManager;
  @Mock private Cache cache;
  @Spy private SentimentAnalysisStrategy sentimentAnalysisStrategy;
  @Spy private SourceCredibilityAnalysisStrategy sourceCredibilityAnalysisStrategy;

  @Test
  void shouldFetchNews_whenNewsAPIReturnsResponse() {
    // Given
    Mockito.when(newsApiClient.fetchNews(anyString())).thenReturn(getNewsApiResponse());
    Mockito.when(cacheManager.getCache(Constants.CACHE_NAME)).thenReturn(cache);
    Mockito.when(cache.get(anyString())).thenReturn(null);
    // When
    var response = newsAggregatorService.fetchNews("news");
    // Then
    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.newsItems.getFirst().id);
  }

  @Test
  void shouldFetchNewsWithBreakingNewsAlert_whenArticlePublishedInLast4Hours() {
    // Given
    var newsApiResponse = getNewsApiResponse();
    newsApiResponse.getArticles().getFirst().setPublishedAt(LocalDateTime.now().toString());
    Mockito.when(newsApiClient.fetchNews(anyString())).thenReturn(newsApiResponse);
    Mockito.when(cacheManager.getCache(Constants.CACHE_NAME)).thenReturn(cache);
    Mockito.when(cache.get(anyString())).thenReturn(null);
    // When
    var response = newsAggregatorService.fetchNews("news");
    // Then
    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.newsItems.getFirst().id);
    Assertions.assertTrue(response.alerts.contains(Constants.BREAKING_NEWS_ALERT));
  }

  @Test
  void shouldFetchNewsWithNegativeSentimentAlert_whenMostArticlesHaveNegativeSentiment() {
    // Given
    var newsApiResponse = getNewsApiResponse();
    newsApiResponse.getArticles().getFirst().setDescription("Bad product, horrible reviews");
    newsApiResponse.getArticles().getLast().setDescription("Unhappy with failed attempt");
    Mockito.when(newsApiClient.fetchNews(anyString())).thenReturn(newsApiResponse);
    Mockito.when(cacheManager.getCache(Constants.CACHE_NAME)).thenReturn(cache);
    Mockito.when(cache.get(anyString())).thenReturn(null);
    // When
    var response = newsAggregatorService.fetchNews("news");
    // Then
    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.newsItems.getFirst().id);
    Assertions.assertTrue(response.alerts.contains(Constants.OVERALL_NEGATIVE_SENTIMENT_ALERT));
  }

  @Test
  void shouldFetchNewsWithDiverseViewpointsAlert_whenLotOfPositiveAndNegativeArticles() {
    // Given
    var newsApiResponse = getNewsApiResponse();
    newsApiResponse.getArticles().getFirst().setDescription("Bad product, horrible reviews");
    newsApiResponse.getArticles().getLast().setDescription("Great product, amazing reviews");
    Mockito.when(newsApiClient.fetchNews(anyString())).thenReturn(newsApiResponse);
    Mockito.when(cacheManager.getCache(Constants.CACHE_NAME)).thenReturn(cache);
    Mockito.when(cache.get(anyString())).thenReturn(null);
    // When
    var response = newsAggregatorService.fetchNews("news");
    // Then
    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.newsItems.getFirst().id);
    Assertions.assertTrue(response.alerts.contains(Constants.DIVERSE_VIEWPOINTS_ALERT));
  }

  @Test
  void shouldFetchNewsWithLimitedSourceDiversityAlert_whenMostArticlesHaveSameSource() {
    // Given
    var newsApiResponse = getNewsApiResponse();
    Mockito.when(newsApiClient.fetchNews(anyString())).thenReturn(newsApiResponse);
    Mockito.when(cacheManager.getCache(Constants.CACHE_NAME)).thenReturn(cache);
    Mockito.when(cache.get(anyString())).thenReturn(null);
    // When
    var response = newsAggregatorService.fetchNews("news");
    // Then
    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.newsItems.getFirst().id);
    Assertions.assertTrue(response.alerts.contains(Constants.LIMITED_SOURCE_DIVERSITY_ALERT));
  }

  private NewsApiResponse getNewsApiResponse() {
    return NewsApiResponse.builder()
        .status("ok")
        .totalResults(5)
        .articles(
            List.of(
                Article.builder()
                    .title("Title 1")
                    .url("url")
                    .author("author")
                    .description("description 1")
                    .source(Source.builder().name("source").build())
                    .publishedAt("2026-05-29T17:09:12Z")
                    .build(),
                Article.builder()
                    .title("Title 2")
                    .url("url")
                    .author("author")
                    .description("description 2")
                    .source(Source.builder().name("source").build())
                    .publishedAt("2026-05-29T17:09:12Z")
                    .build()))
        .build();
  }
}
