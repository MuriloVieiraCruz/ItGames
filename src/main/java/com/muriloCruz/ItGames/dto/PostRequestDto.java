package com.muriloCruz.ItGames.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostRequestDto {

	private String imageUrl;
	
	@NotNull(message = "Postage is required")
	private ServiceRequestDto serviceRequestDto;
}
