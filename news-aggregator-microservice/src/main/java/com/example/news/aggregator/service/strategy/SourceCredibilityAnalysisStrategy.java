package com.example.news.aggregator.service.strategy;

import com.example.news.aggregator.constant.SourceCredibility;
import com.example.news.aggregator.model.NewsItem;
import com.example.news.aggregator.util.YamlReaderUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SourceCredibilityAnalysisStrategy implements NewsItemAnalysisStrategy {

  private static final Map<String, String> SOURCE_CREDIBILITY_MAP;

  static {
    SOURCE_CREDIBILITY_MAP = YamlReaderUtil.getSourceCredibilityMap();
  }

  @Override
  public void analyse(NewsItem newsItem) {
    var sourceCredibilityFromYaml = SOURCE_CREDIBILITY_MAP.get(newsItem.source);
    SourceCredibility sourceCredibility =
        sourceCredibilityFromYaml != null
            ? SourceCredibility.valueOf(sourceCredibilityFromYaml)
            : SourceCredibility.LOW;
    newsItem.setSourceCredibility(sourceCredibility);
  }
}
