package com.example.news.aggregator.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SentimentLexiconTSVReader {

  public static HashMap<String, Double> getSentimentLexiconMap() {
    var sentimentLexiconMap = new HashMap<String, Double>();
    try {
      InputStream inputStream =
          SentimentLexiconTSVReader.class.getClassLoader().getResourceAsStream("AFINN.tsv");
      if (inputStream == null) {
        throw new FileNotFoundException();
      }
      BufferedReader TSVReader = new BufferedReader(new InputStreamReader(inputStream));
      TSVReader.readLine();
      String line;
      line = TSVReader.readLine();
      while (line != null) {
        String[] lineItems = line.split("\t");
        sentimentLexiconMap.put(lineItems[0], Double.parseDouble(lineItems[2]));
        line = TSVReader.readLine();
      }
      TSVReader.close();
    } catch (Exception e) {
      log.atError().setCause(e).log("Error reading SentimentLexiconTSVFile");
      throw new RuntimeException(e);
    }
    return sentimentLexiconMap;
  }
}
