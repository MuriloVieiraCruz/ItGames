package com.muriloCruz.ItGames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Genero extends JpaRepository<Genero, Integer> {
}
