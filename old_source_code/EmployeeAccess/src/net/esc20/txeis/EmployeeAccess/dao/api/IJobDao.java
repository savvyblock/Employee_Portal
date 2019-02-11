package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.Job;

public interface IJobDao
{
	List<Job> getJobs(String employeeNumber);
}