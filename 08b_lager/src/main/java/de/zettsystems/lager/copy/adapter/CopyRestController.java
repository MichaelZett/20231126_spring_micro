package de.zettsystems.lager.copy.adapter;

import de.zettsystems.lager.copy.application.CopyService;
import de.zettsystems.lager.copy.values.CopyTo;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/copies")
@RequiredArgsConstructor
@Slf4j
class CopyRestController {
    private final CopyService copyService;


    @Retry(name = "netzfilm", fallbackMethod = "fallbackForAddCopies")
    @PostMapping("/retry")
    public ResponseEntity<String> addWithRetry(@Valid @RequestBody CopyTo copyTo) {
        final String result = copyService.addCopies(copyTo);
        return ResponseEntity.ok(result);
    }

    @CircuitBreaker(name = "netzfilm")
    @PostMapping("/breaker")
    public ResponseEntity<String> addWithBreaker(@Valid @RequestBody CopyTo copyTo) {
        final String result = copyService.addCopies(copyTo);
        return ResponseEntity.ok(result);
    }

    @TimeLimiter(name = "netzfilm")
    @PostMapping("/time")
    public CompletableFuture<String> addWithTime(@Valid @RequestBody CopyTo copyTo) {
        return CompletableFuture.supplyAsync(() -> copyService.addCopiesWithTimeout(copyTo));
    }

    @Bulkhead(name = "netzfilm")
    @PostMapping("/bulk")
    public ResponseEntity<String> addWithBulk(@Valid @RequestBody CopyTo copyTo) {
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final String result = copyService.addCopies(copyTo);
        return ResponseEntity.ok(result);
    }

    ResponseEntity<String> fallbackForAddCopies(CopyTo copyTo, Throwable t) {
        LOG.warn("Fehler - fallback", t);
        return ResponseEntity.internalServerError().body("Fehler bei Netzfilm.");
    }

    CompletableFuture<String> fallbackForTime(CopyTo copyTo, Throwable t) {
        LOG.warn("Fehler - fallback", t);
        return CompletableFuture.supplyAsync(() -> "Ui, sind die langsam. Versuchen Sie es später nochmal.");
    }
}