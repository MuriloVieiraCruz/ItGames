package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.Service;
import com.muriloCruz.ItGames.entity.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

    @Query(value =
            "SELECT s "
            + "FROM Service s "
            + "JOIN FETCH s.user u "
            + "JOIN FETCH s.game g "
            + "WHERE s.id = :id") 
    public Service searchBy(Integer id);

    @Query(value =
            "SELECT s "
            + "FROM Service s "
            + "WHERE s.game = :game")
    public Service searchBy(Game game);
    
    @Query(value =
            "SELECT s "
            + "FROM Service s "
            + "JOIN FETCH s.game "
            + "JOIN FETCH s.user u "
            + "WHERE (:price IS NULL OR s.price = :price) "
            + "AND (:game IS NULL OR s.game = :game ) "
            + "AND s.status = 'A' "
            + "ORDER BY s.price",
            countQuery = "SELECT Count(s) "
                    + "FROM Service s "
                    + "WHERE (:price IS NULL OR s.price = :price) "
                    + "AND (:game IS NULL OR s.game = :game ) ")
    public Page<Service> listBy(BigDecimal price, Game game, Pageable pagination);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE Service s "
            + "SET s.status = :status "
            + "WHERE s.id = :id")
    public void updateStatusBy(Integer id, Status status);

    @Query(value = "SELECT Count(s) "
            + "FROM Service s "
            + "WHERE s.game.id = :gameId")
    public int countBy(int gameId);
}
