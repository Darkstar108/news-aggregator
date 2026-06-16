package com.example.news.aggregator.service.strategy;

import com.example.news.aggregator.constant.Sentiment;
import com.example.news.aggregator.model.NewsItem;
import com.example.news.aggregator.util.SentimentLexiconTSVReader;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Component
public class SentimentAnalysisStrategy implements NewsItemAnalysisStrategy {

  private static final HashMap<String, Double> LEXICON;
  private static final Set<String> NEGATIONS = Set.of("not", "no", "never", "didnt", "isnt", "wasnt");
  static {
    LEXICON = SentimentLexiconTSVReader.getSentimentLexiconMap();
  }

  @Override
  public void analyse(NewsItem newsItem) {
    var textToAnalyse = newsItem.title + newsItem.description;
    String[] tokens =
        textToAnalyse
            .toLowerCase()
            .replaceAll("[^a-zA-Z\\s]", "")
            .split("\\s+");
    double sentimentScore = analyseSentimentScore(tokens);
    Sentiment sentiment = Sentiment.NEUTRAL;
    if(sentimentScore > 0.0) {
      sentiment = Sentiment.POSITIVE;
    } else if(sentimentScore < 0.0){
      sentiment = Sentiment.NEGATIVE;
    }
    newsItem.setSentiment(sentiment);
    newsItem.setSentimentScore(Math.round(sentimentScore * 100.0)/100.0);
  }

  private double analyseSentimentScore(String[] tokens) {
    double sentimentScore = 0.0;
    boolean negation = false;
    for (String token : tokens) {
      if (NEGATIONS.contains(token)) {
        negation = true;
      }
      if (LEXICON.containsKey(token)) {
        var tokenScore = LEXICON.get(token);
        if(negation) {
          tokenScore *= -1;
        }
        sentimentScore += tokenScore;
      }
      negation = false;
    }
    return sentimentScore;
  }
}
