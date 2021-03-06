package com.esc20.model;
// Generated Jan 4, 2019 3:38:35 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BhrCalSchBkupPaycalcs5 generated by hbm2java
 */
@Entity
@Table(name = "BHR_CAL_SCH_BKUP_PAYCALCS_5", schema = "rsccc", catalog = "rsccc")
public class BhrCalSchBkupPaycalcs5 implements java.io.Serializable {
	private static final long serialVersionUID = 6715142900654758429L;
	private BhrCalSchBkupPaycalcs5Id id;

	public BhrCalSchBkupPaycalcs5() {
	}

	public BhrCalSchBkupPaycalcs5(BhrCalSchBkupPaycalcs5Id id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "payFreq", column = @Column(name = "PAY_FREQ", nullable = false, length = 1)),
			@AttributeOverride(name = "calCd", column = @Column(name = "CAL_CD", nullable = false, length = 2)),
			@AttributeOverride(name = "calDescr", column = @Column(name = "CAL_DESCR", nullable = false, length = 20)),
			@AttributeOverride(name = "module", column = @Column(name = "MODULE", nullable = false, length = 25)) })
	public BhrCalSchBkupPaycalcs5Id getId() {
		return this.id;
	}

	public void setId(BhrCalSchBkupPaycalcs5Id id) {
		this.id = id;
	}

}
