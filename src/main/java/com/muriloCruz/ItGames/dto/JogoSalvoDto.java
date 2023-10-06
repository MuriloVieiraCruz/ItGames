package com.muriloCruz.ItGames.dto;

import java.security.Timestamp;

import com.muriloCruz.ItGames.entity.Empresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JogoSalvoDto {
	
	@Positive(message = "O ID deve ")
	@NotNull(message = "O ID é obrigatório")
	private Integer id;

	@Size(max = 100, min = 3, message = "O nome deve conter entre 3 e 100 caracteres")
	@NotBlank(message = "O nome é obrigatório")
	private String nome;
	
	@NotBlank(message = "O nome não pode ser nulo")
	private String descricao;
	
	@NotNull(message = "A data de lançamento é obrigatório")
	private Timestamp dataLanc;
	
	@NotBlank(message = "A URL da imagem é obrigatória")
	private String imagemUrl;
	
	@NotNull(message = "A empresa é obrigatório")
	private Empresa empresa;
}
