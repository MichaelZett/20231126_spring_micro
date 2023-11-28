package de.zettsystems.netzfilm.customer.domain;

import de.zettsystems.netzfilm.customer.values.CustomerTo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
@Entity
@Table(indexes = {
        @Index(name = "cust_last_name_idx", columnList = "lastName"),
        @Index(name = "cust_uuid_uq_idx", columnList = "uuid", unique = true),
})
public class Customer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_seq"
    )
    @SequenceGenerator(
            name = "customer_seq",
            allocationSize = 1 // default ist 50 in hibernate 6
    )
    private long id;
    @Version
    private long version;
    @Column(updatable = false, nullable = false)
    private final UUID uuid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
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
