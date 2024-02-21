package ru.maksimov.MovieService.services;

import ru.maksimov.MovieService.models.Movie;

import java.util.List;

public interface CRUDService<T> {
    public List<T> findAll();
    public T findById(Integer id);
    public void save(T entity);
    public T update(int id, T entity);
    public void delete(T entity);
}
