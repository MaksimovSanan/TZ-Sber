package ru.maksimov.MovieService.util.exceptions;

/**
 * Исключение, выбрасываемое при отсутствии фильма.
 */
public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(String message) {
        super(message);
    }
}
