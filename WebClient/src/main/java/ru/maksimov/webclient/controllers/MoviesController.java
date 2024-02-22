package ru.maksimov.webclient.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.maksimov.webclient.dto.actorsDto.ActorSimpleDto;
import ru.maksimov.webclient.dto.moviesDto.MovieDto;
import ru.maksimov.webclient.dto.moviesDto.MovieSimpleDto;
import ru.maksimov.webclient.dto.moviesDto.NewMovieDto;
import ru.maksimov.webclient.dto.producersDto.NewProducerDto;
import ru.maksimov.webclient.dto.producersDto.ProducerDto;
import ru.maksimov.webclient.dto.producersDto.ProducerSimpleDto;
import ru.maksimov.webclient.services.ActorsService;
import ru.maksimov.webclient.services.MoviesService;
import ru.maksimov.webclient.services.ProducersService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MoviesController {
    private final MoviesService moviesService;
    private final ActorsService actorsService;
    private final ProducersService producersService;

    @Autowired
    public MoviesController(MoviesService moviesService, ActorsService actorsService, ProducersService producersService) {
        this.moviesService = moviesService;
        this.actorsService = actorsService;
        this.producersService = producersService;
    }

    @GetMapping
    public String getMoviesPage(Model model) {
        List<MovieSimpleDto> movies = moviesService.getMovies();

        model.addAttribute("movies", movies);

        return "movies/moviesPage";
    }

    @GetMapping("/{id}")
    public String getMovieInfo(@PathVariable("id") int id, Model model) {
        MovieDto movie = moviesService.getMovieInfo(id);

        model.addAttribute("movie", movie);

        return "movies/movieInfoPage";
    }

    @GetMapping("/new")
    public String newMoviePage(@ModelAttribute("newMovie") NewMovieDto newMovieDto, Model model) {

        List<ProducerSimpleDto> producers = producersService.getProducers();
        model.addAttribute("producers", producers);

        List<ActorSimpleDto> actors = actorsService.getActors();
        model.addAttribute("actors", actors);

        return "movies/newMoviePage";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute NewMovieDto newMovie) {

        moviesService.createMovie(newMovie);

        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        MovieDto movie = moviesService.getMovieInfo(id);
        model.addAttribute("movie", movie);

        List<ProducerSimpleDto> producers = producersService.getProducers();
        model.addAttribute("producers", producers);

        List<ActorSimpleDto> actors = actorsService.getActors();
        model.addAttribute("actors", actors);

        return "movies/editPage";
    }

    @PatchMapping("/{id}")
    public String updateMovie(@PathVariable("id") int id,
                                 @ModelAttribute NewMovieDto movieDto) {
        moviesService.updateMovie(id, movieDto);
        return "redirect:/movies/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteProducer(@PathVariable("id") int id) {
        moviesService.deleteMovie(id);
        return "redirect:/";
    }
}
