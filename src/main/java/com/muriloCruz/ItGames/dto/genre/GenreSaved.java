package com.muriloCruz.ItGames.dto.genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GenreSaved {

    @NotNull(message = "The ID is required")
    private Long id;

    @NotBlank(message = "The name is required")
    private String name;
}
