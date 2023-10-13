package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muriloCruz.ItGames.entity.enums.Status;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query(value = 
			"SELECT u "
			+ "FROM User u "
			+ "WHERE u.login = :login")
	public User searchBy(String login);
	
	@Query(value = 
			"SELECT u "
			+ "FROM User u "
			+ "WHERE Upper(u.login) = Upper(:login) "
			+ "AND u.status = 'A'",
			countQuery = "SELECT u "
					+ "FROM User u "
					+ "WHERE Upper(u.login) = Upper(:login) "
					+ "AND u.status = 'A'")
	public Page<User> listBy(String login, Pageable pagination);
	
	@Query(value = 
			"UPDATE User u "
			+ "SET u.status = :status "
			+ "WHERE u.id = :id")
	public void updateStatusBy(Integer id, Status status);
}
