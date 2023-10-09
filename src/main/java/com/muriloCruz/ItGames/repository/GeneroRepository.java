package com.muriloCruz.ItGames.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muriloCruz.ItGames.entity.Genero;
import com.muriloCruz.ItGames.entity.enums.Status;


@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {
	
	@Query(value = 
			"SELECT g "
			+ "FROM Genero g "
			+ "WHERE Upper(g.nome) = Upper(:nome)")
	public Genero buscarPor(String nome);
	
	@Query(value = 
			"SELECT g "
			+ "FROM Genero g "
			+ "WHERE g.nome = :nome "
			+ "AND g.status = 'A'",
			countQuery = "SELECT g "
					+ "FROM Genero g "
					+ "WHERE g.nome = :nome "
					+ "AND g.status = 'A'")
	public Page<Genero> listarPor(String nome, Pageable paginacao);
	
	@Query(value = 
			"UPDATE Genero g "
			+ "SET g.status = :status "
			+ "WHERE g.id = :id")
	public void atualizarStatusPor(Integer id, Status status); 
}
