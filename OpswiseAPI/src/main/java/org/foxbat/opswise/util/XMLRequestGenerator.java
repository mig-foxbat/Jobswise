package org.foxbat.opswise.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.foxbat.opswise.AppConfig;
import freemarker.template.TemplateExceptionHandler;

public class XMLRequestGenerator {

	private static Configuration cfg;

	static {
		cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        try { cfg.setDirectoryForTemplateLoading(new File(AppConfig.getPath())); } catch (IOException e) { e.printStackTrace();}
	}

	public static String generateRequestXML(JsonX json, String template_path) {

		try {
			Map<String, Object> map = new HashMap<>();
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
}
