package com.muriloCruz.ItGames.controller;

import com.muriloCruz.ItGames.dto.enterprise.EnterpriseRequest;
import com.muriloCruz.ItGames.dto.enterprise.EnterpriseSaved;
import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.EnterpriseService;
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
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private MapConverter converter;

    @Autowired
    private EnterpriseService service;

    @PostMapping
    @Transactional
    public ResponseEntity<?> insert(
            @RequestBody
            @Valid
            EnterpriseRequest enterprise) {
        Enterprise enterpriseSave = service.insert(enterprise);
        return ResponseEntity.created(URI.create("/enterprise/id/" + enterpriseSave.getId())).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(
            @RequestBody
            @Valid
            EnterpriseSaved enterprise) {
        Enterprise enterpriseUpdate = service.update(enterprise);
        return ResponseEntity.ok(converter.toJsonMap(enterpriseUpdate));
    }

    @PatchMapping("/id/{id}/status/{status}")
    @Transactional
    public ResponseEntity<?> updateStatusBy(
            @PathVariable("id")
            @NotNull(message = "The ID is required")
            Long id,
            @PathVariable("status")
            @NotNull(message = "The status is required")
            Status status) {
        this.service.updateStatusBy(id, status);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> searchBy(
            @PathVariable("id")
            @NotNull(message = "The ID is required")
            Long id) {
        Enterprise enterpriseFound = service.searchBy(id);
        return ResponseEntity.ok(converter.toJsonMap(enterpriseFound));
    }

    @GetMapping
    public ResponseEntity<?> listBy(
            @RequestParam("name")
            @NotBlank(message = "The name is required")
            String name,
            @RequestParam("page")
            Optional<Integer> page) {

        Pageable pagination = null;

        pagination = page.map(integer -> PageRequest.of(integer, 20))
                .orElseGet(() -> PageRequest.of(0, 20));

        Page<Enterprise> enterprises = service.listBy(name, pagination);
        return ResponseEntity.ok(converter.toJsonList(enterprises));
    }

    @DeleteMapping("id/{id}")
    @Transactional
    public ResponseEntity<?> deleteBy(
            @PathVariable("id")
            @NotNull(message = "The ID is required")
            Long id) {
        Enterprise enterpriseDeleted = service.deleteBy(id);

        return ResponseEntity.ok(converter.toJsonMap(enterpriseDeleted));
    }
}
