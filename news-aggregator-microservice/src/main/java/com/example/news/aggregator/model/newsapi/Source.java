package com.example.news.aggregator.model.newsapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Source {
    @Schema(description = "Id of the source", example = "the-verge")
    public String id;
    @Schema(description = "Name of the source", example = "The Verge")
    public String name;
}
