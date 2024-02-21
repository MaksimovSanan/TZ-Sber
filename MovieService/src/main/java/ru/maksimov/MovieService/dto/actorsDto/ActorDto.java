package ru.maksimov.MovieService.dto.actorsDto;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maksimov.MovieService.dto.moviesDto.MovieSimpleDto;
import ru.maksimov.MovieService.models.Movie;

import java.util.List;

@Data
@NoArgsConstructor
public class ActorDto {
    private Integer id;
    private String name;

    private List<MovieSimpleDto> movies;
}
