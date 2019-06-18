package com.esc20.nonDBModels;

import java.io.Serializable;

public class PayInfo implements Serializable {
	
	private static final long serialVersionUID = 6715142900654758429L;

	private Character maritalStatTax;
	private Integer nbrTaxExempts;
	
	public PayInfo(Object maritalStatTax, Object nbrTaxExempts) {  
		this.maritalStatTax = (Character) maritalStatTax;
		this.nbrTaxExempts = (Integer) nbrTaxExempts;
	}
	
	public Character getMaritalStatTax() {
		return maritalStatTax;
	}
	public void setMaritalStatTax(Character maritalStatTax) {
		this.maritalStatTax = maritalStatTax;
	}
	public Integer getNbrTaxExempts() {
		return nbrTaxExempts;
	}
	public void setNbrTaxExempts(Integer nbrTaxExempts) {
		this.nbrTaxExempts = nbrTaxExempts;
	}
	
	
}
