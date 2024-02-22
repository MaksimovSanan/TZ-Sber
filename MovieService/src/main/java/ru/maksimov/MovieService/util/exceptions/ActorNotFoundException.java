package ru.maksimov.MovieService.util.exceptions;

/**
 * Исключение, выбрасываемое при отсутствии актера.
 */
public class ActorNotFoundException extends RuntimeException{
    public ActorNotFoundException(String message) {
        super(message);
    }
}
