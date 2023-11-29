package de.zettsystems.netzfilm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@Slf4j
public class NetzfilmApplication {
    public static void main(String[] args) {
        SpringApplication.run(NetzfilmApplication.class, args);
    }

}
