package com.muriloCruz.ItGames.controller;

import com.muriloCruz.ItGames.dto.post.PostRequestDto;
import com.muriloCruz.ItGames.dto.post.PostSavedDto;
import com.muriloCruz.ItGames.entity.Post;
import com.muriloCruz.ItGames.entity.enums.Availability;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.PostService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/service")
public class PostController {

    @Autowired
    private PostService service;

    @Autowired
    private MapConverter mapConverter;

    @PostMapping
    @Transactional
    public ResponseEntity<?> insert(
            @RequestBody
            @NotNull(message = "The post is required")
            @Valid
            PostRequestDto postRequestDto) {
        Post postSave = service.insert(postRequestDto);
        return ResponseEntity.created(URI.create("/service/id/" + postSave.getId())).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(
            @RequestBody
            @NotNull(message = "The post is required")
            @Valid
            PostSavedDto postSavedDto) {
        Post postUpdate = service.update(postSavedDto);
        return ResponseEntity.ok(convert(postUpdate));
    }

    @PatchMapping("/id/{id}/status/{status}")
    public ResponseEntity<?> updateStatusBy(
            @PathVariable("id")
            @NotNull(message = "The ID is required")
            Long id,
            @PathVariable("status")
            @NotNull(message = "The status is required")
            Status status) {
        service.updateStatusBy(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> searchBy(
            @PathVariable("id")
            @NotNull(message = "The ID is required")
            Long id) {
        Post postFound = service.searchBy(id);
        return ResponseEntity.ok(convert(postFound));
    }

    @GetMapping
    public ResponseEntity<?> listBy(
            @RequestParam("price")
            Optional<BigDecimal> price,
            @RequestParam("availability")
            Optional<Availability> availability,
            @RequestParam("postDate")
            Optional<LocalDate> postDate,
            @RequestParam("gameId")
            Optional<Long> gameId ,
            @RequestParam("userId")
            Optional<Long> userId,
            @RequestParam("page")
            Optional<Integer> page) {
        Pageable pagination = null;

        pagination = page.map(integer -> PageRequest.of(integer, 20))
                .orElseGet(() -> PageRequest.of(0, 20));

        Page<Post> pages = service.listBy(price, availability, postDate, gameId, userId, pagination);

        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("currentPage", pages.getNumber());
        pageMap.put("totalItens", pages.getTotalElements());
        pageMap.put("totalPages", pages.getTotalPages());

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Post post : pages.getContent()) {
            list.add(convert(post));
        }

        pageMap.put("list", list);
        return ResponseEntity.ok(pageMap);
    }

    @DeleteMapping("/id/{id}")
    @Transactional
    public ResponseEntity<?> deleteBy(@PathVariable("id") Long id) {
        Post postExclude = service.deleteBy(id);
        return ResponseEntity.ok(mapConverter.toJsonMap(postExclude));
    }

    private Map<String, Object> convert(Post post) {
        Map<String, Object> serviceMap = new HashMap<String, Object>();
        serviceMap.put("id", post.getId());
        serviceMap.put("description", post.getDescription());
        serviceMap.put("price", post.getPrice());
        serviceMap.put("postDate", post.getPostDate());
        //serviceMap.put("imageUrl", post.getImageUrl());
        serviceMap.put("availability", post.getAvailability());

        Map<String, Object> gameMap = new HashMap<String, Object>();
        gameMap.put("id", post.getGame().getId());
        gameMap.put("name", post.getGame().getName());
        //gameMap.put("imageUrl", post.getGame().getImageUrl());
        gameMap.put("releaseDate", post.getGame().getReleaseDate());

        serviceMap.put("game", gameMap);

        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("id", post.getUser().getId());
        userMap.put("name", post.getUser().getName());
        userMap.put("rating", post.getUser().getRating());

        serviceMap.put("user", userMap);
        return serviceMap;
    }

}
