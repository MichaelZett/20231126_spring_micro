package de.zettsystems.cycle;

import org.springframework.stereotype.Component;

@Component
public class CircularDependencyC {
    private final CircularDependencyA circularDependencyA;

    public CircularDependencyC(CircularDependencyA circularDependencyA) {
        this.circularDependencyA = circularDependencyA;
    }
}