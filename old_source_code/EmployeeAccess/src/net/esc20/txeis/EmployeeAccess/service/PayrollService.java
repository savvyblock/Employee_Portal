package net.esc20.txeis.EmployeeAccess.service;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import net.esc20.txeis.EmployeeAccess.dao.api.IOptionsDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IPayDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IPayrollAccountDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IPayrollDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Bank;
import net.esc20.txeis.EmployeeAccess.domainobject.BankChanges;
import net.esc20.txeis.EmployeeAccess.domainobject.Money;
import net.esc20.txeis.EmployeeAccess.domainobject.Options;
import net.esc20.txeis.EmployeeAccess.domainobject.PayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.PayInfoChanges;
import net.esc20.txeis.EmployeeAccess.domainobject.PayrollFields;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.service.api.IMailUtilService;
import net.esc20.txeis.EmployeeAccess.service.api.IPayrollService;
import net.esc20.txeis.EmployeeAccess.validation.ContextValidationUtil;
import net.esc20.txeis.EmployeeAccess.web.view.Payroll;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.binding.message.Message;
import org.springframework.binding.message.MessageContext;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class PayrollService implements IPayrollService
{
	private IPayrollDao payrollDao;
	private IOptionsDao optionsDao;
	private IPayDao payDao;
	private IPayrollAccountDao payrollAccountDao;
	private IMailUtilService mailUtilService;
	
	private static Log log = LogFactory.getLog(UserService.class);

	public IPayrollDao getPayrollDao() {
		return payrollDao;
	}

	public void setPayrollDao(IPayrollDao payrollDao) {
		this.payrollDao = payrollDao;
	}

	public IOptionsDao getOptionsDao() {
		return optionsDao;
	}

	public void setOptionsDao(IOptionsDao optionsDao) {
		this.optionsDao = optionsDao;
	}

	public IPayDao getPayDao() {
		return payDao;
	}

	public void setPayDao(IPayDao payDao) {
		this.payDao = payDao;
	}

	public IPayrollAccountDao getPayrollAccountDao() {
		return payrollAccountDao;
	}

	public void setPayrollAccountDao(IPayrollAccountDao payrollAccountDao) {
		this.payrollAccountDao = payrollAccountDao;
	}
		
	/**
	 * Returns initial frequency when user first loads page
	 */
	@Override
	public String getInitialFrequency(List<String> frequencies)
	{
		if(frequencies != null && frequencies.size() > 0)
		{
			return frequencies.get(0);
		}
		
		return null;
	}
	
	/**
	 * Returns list of all available frequencies for DDW
	 */
	@Override
	public List<String> getAvailableFrequencies(String employeeNumber)
	{
		List<Frequency> payrollFrequenciesEnum = payrollDao.getPayrollFrequencies(employeeNumber);
		List<String> payrollFrequencies = new ArrayList <String>();
		
		for(int i=0; i < payrollFrequenciesEnum.size(); i++ )
		{
			payrollFrequencies.add(payrollFrequenciesEnum.get(i).getLabel());
		}
		
		return payrollFrequencies;
	}
	
	/**
	 * Returns list of all available banks for bank code lookup
	 */
	@Override
	public List <Code> getAvailableBanks()
	{
		return payrollDao.getAvailableBanks();
	}
	
	/**
	 * Returns direct deposit account limit per frequency
	 */
	@Override
	public Integer getDirectDepositLimit()
	{
		return payrollDao.getDirectDepositLimit();
	}
	
	/**
	 * Returns current marital payroll info
	 */
	@Override
	public PayInfo getPayInfo(String employeeNumber, Frequency frequency)
	{
		return payDao.getPayInfo(employeeNumber, frequency);
	}
	
	/**
	 * Returns marital payroll info change request. If no request exists, fill with current info.
	 */
	@Override
	public PayInfo getPayInfoPending(String employeeNumber, Frequency frequency, PayInfo payInfo)
	{
		PayInfo pending = payrollDao.getPendingPayInfo(employeeNumber, frequency);
		if(pending == null)
		{
			pending = new PayInfo(); 
			
			Code tempCode = new Code();
			tempCode.setCode(payInfo.getMaritalStatus().getCode());
			tempCode.setDescription(payInfo.getMaritalStatus().getDescription());
			pending.setMaritalStatus(tempCode);
			pending.setNumberOfExemptions(payInfo.getNumberOfExemptions());
		}	

		return pending;
	}
	
	@Override
	public PayInfoChanges setPayInfoChanges(PayInfo payInfo, PayInfo payInfoPending)
	{
		PayInfoChanges payInfoChanges = new PayInfoChanges();
		
		if(!payInfo.getMaritalStatus().getCode().equals(payInfoPending.getMaritalStatus().getCode()))
		{
			payInfoChanges.setMaritalStatusChanged(true);
		}
		
		if(!payInfo.getNumberOfExemptions().equals(payInfoPending.getNumberOfExemptions()))
		{
			payInfoChanges.setNumberOfExemptionsChanged(true);
		}
		
		return payInfoChanges;
	}
	
	/**
	 * Reverts marital payroll info
	 */
	@Override
	public PayInfo revertPayInfo(PayInfo payInfo, PayInfo payInfoPending)
	{
		Code tempCode = new Code();
		tempCode.setCode(payInfo.getMaritalStatus().getCode());
		tempCode.setDescription(payInfo.getMaritalStatus().getDescription());
		payInfoPending.setMaritalStatus(tempCode);
		payInfoPending.setNumberOfExemptions(payInfo.getNumberOfExemptions());
		return payInfoPending;
	}
	
	@Override
	public PayInfo copyPayInfo(PayInfo payInfoPending)
	{
		PayInfo payInfoCopy = new PayInfo();
		
		Code tempCode = new Code();
		tempCode.setCode(payInfoPending.getMaritalStatus().getCode());
		tempCode.setDescription(payInfoPending.getMaritalStatus().getDescription());
		payInfoCopy.setMaritalStatus(tempCode);
		payInfoCopy.setNumberOfExemptions(payInfoPending.getNumberOfExemptions());
		
		return payInfoCopy;
	}
	
	/**
	 * Sets and returns option code for marital payroll info group
	 */
	@Override
	public Payroll setOptions(String employeeNumber, Payroll payroll)
	{
		payroll.setFieldDisplayOptionInfo(payrollDao.getPayInfoFieldDisplay(Frequency.getFrequency(payroll.getFrequency()).getCode()));
		payroll.setFieldDisplayOptionBank(payrollDao.getAccountInfoFieldDisplay(Frequency.getFrequency(payroll.getFrequency()).getCode()));
		
		int payInactive = payrollDao.getActiveEmployee(employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode());
		
		if(payInactive == 2)
		{
			payroll.setFieldDisplayOptionInfo("I");
			payroll.setFieldDisplayOptionBank("I");
		}
		
		payroll.setAutoApprovePayInfo(payrollDao.getAutoApprovePayInfo(Frequency.getFrequency(payroll.getFrequency()).getCode()));
		payroll.setAutoApproveAccountInfo(payrollDao.getAutoApproveAccountInfo(Frequency.getFrequency(payroll.getFrequency()).getCode()));
		return payroll;
	}
	
	/**
	 * Returns current bank account info
	 */
	@Override
	public List<Bank> getAccountInfo(String employeeNumber, Frequency frequency)
	{
		return payrollAccountDao.getAccounts(employeeNumber, frequency);
	}
	
	/**
	 * Returns list of pending requests for bank accounts. If no requests exist, set to current info. If request fields are empty, request is marked for deletion.
	 */
	@Override
	public List<List<Bank>> getAccountInfoRequests(String employeeNumber, Frequency frequency, List<Bank> accountInfo)
	{
		List <List <Bank>> accounts = payrollDao.getAccounts(employeeNumber, frequency);

		List<Bank> current = accounts.get(0);
		List<Bank> pending = accounts.get(1);
		
		//if no pending changes
		if(pending == null || pending.size() == 0)
		{	
			pending = copyAllAccountInfo(accountInfo);
			current = copyAllAccountInfo(accountInfo);
		}
		//if has pending changes
		else
		{
			//synch up array sizes
			//if request count > actual # of bank accounts
			for(int i = 0; i < current.size(); i++)
			{
				if(isAddedAccount(current.get(i)))
				{		
					accountInfo.add(createNewBank());
				}
			}
			
			//if request count < actual # of bank accounts
			while(current.size() < accountInfo.size())
			{	
				current.add(createNewBank());
				pending.add(createNewBank());
			}
			
			accountSwap(accountInfo, current, pending);
			
			for(int i = 0; i < current.size(); i++)
			{
				if(isAddedAccount(current.get(i)) && !isAddedAccount(accountInfo.get(i)))
				{
					pending.set(i, copyAccountInfo(accountInfo.get(i)));
				}
				
				if(pending.get(i).getAccountNumber().equals("") && pending.get(i).getAccountType().getCode().equals("") 
						&& pending.get(i).getCode().getCode().equals("") && pending.get(i).getDepositAmount().getAmount() == 0)
				{
					pending.get(i).setIsDelete(true);
				}
			}
		}
		
		List <List <Bank>> requests = new ArrayList <List <Bank>>();
		requests.add(accountInfo);
		requests.add(current);
		requests.add(pending);
		return requests;
		
	}
	
	/**
	 * Add a bank account
	 */
	@Override
	public List <Bank> addBankAccount(MessageContext messageContext, Integer ddAccountLimit, List <Bank> banks)
	{
		int invalidCount = 0;
		for (Bank b: banks)
		{
			if(b.getInvalidAccount())
				invalidCount++;
		}
		
		if((banks.size()-invalidCount) >= ddAccountLimit)
		{
			boolean messageExists= false;
			for(Message m : messageContext.getAllMessages())
			{
				if(m.getText().equals("Maximum allowed number of Direct Deposit accounts reached. Unable to add new account."))
				{
					messageExists = true;
					break;
				}
			}
			if(!messageExists)
				ContextValidationUtil.addMessage(messageContext,"", "Maximum allowed number of Direct Deposit accounts reached. Unable to add new account.");
		}
		else
		{
			if(banks.size() == 0)
			{
				banks.add(createNewBank());
			}
			else
			{
				for(int i = banks.size()-1; i >= 0; i--)
				{
					if(!banks.get(i).getInvalidAccount())
					{
						banks.add(i+1, createNewBank());
						break;
					}
				}
			}
			
		}
		
		return banks;
	}
	
	/**
	 * Returns list of Booleans that determine whether a change request has been made by comparing current values with pending.
	 */
	@Override
	public List <BankChanges> setAccountInfoChanges(List <Bank> accountInfo, List <Bank> accountInfoPending)
	{
		List <BankChanges> accountInfoChanges = new ArrayList <BankChanges>();
		
		for(int i = 0; i < accountInfo.size(); i++)
		{
			accountInfoChanges.add( new BankChanges());
			
			if(!accountInfo.get(i).getAccountNumber().equals(accountInfoPending.get(i).getAccountNumber()))
			{
				accountInfoChanges.get(i).setAccountNumberChanged(true);
			}
			if(!accountInfo.get(i).getAccountType().getCode().equals(accountInfoPending.get(i).getAccountType().getCode()))
			{
				accountInfoChanges.get(i).setAccountTypeChanged(true);
			}
			if(accountInfo.get(i).getDepositAmount().getAmount() != (accountInfoPending.get(i).getDepositAmount().getAmount()))
			{
				accountInfoChanges.get(i).setDepositAmountChanged(true);
			}
			if(!accountInfo.get(i).getCode().getCode().equals(accountInfoPending.get(i).getCode().getCode()))
			{
				accountInfoChanges.get(i).setCodeChanged(true);
			}
			
		}
		
		return accountInfoChanges;
	}
	
	/**
	 * Toggles a bank account for deletion
	 */
	@Override
	public List <Bank> deleteBankAccountRequest(Integer selectedBank,List <Bank> accountInfo, List <Bank> accountInfoPending )
	{
		if(accountInfoPending.get(selectedBank).getIsDelete())
		{
			accountInfoPending.get(selectedBank).setIsDelete(false);
			if(isAddedAccount(accountInfoPending.get(selectedBank)))
				revertAccountInfo(selectedBank, accountInfo, accountInfoPending);
		}
		else if(!accountInfoPending.get(selectedBank).getIsDelete())
			accountInfoPending.get(selectedBank).setIsDelete(true);
		return accountInfoPending;
	}
	
	/**
	 * Reverts a bank account information
	 */
	@Override
	public List <Bank> revertAccountInfo(Integer selectedBank, List <Bank> accountInfo, List <Bank> accountInfoPending)
	{
		accountInfoPending.set(selectedBank, copyAccountInfo(accountInfo.get(selectedBank)));
		return accountInfoPending;
	}
	
	@Override
	public List <Bank> copyAllAccountInfo(List <Bank> accountInfo)
	{
		List <Bank> accountInfoCopy = new ArrayList <Bank> ();
		
		for(int i =0; i < accountInfo.size(); i++)
		{
			accountInfoCopy.add(copyAccountInfo(accountInfo.get(i)));
		}
		
		return accountInfoCopy;
	}
	
	@Override
	public PayrollFields getRequiredFields(String payFreq)
	{
		List <List <String>> result = payrollDao.getRequiredFields(payFreq);
		
		PayrollFields req = new PayrollFields();
		
		for(List <String> entry: result)
		{
			if("BEA_W4".equals(entry.get(0)))
			{
				if("Marital Status".equals(entry.get(1)))
				{
					req.setMaritalStatus(entry.get(2));
				}
				
				else if("Nbr of Exceptions".equals(entry.get(1)))
				{
					req.setNumberOfExemptions(entry.get(2));
				}
			}
			else if("BEA_DRCT_DPST_BNK_ACCT".equals(entry.get(0)))
			{
				if("Bank".equals(entry.get(1)))
				{
					req.setCode(entry.get(2));
				}
				
				else if("Bank Account Nbr".equals(entry.get(1)))
				{
					req.setAccountNumber(entry.get(2));
				}
				
				else if("Bank Account Type".equals(entry.get(1)))
				{
					req.setAccountType(entry.get(2));
				}
				
				else if("Bank Account Amount".equals(entry.get(1)))
				{
					req.setDepositAmount(entry.get(2));
				}
			}
		}
		
		return req;
	}
	
	@Override
	public Boolean savePayroll(MessageContext messageContext, String employeeNumber, Payroll payroll, PayInfo payInfo, List <Bank> accountInfo, PayInfo payInfoPending, List <Bank> accountInfoPending)
	{	
		
		//had to move due to autocommit connection
		List <Boolean> isNewRow = new ArrayList<Boolean>();
		
		if(checkForDuplicates(payroll.getAccountInfo(), payroll.getAccountInfo()))
		{
			ContextValidationUtil.addMessage(messageContext, "", "Duplicate Bank Account and Bank Account Number found. Please ensure all accounts have a unique account number or bank.");
			return false;
		}
		
		for(int i = 0; i < payroll.getAccountInfo().size(); i++)
		{
			isNewRow.add(payrollDao.isNewAccountInfoRow(employeeNumber, Frequency.getFrequency(payroll.getFrequency()), accountInfo.get(i), accountInfoPending.get(i)));
		}
		
		List <Bank> hrAccountsTemp = getAccountInfo(employeeNumber,  Frequency.getFrequency(payroll.getFrequency()));
		
		
		if(!payrollDao.createNewConnection())
		{
			ContextValidationUtil.addMessage(messageContext, "", "Unable to create database connection. Changes were not saved.");
			return false;
		}
		
		int successPayInfo = savePayInfoChanges(messageContext, employeeNumber, payroll, payInfo, payInfoPending);

		if(successPayInfo == -1)
		{
			payrollDao.closeConnection();
			return false;
		}
		
		int successAccount = saveAccountChanges(messageContext, isNewRow, employeeNumber, payroll, accountInfo, accountInfoPending, hrAccountsTemp);
		
		if((successPayInfo == 1 || successAccount == 1) && successPayInfo != -1 && successAccount != -1)
		{
			if(!payrollDao.commitUpdate())
			{
				ContextValidationUtil.addMessage(messageContext, "", "No new requests have been created. An error was experienced while attempting to save changes. ");
				payrollDao.closeConnection();
				return false;
			}
			messageContext.addMessage(ContextValidationUtil.buildSaveMessage(messageContext.toString(), "Save successful"));
			payroll.setPrimary("");
		}
		else if(successPayInfo == -1 || successAccount == -1)
		{
			payrollDao.closeConnection();
			return false;
		}
		else
		{
			payrollDao.closeConnection();
			return false;
		}
		
		payrollDao.closeConnection();
		return true;
	}
	
	@Override
	public Integer sendEmail(String userFirstName, String userLastName, String userWorkEmail, String userHomeEmail,  Payroll payroll, PayInfo payInfoPending, List <Bank> accountInfoPending, PayInfo payInfoCurrent, List <Bank> accountInfoCurrent) {
		boolean payrollSame = comparePayInfo(payroll.getPayInfo(), payInfoCurrent);
		boolean accountSame = true;

		for (int i =0; i < payroll.getAccountInfo().size(); i++) {
			if(!accountSame)
				break;
			accountSame = compareBanks(payroll.getAccountInfo().get(i), accountInfoCurrent.get(i));
		}
		
		//used to track changes for current session on page for emailing purposes
		List <BankChanges> currentAccountInfoChanges = setAccountInfoChanges(accountInfoPending, payroll.getAccountInfo());
		PayInfoChanges currentPayInfoChanges = setPayInfoChanges(payInfoPending, payroll.getPayInfo());
		
		PayrollFields docRequired = getDocumentationRequiredFields(Frequency.getFrequency(payroll.getFrequency()).getCode());
	
		boolean hasDocChanges = false;
		boolean hasApprovChanges = false;
		boolean hasPayInfoChanges = false;
		boolean hasAccountChanges = false;
		
		userFirstName = userFirstName== null ? "" : userFirstName;
		userLastName = userLastName== null ? "" : userLastName;
	
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setSubject("A MESSAGE FROM SELF SERVICE");
		StringBuilder messageContents = new StringBuilder();
		messageContents.append(userFirstName + " " +userLastName + ", \n\n");
		messageContents.append("Your request for changes to payroll data has been submitted. \n");
		
		String header = "\nThe following data was automatically approved and updated: \n";
		StringBuilder contents = new StringBuilder();
		StringBuilder bankContents = new StringBuilder();

		if (currentPayInfoChanges.getMaritalStatusChanged() && payroll.getAutoApprovePayInfo() && !payrollSame) {
			contents.append("W4 Marital Status \t\t" +payroll.getPayInfo().getMaritalStatus().getDisplayLabel()+"\n");
			hasApprovChanges = true;
			hasPayInfoChanges = true;
		}
		if (currentPayInfoChanges.getNumberOfExemptionsChanged() && payroll.getAutoApprovePayInfo() && !payrollSame) {
			contents.append("Number of Exemptions\t\t" +payroll.getPayInfo().getNumberOfExemptions()+"\n");
			hasApprovChanges = true;
			hasPayInfoChanges = true;
		}
		
		int index = 0;
		
		for (BankChanges b: currentAccountInfoChanges) {
			boolean tempChanges = false;
			bankContents = new StringBuilder();
			
			if (b.getCodeChanged() && payroll.getAutoApproveAccountInfo() && !accountSame) {
				bankContents.append("Bank Account Information \t\t" +payroll.getAccountInfo().get(index).getCode().getDescription()+"\t"+payroll.getAccountInfo().get(index).getCode().getSubCode()+"\n");
				hasApprovChanges = true;
				hasAccountChanges = true;
				tempChanges = true;
			}
			if (b.getAccountNumberChanged() && payroll.getAutoApproveAccountInfo() && !accountSame) {
				bankContents.append("Bank Account Number\t\t" +payroll.getAccountInfo().get(index).getAccountNumberLabel()+"\n");
				hasApprovChanges = true;
				hasAccountChanges = true;
				tempChanges = true;
			}
			if (b.getAccountTypeChanged() && payroll.getAutoApproveAccountInfo() && !accountSame) {
				bankContents.append("Bank Account Type\t\t" +payroll.getAccountInfo().get(index).getAccountType().getDisplayLabel()+"\n");
				hasApprovChanges = true;
				hasAccountChanges = true;
				tempChanges = true;
			}
			if (b.getDepositAmountChanged() && payroll.getAutoApproveAccountInfo() && !accountSame) {
				bankContents.append("Bank Account Deposit Amount\t\t" +payroll.getAccountInfo().get(index).getDepositAmount().getDisplayAmount()+"\n");
				hasApprovChanges = true;
				hasAccountChanges = true;
				tempChanges = true;
			}
			
			if (tempChanges) {
				contents.append("Bank Account " +payroll.getAccountInfo().get(index).getCode().getDescription()+": \n");
				contents.append(bankContents);
			}
			
			index++;
			
		}
		if (hasApprovChanges) {
			messageContents.append(header);
			messageContents.append(contents);
		}

		header = "\nThe following request(s) requires documentation be provided: \n";
		contents = new StringBuilder();
		StringBuilder employeeMessageRequestReview = new StringBuilder();			//cs20140220 Add a section to list all changed items

		if (currentPayInfoChanges.getMaritalStatusChanged() && !payrollSame) {
			//contents.append("W4 Marital Status\t\t" +payroll.getPayInfo().getMaritalStatus().getDisplayLabel()+"\n");
			hasPayInfoChanges = true;
			employeeMessageRequestReview.append("W4 Marital Status\n");
			if (docRequired.getMaritalStatus()) {
				contents.append("W4 Marital Status\t\t" +payroll.getPayInfo().getMaritalStatus().getDisplayLabel()+"\n");
				hasDocChanges = true;
			}
		}
		if (currentPayInfoChanges.getNumberOfExemptionsChanged() && !payrollSame) {
			//contents.append("Number of Exemptions\t\t" +payroll.getPayInfo().getNumberOfExemptions()+"\n");
			hasPayInfoChanges = true;
			employeeMessageRequestReview.append("Number of Exemptions\n");
			if (docRequired.getNumberOfExemptions()) {
				contents.append("Number of Exemptions\t\t" +payroll.getPayInfo().getNumberOfExemptions()+"\n");
				hasDocChanges = true;
			}
		}
		
		index = 0;
		
		for (BankChanges b: currentAccountInfoChanges) {
			boolean tempChanges = false;
			bankContents = new StringBuilder();
			
			if (b.getCodeChanged() && !accountSame) {
				//bankContents.append("Bank Account Information \t\t" + payroll.getAccountInfo().get(index).getCode().getDescription()+"\t"+payroll.getAccountInfo().get(index).getCode().getSubCode()+"\n");
				hasAccountChanges = true;
				tempChanges = true;
				employeeMessageRequestReview.append("Bank Account Information\n");
				if (docRequired.getCode()) {
					bankContents.append("Bank Account Information \t\t" + payroll.getAccountInfo().get(index).getCode().getDescription()+"\t"+payroll.getAccountInfo().get(index).getCode().getSubCode()+"\n");
					hasDocChanges = true;
				}
			}
			if (b.getAccountNumberChanged() && !accountSame) {
				//bankContents.append("Bank Account Number \t\t" +payroll.getAccountInfo().get(index).getAccountNumberLabel()+"\n");
				hasAccountChanges = true;
				tempChanges = true;
				employeeMessageRequestReview.append("Bank Account Number\n");
				if (docRequired.getAccountNumber()) {
					bankContents.append("Bank Account Number \t\t" +payroll.getAccountInfo().get(index).getAccountNumberLabel()+"\n");
					hasDocChanges = true;
				}
			}
			if (b.getAccountTypeChanged() && !accountSame) {
				//bankContents.append("Bank Account Type\t\t" +payroll.getAccountInfo().get(index).getAccountType().getDisplayLabel()+"\n");
				hasAccountChanges = true;
				tempChanges = true;
				employeeMessageRequestReview.append("Bank Account Type\n");
				if (docRequired.getAccountType()) {
					bankContents.append("Bank Account Type\t\t" +payroll.getAccountInfo().get(index).getAccountType().getDisplayLabel()+"\n");
					hasDocChanges = true;
				}
			}
			if (b.getDepositAmountChanged() && !accountSame) {
				double amount = payroll.getAccountInfo().get(index).getDepositAmount().getAmount();
				boolean newprim = false;
				if (amount == 0) {
					newprim = true;
				}
				
				//if (newprim) {
				//	bankContents.append("Bank Account Deposit Amount\t\t" +payroll.getAccountInfo().get(index).getDepositAmount().getDisplayAmount()+" (New Primary Account)\n");
				//}
				//else {
				//	bankContents.append("Bank Account Deposit Amount\t\t" +payroll.getAccountInfo().get(index).getDepositAmount().getDisplayAmount()+"\n");
				//}
				hasAccountChanges = true;
				tempChanges = true;
				if (docRequired.getDepositAmount()) {
					if (newprim) {
						bankContents.append("Bank Account Deposit Amount\t\t" +payroll.getAccountInfo().get(index).getDepositAmount().getDisplayAmount()+" (New Primary Account)\n");
					} else {
						bankContents.append("Bank Account Deposit Amount\t\t" +payroll.getAccountInfo().get(index).getDepositAmount().getDisplayAmount()+"\n");
					}
					hasDocChanges = true;
				}
			}
			if (tempChanges) {
				contents.append("Bank Account " +payroll.getAccountInfo().get(index).getCode().getDescription()+": \n");
				contents.append(bankContents);
			}
			
			index++;
		}
		if (hasDocChanges) {
			messageContents.append(header);
		}
		if (hasPayInfoChanges || hasAccountChanges) {
			messageContents.append(contents);

			messageContents.append("\nThe following request(s) is pending to be reviewed:\n");
			messageContents.append(employeeMessageRequestReview.toString()); 
		}

		msg.setText(messageContents.toString());
			
		if (hasPayInfoChanges || hasAccountChanges) {
			if (!"".equals(userWorkEmail)) {
				msg.setTo(userWorkEmail);
				mailUtilService.sendMail(msg);
			}
			else if (!"".equals(userHomeEmail)) {
				msg.setTo(userHomeEmail);
				
				try{
					mailUtilService.sendMail(msg);
				} 
				catch(MailException ex) {
					log.info("An exception has occured with mailing the user.");
				} 
			}	
		}
		
		if (!payrollSame && accountSame)
			return 1;
		else if (payrollSame && !accountSame)
			return 2;
		else if (!payrollSame && !accountSame)
			return 3;
		
		return 0;
	}
	
	@Override
	public void sendApproverEmail(Integer emailChanges, String userFirstName, String userLastName, Payroll payroll,  PayInfo payInfoCurrent, List <Bank> accountInfoCurrent) {
		
		boolean payrollSame = comparePayInfo(payroll.getPayInfo(), payInfoCurrent);
		boolean accountSame = true;
		
		for(int i =0; i < payroll.getAccountInfo().size(); i++)
		{
			if(!accountSame)
				break;
			accountSame = compareBanks(payroll.getAccountInfo().get(i), accountInfoCurrent.get(i));
		}
		
		if((emailChanges == 1 || emailChanges == 3) && !payrollSame)
		{
			if(!payroll.getAutoApprovePayInfo())
			{
				userFirstName = userFirstName == null ? "" : userFirstName;
				userLastName = userLastName == null ? "" : userLastName;
				
				String employeeNumber  = payrollDao.getApproverEmployeeNumber(Frequency.getFrequency(payroll.getFrequency()).getCode(), "BEA_W4");
				User approver = new User();
				
				if(employeeNumber == null || "".equals(employeeNumber))
				{
					return;
				}
				approver = payrollDao.getApprover(employeeNumber);
				
				if(approver == null)
				{
					return;
				}
				
	
				String approverFirstName = approver.getFirstName()== null ? "" : approver.getFirstName();
				String approverLastName = approver.getLastName()== null ? "" : approver.getLastName();
				
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setSubject("A MESSAGE FROM SELF SERVICE");
				StringBuilder messageContents = new StringBuilder();
				messageContents.append(approverFirstName + " " + approverLastName + ", \n\n");
				messageContents.append(userFirstName + " " + userLastName + " has submitted a request to change payroll information. \n");
				messageContents.append("The request is ready for your approval. \n");
				messageContents.append("Login to Human Resources to approve. \n");
	
				msg.setText(messageContents.toString());
				
				if(!"".equals(approver.getWorkEmail()))
				{
					msg.setTo(approver.getWorkEmail());
											
					try{
						mailUtilService.sendMail(msg);
						} 
					catch(MailException ex) {
						log.info("An exception has occured with mailing the user.");
					} 
				}
					
				else if(!"".equals(approver.getHomeEmail()))
				{
					msg.setTo(approver.getHomeEmail());
					
					try{
						mailUtilService.sendMail(msg);
						} 
					catch(MailException ex) {
						log.info("An exception has occured with mailing the user.");
					} 
				}
			}
		}
		
		else if((emailChanges == 2 || emailChanges == 3) && !accountSame)
		{
			if(!payroll.getAutoApproveAccountInfo())
			{
				String employeeNumber  = payrollDao.getApproverEmployeeNumber(Frequency.getFrequency(payroll.getFrequency()).getCode(), "BEA_DRCT_DPST_BNK_ACCT");
				User approver = new User();
				
				if(employeeNumber == null || "".equals(employeeNumber))
				{
					return;
				}
				approver = payrollDao.getApprover(employeeNumber);
				
				if(approver == null)
				{
					return;
				}
	
				String approverFirstName = approver.getFirstName()== null ? "" : approver.getFirstName();
				String approverLastName = approver.getLastName()== null ? "" : approver.getLastName();
				
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setSubject("A MESSAGE FROM SELF SERVICE");
				StringBuilder messageContents = new StringBuilder();
				messageContents.append(approverFirstName == null ? "" : approverFirstName + " " + approverLastName == null ? "" : approverLastName + ", \n\n");
				messageContents.append(userFirstName + " " + userLastName+ "\t\t has submitted a request\n");
				messageContents.append("to change payroll information. \n");
				messageContents.append("The request is ready for your approval. \n");
				messageContents.append("Login to Human Resources to approve. \n");
				
				msg.setText(messageContents.toString());
				
				if(!"".equals(approver.getWorkEmail()))
					{
						msg.setTo(approver.getWorkEmail());
						
						try{
							mailUtilService.sendMail(msg);
							} 
						catch(MailException ex) {
							log.info("An exception has occured with mailing the user.");
						} 
					}
					
					else if(!"".equals(approver.getHomeEmail()))
					{
						msg.setTo(approver.getHomeEmail());
						
						try{
							mailUtilService.sendMail(msg);
							} 
						catch(MailException ex) {
							log.info("An exception has occured with mailing the user.");
						} 
					}
			}
		}
	}
	
	@Override
	public Boolean deleteCheck(Payroll payroll)
	{
		boolean hasDelete = false;
		
		for (Bank b : payroll.getAccountInfo())
		{
			if(b.getIsDelete())
			{
				hasDelete = true;
			}
		}
		
		return (hasDelete && payroll.getAutoApproveAccountInfo());
	}
	
	@Override
	public Boolean hasInvalidAccounts(List <Bank> accounts)
	{
		boolean hasInvalid = false;
		
		for(Bank b: accounts)
		{
			if(b.getInvalidAccount())
			{
				hasInvalid = true;
			}
		}
		
		return hasInvalid;
	}
	
	@Override
	public String getMessage()
	{
		Options o = optionsDao.getOptions();
		return o.getMessageSelfServicePayroll();
	}
	
	@Override
	public void clearErrors(MessageContext messageContext)
	{
		messageContext.clearMessages();
	}
	
	//***************************************private functions*********************************************************************//
	
	private void accountSwap( List<Bank> accountInfo,  List<Bank> current,  List<Bank> pending)
	{
		//if account is either added above or does not exist in HR Table in DB move these accounts to end of List
		int swapCount = 0;
		for(int i = 0; i < current.size(); i++)
		{
			if(isAddedAccount(current.get(i)) && i < current.size()-1 - swapCount)
			{	
				pending.add(pending.get(i));
				current.add(current.get(i));
				
				pending.remove(i);
				current.remove(i);
				swapCount++;
				i--;
			}
		}
		
		for(int i = 0; i < current.size(); i++)
		{
			//check to see if this is a blank row we just added to the arrays above. Then check to see if the current request bank code matches the hr account bank code
			if(!isAddedAccount(current.get(i)) && !accountInfo.get(i).getAccountNumber().equals(current.get(i).getAccountNumber()))
			{
				for(int j = 0; j < accountInfo.size(); j++)
				{
					//hr account matched with request account. now we rearrange the pending and current accounts to reflect the hr account order
					if(accountInfo.get(j).getAccountNumber().equals(current.get(i).getAccountNumber()))
					{
						Bank tempBankCurrent = current.get(i);
						Bank tempBankPending = pending.get(i);
						current.add(j+1, tempBankCurrent);
						pending.add(j+1, tempBankPending);
						if(j < i)
						{
							current.remove(i+1);
							pending.remove(i+1);
						}
						else
						{
							current.remove(i);
							pending.remove(i);
							i--;
						}
						break;
					}
				}
				
			}
		}
		
		//if account is a requested change and does not currently exist in HR Table in DB move these accounts to end of List
		swapCount = 0;
		for(int i = 0; i < pending.size(); i++)
		{
			if(!isAddedAccount(pending.get(i)) && isAddedAccount(current.get(i))&& i < current.size()-1 - swapCount)
			{	
				pending.add(pending.get(i));
				current.add(current.get(i));
				
				pending.remove(i);
				current.remove(i);
				swapCount++;
				i--;
			}
		}
		
		//moves invalid account requests to the end of the list
		for(int i = 0; i < current.size(); i++)
		{
			if(!isAddedAccount(current.get(i)) && !accountInfo.get(i).getAccountNumber().equals(current.get(i).getAccountNumber()) && !accountInfo.get(i).getInvalidAccount())
			{	
				accountInfo.add(createNewBank());
				pending.add(pending.get(i));
				current.add(current.get(i));
				
				pending.set(i, copyAccountInfo(accountInfo.get(i)));
				current.set(i, createNewBank());
				
				accountInfo.get(accountInfo.size()-1).setInvalidAccount(true);
				current.get(current.size()-1).setInvalidAccount(true);
				pending.get(pending.size()-1).setInvalidAccount(true);
			}
		}
	}
	
	private boolean checkForDuplicates( List <Bank> accountInfo, List <Bank> accountInfoPayroll)
	{
		for (int i=0; i < accountInfoPayroll.size(); i++)
		{
			for(int j=0; j < accountInfo.size(); j++)
			{
				if(accountInfoPayroll.get(i).getCode().getCode().equals(accountInfo.get(j).getCode().getCode()) 
				&& accountInfoPayroll.get(i).getAccountNumber().equals(accountInfo.get(j).getAccountNumber())
				&& !accountInfoPayroll.get(i).getCode().getCode().equals("") 
				&& !accountInfoPayroll.get(i).getAccountNumber().equals("")
				&& i != j)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	private int savePayInfoChanges(MessageContext messageContext,String employeeNumber, Payroll payroll, PayInfo payInfo, PayInfo payInfoPending)
	{
		payroll.getPayInfo().setMaritalStatus((Code)ReferenceDataService.getMaritalStatusFromDisplayLabel(payroll.getPayInfo().getMaritalStatus().getDisplayLabel()).clone());
		//check if row is marked for deletion, set all values to empty. Check if new row or not to insert/update. Then check if auto approve to insert/update.
		boolean payInfoSame = comparePayInfo(payroll.getPayInfo(), payInfo);
		boolean payInfoPendingSame = comparePayInfo(payroll.getPayInfo(), payInfoPending);
		boolean query = true;
		
		if(payInfoPendingSame)
			return 0;
		
		if(payrollDao.isNewPayInfoRow(employeeNumber, Frequency.getFrequency(payroll.getFrequency()), payInfo))
		{
			if(!payInfoSame)
			{
				query = payrollDao.insertPayInfoRequest(payroll.getAutoApprovePayInfo(), employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), payroll.getPayInfo(), payInfo);
				if(!query)
				{
					ContextValidationUtil.addMessage(messageContext, "", "No new requests have been created. The following group(s) experienced errors while saving: W4 Marital Status Information ");
					return -1;
				}
				if(payroll.getAutoApprovePayInfo())
				{
					query = payrollDao.updatePayInfoApprove(employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), payroll.getPayInfo(), payInfo);
					if(!query)
					{
						ContextValidationUtil.addMessage(messageContext, "", "No new requests have been created. The following group(s) experienced errors while saving: W4 Marital Status Information ");
						return -1;
					}
				}
			}
		}
		else
		{
			if(!payInfoSame)
			{
				query = payrollDao.updatePayInfoRequest(payroll.getAutoApprovePayInfo(), employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), payroll.getPayInfo(), payInfo);
				if(!query)
				{
					ContextValidationUtil.addMessage(messageContext, "", "No new requests have been created. The following group(s) experienced errors while saving: W4 Marital Status Information ");
					return -1;
				}
				if(payroll.getAutoApprovePayInfo())
				{
					query = payrollDao.updatePayInfoApprove(employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), payroll.getPayInfo(), payInfo);
					if(!query)
					{
						ContextValidationUtil.addMessage(messageContext, "", "No new requests have been created. The following group(s) experienced errors while saving: W4 Marital Status Information ");
						return -1;
					}
				}
			}
			else
			{
				query = payrollDao.deletePayInfoRequest(employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), payroll.getPayInfo(), payInfo);
				if(!query)
				{	
					ContextValidationUtil.addMessage(messageContext, "", "No new requests have been created. The following group(s) experienced errors while saving: W4 Marital Status Information");
					return -1;
				}
			}
		}
		
		return 1;
	}
	private int saveAccountChanges(MessageContext messageContext, List <Boolean> isNewRow, String employeeNumber, Payroll payroll, List <Bank> accountInfo,List <Bank> accountInfoPending, List<Bank> hrAccountsTemp)
	{
		boolean accountInfoAllSame = true;
		boolean query = true;
		
		for(int i = 0; i < payroll.getAccountInfo().size(); i++)
		{	
				//skip evaluation of invalid accounts
				if(accountInfo.get(i).getInvalidAccount())
					continue;
			
				//makes sure account type code / description match up is correct
				payroll.getAccountInfo().get(i).setAccountType((Code)ReferenceDataService.getDdAccountTypeFromDisplayLabel(payroll.getAccountInfo().get(i).getAccountType().getDisplayLabel()).clone());
			
				//check if account is marked for deletion 
				if(payroll.getAccountInfo().get(i).getIsDelete())
				{
					payroll.getAccountInfo().set(i, createNewBank());
					payroll.getAccountInfo().get(i).setIsDelete(true);
				}
				
				//if not marked for deletion check for deletion values
				else if(payroll.getAccountInfo().get(i).getAccountNumber().equals("") && payroll.getAccountInfo().get(i).getCode().getCode().equals("") 
						&& payroll.getAccountInfo().get(i).getAccountType().getCode().equals("") && payroll.getAccountInfo().get(i).getDepositAmount().getAmount() <= 0)
				{
					//if account ha just been added remove from list of accounts
					if(isAddedAccount(accountInfo.get(i)) && isNewRow.get(i) && isAddedAccount(accountInfoPending.get(i)))
					{
						payroll.getAccountInfo().remove(i);
						accountInfo.remove(i);
						accountInfoPending.remove(i);
						isNewRow.remove(i);
						i--;
						continue;
					}
					//if account already exists throw a late validation message informing the user they cant set the values to be the same as if deleting the row
					else if(!isAddedAccount(accountInfo.get(i)) && isNewRow.get(i) && isAddedAccount(accountInfoPending.get(i)))
					{
						ContextValidationUtil.addMessage(messageContext, "accountInfo["+i+"].code.code", "Cannot have empty values for bank account. If you wish to request deletion of account, please select the trashcan.");
						payrollDao.rollbackUpdate();
						return -1;
					}
					
				}
				
				//check if current hr bank and pending request match
				boolean accountInfoSame = compareBanks(payroll.getAccountInfo().get(i), accountInfo.get(i));
				
				//check if any changes have been made to pending since retrieval
				boolean accountInfoPendingSame;
				if(accountInfoPending.size() >= i+1)
					accountInfoPendingSame= compareBanks(payroll.getAccountInfo().get(i), accountInfoPending.get(i));
				else
					accountInfoPendingSame = false;
				
				//if changes have been made since retrieval
				if(!accountInfoPendingSame)
				{
					accountInfoAllSame = false;
					//check if the row exists or not in the DB as a pending request already
					if(isNewRow.get(i))
					{
						//if auto approve is true recheck to make sure HR data has not changed since retrieval
						if(payroll.getAutoApproveAccountInfo() && isAddedAccount(accountInfo.get(i)))
						{
							boolean invalidData= false;
							for (Bank b: hrAccountsTemp)
							{
								if (compareBanks(b, accountInfo.get(i)))
								{
									invalidData = true;
								}
							}
							if(invalidData)
							{
								ContextValidationUtil.addMessage(messageContext, "", "No new requests have been created. One or more requests are no longer valid due to an account information change by an administrator. If you have any questions please contact"+
						    	" your personnel office for more information.");
								return -1;
							}
						}
						else if(payroll.getAutoApproveAccountInfo() && !isAddedAccount(accountInfo.get(i)))
						{
							boolean invalidData= true;
							for (Bank b: hrAccountsTemp)
							{
								if (compareBanks(b, accountInfo.get(i)))
								{
									invalidData = false;
								}
							}
							if(invalidData)
							{
								ContextValidationUtil.addMessage(messageContext, "", "No new requests have been created. One or more requests are no longer valid due to an account information change by an administrator. ");
								ContextValidationUtil.addMessage(messageContext, "", "If you have any questions please contact your personnel office for more information.");
								return -1;
							}
						}
						
						//hr bank and pending info don't match
						if(!accountInfoSame)
						{							
							//insert new request in to ea table
							query = payrollDao.insertAccountRequest(payroll.getAutoApproveAccountInfo(), employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), payroll.getAccountInfo().get(i), accountInfo.get(i));
							if(!query)
							{
								ContextValidationUtil.addMessage(messageContext, "", "No new requests have been created. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
								return -1;
							}
							
							//if auto approve update HR tables
							if(payroll.getAutoApproveAccountInfo())
							{
								Options o = optionsDao.getOptions();
								String prenote="";
								
								if(o.getPreNote().equals("Y"))
								{
									prenote = "P";
								}
								
								//if new account insert in to hr table
								if(isAddedAccount(accountInfo.get(i)) && !payroll.getAccountInfo().get(i).getIsDelete())
								{
									query = payrollDao.insertAccountApprove(employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), prenote, payroll.getAccountInfo().get(i));
									if(!query)
									{
										ContextValidationUtil.addMessage(messageContext, "", "No changes have been saved. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
										return -1;
									}
									
									int queryInt = payrollDao.deleteNextYearAccounts(employeeNumber);
									if(queryInt == -1)
									{
										ContextValidationUtil.addMessage(messageContext, "", "No changes have been saved. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
										return -1;
									}
									if(queryInt == 1)
									{
										queryInt = payrollDao.insertNextYearAccounts(employeeNumber);
										if(queryInt == -1)
										{
											ContextValidationUtil.addMessage(messageContext, "", "No changes have been saved. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
											return -1;
										}
									}
								}
								//if existing account update existing hr account
								else if(!isAddedAccount(accountInfo.get(i)) && !payroll.getAccountInfo().get(i).getIsDelete())
								{
									query = payrollDao.updateAccountApprove(employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), prenote, payroll.getAccountInfo().get(i), accountInfo.get(i));
									if(!query)
									{
										ContextValidationUtil.addMessage(messageContext, "", "No new requests have been created. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
										return -1;
									}
									
									int queryInt = payrollDao.deleteNextYearAccounts(employeeNumber);
									if(queryInt == -1)
									{
										ContextValidationUtil.addMessage(messageContext, "", "No changes have been saved. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
										return -1;
									}
									
									if(queryInt == 1)
									{
										queryInt = payrollDao.insertNextYearAccounts(employeeNumber);
										if(queryInt == -1)
										{
											ContextValidationUtil.addMessage(messageContext, "", "No changes have been saved. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
											return -1;
										}
									}
								}
								else if(payroll.getAccountInfo().get(i).getIsDelete())
								{
									query = payrollDao.deleteAccountApprove(employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), accountInfo.get(i));
									if(!query)
									{
										ContextValidationUtil.addMessage(messageContext, "", "No changes have been saved. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
										return -1;
									}
								}
							}
						
						}
					}
					//if existing request in ea table
					else
					{
						//if auto approve is true recheck to make sure HR data has not changed since retrieval
						if(payroll.getAutoApproveAccountInfo() && isAddedAccount(accountInfo.get(i)))
						{
							boolean invalidData= false;
							for (Bank b: hrAccountsTemp)
							{
								if (compareBanks(b, accountInfo.get(i)))
								{
									invalidData = true;
								}
							}
							if(invalidData)
							{
								ContextValidationUtil.addMessage(messageContext, "", "No new requests have been created. One or more requests are no longer valid due to an account information change by an administrator. If you have any questions please contact"+
						    	" your personnel office for more information.");
								return -1;
							}
						}
						else if(payroll.getAutoApproveAccountInfo() && !isAddedAccount(accountInfo.get(i)))
						{
							boolean invalidData= true;
							for (Bank b: hrAccountsTemp)
							{
								if (compareBanks(b, accountInfo.get(i)))
								{
									invalidData = false;
								}
							}
							if(invalidData)
							{
								ContextValidationUtil.addMessage(messageContext, "", "No new requests have been created. One or more requests are no longer valid due to an account information change by an administrator. If you have any questions please contact"+
						    	" your personnel office for more information.");
								return -1;
							}
						}
						
						//hr bank and pending info don't match
						if(!accountInfoSame)
						{
							query = payrollDao.updateAccountRequest(payroll.getAutoApproveAccountInfo(), employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), payroll.getAccountInfo().get(i),  accountInfo.get(i), accountInfoPending.get(i));
							if(!query)
								return -1;
							
							//if auto approve update HR tables
							if(payroll.getAutoApproveAccountInfo())
							{
								Options o = optionsDao.getOptions();
								String prenote="";
								
								if(o.getPreNote().equals("Y"))
								{
									prenote = "P";
								}
								//if new account insert in to hr table
								if(isAddedAccount(accountInfo.get(i)) && !payroll.getAccountInfo().get(i).getIsDelete())
								{
									query = payrollDao.insertAccountApprove(employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), prenote, payroll.getAccountInfo().get(i));
									if(!query)
									{
										ContextValidationUtil.addMessage(messageContext, "", "No changes have been saved. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
										return -1;
									}
									int queryInt = payrollDao.deleteNextYearAccounts(employeeNumber);
									if(queryInt == -1)
									{
										ContextValidationUtil.addMessage(messageContext, "", "No changes have been saved. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
										return -1;
									}
									if(queryInt == 1)
										{
										queryInt = payrollDao.insertNextYearAccounts(employeeNumber);
										if(queryInt == -1)
										{
											ContextValidationUtil.addMessage(messageContext, "", "No changes have been saved. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
											return -1;
										}
									}
								}
								//if existing account update existing hr account
								else if(!isAddedAccount(accountInfo.get(i)) && !payroll.getAccountInfo().get(i).getIsDelete())
								{
									query = payrollDao.updateAccountApprove(employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), prenote, payroll.getAccountInfo().get(i), accountInfo.get(i));
									if(!query)
									{
										ContextValidationUtil.addMessage(messageContext, "", "No changes have been saved. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
										return -1;
									}
									
									int queryInt = payrollDao.deleteNextYearAccounts(employeeNumber);
									if(queryInt == -1)
									{
										ContextValidationUtil.addMessage(messageContext, "", "No changes have been saved. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
										return -1;
									}
									if(queryInt == 1)
									{
										queryInt = payrollDao.insertNextYearAccounts(employeeNumber);
										if(queryInt == -1)
										{
											ContextValidationUtil.addMessage(messageContext, "", "No changes have been saved. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
											return -1;
										}
									}
								}
								else if(payroll.getAccountInfo().get(i).getIsDelete())
								{
									query = payrollDao.deleteAccountApprove(employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), accountInfo.get(i));
									if(!query)
									{
										ContextValidationUtil.addMessage(messageContext, "", "No changes have been saved. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
										return -1;
									}
								}
							}
						}
						else if(accountInfoSame || payroll.getAccountInfo().get(i).getIsDelete())
						{
							query = payrollDao.deleteAccountRequest(employeeNumber, Frequency.getFrequency(payroll.getFrequency()).getCode(), payroll.getAccountInfo().get(i),  accountInfo.get(i), accountInfoPending.get(i));
							if(!query)
							{
								ContextValidationUtil.addMessage(messageContext, "", "No new requests have been created. The following group(s) experienced errors while saving: Direct Deposit Bank Accounts ");
								return -1;
							}
						}
					}
				}
			}
		
			if(accountInfoAllSame)
				return 0;
			else
				return 1;
	}
	
	private Bank createNewBank()
	{
		Bank tempBank = new Bank();
		tempBank.setAccountNumber("");
		tempBank.setAccountType(new Code());
		tempBank.setCode(new Code());
		tempBank.setDepositAmount(new Money(0, Currency.getInstance(Locale.US)));
		
		return tempBank;
	}
	
	private Bank copyAccountInfo(Bank accountInfo)
	{
		Bank tempBank = new Bank();
		tempBank.setAccountNumber(accountInfo.getAccountNumber());
		tempBank.setAccountType((Code)ReferenceDataService.getDdAccountTypeFromCode(accountInfo.getAccountType().getCode()).clone());
		tempBank.setCode((Code)ReferenceDataService.getBankCodesFromCode(accountInfo.getCode().getCode()).clone());
		tempBank.setDepositAmount(new Money(accountInfo.getDepositAmount().getAmount(), Currency.getInstance(Locale.US)));
		tempBank.setInvalidAccount(accountInfo.getInvalidAccount());
		tempBank.setIsDelete(accountInfo.getIsDelete());	
		
		return tempBank;
	}
	

	private boolean compareBanks(Bank payrollAccountInfo, Bank accountInfo)
	{
		boolean banksSame = true;
			
		if(!payrollAccountInfo.getAccountNumber().equals(accountInfo.getAccountNumber()))
		{
			banksSame = false;
		}
		if(!payrollAccountInfo.getAccountType().getCode().equals(accountInfo.getAccountType().getCode()))
		{
			banksSame = false;
		}
		if(payrollAccountInfo.getDepositAmount().getAmount() != (accountInfo.getDepositAmount().getAmount()))
		{
			banksSame = false;
		}
		if(!payrollAccountInfo.getCode().getCode().equals(accountInfo.getCode().getCode()))
		{
			banksSame = false;
		}
			
		return banksSame;
	}
	
	private boolean comparePayInfo(PayInfo payrollPayInfo, PayInfo payInfo)
	{
		boolean payInfoSame = true;
		
		if(!payrollPayInfo.getMaritalStatus().getCode().equals(payInfo.getMaritalStatus().getCode()))
		{
			payInfoSame = false;
		}
		if(!payrollPayInfo.getNumberOfExemptions().equals(payInfo.getNumberOfExemptions()))
		{
			payInfoSame = false;
		}
			
		return payInfoSame;
	}
	
	private boolean isAddedAccount(Bank accountInfo)
	{
		boolean isAdded = true;
		
		if(!"".equals(accountInfo.getAccountNumber()))
		{
			isAdded = false;
		}
		if(!"".equals(accountInfo.getAccountType().getCode()))
		{
			isAdded = false;
		}
		if(accountInfo.getDepositAmount().getAmount() > 0)
		{
			isAdded = false;
		}
		if(!"".equals(accountInfo.getCode().getCode()))
		{
			isAdded = false;
		}
		
		return isAdded;
	}
	
	private PayrollFields getDocumentationRequiredFields(String frequency)
	{
		List <List <String>> result = payrollDao.getDocRequiredFields(frequency);
		
		PayrollFields req = new PayrollFields();
		
		for(List <String> entry: result)
		{
			if("BEA_W4".equals(entry.get(0)))
			{
				if("Marital Status".equals(entry.get(1)))
				{
					req.setMaritalStatus(entry.get(2));
				}
				
				else if("Nbr of Exemptions".equals(entry.get(1)))
				{
					req.setNumberOfExemptions(entry.get(2));
				}
			}
			else if("BEA_DRCT_DPST_BNK_ACCT".equals(entry.get(0)))
			{
				if("Bank".equals(entry.get(1)))
				{
					req.setCode(entry.get(2));
				}
				
				else if("Bank Account Nbr".equals(entry.get(1)))
				{
					req.setAccountNumber(entry.get(2));
				}
				
				else if("Bank Account Type".equals(entry.get(1)))
				{
					req.setAccountType(entry.get(2));
				}
				
				else if("Bank Account Amount".equals(entry.get(1)))
				{
					req.setDepositAmount(entry.get(2));
				}
			}
		}
		
		return req;
	}

	public IMailUtilService getMailUtilService() {
		return mailUtilService;
	}

	public void setMailUtilService(IMailUtilService mailUtilService) {
		this.mailUtilService = mailUtilService;
	}
	
	
	
}