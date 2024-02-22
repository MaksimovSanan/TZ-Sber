package ru.maksimov.MovieService.dto.actorsDto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Простой DTO для представления информации об актере без списка фильмов.
 */
@Data
@NoArgsConstructor
public class ActorSimpleDto {
    private Integer id;
    private String name;
}
