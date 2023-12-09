package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.repository.PostRepository;
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

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public User insert(UserRequestDto userRequestDto) {
		User userFound = userRepository.searchBy(userRequestDto.getLogin());
		Preconditions.checkNotNull(userFound,
				"There is already a user registered with this login");
		User user = new User();
		user.setLogin(userRequestDto.getLogin());
		user.setPassword(userRequestDto.getPassword());
		user.setName(userRequestDto.getName());
		user.setCpf(userRequestDto.getCpf());
		user.setBirthDate(userRequestDto.getBirthDate());
		User userSalvo = userRepository.save(user);
		return userSalvo;
	}

	@Override
	public User update(UserSavedDto userSavedDto) {
		User userFound = searchBy(userSavedDto.getId());
		userFound.setLogin(userSavedDto.getLogin());
		userFound.setPassword(userSavedDto.getPassword());
		userFound.setName(userSavedDto.getName());
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
		Optional<User> optionalUser = userRepository.findById(id);
		Preconditions.checkArgument(optionalUser.isPresent(),
				"No user was found to be linked to the reported parameters");
		User userFound = optionalUser.get();
		Preconditions.checkArgument(userFound.isActive(),
				"The user informed is inactive");
		return userFound;
	}

	@Override
	public void updateStatusBy(Integer id, Status status) {
		User userFound = this.searchBy(id);
		Preconditions.checkNotNull(userFound,
				"No user was found to be linked to the reported parameters");
		Preconditions.checkArgument(userFound.getStatus() != status ,
				"The status entered is already assigned");
		this.userRepository.updateStatusBy(id, status);
	}
}
