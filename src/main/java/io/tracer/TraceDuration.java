package io.tracer;

import java.time.Duration;
import java.time.Instant;

public class TraceDuration {
    private Instant start;
    private Instant end;
    private String name;

    public TraceDuration(Instant start, Instant end, String name) {
        this.start = start;
        this.end = end;
        this.name = name;
    }

    public long getDuration() {
        return Duration.between(start, end).toMillis();
    }

    public long getStart() {
        return this.start.toEpochMilli();
    }

    public long getEnd() {
        return this.end.toEpochMilli();
    }

    public String getName() {
        return this.name;
    }
}