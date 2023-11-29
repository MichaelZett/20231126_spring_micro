package de.zettsystems.bookkeeping.billing.application;

import de.zettsystems.bookkeeping.billing.values.RentBillingTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
    @Override
    public void billRent(RentBillingTo rent) {
        try {
            Thread.sleep(1L);
            LOG.info("Booked rent into Account of {}", rent.getCustomerUuid());
            Thread.sleep(1L);
            LOG.info("Sent receipt of {} to ledger", rent.getAmount());
            Thread.sleep(1L);
            LOG.info("Printed the bill for the customer {}", rent.getCustomerUuid());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
