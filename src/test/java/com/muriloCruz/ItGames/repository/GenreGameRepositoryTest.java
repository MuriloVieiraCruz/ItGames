package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.composite.GenreGameId;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.entity.enums.TypeAssociation;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class GenreGameRepositoryTest {

    @Autowired
    GenreGameRepository repository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    GameRepository gameRepository;

    @BeforeEach
    public void setUp() {
        Genre genreTest = new Genre(1, "Action", Status.A);
        Enterprise enterpriseTest = new Enterprise(1, "MuriloEnterprise", Status.A);
        Game gameTest = new Game
                (1, "MurilusGame", "description", Instant.now(),
                        Status.A, "url", enterpriseTest, null);
        entityManager.merge(genreTest);
        entityManager.merge(enterpriseTest);
        entityManager.merge(gameTest);

        GenreGameId id = new GenreGameId(genreTest.getId(), gameTest.getId());
        GenreGame genreGameTest = new GenreGame(id, TypeAssociation.PRINCIPAL, gameTest, genreTest);
        entityManager.merge(genreGameTest);
    }

    @Nested
    class CountBy {

        @Test
        @DisplayName("Should count Genre linked to a game by ID successfully from DB")
        void countGameLinkedByIdCase1() {

            int quantity = repository.countByGenre(1);
            assertThat(quantity).isNotZero();
            assertThat(quantity).isNotNull();
            assertThat(quantity).isEqualTo(1);
        }

        @Test
        @DisplayName("Should return zero from DB when genre is not linked to any game")
        void countGameLinkedByIdCase2() {

            int quantity = repository.countByGenre(4);
            assertThat(quantity).isZero();
            assertThat(quantity).isNotNull();
            assertThat(quantity).isEqualTo(0);
        }

        @Test
        @DisplayName("Should count Game linked to a genre by ID successfully from DB")
        void countGenreLinkedByIdCase3() {

            int quantity = repository.countByGame(1);
            assertThat(quantity).isNotZero();
            assertThat(quantity).isNotNull();
            assertThat(quantity).isEqualTo(1);
        }

        @Test
        @DisplayName("Should return zero from DB when game is not linked to any genre")
        void countGenreLinkedByIdCase4() {

            int quantity = repository.countByGame(4);
            assertThat(quantity).isZero();
            assertThat(quantity).isNotNull();
            assertThat(quantity).isEqualTo(0);
        }
    }


    @Nested
    class SearchBy {

        @Test
        @DisplayName("Should get Game by name successfully from DB")
        void searchByGenreGameAssociationCase1() {
            Genre genre = genreRepository.findById(1).get();
            Game game = gameRepository.findById(1).get();

            GenreGame genreGame = repository.searchBy(genre, game);
            assertThat(genreGame).isNotNull();
        }

        @Test
        @DisplayName("Should get Game by name successfully from DB")
        void searchByGenreGameAssociationCase2() {

            GenreGame genreGame = repository.searchBy(null, null);
            assertThat(genreGame).isNull();
        }
    }
}