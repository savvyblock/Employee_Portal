package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.Bank;
import net.esc20.txeis.EmployeeAccess.domainobject.PayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;

public interface IPayrollDao
{
	PayInfo getPendingPayInfo(String employeeNumber, Frequency frequency);
	List<List <Bank>> getAccounts(String employeeNumber, Frequency frequency);
	List<Bank> getCurrentAccounts(String employeeNumber, Frequency frequency);
	List<Bank> getPendingAccounts(String employeeNumber, Frequency frequency);
	List<Frequency> getPayrollFrequencies(String employeeNumber);
	List <Code> getAvailableBanks();
	Integer getDirectDepositLimit(); 
	
	boolean isNewAccountInfoRow(String employeeNumber, Frequency frequency, Bank accountInfo, Bank accountInfoPending);
	boolean isNewPayInfoRow(String employeeNumber, Frequency frequency, PayInfo payInfo);
	
	String getPayInfoFieldDisplay(String frequency);
	String getAccountInfoFieldDisplay(String frequency);
	Boolean getAutoApprovePayInfo(String frequency);
	Boolean getAutoApproveAccountInfo(String frequency);
	
	boolean insertPayInfoRequest(Boolean autoApprove, String employeeNumber, String frequency, PayInfo payrollPayInfo, PayInfo payInfo);
	boolean updatePayInfoRequest(Boolean autoApprove, String employeeNumber, String frequency, PayInfo payrollPayInfo, PayInfo payInfo);
	boolean deletePayInfoRequest(String employeeNumber, String frequency, PayInfo payrollPayInfo, PayInfo payInfo);
	boolean insertAccountRequest(Boolean autoApprove, String employeeNumber, String frequency, Bank payrollAccountInfo, Bank accountInfo);
	boolean updateAccountRequest(Boolean autoApprove, String employeeNumber, String frequency, Bank payrollAccountInfo, Bank accountInfo, Bank accountInfoPending);
	boolean deleteAccountRequest(String employeeNumber, String frequency, Bank payrollAccountInfo, Bank accountInfo, Bank accountInfoPending);
	
	boolean updatePayInfoApprove(String employeeNumber, String frequency, PayInfo payrollPayInfo, PayInfo payInfo);
	boolean insertAccountApprove(String employeeNumber, String prenote, String frequency, Bank payrollAccountInfo);
	boolean updateAccountApprove(String employeeNumber, String prenote, String frequency, Bank payrollAccountInfo, Bank accountInfo);
	boolean deleteAccountApprove(String employeeNumber, String frequency, Bank accountInfo);
	
	int insertNextYearAccounts(String employeeNumber);
	int deleteNextYearAccounts(String employeeNumber);
	
	int getActiveEmployee(String employeeNumber, String frequency);
	
	boolean createNewConnection();
	boolean commitUpdate();
	void rollbackUpdate();
	void closeConnection();
	
	List <List <String>> getRequiredFields(String payFreq);
	List <List <String>> getDocRequiredFields(String payFreq);
	
	String getApproverEmployeeNumber(String frequency, String table);
	User getApprover(String employeeNumber);
}