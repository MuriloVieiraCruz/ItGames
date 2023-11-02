package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.dto.GameRequestDto;
import com.muriloCruz.ItGames.dto.GenreGameRequestDto;
import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.GenreGame;
import com.muriloCruz.ItGames.entity.composite.GenreGameId;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.entity.enums.TypeAssociation;
import com.muriloCruz.ItGames.repository.EnterpriseRepository;
import com.muriloCruz.ItGames.repository.GameRepository;
import com.muriloCruz.ItGames.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GamePostImplTest {

    @InjectMocks
    GameServiceImpl service;

    @Mock
    GameRepository repository;

    @Mock
    EnterpriseRepository enterpriseRepository;

    @Mock
    GenreRepository genreRepository;

    Game gameTest;

    Enterprise enterpriseTest;

    Genre genreTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        enterpriseTest = new Enterprise(1, "MuriloEnterprise", Status.A);
        gameTest = new Game
                (1, "MurilusGame", "description", Instant.now(),
                        Status.A, "url", enterpriseTest, null);
    }

    @Nested
    class InsertGame {

        @Test
        @DisplayName("Should insert Game from DB")
        void insertEnterpriseCase1() {
            genreTest = new Genre(1, "Action", Status.A);
            GenreGameRequestDto genreGameDto = new GenreGameRequestDto(genreTest.getId(), TypeAssociation.PRINCIPAL);
            GameRequestDto game = new GameRequestDto("MurilosGame", "description",
                    Instant.now(), "url", enterpriseTest, Arrays.asList(genreGameDto));
            GenreGameId id = new GenreGameId(genreTest.getId(), gameTest.getId());
            GenreGame genreGame = new GenreGame(id, TypeAssociation.PRINCIPAL, gameTest, genreTest);
            Game gameTest2 = new Game
                    (null, "MurilusGame", "description", Instant.now(),
                            Status.A, "url", enterpriseTest, Arrays.asList(genreGame));

            when(enterpriseRepository.findById(enterpriseTest.getId())).thenReturn(Optional.of(enterpriseTest));
            when(genreRepository.findById(genreTest.getId())).thenReturn(Optional.of(genreTest));
            when(repository.save(gameTest2)).thenReturn(gameTest);
            when(repository.saveAndFlush(gameTest)).thenReturn(gameTest);

            Game gameSave = service.insert(game);

            assertEquals(gameTest, gameSave);
            verify(repository).searchBy(gameTest.getName());
            verify(repository).save(gameTest);
            verifyNoMoreInteractions(repository);
        }

//        @Test
//        @DisplayName("Should not insert Enterprise from DB when enterprise already has the same name in the bank")
//        void insertEnterpriseCase2() {
//            when(repository.searchBy(enterpriseTest2.getName())).thenReturn(enterpriseTest);
//
//            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
//                service.insert(enterpriseTest2);
//            });
//
//            assertThat(e, notNullValue());
//            assertThat(e.getMessage(), is("There is already a enterprise registered with this name"));
//
//            verify(repository, never()).save(any());
//            verify(repository, times(1)).searchBy(any());
//            verifyNoMoreInteractions(repository);
//        }
    }

    @Nested
    class SearchEnterprise {

//        @Test
//        @DisplayName("Should search Enterprise from DB")
//        void searchEnterpriseCase1() {
//            when(repository.findById(enterpriseTest.getId())).thenReturn(Optional.of(enterpriseTest));
//
//            Enterprise enterpriseSave = service.searchBy(enterpriseTest.getId());
//
//            assertEquals(enterpriseTest, enterpriseSave);
//            verify(repository, times(1)).findById(enterpriseTest.getId());
//            verifyNoMoreInteractions(repository);
//        }

//        @Test
//        @DisplayName("Should got error when enterprise found not exists")
//        void searchEnterpriseCase2() {
//            when(repository.findById(5)).thenReturn(Optional.ofNullable(null));
//
//            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
//                service.searchBy(5);
//            });
//
//            assertThat(e, notNullValue());
//            assertThat(e.getMessage(), is("No enterprise was found linked to the parameters entered"));
//
//            verify(repository).findById(5);
//            verifyNoMoreInteractions(repository);
//        }

//        @Test
//        @DisplayName("Should got error when enterprise found is inactive")
//        void searchEnterpriseCase3() {
//            Enterprise enterprise = new Enterprise(3, "", Status.I);
//            when(repository.findById(enterprise.getId())).thenReturn(Optional.ofNullable(enterprise));
//
//            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
//                service.searchBy(enterprise.getId());
//            });
//
//            assertThat(e, notNullValue());
//            assertThat(e.getMessage(), is("The enterprise informed is inactive"));
//
//            verify(repository).findById(enterprise.getId());
//            verifyNoMoreInteractions(repository);
//        }
    }

    @Nested
    class ListEnterprise {

//        @Test
//        @DisplayName("Should list Enterprise from DB")
//        void listEnterpriseCase1() {
//            List<Enterprise> enterpriseList = Arrays.asList(enterpriseTest, enterpriseTest3);
//            Page<Enterprise> enterprisePage = new PageImpl<>(enterpriseList, PageRequest.of(0, enterpriseList.size()),  enterpriseList.size());
//
//            when(repository.listBy("rilo", PageRequest.of(0, 15))).thenReturn(enterprisePage);
//            Page<Enterprise> enterprisesFound = service.listBy("rilo", PageRequest.of(0, 15));
//
//            assertThat(enterprisesFound, notNullValue());
//            assertThat(enterprisesFound.getContent(), not(empty()));
//            assertThat(enterprisesFound.getContent().size(), is(2));
//            assertThat(enterprisesFound.getTotalElements(), is((long)enterpriseList.size()));
//
//            assertThat(enterprisesFound.getContent().get(0).getId(), is(1));
//            assertThat(enterprisesFound.getContent().get(0).getName(), is("MuriloEnterprise"));
//            assertThat(enterprisesFound.getContent().get(1).getId(), is(2));
//            assertThat(enterprisesFound.getContent().get(1).getName(), is("MuriloEnterprise"));
//        }
    }

    @Nested
    class UpdateStatusEnterprise {

//        @Test
//        @DisplayName("Should update Enterprise from DB")
//        void updateStatusEnterpriseCase1() {
//            when(repository.findById(1)).thenReturn(Optional.of(enterpriseTest));
//            doNothing().when(repository).updateStatusBy(1, Status.I);
//
//            service.updateStatusBy(1, Status.I);
//
//            verify(repository).findById(1);
//            verify(repository).updateStatusBy(1, Status.I);
//            verifyNoMoreInteractions(repository);
//        }

//        @Test
//        @DisplayName("Should not update Enterprise from DB when enterprise found not exists")
//        void updateStatusEnterpriseCase2() {
//            when(repository.findById(5)).thenReturn(Optional.ofNullable(null));
//
//            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
//                service.updateStatusBy(5, Status.I);
//            });
//
//            assertThat(e, notNullValue());
//            assertThat(e.getMessage(), is("No enterprise was found linked to the parameters entered"));
//
//            verify(repository).findById(5);
//            verify(repository, never()).updateStatusBy(any(), any());
//            verifyNoMoreInteractions(repository);
//        }

//        @Test
//        @DisplayName("Should update Enterprise from DB")
//        void updateStatusEnterpriseCase3() {
//            Enterprise enterprise = new Enterprise(3, "", Status.I);
//            when(repository.findById(enterprise.getId())).thenReturn(Optional.ofNullable(enterprise));
//
//            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
//                service.updateStatusBy(enterprise.getId(), Status.I);
//            });
//
//            assertThat(e, notNullValue());
//            assertThat(e.getMessage(), is("The status entered is already assigned"));
//
//            verify(repository, times(1)).findById(enterprise.getId());
//            verify(repository, never()).updateStatusBy(any(), any());
//            verifyNoMoreInteractions(repository);
//        }
    }

    @Nested
    class ExcludeEnterprise {

//        @Test
//        @DisplayName("Should exclude Enterprise from DB")
//        void excludeEnterpriseByIdCase1 () {
//            when(repository.findById(1)).thenReturn(Optional.ofNullable(enterpriseTest));
//            when(gameRepository.countGamesLinkedToThe(enterpriseTest.getId())).thenReturn(0);
//
//            Enterprise deletedEnterprise = service.excludeBy(enterpriseTest.getId());
//
//            assertNotNull(deletedEnterprise);
//            assertEquals(enterpriseTest.getId(), deletedEnterprise.getId());
//
//            verify(repository, times(1)).findById(enterpriseTest.getId());
//            verify(repository, times(1)).deleteById(enterpriseTest.getId());
//            verifyNoMoreInteractions(repository);
//
//        }

//        @Test
//        @DisplayName("Should not exclude Enterprise from DB when enterprise do not exist")
//        void excludeEnterpriseByIdCase2 () {
//            when(repository.findById(1256)).thenReturn(Optional.ofNullable(null));
//
//            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
//                service.excludeBy(1256);
//            });
//
//            assertThat(e, notNullValue());
//            assertThat(e.getMessage(), is("No enterprise was found linked to the parameters entered"));
//
//            verify(repository, times(1)).findById(1256);
//            //verify(gameRepository.countGamesLinkedToThe(any()), never());
//            verify(repository, never()).deleteById(any());
//            verifyNoMoreInteractions(repository);
//        }

//        @Test
//        @DisplayName("Should not exclude Enterprise from DB when enterprise is inactive")
//        void excludeEnterpriseByIdCase3 () {
//            when(repository.findById(2)).thenReturn(Optional.ofNullable(enterpriseTest3));
//
//            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
//                service.excludeBy(2);
//            });
//
//            assertThat(e, notNullValue());
//            assertThat(e.getMessage(), is("The enterprise informed is inactive"));
//
//            verify(repository, times(1)).findById(2);
//            //verify(gameRepository.countGamesLinkedToThe(any()), never());
//            verify(repository, never()).deleteById(any());
//            verifyNoMoreInteractions(repository);
//        }

//        @Test
//        @DisplayName("Should not exclude Enterprise from DB when enterprise is linked to another game")
//        void excludeEnterpriseByIdCase4 () {
//            when(repository.findById(enterpriseTest.getId())).thenReturn(Optional.ofNullable(enterpriseTest));
//            when(gameRepository.countGamesLinkedToThe(enterpriseTest.getId())).thenReturn(1);
//
//            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
//                service.excludeBy(enterpriseTest.getId());
//            });
//
//            assertThat(e, notNullValue());
//            assertThat(e.getMessage(), is("This enterprise is linked to '1' games"));
//
//            verify(repository, times(1)).findById(enterpriseTest.getId());
//            //verify(gameRepository.countGamesLinkedToThe(any()), never());
//            verify(repository, never()).deleteById(any());
//            verifyNoMoreInteractions(repository);
//        }
    }

}