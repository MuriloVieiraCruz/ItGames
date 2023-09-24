package com.muriloCruz.ItGames.repository;

import java.awt.print.Pageable;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muriloCruz.ItGames.entity.Postagem;
import com.muriloCruz.ItGames.entity.Servico;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Integer> {

    @Query(value = 
    		"SELECT p "
    		+ "FROM Postagem p "
    		+ "JOIN FETCH p.servico "
    		+ "WHERE p.id = :id")
    public Postagem buscarPor(Integer id);
    
    @Query(value = 
    		"SELECT p "
    		+ "FROM Postagem p "
    		+ "JOIN FETCH p.servico "
    		+ "WHERE (:servico IS NULL OR p.servico = :servico) "
    		+ "AND (:data IS NULL OR p.data = :data) "
    		+ "ORDER BY p.servico",
    		countQuery = "SELECT p "
    	    		+ "FROM Postagem p "
    	    		+ "WHERE (:servico IS NULL OR p.servico = :servico) "
    	    		+ "AND (:data IS NULL OR p.data = :data)")
    public Page<Postagem> listarPor(Servico servico, Date dataPostagem, Pageable paginacao);
    
    @Query(value = 
    		"UPDATE Postagem p "
    		+ "SET p.servico = :servico "
    		+ "WHERE p.id = :id")
    public void atualizarPor(Integer id, Servico servico);

}
