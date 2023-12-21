package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.dto.post.PostRequestDto;
import com.muriloCruz.ItGames.dto.post.PostSavedDto;
import com.muriloCruz.ItGames.entity.Post;
import com.muriloCruz.ItGames.entity.enums.Availability;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class PostServiceProxy {

    @Autowired
    private PostService service;

    public Post insert(PostRequestDto postRequestDto) {
        return service.insert(postRequestDto);
    }
    
    public Post update(PostSavedDto postSavedDto) {
        return service.update(postSavedDto);
    }
    
    public Page<Post> listBy(Optional<BigDecimal> price,
                             Optional<Availability> availability,
                             Optional<LocalDate> postDate,
                             Optional<Long> gameId,
                             Optional<Long> userId,
                             Pageable pagination) {
        return service.listBy(price, availability, postDate, gameId, userId, pagination);
    }
    
    public Post searchBy(Long id) {
        return service.searchBy(id);
    }
    
    public void updateStatusBy(Long id, Status status) {
        this.service.updateStatusBy(id, status);
    }
    
    public void deleteBy(Long id) {
        service.deleteBy(id);
    }
}
