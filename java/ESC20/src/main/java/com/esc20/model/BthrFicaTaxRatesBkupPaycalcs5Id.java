package com.esc20.model;
// Generated Jan 4, 2019 3:51:33 PM by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BthrFicaTaxRatesBkupPaycalcs5Id generated by hbm2java
 */
@Embeddable
public class BthrFicaTaxRatesBkupPaycalcs5Id implements java.io.Serializable {

	private String taxYr;
	private BigDecimal ficaRate;
	private BigDecimal ficaMaxSalary;
	private BigDecimal ficaMaxTax;
	private BigDecimal medRate;
	private BigDecimal medMaxSalary;
	private BigDecimal medMaxTax;
	private BigDecimal eicMaxSal;
	private BigDecimal emplrFicaRate;
	private BigDecimal emplrFicaMaxTax;
	private BigDecimal addlMedRate;
	private BigDecimal addlMedSalThreshold;
	private String module;
	private BigDecimal hsaMaxEmplrContrib;

	public BthrFicaTaxRatesBkupPaycalcs5Id() {
	}

	public BthrFicaTaxRatesBkupPaycalcs5Id(String taxYr, BigDecimal ficaRate, BigDecimal ficaMaxSalary,
			BigDecimal ficaMaxTax, BigDecimal medRate, BigDecimal medMaxSalary, BigDecimal medMaxTax,
			BigDecimal eicMaxSal, BigDecimal emplrFicaRate, BigDecimal emplrFicaMaxTax, BigDecimal addlMedRate,
			BigDecimal addlMedSalThreshold, String module, BigDecimal hsaMaxEmplrContrib) {
		this.taxYr = taxYr;
		this.ficaRate = ficaRate;
		this.ficaMaxSalary = ficaMaxSalary;
		this.ficaMaxTax = ficaMaxTax;
		this.medRate = medRate;
		this.medMaxSalary = medMaxSalary;
		this.medMaxTax = medMaxTax;
		this.eicMaxSal = eicMaxSal;
		this.emplrFicaRate = emplrFicaRate;
		this.emplrFicaMaxTax = emplrFicaMaxTax;
		this.addlMedRate = addlMedRate;
		this.addlMedSalThreshold = addlMedSalThreshold;
		this.module = module;
		this.hsaMaxEmplrContrib = hsaMaxEmplrContrib;
	}

	@Column(name = "TAX_YR", nullable = false, length = 4)
	public String getTaxYr() {
		return this.taxYr;
	}

	public void setTaxYr(String taxYr) {
		this.taxYr = taxYr;
	}

	@Column(name = "FICA_RATE", nullable = false, precision = 5, scale = 4)
	public BigDecimal getFicaRate() {
		return this.ficaRate;
	}

	public void setFicaRate(BigDecimal ficaRate) {
		this.ficaRate = ficaRate;
	}

	@Column(name = "FICA_MAX_SALARY", nullable = false, precision = 9)
	public BigDecimal getFicaMaxSalary() {
		return this.ficaMaxSalary;
	}

	public void setFicaMaxSalary(BigDecimal ficaMaxSalary) {
		this.ficaMaxSalary = ficaMaxSalary;
	}

	@Column(name = "FICA_MAX_TAX", nullable = false, precision = 9)
	public BigDecimal getFicaMaxTax() {
		return this.ficaMaxTax;
	}

	public void setFicaMaxTax(BigDecimal ficaMaxTax) {
		this.ficaMaxTax = ficaMaxTax;
	}

	@Column(name = "MED_RATE", nullable = false, precision = 5, scale = 4)
	public BigDecimal getMedRate() {
		return this.medRate;
	}

	public void setMedRate(BigDecimal medRate) {
		this.medRate = medRate;
	}

	@Column(name = "MED_MAX_SALARY", nullable = false, precision = 9)
	public BigDecimal getMedMaxSalary() {
		return this.medMaxSalary;
	}

