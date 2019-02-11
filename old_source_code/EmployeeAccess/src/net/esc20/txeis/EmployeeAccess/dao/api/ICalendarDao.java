package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.Date;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.Calendar;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;

public interface ICalendarDao
{
	Date getLastPostedPayDate(String employeeNumber, Frequency frequency);
	List<String> getAvailableYears(String employeeNumber);
	List<Calendar> getCalendars(String employeeNumber, String year);
}