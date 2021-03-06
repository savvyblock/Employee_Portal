package com.esc20.model;
// Generated Jan 4, 2019 3:54:39 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BthrStndGrossFlg generated by hbm2java
 */
@Entity
@Table(name = "BTHR_STND_GROSS_FLG", schema = "rsccc", catalog = "rsccc")
public class BthrStndGrossFlg implements java.io.Serializable {

	private char flag;
	private String descr;

	public BthrStndGrossFlg() {
	}

	public BthrStndGrossFlg(char flag, String descr) {
		this.flag = flag;
		this.descr = descr;
	}

	@Id

	@Column(name = "FLAG", nullable = false, length = 1)
	public char getFlag() {
		return this.flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

	@Column(name = "DESCR", nullable = false, length = 50)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

}
