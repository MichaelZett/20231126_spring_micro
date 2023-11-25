package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.values.CopyTo;

import java.util.List;
import java.util.UUID;

public interface CopyService {
    List<CopyTo> findAllRentableCopies();

    String getTitleAndFormat(UUID uuid);
}
