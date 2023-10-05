package com.muriloCruz.ItGames.dto;

import com.muriloCruz.ItGames.entity.enums.TipoAssociacao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class GeneroDoJogoRequestDto {

	@NotNull(message = "O id do gênero é obrigatório")
	@Positive(message = "O id do gênero deve ser maior que 0")
	private Integer idDoGenero;
	
	@NotNull(message = "O tipo de associação do gênero é obrigatório")
	private TipoAssociacao tipoAssociacao;
}
