package org.foxbat.opswise;

import java.util.Map;

import org.json.JSONObject;

public class TriggerHandler extends OpswiseObjectHandler {
	private static final String OBJECT = "trigger";


	public JSONObject launch(JSONObject json) {
		return this.makeRequest(json, OBJECT, "launch");
	}

	public JSONObject createTempTrigger(JSONObject json) {
		return this.makeRequest(json, OBJECT, "create_temp");
	}

	public JSONObject switchTrigger(JSONObject json) {		
		return this.makeRequest(json, OBJECT, "switch");
	}

	public Map<String, Object> queryTriggers() {
		return null;
	}
	
}
