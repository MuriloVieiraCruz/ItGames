package com.muriloCruz.ItGames.repository;

import java.security.Timestamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    		+ "JOIN FETCH p.servico s "
    		+ "JOIN FETCH s.jogo "
    		+ "WHERE (:servico IS NULL OR p.servico = :servico) "
    		+ "AND (:dataPostagem IS NULL OR p.dataPostagem = :dataPostagem) "
    		+ "AND s.status = 'A' "
    		+ "ORDER BY p.dataPostagem",
    		countQuery = "SELECT Count(p) "
    	    		+ "FROM Postagem p "
    	    		+ "WHERE (:servico IS NULL OR p.servico = :servico) "
    	    		+ "AND (:dataPostagem IS NULL OR p.dataPostagem = :dataPostagem)")
    public Page<Postagem> listarPor(Servico servico, Timestamp dataPostagem, Pageable paginacao);
    
    @Query(value = 
    		"UPDATE Postagem p "
    		+ "SET p.servico = :servico "
    		+ "WHERE p.id = :id")
    public void atualizarPor(Integer id, Servico servico);

}
