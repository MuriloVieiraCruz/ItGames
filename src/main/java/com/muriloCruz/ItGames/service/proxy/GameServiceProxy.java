package com.muriloCruz.ItGames.service.proxy;

import com.muriloCruz.ItGames.dto.game.GameRequestDto;
import com.muriloCruz.ItGames.dto.game.GameSavedDto;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void updateStatusBy(Long id, Status status) {
        this.service.updateStatusBy(id, status);
    }

    public Game searchBy(Long id) {
        return service.searchBy(id);
    }
    public Page<Game> listBy(String name, Optional<Long> genreId, Pageable pagination) {
        return service.listBy(name, genreId, pagination);
    }

    public Game deleteBy(Long id) {
        return service.deleteBy(id);
    }
}
