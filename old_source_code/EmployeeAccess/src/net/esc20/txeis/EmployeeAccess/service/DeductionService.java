package net.esc20.txeis.EmployeeAccess.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.dao.api.IDeductionDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IOptionsDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IPayDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Deduction;
import net.esc20.txeis.EmployeeAccess.domainobject.Options;
import net.esc20.txeis.EmployeeAccess.domainobject.PayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.service.api.IDeductionService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeductionService implements IDeductionService
{
	private static Log log = LogFactory.getLog(DeductionService.class);
	
	private IDeductionDao deductionDao;
	private IPayDao payDao;
	private IOptionsDao optionsDao;
	
	@Autowired
	public DeductionService(IDeductionDao deductionDao, IPayDao payDao, IOptionsDao optionsDao)
	{
		this.deductionDao = deductionDao;
		this.payDao = payDao;
		this.optionsDao = optionsDao;
	}
	
	@Override
	public Frequency getInitialFrequency(List<Frequency> frequencies)
	{
		if(frequencies != null && frequencies.size() > 0)
		{
			return frequencies.get(0);
		}
		
		return null;
	}
	
	@Override
	public Map<Frequency,List<Deduction>> retrieveAllDeductions(String employeeNumber)
	{
		List<Frequency> frequencies = getAvailableFrequencies(employeeNumber);
		Map<Frequency,List<Deduction>> deductions = new HashMap<Frequency, List<Deduction>>();
		
		for(Frequency frequency : frequencies)
		{
			if(deductions.get(frequency) == null)
			{
				deductions.put(frequency, new ArrayList<Deduction>());
			}
			
			deductions.get(frequency).addAll(retrieveDeductions(employeeNumber,frequency));
		}
		
		return deductions;
	}
	
	@Override
	public List<Deduction> retrieveDeductions(String employeeNumber, Frequency frequency)
	{
		if(frequency != null)
		{
			return deductionDao.getDeductions(employeeNumber, frequency);
		}
		else
		{
			return new ArrayList<Deduction>();
		}
	}
	
	public Map<Frequency,PayInfo> retrieveAllPayInfo(String employeeNumber)
	{
		List<Frequency> frequencies = getAvailableFrequencies(employeeNumber);
		Map<Frequency,PayInfo> infos = new HashMap<Frequency,PayInfo>();
		
		for(Frequency frequency : frequencies)
		{
			infos.put(frequency, retrievePayInfo(employeeNumber,frequency));
		}
		
		return infos;
	}
	
	@Override
	public PayInfo retrievePayInfo(String employeeNumber, Frequency frequency)
	{
		if(frequency != null)
		{
			try
			{
				return payDao.getPayInfo(employeeNumber, frequency);
			}
			catch(Exception ex)
			{
				log.error("Could not retrieve the pay information correctly.",ex);
				return new PayInfo();
			}
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public String getMessage()
	{
		Options o = optionsDao.getOptions();
		return o.getMessageDeductions();
	}
	
	@Override
	public List<Frequency> getAvailableFrequencies(String employeeNumber)
	{
		return deductionDao.getAvailableFrequencies(employeeNumber);
	}
}