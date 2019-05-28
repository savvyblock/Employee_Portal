package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Currency;

@SuppressWarnings("rawtypes")
public class Money implements Comparable, Serializable{
	private static final long serialVersionUID = 6715142900654758429L;
	private BigInteger amount;
	private Currency currency;
	
	public Money (double amount, Currency currency) {
		DecimalFormat noDForm = new DecimalFormat("#");   //jf20120724 fix 10.04 error
		this.amount = new BigInteger(noDForm.format(amount * 100));   //jf20120724 rounds correctly.
		//this.amount = BigInteger.valueOf(Math.round(Math.floor(amount * 100)));   //jf20120724 doesn't work for 10.04, saves 1003, should be 1004.
		this.currency = currency;
	}
	
	public Money (long amount, Currency currency) {
		this.amount = BigInteger.valueOf(amount * 100);
		this.currency = currency;
	}
	
	private Money (BigInteger amountInPennies, Currency currency, boolean privacyMarker) {
		this.amount = amountInPennies;
		this.currency = currency;
	}
	
	public double getAmount() {
		return amount.doubleValue() / 100;
	}
	
	public void setAmount(double amount) {
		DecimalFormat noDForm = new DecimalFormat("#");   //jf20120724 fix 10.04 error
		this.amount = new BigInteger(noDForm.format(amount * 100));   //jf20120724 rounds correctly.
		//this.amount = BigInteger.valueOf(Math.round(Math.floor(amount * 100)));   //jf20120724 doesn't work for 10.04, saves 1003, should be 1004.
	}
	
	public String getDisplayAmount()
	{
		DecimalFormat dFormat = new DecimalFormat("0.00");
		return dFormat.format(getAmount());

	}
	
	public void setDisplayAmount(String displayAmount)
	{
		setAmount(new Double(displayAmount));
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public Money add (Money arg) {
		return new Money (amount.add(arg.amount), currency, true);
	}
	
	public Money subtract (Money arg) {
		return this.add(arg.negate());
	}
	
	public Money negate() {
		return new Money (amount.negate(), currency, true);
	}
	
	public Money multiply (double arg) {
		return new Money (getAmount() * arg, currency);
	}
	
	public Money[] divide(int denominator) {
		BigInteger bigDenominator = BigInteger.valueOf(denominator);
		Money[] result = new Money[denominator];
		BigInteger simpleResult = amount.divide(bigDenominator);
		for (int i = 0; i < denominator ; i++) {
			result[i] = new Money(simpleResult, currency, true);
		}
		int remainder = amount.subtract(simpleResult.multiply(bigDenominator)).intValue();
		for (int i=0; i < remainder; i++) {
			result[i] = result[i].add(new Money(BigInteger.valueOf(1), currency, true));
		}
		return result;
  	}
	
	public int compareTo (Object arg) {
		Money moneyArg = (Money) arg;
		return amount.compareTo(moneyArg.amount);
	}
	
	public boolean greaterThan(Money arg) {
		return (this.compareTo(arg) == 1);
	}
	
	public boolean lessThan(Money arg) {
		return (this.compareTo(arg) == -1);
	}
	
	public boolean equals(Object arg) {
		if (!(arg instanceof Money)) return false;
		Money other = (Money) arg;
		return (currency.equals(other.currency) && (amount.equals(other.amount)));
	}
	
	public int hashCode() {
		return amount.hashCode();
	}
}
