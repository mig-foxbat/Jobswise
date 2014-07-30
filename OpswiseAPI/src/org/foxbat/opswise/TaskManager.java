package org.foxbat.opswise;

import org.json.JSONObject;

public class TaskManager {
	private final String template_path;

	public TaskManager() {
		this.template_path = AppConfig.getInstance().config.getJSONObject(
				"general").getString("template_path")+"task/";
	}

	public JSONObject create(JSONObject json) {

		// Sample API CALL
		// OpswiseAPIManager opswise = new OpswiseAPIManager();
		// JSONObject json = new JSONObject();
		// json.put("name", "autobot_job1");
		// json.put("command", "echo 'Hello World'");
		// json.put("agent", "pit-dev-script0801 - zr");
		// JSONObject email = new JSONObject();
		// email.put("address", "mig.flanker@gmail.com");
		// email.put("connection", "localhost");
		// json.put("email", email);
		// opswise.getTaskManager().create(json);

		JsonX config = new JsonX(json);
		String xmlcontent = XMLRequestGenerator.generateRequestXML(config,
				this.template_path + "create.xml");
		RestAPIManager rest = new RestAPIManager();
		JsonX output = rest.doPost(
				AppConfig.getInstance().config.getJSONObject("url")
						.getJSONObject("task").getString("create"), xmlcontent);
		return output.getJson();
	}

	public JSONObject launch(JSONObject json) {
		
//		OpswiseAPIManager opswise = new OpswiseAPIManager();
//		JSONObject json = new JSONObject();
//		json.put("name", "autobot_job1");
//		JSONObject vars = new JSONObject();
//		vars.put("address", "mig.flanker@gmail.com");
//		vars.put("connection", "localhost");
//		json.put("vars", vars);
//		opswise.getTaskManager().launch(json);

		JsonX config = new JsonX(json);
		String xmlcontent = XMLRequestGenerator.generateRequestXML(config,
				this.template_path + "launch.xml");
		RestAPIManager rest = new RestAPIManager();
		JsonX output = rest.doPost(
				AppConfig.getInstance().config.getJSONObject("url")
						.getJSONObject("task").getString("launch"), xmlcontent);
		return output.getJson();
	}

	public JsonX queryTaskList(String query) 
	{
		return null;
	}
}
