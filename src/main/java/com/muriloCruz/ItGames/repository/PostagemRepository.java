package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Integer> {

    @Query()
    public Postagem buscarPor();

}
