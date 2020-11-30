package com.esc20.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.esc20.model.BhrCalYtd;
import com.esc20.nonDBModels.Earnings;
import com.esc20.nonDBModels.EarningsBank;
import com.esc20.nonDBModels.EarningsDeductions;
import com.esc20.nonDBModels.EarningsInfo;
import com.esc20.nonDBModels.EarningsJob;
import com.esc20.nonDBModels.EarningsLeave;
import com.esc20.nonDBModels.EarningsOther;
import com.esc20.nonDBModels.EarningsOvertime;
import com.esc20.nonDBModels.EarningsSupplemental;
import com.esc20.nonDBModels.PayDate;
import com.esc20.util.StringUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EarningsDao {
    @Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CalendarYearToDateDao calendarYearToDateDao;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
	public Integer getRestrictEarnings()
	{
		try{
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT isnull(max_days, 0) as max_days from bhr_eap_opt"); 
			Query q = session.createQuery(sql.toString());
			@SuppressWarnings("unchecked")
			List<Object> result = q.list();
			
			if(result!=null && result.size()!=0)
				return (Integer) result.get(0);
			else
				return null;
		}
		catch(Exception e){
			return null;
		}
	}
	
	public List<PayDate> getAvailablePayDates(String employeeNumber) {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT hist.id.payFreq, hist.id.dtOfPay, hist.id.voidOrIss, hist.id.adjNbr, hist.id.chkNbr "); 
			sql.append("FROM BhrPayHist hist ");
			sql.append("WHERE hist.id.empNbr = :employeeNumber ");   //jf20120831 fix CTRS substring
			sql.append("ORDER BY hist.id.dtOfPay DESC ");

			Query q = session.createQuery(sql.toString());
	        q.setParameter("employeeNumber", employeeNumber);

			@SuppressWarnings("unchecked")
			List <Object[]> result = q.list();
			
			List<PayDate> payDates = new ArrayList<PayDate>();
			PayDate payDate;
			
			for(Object[] item: result) {
				payDate = new PayDate(item[0],item[1],item[2],item[3],item[4]);
				payDates.add(payDate);
			}
			return payDates;
	}

	public EarningsInfo getEarningsInfo(String employeeNumber, PayDate payDate) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT hist.payCampus, demo.campusName, hist.id.chkNbr, dates.dtPayperEnd, hist.maritalStatTax, hist.nbrTaxExempts, dates.dtPayperBeg ");
		sql.append("FROM BhrPayHist hist, CrDemo demo, BthrPayDates dates "
				+ "where hist.payCampus = demo.id.campusId AND "
				+ "demo.id.schYr = isnull((select max(crd.id.schYr) from CrDemo crd where hist.payCampus = crd.id.campusId),SUBSTR(hist.id.dtOfPay,1,4)) and ");
		sql.append("hist.id.dtOfPay = dates.id.dtOfPay AND hist.id.payFreq = dates.id.payFreq and ");
		sql.append("hist.id.empNbr=:employeeNumber AND hist.id.payFreq = :frequency AND hist.id.dtOfPay = :dtOfPay AND hist.payCampus != '' "
				+ "AND hist.id.chkNbr = :chkNbr AND hist.id.voidOrIss = :voidOrIss AND hist.id.adjNbr = :adjNbr");
		Query q = session.createQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("dtOfPay", tempDate);
        q.setParameter("voidOrIss", payDate.getVoidIssue().charAt(0));
        q.setParameter("adjNbr", Short.parseShort(payDate.getAdjNumber()));
        q.setParameter("chkNbr", payDate.getCheckNumber());
        Object[] res = (Object[]) q.uniqueResult();
        EarningsInfo info = null;
        if(res!=null)
        	info = new EarningsInfo(res[0],res[1],res[2],res[3],res[4],res[5],res[6]);
		return info;
	}

	public EarningsDeductions getEarningsDeductions(String employeeNumber, PayDate payDate, String checkNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT std_gross, gross_pay_tot, abs_ded_amt, abs_ded_coded, nontrs_nonpay_bus_allow, wh_tax, med_tax, ");
		sql.append("extra_duty_gross, ovtm_gross, abs_ded_refund, taxed_benefits, eic_amt, nontrs_suppl, (nontrs_bus_allow + nontrs_reimbr_excess ) as non_trs_tax_pymt_amt, ");
		sql.append("(nontrs_reimbr_base + nontrs_nontax_bus_allow) as non_trs_nontax_pymt_amt, trs_suppl_comp, fica_tax, trs_salary_red, (bhr_pay_hist.trs_deposit - bhr_pay_hist.trs_salary_red) as trs_ins_amt, ");
		sql.append("sum(case when isnull(bthr_deduc_cd.ded_abbrev_typ, '') = 'WH' then 0 when bhr_pay_deduct_hist.refund_flg = 'Y' then  bhr_pay_deduct_hist.ded_amt * -1 else bhr_pay_deduct_hist.ded_amt end ) as tot_addl_ded, ");
		sql.append("net_pay, nontrs_nontax_nonpay_allow, wh_gross, fica_gross, med_gross ");
		sql.append("FROM bhr_pay_hist ");
		sql.append("LEFT JOIN bhr_pay_deduct_hist ON bhr_pay_hist.emp_nbr = bhr_pay_deduct_hist.emp_nbr AND bhr_pay_hist.dt_of_pay = bhr_pay_deduct_hist.dt_of_pay AND bhr_pay_hist.pay_freq = bhr_pay_deduct_hist.pay_freq AND bhr_pay_hist.chk_nbr = bhr_pay_deduct_hist.chk_nbr ");
		sql.append("LEFT JOIN bthr_deduc_cd ON bhr_pay_deduct_hist.ded_cd = bthr_deduc_cd.ded_cd ");
		sql.append("WHERE bhr_pay_hist.emp_nbr=:is_emp_nbr AND bhr_pay_hist.pay_freq = :is_pay_freq AND bhr_pay_hist.dt_of_pay = :is_dt_of_pay AND bhr_pay_hist.chk_nbr = :is_chk_nbr AND bhr_pay_hist.void_or_iss = :is_void AND bhr_pay_hist.adj_nbr = :is_adj_nbr ");
		sql.append("GROUP BY std_gross, gross_pay_tot, abs_ded_amt, abs_ded_coded, nontrs_nonpay_bus_allow, wh_tax, med_tax, ");
		sql.append("extra_duty_gross, ovtm_gross, abs_ded_refund, taxed_benefits, eic_amt, nontrs_suppl, nontrs_bus_allow, nontrs_reimbr_excess, ");
		sql.append("nontrs_reimbr_base, nontrs_nontax_bus_allow, trs_suppl_comp, fica_tax, trs_salary_red, trs_deposit, trs_salary_red, ");
		sql.append("net_pay, nontrs_nontax_nonpay_allow, wh_gross, fica_gross, med_gross  ");
		
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
		Query q = session.createSQLQuery(sql.toString());
        q.setParameter("is_emp_nbr", employeeNumber);
        q.setParameter("is_pay_freq", tempFreq.charAt(0));
        q.setParameter("is_dt_of_pay", tempDate);
        q.setParameter("is_void", payDate.getVoidIssue().charAt(0));
        q.setParameter("is_adj_nbr", Short.parseShort(payDate.getAdjNumber()));
        q.setParameter("is_chk_nbr", payDate.getCheckNumber());
		Object[] res = (Object[]) q.uniqueResult();
		
		if(res != null) {
			EarningsDeductions deductions = new EarningsDeductions((BigDecimal) res[0],(BigDecimal) res[1],(BigDecimal) res[2],(BigDecimal) res[3],(BigDecimal) res[4],(BigDecimal) res[5],(BigDecimal) res[6],
					(BigDecimal) res[7],(BigDecimal) res[8],(BigDecimal) res[9],(BigDecimal) res[10],(BigDecimal) res[11],(BigDecimal) res[12],(BigDecimal) res[13],
					(BigDecimal) res[14],(BigDecimal) res[15],(BigDecimal) res[16],(BigDecimal) res[17],(BigDecimal) res[18],
					(BigDecimal) res[19],
					(BigDecimal) res[20],(BigDecimal) res[21], (BigDecimal) res[22], (BigDecimal) res[23], (BigDecimal) res[24]);
			return deductions;
		} else {
			return new EarningsDeductions();
		}
		
	}

	public Earnings getBhrCalYTDEarnings(String employeeNumber, PayDate payDate) {
		// BRM-735 DAO for getting an Earnings object with BhrCalYtd data
		String year = payDate.getDateFreq().substring(0, 4);
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		
		BhrCalYtd record = calendarYearToDateDao.getCalenderYTD(employeeNumber, year).stream()
				.filter(cytd -> cytd.getId().getPayFreq() != payDate.getDateFreq().charAt(0))
				.collect(Collectors.toList()).get(0);

		sql.append(
				"SELECT isnull(sum(CASE WHEN isnull(b.ded_abbrev_typ, '') = 'WH' THEN 0 WHEN a.refund_flg = 'Y' THEN a.ded_amt * -1 ELSE a.ded_amt END),0.00) ");
		sql.append("FROM bhr_pay_deduct_hist a ");
		sql.append("LEFT OUTER JOIN bthr_deduc_cd b ON a.ded_cd = b.ded_cd ");
		sql.append("WHERE a.emp_nbr = :emp_nbr ");
		sql.append("AND a.cyr_nyr_flg = 'C' ");
		sql.append("AND a.pay_freq = :pay_freq ");
		sql.append("AND substr(a.dt_of_pay,1,4) = :cal_year ");
		sql.append("AND isnull(b.ded_abbrev_typ,'') <> 'WH' ");
		sql.append("AND a.ded_amt <> 0 ");
		sql.append("AND (a.ded_cd IN ('0CA','0CD','0CH','0CN') OR substr(a.ded_cd, 1, 2) <> '0C') ");
		Query q = session.createSQLQuery(sql.toString());
		q.setParameter("emp_nbr", employeeNumber);
		q.setParameter("pay_freq", record.getId().getPayFreq());
		q.setParameter("cal_year", year);

		BigDecimal totalOtherDeductions = (BigDecimal) q.uniqueResult();
		Earnings ret = new Earnings(record, totalOtherDeductions,
				getEarningsOther(employeeNumber, payDate, "get YTD Other Deduction List"));

		return ret;
	}

	public List<EarningsOther> getEarningsOther(String employeeNumber, PayDate payDate, String checkNumber) {
		// BRM-735 added some logic for getting YTD EarningsOther data
		// ("get YTD Other Deduction List") checks
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT bhr_pay_deduct_hist.ded_cd, CASE WHEN bhr_pay_deduct_hist.ded_cd = '0CE' THEN 'HSA Employer Contrib' WHEN bhr_pay_deduct_hist.ded_cd = '0CA' THEN 'Emplr Annuity Contrib' ");
		sql.append("WHEN bhr_pay_deduct_hist.ded_cd = '0CD' THEN 'Emplr Dependent Care' WHEN bhr_pay_deduct_hist.ded_cd = '0CH' THEN 'TEA Health Insurance Contribution' ");
		sql.append("WHEN bhr_pay_deduct_hist.ded_cd = '0CN' THEN 'Emplr Non-Annuity Contrib' ELSE isnull(bthr_deduc_cd.short_descr,'Not in file') end as short_descr, ");
		if (checkNumber.equals("get YTD Other Deduction List")) {
			sql.append("SUM(");
		}
		sql.append("CASE WHEN refund_flg = 'Y' THEN ded_amt * -1 ELSE ded_amt end ");
		if (checkNumber.equals("get YTD Other Deduction List")) {
			sql.append(")");
		}
		sql.append("as ded_amt, cafe_flg, ");
        sql.append("CASE WHEN bhr_pay_deduct_hist.refund_flg = 'Y' THEN bhr_pay_deduct_hist.emplr_amt * -1 ELSE bhr_pay_deduct_hist.emplr_amt end as emplr_contrib, dep_care_over_max = 0, hsa_emplr_over_max = 0 ");
		sql.append("FROM bhr_pay_deduct_hist ");
		sql.append("LEFT JOIN bthr_deduc_cd ON bhr_pay_deduct_hist.ded_cd = bthr_deduc_cd.ded_cd ");
		sql.append(
				"WHERE bhr_pay_deduct_hist.emp_nbr=:is_emp_nbr AND bhr_pay_deduct_hist.pay_freq = :is_pay_freq AND ");
		if (!checkNumber.equals("get YTD Other Deduction List")) {
			sql.append(
					"bhr_pay_deduct_hist.dt_of_pay = :is_dt_of_pay AND bhr_pay_deduct_hist.chk_nbr = :is_chk_nbr AND ");
		} else {
			sql.append(" substr(bhr_pay_deduct_hist.dt_of_pay,1,4) =  substr(:is_dt_of_pay,1,4) AND ");
		}
		sql.append("bhr_pay_deduct_hist.void_or_iss = :is_void AND  bhr_pay_deduct_hist.adj_nbr = :is_adj_nbr  ");
		sql.append(" and isnull(bthr_deduc_cd.ded_abbrev_typ, '') <> 'WH' "); 
		if (checkNumber.equals("get YTD Other Deduction List")) {
			sql.append(
					"group by bhr_pay_deduct_hist.ded_cd,cafe_flg,short_descr,emplr_contrib,dep_care_over_max,hsa_emplr_over_max ");
		}
		sql.append("UNION SELECT distinct '', '', 0.00, '', 0.00, dep_care_over_max = 1, hsa_emplr_over_max = 0 ");
		sql.append("FROM bhr_cal_ytd  ");
		sql.append("WHERE ( bhr_cal_ytd.emp_nbr = :is_emp_nbr ) AND( bhr_cal_ytd.cyr_nyr_flg = 'C' ) AND ");
		sql.append("( bhr_cal_ytd.pay_freq = :is_pay_freq ) AND ( bhr_cal_ytd.cal_yr = substr(:is_dt_of_pay,1,4) ) AND ");
		sql.append("( bhr_cal_ytd.depend_care + bhr_cal_ytd.emplr_depend_care) > 5000.00 ");
		sql.append("union SELECT distinct '', '', 0.00, '', 0.00, dep_care_over_max = 0, hsa_emplr_over_max = 1 ");
		sql.append("FROM bhr_cal_ytd  ");
		sql.append("WHERE ( bhr_cal_ytd.emp_nbr = :is_emp_nbr ) AND( bhr_cal_ytd.cyr_nyr_flg = 'C' ) AND ");
		sql.append("( bhr_cal_ytd.pay_freq = :is_pay_freq ) AND ( bhr_cal_ytd.cal_yr = substr(:is_dt_of_pay,1,4) ) AND ");
		sql.append("( bhr_cal_ytd.hsa_emplr_contrib) > 3000.00 ");

		Query q = session.createSQLQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("is_emp_nbr", employeeNumber);
        q.setParameter("is_pay_freq", tempFreq.charAt(0));
        q.setParameter("is_dt_of_pay", tempDate);
        q.setParameter("is_void", payDate.getVoidIssue().charAt(0));
        q.setParameter("is_adj_nbr", Short.parseShort(payDate.getAdjNumber()));
		if (!checkNumber.equals("get YTD Other Deduction List")) {
			q.setParameter("is_chk_nbr", checkNumber);
		}
        @SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
        List<EarningsOther> result = new ArrayList<EarningsOther>();
        EarningsOther other;
        for(Object[] item : res) {
        	other = new EarningsOther((String) item[0], (String) item[1], (BigDecimal) item[2], (Character) item[3], (BigDecimal) item[4], (Short) item[5], (Short) item[6]);
        	result.add(other);
        }
        
		return result;
	}

	public List<EarningsJob> getEarningsJob(String employeeNumber, PayDate payDate, String checkNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT bhr_emp_job_hist.job_cd, bhr_emp_job_hist.pay_rate, bhr_emp_job_hist.reg_hrs_wrk, " +
				"bhr_emp_job_hist.xmital_hrs_wrk, bhr_emp_job_hist.pay_type, bthr_job_cd.job_cd_descr ");
		sql.append("FROM bhr_emp_job_hist ");
		sql.append("LEFT JOIN bthr_job_cd ON bhr_emp_job_hist.job_cd = bthr_job_cd.job_cd and " +
					"bhr_emp_job_hist.cyr_nyr_flg = bthr_job_cd.cyr_nyr_flg ");
		sql.append("WHERE bhr_emp_job_hist.emp_nbr=:is_emp_nbr AND bhr_emp_job_hist.pay_freq = :is_pay_freq AND bhr_emp_job_hist.dt_of_pay = :is_dt_of_pay AND bhr_emp_job_hist.chk_nbr = :is_chk_nbr AND bhr_emp_job_hist.void_or_iss = :is_void AND bhr_emp_job_hist.adj_nbr = :is_adj_nbr AND  ");
		sql.append("((bhr_emp_job_hist.pay_rate <> 0 AND bhr_emp_job_hist.pay_type in ('1','2')) OR(bhr_emp_job_hist.pay_rate <> 0 AND bhr_emp_job_hist.pay_type in ('3', '4') ");
		sql.append("AND (bhr_emp_job_hist.reg_hrs_wrk <> 0 OR bhr_emp_job_hist.xmital_hrs_wrk <>  0))) " +
				   " Union all select distinct case when bhr_suppl_xmital.extra_duty_cd = '' then bhr_suppl_xmital.job_cd else bhr_suppl_xmital.extra_duty_cd end, " +
				   "	sum(bhr_suppl_xmital.suppl_amt), 0, 0," +
				   "    bhr_emp_job_hist.pay_type,  " +
								"case when bhr_suppl_xmital.extra_duty_cd = '' then  " +
							     "  (select bthr_job_cd.job_cd_descr  " +
									"    from bthr_job_cd  " +
									"    WHERE bthr_job_cd.cyr_nyr_flg = 'C' and  " +
									"          trim(bthr_job_cd.job_cd) <> '' and  " +
									"          bthr_job_cd.job_cd = bhr_suppl_xmital.job_cd)  " +
							       " else (select bthr_extra_duty.extra_duty_descr  " + 
							    	"	from bthr_extra_duty  " +
							    	"	WHERE bthr_extra_duty.cyr_nyr_flg = 'C' and   " +
							    	"	      trim(bthr_extra_duty.extra_duty_cd) <> '' and  " +
							    	"	      bthr_extra_duty.extra_duty_cd = bhr_suppl_xmital.extra_duty_cd)  " +
							       " end as display " +
							"  from bhr_suppl_xmital, bhr_emp_job_hist " +
							" where bhr_emp_job_hist.pay_freq = :is_pay_freq  and " +
							"       bhr_emp_job_hist.emp_nbr = :is_emp_nbr and " +
							"       bhr_emp_job_hist.dt_of_pay = :is_dt_of_pay and " +
							"       bhr_emp_job_hist.chk_nbr = :is_chk_nbr and " +
							"       bhr_emp_job_hist.void_or_iss = :is_void and " +
							"       bhr_emp_job_hist.adj_nbr = :is_adj_nbr and " +
							"       0 = :is_adj_nbr and " +
							"       bhr_suppl_xmital.pay_freq = bhr_emp_job_hist.pay_freq and " +
							"       bhr_suppl_xmital.dt_of_pay = bhr_emp_job_hist.dt_of_pay and " +
							"       bhr_suppl_xmital.emp_nbr = bhr_emp_job_hist.emp_nbr and " +
							"       bhr_suppl_xmital.job_cd = bhr_emp_job_hist.job_cd and " +
							"       bhr_suppl_xmital.add_std_gross_cd = 'Y' and " +
							"       bhr_suppl_xmital.non_trs_pymt_typ = '' " +
							" group by bhr_suppl_xmital.job_cd,  " +
							"         bhr_emp_job_hist.job_cd, " +
							"	 bhr_emp_job_hist.pay_type, " +
							"	 bhr_suppl_xmital.extra_duty_cd");
		Query q = session.createSQLQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("is_emp_nbr", employeeNumber);
        q.setParameter("is_pay_freq", tempFreq.charAt(0));
        q.setParameter("is_dt_of_pay", tempDate);
        q.setParameter("is_void", payDate.getVoidIssue().charAt(0));
        q.setParameter("is_adj_nbr", Short.parseShort(payDate.getAdjNumber()));
        q.setParameter("is_chk_nbr", payDate.getCheckNumber());
        @SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
        List<EarningsJob> result = new ArrayList<EarningsJob>();
        EarningsJob job;
        for(Object[] item : res) {
        	job = new EarningsJob((String) item[0], (BigDecimal) item[1], (BigDecimal) item[2], (BigDecimal) item[3], (Character) item[4], (String) item[5]);
        	result.add(job);
        }
        
		return result;
	}
	public List<EarningsSupplemental> getEarningsSupplemental(String employeeNumber, PayDate payDate,
			String checkNumber){
		List<Character> types = new ArrayList<Character>();
		types.add('S');
		return getEarningsFromBhrPayDistrHist(employeeNumber,payDate,types);
	}
	public List<EarningsSupplemental> getEarningsFromBhrPayDistrHist(String employeeNumber, PayDate payDate, List<Character> types) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();		
		sql.append("SELECT   sum(hist.acctAmt) as edAmt,  ");
		sql.append("		(case when trim(hist.extraDutyCd) = '' then 'ZZZ' else hist.extraDutyCd end) as edCd,");
		sql.append("	 (case when trim(hist.extraDutyCd) = '' then 'ZZZ' else hist.extraDutyCd end) || ' - ' || isnull(duty.extraDutyDescr, 'Not in file'),");
		sql.append("     hist.id.empNbr as bhrPayDeductHistEmpNbr,  ");
		sql.append("     hist.id.chkNbr as bhrPayDistrHistChkNbr,");
		sql.append("     hist.id.voidOrIss as bhrPayDistrHisVoidOrIss,");
		sql.append("     hist.id.adjNbr as bhrPayDistrHistAdjNbr,");
		sql.append("     hist.id.dtOfPay as bhrPayDistrHistDtOfPay ");
		sql.append("FROM BhrPayDistrHist hist, BthrExtraDuty duty ");
		sql.append("                            where hist.id.cyrNyrFlg = duty.id.cyrNyrFlg and");
		sql.append("                               hist.extraDutyCd = duty.id.extraDutyCd and ");
		sql.append("hist.id.empNbr=:employeeNumber AND hist.id.payFreq = :frequency AND hist.id.dtOfPay = :dtOfPay AND hist.id.chkNbr = :chkNbr AND hist.id.voidOrIss = :voidOrIss AND hist.id.adjNbr = :adjNbr AND ");
		sql.append("     hist.acctTyp in (:list) ");
		sql.append("group by hist.id.payFreq,   ");
		sql.append("     hist.id.empNbr,   ");
		sql.append("     hist.id.cyrNyrFlg,   ");
		sql.append("     hist.id.dtOfPay,   ");
		sql.append("     hist.id.chkNbr,   ");
		sql.append("     hist.id.voidOrIss,   ");
		sql.append("     hist.id.adjNbr,   ");
		sql.append("     hist.acctTyp,   ");
		sql.append("     hist.extraDutyCd,   ");
		sql.append("     duty.extraDutyDescr");
		Query q = session.createQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("dtOfPay", tempDate);
        q.setParameter("voidOrIss", payDate.getVoidIssue().charAt(0));
        q.setParameter("adjNbr", Short.parseShort(payDate.getAdjNumber()));
        q.setParameter("chkNbr", payDate.getCheckNumber());
        q.setParameterList("list", types.toArray());
        @SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
        List<EarningsSupplemental> result = new ArrayList<EarningsSupplemental>();
        EarningsSupplemental suppl;
        for(Object[] item : res) {
        	suppl = new EarningsSupplemental((BigDecimal) item[0], (String) item[1], (String) item[2], (String) item[3], (String) item[4], 
        			(Character) item[5],(Short) item[6],(String) item[7]);
        	result.add(suppl);
        }
        
		return result;
	}

	public List<EarningsSupplemental> getEarningsNonTrsTax(String employeeNumber, PayDate payDate, String checkNumber) {
		List<Character> types = new ArrayList<Character>();
		types.add('B');
		types.add('I');
		return getEarningsFromBhrPayDistrHist(employeeNumber,payDate,types);
	}

	public List<EarningsSupplemental> getEarningsNonTrsNonTax(String employeeNumber, PayDate payDate,
			String checkNumber) {
		List<Character> types = new ArrayList<Character>();
		types.add('T');
		return getEarningsFromBhrPayDistrHist(employeeNumber,payDate,types);
	}

	public List<EarningsBank> getEarningsBank(String employeeNumber, PayDate payDate, String checkNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT hist.id.bankCd, codes.bankName, hist.id.bankAcctTyp,   type.bankAcctTypDescr, isnull(hist.id.bankAcctNbr,'') as bankAcctNbr, hist.bankAcctAmt ");
		sql.append("FROM BhrBankDepositHist hist , BthrBankCodes codes , BthrBankAcctTyp type where codes.bankCd = hist.id.bankCd ");
		sql.append("and type.bankAcctTyp = hist.id.bankAcctTyp ");
		sql.append("and hist.id.empNbr=:employeeNumber AND hist.id.payFreq = :frequency AND hist.id.dtOfPay = :dtOfPay AND hist.id.chkNbr = :chkNbr AND hist.id.voidOrIss = :voidOrIss AND hist.id.adjNbr = :adjNbr");
		Query q = session.createQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("dtOfPay", tempDate);
        q.setParameter("voidOrIss", payDate.getVoidIssue().charAt(0));
        q.setParameter("adjNbr", Short.parseShort(payDate.getAdjNumber()));
        q.setParameter("chkNbr", payDate.getCheckNumber());
        @SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
        List<EarningsBank> result = new ArrayList<EarningsBank>();
        EarningsBank bank;
        for(Object[] item : res) {
        	bank = new EarningsBank((String) item[0], (String) item[1], (Character) item[2], (String) item[3], (String) item[4],(BigDecimal) item[5]);
        	result.add(bank);
        }
        
		return result;
		
	}

	public List<EarningsLeave> getEarningsLeave(String employeeNumber, PayDate payDate, String checkNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT bhr_emp_lv.lv_typ as bhr_emp_lv_lv_typ,bthr_lv_typ_descr.long_descr as bthr_lv_typ_descr_long_descr,   ");
		sql.append(
				"bhr_emp_lv_hist_lv_taken = isnull(bhr_emp_lv_dock_hist.lv_used,0),bhr_emp_lv.lv_end_bal as bhr_emp_lv_lv_end_bal,  ");
		sql.append(
				"bthr_lv_typ.chk_stub_pos as bthr_lv_typ_chk_stub_pos,bhr_emp_lv.lv_used as bhr_emp_lv_lv_used       ");
		sql.append(",isnull((select max(b.dt_of_pay) from bhr_payrun_options b, bthr_pay_dates_status c where  ");
		sql.append("b.pay_freq = :is_pay_freq and  ");
		sql.append("b.zero_sch_ytd = 'S' and  ");
		sql.append("b.adj_nbr = 0 and  ");
		sql.append("c.pay_freq = b.pay_freq and  ");
		sql.append("c.dt_of_pay = b.dt_of_pay and  ");
		sql.append("c.adj_nbr = b.adj_nbr and  ");
		sql.append("c.dt_pay_run <> ''),  ");
		sql.append(":is_dt_of_pay) as first_paydate_sch_yr_pay  ");
		sql.append("FROM (bhr_emp_lv left outer join bhr_emp_lv_dock_hist on  ");
		sql.append("bhr_emp_lv_dock_hist.cyr_nyr_flg = 'C' and  ");
		sql.append("bhr_emp_lv_dock_hist.pay_freq = :is_pay_freq and  ");
		sql.append("bhr_emp_lv_dock_hist.emp_nbr = :is_emp_nbr and  ");
		sql.append("bhr_emp_lv_dock_hist.dt_of_pay = :is_dt_of_pay and  ");
		sql.append("bhr_emp_lv_dock_hist.void_or_iss = :is_void and  ");
		sql.append("bhr_emp_lv_dock_hist.chk_nbr = :is_chk_nbr and  ");
		sql.append("bhr_emp_lv_dock_hist.lv_alt_typ = bhr_emp_lv.lv_typ and  ");
		sql.append(
				"string(bhr_emp_lv_dock_hist.adj_nbr) = CASE WHEN LENGTH(TRIM(:is_adj_nbr))=0 THEN string(bhr_emp_lv_dock_hist.adj_nbr) ELSE :is_adj_nbr END),  ");
		sql.append("bthr_lv_typ_descr,bthr_lv_typ,bhr_payrun_options  ");
		sql.append("WHERE bhr_emp_lv.cyr_nyr_flg = 'C' and  ");
		sql.append("bhr_emp_lv.pay_freq = :is_pay_freq and  ");
		sql.append("bhr_emp_lv.emp_nbr = :is_emp_nbr and  ");
		sql.append("((bhr_emp_lv_hist_lv_taken <> 0 and  ");
		sql.append(":is_dt_of_pay < first_paydate_sch_yr_pay) or  ");
		sql.append(":is_dt_of_pay >= first_paydate_sch_yr_pay) and  ");
		sql.append("bthr_lv_typ_descr.lv_typ = bhr_emp_lv.lv_typ and  ");
		sql.append("bthr_lv_typ.pay_freq = bhr_emp_lv.pay_freq and  ");
		sql.append("bthr_lv_typ.lv_typ = bhr_emp_lv.lv_typ and  ");
		sql.append("bthr_lv_typ.chk_stub_pos <> '' and  ");
		sql.append("bhr_payrun_options.dt_of_pay = :is_dt_of_pay and  ");
		sql.append("bhr_payrun_options.pay_freq = bhr_emp_lv.pay_freq and  ");
		sql.append("bhr_payrun_options.adj_nbr = 0 and  ");
		sql.append("bthr_lv_typ.add_subtract_bal = 'S'  ");
		sql.append(
				"union all SELECT distinct bhr_emp_lv_dock_hist.lv_alt_typ, isnull(bthr_lv_typ_descr.long_descr, 'Not in file' ),  ");
		sql.append("bhr_emp_lv_dock_hist.lv_used,0,bthr_lv_typ.chk_stub_pos,0     ");
		sql.append(",isnull((select max(b.dt_of_pay) from bhr_payrun_options b, bthr_pay_dates_status c where  ");
		sql.append("b.pay_freq = :is_pay_freq and  ");
		sql.append("b.zero_sch_ytd = 'S' and  ");
		sql.append("b.adj_nbr = 0 and  ");
		sql.append("c.pay_freq = b.pay_freq and  ");
		sql.append("c.dt_of_pay = b.dt_of_pay and  ");
		sql.append("c.adj_nbr = b.adj_nbr and  ");
		sql.append("c.dt_pay_run <> ''),   ");
		sql.append(":is_dt_of_pay) as first_paydate_sch_yr_pay  ");
		sql.append(
				"FROM (bhr_emp_lv_dock_hist left outer join bthr_lv_typ_descr on bhr_emp_lv_dock_hist.lv_alt_typ = bthr_lv_typ_descr.lv_typ),  ");
		sql.append("bhr_options,bthr_lv_typ,bhr_payrun_options  ");
		sql.append("WHERE bhr_emp_lv_dock_hist.cyr_nyr_flg = 'C' and  ");
		sql.append("bhr_emp_lv_dock_hist.pay_freq = :is_pay_freq and  ");
		sql.append("bhr_emp_lv_dock_hist.emp_nbr = :is_emp_nbr and  ");
		sql.append("bhr_emp_lv_dock_hist.dt_of_pay = :is_dt_of_pay and  ");
		sql.append("bhr_emp_lv_dock_hist.void_or_iss = :is_void and  ");
		sql.append("bhr_emp_lv_dock_hist.chk_nbr = :is_chk_nbr and  ");
		sql.append("bhr_emp_lv_dock_hist.lv_used <> 0 and  ");
		sql.append("bthr_lv_typ.lv_typ = bhr_emp_lv_dock_hist.lv_alt_typ and  ");
		sql.append("bthr_lv_typ.pay_freq = bhr_emp_lv_dock_hist.pay_freq and  ");
		sql.append("bthr_lv_typ.chk_stub_pos <> '' and  ");
		sql.append("bthr_lv_typ.add_subtract_bal = 'S' and  ");
		sql.append("not exists(select 'X' from bhr_emp_lv  ");
		sql.append("where bhr_emp_lv_dock_hist.cyr_nyr_flg = bhr_emp_lv.cyr_nyr_flg and  ");
		sql.append("bhr_emp_lv_dock_hist.pay_freq = bhr_emp_lv.pay_freq and  ");
		sql.append("bhr_emp_lv_dock_hist.emp_nbr = bhr_emp_lv.emp_nbr and  ");
		sql.append("bhr_emp_lv_dock_hist.lv_alt_typ = bhr_emp_lv.lv_typ)  ");
		sql.append("UNION ALL SELECT  bhr_emp_lv.lv_typ, bthr_lv_typ_descr.long_descr,  ");
		sql.append(
				"bhr_emp_lv_hist_lv_taken = isnull(bhr_emp_lv_hist.lv_taken,0), bhr_emp_lv.lv_end_bal, bthr_lv_typ.chk_stub_pos, bhr_emp_lv.lv_used    ");
		sql.append(",isnull((select max(b.dt_of_pay) from bhr_payrun_options b, bthr_pay_dates_status c where  ");
		sql.append("b.pay_freq = :is_pay_freq and  ");
		sql.append("b.zero_sch_ytd = 'S' and  ");
		sql.append("b.adj_nbr = 0 and  ");
		sql.append("c.pay_freq = b.pay_freq and  ");
		sql.append("c.dt_of_pay = b.dt_of_pay and  ");
		sql.append("c.adj_nbr = b.adj_nbr and  ");
		sql.append("c.dt_pay_run <> ''),  ");
		sql.append(":is_dt_of_pay) as first_paydate_sch_yr_pay  ");
		sql.append("FROM (bhr_emp_lv left outer join bhr_emp_lv_hist on  ");
		sql.append("bhr_emp_lv_hist.cyr_nyr_flg = 'C' and  ");
		sql.append("bhr_emp_lv_hist.pay_freq = :is_pay_freq and  ");
		sql.append("bhr_emp_lv_hist.emp_nbr = :is_emp_nbr and  ");
		sql.append("bhr_emp_lv_hist.dt_of_pay = :is_dt_of_pay and  ");
		sql.append("bhr_emp_lv_hist.void_or_iss = :is_void and  ");
		sql.append("bhr_emp_lv_hist.chk_nbr = :is_chk_nbr and  ");
		sql.append("bhr_emp_lv_hist.lv_typ = bhr_emp_lv.lv_typ and  ");
		sql.append("string(bhr_emp_lv_hist.adj_nbr) = CASE WHEN LENGTH(TRIM(:is_adj_nbr))=0  ");
		sql.append("THEN string(bhr_emp_lv_hist.adj_nbr) ELSE :is_adj_nbr END),  ");
		sql.append("bthr_lv_typ_descr, bthr_lv_typ, bhr_payrun_options  ");
		sql.append("WHERE bhr_emp_lv.cyr_nyr_flg = 'C' and  ");
		sql.append("bhr_emp_lv.pay_freq = :is_pay_freq and  ");
		sql.append("bhr_emp_lv.emp_nbr = :is_emp_nbr and  ");
		sql.append("((bhr_emp_lv_hist_lv_taken <> 0 and  ");
		sql.append(":is_dt_of_pay < first_paydate_sch_yr_pay) or  ");
		sql.append(":is_dt_of_pay >= first_paydate_sch_yr_pay) and  ");
		sql.append("bthr_lv_typ_descr.lv_typ = bhr_emp_lv.lv_typ and  ");
		sql.append("bthr_lv_typ.pay_freq = bhr_emp_lv.pay_freq and  ");
		sql.append("bthr_lv_typ.lv_typ = bhr_emp_lv.lv_typ and  ");
		sql.append("bthr_lv_typ.chk_stub_pos <> '' and  ");
		sql.append("bhr_payrun_options.dt_of_pay = :is_dt_of_pay and  ");
		sql.append("bhr_payrun_options.pay_freq = bhr_emp_lv.pay_freq and  ");
		sql.append("bhr_payrun_options.adj_nbr = 0 and  ");
		sql.append("bthr_lv_typ.add_subtract_bal = 'A'  ");
		sql.append(
				"union all SELECT distinct bhr_emp_lv_hist.lv_typ, isnull(bthr_lv_typ_descr.long_descr, 'Not in file' ),   ");
		sql.append("bhr_emp_lv_hist.lv_taken, 0, bthr_lv_typ.chk_stub_pos, 0  ");
		sql.append(",isnull((select max(b.dt_of_pay) from bhr_payrun_options b, bthr_pay_dates_status c where  ");
		sql.append("b.pay_freq = :is_pay_freq and  ");
		sql.append("b.zero_sch_ytd = 'S' and  ");
		sql.append("b.adj_nbr = 0 and  ");
		sql.append("c.pay_freq = b.pay_freq and  ");
		sql.append("c.dt_of_pay = b.dt_of_pay and  ");
		sql.append("c.adj_nbr = b.adj_nbr and  ");
		sql.append("c.dt_pay_run <> ''),   ");
		sql.append(":is_dt_of_pay) as first_paydate_sch_yr_pay  ");
		sql.append(
				"FROM (bhr_emp_lv_hist left outer join bthr_lv_typ_descr on bhr_emp_lv_hist.lv_typ = bthr_lv_typ_descr.lv_typ),   ");
		sql.append("bhr_options, bthr_lv_typ, bhr_payrun_options  ");
		sql.append("WHERE bhr_emp_lv_hist.cyr_nyr_flg = 'C' and  ");
		sql.append("bhr_emp_lv_hist.pay_freq = :is_pay_freq and  ");
		sql.append("bhr_emp_lv_hist.emp_nbr = :is_emp_nbr and  ");
		sql.append("bhr_emp_lv_hist.dt_of_pay = :is_dt_of_pay and  ");
		sql.append("bhr_emp_lv_hist.void_or_iss = :is_void and  ");
		sql.append("bhr_emp_lv_hist.chk_nbr = :is_chk_nbr and  ");
		sql.append("bhr_emp_lv_hist.lv_taken <> 0 and  ");
		sql.append("bthr_lv_typ.lv_typ = bhr_emp_lv_hist.lv_typ and  ");
		sql.append("bthr_lv_typ.pay_freq = bhr_emp_lv_hist.pay_freq and  ");
		sql.append("bthr_lv_typ.chk_stub_pos <> '' and  ");
		sql.append("bthr_lv_typ.add_subtract_bal = 'A' AND  ");
		sql.append("not exists(select 'X' from bhr_emp_lv  ");
		sql.append("where bhr_emp_lv_hist.cyr_nyr_flg = bhr_emp_lv.cyr_nyr_flg and  ");
		sql.append("bhr_emp_lv_hist.pay_freq = bhr_emp_lv.pay_freq and  ");
		sql.append("bhr_emp_lv_hist.emp_nbr = bhr_emp_lv.emp_nbr and  ");
		sql.append("bhr_emp_lv_hist.lv_typ = bhr_emp_lv.lv_typ)  ");

		Query q = session.createSQLQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("is_emp_nbr", employeeNumber);
        q.setParameter("is_pay_freq", tempFreq.charAt(0));
        q.setParameter("is_dt_of_pay", tempDate);
        q.setParameter("is_void", payDate.getVoidIssue().charAt(0));
        q.setParameter("is_adj_nbr", Short.parseShort(payDate.getAdjNumber()));
        q.setParameter("is_chk_nbr", payDate.getCheckNumber());
        @SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
        List<EarningsLeave> result = new ArrayList<EarningsLeave>();
        for(Object[] item : res) {
        	EarningsLeave leave = new EarningsLeave((String) item[0], (String) item[1], (BigDecimal) item[2],  payDate.getVoidIssue().charAt(0), BigDecimal.valueOf(Long.valueOf(payDate.getAdjNumber())), (BigDecimal) item[3], (Character) item[4],(BigDecimal) item[5]);
        	result.add(leave);
        }
        
		return result;
	}

	public List<EarningsOvertime> getEarningsOvertime(String employeeNumber, PayDate payDate) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct suppl.jobCd as jobCd, cd.jobCdDescr,");
		sql.append("	         sum(suppl.supplAmt) as totSupplAmt, ");
		sql.append("	         sum(suppl.ovtmHrsWrk) as totHrsWrk, ");
		sql.append("	         case when suppl.ovtmHrsWrk > 0 then round(suppl.supplAmt / suppl.ovtmHrsWrk,2) else 0 end as ovtmRate ");
		sql.append("FROM BhrSupplXmital suppl, BthrJobCd cd ");
		sql.append("	   where suppl.id.empNbr = :employeeNumber and suppl.jobCd = cd.id.jobCd and suppl.glFileId = cd.glFileId and " );
		sql.append("	         suppl.id.payFreq = :frequency and ");
		sql.append("	         suppl.id.dtOfPay = :dtOfPay and ");
		sql.append("	         suppl.transmittalType = 'O' and " +
				   "		     cd.id.cyrNyrFlg = 'C' ");
		sql.append("	group by suppl.jobCd, ");
		sql.append("	         suppl.supplAmt, ");
		sql.append("	 		suppl.ovtmHrsWrk, " );
		sql.append("cd.jobCdDescr");
		Query q = session.createQuery(sql.toString());
		String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
		String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("dtOfPay", tempDate);
        @SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
        List<EarningsOvertime> result = new ArrayList<EarningsOvertime>();
        EarningsOvertime overTime;
        for(Object[] item : res) {
        	overTime = new EarningsOvertime((String) item[0], (String) item[1], (BigDecimal) item[2], (BigDecimal) item[3], (BigDecimal) item[4]);
        	result.add(overTime);
        }
        
		return result;
	}

	public BigDecimal getEmplrPrvdHlthcare(String employeeNumber, PayDate latestPayDate) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ytd.emplrPrvdHlthcare from BhrCalYtd ytd where ytd.id.empNbr =:employeeNumber and ");
		sql.append("	         ytd.id.payFreq =:frequency and ");
		sql.append("	         ytd.id.cyrNyrFlg = 'C' and ");
		sql.append("	         ytd.id.calYr =:calYr ");
		Query q = session.createQuery(sql.toString());
		String tempFreq = StringUtil.right(latestPayDate.getDateFreq(), 1);
		String year = latestPayDate.getDateFreq().substring(0, 4);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("frequency", tempFreq.charAt(0));
        q.setParameter("calYr", year);
        BigDecimal res = (BigDecimal) q.uniqueResult();
        
		return res;
	}
}
