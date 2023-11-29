package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.movie.domain.CopyRepository;
import de.zettsystems.netzfilm.movie.domain.Movie;
import de.zettsystems.netzfilm.movie.values.CopyTo;
import de.zettsystems.netzfilm.movie.values.CopyType;
import de.zettsystems.netzfilm.movie.values.Fsk;
import de.zettsystems.netzfilm.movie.values.NoSuchCopyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CopyServiceImplTest {

    public static final String THE_SMURFS = "The smurfs";
    public static final LocalDate SMURFS_DATE = LocalDate.of(2011, 8, 4);
    public static final Fsk SMURFS_FSK = Fsk.FSK_0;
    @Mock
    CopyRepository copyRepository;

    @InjectMocks
    CopyServiceImpl testee;

    @Test
    void findAllRentableCopies() {
        Movie smurfs = new Movie(THE_SMURFS, SMURFS_DATE.minusDays(1L), SMURFS_FSK);
        Copy smurfsCopy = new Copy(CopyType.VHS, smurfs);
        when(copyRepository.findAllByLentIsFalse()).thenReturn(List.of(smurfsCopy));

        final List<CopyTo> result = testee.findAllRentableCopies();

        assertThat(result).hasSize(1).containsOnly(new CopyTo(smurfsCopy.getUuid(), "The smurfs - VHS", false, 0L));
    }

    @Test
    void getTitleAndFormat() {
        Movie movie = new Movie(THE_SMURFS, SMURFS_DATE, SMURFS_FSK);
        Copy copy = new Copy(CopyType.VHS, movie);
        when(copyRepository.findByUuid(copy.getUuid())).thenReturn(Optional.of(copy));

        String result = testee.getTitleAndFormat(copy.getUuid());

        assertThat(result).isEqualTo("%s - %s".formatted(movie.getTitle(), copy.getType()));
    }

    @Test
    void shouldThrowExceptionsForMissingUuidForCopy() {
        when(copyRepository.findByUuid(any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> testee.getTitleAndFormat(UUID.randomUUID())).isInstanceOf(NoSuchCopyException.class);
    }
}