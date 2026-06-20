package com.example.news.aggregator.client;

import com.example.news.aggregator.exception.ClientException;
import com.example.news.aggregator.model.newsapi.NewsApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class NewsApiClient {
  private final WebClient webClient;

  @Value("${news.api.key}")
  String newsApiKey;

  public NewsApiClient(@Value("${news.api.url}") String newsApiUrl) {
    log.atInfo().log("NewsAPI URL: {}", newsApiUrl);
    this.webClient =
        WebClient.builder()
            .baseUrl(newsApiUrl)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .filter(logRequest())
            .build();
  }

  public NewsApiResponse fetchNews(String query, Integer page) {
    log.atInfo().log("NewsApiClient: fetchNews called with query: {} and page: {}", query, page);
    var response =
        this.webClient
            .get()
            .uri(
                uriBuilder ->
                    uriBuilder
                        .path("/everything")
                        .queryParam("q", query)
                            .queryParamIfPresent("page", Optional.of(page))
                        .queryParam("apiKey", newsApiKey)
                        .build())
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> {
                  var httpStatusCode = clientResponse.statusCode();
                  log.atInfo().log(
                      "NewsApiClient: fetchNews returned an error with status: {}", httpStatusCode);
                  return clientResponse
                      .bodyToMono(String.class)
                      .map(
                          errorResponse ->
                              new ClientException(
                                  HttpStatus.resolve(httpStatusCode.value()), errorResponse));
                })
            .bodyToMono(NewsApiResponse.class)
            .block();
    return Objects.requireNonNull(response);
  }

  public static ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(
        clientRequest -> {
          log.atInfo().log("NewsAPIClient Headers: {}", clientRequest.headers());
          log.atInfo().log("NewsAPIClient Url: {}", clientRequest.url());
          return Mono.just(clientRequest);
        });
  }
}
