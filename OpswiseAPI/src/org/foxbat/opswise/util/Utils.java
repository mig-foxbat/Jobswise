package org.foxbat.opswise.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class Utils {

	
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
			StringBuilder content = new StringBuilder();
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
