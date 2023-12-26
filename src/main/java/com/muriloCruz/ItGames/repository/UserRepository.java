package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muriloCruz.ItGames.entity.enums.Status;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query(value = 
			"SELECT u "
			+ "FROM User u "
			+ "WHERE u.email = :email")
	public User searchBy(String email);

	@Query(value =
			"SELECT u "
			+ "FROM User u "
			+ "WHERE u.id = :userId")
	public User searchBy(Long userId);
	
	@Query(value = 
			"SELECT u "
			+ "FROM User u "
			+ "WHERE Upper(u.name) LIKE Upper(%:name%) "
			+ "AND u.status = 'A'",
			countQuery = "SELECT u "
					+ "FROM User u "
					+ "WHERE Upper(u.name) LIKE Upper(%:name%) "
					+ "AND u.status = 'A'")
	public Page<User> listBy(String name, Pageable pagination);

	@Modifying
	@Transactional
	@Query(value = 
			"UPDATE User u "
			+ "SET u.status = :status "
			+ "WHERE u.id = :id")
	public void updateStatusBy(Long id, Status status);
}
