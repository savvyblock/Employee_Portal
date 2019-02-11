package net.esc20.txeis.EmployeeAccess.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.domainobject.District;
import net.esc20.txeis.EmployeeAccess.domainobject.PaymentInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.common.util.Page;

public class EA1095InfoCommand extends PaymentInfo implements Serializable{

	private static final long serialVersionUID = -2527433873958761311L;

	// Active Tab
	private String activeTab = "1";
	private String currentUser = "";
	private String firstName = "";
	private String lastName = "";
	private String workEmail = "";
	private String homeEmail = "";
	private String ea1095ElecConsnt = "";
	private String ea1095ElecConsntMsg = "";
	private boolean email;
	private boolean showEA1095ElecConsntPopup;
	private boolean showSuccessMsg;
	private boolean enableElecConsnt1095;

	private String empNbr = "";
	private List<String> calYrs = new ArrayList<String>();
	private String latestYr = "";
	private String currentYr = "";
	private String msg1095 = "";
	private String ea1095 = "";
	private String disableEa1095B = "";
	private String disableEa1095C = "";

	private String covrgTyp = "";
	private String covrgDescr = "";
	private boolean selfIns = false;
	private String planStrtMon = "";

	private Map<String, String> calMonMap = new HashMap<String, String>();

	private User eaUser = new User();
	private District eaDistrict = new District();
	private District shopDistrict = new District();

	private Page<EA1095BInfoCommand> ea1095BData = new Page<EA1095BInfoCommand>();
	private List<EA1095BInfoCommand> ea1095BCovrg = new ArrayList<EA1095BInfoCommand>();
	private List<EA1095CInfoCommand> ea1095CEmpData = new ArrayList<EA1095CInfoCommand>();
	private Page<EA1095CInfoCommand> ea1095CCovrgData = new Page<EA1095CInfoCommand>();
	private List<EA1095CInfoCommand> ea1095CCovrg = new ArrayList<EA1095CInfoCommand>();

	private EA1095CInfoCommand ea1095CCalMonAll = new EA1095CInfoCommand();
	private EA1095CInfoCommand ea1095CCalMonJan = new EA1095CInfoCommand();
	private EA1095CInfoCommand ea1095CCalMonFeb = new EA1095CInfoCommand();
	private EA1095CInfoCommand ea1095CCalMonMar = new EA1095CInfoCommand();
	private EA1095CInfoCommand ea1095CCalMonApr = new EA1095CInfoCommand();
	private EA1095CInfoCommand ea1095CCalMonMay = new EA1095CInfoCommand();
	private EA1095CInfoCommand ea1095CCalMonJun = new EA1095CInfoCommand();
	private EA1095CInfoCommand ea1095CCalMonJul = new EA1095CInfoCommand();
	private EA1095CInfoCommand ea1095CCalMonAug = new EA1095CInfoCommand();
	private EA1095CInfoCommand ea1095CCalMonSep = new EA1095CInfoCommand();
	private EA1095CInfoCommand ea1095CCalMonOct = new EA1095CInfoCommand();
	private EA1095CInfoCommand ea1095CCalMonNov = new EA1095CInfoCommand();
	private EA1095CInfoCommand ea1095CCalMonDec = new EA1095CInfoCommand();

	// Pagination attributes
	protected Integer currentPage1095B = 1;
	protected Integer totalPages1095B = null;
	protected Integer selectedPage1095B = null;
	protected Integer pageSize1095B = 19;
	protected Integer currentPage1095C = 1;
	protected Integer totalPages1095C = null;
	protected Integer selectedPage1095C = null;
	protected Integer pageSize1095C = 15;
	// Column Sort attributes
	private String sortColumn1095B = "";
	private String sortOrder1095B = "asc";
	private String sortColumn1095C = "";
	private String sortOrder1095C = "asc";

