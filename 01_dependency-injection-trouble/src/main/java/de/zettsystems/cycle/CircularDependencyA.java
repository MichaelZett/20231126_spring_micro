package de.zettsystems.cycle;

import org.springframework.stereotype.Component;

@Component
public class CircularDependencyA {
    private final CircularDependencyB circularDependencyB;

    public CircularDependencyA(CircularDependencyB circularDependencyB) {
        this.circularDependencyB = circularDependencyB;
    }
}
