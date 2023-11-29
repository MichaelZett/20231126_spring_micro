package de.zettsystems.lager.copy.application;

import de.zettsystems.lager.copy.values.CopyTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.PropertyResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CopyServiceImpl implements CopyService {
    private final RestTemplate restTemplate;
    private final String netzfilmUri;

    public CopyServiceImpl(RestTemplate restTemplate, PropertyResolver propertyResolver) {
        netzfilmUri = propertyResolver.getProperty("uri.netzfilm", "http://netzfilm");
        this.restTemplate = restTemplate;
    }


    @Override
    public String addCopies(CopyTo copyTo) {
        final ResponseEntity<String> result = restTemplate.postForEntity("%s/api/copies".formatted(netzfilmUri), copyTo, String.class);
        return result.getBody();
    }

    @Override
    public String addCopiesWithTimeout(CopyTo copyTo) {
        final ResponseEntity<String> result = restTemplate.postForEntity("%s/api/copies/out".formatted(netzfilmUri), copyTo, String.class);
        // for the test we need to delay locally
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignore) {
        }
        return result.getBody();
    }

}
