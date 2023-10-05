package com.muriloCruz.ItGames.dto;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.muriloCruz.ItGames.entity.Empresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JogoRequestDto {

	@Size(max = 100, min = 3, message = "O nome deve conter entre 3 e 100 caracteres")
	@NotBlank(message = "O nome é obrigatório")
	private String nome;
	
	@NotBlank(message = "O nome não pode ser nulo")
	private String descricao;
	
	@NotNull(message = "A data de lançamento é obrigatório")
	private Timestamp dataLanc;
	
	@NotBlank(message = "A URL da imagem é obrigatória")
	private String imagemUrl;
	
	@NotNull(message = "A empresa do jogo é obrigatório")
	private Empresa empresa;
	
	@Size(min = 1, message = "O jogo deve possuir ao menos um gênero")
	private List<GeneroDoJogoRequestDto> generos;
	
	public JogoRequestDto() {
		this.generos = new ArrayList<>();
	}
}
