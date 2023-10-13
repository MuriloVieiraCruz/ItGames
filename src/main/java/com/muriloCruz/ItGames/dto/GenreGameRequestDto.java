package com.muriloCruz.ItGames.dto;

import com.muriloCruz.ItGames.entity.enums.TypeAssociation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class GenreGameRequestDto {

	@NotNull(message = "The gender id is required")
	@Positive(message = "The gender id must be greater than 0")
	private Integer genreId;
	
	@NotNull(message = "The type of gender association is required")
	private TypeAssociation typeAssociation;
}
