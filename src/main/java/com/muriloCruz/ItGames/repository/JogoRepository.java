package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.Jogo;
import com.muriloCruz.ItGames.entity.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Integer> {

    @Query(value =
            "SELECT j " +
                "FROM Jogo j " +
                "JOIN FETCH j.genero " +
                "WHERE Upper(j.nome) = Upper(:nome)")
    public Jogo buscaPor(String nome);

    @Query(value =
            "SELECT j " +
                "FROM Jogo j " +
                "JOIN FETCH j.empresa" +
                "WHERE j.id = :id")
    public Jogo buscarPor(Integer id);

    @Query(value =
            "SELECT j " +
                "FROM Jogo j " +
                "JOIN FETCH j.genero g " +
                "WHERE UPPER(j.nome) LIKE UPPER(:nome) " +
                "AND (:genero IS NULL OR g.nome = :genero) ",
                countQuery = "SELECT Count(j) " +
                        "FROM Jogo j " +
                        "WHERE UPPER(j.nome) LIKE UPPER(:nome) " +
                        "AND (:genero IS NULL OR g.nome = :genero) ")
    public Page<Jogo> listarPor(String nome, String genero, Pageable paginacao);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE Jogo j " +
                "SET j.status = :status " +
                "WHERE j.id = :id")
    public void alterarStatusPor(Integer id, Status status);
}
