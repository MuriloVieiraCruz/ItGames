package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.composite.GenreGameId;

@Repository
public interface GenreGameRepository extends JpaRepository<GenreGame, GenreGameId>{

	@Query(value = "SELECT Count(gj) "
			+ "FROM GenreGame gj "
			+ "WHERE gj.game.id = :gameId")
	public int countByGame(Long gameId);

	@Query(value = "SELECT Count(gj) "
			+ "FROM GenreGame gj "
			+ "WHERE gj.genre.id = :genreId")
	public int countByGenre(Long genreId);
	
	@Query(value = "SELECT gj "
			+ "FROM GenreGame gj "
			+ "WHERE gj.genre.id = :genreId "
			+ "AND gj.game.id = :gameId")
	public GenreGame searchBy(Long genreId, Long gameId);

	@Modifying
	@Query(value = "DELETE FROM GenreGame gg "
			+ "WHERE gg.id.gameId = :gameId "
			+ "AND gg.id.genreId = :genreId ")
	public void deleteBy(Long gameId, Long genreId);
}
