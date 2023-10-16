package com.muriloCruz.ItGames.service;

import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.EnterpriseRepository;
import com.muriloCruz.ItGames.service.impl.EnterpriseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class EnterpriseServiceImplTest {

    @InjectMocks
    EnterpriseServiceImpl service;

    @Mock
    EnterpriseRepository repository;

    Enterprise enterpriseTest;
    Enterprise enterpriseTest2;

    Enterprise enterpriseTest3;
    @BeforeEach
    public void setUp() {
        enterpriseTest = new Enterprise(1, "MuriloEnterprise", Status.A);
        enterpriseTest2 = new Enterprise(null, "MuriloEnterprise", Status.A);
        enterpriseTest3 = new Enterprise(2, "MuriloEnterprise", Status.A);
    }

    @Nested
    class SaveEnterpriseTest {

        @Test
        void mustInsertEnterpriseSuccessfully() {
            when(repository.save(enterpriseTest)).thenReturn(enterpriseTest);

            Enterprise enterpriseSave = service.insert(enterpriseTest);

            assertEquals(enterpriseTest, enterpriseSave);
            verify(repository).searchBy(enterpriseTest.getName());
            verify(repository).save(enterpriseTest);
            verifyNoMoreInteractions(repository);
        }

        @Test
        void shouldNotCallRepositorySameCompanyCase() {
            when(repository.save(enterpriseTest)).thenReturn(enterpriseTest);
            when(repository.searchBy(enterpriseTest2.getName())).thenReturn(enterpriseTest);

            service.insert(enterpriseTest);

            final IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.insert(enterpriseTest2);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("There is already a enterprise registered with this name"));

            verify(repository).save(enterpriseTest);
            verify(repository, times(2)).searchBy(any());
            verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    class SearchEnterpriseTest {

        @Test
        void mustSearchEnterpriseSuccessfully() {
            when(repository.findById(enterpriseTest.getId())).thenReturn(Optional.of(enterpriseTest));

            Enterprise enterpriseSave = service.searchBy(enterpriseTest.getId());

            assertEquals(enterpriseTest, enterpriseSave);
            verify(repository).findById(enterpriseTest.getId());
            verifyNoMoreInteractions(repository);
        }

        @Test
        void shouldNotReturnCaseEnterpriseFoundIsNull() {
            when(repository.findById(9999)).thenReturn(Optional.ofNullable(null));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.searchBy(9999);
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("No enterprise was found linked to the parameters entered"));

            verify(repository).findById(9999);
            verifyNoMoreInteractions(repository);
        }

        @Test
        void shouldNotReturnCaseEnterpriseFoundIsInactive() {
            Enterprise enterprise = new Enterprise(3, "", Status.I);
            when(repository.findById(enterprise.getId())).thenReturn(Optional.ofNullable(enterprise));

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                service.searchBy(enterprise.getId());
            });

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("The enterprise informed is inactive"));

            verify(repository).findById(enterprise.getId());
            verifyNoMoreInteractions(repository);
        }

        @Nested
        class ListByEnterpriseNameTest {

            @Test
            void mustSearchEnterpriseSuccessfully() {
                List<Enterprise> enterpriseList = Arrays.asList(enterpriseTest, enterpriseTest3);
                Page<Enterprise> enterprisePage = new PageImpl<>(enterpriseList, PageRequest.of(0, enterpriseList.size()),  enterpriseList.size());

                when(repository.listBy("%rilo%", PageRequest.of(0, 15))).thenReturn(enterprisePage);
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
        class UpdateStatusByEnterpriseId {

            @Test
            void mustUpdateStatusByEnterpriseIdSuccessfully() {
                when(repository.findById(1)).thenReturn(Optional.of(enterpriseTest));
                doNothing().when(repository).updateStatusBy(1, Status.I);

                service.updateStatusBy(1, Status.I);

                verify(repository).findById(1);
                verify(repository).updateStatusBy(1, Status.I);
                verifyNoMoreInteractions(repository);
            }

            @Test
            void shouldNotCallRepositoryEnterpriseIdNullCase() {
                when(repository.findById(9999)).thenReturn(Optional.ofNullable(null));

                IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                    service.searchBy(9999);
                });

                assertThat(e, notNullValue());
                assertThat(e.getMessage(), is("No enterprise was found linked to the parameters entered"));

                verify(repository).findById(9999);
                verify(repository, never()).updateStatusBy(any(), any());
                verifyNoMoreInteractions(repository);
            }

            @Test
            void shouldNotCallRepositoryEnterpriseInactiveCase() {
                Enterprise enterprise = new Enterprise(3, "", Status.I);
                when(repository.findById(enterprise.getId())).thenReturn(Optional.ofNullable(enterprise));

                IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                    service.searchBy(enterprise.getId());
                });

                assertThat(e, notNullValue());
                assertThat(e.getMessage(), is("The enterprise informed is inactive"));

                verify(repository).findById(enterprise.getId());
                verify(repository, never()).updateStatusBy(any(), any());
                verifyNoMoreInteractions(repository);
            }
        }

        @Nested
        class ExcludeByEnterpriseId {

            @Test
            void mustExcludeByEnterpriseIdSuccessfully () {

            }
        }
    }
}
