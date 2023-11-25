package de.zettsystems.timeutil;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateHelper {
    private static final LocalDate MIN_LOCAL_DATE = LocalDate.of(1800, 1, 1);
    private static final LocalDate MAX_LOCAL_DATE = LocalDate.of(9999, 12, 31);

    private DateHelper() {
        //not intended
    }

    public static LocalDate maxOf(LocalDate left, LocalDate right) {
        return left.isAfter(right) ? left : right;
    }

    public static LocalDate minOf(LocalDate left, LocalDate right) {
        return left.isBefore(right) ? left : right;
    }

    /**
     * provide a max local date that is compatible with Postgres (LocalDate.MAX +999999999 AD, Postgres: 5874897 AD
     *
     * @return compatible Max local Date
     */
    public static LocalDate retrieveMaxLocalDate() {
        return MAX_LOCAL_DATE;
    }

    /**
     * provide a min local date that is compatible with Postgres (LocalDate.MAX 999999999 BC, Postgres: 4713 BC
     *
     * @return compatible Max local Date
     */
    public static LocalDate retrieveMinLocalDate() {
        return MIN_LOCAL_DATE;
    }


    /**
     * converts an instant to a LocalDate with the given zoneId
     *
     * @param instant instant to convert
     * @param zoneId  zone for conversion
     * @return localDate
     */
    public static LocalDate convertInstantToLocalDate(Instant instant, ZoneId zoneId) {
        return LocalDate.from(instant.atZone(zoneId));
    }

    public static LocalDateTime convertInstantToLocalDateTime(Instant instant, ZoneId zoneId) {
        return LocalDateTime.from(instant.atZone(zoneId));
    }

    /**
     * checks if given LocalDate is in between start and end LocalDate
     *
     * @param ofInterest localDate of interest
     * @param start      start of period
     * @param end        end of period
     * @return false if ofInterest is null or outside the given borders
     */
    public static boolean isLocalDateInPeriod(LocalDate ofInterest, LocalDate start, LocalDate end) { // NOSONAR LocalDate is needed here
        if (ofInterest == null) {
            return false;
        }
        return start.minusDays(1).isBefore(ofInterest) &&
                end.plusDays(1).isAfter(ofInterest);
    }

    /**
     * Converts a {@link Timestamp} (that internally has no time zone) to a {@link ZonedDateTime}. The given timestamp
     * is interpreted to be in the given {@link ZoneId}.
     *
     * @param timestamp The {@link Timestamp} to convert.
     * @param zone      The time zone to use.
     * @return Returns the {@link ZonedDateTime}, with the timestamps time and the given zone.
     */
    public static ZonedDateTime convertTimestampToZonedDateTime(Timestamp timestamp, ZoneId zone) {
        return ZonedDateTime.of(timestamp.toLocalDateTime(), zone);
    }
}
