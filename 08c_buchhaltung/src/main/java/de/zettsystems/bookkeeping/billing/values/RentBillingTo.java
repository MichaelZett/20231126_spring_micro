package de.zettsystems.bookkeeping.billing.values;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RentBillingTo {
    private BigDecimal amount;
    private UUID rentUuid;
    private UUID customerUuid;
}