package de.zettsystems.lager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@Slf4j
public class LagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LagerApplication.class, args);
    }

}
