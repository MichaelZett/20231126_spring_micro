package de.zettsystems.java21;

import java.awt.*;

/**
 * Wir haben Records (Java 16), Sealed "Classes" (Java 17).
 * Wenn implementierende Typen mit im file stehen (compilation unit) kann man permits weg lassen.
 */
public sealed interface Shape {
}

record Circle(Point center, int radius) implements Shape {
}

record Rectangle(Point lowerLeft, Point upperRight) implements Shape {
}
