package com.muriloCruz.ItGames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muriloCruz.ItGames.entity.Genero;
import com.muriloCruz.ItGames.entity.GeneroDoJogo;
import com.muriloCruz.ItGames.entity.Jogo;
import com.muriloCruz.ItGames.entity.composite.GeneroDoJogoId;

@Repository
public interface GeneroDoJogoRepository extends JpaRepository<GeneroDoJogo, GeneroDoJogoId>{

	@Query(value = "SELECT Count(gj) "
			+ "FROM GeneroDoJogo gj "
			+ "WHERE gj.jogo.id = :idDoJogo")
	public int contarPor(Integer idDoJogo);
	
	@Query(value = "SELECT gj "
			+ "FROM GeneroDoJogo gj "
			+ "WHERE gj.genero = :genero "
			+ "AND gj.jogo = :jogo")
	public GeneroDoJogo buscarPor(Genero genero, Jogo jogo);
}
