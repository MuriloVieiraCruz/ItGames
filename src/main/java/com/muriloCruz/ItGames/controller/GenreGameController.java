package com.muriloCruz.ItGames.controller;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.enums.TypeAssociation;
import com.muriloCruz.ItGames.service.GenreGameService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/genre_game")
public class GenreGameController {

    @Autowired
    private GenreGameService service;

    @Autowired
    private MapConverter converter;

    @PostMapping
    @Transactional
    @Valid
    public ResponseEntity<?> insert(
            @RequestBody
            @NotNull(message = "The ID is required")
            Long gameId,
            @RequestBody
            @NotNull(message = "The ID is required")
            Long genreId,
            @RequestBody
            @NotNull(message = "The type association is required")
            TypeAssociation typeAssociation) {
        GenreGame genreGameSave = service.insert(gameId, genreId, typeAssociation);
        return ResponseEntity.created(URI.create("genre_game/id/" + genreGameSave.getId())).build();
    }

    @PutMapping
    @Transactional
    @Valid
    public ResponseEntity<?> update(
            @RequestBody
            @NotNull(message = "The ID is required")
            Long gameId,
            @RequestBody
            @NotNull(message = "The ID is required")
            Long genreId,
            @RequestBody
            @NotNull(message = "The type association is required")
            TypeAssociation typeAssociation) {
        GenreGame genreGameUpdate = service.update(gameId, genreId, typeAssociation);
        return ResponseEntity.ok(converter.toJsonMap(genreGameUpdate));
    }

    @GetMapping("/id/{id}")
    @Valid
    public ResponseEntity<?> searchBy(
            @RequestParam("genre")
            @NotNull(message = "The genre is required")
            Long genreId,
            @RequestParam("game")
            @NotNull(message = "The game is required")
            Long gameId) {
        GenreGame genreGameFound = service.searchBy(genreId, gameId);
        return ResponseEntity.ok(converter.toJsonMap(genreGameFound));
    }
}
