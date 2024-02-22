package ru.maksimov.MovieService.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maksimov.MovieService.dto.actorsDto.ActorDto;
import ru.maksimov.MovieService.dto.actorsDto.ActorSimpleDto;
import ru.maksimov.MovieService.dto.actorsDto.NewActorDto;
import ru.maksimov.MovieService.dto.producersDto.NewProducerDto;
import ru.maksimov.MovieService.dto.producersDto.ProducerDto;
import ru.maksimov.MovieService.models.Actor;
import ru.maksimov.MovieService.models.Producer;
import ru.maksimov.MovieService.services.ActorsService;
import ru.maksimov.MovieService.util.BindingResultHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для обработки HTTP-запросов, связанных с актерами.
 */
@RestController
@RequestMapping("/api/actors")
public class ActorsController {
    private final ActorsService actorsService;
    private final ModelMapper modelMapper;

    @Autowired
    public ActorsController(ActorsService actorsService, ModelMapper modelMapper) {
        this.actorsService = actorsService;
        this.modelMapper = modelMapper;
    }

    /**
     * Получить список всех актеров.
     *
     * @return Список всех актеров.
     */
    @GetMapping
    public ResponseEntity<List<ActorSimpleDto>> findAll() {
        List<Actor> actors = actorsService.findAll();
        return ResponseEntity.ok(actors.stream().map(this::convertToActorSimpleDto).collect(Collectors.toList()));
    }

    /**
     * Получить информацию об актере по его идентификатору.
     *
     * @param id Идентификатор актера.
     * @return Информация об актере.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActorDto> findOne(@PathVariable("id") int id) {
        return ResponseEntity.ok(convertToActorDto(actorsService.findById(id)));
    }

    /**
     * Создать нового актера.
     *
     * @param newActorDto Данные для нового актера.
     * @param bindingResult Результаты валидации данных.
     * @return Статус операции создания актера.
     */
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

    /**
     * Удалить актера по его идентификатору.
     *
     * @param id Идентификатор актера.
     * @return Статус операции удаления актера.
     */
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

    /**
     * Обновить информацию об актере.
     *
     * @param id Идентификатор актера.
     * @param actorToBeUpdated Новые данные об актере.
     * @return Обновленная информация об актере.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ActorDto> update(@PathVariable("id") int id,
                                              @RequestBody NewActorDto actorToBeUpdated) {

        Actor actor = modelMapper.map(actorToBeUpdated, Actor.class);

        Actor updatedActor = actorsService.update(id, actor);

        return ResponseEntity.ok(convertToActorDto(updatedActor));
    }

    /**
     * Преобразовать объект актера в объект DTO с полной информацией.
     *
     * @param actor Объект актера.
     * @return Объект DTO с полной информацией об актере.
     */
    private ActorDto convertToActorDto(Actor actor) {
        return modelMapper.map(actor, ActorDto.class);
    }
    /**
     * Преобразовать объект актера в объект DTO с минимальной информацией.
     *
     * @param actor Объект актера.
     * @return Объект DTO с минимальной информацией об актере.
     */
    private ActorSimpleDto convertToActorSimpleDto(Actor actor) {
        return modelMapper.map(actor, ActorSimpleDto.class);
    }
}
