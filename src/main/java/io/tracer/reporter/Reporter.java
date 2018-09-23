package io.tracer.reporter;

import io.tracer.Trace;

import java.util.List;

public interface Reporter {

    void start();
    void finish();
    void report(List<Trace.FinishedTrace> finishedTraces);
}