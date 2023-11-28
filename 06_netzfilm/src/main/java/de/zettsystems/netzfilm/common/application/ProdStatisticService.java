package de.zettsystems.netzfilm.common.application;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
@Slf4j
public class ProdStatisticService {

    @PostConstruct
    public void doStatistics() {
        LOG.info("I'm doing statistics on prod!");
    }
}
