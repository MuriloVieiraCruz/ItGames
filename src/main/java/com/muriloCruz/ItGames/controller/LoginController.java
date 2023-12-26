package com.muriloCruz.ItGames.controller;

import com.muriloCruz.ItGames.dto.user.UserRequestDto;
import com.muriloCruz.ItGames.dto.user.UserSavedDto;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService service;

    @Autowired
    private MapConverter converter;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> registerUser(
            @RequestBody
            @Valid
            UserRequestDto userRequestDto) {
        User userSave = service.insert(userRequestDto);
        return ResponseEntity.created(URI.create("/user/id/" + userSave.getId())).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(
            @RequestBody
            @Valid
            UserSavedDto userSavedDto) {
        User userUpdate = service.update(userSavedDto);
        return ResponseEntity.ok(converter.toJsonMap(userUpdate));
    }

    @PatchMapping("/id/{id}/status/{status}")
    @Transactional
    public ResponseEntity<?> updateStatusBy(
            @PathVariable("id")
            Long id,
            @PathVariable("status")
            Status status) {
        service.updateStatusBy(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> searchBy(@PathVariable("id") Long id) {
        User userFound = service.searchBy(id);
        return ResponseEntity.ok(convert(userFound));
    }

    @GetMapping
    public ResponseEntity<?> listBy(
            @NotBlank(message = "The name is required")
            @RequestParam("name") String name,
            @RequestParam("page") Optional<Integer> page) {
        Pageable pagination = null;

        pagination = page.map(integer -> PageRequest.of(integer, 20))
                .orElseGet(() -> PageRequest.of(0, 20));

        Page<User> users = service.listBy(name, pagination);

        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("currentPage", users.getNumber());
        pageMap.put("totalItens", users.getTotalElements());
        pageMap.put("totalPages", users.getTotalPages());

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (User user : users.getContent()) {
            list.add(convert(user));
        }

        pageMap.put("list", list);
        return ResponseEntity.ok(pageMap);
    }

    private Map<String, Object> convert(User user) {
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("id", user.getId());
        userMap.put("name", user.getName());
        userMap.put("email", user.getEmail());
        userMap.put("cpf", user.getCpf());
        userMap.put("birthDate", user.getBirthDate());

        List<Map<String, Object>> postsMap = new ArrayList<Map<String, Object>>();

        user.getPosts().forEach((post) -> {
            Map<String, Object> postMap = new HashMap<String, Object>();
            postMap.put("id", post.getId());
            postMap.put("postDate", post.getPostDate());
            postMap.put("availability", post.getAvailability());
            postsMap.add(postMap);
        });

        userMap.put("posts", postsMap);
        return userMap;
    }
}
