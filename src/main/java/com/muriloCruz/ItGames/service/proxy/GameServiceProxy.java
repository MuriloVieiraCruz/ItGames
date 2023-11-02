package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.dto.GameRequestDto;
import com.muriloCruz.ItGames.dto.GameSavedDto;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GameServiceProxy implements GameService {

    @Autowired
    @Qualifier("gameServiceImpl")
    private GameService service;

    @Override
    public Game insert(GameRequestDto gameRequestDto) {
        return service.insert(gameRequestDto);
    }

    @Override
    public Game update(GameSavedDto gameSavedDto) {
        return service.update(gameSavedDto);
    }

    @Override
    public Page<Game> listBy(String name, Integer genreId, Pageable pagination) {
        return service.listBy(name, genreId, pagination);
    }

    @Override
    public Game searchBy(Integer id) {
        return service.searchBy(id);
    }

    @Override
    public void updateStatusBy(Integer id, Status status) {
        this.service.updateStatusBy(id, status);
    }

    @Override
    public Game excludeBy(Integer id) {
        return service.excludeBy(id);
    }
}
