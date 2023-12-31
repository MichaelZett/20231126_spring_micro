package de.zettsystems.netzfilm.rent.application;

import de.zettsystems.netzfilm.rent.domain.Rent;
import de.zettsystems.netzfilm.rent.values.RentBillingTo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
    private final AmqpTemplate amqpTemplate;

    @Override
    public void sendBill(Rent rent) {
        amqpTemplate.convertAndSend(
                new RentBillingTo(new BigDecimal(rent.getAmount().getNumber().toString()), rent.getUuid(), rent.getCustomer().getUuid()));
    }
}
