package net.esc20.txeis.EmployeeAccess.service.api;

import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.search.EmployeeCriteria;

import org.springframework.binding.message.MessageContext;

public interface IUserService
{
	User retrieveEmployee(MessageContext messageContext, EmployeeCriteria searchInfo, boolean existsCheck, boolean useSSN);
	User retrieveEmployee(String employeeNumber);
	User retrieveExistingUser(MessageContext messageContext, EmployeeCriteria searchInfo, boolean useSSN);
	Boolean useSSN(String district);
	boolean saveUser(MessageContext messageContext, User user);
	boolean hasExistingUser(String employeeNumber);
	boolean userExists(String userName);
	void sendMail(User user);
}