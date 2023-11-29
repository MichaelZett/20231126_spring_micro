package de.zettsystems.netzfilm.customer.values;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(name = "Customer", description = "Kundendaten")
public record CustomerCreationTo(@Schema(description = "Vorname") @NotBlank String name,
                                 @Schema(description = "Nachname") @NotBlank String lastName,
                                 @Schema(description = "Geburtstag") @Past @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"}) LocalDate birthdate,
                                 @Schema(description = "Very Important Person") Boolean vip) {
}
