package com.example.news.aggregator.controller;

import com.example.news.aggregator.model.NewsAggregatorResponse;
import com.example.news.aggregator.service.NewsAggregatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "${allowed.origins}")
public class NewsAggregatorController {
  private final NewsAggregatorService newsAggregatorService;

  @Operation(
      description = "Fetch news articles with sentiment and source credibility indicators",
      operationId = "fetchNews")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = NewsAggregatorResponse.class))
            })
      })
  @GetMapping("/fetch-news")
  public ResponseEntity<NewsAggregatorResponse> fetchNews(
      @RequestParam(name = "query")
          @Parameter(description = "Query to fetch news articles for", example = "AI")
          @NotBlank
          @Size(min = 2, max = 500)
          String query,
      @RequestParam(name = "page", required = false, defaultValue = "1")
      @Parameter(description = "Page of results", example = "1")
      Integer page) {
    log.atInfo().log("Controller: fetchNews called with query: {} and page: {}", query, page);
    return ResponseEntity.ok().body(newsAggregatorService.fetchNews(query, page));
  }
}
