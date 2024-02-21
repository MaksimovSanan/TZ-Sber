package ru.maksimov.MovieService.dto.producersDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maksimov.MovieService.models.Movie;

import java.util.List;

@Data
@NoArgsConstructor
public class ProducerSimpleDto {
    private Integer id;
    private String name;
}