	public void setMedMaxSalary(BigDecimal medMaxSalary) {
		this.medMaxSalary = medMaxSalary;
	}

	@Column(name = "MED_MAX_TAX", nullable = false, precision = 9)
	public BigDecimal getMedMaxTax() {
		return this.medMaxTax;
	}

	public void setMedMaxTax(BigDecimal medMaxTax) {
		this.medMaxTax = medMaxTax;
	}

	@Column(name = "EIC_MAX_SAL", nullable = false, precision = 7)
	public BigDecimal getEicMaxSal() {
		return this.eicMaxSal;
	}

	public void setEicMaxSal(BigDecimal eicMaxSal) {
		this.eicMaxSal = eicMaxSal;
	}

	@Column(name = "EMPLR_FICA_RATE", nullable = false, precision = 5, scale = 4)
	public BigDecimal getEmplrFicaRate() {
		return this.emplrFicaRate;
	}

	public void setEmplrFicaRate(BigDecimal emplrFicaRate) {
		this.emplrFicaRate = emplrFicaRate;
	}

	@Column(name = "EMPLR_FICA_MAX_TAX", nullable = false, precision = 9)
	public BigDecimal getEmplrFicaMaxTax() {
		return this.emplrFicaMaxTax;
	}

	public void setEmplrFicaMaxTax(BigDecimal emplrFicaMaxTax) {
		this.emplrFicaMaxTax = emplrFicaMaxTax;
	}

	@Column(name = "ADDL_MED_RATE", nullable = false, precision = 5, scale = 4)
	public BigDecimal getAddlMedRate() {
		return this.addlMedRate;
	}

	public void setAddlMedRate(BigDecimal addlMedRate) {
		this.addlMedRate = addlMedRate;
	}

	@Column(name = "ADDL_MED_SAL_THRESHOLD", nullable = false, precision = 9)
	public BigDecimal getAddlMedSalThreshold() {
		return this.addlMedSalThreshold;
	}

	public void setAddlMedSalThreshold(BigDecimal addlMedSalThreshold) {
		this.addlMedSalThreshold = addlMedSalThreshold;
	}

	@Column(name = "MODULE", nullable = false, length = 25)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Column(name = "HSA_MAX_EMPLR_CONTRIB", nullable = false, precision = 7)
	public BigDecimal getHsaMaxEmplrContrib() {
		return this.hsaMaxEmplrContrib;
	}

