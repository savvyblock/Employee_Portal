package com.esc20.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.CalendarYearToDateDao;
import com.esc20.dao.CurrentPayInformationDao;
import com.esc20.dao.DeductionsDao;
import com.esc20.dao.EA1095Dao;
import com.esc20.dao.EarningsDao;
import com.esc20.dao.W2InformationDao;
import com.esc20.model.BhrAca1095bCovrdHist;
import com.esc20.model.BhrAca1095cCovrdHist;
import com.esc20.model.BhrAca1095cEmpHist;
import com.esc20.model.BhrCalYtd;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrThirdPartySickPay;
import com.esc20.model.BhrW2;
import com.esc20.model.BrRptngContact;
import com.esc20.nonDBModels.Account;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.CurrentPayInformation;
import com.esc20.nonDBModels.Deduction;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.EA1095CEmployerShare;
import com.esc20.nonDBModels.Earnings;
import com.esc20.nonDBModels.EarningsBank;
import com.esc20.nonDBModels.EarningsDeductions;
import com.esc20.nonDBModels.EarningsInfo;
import com.esc20.nonDBModels.EarningsJob;
import com.esc20.nonDBModels.EarningsLeave;
import com.esc20.nonDBModels.EarningsOther;
import com.esc20.nonDBModels.EarningsOvertime;
import com.esc20.nonDBModels.EarningsSupplemental;
import com.esc20.nonDBModels.EmployeeInfo;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.PayDate;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.Stipend;
import com.esc20.nonDBModels.W2Print;
import com.esc20.util.CodeIterator;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

@Service
public class InquiryService {

	@Autowired
	private CurrentPayInformationDao currentPayInformationDao;

	@Autowired
	private CalendarYearToDateDao calendarYearToDateDao;

	@Autowired
	private DeductionsDao deductionsDao;

	@Autowired
	private EarningsDao earningsDao;

	@Autowired
	private W2InformationDao w2InformationDao;

	@Autowired
	private EA1095Dao ea1095Dao;

	public List<String> getAvailableYears(String employeeNumber) {
		return calendarYearToDateDao.getAvailableYears(employeeNumber);
	}

	public BhrCalYtd getCalenderYTD(String employeeNumber, String year) {
		return calendarYearToDateDao.getCalenderYTD(employeeNumber, year);
	}

	public String getLatestPayDate(String employeeNumber, Frequency freq) {
		return calendarYearToDateDao.getLatestPayDate(employeeNumber, freq);
	}

	public Map<Frequency, List<CurrentPayInformation>> getJob(String employeeNumber) {
		List<CurrentPayInformation> jobs = currentPayInformationDao.getJob(employeeNumber);
		Map<Frequency, List<CurrentPayInformation>> jobMap = new HashMap<Frequency, List<CurrentPayInformation>>();

		for (CurrentPayInformation job : jobs) {
			if (jobMap.get(job.getPayFreq()) == null) {
				jobMap.put(job.getPayFreq(), new ArrayList<CurrentPayInformation>());
			}

			if (!"XTRA".equals(job.getJobCd())) {
				jobMap.get(job.getPayFreq()).add(job);
			}
		}

		return jobMap;
	}

	public List<Frequency> getFrequencies(Map<Frequency, ?> map) {
		List<Frequency> frequencies = new ArrayList<Frequency>(map.keySet());
		Collections.sort(frequencies);
		return frequencies;
	}

	public Map<Frequency, List<Stipend>> getStipends(String employeeNumber) {
		List<Stipend> stipends = currentPayInformationDao.getStipends(employeeNumber);
		Map<Frequency, List<Stipend>> stipendMap = new HashMap<Frequency, List<Stipend>>();

		for (Stipend stipend : stipends) {
			if (stipendMap.get(stipend.getFrequency()) == null) {
				stipendMap.put(stipend.getFrequency(), new ArrayList<Stipend>());
			}

			stipendMap.get(stipend.getFrequency()).add(stipend);
		}

		return stipendMap;
	}

	public Map<Frequency, List<Account>> getAccounts(String employeeNumber) {
		List<Account> accounts = currentPayInformationDao.getAccounts(employeeNumber);
		Map<Frequency, List<Account>> bankMap = new HashMap<Frequency, List<Account>>();

		for (Account account : accounts) {
			if (bankMap.get(account.getFrequency()) == null) {
				bankMap.put(account.getFrequency(), new ArrayList<Account>());
			}
			account.setBankAccountNumber(getAccountNumberShow(account.getBankAccountNumber()));
			bankMap.get(account.getFrequency()).add(account);
		}

		return bankMap;
	}

