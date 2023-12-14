package com.muriloCruz.ItGames.controller;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.genre.GenreRequest;
import com.muriloCruz.ItGames.dto.genre.GenreSaved;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.GenreService;
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
    @Valid
    public ResponseEntity<?> insert(
            @RequestBody
            GenreRequest genreRequest) {
        Genre genreSave = service.insert(genreRequest);
        return ResponseEntity.created(URI.create("genre/id/" + genreSave.getId())).build();
    }

    @PutMapping
    @Transactional
    @Valid
    public ResponseEntity<?> update(
            @RequestBody
            GenreSaved genreSaved) {
        Genre genreUpdate = service.update(genreSaved);
        return ResponseEntity.ok(converter.toJsonMap(genreUpdate));
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
    @Valid
    public ResponseEntity<?> searchBy(
            @PathVariable("id")
            @NotNull(message = "The ID is required")
            Long id) {
        Genre genreFound = service.searchBy(id);
        return ResponseEntity.ok(converter.toJsonMap(genreFound));
    }

    @GetMapping
    @Valid
    public ResponseEntity<?> listBy(
            @RequestParam("name")
            @NotBlank(message = "The name is required")
            String name,
            @RequestParam("page")
            Optional<Integer> page) {
        Pageable pagination = null;

        pagination = page.map(integer -> PageRequest.of(integer, 20))
                .orElseGet(() -> PageRequest.of(0, 20));

        Page<Genre> genreList = service.listBy(name, pagination);
        return ResponseEntity.ok(converter.toJsonList(genreList));
    }

    @DeleteMapping("/id/{id}")
    @Transactional
    @Valid
    public ResponseEntity<?> excludeBy(
            @PathVariable("id")
            @NotNull(message = "The ID is required")
            Long id) {
        Genre genreExclude = service.deleteBy(id);
        return ResponseEntity.ok(converter.toJsonMap(genreExclude));
    }
}
