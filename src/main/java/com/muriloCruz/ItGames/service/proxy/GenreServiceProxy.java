package com.muriloCruz.ItGames.service.proxy;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.genre.GenreRequest;
import com.muriloCruz.ItGames.dto.genre.GenreSaved;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.GenreService;
import com.muriloCruz.ItGames.service.impl.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceProxy implements GenreService {

    @Autowired
    @Qualifier("genreServiceImpl")
    private GenreService service;

    @Override
    public Genre insert(GenreRequest genre) {
        return service.insert(genre);
    }

    @Override
    public Genre update(GenreSaved genre) {
        return service.update(genre);
    }

    @Override
    public Genre searchBy(Long id) {
        return service.searchBy(id);
    }

    @Override
    public Page<Genre> listBy(String name, Pageable paginacao) {
        return service.listBy(name, paginacao);
    }

    @Override
    public void updateStatusBy(Long id, Status status) {
        this.service.updateStatusBy(id, status);
    }

    @Override
    public void deleteBy(Long id) {
        service.deleteBy(id);
    }
}
