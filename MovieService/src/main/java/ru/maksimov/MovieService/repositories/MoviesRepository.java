package ru.maksimov.MovieService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maksimov.MovieService.models.Movie;

/**
 * Репозиторий для работы с фильмами в базе данных.
 */
@Repository
public interface MoviesRepository extends JpaRepository<Movie, Integer> {
}
