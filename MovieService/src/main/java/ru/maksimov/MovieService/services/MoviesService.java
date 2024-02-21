package ru.maksimov.MovieService.services;

import ru.maksimov.MovieService.models.Movie;

import java.util.List;

public interface MoviesService {

    public List<Movie> findAll();
    public Movie findById(Integer id);
    public void save(Movie movie);
    public Movie update(int id, Movie movieUpdates);
    public void delete(Movie movie);
}
