package de.zettsystems.timeutil;

import org.assertj.core.util.VisibleForTesting;
import org.slf4j.Logger;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * use this class for system wide Time travelling.
 */
public final class TimeMachine {
    private static final Map<ZoneId, Clock> CLOCKS_CACHE = new ConcurrentHashMap<>();
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(TimeMachine.class);
    private static Clock clock = Clock.systemUTC();

    private static boolean travelingDisabled = false;

    static {
        String disableTimetravelParam = System.getenv("DISABLE_TIMETRAVEL");
        if (null != disableTimetravelParam) {
            travelingDisabled = Boolean.parseBoolean(disableTimetravelParam);
        }
    }

    private TimeMachine() {
        // not intended
    }

    static Clock withZoneId(ZoneId zoneId) {
        return CLOCKS_CACHE.computeIfAbsent(zoneId, clock::withZone);
    }

    /**
     * freeze the current time
     */
    public static void freeze() {
        freeze(Instant.now());
    }

    /**
     * freeze clock at the given time
     *
     * @param fixedInstant the given time
     */
    public static void freeze(Instant fixedInstant) {
        setClock(Clock.fixed(fixedInstant, ZoneOffset.UTC));
    }

    /**
     * travel and freeze to given LocalDateTime
     *
     * @param localDateTime
     */
    public static void freeze(LocalDateTime localDateTime) {
        freeze(localDateTime, LegalEntity.GERMAN_COMPANY);
    }

    public static void freeze(LocalDateTime localDateTime, LegalEntity legalEntity) {
        freeze(legalEntity.convertToInstant(localDateTime));
    }

    /**
     * travel to given time
     *
     * @param localDateTime
     */
    public static void travelTo(LocalDateTime localDateTime, LegalEntity legalEntity) {
        setOffset(Duration.between(Instant.now(), legalEntity.convertToInstant(localDateTime)));
    }

    /**
     * travel to given time
     *
     * @param localDateTime
     */
    public static void travelTo(LocalDateTime localDateTime) {
        travelTo(localDateTime, LegalEntity.GERMAN_COMPANY);
    }


    /**
     * back to current time
     */
    public static void reset() {
        setClock(Clock.systemUTC());
    }

    /**
     * put the time backwards (negative hours) or forward
     *
     * @param hours hours to shift time
     */
    public static void setOffsetInHours(long hours) {
        setOffset(Duration.ofHours(hours));
    }

    /**
     * put the time backwards (negative) or forward
     *
     * @param offset duration to add
     */
    public static void setOffset(Duration offset) {
        setClock(Clock.offset(Clock.systemUTC(), offset));
    }

    static Instant now() {
        return clock.instant();
    }

    private static synchronized void setClock(Clock clock) {
        if (!travelingDisabled()) {
            TimeMachine.clock = clock;
            CLOCKS_CACHE.clear();
            LOG.info("Time travel request accepted. new current time : {}", TimeUtil.nowAsString());
        } else {
            LOG.info("Time travel request rejected. Environment variable 'DISABLE_TIMETRAVEL' is set to 'true'.");
        }
    }

    private static boolean travelingDisabled() {
        return travelingDisabled;
    }

    @VisibleForTesting
    static void setTravelingDisabled(boolean travelingDisabled) {
        TimeMachine.travelingDisabled = travelingDisabled;
    }
}
