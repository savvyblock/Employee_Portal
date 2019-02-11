package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;

import com.esc20.util.NumberUtil;
import com.esc20.util.StringUtil;

public class EarningsOther implements Serializable {
private static final long serialVersionUID = -1023249528812172648L;
	
	private String description = "";
	private String cafe_flg = "";
	private BigDecimal amt;
	private String code = "";
	private BigDecimal contrib;
	private Integer depCareMax = new Integer (0);
	private Integer hsaCareMax = new Integer (0);
	
	private BigDecimal tydAmt;
	private BigDecimal tydContrib;
	
	public EarningsOther(String dedCd, String shortDescr, BigDecimal dedAmt, Character cafeFlg,
			BigDecimal emplrContrib, Integer depCareOverMax, Integer hsaEmplrOverMax) {
		this.setDescription(StringUtil.trim(shortDescr));
		this.setCode(StringUtil.trim(dedCd));
		this.setAmt(NumberUtil.value(dedAmt));
		this.setCafe_flg(StringUtil.trim(cafeFlg.toString()));
		this.setContrib(NumberUtil.value(emplrContrib));
		this.setDepCareMax(depCareOverMax);
		this.setHsaCareMax(hsaEmplrOverMax);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCafe_flg() {
		return cafe_flg;
	}
	public void setCafe_flg(String cafe_flg) {
		this.cafe_flg = cafe_flg;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getContrib() {
		return contrib;
	}
	public void setContrib(BigDecimal contrib) {
		this.contrib = contrib;
	}
	public Integer getDepCareMax() {
		return depCareMax;
	}
	public void setDepCareMax(Integer depCareMax) {
		this.depCareMax = depCareMax;
	}
	public Integer getHsaCareMax() {
		return hsaCareMax;
	}
	public void setHsaCareMax(Integer hsaCareMax) {
		this.hsaCareMax = hsaCareMax;
	}
	public BigDecimal getTydAmt() {
		return tydAmt;
	}
	public void setTydAmt(BigDecimal tydAmt) {
		this.tydAmt = tydAmt;
	}
	public BigDecimal getTydContrib() {
		return tydContrib;
	}
	public void setTydContrib(BigDecimal tydContrib) {
		this.tydContrib = tydContrib;
	}
}
