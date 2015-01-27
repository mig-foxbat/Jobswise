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


    public void switchTrigger(JsonX request_config) {
        JsonX form = new JsonX();
        DBConnectionManager dbc = new DBConnectionManager(ops_config);
        OpswiseModelManager ops_model = new OpswiseModelManager(dbc);
        form.setString("sys_action", (request_config.getString("ops_trigger_cron.action")));
        form.setString("sys_target", "ops_trigger_cron");
        form.setString("sys_uniqueName", "sys_id");
        form.setString("sys_uniqueValue", ops_model.getTriggerSysID(request_config.getString("ops_trigger_cron.name")));
        RestAPIManager rest = this.login();
        rest.postForm(AppConfig.getInstance().config.getJSONObject("url")
                .getJSONObject("trigger").getString("portal_enable"), form);
        try {
            if (request_config.getString("ops_trigger_cron.action").equalsIgnoreCase("disable_trigger"))
                if (ops_model.isTriggerEnabled(request_config.getString("ops_trigger_cron.name")))
                    throw new OpswiseAPIException("Disabling Cron Trigger Failed");
                else if (request_config.getString("ops_trigger_cron.action").equalsIgnoreCase("enable_trigger"))
                    if (ops_model.isTriggerEnabled(request_config.getString("ops_trigger_cron.name")))
                        throw new OpswiseAPIException("Enabling Cron Trigger failed");
        } finally {
            dbc.close();
        }
    }


    public void deleteTrigger(JsonX request_config) {
        JsonX form = new JsonX();
        DBConnectionManager dbc = new DBConnectionManager(ops_config);
        OpswiseModelManager ops_model = new OpswiseModelManager(dbc);
        form.setString("sys_action", "sysverb_delete");
        form.setString("sys_target", "ops_trigger_cron");
        form.setString("sys_uniqueName", "sys_id");
        form.setString("sys_uniqueValue", ops_model.getTriggerSysID(request_config.getString("ops_trigger_cron.name")));
        RestAPIManager rest = this.login();
        rest.postForm(AppConfig.getInstance().config.getJSONObject("url")
                .getJSONObject("trigger").getString("delete"), form);
        try {
            if (ops_model.doesTriggerExists(request_config.getString("ops_trigger_cron.name")))
                throw new OpswiseAPIException("Cron Trigger deletion failed");
        }
        finally {
            dbc.close();
        }
    }


    public void deleteTask(JsonX request_config) {
        JsonX form = new JsonX();
        DBConnectionManager dbc = new DBConnectionManager(ops_config);
        OpswiseModelManager ops_model = new OpswiseModelManager(dbc);
        form.setString("sys_action", "sysverb_delete");
        form.setString("sys_target", "ops_task_unix");
        form.setString("sys_uniqueName", "sys_id");
        form.setString("sys_uniqueValue", ops_model.getTaskSysID(request_config.getString("ops_task_unix.name")));
        RestAPIManager rest = this.login();
        rest.postForm(AppConfig.getInstance().config.getJSONObject("url")
                .getJSONObject("task").getString("delete"), form);
        try {
            if (ops_model.doesTaskExists(request_config.getString("ops_task_unix.name")))
                throw new OpswiseAPIException("Unix Task deletion failed");
        }
        finally {
            dbc.close();
        }
    }



    public void createTrigger(JsonX request_config) {
        JsonX ref_config = Utils.getJSONConfig(AppConfig.getPath() + "/opswise/trigger/create.json");
        ref_config = Utils.mergeMaps(ref_config, request_config);
        DBConnectionManager dbc = new DBConnectionManager(this.ops_config);
        OpswiseModelManager ops_model = new OpswiseModelManager(dbc);
        ref_config.setString("ops_trigger_cron.task", ops_model.getTaskSysID(ref_config.getString("ops_trigger_cron.task")));
        ref_config.setString("ops_trigger_cron.calendar", ops_model.getCalendarSysID(ref_config.getString("ops_trigger_cron.calendar")));
        RestAPIManager rest = this.login();
        rest.postForm(AppConfig.getInstance().config.getJSONObject("url").getJSONObject("trigger").getString("create"), ref_config);
        this.logout(rest);
        try {
            if (!ops_model.doesTriggerExists(ref_config.getString("ops_trigger_cron.name"))) {
                throw new OpswiseAPIException("Trigger Creation Failed");
            }
        } finally {
            dbc.close();
        }

    }


    public void updateTask(JsonX request_config,String task_name) {
        DBConnectionManager dbc = new DBConnectionManager(this.ops_config);
        OpswiseModelManager ops_model = new OpswiseModelManager(dbc);
        if (request_config.hasKey("ops_task_unix.agent"))
            request_config.setString("ops_task_unix.agent",ops_model.getAgentSysID(request_config.getString("ops_task_unix.agent")));
        request_config.setString("sys_target","ops_task_unix");
        request_config.setString("sys_action","sysverb_update");
        request_config.setString("sys_uniqueName","sys_id");
        request_config.setString("sys_uniqueValue",ops_model.getTaskSysID(task_name));
        RestAPIManager rest = this.login();
        RestResponse response = rest.postForm(AppConfig.getInstance().config.getJSONObject("url").getJSONObject("task").getString("update"), request_config);
        System.out.println("I am here");
        System.out.println(response.payload);
        this.logout(rest);
        dbc.close();
    }

//      Feature currently not working
//    public void updateTaskEmailNotifications(JsonX request_config,String taskname) {
//        DBConnectionManager dbc = new DBConnectionManager(this.ops_config);
//        OpswiseModelManager ops_model = new OpswiseModelManager(dbc);
//        request_config.setString("sys_target","ops_email_notification");
//        request_config.setString("sys_action","sysverb_update");
//        request_config.setString("sys_uniqueName","sys_id");
//        request_config.setString("sys_uniqueValue",ops_model.getNotificationID(taskname));
//    }
//

    private void logout(RestAPIManager rest) {
        //TODO
        //http://pit-dev-owmaster1.snc1/opswise/logout.do
    }


}
