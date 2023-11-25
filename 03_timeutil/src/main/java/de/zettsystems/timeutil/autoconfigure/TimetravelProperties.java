package de.zettsystems.timeutil.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TimetravelProperties.PREFIX)
public record TimetravelProperties(boolean enable) {
    public static final String PREFIX = "zettsystems.timetravel";
}