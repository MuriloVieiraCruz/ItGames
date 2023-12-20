package com.muriloCruz.ItGames.controller;

import com.muriloCruz.ItGames.entity.Enterprise;
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

    @PostMapping("/gameId/{gameId}/genreId/{genreId}/type/{type}")
    @Transactional
    public ResponseEntity<?> insert(
            @PathVariable("gameId")
            @NotNull(message = "The gameId is required")
            Long gameId,
            @PathVariable("genreId")
            @NotNull(message = "The genreId is required")
            Long genreId,
            @PathVariable("type")
            @NotNull(message = "The type association is required")
            TypeAssociation typeAssociation) {
        GenreGame genreGameSave = service.insert(gameId, genreId, typeAssociation);
        return ResponseEntity.created(URI.create("genre_game/gameId/" + genreGameSave.getId().getGameId()
                + "/genreId/" + genreGameSave.getId().getGenreId())).build()
                ;
    }

    @PutMapping("/gameId/{gameId}/genreId/{genreId}/type/{type}")
    @Transactional
    public ResponseEntity<?> update(
            @PathVariable("gameId")
            @NotNull(message = "The ID is required")
            Long gameId,
            @PathVariable("genreId")
            @NotNull(message = "The ID is required")
            Long genreId,
            @PathVariable("type")
            @NotNull(message = "The type association is required")
            TypeAssociation typeAssociation) {
        GenreGame genreGameUpdate = service.update(gameId, genreId, typeAssociation);
        return ResponseEntity.ok(converter.toJsonMap(genreGameUpdate));
    }

    @GetMapping("/gameId/{gameId}/genreId/{genreId}/type/{type}")
    public ResponseEntity<?> searchBy(
            @PathVariable("genreId")
            @NotNull(message = "The genre is required")
            Long genreId,
            @PathVariable("gameId")
            @NotNull(message = "The game is required")
            Long gameId) {
        GenreGame genreGameFound = service.searchBy(genreId, gameId);
        return ResponseEntity.ok(converter.toJsonMap(genreGameFound));
    }

    @DeleteMapping("/gameId/{gameId}/genreId/{genreId}")
    @Transactional
    public ResponseEntity<?> deleteBy(
            @PathVariable("genreId")
            @NotNull(message = "The genre is required")
            Long genreId,
            @PathVariable("gameId")
            @NotNull(message = "The game is required")
            Long gameId) {
        service.deleteBy(genreId, gameId);
        return ResponseEntity.ok().build();
    }


}
