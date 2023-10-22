package com.muriloCruz.ItGames.service.impl;

import java.util.List;
import java.util.Optional;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.repository.*;
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
import com.muriloCruz.ItGames.service.GameService;

@Service
public class GameServiceImpl implements GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private EnterpriseRepository enterpriseRepository;

	@Autowired
	private ServiceRepository serviceRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private GenreGameRepository genreGameRepository;

	@Override
	public Game insert(GameRequestDto gameRequestDto) {
		Enterprise validEnterprise = getEnterpriseBy(gameRequestDto);
		Game game = new Game();
		game.setName(gameRequestDto.getName());
		game.setDescription(gameRequestDto.getDescription());
		game.setReleaseDate(gameRequestDto.getReleaseDate());
		game.setImageUrl(gameRequestDto.getImageUrl());
		game.setEnterprise(validEnterprise);
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
		int qtyOfBoundGenerations = genreGameRepository.countByGame(id);
		Preconditions.checkArgument(!(qtyOfBoundGenerations >= 1),
				"There are genres linked to informed play");
		int numberLinkedServices = serviceRepository.countBy(id);
		Preconditions.checkArgument(!(numberLinkedServices >= 1),
				"There are services linked to informed play");
		return gameFound;
	}	
	
	private Enterprise getEnterpriseBy(GameRequestDto gameRequestDto) {
		Optional<Enterprise> optionalEnterprise = enterpriseRepository
				.findById(gameRequestDto.getEnterprise().getId());
		Preconditions.checkNotNull(optionalEnterprise.isPresent(),
				"No enterprise found linked to the parameters");
		Enterprise enterpriseFound = optionalEnterprise.get();
		Preconditions.checkArgument(enterpriseFound.isActive(),
				"The entered enterprise is inactive");
		return enterpriseFound;
	}
	
	private Genre getGenreBy(Integer idDoGenero) {
		Optional<Genre> optionalGenre = genreRepository.findById(idDoGenero);
		Preconditions.checkNotNull(optionalGenre.isPresent(),
				"No genre was found linked to the given parameters");
		Genre genreFound = optionalGenre.get();
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
