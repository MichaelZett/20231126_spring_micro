package de.zettsystems.netzfilm;

import de.zettsystems.timeutil.autoconfigure.TimetravelProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class NetzfilmApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetzfilmApplication.class, args);
    }

}
