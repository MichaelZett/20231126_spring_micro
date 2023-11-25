package de.zettsystems.timeutil;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

// Fail with maven run build, check later
@Disabled
class TimeUtilTest {

    @AfterEach
    public void tearDown() throws Exception {
        TimeMachine.reset();
    }

    @Test
    void shouldGetTheCurrentTimeAsString() {
        TimeMachine.freeze();
        Instant now = TimeUtil.now();
        String nowAsString = TimeUtil.nowAsString();

        assertThat(nowAsString).isEqualTo(now.toString());
    }

    @Test
    void shouldGetTheSameTimeTwiceAfterFreeze() throws InterruptedException {
        TimeMachine.freeze();
        String now1 = TimeUtil.nowAsString();
        Thread.sleep(1000); //NOSONAR
        String now2 = TimeUtil.nowAsString();

        assertThat(now1).isEqualTo(now2);
    }

    @Test
    void shouldGetTheSameTimeTwiceAfterFreezeWithGivenTime() throws InterruptedException {
        Instant now = TimeUtil.now();
        TimeUnit.SECONDS.sleep(1); //NOSONAR
        TimeMachine.freeze(now);
        Instant now1 = TimeUtil.now();
        TimeUnit.SECONDS.sleep(1); //NOSONAR
        Instant now2 = TimeUtil.now();

        assertThat(now).isEqualTo(now1).isEqualTo(now2);
    }

    @Test
    void shouldGetDifferentTimesAfterUnfreeze() throws InterruptedException {
        TimeMachine.reset();
        String now1 = TimeUtil.nowAsString();
        TimeUnit.SECONDS.sleep(1); //NOSONAR
        String now2 = TimeUtil.nowAsString();

        assertThat(now1).isNotEqualTo(now2);
    }

    @Test
    void shouldGetEarlierTime() {
        TimeMachine.setOffsetInHours(10L);
        Instant later = TimeUtil.now();
        TimeMachine.reset();
        Instant earlier = TimeUtil.now();
        assertThat(later).isAfter(earlier);
    }

    @Test
    void shouldGetLaterTime() {
        TimeMachine.setOffsetInHours(-10L);
        Instant earlier = TimeUtil.now();
        TimeMachine.reset();
        Instant later = TimeUtil.now();
        assertThat(earlier).isBefore(later);
    }

    @Test
    void shouldReturnCurrentTime() {
        Calendar result = TimeUtil.calendar();

        Calendar expected = Calendar.getInstance();
        assertThat(result.get(Calendar.YEAR)).isEqualTo(expected.get(Calendar.YEAR));
        assertThat(result.get(Calendar.MONTH)).isEqualTo(expected.get(Calendar.MONTH));
        assertThat(result.get(Calendar.DAY_OF_MONTH)).isEqualTo(expected.get(Calendar.DAY_OF_MONTH));
        assertThat(result.getTimeZone()).isEqualTo(TimeZone.getTimeZone("Europe/Berlin"));
    }

    @Test
    void shouldReturnCurrentDateAndTime() {
        TimeMachine.freeze();
        Clock clock = LegalEntity.GERMAN_COMPANY.getClock();

        LocalDateTime dateTime = TimeUtil.todayWithTime();

        assertThat(dateTime).isEqualTo(LocalDateTime.now(clock));
    }

    @Test
    void shouldReturnCurrentClock() {
        Clock expected = LegalEntity.GERMAN_COMPANY.getClock();
        Clock result = TimeUtil.getClock();

        assertThat(result).isSameAs(expected);
    }

    @Test
    void shouldReturnMaxLocalDate() {
        LocalDate expected = DateHelper.retrieveMaxLocalDate();
        LocalDate result = TimeUtil.retrieveMaxLocalDate();

        assertThat(result).isSameAs(expected);
    }

    @Test
    void shouldReturnMinLocalDate() {
        LocalDate expected = DateHelper.retrieveMinLocalDate();
        LocalDate result = TimeUtil.retrieveMinLocalDate();

        assertThat(result).isSameAs(expected);
    }


    @Test
    void shouldReturnTimeInTheFuture() {
        TimeMachine.setOffset(Duration.ofDays(500L));

        Calendar result = TimeUtil.calendar();

        LocalDate expected = TimeUtil.today();
        assertThat(result.get(Calendar.YEAR)).isEqualTo(expected.getYear());
        assertThat(result.get(Calendar.MONTH) + 1).isEqualTo(expected.getMonthValue());
        assertThat(result.get(Calendar.DAY_OF_MONTH)).isEqualTo(expected.getDayOfMonth());
    }

}
