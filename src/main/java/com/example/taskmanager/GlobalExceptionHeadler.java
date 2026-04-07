package com.example.taskmanager;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHeadler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHeadler.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> headlerException(
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
    public ResponseEntity<ErrorResponseDto> headlerNotFoundException(
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
    public ResponseEntity<ErrorResponseDto> headlerBadRequest(
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
