package io.tracer;

import io.tracer.reporter.Reporter;

import java.util.ArrayList;
import java.util.List;

public class Tracer {
    private static List<Trace> traces = new ArrayList<>();
    private static List<Trace.FinishedTrace> finishedTraces = new ArrayList<>();
    private static Reporter reporter;

    public synchronized static TraceBuilder buildTrace() {
        return new TraceBuilder();
    }

    public synchronized static void setReporter(Reporter newReporter) {
        reporter = newReporter;
    }

    public synchronized static void report() {
        reporter.report(finishedTraces);
    }

    public synchronized static void clearTraces() {
        finishedTraces.clear();
    }

    public synchronized static void reportAndClearTraces() {
        report();
        clearTraces();
    }

    protected synchronized static void addFinishedTrace(Trace.FinishedTrace finishedTrace) {
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