package com.muriloCruz.ItGames.controller;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.EnterpriseService;
import jakarta.transaction.Transactional;
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
    @Qualifier("enterpriseServiceProxy")
    private EnterpriseService service;

    @PostMapping
    @Transactional
    public ResponseEntity<?> insert(@RequestBody Enterprise enterprise) {
        Preconditions.checkArgument(!enterprise.isPersisted(),
                "The enterprise cannot have id in the insert");
        Enterprise enterpriseSave = service.insert(enterprise);
        return ResponseEntity.created(URI.create("/enterprise/id/" + enterpriseSave.getId())).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody Enterprise enterprise) {
        Preconditions.checkArgument(enterprise.isPersisted(),
                "The enterprise must have id in the insert");
        Enterprise enterpriseUpdate = service.insert(enterprise);
        return ResponseEntity.ok(converter.toJsonMap(enterpriseUpdate));
    }

    @PatchMapping("/id/{id}/status/{status}")
    @Transactional
    public ResponseEntity<?> updateStatusBy(@PathVariable("id") Integer id, @PathVariable("status") Status status) {
        this.service.updateStatusBy(id, status);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> searchBy(@PathVariable("id") Integer id) {
        Enterprise enterpriseFound = service.searchBy(id);
        return ResponseEntity.ok(converter.toJsonMap(enterpriseFound));
    }

    @GetMapping
    public ResponseEntity<?> listBy(
            @RequestParam("name") String name,
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
    public ResponseEntity<?> excludeBy(@PathVariable("id") Integer id) {
        Enterprise enterpriseExclude = service.excludeBy(id);

        return ResponseEntity.ok(converter.toJsonMap(enterpriseExclude));
    }
}
