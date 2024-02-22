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

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MoviesController {
    private final RestTemplate restTemplate;

    @Autowired
    public MoviesController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getMoviesPage(Model model) {
        List<MovieSimpleDto> movies = Arrays.stream(restTemplate.getForObject(
                "http://MOVIESSERVICE/movies",
                MovieSimpleDto[].class
        )).toList();

        model.addAttribute("movies", movies);

        return "movies/moviesPage";
    }

    @GetMapping("/{id}")
    public String getMovieInfo(@PathVariable("id") int id, Model model) {
        MovieDto movie = restTemplate.getForObject(
                "http://MOVIESSERVICE/movies/" + id,
                MovieDto.class);

        model.addAttribute("movie", movie);

        return "movies/movieInfoPage";
    }

    @GetMapping("/new")
    public String newMoviePage(@ModelAttribute("newMovie") NewMovieDto newMovieDto, Model model) {

        List<ProducerSimpleDto> producers = Arrays.stream(restTemplate.getForObject(
                "http://MOVIESSERVICE/producers",
                ProducerSimpleDto[].class
        )).toList();
        model.addAttribute("producers", producers);

        List<ActorSimpleDto> actors = Arrays.stream(restTemplate.getForObject(
                "http://MOVIESSERVICE/actors",
                ActorSimpleDto[].class
        )).toList();
        model.addAttribute("actors", actors);

        return "movies/newMoviePage";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute NewMovieDto newMovie) {

        String createMovieUrl = "http://MOVIESSERVICE/movies";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NewMovieDto> requestEntity = new HttpEntity<>(newMovie, headers);
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(createMovieUrl, requestEntity, Void.class);

        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        MovieDto movie = restTemplate.getForObject(
                "http://MOVIESSERVICE/movies/" + id,
                MovieDto.class);
        model.addAttribute("movie", movie);

        List<ProducerSimpleDto> producers = Arrays.stream(restTemplate.getForObject(
                "http://MOVIESSERVICE/producers",
                ProducerSimpleDto[].class
        )).toList();
        model.addAttribute("producers", producers);

        List<ActorSimpleDto> actors = Arrays.stream(restTemplate.getForObject(
                "http://MOVIESSERVICE/actors",
                ActorSimpleDto[].class
        )).toList();
        model.addAttribute("actors", actors);

        return "movies/editPage";
    }

    @PatchMapping("/{id}")
    public String updateMovie(@PathVariable("id") int id,
                                 @ModelAttribute NewMovieDto movieDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NewMovieDto> requestEntity = new HttpEntity<>(movieDto, headers);

        restTemplate.patchForObject("http://MOVIESSERVICE/movies/" + id, requestEntity, Void.class);
        return "redirect:/movies/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteProducer(@PathVariable("id") int id) {
        restTemplate.delete("http://MOVIESSERVICE/movies/" + id);
        return "redirect:/";
    }
}
