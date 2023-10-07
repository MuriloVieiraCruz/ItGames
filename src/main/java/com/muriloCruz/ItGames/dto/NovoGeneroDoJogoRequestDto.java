package com.muriloCruz.ItGames.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class NovoGeneroDoJogoRequestDto {

	@NotNull(message = "O genero do jogo é obrigatório")
	private GeneroDoJogoRequestDto generoDoJogoRequestDto;
	
	@NotNull(message = "O id do jogo é obrigatório")
	@Positive(message = "O id do jogo deve ser maior que 0")
	private Integer idDoJogo;
}
