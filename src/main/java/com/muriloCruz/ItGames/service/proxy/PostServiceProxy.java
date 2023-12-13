package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.dto.post.PostRequestDto;
import com.muriloCruz.ItGames.dto.post.PostSavedDto;
import com.muriloCruz.ItGames.entity.Post;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
    
    public Page<Post> listBy(BigDecimal price, Integer gameId, Pageable page) {
        return service.listBy(price, gameId, page);
    }
    
    public Post searchBy(Integer id) {
        return service.searchBy(id);
    }
    
    public void updateStatusBy(Integer id, Status status) {
        this.service.updateStatusBy(id, status);
    }
    
    public Post excludeBy(Integer id) {
        return service.excludeBy(id);
    }
}
