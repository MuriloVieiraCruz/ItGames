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
import com.muriloCruz.ItGames.dto.GameSavedDto;
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
	private PostRepository postRepository;
	
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
	public Game update(GameSavedDto gameSavedDto) {
		Enterprise enterpriseFound = enterpriseRepository
				.findById(gameSavedDto.getEnterprise().getId()).get();
		Game gameFound = searchBy(gameSavedDto.getId());
		gameFound.setName(gameSavedDto.getName());
		gameFound.setDescription(gameSavedDto.getDescription());
		gameFound.setReleaseDate(gameSavedDto.getReleaseDate());
		gameFound.setStatus(gameSavedDto.getStatus());
		gameFound.setImageUrl(gameSavedDto.getImageUrl());
		gameFound.setEnterprise(enterpriseFound);
		Game gameSaved = gameRepository.saveAndFlush(gameFound);
		return gameSaved;
	}

	@Override
	public Page<Game> listBy(String name, Integer genreId, Pageable pagination) {
		Preconditions.checkArgument(name != null || genreId != null,
				"You must provide at least one name or gender for the list");
		Genre genreFound = getGenreBy(genreId);
		return this.gameRepository.listBy(name, genreFound, pagination);
	}

	@Override
	public Game searchBy(Integer id) {
		Game gameFound = gameRepository.searchBy(id);
		Preconditions.checkNotNull(gameFound,
				"No game was found to be linked to the reported parameters");
		Preconditions.checkArgument(gameFound.isPersisted() ,
				"The game is inactive");
		return gameFound;
	}

	@Override
	public void updateStatusBy(Integer id, Status status) {
		Game gameFound = this.gameRepository.findById(id).get();
		Preconditions.checkNotNull(gameFound,
				"No game was found to be linked to the reported parameters");
		Preconditions.checkArgument(gameFound.getStatus() != status ,
				"The status entered is already assigned");
		this.gameRepository.updateStatusBy(id, status);
	}
	
	@Override
	public Game excludeBy(Integer id) {
		Game gameFound = searchBy(id);
		int qtyOfBoundGenerations = genreGameRepository.countByGame(id);
		Preconditions.checkArgument(!(qtyOfBoundGenerations >= 1),
				"There are genres linked to informed play");
		int numberLinkedServices = postRepository.countBy(id);
		Preconditions.checkArgument(!(numberLinkedServices >= 1),
				"There are services linked to informed play");
		return gameFound;
	}	
	
	private Enterprise getEnterpriseBy(GameRequestDto gameRequestDto) {
		Optional<Enterprise> optionalEnterprise = enterpriseRepository
				.findById(gameRequestDto.getEnterprise().getId());
		Preconditions.checkNotNull(optionalEnterprise.isPresent(),
				"No enterprise was found to be linked to the reported parameters");
		Enterprise enterpriseFound = optionalEnterprise.get();
		Preconditions.checkArgument(enterpriseFound.isActive(),
				"The enterprise is inactive");
		return enterpriseFound;
	}
	
	private Genre getGenreBy(Integer idDoGenero) {
		Optional<Genre> optionalGenre = genreRepository.findById(idDoGenero);
		Preconditions.checkNotNull(optionalGenre.isPresent(),
				"No genre was found to be linked to the reported parameters");
		Genre genreFound = optionalGenre.get();
		Preconditions.checkArgument(genreFound.isActive(),
				"The genre is inactive");
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
