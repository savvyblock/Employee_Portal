package com.esc20.model;
// Generated Jan 4, 2019 3:51:33 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BthrGrpTermLifeSec79RateBkupPaycalcs5 generated by hbm2java
 */
@Entity
@Table(name = "BTHR_GRP_TERM_LIFE_SEC_79_RATE_BKUP_PAYCALCS_5", schema = "rsccc", catalog = "rsccc")
public class BthrGrpTermLifeSec79RateBkupPaycalcs5 implements java.io.Serializable {

	private BthrGrpTermLifeSec79RateBkupPaycalcs5Id id;

	public BthrGrpTermLifeSec79RateBkupPaycalcs5() {
	}

	public BthrGrpTermLifeSec79RateBkupPaycalcs5(BthrGrpTermLifeSec79RateBkupPaycalcs5Id id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "taxYr", column = @Column(name = "TAX_YR", nullable = false, length = 4)),
			@AttributeOverride(name = "ageFrom", column = @Column(name = "AGE_FROM", nullable = false, precision = 3, scale = 0)),
			@AttributeOverride(name = "ageTo", column = @Column(name = "AGE_TO", nullable = false, precision = 3, scale = 0)),
			@AttributeOverride(name = "cost", column = @Column(name = "COST", nullable = false, precision = 4)),
			@AttributeOverride(name = "module", column = @Column(name = "MODULE", nullable = false, length = 25)) })
	public BthrGrpTermLifeSec79RateBkupPaycalcs5Id getId() {
		return this.id;
	}

	public void setId(BthrGrpTermLifeSec79RateBkupPaycalcs5Id id) {
		this.id = id;
	}

}
