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

	@NotNull(message = "The description cannot be null")
	private String description;

	@DecimalMin(value = "0.0", inclusive = true, message = "The price must be positive")
	@Digits(integer = 10, fraction = 2, message = "The price must have the format 'NNNNNNNNNNN.NN'")
	@NotNull(message = "Price cannot be null")
	private BigDecimal price;

	@Size(max = 250, min = 3, message = "The name must contain between 3 and 250 characters")
	@Email(message = "The e-mail is in an invalid format")
	@NotBlank(message = "User is required")
	private String userLogin;

	@Positive(message = "Game ID must be greater than 0")
	@NotNull(message = "Game ID is required")
	private Long gameId;

	@NotBlank(message = "Image URL is required")
	private String imageUrl;
}
