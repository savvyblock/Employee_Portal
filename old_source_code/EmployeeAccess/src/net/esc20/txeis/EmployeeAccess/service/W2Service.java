package net.esc20.txeis.EmployeeAccess.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.esc20.txeis.EmployeeAccess.dao.api.IOptionsDao;
import net.esc20.txeis.EmployeeAccess.dao.api.ISickPayDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IW2Dao;
import net.esc20.txeis.EmployeeAccess.domainobject.Options;
import net.esc20.txeis.EmployeeAccess.domainobject.SickPayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.W2Info;
import net.esc20.txeis.EmployeeAccess.domainobject.W2Print;
import net.esc20.txeis.EmployeeAccess.domainobject.report.IReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ParameterReport;
import net.esc20.txeis.EmployeeAccess.service.api.IMailUtilService;
import net.esc20.txeis.EmployeeAccess.service.api.IW2Service;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class W2Service implements IW2Service
{
	private IW2Dao w2Dao;
	private ISickPayDao sickPayDao;
	private IOptionsDao optionsDao;
	private ServletContext servletContext;
	private IMailUtilService mailUtilService;

	@Autowired
	public W2Service(IW2Dao w2Dao, ISickPayDao sickPayDao, IOptionsDao optionsDao, ServletContext servletContext, IMailUtilService mailUtilService)
	{
		this.w2Dao = w2Dao;
		this.sickPayDao = sickPayDao;
		this.optionsDao = optionsDao;
		this.servletContext = servletContext;
		this.mailUtilService = mailUtilService;
	}

	public void setW2Dao(IW2Dao w2Dao) {
		this.w2Dao = w2Dao;
	}

	@Override
	public List<String> retrieveYears(String employeeNumber)
	{
		return w2Dao.getAvailableYears(employeeNumber);
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
		return "";
	}

	@Override
	public String retrieveEA1095ElecConsent(String employeeNumber)
	{
		return w2Dao.retrieveEA1095ElecConsent(employeeNumber);
	}

	@Override
	public String retrieveEA1095ElecConsentMsg()
	{
		return w2Dao.retrieveEA1095ElecConsentMsg();
	}

	@Override
	public W2Info retrieveW2Info(String employeeNumber, String year)
	{
		W2Info info = w2Dao.getW2Info(employeeNumber, year);
		if(info != null)
		{
			info.setSickPay(sickPayDao.getThirdPartySickPay(employeeNumber, year));
		}
		return info;
	}

	@Override
	public boolean updateW2ElecConsent(String employeeNumber, String elecConsntW2)
	{
		boolean email = true;
		if (elecConsntW2 != null && !elecConsntW2.equals("")) {
			w2Dao.updateW2ElecConsent(employeeNumber, elecConsntW2);
		}
		if (elecConsntW2.equals("")) {
			email = false;
		}
		return email;
	}

	@Override
	public Integer sendEmail(String userFirstName, String userLastName, String userWorkEmail, String userHomeEmail, String elecConsntW2) {
		StringBuilder messageContents = new StringBuilder();
		messageContents.append(userFirstName + " " +userLastName + ", \n\n");
		messageContents.append("This receipt confirms you selected ");
		messageContents.append((elecConsntW2.equals("Y") ? " YES " : " NO "));
		messageContents.append("in participating in the W-2 Electronic Process. \n");
		messageContents.append("Effective immediately on ");

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		messageContents.append(dateFormat.format(cal.getTime()) + "\n");
		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setSubject("A MESSAGE FROM W2 ELECTRONIC CONSENT");
		msg.setText(messageContents.toString());

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
				ex.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public List<SickPayInfo> retrieveSickPayInfo(String employeeNumber, String year)
	{
		return sickPayDao.getSickPayInfo(employeeNumber, year);
	}

	@Override
	public W2Print generateW2Copy(W2Print print)
	{
		W2Print copy = (W2Print)print.clone();
		copy.setCopy("Copy C, For Employee\'s RECORDS");
		return copy;
	}

	@Override
	public W2Print generateW2Print(W2Info info, User user)
	{
		W2Print print = new W2Print();

		if(info == null || user == null)
		{
			return print;
		}

		if(!StringUtil.isNullOrEmpty(user.getSsn()) && user.getSsn().length() == 9)
		{
			String ssn1 = user.getSsn().substring(0,3);
			String ssn2 = user.getSsn().substring(3,5);
			String ssn3 = user.getSsn().substring(5,9);
			print.setSsn(ssn1 + "-" + ssn2 + "-" + ssn3);
		}

		if(!StringUtil.isNullOrEmpty(user.getEmployeeNumber()) && user.getEmployeeNumber().length() == 6)
		{
			String ein1 = user.getEmployer().getNumber().substring(0,2);
			String ein2 = user.getEmployer().getNumber().substring(2,user.getEmployer().getNumber().length());
			print.setEin(ein1 + "-" + ein2);
		}

		print.setEname(user.getEmployer().getName());
		print.setEaddress(user.getEmployer().getAddress());
		print.setEcityst(user.getEmployer().getCity() + " " + user.getEmployer().getState() + " " + user.getEmployer().getZip());
		
		if(user.getEmployer().getZip4().length() > 0)
		{
			print.setEcityst(print.getEcityst() + "-" + user.getEmployer().getZip4());
		}

		String middleName = user.getMiddleName();

		if(middleName.length() > 0)
		{
			middleName = "" + middleName.charAt(0);
		}

		print.setEmpname(user.getFirstName() + " " + middleName + " " + user.getLastName() + " " + user.getGenerationDescr());   //jf20150113 Display description instead of code fix
		print.setEmpaddress(user.getAddress());
		print.setEmpcityst(user.getCity() + " " + user.getState() + " " + user.getZipCode());

		if(user.getZipCode4().length() > 0)
		{
			print.setEmpcityst(print.getEmpcityst() + "-" + user.getZipCode4());
		}

		if(info != null)
		{
			print.setTgross(info.getTaxableGrossPay().toString());
			print.setWhold(info.getWithholdingTax().toString());
			print.setFgross(info.getFicaGross().toString());
			print.setFtax(info.getFicaTax().toString());
			print.setEic(info.getEarnedIncomeCredit().toString());
			print.setDcare(info.getDependentCare().toString());
			print.setMgross(info.getMedicareGross().toString());
			print.setMtax(info.getMedicareTax().toString());
		}

		String path = servletContext.getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}

		String unchecked = "uncheckedbox";
		String checked = "checkedbox";

		String statemp = unchecked;
		String retplan = unchecked;
		String thrdsick = unchecked;

		if(info.isPension())
		{
			retplan = checked;
		}

		if(info.getSickPay().doubleValue() > 0)
		{
			thrdsick = checked;
		}

		print.setStatemp(path + "images\\" + statemp + ".gif");
		print.setRetplan(path + "images\\"+ retplan +".gif");
		print.setThrdsick(path + "images\\" + thrdsick + ".gif");

		print.setCopy("Copy B, To Be Filed With Employee\'s FEDERAL Tax Return");

		//*********************************************************************************************************************************
		// BOX12
		DecimalFormat d2Digit = new DecimalFormat("#.00");   //jf20130108 makes sure double value prints .00, instead of .0
		int row = 0;
		double ld_data = info.getTaxedLifeContribution().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "C", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = info.getAnnuityDeduction().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "E", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = (info.getAnnuity457Employee().add(info.getAnnuity457Employer())).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "G", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = info.getNonTaxSickPay().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "J", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = info.getEmployeeBusinessExpense().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "L", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = info.getMovingExpenseReimbursement().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "P", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = info.getHealthSavingsAccount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "W", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = info.getRoth403BAfterTax().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "BB", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		//For calendar year >= 2010
		if (info.getCalYr() != null && !info.getCalYr().trim().equals("") && Integer.valueOf(info.getCalYr()) >= 2010) {
			ld_data = info.getHireExemptWgs().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
			if (ld_data != 0.00) {
				row ++;
				this.populatePrint(print, row, "CC", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
			}
		}
		//Employer Provided Healthcare reporting starts 2012   jf20120907
		if (info.getCalYr() != null && !info.getCalYr().trim().equals("") && Integer.valueOf(info.getCalYr()) >= 2012) {
			ld_data = info.getEmplrPrvdHlthcare().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
			if (ld_data != 0.00) {
				row ++;
				this.populatePrint(print, row, "DD", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
				
			}
		}
		//Annuity Roth 457b reporting starts 2016   
		if (info.getCalYr() != null && !info.getCalYr().trim().equals("") && Integer.valueOf(info.getCalYr()) >= 2012) {
			ld_data = info.getAnnuityRoth457b().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
			if (ld_data != 0.00) {
				row ++;
				this.populatePrint(print, row, "EE", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
			}
		}

		List<String> box14List = new ArrayList<String>();
		box14List.add("Non-Tax Allowance");
		box14List.add("Cafeteria 125");
		box14List.add("TRS Salary Reduction");
		box14List.add("Health Ins Ded");
		box14List.add("Taxable Allowance");
		box14List.add("Tax Fringe Benefits");
		box14List.add("Dummy Last Entry");   //jf20130109 the iter14.next does not return Tax Fringe Benefits, 
		                                     //           because it is last in the list.  if put Dummy Last Entry
											 //           then Tax Fringe Benefits returned if have amount > 0.00

		Map<String,BigDecimal> box14Map = new HashMap<String,BigDecimal>();
		box14Map.put(box14List.get(0),info.getNonTrsBusinessExpense());
		box14Map.put(box14List.get(1), info.getCafeteria125());
		box14Map.put(box14List.get(2), info.getSalaryReduction());
		box14Map.put(box14List.get(3), info.getHealthInsuranceDeduction());
		box14Map.put(box14List.get(4), info.getTaxableAllowance());
		box14Map.put(box14List.get(5), info.getTaxableBenefits());
		box14Map.put(box14List.get(6), new BigDecimal(0.00));   //jf20130109 init Dummy Last Entry to zero

		Iterator<String> iter14 = new CodeIterator(box14List,box14Map);
		print.setCode1401(iter14.next());
		print.setAmt1401(toString(box14Map.get(print.getCode1401())));
		print.setCode1402(iter14.next());
		print.setAmt1402(toString(box14Map.get(print.getCode1402())));
		print.setCode1403(iter14.next());
		print.setAmt1403(toString(box14Map.get(print.getCode1403())));
		print.setCode1404(iter14.next());
		print.setAmt1404(toString(box14Map.get(print.getCode1404())));
		print.setCode1405(iter14.next());
		print.setAmt1405(toString(box14Map.get(print.getCode1405())));
		print.setCode1406(iter14.next());
		print.setAmt1406(toString(box14Map.get(print.getCode1406())));

		return print;
	}

	public void populatePrint(W2Print print, int row, String code, String value) {
		if (row == 1) {
			print.setCode1201(code);
			print.setAmt1201(value);
		}
		else if (row == 2) {
			print.setCode1202(code);
			print.setAmt1202(value);
		}
		else if (row == 3) {
			print.setCode1203(code);
			print.setAmt1203(value);
		}
		else if (row == 4) {
			print.setCode1204(code);
			print.setAmt1204(value);
		}
		else if (row == 5) {
			print.setCode1205(code);
			print.setAmt1205(value);
		}
		else if (row == 6) {
			print.setCode1206(code);
			print.setAmt1206(value);
		}
		else if (row == 7) {
			print.setCode1207(code);
			print.setAmt1207(value);
		}
		else if (row == 8) {
			print.setCode1208(code);
			print.setAmt1208(value);
		}
		else if (row == 9) {
			print.setCode1209(code);
			print.setAmt1209(value);
		}
		else if (row == 10) {
			print.setCode1210(code);
			print.setAmt1210(value);
		}
		else if (row == 11) {
			print.setCode1211(code);
			print.setAmt1211(value);
		}
	}

	private String toString(BigDecimal b) {
		if(b != null) {
			return b.toString();
		}
		else {
			return "";
		}
	}

	private static class CodeIterator implements Iterator<String> {
		private List<String> codes;
		private Map<String,BigDecimal> values;
		private Iterator<String> iter;
		private String next;

		public CodeIterator(List<String> codes, Map<String, BigDecimal> values) {
			this.codes = codes;
			this.values = values;
			iter = codes.iterator();
			next();
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public String next() {
			String result = next;
			BigDecimal temp = null;

			while((temp == null || temp.doubleValue() == 0) && iter.hasNext()) {
				next = iter.next();
				temp = values.get(next);

				if(!iter.hasNext()) {
					next = null;
				}
			}

			return result;
		}

		@Override
		public void remove() {
			iter.remove();
		}
	}

	@Override
	public Options getOptions() {
		Options o = optionsDao.getOptions();
		return o;
	}

	@Override
	public IReport setupReport(ParameterReport report, W2Print parameters, String year) 
	{
		report.getParameters().clear();
		List<W2Print> forms = new ArrayList<W2Print>();
		forms.add(parameters);
		forms.add(generateW2Copy(parameters));
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(forms);
		// Set the JRXML report for the year being printed.
		if (year == null) {
			return report;
		} else {
			report.setFileName("W2_" + year);   //jf20120817
		}

		report.setDataSource(dataSource);

		return report;
	}
}