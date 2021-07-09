package com.esc20.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.AccountCodeDao;
import com.esc20.model.BeaEmpTrvlAcct;

@Service
public class AccountCodesService {

	@Autowired
	private AccountCodeDao accountCodeDao;

	public void saveAccountCodes(List<BeaEmpTrvlAcct> beaEmpTrvlAccts) {
		accountCodeDao.saveAccountCodes(beaEmpTrvlAccts);
	}
	
	public List<Map<String, String>> getAccountCodes(String employeeNumber){
		return accountCodeDao.getAccountCodes(employeeNumber);
	}
	
	public List<Map<String, String>> getAccountCodesTrip(String employeeNumber, String tripNbr){
		return accountCodeDao.getAccountCodesTrip(employeeNumber, tripNbr);
	}
	
	public List<Map<String, String>> getAccountCodesTripSeqWise(String employeeNumber, String tripNbr, Long tripSeqNbr){
		return accountCodeDao.getAccountCodesTripSeqWise(employeeNumber, tripNbr, tripSeqNbr);
	}
	
	public String getAccountDescTrip(String fund, String func, String obj, String sobj, String org, String fscl_yr,
			String pgm, String ed_span, String proj_dtl){
		return accountCodeDao.getAccountDescTrip(fund, func, obj, sobj, org, fscl_yr, pgm, ed_span, proj_dtl);
	}
	
	public boolean accountCodeExists(String accountCd, String fileId, String empNbr) {
		return accountCodeDao.accountCodeExists(accountCd, fileId, empNbr);
	}
}
