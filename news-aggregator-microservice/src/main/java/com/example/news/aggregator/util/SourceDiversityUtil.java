package com.example.news.aggregator.util;

import com.example.news.aggregator.model.NewsAggregatorResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SourceDiversityUtil {

  // Use Shannon-Wiener Index: (H = -\sum p_{i} \ln(p_{i})\) to calculate diversity. Greater than
  // 3.0 is diverse
  public static double analyseSourceDiversity(
      NewsAggregatorResponse newsAggregatorResponse, Map<String, Integer> sourceCounts) {
    if (newsAggregatorResponse.newsItems.isEmpty()) {
      return 0.0;
    }

    double totalArticles = newsAggregatorResponse.newsItems.size();
    double shannonIndexSum = 0.0;

    for (Map.Entry<String, Integer> entry : sourceCounts.entrySet()) {
      double pi = entry.getValue() / totalArticles;
      shannonIndexSum += pi * Math.log(pi);
    }

    log.atInfo().log("Source diversity score: {}", -shannonIndexSum);
    shannonIndexSum = -(Math.round(shannonIndexSum * 10.0) / 10.0);
    return shannonIndexSum;
  }
}
