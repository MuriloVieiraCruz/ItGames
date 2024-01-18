package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.repository.PostRepository;
import com.muriloCruz.ItGames.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.user.UserRequestDto;
import com.muriloCruz.ItGames.dto.user.UserSavedDto;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public User insert(UserRequestDto userRequestDto) {
		User userFound = userRepository.searchBy(userRequestDto.getEmail());
		Preconditions.checkArgument(userFound == null,
				"There is already a user registered with this login");
		User user = new User();
		user.setEmail(userRequestDto.getEmail());
		String hashPsswrd = passwordEncoder.encode(userRequestDto.getPassword());
		user.setPassword(hashPsswrd);
		user.setName(userRequestDto.getName());
		user.setCpf(userRequestDto.getCpf());
		user.setBirthDate(userRequestDto.getBirthDate());
        return userRepository.save(user);
	}

	@Override
	public User update(UserSavedDto userSavedDto) {
		User userFound = searchBy(userSavedDto.getId());
		userFound.setEmail(userSavedDto.getEmail());
		userFound.setPassword(userSavedDto.getPassword());
		userFound.setName(userSavedDto.getName());
		userFound.setCpf(userSavedDto.getCpf());
		userFound.setBirthDate(userSavedDto.getBirthDate());
        return userRepository.saveAndFlush(userFound);
	}

	@Override
	public Page<User> listBy(String name, Pageable paginacao) {
		return userRepository.listBy(name, paginacao);
	}

	@Override
	public User searchBy(Long id) {
		User userFound = userRepository.searchBy(id);
		Preconditions.checkNotNull(userFound,
				"No user was found to be linked to the reported parameters");
		Preconditions.checkArgument(userFound.isActive(),
				"The user informed is inactive");
		return userFound;
	}

	public User searchBy(String email) {
		User userFound = userRepository.searchBy(email);
		Preconditions.checkNotNull(userFound,
				"No user was found to be linked to the reported parameters");
		Preconditions.checkArgument(userFound.isActive(),
				"The user informed is inactive");
		return userFound;
	}

	@Override
	public void updateStatusBy(Long id, Status status) {
		User userFound = userRepository.searchBy(id);
		Preconditions.checkNotNull(userFound,
				"No user was found linked to the reported parameters");
		Preconditions.checkArgument(userFound.getStatus() != status ,
				"The status entered is already assigned");
		this.userRepository.updateStatusBy(id, status);
	}
}
