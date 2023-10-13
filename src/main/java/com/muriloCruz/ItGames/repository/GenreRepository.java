package com.muriloCruz.ItGames.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.enums.Status;


@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
	
	@Query(value = 
			"SELECT g "
			+ "FROM Genre g "
			+ "WHERE Upper(g.name) = Upper(:name)")
	public Genre searchBy(String name);
	
	@Query(value = 
			"SELECT g "
			+ "FROM Genre g "
			+ "WHERE g.name = :name "
			+ "AND g.status = 'A'",
			countQuery = "SELECT g "
					+ "FROM Genre g "
					+ "WHERE g.name = :name "
					+ "AND g.status = 'A'")
	public Page<Genre> listBy(String name, Pageable pagination);
	
	@Query(value = 
			"UPDATE Genre g "
			+ "SET g.status = :status "
			+ "WHERE g.id = :id")
	public void updateStatusBy(Integer id, Status status);
}
