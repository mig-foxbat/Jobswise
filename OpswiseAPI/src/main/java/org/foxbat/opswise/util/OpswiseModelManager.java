package org.foxbat.opswise.util;

/**
 * Created by chlr on 9/26/14.
 */
public class OpswiseModelManager {

    private DBConnectionManager dbc;

    private static final String SYSID_FOR_TASK = "SELECT sys_id FROM ops_task where name = '%s'; ";
    private static final String SYSID_FOR_AGENT = "SELECT sys_id FROM ops_agent where name = '%s'; ";
    private static final String SYSID_FOR_CALENDAR = "SELECT sys_id FROM ops_calendar where name = '%s'; ";
    private static final String SYSID_FOR_TRIGGER = "SELECT sys_id FROM ops_trigger where name = '%s'; ";
    private static final String IS_TASK_ENABLED = "SELECT name FROM ops_trigger WHERE name = '%s' and enabled = 1";
    private static final String SYSID_FOR_EMAILCONN = "SELECT sys_id FROM ops_email_connection WHERE name = '%s'";
    private static final String SYSID_FOR_NOTIFICATION = "SELECT sys_id FROM ops_notification WHERE task_id = '%s'";

    public OpswiseModelManager(DBConnectionManager dbc) {
        this.dbc = dbc;
    }

    public boolean doesTaskExists(String task_name) {
        return dbc.executeQuery(String.format(SYSID_FOR_TASK,task_name)) != null;
    }

    public boolean doesTriggerExists(String trigger_name) {
        return dbc.executeQuery(String.format(SYSID_FOR_TRIGGER,trigger_name)) != null;
    }

    public boolean isTriggerEnabled(String trigger_name) {
        return dbc.executeQuery(String.format(IS_TASK_ENABLED,trigger_name)) != null;
    }


    public String getTriggerSysID(String trigger_name) {
        return dbc.executeQuery(String.format(SYSID_FOR_TRIGGER,trigger_name));
    }

    public String getEmailSysID(String email_connection) {
        return dbc.executeQuery(String.format(SYSID_FOR_EMAILCONN,email_connection));
    }

    public String getAgentSysID(String agentname) {
        return dbc.executeQuery(String.format(SYSID_FOR_AGENT,agentname));
    }

    public String getNotificationID(String taskname) {
        return dbc.executeQuery(String.format(SYSID_FOR_NOTIFICATION,getTaskSysID(taskname)));
    }

    public String getTaskSysID(String name) {
        return dbc.executeQuery(String.format(SYSID_FOR_TASK,name));
    }

    public String getCalendarSysID(String name) {
        return dbc.executeQuery(String.format(SYSID_FOR_CALENDAR,name));
    }



}
