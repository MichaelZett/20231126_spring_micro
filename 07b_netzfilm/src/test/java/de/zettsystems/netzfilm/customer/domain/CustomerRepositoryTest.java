package de.zettsystems.netzfilm.customer.domain;

import de.zettsystems.netzfilm.configuration.TestContainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // we don't want in memory DB
@Import(TestContainersConfiguration.class)
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository testee;

    @Test
    void shouldFindByUuid() {
        final Optional<Customer> optionalCustomer = testee.findByUuid(UUID.fromString("97eb8fc7-3a5f-4774-bdc7-aeeef0964ff9"));

        assertThat(optionalCustomer).isNotEmpty();
        final Customer customer = optionalCustomer.get();
        assertThat(customer)
                .usingRecursiveComparison()
                .ignoringFields("id", "uuid")
                .isEqualTo(new Customer("Petra", "Meyer", LocalDate.of(1963, 7, 11), false));
    }
}