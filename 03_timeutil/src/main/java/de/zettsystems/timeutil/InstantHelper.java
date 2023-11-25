package de.zettsystems.timeutil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;

import static java.time.temporal.ChronoUnit.NANOS;

/**
 * use this class to access an instant so that we are able to fake the time in order to do time journeys.
 * use InstantHelper or LegalEntity instead
 */
public final class InstantHelper {
    private InstantHelper() {
        //not intended
    }

    /**
     * get the current time
     *
     * @return current time as given by TimeMachine
     */
    public static Instant now() {
        return TimeMachine.now();
    }

    /**
     * get the current time as string
     *
     * @return current time as string as given by TimeMachine
     */
    public static String nowAsString() {
        return now().toString();
    }

    public static boolean isInstantInPeriod(Instant ofInterest, Instant start, Instant end) {
        if (ofInterest == null) {
            return false;
        }
        return start.minus(1, NANOS).isBefore(ofInterest) &&
                end.plus(1, NANOS).isAfter(ofInterest);
    }

    public static boolean isInstantInPeriod(Instant ofInterest, LocalDate start, LocalDate end, ZoneId zoneId) {
        Instant startAsInstant = convertLocalDateToStartOfDayInstant(start, zoneId);
        Instant endAsInstant = convertLocalDateToEndOfDayInstant(end, zoneId);
        return InstantHelper.isInstantInPeriod(ofInterest, startAsInstant, endAsInstant);
    }

    /**
     * converts a {@link LocalDate} at Time 23:59:59.999999999 to an {@link Instant} based on the given {@link ZoneId}
     *
     * @param localDate localDate to convert
     * @param zoneId    zoneId for conversion
     * @return instant
     */
    public static Instant convertLocalDateToEndOfDayInstant(ChronoLocalDate localDate, ZoneId zoneId) {
        if (localDate == null) {
            return null;
        }
        return localDate.atTime(LocalTime.MAX).atZone(zoneId).toInstant();
    }

    /**
     * converts a {@link LocalDate} at Time 00:00 to an {@link Instant} based on the given {@link ZoneId}
     *
     * @param localDate localDate to convert
     * @param zoneId    zoneId for conversion
     * @return instant
     */
    public static Instant convertLocalDateToStartOfDayInstant(ChronoLocalDate localDate, ZoneId zoneId) {
        if (localDate == null) {
            return null;
        }
        return localDate.atTime(LocalTime.MIDNIGHT).atZone(zoneId).toInstant();
    }
}
