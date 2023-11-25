package de.zettsystems.netzfilm.customer.domain;

import de.zettsystems.netzfilm.customer.values.CustomerTo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
@Entity
public class Customer {
    @Id
    private long id;
    private long version;
    private final UUID uuid;
    private String name;
    private String lastName;
    private LocalDate birthdate;
    private boolean vip;

    //for jpa
    protected Customer() {
        super();
        this.uuid = UUID.randomUUID();
    }

    public Customer(String name, String lastName, LocalDate birthdate, boolean vip) {
        this();
        this.name = name;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.vip = vip;
    }

    public CustomerTo toTo() {
        return new CustomerTo(this.getUuid(), this.getName(), this.getLastName(), this.getBirthdate(), this.isVip(), this.version);
    }

    public void update(CustomerTo customer) {
        this.name = customer.name();
        this.lastName = customer.lastName();
        this.birthdate = customer.birthdate();
        this.vip = customer.vip() != null && customer.vip();
        this.version = customer.version();
    }
}
