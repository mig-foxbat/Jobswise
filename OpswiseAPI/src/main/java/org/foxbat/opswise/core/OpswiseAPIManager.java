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
        json.setString("ops_trigger_cron.name", "tgr_u_chlr_sql_test_mod1");
        json.setString("ops_trigger_cron.month", "*");
        json.setString("ops_trigger_cron.minutes", "2,6");
        json.setString("ops_trigger_cron.hours", "12");
        json.setString("ops_trigger_cron.day_of_week", "3");
        json.setString("ops_trigger_cron.day_of_month", "4");
        JsonX result = opswise.getTriggerHandler().update(json,"tgr_u_chlr_sql_test_mod");
        System.out.println(result);
    }


}
