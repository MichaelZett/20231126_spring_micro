package de.zettsystems.java14;

import java.io.IOException;

public class SwitchExpressions {

    public static void main(String[] args) throws IOException {
        oldSwitchStatement();
        enhancedSwitchStatement();
        switchExpression();
        switchExpressionYield();
    }

    /**
     * Wer liebt es nicht - switch statement mit fall-through und break;?
     */
    public static void oldSwitchStatement() {
        System.out.println("**************_Switch Statement_**************");
        int numLetters = 0;
        Day day = Day.WEDNESDAY;
        switch (day) {
            case MONDAY:
            case FRIDAY:
            case SUNDAY:
                numLetters = 6;
                break;
            case TUESDAY:
                numLetters = 7;
                break;
            case THURSDAY:
            case SATURDAY:
                numLetters = 8;
                break;
            case WEDNESDAY:
                numLetters = 9;
                break;
            default:
                throw new IllegalStateException("Invalid day: " + day);
        }
        System.out.println(numLetters);
    }

    /**
     * Expression style cases im switch statement
     */
    public static void enhancedSwitchStatement() {
        System.out.println("**************_Enhanced Switch Statement_**************");
        int numLetters = 0;
        Day day = Day.WEDNESDAY;
        switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> numLetters = 6;
            case TUESDAY -> numLetters = 7;
            case THURSDAY, SATURDAY -> numLetters = 8;
            case WEDNESDAY -> numLetters = 9;
            default -> throw new IllegalStateException("Invalid day: " + day);
        }

        System.out.println(numLetters);
    }

    /**
     * Switch Expression gibt einen Wert zurück. Braucht Fälle für alle Werte oder default.
     * Kein fall-through, kein break;
     */
    public static void switchExpression() {
        System.out.println("**************_Switch Expression_**************");
        Day day = Day.WEDNESDAY;
        System.out.println(
                switch (day) {
                    case MONDAY, FRIDAY, SUNDAY -> {
                        int zahl = 5;
                        yield zahl + 1;
                    }
                    case TUESDAY -> 7;
                    case THURSDAY, SATURDAY -> 8;
                    case WEDNESDAY -> 9;
                    default -> throw new IllegalStateException("Invalid day: " + day);
                }
        );
    }

    /**
     * Man kann old-style case labels in der expression verwenden, dann benutzt man yield, um den Wert zurückzugeben.
     * Die fall-through labels, samt des auszuführenden Codes nennt man "switch labeled statement group".
     * Empfohlen wird aber der vollständige expression style.
     */
    public static void switchExpressionYield() {
        System.out.println("**************_Switch Expression Yield_**************");
        Day day = Day.WEDNESDAY;
        int numLetters = switch (day) {
            case MONDAY:
            case FRIDAY:
            case SUNDAY:
                yield 6;
            case TUESDAY:
                yield 7;
            case THURSDAY:
            case SATURDAY:
                yield 8;
            case WEDNESDAY:
                yield 9;
            default:
                throw new IllegalStateException("Invalid day: " + day);
        };
        System.out.println(numLetters);
    }

}
