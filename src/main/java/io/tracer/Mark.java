package io.tracer;

import java.time.Instant;

public class Mark {
    private String tag;
    private Instant instant;

    public Mark(String tag, Instant instant) {
        this.tag = tag;
        this.instant = instant;
    }

    public String getTag() {
        return tag;
    }

    public Instant getInstant() {
        return instant;
    }
}
