package com.example.news.aggregator.model;

import com.example.news.aggregator.constant.Sentiment;
import com.example.news.aggregator.constant.SourceCredibility;
import com.example.news.aggregator.model.newsapi.Source;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsItem {
    @Schema(description = "UUID", example = "1920613e-60fc-469d-98f2-0c9f0d24213b")
    public String id;
    @Schema(description = "Source of article", example = "The Verge")
    public String source;
    @Schema(description = "Name of author", example = "John Doe")
    public String author;
    @Schema(description = "Title of article", example = "Title")
    public String title;
    @Schema(description = "Description of article", example = "Description")
    public String description;
    @Schema(description = "Url to article", example = "http://website.com")
    public String url;
    @Schema(description = "Url to image in the article", example = "http://website.com/image.jpg")
    public String urlToImage;
    @Schema(description = "Article published at", example = "2026-05-29T17:09:12Z")
    public String publishedAt;
    @Schema(description = "Sentiment of article", example = "POSITIVE")
    public Sentiment sentiment;
    @Schema(description = "Source credibility of article", example = "HIGH")
    public SourceCredibility sourceCredibility;
}
