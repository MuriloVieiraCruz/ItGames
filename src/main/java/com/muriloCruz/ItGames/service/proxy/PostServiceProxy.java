package com.muriloCruz.ItGames.service.proxy;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.post.PostRequestDto;
import com.muriloCruz.ItGames.dto.post.PostSavedDto;
import com.muriloCruz.ItGames.entity.Post;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Availability;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.PostService;
import com.muriloCruz.ItGames.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class PostServiceProxy implements PostService {

    @Autowired
    @Qualifier("postServiceImpl")
    private PostService service;

    @Override
    public Post insert(PostRequestDto postRequestDto) {
        return service.insert(postRequestDto);
    }

    @Override
    public void linkFreelancer(Long freelancerId, Long postId) {
        service.linkFreelancer(freelancerId, postId);
    }

    @Override
    public Post update(PostSavedDto postSavedDto) {
        return service.update(postSavedDto);
    }

    @Override
    public Page<Post> listBy(Optional<BigDecimal> price,
                             Optional<Availability> availability,
                             Optional<LocalDate> postDate,
                             Optional<Long> gameId,
                             Optional<Long> userId,
                             Pageable pagination) {
        return service.listBy(price, availability, postDate, gameId, userId, pagination);
    }

    @Override
    public Post searchBy(Long id) {
        return service.searchBy(id);
    }

    @Override
    public void updateStatusBy(Long id, Status status) {
        this.service.updateStatusBy(id, status);
    }

    @Override
    public void deleteBy(Long id) {
        service.deleteBy(id);
    }
}
