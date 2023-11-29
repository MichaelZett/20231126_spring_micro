package de.zettsystems.netzfilm.movie.values;

import java.util.UUID;

public record CopyAddTo(UUID movieUuid, CopyType copyType, int number) {
}
