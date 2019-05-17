package com.esc20.nonDBModels;


public class DatabaseContextHolder  {


	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	
	public static void setCountyDistrict(String countyDistrict)
	{
		if(countyDistrict!=null)
		{
			contextHolder.set(countyDistrict);
		}
	}
	
	public static String getCountyDistrict() {
		return (String)contextHolder.get();
	}
	
	public static String getDatabase()
	{
		if (getCountyDistrict() == null) {
			return null;
		}
		
		return "DB" + getCountyDistrict();
	}
	
	public static void clearDatabase()
	{
		contextHolder.remove();
	}
	
}
