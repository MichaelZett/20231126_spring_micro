package de.zettsystems.cycle;

import org.springframework.stereotype.Component;

@Component
public class CircularDependencyB {
    private final CircularDependencyC circularDependencyC;

    public CircularDependencyB(CircularDependencyC circularDependencyC) {
        this.circularDependencyC = circularDependencyC;
    }
}