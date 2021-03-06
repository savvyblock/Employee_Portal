package com.esc20.model;
// Generated Jan 4, 2019 3:54:39 PM by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BthrSimHrlyDlySal generated by hbm2java
 */
@Entity
@Table(name = "BTHR_SIM_HRLY_DLY_SAL", schema = "rsccc", catalog = "rsccc")
public class BthrSimHrlyDlySal implements java.io.Serializable {

	private BthrSimHrlyDlySalId id;
	private BigDecimal amtChg;
	private BigDecimal pctChg;
	private String module;

	public BthrSimHrlyDlySal() {
	}

	public BthrSimHrlyDlySal(BthrSimHrlyDlySalId id, BigDecimal amtChg, BigDecimal pctChg, String module) {
		this.id = id;
		this.amtChg = amtChg;
		this.pctChg = pctChg;
		this.module = module;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "cyrNyrFlg", column = @Column(name = "CYR_NYR_FLG", nullable = false, length = 1)),
			@AttributeOverride(name = "simName", column = @Column(name = "SIM_NAME", nullable = false, length = 7)),
			@AttributeOverride(name = "payGrd", column = @Column(name = "PAY_GRD", nullable = false, length = 3)),
			@AttributeOverride(name = "payStp", column = @Column(name = "PAY_STP", nullable = false, length = 2)),
			@AttributeOverride(name = "sched", column = @Column(name = "SCHED", nullable = false, length = 1)) })
	public BthrSimHrlyDlySalId getId() {
		return this.id;
	}

	public void setId(BthrSimHrlyDlySalId id) {
		this.id = id;
	}

	@Column(name = "AMT_CHG", nullable = false, precision = 6, scale = 3)
	public BigDecimal getAmtChg() {
		return this.amtChg;
	}

	public void setAmtChg(BigDecimal amtChg) {
		this.amtChg = amtChg;
	}

	@Column(name = "PCT_CHG", nullable = false, precision = 5, scale = 4)
	public BigDecimal getPctChg() {
		return this.pctChg;
	}

	public void setPctChg(BigDecimal pctChg) {
		this.pctChg = pctChg;
	}

	@Column(name = "MODULE", nullable = false, length = 25)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

}
