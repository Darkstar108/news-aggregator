# News Aggregator Microservice

## Overview

Java/SpringBoot microservice that exposes one REST endpoint to fetch news articles based on the query param provided. The servic further enhances the response with sentiment analysis, source credibility indicators and source diversity anaylsis.

## Architecture and Design

### Design Patterns

- **Strategy Pattern**: Used to encapsulate article analysis logic with an analyse() method which can be easily extended to create new article analysis strategies
- **Facade Pattern**: NewsAggregatorService provides a simple interface with fetchNews() method to be used by the controller without exposing underlying complexity
- **Builder Pattern**: Implemented using Lombok @Builder annotation to easily create objects

### SOLID Principles

- **Single Responsibility**: All classes have a single responsibility. Various strategies ensure that each analysis happens in its own class
- **Liskov Substitution**: All strategy implementations can substitute the parent interface without breaking
- **Dependency Inversion**: NewsAggregatorController depends on the NewsAggregatorService Interface and not its implemenatation

### In-Memory Caching

The application makes use of in-memory caching using Caffiene with a TTL of 5 mins to improve performance and return almost instantaneous responses to repeated queries

### News Analysis Algorithms

#### Sentiment Analysis

For sentiment analysis, a rule-based algorithm is used that analyses the title and description of the article against a labelled lexicon. The lexicon, AFINN created by Finn Årup Nielsen has 2,477 English words along with a normalised sentiment score from -1 (negative) to +1 (positive).

For each word present in the lexicon, it's corresponding sentiment score is added to the total. If the word is preceded by certain words like "not", "no", "never" etc, it's sentiment is reversed when adding to the total.

If the total is positive, the sentiment of the article is marked as positive and if the total is negative, the sentiment of the article is marked as negative.

#### Source Credibility Analysis

The Source credibility indicator for each article is set by checking its source against a static yaml file that is loaded on startup which contains a list of sources and their credibility indicator.

#### Source Diversity Analysis

To analyse the diversity of sources, the Shannon Diversity Index is used which is a a mathematical measure used to quantify the diversity of entities (such as species, patch types, or categories) within a dataset or ecosystem. A value of 0 indicates no diversity and higher values indicate a richer ecosystem.

## Local Usage

To run the microservice locally, first build the service using

```
mvn clean install
```

Then start the service with

```
mvn spring-boot:run
```

## Related Documentation

- [Project Overview](../README.md)
- [Frontend Documentation](../news-aggregator-app/README.md)
