package de.zettsystems.timeutil.adapter;

import de.zettsystems.timeutil.TimeMachine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/time/travel")
public class TimeTravelController {

    @PostMapping("freeze")
    public void travelTo(@RequestParam Instant timestamp) {
        TimeMachine.freeze(timestamp);
    }

    @PostMapping("reset")
    public void reset() {
        TimeMachine.reset();
    }

}