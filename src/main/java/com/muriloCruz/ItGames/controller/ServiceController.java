package com.muriloCruz.ItGames.controller;

import com.muriloCruz.ItGames.dto.ServiceRequestDto;
import com.muriloCruz.ItGames.dto.ServiceSavedDto;
import com.muriloCruz.ItGames.entity.Service;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    @Qualifier("serviceServiceImpl")
    private ServiceService service;

    @Autowired
    private MapConverter mapConverter;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ServiceRequestDto serviceRequestDto) {
        Service serviceSave = service.insert(serviceRequestDto);
        return ResponseEntity.created(URI.create("/service/id/" + serviceSave.getId())).build();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ServiceSavedDto serviceSavedDto) {
        Service serviceUpdate = service.update(serviceSavedDto);
        return ResponseEntity.ok(convert(serviceUpdate));
    }

    @PatchMapping("/id/{id}/status/{status}")
    public ResponseEntity<?> updateStatusBy(@PathVariable("id") Integer id, @PathVariable("status")Status status) {
        service.updateStatusBy(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> searchBy(@PathVariable("id") Integer id) {
        Service serviceFound = service.searchBy(id);
        return ResponseEntity.ok(convert(serviceFound));
    }

    @GetMapping
    public ResponseEntity<?> listBy(@RequestParam("name") BigDecimal price, @RequestParam("gameId") Integer gameId , @RequestParam("page") Optional<Integer> page) {
        Pageable pagination = null;

        if (page .isPresent()) {
            pagination = PageRequest.of(page.get(), 20);
        } else {
            pagination = PageRequest.of(0, 20);
        }

        Page<Service> pages = service.listBy(price, gameId, pagination);

        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("currentPage", pages.getNumber());
        pageMap.put("totalItens", pages.getTotalElements());
        pageMap.put("totalPages", pages.getTotalPages());

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Service service : pages.getContent()) {
            list.add(convert(service));
        }

        pageMap.put("list", list);
        return ResponseEntity.ok(pageMap);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> excludeBy(@PathVariable("id") Integer id) {
        Service serviceExclude = service.excludeBy(id);
        return ResponseEntity.ok(mapConverter.toJsonMap(serviceExclude));
    }

    private Map<String, Object> convert(Service service) {
        Map<String, Object> serviceMap = new HashMap<String, Object>();
        serviceMap.put("id", service.getId());
        serviceMap.put("description", service.getDescription());
        serviceMap.put("price", service.getPrice());
        serviceMap.put("postDate", service.getPostDate());
        serviceMap.put("imageUrl", service.getImageUrl());
        serviceMap.put("availability", service.getAvailability());

        Map<String, Object> gameMap = new HashMap<String, Object>();
        gameMap.put("id", service.getGame().getId());
        gameMap.put("name", service.getGame().getName());
        gameMap.put("imageUrl", service.getGame().getImageUrl());
        gameMap.put("releaseDate", service.getGame().getReleaseDate());

        serviceMap.put("game", gameMap);

        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("id", service.getUser().getId());
        userMap.put("name", service.getUser().getName());
        userMap.put("rating", service.getUser().getRating());

        serviceMap.put("user", userMap);
        return serviceMap;
    }

}
