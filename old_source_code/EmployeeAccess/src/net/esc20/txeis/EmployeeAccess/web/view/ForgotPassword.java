package net.esc20.txeis.EmployeeAccess.web.view;

import java.io.Serializable;

public class ForgotPassword implements Serializable
{

	private static final long serialVersionUID = 8222009316886080791L;
	
	private int emailFailAttempts;

	public int getEmailFailAttempts() {
		return emailFailAttempts;
	}

	public void setEmailFailAttempts(int emailFailAttempts) {
		this.emailFailAttempts = emailFailAttempts;
	}
	
}
