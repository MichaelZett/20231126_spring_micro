package de.zettsystems.bookkeeping.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertyResolver;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RabbitConfiguration {
    private final CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public Declarables createBillingStructures(PropertyResolver propertyResolver) {
        final FanoutExchange billingExchange = new FanoutExchange(propertyResolver.getProperty("exchange.name.billing", "x.billing"));
        final Queue billing = new Queue(propertyResolver.getProperty("queue.name.billing", "q.billing"));
        final Queue billingDebug = new Queue(propertyResolver.getProperty("queue.name.billing-debug", "q.billing-debug"),
                false, false, true, Map.of("x-queue-mode", "lazy", "x-max-length", 50));
        final Binding billingBinding = BindingBuilder.bind(billing).to(billingExchange);
        final Binding billingDebugBinding = BindingBuilder.bind(billingDebug).to(billingExchange);
        return new Declarables(
                billingExchange, billing, billingDebug,
                billingBinding, billingDebugBinding
        );
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(converter());
        return factory;
    }
}
