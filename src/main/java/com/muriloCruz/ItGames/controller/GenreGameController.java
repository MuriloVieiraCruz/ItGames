package com.muriloCruz.ItGames.controller;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.enums.TypeAssociation;
import com.muriloCruz.ItGames.service.impl.GenreGameService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public ResponseEntity<?> insert(
            @RequestBody Integer gameId,
            @RequestBody Integer genreId,
            @RequestBody TypeAssociation typeAssociation) {
        GenreGame genreGameSave = service.insert(gameId, genreId, typeAssociation);
        return ResponseEntity.created(URI.create("genre_game/id/" + genreGameSave.getId())).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(
            @RequestBody Integer gameId,
            @RequestBody Integer genreId,
            @RequestBody TypeAssociation typeAssociation) {
        GenreGame genreGameUpdate = service.update(gameId, genreId, typeAssociation);
        return ResponseEntity.ok(converter.toJsonMap(genreGameUpdate));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> searchBy(@RequestParam("genre") Genre genre, @RequestParam("game") Game game) {
        GenreGame genreGameFound = service.searchBy(genre, game);
        return ResponseEntity.ok(converter.toJsonMap(genreGameFound));
    }
}
