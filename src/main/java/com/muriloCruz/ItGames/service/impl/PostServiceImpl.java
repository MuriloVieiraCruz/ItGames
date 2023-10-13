package com.muriloCruz.ItGames.service.impl;

import java.time.Instant;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.PostRequestDto;
import com.muriloCruz.ItGames.entity.Service;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.repository.GameRepository;
import com.muriloCruz.ItGames.repository.PostRepository;
import com.muriloCruz.ItGames.repository.UserRepository;
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
        Game game = getGameBy(postRequestDto.getServiceRequestDto().getGameId());
        User user = getUserBy(postRequestDto.getServiceRequestDto().getUserId());
        Service service = new Service();
        service.setDescription(postRequestDto.getServiceRequestDto().getDescription());
        service.setPrice(postRequestDto.getServiceRequestDto().getPrice());
        service.setGame(game);
        service.setUser(user);
        Post post = new Post();
        post.setImageUrl(postRequestDto.getImageUrl());
        post.setService(service);
        service.setPost(post);
        Post postSaved = this.postRepository.saveAndFlush(post);
        return postSaved;
    }

    @Override
    public Page<Post> listBy(Service service, Instant postingDate, Pageable pagination) {
        Preconditions.checkArgument(service != null || postingDate != null,
                "At least one service or date must be listed");
        return this.postRepository.listBy(service, postingDate, pagination);
    }

	@Override
	public Post searchBy(Integer id) {
		Post postFound = postRepository.searchBy(id);
		Preconditions.checkNotNull(postFound,
				"No post was found linked to the id entered");
		Preconditions.checkArgument(postFound.getService().isPersisted(),
				"The post is inactive");
		return postFound;
	}

	@Override
	public Post excludeBy(Integer id) {
		Post postFound = postRepository.searchBy(id);
		Preconditions.checkNotNull(postFound,
				"No post was found linked to the id entered");
		Preconditions.checkArgument(postFound.getService().isPersisted(),
				"The post is inactive");
		this.postRepository.deleteById(id);
		return postFound;
	}

	private Game getGameBy(Integer gameId) {
		Game gameFound = gameRepository.searchBy(gameId);
		Preconditions.checkNotNull(gameFound,
				"No game was found linked to the parameters passed");
		Preconditions.checkArgument(gameFound.isActive(),
				"The entered game is inactive");
		return gameFound;
	}

	private User getUserBy(Integer userId) {
		User userFound = userRepository.findById(userId).get();
		Preconditions.checkNotNull(userFound,
				"User linked to the passed parameters was not found");
		Preconditions.checkArgument(userFound.isActive(),
				"The user entered is inactive");
		return userFound;
	}

}
