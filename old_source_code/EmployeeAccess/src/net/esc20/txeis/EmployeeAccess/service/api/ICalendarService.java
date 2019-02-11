package net.esc20.txeis.EmployeeAccess.service.api;

import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.CalYTDPrint;
import net.esc20.txeis.EmployeeAccess.domainobject.Calendar;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.report.IReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ParameterReport;

public interface ICalendarService
{
	String getMessage();
	String getLatestYear(List<String> years);
	List<String> retrieveYears(String employeeNumber);
	List<Calendar> retrieveCalendar(String employeeNumber, String year);
	List<CalYTDPrint> generateCalYTDPrint(User user, List<Calendar> calendars);   //jf20140110 created new method
	IReport setupReport(ParameterReport report, List<CalYTDPrint> parameters);   //jf20140110 created new method
}