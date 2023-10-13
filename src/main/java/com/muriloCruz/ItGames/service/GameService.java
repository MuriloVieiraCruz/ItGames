package com.muriloCruz.ItGames.service;

import com.muriloCruz.ItGames.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.dto.GameRequestDto;
import com.muriloCruz.ItGames.dto.GameSalvedDto;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface GameService {

	public Game insert(
			@Valid
			@NotNull(message = "The game is required")
			GameRequestDto gameRequestDto);
	
	public Game update(
			@Valid
			@NotNull(message = "The game is required")
			GameSalvedDto gameSalvedDto);
	
	public Page<Game> listBy(
			String name,
			Genre genre,
			Pageable pagination);
	
	public Game searchBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id);

	public void updateStatusBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id,
			@NotNull(message = "The Status is required")
			Status status);

	public Game excludeBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id);
}
