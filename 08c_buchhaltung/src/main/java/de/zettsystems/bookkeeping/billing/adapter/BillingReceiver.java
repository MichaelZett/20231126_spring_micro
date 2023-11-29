package de.zettsystems.bookkeeping.billing.adapter;

import de.zettsystems.bookkeeping.billing.application.BillingService;
import de.zettsystems.bookkeeping.billing.values.RentBillingTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillingReceiver {
    private final BillingService billingService;

    @RabbitListener(queues = "${queue.name.billing:q.billing}")
    public void receiveRent(RentBillingTo rent) {
        billingService.billRent(rent);

        LOG.info("Received rent: {}", rent.getRentUuid());

    }
}