package ru.maksimov.MovieService.util.exceptions;

/**
 * Исключение, выбрасываемое при отсутствии продюсера.
 */
public class ProducerNotFoundException extends RuntimeException{
    public ProducerNotFoundException(String message) {
        super(message);
    }
}
