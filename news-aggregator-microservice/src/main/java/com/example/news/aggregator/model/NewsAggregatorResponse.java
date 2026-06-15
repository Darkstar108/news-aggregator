package com.example.news.aggregator.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsAggregatorResponse {
    public List<NewsItem> newsItems;
    public List<String> souceList;
    public List<String> alerts;
}
