package org.foxbat.opswise.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class JsonX {

	/**
	 * 
	 */
	private JSONObject json;

	public JsonX(JSONObject json) {
		this.json = json;
	}


    public JsonX() {
        this.json = new JSONObject();
    }


	public String getString(String key) {
		try {
			return json.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

    public Iterator<String> keys() {
        return json.keys();
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
	
	public void setString(String key,String value)
	{
		try {
            this.json.remove(key);
			this.json.put(key, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setJsonX(String key,JsonX value)
	{
		try {
			this.json.put(key, value.getJson());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public boolean hasKey(String key) {
        return json.has(key);
    }
	

}
