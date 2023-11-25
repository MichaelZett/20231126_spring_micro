package de.zettsystems.timeutil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("changeEnv") // junit 5 and gradle changed the env/context and fails
class TimeMachineMockTest {

    @BeforeEach
    public void resetClock() {
        TimeMachine.reset();
    }

    @Test
    void timeTravelingShouldBeAllowedOnDt() {
        TimeMachine.setTravelingDisabled(false);

        Instant now = TimeMachine.now();
        TimeMachine.setOffsetInHours(-24L);
        Instant traveledNow = TimeMachine.now();

        assertThat(now).isAfter(traveledNow);
    }

    @Test
    void timeTravelingShouldNotBeAllowedOnProd() {
        TimeMachine.setTravelingDisabled(true);

        Instant now = TimeMachine.now();
        TimeMachine.setOffsetInHours(-24L);
        Instant traveledNow = TimeMachine.now();

        assertThat(now).isBeforeOrEqualTo(traveledNow);
    }
}
