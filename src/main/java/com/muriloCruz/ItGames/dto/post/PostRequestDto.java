package com.muriloCruz.ItGames.dto.post;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {

	@NotNull(message = "The description is required")
	private String description;

	@DecimalMin(value = "0.0", inclusive = true, message = "The price must be positive")
	@Digits(integer = 4, fraction = 2, message = "The price must have the format 'NNNN.NN'")
	@NotNull(message = "The price is required")
	private BigDecimal price;

	@Size(max = 250, min = 3, message = "The name must contain between 3 and 250 characters")
	@Email(message = "The e-mail is an invalid format")
	@NotBlank(message = "The user is required")
	private String userEmail;

	@Positive(message = "Game ID must be greater than 0")
	@NotNull(message = "The game ID is required")
	private Long gameId;

//	@NotBlank(message = "The image URL is required")
//	private String imageUrl;
}
