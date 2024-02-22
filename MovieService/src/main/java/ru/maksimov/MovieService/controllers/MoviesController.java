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

@RestController
@RequestMapping("/movies")
public class MoviesController {
    private final MoviesService moviesService;
    private final ModelMapper modelMapper;

    @Autowired
    public MoviesController(MoviesService moviesService, ModelMapper modelMapper) {
        this.moviesService = moviesService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<MovieSimpleDto>> getAll() {
        List<Movie> movies = moviesService.findAll();
        return ResponseEntity.ok(movies.stream().map(this::convertToMovieSimpleDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> findOne(@PathVariable("id") int id) {
        return ResponseEntity.ok(convertToMovieDto(moviesService.findById(id)));
    }

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

    @PatchMapping("/{id}")
    public ResponseEntity<MovieDto> update(@PathVariable("id") int id,
                                             @RequestBody NewMovieDto movieToBeUpdated) {

        Movie movie = modelMapper.map(movieToBeUpdated, Movie.class);

        Movie updatedMovie = moviesService.update(id, movie);

        return ResponseEntity.ok(convertToMovieDto(updatedMovie));
    }

    private MovieDto convertToMovieDto(Movie movie) {
        return modelMapper.map(movie, MovieDto.class);
    }
    private MovieSimpleDto convertToMovieSimpleDto(Movie movie) {
        return modelMapper.map(movie, MovieSimpleDto.class);
    }
}
