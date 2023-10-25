package com.muriloCruz.ItGames.service;

import com.muriloCruz.ItGames.entity.enums.TypeAssociation;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.Game;

import jakarta.validation.constraints.NotNull;

@Validated
public interface GenreGameService {

	public GenreGame insert(
			@Positive(message = "The game id must be positive")
			@NotNull(message = "The game id is required")
			Integer gameId,
			@Positive(message = "The genre id must be positive")
			@NotNull(message = "The genre id is required")
			Integer genreId,
			@NotNull(message = "Both the ID and the association type are required")
			TypeAssociation typeAssociation);
	
	public GenreGame update(
			@Positive(message = "The game id must be positive")
			@NotNull(message = "The game id is required")
			Integer gameId,
			@Positive(message = "The genre id must be positive")
			@NotNull(message = "The genre id is required")
			Integer genreId,
			@NotNull(message = "Both the ID and the association type are required")
			TypeAssociation typeAssociation);
	
	public GenreGame searchBy(
			@NotNull(message = "The genre is required")
            Genre genre,
			@NotNull(message = "The game is required")
			Game game);
}
