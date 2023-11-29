package de.zettsystems.netzfilm.rent.application;

import de.zettsystems.netzfilm.rent.domain.Rent;

public interface BillingService {
    void sendBill(Rent rent);
}
