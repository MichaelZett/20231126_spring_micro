package de.zettsystems.bookkeeping.billing.application;

import de.zettsystems.bookkeeping.billing.values.RentBillingTo;

public interface BillingService {
    void billRent(RentBillingTo rent);
}
