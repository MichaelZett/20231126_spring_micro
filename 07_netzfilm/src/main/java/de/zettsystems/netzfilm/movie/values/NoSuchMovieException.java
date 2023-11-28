package de.zettsystems.netzfilm.movie.values;

import de.zettsystems.netzfilm.exception.values.NoSuchElementFoundException;

import java.util.UUID;

public class NoSuchMovieException extends NoSuchElementFoundException {
    public NoSuchMovieException(UUID uuid) {
        super("No movie exists with uuid " + uuid);
    }
}
