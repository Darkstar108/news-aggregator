package com.example.news.aggregator.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class YamlReaderUtilTest {

    @Test
    void shouldGetSourceCredibilityMap() {
        var sourceCredibilityMap = YamlReaderUtil.getSourceCredibilityMap();
        Assertions.assertNotNull(sourceCredibilityMap);
    }
}
