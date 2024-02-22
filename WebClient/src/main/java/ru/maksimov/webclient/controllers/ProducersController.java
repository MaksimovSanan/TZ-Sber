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
import ru.maksimov.webclient.dto.actorsDto.ActorDto;
import ru.maksimov.webclient.dto.actorsDto.NewActorDto;
import ru.maksimov.webclient.dto.moviesDto.MovieDto;
import ru.maksimov.webclient.dto.moviesDto.MovieSimpleDto;
import ru.maksimov.webclient.dto.moviesDto.NewMovieDto;
import ru.maksimov.webclient.dto.producersDto.NewProducerDto;
import ru.maksimov.webclient.dto.producersDto.ProducerDto;
import ru.maksimov.webclient.dto.producersDto.ProducerSimpleDto;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/producers")
public class ProducersController {
    private final RestTemplate restTemplate;

    @Autowired
    public ProducersController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getProducersPage(Model model) {
        List<ProducerSimpleDto> producers = Arrays.stream(restTemplate.getForObject(
                "http://MOVIESSERVICE/api/producers",
                ProducerSimpleDto[].class
        )).toList();

        model.addAttribute("producers", producers);

        return "producers/producersPage";
    }

    @GetMapping("/{id}")
    public String getProducerInfo(@PathVariable("id") int id, Model model) {
        ProducerDto producer = restTemplate.getForObject(
                "http://MOVIESSERVICE/api/producers/" + id,
                ProducerDto.class);

        model.addAttribute("producer", producer);

        return "producers/producerInfoPage";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        ProducerDto producer = restTemplate.getForObject(
                "http://MOVIESSERVICE/api/producers/" + id,
                ProducerDto.class);
        model.addAttribute("producer", producer);
        return "producers/editPage";
    }

    @PatchMapping("/{id}")
    public String updateProducer(@PathVariable("id") int id,
                              @ModelAttribute NewProducerDto producerDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NewProducerDto> requestEntity = new HttpEntity<>(producerDto, headers);

        restTemplate.patchForObject("http://MOVIESSERVICE/api/producers/" + id, requestEntity, Void.class);
        return "redirect:/producers/" + id;
    }

    @GetMapping("/new")
    public String newProducerPage(@ModelAttribute("newProducer") NewProducerDto newProducerDto) {
        return "producers/newProducerPage";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute NewProducerDto newProducer) {

        String createMovieUrl = "http://MOVIESSERVICE/api/producers";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NewProducerDto> requestEntity = new HttpEntity<>(newProducer, headers);
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(createMovieUrl, requestEntity, Void.class);

        return "redirect:/";
    }

}
