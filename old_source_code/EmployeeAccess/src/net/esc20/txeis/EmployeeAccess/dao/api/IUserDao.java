package net.esc20.txeis.EmployeeAccess.dao.api;

import net.esc20.txeis.EmployeeAccess.domainobject.District;
import net.esc20.txeis.EmployeeAccess.domainobject.User;

public interface IUserDao {
	User retrieveEmployee(String employeeNumber);
	User retrieveEmployee(String employeeNumber, String dob, String zip);
	User retrieveExistingUserByEmpData(String employeeNumber, String dob, String zip);
	User retrieveExistingUser(String userName);
	User retrieveExistingUserByEmpNbr(String employeeNumber);
	void insertUser(User user);
	void updateEmail(User user);
	void deleteUser(User user);
	String employeeRetrievalMethod();
	String retrieveEmpNbrBySSN(String ssn);
	District getDistrict();
}
