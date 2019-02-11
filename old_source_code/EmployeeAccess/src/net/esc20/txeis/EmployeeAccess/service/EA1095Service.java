package net.esc20.txeis.EmployeeAccess.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.dao.api.IEA1095Dao;
import net.esc20.txeis.EmployeeAccess.dao.api.IOptionsDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IUserDao;
import net.esc20.txeis.EmployeeAccess.domain.EA1095BInfoCommand;
import net.esc20.txeis.EmployeeAccess.domain.EA1095CInfoCommand;
import net.esc20.txeis.EmployeeAccess.domain.EA1095InfoCommand;
import net.esc20.txeis.EmployeeAccess.domainobject.District;
import net.esc20.txeis.EmployeeAccess.domainobject.Options;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.service.api.IEA1095ervice;
import net.esc20.txeis.common.domainobjects.ReportingContact;
import net.esc20.txeis.common.util.Page;

import org.springframework.stereotype.Service;

@Service
public class EA1095Service implements IEA1095ervice
{
	private IEA1095Dao ea1095Dao;
	private IOptionsDao optionsDao;
	private IUserDao    userDao;

	public IEA1095Dao getEa1095Dao() {
		return ea1095Dao;
	}

	public void setEa1095Dao(IEA1095Dao ea1095Dao) {
		this.ea1095Dao = ea1095Dao;
	}

	public IOptionsDao getOptionsDao() {
		return optionsDao;
	}

