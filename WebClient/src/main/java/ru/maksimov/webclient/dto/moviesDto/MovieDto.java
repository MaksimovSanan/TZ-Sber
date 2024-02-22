package ru.maksimov.webclient.dto.moviesDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maksimov.webclient.dto.actorsDto.ActorSimpleDto;
import ru.maksimov.webclient.dto.producersDto.ProducerSimpleDto;


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
