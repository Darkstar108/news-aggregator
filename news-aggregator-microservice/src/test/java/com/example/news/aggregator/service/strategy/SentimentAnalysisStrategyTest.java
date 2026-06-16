package com.example.news.aggregator.service.strategy;

import com.example.news.aggregator.constant.Sentiment;
import com.example.news.aggregator.model.NewsItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class SentimentAnalysisStrategyTest {

    @InjectMocks
    SentimentAnalysisStrategy sentimentAnalysisStrategy;

    @ParameterizedTest
    @MethodSource("provideNewsItemsForSentimentAnalysis")
    void shouldAnalyseSentimentOfNewsItem(String title, String description, Sentiment expectedSentiment) {
        var newsItem = NewsItem.builder()
                .title(title)
                .description(description)
                .build();
        sentimentAnalysisStrategy.analyse(newsItem);
        Assertions.assertEquals(expectedSentiment, newsItem.sentiment);
    }

    private static Stream<Arguments> provideNewsItemsForSentimentAnalysis() {
        return Stream.of(
                Arguments.of("AI will end humanity as we know it", "AI is destroying creativity and attention spans", Sentiment.NEGATIVE)
        );
    }
}
