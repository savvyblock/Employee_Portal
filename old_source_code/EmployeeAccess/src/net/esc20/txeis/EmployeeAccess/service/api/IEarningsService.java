package net.esc20.txeis.EmployeeAccess.service.api;

import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.domainobject.Earnings;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsBank;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsJob;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsLeave;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsNonTrsNonTax;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsNonTrsTax;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsOther;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsOvertime;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsPrint;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsSupplemental;
import net.esc20.txeis.EmployeeAccess.domainobject.Job;
import net.esc20.txeis.EmployeeAccess.domainobject.PayDate;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.report.IReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ParameterReport;

import org.springframework.binding.message.MessageContext;

public interface IEarningsService {
	
	String getMessage();
	Integer getRestrictEarnings();
	List<PayDate> retrievePayDates(String employeeNumber, Integer restrictPayDate);
	PayDate getLatestPayDate(List<PayDate> payDates);
	Earnings retrieveEarnings(MessageContext messageContext, String employeeNumber, PayDate payDate);
	Earnings calculateTotals(Earnings earnings, List <EarningsOther> earningsOther, List <EarningsJob> earningsJob, List <EarningsSupplemental> earningsSupplemental, 
						 List <EarningsBank> earningsBank, List <EarningsLeave> earningsLeave, List <EarningsOvertime> earningsOvertime,
						 List <EarningsNonTrsTax> earningsNonTrsTax, List <EarningsNonTrsNonTax> earningsNonTrsNonTax);
	public EarningsPrint generateEarningsPrint(User user, EarningsInfo earningsInfo, PayDate payDate, Map<Frequency,List<Job>> jobs);
	IReport setupReport(ParameterReport report, EarningsPrint parameters);

}
