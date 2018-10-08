package io.tracer;

import io.tracer.reporter.RemotePerformanceReporter;
import io.tracer.reporter.Reporter;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

public class TracerTest {

    @Test
    public void testTracer() throws Exception {
        Tracer.setReporter(new RemotePerformanceReporter("http://localhost:5000", "test"));
        Trace t = Tracer.buildTrace()
                .withName("Test trace")
                .build();
        t.start();
        Thread.sleep(500);
        t.mark("hey");
        Thread.sleep(500);
        t.mark("yo");

        Tracer.reportFinishedTrace(t.finish());
    }
}
