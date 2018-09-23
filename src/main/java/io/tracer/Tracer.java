package io.tracer;

import io.tracer.reporter.Reporter;

import java.util.ArrayList;
import java.util.List;

public class Tracer {
    private static List<Trace> traces = new ArrayList<>();
    private static List<Trace.FinishedTrace> finishedTraces = new ArrayList<>();
    private static Reporter reporter;

    public static TraceBuilder buildTrace(String name) {
        return new TraceBuilder();
    }

    public static void setReporter(Reporter newReporter) {
        reporter = newReporter;
    }

    public static void report() {
        reporter.report(finishedTraces);
    }

    protected static void addFinishedTrace(Trace.FinishedTrace finishedTrace) {
        finishedTraces.add(finishedTrace);
    }

    public static class TraceBuilder {
        private String name;

        public TraceBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Trace build() {
            Trace newTrace = new Trace(this.name);
            traces.add(newTrace);
            return newTrace;
        }
    }
}