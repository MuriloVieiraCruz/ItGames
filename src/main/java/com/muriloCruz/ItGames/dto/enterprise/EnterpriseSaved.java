package com.muriloCruz.ItGames.dto.enterprise;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnterpriseSaved {

    @NotNull(message = "The id is required")
    private Long id;

    @NotBlank(message = "The name is required")
    private String name;
}
