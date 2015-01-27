package org.foxbat.opswise.core;


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

    public JsonX delete(JsonX request_config) {
        OpsMockUserInterface trigger = new OpsMockUserInterface(this.ops_config);
        trigger.deleteTrigger(request_config);
        return null;
    }

    public JsonX update(JsonX request_json,String task_name) {
        OpsMockUserInterface ops = new OpsMockUserInterface(ops_config);
        ops.updateTrigger(request_json, task_name);
        JsonX response = new JsonX();
        response.setString("success","job updated");
        return response;
    }
 
    public JsonX launch(JSONObject request_config) {
        return this.makeRequest(new JsonX(request_config), this.ops_config, OBJECT, "launch");
    }

    public JsonX createTempTrigger(JSONObject request_config) {
        return this.makeRequest(new JsonX(request_config),this.ops_config, OBJECT, "create_temp");
    }

    public JsonX switchTrigger(JsonX request_config) {
        OpsMockUserInterface trigger = new OpsMockUserInterface(this.ops_config);
        trigger.switchTrigger(request_config);
        return null;
    }

    public JsonX queryTriggers() {
        return null;
    }

}
