package de.zettsystems.netzfilm.rent.values;

import de.zettsystems.netzfilm.movie.values.CopyType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RentFullTo(String title, CopyType copyType, LocalDate startDate, LocalDate endDate, String customer,
                         BigDecimal amount) {
}
