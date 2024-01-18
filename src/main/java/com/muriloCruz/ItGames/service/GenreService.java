package com.muriloCruz.ItGames.service;

import com.muriloCruz.ItGames.dto.genre.GenreRequest;
import com.muriloCruz.ItGames.dto.genre.GenreSaved;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreService {

    public Genre insert(GenreRequest genre);

    public Genre update(GenreSaved genre);

    public Genre searchBy(Long id);

    public Page<Genre> listBy(String name, Pageable paginacao);

    public void updateStatusBy(Long id, Status status);

    public void deleteBy(Long id);
}
