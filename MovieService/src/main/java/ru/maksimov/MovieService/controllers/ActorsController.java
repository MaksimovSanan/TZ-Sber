package ru.maksimov.MovieService.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maksimov.MovieService.dto.actorsDto.ActorDto;
import ru.maksimov.MovieService.dto.actorsDto.NewActorDto;
import ru.maksimov.MovieService.dto.producersDto.NewProducerDto;
import ru.maksimov.MovieService.dto.producersDto.ProducerDto;
import ru.maksimov.MovieService.models.Actor;
import ru.maksimov.MovieService.models.Producer;
import ru.maksimov.MovieService.services.ActorsService;
import ru.maksimov.MovieService.util.BindingResultHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/actors")
public class ActorsController {
    private final ActorsService actorsService;
    private final ModelMapper modelMapper;

    @Autowired
    public ActorsController(ActorsService actorsService, ModelMapper modelMapper) {
        this.actorsService = actorsService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public ResponseEntity<List<ActorDto>> findAll() {
        List<Actor> actors = actorsService.findAll();
        return ResponseEntity.ok(actors.stream().map(this::convertToActorDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDto> findOne(@PathVariable("id") int id) {
        return ResponseEntity.ok(convertToActorDto(actorsService.findById(id)));
    }



    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid NewActorDto newActorDto,
                                         BindingResult bindingResult) {

        String errMsg = BindingResultHandler.handlingBindingResult(bindingResult);
        if(!errMsg.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errMsg);
        }
        Actor actor = modelMapper.map(newActorDto, Actor.class);

        try {
            actorsService.save(actor);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating actor:" + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Actor created successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        Actor actor = actorsService.findById(id);
        try{
            actorsService.delete(actor);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting actor:" + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Actor was deleted");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ActorDto> update(@PathVariable("id") int id,
                                              @RequestBody NewActorDto actorToBeUpdated) {

        Actor actor = modelMapper.map(actorToBeUpdated, Actor.class);

        Actor updatedActor = actorsService.update(id, actor);

        return ResponseEntity.ok(convertToActorDto(updatedActor));
    }

    private ActorDto convertToActorDto(Actor actor) {
        return modelMapper.map(actor, ActorDto.class);
    }

}
