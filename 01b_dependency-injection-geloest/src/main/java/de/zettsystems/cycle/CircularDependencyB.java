package de.zettsystems.cycle;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CircularDependencyB {
    private final CircularDependencyC circularDependencyC;

    public CircularDependencyB(@Lazy CircularDependencyC circularDependencyC) {
        this.circularDependencyC = circularDependencyC;
    }
}