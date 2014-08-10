package org.foxbat.opswise.core;

import org.json.JSONObject;

public class TaskHandler extends OpswiseObjectHandler {
	private static final String OBJECT = "task";

	public JSONObject create(JSONObject json) {
		return this.makeRequest(json, OBJECT, "create");
	}

	public JSONObject launch(JSONObject json) {

		return this.makeRequest(json, OBJECT, "launch");
	}

	public JSONObject queryTaskList(JSONObject json) {
		return this.makeRequest(json, OBJECT, "launch");
	}
}
