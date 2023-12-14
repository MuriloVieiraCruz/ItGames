package com.muriloCruz.ItGames.dto.genreGame;

import com.muriloCruz.ItGames.entity.enums.TypeAssociation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenreGameRequestDto {

	@NotNull(message = "The gender ID is required")
	@Positive(message = "The gender ID must be greater than 0")
	private Long genreId;
	
	@NotNull(message = "The type of gender association is required")
	private TypeAssociation typeAssociation;
}
