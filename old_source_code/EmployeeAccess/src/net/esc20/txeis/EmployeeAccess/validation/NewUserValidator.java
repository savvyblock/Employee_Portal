package net.esc20.txeis.EmployeeAccess.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.esc20.txeis.EmployeeAccess.domainobject.RoutedPreferenceFactory;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.report.PasswordPreferences;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;
import net.esc20.txeis.common.constants.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.stereotype.Component;

@Component
public class NewUserValidator extends AbstractValidator
{
	private static final long serialVersionUID = -1023249528812172648L;
	private RoutedPreferenceFactory prefFactory;
	
	@Autowired
	NewUserValidator(RoutedPreferenceFactory prefFactory) {
		this.prefFactory = prefFactory;
	}
	
	@Override
	protected Map<String, String> createTypeValidationMap() {
		return new HashMap<String,String>();
	}
	
	public void validateCreateNewUserInfo(User o, ValidationContext context) {
		super.validateView(context);
		
		if( o.getUserName() == null || "".equals(o.getUserName())) {
			ContextValidationUtil.addMessage(context.getMessageContext(), "userName", "User Name is a required field. Please enter a User Name between 6-8 characters in length.");
		}
		else if(o.getUserName().length() < 6 || o.getUserName().length() > 8)
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "userName", "User Name length is incorrect. Please enter a User Name between 6-8 characters in length.");
		}
		else if (!o.getUserName().matches("\\p{Alnum}+"))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "userName", "User Name is incorrect. User Name must be alpha numeric.");
		}
		else if(o.getPassword().equals(o.getUserName()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "userName", "User Name may not be same as Password.");
		}
		
		PasswordPreferences passPrefs = prefFactory.getItem().getPasswordPreferences();
		if (!o.getPassword().equals(o.getPasswordVerification())) {
			ContextValidationUtil.addMessage(context.getMessageContext(), "password", String.format("New password does not equal new password verification." ));
		}
		//cs20120904 Password size is 6 to 9, hard coded size on from and to 
		if (6 > o.getPassword().length() || o.getPassword().length() > 9) {
			ContextValidationUtil.addMessage(context.getMessageContext(), "password", String.format("New password must be between 6 and 9 characters long."));
		}
		if (passPrefs.isNumbersIncluded() && !o.getPassword().matches(".*[0-9]+.*")){
			ContextValidationUtil.addMessage(context.getMessageContext(), "password", "New password must contain a number.");
		}
		if (passPrefs.isUppercaseIncluded() && !o.getPassword().matches(".*[A-Z]+.*")){
			ContextValidationUtil.addMessage(context.getMessageContext(), "password", "New password must contain an uppercase character.");
		}
		if (passPrefs.isOthersIncluded() && !o.getPassword().matches(".*[.,\'-/&!\"'$%()*+#@!%]+.*")){
			ContextValidationUtil.addMessage(context.getMessageContext(), "password", "New password must contain a special character.");
		}
	
		if(((o.getWorkEmail() != null && !"".equals(o.getWorkEmail())) || ((o.getWorkEmailVerification() != null && !"".equals(o.getWorkEmailVerification())) )) && !o.getWorkEmail().equals(o.getWorkEmailVerification()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "workEmailVerification", "Work Email Verification field does not match Work Email field.");
		}
		
		if(((o.getHomeEmail() != null && !"".equals(o.getHomeEmail())) || ((o.getHomeEmailVerification() != null && !"".equals(o.getHomeEmailVerification())) )) && !o.getHomeEmail().equals(o.getHomeEmailVerification()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "homeEmailVerification", "Home Email Verification field does not match Home Email field.");
		}
		
		if(o.getHomeEmail() != null && !"".equals(o.getHomeEmail()) && o.getHomeEmail().equals(o.getWorkEmail()) && (o.isWorkEmailEmpty() || o.isHomeEmailEmpty())) 
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "homeEmail", "Home Email and Work Email addresses must be different.");
		}
		
		// this regex is derived from IETF RFC 2822 email address standard
		// the standard does not allow capital letters, but the RSCCC DB
		// had all-caps emails in it, so to be consistent, the regex is
		// modified to allow caps
		
		if(o.getWorkEmail() != null && !"".equals(o.getWorkEmail()) && o.isWorkEmailEmpty())
		{
			Pattern p = Pattern.compile("[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?");
			Matcher m = p.matcher(o.getWorkEmail());
			boolean found = m.matches();

			if (!found || ( !StringUtil.right(StringUtil.upper(o.getWorkEmail()), 3).equals("COM") && !StringUtil.right(StringUtil.upper(o.getWorkEmail()), 3).equals("NET") 
				&& !StringUtil.right(StringUtil.upper(o.getWorkEmail()), 3).equals("ORG") && !StringUtil.right(StringUtil.upper(o.getWorkEmail()), 3).equals("EDU")  &&
				!StringUtil.right(StringUtil.upper(o.getWorkEmail()), 3).equals("BIZ")	&& !StringUtil.right(StringUtil.upper(o.getWorkEmail()), 2).equals("TV")  &&
				!StringUtil.right(StringUtil.upper(o.getWorkEmail()), 2).equals("US") && !StringUtil.right(StringUtil.upper(o.getWorkEmail()), 2).equals("CC") && 
				!StringUtil.right(StringUtil.upper(o.getWorkEmail()), 2).equals("WS") && !StringUtil.right(StringUtil.upper(o.getWorkEmail()), 2).equals("BZ") &&
				!StringUtil.right(StringUtil.upper(o.getWorkEmail()), 2).equals("VG") && !StringUtil.right(StringUtil.upper(o.getWorkEmail()), 3).equals("GOV") &&
				!StringUtil.right(StringUtil.upper(o.getWorkEmail()), 2).equals("MS") && !StringUtil.right(StringUtil.upper(o.getWorkEmail()), 2).equals("TC") &&
				!StringUtil.right(StringUtil.upper(o.getWorkEmail()), 2).equals("GS") && !StringUtil.right(StringUtil.upper(o.getWorkEmail()), 4).equals("INFO") &&
				!StringUtil.right(StringUtil.upper(o.getWorkEmail()), 4).equals("NAME")))
				ContextValidationUtil.addMessage(context.getMessageContext(), "workEmail", "Work email is an invalid email format. Please re-enter.");
		}

		if (o.getHomeEmail() != null && !"".equals(o.getHomeEmail()) && o.isHomeEmailEmpty()) {
			//cs20151206  allow any email domains
			if (!o.getHomeEmail().matches(Constant.emailRegEx)) {
				ContextValidationUtil.addMessage(context.getMessageContext(), "homeEmail", "Home email is an invalid email format. Please re-enter.");
			}
//			Pattern p = Pattern.compile("[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?");
//			Matcher m = p.matcher(o.getHomeEmail());
//			boolean found = m.matches();
//		
//			if (!found || ( !StringUtil.right(StringUtil.upper(o.getHomeEmail()), 3).equals("COM") && !StringUtil.right(StringUtil.upper(o.getHomeEmail()), 3).equals("NET") 
//					&& !StringUtil.right(StringUtil.upper(o.getHomeEmail()), 3).equals("ORG") && !StringUtil.right(StringUtil.upper(o.getHomeEmail()), 3).equals("EDU")  &&
//					!StringUtil.right(StringUtil.upper(o.getHomeEmail()), 3).equals("BIZ")	&& !StringUtil.right(StringUtil.upper(o.getHomeEmail()), 2).equals("TV")  &&
//					!StringUtil.right(StringUtil.upper(o.getHomeEmail()), 2).equals("US") && !StringUtil.right(StringUtil.upper(o.getHomeEmail()), 2).equals("CC") && 
//					!StringUtil.right(StringUtil.upper(o.getHomeEmail()), 2).equals("WS") && !StringUtil.right(StringUtil.upper(o.getHomeEmail()), 2).equals("BZ") &&
//					!StringUtil.right(StringUtil.upper(o.getHomeEmail()), 2).equals("VG") && !StringUtil.right(StringUtil.upper(o.getHomeEmail()), 3).equals("GOV") &&
//					!StringUtil.right(StringUtil.upper(o.getHomeEmail()), 2).equals("MS") && !StringUtil.right(StringUtil.upper(o.getHomeEmail()), 2).equals("TC") &&
//					!StringUtil.right(StringUtil.upper(o.getHomeEmail()), 2).equals("GS") && !StringUtil.right(StringUtil.upper(o.getHomeEmail()), 4).equals("INFO") &&
//					!StringUtil.right(StringUtil.upper(o.getHomeEmail()), 4).equals("NAME")))
//				ContextValidationUtil.addMessage(context.getMessageContext(), "homeEmail", "Home email is an invalid email format. Please re-enter.");
		}

		if( o.getHint() == null || "".equals(o.getHint())) {
			ContextValidationUtil.addMessage(context.getMessageContext(), "hint", "Hint field is blank. Please enter a hint.");
		}
		
		if( o.getHintAnswer() == null || "".equals(o.getHintAnswer())) {
			ContextValidationUtil.addMessage(context.getMessageContext(), "hintAnswer", "Hint answer is blank. Please enter a hint answer.");
		}
		else if(o.getHint().equals(o.getHintAnswer()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "hintAnswer", "Hint answer may not be the same as Hint.");
		}
		
	}

	public RoutedPreferenceFactory getPrefFactory() {
		return prefFactory;
	}

	public void setPrefFactory(RoutedPreferenceFactory prefFactory) {
		this.prefFactory = prefFactory;
	}
}