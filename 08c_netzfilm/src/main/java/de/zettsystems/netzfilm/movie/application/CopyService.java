package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.values.CopyAddTo;
import de.zettsystems.netzfilm.movie.values.CopyTo;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface CopyService {
    List<CopyTo> findAllRentableCopies();

    String getTitleAndFormat(UUID uuid);

    void addCopies(@Valid CopyAddTo copyTo);
}
