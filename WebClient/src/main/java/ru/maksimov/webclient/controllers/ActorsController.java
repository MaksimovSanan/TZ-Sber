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
import ru.maksimov.webclient.services.ActorsService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/actors")
public class ActorsController {
    private final ActorsService actorsService;

    @Autowired
    public ActorsController(ActorsService actorsService) {
        this.actorsService = actorsService;
    }

//    @GetMapping
//    public String getActorsPage(Model model) {
//        List<ActorSimpleDto> actors = actorsService.getActors();
//
//        model.addAttribute("actors", actors);
//
//        return "actors/actorsPage";
//    }

    @GetMapping("/{id}")
    public String getActorInfo(@PathVariable("id") int id, Model model) {
        ActorDto actor = actorsService.getActorInfo(id);

        model.addAttribute("actor", actor);

        return "actors/actorInfoPage";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        ActorDto actor = actorsService.getActorInfo(id);
        model.addAttribute("actor", actor);
        return "actors/editPage";
    }

    @PatchMapping("/{id}")
    public String updateActor(@PathVariable("id") int id,
                             @ModelAttribute NewActorDto actorDto) {
        actorsService.updateActor(id, actorDto);
        return "redirect:/actors/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteProducer(@PathVariable("id") int id) {
        actorsService.deleteActor(id);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String newActorPage(@ModelAttribute("newActor") NewActorDto newActorDto) {
        return "actors/newActorPage";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute NewActorDto newActor) {

        actorsService.createActor(newActor);

        return "redirect:/";
    }
}
