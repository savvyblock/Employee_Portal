package com.esc20.model;
// Generated Jan 4, 2019 3:46:11 PM by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BhrServRec generated by hbm2java
 */
@Entity
@Table(name = "BHR_SERV_REC", schema = "rsccc", catalog = "rsccc")
public class BhrServRec implements java.io.Serializable {
	private static final long serialVersionUID = 6715142900654758429L;
	private BhrServRecId id;
	private String posHeld;
	private String grdsTaught;
	private String payStep;
	private String yrsExp;
	private short pctOfDayEmpld;
	private BigDecimal nbrDaysEmpld;
	private String servDtFrom;
	private String servDtTo;
	private BigDecimal stSickLvBalPriorYr;
	private BigDecimal stSickLvEarned;
	private BigDecimal stSickLvUsed;
	private BigDecimal stSickLvBalEoy;
	private BigDecimal stPersLvBalPriorYr;
	private BigDecimal stPersLvEarned;
	private BigDecimal stPersLvUsed;
	private BigDecimal stPersLvBalEoy;
	private String notes;
	private String module;
	private String distTyp;
	private char fullSem;

	public BhrServRec() {
	}

	public BhrServRec(BhrServRecId id, String posHeld, String grdsTaught, String payStep, String yrsExp,
			short pctOfDayEmpld, BigDecimal nbrDaysEmpld, String servDtFrom, String servDtTo,
			BigDecimal stSickLvBalPriorYr, BigDecimal stSickLvEarned, BigDecimal stSickLvUsed,
			BigDecimal stSickLvBalEoy, BigDecimal stPersLvBalPriorYr, BigDecimal stPersLvEarned,
			BigDecimal stPersLvUsed, BigDecimal stPersLvBalEoy, String notes, String module, String distTyp,
			char fullSem) {
		this.id = id;
		this.posHeld = posHeld;
		this.grdsTaught = grdsTaught;
		this.payStep = payStep;
		this.yrsExp = yrsExp;
		this.pctOfDayEmpld = pctOfDayEmpld;
		this.nbrDaysEmpld = nbrDaysEmpld;
		this.servDtFrom = servDtFrom;
		this.servDtTo = servDtTo;
		this.stSickLvBalPriorYr = stSickLvBalPriorYr;
		this.stSickLvEarned = stSickLvEarned;
		this.stSickLvUsed = stSickLvUsed;
		this.stSickLvBalEoy = stSickLvBalEoy;
		this.stPersLvBalPriorYr = stPersLvBalPriorYr;
		this.stPersLvEarned = stPersLvEarned;
		this.stPersLvUsed = stPersLvUsed;
		this.stPersLvBalEoy = stPersLvBalEoy;
		this.notes = notes;
		this.module = module;
		this.distTyp = distTyp;
		this.fullSem = fullSem;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "empNbr", column = @Column(name = "EMP_NBR", nullable = false, length = 6)),
			@AttributeOverride(name = "schYr", column = @Column(name = "SCH_YR", nullable = false, length = 4)),
			@AttributeOverride(name = "dts", column = @Column(name = "DTS", nullable = false, length = 16)) })
	public BhrServRecId getId() {
		return this.id;
	}

	public void setId(BhrServRecId id) {
		this.id = id;
	}

	@Column(name = "POS_HELD", nullable = false, length = 30)
	public String getPosHeld() {
		return this.posHeld;
	}

	public void setPosHeld(String posHeld) {
		this.posHeld = posHeld;
	}

	@Column(name = "GRDS_TAUGHT", nullable = false, length = 7)
	public String getGrdsTaught() {
		return this.grdsTaught;
	}

	public void setGrdsTaught(String grdsTaught) {
		this.grdsTaught = grdsTaught;
	}

	@Column(name = "PAY_STEP", nullable = false, length = 2)
	public String getPayStep() {
		return this.payStep;
	}

	public void setPayStep(String payStep) {
		this.payStep = payStep;
	}

	@Column(name = "YRS_EXP", nullable = false, length = 2)
	public String getYrsExp() {
		return this.yrsExp;
	}

	public void setYrsExp(String yrsExp) {
		this.yrsExp = yrsExp;
	}

	@Column(name = "PCT_OF_DAY_EMPLD", nullable = false, precision = 3, scale = 0)
	public short getPctOfDayEmpld() {
		return this.pctOfDayEmpld;
	}

	public void setPctOfDayEmpld(short pctOfDayEmpld) {
		this.pctOfDayEmpld = pctOfDayEmpld;
	}

	@Column(name = "NBR_DAYS_EMPLD", nullable = false, precision = 5)
	public BigDecimal getNbrDaysEmpld() {
		return this.nbrDaysEmpld;
	}

	public void setNbrDaysEmpld(BigDecimal nbrDaysEmpld) {
		this.nbrDaysEmpld = nbrDaysEmpld;
	}

	@Column(name = "SERV_DT_FROM", nullable = false, length = 8)
	public String getServDtFrom() {
		return this.servDtFrom;
	}

	public void setServDtFrom(String servDtFrom) {
		this.servDtFrom = servDtFrom;
	}

	@Column(name = "SERV_DT_TO", nullable = false, length = 8)
	public String getServDtTo() {
		return this.servDtTo;
	}

	public void setServDtTo(String servDtTo) {
		this.servDtTo = servDtTo;
	}

	@Column(name = "ST_SICK_LV_BAL_PRIOR_YR", nullable = false, precision = 7)
	public BigDecimal getStSickLvBalPriorYr() {
		return this.stSickLvBalPriorYr;
	}

	public void setStSickLvBalPriorYr(BigDecimal stSickLvBalPriorYr) {
		this.stSickLvBalPriorYr = stSickLvBalPriorYr;
	}

	@Column(name = "ST_SICK_LV_EARNED", nullable = false, precision = 7)
	public BigDecimal getStSickLvEarned() {
		return this.stSickLvEarned;
	}

	public void setStSickLvEarned(BigDecimal stSickLvEarned) {
		this.stSickLvEarned = stSickLvEarned;
	}

	@Column(name = "ST_SICK_LV_USED", nullable = false, precision = 7)
	public BigDecimal getStSickLvUsed() {
		return this.stSickLvUsed;
	}

	public void setStSickLvUsed(BigDecimal stSickLvUsed) {
		this.stSickLvUsed = stSickLvUsed;
	}

	@Column(name = "ST_SICK_LV_BAL_EOY", nullable = false, precision = 7)
	public BigDecimal getStSickLvBalEoy() {
		return this.stSickLvBalEoy;
	}

	public void setStSickLvBalEoy(BigDecimal stSickLvBalEoy) {
		this.stSickLvBalEoy = stSickLvBalEoy;
	}

	@Column(name = "ST_PERS_LV_BAL_PRIOR_YR", nullable = false, precision = 7)
	public BigDecimal getStPersLvBalPriorYr() {
		return this.stPersLvBalPriorYr;
	}

	public void setStPersLvBalPriorYr(BigDecimal stPersLvBalPriorYr) {
		this.stPersLvBalPriorYr = stPersLvBalPriorYr;
	}

	@Column(name = "ST_PERS_LV_EARNED", nullable = false, precision = 7)
	public BigDecimal getStPersLvEarned() {
		return this.stPersLvEarned;
	}

	public void setStPersLvEarned(BigDecimal stPersLvEarned) {
		this.stPersLvEarned = stPersLvEarned;
	}

	@Column(name = "ST_PERS_LV_USED", nullable = false, precision = 7)
	public BigDecimal getStPersLvUsed() {
		return this.stPersLvUsed;
	}

	public void setStPersLvUsed(BigDecimal stPersLvUsed) {
		this.stPersLvUsed = stPersLvUsed;
	}

	@Column(name = "ST_PERS_LV_BAL_EOY", nullable = false, precision = 7)
	public BigDecimal getStPersLvBalEoy() {
		return this.stPersLvBalEoy;
	}

	public void setStPersLvBalEoy(BigDecimal stPersLvBalEoy) {
		this.stPersLvBalEoy = stPersLvBalEoy;
	}

	@Column(name = "NOTES", nullable = false, length = 200)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "MODULE", nullable = false, length = 25)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Column(name = "DIST_TYP", nullable = false, length = 7)
	public String getDistTyp() {
		return this.distTyp;
	}

	public void setDistTyp(String distTyp) {
		this.distTyp = distTyp;
	}

	@Column(name = "FULL_SEM", nullable = false, length = 1)
	public char getFullSem() {
		return this.fullSem;
	}

	public void setFullSem(char fullSem) {
		this.fullSem = fullSem;
	}

}
