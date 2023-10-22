package com.muriloCruz.ItGames.dto;

import java.math.BigDecimal;

import com.muriloCruz.ItGames.entity.enums.Availability;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ServiceSavedDto {

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
	private Integer gameId;
}
