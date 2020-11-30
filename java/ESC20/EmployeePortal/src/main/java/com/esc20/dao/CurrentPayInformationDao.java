package com.esc20.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.nonDBModels.Account;
import com.esc20.nonDBModels.CurrentPayInformation;
import com.esc20.nonDBModels.EmployeeInfo;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.Stipend;

@Repository
public class CurrentPayInformationDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public List<CurrentPayInformation> getJob(String employeeNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT job.id.jobCd,");
		sql.append("code.jobCdDescr,");
		sql.append("job.nbrAnnualPymts,");
		sql.append("job.regHrsWrk,");
		sql.append("job.nbrRemainPymts,");
		sql.append("job.contrAmt,");
		sql.append("job.dlyRateOfPay,");
		sql.append("job.payRate,");
		sql.append("job.ovtmRate,");
		sql.append("job.primJob,"); // jf20140110 earnings report
		sql.append("job.campusId,"); // jf20140110 earnings report
		sql.append("ISNULL((SELECT DISTINCT B.campusName "); // jf20140110 earnings report campus name
		sql.append("          FROM CrDemo B "); // jf20140110 get campus name
		sql.append("         WHERE B.id.campusId = job.campusId "); // jf20140110 get campus name
		sql.append("           AND B.id.schYr = (SELECT MAX(C.id.schYr) "); // jf20140110 get campus name
		sql.append("                             FROM CrDemo C)), '') AS campusName, "); // jf20140110 get campus name
		sql.append("job.id.payFreq");
		sql.append(" FROM BhrEmpJob job,");
		sql.append(" BthrJobCd code");
		sql.append(" WHERE job.id.empNbr = :employeeNumber");
		sql.append(" AND job.id.cyrNyrFlg = 'C'");
		sql.append(" AND code.id.cyrNyrFlg = 'C'");
		sql.append(" AND job.id.jobCd = code.id.jobCd");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		@SuppressWarnings("unchecked")
		List<Object[]> list = q.list();
		List<CurrentPayInformation> result = new ArrayList<CurrentPayInformation>();
		CurrentPayInformation info;
		for (Object[] item : list) {
			info = new CurrentPayInformation(item[0], item[1], item[2], item[3], item[4], item[5], item[6], item[7],
					item[8], item[9], item[10], item[11], item[12]);
			result.add(info);
		}
		
		return result;
	}

	public List<Stipend> getStipends(String employeeNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT bhr.id.extraDutyCd,");
		sql.append("bthr.extraDutyDescr,");
		sql.append("bthr.defaultAcctTyp,");
		sql.append("bhr.extraDutyAmt,");
		sql.append("bhr.remainAmt,");
		sql.append("bhr.remainPymts,");
		sql.append("bhr.id.payFreq");
		sql.append(" FROM BhrEmpExtraDuty bhr,");
		sql.append(" BthrExtraDuty bthr");
		sql.append(" WHERE bhr.id.empNbr = :employeeNumber");
		sql.append(" AND bhr.id.cyrNyrFlg = 'C'");
		sql.append(" AND bthr.id.cyrNyrFlg = 'C'");
		sql.append(" AND bhr.id.extraDutyCd = bthr.id.extraDutyCd");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		@SuppressWarnings("unchecked")
		List<Object[]> list = q.list();
		List<Stipend> result = new ArrayList<Stipend>();
		Stipend temp;
		for (Object[] item : list) {
			temp = new Stipend(item[0], item[1], item[2], item[3], item[4], item[5], item[6]);
			result.add(temp);
		}
		
		return result;
	}

	public List<Account> getAccounts(String employeeNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT BHR_BANK_DEPOSIT.BANK_CD,");
		sql.append("BTHR_BANK_CODES.BANK_NAME,");
		sql.append("BTHR_BANK_CODES.TRANSIT_ROUTE,");
		sql.append("BHR_BANK_DEPOSIT.PAY_FREQ,");
		sql.append("BHR_BANK_DEPOSIT.BANK_ACCT_NBR,");
		sql.append("BHR_BANK_DEPOSIT.BANK_ACCT_TYP,");
		sql.append("BTHR_BANK_ACCT_TYP.BANK_ACCT_TYP_DESCR,");
		sql.append("BHR_BANK_DEPOSIT.BANK_ACCT_AMT");
		sql.append(" FROM BHR_BANK_DEPOSIT,");
		sql.append("BTHR_BANK_CODES,");
		sql.append("BTHR_BANK_ACCT_TYP");
		sql.append(" WHERE EMP_NBR = :employeeNumber");
		sql.append(" AND CYR_NYR_FLG = 'C'");
		sql.append(" AND BHR_BANK_DEPOSIT.BANK_CD = BTHR_BANK_CODES.BANK_CD");
		sql.append(" AND BHR_BANK_DEPOSIT.BANK_ACCT_TYP = BTHR_BANK_ACCT_TYP.BANK_ACCT_TYP");
		Query q = session.createSQLQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		@SuppressWarnings("unchecked")
		List<Object[]> list = q.list();
		List<Account> result = new ArrayList<Account>();
		Account temp;
		for (Object[] item : list) {
			temp = new Account(item[0], item[1], item[2], item[3], item[4], item[5], item[6], item[7]);
			result.add(temp);
		}
		
		return result;
	}

	public EmployeeInfo getEmployeeInfo(String employeeNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT B.highDegree, deg.highdegDescr,");
		sql.append("B.yrsProExper,");
		sql.append("B.yrsProExperLoc,");
		sql.append("B.yrsExpDist,");
		sql.append("B.yrsExpDistLoc");
		sql.append(" FROM BhrEmpEmply B, EtC015Highdeg deg, BhrOptions opt");
		sql.append(" WHERE B.empNbr = :employeeNumber");
		if (checkHighDegree(employeeNumber) != null && !checkHighDegree(employeeNumber).isEmpty()) {
			sql.append(" AND B.highDegree = deg.id.highdegCd");
		} else {
			sql.append(" AND 1 = deg.id.highdegCd");
		}

		sql.append(" AND deg.id.schYr = opt.peimsCdYr");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		@SuppressWarnings("unchecked")
		List<Object[]> list = q.list();
		Object[] item = list.get(0);
		EmployeeInfo temp = new EmployeeInfo(item[0], item[1], item[2], item[3], item[4], item[5]);
		
		return temp;
	}

	public String checkHighDegree(String empNbr) {
		Session session = this.getSession();
		String sql = "select highDegree from BhrEmpEmply where empNbr = :empNbr ";
		Query q = session.createQuery(sql.toString());
		q.setParameter("empNbr", empNbr);
		String result = ((Character) q.uniqueResult()).toString().trim();
		
		return result;
	}

	public PayInfo getPayInfo(String employeeNumber, Frequency frequency) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT pay.maritalStatTax,");
		sql.append(" pay.nbrTaxExempts,  pay.w4FileStat, pay.w4MultiJob, pay.w4NbrChldrn, ");
		sql.append(" pay.w4NbrOthrDep, pay.w4OthrIncAmt, pay.w4OthrDedAmt, pay.w4OthrExmptAmt  ");
		sql.append(" FROM BhrEmpPay pay");
		sql.append(" WHERE pay.id.cyrNyrFlg = 'C'");
		sql.append(" AND pay.id.payFreq = :frequency");
		sql.append(" AND pay.id.empNbr = :employeeNumber");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		q.setParameter("frequency", frequency.getCode().charAt(0));
		Object[] res = (Object[]) q.uniqueResult();
		PayInfo payinfo = new PayInfo(res[0], res[1], res[2], res[3], res[4], res[5], res[6], res[7], res[8]);
		
		return payinfo;
	}

	public Map<Frequency, String> getPayCampuses(String employeeNumber, Frequency frequency) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT pay.id.payFreq, pay.payCampus + ' : ' + ");
		sql.append(" isnull((select c.campusName from CrDemo c where pay.payCampus = c.id.campusId and c.id.schYr = isnull((select max(d.id.schYr) from CrDemo d where c.id.campusId = d.id.campusId),'')), '') ");
		sql.append(" FROM BhrEmpPay pay ");
		sql.append(" WHERE pay.id.cyrNyrFlg = 'C' ");
		sql.append(" AND pay.id.empNbr = :employeeNumber and pay.id.payFreq = :frequency ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		q.setParameter("frequency", frequency.getCode().charAt(0));
		Object[] res = (Object[]) q.uniqueResult();
		Map<Frequency, String> result = new HashMap<Frequency, String>();
		String resFreq = ((Character) res[0]).toString();
		String payCampus = (String) res[1];
		result.put(Frequency.getFrequency(resFreq), payCampus);
		
		return result;
	}
}
