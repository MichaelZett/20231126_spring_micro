package de.zettsystems.netzfilm.movie.values;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Movie", description = "Filmdaten")
public record MovieTo(@Schema(description = "Eindeutige ID") UUID uuid,
                      @Schema(description = "Filmtitel") @NotBlank String title,
                      @Schema(description = "Erscheinungsjahr") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"}) LocalDate releaseDate,
                      @Schema(description = "Altersfreigabe") Fsk fsk,
                      @Schema(description = "Interne Version") long version) {
}
