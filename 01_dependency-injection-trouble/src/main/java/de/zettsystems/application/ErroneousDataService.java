package de.zettsystems.application;

import de.zettsystems.domain.DataRepository;
import de.zettsystems.domain.SomeOtherRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ErroneousDataService {
    private static final Logger LOG = LoggerFactory.getLogger(ErroneousDataService.class);
    private final DataRepository repo;
    private final SomeOtherRepository someOtherRepository;
    private final ErrorRepository errorRepo;

    public ErroneousDataService(DataRepository repo, SomeOtherRepository someOtherRepository, ErrorRepository errorRepo) {
        super();
        this.repo = repo;
        this.someOtherRepository = someOtherRepository;
        this.errorRepo = errorRepo;
    }

    @PostConstruct
    public void setup() {
        repo.putData("key", "value");
        LOG.info(repo.getDataByKey("key").get());
    }

}

