package ru.maksimov.MovieService.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maksimov.MovieService.models.Producer;
import ru.maksimov.MovieService.repositories.ProducersRepository;
import ru.maksimov.MovieService.util.PatchHelper;
import ru.maksimov.MovieService.util.exceptions.MovieNotFoundException;
import ru.maksimov.MovieService.util.exceptions.ProducerNotFoundException;

import java.util.List;

/**
 * Реализация сервиса для работы с продюсерами фильмов.
 */
@Service
@Transactional(readOnly = true)
public class ProducersServiceImpl implements ProducersService{

    private final ProducersRepository producersRepository;
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Конструктор класса ProducersServiceImpl.
     *
     * @param producersRepository Репозиторий продюсеров фильмов.
     */
    @Autowired
    public ProducersServiceImpl(ProducersRepository producersRepository) {
        this.producersRepository = producersRepository;
    }

    /**
     * Получить все записи о продюсерах фильмов.
     *
     * @return Список всех продюсеров фильмов.
     */
    @Override
    public List<Producer> findAll() {
        return producersRepository.findAll();
    }

    /**
     * Найти продюсера фильма по его идентификатору.
     *
     * @param id Идентификатор продюсера фильма.
     * @return Найденный продюсер фильма.
     * @throws ProducerNotFoundException если продюсер с указанным идентификатором не найден.
     */
    @Override
    public Producer findById(Integer id) {
        return producersRepository.findById(id).orElseThrow(
                () -> new ProducerNotFoundException("Producer with id " + id + " not found!"));
    }

    /**
     * Сохранить продюсера фильма.
     *
     * @param producer Продюсер фильма для сохранения.
     */
    @Override
    @Transactional
    public void save(Producer producer) {
        producersRepository.save(producer);
    }

    /**
     * Обновить информацию о продюсере фильма.
     *
     * @param id             Идентификатор продюсера фильма для обновления.
     * @param producerUpdated Обновленная информация о продюсере фильма.
     * @return Обновленный продюсер фильма.
     * @throws ProducerNotFoundException если продюсер фильма с указанным идентификатором не найден.
     */
    @Override
    @Transactional
    public Producer update(int id, Producer producerUpdated) {
        Producer existingProducer = entityManager.find(Producer.class, id);
        if (existingProducer == null) {
            throw new MovieNotFoundException("Producer with id " + id + " not found!");
        }


        PatchHelper.copyNonNullProperties(producerUpdated, existingProducer);


        return entityManager.merge(existingProducer);
    }

    /**
     * Удалить продюсера фильма.
     *
     * @param producer Продюсер фильма для удаления.
     */
    @Override
    @Transactional
    public void delete(Producer producer) {
        producersRepository.delete(producer);
    }
}
