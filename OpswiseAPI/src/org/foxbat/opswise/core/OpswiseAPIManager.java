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
//       OpswiseAPIManager opswise = new OpswiseAPIManager("/Users/chlr/dev_root/intellij/Jobswise/OpswiseAPI/config","na_opswise.json");
//       TriggerHandler trigger = opswise.getTriggerHandler();
//       JsonX request = new JsonX();
//       request.setString("ops_trigger_cron.task","temp_job_name10");
//       request.setString("ops_trigger_cron.calendar","System Default");
//       request.setString("ops_trigger_cron.name","tgr_temp_job_name10");
//       request.setString("ops_trigger_cron.month","*");
//       request.setString("ops_trigger_cron.minutes","5/*");
//       request.setString("ops_trigger_cron.day_of_week","*");
//       request.setString("ops_trigger_cron.day_of_month","*");
//       trigger.createCron(request);

//        OpswiseAPIManager opswise = new OpswiseAPIManager("/Users/chlr/dev_root/intellij/Jobswise/OpswiseAPI/config","na_opswise.json");
//        TriggerHandler trigger = opswise.getTriggerHandler();
//        JsonX request = new JsonX();
//        request.setString("ops_trigger_cron.action","disable_trigger");
//        request.setString("ops_trigger_cron.name","temp_trigger_1");
//        trigger.switchTrigger(request);



//        OpswiseAPIManager opswise = new OpswiseAPIManager("/Users/chlr/dev_root/intellij/Jobswise/OpswiseAPI/config","na_opswise.json");
//        TaskHandler task = opswise.getTaskHandler();
//        JsonX request = new JsonX();
//        request.setString("ops_task_unix.name","temp_job_name10");
//        request.setString("ops_task_unix.command","echo All Hail Megatron");
//        request.setString("ops_task_unix.agent","pit-dev-owagent1 - AGNT000712");
//        task.create(request);


//          OpswiseAPIManager opswise = new OpswiseAPIManager("/Users/chlr/dev_root/intellij/Jobswise/OpswiseAPI/config","na_opswise.json");
//          TaskHandler task = opswise.getTaskHandler();
//          JsonX request = new JsonX();
//          request.setString("ops_task_unix.name","##temp_job_name");
//          task.delete(request);


//          OpswiseAPIManager opswise = new OpswiseAPIManager("/Users/chlr/dev_root/intellij/Jobswise/OpswiseAPI/config","na_opswise.json");
//          TriggerHandler trigger = opswise.getTriggerHandler();
//          JsonX request = new JsonX();
//          request.setString("ops_trigger_cron.name","temp_trigger");
//          trigger.delete(request);



    }


}
