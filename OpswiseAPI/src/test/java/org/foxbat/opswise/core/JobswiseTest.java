package org.foxbat.opswise.core;

import junit.runner.Version;
import org.foxbat.opswise.util.DBConnectionManager;
import org.foxbat.opswise.util.JsonX;
import org.foxbat.opswise.util.OpswiseModelManager;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;



public class JobswiseTest {

    static OpswiseAPIManager opswise;
    static DBConnectionManager dbc;
    static OpswiseModelManager modelManager;

    private static final String TASKNAME = "temp_job_name_30";
    private static final String TRIGGERNAME = "tgr_temp_job_name_30";

    @BeforeClass
    public static void setUp() throws Exception {
         System.out.println(Version.id());
         opswise = new OpswiseAPIManager("/Users/chlr/dev_root/intellij/Jobswise/OpswiseAPI/config","na_opswise.json");
         dbc = new DBConnectionManager(opswise.ops_config);
         modelManager = new OpswiseModelManager(dbc);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        dbc.close();
    }


    @Test(timeout = 10000)
    public void test_2_CreateTrigger() throws Exception {
        TriggerHandler trigger = opswise.getTriggerHandler();
        JsonX request = new JsonX();
        request.setString("ops_trigger_cron.task","autobot_job2");
        request.setString("ops_trigger_cron.calendar","System Default");
        request.setString("ops_trigger_cron.name",TRIGGERNAME);
        request.setString("ops_trigger_cron.month","*");
        request.setString("ops_trigger_cron.minutes","5/*");
        request.setString("ops_trigger_cron.day_of_week","*");
        request.setString("ops_trigger_cron.day_of_month","*");
        trigger.createCron(request);
        assertEquals(modelManager.doesTriggerExists(request.getString("ops_trigger_cron.name")), true);
    }

    @Test(timeout = 10000)
    public void test_1_CreateTask() throws Exception {
        OpswiseAPIManager opswise = new OpswiseAPIManager("/Users/chlr/dev_root/intellij/Jobswise/OpswiseAPI/config","na_opswise.json");
        JsonX json = new JsonX();
        json.setString("name", TASKNAME);
        json.setString("command", "echo All Hail Megatron");
        json.setString("agent", "pit-dev-owagent1 - AGNT0007");
        JsonX email = new JsonX();
        email.setString("address", "mig.flanker@gmail.com");
        email.setString("connection", "Gmail - dw_etl");
        json.setJsonX("email", email);
        opswise.getTaskHandler().create(json);
        assertEquals(modelManager.doesTaskExists(json.getString("name")), true);
    }

    @Test(timeout = 10000)
    public void test_1_LaunchTask() throws Exception {
        OpswiseAPIManager opswise = new OpswiseAPIManager("/Users/chlr/dev_root/intellij/Jobswise/OpswiseAPI/config","na_opswise.json");
        JSONObject json = new JSONObject();
        json.put("name","###temp_job");
        opswise.getTaskHandler().launch(json);
    }


}