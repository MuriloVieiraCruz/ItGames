package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.enums.TypeAssociation;
import com.muriloCruz.ItGames.service.GameService;
import com.muriloCruz.ItGames.service.GenreGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.composite.GenreGameId;
import com.muriloCruz.ItGames.repository.GenreGameRepository;

@Service
public class GenreGameServiceImpl implements GenreGameService {
	
	@Autowired
	private GenreServiceImpl genreService;
	
	@Autowired
	@Qualifier("gameServiceImpl")
	private GameService gameService;
	
	@Autowired
	private GenreGameRepository genreGameRepository;

	@Override
	public GenreGame insert(Long gameId, Long genreId, TypeAssociation typeAssociation) {
		Genre genreFound = genreService.searchBy(genreId);
		Game gameFound = gameService.searchBy(gameId);
		this.validateDuplication(gameId, genreId);
		GenreGameId id = new GenreGameId(genreFound.getId(), gameFound.getId());
		GenreGame genreGame = new GenreGame();
		genreGame.setId(id);
		genreGame.setTypeAssociation(typeAssociation);
		genreGame.setGenre(genreFound);
		genreGame.setGame(gameFound);
        return genreGameRepository.saveAndFlush(genreGame);
	}

	@Override
	public GenreGame update(Long gameId, Long genreId, TypeAssociation typeAssociation) {
		Genre genreFound = genreService.searchBy(genreId);
		Game gameFound = gameService.searchBy(gameId);
		this.validateDuplication(gameId, genreId);
		GenreGame genreGameFound = searchBy(genreFound.getId(), gameFound.getId());
		genreGameFound.setGenre(genreFound);
		genreGameFound.setGame(gameFound);
		genreGameFound.setTypeAssociation(typeAssociation);
        return genreGameRepository.saveAndFlush(genreGameFound);
	}

	@Override
	public GenreGame searchBy(Long genreId, Long gameId) {
		this.validateGenreAndGame(gameId, genreId);
		GenreGame genreGameFound = genreGameRepository.searchBy(genreId, gameId);
		Preconditions.checkNotNull(genreGameFound,
				"No game genre was found linked to the ID '" + genreGameFound.getId() + "' informed");
		return genreGameFound;
	}

	@Override
	public void deleteBy(Long genreId, Long gameId) {
		GenreGame genreGameFound = searchBy(genreId, gameId);
		genreGameRepository.deleteBy(genreId, gameId);
	}

	private void validateGenreAndGame(Long gameId, Long genreId) {
		Genre genreFound = genreService.searchBy(genreId);
		Game gameFound = gameService.searchBy(gameId);
		Preconditions.checkNotNull(genreFound,
				"No genre was found linked to the reported parameters");
		Preconditions.checkNotNull(gameFound,
				"No game was found linked to the reported parameters");
	}

	private void validateDuplication(Long gameId, Long genreId) {
		GenreGame genreGameFound = genreGameRepository.searchBy(genreId, gameId);
		Preconditions.checkArgument(genreGameFound == null,
				"The reported gender is already tied to the game");
	}
}
