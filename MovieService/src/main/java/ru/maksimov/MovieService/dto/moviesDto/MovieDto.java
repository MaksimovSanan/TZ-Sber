package ru.maksimov.MovieService.dto.moviesDto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maksimov.MovieService.dto.actorsDto.ActorSimpleDto;
import ru.maksimov.MovieService.dto.producersDto.ProducerSimpleDto;
import ru.maksimov.MovieService.models.Actor;
import ru.maksimov.MovieService.models.Producer;

import java.util.List;

@Data
@NoArgsConstructor
public class MovieDto {
    private Integer id;
    private String title;
    private String description;
    private Integer yearOfProduction;

    private ProducerSimpleDto producer;

    private List<ActorSimpleDto> actors;
}
