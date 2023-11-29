package de.zettsystems.netzfilm.common.adapter;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Endpoint(id = "featureToggle")
public class FeatureToggleEndpoint {

    private Map<String, Feature> features = new ConcurrentHashMap<>();

    @PostConstruct
    public void setup() {
        Feature feature = new Feature("Some nice feature", Boolean.TRUE);
        features.put(feature.name, feature);
        feature = new Feature("Feature in development", Boolean.FALSE);
        features.put(feature.name, feature);
    }

    @ReadOperation
    public Map<String, Feature> features() {
        return features;
    }

    @ReadOperation
    public Feature feature(@Selector String name) {
        return features.get(name);
    }

    public record Feature(String name, Boolean enabled) {
    }

}