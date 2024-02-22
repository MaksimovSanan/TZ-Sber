package ru.maksimov.MovieService.services;

import ru.maksimov.MovieService.models.Movie;

import java.util.List;

/**
 * Интерфейс сервиса CRUD для работы с сущностями.
 *
 * @param <T> Тип сущности.
 */
public interface CRUDService<T> {
    /**
     * Получить все сущности.
     *
     * @return Список всех сущностей.
     */
    public List<T> findAll();
    /**
     * Найти сущность по идентификатору.
     *
     * @param id Идентификатор сущности.
     * @return Найденная сущность.
     */
    public T findById(Integer id);
    /**
     * Сохранить сущность.
     *
     * @param entity Сущность для сохранения.
     */
    public void save(T entity);
    /**
     * Обновить сущность.
     *
     * @param id      Идентификатор сущности для обновления.
     * @param entity  Новая версия сущности.
     * @return Обновленная сущность.
     */
    public T update(int id, T entity);
    /**
     * Удалить сущность.
     *
     * @param entity Сущность для удаления.
     */
    public void delete(T entity);
}
