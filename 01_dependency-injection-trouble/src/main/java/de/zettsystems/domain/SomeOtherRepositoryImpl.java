package de.zettsystems.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SomeOtherRepositoryImpl implements SomeOtherRepository {
    private static final Logger LOG = LoggerFactory.getLogger(SomeOtherRepositoryImpl.class);

    @Override
    public void tellMe() {
        LOG.info("Das wüsste ich aber!");
    }
}
