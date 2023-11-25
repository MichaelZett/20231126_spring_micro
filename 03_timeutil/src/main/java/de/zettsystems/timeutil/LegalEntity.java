package de.zettsystems.timeutil;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

public enum LegalEntity {

    GERMAN_COMPANY(ZoneId.of("Europe/Berlin"));

    private final ZoneId zoneId; //NOSONAR transient leads to bug

    LegalEntity(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public Clock getClock() {
        return TimeMachine.withZoneId(getZoneId());
    }

    public LocalDate today() {
        return LocalDate.now(getClock());
    }

    public LocalDateTime todayWithTime() {
        return LocalDateTime.now(getClock());
    }

    public Calendar calendar() {
        Clock clock = getClock();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(clock.getZone()));
        calendar.setTimeInMillis(clock.millis());
        return calendar;
    }

    /**
     * converts a {@link LocalDate} at Time 23:59:59.999999999 to an {@link Instant} based on the given {@link ZoneId} of the {@link LegalEntity}
     *
     * @param localDate localDate to convert
     * @return instant
     */
    public Instant convertLocalDateToEndOfDayInstant(LocalDate localDate) {
        return InstantHelper.convertLocalDateToEndOfDayInstant(localDate, getZoneId());
    }

    /**
     * converts a {@link LocalDate} at Time 00:00 to an {@link Instant} based on the given {@link ZoneId} of the {@link LegalEntity}
     *
     * @param localDate localDate to convert
     * @return instant
     */
    public Instant convertLocalDateToStartOfDayInstant(LocalDate localDate) {
        return InstantHelper.convertLocalDateToStartOfDayInstant(localDate, getZoneId());
    }

    public LocalDate convertInstantToLocalDate(Instant instant) {
        return DateHelper.convertInstantToLocalDate(instant, getZoneId());
    }

    public LocalDateTime convertInstantToLocalDateTime(Instant instant) {
        return DateHelper.convertInstantToLocalDateTime(instant, getZoneId());
    }

    public boolean isInstantInPeriod(Instant ofInterest, LocalDate start, LocalDate end) {
        return InstantHelper.isInstantInPeriod(ofInterest, start, end, getZoneId());
    }

    public Instant convertToInstant(LocalDateTime localDateTime) {
        return localDateTime.atZone(getZoneId()).toInstant();
    }
}
