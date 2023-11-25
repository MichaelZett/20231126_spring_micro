package de.zettsystems.timeutil;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * Use this class to access the time so that we are able to fake the time in order to do time journeys.
 * To do time journeys, use {@link TimeMachine}
 * <p>
 * Some are kept for better code readability as we do not have multiple {@link LegalEntity} for now.
 */
public final class TimeUtil {

    private TimeUtil() {
    }

    /**
     * get the current day as defined by the zoneId of {@link LegalEntity#GERMAN_COMPANY} and a possible time travel defined by {@link TimeMachine}
     *
     * @return current day as given by {@link TimeMachine} and zoneId of {@link LegalEntity#GERMAN_COMPANY}
     */
    public static LocalDate today() {
        return LegalEntity.GERMAN_COMPANY.today();
    }

    /**
     * get the current day and time as defined by the zoneId of {@link LegalEntity#GERMAN_COMPANY} and a possible time travel defined by {@link TimeMachine}
     *
     * @return current day and time as given by {@link TimeMachine} and zoneId of {@link LegalEntity#GERMAN_COMPANY}
     */
    public static LocalDateTime todayWithTime() {
        return LegalEntity.GERMAN_COMPANY.todayWithTime();
    }

    /**
     * get the current time, possibly influenced by a time travel via {@link TimeMachine}
     *
     * @return current time as given by {@link TimeMachine}
     */
    public static Instant now() {
        return InstantHelper.now();
    }

    /**
     * get the current time as string, possibly influenced by a time travel via {@link TimeMachine}
     *
     * @return current time as string, as given by {@link TimeMachine}
     */
    public static String nowAsString() {
        return InstantHelper.nowAsString();
    }

    /**
     * get the current calender as defined by the zoneId of {@link LegalEntity#GERMAN_COMPANY} and a possible time travel defined by {@link TimeMachine}
     *
     * @return calender as given by {@link TimeMachine} and zoneId of {@link LegalEntity#GERMAN_COMPANY}
     */
    public static Calendar calendar() {
        return LegalEntity.GERMAN_COMPANY.calendar();
    }

    /**
     * @return the maximum local date the system can handle (especially postgres)
     */
    public static LocalDate retrieveMaxLocalDate() {
        return DateHelper.retrieveMaxLocalDate();
    }

    /**
     * @return the minimum local date the system can handle  (especially postgres)
     */
    public static LocalDate retrieveMinLocalDate() {
        return DateHelper.retrieveMinLocalDate();
    }

    /**
     * get the current clock
     *
     * @return the current clock
     */
    public static Clock getClock() {
        return LegalEntity.GERMAN_COMPANY.getClock();
    }
}
