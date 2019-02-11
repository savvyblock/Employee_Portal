package net.esc20.txeis.EmployeeAccess.service;

import java.util.ArrayList;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;
import net.esc20.txeis.EmployeeAccess.service.api.ICodeService;

import org.springframework.stereotype.Service;

@Service
public class CodeService implements ICodeService
{
	public CodeService()
	{
		
	}
	
	public List<ICode> getFrequencies()
	{
		List<ICode> frequencies = new ArrayList<ICode>();
		
		frequencies.add(new Code("4", "Biweekly"));
		frequencies.add(new Code("5", "Semimonthly"));
		frequencies.add(new Code("6", "Monthly"));
		
		return frequencies;
	}
	
	public List<ICode> getFrequenciesLeave()
	{
		List<ICode> frequencies = new ArrayList<ICode>();
		
		frequencies.add(new Code("4", "Biweekly Leave Balances"));
		frequencies.add(new Code("5", "Semimonthly Leave Balances"));
		frequencies.add(new Code("6", "Monthly Leave Balances"));
		
		return frequencies;
	}
}