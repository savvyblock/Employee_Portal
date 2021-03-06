package com.esc20.model;
// Generated Jan 4, 2019 3:44:27 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BhrPayLiabChecksId generated by hbm2java
 */
@Embeddable
public class BhrPayLiabChecksId implements java.io.Serializable {
	private static final long serialVersionUID = 6715142900654758429L;
	private char payFreq;
	private String empNbr;
	private char cyrNyrFlg;
	private String dtOfPay;
	private String dedCd;
	private String dedSeq;

	public BhrPayLiabChecksId() {
	}

	public BhrPayLiabChecksId(char payFreq, String empNbr, char cyrNyrFlg, String dtOfPay, String dedCd,
			String dedSeq) {
		this.payFreq = payFreq;
		this.empNbr = empNbr;
		this.cyrNyrFlg = cyrNyrFlg;
		this.dtOfPay = dtOfPay;
		this.dedCd = dedCd;
		this.dedSeq = dedSeq;
	}

	@Column(name = "PAY_FREQ", nullable = false, length = 1)
	public char getPayFreq() {
		return this.payFreq;
	}

	public void setPayFreq(char payFreq) {
		this.payFreq = payFreq;
	}

	@Column(name = "EMP_NBR", nullable = false, length = 6)
	public String getEmpNbr() {
		return this.empNbr;
	}

	public void setEmpNbr(String empNbr) {
		this.empNbr = empNbr;
	}

	@Column(name = "CYR_NYR_FLG", nullable = false, length = 1)
	public char getCyrNyrFlg() {
		return this.cyrNyrFlg;
	}

	public void setCyrNyrFlg(char cyrNyrFlg) {
		this.cyrNyrFlg = cyrNyrFlg;
	}

	@Column(name = "DT_OF_PAY", nullable = false, length = 8)
	public String getDtOfPay() {
		return this.dtOfPay;
	}

	public void setDtOfPay(String dtOfPay) {
		this.dtOfPay = dtOfPay;
	}

	@Column(name = "DED_CD", nullable = false, length = 3)
	public String getDedCd() {
		return this.dedCd;
	}

	public void setDedCd(String dedCd) {
		this.dedCd = dedCd;
	}

	@Column(name = "DED_SEQ", nullable = false, length = 2)
	public String getDedSeq() {
		return this.dedSeq;
	}

	public void setDedSeq(String dedSeq) {
		this.dedSeq = dedSeq;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BhrPayLiabChecksId))
			return false;
		BhrPayLiabChecksId castOther = (BhrPayLiabChecksId) other;

		return (this.getPayFreq() == castOther.getPayFreq())
				&& ((this.getEmpNbr() == castOther.getEmpNbr()) || (this.getEmpNbr() != null
						&& castOther.getEmpNbr() != null && this.getEmpNbr().equals(castOther.getEmpNbr())))
				&& (this.getCyrNyrFlg() == castOther.getCyrNyrFlg())
				&& ((this.getDtOfPay() == castOther.getDtOfPay()) || (this.getDtOfPay() != null
						&& castOther.getDtOfPay() != null && this.getDtOfPay().equals(castOther.getDtOfPay())))
				&& ((this.getDedCd() == castOther.getDedCd()) || (this.getDedCd() != null
						&& castOther.getDedCd() != null && this.getDedCd().equals(castOther.getDedCd())))
				&& ((this.getDedSeq() == castOther.getDedSeq()) || (this.getDedSeq() != null
						&& castOther.getDedSeq() != null && this.getDedSeq().equals(castOther.getDedSeq())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getPayFreq();
		result = 37 * result + (getEmpNbr() == null ? 0 : this.getEmpNbr().hashCode());
		result = 37 * result + this.getCyrNyrFlg();
		result = 37 * result + (getDtOfPay() == null ? 0 : this.getDtOfPay().hashCode());
		result = 37 * result + (getDedCd() == null ? 0 : this.getDedCd().hashCode());
		result = 37 * result + (getDedSeq() == null ? 0 : this.getDedSeq().hashCode());
		return result;
	}

}
