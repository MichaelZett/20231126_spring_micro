package de.zettsystems.netzfilm;

import de.zettsystems.netzfilm.configuration.TestContainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Import(TestContainersConfiguration.class)
class NetzfilmApplicationTests {

    @Test
    void contextLoads() {
        // hier wird getestet, dass ApplicationContext und Anwendung ohne Fehler hochfahren
    }

}
