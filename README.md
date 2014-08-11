Jobswise
========

A Java API for the open source scheduler tool Opswise

API Usage:

For creating triggers:

		OpswiseAPIManager opswise = new OpswiseAPIManager("/home/foxbat/dev_root/Jobswise/OpswiseAPI/config","na_opswise.json");
		JSONObject json = new JSONObject();
		json.put("name", "tgr_autobot_job2");
		json.put("task_name", "autobot_job2");
		json.put("user_name", "chlr");
		JSONObject cron = new JSONObject();
		cron.put("minutes", "34");
		cron.put("hours", "*");
		cron.put("day_of_week", "*");
		cron.put("day_of_month", "*");
		cron.put("month", "*");
		json.put("cron", cron);
		opswise.getTriggerHandler().create(json);

For creating tasks:

     	  OpswiseAPIManager opswise = new OpswiseAPIManager("/home/foxbat/dev_root/Jobswise/OpswiseAPI/config","na_opswise.json");
        JSONObject json = new JSONObject();
        json.put("name", "autobot_job2");
        json.put("command", "echo All Hail Megatron");
        json.put("agent", "pit-dev-owagent1 - AGNT0007");
        JSONObject email = new JSONObject();
        email.put("address", "mig.flanker@gmail.com");
        email.put("connection", "Gmail - dw_etl");
        json.put("email", email);
        opswise.getTaskHandler().create(json);

