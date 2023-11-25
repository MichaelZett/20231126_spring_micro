package de.zettsystems.timeutil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Use this class to access the time so that we are able to fake the time in order to do time journeys.
 * For now, most of the former functions have been moved to {@link InstantHelper} and {@link DateHelper}.
 * Some are kept for better code readability as we do not have multiple {@link LegalEntity} for now.
 */
public final class TimeDateConverter {

    private TimeDateConverter() {
        //not intended
    }

    /**
     * ATTENTION: up to release 1.4.2, this function used {@link ZoneId#systemDefault()} for conversion.
     * This seems to be wrong in nearly all situations. Now we use the ZoneId of the
     * {@link LegalEntity#GERMAN_COMPANY} instead.
     *
     * @param endDate the value to convert
     * @return the converted value
     */
    public static Instant convertDateToEndOfDayInstant(LocalDate endDate) {
        return LegalEntity.GERMAN_COMPANY.convertLocalDateToEndOfDayInstant(endDate);
    }

    /**
     * ATTENTION: up to release 1.4.2, this function used {@link ZoneId#systemDefault()} for conversion.
     * This seems to be wrong in nearly all situations. Now we use the ZoneId of the
     * {@link LegalEntity#GERMAN_COMPANY} instead.
     *
     * @param startDate the value to convert
     * @return the converted value
     */
    public static Instant convertDateToStartOfDayInstant(LocalDate startDate) {
        return LegalEntity.GERMAN_COMPANY.convertLocalDateToStartOfDayInstant(startDate);
    }

    /**
     * ATTENTION: up to release 1.4.2, this function used ZoneId.systemDefault() for conversion.
     * This seems to be wrong in nearly all situation. Now we use the ZoneId of the
     * {@link LegalEntity#GERMAN_COMPANY}.
     *
     * @param instant the value to convert
     * @return the converted value
     */
    public static LocalDate convertInstantToDate(Instant instant) {
        return LegalEntity.GERMAN_COMPANY.convertInstantToLocalDate(instant);
    }

    public static LocalDateTime convertInstantToDateTime(Instant instant) {
        return LegalEntity.GERMAN_COMPANY.convertInstantToLocalDateTime(instant);
    }

    /**
     * ATTENTION: up to release 1.4.2, this function used {@link ZoneId#systemDefault()} for conversion.
     * This seems to be wrong in nearly all situations. Now we use the ZoneId of the
     * {@link LegalEntity#GERMAN_COMPANY} instead.
     *
     * @param ofInterest the value to check
     * @param start      the start of the interval
     * @param end        the end of the interval
     * @return if instance is in period
     */
    public static boolean isInstantInPeriod(Instant ofInterest, LocalDate start, LocalDate end) {
        return LegalEntity.GERMAN_COMPANY.isInstantInPeriod(ofInterest, start, end);
    }

    /**
     * Converts a {@link LocalDateTime} to an Instant (that always represents UTC) assuming the localDateTime to be
     * in the ZoneId of the {@link LegalEntity#GERMAN_COMPANY}.
     *
     * @param localDateTime The {@link LocalDateTime} to be converted. It is implicitly assumed that this value refers to the ZoneId of {@link LegalEntity#GERMAN_COMPANY}
     * @return Returns the {@link LocalDateTime} converted to an Instant.
     */
    public static Instant convertLocalDateTimeToInstant(LocalDateTime localDateTime) {
        return LegalEntity.GERMAN_COMPANY.convertToInstant(localDateTime);
    }
}
