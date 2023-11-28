package de.zettsystems.netzfilm.customer.application;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.customer.domain.CustomerRepository;
import de.zettsystems.netzfilm.customer.values.CustomerCreationTo;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import de.zettsystems.netzfilm.customer.values.NoSuchCustomerException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    public static final String SCHMIDT = "Schmidt";
    public static final String MICHAEL = "Michael";
    public static final LocalDate BIRTHDATE = LocalDate.of(2011, 8, 4);
    public static final boolean VIP = Boolean.FALSE;
    @Mock
    CustomerRepository customerRepository;

    @Mock
    EntityManager entityManager;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @InjectMocks
    CustomerServiceImpl testee;

    @Test
    void shouldGetAllCustomers() {
        Customer michaelSchmidt = new Customer(MICHAEL, SCHMIDT, BIRTHDATE, VIP);
        when(customerRepository.findAll()).thenReturn(List.of(michaelSchmidt));

        final List<CustomerTo> allCustomers = testee.getAllCustomers();

        assertThat(allCustomers).usingRecursiveComparison().isEqualTo(List.of(michaelSchmidt));
    }

    @Test
    void shouldAddCustomer() {
        when(customerRepository.save(any(Customer.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        final UUID uuid = testee.addCustomer(new CustomerCreationTo(MICHAEL, SCHMIDT, BIRTHDATE, VIP));

        verify(customerRepository).save(customerArgumentCaptor.capture());
        final Customer result = customerArgumentCaptor.getValue();

        assertThat(result.getName()).isEqualTo(MICHAEL);
        assertThat(result.getLastName()).isEqualTo(SCHMIDT);
        assertThat(result.getBirthdate()).isEqualTo(BIRTHDATE);
        assertThat(result.isVip()).isEqualTo(VIP);
        assertThat(result.getVersion()).isZero();
        assertThat(result.getUuid()).isEqualTo(uuid);
    }

    @Test
    void shouldGetCustomer() {
        Customer michaelSchmidt = new Customer(MICHAEL, SCHMIDT, BIRTHDATE, VIP);
        when(customerRepository.findByUuid(michaelSchmidt.getUuid())).thenReturn(Optional.of(michaelSchmidt));

        final CustomerTo result = testee.getCustomer(michaelSchmidt.getUuid());

        assertThat(result).usingRecursiveComparison().isEqualTo(michaelSchmidt);
    }

    @Test
    void shouldThrowExceptionsForMissingUuid() {
        when(customerRepository.findByUuid(any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> testee.getCustomer(UUID.randomUUID())).isInstanceOf(NoSuchCustomerException.class);
    }

    @Test
    void updateMovie() {
        Customer michaelSchmidt = new Customer(MICHAEL, SCHMIDT, BIRTHDATE.minusDays(1L), VIP);
        when(customerRepository.findByUuid(michaelSchmidt.getUuid())).thenReturn(Optional.of(michaelSchmidt));

        testee.updateCustomer(new CustomerTo(michaelSchmidt.getUuid(), michaelSchmidt.getName(), michaelSchmidt.getLastName(), BIRTHDATE, VIP, 0L));

        verify(entityManager).detach(michaelSchmidt);
        verify(customerRepository).save(customerArgumentCaptor.capture());
        final Customer result = customerArgumentCaptor.getValue();
        assertThat(result.getBirthdate()).isEqualTo(BIRTHDATE);
    }

    @Test
    void deleteMovie() {
        final UUID uuid = UUID.randomUUID();

        testee.deleteCustomer(uuid);

        verify(customerRepository).deleteByUuid(uuid);
    }

}