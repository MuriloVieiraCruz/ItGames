package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.dto.game.GameRequestDto;
import com.muriloCruz.ItGames.dto.game.GameSavedDto;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.impl.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GameServiceProxy {

    @Autowired
    private GameService service;

    public Game insert(GameRequestDto gameRequestDto) {
        return service.insert(gameRequestDto);
    }

    public Game update(GameSavedDto gameSavedDto) {
        return service.update(gameSavedDto);
    }

    public Page<Game> listBy(String name, Integer genreId, Pageable pagination) {
        return service.listBy(name, genreId, pagination);
    }

    public Game searchBy(Integer id) {
        return service.searchBy(id);
    }

    public void updateStatusBy(Integer id, Status status) {
        this.service.updateStatusBy(id, status);
    }

    public Game excludeBy(Integer id) {
        return service.excludeBy(id);
    }
}
