package net.esc20.txeis.EmployeeAccess.service.api;

import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.web.view.ForgotPassword;

import org.springframework.binding.message.MessageContext;

public interface IPasswordService
{
	boolean changePassword(MessageContext messageContext, User changePassword, String userName, String userFirstName, String userLastName, String userWorkEmail, String userHomeEmail);
	int requestNewPassword(MessageContext messageContext, User user, String hintAnswer, String emailSelected, ForgotPassword failEmail);
	boolean isAccountHintLocked(MessageContext messageContext, User user);
	String generateTempPassword();
	int sendMail(User user, String emailSelected, String password);
	void addPasswordSuccessMessage(MessageContext messageContext);
}