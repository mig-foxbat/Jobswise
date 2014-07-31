package org.foxbat.opswise;

import org.json.JSONObject;

public abstract class OpswiseObjectHandler 
{
	
	
	protected JSONObject makeRequest(JSONObject json,String object,String action)
	{
		JsonX config = new JsonX(json);
		String xmlcontent = XMLRequestGenerator.generateRequestXML(config,
				AppConfig.getInstance().config.getJSONObject("general").getString("template_path")+object+"/" + action+".xml");
		RestAPIManager rest = new RestAPIManager();
		JsonX output = rest.doPost(
				AppConfig.getInstance().config.getJSONObject("url")
						.getJSONObject(object).getString(action), xmlcontent);
		return output.getJson();
	}
}
