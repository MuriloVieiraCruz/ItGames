package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.dto.UserRequestDto;
import com.muriloCruz.ItGames.dto.UserSavedDto;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.PostRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

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
class UserPostImplTest {

    @InjectMocks
    UserServiceImpl service;

    @Mock
    UserRepository repository;

    @Mock
    PostRepository postRepository;

    User userTest;
    UserSavedDto userTest2;
    UserRequestDto userTest3;

    @BeforeEach
    public void setUp() {
        userTest = new User(1, "lil@gmail.com", "123", "Murilo",
                "780.476.330-15", Status.A, Instant.now(), Instant.now(), null, new ArrayList<>());
        userTest2 = new UserSavedDto(1, "lil@gmail.com", "123", "Murilo Test",
                "780.476.330-15", Instant.now());
    }

    @Nested
    class InsertUser {

        @Test
        @DisplayName("Should insert User from DB")
        void insertEnterpriseCase1() {
            userTest3 = new UserRequestDto("lil@gmail.com", "123", "Murilo",
                    "780.476.330-15", Instant.now());

            userTest = new User(null, "lil@gmail.com", "123", "Murilo",
                    "780.476.330-15", Status.A, Instant.now(), Instant.now(), null, new ArrayList<>());

            when(repository.save(userTest)).thenReturn(userTest);

            User userSave = service.insert(userTest3);

            assertEquals(userTest, userSave);
            verify(repository, times(1)).searchBy(userTest.getLogin());
            verify(repository, times(1)).save(userTest);
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not insert User from DB when enterprise already has the same name in the bank")
        void insertEnterpriseCase2() {
            userTest3 = new UserRequestDto("lil@gmail.com", "123", "Murilo",
                    "780.476.330-15", Instant.now());

            when(repository.searchBy(userTest3.getLogin())).thenReturn(userTest);

            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.insert(userTest3);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("There is already a user registered with this login"));

            verify(repository, never()).save(any());
            verify(repository, times(1)).searchBy(any());
            verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    class SearchUser {

        @Test
        @DisplayName("Should search User from DB")
        void searchUserCase1() {
            when(repository.findById(userTest.getId())).thenReturn(Optional.of(userTest));

            User userSave = service.searchBy(userTest.getId());

            assertEquals(userTest, userSave);
            verify(repository, times(1)).findById(userTest.getId());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should got error when user found not exists")
        void searchUserCase2() {
            when(repository.findById(5)).thenReturn(Optional.ofNullable(null));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.searchBy(5);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("User linked to the parameters was not found"));

            verify(repository).findById(5);
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should got error when enterprise found is inactive")
        void searchUserCase3() {
            userTest.setStatus(Status.I);
            when(repository.findById(userTest.getId())).thenReturn(Optional.ofNullable(userTest));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.searchBy(userTest.getId());
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The user is inactive"));

            verify(repository).findById(userTest.getId());
            verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    class ListUser {

        @Test
        @DisplayName("Should list User from DB")
        void listUserCase1() {
            User userTest4 = new User(2, "loiola@gmail.com", "123", "Murilo",
                    "780.476.330-15", Status.A, Instant.now(), Instant.now(), null, new ArrayList<>());

            List<User> userList = Arrays.asList(userTest, userTest4);
            Page<User> userPage = new PageImpl<>(userList, PageRequest.of(0, userList.size()),  userList.size());

            when(repository.listBy("rilo", PageRequest.of(0, 15))).thenReturn(userPage);
            Page<User> usersFound = service.listBy("rilo", PageRequest.of(0, 15));

            assertThat(usersFound, notNullValue());
            assertThat(usersFound.getContent(), not(empty()));
            assertThat(usersFound.getContent().size(), is(2));
            assertThat(usersFound.getTotalElements(), is((long)userList.size()));

            assertThat(usersFound.getContent().get(0).getId(), is(1));
            assertThat(usersFound.getContent().get(0).getLogin(), is("lil@gmail.com"));
            assertThat(usersFound.getContent().get(1).getId(), is(2));
            assertThat(usersFound.getContent().get(1).getLogin(), is("loiola@gmail.com"));
        }
    }

    @Nested
    class UpdateStatusEnterprise {

//        @Test
//        @DisplayName("Should update User from DB")
//        void updateStatusUserCase1() {
//            User userTest1 = new User(1, "lil@gmail.com", "123", "Murilo",
//                    "780.476.330-15", Status.A, Instant.now(), Instant.now(), null, new ArrayList<>());
//
//            //when(repository.findById(userTest1.getId())).thenReturn(Optional.of(userTest1));
//
//            service.updateStatusBy(1, Status.I);
//
//            User userUpdated = repository.findById(userTest1.getId()).get();
//
//            assertEquals(Status.I, userUpdated.getStatus());
//            verify(repository).findById(userTest1.getId());
//            verify(repository).updateStatusBy(userTest1.getId(), Status.I);
//            verifyNoMoreInteractions(repository);
//        }

        @Test
        @DisplayName("Should not update User from DB when user found not exists")
        void updateStatusUserCase2() {
            when(repository.findById(5)).thenReturn(Optional.ofNullable(null));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.updateStatusBy(5, Status.I);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("User linked to the parameters was not found"));

            verify(repository).findById(5);
            verify(repository, never()).updateStatusBy(any(), any());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not update User from DB when status is already assigned")
        void updateStatusUserCase3() {
            userTest.setStatus(Status.I);
            when(repository.findById(userTest.getId())).thenReturn(Optional.ofNullable(userTest));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.updateStatusBy(userTest.getId(), Status.I);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The status entered is already assigned"));

            verify(repository, times(1)).findById(userTest.getId());
            verify(repository, never()).updateStatusBy(any(), any());
            verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    class ExcludeUser {

        @Test
        @DisplayName("Should exclude User from DB")
        void excludeUserByIdCase1 () {
            User userTest1 = new User(1, "lil@gmail.com", "500000", "Murilo",
                    "780.476.330-15", Status.A, Instant.now(), Instant.now(), null, new ArrayList<>());

            when(repository.findById(userTest1.getId())).thenReturn(Optional.ofNullable(userTest1));
            User deletedUser = service.excludeBy(userTest1.getId());

            assertNotNull(deletedUser);
            assertEquals(userTest1.getId(), deletedUser.getId());

            verify(repository, times(1)).findById(userTest1.getId());
            verify(repository, times(1)).deleteById(userTest1.getId());
            verifyNoMoreInteractions(repository);

        }

        @Test
        @DisplayName("Should not exclude User from DB when user do not exist")
        void excludeUserByIdCase2 () {
            when(repository.findById(1256)).thenReturn(Optional.ofNullable(null));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.excludeBy(1256);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("User linked to the parameters was not found"));

            verify(repository, times(1)).findById(1256);
            //verify(gameRepository.countGamesLinkedToThe(any()), never());
            verify(repository, never()).deleteById(any());
            verifyNoMoreInteractions(repository);
        }
    }
}