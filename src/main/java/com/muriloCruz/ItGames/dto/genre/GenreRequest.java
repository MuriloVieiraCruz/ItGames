package com.muriloCruz.ItGames.dto.genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GenreRequest {

    @NotBlank(message = "The name is required")
    private String name;
}
