package com.muriloCruz.ItGames.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import com.muriloCruz.ItGames.dto.PostRequestDto;
import com.muriloCruz.ItGames.entity.Post;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.PostSavedDto;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.GameRepository;
import com.muriloCruz.ItGames.repository.PostRepository;
import com.muriloCruz.ItGames.service.PostService;

@org.springframework.stereotype.Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Post insert(PostRequestDto postRequestDto) {
		User userFound = getUserBy(postRequestDto.getUserLogin());
		Game gameFound = getGameBy(postRequestDto.getGameId());
		Post post = new Post();
		post.setDescription(postRequestDto.getDescription());
		post.setPrice(postRequestDto.getPrice());
		post.setImageUrl(postRequestDto.getImageUrl());
		post.setUser(userFound);
		post.setGame(gameFound);
		Post postSave = postRepository.save(post);
		return postSave;
	}

	@Override
	public Post update(PostSavedDto postSavedDto) {
		Post postFound = searchBy(postSavedDto.getId());
		Game game = getGameBy(postSavedDto.getGameId());
		postFound.setDescription(postSavedDto.getDescription());
		postFound.setAvailability(postSavedDto.getAvailability());
		postFound.setPrice(postSavedDto.getPrice());
		postFound.setGame(game);
		Post postUpdated = postRepository.saveAndFlush(postFound);
		return postUpdated;
	}

	@Override
	public Page<Post> listBy(BigDecimal price, Integer gameId, Pageable pagination) {
		Game gameFound = getGameBy(gameId);
		Preconditions.checkArgument(price != null || gameFound != null,
				"You must provide at least one price or one game for listing");
		return postRepository.listBy(price, gameFound, pagination);
	}

	@Override
	public Post searchBy(Integer id) {
		Post postFound = postRepository.searchBy(id);
		Preconditions.checkNotNull(postFound,
				"No service was found to be linked to the reported parameters");
		Preconditions.checkArgument(postFound.isActive(),
				"The service is inactive");
		return postFound;
	}

	@Override
	public void updateStatusBy(Integer id, Status status) {
		Optional<Post> optionalService = this.postRepository.findById(id);
		Preconditions.checkArgument(optionalService.isPresent(),
				"No service was found linked to the id entered");
		Post postFound = optionalService.get();
		Preconditions.checkArgument(postFound.getStatus() != status ,
				"The entered status is already assigned");
		this.postRepository.updateStatusBy(id, status);

	}

	@Override
	public Post excludeBy(Integer id) {
		Post postFound = searchBy(id);
		this.postRepository.deleteById(postFound.getId());
		return postFound;
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
