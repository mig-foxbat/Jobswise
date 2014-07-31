package org.foxbat.opswise;

import java.util.Map;

public class OpswiseAPIManager {
	Map<String, Object> config;

	public OpswiseAPIManager(){
	}
	
	public TaskHandler getTaskHandler()
	{
		return new TaskHandler();
	}
	
	public TriggerHandler getTriggerHandler()
	{
		return new TriggerHandler();
	}
	
}
