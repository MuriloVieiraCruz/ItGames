package com.muriloCruz.ItGames.dto.enterprise;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnterpriseRequest {

    @NotBlank(message = "The name is required")
    private String name;
}
