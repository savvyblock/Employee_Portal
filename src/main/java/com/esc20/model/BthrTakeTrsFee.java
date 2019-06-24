package com.esc20.model;
// Generated Jan 4, 2019 3:54:58 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BthrTakeTrsFee generated by hbm2java
 */
@Entity
@Table(name = "BTHR_TAKE_TRS_FEE", schema = "rsccc", catalog = "rsccc")
public class BthrTakeTrsFee implements java.io.Serializable {

	private char code;
	private String descr;

	public BthrTakeTrsFee() {
	}

	public BthrTakeTrsFee(char code, String descr) {
		this.code = code;
		this.descr = descr;
	}

	@Id

	@Column(name = "CODE", nullable = false, length = 1)
	public char getCode() {
		return this.code;
	}

	public void setCode(char code) {
		this.code = code;
	}

	@Column(name = "DESCR", nullable = false, length = 60)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

}