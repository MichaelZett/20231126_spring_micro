package de.zettsystems.netzfilm.movie.values;

import de.zettsystems.netzfilm.exception.values.NoSuchElementFoundException;

import java.util.UUID;

public class NoSuchCopyException extends NoSuchElementFoundException {
    public NoSuchCopyException(UUID uuid) {
        super("No copy exists with uuid " + uuid);
    }
}
