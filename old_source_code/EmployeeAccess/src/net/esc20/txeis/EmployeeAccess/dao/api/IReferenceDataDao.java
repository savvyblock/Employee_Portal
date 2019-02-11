package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;

public interface IReferenceDataDao 
{
	//List<Code> getTitles();
	List <Code> getGenerations();
	List <Code> getStates();
	List <Code> getMaritalStatuses();
	List <Code> getRestrictions();
	List <Code> getDdAccountTypes();
	List <Code> getAvailableBanks();
	List <Code> getMaritalActualStatuses();
}
