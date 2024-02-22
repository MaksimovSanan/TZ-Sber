package ru.maksimov.MovieService.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maksimov.MovieService.models.Actor;
import ru.maksimov.MovieService.models.Movie;
import ru.maksimov.MovieService.models.Producer;
import ru.maksimov.MovieService.repositories.ActorsRepository;
import ru.maksimov.MovieService.util.PatchHelper;
import ru.maksimov.MovieService.util.exceptions.ActorNotFoundException;
import ru.maksimov.MovieService.util.exceptions.MovieNotFoundException;
import ru.maksimov.MovieService.util.exceptions.ProducerNotFoundException;

import java.util.List;

/**
 * Реализация сервиса для работы с актерами.
 */
@Service
@Transactional(readOnly = true)
public class ActorsServiceImpl implements ActorsService{
    private final ActorsRepository actorsRepository;
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Конструктор для создания объекта ActorsServiceImpl с указанным репозиторием актеров.
     *
     * @param actorsRepository Репозиторий актеров.
     */
    @Autowired
    public ActorsServiceImpl(ActorsRepository actorsRepository) {
        this.actorsRepository = actorsRepository;
    }

    /**
     * Получить список всех актеров.
     *
     * @return Список всех актеров.
     */
    @Override
    public List<Actor> findAll() {
        return actorsRepository.findAll();
    }

    /**
     * Найти актера по его идентификатору.
     *
     * @param id Идентификатор актера.
     * @return Найденный актер.
     * @throws ActorNotFoundException если актер с указанным идентификатором не найден.
     */
    @Override
    public Actor findById(Integer id) {
        return actorsRepository.findById(id).orElseThrow(
                () -> new ActorNotFoundException("Actor with id " + id + " not found!"));
    }

    /**
     * Сохранить нового актера.
     *
     * @param actor Новый актер для сохранения.
     */
    @Override
    @Transactional
    public void save(Actor actor) {
        actorsRepository.save(actor);
    }

    /**
     * Обновить информацию об актере.
     *
     * @param id            Идентификатор актера для обновления.
     * @param actorUpdated  Новая информация об актере.
     * @return Обновленный актер.
     * @throws ActorNotFoundException если актер с указанным идентификатором не найден.
     */
    @Override
    @Transactional
    public Actor update(int id, Actor actorUpdated) {
        Actor existingActor = entityManager.find(Actor.class, id);
        if (existingActor == null) {
            throw new MovieNotFoundException("Actor with id " + id + " not found!");
        }

        PatchHelper.copyNonNullProperties(actorUpdated, existingActor);

        return entityManager.merge(existingActor);
    }

    /**
     * Удалить актера.
     *
     * @param actor Актер для удаления.
     */
    @Override
    @Transactional
    public void delete(Actor actor) {
        for (Movie movie : actor.getMovies()) {
            movie.getActors().remove(actor);
        }
        actor.getMovies().clear();

        actorsRepository.delete(actor);
    }
}
