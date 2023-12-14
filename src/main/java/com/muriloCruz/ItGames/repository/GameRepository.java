package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.transaction.Transactional;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query(value =
            "SELECT g "
            + "FROM Game g "
            + "JOIN FETCH g.genres gg "
            + "JOIN FETCH gg.genre ge "
            + "JOIN FETCH g.enterprise "
            + "WHERE g.id = :id")
    public Game searchBy(Long id);
    
    @Query(value =
            "SELECT g "
            + "FROM Game g "
            + "WHERE Upper(g.name) = Upper(:name)")
    public Game searchBy(String name);

    @Query(value =
            "SELECT g "
            + "FROM Game g "
            + "JOIN FETCH g.genres gg "
            + "JOIN FETCH gg.genre ge "
            + "JOIN FETCH g.enterprise "
            + "WHERE UPPER(g.name) LIKE UPPER(%:name%) "
            + "AND (:genreId IS NULL OR g.id = :genreId) "
            + "AND g.status = 'A' "
            + "ORDER BY g.name",
            countQuery = "SELECT g "
                    + "FROM Game g "
                    + "WHERE UPPER(g.name) LIKE UPPER(:name) "
                    + "AND (:genreId IS NULL OR g.id = :genreId) "
                    + "AND g.status = 'A' ")
    public Page<Game> listBy(String name, Optional<Long> genreId, Pageable pagination);

    @Modifying
    @Query(value =
            "UPDATE Game g "
            + "SET g.status = :status "
            + "WHERE g.id = :id")
    public void updateStatusBy(Long id, Status status);

    @Query(value =
            "SELECT Count(g) "
            + "FROM Game g "
            + "WHERE g.enterprise.id = :enterpriseId")
    public int countGamesLinkedToThe(Long enterpriseId);


    @Modifying
    @Query(value = "DELETE FROM Game g "
            + "WHERE g.id = :gameId")
    public void deleteBy(Long gameId);
}
