package com.muriloCruz.ItGames.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import com.muriloCruz.ItGames.dto.post.PostRequestDto;
import com.muriloCruz.ItGames.entity.Post;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Availability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.post.PostSavedDto;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.GameRepository;
import com.muriloCruz.ItGames.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private GameRepository gameRepository;

	public Post insert(PostRequestDto postRequestDto) {
		//User userFound = getUserBy(postRequestDto.getUserId());
		Game gameFound = getGameBy(postRequestDto.getGameId());
		Post post = new Post();
		post.setDescription(postRequestDto.getDescription());
		post.setPrice(postRequestDto.getPrice());
		//post.setImageUrl(postRequestDto.getImageUrl());
		//post.setUser(userFound);
		post.setGame(gameFound);
        return postRepository.save(post);
	}

	public void linkFreelancer(Long freelancerId, Long postId) {
		//User freelancerFound = getUserBy(freelancerId);
		Optional<Post> postFound = postRepository.findById(postId);
		Preconditions.checkArgument(postFound.isPresent(),
				"There is no post to the ");
		Post post = postFound.get();
		//post.setFreelancer(freelancerFound);
		postRepository.save(post);
	}

	public Post update(PostSavedDto postSavedDto) {
		Post postFound = searchBy(postSavedDto.getId());
		Preconditions.checkNotNull(postFound,
				"No post was found linked to the id reported");
		Game game = getGameBy(postSavedDto.getGameId());
		postFound.setDescription(postSavedDto.getDescription());
		postFound.setAvailability(postSavedDto.getAvailability());
		postFound.setPrice(postSavedDto.getPrice());
		postFound.setGame(game);
        return postRepository.saveAndFlush(postFound);
	}

	public void updateStatusBy(Long id, Status status) {
		Post postFound = postRepository.searchBy(id);
		Preconditions.checkNotNull(postFound,
				"No post was found linked to the id reported");
		Preconditions.checkArgument(postFound.getStatus() != status ,
				"The entered status is already assigned");
		this.postRepository.updateStatusBy(id, status);

	}

	public Post searchBy(Long id) {
		Post postFound = postRepository.searchBy(id);
		Preconditions.checkNotNull(postFound,
				"No service was found linked to the reported parameters");
		Preconditions.checkArgument(postFound.isActive(),
				"The service is inactive");
		return postFound;
	}
	
	public Page<Post> listBy(Optional<BigDecimal> price,
							 Optional<Availability> availability,
							 Optional<LocalDate> postDate,
							 Optional<Long> gameId,
							 Optional<Long> userId,
							 Pageable pagination) {
		return postRepository.listBy(price, availability, postDate, gameId, userId, pagination);
	}

	public void deleteBy(Long id) {
		Post postFound = searchBy(id);
		this.postRepository.deleteBy(postFound.getId());
	}

	private Game getGameBy(Long gameId) {
		Game gameFound = gameRepository.searchBy(gameId);
		Preconditions.checkNotNull(gameFound,
				"No game was found linked to the reported parameters");
		Preconditions.checkArgument(gameFound.isActive(),
				"The entered game is inactive");
		return gameFound;
	}

//	private User getUserBy(Long userId) {
//		User userFound = userRepository.searchBy(userId);
//		Preconditions.checkNotNull(userFound,
//				"No user was found linked to the reported parameters");
//		Preconditions.checkArgument(userFound.isActive(),
//				"The entered user is inactive");
//		return userFound;
//	}

}
