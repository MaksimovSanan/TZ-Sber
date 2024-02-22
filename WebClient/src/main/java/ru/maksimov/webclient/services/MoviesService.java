package ru.maksimov.webclient.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.maksimov.webclient.dto.moviesDto.MovieDto;
import ru.maksimov.webclient.dto.moviesDto.MovieSimpleDto;
import ru.maksimov.webclient.dto.moviesDto.NewMovieDto;
import ru.maksimov.webclient.dto.producersDto.NewProducerDto;
import ru.maksimov.webclient.dto.producersDto.ProducerDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MoviesService {
    private final RestTemplate restTemplate;
    private final String url = "http://MOVIESSERVICE/api/movies";

    @Autowired
    public MoviesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MovieSimpleDto> getMovies(){
        List<MovieSimpleDto> movieSimpleDtoList = new ArrayList<>();
        try {
            movieSimpleDtoList =  Arrays.stream(restTemplate.getForObject(
                    url,
                    MovieSimpleDto[].class
            )).toList();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return movieSimpleDtoList;
    }

    public MovieDto getMovieInfo(int id) {
        MovieDto movieDto = null;
        try {
            movieDto = restTemplate.getForObject(
                    url + "/" + id,
                    MovieDto.class);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return movieDto;
    }

    public void createMovie(NewMovieDto newMovie) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NewMovieDto> requestEntity = new HttpEntity<>(newMovie, headers);
        try {
            ResponseEntity<Void> responseEntity = restTemplate.postForEntity(url, requestEntity, Void.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateMovie(int id, NewMovieDto movieDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NewMovieDto> requestEntity = new HttpEntity<>(movieDto, headers);

        try {
            restTemplate.patchForObject(url + "/" + id, requestEntity, Void.class);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteMovie(int id) {
        try {
            restTemplate.delete(url + "/" + id);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
