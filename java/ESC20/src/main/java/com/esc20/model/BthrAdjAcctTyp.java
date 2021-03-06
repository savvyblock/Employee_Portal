package com.esc20.model;
// Generated Jan 4, 2019 3:50:32 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BthrAdjAcctTyp generated by hbm2java
 */
@Entity
@Table(name = "BTHR_ADJ_ACCT_TYP", schema = "rsccc", catalog = "rsccc")
public class BthrAdjAcctTyp implements java.io.Serializable {

	private char acctTyp;
	private String acctTypDescr;

	public BthrAdjAcctTyp() {
	}

	public BthrAdjAcctTyp(char acctTyp, String acctTypDescr) {
		this.acctTyp = acctTyp;
		this.acctTypDescr = acctTypDescr;
	}

	@Id

	@Column(name = "ACCT_TYP", nullable = false, length = 1)
	public char getAcctTyp() {
		return this.acctTyp;
	}

	public void setAcctTyp(char acctTyp) {
		this.acctTyp = acctTyp;
	}

	@Column(name = "ACCT_TYP_DESCR", nullable = false, length = 30)
	public String getAcctTypDescr() {
		return this.acctTypDescr;
	}

	public void setAcctTypDescr(String acctTypDescr) {
		this.acctTypDescr = acctTypDescr;
	}

}
