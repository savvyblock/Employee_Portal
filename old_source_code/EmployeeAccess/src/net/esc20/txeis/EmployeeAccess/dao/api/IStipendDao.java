package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.Stipend;

public interface IStipendDao
{
	List<Stipend> getStipends(String employeeNumber);
}