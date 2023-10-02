package com.muriloCruz.ItGames.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.muriloCruz.ItGames.entity.Empresa;

import ch.qos.logback.core.status.Status;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

	@Query(value = 
			"SELECT e "
			+ "FROM Empresa e "
			+ "WHERE e.nome = :nome")
	public Empresa buscarPor(String nome);
	
	@Query(value = 
			"SELECT e "
			+ "FROM Empresa e "
			+ "WHERE e.nome = :nome "
			+ "AND e.status = 'A'",
			countQuery = "SELECT e "
					+ "FROM Empresa e "
					+ "WHERE e.nome = :nome "
					+ "AND e.status = 'A'")
	public Page<Empresa> listarPor(String nome, Pageable paginacao);
	
	@Query(value = 
			"UPDATE Empresa e "
			+ "SET e.status = :status "
			+ "WHERE e.id = :id")
	public void atualizarStatusPor(Integer id, Status status);

}
