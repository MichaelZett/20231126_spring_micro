package de.zettsystems.netzfilm.movie.values;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class NoSuchCopyException extends RuntimeException {
    public NoSuchCopyException(UUID uuid) {
        super(HttpStatus.NOT_FOUND, "No copy exists with uuid " + uuid);
    }
}
