package com.example.news.aggregator.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientException extends RuntimeException {

    private final HttpStatus status;

    public ClientException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
