package com.muriloCruz.ItGames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muriloCruz.ItGames.entity.GeneroDoJogo;
import com.muriloCruz.ItGames.entity.composite.GeneroDoJogoId;

@Repository
public interface GeneroDoJogoRepository extends JpaRepository<GeneroDoJogo, GeneroDoJogoId>{

	
}
