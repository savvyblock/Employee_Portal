package com.esc20.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManifestUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ManifestUtil.class);
	
	private static String _VERSION_ = "";
	public static String getVersionInfo(ServletContext servletContext) {
		if(!StringUtil.isNullOrEmpty(_VERSION_))return _VERSION_;
		if(servletContext==null) return "";
		try {
			Properties prop = new Properties();
			prop.load(servletContext.getResourceAsStream("/META-INF/MANIFEST.MF"));
			String timeStamp = prop.getProperty("Timestamp");
			String release = prop.getProperty("Build_To_Be_Released");
			if((!StringUtil.isNullOrEmpty(release))&&release.trim().toLowerCase().equals("true") ) {
				 _VERSION_ = "";
				 return _VERSION_;
			}
			if(StringUtil.isNullOrEmpty(timeStamp))return "";
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			df.setLenient(false);
			Date dateUTC = df.parse(timeStamp);
			Date dateLocal = DateUtil.getLocalTime(dateUTC);
			_VERSION_ = DateUtil.getString(dateLocal,"MMddyyHHmm");
		}catch(Exception e) {
			logger.error("read version error",e);
		}
		return _VERSION_;
	}
}
