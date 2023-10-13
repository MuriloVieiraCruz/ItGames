package com.muriloCruz.ItGames.service;

import java.time.Instant;

import com.muriloCruz.ItGames.entity.Post;
import com.muriloCruz.ItGames.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.dto.PostRequestDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface PostService {

	public Post insert(
			@Valid
			@NotNull(message = "Posting is required")
			PostRequestDto postRequestDto);

	public Page<Post> listBy(
			Service service,
			Instant postingDate,
			Pageable pagination);

	public Post searchBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id);

	public Post excludeBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id);
}
