package org.foxbat.opswise;

import java.util.Map;

import org.json.JSONObject;

public class TriggerManager {
	String template_path;

	public TriggerManager() {
		this.template_path = AppConfig.getInstance().config.getJSONObject(
				"general").getString("template_path")
				+ "trigger/";
	}

	public JSONObject launch(JSONObject json) {
		JsonX config = new JsonX(json);
		String xmlcontent = XMLRequestGenerator.generateRequestXML(config,
				this.template_path + "launch.xml");
		RestAPIManager rest = new RestAPIManager();
		JsonX output = rest.doPost(
				AppConfig.getInstance().config.getJSONObject("url")
						.getJSONObject("trigger").getString("launch"), xmlcontent);
		return output.getJson();
	}

	public Map<String, Object> createTempTrigger() {
		throw new UnsupportedOperationException();
		// return null;
	}

	public Map<String, Object> switchTrigger() {
		return null;
	}

	public Map<String, Object> queryTriggers() {
		return null;
	}
}