	public void setOptionsDao(IOptionsDao optionsDao) {
		this.optionsDao = optionsDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public Options getOptions() {
		Options information = optionsDao.getOptions();
		return information;
	}

	@Override
	public List<String> retrieveAvailableCalYrs(String employeeNumber) {
		return ea1095Dao.retrieveAvailableCalYrs(employeeNumber);
	}

	@Override
	public String retrieveLatestYr(List<String> calYrs) {
		if(calYrs.size() > 0)
		{
			String latest = calYrs.get(0);

			for(String year : calYrs)
			{
				if(Integer.parseInt(year) > Integer.parseInt(latest))
				{
					latest = year;
				}
			}
			return latest;
		} else {
			return "";
		}
	}

	@Override
	public List<String> retrieveEA1095BEmpInfo(String activeTab, String empNbr, String calYr) {
		return ea1095Dao.retrieveEA1095BEmpInfo(activeTab, empNbr, calYr);
	}
	
	@Override
	public List<EA1095BInfoCommand> retrieveEA1095BCovrgInfo(String activeTab, String empNbr, String calYr) {
		return ea1095Dao.retrieveEA1095BCovrgInfo(activeTab, empNbr, calYr);
	}

	@Override
	public Page<EA1095BInfoCommand> retrieveEA1095BCovrgInfo(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize) {
		return ea1095Dao.retrieveEA1095BCovrgInfo(activeTab, empNbr, calYr, sortColumn, sortOrder, pageNbr, pageSize);
	}

	@Override
	public List<EA1095CInfoCommand> retrieveEA1095CEmpInfo(String activeTab, String empNbr, String calYr) {
		return ea1095Dao.retrieveEA1095CEmpInfo(activeTab, empNbr, calYr);
	}

	@Override
	public Page<EA1095CInfoCommand> retrieveEA1095CCovrgInfo(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize) {
		return ea1095Dao.retrieveEA1095CCovrgInfo(activeTab, empNbr, calYr, sortColumn, sortOrder, pageNbr, pageSize);
	}
	
	@Override
	public List<EA1095CInfoCommand> retrieveEA1095CCovrgInfo(String activeTab, String empNbr, String calYr) {
		return ea1095Dao.retrieveEA1095CCovrgInfo(activeTab, empNbr, calYr);
	}

	@Override
	public void buildEA1095CEmpDataDisplay(EA1095InfoCommand ea1095Info) {

		if (ea1095Info.getEa1095CEmpData().size() == 0) {
			EA1095CInfoCommand ea1095CalMonAll = new EA1095CInfoCommand();
			ea1095CalMonAll.setCalMon("ALL");
			ea1095Info.setEa1095CCalMonAll(ea1095CalMonAll);

			EA1095CInfoCommand ea1095CalMonJan = new EA1095CInfoCommand();
			ea1095CalMonJan.setCalMon("01");
			ea1095Info.setEa1095CCalMonJan(ea1095CalMonJan);

			EA1095CInfoCommand ea1095CalMonFeb = new EA1095CInfoCommand();
			ea1095CalMonFeb.setCalMon("02");
			ea1095Info.setEa1095CCalMonFeb(ea1095CalMonFeb);

			EA1095CInfoCommand ea1095CalMonMar = new EA1095CInfoCommand();
			ea1095CalMonMar.setCalMon("03");
			ea1095Info.setEa1095CCalMonMar(ea1095CalMonMar);

			EA1095CInfoCommand ea1095CalMonApr = new EA1095CInfoCommand();
			ea1095CalMonApr.setCalMon("04");
			ea1095Info.setEa1095CCalMonApr(ea1095CalMonApr);

			EA1095CInfoCommand ea1095CalMonMay = new EA1095CInfoCommand();
			ea1095CalMonMay.setCalMon("05");
			ea1095Info.setEa1095CCalMonMay(ea1095CalMonMay);

			EA1095CInfoCommand ea1095CalMonJun = new EA1095CInfoCommand();
			ea1095CalMonJun.setCalMon("06");
			ea1095Info.setEa1095CCalMonJun(ea1095CalMonJun);

			EA1095CInfoCommand ea1095CalMonJul = new EA1095CInfoCommand();
			ea1095CalMonJul.setCalMon("07");
			ea1095Info.setEa1095CCalMonJul(ea1095CalMonJul);

			EA1095CInfoCommand ea1095CalMonAug = new EA1095CInfoCommand();
			ea1095CalMonAug.setCalMon("08");
			ea1095Info.setEa1095CCalMonAug(ea1095CalMonAug);

			EA1095CInfoCommand ea1095CalMonSep = new EA1095CInfoCommand();
			ea1095CalMonSep.setCalMon("09");
			ea1095Info.setEa1095CCalMonSep(ea1095CalMonSep);

			EA1095CInfoCommand ea1095CalMonOct = new EA1095CInfoCommand();
			ea1095CalMonOct.setCalMon("10");
			ea1095Info.setEa1095CCalMonOct(ea1095CalMonOct);

			EA1095CInfoCommand ea1095CalMonNov = new EA1095CInfoCommand();
			ea1095CalMonNov.setCalMon("11");
			ea1095Info.setEa1095CCalMonNov(ea1095CalMonNov);

			EA1095CInfoCommand ea1095CalMonDec = new EA1095CInfoCommand();
			ea1095CalMonDec.setCalMon("12");
			ea1095Info.setEa1095CCalMonDec(ea1095CalMonDec);
		}
		else {
			for (EA1095CInfoCommand ea1095CInfo : ea1095Info.getEa1095CEmpData()) {
				EA1095CInfoCommand ea1095CEmpDataDisplay = new EA1095CInfoCommand();
				String calMon = ea1095CInfo.getCalMon().trim().toUpperCase();

				ea1095CEmpDataDisplay.setOffrOfCovrg(ea1095CInfo.getOffrOfCovrg());
				ea1095CEmpDataDisplay.setEmpShr(ea1095CInfo.getEmpShr().setScale(2, BigDecimal.ROUND_HALF_UP));
				ea1095CEmpDataDisplay.setSafeHrbr(ea1095CInfo.getSafeHrbr());
				ea1095CEmpDataDisplay.setSelfIns(ea1095CInfo.isSelfIns());

				switch (calMon) {
				case "ALL":{
					ea1095CEmpDataDisplay.setCalMon("ALL");
					ea1095Info.setEa1095CCalMonAll(ea1095CEmpDataDisplay);
					break;
				} case "01":{
					ea1095CEmpDataDisplay.setCalMon("01");
					ea1095Info.setEa1095CCalMonJan(ea1095CEmpDataDisplay);
					break;
				} case "02":{
					ea1095CEmpDataDisplay.setCalMon("02");
					ea1095Info.setEa1095CCalMonFeb(ea1095CEmpDataDisplay);
					break;
				} case "03":{
					ea1095CEmpDataDisplay.setCalMon("03");
					ea1095Info.setEa1095CCalMonMar(ea1095CEmpDataDisplay);
					break;
				} case "04":{
					ea1095CEmpDataDisplay.setCalMon("04");
					ea1095Info.setEa1095CCalMonApr(ea1095CEmpDataDisplay);
					break;
				} case "05":{
					ea1095CEmpDataDisplay.setCalMon("05");
					ea1095Info.setEa1095CCalMonMay(ea1095CEmpDataDisplay);
					break;
				} case "06":{
					ea1095CEmpDataDisplay.setCalMon("06");
					ea1095Info.setEa1095CCalMonJun(ea1095CEmpDataDisplay);
					break;
				} case "07":{
					ea1095CEmpDataDisplay.setCalMon("07");
					ea1095Info.setEa1095CCalMonJul(ea1095CEmpDataDisplay);
					break;
				} case "08":{
					ea1095CEmpDataDisplay.setCalMon("08");
					ea1095Info.setEa1095CCalMonAug(ea1095CEmpDataDisplay);
					break;
				} case "09":{
					ea1095CEmpDataDisplay.setCalMon("09");
					ea1095Info.setEa1095CCalMonSep(ea1095CEmpDataDisplay);
					break;
				} case "10":{
					ea1095CEmpDataDisplay.setCalMon("10");
					ea1095Info.setEa1095CCalMonOct(ea1095CEmpDataDisplay);
					break;
				} case "11":{
					ea1095CEmpDataDisplay.setCalMon("11");
					ea1095Info.setEa1095CCalMonNov(ea1095CEmpDataDisplay);
					break;
				} case "12":{
					ea1095CEmpDataDisplay.setCalMon("12");
					ea1095Info.setEa1095CCalMonDec(ea1095CEmpDataDisplay);
					break;
				}
				default:
					break;
				}
			}
		}
	}

	@Override
	public Map<String, String> buildEA1095CCalMonMap() {
		Map<String, String> calMonMap= new HashMap<String, String>();
		calMonMap.put("ALL", "All");
		calMonMap.put("01", "Jan");
		calMonMap.put("02", "Feb");
		calMonMap.put("03", "Mar");
		calMonMap.put("04", "Apr");
		calMonMap.put("05", "May");
		calMonMap.put("06", "Jun");
		calMonMap.put("07", "Jul");
		calMonMap.put("08", "Aug");
		calMonMap.put("09", "Sep");
		calMonMap.put("10", "Oct");
		calMonMap.put("11", "Nov");
		calMonMap.put("12", "Dec");

		return calMonMap;
	}

	@Override
	public String retrieveEA1095ElecConsent(String empNbr) {
		return ea1095Dao.retrieveEA1095ElecConsent(empNbr);
	}

	@Override
	public String retrieveEA1095ElecConsentMsg() {
		return ea1095Dao.retrieveEA1095ElecConsentMsg();
	}

	@Override
	public boolean updateEA1095ElecConsent(String employeeNumber, String ea1095ElecConsnt)
	{
		boolean update = false;
		if (ea1095ElecConsnt != null && !ea1095ElecConsnt.equals("")) {
			update = ea1095Dao.updateEA1095ElecConsent(employeeNumber, ea1095ElecConsnt);
		}
		return update;
	}

	@Override
	public String getEA1095BCovrgInfoSQL(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize) {
		return ea1095Dao.getEA1095BCovrgInfoSQL(activeTab, empNbr, calYr, sortColumn, sortOrder, pageNbr, pageSize);
	}

//	@Override
//	public String getEA1095CCovrgInfoSQL(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize) {
//		return ea1095Dao.getEA1095CCovrgInfoSQL(activeTab, empNbr, calYr, sortColumn, sortOrder, pageNbr, pageSize);
//	}
	
	@Override
	public User retrieveEmployee(String employeeNumber)
	{
		return userDao.retrieveEmployee(employeeNumber);
	}
	
	@Override
	public District retrieveDistrict(){
		return userDao.getDistrict();
	}
	
	@Override
	public District get1095SHOPParams(String calYr){
		return ea1095Dao.get1095SHOPParams(calYr);
	}

	@Override
	public ReportingContact getReportingContact(){
		return ea1095Dao.getReportingContact();
	}
}