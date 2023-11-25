package de.zettsystems.timeutil;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class TimeDateConverterTest {

    @Test
    void shouldConvertToEndOfDay() {
        final LocalDate parse = LocalDate.parse("2017-01-01");
        assertThat(TimeDateConverter.convertDateToEndOfDayInstant(parse)).
                isEqualTo(LocalDateTime.parse("2017-01-01T23:59:59.999999999").atZone(ZoneId.systemDefault()).toInstant());
    }

    @Test
    void shouldConvertToStartOfDay() {
        final LocalDate parse = LocalDate.parse("2017-01-01");
        assertThat(TimeDateConverter.convertDateToStartOfDayInstant(parse)).
                isEqualTo(LocalDateTime.parse("2017-01-01T00:00:00").atZone(ZoneId.systemDefault()).toInstant());
    }

    @Test
    void shouldConvertToLocalDate() {
        Instant instant = Instant.parse("2014-12-07T07:52:43.900Z");
        assertThat(TimeDateConverter.convertInstantToDate(instant)).
                isEqualTo(LocalDate.parse("2014-12-07"));
    }

    @Test
    void shouldConvertToLocalDateTime() {
        Instant instant = Instant.parse("2014-12-07T07:52:43.900Z");
        assertThat(TimeDateConverter.convertInstantToDateTime(instant)).
                isEqualTo(LocalDateTime.parse("2014-12-07T08:52:43.900"));
    }

    @Test
    void shouldNotBeInPeriodForNullInstant() {
        assertThat(TimeDateConverter.isInstantInPeriod(null, LocalDate.MIN, LocalDate.MAX)).
                isFalse();
    }

    @Test
    void shouldBeInPeriodForSameInstant() {
        assertThat(TimeDateConverter
                .isInstantInPeriod(Instant.parse("2017-01-13T07:52:43.900Z"), LocalDate.of(2017, 1, 13),
                        LocalDate.of(2017, 1, 13))).isTrue();
    }

    @Test
    void shouldNotBeInPeriodForNanoEarly() {
        assertThat(TimeDateConverter
                .isInstantInPeriod(LocalDateTime.parse("2017-01-01T23:59:59.999999999").atZone(ZoneId.systemDefault()).toInstant(), LocalDate.of(2017, 1, 13),
                        LocalDate.of(2017, 1, 13))).isFalse();
    }

    @Test
    void shouldNotBeInPeriodForNanoLate() {
        assertThat(TimeDateConverter
                .isInstantInPeriod(LocalDateTime.parse("2017-01-14T00:00:00.001").atZone(ZoneId.systemDefault()).toInstant(), LocalDate.of(2017, 1, 13),
                        LocalDate.of(2017, 1, 13))).isFalse();
    }

    @Test
    void shouldConvertLocalDateTimeToInstant() {
        LocalDateTime localDateTime = LocalDateTime.of(2019, 1, 24, 13, 40);
        assertThat(TimeDateConverter.convertLocalDateTimeToInstant(localDateTime)).
                isEqualTo(LocalDateTime.of(2019, 1, 24, 12, 40).toInstant(ZoneOffset.UTC));
    }

    @Test
    void shouldReturnNullWhenLocalDateIsNull() {
        assertThat(TimeDateConverter.convertDateToEndOfDayInstant(null)).isNull();
        assertThat(TimeDateConverter.convertDateToStartOfDayInstant(null)).isNull();
    }
}
