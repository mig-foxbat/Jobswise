package org.foxbat.opswise.util;

import org.apache.http.Header;

public class RestResponse 
{
	public final String payload;
	public final Header[] headers;
	
	public RestResponse(String payload, Header[] headers)
	{
		this.payload = payload;
		this.headers = headers;
	}
	
}
