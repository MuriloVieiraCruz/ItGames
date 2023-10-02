package com.muriloCruz.ItGames.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muriloCruz.ItGames.entity.Genero;
import com.muriloCruz.ItGames.entity.Jogo;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.transaction.Transactional;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Integer> {

    @Query(value =
            "SELECT j "
            + "FROM Jogo j "
            + "JOIN FETCH j.generos gj "
            + "JOIN FETCH gj.genero g "
            + "JOIN FETCH j.empresa e "
            + "WHERE j.id = :id")
    public Jogo buscarPor(Integer id);
    
    @Query(value =
            "SELECT j "
            + "FROM Jogo j "
            + "WHERE j.nome = :nome")
    public Jogo buscarPor(String nome);

    @Query(value =
            "SELECT j "
            + "FROM Jogo j "
            + "JOIN FETCH j.generos gj "
            + "JOIN FETCH gj.genero g "
            + "WHERE UPPER(j.nome) LIKE UPPER(:nome) "
            + "AND (:genero IS NULL OR g = :genero) "
            + "AND j.status = 'A'",
            countQuery = "SELECT j "
                    + "FROM Jogo j "
                    + "JOIN FETCH j.generos gj "
                    + "JOIN FETCH gj.genero g "
                    + "WHERE UPPER(j.nome) LIKE UPPER(:nome) "
                    + "AND (:genero IS NULL OR g = :genero) ")
    public Page<Jogo> listarPor(String nome, Genero genero, Pageable paginacao);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE Jogo j "
            + "SET j.status = :status "
            + "WHERE j.id = :id")
    public void alterarStatusPor(Integer id, Status status);
}
