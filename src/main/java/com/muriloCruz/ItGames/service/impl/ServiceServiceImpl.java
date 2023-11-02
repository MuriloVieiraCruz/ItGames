package com.muriloCruz.ItGames.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import com.muriloCruz.ItGames.dto.ServiceRequestDto;
import com.muriloCruz.ItGames.entity.Service;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.ServiceSavedDto;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.GameRepository;
import com.muriloCruz.ItGames.repository.ServiceRepository;
import com.muriloCruz.ItGames.service.ServiceService;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
	
	@Autowired
	private ServiceRepository serviceRepository;
	
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Service insert(ServiceRequestDto serviceRequestDto) {
		User userFound = getUserBy(serviceRequestDto.getUserLogin());
		Game gameFound = getGameBy(serviceRequestDto.getGameId());
		Service service = new Service();
		service.setDescription(serviceRequestDto.getDescription());
		service.setPrice(serviceRequestDto.getPrice());
		service.setImageUrl(serviceRequestDto.getImageUrl());
		service.setUser(userFound);
		service.setGame(gameFound);
		Service serviceSave = serviceRepository.save(service);
		return serviceSave;
	}

	@Override
	public Service update(ServiceSavedDto serviceSavedDto) {
		Service serviceFound = searchBy(serviceSavedDto.getId());
		Game game = getGameBy(serviceSavedDto.getGameId());
		serviceFound.setDescription(serviceSavedDto.getDescription());
		serviceFound.setAvailability(serviceSavedDto.getAvailability());
		serviceFound.setPrice(serviceSavedDto.getPrice());
		serviceFound.setGame(game);
		Service serviceUpdated = serviceRepository.saveAndFlush(serviceFound);
		return serviceUpdated;
	}

	@Override
	public Page<Service> listBy(BigDecimal price, Integer gameId, Pageable pagination) {
		Game gameFound = getGameBy(gameId);
		Preconditions.checkArgument(price != null || gameFound != null,
				"You must provide at least one price or one game for listing");
		return serviceRepository.listBy(price, gameFound, pagination);
	}

	@Override
	public Service searchBy(Integer id) {
		Service serviceFound = serviceRepository.searchBy(id);
		Preconditions.checkNotNull(serviceFound,
				"No service was found to be linked to the reported parameters");
		Preconditions.checkArgument(serviceFound.isActive(),
				"The service is inactive");
		return serviceFound;
	}

	@Override
	public void updateStatusBy(Integer id, Status status) {
		Optional<Service> optionalService = this.serviceRepository.findById(id);
		Preconditions.checkArgument(optionalService.isPresent(),
				"No service was found linked to the id entered");
		Service serviceFound = optionalService.get();
		Preconditions.checkArgument(serviceFound.getStatus() != status ,
				"The entered status is already assigned");
		this.serviceRepository.updateStatusBy(id, status);

	}

	@Override
	public Service excludeBy(Integer id) {
		Service serviceFound = searchBy(id);
		this.serviceRepository.deleteById(serviceFound.getId());
		return serviceFound;
	}

	private Game getGameBy(Integer gameId) {
		Game gameFound = gameRepository.searchBy(gameId);
		Preconditions.checkNotNull(gameFound,
				"No game was found to be linked to the reported parameters");
		Preconditions.checkArgument(gameFound.isActive(),
				"The entered game is inactive");
		return gameFound;
	}

	private User getUserBy(String userLogin) {
		User userFound = userRepository.searchBy(userLogin);
		Preconditions.checkNotNull(userFound,
				"No user was found to be linked to the reported parameters");
		Preconditions.checkArgument(userFound.isActive(),
				"The entered user is inactive");
		return userFound;
	}

}
