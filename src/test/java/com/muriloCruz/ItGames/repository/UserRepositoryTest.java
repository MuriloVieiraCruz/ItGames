package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.User;
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

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        User user = new User(null, "Murilo", "123", "lil@gmail.com",
                "780.476.330-15", Status.A, Instant.now(), Instant.now(), null, null, Role.USER);
        entityManager.persist(user);
    }

    @Nested
    class SearchBy {

        @Test
        @DisplayName("Should get User successfully from DB")
        void searchByEnterpriseByLoginCase1() {

            User user = repository.searchBy("Murilo");
            assertThat(user).isNotNull();
        }

        @Test
        @DisplayName("Should not get User from DB when user not exists")
        void searchUserByLoginCase2() {

            User user = repository.searchBy("Mur");
            assertThat(user).isNull();
        }
    }

    @Nested
    class ListBy {

        @Test
        @DisplayName("Should get User list successfully from DB")
        void listUserByLoginCase1() {

            Page<User> page = repository.listBy("muri", PageRequest.of(0, 15));
            assertThat(page).isNotNull();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }

        @Test
        @DisplayName("Should not get User list from DB when user not exist")
        void listUserByLoginCase2() {

            Page<User> page = repository.listBy("rrrr",PageRequest.of(0, 15));
            assertThat(page.getContent()).isEmpty();
            assertThat(page.getTotalElements()).isZero();
        }
    }

    @Nested
    class UpdateBy {

        @Test
        @DisplayName("Should update Enterprise from DB")
        void updateEnterpriseByNameCase1() {

            repository.updateStatusBy(1,  Status.I);
            User updatedUser = repository.findById(1).orElse(null);

            assertThat(updatedUser).isNotNull();
            assertThat(updatedUser.getStatus()).isEqualByComparingTo(Status.I);
        }

        @Test
        @DisplayName("Should not update Enterprise from DB when enterprise not found")
        void updateByEnterpriseByNameCase2() {

            repository.updateStatusBy(5,  Status.I);
            User updatedUser = repository.findById(5).orElse(null);

            assertThat(updatedUser).isNull();
        }
    }
}