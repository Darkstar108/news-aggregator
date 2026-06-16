package com.example.news.aggregator.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Error {
    @Schema(description = "Reason code of Error", example = "Bad Request")
    private String reasonCode;
    @Schema(description = "Error message", example = "Something went wrong")
    private String message;
}
