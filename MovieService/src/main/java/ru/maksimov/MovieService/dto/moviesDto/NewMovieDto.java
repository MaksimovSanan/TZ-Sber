package ru.maksimov.MovieService.dto.moviesDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class NewMovieDto {
    @NotEmpty(message = "title should not be empty")
    private String title;
    private String description;
    private Integer yearOfProduction;

    private Integer producerId;

    private List<Integer> actorsId;
}
