package com.muriloCruz.ItGames.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

	@Query(value = 
			"SELECT e "
			+ "FROM Enterprise e "
			+ "WHERE e.cnpj = :cnpj")
	public Enterprise searchBy(String cnpj);

	@Query(value =
			"SELECT e "
			+ "FROM Enterprise e "
			+ "WHERE e.id = :idEnterprise")
	public Enterprise searchBy(Long idEnterprise);
	
	@Query(value = 
			"SELECT e "
			+ "FROM Enterprise e "
			+ "WHERE Upper(e.name) LIKE Upper(%:name%) "
			+ "AND e.status = 'A' ",
			countQuery = "SELECT e "
					+ "FROM Enterprise e "
					+ "WHERE Upper(e.name) LIKE Upper(%:name%) "
					+ "AND e.status = 'A' ")
	public Page<Enterprise> listBy(String name, Pageable pagination);

	@Modifying
	@Query(value = 
			"UPDATE Enterprise e "
			+ "SET e.status = :status "
			+ "WHERE e.id = :id")
	public void updateStatusBy(Long id, Status status);

	@Modifying
	@Query(value = "DELETE FROM Enterprise e "
			+ "WHERE e.id = :enterpriseId")
	public void deleteBy(Long enterpriseId);

}
