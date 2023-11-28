package de.zettsystems.netzfilm;

import de.zettsystems.netzfilm.configuration.TestContainersConfiguration;
import org.springframework.boot.SpringApplication;

/**
 * Verwendet man diese Klasse zum Starten verwendet kann man sich das vorherige docker-compose up sparen
 */
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.from(NetzfilmApplication::main)
                .with(TestContainersConfiguration.class)
                .run(args);
    }
}
