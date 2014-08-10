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

public class OpsTriggerCreator {
	private JsonX config;

	public OpsTriggerCreator(JsonX config) {
		this.config = config;
	}

	public void makeWebRequest() {
		JsonX json = AppConfig.getInstance().config.getJSONObject("server");
		RestAPIManager rest = new RestAPIManager();
		Map<String, String> form = new HashMap<String, String>();
		form.put("user_name", json.getString("username"));
		form.put("user_password", json.getString("password"));
		form.put("sys_action", "sysverb_login");
		rest.postForm(AppConfig.getInstance().config.getJSONObject("url")
				.getJSONObject("general").getString("login"), form);
		form.clear();
		form.put("user_name", json.getString("username"));
		form.put("sysparm_referring_url", "ops_trigger_cron_list.do");
		form.put("sysparm_target", "ops_trigger_cron");
		form.put("sysparm_import_dir", AppConfig.getInstance().config.getJSONObject(
				"server").getJSONObject("ssh").getString("dir") + "/"
						+ config.getString("name"));
		rest.postForm(AppConfig.getInstance().config.getJSONObject("url")
				.getJSONObject("trigger").getString("create"), form);
	}

	public void createXMLFile() {
		DBConnectionManager dbc = new DBConnectionManager();
		String sys_id = dbc.querySysID(config.getString("task_name"));
		dbc.close();
		config.setString("task_id", sys_id == null ? "" : sys_id);
		config.setString("sys_id", getMd5Hash(config.getString("name")));
		String xmlcontent = XMLRequestGenerator.generateRequestXML(config,
				AppConfig.getInstance().config.getJSONObject("general")
						.getString("config_path")
						+ "config/opswise/trigger/create.xml");
		JsonX ssh_config = AppConfig.getInstance().config.getJSONObject(
				"server").getJSONObject("ssh");
		RemoteShellManager shell = new RemoteShellManager(
				ssh_config.getString("user"), ssh_config.getString("host"),
				Integer.parseInt(ssh_config.getString("port")));
		String target = ssh_config.getString("dir") + "/"
				+ config.getString("name");
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
