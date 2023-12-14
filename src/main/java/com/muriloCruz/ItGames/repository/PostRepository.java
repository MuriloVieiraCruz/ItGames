package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.Post;
import com.muriloCruz.ItGames.entity.enums.Availability;
import com.muriloCruz.ItGames.entity.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value =
            "SELECT p "
            + "FROM Post p "
            + "JOIN FETCH p.user u "
            + "JOIN FETCH p.game g "
            + "WHERE p.id = :id") 
    public Post searchBy(Long id);

    @Query(value =
            "SELECT p "
            + "FROM Post p "
            + "WHERE p.game = :game")
    public Post searchBy(Game game);
    
    @Query(value =
            "SELECT p "
            + "FROM Post p "
            + "JOIN FETCH p.game g "
            + "JOIN FETCH p.user u "
            + "WHERE (:price IS NULL OR p.price = :price) "
            + "AND (:availability IS NULL OR p.availability = :availability) "
            + "AND (:postDate IS NULL OR p.postDate = :postDate) "
            + "AND (:gameId IS NULL OR p.game.id = :gameId) "
            + "AND (:userId IS NULL OR p.user.id = :userId) "
            + "AND p.status = 'A' "
            + "ORDER BY p.price",
            countQuery = "SELECT Count(p) "
                    + "FROM Post p "
                    + "WHERE (:price IS NULL OR p.price = :price) "
                    + "AND (:availability IS NULL OR p.availability = :availability) "
                    + "AND (:postDate IS NULL OR p.postDate = :postDate) "
                    + "AND (:gameId IS NULL OR p.game.id = :gameId) "
                    + "AND (:userId IS NULL OR p.user.id = :userId) "
                    + "AND p.status = 'A' ")
    public Page<Post> listBy(
            Optional<BigDecimal> price,
            Optional<Availability> availability,
            Optional<LocalDate> postDate,
            Optional<Long> gameId,
            Optional<Long> userId,
            Pageable pagination);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE Post p "
            + "SET p.status = :status "
            + "WHERE p.id = :id")
    public void updateStatusBy(Long id, Status status);

    @Query(value = "SELECT Count(p) "
            + "FROM Post p "
            + "WHERE p.game.id = :gameId")
    public int countBy(Long gameId);

    @Modifying
    @Query(value = "DELETE FROM Post p "
            + "WHERE p.id = :postId")
    public void deleteBy(Long postId);
}
