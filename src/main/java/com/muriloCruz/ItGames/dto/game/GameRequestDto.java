package com.muriloCruz.ItGames.dto.game;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.muriloCruz.ItGames.dto.genreGame.GenreGameRequestDto;
import com.muriloCruz.ItGames.entity.Enterprise;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameRequestDto {

	@Size(max = 100, min = 3, message = "Name must contain between 3 and 100 characters")
	@NotBlank(message = "The name is required")
	private String name;

	@NotBlank(message = "The name is required")
	private String description;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	@NotNull(message = "The release date is required")
	private LocalDate releaseDate;

//	@Lob
//	private String imageUrl;

	@NotNull(message = "The game's enterprise is required")
	private Enterprise enterprise;

	@Size(min = 1, message = "The game must have at least one genre")
	private List<GenreGameRequestDto> genres;
	
	public GameRequestDto() {
		this.genres = new ArrayList<>();
	}
}
