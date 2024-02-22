package ru.maksimov.MovieService.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maksimov.MovieService.models.Actor;
import ru.maksimov.MovieService.models.Movie;
import ru.maksimov.MovieService.models.Producer;
import ru.maksimov.MovieService.repositories.MoviesRepository;
import ru.maksimov.MovieService.util.PatchHelper;
import ru.maksimov.MovieService.util.exceptions.ActorNotFoundException;
import ru.maksimov.MovieService.util.exceptions.MovieNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.maksimov.MovieService.util.exceptions.ProducerNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сервиса для работы с фильмами.
 */
@Service
@Transactional(readOnly = true)
public class MoviesServiceImpl implements MoviesService{

    private final MoviesRepository moviesRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public MoviesServiceImpl(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    /**
     * Получить все фильмы.
     *
     * @return Список всех фильмов.
     */
    @Override
    public List<Movie> findAll() {
        return moviesRepository.findAll();
    }

    /**
     * Найти фильм по идентификатору.
     *
     * @param id Идентификатор фильма.
     * @return Найденный фильм.
     * @throws MovieNotFoundException если фильм не найден.
     */
    @Override
    public Movie findById(Integer id) {
        return moviesRepository.findById(id).orElseThrow(
                () -> new MovieNotFoundException("Movie with id " + id + " not found"));
    }

    /**
     * Сохранить фильм.
     *
     * @param movie Фильм для сохранения.
     */
    @Override
    @Transactional
    public void save(Movie movie) {
        // TODO need to implement check producerId, actorsId is present(P.S: при отправке запроса с несуществующими producerId, actorId ответ необходимо сделать человекочитаемым)
        moviesRepository.save(movie);
    }

    /**
     * Обновить фильм.
     *
     * @param id           Идентификатор фильма для обновления.
     * @param movieUpdates Новая версия фильма.
     * @return Обновленный фильм.
     * @throws MovieNotFoundException   если фильм не найден.
     * @throws ProducerNotFoundException если продюсер не найден.
     * @throws ActorNotFoundException    если актер не найден.
     */
    @Override
    @Transactional
    public Movie update(int id, Movie movieUpdates) {
        Movie existingMovie = entityManager.find(Movie.class, id);
        if (existingMovie == null) {
            throw new MovieNotFoundException("Movie with id " + id + " not found!");
        }


        PatchHelper.copyNonNullProperties(movieUpdates, existingMovie);

        if(movieUpdates.getProducer() != null) {
            Producer existingProducer = entityManager.find(Producer.class, movieUpdates.getProducer().getId());
            if (existingProducer == null) {
                throw new ProducerNotFoundException("Producer with id " + movieUpdates.getProducer().getId() + " not found!");
            }
            existingMovie.setProducer(existingProducer);
        }

        if(movieUpdates.getActors() != null) {
            List<Actor> existingActors = new ArrayList<>();
            for (Actor actor : movieUpdates.getActors()) {
                Actor existingActor = entityManager.find(Actor.class, actor.getId());
                if (existingActor == null) {
                    throw new ActorNotFoundException("Actor with id " + actor.getId() + " not found!");
                }
                existingActors.add(existingActor);
            }

            existingMovie.setActors(existingActors);
        }


        return entityManager.merge(existingMovie);
    }

    /**
     * Удалить фильм.
     *
     * @param movie Фильм для удаления.
     */
    @Override
    @Transactional
    public void delete(Movie movie) {
        moviesRepository.delete(movie);
    }
}
