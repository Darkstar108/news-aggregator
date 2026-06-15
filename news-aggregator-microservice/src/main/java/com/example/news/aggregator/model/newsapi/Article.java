package com.example.news.aggregator.model.newsapi;

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
public class Article {
    public Source source;
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
    @Schema(description = "Content of article", example = "Content")
    public String content;
}
