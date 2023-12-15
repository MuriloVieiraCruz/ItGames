package com.muriloCruz.ItGames.dto.enterprise;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EnterpriseSaved {

    @NotNull(message = "The ID is required")
    private Long id;

    @NotBlank(message = "The name is required")
    private String name;

    @Size(max = 14, min = 14, message = "The cnpj must contain 14 characters")
    @NotBlank(message = "The cnpj is required")
    private String cnpj;
}
