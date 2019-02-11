package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.EarningsBank;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsDeductions;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsJob;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsLeave;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsOther;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsOvertime;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsSupplemental;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsNonTrsTax;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsNonTrsNonTax;
import net.esc20.txeis.EmployeeAccess.domainobject.PayDate;

public interface IEarningsDao {

	Integer getRestrictEarnings();
	List<PayDate> getAvailablePayDates(String employeeNumber);
	EarningsInfo getEarningsInfo(String employeeNumber, PayDate payDate);
	EarningsDeductions getEarningsDeductions(String employeeNumber, PayDate payDate, String checkNumber);
	List <EarningsOther> getEarningsOther(String employeeNumber, PayDate payDate, String checkNumber);
	List <EarningsJob> getEarningsJob(String employeeNumber, PayDate payDate, String checkNumber);
	List <EarningsSupplemental> getEarningsSupplemental(String employeeNumber, PayDate payDate, String checkNumber);
	List <EarningsNonTrsTax> getEarningsNonTrsTax(String employeeNumber, PayDate payDate, String checkNumber);
	List <EarningsNonTrsNonTax> getEarningsNonTrsNonTax(String employeeNumber, PayDate payDate, String checkNumber);
	
	List <EarningsBank> getEarningsBank(String employeeNumber, PayDate payDate, String checkNumber);
	List <EarningsLeave> getEarningsLeave(String employeeNumber, PayDate payDate, String checkNumber);
	List <EarningsOvertime> getEarningsOvertime(String employeeNumber, PayDate payDate);
}
