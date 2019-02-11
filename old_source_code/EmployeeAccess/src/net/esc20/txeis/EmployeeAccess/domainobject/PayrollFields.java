package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

import net.esc20.txeis.EmployeeAccess.conversion.YesNoStringToBoolean;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.FieldDisplayOption;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

public class PayrollFields implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private Boolean code= false;
	private Boolean accountNumber = false;
	private Boolean accountType = false;
	private Boolean depositAmount = false;
	private Boolean maritalStatus = false;
	private Boolean numberOfExemptions = false;
	
	public Boolean getCode() {
		return code;
	}
	
	public void setCode(Boolean code) {
		this.code = code;
	}
	
	public void setCode(String code) {
		this.code = YesNoStringToBoolean.convertToBoolean(code);
	}
	
	public Boolean getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(Boolean accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = YesNoStringToBoolean.convertToBoolean(accountNumber);
	}
	
	public Boolean getAccountType() {
		return accountType;
	}
	
	public void setAccountType(Boolean accountType) {
		this.accountType = accountType;
	}
	
	public void setAccountType(String accountType) {
		this.accountType = YesNoStringToBoolean.convertToBoolean(accountType);
	}
	
	public Boolean getDepositAmount() {
		return depositAmount;
	}
	
	public void setDepositAmount(Boolean depositAmount) {
		this.depositAmount = depositAmount;
	}
	
	public void setDepositAmount(String depositAmount) {
		this.depositAmount = YesNoStringToBoolean.convertToBoolean(depositAmount);
	}
	
	public Boolean getMaritalStatus() {
		return maritalStatus;
	}
	
	public void setMaritalStatus(Boolean maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = YesNoStringToBoolean.convertToBoolean(maritalStatus);
	}
	
	public Boolean getNumberOfExemptions() {
		return numberOfExemptions;
	}
	
	public void setNumberOfExemptions(Boolean numberOfExemptions) {
		this.numberOfExemptions = numberOfExemptions;
	}
	
	public void setNumberOfExemptions(String numberOfExemptions) {
		this.numberOfExemptions = YesNoStringToBoolean.convertToBoolean(numberOfExemptions);
	}
	
	
	
	
}