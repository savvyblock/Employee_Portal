package net.esc20.txeis.EmployeeAccess.Exception;

public class LeaveRequestException extends Exception {

	private static final long serialVersionUID = -4852119337942738723L;
	
	public static final int SERVICE_ERROR = 1;
	public static final int SERVICE_SUBMITTAL_NO_PAYDATE = 2;
	
	int code;
	String message="";

	public LeaveRequestException(int code) {
		super();
		this.code = code;
	}

	public LeaveRequestException(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