	public void setHsaMaxEmplrContrib(BigDecimal hsaMaxEmplrContrib) {
		this.hsaMaxEmplrContrib = hsaMaxEmplrContrib;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BthrFicaTaxRatesBkupPaycalcs5Id))
			return false;
		BthrFicaTaxRatesBkupPaycalcs5Id castOther = (BthrFicaTaxRatesBkupPaycalcs5Id) other;

		return ((this.getTaxYr() == castOther.getTaxYr()) || (this.getTaxYr() != null && castOther.getTaxYr() != null
				&& this.getTaxYr().equals(castOther.getTaxYr())))
				&& ((this.getFicaRate() == castOther.getFicaRate()) || (this.getFicaRate() != null
						&& castOther.getFicaRate() != null && this.getFicaRate().equals(castOther.getFicaRate())))
				&& ((this.getFicaMaxSalary() == castOther.getFicaMaxSalary())
						|| (this.getFicaMaxSalary() != null && castOther.getFicaMaxSalary() != null
								&& this.getFicaMaxSalary().equals(castOther.getFicaMaxSalary())))
				&& ((this.getFicaMaxTax() == castOther.getFicaMaxTax()) || (this.getFicaMaxTax() != null
						&& castOther.getFicaMaxTax() != null && this.getFicaMaxTax().equals(castOther.getFicaMaxTax())))
				&& ((this.getMedRate() == castOther.getMedRate()) || (this.getMedRate() != null
						&& castOther.getMedRate() != null && this.getMedRate().equals(castOther.getMedRate())))
				&& ((this.getMedMaxSalary() == castOther.getMedMaxSalary())
						|| (this.getMedMaxSalary() != null && castOther.getMedMaxSalary() != null
								&& this.getMedMaxSalary().equals(castOther.getMedMaxSalary())))
				&& ((this.getMedMaxTax() == castOther.getMedMaxTax()) || (this.getMedMaxTax() != null
						&& castOther.getMedMaxTax() != null && this.getMedMaxTax().equals(castOther.getMedMaxTax())))
				&& ((this.getEicMaxSal() == castOther.getEicMaxSal()) || (this.getEicMaxSal() != null
						&& castOther.getEicMaxSal() != null && this.getEicMaxSal().equals(castOther.getEicMaxSal())))
				&& ((this.getEmplrFicaRate() == castOther.getEmplrFicaRate())
						|| (this.getEmplrFicaRate() != null && castOther.getEmplrFicaRate() != null
								&& this.getEmplrFicaRate().equals(castOther.getEmplrFicaRate())))
				&& ((this.getEmplrFicaMaxTax() == castOther.getEmplrFicaMaxTax())
						|| (this.getEmplrFicaMaxTax() != null && castOther.getEmplrFicaMaxTax() != null
								&& this.getEmplrFicaMaxTax().equals(castOther.getEmplrFicaMaxTax())))
				&& ((this.getAddlMedRate() == castOther.getAddlMedRate())
						|| (this.getAddlMedRate() != null && castOther.getAddlMedRate() != null
								&& this.getAddlMedRate().equals(castOther.getAddlMedRate())))
				&& ((this.getAddlMedSalThreshold() == castOther.getAddlMedSalThreshold())
						|| (this.getAddlMedSalThreshold() != null && castOther.getAddlMedSalThreshold() != null
								&& this.getAddlMedSalThreshold().equals(castOther.getAddlMedSalThreshold())))
				&& ((this.getModule() == castOther.getModule()) || (this.getModule() != null
						&& castOther.getModule() != null && this.getModule().equals(castOther.getModule())))
				&& ((this.getHsaMaxEmplrContrib() == castOther.getHsaMaxEmplrContrib())
						|| (this.getHsaMaxEmplrContrib() != null && castOther.getHsaMaxEmplrContrib() != null
								&& this.getHsaMaxEmplrContrib().equals(castOther.getHsaMaxEmplrContrib())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getTaxYr() == null ? 0 : this.getTaxYr().hashCode());
		result = 37 * result + (getFicaRate() == null ? 0 : this.getFicaRate().hashCode());
		result = 37 * result + (getFicaMaxSalary() == null ? 0 : this.getFicaMaxSalary().hashCode());
		result = 37 * result + (getFicaMaxTax() == null ? 0 : this.getFicaMaxTax().hashCode());
		result = 37 * result + (getMedRate() == null ? 0 : this.getMedRate().hashCode());
		result = 37 * result + (getMedMaxSalary() == null ? 0 : this.getMedMaxSalary().hashCode());
		result = 37 * result + (getMedMaxTax() == null ? 0 : this.getMedMaxTax().hashCode());
		result = 37 * result + (getEicMaxSal() == null ? 0 : this.getEicMaxSal().hashCode());
		result = 37 * result + (getEmplrFicaRate() == null ? 0 : this.getEmplrFicaRate().hashCode());
		result = 37 * result + (getEmplrFicaMaxTax() == null ? 0 : this.getEmplrFicaMaxTax().hashCode());
		result = 37 * result + (getAddlMedRate() == null ? 0 : this.getAddlMedRate().hashCode());
		result = 37 * result + (getAddlMedSalThreshold() == null ? 0 : this.getAddlMedSalThreshold().hashCode());
		result = 37 * result + (getModule() == null ? 0 : this.getModule().hashCode());
		result = 37 * result + (getHsaMaxEmplrContrib() == null ? 0 : this.getHsaMaxEmplrContrib().hashCode());
		return result;
	}

}
