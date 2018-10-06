package io.tracer;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TraceInfo {
    private List<TraceDuration> durations;

    public TraceInfo(Trace.FinishedTrace finishedTrace) {
        this.durations = new ArrayList<>();
        Instant start = finishedTrace.getStartInstant();
        Instant end = finishedTrace.getEndInstant();
        List<Mark> marks = finishedTrace.getMarks();
        for (int i = 0; i < marks.size(); i++) {
            Mark currentMark = marks.get(i);
            if (i == 0) {
                this.durations.add(new TraceDuration(start, currentMark.getInstant(), currentMark.getTag()));
            } else {
                this.durations.add(new TraceDuration(marks.get(i - 1).getInstant(), currentMark.getInstant(), currentMark.getTag()));
            }
        }
        this.durations.add(new TraceDuration(marks.get(marks.size() - 1).getInstant(), end, finishedTrace.getName()));
    }

    public String toDebugString() {
        StringBuilder builder = new StringBuilder();
        for (TraceDuration duration: this.durations) {
            builder.append(duration.getDuration()).append(",").append(duration.getName()).append("\n");
        }
        return builder.toString();
    }

    public long getTotalTimeMillis() {
        long totalTime = 0;
        for (TraceDuration duration: this.durations) {
            totalTime += duration.getDuration();
        }
        return totalTime;
    }
}
