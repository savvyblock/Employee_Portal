package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.List;

public interface IAuthorityDao {

	List <Integer> retrieveMenuPermissions();
	void updatePassCount(int passCount, String userName);
	void deletePassword(String userName);
	boolean isTempPassword(String userName);
	int retrievePassCount(String userName);
	String retrieveTempLock(String userName);
	Integer employeePayCampusLeaveCampusCount(String employeeNumber);
	boolean isLeaveSupervisor(String employeeNumber);
	boolean isLeavePMISSupervisor(String employeeNumber);
}
