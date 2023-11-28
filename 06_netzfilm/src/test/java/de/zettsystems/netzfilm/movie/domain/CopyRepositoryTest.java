package de.zettsystems.netzfilm.movie.domain;

import de.zettsystems.netzfilm.configuration.TestContainersConfiguration;
import de.zettsystems.netzfilm.movie.values.CopyOverview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // we don't want in memory DB
@Import(TestContainersConfiguration.class)
class CopyRepositoryTest {
    @Autowired
    CopyRepository testee;


    @Test
    void customFindAllByLentIsFalse() {
        final List<Copy> copies = testee.customFindAllByLentIsFalse();

        assertThat(copies).hasSize(8);
    }

    @Test
    void getCopyOverview() {
        final Collection<CopyOverview> copyOverview = testee.getCopyOverview();

        assertThat(copyOverview).hasSize(4);

        for (CopyOverview co : copyOverview) {
            assertThat(co.getDvd()).isEqualTo("1");
            assertThat(co.getVhs()).isEqualTo("1");
        }
    }
}