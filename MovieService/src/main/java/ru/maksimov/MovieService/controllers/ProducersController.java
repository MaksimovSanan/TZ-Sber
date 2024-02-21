package ru.maksimov.MovieService.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maksimov.MovieService.dto.moviesDto.MovieDto;
import ru.maksimov.MovieService.dto.moviesDto.NewMovieDto;
import ru.maksimov.MovieService.dto.producersDto.NewProducerDto;
import ru.maksimov.MovieService.dto.producersDto.ProducerDto;
import ru.maksimov.MovieService.models.Movie;
import ru.maksimov.MovieService.models.Producer;
import ru.maksimov.MovieService.services.ProducersService;
import ru.maksimov.MovieService.util.BindingResultHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/producers")
public class ProducersController {
    private final ProducersService producersService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProducersController(ProducersService producersService, ModelMapper modelMapper) {
        this.producersService = producersService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProducerDto>> findAll() {
        List<Producer> producers = producersService.findAll();
        return ResponseEntity.ok(producers.stream().map(this::convertToProducerDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProducerDto> findOne(@PathVariable("id") int id) {
        return ResponseEntity.ok(convertToProducerDto(producersService.findById(id)));
    }



    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid NewProducerDto newProducerDto,
                                         BindingResult bindingResult) {

        String errMsg = BindingResultHandler.handlingBindingResult(bindingResult);
        if(!errMsg.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errMsg);
        }
        Producer producer = modelMapper.map(newProducerDto, Producer.class);

        try {
            producersService.save(producer);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating producer:" + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Producer created successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        Producer producer = producersService.findById(id);
        try{
            producersService.delete(producer);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting producer:" + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Producer was deleted");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProducerDto> update(@PathVariable("id") int id,
                                           @RequestBody NewProducerDto producerToBeUpdated) {

        Producer producer = modelMapper.map(producerToBeUpdated, Producer.class);

        Producer updatedProducer = producersService.update(id, producer);

        return ResponseEntity.ok(convertToProducerDto(updatedProducer));
    }

    private ProducerDto convertToProducerDto(Producer producer) {
        return modelMapper.map(producer, ProducerDto.class);
    }
}
