package com.esc20.nonDBModels;

import com.esc20.model.BhrAca1095bCovrdHist;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

public class BCoveredHistory {

	private String empNbr;
	private String calYr;
	private Byte seqNbr;
	private String nameF;
	private String nameM;
	private String nameL;
	private Character nameGen;
	private String ssn;
	private String dob;
	private Character monAll;
	private Character mon01;
	private Character mon02;
	private Character mon03;
	private Character mon04;
	private Character mon05;
	private Character mon06;
	private Character mon07;
	private Character mon08;
	private Character mon09;
	private Character mon10;
	private Character mon11;
	private Character mon12;
	
	public BCoveredHistory (BhrAca1095bCovrdHist hist) {
		this.empNbr = hist.getId().getEmpNbr();
		this.calYr = hist.getId().getCalYr();
		this.seqNbr = hist.getId().getSeqNbr();
		this.nameF = hist.getNameF();
		this.nameM = hist.getNameM();
		this.nameL = hist.getNameL();
		this.nameGen = hist.getNameGen();
		this.ssn = hist.getSsn();
		this.dob = hist.getDob();
		this.monAll = hist.getMonAll();
		this.mon01 = hist.getMon01();
		this.mon02 = hist.getMon02();
		this.mon03 = hist.getMon03();
		this.mon04 = hist.getMon04();
		this.mon05 = hist.getMon05();
		this.mon06 = hist.getMon06();
		this.mon07 = hist.getMon07();
		this.mon08 = hist.getMon08();
		this.mon09 = hist.getMon09();
		this.mon10 = hist.getMon10();
		this.mon11 = hist.getMon11();
		this.mon12 = hist.getMon12();
	}

	public String getNameF() {
		return this.nameF;
	}

	public void setNameF(String nameF) {
		this.nameF = nameF;
	}

	public String getNameM() {
		return this.nameM;
	}

	public void setNameM(String nameM) {
		this.nameM = nameM;
	}

	public String getNameL() {
		return this.nameL;
	}

	public void setNameL(String nameL) {
		this.nameL = nameL;
	}

	public Character getNameGen() {
		return this.nameGen;
	}

	public void setNameGen(Character nameGen) {
		this.nameGen = nameGen;
	}

	public String getSsn() {
		if(!StringUtil.isNullOrEmpty(this.ssn) && this.ssn.length() == 9)
			return this.ssn.substring(0,3) + "-" + this.ssn.substring(3,5) + "-" + this.ssn.substring(5,9);
		else 
			return "";
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getDob() {
		return DateUtil.formatDate(this.dob, "yyyyMMdd", "MM-dd-yyyy");
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public Character getMonAll() {
		return this.monAll;
	}

	public void setMonAll(Character monAll) {
		this.monAll = monAll;
	}

	public Character getMon01() {
		return this.mon01;
	}

	public void setMon01(Character mon01) {
		this.mon01 = mon01;
	}

	public Character getMon02() {
		return this.mon02;
	}

	public void setMon02(Character mon02) {
		this.mon02 = mon02;
	}

	public Character getMon03() {
		return this.mon03;
	}

	public void setMon03(Character mon03) {
		this.mon03 = mon03;
	}

	public Character getMon04() {
		return this.mon04;
	}

	public void setMon04(Character mon04) {
		this.mon04 = mon04;
	}

	public Character getMon05() {
		return this.mon05;
	}

	public void setMon05(Character mon05) {
		this.mon05 = mon05;
	}

	public Character getMon06() {
		return this.mon06;
	}

	public void setMon06(Character mon06) {
		this.mon06 = mon06;
	}

	public Character getMon07() {
		return this.mon07;
	}

	public void setMon07(Character mon07) {
		this.mon07 = mon07;
	}

	public Character getMon08() {
		return this.mon08;
	}

	public void setMon08(Character mon08) {
		this.mon08 = mon08;
	}

	public Character getMon09() {
		return this.mon09;
	}

	public void setMon09(Character mon09) {
		this.mon09 = mon09;
	}

	public Character getMon10() {
		return this.mon10;
	}

	public void setMon10(Character mon10) {
		this.mon10 = mon10;
	}

	public Character getMon11() {
		return this.mon11;
	}

	public void setMon11(Character mon11) {
		this.mon11 = mon11;
	}

	public Character getMon12() {
		return this.mon12;
	}

	public void setMon12(Character mon12) {
		this.mon12 = mon12;
	}

	public String getEmpNbr() {
		return empNbr;
	}

	public void setEmpNbr(String empNbr) {
		this.empNbr = empNbr;
	}

	public String getCalYr() {
		return calYr;
	}

	public void setCalYr(String calYr) {
		this.calYr = calYr;
	}

	public Byte getSeqNbr() {
		return seqNbr;
	}

	public void setSeqNbr(Byte seqNbr) {
		this.seqNbr = seqNbr;
	}
	
}
