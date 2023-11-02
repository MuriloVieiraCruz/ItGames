package com.muriloCruz.ItGames.service.impl;

import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.EnterpriseRepository;
import com.muriloCruz.ItGames.repository.GameRepository;
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


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnterpriseServiceImplTest {

    @InjectMocks
    EnterpriseServiceImpl service;

    @Mock
    EnterpriseRepository repository;

    @Mock
    GameRepository gameRepository;

    Enterprise enterpriseTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        enterpriseTest = new Enterprise(1, "MuriloEnterprise", Status.A);
    }

    @Nested
    class InsertEnterprise {

        @Test
        @DisplayName("Should insert enterprise from DB")
        void insertEnterpriseCase1() {
            when(repository.save(enterpriseTest)).thenReturn(enterpriseTest);

            Enterprise enterpriseSave = service.insert(enterpriseTest);

            assertEquals(enterpriseTest, enterpriseSave);
            verify(repository).searchBy(enterpriseTest.getName());
            verify(repository).save(enterpriseTest);
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not insert enterprise from DB when enterprise already has the same name in the bank")
        void insertEnterpriseCase2() {
            Enterprise enterpriseTest2 = new Enterprise(2, "MuriloEnterprise", Status.A);
            when(repository.searchBy(enterpriseTest.getName())).thenReturn(enterpriseTest2);

            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.insert(enterpriseTest);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("There is already have a enterprise registered with this name"));

            verify(repository, never()).save(any());
            verify(repository, times(1)).searchBy(any());
            verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    class UpdateStatusEnterprise {

        @Test
        @DisplayName("Should update enterprise from DB")
        void updateStatusEnterpriseCase1() {
            when(repository.findById(enterpriseTest.getId())).thenReturn(Optional.of(enterpriseTest));
            doNothing().when(repository).updateStatusBy(enterpriseTest.getId(), Status.I);

            service.updateStatusBy(enterpriseTest.getId(), Status.I);

            verify(repository).findById(enterpriseTest.getId());
            verify(repository).updateStatusBy(enterpriseTest.getId(), Status.I);
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not update Enterprise from DB when enterprise found not exists")
        void updateStatusEnterpriseCase2() {
            when(repository.findById(enterpriseTest.getId())).thenReturn(Optional.ofNullable(null));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.updateStatusBy(enterpriseTest.getId(), Status.I);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No enterprise was found linked to the parameters entered"));

            verify(repository).findById(enterpriseTest.getId());
            verify(repository, never()).updateStatusBy(any(), any());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not update enterprise from DB when status is already assigned")
        void updateStatusEnterpriseCase3() {
            when(repository.findById(enterpriseTest.getId())).thenReturn(Optional.ofNullable(enterpriseTest));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.updateStatusBy(enterpriseTest.getId(), Status.A);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The status entered is already assigned"));

            verify(repository, times(1)).findById(enterpriseTest.getId());
            verify(repository, never()).updateStatusBy(any(), any());
            verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    class SearchEnterprise {

        @Test
        @DisplayName("Should search enterprise from DB")
        void searchEnterpriseCase1() {
            when(repository.findById(enterpriseTest.getId())).thenReturn(Optional.of(enterpriseTest));

            Enterprise enterpriseSave = service.searchBy(enterpriseTest.getId());

            assertEquals(enterpriseTest, enterpriseSave);
            verify(repository, times(1)).findById(enterpriseTest.getId());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should got error when enterprise found not exists")
        void searchEnterpriseCase2() {
            when(repository.findById(enterpriseTest.getId())).thenReturn(Optional.ofNullable(null));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.searchBy(enterpriseTest.getId());
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No enterprise was found linked to the parameters entered"));

            verify(repository).findById(enterpriseTest.getId());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should got error when enterprise found is inactive")
        void searchEnterpriseCase3() {
            enterpriseTest.setStatus(Status.I);
            when(repository.findById(enterpriseTest.getId())).thenReturn(Optional.ofNullable(enterpriseTest));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.searchBy(enterpriseTest.getId());
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The enterprise informed is inactive"));

            verify(repository).findById(enterpriseTest.getId());
            verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    class ListEnterprise {

        @Test
        @DisplayName("Should list Enterprise from DB")
        void listEnterpriseCase1() {
            Enterprise enterpriseTest2 = new Enterprise(2, "MuriloEnterprise", Status.A);
            List<Enterprise> enterpriseList = Arrays.asList(enterpriseTest, enterpriseTest2);
            Page<Enterprise> enterprisePage = new PageImpl<>(enterpriseList, PageRequest.of(0, enterpriseList.size()),  enterpriseList.size());

            when(repository.listBy("rilo", PageRequest.of(0, 15))).thenReturn(enterprisePage);
            Page<Enterprise> enterprisesFound = service.listBy("rilo", PageRequest.of(0, 15));

            assertThat(enterprisesFound, notNullValue());
            assertThat(enterprisesFound.getContent(), not(empty()));
            assertThat(enterprisesFound.getContent().size(), is(2));
            assertThat(enterprisesFound.getTotalElements(), is((long)enterpriseList.size()));

            assertThat(enterprisesFound.getContent().get(0).getId(), is(1));
            assertThat(enterprisesFound.getContent().get(0).getName(), is("MuriloEnterprise"));
            assertThat(enterprisesFound.getContent().get(1).getId(), is(2));
            assertThat(enterprisesFound.getContent().get(1).getName(), is("MuriloEnterprise"));
        }
    }

    @Nested
    class ExcludeEnterprise {

        @Test
        @DisplayName("Should exclude Enterprise from DB")
        void excludeEnterpriseByIdCase1 () {
            when(repository.findById(1)).thenReturn(Optional.ofNullable(enterpriseTest));
            when(gameRepository.countGamesLinkedToThe(enterpriseTest.getId())).thenReturn(0);

            Enterprise deletedEnterprise = service.excludeBy(enterpriseTest.getId());

            assertNotNull(deletedEnterprise);
            assertEquals(enterpriseTest.getId(), deletedEnterprise.getId());

            verify(repository, times(1)).findById(enterpriseTest.getId());
            verify(repository, times(1)).deleteById(enterpriseTest.getId());
            verifyNoMoreInteractions(repository);

        }

        @Test
        @DisplayName("Should not exclude Enterprise from DB when enterprise do not exist")
        void excludeEnterpriseByIdCase2 () {
            when(repository.findById(enterpriseTest.getId())).thenReturn(Optional.ofNullable(null));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.excludeBy(enterpriseTest.getId());
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No enterprise was found linked to the parameters entered"));

            verify(repository, times(1)).findById(enterpriseTest.getId());
            verify(gameRepository, never()).countGamesLinkedToThe(enterpriseTest.getId());
            verify(repository, never()).deleteById(any());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not exclude Enterprise from DB when enterprise is inactive")
        void excludeEnterpriseByIdCase3 () {
            enterpriseTest.setStatus(Status.I);
            when(repository.findById(enterpriseTest.getId())).thenReturn(Optional.ofNullable(enterpriseTest));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.excludeBy(enterpriseTest.getId());
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The enterprise informed is inactive"));

            verify(repository, times(1)).findById(enterpriseTest.getId());
            verify(gameRepository, never()).countGamesLinkedToThe(enterpriseTest.getId());
            verify(repository, never()).deleteById(any());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Should not exclude Enterprise from DB when enterprise is linked to another game")
        void excludeEnterpriseByIdCase4 () {
            when(repository.findById(enterpriseTest.getId())).thenReturn(Optional.ofNullable(enterpriseTest));
            when(gameRepository.countGamesLinkedToThe(enterpriseTest.getId())).thenReturn(1);

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.excludeBy(enterpriseTest.getId());
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("This enterprise is linked to '1' games"));

            verify(repository, times(1)).findById(enterpriseTest.getId());
            verify(gameRepository, times(1)).countGamesLinkedToThe(enterpriseTest.getId());
            verify(repository, never()).deleteById(any());
            verifyNoMoreInteractions(repository);
        }
    }
}
