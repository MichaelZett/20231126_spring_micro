package de.zettsystems.timeutil;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DateHelperTest {

    @Test
    void shouldRetrieveMaxLocalDate() throws Exception {
        LocalDate result = DateHelper.retrieveMaxLocalDate();

        assertThat(result).isEqualTo(LocalDate.parse("9999-12-31"));
    }

    @Test
    void shouldRetrieveMinLocalDate() throws Exception {
        LocalDate result = DateHelper.retrieveMinLocalDate();

        assertThat(result).isEqualTo(LocalDate.parse("1800-01-01"));
    }

    @Test
    void shouldConvertInstantToLocalDate() throws Exception {
        LocalDate expected = LocalDate.parse("2017-12-04");
        ZoneId zone = ZoneId.of("Europe/Berlin");
        Instant localDate = Instant.parse("2017-12-03T23:00:01.00Z");

        LocalDate result = DateHelper.convertInstantToLocalDate(localDate, zone);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldConvertInstantToLocalDateTime() throws Exception {
        LocalDateTime expected = LocalDateTime.parse("2017-12-04T00:00:01.021");
        ZoneId zone = ZoneId.of("Europe/Berlin");
        Instant localDate = Instant.parse("2017-12-03T23:00:01.021Z");

        LocalDateTime result = DateHelper.convertInstantToLocalDateTime(localDate, zone);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldNotBeInPeriodForNull() {
        boolean result = DateHelper.isLocalDateInPeriod(null, LocalDate.MIN, LocalDate.MAX);

        assertThat(result).isFalse();
    }

    @Test
    void shouldBeInPeriodForSameDay() {
        LocalDate date = LocalDate.parse("2017-01-13");

        boolean result = DateHelper.isLocalDateInPeriod(date, date, date);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotBeInPeriodForDayEarly() {
        LocalDate ofInterest = LocalDate.parse("2017-01-12");
        LocalDate date = LocalDate.parse("2017-01-13");

        boolean result = DateHelper.isLocalDateInPeriod(ofInterest, date, date);

        assertThat(result).isFalse();
    }

    @Test
    void shouldNotBeInPeriodForDayLate() {
        LocalDate ofInterest = LocalDate.parse("2017-01-14");
        LocalDate date = LocalDate.parse("2017-01-13");

        boolean result = DateHelper.isLocalDateInPeriod(ofInterest, date, date);

        assertThat(result).isFalse();
    }

    @Test
    void shouldConvertTimestampToZonedDateTimeDefault() {
        ZonedDateTime expected = LocalDateTime.parse("2010-01-20T13:14:15").atZone(ZoneOffset.UTC);

        Timestamp ts = Timestamp.valueOf("2010-01-20 13:14:15");

        ZonedDateTime result = DateHelper.convertTimestampToZonedDateTime(ts, ZoneOffset.UTC);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldConvertTimestampToZonedDateTime() {
        ZoneId zone = ZoneId.of("Europe/Berlin");
        ZonedDateTime expected = LocalDateTime.parse("2010-01-20T13:14:15").atZone(zone);
        Timestamp ts = Timestamp.valueOf("2010-01-20 13:14:15");

        ZonedDateTime result = DateHelper.convertTimestampToZonedDateTime(ts, zone);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldProvideMaxOf() {
        LocalDate smaller = LocalDate.now();
        LocalDate higher = smaller.plusDays(1);

        assertThat(DateHelper.maxOf(smaller, higher)).isEqualTo(higher);
        assertThat(DateHelper.maxOf(higher, smaller)).isEqualTo(higher);
    }

    @Test
    void shouldProvideMinOf() {
        LocalDate smaller = LocalDate.now();
        LocalDate higher = smaller.plusDays(1);

        assertThat(DateHelper.minOf(smaller, higher)).isEqualTo(smaller);
        assertThat(DateHelper.minOf(higher, smaller)).isEqualTo(smaller);
    }

}
