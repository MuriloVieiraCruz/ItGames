package com.muriloCruz.ItGames.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.UserRequestDto;
import com.muriloCruz.ItGames.dto.UserSavedDto;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.UserRepository;
import com.muriloCruz.ItGames.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User insert(UserRequestDto userRequestDto) {
		User user = new User();
		user.setLogin(userRequestDto.getLogin());
		user.setPassword(userRequestDto.getPassword());
		user.setEmail(userRequestDto.getEmail());
		user.setCpf(userRequestDto.getCpf());
		user.setBirthDate(userRequestDto.getBirthDate());
		User userSalvo = userRepository.saveAndFlush(user);
		return userSalvo;
	}

	@Override
	public User update(UserSavedDto userSavedDto) {
		User userFound = userRepository.findById(userSavedDto.getId()).get();
		Preconditions.checkNotNull(userFound,
				"User linked to the parameters was not found");
		Preconditions.checkArgument(userFound.isActive(),
				"The user is inactive");
		userFound.setLogin(userSavedDto.getLogin());
		userFound.setPassword(userSavedDto.getPassword());
		userFound.setEmail(userSavedDto.getEmail());
		userFound.setCpf(userSavedDto.getCpf());
		userFound.setBirthDate(userSavedDto.getBirthDate());
		User userUpdated = userRepository.saveAndFlush(userFound);
		return userUpdated;
	}

	@Override
	public Page<User> listBy(String login, Pageable paginacao) {
		return userRepository.listBy(login, paginacao);
	}

	@Override
	public User searchBy(Integer id) {
		User userFound = userRepository.findById(id).get();
		Preconditions.checkNotNull(userFound,
				"User linked to the parameters was not found");
		Preconditions.checkArgument(userFound.isActive(),
				"The user is inactive");
		return userFound;
	}

	@Override
	public void updateStatusBy(Integer id, Status status) {
		User userFound = userRepository.findById(id).get();
		Preconditions.checkNotNull(userFound,
				"User linked to the parameters was not found");
		Preconditions.checkArgument(userFound.isActive(),
				"The user is inactive");
		this.userRepository.updateStatusBy(id, status);
	}

	@Override
	public User excludeBy(Integer id) {
		User userFound = userRepository.findById(id).get();
		Preconditions.checkNotNull(userFound,
				"User linked to the parameters was not found");
		Preconditions.checkArgument(userFound.isActive(),
				"The user is inactive");
		this.userRepository.deleteById(userFound.getId());
		return userFound;
	}

}
