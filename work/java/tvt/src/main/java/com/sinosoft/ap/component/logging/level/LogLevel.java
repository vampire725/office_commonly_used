package com.sinosoft.ap.component.logging.level;

public enum LogLevel {

	NOTHING(0, "Nothing"),   //无日志
	ERROR(1, "Error"), 	     //错误日志
	MINIMAL(2, "Minimal"),   //最小日志
	BASIC(3, "Basic"), 		 //基本日志
	DETAILED(4, "Detailed"), //详细日志
	DEBUG(5, "DEBUG"),       //调试日志
	ROWLEVEL(6, "Rowlevel"); //行级日志

	//日志描述
	public static final String[] logLevelDescriptions = {};

	private int level;
	private String code;

	private LogLevel(int level, String code) {
		this.level = level;
		this.code = code;
	}

	public int getLevel() {
		return level;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return logLevelDescriptions[level];
	}

	public static LogLevel getLogLevelForCode(String code) {
		for (LogLevel logLevel : values()) {
			if (logLevel.getCode().equals(code)) {
				return logLevel;
			}
		}
		return BASIC;
	}

	public boolean isVisible(LogLevel filterLogLevel) {
		return getLevel() <= filterLogLevel.getLevel();
	}

	public boolean isError() {
		return this == ERROR;
	}

	public boolean isNothing() {
		return this.level >= NOTHING.level;
	}

	public boolean isMinimal() {
		return this.level >= MINIMAL.level;
	}

	public boolean isBasic() {
		return this.level >= BASIC.level;
	}

	public boolean isDetailed() {
		return this.level >= DETAILED.level;
	}

	public boolean isDebug() {
		return this.level >= DEBUG.level;
	}

	public boolean isRowlevel() {
		return this.level >= ROWLEVEL.level;
	}

	public static String[] getLogLevelDescriptions() {
		return logLevelDescriptions;
	}

	public static String[] logLogLevelCodes() {
		String[] codes = new String[values().length];
		for (int i = 0; i < codes.length; i++) {
			codes[i] = values()[i].getCode();
		}
		return codes;
	}
}
