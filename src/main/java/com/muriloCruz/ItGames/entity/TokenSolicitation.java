package com.muriloCruz.ItGames.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenSolicitation {

    @NotBlank(message = "The login is required")
    private String login;

    @NotBlank(message = "The password is required")
    private String password;
}
