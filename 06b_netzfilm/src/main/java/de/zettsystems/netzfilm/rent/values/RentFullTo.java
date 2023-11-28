package de.zettsystems.netzfilm.rent.values;

import de.zettsystems.netzfilm.movie.values.CopyType;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.money.MonetaryAmount;
import java.time.LocalDate;

@Schema(name = "Leihe", description = "Leihedaten")
public record RentFullTo(@Schema(description = "Filmtitel") String title,
                         @Schema(description = "Kopietyp") CopyType copyType,
                         @Schema(description = "Startdatum") LocalDate startDate,
                         @Schema(description = "Endedatum") LocalDate endDate,
                         @Schema(description = "Kunde") String customer,
                         @Schema(description = "Betrag") MonetaryAmount amount) {
}
