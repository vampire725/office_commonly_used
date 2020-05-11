package com.sinosoft.ap.component.logging.level;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DefaultLogLevel {
	
  private static DefaultLogLevel defaultLogLevel;

  private LogLevel logLevel;

  private DefaultLogLevel() {
    logLevel = getSysConfigLogLevel();
  }

  private static DefaultLogLevel getInstance() {
    if ( defaultLogLevel == null ) {
      defaultLogLevel = new DefaultLogLevel();
    }
    return defaultLogLevel;
  }

  public static LogLevel getLogLevel() {
    DefaultLogLevel instance = getInstance();
    return instance.logLevel;
  }

  public static void setLogLevel( LogLevel logLevel ) {
    DefaultLogLevel instance = getInstance();
    instance.logLevel = logLevel;
  }

  private LogLevel  getSysConfigLogLevel(){
		Properties properties = new Properties();
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
		try {
			properties.load(input);
		} catch (IOException e) {
			System.err.println("系统配置文件不存在 classPath:config.properties");
			return LogLevel.DEBUG;
		}
		String logLevelCode = properties.getProperty("logging.level");
		if(LogLevel.NOTHING.getCode().equalsIgnoreCase(logLevelCode)){
			return LogLevel.NOTHING;
		}else if(LogLevel.ERROR.getCode().equalsIgnoreCase(logLevelCode)){
			return LogLevel.ERROR;
		}else if(LogLevel.MINIMAL.getCode().equalsIgnoreCase(logLevelCode)){
			return LogLevel.MINIMAL;
		}else if(LogLevel.BASIC.getCode().equalsIgnoreCase(logLevelCode)){
			return LogLevel.BASIC;
		}else if(LogLevel.DETAILED.getCode().equalsIgnoreCase(logLevelCode)){
			return LogLevel.DETAILED;
		}else if(LogLevel.DEBUG.getCode().equalsIgnoreCase(logLevelCode)){
			return LogLevel.DEBUG;
		}else if(LogLevel.ROWLEVEL.getCode().equalsIgnoreCase(logLevelCode)){
			return LogLevel.ROWLEVEL;
		}else{
			return LogLevel.DEBUG;
		}
	}
}
