package io.tracer;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Trace {
    private String name;
    private Instant startInstant;
    private Instant endInstant;
    private List<Mark> marks;

    public Trace(String name) {
        this.name = name;
        this.marks = new ArrayList<>();
    }

    public void start() {
        this.startInstant = Instant.now();
    }

    public FinishedTrace finish() {
        this.endInstant = Instant.now();
        FinishedTrace finishedTrace = new FinishedTrace(this.name, this.startInstant, this.endInstant, this.marks);
        Tracer.addFinishedTrace(finishedTrace);
        return finishedTrace;
    }

    public void mark(String name) {
        this.marks.add(new Mark(name, Instant.now()));
    }

    public class FinishedTrace {
        private String name;
        private Instant startInstant;
        private Instant endInstant;
        private List<Mark> marks;

        public FinishedTrace(String name, Instant startInstant, Instant endInstant, List<Mark> marks) {
            this.name = name;
            this.startInstant = startInstant;
            this.endInstant = endInstant;
            this.marks = marks;
        }

        public String getName() {
            return name;
        }

        public Instant getStartInstant() {
            return startInstant;
        }

        public Instant getEndInstant() {
            return endInstant;
        }

        public List<Mark> getMarks() {
            return marks;
        }
    }
}
