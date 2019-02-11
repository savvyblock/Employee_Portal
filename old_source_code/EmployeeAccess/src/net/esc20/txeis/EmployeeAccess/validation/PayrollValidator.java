package net.esc20.txeis.EmployeeAccess.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.domainobject.Bank;
import net.esc20.txeis.EmployeeAccess.domainobject.PayrollFields;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.service.ReferenceDataService;
import net.esc20.txeis.EmployeeAccess.service.api.IPayrollService;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;
import net.esc20.txeis.EmployeeAccess.web.view.Payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.stereotype.Component;

@Component
public class PayrollValidator extends AbstractValidator
{
	private static final long serialVersionUID = -1023249528812172648L;
	
	private IPayrollService payrollService;
	
	@Autowired
	public PayrollValidator(IPayrollService payrollService)
	{
		this.payrollService = payrollService;
	}
	
	@Override
	protected Map<String, String> createTypeValidationMap() {
		return new HashMap<String,String>();
	}
	
	public void validatePayrollStartView(Payroll o, ValidationContext context) {
		super.validateView(context);
		
		PayrollFields requiredFields = payrollService.getRequiredFields(o.getFrequency());
		int numDelete = 0;
		boolean isEmptyValues = false;
		
		for(Bank b : o.getAccountInfo())
		{
			if(b.getIsDelete())
				numDelete++;
		}
		
		if(requiredFields.getCode() && (o.getAccountInfo().size() - numDelete == 0))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "", "At least one bank account is required. Please add a valid account or reset one marked for deletion.");
		}
		else if(o.getAccountInfo().size() > 0)
		{
		
			if(o.getPayInfo().getNumberOfExemptions() == null && requiredFields.getNumberOfExemptions())
			{
				ContextValidationUtil.addMessage(context.getMessageContext(), "numberOfExemptions", "Please enter a valid number of exemptions.");
			}
			
			if(o.getPrimary() == null || "".equals(o.getPrimary()))
			{
				ContextValidationUtil.addMessage(context.getMessageContext(), "", "Please select a primary account.");
			}
			else
			{
				Integer primary = new Integer(o.getPrimary());
				List<Code> banks = payrollService.getAvailableBanks();
	
				if(o.getAccountInfo().get(primary).getIsDelete() && ((o.getAccountInfo().size() > 1 && !(o.getAccountInfo().size() - numDelete == 0) ) || requiredFields.getCode()))
				{
					ContextValidationUtil.addMessage(context.getMessageContext(), "accountInfo["+o.getPrimary()+"].code.subCode", "Please select another account as your primary one before deleting this account.");
				}
				
				for(int i = 0; i < o.getAccountInfo().size(); i++)
				{
					
					if("".equals(o.getAccountInfo().get(i).getCode().getCode()) && "".equals(o.getAccountInfo().get(i).getAccountNumber()) && 
					   "".equals(o.getAccountInfo().get(i).getAccountType().getCode()) && o.getAccountInfo().get(i).getDepositAmount().getAmount() <=0)
					{
						isEmptyValues = true;
					}
					
					if(primary == i && isEmptyValues && ((o.getAccountInfo().size() > 1 && !(o.getAccountInfo().size() - numDelete == 0) ) || requiredFields.getCode()))
					{
						ContextValidationUtil.addMessage(context.getMessageContext(), "accountInfo["+o.getPrimary()+"].code.subCode", "Please select another account as your primary one before deleting this account.");
					}
					

					else if(!isEmptyValues && !o.getAccountInfo().get(i).getInvalidAccount())
					{
						//Account Code Validation
						if("".equals(o.getAccountInfo().get(i).getCode().getCode()) && !o.getAccountInfo().get(i).getIsDelete())
						{
							ContextValidationUtil.addMessage(context.getMessageContext(), "accountInfo["+i+"].code.subCode", "Bank Account may not be blank. Please choose a valid account.");
						}
						
						boolean bankFound = false;
						
						for(Code currentBank: banks)
						{
							if(currentBank.getSubCode().equals(o.getAccountInfo().get(i).getCode().getCode()))
							{
								bankFound = true;
								break;
							}
						}
						
						if(!bankFound)
						{
							ContextValidationUtil.addMessage(context.getMessageContext(), "accountInfo["+i+"].code.subCode", "Bank Account is not valid. Please choose a valid account.");
						}
						
						//Account Number Validation
						if("".equals(o.getAccountInfo().get(i).getAccountNumber()) && !o.getAccountInfo().get(i).getIsDelete())
						{
							ContextValidationUtil.addMessage(context.getMessageContext(), "accountInfo["+i+"].accountNumber", "Account number may not be blank.");
						}
						else if(!o.getAccountInfo().get(i).getIsDelete())
						{
							int accountNumLength = o.getAccountInfo().get(i).getAccountNumber().length();
							
							if(o.getAccountInfo().get(i).getAccountNumber().length() > 17)
							{
								ContextValidationUtil.addMessage(context.getMessageContext(), "accountInfo["+i+"].accountNumber", "Account number may not be more than 17 characters in length.");
							}
							else if(!o.getAccountInfo().get(i).getIsDelete() && (!StringUtil.isAlphaNumeric(o.getAccountInfo().get(i).getAccountNumber().substring(0, accountNumLength/2)) || !StringUtil.isAlphaNumeric(o.getAccountInfo().get(i).getAccountNumber().substring(accountNumLength/2, accountNumLength))))
							{
								ContextValidationUtil.addMessage(context.getMessageContext(), "accountInfo["+i+"].accountNumber", "Account number must be alpha-numeric.");
							}
						}
						
						o.getAccountInfo().get(i).setAccountType((Code)ReferenceDataService.getDdAccountTypeFromDisplayLabel(o.getAccountInfo().get(i).getAccountType().getDisplayLabelActual()).clone());
						
						
						//Account Type Validation
						if("".equals(o.getAccountInfo().get(i).getAccountType().getDisplayLabel()) && !o.getAccountInfo().get(i).getIsDelete())
						{
							ContextValidationUtil.addMessage(context.getMessageContext(), "accountInfo["+i+"].accountType.displayLabel", "Account type may not be blank.");
						}
						
						//Deposit Amount Validation
						if(o.getAccountInfo().get(i).getDepositAmount().getAmount() <= 0 && i != primary && !o.getAccountInfo().get(i).getIsDelete())
						{
							ContextValidationUtil.addMessage(context.getMessageContext(), "accountInfo["+i+"].depositAmount.displayAmount", "Secondary accounts must have a deposit amount greater than $0.00.");
						}
						
						if(o.getAccountInfo().get(i).getDepositAmount().getAmount() > 99999 && !!o.getAccountInfo().get(i).getIsDelete())
						{
							ContextValidationUtil.addMessage(context.getMessageContext(), "accountInfo["+i+"].depositAmount.displayAmount", "Secondary accounts must have a deposit amount less than $100,000.");
						}
						
					}
					
					
				}
			}
			
		}
		
	}
}