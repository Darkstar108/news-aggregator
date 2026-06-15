package com.example.news.aggregator.model.newsapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsApiResponse {
    @Schema(description = "Status of response", example = "ok")
    public String status;
    @Schema(description = "Number of results", example = "100")
    public Integer totalResults;
    public List<Article> articles;
}
