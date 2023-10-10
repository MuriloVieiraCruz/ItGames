package com.muriloCruz.ItGames.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostagemRequestDto {

	private String imagemUrl;
	
	@NotNull(message = "O servico da postagem é obrigatório")
	private ServicoRequestDto servicoRequestDto;
}
