package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.*;
import com.muriloCruz.ItGames.entity.enums.Availability;
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
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ServiceRepositoryTest {

    @Autowired
    ServiceRepository serviceRepository;

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
                "780.476.330-15", Status.A, Instant.now(), Instant.now(), null, null);
        Service serviceTest = new Service(1, "description", BigDecimal.TEN, Availability.OPEN,
                Status.A, gameTest, userTest, Instant.now(), "image");
        Service serviceTest2 = new Service(1, "description", BigDecimal.ONE, Availability.OPEN,
                Status.A, gameTest, userTest, Instant.now(), "image");
        this.entityManager.merge(userTest);
        this.entityManager.merge(enterpriseTest);
        this.entityManager.merge(gameTest);
        this.entityManager.merge(serviceTest);
    }

    @Nested
    class SearchById {

        @Test
        @DisplayName("Should get Service by ID successfully from DB")
        void searchByServiceIdCase1() {

            Service service = serviceRepository.searchBy(1);
            assertThat(service).isNotNull();
            assertThat(service.getId()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should not get Service from DB when service not exists")
        void searchByServiceIdCase2() {

            Service service = serviceRepository.searchBy(2);
            assertThat(service).isNull();
        }
    }

    @Nested
    class SearchByGame {

        @Test
        @DisplayName("Should get Game by name successfully from DB")
        void searchByGameCase1() {

            Game game = gameRepository.searchBy("MurilusGame");
            Service service = serviceRepository.searchBy(game);
            assertThat(service).isNotNull();
            assertThat(service.getId()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should not get Game from DB when game not exists")
        void searchByGameCase2() {

            Game game = gameRepository.searchBy("tec");
            Service service = serviceRepository.searchBy(game);
            assertThat(service).isNull();
        }
    }

    @Nested
    class ListBy {

        @Test
        @DisplayName("Should get Service list successfully from DB")
        void listServiceByPriceCase1() {

            Page<Service> page = serviceRepository.listBy(BigDecimal.TEN,null, PageRequest.of(0, 15));
            assertThat(page).isNotNull();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }

        @Test
        @DisplayName("Should get Service list from DB when game not exist")
        void listByGameByGameCase2() {
            Game game = gameRepository.findById(1).get();

            Page<Service> page = serviceRepository.listBy(null, game, PageRequest.of(0, 15));
            assertThat(page).isNotNull();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }

        @Test
        @DisplayName("Should not get Game list from DB when game not exist")
        void listByGameByNameCase3() {
            Game game = gameRepository.findById(999).get();

            Page<Service> page = serviceRepository.listBy(null, game, PageRequest.of(0, 15));
            assertThat(page.getContent()).isEmpty();
            assertThat(page.getTotalElements()).isZero();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }

        @Test
        @DisplayName("Should not get Service list from DB when all parameters are null")
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

            serviceRepository.updateStatusBy(1,  Status.I);
            Service updatedService = serviceRepository.findById(1).orElse(null);

            assertThat(updatedService).isNotNull();
            assertThat(updatedService.getStatus()).isEqualByComparingTo(Status.I);
        }

        @Test
        @DisplayName("Should not update Service from DB when service not found")
        void updateServiceByIdCase2() {
            serviceRepository.updateStatusBy(5,  Status.I);
            Service updatedService = serviceRepository.findById(5).orElse(null);

            assertThat(updatedService).isNull();
        }
    }

    @Nested
    class CountBy {

        @Test
        @DisplayName("Should count Games linked to the enterprise ID from DB")
        void countGamesLinkedToTheCase1() {

            int quantity = serviceRepository.countBy(1);
            assertThat(quantity).isNotZero();
            assertThat(quantity).isNotNull();
            assertThat(quantity).isEqualTo(1);
        }

        @Test
        @DisplayName("Should return zero from DB when service is not linked to any game ID")
        void countGamesLinkedToTheCase2() {

            int quantity = serviceRepository.countBy(3);
            assertThat(quantity).isZero();
            assertThat(quantity).isNotNull();
        }
    }

}