package com.muriloCruz.ItGames.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {

	@Query(value = 
			"SELECT e "
			+ "FROM Enterprise e "
			+ "WHERE Upper(e.name) = Upper(:name)")
	public Enterprise searchBy(String name);
	
	@Query(value = 
			"SELECT e "
			+ "FROM Enterprise e "
			+ "WHERE e.name LIKE %:name% "
			+ "AND e.status = 'A'",
			countQuery = "SELECT e "
					+ "FROM Enterprise e "
					+ "WHERE e.name LIKE :name "
					+ "AND e.status = 'A'")
	public Page<Enterprise> listBy(String name, Pageable pagination);

	@Modifying
	@Transactional
	@Query(value = 
			"UPDATE Enterprise e "
			+ "SET e.status = :status "
			+ "WHERE e.id = :id")
	public void updateStatusBy(Integer id, Status status);

}
