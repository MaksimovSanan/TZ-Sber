package ru.maksimov.MovieService.dto.producersDto;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maksimov.MovieService.dto.moviesDto.MovieSimpleDto;
import ru.maksimov.MovieService.models.Movie;

import java.util.List;

/**
 * DTO для представления информации о продюсере с дополнительным списком фильмов.
 */
@Data
@NoArgsConstructor
public class ProducerDto {
    private Integer id;
    private String name;

    private List<MovieSimpleDto> movies;
}
