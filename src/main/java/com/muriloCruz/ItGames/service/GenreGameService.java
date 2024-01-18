package com.muriloCruz.ItGames.service;

import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.enums.TypeAssociation;

public interface GenreGameService {

    public GenreGame insert(Long gameId, Long genreId, TypeAssociation typeAssociation);

    public GenreGame update(Long gameId, Long genreId, TypeAssociation typeAssociation);

    public GenreGame searchBy(Long genre, Long game);

    public void deleteBy(Long genreId, Long gameId);
}
