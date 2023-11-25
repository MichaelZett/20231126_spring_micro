package de.zettsystems.timeutil;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class InstantHelperTest {

    @AfterEach
    void tearDown() throws Exception {
        TimeMachine.reset();
    }

    @Test
    void shouldGetTheCurrentTimeAsString() {
        TimeMachine.freeze();
        Instant now = InstantHelper.now();
        String nowAsString = InstantHelper.nowAsString();

        assertThat(nowAsString).isEqualTo(now.toString());
    }

    @Test
    void shouldGetTheSameTimeTwiceAfterFreeze() throws InterruptedException {
        TimeMachine.freeze();
        String now1 = InstantHelper.nowAsString();
        TimeUnit.SECONDS.sleep(1); //NOSONAR
        String now2 = InstantHelper.nowAsString();

        assertThat(now1).isEqualTo(now2);
    }

    @Test
    void shouldGetTheSameTimeTwiceAfterFreezeWithGivenTime() throws InterruptedException {
        Instant now = TimeUtil.now();
        TimeUnit.SECONDS.sleep(1); //NOSONAR
        TimeMachine.freeze(now);
        Instant now1 = InstantHelper.now();
        TimeUnit.SECONDS.sleep(1); //NOSONAR
        Instant now2 = InstantHelper.now();

        assertThat(now).isEqualTo(now1).isEqualTo(now2);
    }

    @Test
    void shouldReturnNullWhenLocalDateIsNull() {

        final Instant instantEndOfDay = InstantHelper.convertLocalDateToEndOfDayInstant(null, LegalEntity.GERMAN_COMPANY.getZoneId());
        final Instant instantStartOfDay = InstantHelper.convertLocalDateToStartOfDayInstant(null, LegalEntity.GERMAN_COMPANY.getZoneId());

        assertThat(instantEndOfDay).isNull();
        assertThat(instantStartOfDay).isNull();
    }
}
