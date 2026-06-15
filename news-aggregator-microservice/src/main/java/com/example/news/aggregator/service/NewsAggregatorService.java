package com.example.news.aggregator.service;

import com.example.news.aggregator.client.NewsApiClient;
import com.example.news.aggregator.mapper.NewsAggregatorResponseMapper;
import com.example.news.aggregator.model.NewsAggregatorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsAggregatorService {

  private final NewsApiClient newsApiClient;

  public NewsAggregatorResponse fetchNews(String query) {
    log.atInfo().log("Service: fetchNews called with query: {}", query);
    var newsApiResponse = newsApiClient.fetchNews(query);
    log.atInfo().log(
        "NewsApiClient: response fetched with status: {} and totalResults: {}",
        newsApiResponse.status,
        newsApiResponse.totalResults);
    var newsAggregatorResponse =
        NewsAggregatorResponse.builder()
            .newsItems(NewsAggregatorResponseMapper.INSTANCE.mapToNewsItems(newsApiResponse.articles))
            .build();
    return newsAggregatorResponse;
  }
}
