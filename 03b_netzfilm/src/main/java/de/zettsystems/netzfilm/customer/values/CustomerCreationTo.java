package de.zettsystems.netzfilm.customer.values;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record CustomerCreationTo(@NotBlank String name, @NotBlank String lastName,
                                 @Past @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"}) LocalDate birthdate,
                                 Boolean vip) {
}
