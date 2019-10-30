//package com.esc20.util;
//
//import java.util.Properties;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//public class MailUtil {
//
//	private static final String serverHost = "smtp.163.com";
//
//	private static final Integer serverPort = 25;
//
//	private static final String fromAddress = "m15043019587@163.com";
//	
//	private static final String pass = "";
//	
//	public static void sendEmail(String to, String subject, String content) throws MessagingException{
//		Properties props = new Properties();
//		props.put("mail.smtp.host", serverHost);
//		props.put("mail.smtp.port", serverPort);
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.transport.protocol", "smtp");
//		Session session = Session.getInstance(props);
//		
//		MimeMessage message = new MimeMessage(session);
//		message.setSubject(subject);
//		message.setContent(content,"text/html;charset=UTF-8");
//		message.setFrom(new InternetAddress(fromAddress));
//		message.setRecipients(Message.RecipientType.TO, to);
//		Transport transport = session.getTransport();
//		transport.connect(fromAddress, pass);
//		transport.sendMessage(message, message.getAllRecipients());
//		transport.close();
//	}
//	
//}

package com.esc20.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class MailUtil {

	//private static final String serverHost = "tcc11smtp.txeis.net";
	private static final String serverHost = "tcc20smtp.txeis.net"; 

	private static final Integer serverPort = 25;

	private static final String fromAddress = "employeeportal@txeis.net";
	
	public static void sendEmail(String to, String subject, String content) throws MessagingException{
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