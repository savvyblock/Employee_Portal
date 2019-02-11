package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

public class W2Info extends PaymentInfo implements Serializable
{
	private static final long serialVersionUID = -2527433873958761311L;
	
	private BigDecimal taxableGrossPay;
	private boolean pension;
	private BigDecimal cafeteria125;
	private BigDecimal taxableAllowance;
	private BigDecimal employeeBusinessExpense;
	private BigDecimal healthSavingsAccount;
	private BigDecimal nonTaxSickPay;
	private boolean email;
	private boolean showElecConsntW2Popup;

	private BigDecimal sickPay;
	
	public BigDecimal getSickPay() {
		return sickPay;
	}
	public void setSickPay(BigDecimal sickPay) {
		this.sickPay = sickPay;
	}
	public BigDecimal getTaxableGrossPay() {
		return taxableGrossPay;
	}
	public void setTaxableGrossPay(BigDecimal taxableGrossPay) {
		this.taxableGrossPay = taxableGrossPay;
	}
	public boolean isPension() {
		return pension;
	}
	public void setPension(boolean pension) {
		this.pension = pension;
	}
	public BigDecimal getCafeteria125() {
		return cafeteria125;
	}
	public void setCafeteria125(BigDecimal cafeteria125) {
		this.cafeteria125 = cafeteria125;
	}
	public BigDecimal getTaxableAllowance() {
		return taxableAllowance;
	}
	public void setTaxableAllowance(BigDecimal taxableAllowance) {
		this.taxableAllowance = taxableAllowance;
	}
	public BigDecimal getEmployeeBusinessExpense() {
		return employeeBusinessExpense;
	}
	public void setEmployeeBusinessExpense(BigDecimal employeeBusinessExpense) {
		this.employeeBusinessExpense = employeeBusinessExpense;
	}
	public BigDecimal getHealthSavingsAccount() {
		return healthSavingsAccount;
	}
	public void setHealthSavingsAccount(BigDecimal healthSavingsAccount) {
		this.healthSavingsAccount = healthSavingsAccount;
	}
	public BigDecimal getNonTaxSickPay() {
		return nonTaxSickPay;
	}
	public void setNonTaxSickPay(BigDecimal nonTaxSickPay) {
		this.nonTaxSickPay = nonTaxSickPay;
	}
	public boolean isEmail() {
		return email;
	}
	public void setEmail(boolean email) {
		this.email = email;
	}
	public boolean isShowElecConsntW2Popup() {
		return showElecConsntW2Popup;
	}
	public void setShowElecConsntW2Popup(boolean showElecConsntW2Popup) {
		this.showElecConsntW2Popup = showElecConsntW2Popup;
	}
}