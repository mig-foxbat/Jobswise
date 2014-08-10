package org.foxbat.opswise.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class Utils {


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
	
	public static JSONObject XMLtoJsonConverter(String xml)
	{
		try
		{
		return XML.toJSONObject(xml);
		}
		catch(JSONException e)
		{
			e.printStackTrace();
			return null;
			
		}
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
