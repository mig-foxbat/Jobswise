package org.foxbat.opswise.core;

import java.util.Map;
import org.foxbat.opswise.ducttape.OpsTriggerCreator;
import org.foxbat.opswise.util.JsonX;
import org.json.JSONObject;

public class TriggerHandler extends OpswiseObjectHandler {
	private static final String OBJECT = "trigger";

	public void create(JSONObject json) {
		OpsTriggerCreator ops_trigger = new OpsTriggerCreator(new JsonX(json));
		ops_trigger.createXMLFile();
		ops_trigger.makeWebRequest();
	}

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
