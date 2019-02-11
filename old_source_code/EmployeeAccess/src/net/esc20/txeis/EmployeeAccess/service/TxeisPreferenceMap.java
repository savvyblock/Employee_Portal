package net.esc20.txeis.EmployeeAccess.service;

import java.util.HashMap;

import net.esc20.txeis.EmployeeAccess.dao.api.ITxeisPreferenceDAO;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ITxeisPreferenceMap;
import net.esc20.txeis.EmployeeAccess.domainobject.report.LdapPreferences;
import net.esc20.txeis.EmployeeAccess.domainobject.report.PasswordPreferences;
import net.esc20.txeis.EmployeeAccess.domainobject.report.SessionTimeoutPreferences;


public class TxeisPreferenceMap implements ITxeisPreferenceMap {

	HashMap<String,String>	preferences;
	ITxeisPreferenceDAO 	preferencesDAO;
	private static final String minPasswordLength = "8";
	
	public HashMap<String, String> getPreferences() {
		if (preferences == null) {
			preferences = preferencesDAO.getPreferences();
		}
		return preferences;
	}

	public String getLdapUrlPreferences() {
		return getPreferences().get("ldap_url");
	}
	
	public String getLdapBasePreferences(){
		return getPreferences().get("ldap_base");
	}
	
	public String  getLdapUsrDnPreferences(){
		return getPreferences().get("ldap_userDn");
	}
	
	public String  getLdapPwdPreferences(){
		return PasswordEncryption.failBlankDecrypt(getPreferences().get("ldap_password"));
	}
	
	public String getLdapPooledPreferences(){
		return getPreferences().get("ldap_pooled");
	}
	
	public String getLdapSearchBasePreferences(){
		return getPreferences().get("ldap_searchBase");
	}
	
	public String getLdapUserAttributePreferences(){
		return getPreferences().get("ldap_userAttribute");
	}
	
	public String getLdapObjectCategoryPreferences(){
		return getPreferences().get("ldap_objectCategory");
	}

	public String getReportPrinter() {
		return getPreferences().get("report_printer");
	}

	public boolean getLdapIgnorePartialResultExceptionPreferences(){
		return !"no".equalsIgnoreCase(getPreferences().get("ldap_ignorePartialResultException"));
	}
	
	public void setPreferencesDAO(ITxeisPreferenceDAO preferencesDAO) {
		this.preferencesDAO = preferencesDAO;
	}
	
	public void setPreferences(HashMap<String, String> preferences) {
		this.preferences = preferences;
	}
	
	public String getPwdLength(){
		if(getPreferences().get("pwd_length").length()==0){
			return minPasswordLength; 
		}
		else{
			return getPreferences().get("pwd_length");
		}
	}
	
	public PasswordPreferences getPasswordPreferences(){
		
		return new PasswordPreferences(Boolean.parseBoolean(getPreferences().get("pwd_number")), 
										Boolean.parseBoolean(getPreferences().get("pwd_spec_char")),
										Boolean.parseBoolean(getPreferences().get("pwd_case")),
										Integer.parseInt(getPwdLength()));
	}	
	
	public LdapPreferences getLdapPreferences() {
		return new LdapPreferences(getLdapUsrDnPreferences(), getLdapPwdPreferences(), 
				Boolean.parseBoolean(getLdapPooledPreferences()), getLdapUrlPreferences(),
				getLdapBasePreferences(), getLdapSearchBasePreferences(), 
				getLdapUserAttributePreferences(), getLdapIgnorePartialResultExceptionPreferences(),
				getLdapObjectCategoryPreferences());
	}
	
	public SessionTimeoutPreferences getSessionTimeoutPreferences() {
		return SessionTimeoutPreferences.create(getPreferences().get("session_timeout"));
	}
	
	public int getLockoutTries() {
		String retvalue = getPreferences().get("lock_tries");
		if (retvalue.equalsIgnoreCase("")) {
			return 0;
		} else {
			return Integer.parseInt(retvalue);
		}
	}
	
	public int getLockoutTimeout() {
		String retvalue = getPreferences().get("lock_timeout");
		if (retvalue.equalsIgnoreCase("")) {
			return 0;
		} else {
			return Integer.parseInt(retvalue);
		}
	}
	
	public int getReportTimeout() {
		String retvalue = getPreferences().get("report_timeout");
		if (retvalue.equalsIgnoreCase("")) {
			return 0;
		} else {
			return Integer.parseInt(retvalue);
		}
	}
	
	public String getEmailSenderAddrPreferences() {
		String retvalue = getPreferences().get("email_sender_addr");
		if (retvalue.equalsIgnoreCase("")) {
			return "";
		} else {
			return retvalue;
		}
	}
	
	public String getEmailPasswordPreferences() {
		return PasswordEncryption.failBlankDecrypt(getPreferences().get("email_password"));
	}
	
	public String getEmailUserIdPreferences() {
		String retvalue = getPreferences().get("email_userid");
		if (retvalue.equalsIgnoreCase("")) {
			return "";
		} else {
			return retvalue;
		}
	}
	
	public String getEmailSmtpAddrPreferences() {
		String retvalue = getPreferences().get("email_smtp_addr");
		if (retvalue.equalsIgnoreCase("")) {
			return "";
		} else {
			return retvalue;
		}
	}
	public String getEmailSmtpPortPreferences() {
		String retvalue = getPreferences().get("email_smtp_port");
		if (retvalue == null || retvalue.trim().equalsIgnoreCase("")) {
			return "25";
		} else {
			return retvalue;
		}
	}
	
	public String getEmailProtocol() {
		return (null == getPreferences().get("email_protocol"))
			? "smtp"
			: getPreferences().get("email_protocol");
	}
	
	public int getPasswordExpire() {
		String retvalue = getPreferences().get("pwd_expire");
		if (retvalue.equalsIgnoreCase("")) {
			return 0;
		} else {
			return Integer.parseInt(retvalue);
		}
	}
}
