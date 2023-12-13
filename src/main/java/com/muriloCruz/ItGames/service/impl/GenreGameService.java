package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.enums.TypeAssociation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.composite.GenreGameId;
import com.muriloCruz.ItGames.repository.GenreGameRepository;

@Service
public class GenreGameService {
	
	@Autowired
	private GenreService genreService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GenreGameRepository genreGameRepository;

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
	
	public GenreGame searchBy(Genre genre, Game game) {
		Genre genreFound = genreService.searchBy(genre.getId());
		Game gameFound = gameService.searchBy(game.getId());
		GenreGame genreGameFound = genreGameRepository.searchBy(genreFound, gameFound);
		Preconditions.checkNotNull(genreGameFound,
				"No game genre was found linked to the ID '" + genre.getId() + "' informed");
		return genreGameFound;
	}
}
