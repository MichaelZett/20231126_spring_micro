package de.zettsystems.netzfilm.customer.adapter;


import de.zettsystems.netzfilm.customer.application.CustomerService;
import de.zettsystems.netzfilm.customer.values.CustomerTo;
import de.zettsystems.netzfilm.customer.values.NoSuchCustomerException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/hateos/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "the customers hateoas API to do CRUD")
class CustomerHateoasRestController {
    private final CustomerService customerService;

    @Operation(summary = "Get all customers hateoas style")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "got all customers", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerTo.class))})})
    @GetMapping("/")
    public CollectionModel<EntityModel<CustomerTo>> findAllCustomers() {
        List<EntityModel<CustomerTo>> customers = customerService.getAllCustomers().stream()
                .map(customer -> EntityModel.of(customer,
                        WebMvcLinkBuilder.linkTo(methodOn(CustomerHateoasRestController.class).getCustomer(customer.uuid())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(methodOn(CustomerHateoasRestController.class).findAllCustomers()).withRel("customers")))
                .toList();

        return CollectionModel.of(customers,
                WebMvcLinkBuilder.linkTo(methodOn(CustomerHateoasRestController.class).findAllCustomers()).withSelfRel());
    }

    @Operation(summary = "Get a customer by uuid hateoas style")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the customer", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerTo.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid uuid supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "customer not found", content = @Content)})
    @GetMapping("/{uuid}")
    public ResponseEntity<EntityModel<CustomerTo>> getCustomer(@PathVariable UUID uuid) {
        try {
            CustomerTo customer = customerService.getCustomer(uuid);
            EntityModel<CustomerTo> resource = EntityModel.of(customer,
                    WebMvcLinkBuilder.linkTo(methodOn(CustomerHateoasRestController.class).getCustomer(uuid)).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(methodOn(CustomerHateoasRestController.class).findAllCustomers()).withRel("customers"));
            return ResponseEntity.of(Optional.of(resource));
        } catch (NoSuchCustomerException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
