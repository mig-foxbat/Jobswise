package org.foxbat.opswise.core;

import org.foxbat.opswise.AppConfig;
import org.foxbat.opswise.util.Utils;
import org.foxbat.opswise.util.JsonX;


public class OpswiseAPIManager {
    JsonX ops_config;


    public OpswiseAPIManager(String config_dir,String ops_config_file) {
        this.ops_config = new JsonX(Utils.getJSONConfig(config_dir+"/"+ops_config_file));
        AppConfig.setConfigDir(config_dir);
    }

    public TaskHandler getTaskHandler() {
        return new TaskHandler(this.ops_config);
    }

    public TriggerHandler getTriggerHandler() {
        return new TriggerHandler(this.ops_config);
    }

}
