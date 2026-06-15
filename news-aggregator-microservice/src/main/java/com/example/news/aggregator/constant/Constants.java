package com.example.news.aggregator.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String OVERALL_NEGATIVE_SENTIMENT_ALERT = "High volume of negative news - Content may be distressing";
    public static final String LIMITED_SOURCE_DIVERSITY_ALERT = "Limited source diversity - Verify from multiple outlets";
    public static final String BREAKING_NEWS_ALERT = "Breaking News Alert - Recently published";
    public static final String DIVERSE_VIEWPOINTS_ALERT = "Diverse viewpoints available - Read multiple perspectives";
}
