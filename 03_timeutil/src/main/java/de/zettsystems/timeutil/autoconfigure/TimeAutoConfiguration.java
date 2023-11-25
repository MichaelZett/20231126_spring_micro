package de.zettsystems.timeutil.autoconfigure;


import de.zettsystems.timeutil.adapter.TimeInfoContributor;
import de.zettsystems.timeutil.adapter.TimeTravelController;
import org.springframework.boot.actuate.autoconfigure.info.ConditionalOnEnabledInfoContributor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnWebApplication
@EnableConfigurationProperties(TimetravelProperties.class)
public class TimeAutoConfiguration {

    @Bean
    @ConditionalOnClass(Endpoint.class)
    @ConditionalOnEnabledInfoContributor("time")
    public TimeInfoContributor timeInfoContributor() {
        return new TimeInfoContributor();
    }

    @Bean
    @ConditionalOnProperty(prefix = TimetravelProperties.PREFIX, name = "enable")
    public TimeTravelController timeTravelController() {
        return new TimeTravelController();
    }

}
