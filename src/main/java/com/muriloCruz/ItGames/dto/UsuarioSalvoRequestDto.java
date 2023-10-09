package com.muriloCruz.ItGames.dto;

import java.time.Instant;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioSalvoRequestDto {
	
	@Positive(message = "O ID deve ")
	@NotNull(message = "O ID é obrigatório")
	private Integer id;

	@Size(max = 250, min = 3, message = "O nome deve conter entre 3 e 250 caracteres")
	@NotBlank(message = "O nome é obrigatório")
	private String login;
	
	@NotBlank(message = "A senha é obrigatório")
	private String senha; 
	
	@Size(max = 200, min = 3, message = "O nome deve conter entre 3 e 200 caracteres")
    @Email(message = "O e-mail está com o formato inválido")
    @NotBlank(message = "O e-mail não pode ser nula")
	private String email;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	@NotBlank(message = "O cpf é obrigatório")
	@CPF(message = "O cpf está incorreto")
	private String cpf;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @NotNull(message = "A data de registro não pode ser nulo")
	private Instant dataNasc;
}
