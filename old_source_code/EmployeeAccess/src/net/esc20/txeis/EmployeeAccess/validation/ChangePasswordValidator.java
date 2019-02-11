package net.esc20.txeis.EmployeeAccess.validation;

import java.util.HashMap;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.domainobject.RoutedPreferenceFactory;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.report.PasswordPreferences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.stereotype.Component;

@Component
public class ChangePasswordValidator extends AbstractValidator {
	private static final long serialVersionUID = -1023249528812172648L;
	private RoutedPreferenceFactory prefFactory;
	
	@Autowired
	ChangePasswordValidator(RoutedPreferenceFactory prefFactory) {
		this.prefFactory = prefFactory;
	}

	@Override
	protected Map<String, String> createTypeValidationMap() {
		return new HashMap<String,String>();
	}
	
	public void validateChangePasswordInfo(User o, ValidationContext context) {
		super.validateView(context);
		
//		boolean hasUpper = false;
//		boolean hasLower = false;
//		boolean hasDigit = false;
		
		//cs20120904 Use the same method to valid the password
		PasswordPreferences passPrefs = prefFactory.getItem().getPasswordPreferences();
		if (!o.getPassword().equals(o.getPasswordVerification())) {
			ContextValidationUtil.addMessage(context.getMessageContext(), "password", String.format("New password does not equal new password verification." ));
		}
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

//		if( o.getPassword() == null || "".equals(o.getPassword())) {
//			ContextValidationUtil.addMessage(context.getMessageContext(), "password", "Password is a required field. Please enter a Password between 6-9 characters in length.");
//		}
//		else if(o.getPassword().length() < 6 || o.getPassword().length() > 9)
//		{
//			ContextValidationUtil.addMessage(context.getMessageContext(), "password", "Password length is incorrect. Please enter a Password between 6-9 characters in length.");
//		}
//		else if(!o.getPassword().equals(o.getPasswordVerification()))
//		{
//			ContextValidationUtil.addMessage(context.getMessageContext(), "passwordVerification", "Password Verification field does not match Password field.");
//		}
//		else if(!o.getPassword().matches("\\p{Alnum}+"))
//		{
//			ContextValidationUtil.addMessage(context.getMessageContext(), "password", "Password is incorrect. Password must be alpha numeric.");
//		}
//		else{
//			for (char c : o.getPassword().toCharArray()) {
//			    if (Character.isUpperCase(c)) {
//			    	hasUpper = true;
//			    }
//			    if (Character.isLowerCase(c)) {
//			    	hasLower = true;
//			    }
//			    if (Character.isDigit(c)) {
//			    	hasDigit = true;
//			    }
//			    if(hasUpper && hasLower && hasDigit)
//			    {
//			    	break;
//			    }
//			}
//			if(!hasUpper || ! hasLower || !hasDigit)
//			{
//				ContextValidationUtil.addMessage(context.getMessageContext(), "password", "Password must contain at least one upper case letter, one lower case letter, and one number.");
//			}
//		}
	}

	public RoutedPreferenceFactory getPrefFactory() {
		return prefFactory;
	}

	public void setPrefFactory(RoutedPreferenceFactory prefFactory) {
		this.prefFactory = prefFactory;
	}

}