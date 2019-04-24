package com.esc20.nonDBModels.report;

public class PasswordPreferences {
	
	boolean 	numbersIncluded 	= true;
	boolean 	othersIncluded 		= true;
	boolean 	uppercaseIncluded 	= true;
	int     	length			;

	public PasswordPreferences(boolean numbersIncluded,boolean othersIncluded,boolean uppercaseIncluded,int length ){
		this.numbersIncluded = numbersIncluded;
		this.othersIncluded = othersIncluded;
		this.uppercaseIncluded = uppercaseIncluded;
		this.length = length;
	}

	public boolean isNumbersIncluded() {
		return numbersIncluded;
	}

	public boolean isOthersIncluded() {
		return othersIncluded;
	}

	public boolean isUppercaseIncluded() {
		return uppercaseIncluded;
	}

	public int getLength() {
		return length;
	}
	
	
	
}
