package com.esc20.model;
// Generated Jan 4, 2019 3:42:57 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BhrHlthInsStat generated by hbm2java
 */
@Entity
@Table(name = "BHR_HLTH_INS_STAT", schema = "rsccc", catalog = "rsccc")
public class BhrHlthInsStat implements java.io.Serializable {
	private static final long serialVersionUID = 6715142900654758429L;
	private BhrHlthInsStatId id;
	private char actnStat;
	private String module;

	public BhrHlthInsStat() {
	}

	public BhrHlthInsStat(BhrHlthInsStatId id, char actnStat, String module) {
		this.id = id;
		this.actnStat = actnStat;
		this.module = module;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "empNbr", column = @Column(name = "EMP_NBR", nullable = false, length = 6)),
			@AttributeOverride(name = "offrDt", column = @Column(name = "OFFR_DT", nullable = false, length = 8)) })
	public BhrHlthInsStatId getId() {
		return this.id;
	}

	public void setId(BhrHlthInsStatId id) {
		this.id = id;
	}

	@Column(name = "ACTN_STAT", nullable = false, length = 1)
	public char getActnStat() {
		return this.actnStat;
	}

	public void setActnStat(char actnStat) {
		this.actnStat = actnStat;
	}

	@Column(name = "MODULE", nullable = false, length = 25)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

}
