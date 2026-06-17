package com.example.news.aggregator.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServerException extends RuntimeException {

    public ServerException(String message) {
        super(message);
    }

}
