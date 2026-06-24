package com.example.news.aggregator.service;

import com.example.news.aggregator.model.NewsAggregatorResponse;

public interface NewsAggregatorService {

    public NewsAggregatorResponse fetchNews(String query, Integer page);
}
