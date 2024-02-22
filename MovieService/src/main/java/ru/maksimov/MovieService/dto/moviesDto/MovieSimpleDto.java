package ru.maksimov.MovieService.dto.moviesDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maksimov.MovieService.dto.producersDto.ProducerSimpleDto;
import ru.maksimov.MovieService.models.Actor;

import java.util.List;

/**
 * Простой DTO для представления информации о фильме без списка актеров и продюсера.
 */
@Data
@NoArgsConstructor
public class MovieSimpleDto {
    private Integer id;
    private String title;
    private String description;
    private Integer yearOfProduction;
}
