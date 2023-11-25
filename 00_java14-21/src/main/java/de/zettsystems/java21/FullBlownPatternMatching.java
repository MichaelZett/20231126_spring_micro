package de.zettsystems.java21;

import java.awt.*;

public class FullBlownPatternMatching {
    public static void main(String[] args) {
        System.out.println(getDoubleUsingIf(3));
        System.out.println(getDoubleUsingSwitch(5));
        System.out.println(getDoubleValueUsingGuardedPatterns("7.0"));
        System.out.println(getDoubleValueUsingParenthesizedPatterns("9"));
        System.out.println(getArea(new Circle(new Point(5, 5), 4)));
        System.out.println(getAreaRecordPattern(new Rectangle(new Point(1, 3), new Point(5, 9))));
    }

    /**
     * früher ging Folgendes mit Switch gar nicht - daher mit if
     */
    static double getDoubleUsingIf(Object o) {
        double result;
        if (o instanceof Integer) {
            result = ((Integer) o).doubleValue();
        } else if (o instanceof Float) {
            result = ((Float) o).doubleValue();
        } else if (o instanceof String) {
            result = Double.parseDouble(((String) o));
        } else {
            result = 0d;
        }
        return result;
    }

    /**
     * Switch Expression (Java 14) und Pattern Matching for Switch (Java 21)
     */
    static double getDoubleUsingSwitch(Object o) {
        return switch (o) {
            case Integer i -> i.doubleValue();
            case Float f -> f.doubleValue();
            case String s -> Double.parseDouble(s);
            default -> 0d;
        };
    }

    /**
     * Neues Schlüsselwort when für constraints
     */
    static double getDoubleValueUsingGuardedPatterns(Object o) {
        return switch (o) {
            case null -> -1d;
            case Integer i -> i.doubleValue();
            case Float f -> f.doubleValue();
            case String s when !s.isEmpty() -> Double.parseDouble(s);
            default -> 0d;
        };
    }

    static double getDoubleValueUsingParenthesizedPatterns(Object o) {
        return switch (o) {
            case null -> -1d;
            case Integer i -> i.doubleValue();
            case Float f -> f.doubleValue();
            case String s when !s.isEmpty() && !(s.contains("#") || s.contains("@")) -> Double.parseDouble(s);
            default -> 0d;
        };
    }

    /**
     * Switch Expression (Java 14) mit Pattern Matching für Switch (Java 21) auf Sealed Classes (Java 17) und
     * Records (Java 16).
     */
    public static double getArea(Shape shape) {
        return switch (shape) {
            case Circle c -> Math.PI * c.radius() * c.radius();
            case Rectangle r -> Math.abs((r.upperRight().y - r.lowerLeft().y)
                    * (r.upperRight().x - r.lowerLeft().x));
            // no default needed because auf sealed hierarchy!
        };
    }

    /**
     * Und Record Pattern (Java 21) - noch alles klar?
     */
    public static double getAreaRecordPattern(Shape shape) {
        return switch (shape) {
            // mit "Unnamed Pattern" in Preview bzw. 22+ könnte man an Stelle von var center einfach _ schreiben, weil man es eh nicht braucht
            case Circle(var center, int radius) -> Math.PI * radius * radius;
            case Rectangle(Point lowerLeft, Point upperRight) -> Math.abs((upperRight.y - lowerLeft.y)
                    * (upperRight.x - lowerLeft.x));
            // no default needed because of sealed hierarchy!
        };
    }
}
