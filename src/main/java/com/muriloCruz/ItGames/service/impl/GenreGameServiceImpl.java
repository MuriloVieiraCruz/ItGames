package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.NewGenreGameRequestDto;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.composite.GenreGameId;
import com.muriloCruz.ItGames.repository.GenreGameRepository;
import com.muriloCruz.ItGames.repository.GameRepository;
import com.muriloCruz.ItGames.service.GenreGameService;

@Service
public class GenreGameServiceImpl implements GenreGameService {
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private GenreGameRepository genreGameRepository;

	@Override
	public GenreGame insert(NewGenreGameRequestDto newGenreGameRequestDto) {
		Genre genreFound = getGenreBy(newGenreGameRequestDto
				.getGenreGameRequestDto().getGenreId());
		Game gameFound = getGameBy(newGenreGameRequestDto.getIdDoJogo());
		GenreGameId id = new GenreGameId(genreFound.getId(), gameFound.getId());
		GenreGame genreGame = new GenreGame();
		genreGame.setId(id);
		genreGame.setTypeAssociation(newGenreGameRequestDto
				.getGenreGameRequestDto().getTypeAssociation());
		genreGame.setGenre(genreFound);
		genreGame.setGame(gameFound);
		GenreGame genreGameSlavo = genreGameRepository.saveAndFlush(genreGame);
		return genreGameSlavo;
	}

	@Override
	public GenreGame update(GenreGame genreGame) {
		Preconditions.checkNotNull(genreGame.getId(),
				"The game genre id is required");
		GenreGame genreGameEncontrado = genreGameRepository.findById(genreGame.getId()).get();
		Preconditions.checkNotNull(genreGameEncontrado,
				"No game genre was found linked to the parameters informed");
		Genre genreFound = getGenreBy(genreGame.getGenre().getId());
		Game gameFound = getGameBy(genreGame.getGame().getId());
		genreGameEncontrado.setGenre(genreFound);
		genreGameEncontrado.setGame(gameFound);
		genreGameEncontrado.setTypeAssociation(genreGame.getTypeAssociation());
		GenreGame genreGameUpdated = this.genreGameRepository.saveAndFlush(genreGameEncontrado);
		return genreGameUpdated;
	}

	@Override
	public GenreGame searchBy(Genre genre, Game game) {
		Genre genreFound = getGenreBy(genre.getId());
		Game gameFound = getGameBy(game.getId());
		GenreGame genreGameFound = this.genreGameRepository.searchBy(genreFound, gameFound);
		Preconditions.checkNotNull(genreGameFound,
				"No game genres were found linked to the parameters informed");
		return genreGameFound;
	}
	
	private Genre getGenreBy(Integer genreId) {
		Genre genreFound = genreRepository.findById(genreId).get();
		Preconditions.checkNotNull(genreFound,
				"No gender was found linked to the parameters informed");
		Preconditions.checkArgument(genreFound.isActive(),
				"The gender entered is inactive");
		return genreFound;
	}
	
	private Game getGameBy(Integer gameId) {
		Game gameFound = gameRepository.searchBy(gameId);
		Preconditions.checkNotNull(gameFound,
				"No game was found linked to the parameters informed");
		Preconditions.checkArgument(gameFound.isActive(),
				"The game entered is inactive");
		return gameFound;
	}

}
