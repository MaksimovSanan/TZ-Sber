package ru.maksimov.webclient.dto.moviesDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieSimpleDto {
    private Integer id;
    private String title;
    private String description;
    private Integer yearOfProduction;
}
