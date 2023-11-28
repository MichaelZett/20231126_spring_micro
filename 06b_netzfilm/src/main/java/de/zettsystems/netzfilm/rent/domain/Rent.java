package de.zettsystems.netzfilm.rent.domain;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.rent.values.RentFullTo;
import io.hypersistence.utils.hibernate.type.money.MonetaryAmountType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CompositeType;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
@Entity
@Table(indexes = {
        @Index(name = "rent_copy_id_fk_idx", columnList = "copy_id"),
        @Index(name = "rent_customer_id_fk_idx", columnList = "customer_id"),
        @Index(name = "rent_uuid_uq_idx", columnList = "uuid", unique = true),
})
public class Rent {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rent_seq"
    )
    @SequenceGenerator(
            name = "rent_seq",
            allocationSize = 1 // default ist 50 in hibernate 6
    )
    private long id;
    @Version
    private long version;
    @Column(updatable = false, nullable = false)
    private final UUID uuid;
    @ManyToOne(optional = false)
    private Copy copy;
    @ManyToOne(optional = false)
    private Customer customer;
    @AttributeOverride(
            name = "amount",
            column = @Column(name = "amount")
    )
    @AttributeOverride(
            name = "currency",
            column = @Column(name = "currency")
    )
    @CompositeType(MonetaryAmountType.class)
    @Column(nullable = false)
    private MonetaryAmount amount;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;

    //for jpa
    protected Rent() {
        super();
        this.uuid = UUID.randomUUID();
    }

    public Rent(Copy copy, Customer customer, MonetaryAmount amount, LocalDate startDate, LocalDate endDate) {
        this();
        this.copy = copy;
        this.customer = customer;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RentFullTo toFullTo() {
        return new RentFullTo(copy.getMovie().getTitle(), copy.getType(), startDate, endDate, customer.getName() + " " + customer.getLastName(), amount);
    }
}
