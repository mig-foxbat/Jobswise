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
	protected JsonX makeRequest(JsonX request_config,JsonX ops_config,String object,String action)
	{
		String xmlcontent = XMLRequestGenerator.generateRequestXML(request_config,
				"opswise/"+object+"/" + action+".xml");
		RestAPIManager rest = new RestAPIManager(ops_config);
		Map<String,String> headers = new HashMap<>();
		headers.put("Content-Type", "application/xml");
		RestResponse output = rest.postDocument(
				headers,AppConfig.getInstance().config.getJSONObject("url")
						.getJSONObject(object).getString(action), xmlcontent);
		return new JsonX(Utils.XMLtoJsonConverter(output.payload));
	}
}
