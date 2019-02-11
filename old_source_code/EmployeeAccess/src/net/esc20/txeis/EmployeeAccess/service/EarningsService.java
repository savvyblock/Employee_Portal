package net.esc20.txeis.EmployeeAccess.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.dao.EarningsDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IEarningsDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IOptionsDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Earnings;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsBank;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsDeductions;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsJob;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsLeave;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsOther;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsOvertime;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsPrint;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsSupplemental;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsNonTrsTax;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsNonTrsNonTax;
import net.esc20.txeis.EmployeeAccess.domainobject.Job;
import net.esc20.txeis.EmployeeAccess.domainobject.Options;
import net.esc20.txeis.EmployeeAccess.domainobject.PayDate;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.report.IReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ParameterReport;
import net.esc20.txeis.EmployeeAccess.service.api.IEarningsService;
import net.esc20.txeis.EmployeeAccess.util.DateUtil;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Service;

@Service
public class EarningsService implements IEarningsService {
	
	private static Log log = LogFactory.getLog(EarningsService.class);

	private IEarningsDao earningsDao;
	private IOptionsDao optionsDao;
	
	@Autowired
	public EarningsService(EarningsDao earningsDao, IOptionsDao optionsDao) {
		this.earningsDao = earningsDao;
		this.optionsDao = optionsDao;
	}
	
	@Override
	public String getMessage()
	{
		Options o = optionsDao.getOptions();
		return o.getMessageEarnings();
	}
	
	@Override
	public Integer getRestrictEarnings()
	{
		return earningsDao.getRestrictEarnings();
	}
	
	@Override
	public PayDate getLatestPayDate(List<PayDate> payDates)
	{
		if(payDates.size() > 0)
		{
			PayDate latest = payDates.get(0);
			String tempDateString = StringUtil.left(latest.getDateFreq(), latest.getDateFreq().length()-1);
			Date latestDate = DateUtil.getDate(tempDateString, "yyyyMMdd");
			
			for(PayDate payDate : payDates)
			{
				tempDateString = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
				Date tempDate = DateUtil.getDate(tempDateString, "yyyyMMdd");
				if(tempDate.getTime() >  latestDate.getTime())
				{
					latest = payDate;
					latestDate = tempDate;
				}
			}
			
			return latest;
		}
		
		return null;
	}
	
	@Override
	public List<PayDate> retrievePayDates(String employeeNumber, Integer restrictPayDate)
	{
		List<PayDate> payDates = earningsDao.getAvailablePayDates(employeeNumber);
		if(payDates.size() == 0) return payDates;
		
		PayDate latest = payDates.get(0);
		String tempDateString = StringUtil.left(latest.getDateFreq(), latest.getDateFreq().length()-1);
		Date latestDate = DateUtil.getDate(tempDateString, "yyyyMMdd");
		
		
		//Removes pay date from dropdown if greater than 18 months from most recent pay date
		for(int i = 0; i < payDates.size(); i++)
		{
			tempDateString = StringUtil.left(payDates.get(i).getDateFreq(), payDates.get(i).getDateFreq().length()-1);
			Date tempDate = DateUtil.getDate(tempDateString, "yyyyMMdd");
			if(DateUtil.monthsDiff(latestDate, tempDate) > 18)
			{
				payDates.remove(i);
				i--;
			}
		}
		
		//Removes pay date from dropdown if over 50 pay dates are in dropdown
		if(payDates.size() > 50)
		{
			for(int i = 50; i < payDates.size(); i++)
			{
				payDates.remove(i);
				i--;
			}
			
		}
		
		//formats pay date display label for dropdown
		for(int i = 0; i < payDates.size(); i++)
		{
			DateFormat df = new SimpleDateFormat ("yyyyMMdd");
			Date date = null;
			String tempFreq = StringUtil.right(payDates.get(i).getDateFreq(), 1);
			tempDateString = StringUtil.left(payDates.get(i).getDateFreq(), payDates.get(i).getDateFreq().length()-1);
			
			try {
				date = df.parse(tempDateString);
			} catch (ParseException e) {
				log.info("Date Parse Error.");
			}
			StringBuffer displayLabel = new StringBuffer();
			displayLabel.append(new SimpleDateFormat("MMMM dd, yyyy").format(date));
			displayLabel.append(" ");
			displayLabel.append(Frequency.getFrequency(tempFreq));
			displayLabel.append(" Payroll ");
			if(payDates.get(i).getVoidIssue().equals("I"))
			{
				displayLabel.append(" Reissue ");
			}
			else if(payDates.get(i).getVoidIssue().equals("V"))
			{
				displayLabel.append(" Void ");
			}
			if(!payDates.get(i).getAdjNumber().equals("0"))
			{
				displayLabel.append(" Adjustment ");
				displayLabel.append(payDates.get(i).getAdjNumber());
			}
			
			payDates.get(i).setLabel(displayLabel.toString());
		}
		
		//cs20110502  using Calendar class
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		
		//Compare the current to last pay date
		long differenceMs = cal.getTimeInMillis() - latestDate.getTime();
		long msInDay = 24 * 60 * 60 * 1000;
		//Convert back to days and round
		double result = (double)differenceMs/(double)msInDay;
		long daysDiff = Math.round(result);
		
		//cs20110502  if current date already pass the last pay date or current date is pay date, is OK to see the last payroll
		if (restrictPayDate != null && daysDiff < 0){
			if (daysDiff + restrictPayDate < 0) {
				payDates.remove(0);
			}
		}

//		Date currentDate = new Date();
//		int daysDiff = DateUtil.daysAfter(currentDate, latestDate);
//		if( restrictPayDate != null && daysDiff > 0 && restrictPayDate > 0) 
//		{
//			if(daysDiff > restrictPayDate)
//			{
//				payDates.remove(0);
//			}
//		}
		
		return payDates;
		
	}
	
