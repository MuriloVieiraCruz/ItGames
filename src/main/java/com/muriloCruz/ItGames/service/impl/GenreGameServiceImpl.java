package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.enums.TypeAssociation;
import com.muriloCruz.ItGames.repository.GenreRepository;
import com.muriloCruz.ItGames.service.GameService;
import com.muriloCruz.ItGames.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.composite.GenreGameId;
import com.muriloCruz.ItGames.repository.GenreGameRepository;
import com.muriloCruz.ItGames.repository.GameRepository;
import com.muriloCruz.ItGames.service.GenreGameService;

import java.util.Optional;

@Service
public class GenreGameServiceImpl implements GenreGameService {
	
	@Autowired
	private GenreService genreService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GenreGameRepository genreGameRepository;

	@Override
	public GenreGame insert(Integer gameId, Integer genreId, TypeAssociation typeAssociation) {
		Genre genreFound = genreService.searchBy(genreId);
		Game gameFound = gameService.searchBy(gameId);
		GenreGameId id = new GenreGameId(genreFound.getId(), gameFound.getId());
		GenreGame genreGame = new GenreGame();
		genreGame.setId(id);
		genreGame.setTypeAssociation(typeAssociation);
		genreGame.setGenre(genreFound);
		genreGame.setGame(gameFound);
		GenreGame genreGameSave = genreGameRepository.saveAndFlush(genreGame);
		return genreGameSave;
	}

	@Override
	public GenreGame update(Integer gameId, Integer genreId, TypeAssociation typeAssociation) {
		Genre genreFound = genreService.searchBy(genreId);
		Game gameFound = gameService.searchBy(gameId);
		GenreGame genreGameFound = searchBy(genreFound, gameFound);
		genreGameFound.setGenre(genreFound);
		genreGameFound.setGame(gameFound);
		genreGameFound.setTypeAssociation(typeAssociation);
		GenreGame genreGameUpdated = this.genreGameRepository.saveAndFlush(genreGameFound);
		return genreGameUpdated;
	}

	@Override
	public GenreGame searchBy(Genre genre, Game game) {
		Genre genreFound = genreService.searchBy(genre.getId());
		Game gameFound = gameService.searchBy(game.getId());
		GenreGame genreGameFound = genreGameRepository.searchBy(genreFound, gameFound);
		Preconditions.checkNotNull(genreGameFound,
				"No game genre was found linked to the ID '" + genre.getId() + "' informed");
		return genreGameFound;
	}
}
