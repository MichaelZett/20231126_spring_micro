package de.zettsystems.netzfilm.common.application;

import de.zettsystems.netzfilm.common.values.AfternoonCondition;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

@Service
@Conditional(AfternoonCondition.class)
@Slf4j
public class LateRiserService {
    @PostConstruct
    public void rise() {
        LOG.info("Ok, ok, I'll join... ");
    }
}
