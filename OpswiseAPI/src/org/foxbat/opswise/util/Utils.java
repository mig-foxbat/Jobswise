package org.foxbat.opswise.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

	public static JsonX getJSONConfig(String filepath) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			StringBuilder content = new StringBuilder();
            String line;
			while ((line = br.readLine()) != null) {
				content.append(line + "\n");
			}
			JSONObject json = new JSONObject(content.toString());
			return new JsonX(json);

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


    public static Map<String,String> getMapFromJson(JsonX json) {

        Iterator<String> keys = json.keys();
        Map<String,String> result = new HashMap<>();
        while(keys.hasNext()) {
            String key = keys.next();
            result.put(key,json.getString(key));
        }
        return result;
    }

    public static JsonX mergeMaps(JsonX main, JsonX reference) {
        Iterator<String> keys = reference.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            main.setString(key,reference.getString(key));
        }
        return main;
    }


    public static String getMd5Hash(String triggername) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(triggername.getBytes("UTF-8"));
            BigInteger bigInt = new BigInteger(1, bytes);
            String hashtext = bigInt.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }




}
