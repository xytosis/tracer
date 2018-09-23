package io.tracer;

import io.tracer.reporter.Reporter;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

public class TracerTest {

    @Test
    public void testTracer() throws Exception {
        Tracer.setReporter(new TestReporter());
        Trace t = Tracer.buildTrace("hello").build();
        t.start();
        Thread.sleep(500);
        t.mark("hey");
        Thread.sleep(500);
        t.mark("yo");
        t.finish();

        Tracer.report();
    }

    private class TestReporter implements Reporter {

        @Override
        public void start() {

        }

        @Override
        public void finish() {

        }

        @Override
        public void report(List<Trace.FinishedTrace> finishedTraces) {
            System.out.println("got here");
            System.out.println(finishedTraces.size());
            for (Trace.FinishedTrace finishedTrace: finishedTraces) {
                System.out.println(Duration.between(finishedTrace.getStartInstant(), finishedTrace.getEndInstant()).toMillis());
            }
        }
    }
}
