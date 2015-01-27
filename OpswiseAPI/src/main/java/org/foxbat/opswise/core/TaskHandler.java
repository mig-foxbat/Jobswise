package org.foxbat.opswise.core;

import org.foxbat.opswise.util.DBConnectionManager;
import org.foxbat.opswise.util.OpswiseModelManager;
import org.json.JSONObject;
import org.foxbat.opswise.util.JsonX;

public class TaskHandler extends OpswiseObjectHandler {
    private static final String OBJECT = "task";
    private final JsonX ops_config;

    public TaskHandler(JsonX ops_config) {
        this.ops_config = ops_config;
    }

    public JsonX create(JsonX request_json) {
        JsonX output = this.makeRequest(request_json, this.ops_config, OBJECT, "create");
        DBConnectionManager dbc = new DBConnectionManager(this.ops_config);
        OpswiseModelManager ops_model = new OpswiseModelManager(dbc);
        String sys_id = ops_model.getTaskSysID(request_json.getString("name"));

        try {
            if (sys_id == null) {
                throw new OpswiseAPIException("Task Creation failed");
            }
        }
        finally {
            dbc.close();
            JsonX response = new JsonX();
            response.setString("sys_id",sys_id);
            return response;
        }
    }

    public JsonX update(JsonX request_json,String task_name) {
        OpsMockUserInterface ops = new OpsMockUserInterface(ops_config);
        ops.updateTask(request_json,task_name);
        JsonX response = new JsonX();
        response.setString("success","job updated");
        return response;
    }

    public JsonX delete(JsonX request_json) {
        OpsMockUserInterface ops = new OpsMockUserInterface(ops_config);
        ops.deleteTask(request_json);
        return null;
    }


    public JsonX launch(JSONObject request_json) {
        return this.makeRequest(new JsonX(request_json), this.ops_config, OBJECT, "launch");
    }

    public JsonX queryTaskList(JSONObject request_json) {
        return this.makeRequest(new JsonX(request_json),this.ops_config, OBJECT, "query");
    }

}
