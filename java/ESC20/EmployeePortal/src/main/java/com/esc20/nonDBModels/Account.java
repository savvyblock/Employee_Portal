package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import com.esc20.util.NumberUtil;

public class Account implements Serializable{

	private static final long serialVersionUID = 6715142900654758429L;
	private String bankCd;
	private String bankName;
	private String transitRoute;
	private Frequency frequency;
	private String bankAccountNumber;
	private Character bankAccountType;
	private String bankAccountTypeDescription;
	private Money bankAccountAmount;
	public Account(Object bankCd, Object bankName, Object transitRoute, Object frequency, Object bankAccountNumber, Object bankAccountType,
			Object bankAccountTypeDescription, Object bankAccountAmount) {
		this.setBankCd((String) bankCd);
		this.setBankName((String) bankName);
		this.setTransitRoute((String) transitRoute);
		this.setFrequency(Frequency.getFrequency((((Character) frequency).toString()).trim()));
		this.setBankAccountNumber((String) bankAccountNumber);
		this.setBankAccountType((Character) bankAccountType);
		this.setBankAccountTypeDescription((String) bankAccountTypeDescription);
		this.setBankAccountAmount(new Money(NumberUtil.value((BigDecimal) bankAccountAmount).doubleValue(), Currency.getInstance(Locale.US)));
	}
	public String getBankCd() {
		return bankCd;
	}
	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getTransitRoute() {
		return transitRoute;
	}
	public void setTransitRoute(String transitRoute) {
		this.transitRoute = transitRoute;
	}
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public Character getBankAccountType() {
		return bankAccountType;
	}
	public void setBankAccountType(Character bankAccountType) {
		this.bankAccountType = bankAccountType;
	}
	public String getBankAccountTypeDescription() {
		return bankAccountTypeDescription;
	}
	public void setBankAccountTypeDescription(String bankAccountTypeDescription) {
		this.bankAccountTypeDescription = bankAccountTypeDescription;
	}
	public Money getBankAccountAmount() {
		return bankAccountAmount;
	}
	public void setBankAccountAmount(Money bankAccountAmount) {
		this.bankAccountAmount = bankAccountAmount;
	}
}
