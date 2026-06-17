package com.example.news.aggregator.exception;

import com.example.news.aggregator.model.Error;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class NewsAggregatorExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception ex) {
    log.atInfo().log("NewsAggregatorExceptionHandler: handleException {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            Error.builder()
                .reasonCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .build());
  }

  @ExceptionHandler(ClientException.class)
  public ResponseEntity<?> handleClientException(ClientException ex) {
    log.atInfo().log("NewsAggregatorExceptionHandler: handleClientException {}", ex.getMessage());
    return ResponseEntity.status(ex.getStatus())
        .body(
            Error.builder()
                .reasonCode(ex.getStatus().getReasonPhrase())
                .message(ex.getMessage())
                .build());
  }

  @ExceptionHandler(ServerException.class)
  public ResponseEntity<?> handleServerException(ServerException ex) {
    log.atInfo().log("NewsAggregatorExceptionHandler: handleServerException {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            Error.builder()
                .reasonCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .build());
  }
}
