package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

public class EarningsOther implements Serializable {
	
	private static final long serialVersionUID = -1023249528812172648L;
	
	private String description = "";
	private String cafe_flg = "";
	private BigDecimal amt;
	private String code = "";
	private BigDecimal contrib;
	private Integer depCareMax = new Integer (0);
	private Integer hsaCareMax = new Integer (0);
	
	
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
	

}
