## News Aggregator

## Description

News Aggregator is a small project that fetches news articles based on the input keywords or phrases. It fetches articles using https://newsapi.org/ with the source, author, description, publication date, sentiment analysis and source credibility.

Features:

- Search for news articles
- Sentiment analysis
- Source credibility indicators
- Source diveristy analysis
- Client-side filtering and sorting
- Result specific alerts
- In-memory caching
- Responsive design

## Tech Stack

Frontend

- React
- TypeScript
- Material UI
- Axios
- Vitest

Backend

- Java 21
- Spring Boot
- Caffeine Cache
- Maven

Infrastructure

- Docker
- GitHub Actions
- Azure Container Registry
- Azure Container Apps

## Sequence Diagram

[Simple positive flows](./news-aggregator-sequence.drawio.png)

## Further Documentation

- [Frontend Documentation](./news-aggregator-app/README.md)
- [Backend Documentation](./news-aggregator-microservice/README.md)
