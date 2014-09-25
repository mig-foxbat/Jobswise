package org.foxbat.opswise.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;


public class RestAPIManager {
	private HttpClient client;
    private final JsonX ops_config;

	public RestAPIManager(JsonX ops_config)
    {
        this.ops_config = ops_config;
		this.client = getOpswiseHttpClient();
	}

	private HttpClient getOpswiseHttpClient() {

		CredentialsProvider provider = new BasicCredentialsProvider();
		JsonX config =  ops_config.getJSONObject("server");
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				config.getString("username"),
				config.getString("password"));
		provider.setCredentials(AuthScope.ANY, credentials);
		return HttpClientBuilder.create()
.setDefaultCredentialsProvider(provider).build();

	}

	public RestResponse postDocument(Map<String, String> headers, String url,
			String document_content) {
		JsonX config = ops_config.getJSONObject("server");
		HttpPost post = new HttpPost("http://" + config.getString("host") + ":"
				+ config.getString("port") + url);
		for (String key : headers.keySet())
			post.addHeader(key, headers.get(key));
		ByteArrayEntity bae = new ByteArrayEntity(document_content.getBytes(),
				0, document_content.getBytes().length);
		post.setEntity(bae);
		try {
			HttpResponse response = client.execute(post);
			RestResponse result = new RestResponse(
					getResponseString(response.getEntity()),
					response.getAllHeaders());
			return result;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public RestResponse postForm(String url,
			JsonX formdata) {
        JsonX config = ops_config.getJSONObject("server");
        System.out.println("http://" + config.getString("host") + ":"
                + config.getString("port") + url);
		HttpPost post = new HttpPost("http://" + config.getString("host") + ":"
				+ config.getString("port") + url);
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        Iterator<String> keys = formdata.keys();
		while(keys.hasNext()) {
            String key = keys.next();
            urlParameters.add(new BasicNameValuePair(key, formdata.getString(key)));
        }
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
            System.out.println(post.getAllHeaders().toString());
			HttpResponse response = client.execute(post);
			RestResponse result = new RestResponse(
					getResponseString(response.getEntity()),
					response.getAllHeaders());
			return result;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}



	private String getResponseString(HttpEntity entity) {
		try {
			InputStream is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuffer content = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line);
                System.out.println(line);
			}
			br.close();
			return content.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