	private String getAccountNumberShow(String accountNumber) {
		String symbol = "";
		for (int i = 0; i < accountNumber.length() - 4; i++) {
			symbol += "*";
		}
		return symbol + accountNumber.substring(accountNumber.length() - 4, accountNumber.length());
	}

	public Map<Frequency, PayInfo> retrievePayInfo(String employeeNumber, List<Frequency> frequencies) {
		Map<Frequency, PayInfo> payMap = new HashMap<Frequency, PayInfo>();

		for (Frequency frequency : frequencies) {
			try {
				payMap.put(frequency, currentPayInformationDao.getPayInfo(employeeNumber, frequency));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return payMap;
	}

	public Map<Frequency, String> retrievePayCampuses(String employeeNumber) {
		return currentPayInformationDao.getPayCampuses(employeeNumber);
	}

	public EmployeeInfo getEmployeeInfo(String employeeNumber) {
		return currentPayInformationDao.getEmployeeInfo(employeeNumber);
	}

	public List<Frequency> getAvailableFrequencies(String employeeNumber) {
		return deductionsDao.getAvailableFrequencies(employeeNumber);
	}

	public Map<Frequency, List<Deduction>> retrieveAllDeductions(String employeeNumber, List<Frequency> frequencies) {
		Map<Frequency, List<Deduction>> deductions = new HashMap<Frequency, List<Deduction>>();

		for (Frequency frequency : frequencies) {
			if (deductions.get(frequency) == null) {
				deductions.put(frequency, new ArrayList<Deduction>());
			}

			deductions.get(frequency).addAll(retrieveDeductions(employeeNumber, frequency));
		}

		return deductions;
	}

	public List<Deduction> retrieveDeductions(String employeeNumber, Frequency frequency) {
		List<Deduction> deductions;
		if (frequency != null) {
			deductions = deductionsDao.getDeductions(employeeNumber, frequency);
		} else {
			deductions = new ArrayList<Deduction>();
		}
		return deductions;
	}

	public Earnings getTYDEarnings(String employeeNumber, List<PayDate> payDates, PayDate payDate) {
		if (payDate == null) {
			Earnings earnings = new Earnings();
			earnings.setInfo(new EarningsInfo());
			return earnings;
		}

		String year = payDate.getDateFreq().substring(0, 4);
		// remove not selected year PayDates
		List<PayDate> tempDates = new ArrayList<PayDate>();
		tempDates.addAll(payDates);
		for (int i = 0; i < tempDates.size(); i++) {
			if (!year.equals(tempDates.get(i).getDateFreq().substring(0, 4))) {
				tempDates.remove(i);
				i--;
				continue;
			}
			if (payDate.getDateFreq().equals(tempDates.get(i).getDateFreq())) {
				tempDates.remove(i);
				i--;
			}
		}
		Earnings ytdEarnings = this.retrieveEarnings(employeeNumber, payDate);
		if(ytdEarnings==null) {
			return null;
		}
		List<EarningsOther> earningsOther = ytdEarnings.getOther();
		ytdEarnings.setEarningsOtherTydTotal(new BigDecimal(0));
		for (int i = 0; i < earningsOther.size(); i++) {
			earningsOther.get(i).setTydAmt(
					earningsOther.get(i).getAmt() == null ? BigDecimal.valueOf(0) : earningsOther.get(i).getAmt());
			earningsOther.get(i).setTydContrib(earningsOther.get(i).getContrib() == null ? BigDecimal.valueOf(0)
					: earningsOther.get(i).getContrib());

		}
		ytdEarnings.setEarningsOtherTydTotal(new BigDecimal(0));
		Earnings fetchedEarnings;
		for (int i = 0; i < tempDates.size(); i++) {
			fetchedEarnings = this.retrieveEarnings(employeeNumber, tempDates.get(i));
			ytdEarnings = this.sumUpEarnings(ytdEarnings, fetchedEarnings);
		}
		for (int i = 0; i < ytdEarnings.getOther().size(); i++) {
			ytdEarnings.setEarningsOtherTydTotal(
					ytdEarnings.getEarningsOtherTydTotal().add(ytdEarnings.getOther().get(i).getTydAmt()));
		}
		return ytdEarnings;
	}

	private Earnings sumUpEarnings(Earnings ytdEarnings, Earnings fetchedEarnings) {
		EarningsDeductions ytd = ytdEarnings.getDeductions();
		EarningsDeductions fetched = fetchedEarnings.getDeductions();
		ytd.setStandardGross(ytd.getStandardGross().add(fetched.getStandardGross()));
		ytd.setTotalEarnings(ytd.getTotalEarnings().add(fetched.getTotalEarnings()));
		ytd.setWithholdingTax(ytd.getWithholdingTax().add(fetched.getWithholdingTax()));
		ytd.setMedicareTax(ytd.getMedicareTax().add(fetched.getMedicareTax()));
		ytd.setNonTrsNonPayNonTax(ytd.getNonTrsNonPayNonTax().add(fetched.getNonTrsNonPayNonTax()));
		ytd.setSupplementalPay(ytd.getSupplementalPay().add(fetched.getSupplementalPay()));
		ytd.setOvertimePay(ytd.getOvertimePay().add(fetched.getOvertimePay()));
		ytd.setAbsenceRefund(ytd.getAbsenceRefund().add(fetched.getAbsenceRefund()));
		ytd.setTaxedFringe(ytd.getTaxedFringe().add(fetched.getTaxedFringe()));
		ytd.setEarnedIncomeCred(ytd.getEarnedIncomeCred().add(fetched.getEarnedIncomeCred()));
		ytd.setNonTrsTax(ytd.getNonTrsTax().add(fetched.getNonTrsTax()));
		ytd.setNonTrsNonTax(ytd.getNonTrsNonTax().add(fetched.getNonTrsNonTax()));
		ytd.setTrsSupplemental(ytd.getTrsSupplemental().add(fetched.getTrsSupplemental()));
		ytd.setAbsenceDed(ytd.getAbsenceDed().add(fetched.getAbsenceDed()));
		ytd.setFicaTax(ytd.getFicaTax().add(fetched.getFicaTax()));
		ytd.setTrsSalaryRed(ytd.getTrsSalaryRed().add(fetched.getTrsSalaryRed()));
		ytd.setTrsInsurance(ytd.getTrsInsurance().add(fetched.getTrsInsurance()));
		ytd.setTotOtherDed(ytd.getTotOtherDed().add(fetched.getTotOtherDed()));
		ytd.setTotDed(ytd.getTotDed().add(fetched.getTotDed()));
		ytd.setNetPay(ytd.getNetPay().add(fetched.getNetPay()));
		ytd.setNonTrsNonPayTax(ytd.getNonTrsNonPayTax().add(fetched.getNonTrsNonPayTax()));
		ytd.setTaxableWage(ytd.getTaxableWage().add(fetched.getTaxableWage()));
		ytd.setFicaWage(ytd.getFicaWage().add(fetched.getFicaWage()));
		ytd.setMedGross(ytd.getMedGross().add(fetched.getMedGross()));
		ytdEarnings.setDeductions(ytd);
		List<EarningsOther> earningsOther = ytdEarnings.getOther();

		List<EarningsOther> fetchedOther = fetchedEarnings.getOther();
		for (int i = 0; i < earningsOther.size(); i++) {
			for (int j = 0; j < fetchedOther.size(); j++) {
				if (earningsOther.get(i).getCode().equals(fetchedOther.get(j).getCode())) {
					earningsOther.get(i)
							.setTydAmt(earningsOther.get(i).getTydAmt()
									.add(fetchedOther.get(j).getAmt() == null ? BigDecimal.valueOf(0)
											: fetchedOther.get(j).getAmt()));
					earningsOther.get(i)
							.setTydContrib(earningsOther.get(i).getTydContrib()
									.add(fetchedOther.get(j).getContrib() == null ? BigDecimal.valueOf(0)
											: fetchedOther.get(j).getContrib()));
				}
			}

		}
		ytdEarnings.setOther(earningsOther);
		List<EarningsJob> earningsJob = ytdEarnings.getJob();
		earningsJob.addAll(fetchedEarnings.getJob());
		ytdEarnings.setJob(earningsJob);
		List<EarningsSupplemental> earningsSupplemental = ytdEarnings.getSupplemental();
		earningsSupplemental.addAll(fetchedEarnings.getSupplemental());
		ytdEarnings.setSupplemental(earningsSupplemental);
		List<EarningsSupplemental> earningsNonTrsTax = ytdEarnings.getNonTrsTax();
		earningsNonTrsTax.addAll(fetchedEarnings.getNonTrsTax());
		ytdEarnings.setNonTrsTax(earningsNonTrsTax);
		List<EarningsSupplemental> earningsNonTrsNonTax = ytdEarnings.getNonTrsNonTax();
		earningsNonTrsNonTax.addAll(earningsNonTrsNonTax);
		ytdEarnings.setNonTrsNonTax(earningsNonTrsNonTax);
		List<EarningsBank> earningsBank = ytdEarnings.getBank();
		earningsBank.addAll(fetchedEarnings.getBank());
		ytdEarnings.setBank(earningsBank);
		List<EarningsLeave> earningsLeave = ytdEarnings.getLeave();
		earningsLeave.addAll(fetchedEarnings.getLeave());
		ytdEarnings.setLeave(earningsLeave);
		List<EarningsOvertime> earningsOvertime = ytdEarnings.getOvertime();
		earningsOvertime.addAll(fetchedEarnings.getOvertime());
		ytdEarnings.setOvertime(earningsOvertime);
		ytdEarnings.setEarningsOtherTotal(
				ytdEarnings.getEarningsOtherTotal().add(fetchedEarnings.getEarningsOtherTotal()));
		ytdEarnings.setEarningsOtherContribTotal(
				ytdEarnings.getEarningsOtherContribTotal().add(fetchedEarnings.getEarningsOtherContribTotal()));
		ytdEarnings.setEarningsJobTotal(ytdEarnings.getEarningsJobTotal().add(fetchedEarnings.getEarningsJobTotal()));
		ytdEarnings.setEarningsSupplementalTotal(
				ytdEarnings.getEarningsSupplementalTotal().add(fetchedEarnings.getEarningsSupplementalTotal()));
		ytdEarnings.setEarningsNonTrsTaxTotal(
				ytdEarnings.getEarningsNonTrsTaxTotal().add(fetchedEarnings.getEarningsNonTrsTaxTotal()));
		ytdEarnings.setEarningsNonTrsNonTaxTotal(
				ytdEarnings.getEarningsNonTrsNonTaxTotal().add(fetchedEarnings.getEarningsNonTrsNonTaxTotal()));
		ytdEarnings
				.setEarningsBankTotal(ytdEarnings.getEarningsBankTotal().add(fetchedEarnings.getEarningsBankTotal()));
		ytdEarnings.setEarningsOvertimeTotal(
				ytdEarnings.getEarningsOvertimeTotal().add(fetchedEarnings.getEarningsOvertimeTotal()));
		return ytdEarnings;
	}

	public List<PayDate> getAvailablePayDates(String employeeNumber, Integer restrictPayDate) {
		List<PayDate> payDates = earningsDao.getAvailablePayDates(employeeNumber);
		if (payDates.size() == 0)
			return payDates;
		String prefix;
		String dt;
		Date dtDate;
		Calendar cal = Calendar.getInstance();
		for (int i = 0; i < payDates.size(); i++) {
			prefix = payDates.get(i).getCheckNumber().substring(0, 4);
			if (prefix.equals("CYTD") || prefix.equals("AVAR") || prefix.equals("SYTD") || prefix.equals("SADJ")
					|| prefix.equals("ADJU") || prefix.equals("CTRS")) {
				payDates.remove(i);
				i--;
				continue;
			}
			dt = StringUtil.left(payDates.get(i).getDateFreq(), payDates.get(i).getDateFreq().length() - 1);
			dtDate = DateUtil.getDate(dt, "yyyyMMdd");
			if (DateUtil.differentDays(dtDate, new Date()) + restrictPayDate < 0) {
				payDates.remove(i);
				i--;
			}
		}
		// top 50
		if (payDates.size() >= 50)
			payDates = payDates.subList(0, 49);

		PayDate latest = payDates.get(0);
		String tempDateString = StringUtil.left(latest.getDateFreq(), latest.getDateFreq().length() - 1);
		Date latestDate = DateUtil.getDate(tempDateString, "yyyyMMdd");

		// Removes pay date from dropdown if greater than 18 months from most recent pay
		// date
		for (int i = 0; i < payDates.size(); i++) {
			tempDateString = StringUtil.left(payDates.get(i).getDateFreq(), payDates.get(i).getDateFreq().length() - 1);
			Date tempDate = DateUtil.getDate(tempDateString, "yyyyMMdd");
			if (DateUtil.monthsDiff(latestDate, tempDate) > 18) {
				payDates.remove(i);
				i--;
			}
		}

		// Removes pay date from dropdown if over 50 pay dates are in dropdown
		if (payDates.size() > 50) {
			for (int i = 50; i < payDates.size(); i++) {
				payDates.remove(i);
				i--;
			}

		}

		// formats pay date display label for dropdown
		for (int i = 0; i < payDates.size(); i++) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			Date date = null;
			String tempFreq = StringUtil.right(payDates.get(i).getDateFreq(), 1);
			tempDateString = StringUtil.left(payDates.get(i).getDateFreq(), payDates.get(i).getDateFreq().length() - 1);

			try {
				date = df.parse(tempDateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			StringBuffer displayLabel = new StringBuffer();
			displayLabel.append(new SimpleDateFormat("MMMM dd, yyyy").format(date));
			displayLabel.append(" ");
			displayLabel.append(Frequency.getFrequency(tempFreq));
			displayLabel.append(" Payroll ");
			if (payDates.get(i).getVoidIssue().equals("I")) {
				displayLabel.append(" Reissue ");
			} else if (payDates.get(i).getVoidIssue().equals("V")) {
				displayLabel.append(" Void ");
			}
			if (!payDates.get(i).getAdjNumber().equals("0")) {
				displayLabel.append(" Adjustment ");
				displayLabel.append(payDates.get(i).getAdjNumber());
			}

			payDates.get(i).setLabel(displayLabel.toString());
		}

		// cs20110502 using Calendar class
		cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

		// Compare the current to last pay date
		long differenceMs = cal.getTimeInMillis() - latestDate.getTime();
		long msInDay = 24 * 60 * 60 * 1000;
		// Convert back to days and round
		double result = (double) differenceMs / (double) msInDay;
		long daysDiff = Math.round(result);

		// cs20110502 if current date already pass the last pay date or current date is
		// pay date, is OK to see the last payroll
		if (restrictPayDate != null && daysDiff < 0) {
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

	public PayDate getLatestPayDate(List<PayDate> payDates) {
		if (payDates.size() > 0) {
			PayDate latest = payDates.get(0);
			String tempDateString = StringUtil.left(latest.getDateFreq(), latest.getDateFreq().length() - 1);
			Date latestDate = DateUtil.getDate(tempDateString, "yyyyMMdd");

			for (PayDate payDate : payDates) {
				tempDateString = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length() - 1);
				Date tempDate = DateUtil.getDate(tempDateString, "yyyyMMdd");
				if (tempDate.getTime() > latestDate.getTime()) {
					latest = payDate;
					latestDate = tempDate;
				}
			}

			return latest;
		}

		return null;
	}

	public Earnings retrieveEarnings(String employeeNumber, PayDate payDate) {
		if (payDate == null) {
			Earnings earnings = new Earnings();
			earnings.setInfo(new EarningsInfo());
			return earnings;
		}

		Earnings earnings = new Earnings();
		EarningsInfo earningsInfo;
		EarningsDeductions earningsDeductions;
		List<EarningsOther> earningsOther;
		List<EarningsJob> earningsJob;
		List<EarningsSupplemental> earningsSupplemental;
		List<EarningsSupplemental> earningsNonTrsTax;
		List<EarningsSupplemental> earningsNonTrsNonTax;
		List<EarningsBank> earningsBank;
		List<EarningsLeave> earningsLeave;
		List<EarningsOvertime> earningsOvertime;
		String checkNumber = "";
		checkNumber = payDate.getCheckNumber();

		earningsInfo = earningsDao.getEarningsInfo(employeeNumber, payDate);
		if(earningsInfo==null) {
			return null;
		}
		if (earningsInfo.getPeriodEndingDate() != null && !earningsInfo.getPeriodEndingDate().equals("")) {
			earningsInfo.formatPeriodEndingDate();
		}

		if (earningsInfo.getPeriodBeginningDate() != null && !earningsInfo.getPeriodBeginningDate().equals("")) {
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

		for (int i = 0; i < earningsLeave.size(); i++) {
			if ((!earningsLeave.get(i).getVoidIssue().equals("")
					&& !earningsLeave.get(i).getVoidIssue().equals(payDate.getVoidIssue()))
					|| (!earningsLeave.get(i).getAdjNumber().equals("0")
							&& !earningsLeave.get(i).getAdjNumber().equals(payDate.getAdjNumber()))) {
				earningsLeave.remove(i);
				if (i != earningsLeave.size() - 1)
					i--;
			}
		}

		earningsOvertime = earningsDao.getEarningsOvertime(employeeNumber, payDate);
		if (payDate.getVoidIssue().equals("V")) {
			for (int i = 0; i < earningsOvertime.size(); i++) {
				earningsOvertime.get(i)
						.setOvertimeRate(earningsOvertime.get(i).getOvertimeRate().multiply(new BigDecimal(-1)));
				earningsOvertime.get(i)
						.setOvertimeUnits(earningsOvertime.get(i).getOvertimeUnits().multiply(new BigDecimal(-1)));
				earningsOvertime.get(i)
						.setThisPeriod(earningsOvertime.get(i).getThisPeriod().multiply(new BigDecimal(-1)));

			}
		}

		earnings = calculateTotals(earnings, earningsOther, earningsJob, earningsSupplemental, earningsBank,
				earningsLeave, earningsOvertime, earningsNonTrsTax, earningsNonTrsNonTax);

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

	public Earnings calculateTotals(Earnings earnings, List<EarningsOther> earningsOther, List<EarningsJob> earningsJob,
			List<EarningsSupplemental> earningsSupplemental, List<EarningsBank> earningsBank,
			List<EarningsLeave> earningsLeave, List<EarningsOvertime> earningsOvertime,
			List<EarningsSupplemental> earningsNonTrsTax, List<EarningsSupplemental> earningsNonTrsNonTax) {
		BigDecimal otherTotal = new BigDecimal(0);
		BigDecimal otherContrib = new BigDecimal(0);
		BigDecimal jobTotal = new BigDecimal(0);
		BigDecimal overTotal = new BigDecimal(0);
		BigDecimal supplementalTotal = new BigDecimal(0);
		BigDecimal nonTrsNonTaxTotal = new BigDecimal(0);
		BigDecimal nonTrsTaxTotal = new BigDecimal(0);
		BigDecimal bankTotal = new BigDecimal(0);
		for (int i = 0; i < earningsOther.size(); i++) {
			if (earningsOther.get(i).getAmt() != null)
				otherTotal = otherTotal.add(earningsOther.get(i).getAmt());
			if (earningsOther.get(i).getContrib() != null)
				otherContrib = otherContrib.add(earningsOther.get(i).getContrib());
		}

		for (int i = 0; i < earningsJob.size(); i++) {
			if (earningsJob.get(i).getAmt() != null)
				jobTotal = jobTotal.add(earningsJob.get(i).getAmt());
		}

		for (int i = 0; i < earningsOvertime.size(); i++) {
			if (earningsOvertime.get(i).getThisPeriod() != null)
				overTotal = overTotal.add(earningsOvertime.get(i).getThisPeriod());
		}

		for (int i = 0; i < earningsSupplemental.size(); i++) {
			if (earningsSupplemental.get(i).getAmt() != null)
				supplementalTotal = supplementalTotal.add(earningsSupplemental.get(i).getAmt());
		}

		for (int i = 0; i < earningsNonTrsTax.size(); i++) {
			if (earningsNonTrsTax.get(i).getAmt() != null)
				nonTrsTaxTotal = nonTrsTaxTotal.add(earningsNonTrsTax.get(i).getAmt());
		}

		for (int i = 0; i < earningsNonTrsNonTax.size(); i++) {
			if (earningsNonTrsNonTax.get(i).getAmt() != null)
				nonTrsNonTaxTotal = nonTrsNonTaxTotal.add(earningsNonTrsNonTax.get(i).getAmt());
		}

		for (int i = 0; i < earningsBank.size(); i++) {
			if (earningsBank.get(i).getAmt() != null)
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

	public BigDecimal getEmplrPrvdHlthcare(String employeeNumber, PayDate latestPayDate) {

		return earningsDao.getEmplrPrvdHlthcare(employeeNumber, latestPayDate);
	}

	public List<String> getW2Years(String employeeNumber) {
		return w2InformationDao.getAvailableYears(employeeNumber);
	}

	public BhrW2 getW2Info(String employeeNumber, String year) {
		return w2InformationDao.getW2Info(employeeNumber, year);
	}

	public String getW2Consent(String employeeNumber) {
		return w2InformationDao.retrieveEA1095ElecConsent(employeeNumber);
	}

	public List<BhrThirdPartySickPay> getBhrThirdPartySickPay(String employeeNumber, String year) {
		return w2InformationDao.getSickPayInfo(employeeNumber, year);
	}

	public Boolean updateW2ElecConsent(String employeeNumber, String consent) {
		return w2InformationDao.updateW2ElecConsent(employeeNumber, consent);
	}

	public W2Print getW2Print(BhrW2 w2Info, BhrEmpDemo user, District employer) {
		W2Print print = new W2Print();
		if (w2Info == null || user == null) {
			return print;
		}

		if (!StringUtil.isNullOrEmpty(user.getStaffId()) && user.getStaffId().length() == 9) {
			String ssn1 = user.getStaffId().substring(0, 3);
			String ssn2 = user.getStaffId().substring(3, 5);
			String ssn3 = user.getStaffId().substring(5, 9);
			print.setSsn(ssn1 + "-" + ssn2 + "-" + ssn3);
		}

		if (!StringUtil.isNullOrEmpty(user.getEmpNbr()) && user.getEmpNbr().length() == 6) {
			String ein1 = employer.getEin().substring(0, 2);
			String ein2 = employer.getEin().substring(2, employer.getEin().length());
			print.setEin(ein1 + "-" + ein2);
		}

		print.setEname(employer.getName());
		print.setEaddress(employer.getAddress());
		print.setEcityst(employer.getCity() + " " + employer.getState() + " " + employer.getZip());

		if (employer.getZip4()!=null && employer.getZip4().length() > 0) {
			print.setEcityst(print.getEcityst() + "-" + employer.getZip4());
		}

		String middleName = user.getNameM();

		if (middleName.length() > 0) {
			middleName = "" + middleName.charAt(0);
		}

		print.setEmpname(user.getNameF() + " " + middleName + " " + user.getNameL()); // jf20150113 Display description
																						// instead of code fix
		print.setEmpaddress((user.getAddrNbr() == null ? "" : user.getAddrNbr()) + " " + user.getAddrStr());
		print.setEmpcityst(user.getAddrCity() + " " + user.getAddrSt() + " " + user.getAddrZip());

		if (user.getAddrZip4() != null && user.getAddrZip4().length() > 0) {
			print.setEmpcityst(print.getEmpcityst() + "-" + user.getAddrZip4());
		}

		if (w2Info != null) {
			print.setTgross(w2Info.getWhGross().toString());
			print.setWhold(w2Info.getWhTax().toString());
			print.setFgross(w2Info.getFicaGross().toString());
			print.setFtax(w2Info.getFicaTax().toString());
			print.setEic(w2Info.getEicAmt().toString());
			print.setDcare(w2Info.getDependCare().toString());
			print.setMgross(w2Info.getMedGross().toString());
			print.setMtax(w2Info.getMedGross().toString());
		}
		String unchecked = "uncheckedbox";
		String checked = "checkedbox";

		String statemp = unchecked;
		String retplan = unchecked;
		String thrdsick = unchecked;

		if (("Y").equals(w2Info.getPension().toString())) {
			retplan = checked;
		}

		if (w2Info.getSickPayNontax().doubleValue() > 0) {
			thrdsick = checked;
		}

		print.setStatemp(statemp);
		print.setRetplan(retplan);
		print.setThrdsick(thrdsick);

		print.setCopy("Copy B, To Be Filed With Employee\'s FEDERAL Tax Return");
		// *********************************************************************************************************************************
		// BOX12
		DecimalFormat d2Digit = new DecimalFormat("#.00"); // jf20130108 makes sure double value prints .00, instead of
															// .0
		int row = 0;
		double ld_data = w2Info.getTaxEmplrLifeGrp().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row++;
			this.populatePrint(print, row, "C", d2Digit.format(ld_data)); // 20130108 print .00, instead of .0
		}

		ld_data = w2Info.getAnnuityDed().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row++;
			this.populatePrint(print, row, "E", d2Digit.format(ld_data)); // 20130108 print .00, instead of .0
		}

		ld_data = (w2Info.getEmp457Contrib().add(w2Info.getEmplrContrib457())).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row++;
			this.populatePrint(print, row, "G", d2Digit.format(ld_data)); // 20130108 print .00, instead of .0
		}

		ld_data = w2Info.getSickPayNontax().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row++;
			this.populatePrint(print, row, "J", d2Digit.format(ld_data)); // 20130108 print .00, instead of .0
		}

		ld_data = w2Info.getEmpBusinessExpense().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row++;
			this.populatePrint(print, row, "L", d2Digit.format(ld_data)); // 20130108 print .00, instead of .0
		}

		ld_data = w2Info.getMovingExpReimbr().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row++;
			this.populatePrint(print, row, "P", d2Digit.format(ld_data)); // 20130108 print .00, instead of .0
		}

		ld_data = w2Info.getHsaContrib().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row++;
			this.populatePrint(print, row, "W", d2Digit.format(ld_data)); // 20130108 print .00, instead of .0
		}

		ld_data = w2Info.getAnnuityRoth().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row++;
			this.populatePrint(print, row, "BB", d2Digit.format(ld_data)); // 20130108 print .00, instead of .0
		}

		// For calendar year >= 2010
		if (w2Info.getId().getCalYr() != null && !w2Info.getId().getCalYr().trim().equals("")
				&& Integer.valueOf(w2Info.getId().getCalYr()) >= 2010) {
			ld_data = w2Info.getHireExemptWgs().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
			if (ld_data != 0.00) {
				row++;
				this.populatePrint(print, row, "CC", d2Digit.format(ld_data)); // 20130108 print .00, instead of .0
			}
		}
		// Employer Provided Healthcare reporting starts 2012 jf20120907
		if (w2Info.getId().getCalYr() != null && !w2Info.getId().getCalYr().trim().equals("")
				&& Integer.valueOf(w2Info.getId().getCalYr()) >= 2012) {
			ld_data = w2Info.getEmplrPrvdHlthcare().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
			if (ld_data != 0.00) {
				row++;
				this.populatePrint(print, row, "DD", d2Digit.format(ld_data)); // 20130108 print .00, instead of .0

			}
		}
		// Annuity Roth 457b reporting starts 2016
		if (w2Info.getId().getCalYr() != null && !w2Info.getId().getCalYr().trim().equals("")
				&& Integer.valueOf(w2Info.getId().getCalYr()) >= 2012) {
			ld_data = w2Info.getAnnuityRoth457b().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
			if (ld_data != 0.00) {
				row++;
				this.populatePrint(print, row, "EE", d2Digit.format(ld_data)); // 20130108 print .00, instead of .0
			}
		}

		List<String> box14List = new ArrayList<String>();
		box14List.add("Non-Tax Allowance");
		box14List.add("Cafeteria 125");
		box14List.add("TRS Salary Reduction");
		box14List.add("Health Ins Ded");
		box14List.add("Taxable Allowance");
		box14List.add("Tax Fringe Benefits");
		box14List.add("Dummy Last Entry"); // jf20130109 the iter14.next does not return Tax Fringe Benefits,
											// because it is last in the list. if put Dummy Last Entry
											// then Tax Fringe Benefits returned if have amount > 0.00

		Map<String, BigDecimal> box14Map = new HashMap<String, BigDecimal>();
		box14Map.put(box14List.get(0), w2Info.getNontrsBusAllow());
		box14Map.put(box14List.get(1), w2Info.getCafeAmt());
		box14Map.put(box14List.get(2), w2Info.getTrsDeposit());
		box14Map.put(box14List.get(3), w2Info.getHlthInsDed());
		box14Map.put(box14List.get(4), w2Info.getNontrsBusAllow());
		box14Map.put(box14List.get(5), w2Info.getTaxedBenefits());
		box14Map.put(box14List.get(6), new BigDecimal(0.00)); // jf20130109 init Dummy Last Entry to zero

		Iterator<String> iter14 = new CodeIterator(box14List, box14Map);
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
		} else if (row == 2) {
			print.setCode1202(code);
			print.setAmt1202(value);
		} else if (row == 3) {
			print.setCode1203(code);
			print.setAmt1203(value);
		} else if (row == 4) {
			print.setCode1204(code);
			print.setAmt1204(value);
		} else if (row == 5) {
			print.setCode1205(code);
			print.setAmt1205(value);
		} else if (row == 6) {
			print.setCode1206(code);
			print.setAmt1206(value);
		} else if (row == 7) {
			print.setCode1207(code);
			print.setAmt1207(value);
		} else if (row == 8) {
			print.setCode1208(code);
			print.setAmt1208(value);
		} else if (row == 9) {
			print.setCode1209(code);
			print.setAmt1209(value);
		} else if (row == 10) {
			print.setCode1210(code);
			print.setAmt1210(value);
		} else if (row == 11) {
			print.setCode1211(code);
			print.setAmt1211(value);
		}
	}

	public BigDecimal getThirdPartySickPay(String employeeNumber, String year) {
		return w2InformationDao.getThirdPartySickPay(employeeNumber, year);
	}
	
	private String toString(BigDecimal b) {
		if (b != null) {
			return b.toString();
		} else {
			return "";
		}
	}

	public List<String> retrieveAvailable1095CalYrs(String employeeNumber) {
		return ea1095Dao.getAvailableYears(employeeNumber);
	}

	public String get1095Consent(String employeeNumber) {
		return ea1095Dao.get1095Consent(employeeNumber);
	}

	public List<BhrAca1095bCovrdHist> retrieveEA1095BInfo(String employeeNumber, String year, String sortBy,
			String sortOrder, Integer bPageNo) {
		return ea1095Dao.retrieveEA1095BInfo(employeeNumber, year, sortBy, sortOrder, bPageNo);
	}

	public List<BhrAca1095cCovrdHist> retrieveEA1095CInfo(String employeeNumber, String year, String sortBy,
			String sortOrder, Integer cPageNo) {
		return ea1095Dao.retrieveEA1095CInfo(employeeNumber, year, sortBy, sortOrder, cPageNo);
	}

	public List<Code> retrieveEA1095BEmpInfo(String employeeNumber, String year) {
		return ea1095Dao.retrieveEA1095BEmpInfo(employeeNumber, year);
	}

	public List<EA1095CEmployerShare> retrieveEA1095CEmpInfo(String employeeNumber, String year) {
		return ea1095Dao.retrieveEA1095CEmpInfo(employeeNumber, year);
	}

	public Integer getBInfoTotal(String employeeNumber, String year) {
		return ea1095Dao.getBInfoTotal(employeeNumber, year);
	}

	public Integer getCInfoTotal(String employeeNumber, String year) {
		return ea1095Dao.getCInfoTotal(employeeNumber, year);
	}

	public Boolean update1095ElecConsent(String employeeNumber, String consent) {
		return ea1095Dao.update1095ElecConsent(employeeNumber, consent);
	}
	
	public BrRptngContact getReportingContact() {
		return ea1095Dao.getReportingContact();
	}

	public List<BhrAca1095cEmpHist> retrieveEA1095CEmpInfoPrint(String employeeNumber, String year) {
		return ea1095Dao.retrieveEA1095CEmpInfoPrint(employeeNumber, year);
	}
}
