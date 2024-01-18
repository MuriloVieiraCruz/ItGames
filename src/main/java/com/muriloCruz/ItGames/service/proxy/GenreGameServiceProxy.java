package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.enums.TypeAssociation;
import com.muriloCruz.ItGames.service.GenreGameService;
import com.muriloCruz.ItGames.service.impl.GenreGameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GenreGameServiceProxy implements GenreGameService {

    @Autowired
    @Qualifier("genreGameServiceImpl")
    private GenreGameServiceImpl service;

    @Override
    public GenreGame insert(Long gameId, Long genreId, TypeAssociation typeAssociation) {
        return service.insert(gameId, genreId, typeAssociation);
    }

    @Override
    public GenreGame update(Long gameId, Long genreId, TypeAssociation typeAssociation) {
        return service.update(gameId, genreId, typeAssociation);
    }

    @Override
    public GenreGame searchBy(Long genre, Long game) {
        return service.searchBy(genre, game);
    }

    @Override
    public void deleteBy(Long genreId, Long gameId) {
        service.deleteBy(genreId, gameId);
    }
}
