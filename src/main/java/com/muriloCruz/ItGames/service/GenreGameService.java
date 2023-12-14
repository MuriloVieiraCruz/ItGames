package com.muriloCruz.ItGames.service;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.enums.TypeAssociation;
import org.springframework.beans.factory.annotation.Autowired;
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

	public GenreGame insert(Long gameId, Long genreId, TypeAssociation typeAssociation) {
		Genre genreFound = genreService.searchBy(genreId);
		Game gameFound = gameService.searchBy(gameId);
		GenreGameId id = new GenreGameId(genreFound.getId(), gameFound.getId());
		GenreGame genreGame = new GenreGame();
		genreGame.setId(id);
		genreGame.setTypeAssociation(typeAssociation);
		genreGame.setGenre(genreFound);
		genreGame.setGame(gameFound);
        return genreGameRepository.saveAndFlush(genreGame);
	}
	
	public GenreGame update(Long gameId, Long genreId, TypeAssociation typeAssociation) {
		Genre genreFound = genreService.searchBy(genreId);
		Game gameFound = gameService.searchBy(gameId);
		GenreGame genreGameFound = searchBy(genreFound.getId(), gameFound.getId());
		genreGameFound.setGenre(genreFound);
		genreGameFound.setGame(gameFound);
		genreGameFound.setTypeAssociation(typeAssociation);
        return genreGameRepository.saveAndFlush(genreGameFound);
	}
	
	public GenreGame searchBy(Long genreId, Long gameId) {
		Genre genreFound = genreService.searchBy(genreId);
		Game gameFound = gameService.searchBy(gameId);
		GenreGame genreGameFound = genreGameRepository.searchBy(genreFound.getId(), gameFound.getId());
		Preconditions.checkNotNull(genreGameFound,
				"No game genre was found linked to the ID '" + genreGameFound.getId() + "' informed");
		return genreGameFound;
	}
}
