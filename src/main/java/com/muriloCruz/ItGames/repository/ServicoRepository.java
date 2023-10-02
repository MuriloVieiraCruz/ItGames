package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.Jogo;
import com.muriloCruz.ItGames.entity.Servico;
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
public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    @Query(value =
            "SELECT s "
            + "FROM Servico s "
            + "JOIN FETCH s.usuario "
            + "JOIN FETCH s.jogo "
            + "WHERE s.id = :id") 
    public Servico buscarPor(Integer id);

    @Query(value =
            "SELECT s "
            + "FROM Servico s "
            + "WHERE s.jogo = :jogo")
    public Servico buscarPor(Jogo jogo);
    
    @Query(value =
            "SELECT s "
            + "FROM Servico s "
            + "JOIN FETCH s.jogo "
            + "WHERE (:preco IS NULL OR s.preco = :preco) "
            + "AND (:jogo IS NULL OR s.jogo = :jogo ) "
            + "AND s.status = 'A' "
            + "ORDER BY s.preco",
            countQuery = "SELECT Count(s) "
                    + "FROM Servico s "
                    + "WHERE (:preco IS NULL OR s.preco = :preco) "
                    + "AND (:jogo IS NULL OR s.jogo = :jogo ) ")
    public Page<Servico> listarPor(BigDecimal preco, Jogo jogo, Pageable paginacao);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE Servico s "
            + "SET s.status = :status "
            + "WHERE s.id = :id")
    public void atualizarStatusPor(Integer id, Status status);
}
