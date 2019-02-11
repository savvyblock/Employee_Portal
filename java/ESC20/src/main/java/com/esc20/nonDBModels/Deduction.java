package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;

import com.esc20.util.StringUtil;

public class Deduction implements Serializable {
	
	private static final long serialVersionUID = 6715142900654758429L;

	private String dedCd;
	private String dedCdDesc;
	private BigDecimal empAmt;
	private BigDecimal emplrAmt;
	private Boolean cafeFlg;
	
	public Deduction() {
		
	}
	
	public Deduction(Object dedCd, Object dedCdDesc, Object empAmt, Object emplrAmt, Object cafeFlg) {
		this.dedCd = (String) dedCd;
		this.dedCdDesc = (String) dedCdDesc;
		this.empAmt = (BigDecimal) empAmt;
		this.emplrAmt = (BigDecimal) emplrAmt;
		this.cafeFlg = StringUtil.convertToBoolean(((Character) cafeFlg).toString());
	}

	public String getDedCd() {
		return dedCd;
	}
	public void setDedCd(String dedCd) {
		this.dedCd = dedCd;
	}
	public String getDedCdDesc() {
		return dedCdDesc;
	}
	public void setDedCdDesc(String dedCdDesc) {
		this.dedCdDesc = dedCdDesc;
	}
	public BigDecimal getEmpAmt() {
		return empAmt;
	}
	public void setEmpAmt(BigDecimal empAmt) {
		this.empAmt = empAmt;
	}
	public BigDecimal getEmplrAmt() {
		return emplrAmt;
	}
	public void setEmplrAmt(BigDecimal emplrAmt) {
		this.emplrAmt = emplrAmt;
	}
	public Boolean getCafeFlg() {
		return cafeFlg;
	}
	public void setCafeFlg(Boolean cafeFlg) {
		this.cafeFlg = cafeFlg;
	}
	
	
}
