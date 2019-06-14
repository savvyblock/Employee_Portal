package com.esc20.util;

public class DataSourceContextHolder {
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	
	private static final ThreadLocal<Boolean> isTimeOut = new ThreadLocal<Boolean>();
	
	public static void setDataSourceType(String dataSourceType) {
		contextHolder.set(dataSourceType);
	}
	
	public static String getDataSourceType() {
		return (String) contextHolder.get();
	}
	
	public static void clearDataSourceType() {
		contextHolder.remove();
	}

	public static Boolean getIstimeout() {
		return (Boolean)isTimeOut.get();
	}
	
	public static void setIstimeout(Boolean timeout) {
		isTimeOut.set(timeout);
	}
}