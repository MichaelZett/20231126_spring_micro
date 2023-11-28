package de.zettsystems.netzfilm.movie.domain;

import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.movie.domain.Movie;
import de.zettsystems.netzfilm.movie.values.CopyTo;
import de.zettsystems.netzfilm.movie.values.CopyType;
import de.zettsystems.netzfilm.movie.values.Fsk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CopyTest {
    Copy testee;
    Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie("Gone with the wind", LocalDate.of(1939, 12, 15), Fsk.FSK_12);
        testee = new Copy(CopyType.VHS, movie);
    }

    @DisplayName("Die Kopie soll ausgeliehen sein nach dem Leihen")
    @Test
    void shouldLend() {
        assertThat(testee.isLent()).isFalse();

        testee.lend();

        assertThat(testee.isLent()).isTrue();
    }

    @Test
    void shouldToTo() {
        final CopyTo to = testee.toTo();

        assertThat(to.title()).isEqualTo("%s - %s".formatted(movie.getTitle(), testee.getType()));
        assertThat(to.lent()).isEqualTo(testee.isLent());
        assertThat(to.uuid()).isEqualTo(testee.getUuid());
        assertThat(to.version()).isEqualTo(testee.getVersion());

    }
}