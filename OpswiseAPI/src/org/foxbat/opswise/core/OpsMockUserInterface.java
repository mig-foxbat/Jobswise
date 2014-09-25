package org.foxbat.opswise.core;

import org.foxbat.opswise.AppConfig;
import org.foxbat.opswise.util.*;
import org.json.JSONObject;

public class OpsMockUserInterface {
	private JsonX ops_config;

	public OpsMockUserInterface(JsonX ops_config) {
        this.ops_config = ops_config;
	}


    public RestAPIManager login() {
        RestAPIManager rest = new RestAPIManager(ops_config);
        JsonX form = new JsonX(new JSONObject());
        form.setString("user_name", ops_config.getJSONObject("server").getString("username"));
        form.setString("user_password", ops_config.getJSONObject("server").getString("password"));
        form.setString("sys_action", "sysverb_login");
        rest.postForm(AppConfig.getInstance().config.getJSONObject("url")
                .getJSONObject("general").getString("login"), form);
        return rest;
    }


    public void enableTrigger(JsonX request_config) {
        RestAPIManager rest = new RestAPIManager(ops_config);
        JsonX form = new JsonX(new JSONObject());
        form.setString("sys_action","enable_trigger");
        form.setString("sys_target","ops_trigger_cron");
        form.setString("sys_uniqueName","sys_id");
        form.setString("sys_uniqueValue", Utils.getMd5Hash(request_config.getString("name")));
        rest.postForm(AppConfig.getInstance().config.getJSONObject("url")
                .getJSONObject("trigger").getString("portal_enable"), form);
    }


    public void createTrigger(JsonX request_config) {
        JsonX ref_config = Utils.getJSONConfig(AppConfig.getPath() + "/opswise/trigger/create.json");
        ref_config = Utils.mergeMaps(ref_config,request_config);
        DBConnectionManager dbc = new DBConnectionManager(this.ops_config);
        ref_config.setString("ops_trigger_cron.task",dbc.getTaskSysID(ref_config.getString("ops_trigger_cron.task")));
        ref_config.setString("ops_trigger_cron.calendar",dbc.getCalendarSysID(ref_config.getString("ops_trigger_cron.calendar")));
        RestAPIManager rest = this.login();
        rest.postForm(AppConfig.getInstance().config.getJSONObject("url").getJSONObject("trigger").getString("create"),ref_config);
        this.logout(rest);
    }

    public void createTask(JsonX request_config) {
        JsonX ref_config = Utils.getJSONConfig(AppConfig.getPath() + "/opswise/task/create.json");
        ref_config = Utils.mergeMaps(ref_config,request_config);
        DBConnectionManager dbc = new DBConnectionManager(this.ops_config);
        ref_config.setString("ops_task_unix.agent", dbc.getAgentSysID(request_config.getString("ops_task_unix.agent")));
        RestAPIManager rest = this.login();
        rest.postForm(AppConfig.getInstance().config.getJSONObject("url").getJSONObject("task").getString("create"),ref_config);
        this.logout(rest);
    }


    private void logout(RestAPIManager rest) {
        //TODO
        //http://pit-dev-owmaster1.snc1/opswise/logout.do
    }



}
