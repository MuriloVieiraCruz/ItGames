package com.muriloCruz.ItGames.service;

import com.muriloCruz.ItGames.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.user.UserRequestDto;
import com.muriloCruz.ItGames.dto.user.UserSavedDto;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	public User insert(UserRequestDto userRequestDto) {
		User userFound = userRepository.searchBy(userRequestDto.getEmail());
		Preconditions.checkNotNull(userFound,
				"There is already a user registered with this login");
		User user = new User();
		user.setEmail(userRequestDto.getEmail());
		user.setPassword(userRequestDto.getPassword());
		user.setName(userRequestDto.getName());
		user.setCpf(userRequestDto.getCpf());
		user.setBirthDate(userRequestDto.getBirthDate());
        return userRepository.save(user);
	}
	
	public User update(UserSavedDto userSavedDto) {
		User userFound = searchBy(userSavedDto.getId());
		userFound.setEmail(userSavedDto.getEmail());
		userFound.setPassword(userSavedDto.getPassword());
		userFound.setName(userSavedDto.getName());
		userFound.setCpf(userSavedDto.getCpf());
		userFound.setBirthDate(userSavedDto.getBirthDate());
        return userRepository.saveAndFlush(userFound);
	}
	
	public Page<User> listBy(String login, Pageable paginacao) {
		return userRepository.listBy(login, paginacao);
	}

	public User searchBy(Long id) {
		User userFound = userRepository.searchBy(id);
		Preconditions.checkNotNull(userFound,
				"No user was found to be linked to the reported parameters");
		Preconditions.checkArgument(userFound.isActive(),
				"The user informed is inactive");
		return userFound;
	}

	public void updateStatusBy(Long id, Status status) {
		User userFound = this.searchBy(id);
		Preconditions.checkNotNull(userFound,
				"No user was found linked to the reported parameters");
		Preconditions.checkArgument(userFound.getStatus() != status ,
				"The status entered is already assigned");
		this.userRepository.updateStatusBy(id, status);
	}
}
