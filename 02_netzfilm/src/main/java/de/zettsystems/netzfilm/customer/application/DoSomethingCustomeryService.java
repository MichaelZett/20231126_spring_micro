package de.zettsystems.netzfilm.customer.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoSomethingCustomeryService {
    private final CustomerService customerService;

    @EventListener
    public void onStartUp(ContextRefreshedEvent event) {
        customerService.getAllCustomers().forEach(c -> LOG.info(c.toString()));
    }
}
