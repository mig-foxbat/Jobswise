package org.foxbat.opswise;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonX {

	/**
	 * 
	 */
	private JSONObject json;

	public JsonX(JSONObject json) {
		this.json = json;
	}

	public String getString(String key) {
		try {
			return (String) json.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public JsonX getJSONObject(String key) {
		try {
			return new JsonX(json.getJSONObject(key));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String toString() {
		try {
			return this.json.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public JSONObject getJson() {
		return this.json;
	}

}
