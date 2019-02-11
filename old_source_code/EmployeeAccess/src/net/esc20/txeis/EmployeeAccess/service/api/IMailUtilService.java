package net.esc20.txeis.EmployeeAccess.service.api;

import org.springframework.mail.SimpleMailMessage;

public interface IMailUtilService {
	public void sendMail(SimpleMailMessage msg );
	public void sendMail(SimpleMailMessage msg, String contentType );
}
