package com.muriloCruz.ItGames.service;

import com.muriloCruz.ItGames.dto.game.GameRequestDto;
import com.muriloCruz.ItGames.dto.game.GameSavedDto;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GameService {

    public Game insert(GameRequestDto gameRequestDto);

    public Game update(GameSavedDto gameSavedDto);

    public void updateStatusBy(Long id, Status status);

    public Game searchBy(Long id);
    public Page<Game> listBy(String name, Optional<Long> genreId, Pageable pagination);

    public Game deleteBy(Long id);
}
