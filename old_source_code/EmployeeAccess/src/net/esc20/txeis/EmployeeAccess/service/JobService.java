package net.esc20.txeis.EmployeeAccess.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.dao.api.IEmployeeDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IJobDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IOptionsDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IPayDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IPayrollAccountDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IStipendDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IUserDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Bank;
import net.esc20.txeis.EmployeeAccess.domainobject.EmployeeInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.Job;
import net.esc20.txeis.EmployeeAccess.domainobject.Options;
import net.esc20.txeis.EmployeeAccess.domainobject.PayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.Stipend;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.PayPrint;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.report.IReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ParameterReport;
import net.esc20.txeis.EmployeeAccess.service.api.IJobService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService implements IJobService
{
	private static Log log = LogFactory.getLog(JobService.class);
	
	private IJobDao jobDao;
	private IStipendDao stipendDao;
	private IOptionsDao optionsDao;
	private IPayrollAccountDao payrollDao;
	private IPayDao payDao;
	private IUserDao userDao;
	private IEmployeeDao employeeDao;
	
	@Autowired
	public JobService(IJobDao jobDao, 
			IStipendDao stipendDao, 
			IOptionsDao optionsDao, 
			IPayrollAccountDao payrollDao, 
			IPayDao payDao, 
			IUserDao userDao,
			IEmployeeDao employeeDao)
	{
		this.jobDao = jobDao;
		this.stipendDao = stipendDao;
		this.optionsDao = optionsDao;
		this.payrollDao = payrollDao;
		this.payDao = payDao;
		this.userDao = userDao;
		this.employeeDao = employeeDao;
	}
	
	@Override
	public List<Frequency> getFrequencies(Map<Frequency,?> map)
	{
		List<Frequency> frequencies = new ArrayList<Frequency>(map.keySet());
		Collections.sort(frequencies);
		return frequencies;
	}
	
	@Override
	public Map<Frequency,List<Job>> retrieveJobs(String employeeNumber)
	{
		List<Job> jobs = jobDao.getJobs(employeeNumber);
		Map<Frequency,List<Job>> jobMap = new HashMap<Frequency,List<Job>>();
		
		for(Job job : jobs)
		{
			if(jobMap.get(job.getFrequency()) == null)
			{
				jobMap.put(job.getFrequency(), new ArrayList<Job>());
			}
			
			if(!"XTRA".equals(job.getTitle().getCode()))
			{
				jobMap.get(job.getFrequency()).add(job);
			}
		}
		
		return jobMap;
	}
	
	@Override
	public Map<Frequency,List<Stipend>> retrieveStipends(String employeeNumber)
	{
		List<Stipend> stipends = stipendDao.getStipends(employeeNumber);
		Map<Frequency,List<Stipend>> stipendMap = new HashMap<Frequency,List<Stipend>>();
		
		for(Stipend stipend : stipends)
		{
			if(stipendMap.get(stipend.getFrequency()) == null)
			{
				stipendMap.put(stipend.getFrequency(), new ArrayList<Stipend>());
			}
			
			stipendMap.get(stipend.getFrequency()).add(stipend);
		}
		
		return stipendMap;
	}
	
	@Override
	public Map<Frequency,List<Bank>> retrieveAccounts(String employeeNumber)
	{
		List<Bank> banks = payrollDao.getAccounts(employeeNumber);
		Map<Frequency,List<Bank>> bankMap = new HashMap<Frequency,List<Bank>>();
		
		for(Bank bank : banks)
		{
			if(bankMap.get(bank.getFrequency()) == null)
			{
				bankMap.put(bank.getFrequency(), new ArrayList<Bank>());
			}
			
			bankMap.get(bank.getFrequency()).add(bank);
		}
		
		return bankMap;
	}
	
	@Override
	public Map<Frequency,PayInfo> retrievePayInfo(String employeeNumber, List<Frequency> frequencies)
	{
		Map<Frequency,PayInfo> payMap = new HashMap<Frequency,PayInfo>();
		
		for(Frequency frequency : frequencies)
		{
			try
			{
				payMap.put(frequency, payDao.getPayInfo(employeeNumber, frequency));
			}
			catch(Exception ex)
			{
				log.error(ex);
			}
		}
		
		return payMap;
	}
	
	@Override
	public Map<Frequency,String> retrievePayCampuses(String employeeNumber)
	{
		return employeeDao.getPayCampuses(employeeNumber);
	}
	
	@Override
	public EmployeeInfo retrieveEmployeeInfo(String employeeNumber)
	{
		EmployeeInfo empInfo= employeeDao.getEmployeeInfo(employeeNumber);
		if(empInfo.getDegree() == null || empInfo.getDegree().getCode().equals("")){
			Code degree = new Code();
				degree.setCode("");
				degree.setDescription("");
				empInfo.setDegree(degree);
		}
		return empInfo;
	}
	
	@Override
	public User retrieveUser(String employeeNumber)
	{
		User us= userDao.retrieveEmployee(employeeNumber);
		if(us.getPhoneNumber() == null){
			us.setPhoneNumber("");
		}
		return us;
	}
	
	@Override
	public String getMessage()
	{
		Options o = optionsDao.getOptions();
		return o.getMessageCurrentPayInformation();
	}
	//jf20120724 Print Report on Current Pay Information screen
	@Override
	public PayPrint generatePayPrint(User user, EmployeeInfo employeeInfo)
	{
		PayPrint print = new PayPrint();
		
		print.setDname(user.getEmployer().getName());
		print.setDaddress(user.getEmployer().getAddress());
		print.setDcityst(user.getEmployer().getCity() + ", " + user.getEmployer().getState() + " " + user.getEmployer().getZip());
		
		if(user.getEmployer().getZip4().length() > 0)
		{
			print.setDcityst(print.getDcityst() + "-" + user.getEmployer().getZip4());
		}
		
		String middleName = user.getMiddleName().trim();
		if (middleName.length() > 0) {
			middleName = middleName + " ";
		} else {
			middleName = "";
		}
		
		print.setEname(user.getFirstName() + " " + middleName + user.getLastName() + " " + user.getGenerationDescr());  //jf20150113 Display description instead of code fix 
		print.setEaddress(user.getAddress());
		print.setEcityst(user.getCity() + ", " + user.getState() + " " + user.getZipCode());
		
		if(user.getZipCode4().length() > 0)
		{
			print.setEcityst(print.getEcityst() + "-" + user.getZipCode4());
		}
		
		print.setPhoneNumber(user.getPhoneNumber());
		print.setEmployeeNumber(user.getEmployeeNumber());
		print.setDateOfBirth(user.getFormattedDateOfBirth());
		
		String gender;
		if (user.getGender() == User.Gender.Male) {
			gender = "Male";
		} else if (user.getGender() == User.Gender.Female) {
			gender = "Female";
		} else {
			gender = "";
		}
		print.setGender(gender);
		
		if (employeeInfo.getDegree() != null) {
			if (employeeInfo.getDegree().getDescription() != null) {
				print.setDegree(employeeInfo.getDegree().getDescription());
			}
		}
		if (employeeInfo.getProExperience() != null) {
			print.setProExperience(employeeInfo.getProExperience());
		}
		if (employeeInfo.getNonProExperience() != null) {
			print.setNonProExperience(employeeInfo.getNonProExperience());
		}
		if (employeeInfo.getProExperienceDistrict() != null) {
			print.setProExperienceDistrict(employeeInfo.getProExperienceDistrict());
		}
		if (employeeInfo.getNonProExperienceDistrict() != null) {
			print.setNonProExperienceDistrict(employeeInfo.getNonProExperienceDistrict());
		}
		
		return print;
	}
	//jf20120724 Print Report on Current Pay Information screen
	@Override
	public IReport setupReport(ParameterReport report, PayPrint parameters) 
	{
		report.getParameters().clear();
		List<PayPrint> forms = new ArrayList<PayPrint>();
		forms.add(parameters);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(forms);
		// Set the JRXML report
		report.setFileName("DRptPay");

		report.setDataSource(dataSource);

		return report;
	}
}