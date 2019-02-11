package com.esc20.nonDBModels;

import java.io.Serializable;

public class PayInfo implements Serializable {
	
	private static final long serialVersionUID = 6715142900654758429L;

	private Character maritalStatTax;
	private Short nbrTaxExempts;
	
	public PayInfo(Object maritalStatTax, Object nbrTaxExempts) {  
		this.maritalStatTax = (Character) maritalStatTax;
		this.nbrTaxExempts = (Short) nbrTaxExempts;
	}
	
	public Character getMaritalStatTax() {
		return maritalStatTax;
	}
	public void setMaritalStatTax(Character maritalStatTax) {
		this.maritalStatTax = maritalStatTax;
	}
	public Short getNbrTaxExempts() {
		return nbrTaxExempts;
	}
	public void setNbrTaxExempts(Short nbrTaxExempts) {
		this.nbrTaxExempts = nbrTaxExempts;
	}
	
	
}
