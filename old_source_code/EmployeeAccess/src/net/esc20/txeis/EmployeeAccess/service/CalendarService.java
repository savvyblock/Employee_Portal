package net.esc20.txeis.EmployeeAccess.service;

import java.util.ArrayList;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.dao.api.ICalendarDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IOptionsDao;
import net.esc20.txeis.EmployeeAccess.domainobject.CalYTDPrint;
import net.esc20.txeis.EmployeeAccess.domainobject.Calendar;
import net.esc20.txeis.EmployeeAccess.domainobject.Options;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.report.IReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ParameterReport;
import net.esc20.txeis.EmployeeAccess.service.api.ICalendarService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarService implements ICalendarService
{
	private ICalendarDao calendarDao;
	private IOptionsDao optionsDao;
	
	@Autowired
	public CalendarService(ICalendarDao calendarDao, IOptionsDao optionsDao)
	{
		this.calendarDao = calendarDao;
		this.optionsDao = optionsDao;
	}
	
	@Override
	public String getMessage()
	{
		Options o = optionsDao.getOptions();
		return o.getMessageCalendarYearToDate();
	}
	
	@Override
	public String getLatestYear(List<String> years)
	{
		if(years.size() > 0)
		{
			String latest = years.get(0);
			
			for(String year : years)
			{
				if(Integer.parseInt(year) > Integer.parseInt(latest))
				{
					latest = year;
				}
			}
			
			return latest;
		}
		
		return null;
	}
	
	@Override
	public List<String> retrieveYears(String employeeNumber)
	{
		return calendarDao.getAvailableYears(employeeNumber);
	}
	
	@Override
	public List<Calendar> retrieveCalendar(String employeeNumber, String year)
	{
		return calendarDao.getCalendars(employeeNumber, year);
	}
	
	//jf20140110 Print Report on Calendar YTD screen
	@Override
	public List<CalYTDPrint> generateCalYTDPrint(User user, List<Calendar> calendars)
	{
		String dCitySt = "";
		
		dCitySt = user.getEmployer().getCity() + ", " + user.getEmployer().getState() + " " + user.getEmployer().getZip();
		
		if(user.getEmployer().getZip4().length() > 0)
		{
			dCitySt = dCitySt + "-" + user.getEmployer().getZip4();
		}
		
		String eName = "";
		String middleName = user.getMiddleName().trim();
		if (middleName.length() > 0) {
			middleName = middleName + " ";
		} else {
			middleName = "";
		}
		
		eName = user.getFirstName() + " " + middleName + user.getLastName() + " " + user.getGenerationDescr();   //jf20150113 Display description instead of code fix
		
		List<CalYTDPrint> calYTDRpt = new ArrayList<CalYTDPrint>();
		CalYTDPrint print = null;
		Calendar calendar = null;
		
		for (int i = 0; i < calendars.size(); i++) {
			calendar = calendars.get(i);
			print = new CalYTDPrint();
			print.setDname(user.getEmployer().getName());
			print.setDaddress(user.getEmployer().getAddress());
			print.setDcityst(dCitySt);
			
			print.setEname(eName);
			print.setEmployeeNumber(user.getEmployeeNumber());
			
			print.setCalYr(calendar.getCalYr());
			print.setFrequency(calendar.getFrequency().getLabel());
			String postedDate = calendar.getLastPostedPayDateLabel();
			postedDate = postedDate  == null ? "no pay date": postedDate;
			print.setLastPostedPayDate(postedDate);
			
			print.setContractPay(calendar.getContractPay());
			print.setNonContractPay(calendar.getNonContractPay());
			print.setSupplementalPay(calendar.getSupplementalPay());
			
			print.setWithholdingGross(calendar.getWithholdingGross());
			print.setWithholdingTax(calendar.getWithholdingTax());
			print.setEarnedIncomeCredit(calendar.getEarnedIncomeCredit());
			
			print.setFicaGross(calendar.getFicaGross());
			print.setFicaTax(calendar.getFicaTax());
			
			print.setDependentCare(calendar.getDependentCare());
			print.setDependentCareEmployer(calendar.getDependentCareEmployer());
			print.setDependentCareExceeds(calendar.getDependentCareExceeds());
			
			print.setMedicareGross(calendar.getMedicareGross());
			print.setMedicareTax(calendar.getMedicareTax());
			
			print.setAnnuityDeduction(calendar.getAnnuityDeduction());
			print.setRoth403BAfterTax(calendar.getRoth403BAfterTax());
			print.setTaxableBenefits(calendar.getTaxableBenefits());
			
			print.setAnnuity457Employee(calendar.getAnnuity457Employee());
			print.setAnnuity457Employer(calendar.getAnnuity457Employer());
			print.setAnnuity457Withdraw(calendar.getAnnuity457Withdraw());
			
			print.setNonTrsBusinessExpense(calendar.getNonTrsBusinessExpense());
			print.setNonTrsReimbursementBase(calendar.getNonTrsReimbursementBase());
			print.setNonTrsReimbursementExcess(calendar.getNonTrsReimbursementExcess());
			
			print.setMovingExpenseReimbursement(calendar.getMovingExpenseReimbursement());
			print.setNonTrsNonTaxBusinessAllow(calendar.getNonTrsNonTaxBusinessAllow());
			print.setNonTrsNonTaxNonPayAllow(calendar.getNonTrsNonTaxNonPayAllow());
			
			print.setSalaryReduction(calendar.getSalaryReduction());
			print.setTrsInsurance(calendar.getTrsInsurance());
			
			print.setHsaEmployerContribution(calendar.getHsaEmployerContribution());
			print.setHsaEmployeeSalaryReductionContribution(calendar.getHsaEmployeeSalaryReductionContribution());
			print.setHireExemptWgs(calendar.getHireExemptWgs());
			
			print.setTaxedLifeContribution(calendar.getTaxedLifeContribution());
			print.setTaxedGroupContribution(calendar.getTaxedGroupContribution());
			print.setHealthInsuranceDeduction(calendar.getHealthInsuranceDeduction());
			
			print.setEmplrPrvdHlthcare(calendar.getEmplrPrvdHlthcare());
			print.setAnnuityRoth457b(calendar.getAnnuityRoth457b());
			calYTDRpt.add(print);
		}
		
		return calYTDRpt;
	}
	
	//jf20140110 Print Report on Current Calendar YTD screen
	@Override
	public IReport setupReport(ParameterReport report, List<CalYTDPrint> parameters) 
	{
		report.getParameters().clear();
		List<CalYTDPrint> forms = new ArrayList<CalYTDPrint>();
		for (int i = 0; i < parameters.size(); i++) {
			forms.add(parameters.get(i));
		}
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(forms);
		// Set the JRXML report
		report.setFileName("CalYTDPrint");

		report.setDataSource(dataSource);

		return report;
	}
}