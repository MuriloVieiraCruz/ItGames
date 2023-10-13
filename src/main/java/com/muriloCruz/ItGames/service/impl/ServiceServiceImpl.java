package com.muriloCruz.ItGames.service.impl;

import java.math.BigDecimal;

import com.muriloCruz.ItGames.entity.Service;
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
	
	@Override
	public Service update(ServiceSavedDto serviceSavedDto) {
		Service serviceFound = serviceRepository.searchBy(serviceSavedDto.getId());
		Game game = getGameBy(serviceSavedDto.getGameId());
		Preconditions.checkNotNull(serviceFound,
				"User linked to the parameters was not found");
		Preconditions.checkArgument(serviceFound.isActive(),
				"The user is inactive");
		serviceFound.setDescription(serviceSavedDto.getDescription());
		serviceFound.setAvailability(serviceSavedDto.getAvailability());
		serviceFound.setPrice(serviceSavedDto.getPrice());
		serviceFound.setGame(game);
		Service serviceUpdated = serviceRepository.saveAndFlush(serviceFound);
		return serviceUpdated;
	}

	@Override
	public Page<Service> listBy(BigDecimal price, Game game, Pageable pagination) {
		Preconditions.checkArgument(price != null || game != null,
				"You must provide at least one price or one game for listing");
		return serviceRepository.listBy(price, game, pagination);
	}

	@Override
	public Service searchBy(Integer id) {
		Service serviceFound = serviceRepository.searchBy(id);
		Preconditions.checkNotNull(serviceFound,
				"Service linked to the parameters was not found");
		Preconditions.checkArgument(serviceFound.isActive(),
				"The user is inactive");
		return serviceFound;
	}

	@Override
	public void updateStatusBy(Integer id, Status status) {
		Service serviceFound = this.serviceRepository.findById(id).get();
		Preconditions.checkNotNull(serviceFound,
				"No service was found linked to the id entered");
		Preconditions.checkArgument(serviceFound.getStatus() != status ,
				"The entered status is already assigned");
		this.serviceRepository.updateStatusBy(id, status);

	}

	@Override
	public Service excludeBy(Integer id) {
		Service serviceFound = serviceRepository.searchBy(id);
		Preconditions.checkNotNull(serviceFound,
				"User linked to the parameters was not found");
		Preconditions.checkArgument(serviceFound.isActive(),
				"The user is inactive");
		this.serviceRepository.delete(serviceFound);
		return serviceFound;
	}

	private Game getGameBy(Integer gameId) {
		Game gameFound = gameRepository.searchBy(gameId);
		Preconditions.checkNotNull(gameFound,
				"No game was found linked to the parameters passed");
		Preconditions.checkArgument(gameFound.isActive(),
				"The entered game is inactive");
		return gameFound;
	}

}
