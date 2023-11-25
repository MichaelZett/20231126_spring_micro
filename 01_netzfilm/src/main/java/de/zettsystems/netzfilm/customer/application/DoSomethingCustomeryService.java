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

    @EventListener
    public void onStartUp(ContextRefreshedEvent event) {
        LOG.info("Here we can do things after startup for customers.");
    }
}
