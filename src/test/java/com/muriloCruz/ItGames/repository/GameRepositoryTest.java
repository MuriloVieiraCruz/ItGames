package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.enums.Status;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class GameRepositoryTest {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        Enterprise enterpriseTest = new Enterprise(null, "MuriloEnterprise", Status.A);
        Game gameTest = new Game
                (null, "MurilusGame", "description", Instant.now(),
                        Status.A, "url", enterpriseTest, null);
        this.entityManager.persist(enterpriseTest);
        this.entityManager.persist(gameTest);
    }

    @Nested
    class SearchById {

        @Test
        @DisplayName("Should get Game by ID successfully from DB")
        void searchByGameIdCase1() {

            Game game = gameRepository.searchBy(1);
            assertThat(game).isNotNull();
            assertThat(game.getId()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should not get Game from DB when game not exists")
        void searchByGameIdCase2() {

            Game game = gameRepository.searchBy(2);
            assertThat(game).isNull();
        }
    }

    @Nested
    class SearchByName {

        @Test
        @DisplayName("Should get Game by name successfully from DB")
        void searchByGameNameCase1() {

            Game game = gameRepository.searchBy("MurilusGame");
            assertThat(game).isNotNull();
            assertThat(game.getId()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should not get Game from DB when game not exists")
        void searchByGameNameCase2() {

            Game game = gameRepository.searchBy("tec");
            assertThat(game).isNull();
        }
    }

    @Nested
    class ListBy {

        @Test
        @DisplayName("Should get Game list successfully from DB")
        void listGameByNameCase1() {

            Page<Game> page = gameRepository.listBy("rilu",null, PageRequest.of(0, 15));
            assertThat(page).isNotNull();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }

        @Test
        @DisplayName("Should not get Game list from DB when game not exist")
        void listGameByNameCase2() {

            Page<Game> page = gameRepository.listBy("rrrr", null,PageRequest.of(0, 15));
            assertThat(page.getContent()).isEmpty();
            assertThat(page.getTotalElements()).isZero();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }

        @Test
        @DisplayName("Should not get Game list from DB when all parameters are null")
        void listGameByNameCase3() {

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
        void updateGameByNameCase1() {

            gameRepository.updateStatusBy(1,  Status.I);
            Game updatedGame = gameRepository.findById(1).orElse(null);

            assertThat(updatedGame).isNotNull();
            assertThat(updatedGame.getStatus()).isEqualByComparingTo(Status.I);
        }

        @Test
        @DisplayName("Should not update Game from DB when game not found")
        void updateEnterpriseByNameCase2() {
            gameRepository.updateStatusBy(5,  Status.I);
            Game updatedGame = gameRepository.findById(5).orElse(null);

            assertThat(updatedGame).isNull();
        }
    }

    @Nested
    class CountBy {

        @Test
        @DisplayName("Should count Games linked to the enterprise ID from DB")
        void countGamesLinkedToTheCase1() {

            int quantity = gameRepository.countGamesLinkedToThe(1);
            assertThat(quantity).isNotZero();
            assertThat(quantity).isNotNull();
            assertThat(quantity).isEqualTo(1);
        }

        @Test
        @DisplayName("Should return zero from DB when game is not linked to any enterprise ID")
        void countGamesLinkedToTheCase2() {

            int quantity = gameRepository.countGamesLinkedToThe(3);
            assertThat(quantity).isZero();
            assertThat(quantity).isNotNull();
        }
    }
}