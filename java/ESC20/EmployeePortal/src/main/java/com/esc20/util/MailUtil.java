package com.esc20.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esc20.dao.PreferencesDao;

@Component
public class MailUtil {

    @Autowired
    private PreferencesDao preferencesDao;
	
	public void sendEmail(String to, String subject, String content) throws MessagingException{
		String serverHost = preferencesDao.getPrefenceByPrefName("email_smtp_addr").getPrefValue();
		Integer serverPort = Integer.parseInt(preferencesDao.getPrefenceByPrefName("email_smtp_port").getPrefValue());
		String fromAddress = preferencesDao.getPrefenceByPrefName("email_sender_addr").getPrefValue();
		
		Properties props = new Properties();
		props.put("mail.smtp.host", serverHost);
		props.put("mail.smtp.port", serverPort);
		props.put("mail.smtp.auth", "false");
		//props.put("mail.transport.protocol", "smtp");
		Session session = Session.getInstance(props);
		
		MimeMessage message = new MimeMessage(session);
		message.setSubject(subject);
		message.setContent(content,"text/html;charset=UTF-8");
		message.setFrom(new InternetAddress(fromAddress));
		message.setRecipients(Message.RecipientType.TO, to);
		Transport.send(message);
		/*Transport transport = session.getTransport();
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();*/
	}
	
}