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
import ru.maksimov.webclient.dto.actorsDto.ActorSimpleDto;
import ru.maksimov.webclient.dto.actorsDto.NewActorDto;
import ru.maksimov.webclient.dto.moviesDto.NewMovieDto;
import ru.maksimov.webclient.dto.producersDto.NewProducerDto;
import ru.maksimov.webclient.dto.producersDto.ProducerDto;
import ru.maksimov.webclient.dto.producersDto.ProducerSimpleDto;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/actors")
public class ActorsController {
    private final RestTemplate restTemplate;

    @Autowired
    public ActorsController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getActorsPage(Model model) {
        List<ActorSimpleDto> actors = Arrays.stream(restTemplate.getForObject(
                "http://MOVIESSERVICE/api/actors",
                ActorSimpleDto[].class
        )).toList();

        model.addAttribute("actors", actors);

        return "actors/actorsPage";
    }

    @GetMapping("/{id}")
    public String getActorInfo(@PathVariable("id") int id, Model model) {
        ActorDto actor = restTemplate.getForObject(
                "http://MOVIESSERVICE/api/actors/" + id,
                ActorDto.class);

        model.addAttribute("actor", actor);

        return "actors/actorInfoPage";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        ActorDto actor = restTemplate.getForObject(
                "http://MOVIESSERVICE/api/actors/" + id,
                ActorDto.class);
        model.addAttribute("actor", actor);
        return "actors/editPage";
    }

    @PatchMapping("/{id}")
    public String updateActor(@PathVariable("id") int id,
                             @ModelAttribute NewActorDto actorDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NewActorDto> requestEntity = new HttpEntity<>(actorDto, headers);

        restTemplate.patchForObject("http://MOVIESSERVICE/api/actors/" + id, requestEntity, Void.class);
        return "redirect:/actors/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteProducer(@PathVariable("id") int id) {
        restTemplate.delete("http://MOVIESSERVICE/api/actors/" + id);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String newActorPage(@ModelAttribute("newActor") NewActorDto newActorDto) {
        return "actors/newActorPage";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute NewActorDto newActor) {

        String createActorUrl = "http://MOVIESSERVICE/api/actors";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NewActorDto> requestEntity = new HttpEntity<>(newActor, headers);
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(createActorUrl, requestEntity, Void.class);

        return "redirect:/";
    }
}
