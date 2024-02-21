package ru.maksimov.MovieService.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.maksimov.MovieService.util.exceptions.ActorNotFoundException;
import ru.maksimov.MovieService.util.exceptions.MovieNotFoundException;
import ru.maksimov.MovieService.util.exceptions.ProducerNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<String> handleException(MovieNotFoundException movieNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(movieNotFoundException.getMessage());
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(ProducerNotFoundException producerNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(producerNotFoundException.getMessage());
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(ActorNotFoundException actorNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(actorNotFoundException.getMessage());
    }
}
