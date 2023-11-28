package de.zettsystems.netzfilm.movie.domain;

import de.zettsystems.netzfilm.movie.values.CopyTo;
import de.zettsystems.netzfilm.movie.values.CopyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.util.UUID;

@Getter
@ToString
@Entity
@Table(indexes = {
        @Index(name = "copy_movie_id_fk_idx", columnList = "movie_id"),
        @Index(name = "copy_lent_idx", columnList = "lent"),
        @Index(name = "copy_uuid_uq_idx", columnList = "uuid", unique = true),
})
public class Copy {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "copy_seq"
    )
    @SequenceGenerator(
            name = "copy_seq",
            allocationSize = 1 // default ist 50 in hibernate 6
    )
    private long id;
    @Version
    private long version;
    @Column(updatable = false, nullable = false)
    private final UUID uuid;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CopyType type;
    @ManyToOne(optional = false)
    private Movie movie;
    private boolean lent;

    //for jpa
    protected Copy() {
        super();
        this.uuid = UUID.randomUUID();
    }

    public Copy(CopyType type, Movie movie) {
        this();
        this.lent = false;
        this.type = type;
        this.movie = movie;
    }

    public void lend() {
        this.lent = true;
    }

    public CopyTo toTo() {
        return new CopyTo(uuid, movie.getTitle() + " - " + type, lent, version);
    }
}
