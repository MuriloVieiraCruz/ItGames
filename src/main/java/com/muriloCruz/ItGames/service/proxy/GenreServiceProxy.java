package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.dto.genre.GenreRequest;
import com.muriloCruz.ItGames.dto.genre.GenreSaved;
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

    public Genre insert(GenreRequest genre) {
        return service.insert(genre);
    }

    public Genre update(GenreSaved genre) {
        return service.update(genre);
    }

    public Genre searchBy(Long id) {
        return service.searchBy(id);
    }

    public Page<Genre> listBy(String name, Pageable paginacao) {
        return service.listBy(name, paginacao);
    }

    public void updateStatusBy(Long id, Status status) {
        this.service.updateStatusBy(id, status);
    }

    public Genre excludeBy(Long id) {
        return service.deleteBy(id);
    }
}
