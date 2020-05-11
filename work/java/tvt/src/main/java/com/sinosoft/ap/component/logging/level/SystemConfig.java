package com.sinosoft.ap.component.logging.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class SystemConfig {
	
  public static String  getSystemConfig(){
		Properties properties = new Properties();
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			properties.load(bf);
			String logLevelCode = properties.getProperty("system.name");
			return logLevelCode;
		} catch (IOException e) {
			System.err.println("系统配置文件不存在 classPath:application.properties");
			return "";
		}
	}
}
