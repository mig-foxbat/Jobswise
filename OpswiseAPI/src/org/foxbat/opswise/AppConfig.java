package org.foxbat.opswise;

import org.json.JSONObject;

public class AppConfig {
	private static AppConfig singleton;
	public JsonX config;
	private static final String PATH = "config/app.json";

	private AppConfig() {
		this.loadConfig();
	}

	public static AppConfig getInstance() {
		if (AppConfig.singleton == null) {
			singleton = new AppConfig();
			return AppConfig.singleton;
		} else
			return AppConfig.singleton;
	}

	private void loadConfig() {
		JSONObject json = Utils.getJSONConfig(AppConfig.PATH);
		this.config = new JsonX(json);
	}

}
