package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.enums.TypeAssociation;
import com.muriloCruz.ItGames.service.GenreGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GenreGameServiceProxy implements GenreGameService{

    @Autowired
    @Qualifier("genreGameServiceImpl")
    private GenreGameService service;


    @Override
    public GenreGame insert(Integer gameId, Integer genreId, TypeAssociation typeAssociation) {
        return service.insert(gameId, genreId, typeAssociation);
    }

    @Override
    public GenreGame update(Integer gameId, Integer genreId, TypeAssociation typeAssociation) {
        return service.update(gameId, genreId, typeAssociation);
    }

    @Override
    public GenreGame searchBy(Genre genre, Game game) {
        return service.searchBy(genre, game);
    }
}
