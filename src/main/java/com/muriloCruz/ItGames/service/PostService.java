package com.muriloCruz.ItGames.service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.post.PostRequestDto;
import com.muriloCruz.ItGames.dto.post.PostSavedDto;
import com.muriloCruz.ItGames.entity.Post;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Availability;
import com.muriloCruz.ItGames.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface PostService {

    public Post insert(PostRequestDto postRequestDto);
    public void linkFreelancer(Long freelancerId, Long postId);

    public Post update(PostSavedDto postSavedDto);

    public Page<Post> listBy(Optional<BigDecimal> price,
                             Optional<Availability> availability,
                             Optional<LocalDate> postDate,
                             Optional<Long> gameId,
                             Optional<Long> userId,
                             Pageable pagination);

    public Post searchBy(Long id);

    public void updateStatusBy(Long id, Status status);

    public void deleteBy(Long id);
}
