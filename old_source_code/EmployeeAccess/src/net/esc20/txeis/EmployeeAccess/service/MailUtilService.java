package net.esc20.txeis.EmployeeAccess.service;

import java.util.HashMap;

import net.esc20.txeis.EmployeeAccess.domainobject.DatabaseContextHolder;
import net.esc20.txeis.EmployeeAccess.domainobject.RoutedPreferenceFactory;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ITxeisPreferenceMap;
import net.esc20.txeis.EmployeeAccess.service.api.IMailUtilService;
import net.esc20.txeis.common.util.Email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class MailUtilService implements IMailUtilService{
	private RoutedPreferenceFactory preferences;

	private MailUtilService() {
	}

	public void sendMail(SimpleMailMessage msg){
		sendMail(msg, null);
	}

	public void sendMail(SimpleMailMessage msg, String contentType){
		try{
			
			Email email = new Email(getEmailMap());
			email.setTo(msg.getTo());
			email.setSubject(msg.getSubject().trim());
			email.setText(msg.getText());
			if (contentType != null) {
				email.setContentType(contentType);
			}
			String defaultDB = DatabaseContextHolder.getCountyDistrict();
			email.sendEmailAdjusted(defaultDB);
			
		} catch (Exception e) {
			String exceptionMsg = "Error queing email.\n";
			exceptionMsg += ("Recipient[0]: " +
					((msg.getTo() != null && msg.getTo().length > 0) ? msg.getTo()[0] : "none specified")+
					"\n");
			exceptionMsg += ("Subject: "+msg.getSubject()+"\n");
			System.err.println(exceptionMsg + e.getMessage());
		}
	}

	private HashMap<String,String> getEmailMap(){
		
		ITxeisPreferenceMap txeisPrefs = preferences.getItem();
		
		String email_protocol = txeisPrefs.getEmailProtocol();
		String email_userid = txeisPrefs.getEmailUserIdPreferences();
		String email_smtp_addr = txeisPrefs.getEmailSmtpAddrPreferences();
		String email_smtp_port = txeisPrefs.getEmailSmtpPortPreferences();
		String email_sender_addr = txeisPrefs.getEmailSenderAddrPreferences();
		String email_password = txeisPrefs.getEmailPasswordPreferences();
		
		HashMap<String,String> emailMap = new HashMap<String,String>();
		
		emailMap.put("email_userid", email_userid);
		emailMap.put("email_password", email_password);
		emailMap.put("email_sender_addr", email_sender_addr);
		emailMap.put("email_smtp_addr", email_smtp_addr);
		emailMap.put("email_smtp_port", email_smtp_port);
		emailMap.put("email_protocol", email_protocol);
				
		return emailMap;
	}

	public RoutedPreferenceFactory getPreferences() {
		return preferences;
	}

	public void setPreferences(RoutedPreferenceFactory preferences) {
		this.preferences = preferences;
	}	
	
}
