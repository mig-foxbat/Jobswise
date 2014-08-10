package org.foxbat.opswise.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.foxbat.opswise.core.OpswiseAPIManager;
import org.json.JSONObject;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class XMLRequestGenerator {

	private static Configuration cfg;

	static {
		cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	public static String generateRequestXML(JsonX json, String template_path) {

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String config = json.toString();
			map.put("config", config);
			Template template = cfg.getTemplate(template_path);
			Writer writer = new StringWriter();
			template.process(map, writer);
			return writer.toString();
		} catch (TemplateException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String args[]) {
		try 
		{
		OpswiseAPIManager opswise = new OpswiseAPIManager();
		JSONObject json = new JSONObject();
		json.put("name", "tgr_autobot_job1");	
		json.put("task_name", "autobot_job1");
		json.put("user_name", "chlr");
		json.put("task_id", "");
		json.put("sys_id", "");
		JSONObject cron = new JSONObject();
		cron.put("minutes", "34");
		cron.put("hours", "*");
		cron.put("day_of_week", "*");
		cron.put("day_of_month", "*");
		cron.put("month", "*");
		json.put("cron", cron);
		opswise.getTriggerHandler().create(json);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
