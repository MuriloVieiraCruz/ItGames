package com.muriloCruz.ItGames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muriloCruz.ItGames.entity.Genero;

import ch.qos.logback.core.status.Status;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {
	
	@Query(value = 
			"SELECT g "
			+ "FROM Genero g "
			+ "WHERE g.nome = :nome")
	public Genero buscarPor(String nome);
	
	@Query(value = 
			"UPDATE Genero g "
			+ "SET g.status = :status "
			+ "WHERE g.id = :id")
	public void atualizarStatusPor(Integer id, Status status); 
}
