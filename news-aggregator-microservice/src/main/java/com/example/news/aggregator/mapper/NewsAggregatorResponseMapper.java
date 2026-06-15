package com.example.news.aggregator.mapper;

import com.example.news.aggregator.model.NewsItem;
import com.example.news.aggregator.model.newsapi.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NewsAggregatorResponseMapper {

    NewsAggregatorResponseMapper INSTANCE = Mappers.getMapper(NewsAggregatorResponseMapper.class);

    @Mapping(target = "source", source = "source.name")
    NewsItem mapToNewsItem(Article article);

    List<NewsItem> mapToNewsItems(List<Article> articles);
}
