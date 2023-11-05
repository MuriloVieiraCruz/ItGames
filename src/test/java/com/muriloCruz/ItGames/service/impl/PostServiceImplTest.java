package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.dto.PostRequestDto;
import com.muriloCruz.ItGames.dto.PostSavedDto;
import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.Game;
import com.muriloCruz.ItGames.entity.Post;
import com.muriloCruz.ItGames.entity.User;
import com.muriloCruz.ItGames.entity.enums.Availability;
import com.muriloCruz.ItGames.entity.enums.Role;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.GameRepository;
import com.muriloCruz.ItGames.repository.PostRepository;
import com.muriloCruz.ItGames.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Instant;
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
class PostServiceImplTest {

    @InjectMocks
    PostServiceImpl service;

    @Mock
    PostRepository repository;

    @Mock
    UserRepository userRepository;

    @Mock
    GameRepository gameRepository;

    User userTest = new User(1, "lil@gmail.com", "123", "Murilo",
            "780.476.330-15", Status.A, Instant.now(), Instant.now(), null, null, Role.USER);

    Enterprise enterpriseTest = new Enterprise(1, "MuriloEnterprise", Status.A);

    Game gameTest = new Game
            (1, "MurilusGame", "description", Instant.now(),
                    Status.A, "url", enterpriseTest, Arrays.asList());

