package ru.maksimov.MovieService.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.maksimov.MovieService.util.exceptions.ActorNotFoundException;
import ru.maksimov.MovieService.util.exceptions.MovieNotFoundException;
import ru.maksimov.MovieService.util.exceptions.ProducerNotFoundException;

/**
 * Глобальный обработчик исключений для перехвата и обработки исключений, которые могут возникнуть в контроллерах.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Обработчик исключения MovieNotFoundException.
     *
     * @param movieNotFoundException Исключение, указывающее на то, что фильм не найден.
     * @return ResponseEntity с HTTP-статусом NOT_FOUND и сообщением об ошибке.
     */
    @ExceptionHandler
    private ResponseEntity<String> handleException(MovieNotFoundException movieNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(movieNotFoundException.getMessage());
    }

    /**
     * Обработчик исключения ProducerNotFoundException.
     *
     * @param producerNotFoundException Исключение, указывающее на то, что продюсер не найден.
     * @return ResponseEntity с HTTP-статусом NOT_FOUND и сообщением об ошибке.
     */
    @ExceptionHandler
    private ResponseEntity<String> handleException(ProducerNotFoundException producerNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(producerNotFoundException.getMessage());
    }

    /**
     * Обработчик исключения ActorNotFoundException.
     *
     * @param actorNotFoundException Исключение, указывающее на то, что актер не найден.
     * @return ResponseEntity с HTTP-статусом NOT_FOUND и сообщением об ошибке.
     */
    @ExceptionHandler
    private ResponseEntity<String> handleException(ActorNotFoundException actorNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(actorNotFoundException.getMessage());
    }
}
