package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.entity.*;
import com.muriloCruz.ItGames.entity.composite.GenreGameId;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.entity.enums.TypeAssociation;
import com.muriloCruz.ItGames.repository.GenreGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class GenreGameServiceImplTest {

    @InjectMocks
    GenreGameServiceImpl service;

    @Mock
    GenreGameRepository repository;

    @Mock
    GameServiceImpl gameService;

    @Mock
    GenreServiceImpl genreService;

    Enterprise enterpriseTest;
    Game gameTest;
    Genre genreTest;
    GenreGameId id;
    GenreGame genreGameTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        enterpriseTest = new Enterprise(1, "MuriloEnterprise", Status.A);
        gameTest = new Game
                (1, "MurilusGame", "description", Instant.now(),
                        Status.A, "url", enterpriseTest, Arrays.asList());
        genreTest = new Genre(1, "Action", Status.A);
        id = new GenreGameId(genreTest.getId(), gameTest.getId());
        genreGameTest = new GenreGame(id, TypeAssociation.PRINCIPAL, gameTest, genreTest);
    }

    @Nested
    class InsertEnterprise {

        @Test
        @DisplayName("Should insert genreGame from DB")
        void insertGenreGameCase1() {
            when(genreService.searchBy(genreTest.getId())).thenReturn(genreTest);
            when(gameService.searchBy(gameTest.getId())).thenReturn(gameTest);
            when(repository.saveAndFlush(genreGameTest)).thenReturn(genreGameTest);

            GenreGame genreGameSave = service.insert(gameTest.getId(), genreTest.getId(), TypeAssociation.PRINCIPAL);

            //assertEquals(genreGameSave, genreGameTest);
            verify(repository).saveAndFlush(genreGameTest);
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not insert genreGame from DB when genre not found in the bank")
        void insertGenreGameCase2() {
            when(genreService.searchBy(genreTest.getId())).thenReturn(null);

            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.insert(gameTest.getId(), genreTest.getId(), TypeAssociation.PRINCIPAL);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No gender was found linked to the parameters informed"));

            verify(repository, never()).save(any());
            verify(genreService, times(1)).searchBy(any());
            verify(gameService, never()).searchBy(gameTest.getId());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not insert genreGame from DB when genre is inactive in the bank")
        void insertGenreGameCase3() {
            genreTest.setStatus(Status.I);
            when(genreService.searchBy(genreTest.getId())).thenReturn(genreTest);

            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.insert(gameTest.getId(), genreTest.getId(), TypeAssociation.PRINCIPAL);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The gender entered is inactive"));

            verify(repository, never()).save(any());
            verify(genreService, times(1)).searchBy(any());
            verify(gameService, never()).searchBy(gameTest.getId());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not insert Post from DB when game not found in the bank")
        void insertGenreGameCase4() {
            when(genreService.searchBy(genreTest.getId())).thenReturn(genreTest);
            when(gameService.searchBy(gameTest.getId())).thenReturn(null);

            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.insert(gameTest.getId(), genreTest.getId(), TypeAssociation.PRINCIPAL);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No game was found linked to the parameters informed"));

            verify(repository, never()).save(any());
            verify(genreService, times(1)).searchBy(any());
            verify(gameService, times(1)).searchBy(gameTest.getId());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not insert genreGame from DB when game is inactive in the bank")
        void insertGenreGameCase5() {
            gameTest.setStatus(Status.I);
            when(genreService.searchBy(genreTest.getId())).thenReturn(genreTest);
            when(gameService.searchBy(gameTest.getId())).thenReturn(gameTest);

            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.insert(gameTest.getId(), genreTest.getId(), TypeAssociation.PRINCIPAL);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The game entered is inactive"));

            verify(repository, never()).save(any());
            verify(genreService, times(1)).searchBy(any());
            verify(gameService, times(1)).searchBy(gameTest.getId());
            verifyNoMoreInteractions(repository);
        }

    }

    @Nested
    class UpdateGenreGame {

        @Test
        @DisplayName("Should update genreGame enterprise from DB")
        void updateGenreGameCase1() {
            when(genreService.searchBy(genreTest.getId())).thenReturn(genreTest);
            when(gameService.searchBy(gameTest.getId())).thenReturn(gameTest);
            when(repository.searchBy(genreTest, gameTest)).thenReturn(genreGameTest);
            when(service.searchBy(genreTest, gameTest)).thenReturn(genreGameTest);

            service.update(gameTest.getId(), genreTest.getId(), TypeAssociation.PRINCIPAL);

            //assertEquals(genreGameSave, genreGameTest);
            verify(repository).saveAndFlush(genreGameTest);
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not update genreGame from DB when genreGame found not exists")
        void updateGenreGameCase2() {
            when(genreService.searchBy(genreTest.getId())).thenReturn(genreTest);
            when(gameService.searchBy(gameTest.getId())).thenReturn(gameTest);
            when(repository.searchBy(genreTest, gameTest)).thenReturn(null);

            NullPointerException e = assertThrows(NullPointerException.class, () -> {
                service.update(gameTest.getId(), genreTest.getId(), TypeAssociation.PRINCIPAL);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No game genre was found linked to the ID '1' informed"));

            verify(repository, never()).saveAndFlush(genreGameTest);
            verifyNoMoreInteractions(repository);
            verify(genreService, times(2)).searchBy(genreTest.getId());
            verify(gameService, times(2)).searchBy(gameTest.getId());
            //verify(service, times(1)).searchBy(genreTest, gameTest);
            verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    class SearchEnterprise {

        @Test
        @DisplayName("Should search genreGame from DB")
        void searchGenreGameCase1() {
            when(genreService.searchBy(genreTest.getId())).thenReturn(genreTest);
            when(gameService.searchBy(gameTest.getId())).thenReturn(gameTest);
            when(repository.searchBy(genreTest, gameTest)).thenReturn(genreGameTest);

            GenreGame genreGameSave = service.searchBy(genreTest, gameTest);

            assertEquals(genreGameTest, genreGameSave);
            verify(genreService, times(1)).searchBy(genreTest.getId());
            verify(gameService, times(1)).searchBy(gameTest.getId());
            verify(repository, times(1)).searchBy(genreTest, gameTest);
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should got error when genreGame found not exists")
        void searchGenreGameCase2() {
            when(genreService.searchBy(genreTest.getId())).thenReturn(genreTest);
            when(gameService.searchBy(gameTest.getId())).thenReturn(gameTest);
            when(repository.searchBy(genreTest, gameTest)).thenReturn(null);

            NullPointerException e = assertThrows(NullPointerException.class, () -> {
                GenreGame genreGameSave = service.searchBy(genreTest, gameTest);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No game genre was found linked to the ID '1' informed"));

            verify(genreService, times(1)).searchBy(genreTest.getId());
            verify(gameService, times(1)).searchBy(gameTest.getId());
            verify(repository, times(1)).searchBy(genreTest, gameTest);
            verifyNoMoreInteractions(repository);
        }
    }
}