package com.muriloCruz.ItGames.controller;

import com.google.common.base.Optional;
import com.muriloCruz.ItGames.dto.enterprise.EnterpriseRequest;
import com.muriloCruz.ItGames.dto.enterprise.EnterpriseSaved;
import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.impl.EnterpriseService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private MapConverter converter;

    @Autowired
    private EnterpriseService service;

    @PostMapping
    @Transactional
    @Valid
    public ResponseEntity<?> insert(@RequestBody EnterpriseRequest enterprise) {
        Enterprise enterpriseSave = service.insert(enterprise);
        return ResponseEntity.created(URI.create("/enterprise/id/" + enterpriseSave.getId())).build();
    }

    @PutMapping
    @Transactional
    @Valid
    public ResponseEntity<?> update(@RequestBody EnterpriseSaved enterprise) {
        Enterprise enterpriseUpdate = service.update(enterprise);
        return ResponseEntity.ok(converter.toJsonMap(enterpriseUpdate));
    }

    @PatchMapping("/id/{id}/status/{status}")
    @Transactional
    @Valid
    public ResponseEntity<?> updateStatusBy(@PathVariable("id")  Integer id, @PathVariable("status") Status status) {
        this.service.updateStatusBy(id, status);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{id}")
    @Valid
    public ResponseEntity<?> searchBy(
            @PathVariable("id")
            @NotNull(message = "The ID is required")
            Integer id) {
        Enterprise enterpriseFound = service.searchBy(id);
        return ResponseEntity.ok(converter.toJsonMap(enterpriseFound));
    }

    @GetMapping
    @Valid
    public ResponseEntity<?> listBy(
            @RequestParam("name")
            @NotBlank(message = "The name is required")
            String name,
            @RequestParam("page")Optional<Integer> page) {
        Pageable pagination = null;
        if (page.isPresent()) {
            pagination = PageRequest.of(page.get(), 20);
        } else {
            pagination = PageRequest.of(0, 20);
        }

        Page<Enterprise> enterprises = service.listBy(name, pagination);
        return ResponseEntity.ok(converter.toJsonList(enterprises));
    }

    @DeleteMapping("id/{id}")
    @Transactional
    @Valid
    public ResponseEntity<?> excludeBy(
            @PathVariable("id")
            @NotNull(message = "The ID is required")
            Integer id) {
        Enterprise enterpriseExclude = service.excludeBy(id);

        return ResponseEntity.ok(converter.toJsonMap(enterpriseExclude));
    }
}
