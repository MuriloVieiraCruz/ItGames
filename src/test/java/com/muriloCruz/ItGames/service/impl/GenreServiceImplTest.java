package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.dto.UserRequestDto;
import com.muriloCruz.ItGames.dto.UserSavedDto;
import com.muriloCruz.ItGames.entity.Genre;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.GenreRepository;
import com.muriloCruz.ItGames.repository.ServiceRepository;
import com.muriloCruz.ItGames.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @InjectMocks
    GenreServiceImpl service;

    @Mock
    GenreRepository repository;

    Genre genreTest;
    Genre genreTest2;

    @BeforeEach
    public void setUp() {
        genreTest = new Genre(1, "Action", Status.A);
    }

    @Nested
    class InsertUser {

        @Test
        @DisplayName("Should insert Genre from DB")
        void insertGenreCase1() {

            when(repository.save(genreTest)).thenReturn(genreTest);

            Genre genreSave = service.insert(genreTest);

            assertEquals(genreTest, genreSave);
            verify(repository, times(1)).searchBy(genreTest.getName());
            verify(repository, times(1)).save(genreTest);
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should insert Genre from DB when genre is persisted")
        void insertGenreCase2() {
            genreTest2 = new Genre(null, "Action", Status.A);

            when(repository.searchBy(genreTest2.getName())).thenReturn(genreTest);

            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.insert(genreTest2);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("There is already a registered genre with this name"));

            verify(repository, never()).save(any());
            verify(repository, times(1)).searchBy(genreTest2.getName());
            verify(repository, never()).save(any());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not insert Genre from DB when genre already has the same name in the bank")
        void insertGenreCase3() {
            genreTest2 = new Genre(2, "Action", Status.A);

            when(repository.searchBy(genreTest.getName())).thenReturn(genreTest2);

            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.insert(genreTest);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("There is already a registered genre with this name"));

            verify(repository, never()).save(any());
            verify(repository, times(1)).searchBy(any());
            verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    class SearchUser {

        @Test
        @DisplayName("Should search Genre from DB")
        void searchGenreCase1() {
            when(repository.findById(genreTest.getId())).thenReturn(Optional.of(genreTest));

            Genre genreSave = service.searchBy(genreTest.getId());

            assertEquals(genreTest, genreSave);
            verify(repository, times(1)).findById(genreTest.getId());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should got error when genre found not exists")
        void searchGenreCase2() {

            when(repository.findById(5)).thenReturn(Optional.ofNullable(null));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.searchBy(5);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No genre was found linked to the parameters entered"));

            verify(repository).findById(5);
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should got error when genre found is inactive")
        void searchGenreCase3() {
            genreTest.setStatus(Status.I);
            when(repository.findById(genreTest.getId())).thenReturn(Optional.ofNullable(genreTest));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.searchBy(genreTest.getId());
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The genre informed is inactive"));

            verify(repository).findById(genreTest.getId());
            verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    class ListGenre {

        @Test
        @DisplayName("Should list Genre from DB")
        void listGenreCase1() {
            genreTest2 = new Genre(2, "Meditation", Status.A);

            List<Genre> genreList = Arrays.asList(genreTest, genreTest2);
            Page<Genre> genrePage = new PageImpl<>
                    (genreList, PageRequest.of(0, genreList.size()),  genreList.size());

            when(repository.listBy("tion", PageRequest.of(0, 15))).thenReturn(genrePage);

            Page<Genre> genreFound = service.listBy("tion", PageRequest.of(0, 15));

            assertThat(genreFound, notNullValue());
            assertThat(genreFound.getContent(), not(empty()));
            assertThat(genreFound.getContent().size(), is(2));
            assertThat(genreFound.getTotalElements(), is((long) genreList.size()));

            assertThat(genreFound.getContent().get(0).getId(), is(1));
            assertThat(genreFound.getContent().get(0).getName(), is("Action"));
            assertThat(genreFound.getContent().get(1).getId(), is(2));
            assertThat(genreFound.getContent().get(1).getName(), is("Meditation"));
        }
    }

    @Nested
    class UpdateStatusGenre {

        @Test
        @DisplayName("Should update Genre from DB")
        void updateStatusUserCase1() {

            when(repository.findById(genreTest.getId())).thenReturn(Optional.of(genreTest));

            service.updateStatusBy(genreTest.getId(), Status.I);
            Genre genreUpdated = repository.findById(genreTest.getId()).get();

            assertEquals(Status.I, genreUpdated.getStatus());
            verify(repository).findById(genreUpdated.getId());
            verify(repository).updateStatusBy(genreUpdated.getId(), Status.I);
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not update Genre from DB when genre found not exists")
        void updateStatusGenreCase2() {
            when(repository.findById(5)).thenReturn(Optional.ofNullable(null));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.updateStatusBy(5, Status.I);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No genre was found linked to the parameters entered"));

            verify(repository).findById(5);
            verify(repository, never()).updateStatusBy(any(), any());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should update User from DB")
        void updateStatusUserCase3() {
            genreTest.setStatus(Status.I);
            when(repository.findById(genreTest.getId())).thenReturn(Optional.ofNullable(genreTest));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.updateStatusBy(genreTest.getId(), Status.I);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The status entered is already assigned"));

            verify(repository, times(1)).findById(genreTest.getId());
            verify(repository, never()).updateStatusBy(any(), any());
            verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    class ExcludeUser {

//        @Test
//        @DisplayName("Should exclude User from DB")
//        void excludeUserByIdCase1 () {
//            User userTest1 = new User(1, "lil@gmail.com", "500000", "Murilo",
//                    "780.476.330-15", Status.A, Instant.now(), Instant.now(), null, new ArrayList<>());
//
//            when(repository.findById(userTest1.getId())).thenReturn(Optional.ofNullable(userTest1));
//            User deletedUser = service.excludeBy(userTest1.getId());
//
//            assertNotNull(deletedUser);
//            assertEquals(userTest1.getId(), deletedUser.getId());
//
//            verify(repository, times(1)).findById(userTest1.getId());
//            verify(repository, times(1)).deleteById(userTest1.getId());
//            verifyNoMoreInteractions(repository);
//
//        }
//
//        @Test
//        @DisplayName("Should not exclude User from DB when user do not exist")
//        void excludeUserByIdCase2 () {
//            when(repository.findById(1256)).thenReturn(Optional.ofNullable(null));
//
//            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
//                service.excludeBy(1256);
//            });
//
//            assertThat(e, notNullValue());
//            assertThat(e.getMessage(), is("User linked to the parameters was not found"));
//
//            verify(repository, times(1)).findById(1256);
//            //verify(gameRepository.countGamesLinkedToThe(any()), never());
//            verify(repository, never()).deleteById(any());
//            verifyNoMoreInteractions(repository);
//        }
    }
}