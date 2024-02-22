package ru.maksimov.MovieService.dto.producersDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maksimov.MovieService.models.Movie;

import java.util.List;

/**
 * Простой DTO для представления информации о продюсере без списка фильмов.
 */
@Data
@NoArgsConstructor
public class ProducerSimpleDto {
    private Integer id;
    private String name;
}
