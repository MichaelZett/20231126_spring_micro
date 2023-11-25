package de.zettsystems.timeutil.adapter;

import de.zettsystems.timeutil.TimeUtil;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;

import java.util.Collections;

public class TimeInfoContributor implements InfoContributor {

    @Override
    public void contribute(Builder builder) {
        builder.withDetail("time", Collections.singletonMap("current", TimeUtil.now()));
    }

}
