Jobswise
========

A Java API for the open source scheduler tool Opswise

API Usage:

For creating triggers:

	OpswiseAPIManager opswise = new OpswiseAPIManager("/home/user/dev_root/Jobswise/OpswiseAPI/config","na_opswise.json");
	TriggerHandler trigger = opswise.getTriggerHandler();
	JsonX request = new JsonX();
	request.setString("ops_trigger_cron.task","temp_job_name10");
	request.setString("ops_trigger_cron.calendar","System Default");
	request.setString("ops_trigger_cron.name","tgr_temp_job_name10");
	request.setString("ops_trigger_cron.month","*");
	request.setString("ops_trigger_cron.minutes","5/*");
	request.setString("ops_trigger_cron.day_of_week","*");
	request.setString("ops_trigger_cron.day_of_month","*");
	trigger.createCron(request);

For creating tasks:
	
	OpswiseAPIManager opswise = new OpswiseAPIManager("/home/user/dev_root/Jobswise/OpswiseAPI/config","na_opswise.json");
	TaskHandler task = opswise.getTaskHandler();
	JsonX request = new JsonX();
	request.setString("ops_task_unix.name","temp_job_name10");
	request.setString("ops_task_unix.command","echo All Hail Megatron");
	request.setString("ops_task_unix.agent","pit-dev-owagent1 - AGNT000712");
	task.create(request);

For enabling/disabling triggers:

	OpswiseAPIManager opswise = new OpswiseAPIManager("/home/user/dev_root/Jobswise/OpswiseAPI/config","na_opswise.json");
	TriggerHandler trigger = opswise.getTriggerHandler();
	JsonX request = new JsonX();
	request.setString("ops_trigger_cron.action","disable_trigger");
	request.setString("ops_trigger_cron.name","temp_trigger_1");
	trigger.switchTrigger(request);
	
For deleting Unix Tasks:
	
	OpswiseAPIManager opswise = new OpswiseAPIManager("/home/user/dev_root/Jobswise/OpswiseAPI/config","na_opswise.json");
	TaskHandler task = opswise.getTaskHandler();
	JsonX request = new JsonX();
	request.setString("ops_task_unix.name","##temp_job_name");
	task.delete(request);   
	
For deleting Cron Triggers:
	
	OpswiseAPIManager opswise = new OpswiseAPIManager("/Users/chlr/dev_root/intellij/Jobswise/OpswiseAPI/config","na_opswise.json");
	TriggerHandler trigger = opswise.getTriggerHandler();
	JsonX request = new JsonX();
	request.setString("ops_trigger_cron.name","temp_trigger");
	trigger.delete(request);

