package de.zettsystems.netzfilm.common.adapter;


import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@ConditionalOnEnabledHealthIndicator("custom")
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        double chance = ThreadLocalRandom.current().nextDouble();
        Health.Builder status = Health.up();
        if (chance > 0.7) {
            status = Health.down();
        }
        return status
                .withDetail("chance", chance)
                .withDetail("strategy", "thread-local")
                .build();
    }
}
