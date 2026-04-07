package com.example.taskmanager.web;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handlerException(
            Exception e
    ){
        var errorDto = new ErrorResponseDto(
                "Internal server error",
                e.getMessage(),
                LocalDateTime.now()
        );
        log.error("Headler exception: ", e);
        return ResponseEntity.status(500).body(errorDto);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlerNotFoundException(
            EntityNotFoundException e
    ){
        var errorDto = new ErrorResponseDto(
                "Not found entity",
                e.getMessage(),
                LocalDateTime.now()
        );
        log.error("Headler exception: ", e);
        return ResponseEntity.status(404).body(errorDto);
    }
    @ExceptionHandler(exception = {
        IllegalArgumentException.class,
            IllegalStateException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponseDto> handlerBadRequest(
            Exception e
    ){
        var errorDto = new ErrorResponseDto(
                "Invalid argument(s)",
                e.getMessage(),
                LocalDateTime.now()
        );
        log.error("Headler exception: ", e);
        return ResponseEntity.status(400).body(errorDto);
    }
}
