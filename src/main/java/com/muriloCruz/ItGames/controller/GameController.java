package com.muriloCruz.ItGames.controller;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.GameRequestDto;
import com.muriloCruz.ItGames.dto.GameSavedDto;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.GameService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("gameServiceImpl")
    private GameService service;

    @PostMapping
    @Transactional
    public ResponseEntity<?> insert(@RequestBody GameRequestDto gameRequestDto) {
        Game gameSave = service.insert(gameRequestDto);
        return ResponseEntity.created(URI.create("game/id/" + gameSave.getId())).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody GameSavedDto gameSavedDto) {
        Game gameUpdate = service.update(gameSavedDto);
        return ResponseEntity.ok(convert(gameUpdate));
    }

    @PatchMapping("/id/{id}/status/{status}")
    @Transactional
    public ResponseEntity<?> updateStatusBy(@PathVariable("id") Integer id, @PathVariable("status") Status status) {
        service.updateStatusBy(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> searchBy(@PathVariable("id") Integer id) {
        Game gameFound = service.searchBy(id);
        return ResponseEntity.ok(convert(gameFound));
    }

    @GetMapping
    public ResponseEntity<?> listBy(@RequestParam("name") String name, @RequestParam("genreId") Integer genreId ,@RequestParam("page") Optional<Integer> page) {
        Pageable pagination = null;

        if (page .isPresent()) {
            pagination = PageRequest.of(page.get(), 20);
        } else {
            pagination = PageRequest.of(0, 20);
        }

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
    public ResponseEntity<?> excludeBy(@PathVariable("id") Integer id) {
        Game gameExclude = service.excludeBy(id);
        return ResponseEntity.ok(convert(gameExclude));
    }

    private Map<String, Object> convert(Game game) {
        Map<String, Object> gameMap = new HashMap<String, Object>();
        gameMap.put("id", game.getId());
        gameMap.put("name", game.getName());
        gameMap.put("status", game.getStatus());
        gameMap.put("releaseDate", game.getReleaseDate());

        Map<String, Object> enterpriseMap = new HashMap<String, Object>();
        enterpriseMap.put("id", game.getEnterprise().getId());
        enterpriseMap.put("name", game.getEnterprise().getName());

        gameMap.put("enterprise", enterpriseMap);

        List<Map<String, Object>> genresMap = new ArrayList<Map<String, Object>>();
        for (GenreGame genreGame : game.getGenres()) {
            Map<String, Object> genreMap = new HashMap<String, Object>();
            genreMap.put("id", genreGame.getGenre().getId());
            genreMap.put("id", genreGame.getGenre().getName());

            genresMap.add(genreMap);
        }

        gameMap.put("genres", genresMap);

        return gameMap;
    }
}
