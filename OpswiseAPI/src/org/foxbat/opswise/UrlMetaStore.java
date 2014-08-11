package org.foxbat.opswise;

/**
 * Created by chlr on 8/10/14.
 */


public class UrlMetaStore
{
    public static class General
    {
        public final static String login = "/opswise/login.do";
    }

    public static class Task {
        public final static String create = "/opswise/resources/task/ops-task-create";
        public final static String launch = "/opswise/resources/task/ops-task-launch";
    }

    public static class Trigger {
        public final static String create = "/opswise/opswise_import_execute.do";
        public final static String create_temp = "/opswise/resources/trigger/ops-create-temp-trigger";
        public final static String launch = "/opswise/resources/trigger/ops-trigger-now";
        public final static String toggle = "/opswise/resources/trigger/ops-enable-disable-triggers";
    }

    public static class Agents {
        public final static String list = "/opswise/resources/agents/list";
    }

    public static class Variables {
        public final static String set = "/opswise/resources/variable/ops-variable-set";

    }

    public static class VirtualResouce {
        public final static String limit = "/opswise/resources/virtual/ops-update-resource-limit";
    }


}
