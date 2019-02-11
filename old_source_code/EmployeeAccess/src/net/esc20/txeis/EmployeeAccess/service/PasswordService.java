package net.esc20.txeis.EmployeeAccess.service;

import java.util.Random;

import net.esc20.txeis.EmployeeAccess.dao.PasswordDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IPasswordDao;
import net.esc20.txeis.EmployeeAccess.domainobject.RoutedPreferenceFactory;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.report.PasswordPreferences;
import net.esc20.txeis.EmployeeAccess.service.api.IMailUtilService;
import net.esc20.txeis.EmployeeAccess.service.api.IPasswordService;
import net.esc20.txeis.EmployeeAccess.validation.ContextValidationUtil;
import net.esc20.txeis.EmployeeAccess.web.view.ForgotPassword;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService implements IPasswordService {
	
	private IPasswordDao passwordDao;
	private IMailUtilService mailUtilService;
	private SimpleMailMessage message;
	private RoutedPreferenceFactory prefFactory;

	private static Log log = LogFactory.getLog(PasswordService.class);

	@Autowired
	public PasswordService(PasswordDao passwordDao, MailUtilService mailUtilService, RoutedPreferenceFactory prefFactory) {
		this.passwordDao = passwordDao;
		this.mailUtilService = mailUtilService;
		this.message = new SimpleMailMessage();
		this.prefFactory = prefFactory;
	}
	
	@Override
	public boolean changePassword(MessageContext messageContext, User changePassword, String userName, String userFirstName, String userLastName, String userWorkEmail, String userHomeEmail)
	{
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String encodedPassword = encoder.encodePassword(changePassword.getPassword(), null);
		String oldPassword = passwordDao.retrievePassword(userName);
		
		if(encodedPassword.equals(oldPassword))
		{
			ContextValidationUtil.addMessage(messageContext, "password", "New password must be different than old password.");
			return false;
		}
		
		PasswordPreferences passPrefs = prefFactory.getItem().getPasswordPreferences();
		Boolean badPassword = false;
		if (!changePassword.getPassword().equals(changePassword.getPasswordVerification())) {
			ContextValidationUtil.addMessage(messageContext, "password", String.format("New password does not equal new password verification." ));
			badPassword = true;
		}
		//cs20120904 Password size is 6 to 9, hard coded size on from and to 
		if (6 > changePassword.getPassword().length() || changePassword.getPassword().length() > 9) {
			ContextValidationUtil.addMessage(messageContext, "password", String.format("New password must be between 6 and 9 characters long."));
			badPassword = true;	
		}
		if (passPrefs.isNumbersIncluded() && !changePassword.getPassword().matches(".*[0-9]+.*")){
			ContextValidationUtil.addMessage(messageContext, "password", "New password must contain a number.");
			badPassword = true;
		}
		if (passPrefs.isUppercaseIncluded() && !changePassword.getPassword().matches(".*[A-Z]+.*")){
			ContextValidationUtil.addMessage(messageContext, "password", "New password must contain an uppercase character.");
			badPassword = true;	
		}		
		if (passPrefs.isOthersIncluded() && !changePassword.getPassword().matches(".*[.,\'-/&!\"'$%()*+#@!%]+.*")){
			ContextValidationUtil.addMessage(messageContext, "password", "New password must contain a special character.");
			badPassword = true;	
		}

		if (badPassword) {
			return false;
		}
		
		passwordDao.updatePassword(0, userName, changePassword.getPassword());
		passwordDao.resetLocks(userName);
		
		passwordChangeSendEmailConfirmation (userName, userFirstName, userLastName, userHomeEmail, userWorkEmail); 
		
		ContextValidationUtil.assertSuccess(messageContext);
		return true;
	}
	
	@Override
	public int  requestNewPassword(MessageContext messageContext, User user, String hintAnswer, String emailSelected, ForgotPassword emailFail)
	{
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		
		if(!"".equals(hintAnswer) && !encoder.encodePassword(hintAnswer,null).equals(user.getHintAnswer()))
		{
			int hintCount = passwordDao.retrieveHintCount(user.getUserName()) +1;
			if(hintCount < 3)
			{
				ContextValidationUtil.addMessage(messageContext, "", "Hint Answer entered is incorrect. Please re-enter.");
				passwordDao.updateHintCount(hintCount, user.getUserName());
				return 0;
			}
			else if(hintCount == 3)
			{
				passwordDao.updateHintCount(hintCount, user.getUserName());
				if(messageContext != null)
					ContextValidationUtil.addMessage(messageContext, "", "User has exceeded the maximum number of tries to answer the hint. Please contact personnel office to delete existing user account before creating a new one.");
				return -1;
			}
			else
			{
				if(messageContext != null)
					ContextValidationUtil.addMessage(messageContext, "", "User has exceeded the maximum number of tries to answer the hint. Please contact personnel office to delete existing user account before creating a new one.");
				return -1;
			}
		}
		else if(!"".equals(hintAnswer) && encoder.encodePassword(hintAnswer,null).equals(user.getHintAnswer()))
		{
			boolean passwordSuccess = false;
			String password ="";
			
			while(!passwordSuccess)
			{
				boolean hasUpper = false;
				boolean hasLower = false;
				boolean hasDigit = false;
				password = generateTempPassword();
				
				for (char c : password.toCharArray()) {
				    if (Character.isUpperCase(c)) {
				    	hasUpper = true;
				    }
				    if (Character.isLowerCase(c)) {
				    	hasLower = true;
				    }
				    if (Character.isDigit(c)) {
				    	hasDigit = true;
				    }
				    if(hasUpper && hasLower && hasDigit)
				    {
				    	passwordSuccess = true;
				    	break;
				    }
				}
			}
			
			int tempCount = passwordDao.retrieveTempCount(user.getUserName()) +1;
			
			passwordDao.updatePassword(tempCount, user.getUserName(), password);
			
			int emailSuccess = sendMail(user, emailSelected, password);
			
			if(emailSuccess != 1 && emailFail.getEmailFailAttempts() < 1)
			{
				ContextValidationUtil.addMessage(messageContext, "", "There was a problem sending your temporary password. Please confirm your email address is valid. ");
				emailFail.setEmailFailAttempts(emailFail.getEmailFailAttempts()+1);
			}
			
			else if(emailSuccess != 1 && emailFail.getEmailFailAttempts() >= 1)
			{
				ContextValidationUtil.addMessage(messageContext, "", "There was a problem sending your temporary password. Please contact your local office personel for more information.");
				emailFail.setEmailFailAttempts(emailFail.getEmailFailAttempts()+1);
			}
			
			return emailSuccess;
		}
		else{
			ContextValidationUtil.addMessage(messageContext, "", "Hint Answer is blank. Please enter an answer.");
			return 0;
		}
	}
	
	@Override
	public String generateTempPassword()
	{ 
		String lowerCharSet= "abcdefghijklmnopqrstuvwxyz";
		String upperCharSet= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numericSet= "0123456789";
		StringBuffer typeSet= new StringBuffer("");
		
		Random randGenerator = new Random();
		
		StringBuffer tempPassword = new StringBuffer("");
		
		int length = (randGenerator.nextInt(4)) + 6;
		
		for(int i = 0; i < length; i++)
		{
			int tempNum = (randGenerator.nextInt(3));
			if( tempNum == 0)
			{
				typeSet.append("u");
			}
			else if(tempNum == 1)
			{
				typeSet.append("l");
			}
			else if(tempNum == 2)
			{
				typeSet.append("n");
			}
			
		}
		
		for(int i = 0; i < length; i++)
		{
			
			if(typeSet.charAt(i) == 'u')
			{
				int tempNum = (randGenerator.nextInt((upperCharSet.length())));
				tempPassword.append(upperCharSet.charAt(tempNum));
			}
			else if(typeSet.charAt(i) == 'l')
			{
				int tempNum = (randGenerator.nextInt((lowerCharSet.length())));
				tempPassword.append(lowerCharSet.charAt(tempNum));
			}
			else if(typeSet.charAt(i) == 'n')
			{
				int tempNum = (randGenerator.nextInt((numericSet.length())));
				tempPassword.append(numericSet.charAt(tempNum));
			}
			
		}		
		return tempPassword.toString();

	}
	
	@Override
	public boolean isAccountHintLocked(MessageContext messageContext, User user)
	{
		String result = passwordDao.retrieveHintLock(user.getUserName());
		
		if("Y".equals(result))
		{
			if(messageContext != null)
				ContextValidationUtil.addMessage(messageContext, "", "User has exceeded the maximum number of tries to answer the hint. Please contact personnel office to delete existing user account before creating a new one.");
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	@Override
	public int sendMail(User user, String emailSelected, String password)
	{
		int mailSuccess = 1;
		SimpleMailMessage msg = new SimpleMailMessage(this.message);
		msg.setSubject("Temporary Password Generated");
		msg.setText("Temporary Password: " + password + "\nYour temporary password will expire within 24 hours.");
		String toEmail="";
		
		if("work".equals(emailSelected)) {
			if (!user.getWorkEmail().trim().equals("")) {
				toEmail = user.getWorkEmail();
			}
		} else {
			if (!user.getHomeEmail().trim().equals("")) {
				toEmail = user.getHomeEmail();
			}
		}
		
		if (toEmail.equals("")) {
			mailSuccess = -2;
		} else {
			try{
				msg.setTo(toEmail);
				mailUtilService.sendMail(msg);
			} catch(MailException ex) {
				mailSuccess = -2;
			} 
		}
		
		return mailSuccess;
	}

	@Override
	public void addPasswordSuccessMessage(MessageContext messageContext)
	{
		ContextValidationUtil.assertSuccess(messageContext);
	}

	public RoutedPreferenceFactory getPrefFactory() {
		return prefFactory;
	}

	public void setPrefFactory(RoutedPreferenceFactory prefFactory) {
		this.prefFactory = prefFactory;
	}
	
	private void passwordChangeSendEmailConfirmation (String userName, String userFirstName, String userLastName, String userHomeEmail, String userWorkEmail) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setSubject("A MESSAGE FROM SELF SERVICE");
		StringBuilder messageContents = new StringBuilder();
		userFirstName = userFirstName== null ? "" : userFirstName.trim();
		userLastName = userLastName== null ? "" : userLastName.trim();
		messageContents.append(userFirstName + " " +userLastName + ", \n\n");
		messageContents.append("Your request to change your password was successful. \n");		
		
		msg.setText(messageContents.toString());
		
		if (!"".equals(userWorkEmail)) {
			msg.setTo(userWorkEmail);
		} else if (!"".equals(userHomeEmail)) {
			msg.setTo(userHomeEmail);	
		}
		if (msg.getTo()!=null && msg.getTo().length > 0) {
			try{
				mailUtilService.sendMail(msg);
			} 
			catch(MailException ex) {
				log.info("Self Service Change Password: An exception has occured with mailing the user "+userName+".");
			} 
		} else {
			log.info("Self Service Change Password: Unable to send an email confirmation.  No email address is avaiable for user "+userName+".");
		}
		
	}
}
