package de.zettsystems.netzfilm.common.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class CustomInfoContributor implements InfoContributor {

    @Autowired
    private Environment environment;

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("custom",
                Map.of(
                        "port", Objects.requireNonNull(environment.getProperty("local.server.port")),
                        "profiles", environment.getDefaultProfiles()
                )
        );
    }
}