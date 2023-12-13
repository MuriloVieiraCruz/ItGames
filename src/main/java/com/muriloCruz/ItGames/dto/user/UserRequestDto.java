package com.muriloCruz.ItGames.dto.user;

import java.time.Instant;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

	@Size(max = 250, min = 3, message = "The login must contain between 3 and 250 characters")
	@Email(message = "The login is in an invalid format")
	@NotBlank(message = "Login is required")
	private String email;

	@NotBlank(message = "Password is required")
	private String password;

	@Size(max = 150, min = 3, message = "Name must contain between 3 and 150 characters")
	@NotBlank(message = "Name cannot be null")
	private String name;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	@NotBlank(message = "The cpf is required")
	@CPF(message = "The cpf is incorrect")
	private String cpf;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	@NotNull(message = "The registration date cannot be null")
	private LocalDate birthDate;
}
