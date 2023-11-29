package de.zettsystems.lager.copy.adapter;


import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import de.zettsystems.lager.copy.values.CopyTo;
import de.zettsystems.lager.copy.values.CopyType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BANDWIDTH_LIMIT_EXCEEDED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"uri.netzfilm=http://localhost:" + CopyRestControllerTest.NETZFILM_PORT, "spring.cloud.discovery.enabled=false"})
@Slf4j
class CopyRestControllerTest {
    @Autowired
    private CopyRestController testee;

    @Autowired
    private TestRestTemplate restTemplate;


    CopyTo copyTo = new CopyTo(UUID.randomUUID(), CopyType.DVD, 1);

    public static final String NETZFILM_PORT = "9990";
    @RegisterExtension
    static WireMockExtension NETFILM = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig()
                    .port(Integer.parseInt(NETZFILM_PORT)))
            .build();

    @Test
    void addWithRetry() {
        NETFILM.stubFor(WireMock.post("/api/copies")
                .willReturn(ok()));
        ResponseEntity<String> response1 = restTemplate.postForEntity("/api/copies/retry", copyTo, String.class);
        NETFILM.verify(1, postRequestedFor(urlEqualTo("/api/copies")));

        NETFILM.resetRequests();

        NETFILM.stubFor(WireMock.post("/api/copies")
                .willReturn(serverError()));
        ResponseEntity<String> response2 = restTemplate.postForEntity("/api/copies/retry", copyTo, String.class);
        assertThat(response2.getBody()).isEqualTo("Fehler bei Netzfilm.");
        NETFILM.verify(3, postRequestedFor(urlEqualTo("/api/copies")));
    }

    @Test
    void addWithTime() {
        NETFILM.stubFor(WireMock.post("/api/copies/out").willReturn(ok()));
        ResponseEntity<String> response = restTemplate.postForEntity("/api/copies/time", copyTo, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.REQUEST_TIMEOUT);
        NETFILM.verify(1, postRequestedFor(urlEqualTo("/api/copies/out")));
    }

    @Disabled
    @Test
    void addWithBreaker() {
        NETFILM.stubFor(WireMock.post("/api/copies").willReturn(serverError()));

        IntStream.rangeClosed(1, 5)
                .forEach(i -> {
                    ResponseEntity<String> response = restTemplate.postForEntity("/api/copies/breaker", copyTo, String.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
                });

        IntStream.rangeClosed(1, 5)
                .forEach(i -> {
                    ResponseEntity<String> response = restTemplate.postForEntity("/api/copies/breaker", copyTo, String.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
                });

        NETFILM.verify(5, postRequestedFor(urlEqualTo("/api/copies")));
    }


    @Test
    void addWithBulk() throws InterruptedException {
        NETFILM.stubFor(WireMock.post("/api/copies").willReturn(ok()));
        Map<Integer, Integer> responseStatusCount = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);

        IntStream.rangeClosed(1, 5)
                .forEach(i -> executorService.execute(() -> {
                    ResponseEntity<String> response = restTemplate.postForEntity("/api/copies/bulk",
                            copyTo, String.class);
                    int statusCode = response.getStatusCodeValue();
                    responseStatusCount.merge(statusCode, 1, Integer::sum);
                    latch.countDown();
                }));
        latch.await();
        executorService.shutdown();

        assertEquals(2, responseStatusCount.keySet().size());
        LOG.info("Response statuses: " + responseStatusCount.keySet());
        assertTrue(responseStatusCount.containsKey(BANDWIDTH_LIMIT_EXCEEDED.value()));
        assertTrue(responseStatusCount.containsKey(OK.value()));
        NETFILM.verify(3, postRequestedFor(urlEqualTo("/api/copies")));
    }
}