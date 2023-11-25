package de.zettsystems.netzfilm.movie.values;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record MovieCreationTo(@NotBlank String title,
                              @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"}) LocalDate releaseDate,
                              Fsk fsk) {
}
