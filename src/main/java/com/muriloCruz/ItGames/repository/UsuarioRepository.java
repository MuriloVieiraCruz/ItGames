package com.muriloCruz.ItGames.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muriloCruz.ItGames.entity.Usuario;
import com.muriloCruz.ItGames.entity.enums.Status;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{
	
	@Query(value = 
			"SELECT u "
			+ "FROM Usuario u "
			+ "WHERE u.login = :login")
	public Usuario buscarPor(String login);
	
	@Query(value = 
			"SELECT e "
			+ "FROM Usuario u "
			+ "WHERE Upper(u.login) = Upper(:login) "
			+ "AND u.status = 'A'",
			countQuery = "SELECT e "
					+ "FROM Usuario u "
					+ "WHERE Upper(u.login) = Upper(:login) "
					+ "AND u.status = 'A'")
	public Page<Usuario> listarPor(String login, Pageable paginacao);
	
	@Query(value = 
			"UPDATE Usuario u "
			+ "SET u.status = :status "
			+ "WHERE u.id = :id")
	public void atualizarStatusPor(Integer id, Status status);
}
