package de.zettsystems.netzfilm.common.values;

import de.zettsystems.timeutil.TimeUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.time.LocalTime;

public class AfternoonCondition implements Condition {
    private static final LocalTime NOON = LocalTime.of(12, 0);

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        final LocalTime now = TimeUtil.todayWithTime().toLocalTime();
        return now.isAfter(NOON);
    }
}