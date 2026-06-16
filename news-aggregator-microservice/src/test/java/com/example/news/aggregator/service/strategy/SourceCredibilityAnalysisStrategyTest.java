package com.example.news.aggregator.service.strategy;

import com.example.news.aggregator.constant.Sentiment;
import com.example.news.aggregator.constant.SourceCredibility;
import com.example.news.aggregator.model.NewsItem;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

@ExtendWith(MockitoExtension.class)
public class SourceCredibilityAnalysisStrategyTest {

    @InjectMocks
    SourceCredibilityAnalysisStrategy sourceCredibilityAnalysisStrategy;

    @ParameterizedTest
    @MethodSource("provideNewsItemsForSentimentAnalysis")
    void shouldAnalyseSourceCredibilityOfNewsItem(String source, SourceCredibility expectedCredibility) {
        var newsItem = NewsItem.builder()
                .source(source)
                .build();
        sourceCredibilityAnalysisStrategy.analyse(newsItem);
        Assertions.assertEquals(expectedCredibility, newsItem.sourceCredibility);
    }

    private static Stream<Arguments> provideNewsItemsForSentimentAnalysis() {
        return Stream.of(
                Arguments.of("BBC", SourceCredibility.HIGH),
                Arguments.of("ABC", SourceCredibility.LOW)
        );
    }
}
