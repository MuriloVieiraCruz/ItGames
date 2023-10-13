package com.muriloCruz.ItGames.repository;

import java.time.Instant;

import com.muriloCruz.ItGames.entity.Post;
import com.muriloCruz.ItGames.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = 
    		"SELECT p "
    		+ "FROM Post p "
    		+ "JOIN FETCH p.service "
    		+ "WHERE p.id = :id")
    public Post searchBy(Integer id);
    
    @Query(value = 
    		"SELECT p "
    		+ "FROM Post p "
    		+ "JOIN FETCH p.service s "
    		+ "JOIN FETCH s.game "
    		+ "WHERE (:service IS NULL OR p.service = :service) "
    		+ "AND (:postingDate IS NULL OR p.postingDate = :postingDate) "
    		+ "AND s.status = 'A' "
    		+ "ORDER BY p.postingDate",
    		countQuery = "SELECT Count(p) "
    	    		+ "FROM Post p "
    	    		+ "WHERE (:service IS NULL OR p.service = :service) "
    	    		+ "AND (:postingDate IS NULL OR p.postingDate = :postingDate)")
    public Page<Post> listBy(Service service, Instant postingDate, Pageable pagination);
    
    @Query(value = 
    		"UPDATE Post p "
    		+ "SET p.service = :service "
    		+ "WHERE p.id = :id")
    public void updateBy(Integer id, Service service);

}
