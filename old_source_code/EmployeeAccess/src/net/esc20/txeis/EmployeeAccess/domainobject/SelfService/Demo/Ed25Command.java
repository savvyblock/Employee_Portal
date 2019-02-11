package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import org.springframework.stereotype.Component;

@Component
public class Ed25Command {

	// Active Tab
	private String activeTab = "1";

	private String rptYr = "";
	private String rptMon = "";
	private String empNbr = "";
	private String empName = "";
	private String origStaffID = "";
	private String origDOB = "";
	private String origSex = "";
	private String origNameF = "";
	private String origNameM = "";
	private String origNameL = "";
	private String origNameGen = "";
	private String newStaffID = "";
	private String newDOB = "";
	private String newSex = "";
	private String newNameF = "";
	private String newNameM = "";
	private String newNameL = "";
	private String newNameGen = "";
	private String newAddrNbr = "";
	private String newAddrStr = "";
	private String newAddrApt = "";
	private String newAddrCity = "";
	private String newAddrSt = "";
	private String newAddrZip = "";
	private String newAddrZip4 = "";
	private String newAddrProvince = "";
	private String newAddrCtry = "";
	private String newPostalCD = "";
	private String newEmail = "";
	private String newPhoneArea = "";
	private String newPhoneNbr = "";
	private String module = "";
	
	private boolean newRow = false; 

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public String getRptYr() {
		return rptYr;
	}

	public void setRptYr(String rptYr) {
		this.rptYr = rptYr;
	}

	public String getRptMon() {
		return rptMon;
	}

	public void setRptMon(String rptMon) {
		this.rptMon = rptMon;
	}

	public String getEmpNbr() {
		return empNbr;
	}

	public void setEmpNbr(String empNbr) {
		this.empNbr = empNbr;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getOrigStaffID() {
		return origStaffID;
	}

	public void setOrigStaffID(String origStaffID) {
		this.origStaffID = origStaffID;
	}

	public String getOrigDOB() {
		return origDOB;
	}

	public void setOrigDOB(String origDOB) {
		this.origDOB = origDOB;
	}

	public String getOrigSex() {
		return origSex;
	}

	public void setOrigSex(String origSex) {
		this.origSex = origSex;
	}

	public String getOrigNameF() {
		return origNameF;
	}

	public void setOrigNameF(String origNameF) {
		this.origNameF = origNameF;
	}

	public String getOrigNameM() {
		return origNameM;
	}

	public void setOrigNameM(String origNameM) {
		this.origNameM = origNameM;
	}

	public String getOrigNameL() {
		return origNameL;
	}

	public void setOrigNameL(String origNameL) {
		this.origNameL = origNameL;
	}

	public String getOrigNameGen() {
		return origNameGen;
	}

	public void setOrigNameGen(String origNameGen) {
		this.origNameGen = origNameGen;
	}

	public String getNewStaffID() {
		return newStaffID;
	}

	public void setNewStaffID(String newStaffID) {
		this.newStaffID = newStaffID;
	}

	public String getNewDOB() {
		return newDOB;
	}

	public void setNewDOB(String newDOB) {
		this.newDOB = newDOB;
	}

	public String getNewSex() {
		return newSex;
	}

	public void setNewSex(String newSex) {
		this.newSex = newSex;
	}

	public String getNewNameF() {
		return newNameF;
	}

	public void setNewNameF(String newNameF) {
		this.newNameF = newNameF;
	}

	public String getNewNameM() {
		return newNameM;
	}

	public void setNewNameM(String newNameM) {
		this.newNameM = newNameM;
	}

	public String getNewNameL() {
		return newNameL;
	}

	public void setNewNameL(String newNameL) {
		this.newNameL = newNameL;
	}

	public String getNewNameGen() {
		return newNameGen;
	}

	public void setNewNameGen(String newNameGen) {
		this.newNameGen = newNameGen;
	}

	public String getNewAddrNbr() {
		return newAddrNbr;
	}

	public void setNewAddrNbr(String newAddrNbr) {
		this.newAddrNbr = newAddrNbr;
	}

	public String getNewAddrStr() {
		return newAddrStr;
	}

	public void setNewAddrStr(String newAddrStr) {
		this.newAddrStr = newAddrStr;
	}

	public String getNewAddrApt() {
		return newAddrApt;
	}

	public void setNewAddrApt(String newAddrApt) {
		this.newAddrApt = newAddrApt;
	}

	public String getNewAddrCity() {
		return newAddrCity;
	}

	public void setNewAddrCity(String newAddrCity) {
		this.newAddrCity = newAddrCity;
	}

	public String getNewAddrSt() {
		return newAddrSt;
	}

	public void setNewAddrSt(String newAddrSt) {
		this.newAddrSt = newAddrSt;
	}

	public String getNewAddrZip() {
		return newAddrZip;
	}

	public void setNewAddrZip(String newAddrZip) {
		this.newAddrZip = newAddrZip;
	}

	public String getNewAddrZip4() {
		return newAddrZip4;
	}

	public void setNewAddrZip4(String newAddrZip4) {
		this.newAddrZip4 = newAddrZip4;
	}

	public String getNewAddrProvince() {
		return newAddrProvince;
	}

	public void setNewAddrProvince(String newAddrProvince) {
		this.newAddrProvince = newAddrProvince;
	}

	public String getNewAddrCtry() {
		return newAddrCtry;
	}

	public void setNewAddrCtry(String newAddrCtry) {
		this.newAddrCtry = newAddrCtry;
	}

	public String getNewPostalCD() {
		return newPostalCD;
	}

	public void setNewPostalCD(String newPostalCD) {
		this.newPostalCD = newPostalCD;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public String getNewPhoneArea() {
		return newPhoneArea;
	}

	public void setNewPhoneArea(String newPhoneArea) {
		this.newPhoneArea = newPhoneArea;
	}

	public String getNewPhoneNbr() {
		return newPhoneNbr;
	}

	public void setNewPhoneNbr(String newPhoneNbr) {
		this.newPhoneNbr = newPhoneNbr;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public boolean isNewRow() {
		return newRow;
	}

	public void setNewRow(boolean newRow) {
		this.newRow = newRow;
	}

}
