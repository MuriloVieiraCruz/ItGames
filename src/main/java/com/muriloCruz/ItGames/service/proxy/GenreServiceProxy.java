package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceProxy {

    @Autowired
    private GenreService service;

    public Genre insert(Genre genre) {
        return service.insert(genre);
    }

    public Genre searchBy(Integer id) {
        return service.searchBy(id);
    }

    public Page<Genre> listBy(String name, Pageable paginacao) {
        return service.listBy(name, paginacao);
    }

    public void updateStatusBy(Integer id, Status status) {
        this.service.updateStatusBy(id, status);
    }

    public Genre excludeBy(Integer id) {
        return service.excludeBy(id);
    }
}
