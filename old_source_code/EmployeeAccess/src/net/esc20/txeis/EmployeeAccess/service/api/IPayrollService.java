package net.esc20.txeis.EmployeeAccess.service.api;

import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.Bank;
import net.esc20.txeis.EmployeeAccess.domainobject.BankChanges;
import net.esc20.txeis.EmployeeAccess.domainobject.PayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.PayInfoChanges;
import net.esc20.txeis.EmployeeAccess.domainobject.PayrollFields;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.web.view.Payroll;

import org.springframework.binding.message.MessageContext;

public interface IPayrollService
{
	List <String> getAvailableFrequencies(String employeeNumber);
	List <Code> getAvailableBanks();
	Integer getDirectDepositLimit();
	String getInitialFrequency(List<String> frequencies);
	PayInfo getPayInfo(String employeeNumber, Frequency frequency);
	PayInfo getPayInfoPending(String employeeNumber, Frequency frequency, PayInfo payInfo);
	PayInfoChanges setPayInfoChanges(PayInfo payInfo, PayInfo payInfoPending);
	PayInfo revertPayInfo(PayInfo payInfo, PayInfo payInfoPending);
	PayInfo copyPayInfo(PayInfo payInfoPending);
	Payroll setOptions(String employeeNumber, Payroll payroll);
	List <Bank> getAccountInfo(String employeeNumber, Frequency frequency);
	List<List<Bank>> getAccountInfoRequests(String employeeNumber, Frequency frequency, List<Bank> accountInfo);
	List <Bank> addBankAccount(MessageContext messageContext, Integer ddAccountLimit, List <Bank> banks);
	List <Bank> deleteBankAccountRequest(Integer selectedBank, List <Bank> accountInfo, List <Bank> accountInfoPending );
	List <BankChanges> setAccountInfoChanges(List <Bank> accountInfo, List <Bank> accountInfoPending);
	List <Bank> revertAccountInfo(Integer selectedBank, List <Bank> accountInfo, List <Bank> accountInfoPending);
	List <Bank> copyAllAccountInfo(List <Bank> accountInfoPending);
	PayrollFields getRequiredFields(String payFreq);
	Boolean savePayroll(MessageContext messageContext, String employeeNumber, Payroll payroll, PayInfo payInfo, List <Bank> accountInfo, PayInfo payInfoPending, List <Bank> accountInfoPending);
	Integer sendEmail(String userFirstName, String userLastName, String userWorkEmail, String userHomeEmail, Payroll payroll, PayInfo payInfoPending, List <Bank> accountInfoPending,  PayInfo payInfoCurrent, List <Bank> accountInfoCurrent);
	Boolean deleteCheck(Payroll payroll);
	Boolean hasInvalidAccounts(List <Bank> accounts);
	void sendApproverEmail(Integer emailChanges, String userFirstName, String userLastName, Payroll payroll, PayInfo payInfoCurrent, List <Bank> accountInfoCurrent);
	String getMessage();
	void clearErrors(MessageContext messageContext);
}