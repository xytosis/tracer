# tracer

A simple segmented performance measuring tool for JVM applications, including Android that can log results to a remote server.

# Usage

Open up a local server at port 5000 using `nc -l 5000`.

```
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

```
