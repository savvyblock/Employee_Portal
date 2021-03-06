package com.esc20.model;
// Generated Jan 4, 2019 3:31:47 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BhrAca1094cOthrAle generated by hbm2java
 */
@Entity
@Table(name = "BHR_ACA_1094C_OTHR_ALE", schema = "rsccc", catalog = "rsccc")
public class BhrAca1094cOthrAle implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8843373105489847258L;
	private BhrAca1094cOthrAleId id;
	private String mbrName;
	private String module;

	public BhrAca1094cOthrAle() {
	}

	public BhrAca1094cOthrAle(BhrAca1094cOthrAleId id, String mbrName, String module) {
		this.id = id;
		this.mbrName = mbrName;
		this.module = module;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "calYr", column = @Column(name = "CAL_YR", nullable = false, length = 4)),
			@AttributeOverride(name = "ein", column = @Column(name = "EIN", nullable = false, length = 9)) })
	public BhrAca1094cOthrAleId getId() {
		return this.id;
	}

	public void setId(BhrAca1094cOthrAleId id) {
		this.id = id;
	}

	@Column(name = "MBR_NAME", nullable = false, length = 75)
	public String getMbrName() {
		return this.mbrName;
	}

	public void setMbrName(String mbrName) {
		this.mbrName = mbrName;
	}

	@Column(name = "MODULE", nullable = false, length = 25)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

}
