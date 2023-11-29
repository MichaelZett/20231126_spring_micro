package de.zettsystems.netzfilm.rent.values;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RentBillingTo {
    private BigDecimal amount;
    private UUID rentUuid;
    private UUID customerUuid;
}
