package net.esc20.txeis.EmployeeAccess.domain;

public class EA1095BInfoCommand {

	private String activeTab = "1";

	// Elements of BHR_ACA_EMP_1095B table 
	private String covrgTyp = "";
	private String cyrNyrFlg = "";
	private String empNbr = "";
	private String calYr = "";

	// Elements of BHR_ACA_COVRD_1095B Table
	private String payFreq = "";
	private String seqNbr = "";
	private String fName = " ";
	private String mName = "";
	private String lName = "";
	private String nameGen = "";
	private String nameGenDescr = "";
	private String ssn = "";
	private String dob = "";
	private boolean allMon = false;
	private boolean jan = false;
	private boolean feb = false;
	private boolean mar = false;
	private boolean apr = false;
	private boolean may = false;
	private boolean jun = false;
	private boolean jul = false;
	private boolean aug = false;
	private boolean sep = false;
	private boolean oct = false;
	private boolean nov = false;
	private boolean dec = false;

	private boolean deleteRow = false;
	private boolean origDeleteRow = false;
	private boolean newRow = true;
	private boolean readonly = false;
	private boolean selected = false;
	private int selectedRow = 0;
	private boolean hasChanged = false;

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public String getCovrgTyp() {
		return covrgTyp;
	}

	public void setCovrgTyp(String covrgTyp) {
		this.covrgTyp = covrgTyp;
	}

	public String getCyrNyrFlg() {
		return cyrNyrFlg;
	}

	public void setCyrNyrFlg(String cyrNyrFlg) {
		this.cyrNyrFlg = cyrNyrFlg;
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

	public String getPayFreq() {
		return payFreq;
	}

	public void setPayFreq(String payFreq) {
		this.payFreq = payFreq;
	}

	public String getSeqNbr() {
		return seqNbr;
	}

	public void setSeqNbr(String seqNbr) {
		this.seqNbr = seqNbr;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getNameGen() {
		return nameGen;
	}

	public void setNameGen(String nameGen) {
		this.nameGen = nameGen;
	}

	public String getNameGenDescr() {
		return nameGenDescr;
	}

	public void setNameGenDescr(String nameGenDescr) {
		this.nameGenDescr = nameGenDescr;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public boolean isAllMon() {
		return allMon;
	}

	public void setAllMon(boolean allMon) {
		this.allMon = allMon;
	}

	public boolean isJan() {
		return jan;
	}

	public void setJan(boolean jan) {
		this.jan = jan;
	}

	public boolean isFeb() {
		return feb;
	}

	public void setFeb(boolean feb) {
		this.feb = feb;
	}

	public boolean isMar() {
		return mar;
	}

	public void setMar(boolean mar) {
		this.mar = mar;
	}

	public boolean isApr() {
		return apr;
	}

	public void setApr(boolean apr) {
		this.apr = apr;
	}

	public boolean isMay() {
		return may;
	}

	public void setMay(boolean may) {
		this.may = may;
	}

	public boolean isJun() {
		return jun;
	}

	public void setJun(boolean jun) {
		this.jun = jun;
	}

	public boolean isJul() {
		return jul;
	}

	public void setJul(boolean jul) {
		this.jul = jul;
	}

	public boolean isAug() {
		return aug;
	}

	public void setAug(boolean aug) {
		this.aug = aug;
	}

	public boolean isSep() {
		return sep;
	}

	public void setSep(boolean sep) {
		this.sep = sep;
	}

	public boolean isOct() {
		return oct;
	}

	public void setOct(boolean oct) {
		this.oct = oct;
	}

	public boolean isNov() {
		return nov;
	}

	public void setNov(boolean nov) {
		this.nov = nov;
	}

	public boolean isDec() {
		return dec;
	}

	public void setDec(boolean dec) {
		this.dec = dec;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public boolean isDeleteRow() {
		return deleteRow;
	}

	public void setDeleteRow(boolean deleteRow) {
		this.deleteRow = deleteRow;
	}

	public boolean isNewRow() {
		return newRow;
	}

	public void setNewRow(boolean newRow) {
		this.newRow = newRow;
	}

	public boolean isOrigDeleteRow() {
		return origDeleteRow;
	}

	public void setOrigDeleteRow(boolean origDeleteRow) {
		this.origDeleteRow = origDeleteRow;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(int selectedRow) {
		this.selectedRow = selectedRow;
	}

	public boolean isHasChanged() {
		return hasChanged;
	}

	public void setHasChanged(boolean hasChanged) {
		this.hasChanged = hasChanged;
	}
}