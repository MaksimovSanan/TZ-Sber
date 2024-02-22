package ru.maksimov.MovieService.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maksimov.MovieService.dto.moviesDto.MovieDto;
import ru.maksimov.MovieService.dto.moviesDto.MovieSimpleDto;
import ru.maksimov.MovieService.dto.moviesDto.NewMovieDto;
import ru.maksimov.MovieService.models.Movie;
import ru.maksimov.MovieService.services.MoviesService;
import ru.maksimov.MovieService.util.BindingResultHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для обработки HTTP-запросов, связанных с фильмами.
 */
@RestController
@RequestMapping("/api/movies")
public class MoviesController {
    private final MoviesService moviesService;
    private final ModelMapper modelMapper;

    /**
     * Конструктор контроллера фильмов.
     *
     * @param moviesService Сервис для работы с фильмами.
     * @param modelMapper   Инструмент для преобразования моделей и dto фильмов.
     */
    @Autowired
    public MoviesController(MoviesService moviesService, ModelMapper modelMapper) {
        this.moviesService = moviesService;
        this.modelMapper = modelMapper;
    }

    /**
     * Получить список всех фильмов.
     *
     * @return Список всех фильмов.
     */
    @GetMapping
    public ResponseEntity<List<MovieSimpleDto>> getAll() {
        List<Movie> movies = moviesService.findAll();
        return ResponseEntity.ok(movies.stream().map(this::convertToMovieSimpleDto).collect(Collectors.toList()));
    }

    /**
     * Получить информацию о фильме по его идентификатору.
     *
     * @param id Идентификатор фильма.
     * @return Информация о фильме.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> findOne(@PathVariable("id") int id) {
        return ResponseEntity.ok(convertToMovieDto(moviesService.findById(id)));
    }

    /**
     * Создать новый фильм.
     *
     * @param newMovieDto   Данные для нового фильма.
     * @param bindingResult Результаты валидации данных.
     * @return Статус операции создания фильма.
     */
    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid NewMovieDto newMovieDto,
                                             BindingResult bindingResult) {

        String errMsg = BindingResultHandler.handlingBindingResult(bindingResult);
        if(!errMsg.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errMsg);
        }
        Movie movie = modelMapper.map(newMovieDto, Movie.class);


        try {
            moviesService.save(movie);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating movie:" + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Movie created successfully");
    }

    /**
     * Удалить фильм по его идентификатору.
     *
     * @param id Идентификатор фильма для удаления.
     * @return Статус операции удаления фильма.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        Movie movie = moviesService.findById(id);
        try{
            moviesService.delete(movie);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting movie:" + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Movie was deleted");
    }

    /**
     * Обновить информацию о фильме.
     *
     * @param id                Идентификатор фильма для обновления.
     * @param movieToBeUpdated  Обноввленные данные о фильме.
     * @return Информация об обновленном фильме.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<MovieDto> update(@PathVariable("id") int id,
                                             @RequestBody NewMovieDto movieToBeUpdated) {

        Movie movie = modelMapper.map(movieToBeUpdated, Movie.class);

        Movie updatedMovie = moviesService.update(id, movie);

        return ResponseEntity.ok(convertToMovieDto(updatedMovie));
    }

    /**
     * Преобразовать сущность фильма в DTO объект с полной информацией.
     *
     * @param movie Сущность фильма.
     * @return DTO объект с полной информацией о фильме.
     */
    private MovieDto convertToMovieDto(Movie movie) {
        return modelMapper.map(movie, MovieDto.class);
    }
    /**
     * Преобразовать сущность фильма в DTO объект с минимальной информацией.
     *
     * @param movie Сущность фильма.
     * @return DTO объект с минимальной информацией о фильме.
     */
    private MovieSimpleDto convertToMovieSimpleDto(Movie movie) {
        return modelMapper.map(movie, MovieSimpleDto.class);
    }
}
