package com.esc20.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.ReferenceDao;
import com.esc20.nonDBModels.Code;

@Service
public class ReferenceService {
    @Autowired
    private ReferenceDao referenceDao;
	
	private static List <Code> titles = new ArrayList<Code>(Arrays.asList(new Code("", ""),
			  new Code("1", "Mr."), 
			  new Code("2", "Mrs."), 
			  new Code("3", "Miss"), 
			  new Code("4", "Ms.")));
    
	public List<Code> getMaritalActualStatuses() {
		return referenceDao.getMaritalActualStatuses();
	}
	
	public List<Code> getMaritalTaxStatuses() {
		return referenceDao.getMaritalStatuses();
	}

	public List<Code> getGenerations() {
		return referenceDao.getGenerations();
	}
	
	public List<Code> getTitles() {
		return titles;
	}
	
	public List<Code> getStates() {
		return referenceDao.getStates();
	}
	
	public List<Code> getRestrictions() {
		return referenceDao.getRestrictions();
	}
	
	public List<Code> getAbsRsns(){
		return referenceDao.getAbsRsns();
	}
	
	public List<Code> getLeaveTypes(){
		return referenceDao.getLeaveTypes();
	}

	public List<Code> getLeaveStatus() {
		List<Code> codes  = referenceDao.getLeaveStatus();
		Code notProcessed = new Code();
		notProcessed.setCode("N");
		notProcessed.setSubCode("");
		notProcessed.setDescription("Not Processed");
		codes.add(notProcessed);
		return codes;
	}
	
	public List<Code> getPayrollFrequencies(String empNbr) {
		List<Code> payrollFrequencies = referenceDao.getPayrollFrequencies(empNbr);
		
		return payrollFrequencies;
	}

	public List<Code> getDdAccountTypes() {
		return referenceDao.getDdAccountTypes();
	}
}
