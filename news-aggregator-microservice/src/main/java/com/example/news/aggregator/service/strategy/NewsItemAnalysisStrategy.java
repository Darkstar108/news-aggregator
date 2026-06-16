package com.example.news.aggregator.service.strategy;

import com.example.news.aggregator.model.NewsItem;

public interface NewsItemAnalysisStrategy {
    void analyse(NewsItem newsItem);
}
