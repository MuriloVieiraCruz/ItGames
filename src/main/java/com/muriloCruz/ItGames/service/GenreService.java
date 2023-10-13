package com.muriloCruz.ItGames.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Validated
public interface GenreService {

	public Genre insert(
			@Valid
			@NotNull(message = "The gender is required")
            Genre genre);
	
	public Genre searchBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id);
	
	public Page<Genre> listBy(
			@Size(min = 3, max = 100, message = "The name must contain between 3 and 100 characters")
			@NotBlank(message = "The name is required")
			String name,
			Pageable paginacao);
	
	public void updateStatusBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id,
			@NotNull(message = "The status is required")
			Status status);
	
	public Genre excludeBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id);
}
