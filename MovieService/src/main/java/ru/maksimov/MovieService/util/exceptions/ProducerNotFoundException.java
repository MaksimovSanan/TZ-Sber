package ru.maksimov.MovieService.util.exceptions;

public class ProducerNotFoundException extends RuntimeException{
    public ProducerNotFoundException(String message) {
        super(message);
    }
}
