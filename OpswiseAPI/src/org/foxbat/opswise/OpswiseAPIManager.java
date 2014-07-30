package org.foxbat.opswise;

import java.util.Map;

public class OpswiseAPIManager {
	Map<String, Object> config;

	public OpswiseAPIManager(){
	}
	
	public TaskManager getTaskManager()
	{
		return new TaskManager();
	}
	
	public TriggerManager getTriggerManager()
	{
		return new TriggerManager();
	}
	
}
