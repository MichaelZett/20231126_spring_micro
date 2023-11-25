package de.zettsystems.netzfilm.customer.adapter;

import de.zettsystems.netzfilm.customer.application.CustomerService;
import de.zettsystems.netzfilm.customer.values.CustomerCreationTo;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import de.zettsystems.netzfilm.customer.values.NoSuchCustomerException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
class CustomerRestController {
    private final CustomerService customerService;

    @GetMapping("/")
    public Collection<CustomerTo> findAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerTo> getCustomer(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.of(Optional.ofNullable(customerService.getCustomer(uuid)));
        } catch (NoSuchCustomerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Void> update(@Valid @RequestBody CustomerTo customerTo) {
        try {
            customerService.updateCustomer(customerTo);
            return ResponseEntity.ok().build();
        } catch (NoSuchCustomerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        customerService.deleteCustomer(uuid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@Valid @RequestBody CustomerCreationTo customerTo) {
        final UUID uuid = customerService.addCustomer(customerTo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(uuid)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}