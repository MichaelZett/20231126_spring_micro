package de.zettsystems.netzfilm.customer.domain;


import de.zettsystems.netzfilm.customer.values.CustomerTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    Customer testee;

    @BeforeEach
    void setup() {
        testee = new Customer("Frank", "Meyer", LocalDate.of(2001, 9, 8), false);
    }

    @Test
    void shouldProvideToWithSameData() {
        final CustomerTo customerTo = testee.toTo();

        // the method can be used for different types, it seems not to be possible to get rid of the warning
        assertThat(customerTo).usingRecursiveComparison().isEqualTo(testee);
    }

    @Test
    void shouldUpdate() {
        final LocalDate newBirthday = LocalDate.of(2003, 9, 8);
        testee.update(new CustomerTo(testee.getUuid(), testee.getName(), testee.getLastName(), newBirthday, true, 0));

        assertThat(testee.getBirthdate()).isEqualTo(newBirthday);
        assertThat(testee.isVip()).isEqualTo(true);
    }
}