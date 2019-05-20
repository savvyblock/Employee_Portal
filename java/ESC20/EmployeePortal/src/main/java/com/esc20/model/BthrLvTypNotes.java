package com.esc20.model;
// Generated Jan 4, 2019 3:52:00 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * BthrLvTypNotes generated by hbm2java
 */
@Entity
@Table(name = "BTHR_LV_TYP_NOTES", schema = "rsccc", catalog = "rsccc")
public class BthrLvTypNotes implements java.io.Serializable {

	private String lvTyp;
	private BthrLvTypDescr bthrLvTypDescr;
	private String notes;
	private String module;

	public BthrLvTypNotes() {
	}

	public BthrLvTypNotes(BthrLvTypDescr bthrLvTypDescr, String notes, String module) {
		this.bthrLvTypDescr = bthrLvTypDescr;
		this.notes = notes;
		this.module = module;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "bthrLvTypDescr"))
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "LV_TYP", nullable = false, length = 2)
	public String getLvTyp() {
		return this.lvTyp;
	}

	public void setLvTyp(String lvTyp) {
		this.lvTyp = lvTyp;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public BthrLvTypDescr getBthrLvTypDescr() {
		return this.bthrLvTypDescr;
	}

	public void setBthrLvTypDescr(BthrLvTypDescr bthrLvTypDescr) {
		this.bthrLvTypDescr = bthrLvTypDescr;
	}

	@Column(name = "NOTES", nullable = false, length = 250)
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

}