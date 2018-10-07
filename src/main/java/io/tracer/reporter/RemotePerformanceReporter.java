package io.tracer.reporter;

import io.tracer.Trace;
import io.tracer.TraceDuration;
import io.tracer.TraceInfo;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

public class RemotePerformanceReporter implements Reporter {
    private HttpClient client;
    private String url;
    private String source;

    public RemotePerformanceReporter(String url, String source) {
        client = HttpClients.createDefault();
        this.url = url;
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }

    @Override
    public void report(List<Trace.FinishedTrace> finishedTraces) {
        List<TraceInfo> infos = finishedTraces.stream().map((trace) -> new TraceInfo(trace)).collect(Collectors.toList());
        JSONArray traceInfosJSON = traceInfosToJSON(infos);
        try {
            HttpPost post = new HttpPost(this.url);
            post.addHeader("content-type", "application/json");
            StringEntity params = new StringEntity(traceInfosJSON.toJSONString());
            post.setEntity(params);
            this.client.execute(post);
        } catch (Exception e) {

        }
    }

    private JSONArray traceInfosToJSON(List<TraceInfo> infos) {
        JSONArray infosJSON = new JSONArray();
        for (TraceInfo info: infos) {
            infosJSON.add(traceInfoToJSON(info));
        }
        return infosJSON;
    }

    private JSONObject traceInfoToJSON(TraceInfo info) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", info.getName());
        jsonObject.put("totalTime", info.getTotalTimeMillis());
        jsonObject.put("source", this.source);
        JSONArray list = new JSONArray();
        for (TraceDuration duration: info.getDurations()) {
            JSONObject durationJSON = new JSONObject();
            durationJSON.put("name", duration.getName());
            durationJSON.put("duration", duration.getDuration());
            durationJSON.put("startEpoch", duration.getStart());
            durationJSON.put("endEpoch", duration.getEnd());
            list.add(durationJSON);
        }
        jsonObject.put("durations", list);
        return jsonObject;
    }
}
