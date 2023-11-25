package de.zettsystems.netzfilm.movie.values;

import java.util.UUID;

public class NoSuchCopyException extends RuntimeException {
    public NoSuchCopyException(UUID uuid) {
        super("No copy exists with uuid " + uuid);
    }
}
