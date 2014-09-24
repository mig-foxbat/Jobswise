package org.foxbat.opswise.ducttape;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.foxbat.opswise.AppConfig;
import org.foxbat.opswise.util.JsonX;
import org.foxbat.opswise.util.RemoteShellManager;
import org.foxbat.opswise.util.RestAPIManager;
import org.foxbat.opswise.util.XMLRequestGenerator;

public class OpsMockUserInterface {
	private JsonX request_config,ops_config;

	public OpsMockUserInterface(JsonX ops_config, JsonX request_config) {
		this.request_config = request_config;
        this.ops_config = ops_config;
	}

	public void makeWebRequest() {

        JsonX json = ops_config.getJSONObject("server");
		RestAPIManager rest = new RestAPIManager(ops_config);
		Map<String, String> form = new HashMap<>();

        // Login to opswise

        form.put("user_name", ops_config.getJSONObject("server").getString("username"));
		form.put("user_password", ops_config.getJSONObject("server").getString("password"));
		form.put("sys_action", "sysverb_login");
		rest.postForm(AppConfig.getInstance().config.getJSONObject("url")
				.getJSONObject("general").getString("login"), form);

        // Import Trigger xml file


        form.clear();
		form.put("user_name", json.getString("username"));
		form.put("sysparm_referring_url", "ops_trigger_cron_list.do");
		form.put("sysparm_target", "ops_trigger_cron");
		form.put("sysparm_import_dir", ops_config.getJSONObject("server").getJSONObject("ssh").getString("dir") + "/"
                + request_config.getString("name"));
		rest.postForm(AppConfig.getInstance().config.getJSONObject("url")
				.getJSONObject("trigger").getString("create"), form);

        // enable Trigger

        form.clear();
        form.put("sys_action","enable_trigger");
        form.put("sys_target","ops_trigger_cron");
        form.put("sys_uniqueName","sys_id");
        form.put("sys_uniqueValue",getMd5Hash(request_config.getString("name")));

        rest.postForm(AppConfig.getInstance().config.getJSONObject("url")
                .getJSONObject("trigger").getString("portal_enable"), form);

	}




	public void createXMLFile() {
		DBConnectionManager dbc = new DBConnectionManager(this.ops_config);
		String sys_id = dbc.querySysID(request_config.getString("task_name"));
		dbc.close();
        request_config.setString("task_id", sys_id);
        request_config.setString("sys_id", getMd5Hash(request_config.getString("name")));
		String xmlcontent = XMLRequestGenerator.generateRequestXML(request_config,
				AppConfig.getPath()
						+ "/opswise/trigger/create.xml");
		JsonX ssh_config = ops_config.getJSONObject(
				"server").getJSONObject("ssh");
		RemoteShellManager shell = new RemoteShellManager(ops_config);
		String target = ssh_config.getString("dir") + "/"
				+ request_config.getString("name");
		shell.executeCommand("mkdir", target);
		shell.executeSFTP(xmlcontent, target + "/cron_trigger.xml");
		shell.close();
	}



	private String getMd5Hash(String triggername) {
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
