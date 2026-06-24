package com.example.news.aggregator.config;

import com.example.news.aggregator.constant.Constants;
import com.example.news.aggregator.model.NewsAggregatorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheClient {
    private final CacheManager cacheManager;

    public NewsAggregatorResponse fetchResponseFromCache(String query, Integer page) {
        var cache = cacheManager.getCache(Constants.CACHE_NAME);
        var key = new SimpleKey(query, page);
        if (cache != null && cache.get(key) != null) {
            return cache.get(key, NewsAggregatorResponse.class);
        }
        return null;
    }

    public void setResponseInCache(String query, Integer page, NewsAggregatorResponse newsAggregatorResponse) {
        var cache = cacheManager.getCache(Constants.CACHE_NAME);
        var key = new SimpleKey(query, page);
        if (cache != null) {
            cache.put(key, newsAggregatorResponse);
        }
    }
}
