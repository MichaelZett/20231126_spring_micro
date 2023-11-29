package de.zettsystems.lager.copy.application;

import de.zettsystems.lager.copy.values.CopyTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class CopyServiceImpl implements CopyService {
    private final RestTemplate restTemplate;

    @Override
    public void addCopies(CopyTo copyTo) {
        String serviceId = "netzfilm";
        final ResponseEntity<String> result = restTemplate.postForEntity("http://%s/api/copies/".formatted(serviceId), copyTo, String.class);
        LOG.info(result.getBody());
    }
}
