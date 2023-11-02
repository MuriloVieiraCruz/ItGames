package com.muriloCruz.ItGames.service;

import java.math.BigDecimal;

import com.muriloCruz.ItGames.dto.PostRequestDto;
import com.muriloCruz.ItGames.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.dto.PostSavedDto;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface PostService {

	public Post insert(
			@Valid
			@NotNull(message = "The service is required")
			PostRequestDto postRequestDto);

	public Post update(
			@Valid
			@NotNull(message = "The service is required")
			PostSavedDto postSavedDto);

	public Page<Post> listBy(
			BigDecimal price,
			Integer gameId,
			Pageable page);

	public Post searchBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id);

	public void updateStatusBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id,
			@NotNull(message = "The status is required")
			Status status);

	public Post excludeBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id);

}
