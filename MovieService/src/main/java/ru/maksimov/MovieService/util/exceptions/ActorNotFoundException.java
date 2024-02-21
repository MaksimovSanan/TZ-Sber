package ru.maksimov.MovieService.util.exceptions;

public class ActorNotFoundException extends RuntimeException{
    public ActorNotFoundException(String message) {
        super(message);
    }
}
