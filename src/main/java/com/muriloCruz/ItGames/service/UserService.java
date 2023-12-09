package com.muriloCruz.ItGames.service;

import com.muriloCruz.ItGames.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.dto.UserRequestDto;
import com.muriloCruz.ItGames.dto.UserSavedDto;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Validated
public interface UserService {
	
	public User insert(
			@Valid
			@NotNull(message = "The user is required")
            UserRequestDto userRequestDto);
	
	public User update(
			@Valid
			@NotNull(message = "The user is required")
			UserSavedDto userSavedDto);

	public Page<User> listBy(
			@Size(max = 250, min = 3, message = "Login must contain between 3 and 250 characters")
			@NotBlank(message = "The login is required")
			String login,
			Pageable pagination);

	public User searchBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id);

	public void updateStatusBy(
			@Positive(message = "The ID must be greater than 0")
			@NotNull(message = "The ID is required")
			Integer id,
			@NotNull(message = "The status is required")
			Status status);
}