    Post postTest;
    PostRequestDto serviceRequest;
    PostSavedDto serviceSaved;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        postTest = new Post(null,"description", BigDecimal.TEN, Availability.OPEN, Status.A, gameTest , userTest, Instant.now(), "RETRETEEGEDG");
        serviceRequest = new PostRequestDto("description", BigDecimal.TEN, userTest.getLogin(), gameTest.getId(), "rgrgr");
        serviceSaved = new PostSavedDto(1, "description", BigDecimal.TEN, Availability.OPEN, gameTest.getId());
    }

    @Nested
    class InsertEnterprise {

        @Test
        @DisplayName("Should insert Post from DB")
        void insertServiceCase1() {
            when(userRepository.searchBy(userTest.getLogin())).thenReturn(userTest);
            when(gameRepository.searchBy(gameTest.getId())).thenReturn(gameTest);
            when(repository.save(postTest)).thenReturn(postTest);

            Post postSave = service.insert(serviceRequest);

            assertEquals(postSave, postTest);
            verify(repository).save(postTest);
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not insert Post from DB when user not found in the bank")
        void insertServiceCase2() {
            when(userRepository.searchBy(userTest.getLogin())).thenReturn(null);

            final NullPointerException e = assertThrows(NullPointerException.class, () -> {
                service.insert(serviceRequest);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No user was found linked to the parameters passed"));

            verify(repository, never()).save(any());
            verify(userRepository, times(1)).searchBy(any());
            verify(gameRepository, never()).searchBy(gameTest.getId());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not insert Post from DB when user is inactive in the bank")
        void insertServiceCase3() {
            userTest.setStatus(Status.I);
            when(userRepository.searchBy(userTest.getLogin())).thenReturn(userTest);

            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.insert(serviceRequest);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The entered user is inactive"));

            verify(repository, never()).save(any());
            verify(userRepository, times(1)).searchBy(any());
            verify(gameRepository, never()).searchBy(gameTest.getId());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not insert Post from DB when game not found in the bank")
        void insertServiceCase4() {
            when(userRepository.searchBy(userTest.getLogin())).thenReturn(userTest);
            when(gameRepository.searchBy(gameTest.getId())).thenReturn(null);

            final NullPointerException e = assertThrows(NullPointerException.class, () -> {
                service.insert(serviceRequest);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No game was found linked to the parameters passed"));

            verify(repository, never()).save(any());
            verify(userRepository, times(1)).searchBy(any());
            verify(gameRepository, times(1)).searchBy(gameTest.getId());
            verifyNoMoreInteractions(repository);
        }
        @Test
        @DisplayName("Should not insert Post from DB when game is inactive in the bank")
        void insertServiceCase5() {
            gameTest.setStatus(Status.I);
            when(userRepository.searchBy(userTest.getLogin())).thenReturn(userTest);
            when(gameRepository.searchBy(gameTest.getId())).thenReturn(gameTest);

            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.insert(serviceRequest);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The entered game is inactive"));

            verify(repository, never()).save(any());
            verify(userRepository, times(1)).searchBy(any());
            verify(gameRepository, times(1)).searchBy(gameTest.getId());
            verifyNoMoreInteractions(repository);
        }

    }

    @Nested
    class SearchPost {

        @Test
        @DisplayName("Should search Post from DB")
        void searchServiceCase1() {
            postTest.setId(1);
            when(repository.searchBy(postTest.getId())).thenReturn(postTest);

            Post postSave = service.searchBy(postTest.getId());

            assertEquals(postTest, postSave);
            verify(repository, times(1)).searchBy(postTest.getId());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should got error when service found not exists")
        void searchServiceCase2() {
            when(repository.searchBy(postTest.getId())).thenReturn(null);

            NullPointerException e = assertThrows(NullPointerException.class, () -> {
                service.searchBy(postTest.getId());
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No service was found linked to the parameters entered"));

            verify(repository).searchBy(postTest.getId());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should got error when service found is inactive")
        void searchServiceCase3() {
            postTest.setStatus(Status.I);
            when(repository.searchBy(postTest.getId())).thenReturn(postTest);

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.searchBy(postTest.getId());
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The service is inactive"));

            verify(repository).searchBy(postTest.getId());
            verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    class ListPost {

        @Test
        @DisplayName("Should list Post from DB")
        void listServiceCase1() {
            postTest.setId(1);
            Post postTest2 = new Post(2,"description",
                    BigDecimal.TEN, Availability.OPEN, Status.A, gameTest
                    , userTest, Instant.now(), "RETRETEEGEDG");

            List<Post> postList = Arrays.asList(postTest, postTest2);
            Page<Post> servicePage = new PageImpl<>(postList,
                    PageRequest.of(0, postList.size()),  postList.size());

            when(repository.listBy(
                    BigDecimal.TEN, gameTest, PageRequest.of(0, 15))).thenReturn(servicePage);
            Page<Post> servicesFound = service.listBy(
                    BigDecimal.TEN, gameTest.getId(), PageRequest.of(0, 15));

            assertThat(servicesFound, notNullValue());
            assertThat(servicesFound.getContent(), not(empty()));
            assertThat(servicesFound.getContent().size(), is(2));
            assertThat(servicesFound.getTotalElements(), is((long) postList.size()));

            assertThat(servicesFound.getContent().get(0).getId(), is(1));
            assertThat(servicesFound.getContent().get(0).getGame(), is(gameTest));
            assertThat(servicesFound.getContent().get(0).getUser().getLogin(), is("lil@gmail.com"));
            assertThat(servicesFound.getContent().get(1).getId(), is(2));
            assertThat(servicesFound.getContent().get(1).getGame(), is(gameTest));
            assertThat(servicesFound.getContent().get(1).getUser().getLogin(), is("lil@gmail.com"));
        }
    }

    @Nested
    class UpdateStatusEnterprise {

        @Test
        @DisplayName("Should update Post from DB")
        void updateStatusServiceCase1() {
            postTest.setId(1);
            when(repository.findById(postTest.getId())).thenReturn(Optional.of(postTest));
            doNothing().when(repository).updateStatusBy(postTest.getId(), Status.I);

            service.updateStatusBy(postTest.getId(), Status.I);

            verify(repository).findById(postTest.getId());
            verify(repository).updateStatusBy(postTest.getId(), Status.I);
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not update Post from DB when service found not exists")
        void updateStatusServiceCase2() {
            postTest.setId(1);
            when(repository.findById(postTest.getId())).thenReturn(Optional.ofNullable(null));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.updateStatusBy(postTest.getId(), Status.I);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No service was found linked to the id entered"));

            verify(repository).findById(postTest.getId());
            verify(repository, never()).updateStatusBy(any(), any());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not update Post from DB when status when status is already assigned")
        void updateStatusServiceCase3() {
            postTest.setId(1);
            when(repository.findById(postTest.getId())).thenReturn(Optional.ofNullable(postTest));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.updateStatusBy(postTest.getId(), Status.A);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The entered status is already assigned"));

            verify(repository, times(1)).findById(postTest.getId());
            verify(repository, never()).updateStatusBy(any(), any());
            verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    class ExcludePost {

        @Test
        @DisplayName("Should exclude Post from DB")
        void excludeServiceByIdCase1 () {
            postTest.setId(1);
            when(repository.searchBy(postTest.getId())).thenReturn(postTest);

            Post deletedPost = service.excludeBy(postTest.getId());

            assertNotNull(deletedPost);
            assertEquals(postTest.getId(), deletedPost.getId());

            verify(repository, times(1)).searchBy(postTest.getId());
            verify(repository, times(1)).deleteById(postTest.getId());
            verifyNoMoreInteractions(repository);

        }

        @Test
        @DisplayName("Should not exclude Post from DB when service do not exist")
        void excludeServiceByIdCase2 () {
            when(repository.searchBy(postTest.getId())).thenReturn(null);

            NullPointerException e = assertThrows(NullPointerException.class, () -> {
                service.excludeBy(postTest.getId());
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("Post linked to the parameters was not found"));

            verify(repository, times(1)).searchBy(postTest.getId());
            verify(repository, never()).deleteById(any());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not exclude Post from DB when service is inactive")
        void excludeServiceByIdCase3 () {
            postTest.setStatus(Status.I);
            when(repository.searchBy(postTest.getId())).thenReturn(postTest);

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.excludeBy(postTest.getId());
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The service is inactive"));

            verify(repository, times(1)).searchBy(postTest.getId());
            verify(repository, never()).deleteById(any());
            verifyNoMoreInteractions(repository);
        }
    }
}