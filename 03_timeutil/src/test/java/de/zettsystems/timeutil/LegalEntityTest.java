package de.zettsystems.timeutil;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class LegalEntityTest {

    @AfterEach
    public void tearDown() throws Exception {
        TimeMachine.reset();
    }

    @Test
    void shouldGetCurrentDay() {
        Clock clock = LegalEntity.GERMAN_COMPANY.getClock();
        LocalDate today = TimeUtil.today();
        assertThat(today).isEqualTo(LocalDate.now(clock));
    }

    @Test
    void shouldGetCurrentDayAndTime() {
        Clock clock = LegalEntity.GERMAN_COMPANY.getClock();

        LocalDateTime dateTime = TimeUtil.todayWithTime();

        assertThat(dateTime).isCloseTo(LocalDateTime.now(clock), within(1, ChronoUnit.SECONDS));
    }

    @Test
    void shouldReturnCurrentTime() {
        Calendar result = LegalEntity.GERMAN_COMPANY.calendar();

        Calendar expected = Calendar.getInstance();
        assertThat(result.get(Calendar.YEAR)).isEqualTo(expected.get(Calendar.YEAR));
        assertThat(result.get(Calendar.MONTH)).isEqualTo(expected.get(Calendar.MONTH));
        assertThat(result.get(Calendar.DAY_OF_MONTH)).isEqualTo(expected.get(Calendar.DAY_OF_MONTH));
        assertThat(result.getTimeZone()).isEqualTo(TimeZone.getTimeZone("Europe/Berlin"));
    }

    @Test
    void shouldReturnTimeInTheFuture() {
        TimeMachine.setOffset(Duration.ofDays(500L));

        Calendar result = LegalEntity.GERMAN_COMPANY.calendar();

        LocalDate expected = TimeUtil.today();
        assertThat(result.get(Calendar.YEAR)).isEqualTo(expected.getYear());
        assertThat(result.get(Calendar.MONTH) + 1).isEqualTo(expected.getMonthValue());
        assertThat(result.get(Calendar.DAY_OF_MONTH)).isEqualTo(expected.getDayOfMonth());
    }

    @Test
    void shouldConvertToEndOfDay() {
        Instant expected = LocalDateTime.parse("2017-01-01T23:59:59.999999999").atZone(LegalEntity.GERMAN_COMPANY.getZoneId()).toInstant();
        final LocalDate parse = LocalDate.parse("2017-01-01");

        Instant result = LegalEntity.GERMAN_COMPANY.convertLocalDateToEndOfDayInstant(parse);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldConvertToStartOfDay() {
        Instant expected = LocalDateTime.parse("2017-01-01T00:00:00").atZone(ZoneId.systemDefault()).toInstant();
        final LocalDate parse = LocalDate.parse("2017-01-01");

        Instant result = LegalEntity.GERMAN_COMPANY.convertLocalDateToStartOfDayInstant(parse);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldConvertToLocalDate() {
        LocalDate expected = LocalDate.parse("2014-12-07");
        Instant instant = Instant.parse("2014-12-07T07:52:43.900Z");

        LocalDate result = LegalEntity.GERMAN_COMPANY.convertInstantToLocalDate(instant);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldConvertToLocalDateTime() {
        LocalDateTime expected = LocalDateTime.parse("2014-12-07T08:52:43.900");
        Instant instant = Instant.parse("2014-12-07T07:52:43.900Z");

        LocalDateTime result = LegalEntity.GERMAN_COMPANY.convertInstantToLocalDateTime(instant);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldNotBeInPeriodForNullInstant() {

        boolean result = LegalEntity.GERMAN_COMPANY.isInstantInPeriod(null, LocalDate.MIN, LocalDate.MAX);

        assertThat(result).isFalse();
    }

    @Test
    void shouldBeInPeriodForSameInstant() {
        Instant ofInterest = Instant.parse("2017-01-13T07:52:43.900Z");
        LocalDate date = LocalDate.parse("2017-01-13");

        boolean result = LegalEntity.GERMAN_COMPANY.isInstantInPeriod(ofInterest, date, date);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotBeInPeriodForNanoEarly() {
        Instant ofInterest = LocalDateTime.parse("2017-01-01T23:59:59.999999999").atZone(ZoneId.systemDefault()).toInstant();
        LocalDate date = LocalDate.parse("2017-01-13");

        boolean result = LegalEntity.GERMAN_COMPANY.isInstantInPeriod(ofInterest, date, date);

        assertThat(result).isFalse();
    }

    @Test
    void shouldNotBeInPeriodForNanoLate() {
        Instant ofInterest = LocalDateTime.parse("2017-01-14T00:00:00.001").atZone(ZoneId.systemDefault()).toInstant();
        LocalDate date = LocalDate.parse("2017-01-13");
        boolean result = LegalEntity.GERMAN_COMPANY.isInstantInPeriod(ofInterest, date, date);

        assertThat(result).isFalse();
    }

}
