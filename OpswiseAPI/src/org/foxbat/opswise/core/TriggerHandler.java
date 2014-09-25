package org.foxbat.opswise.core;

import java.util.Map;

import org.foxbat.opswise.util.JsonX;
import org.json.JSONObject;

public class TriggerHandler extends OpswiseObjectHandler {
    private static final String OBJECT = "trigger";
    private final JsonX ops_config;

    public TriggerHandler(JsonX ops_config) {
        this.ops_config = ops_config;
    }


    public JsonX createCron(JsonX request_config) {
        OpsMockUserInterface trigger = new OpsMockUserInterface(this.ops_config);
        trigger.createTrigger(request_config);
        return null; // the future implementation will have the request's response information in JsonX bundle.
    }

    public JSONObject launch(JSONObject request_config) {
        return this.makeRequest(new JsonX(request_config), this.ops_config, OBJECT, "launch");
    }

    public JSONObject createTempTrigger(JSONObject request_config) {
        return this.makeRequest(new JsonX(request_config),this.ops_config, OBJECT, "create_temp");
    }

    public JSONObject switchTrigger(JSONObject request_config) {
        return this.makeRequest(new JsonX(request_config),this.ops_config, OBJECT, "switch");
    }

    public Map<String, Object> queryTriggers() {
        return null;
    }

}
