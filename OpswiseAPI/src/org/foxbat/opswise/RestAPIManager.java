package org.foxbat.opswise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.XML;

public class RestAPIManager {
	HttpClient client;

	public RestAPIManager() {
		this.client = getOpswiseHttpClient();
	}

	private HttpClient getOpswiseHttpClient() {
		CredentialsProvider provider = new BasicCredentialsProvider();
		JsonX config =  AppConfig.getInstance().config.getJSONObject("server");
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				config.getString("username"),
				config.getString("password"));
		provider.setCredentials(AuthScope.ANY, credentials);
		return HttpClientBuilder.create()
.setDefaultCredentialsProvider(provider).build();
	}

	public JsonX doPost(String url, String xmlcontent) {
		JsonX config =  AppConfig.getInstance().config.getJSONObject("server");
		System.out.println("http://"+config.getString("host")+":"+config.getString("port")+url);
		HttpPost post = new HttpPost("http://"+config.getString("host")+":"+config.getString("port")+url);
		post.setHeader("Content-Type", "application/xml");
		ByteArrayEntity bae = new ByteArrayEntity(xmlcontent.getBytes(), 0,
				xmlcontent.getBytes().length);
		post.setEntity(bae);
		try {
			HttpResponse response = client.execute(post);
			return new JsonX(XML.toJSONObject((getResponseString(response.getEntity()))));
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String getResponseString(HttpEntity entity) 
	{
		try
		{
		InputStream is = entity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuffer content = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null)
		{
			content.append(line);
			System.out.println(line);
		}
		br.close();
		return content.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}		
	}
}
