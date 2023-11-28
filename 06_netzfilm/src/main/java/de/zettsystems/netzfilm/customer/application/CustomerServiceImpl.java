package de.zettsystems.netzfilm.customer.application;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.customer.domain.CustomerRepository;
import de.zettsystems.netzfilm.customer.values.CustomerCreationTo;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import de.zettsystems.netzfilm.customer.values.NoSuchCustomerException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final EntityManager entityManager;

    @Override
    public List<CustomerTo> getAllCustomers() {
        return customerRepository.findAll().stream().map(Customer::toTo).toList();
    }

    @Override
    public UUID addCustomer(CustomerCreationTo customer) {
        final Customer saved = customerRepository.save(new Customer(customer.name(), customer.lastName(), customer.birthdate(), customer.vip() != null && customer.vip()));
        return saved.getUuid();
    }

    @Override
    @Transactional
    public void deleteCustomer(UUID uuid) {
        customerRepository.deleteByUuid(uuid);
    }

    @Override
    public CustomerTo getCustomer(UUID uuid) {
        return customerRepository.findByUuid(uuid).orElseThrow(() -> new NoSuchCustomerException(uuid)).toTo();
    }

    @Override
    @Transactional
    public void updateCustomer(CustomerTo customer) {
        final Customer customerEntity = customerRepository.findByUuid(customer.uuid()).orElseThrow(() -> new NoSuchCustomerException(customer.uuid()));
        // entity aus Hibernate lösen
        entityManager.detach(customerEntity);
        // ändern
        customerEntity.update(customer);
        // wieder einfügen, andernfalls würde die Version einfach aktualisiert werden
        customerRepository.save(customerEntity);
    }
}
