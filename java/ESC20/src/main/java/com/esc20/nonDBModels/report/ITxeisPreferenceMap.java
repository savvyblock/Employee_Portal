
package com.esc20.nonDBModels.report;

import java.util.HashMap;

public interface ITxeisPreferenceMap {
	String getLdapUrlPreferences();
	String getLdapBasePreferences();
	String getLdapUsrDnPreferences();
	String getLdapPwdPreferences();
	String getLdapPooledPreferences();
	String getLdapObjectCategoryPreferences();
	SessionTimeoutPreferences getSessionTimeoutPreferences();
	HashMap<String,String> getPreferences();
	PasswordPreferences getPasswordPreferences();
	LdapPreferences getLdapPreferences();
	String getLdapSearchBasePreferences();
	String getLdapUserAttributePreferences();
	int getLockoutTries();
	int getLockoutTimeout();
	int getPasswordExpire();
	String getReportPrinter();
	int getReportTimeout();
	String getEmailSenderAddrPreferences();
	String getEmailPasswordPreferences();
	String getEmailUserIdPreferences();
	String getEmailSmtpAddrPreferences();
	String getEmailSmtpPortPreferences();
	String getEmailProtocol();
}
