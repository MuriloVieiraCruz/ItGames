package com.muriloCruz.ItGames.service.impl;

import java.util.List;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.GenreGameRequestDto;
import com.muriloCruz.ItGames.dto.GameRequestDto;
import com.muriloCruz.ItGames.dto.GameSalvedDto;
import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.composite.GenreGameId;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.EnterpriseRepository;
import com.muriloCruz.ItGames.repository.GenreGameRepository;
import com.muriloCruz.ItGames.repository.GameRepository;
import com.muriloCruz.ItGames.service.GameService;

@Service
public class GameServiceImpl implements GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private EnterpriseRepository enterpriseRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private GenreGameRepository genreGameRepository;

	@Override
	public Game insert(GameRequestDto gameRequestDto) {
		Enterprise enterpriseValida = getEnterpriseBy(gameRequestDto);
		Game game = new Game();
		game.setName(gameRequestDto.getName());
		game.setDescription(gameRequestDto.getDescription());
		game.setReleaseDate(gameRequestDto.getReleaseDate());
		game.setImageUrl(gameRequestDto.getImageUrl());
		game.setEnterprise(enterpriseValida);
		validateDuplication(gameRequestDto.getGenres());
		Game gameSaved = gameRepository.save(game);
		for (GenreGameRequestDto genreDto: gameRequestDto.getGenres()) {
			Genre genre = getGenreBy(genreDto.getGenreId());
			GenreGameId id = new GenreGameId(genre.getId(), gameSaved.getId());
			GenreGame genreGame = new GenreGame();
			genreGame.setId(id);
			genreGame.setTypeAssociation(genreDto.getTypeAssociation());
			genreGame.setGenre(genre);
			genreGame.setGame(gameSaved);
			gameSaved.getGenres().add(genreGame);
		}
		this.gameRepository.saveAndFlush(gameSaved);
		return this.gameRepository.searchBy(gameSaved.getId());
	}

	@Override
	public Game update(GameSalvedDto gameSalvedDto) {
		Enterprise enterpriseFound = enterpriseRepository
				.findById(gameSalvedDto.getEnterprise().getId()).get();
		Game gameFound = gameRepository.findById(gameSalvedDto.getId()).get();
		Preconditions.checkNotNull(gameFound,
				"No games were found linked to the parameters entered");
		Preconditions.checkArgument(gameFound.isActive(),
				"The reported game is inactive");
		gameFound.setName(gameSalvedDto.getName());
		gameFound.setDescription(gameSalvedDto.getDescription());
		gameFound.setReleaseDate(gameSalvedDto.getReleaseDate());
		gameFound.setStatus(gameSalvedDto.getStatus());
		gameFound.setImageUrl(gameSalvedDto.getImageUrl());
		gameFound.setEnterprise(enterpriseFound);
		Game gameSaved = gameRepository.saveAndFlush(gameFound);
		return gameSaved;
	}

	@Override
	public Page<Game> listBy(String name, Genre genre, Pageable pagination) {
		Preconditions.checkArgument(name != null || genre != null,
				"You must provide at least one name or gender for the list");
		return this.gameRepository.listBy(name, genre, pagination);
	}

	@Override
	public Game searchBy(Integer id) {
		Game gameFound = gameRepository.searchBy(id);
		Preconditions.checkNotNull(gameFound,
				"No game linked to the given id was found");
		Preconditions.checkArgument(gameFound.isPersisted() ,
				"The game is inactive");
		return gameFound;
	}

	@Override
	public void updateStatusBy(Integer id, Status status) {
		Game gameFound = this.gameRepository.findById(id).get();
		Preconditions.checkNotNull(gameFound,
				"No game linked to the given id was found");
		Preconditions.checkArgument(gameFound.getStatus() != status ,
				"The status entered is already assigned");
		this.gameRepository.updateStatusBy(id, status);
	}
	
	@Override
	public Game excludeBy(Integer id) {
		Game gameFound = gameRepository.findById(id).get();
		Preconditions.checkNotNull(gameFound,
				"No game linked to the given id was found");
		int qtyOfBoundGenerations = genreGameRepository.countBy(id);
		Preconditions.checkArgument(!(qtyOfBoundGenerations >= 1),
				"There are genres linked to informed play");
		return gameFound;
	}	
	
	private Enterprise getEnterpriseBy(GameRequestDto gameRequestDto) {
		Enterprise enterpriseFound = enterpriseRepository
				.findById(gameRequestDto.getEnterprise().getId()).get();
		Preconditions.checkNotNull(enterpriseFound,
				"Não foi encontrado empresa vinculada aos parâmetros passados");
		Preconditions.checkArgument(enterpriseFound.isActive(),
				"A empresa informada está inativa");
		return enterpriseFound;
	}
	
	private Genre getGenreBy(Integer idDoGenero) {
		Genre genreFound = genreRepository.findById(idDoGenero).get();
		Preconditions.checkNotNull(genreFound,
				"No genre was found linked to the given parameters");
		Preconditions.checkArgument(genreFound.isActive(),
				"The entered genre is inactive");
		return genreFound;
	}
	
	private void validateDuplication(List<GenreGameRequestDto> generoDoJogoDtoList) {
		for(GenreGameRequestDto genreGame: generoDoJogoDtoList) {
			int amountDuplicated = 0;
			for(GenreGameRequestDto otherGenreGameDto: generoDoJogoDtoList) {
				if (genreGame.getGenreId().equals(otherGenreGameDto.getGenreId())) {
					amountDuplicated++;
				}
			}
			Preconditions.checkArgument(amountDuplicated >= 1,
					"There are duplicate genres in the list");
		}
	}
}
