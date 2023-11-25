package de.zettsystems.netzfilm.movie.values;

import java.util.UUID;

public class NoSuchMovieException extends RuntimeException {
    public NoSuchMovieException(UUID uuid) {
        super("No movie exists with uuid " + uuid);
    }
}
