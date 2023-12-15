package com.muriloCruz.ItGames.controller;

import com.muriloCruz.ItGames.dto.user.UserRequestDto;
import com.muriloCruz.ItGames.dto.user.UserSavedDto;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private MapConverter converter;

    @PostMapping
    @Transactional
    public ResponseEntity<?> insert(@RequestBody UserRequestDto userRequestDto) {
        User userSave = service.insert(userRequestDto);
        return ResponseEntity.created(URI.create("/user/id/" + userSave.getId())).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody UserSavedDto userSavedDto) {
        User userUpdate = service.update(userSavedDto);
        return ResponseEntity.ok(converter.toJsonMap(userUpdate));
    }

    @PatchMapping("/id/{id}/status/{status}")
    @Transactional
    public ResponseEntity<?> updateStatusBy(@PathVariable("id") Long id, @PathVariable("status") Status status) {
        service.updateStatusBy(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> searchBy(@PathVariable("id") Long id) {
        User userFound = service.searchBy(id);
        return ResponseEntity.ok(converter.toJsonMap(userFound));
    }

    @GetMapping
    public ResponseEntity<?> listBy(@RequestParam("login") String login, @RequestParam("page") Optional<Integer> page) {
        Pageable pagination = null;

        pagination = page.map(integer -> PageRequest.of(integer, 20))
                .orElseGet(() -> PageRequest.of(0, 20));

        Page<User> userList = service.listBy(login, pagination);
        return ResponseEntity.ok(converter.toJsonList(userList));
    }
}
