package com.muriloCruz.ItGames.controller;

import com.muriloCruz.ItGames.dto.game.GameRequestDto;
import com.muriloCruz.ItGames.dto.game.GameSavedDto;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.GameService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService service;

    @Autowired
    private MapConverter mapConverter;

    @PostMapping
    @Transactional
    @Valid
    public ResponseEntity<?> insert(
            @RequestBody
            GameRequestDto gameRequestDto) {
        Game gameSave = service.insert(gameRequestDto);
        return ResponseEntity.created(URI.create("game/id/" + gameSave.getId())).build();
    }

    @PutMapping
    @Transactional
    @Valid
    public ResponseEntity<?> update(
            @RequestBody
            GameSavedDto gameSavedDto) {
        Game gameUpdate = service.update(gameSavedDto);
        return ResponseEntity.ok(convert(gameUpdate));
    }

    @PatchMapping("/id/{id}/status/{status}")
    @Transactional
    @Valid
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
        Game gameFound = service.searchBy(id);
        return ResponseEntity.ok(convert(gameFound));
    }

    @GetMapping
    @Valid
    public ResponseEntity<?> listBy(
            @RequestParam("name")
            @NotBlank(message = "The name is required")
            String name,
            @RequestParam("genreId")
            Optional<Long> genreId ,
            @RequestParam("page")
            Optional<Integer> page) {

        Pageable pagination = null;

        pagination = page.map(integer -> PageRequest.of(integer, 20))
                .orElseGet(() -> PageRequest.of(0, 20));

        Page<Game> pages = service.listBy(name, genreId, pagination);

        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("currentPage", pages.getNumber());
        pageMap.put("totalItens", pages.getTotalElements());
        pageMap.put("totalPages", pages.getTotalPages());

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Game game : pages.getContent()) {
            list.add(convert(game));
        }

        pageMap.put("list", list);
        return ResponseEntity.ok(pageMap);
    }

    @DeleteMapping("/id/{id}")
    @Transactional
    @Valid
    public ResponseEntity<?> deleteBy(
            @PathVariable("id")
            @NotNull(message = "The ID is required")
            Long id) {
        Game gameExclude = service.deleteBy(id);
        return ResponseEntity.ok(mapConverter.toJsonMap(gameExclude));
    }

    private Map<String, Object> convert(Game game) {
        Map<String, Object> gameMap = new HashMap<String, Object>();
        gameMap.put("id", game.getId());
        gameMap.put("name", game.getName());
        gameMap.put("imageUrl", game.getImageUrl());
        gameMap.put("releaseDate", game.getReleaseDate());

        Map<String, Object> enterpriseMap = new HashMap<String, Object>();
        enterpriseMap.put("id", game.getEnterprise().getId());
        enterpriseMap.put("name", game.getEnterprise().getName());

        gameMap.put("enterprise", enterpriseMap);

        List<Map<String, Object>> genresMap = new ArrayList<Map<String, Object>>();
        for (GenreGame genreGame : game.getGenres()) {
            Map<String, Object> genreMap = new HashMap<String, Object>();
            genreMap.put("id", genreGame.getGenre().getId());
            genreMap.put("name", genreGame.getGenre().getName());

            genresMap.add(genreMap);
        }

        gameMap.put("genres", genresMap);

        return gameMap;
    }
}
