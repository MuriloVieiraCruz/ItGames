package com.muriloCruz.ItGames.dto.game;

import java.time.Instant;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GameSavedDto {

	@Positive(message = "The ID must be positive")
	@NotNull(message = "The ID is required")
	private Long id;

	@Size(max = 100, min = 3, message = "The name must contain between 3 and 100 characters")
	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "The description is required")
	private String description;

	@NotNull(message = "Status is required")
	private Status status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	@NotNull(message = "The launch date is required")
	private LocalDate releaseDate;

	@NotBlank(message = "Image URL is required")
	private String imageUrl;

	@NotNull(message = "Enterprise is required")
	private Enterprise enterprise;
}
