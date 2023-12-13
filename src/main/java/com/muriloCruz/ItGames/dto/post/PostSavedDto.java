package com.muriloCruz.ItGames.dto.post;

import java.math.BigDecimal;

import com.muriloCruz.ItGames.entity.enums.Availability;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSavedDto {

	@Positive(message = "The ID must be positive")
	@NotNull(message = "The ID is required")
	private Integer id;

	@NotNull(message = "The description cannot be null")
	private String description;

	@DecimalMin(value = "0.0", inclusive = true, message = "The price must be positive")
	@Digits(integer = 10, fraction = 2, message = "The price must have the format 'NNNNNNNNNNN.NN'")
	@NotNull(message = "Price cannot be null")
	private BigDecimal price;

	@NotNull(message = "Availability is required")
	private Availability availability;

	@Positive(message = "Game ID must be greater than 0")
	@NotNull(message = "Game ID is required")
	private Long gameId;

	@NotBlank(message = "Image URL is required")
	private String imageUrl;
}
