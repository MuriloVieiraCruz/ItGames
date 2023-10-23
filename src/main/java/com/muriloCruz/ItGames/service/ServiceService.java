package com.muriloCruz.ItGames.service;

import java.math.BigDecimal;

import com.muriloCruz.ItGames.dto.ServiceRequestDto;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.dto.ServiceSavedDto;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface ServiceService {

	public Service insert(
			@Valid
			@NotNull(message = "The service is required")
			ServiceRequestDto serviceRequestDto);

	public Service update(
			@Valid
			@NotNull(message = "The service is required")
			ServiceSavedDto serviceSavedDto);

	public Page<Service> listBy(
			BigDecimal price,
			Game game,
			Pageable page);

	public Service searchBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id);

	public void updateStatusBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id,
			@NotNull(message = "The status is required")
			Status status);

	public Service excludeBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id);

}
