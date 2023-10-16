package com.muriloCruz.ItGames.repository;

import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;
import jakarta.persistence.EntityManager;
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
class EnterpriseRepositoryTest {

    @Autowired
    EnterpriseRepository repository;

    @Autowired
    EntityManager entityManager;

    @Nested
    class SearchBy {

        @Test
        @DisplayName("Should get Enterprise successfully from DB")
        void searchByEnterpriseByNameCase1() {
            createEnterprises();

            Enterprise enterprise = repository.searchBy("MuriloEnterprise");
            assertThat(enterprise).isNotNull();
        }

        @Test
        @DisplayName("Should not get Enterprise from DB when enterprise not exists")
        void searchByEnterpriseByNameCase2() {
            createEnterprises();

            Enterprise enterprise = repository.searchBy("Murilo");
            assertThat(enterprise).isNull();
        }
    }

    @Nested
    class FindBy {

        @Test
        @DisplayName("Should get Enterprise list successfully from DB")
        void findByEnterpriseByNameCase1() {
            createEnterprises();

            Page<Enterprise> list = repository.listBy("muri",PageRequest.of(0, 15));
            assertThat(list).isNotNull();
        }

        @Test
        @DisplayName("Should not get Enterprise list from DB when enterprise not exist")
        void findByEnterpriseByNameCase2() {
            createEnterprises();

            Page<Enterprise> page = repository.listBy("rrrr",PageRequest.of(0, 15));
            assertThat(page.getContent()).isEmpty();
            assertThat(page.getTotalElements()).isZero();
        }
    }

    @Nested
    class UpdateBy {

        @Test
        @DisplayName("Should update Enterprise from DB")
        void updateByEnterpriseByNameCase1() {
            createEnterprises();

            repository.updateStatusBy(1,  Status.I);
            Enterprise updatedEnterprise = repository.findById(1).orElse(null);

            assertThat(updatedEnterprise).isNotNull();
            assertThat(updatedEnterprise.getStatus()).isEqualByComparingTo(Status.I);
        }

        @Test
        @DisplayName("Should update Enterprise from DB")
        void updateByEnterpriseByNameCase2() {
            createEnterprises();

            repository.updateStatusBy(5,  Status.I);
            Enterprise updatedEnterprise = repository.findById(5).orElse(null);

            assertThat(updatedEnterprise).isNull();
        }
    }

    private void createEnterprises() {
        Enterprise enterpriseTest = new Enterprise(null, "MuriloEnterprise", Status.A);
        Enterprise enterpriseTest2 = new Enterprise(null, "MuriloEnterprisesMalacoy", Status.A);
        this.entityManager.persist(enterpriseTest);
        this.entityManager.persist(enterpriseTest2);
    }
}