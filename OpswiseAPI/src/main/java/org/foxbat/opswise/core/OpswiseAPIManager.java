package org.foxbat.opswise.core;

import org.foxbat.opswise.AppConfig;
import org.foxbat.opswise.util.Utils;
import org.foxbat.opswise.util.JsonX;


public class OpswiseAPIManager {
    JsonX ops_config;


    public OpswiseAPIManager(String config_dir,String ops_config_file) {
        this.ops_config = Utils.getJSONConfig(config_dir+"/"+ops_config_file);
        AppConfig.setConfigDir(config_dir);
    }

    public TaskHandler getTaskHandler() {
        return new TaskHandler(this.ops_config);
    }

    public TriggerHandler getTriggerHandler() {
        return new TriggerHandler(this.ops_config);
    }


    public static void main(String args[]) {

        OpswiseAPIManager opswise = new OpswiseAPIManager("/Users/chlr/dev_root/intellij/Jobswise/OpswiseAPI/config","na_opswise.json");
        JsonX json = new JsonX();
        json.setString("name", "###temp_job");
        json.setString("command", "echo All Hail Megatron");
        json.setString("agent", "pit-dev-owagent1 - AGNT0007");
        JsonX email = new JsonX();
        email.setString("address", "mig.flanker@gmail.com");
        email.setString("connection", "Gmail - dw_etl");
        json.setJsonX("email", email);
        opswise.getTaskHandler().create(json);

    }


}
