package ru.maksimov.webclient.dto.actorsDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maksimov.webclient.dto.moviesDto.MovieSimpleDto;

import java.util.List;

@Data
@NoArgsConstructor
public class ActorDto {
    private Integer id;
    private String name;

    private List<MovieSimpleDto> movies;
}
