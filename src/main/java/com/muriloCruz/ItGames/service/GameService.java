package com.muriloCruz.ItGames.service;

import java.util.List;
import java.util.Optional;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.genreGame.GenreGameRequestDto;
import com.muriloCruz.ItGames.dto.game.GameRequestDto;
import com.muriloCruz.ItGames.dto.game.GameSavedDto;
import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.composite.GenreGameId;
import com.muriloCruz.ItGames.entity.enums.Status;

@Service
public class GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private EnterpriseService enterpriseService;

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private GenreGameRepository genreGameRepository;

	
	public Game insert(GameRequestDto gameRequestDto) {
		Enterprise validEnterprise = getEnterpriseBy(gameRequestDto.getEnterprise().getId());
		Game game = new Game();
		game.setName(gameRequestDto.getName());
		game.setDescription(gameRequestDto.getDescription());
		game.setReleaseDate(gameRequestDto.getReleaseDate());
		game.setImageUrl(gameRequestDto.getImageUrl());
		game.setEnterprise(validEnterprise);
		Game gameSaved = gameRepository.save(game);
		validateDuplication(gameRequestDto.getGenres());
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

	
	public Game update(GameSavedDto gameSavedDto) {
		Enterprise enterpriseFound = enterpriseService.searchBy(gameSavedDto.getEnterprise().getId());
		Game gameFound = searchBy(gameSavedDto.getId());
		gameFound.setName(gameSavedDto.getName());
		gameFound.setDescription(gameSavedDto.getDescription());
		gameFound.setReleaseDate(gameSavedDto.getReleaseDate());
		gameFound.setStatus(gameSavedDto.getStatus());
		gameFound.setImageUrl(gameSavedDto.getImageUrl());
		gameFound.setEnterprise(enterpriseFound);
        return gameRepository.saveAndFlush(gameFound);
	}

	public void updateStatusBy(Long id, Status status) {
		Game gameFound = this.searchBy(id);
		Preconditions.checkNotNull(gameFound,
				"No game was found linked to the reported parameters");
		Preconditions.checkArgument(gameFound.getStatus() != status ,
				"The status entered is already assigned");
		this.gameRepository.updateStatusBy(id, status);
	}

	public Game searchBy(Long id) {
		Game gameFound = gameRepository.searchBy(id);
		Preconditions.checkNotNull(gameFound,
				"No game was found linked to the reported parameters");
		Preconditions.checkArgument(gameFound.isPersisted() ,
				"The game is inactive");
		return gameFound;
	}

	public Page<Game> listBy(String name, Optional<Long> genreId, Pageable pagination) {
		return this.gameRepository.listBy(name, genreId, pagination);
	}

	public Game deleteBy(Long id) {
		Game gameFound = searchBy(id);
		int qtyOfBoundGenerations = genreGameRepository.countByGame(id);
		Preconditions.checkArgument(qtyOfBoundGenerations <= 0,
				"There are genres linked to the game");
		int numberLinkedServices = postRepository.countBy(id);
		Preconditions.checkArgument(numberLinkedServices <= 0,
				"There are services linked to the game");
		gameRepository.deleteBy(gameFound.getId());
		return gameFound;
	}	
	
	private Enterprise getEnterpriseBy(Long enterpriseId) {
		Enterprise enterpriseFound = enterpriseService
				.searchBy(enterpriseId);
		Preconditions.checkNotNull(enterpriseFound,
				"No enterprise was found linked to the reported parameters");
		Preconditions.checkArgument(enterpriseFound.isActive(),
				"The enterprise is inactive");
		return enterpriseFound;
	}
	
	private Genre getGenreBy(Long genreId) {
		Genre genreFound = genreRepository.searchBy(genreId);
		Preconditions.checkNotNull(genreFound,
				"No genre was found to be linked to the reported parameters");
		Preconditions.checkArgument(genreFound.isActive(),
				"The genre is inactive");
		return genreFound;
	}
	
	private void validateDuplication(List<GenreGameRequestDto> generoDoJogoDtoList) {

		generoDoJogoDtoList
				.forEach(genreGame -> {
					long amountDuplicated = generoDoJogoDtoList.stream()
							.filter(otherGenreGame -> genreGame.getGenreId().equals(otherGenreGame.getGenreId()))
							.count();

					Preconditions.checkArgument(amountDuplicated >= 1,
							"There are duplicate genres in the list");
				});
	}
}
