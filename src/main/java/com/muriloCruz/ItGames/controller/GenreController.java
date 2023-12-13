package com.muriloCruz.ItGames.controller;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.GenreService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/genre")
public class GenreController {

    @Autowired
    private MapConverter converter;

    @Autowired
    private GenreService service;

    @PostMapping
    @Transactional
    public ResponseEntity<?> insert(Genre genre) {
        Preconditions.checkArgument(!genre.isPersisted(),
                "The genre cannot have id in the insert");
        Genre genreSave = service.insert(genre);
        return ResponseEntity.created(URI.create("genre/id/" + genreSave.getId())).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(Genre genre) {
        Preconditions.checkArgument(genre.isPersisted(),
                "The genre must have id in the insert");
        Genre genreUpdate = service.insert(genre);
        return ResponseEntity.ok(converter.toJsonMap(genreUpdate));
    }

    @PatchMapping("/id/{id}/status/{status}")
    @Transactional
    public ResponseEntity<?> updateStatusBy(@PathVariable("id") Integer id, @PathVariable("status") Status status) {
        service.updateStatusBy(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> searchBy(@PathVariable("id") Integer id) {
        Genre genreFound = service.searchBy(id);
        return ResponseEntity.ok(converter.toJsonMap(genreFound));
    }

    @GetMapping
    public ResponseEntity<?> listBy(@RequestParam("name") String name, @RequestParam("page") Optional<Integer> page) {
        Pageable pagination = null;

        if (page .isPresent()) {
            pagination = PageRequest.of(page.get(), 20);
        } else {
            pagination = PageRequest.of(0, 20);
        }

        Page<Genre> genreList = service.listBy(name, pagination);
        return ResponseEntity.ok(converter.toJsonList(genreList));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> excludeBy(@PathVariable("id") Integer id) {
        Genre genreExclude = service.deleteBy(id);
        return ResponseEntity.ok(converter.toJsonMap(genreExclude));
    }
}
