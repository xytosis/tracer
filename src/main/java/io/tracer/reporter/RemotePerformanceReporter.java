package io.tracer.reporter;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.tracer.Trace;
import io.tracer.TraceDuration;
import io.tracer.TraceInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemotePerformanceReporter implements Reporter {
    private String url;
    private String source;

    public RemotePerformanceReporter(String url, String source) {
        this.url = url;
        this.source = source;
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }

    @Override
    public void report(List<Trace.FinishedTrace> finishedTraces) {
        List<TraceInfo> infos = new ArrayList<>();
        for (Trace.FinishedTrace finishedTrace: finishedTraces) {
            infos.add(new TraceInfo(finishedTrace));
        }
        JSONArray traceInfosJSON = traceInfosToJSON(infos);
        try {
            HttpResponse<String> response = Unirest.post(this.url)
                    .header("Content-Type", "application/json")
                    .header("source", this.source)
                    .body(traceInfosJSON.toString())
                    .asString();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private JSONArray traceInfosToJSON(List<TraceInfo> infos) {
        JSONArray infosJSON = new JSONArray();
        for (TraceInfo info: infos) {
            infosJSON.put(traceInfoToJSON(info));
        }
        return infosJSON;
    }

    private JSONObject traceInfoToJSON(TraceInfo info) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", info.getName());
        jsonObject.put("totalTime", info.getTotalTimeMillis());
        JSONArray list = new JSONArray();
        for (TraceDuration duration: info.getDurations()) {
            JSONObject durationJSON = new JSONObject();
            durationJSON.put("name", duration.getName());
            durationJSON.put("duration", duration.getDuration());
            durationJSON.put("startEpoch", duration.getStart());
            durationJSON.put("endEpoch", duration.getEnd());
            list.put(durationJSON);
        }
        jsonObject.put("durations", list);
        return jsonObject;
    }
}
