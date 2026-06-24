package com.example.news.aggregator.service;

import com.example.news.aggregator.client.NewsApiClient;
import com.example.news.aggregator.config.CacheClient;
import com.example.news.aggregator.constant.Constants;
import com.example.news.aggregator.model.newsapi.Article;
import com.example.news.aggregator.model.newsapi.NewsApiResponse;
import com.example.news.aggregator.model.newsapi.Source;
import com.example.news.aggregator.service.strategy.SentimentAnalysisStrategy;
import com.example.news.aggregator.service.strategy.SourceCredibilityAnalysisStrategy;
import com.example.news.aggregator.util.SourceDiversityUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class NewsAggregatorServiceTest {
  @InjectMocks private NewsAggregatorServiceImpl newsAggregatorService;
  @Mock private NewsApiClient newsApiClient;
  @Mock private CacheClient cacheClient;
  @Spy private SourceDiversityUtil sourceDiversityUtil;
  @Spy private SentimentAnalysisStrategy sentimentAnalysisStrategy;
  @Spy private SourceCredibilityAnalysisStrategy sourceCredibilityAnalysisStrategy;

  private final String query = "news";
  private final Integer page = 1;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(newsAggregatorService, "breakingNewsHours", 24);
    ReflectionTestUtils.setField(newsAggregatorService, "negativeSentimentThreshold", 0.6);
    ReflectionTestUtils.setField(newsAggregatorService, "diverseViewpointThreshold", 0.3);
    ReflectionTestUtils.setField(newsAggregatorService, "limitedSourceDiversityThreshold", 3.0);
    ReflectionTestUtils.setField(newsAggregatorService, "newsApiPageSize", 100);
  }

  @Test
  void shouldFetchNews_whenNewsAPIReturnsResponse() {
    // Given
    Mockito.when(newsApiClient.fetchNews(anyString(), anyInt())).thenReturn(getNewsApiResponse());
    Mockito.when(cacheClient.fetchResponseFromCache(anyString(), anyInt())).thenReturn(null);
    // When
    var response = newsAggregatorService.fetchNews(query, page);
    // Then
    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.newsItems.getFirst().id);
  }

  @Test
  void shouldFetchNewsWithValidNextPageField_whenMoreResultsExist() {
    // Given
    Mockito.when(newsApiClient.fetchNews(anyString(), anyInt())).thenReturn(getNewsApiResponse());
    Mockito.when(cacheClient.fetchResponseFromCache(anyString(), anyInt())).thenReturn(null);
    // When
    var response = newsAggregatorService.fetchNews(query, page);
    // Then
    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.newsItems.getFirst().id);
    Assertions.assertEquals(2, response.nextPage);
  }

  @Test
  void shouldFetchNewsWithNullNextPageField_whenNoMoreResultsExist() {
    // Given
    Mockito.when(newsApiClient.fetchNews(anyString(), anyInt())).thenReturn(getNewsApiResponse());
    Mockito.when(cacheClient.fetchResponseFromCache(anyString(), anyInt())).thenReturn(null);
    var page = 5;
    // When
    var response = newsAggregatorService.fetchNews(query, page);
    // Then
    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.newsItems.getFirst().id);
    Assertions.assertNull(response.nextPage);
  }

  @Test
  void shouldFetchNewsWithBreakingNewsAlert_whenArticlePublishedInLast4Hours() {
    // Given
    var newsApiResponse = getNewsApiResponse();
    newsApiResponse.getArticles().getFirst().setPublishedAt(LocalDateTime.now().toString());
    Mockito.when(newsApiClient.fetchNews(anyString(), anyInt())).thenReturn(newsApiResponse);
    Mockito.when(cacheClient.fetchResponseFromCache(anyString(), anyInt())).thenReturn(null);
    // When
    var response = newsAggregatorService.fetchNews(query, page);
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
    Mockito.when(newsApiClient.fetchNews(anyString(), anyInt())).thenReturn(newsApiResponse);
    Mockito.when(cacheClient.fetchResponseFromCache(anyString(), anyInt())).thenReturn(null);
    // When
    var response = newsAggregatorService.fetchNews(query, page);
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
    Mockito.when(newsApiClient.fetchNews(anyString(), anyInt())).thenReturn(newsApiResponse);
    Mockito.when(cacheClient.fetchResponseFromCache(anyString(), anyInt())).thenReturn(null);
    // When
    var response = newsAggregatorService.fetchNews(query, page);
    // Then
    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.newsItems.getFirst().id);
    Assertions.assertTrue(response.alerts.contains(Constants.DIVERSE_VIEWPOINTS_ALERT));
  }

  @Test
  void shouldFetchNewsWithLimitedSourceDiversityAlert_whenMostArticlesHaveSameSource() {
    // Given
    var newsApiResponse = getNewsApiResponse();
    Mockito.when(newsApiClient.fetchNews(anyString(), anyInt())).thenReturn(newsApiResponse);
    Mockito.when(cacheClient.fetchResponseFromCache(anyString(), anyInt())).thenReturn(null);
    // When
    var response = newsAggregatorService.fetchNews(query, page);
    // Then
    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.newsItems.getFirst().id);
    Assertions.assertTrue(response.alerts.contains(Constants.LIMITED_SOURCE_DIVERSITY_ALERT));
  }

  private NewsApiResponse getNewsApiResponse() {
    return NewsApiResponse.builder()
        .status("ok")
        .totalResults(500)
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
