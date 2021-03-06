package com.esc20.model;
// Generated Jan 4, 2019 3:52:00 PM by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * BthrLvTyp generated by hbm2java
 */
@Entity
@Table(name = "BTHR_LV_TYP", schema = "rsccc", catalog = "rsccc")
public class BthrLvTyp implements java.io.Serializable {

	private BthrLvTypId id;
	private BthrLvTypDescr bthrLvTypDescr;
	private char chkStubPos;
	private int maxBal;
	private String daysHrs;
	private char dockWithBal;
	private char addSubtractBal;
	private String module;
	private char stat;
	private Set<BthrLvSeq> bthrLvSeqsForFkBthrLvRefLvSeqTyp = new HashSet<BthrLvSeq>(0);
	private Set<BthrLvSeq> bthrLvSeqsForFkBthrLvRefBwdLv = new HashSet<BthrLvSeq>(0);
	private Set<BthrLvSeq> bthrLvSeqsForFkBthrLvRefBthrLvTyp = new HashSet<BthrLvSeq>(0);
	private Set<BthrLvRates> bthrLvRateses = new HashSet<BthrLvRates>(0);

	public BthrLvTyp() {
	}

	public BthrLvTyp(BthrLvTypId id, BthrLvTypDescr bthrLvTypDescr, char chkStubPos, int maxBal, String daysHrs,
			char dockWithBal, char addSubtractBal, String module, char stat) {
		this.id = id;
		this.bthrLvTypDescr = bthrLvTypDescr;
		this.chkStubPos = chkStubPos;
		this.maxBal = maxBal;
		this.daysHrs = daysHrs;
		this.dockWithBal = dockWithBal;
		this.addSubtractBal = addSubtractBal;
		this.module = module;
		this.stat = stat;
	}

	public BthrLvTyp(BthrLvTypId id, BthrLvTypDescr bthrLvTypDescr, char chkStubPos, int maxBal, String daysHrs,
			char dockWithBal, char addSubtractBal, String module, char stat,
			Set<BthrLvSeq> bthrLvSeqsForFkBthrLvRefLvSeqTyp, Set<BthrLvSeq> bthrLvSeqsForFkBthrLvRefBwdLv,
			Set<BthrLvSeq> bthrLvSeqsForFkBthrLvRefBthrLvTyp, Set<BthrLvRates> bthrLvRateses) {
		this.id = id;
		this.bthrLvTypDescr = bthrLvTypDescr;
		this.chkStubPos = chkStubPos;
		this.maxBal = maxBal;
		this.daysHrs = daysHrs;
		this.dockWithBal = dockWithBal;
		this.addSubtractBal = addSubtractBal;
		this.module = module;
		this.stat = stat;
		this.bthrLvSeqsForFkBthrLvRefLvSeqTyp = bthrLvSeqsForFkBthrLvRefLvSeqTyp;
		this.bthrLvSeqsForFkBthrLvRefBwdLv = bthrLvSeqsForFkBthrLvRefBwdLv;
		this.bthrLvSeqsForFkBthrLvRefBthrLvTyp = bthrLvSeqsForFkBthrLvRefBthrLvTyp;
		this.bthrLvRateses = bthrLvRateses;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "lvTyp", column = @Column(name = "LV_TYP", nullable = false, length = 2)),
			@AttributeOverride(name = "payFreq", column = @Column(name = "PAY_FREQ", nullable = false, length = 1)) })
	public BthrLvTypId getId() {
		return this.id;
	}

	public void setId(BthrLvTypId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LV_TYP", nullable = false, insertable = false, updatable = false)
	public BthrLvTypDescr getBthrLvTypDescr() {
		return this.bthrLvTypDescr;
	}

	public void setBthrLvTypDescr(BthrLvTypDescr bthrLvTypDescr) {
		this.bthrLvTypDescr = bthrLvTypDescr;
	}

	@Column(name = "CHK_STUB_POS", nullable = false, length = 1)
	public char getChkStubPos() {
		return this.chkStubPos;
	}

	public void setChkStubPos(char chkStubPos) {
		this.chkStubPos = chkStubPos;
	}

	@Column(name = "MAX_BAL", nullable = false, precision = 5, scale = 0)
	public int getMaxBal() {
		return this.maxBal;
	}

	public void setMaxBal(int maxBal) {
		this.maxBal = maxBal;
	}

	@Column(name = "DAYS_HRS", nullable = false, length = 3)
	public String getDaysHrs() {
		return this.daysHrs;
	}

	public void setDaysHrs(String daysHrs) {
		this.daysHrs = daysHrs;
	}

	@Column(name = "DOCK_WITH_BAL", nullable = false, length = 1)
	public char getDockWithBal() {
		return this.dockWithBal;
	}

	public void setDockWithBal(char dockWithBal) {
		this.dockWithBal = dockWithBal;
	}

	@Column(name = "ADD_SUBTRACT_BAL", nullable = false, length = 1)
	public char getAddSubtractBal() {
		return this.addSubtractBal;
	}

	public void setAddSubtractBal(char addSubtractBal) {
		this.addSubtractBal = addSubtractBal;
	}

	@Column(name = "MODULE", nullable = false, length = 25)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Column(name = "STAT", nullable = false, length = 1)
	public char getStat() {
		return this.stat;
	}

	public void setStat(char stat) {
		this.stat = stat;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bthrLvTypByFkBthrLvRefLvSeqTyp")
	public Set<BthrLvSeq> getBthrLvSeqsForFkBthrLvRefLvSeqTyp() {
		return this.bthrLvSeqsForFkBthrLvRefLvSeqTyp;
	}

	public void setBthrLvSeqsForFkBthrLvRefLvSeqTyp(Set<BthrLvSeq> bthrLvSeqsForFkBthrLvRefLvSeqTyp) {
		this.bthrLvSeqsForFkBthrLvRefLvSeqTyp = bthrLvSeqsForFkBthrLvRefLvSeqTyp;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bthrLvTypByFkBthrLvRefBwdLv")
	public Set<BthrLvSeq> getBthrLvSeqsForFkBthrLvRefBwdLv() {
		return this.bthrLvSeqsForFkBthrLvRefBwdLv;
	}

	public void setBthrLvSeqsForFkBthrLvRefBwdLv(Set<BthrLvSeq> bthrLvSeqsForFkBthrLvRefBwdLv) {
		this.bthrLvSeqsForFkBthrLvRefBwdLv = bthrLvSeqsForFkBthrLvRefBwdLv;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bthrLvTypByFkBthrLvRefBthrLvTyp")
	public Set<BthrLvSeq> getBthrLvSeqsForFkBthrLvRefBthrLvTyp() {
		return this.bthrLvSeqsForFkBthrLvRefBthrLvTyp;
	}

	public void setBthrLvSeqsForFkBthrLvRefBthrLvTyp(Set<BthrLvSeq> bthrLvSeqsForFkBthrLvRefBthrLvTyp) {
		this.bthrLvSeqsForFkBthrLvRefBthrLvTyp = bthrLvSeqsForFkBthrLvRefBthrLvTyp;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bthrLvTyp")
	public Set<BthrLvRates> getBthrLvRateses() {
		return this.bthrLvRateses;
	}

	public void setBthrLvRateses(Set<BthrLvRates> bthrLvRateses) {
		this.bthrLvRateses = bthrLvRateses;
	}

}
