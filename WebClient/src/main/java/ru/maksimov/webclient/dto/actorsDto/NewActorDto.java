package ru.maksimov.webclient.dto.actorsDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewActorDto {
    @NotEmpty(message = "name should not be empty")
    private String name;
}
