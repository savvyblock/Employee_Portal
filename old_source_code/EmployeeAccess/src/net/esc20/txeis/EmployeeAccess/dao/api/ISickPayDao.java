package net.esc20.txeis.EmployeeAccess.dao.api;

import java.math.BigDecimal;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.SickPayInfo;

public interface ISickPayDao
{
	BigDecimal getThirdPartySickPay(String employeeNumber, String year);
	List<SickPayInfo> getSickPayInfo(String employeeNumber, String year);
}