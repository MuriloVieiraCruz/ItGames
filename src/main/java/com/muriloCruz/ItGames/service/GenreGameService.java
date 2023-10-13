package com.muriloCruz.ItGames.service;

import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.dto.NewGenreGameRequestDto;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.Game;

import jakarta.validation.constraints.NotNull;

@Validated
public interface GenreGameService {

	public GenreGame insert(
			@NotNull(message = "Both the ID and the association type are required")
			NewGenreGameRequestDto newGenreGameRequestDto);
	
	public GenreGame update(
			@NotNull(message = "The genre of the game is required")
			GenreGame genreGame);
	
	public GenreGame searchBy(
			@NotNull(message = "The genre is required")
            Genre genre,
			@NotNull(message = "The game is required")
			Game game);
}
