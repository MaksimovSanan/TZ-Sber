package ru.maksimov.MovieService.dto.producersDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для создания нового продюсера.
 */
@Data
@NoArgsConstructor
public class NewProducerDto {
    @NotEmpty(message = "name should not be empty")
    private String name;
}
