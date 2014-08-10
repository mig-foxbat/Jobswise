package org.foxbat.opswise.core;

import java.util.HashMap;
import java.util.Map;

import org.foxbat.opswise.AppConfig;
import org.foxbat.opswise.util.JsonX;
import org.foxbat.opswise.util.RestAPIManager;
import org.foxbat.opswise.util.RestResponse;
import org.foxbat.opswise.util.Utils;
import org.foxbat.opswise.util.XMLRequestGenerator;
import org.json.JSONObject;

public abstract class OpswiseObjectHandler 
{
	protected JSONObject makeRequest(JSONObject json,String object,String action)
	{
		JsonX config = new JsonX(json);
		String xmlcontent = XMLRequestGenerator.generateRequestXML(config,
				AppConfig.getInstance().config.getJSONObject("general").getString("config_path")+"config/opswise/"+object+"/" + action+".xml");
		RestAPIManager rest = new RestAPIManager();
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Content-Type", "application/xml");
		RestResponse output = rest.postDocument(
				headers,AppConfig.getInstance().config.getJSONObject("url")
						.getJSONObject(object).getString(action), xmlcontent);
		return Utils.XMLtoJsonConverter(output.payload);
	}
}
