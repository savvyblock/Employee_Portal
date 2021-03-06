package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.FieldDisplayOption;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

public class BankChanges implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private Boolean codeChanged = false;
	private Boolean accountNumberChanged = false;
	private Boolean accountTypeChanged = false;
	private Boolean depositAmountChanged = false;
	
	public Boolean getCodeChanged() {
		return codeChanged;
	}
	public void setCodeChanged(Boolean codeChanged) {
		this.codeChanged = codeChanged;
	}
	public Boolean getAccountNumberChanged() {
		return accountNumberChanged;
	}
	public void setAccountNumberChanged(Boolean accountNumberChanged) {
		this.accountNumberChanged = accountNumberChanged;
	}
	public Boolean getAccountTypeChanged() {
		return accountTypeChanged;
	}
	public void setAccountTypeChanged(Boolean accountTypeChanged) {
		this.accountTypeChanged = accountTypeChanged;
	}
	public Boolean getDepositAmountChanged() {
		return depositAmountChanged;
	}
	public void setDepositAmountChanged(Boolean depositAmountChanged) {
		this.depositAmountChanged = depositAmountChanged;
	}
	
	
}