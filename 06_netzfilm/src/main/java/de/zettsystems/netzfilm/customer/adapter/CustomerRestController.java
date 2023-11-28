package de.zettsystems.netzfilm.customer.adapter;

import de.zettsystems.netzfilm.customer.application.CustomerService;
import de.zettsystems.netzfilm.customer.values.CustomerCreationTo;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import de.zettsystems.netzfilm.customer.values.NoSuchCustomerException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Customers", description = "the customers API to do CRUD")
class CustomerRestController {
    private final CustomerService customerService;

    @Operation(summary = "Get all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "got all customers", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerTo.class))})})
    @GetMapping("/")
    public Collection<CustomerTo> findAllCustomers() {
        return customerService.getAllCustomers();
    }

    @Operation(summary = "Get a customer by uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the customer", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerTo.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid uuid supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "customer not found", content = @Content)})
    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerTo> getCustomer(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.of(Optional.ofNullable(customerService.getCustomer(uuid)));
        } catch (NoSuchCustomerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update a customer by uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated the customer", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid uuid supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "customer not found", content = @Content)})
    @PutMapping("/{uuid}")
    public ResponseEntity<Void> update(@Valid @RequestBody CustomerTo customerTo) {
        try {
            customerService.updateCustomer(customerTo);
            return ResponseEntity.ok().build();
        } catch (NoSuchCustomerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a customer by uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "customer with given uuid deleted or did not exist", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid uuid supplied", content = @Content)})
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        customerService.deleteCustomer(uuid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created the customer", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content)})
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