package com.example.news.aggregator.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SentimentLexiconTSVReader {
  public static final String SENTIMENT_LEXICON_FILE_NAME = "src/main/resources/AFINN.tsv";

  public static HashMap<String, Double> getSentimentLexiconMap() {
    var sentimentLexiconMap = new HashMap<String, Double>();
    try {
      BufferedReader TSVReader = new BufferedReader(new FileReader(SENTIMENT_LEXICON_FILE_NAME));
      String line = TSVReader.readLine();
      line = TSVReader.readLine();
      while (line != null) {
        String[] lineItems = line.split("\t");
        sentimentLexiconMap.put(lineItems[0], Double.parseDouble(lineItems[2]));
        line = TSVReader.readLine();
      }
      TSVReader.close();
    } catch (Exception e) {
      log.atError().setCause(e).log("Error reading SentimentLexiconTSVFile");
    }
    return sentimentLexiconMap;
  }
}
