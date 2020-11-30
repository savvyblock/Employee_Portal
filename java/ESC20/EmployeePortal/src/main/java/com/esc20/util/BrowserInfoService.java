package com.esc20.util;

import org.springframework.stereotype.Service;

import com.esc20.util.StringUtil;

@Service
public final class BrowserInfoService {
	
	private String browserName = "";
	private String browserVersion = "";	
	
	public void createBrowserInfo(String userAgent) {
		
		String temp = "";
		String name = "";
		String version = "";
		
		if(userAgent.contains("Chrome")) { //checking if chrome
			temp = userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0];
			name = temp.split("/")[0];
			version = temp.split("/")[1];			
		}
		else if(userAgent.contains("Firefox")) { //checking if Firefox
			temp = userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0];
			name = temp.split("/")[0];
			version = temp.split("/")[1];
		}
		else if(userAgent.contains("MSIE")) { //checking if Internet Explorer
			temp = userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0];
			name = temp.split("/")[0];
			version = temp.split("/")[1];
		}
		else if(userAgent.contains("rv")) { //checking if Internet Explorer 11
			temp = userAgent.substring(userAgent.indexOf("rv")).split(" ")[0];
			name = temp.split(":")[0];
			version = temp.split(":")[1];
		}
		else if(userAgent.contains("Version") && userAgent.contains("Safari")) { //checking if Safari
			temp = userAgent.substring(userAgent.indexOf("Version")).split(" ")[0];
			name = temp.split("/")[0];
			version = temp.split("/")[1];
		} 
		
		//Abbreviate browser names
		if(name.equalsIgnoreCase("rv") || name.equalsIgnoreCase("MSIE")) {
			name =  "IE";
		} 
		else if(name.equalsIgnoreCase("Chrome")) {
			name = "GC";
		}
		else if(name.equalsIgnoreCase("FireFox")) {
			name = "FF";
		}
		else if(name.equalsIgnoreCase("Version")) {
			name = "SF";
		}
		
		//Trim the version up to one decimal point
		if(!StringUtil.isNullOrEmpty(version) && version.contains(".")) {			
			version = version.substring(0, version.indexOf('.')+2);			
		}

		this.browserName = name;
		this.browserVersion = version;		
	}

	public String getBrowserName() {
		return browserName;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

}
