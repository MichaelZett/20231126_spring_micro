package de.zettsystems.netzfilm.movie.values;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "Kopie", description = "Kopiedaten")
public record CopyTo(@Schema(description = "Eindeutige ID") UUID uuid,
                     @Schema(description = "Filmtitel") String title, @Schema(description = "Verliehen") boolean lent,
                     @Schema(description = "Interne Version") long version) {
}
