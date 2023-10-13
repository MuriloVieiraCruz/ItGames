package com.muriloCruz.ItGames.dto;

import java.time.Instant;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {

	@Size(max = 250, min = 3, message = "The login must contain between 3 and 250 characters")
	@NotBlank(message = "Login is required")
	private String login;

	@NotBlank(message = "Password is required")
	private String password;

	@Size(max = 200, min = 3, message = "Name must contain between 3 and 200 characters")
	@Email(message = "The e-mail is in an invalid format")
	@NotBlank(message = "Email cannot be null")
	private String email;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	@NotBlank(message = "The cpf is required")
	@CPF(message = "The cpf is incorrect")
	private String cpf;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	@NotNull(message = "The registration date cannot be null")
	private Instant birthDate;
}
