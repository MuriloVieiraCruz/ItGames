package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.Genre;
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

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class GenreRepositoryTest {

    @Autowired
    GenreRepository repository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        Genre genreTest = new Genre(null, "Action", Status.A);
        Genre genreTest2 = new Genre(null, "Horror", Status.A);
        this.entityManager.persist(genreTest);
        this.entityManager.persist(genreTest2);
    }

    @Nested
    class SearchBy {

        @Test
        @DisplayName("Should get Genre successfully from DB")
        void searchGenreByNameCase1() {

            Genre genre = repository.searchBy("Action");
            assertThat(genre).isNotNull();
            assertThat(genre.getId()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should not get Genre from DB when genre not exists")
        void searchGenreByNameCase2() {

            Genre genre = repository.searchBy("Act");
            assertThat(genre).isNull();
        }
    }

    @Nested
    class ListBy {

        @Test
        @DisplayName("Should get Genre list successfully from DB")
        void listGenreByNameCase1() {

            Page<Genre> page = repository.listBy("acti", PageRequest.of(0, 15));
            assertThat(page).isNotNull();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }

        @Test
        @DisplayName("Should not get Genre list from DB when genre not exist")
        void listGenreByNameCase2() {

            Page<Genre> page = repository.listBy("rrrr",PageRequest.of(0, 15));
            assertThat(page.getContent()).isEmpty();
            assertThat(page.getTotalElements()).isZero();
            assertThat(page.getTotalPages()).isEqualTo(0);
            assertThat(page.getNumber()).isEqualTo(0);
        }
    }

    @Nested
    class UpdateBy {

        @Test
        @DisplayName("Should update Genre from DB")
        void updateEnterpriseByNameCase1() {

            repository.updateStatusBy(1,  Status.I);
            Genre updatedGenre = repository.findById(1).orElse(null);

            assertThat(updatedGenre).isNotNull();
            assertThat(updatedGenre.getStatus()).isEqualByComparingTo(Status.I);
        }

        @Test
        @DisplayName("Should not update Genre from DB when genre not found")
        void updateEnterpriseByNameCase2() {

            repository.updateStatusBy(5,  Status.I);
            Genre updatedGenre = repository.findById(5).orElse(null);

            assertThat(updatedGenre).isNull();
        }
    }
}