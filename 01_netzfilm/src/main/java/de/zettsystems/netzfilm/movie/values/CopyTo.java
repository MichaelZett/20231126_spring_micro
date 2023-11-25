package de.zettsystems.netzfilm.movie.values;

import java.util.UUID;

public record CopyTo(UUID uuid, String title, boolean lent, long version) {
}
