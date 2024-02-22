package ru.maksimov.MovieService.dto.actorsDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для создания нового актера.
 */
@Data
@NoArgsConstructor
public class NewActorDto {
    @NotEmpty(message = "name should not be empty")
    private String name;
}