	@Override
	public Earnings retrieveEarnings(MessageContext messageContext,String employeeNumber, PayDate payDate)
	{
		if(payDate == null)
		{
			Earnings earnings = new Earnings();
			earnings.setInfo(new EarningsInfo());
			return earnings;
		}
		
		Earnings earnings = new Earnings();
		EarningsInfo earningsInfo;
		EarningsDeductions earningsDeductions;
		List <EarningsOther> earningsOther;
		List <EarningsJob> earningsJob;
		List <EarningsSupplemental> earningsSupplemental;
		List <EarningsNonTrsTax> earningsNonTrsTax;
		List <EarningsNonTrsNonTax> earningsNonTrsNonTax;
		List <EarningsBank> earningsBank;
		List <EarningsLeave> earningsLeave;
		List <EarningsOvertime> earningsOvertime;
		String checkNumber ="";
		checkNumber = payDate.getCheckNumber();
		
		earningsInfo = earningsDao.getEarningsInfo(employeeNumber, payDate);
		
		if(earningsInfo.getPeriodEndingDate() != null && !earningsInfo.getPeriodEndingDate().equals(""))
		{
			earningsInfo.formatPeriodEndingDate();
		}
		
		if(earningsInfo.getPeriodBeginningDate() != null && !earningsInfo.getPeriodBeginningDate().equals(""))
		{
			earningsInfo.formatPeriodBeginningDate();
		}
		
		earningsDeductions = earningsDao.getEarningsDeductions(employeeNumber, payDate, checkNumber);
		earningsOther = earningsDao.getEarningsOther(employeeNumber, payDate, checkNumber);
		earningsJob = earningsDao.getEarningsJob(employeeNumber, payDate, checkNumber);
		earningsSupplemental = earningsDao.getEarningsSupplemental(employeeNumber, payDate, checkNumber);
		earningsNonTrsTax = earningsDao.getEarningsNonTrsTax(employeeNumber, payDate, checkNumber);
		earningsNonTrsNonTax = earningsDao.getEarningsNonTrsNonTax(employeeNumber, payDate, checkNumber);
		
		earningsBank = earningsDao.getEarningsBank(employeeNumber, payDate, checkNumber);
		
		earningsLeave = earningsDao.getEarningsLeave(employeeNumber, payDate, checkNumber);

		for(int i = 0; i < earningsLeave.size(); i++)
		{
			if((!earningsLeave.get(i).getVoidIssue().equals("") && !earningsLeave.get(i).getVoidIssue().equals(payDate.getVoidIssue()))
			   || (!earningsLeave.get(i).getAdjNumber().equals("0") && !earningsLeave.get(i).getAdjNumber().equals(payDate.getAdjNumber())))
			{
				earningsLeave.remove(i);
				if(i != earningsLeave.size()-1)
					i--;
			}
//ts20100927 comment - lv balance should always be the leave balance from the lv master
//			else
//			{
//				BigDecimal used = new BigDecimal(earningsLeave.get(i).getUnitsPrior());
//				if( used.intValue() < 0)
//				{
//					earningsLeave.get(i).setBalance(earningsLeave.get(i).getBalance().subtract(used));
//				}
//			}
		}
		
		earningsOvertime = earningsDao.getEarningsOvertime(employeeNumber, payDate);
		if(payDate.getVoidIssue().equals("V"))
		{
			for(int i = 0; i < earningsOvertime.size(); i++)
			{
				earningsOvertime.get(i).setOvertimeRate(earningsOvertime.get(i).getOvertimeRate().multiply(new BigDecimal(-1)));
				earningsOvertime.get(i).setOvertimeUnits(earningsOvertime.get(i).getOvertimeUnits().multiply(new BigDecimal(-1)));
				earningsOvertime.get(i).setThisPeriod(earningsOvertime.get(i).getThisPeriod().multiply(new BigDecimal(-1)));
					
			}
		}
			
		earnings = calculateTotals(earnings, earningsOther, earningsJob, earningsSupplemental, 
				earningsBank, earningsLeave, earningsOvertime, earningsNonTrsTax, earningsNonTrsNonTax);
		
		earnings.setInfo(earningsInfo);
		earnings.setDeductions(earningsDeductions);
		earnings.setOther(earningsOther);
		earnings.setJob(earningsJob);
		earnings.setSupplemental(earningsSupplemental);
		earnings.setNonTrsTax(earningsNonTrsTax);
		earnings.setNonTrsNonTax(earningsNonTrsNonTax);
		earnings.setBank(earningsBank);
		earnings.setLeave(earningsLeave);
		earnings.setOvertime(earningsOvertime);
		
		return earnings;
	}
	@Override
	public Earnings calculateTotals(Earnings earnings, List <EarningsOther> earningsOther, List <EarningsJob> earningsJob, List <EarningsSupplemental> earningsSupplemental, 
								List <EarningsBank> earningsBank, List <EarningsLeave> earningsLeave, List <EarningsOvertime> earningsOvertime,
								List <EarningsNonTrsTax> earningsNonTrsTax, List <EarningsNonTrsNonTax> earningsNonTrsNonTax)
	{
		BigDecimal otherTotal = new BigDecimal(0);
		BigDecimal otherContrib = new BigDecimal(0);
		BigDecimal jobTotal = new BigDecimal(0);
		BigDecimal overTotal = new BigDecimal(0);
		BigDecimal supplementalTotal = new BigDecimal(0);
		BigDecimal nonTrsNonTaxTotal = new BigDecimal(0);
		BigDecimal nonTrsTaxTotal = new BigDecimal(0);
		BigDecimal bankTotal = new BigDecimal(0);
		
		for(int i = 0; i < earningsOther.size(); i++)
		{
			if(earningsOther.get(i).getAmt() != null)
				otherTotal = otherTotal.add(earningsOther.get(i).getAmt());
			if(earningsOther.get(i).getContrib() != null)
				otherContrib = otherContrib.add(earningsOther.get(i).getContrib());
		}
		
		for(int i = 0; i < earningsJob.size(); i++)
		{
			if(earningsJob.get(i).getAmt() != null)
				jobTotal = jobTotal.add(earningsJob.get(i).getAmt());
		}
		
		for(int i = 0; i < earningsOvertime.size(); i++)
		{
			if(earningsOvertime.get(i).getThisPeriod() != null)
				overTotal = overTotal.add(earningsOvertime.get(i).getThisPeriod());
		}
		
		for(int i = 0; i < earningsSupplemental.size(); i++)
		{
			if(earningsSupplemental.get(i).getAmt() != null)
				supplementalTotal = supplementalTotal.add(earningsSupplemental.get(i).getAmt());
		}
		
		for(int i = 0; i < earningsNonTrsTax.size(); i++)
		{
			if(earningsNonTrsTax.get(i).getAmt() != null)
				nonTrsTaxTotal = nonTrsTaxTotal.add(earningsNonTrsTax.get(i).getAmt());
		}
		
		for(int i = 0; i < earningsNonTrsNonTax.size(); i++)
		{
			if(earningsNonTrsNonTax.get(i).getAmt() != null)
				nonTrsNonTaxTotal = nonTrsNonTaxTotal.add(earningsNonTrsNonTax.get(i).getAmt());
		}
		
		for(int i = 0; i < earningsBank.size(); i++)
		{
			if(earningsBank.get(i).getAmt() != null)
				bankTotal = bankTotal.add(earningsBank.get(i).getAmt());
		}
		
		earnings.setEarningsOtherTotal(otherTotal);
		earnings.setEarningsOtherContribTotal(otherContrib);
		earnings.setEarningsJobTotal(jobTotal);
		earnings.setEarningsSupplementalTotal(supplementalTotal);
		earnings.setEarningsNonTrsTaxTotal(nonTrsTaxTotal);
		earnings.setEarningsNonTrsNonTaxTotal(nonTrsNonTaxTotal);
		earnings.setEarningsBankTotal(bankTotal);
		earnings.setEarningsOvertimeTotal(overTotal);
		
		return earnings;
		
	}
	//jf20140110 Print Report on Earnings screen
	@Override
	public EarningsPrint generateEarningsPrint(User user, EarningsInfo earningsInfo, PayDate payDate, Map<Frequency,List<Job>> jobs)
	//note: jobs contains only one element
	{
		EarningsPrint print = new EarningsPrint();

		print.setDname(user.getEmployer().getName());

		print.setBhr_emp_demo_name_l(user.getLastName());
		print.setBhr_emp_demo_name_gen(user.getGeneration());
		print.setGen_code_descr(user.getGenerationDescr());   //jf20150113 Display description instead of code fix
		print.setBhr_emp_demo_name_f(user.getFirstName());
		print.setBhr_emp_demo_name_m(user.getMiddleName());

		print.setBhr_pay_hist_chk_nbr(earningsInfo.getCheckNumber());

		print.setBthr_pay_dates_dt_payper_beg(earningsInfo.getPeriodBeginningDate());
		print.setBthr_pay_dates_dt_payper_end(earningsInfo.getPeriodEndingDate());

		print.setBhr_pay_hist_marital_stat_tax(earningsInfo.getWithholdingStatus());
		if (!earningsInfo.getNumExceptions().equals("")) {
			print.setBhr_pay_hist_nbr_tax_exempts(Integer.parseInt(earningsInfo.getNumExceptions()));
		}
		String primaryCampusId = "", primaryCampusName = "";
		if (payDate != null) {
			print.setBhr_pay_hist_dt_of_pay(StringUtil.left(payDate.getDateFreq(),payDate.getDateFreq().length()-1));
			print.setBhr_emp_pay_pay_freq(StringUtil.right(payDate.getDateFreq(), 1));
			print.setCal_year(StringUtil.left(payDate.getDateFreq(), 4));
			print.setBhr_pay_hist_void_or_iss(payDate.getVoidIssue());
			print.setBhr_pay_hist_adj_nbr(payDate.getAdjNumber());

			String payFreq = print.getBhr_emp_pay_pay_freq(); 
			if (!jobs.isEmpty()) {
				Frequency freq = Frequency.getFrequency(payFreq);
				if (jobs.containsKey(freq)) {
					for (int i = 0; i < jobs.get(freq).size(); i++)
					{
						if (jobs.get(freq).get(i).getPrimaryJob().equals("Y")) {
							primaryCampusId = jobs.get(freq).get(i).getCampusID();
							primaryCampusName = jobs.get(freq).get(i).getCampusName();
							break;
						}
					}
				}
			}	
		}
		print.setBhr_emp_demo_addr_nbr("");
		print.setBhr_emp_demo_addr_str(user.getAddress());
		print.setBhr_emp_demo_addr_apt("");
		print.setBhr_emp_demo_addr_city(user.getCity());
		print.setBhr_emp_demo_addr_st(user.getState());
		print.setBhr_emp_demo_addr_zip(user.getZipCode());
		print.setBhr_emp_demo_addr_zip4(user.getZipCode4());

		print.setBhr_emp_pay_pay_campus(earningsInfo.getCampusId());

		print.setBhr_emp_pay_emp_nbr(user.getEmployeeNumber());

		print.setBhr_emp_job_campus_id(primaryCampusId);
		print.setBhr_emp_job_campus_id_displayvalue(primaryCampusId + " " + primaryCampusName);
		print.setBhr_emp_pay_pay_campus_displayvalue(earningsInfo.getCampusId() + " " + earningsInfo.getCampusName());

		return print;
	}

	//jf20140110 Print Report on Current Earnings screen
	@Override
	public IReport setupReport(ParameterReport report, EarningsPrint parameters) 
	{
		report.getParameters().clear();
		List<EarningsPrint> forms = new ArrayList<EarningsPrint>();
		forms.add(parameters);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(forms);
		// Set the JRXML report
		report.setFileName("DHrs2500WageandearningstmtTab");

		report.setDataSource(dataSource);

		return report;
	}

}
