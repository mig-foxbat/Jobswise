package org.foxbat.opswise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {

	// public static JsonX<String, Object> jsontoMapConverter(JSONObject json) {
	// @SuppressWarnings("unchecked")
	// Iterator<String> keys = json.keys();
	// JsonX<String, Object> result = new JsonX<String, Object>();
	// while (keys.hasNext()) {
	// try {
	// String key = keys.next();
	// Object value = json.get(key);
	// if (value instanceof JSONObject)
	// result.put(key, jsontoMapConverter((JSONObject) value));
	// else
	// result.put(key, value);
	//
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// return result;
	// }

	@SuppressWarnings("unchecked")
	public static Map<String, Object> mergeMaps(Map<String, Object> base,
			Map<String, Object> reference) {
		Set<Map.Entry<String, Object>> ref_set = reference.entrySet();

		for (Map.Entry<String, Object> refitem : ref_set) {
			if (base.containsKey(refitem.getKey())) {
				if (refitem.getValue() instanceof Map) {
					mergeMaps((Map<String, Object>) base.get(refitem.getKey()),
							(Map<String, Object>) refitem.getValue());
				} else {
					base.put(refitem.getKey(), refitem.getValue());
				}
			} else {
				base.put(refitem.getKey(), refitem.getValue());
			}
		}
		return base;
	}

	public static JSONObject getJSONConfig(String filepath) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			StringBuffer content = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				content.append(line + "\n");
			}
			JSONObject json = new JSONObject(content.toString());
			return json;

		} catch (JSONException | IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				br.close();
			} catch (Exception e) {
			}
		}
	}

}
