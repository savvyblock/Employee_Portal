package com.esc20.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.AppUserDao;
import com.esc20.dao.TravelRequestDao;
import com.esc20.model.BeaEmpTrvl;
import com.esc20.nonDBModels.BfnOptionsTrvl;
import com.esc20.nonDBModels.TravelCheckDetails;
import com.esc20.nonDBModels.TravelInfo;
import com.esc20.nonDBModels.TravelRequestCalendar;
import com.esc20.nonDBModels.TravelRequestInfo;
import com.esc20.nonDBModels.TravelType;

@Service
public class TravelRequestService {

	@Autowired
	private TravelRequestDao travelRequestDao;
	
	@Autowired
	private AppUserDao appUserDao;

	public List<TravelRequestInfo> getTravelRequestInfo(String employeeNumber) {
		return travelRequestDao.getTravelRequestInfo(employeeNumber);
	}

	public List<TravelRequestInfo> getVendorAddressInfo(String vendorNbr) {
		return travelRequestDao.getVendorAddressInfo(vendorNbr);
	}
	
	public BigDecimal getCommuteDistance(String employeeNumber) {
		return travelRequestDao.getCommuteDistance(employeeNumber);
	}

	public boolean updateTravelRequestCommuteDist(String employeeNumber, String changeCommuteDist) {
		return travelRequestDao.updateTravelRequestCommuteDist(employeeNumber, changeCommuteDist);
	}

	public List<TravelRequestInfo> getCampusPay(String employeeNumber) {
		return travelRequestDao.getCampusPay(employeeNumber);
	}

	public int getTripCount(String employeeNumber, String tripNbr) {
		return travelRequestDao.getTripCount(employeeNumber, tripNbr);
	}

	public List<String> getDistinctTripNumber(String employeeNumber) {
		return travelRequestDao.getDistinctTripNumber(employeeNumber);
	}

	public List<TravelRequestCalendar> getTravelRequestCalendarParameters(String employeeNumber, String tripNbr) {
		return travelRequestDao.getTravelRequestCalendarParameters(employeeNumber, tripNbr);
	}

	public List<TravelType> getTravelStatus() {
		return travelRequestDao.getTravelStatus();
	}

	public List<TravelInfo> getTravelInfo(String employeeNumber, String SearchType, String StartDate, String EndDate) {
		return travelRequestDao.getTravelInfo(employeeNumber, SearchType, StartDate, EndDate);
	}

	public List<TravelRequestInfo> getCampuses() {
		return travelRequestDao.getCampuses();
	}
	
	public int getFirstApproverExists(String departId, int workFlowType) {
		return travelRequestDao.getFirstApproverExists(departId, workFlowType);
	}
	
	public List<Map<String, Long>> saveTravelReimbursementRequest(String employeeNumber, String tripNum,  List<BeaEmpTrvl> empTrvls) {
		return travelRequestDao.saveTravelReimbursementRequest(employeeNumber, tripNum, empTrvls);
	}
	
	public List<Map<String, Long>> deleteTravelReimbursementRequest(String employeeNumber, String tripNum) {
		return travelRequestDao.deleteTravelReimbursementRequest(employeeNumber, tripNum);
	}

	public String GetSISPreference(String pPreferenceName) {
		return travelRequestDao.GetSISPreference(pPreferenceName);
	}

	public String GetSISTaskURL(String pTaskName) {
		return travelRequestDao.GetSISTaskURL(pTaskName);
	}

	public String getTripTotalAmount(String employeeNumber, String tripNbr) {
		return travelRequestDao.getTripTotalAmount(employeeNumber, tripNbr);
	}

	public TravelCheckDetails getTripCheckNumDt(String tripNbr) {
		return travelRequestDao.getTripCheckNumDt(tripNbr);
	}

	public String getNxtTripNbr() {
		return travelRequestDao.getNxtTripNbr();
	}

	public BfnOptionsTrvl getBfnOptionsTrvl() {
		return travelRequestDao.getBfnOptionsTrvl();
	}

	public void updateNxtTripNbr(String nxtTripNbr) {
		travelRequestDao.updateNxtTripNbr(nxtTripNbr);
	}

	public Integer getSecUserId(String userName) {
		return travelRequestDao.getSecUserId(userName);
	}

	public String getReimburseCampus(String empNbr) {
		return travelRequestDao.getReimburseCampus(empNbr);
	}

	public String getDistrictId() {
		return travelRequestDao.getDistrictId();
	}
	
	public boolean isTravelApprover(String empNbr, int workFlowType) {
		return travelRequestDao.isTravelApprover(empNbr, workFlowType);
	}

	public boolean isDocoumentAttached(String tripNbr) {
		return travelRequestDao.isDocoumentAttached(tripNbr);
	}
	
	public Integer getSecUserIdByEmpNbr(String empNbr) throws Exception {
		return appUserDao.getSecUserForEmpNbr(empNbr);
	}
	
	public int getMaxTripNum() {
		return travelRequestDao.getMaxTripNum();
	}
}
