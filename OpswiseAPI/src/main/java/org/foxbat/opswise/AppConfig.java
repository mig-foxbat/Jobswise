package org.foxbat.opswise;

import org.foxbat.opswise.util.JsonX;
import org.foxbat.opswise.util.Utils;


public class AppConfig {
	private static AppConfig singleton;
	public JsonX config;
	private static String PATH = "config/app.json";

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

    public static void setConfigDir(String path){
        PATH = path;
    }

    public static String getPath(){
        return PATH;
    }


	private void loadConfig() {
        this.config = Utils.getJSONConfig(AppConfig.PATH+"/app.json");
	}

}
