package org.foxbat.opswise.core;

import org.json.JSONObject;
import org.foxbat.opswise.util.JsonX;

public class TaskHandler extends OpswiseObjectHandler {
    private static final String OBJECT = "task";
    private final JsonX ops_config;

    public TaskHandler(JsonX ops_config) {
        this.ops_config = ops_config;
    }

    public JSONObject create(JSONObject request_json) {
        return this.makeRequest(new JsonX(request_json), this.ops_config, OBJECT, "create");
    }

    public JSONObject launch(JSONObject request_json) {
        return this.makeRequest(new JsonX(request_json), this.ops_config, OBJECT, "launch");
    }

    public JSONObject queryTaskList(JSONObject request_json) {
        return this.makeRequest(new JsonX(request_json),this.ops_config, OBJECT, "query");
    }
}
