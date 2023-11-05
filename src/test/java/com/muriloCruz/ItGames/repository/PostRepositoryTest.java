package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.*;
import com.muriloCruz.ItGames.entity.enums.Availability;
import com.muriloCruz.ItGames.entity.enums.Role;
import com.muriloCruz.ItGames.entity.enums.Status;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        Enterprise enterpriseTest = new Enterprise(1, "MuriloEnterprise", Status.A);
        Game gameTest = new Game
                (1, "MurilusGame", "description", Instant.now(),
                        Status.A, "url", enterpriseTest, null);
        User userTest = new User(1, "Murilo", "123", "lil@gmail.com",
                "780.476.330-15", Status.A, Instant.now(), Instant.now(), null, null, Role.USER);
        Post postTest = new Post(1, "description", BigDecimal.TEN, Availability.OPEN,
                Status.A, gameTest, userTest, Instant.now(), "image");
        Post postTest2 = new Post(1, "description", BigDecimal.ONE, Availability.OPEN,
                Status.A, gameTest, userTest, Instant.now(), "image");
        this.entityManager.merge(userTest);
        this.entityManager.merge(enterpriseTest);
        this.entityManager.merge(gameTest);
        this.entityManager.merge(postTest);
    }

    @Nested
    class SearchById {

        @Test
        @DisplayName("Should get Post by ID successfully from DB")
        void searchByServiceIdCase1() {

            Post post = postRepository.searchBy(1);
            assertThat(post).isNotNull();
            assertThat(post.getId()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should not get Post from DB when service not exists")
        void searchByServiceIdCase2() {

            Post post = postRepository.searchBy(2);
            assertThat(post).isNull();
        }
    }

    @Nested
    class SearchByGame {

        @Test
        @DisplayName("Should get Game by name successfully from DB")
        void searchByGameCase1() {

            Game game = gameRepository.searchBy("MurilusGame");
            Post post = postRepository.searchBy(game);
            assertThat(post).isNotNull();
            assertThat(post.getId()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should not get Game from DB when game not exists")
        void searchByGameCase2() {

            Game game = gameRepository.searchBy("tec");
            Post post = postRepository.searchBy(game);
            assertThat(post).isNull();
        }
    }

    @Nested
    class ListBy {

        @Test
        @DisplayName("Should get Post list successfully from DB")
        void listServiceByPriceCase1() {

            Page<Post> page = postRepository.listBy(BigDecimal.TEN,null, PageRequest.of(0, 15));
            assertThat(page).isNotNull();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }

        @Test
        @DisplayName("Should get Post list from DB when game not exist")
        void listByGameByGameCase2() {
            Game game = gameRepository.findById(1).get();

            Page<Post> page = postRepository.listBy(null, game, PageRequest.of(0, 15));
            assertThat(page).isNotNull();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }

        @Test
        @DisplayName("Should not get Game list from DB when game not exist")
        void listByGameByNameCase3() {
            Game game = gameRepository.findById(999).get();

            Page<Post> page = postRepository.listBy(null, game, PageRequest.of(0, 15));
            assertThat(page.getContent()).isEmpty();
            assertThat(page.getTotalElements()).isZero();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }

        @Test
        @DisplayName("Should not get Post list from DB when all parameters are null")
        void listByServiceCase4() {

            Page<Game> page = gameRepository.listBy(null, null,PageRequest.of(0, 15));
            assertThat(page.getContent()).isEmpty();
            assertThat(page.getTotalElements()).isZero();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }
    }

    @Nested
    class UpdateBy {

        @Test
        @DisplayName("Should update Game from DB")
        void updateServiceByIdCase1() {

            postRepository.updateStatusBy(1,  Status.I);
            Post updatedPost = postRepository.findById(1).orElse(null);

            assertThat(updatedPost).isNotNull();
            assertThat(updatedPost.getStatus()).isEqualByComparingTo(Status.I);
        }

        @Test
        @DisplayName("Should not update Post from DB when service not found")
        void updateServiceByIdCase2() {
            postRepository.updateStatusBy(5,  Status.I);
            Post updatedPost = postRepository.findById(5).orElse(null);

            assertThat(updatedPost).isNull();
        }
    }

    @Nested
    class CountBy {

        @Test
        @DisplayName("Should count Games linked to the enterprise ID from DB")
        void countGamesLinkedToTheCase1() {

            int quantity = postRepository.countBy(1);
            assertThat(quantity).isNotZero();
            assertThat(quantity).isNotNull();
            assertThat(quantity).isEqualTo(1);
        }

        @Test
        @DisplayName("Should return zero from DB when service is not linked to any game ID")
        void countGamesLinkedToTheCase2() {

            int quantity = postRepository.countBy(3);
            assertThat(quantity).isZero();
            assertThat(quantity).isNotNull();
        }
    }

}