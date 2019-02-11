package net.esc20.txeis.EmployeeAccess.web.mvc;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

public class UserException extends RuntimeException{
	
	private static final long serialVersionUID = 6032839823495500860L;

	public UserException(String msg){
		super(msg);
	}
	
	public UserException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public void add(BindException errors, String objectName ){
		errors.addError(new ObjectError(objectName,null,null,getMessage()));	
	}
	
	public boolean isLogged() {
		return false;
	}
}