	private String focusField = "";
	private int selectedRow = 0;
	private boolean showReportInNewWindow = false;
	private boolean showReportInNewTab = false;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getWorkEmail() {
		return workEmail;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	public String getHomeEmail() {
		return homeEmail;
	}

	public void setHomeEmail(String homeEmail) {
		this.homeEmail = homeEmail;
	}

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public String getEa1095ElecConsnt() {
		return ea1095ElecConsnt;
	}

	public void setEa1095ElecConsnt(String ea1095ElecConsnt) {
		this.ea1095ElecConsnt = ea1095ElecConsnt;
	}

	public String getEa1095ElecConsntMsg() {
		return ea1095ElecConsntMsg;
	}

	public void setEa1095ElecConsntMsg(String ea1095ElecConsntMsg) {
		this.ea1095ElecConsntMsg = ea1095ElecConsntMsg;
	}

	public boolean isEmail() {
		return email;
	}

	public void setEmail(boolean email) {
		this.email = email;
	}

	public boolean isShowEA1095ElecConsntPopup() {
		return showEA1095ElecConsntPopup;
	}

	public void setShowEA1095ElecConsntPopup(boolean showEA1095ElecConsntPopup) {
		this.showEA1095ElecConsntPopup = showEA1095ElecConsntPopup;
	}

	public boolean isShowSuccessMsg() {
		return showSuccessMsg;
	}

	public void setShowSuccessMsg(boolean showSuccessMsg) {
		this.showSuccessMsg = showSuccessMsg;
	}

	public boolean isEnableElecConsnt1095() {
		return enableElecConsnt1095;
	}

	public void setEnableElecConsnt1095(boolean enableElecConsnt1095) {
		this.enableElecConsnt1095 = enableElecConsnt1095;
	}

	public String getEmpNbr() {
		return empNbr;
	}

	public void setEmpNbr(String empNbr) {
		this.empNbr = empNbr;
	}

	public List<String> getCalYrs() {
		return calYrs;
	}

	public void setCalYrs(List<String> calYrs) {
		this.calYrs = calYrs;
	}

	public String getLatestYr() {
		return latestYr;
	}

	public void setLatestYr(String latestYr) {
		this.latestYr = latestYr;
	}

	public String getCurrentYr() {
		return currentYr;
	}

	public void setCurrentYr(String currentYr) {
		this.currentYr = currentYr;
	}

	public String getMsg1095() {
		return msg1095;
	}

	public void setMsg1095(String msg1095) {
		this.msg1095 = msg1095;
	}

	public String getEa1095() {
		return ea1095;
	}

	public void setEa1095(String ea1095) {
		this.ea1095 = ea1095;
	}

	public String getDisableEa1095B() {
		return disableEa1095B;
	}

	public void setDisableEa1095B(String disableEa1095B) {
		this.disableEa1095B = disableEa1095B;
	}

	public String getDisableEa1095C() {
		return disableEa1095C;
	}

	public void setDisableEa1095C(String disableEa1095C) {
		this.disableEa1095C = disableEa1095C;
	}

	public String getCovrgTyp() {
		return covrgTyp;
	}

	public void setCovrgTyp(String covrgTyp) {
		this.covrgTyp = covrgTyp;
	}

	public String getCovrgDescr() {
		return covrgDescr;
	}

	public void setCovrgDescr(String covrgDescr) {
		this.covrgDescr = covrgDescr;
	}

	public boolean isSelfIns() {
		return selfIns;
	}

	public void setSelfIns(boolean selfIns) {
		this.selfIns = selfIns;
	}

	public String getPlanStrtMon() {
		return planStrtMon;
	}

	public void setPlanStrtMon(String planStrtMon) {
		this.planStrtMon = planStrtMon;
	}

	public Map<String, String> getCalMonMap() {
		return calMonMap;
	}

	public void setCalMonMap(Map<String, String> calMonMap) {
		this.calMonMap = calMonMap;
	}

	public User getEaUser() {
		return eaUser;
	}

	public void setEaUser(User eaUser) {
		this.eaUser = eaUser;
	}

	public District getEaDistrict() {
		return eaDistrict;
	}

	public void setEaDistrict(District eaDistrict) {
		this.eaDistrict = eaDistrict;
	}

	public Page<EA1095BInfoCommand> getEa1095BData() {
		return ea1095BData;
	}

	public void setEa1095BData(Page<EA1095BInfoCommand> ea1095BData) {
		this.ea1095BData = ea1095BData;
	}

	public List<EA1095CInfoCommand> getEa1095CEmpData() {
		return ea1095CEmpData;
	}

	public void setEa1095CEmpData(List<EA1095CInfoCommand> ea1095CEmpData) {
		this.ea1095CEmpData = ea1095CEmpData;
	}

	public Page<EA1095CInfoCommand> getEa1095CCovrgData() {
		return ea1095CCovrgData;
	}

	public void setEa1095CCovrgData(Page<EA1095CInfoCommand> ea1095CCovrgData) {
		this.ea1095CCovrgData = ea1095CCovrgData;
	}

	public EA1095CInfoCommand getEa1095CCalMonAll() {
		return ea1095CCalMonAll;
	}

	public void setEa1095CCalMonAll(EA1095CInfoCommand ea1095CCalMonAll) {
		this.ea1095CCalMonAll = ea1095CCalMonAll;
	}

	public EA1095CInfoCommand getEa1095CCalMonJan() {
		return ea1095CCalMonJan;
	}

	public void setEa1095CCalMonJan(EA1095CInfoCommand ea1095CCalMonJan) {
		this.ea1095CCalMonJan = ea1095CCalMonJan;
	}

	public EA1095CInfoCommand getEa1095CCalMonFeb() {
		return ea1095CCalMonFeb;
	}

	public void setEa1095CCalMonFeb(EA1095CInfoCommand ea1095CCalMonFeb) {
		this.ea1095CCalMonFeb = ea1095CCalMonFeb;
	}

	public EA1095CInfoCommand getEa1095CCalMonMar() {
		return ea1095CCalMonMar;
	}

	public void setEa1095CCalMonMar(EA1095CInfoCommand ea1095CCalMonMar) {
		this.ea1095CCalMonMar = ea1095CCalMonMar;
	}

	public EA1095CInfoCommand getEa1095CCalMonApr() {
		return ea1095CCalMonApr;
	}

	public void setEa1095CCalMonApr(EA1095CInfoCommand ea1095CCalMonApr) {
		this.ea1095CCalMonApr = ea1095CCalMonApr;
	}

	public EA1095CInfoCommand getEa1095CCalMonMay() {
		return ea1095CCalMonMay;
	}

	public void setEa1095CCalMonMay(EA1095CInfoCommand ea1095CCalMonMay) {
		this.ea1095CCalMonMay = ea1095CCalMonMay;
	}

	public EA1095CInfoCommand getEa1095CCalMonJun() {
		return ea1095CCalMonJun;
	}

	public void setEa1095CCalMonJun(EA1095CInfoCommand ea1095CCalMonJun) {
		this.ea1095CCalMonJun = ea1095CCalMonJun;
	}

	public EA1095CInfoCommand getEa1095CCalMonJul() {
		return ea1095CCalMonJul;
	}

	public void setEa1095CCalMonJul(EA1095CInfoCommand ea1095CCalMonJul) {
		this.ea1095CCalMonJul = ea1095CCalMonJul;
	}

	public EA1095CInfoCommand getEa1095CCalMonAug() {
		return ea1095CCalMonAug;
	}

	public void setEa1095CCalMonAug(EA1095CInfoCommand ea1095CCalMonAug) {
		this.ea1095CCalMonAug = ea1095CCalMonAug;
	}

	public EA1095CInfoCommand getEa1095CCalMonSep() {
		return ea1095CCalMonSep;
	}

	public void setEa1095CCalMonSep(EA1095CInfoCommand ea1095CCalMonSep) {
		this.ea1095CCalMonSep = ea1095CCalMonSep;
	}

	public EA1095CInfoCommand getEa1095CCalMonOct() {
		return ea1095CCalMonOct;
	}

	public void setEa1095CCalMonOct(EA1095CInfoCommand ea1095CCalMonOct) {
		this.ea1095CCalMonOct = ea1095CCalMonOct;
	}

	public EA1095CInfoCommand getEa1095CCalMonNov() {
		return ea1095CCalMonNov;
	}

	public void setEa1095CCalMonNov(EA1095CInfoCommand ea1095CCalMonNov) {
		this.ea1095CCalMonNov = ea1095CCalMonNov;
	}

	public EA1095CInfoCommand getEa1095CCalMonDec() {
		return ea1095CCalMonDec;
	}

	public void setEa1095CCalMonDec(EA1095CInfoCommand ea1095CCalMonDec) {
		this.ea1095CCalMonDec = ea1095CCalMonDec;
	}

	public Integer getCurrentPage1095B() {
		return currentPage1095B;
	}

	public void setCurrentPage1095B(Integer currentPage1095B) {
		this.currentPage1095B = currentPage1095B;
	}

	public Integer getTotalPages1095B() {
		return totalPages1095B;
	}

	public void setTotalPages1095B(Integer totalPages1095B) {
		this.totalPages1095B = totalPages1095B;
	}

	public Integer getSelectedPage1095B() {
		return selectedPage1095B;
	}

	public void setSelectedPage1095B(Integer selectedPage1095B) {
		this.selectedPage1095B = selectedPage1095B;
	}

	public Integer getPageSize1095B() {
		return pageSize1095B;
	}

	public void setPageSize1095B(Integer pageSize1095B) {
		this.pageSize1095B = pageSize1095B;
	}

	public Integer getCurrentPage1095C() {
		return currentPage1095C;
	}

	public void setCurrentPage1095C(Integer currentPage1095C) {
		this.currentPage1095C = currentPage1095C;
	}

	public Integer getTotalPages1095C() {
		return totalPages1095C;
	}

	public void setTotalPages1095C(Integer totalPages1095C) {
		this.totalPages1095C = totalPages1095C;
	}

	public Integer getSelectedPage1095C() {
		return selectedPage1095C;
	}

	public void setSelectedPage1095C(Integer selectedPage1095C) {
		this.selectedPage1095C = selectedPage1095C;
	}

	public Integer getPageSize1095C() {
		return pageSize1095C;
	}

	public void setPageSize1095C(Integer pageSize1095C) {
		this.pageSize1095C = pageSize1095C;
	}

	public String getSortColumn1095B() {
		return sortColumn1095B;
	}

	public void setSortColumn1095B(String sortColumn1095B) {
		this.sortColumn1095B = sortColumn1095B;
	}

	public String getSortOrder1095B() {
		return sortOrder1095B;
	}

	public void setSortOrder1095B(String sortOrder1095B) {
		this.sortOrder1095B = sortOrder1095B;
	}

	public String getSortColumn1095C() {
		return sortColumn1095C;
	}

	public void setSortColumn1095C(String sortColumn1095C) {
		this.sortColumn1095C = sortColumn1095C;
	}

	public String getSortOrder1095C() {
		return sortOrder1095C;
	}

	public void setSortOrder1095C(String sortOrder1095C) {
		this.sortOrder1095C = sortOrder1095C;
	}

	public String getFocusField() {
		return focusField;
	}

	public void setFocusField(String focusField) {
		this.focusField = focusField;
	}

	public int getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(int selectedRow) {
		this.selectedRow = selectedRow;
	}

	public boolean isShowReportInNewWindow() {
		return showReportInNewWindow;
	}

	public void setShowReportInNewWindow(boolean showReportInNewWindow) {
		this.showReportInNewWindow = showReportInNewWindow;
	}

	public boolean isShowReportInNewTab() {
		return showReportInNewTab;
	}

	public void setShowReportInNewTab(boolean showReportInNewTab) {
		this.showReportInNewTab = showReportInNewTab;
	}

	public List<EA1095CInfoCommand> getEa1095CCovrg() {
		return ea1095CCovrg;
	}

	public void setEa1095CCovrg(List<EA1095CInfoCommand> ea1095cCovrg) {
		ea1095CCovrg = ea1095cCovrg;
	}

	public List<EA1095BInfoCommand> getEa1095BCovrg() {
		return ea1095BCovrg;
	}

	public void setEa1095BCovrg(List<EA1095BInfoCommand> ea1095bCovrg) {
		ea1095BCovrg = ea1095bCovrg;
	}

	public District getShopDistrict() {
		return shopDistrict;
	}

	public void setShopDistrict(District shopDistrict) {
		this.shopDistrict = shopDistrict;
	}
}