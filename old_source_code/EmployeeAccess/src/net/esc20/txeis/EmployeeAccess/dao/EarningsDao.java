package net.esc20.txeis.EmployeeAccess.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.dao.api.IEarningsDao;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsBank;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsDeductions;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsJob;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsLeave;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsOther;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsOvertime;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsSupplemental;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsNonTrsTax;
import net.esc20.txeis.EmployeeAccess.domainobject.EarningsNonTrsNonTax;
import net.esc20.txeis.EmployeeAccess.domainobject.PayDate;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.util.NumberUtil;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public class EarningsDao extends NamedParameterJdbcDaoSupport implements IEarningsDao{
	
	private static Log log = LogFactory.getLog(EarningsDao.class);
	
	@Override
	public Integer getRestrictEarnings()
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT isnull(max_days, 0) as max_days from bhr_eap_opt"); 

			MapSqlParameterSource params = new MapSqlParameterSource();
	
			Integer result = (Integer)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, Integer.class);
			
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No Restriction");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PayDate> getAvailablePayDates(String employeeNumber) {
		//cs20170613 work with max_days
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT Distinct TOP 50 bhr_pay_hist.pay_freq, bhr_pay_hist.dt_of_pay, bhr_pay_hist.void_or_iss, bhr_pay_hist.adj_nbr, bhr_pay_hist.chk_nbr, "); 
			sql.append("PAY_DATE_VIEW = datediff(dd, bhr_pay_hist.dt_of_pay, getdate()), bhr_eap_opt.max_days "); 
			sql.append("FROM bhr_pay_hist, bhr_eap_opt ");
			sql.append("WHERE emp_nbr = :is_emp_nbr AND SUBSTRING(CHK_NBR, 1, 4) != 'CYTD' AND SUBSTRING(CHK_NBR, 1, 4) != 'AVAR' ");
			sql.append("AND SUBSTRING(CHK_NBR, 1, 4) != 'SYTD' AND SUBSTRING(CHK_NBR, 1, 4) != 'SADJ' AND SUBSTRING(CHK_NBR, 1, 4) != 'ADJU' ");   //jf20120831 fix SYTD substring
			sql.append("AND SUBSTRING(CHK_NBR, 1, 4) != 'CTRS' ");   //jf20120831 fix CTRS substring
			sql.append("AND ( PAY_DATE_VIEW + bhr_eap_opt.max_days ) >= 0 ");
			sql.append("ORDER BY dt_of_pay DESC ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			params.addValue("is_emp_nbr", employeeNumber);

			List <PayDate> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new PayDateMapper());
		
			return result;
		}
		catch (EmptyResultDataAccessException e) {
			log.info("No pay dates found for employee.");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EarningsInfo getEarningsInfo(String employeeNumber, PayDate payDate)
	{
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT bhr_pay_hist.pay_campus, cr_demo.campus_name, bhr_pay_hist.chk_nbr, bthr_pay_dates.dt_payper_end, bhr_pay_hist.marital_stat_tax, bhr_pay_hist.nbr_tax_exempts, bthr_pay_dates.dt_payper_beg ");
			sql.append("FROM bhr_pay_hist ");
			sql.append("LEFT JOIN cr_demo ON  bhr_pay_hist.pay_campus = cr_demo.campus_id AND cr_demo.sch_yr = SUBSTR(bhr_pay_hist.dt_of_pay,0, 5) ");
			sql.append("LEFT JOIN bthr_pay_dates ON  bhr_pay_hist.dt_of_pay = bthr_pay_dates.dt_of_pay AND bhr_pay_hist.pay_freq = bthr_pay_dates.pay_freq ");
			sql.append("WHERE bhr_pay_hist.emp_nbr=:is_emp_nbr AND bhr_pay_hist.pay_freq = :is_pay_freq AND bhr_pay_hist.dt_of_pay = :is_dt_of_pay AND bhr_pay_hist.pay_campus != '' AND bhr_pay_hist.chk_nbr = :is_chk_nbr AND bhr_pay_hist.void_or_iss = :is_void AND bhr_pay_hist.adj_nbr = :is_adj_nbr");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
			String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
			
			params.addValue("is_emp_nbr", employeeNumber);
			params.addValue("is_pay_freq", tempFreq);
			params.addValue("is_dt_of_pay", tempDate);
			params.addValue("is_void", payDate.getVoidIssue());
			params.addValue("is_adj_nbr", payDate.getAdjNumber());
			params.addValue("is_chk_nbr", payDate.getCheckNumber());
	
			List<EarningsInfo> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new EarningsInfoMapper());
			
			if(result != null && result.size() > 0)
			{
				return result.get(0);
			}
			else
			{
				return new EarningsInfo();
			}
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No Employee Earnings Info Found");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EarningsDeductions getEarningsDeductions(String employeeNumber, PayDate payDate, String checkNumber)
	{
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT std_gross, gross_pay_tot, abs_ded_amt, abs_ded_coded, nontrs_nonpay_bus_allow, wh_tax, med_tax, ");
			sql.append("extra_duty_gross, ovtm_gross, abs_ded_refund, taxed_benefits, eic_amt, nontrs_suppl, (nontrs_bus_allow + nontrs_reimbr_excess ) as non_trs_tax_pymt_amt, ");
			sql.append("(nontrs_reimbr_base + nontrs_nontax_bus_allow) as non_trs_nontax_pymt_amt, trs_suppl_comp, fica_tax, trs_salary_red, (bhr_pay_hist.trs_deposit - bhr_pay_hist.trs_salary_red) as trs_ins_amt, ");
			sql.append("sum(case when isnull(bthr_deduc_cd.ded_abbrev_typ, '') = 'WH' then 0 when bhr_pay_deduct_hist.refund_flg = 'Y' then  bhr_pay_deduct_hist.ded_amt * -1 else bhr_pay_deduct_hist.ded_amt end ) as tot_addl_ded, ");
			sql.append("net_pay, nontrs_nonpay_bus_allow, nontrs_nontax_nonpay_allow ");
			sql.append("FROM bhr_pay_hist ");
			sql.append("LEFT JOIN bhr_pay_deduct_hist ON bhr_pay_hist.emp_nbr = bhr_pay_deduct_hist.emp_nbr AND bhr_pay_hist.dt_of_pay = bhr_pay_deduct_hist.dt_of_pay AND bhr_pay_hist.pay_freq = bhr_pay_deduct_hist.pay_freq AND bhr_pay_hist.chk_nbr = bhr_pay_deduct_hist.chk_nbr ");
			sql.append("LEFT JOIN bthr_deduc_cd ON bhr_pay_deduct_hist.ded_cd = bthr_deduc_cd.ded_cd ");
			sql.append("WHERE bhr_pay_hist.emp_nbr=:is_emp_nbr AND bhr_pay_hist.pay_freq = :is_pay_freq AND bhr_pay_hist.dt_of_pay = :is_dt_of_pay AND bhr_pay_hist.chk_nbr = :is_chk_nbr AND bhr_pay_hist.void_or_iss = :is_void AND bhr_pay_hist.adj_nbr = :is_adj_nbr ");
			sql.append("GROUP BY std_gross, gross_pay_tot, abs_ded_amt, abs_ded_coded, nontrs_nonpay_bus_allow, wh_tax, med_tax, ");
			sql.append("extra_duty_gross, ovtm_gross, abs_ded_refund, taxed_benefits, eic_amt, nontrs_suppl, nontrs_bus_allow, nontrs_reimbr_excess, ");
			sql.append("nontrs_reimbr_base, nontrs_nontax_bus_allow, trs_suppl_comp, fica_tax, trs_salary_red, trs_deposit, trs_salary_red, ");
			sql.append("net_pay, nontrs_nonpay_bus_allow, nontrs_nontax_nonpay_allow  ");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
			String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
			String tempVoid = payDate.getVoidIssue();
			String tempAdj = payDate.getAdjNumber();
			
			params.addValue("is_emp_nbr", employeeNumber);
			params.addValue("is_pay_freq", tempFreq);
			params.addValue("is_dt_of_pay", tempDate);
			params.addValue("is_void", tempVoid);
			params.addValue("is_adj_nbr", tempAdj);
			params.addValue("is_chk_nbr", checkNumber);
	
			List<EarningsDeductions> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new EarningsDeductionsMapper());
			
			if(result != null && result.size() > 0)
			{
				return result.get(0);
			}
			else
			{
				return new EarningsDeductions();
			}
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No Employee Earnings/Deductions Found");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List <EarningsOther> getEarningsOther(String employeeNumber, PayDate payDate, String checkNumber)
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT bhr_pay_deduct_hist.ded_cd, CASE WHEN bhr_pay_deduct_hist.ded_cd = '0CE' THEN 'HSA Employer Contrib' WHEN bhr_pay_deduct_hist.ded_cd = '0CA' THEN 'Emplr Annuity Contrib' ");
			sql.append("WHEN bhr_pay_deduct_hist.ded_cd = '0CD' THEN 'Emplr Dependent Care' WHEN bhr_pay_deduct_hist.ded_cd = '0CH' THEN 'TEA Health Insurance Contribution' ");
            sql.append("WHEN bhr_pay_deduct_hist.ded_cd = '0CN' THEN 'Emplr Non-Annuity Contrib' ELSE isnull(bthr_deduc_cd.short_descr,'Not in file') end as short_descr, ");
            sql.append("CASE WHEN refund_flg = 'Y' THEN ded_amt * -1 ELSE ded_amt end as ded_amt, cafe_flg, ");
            sql.append("CASE WHEN bhr_pay_deduct_hist.refund_flg = 'Y' THEN bhr_pay_deduct_hist.emplr_amt * -1 ELSE bhr_pay_deduct_hist.emplr_amt end as emplr_contrib, dep_care_over_max = 0, hsa_emplr_over_max = 0 ");
			sql.append("FROM bhr_pay_deduct_hist ");
			sql.append("LEFT JOIN bthr_deduc_cd ON bhr_pay_deduct_hist.ded_cd = bthr_deduc_cd.ded_cd ");
			sql.append("WHERE bhr_pay_deduct_hist.emp_nbr=:is_emp_nbr AND bhr_pay_deduct_hist.pay_freq = :is_pay_freq AND bhr_pay_deduct_hist.dt_of_pay = :is_dt_of_pay AND  bhr_pay_deduct_hist.chk_nbr = :is_chk_nbr AND  bhr_pay_deduct_hist.void_or_iss = :is_void AND  bhr_pay_deduct_hist.adj_nbr = :is_adj_nbr  ");
			sql.append("UNION SELECT distinct '', '', 0.00, '', 0.00, dep_care_over_max = 1, hsa_emplr_over_max = 0 ");
			sql.append("FROM bhr_cal_ytd  ");
			sql.append("WHERE ( bhr_cal_ytd.emp_nbr = :is_emp_nbr ) AND( bhr_cal_ytd.cyr_nyr_flg = 'C' ) AND ");
			sql.append("( bhr_cal_ytd.pay_freq = :is_pay_freq ) AND ( bhr_cal_ytd.cal_yr = substr(:is_dt_of_pay,1,4) ) AND ");
			sql.append("( bhr_cal_ytd.depend_care + bhr_cal_ytd.emplr_depend_care) > 5000.00 ");
			sql.append("union SELECT distinct '', '', 0.00, '', 0.00, dep_care_over_max = 0, hsa_emplr_over_max = 1 ");
			sql.append("FROM bhr_cal_ytd  ");
			sql.append("WHERE ( bhr_cal_ytd.emp_nbr = :is_emp_nbr ) AND( bhr_cal_ytd.cyr_nyr_flg = 'C' ) AND ");
			sql.append("( bhr_cal_ytd.pay_freq = :is_pay_freq ) AND ( bhr_cal_ytd.cal_yr = substr(:is_dt_of_pay,1,4) ) AND ");
			sql.append("( bhr_cal_ytd.hsa_emplr_contrib) > 3000.00");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
			String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
			
			params.addValue("is_emp_nbr", employeeNumber);
			params.addValue("is_pay_freq", tempFreq);
			params.addValue("is_dt_of_pay", tempDate);
			params.addValue("is_void", payDate.getVoidIssue());
			params.addValue("is_adj_nbr", payDate.getAdjNumber());
			params.addValue("is_chk_nbr", checkNumber);
	
			List <EarningsOther> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new EarningsOtherMapper());
			
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No Employee Other Deductions Found");
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List <EarningsJob> getEarningsJob(String employeeNumber, PayDate payDate, String checkNumber)
	{
		try{
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
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
			String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
			
			params.addValue("is_emp_nbr", employeeNumber);
			params.addValue("is_pay_freq", tempFreq);
			params.addValue("is_dt_of_pay", tempDate);
			params.addValue("is_chk_nbr", checkNumber);
			params.addValue("is_void", payDate.getVoidIssue());
			params.addValue("is_adj_nbr", payDate.getAdjNumber());
	
			List <EarningsJob> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new EarningsJobMapper());
			
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No Employee Job Info Found");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List <EarningsSupplemental> getEarningsSupplemental(String employeeNumber, PayDate payDate, String checkNumber)
	{
		try{
			StringBuilder sql = new StringBuilder();		
			sql.append("SELECT   sum(bhr_pay_distr_hist.acct_amt) as ed_amt,  ");
			sql.append("		case when trim(bhr_pay_distr_hist.extra_duty_cd) = ''");
			sql.append("               then 'ZZZ' ");
			sql.append("               else bhr_pay_distr_hist.extra_duty_cd end as ed_cd,");
			sql.append("		short_descr = ed_cd || ' - ' || isnull(bthr_extra_duty.extra_duty_descr, 'Not in file'),");
			sql.append("     bhr_pay_distr_hist.emp_nbr as bhr_pay_deduct_hist_emp_nbr,  ");
			sql.append("     bhr_pay_distr_hist.chk_nbr as bhr_pay_distr_hist_chk_nbr,");
			sql.append("     bhr_pay_distr_hist.void_or_iss as bhr_pay_distr_hist_void_or_iss,");
			sql.append("     bhr_pay_distr_hist.adj_nbr as bhr_pay_distr_hist_adj_nbr,");
			sql.append("     bhr_pay_distr_hist.dt_of_pay as bhr_pay_distr_hist_dt_of_pay ");
			sql.append("FROM {oj bhr_pay_distr_hist left outer join bthr_extra_duty ");
			sql.append("                            on bhr_pay_distr_hist.cyr_nyr_flg = bthr_extra_duty.cyr_nyr_flg and");
			sql.append("                               bhr_pay_distr_hist.extra_duty_cd = bthr_extra_duty.extra_duty_cd} ");
			sql.append("where bhr_pay_distr_hist.emp_nbr = :is_emp_nbr and");
			sql.append("     bhr_pay_distr_hist.pay_freq = :is_pay_freq and");
			sql.append("     bhr_pay_distr_hist.dt_of_pay = :is_dt_of_pay AND bhr_pay_distr_hist.chk_nbr = :is_chk_nbr AND bhr_pay_distr_hist.void_or_iss = :is_void AND bhr_pay_distr_hist.adj_nbr = :is_adj_nbr and");
			sql.append("     bhr_pay_distr_hist.acct_typ = 'S' ");
			sql.append("group by bhr_pay_distr_hist.pay_freq,   ");
			sql.append("     bhr_pay_distr_hist.emp_nbr,   ");
			sql.append("     bhr_pay_distr_hist.cyr_nyr_flg,   ");
			sql.append("     bhr_pay_distr_hist.dt_of_pay,   ");
			sql.append("     bhr_pay_distr_hist.chk_nbr,   ");
			sql.append("     bhr_pay_distr_hist.void_or_iss,   ");
			sql.append("     bhr_pay_distr_hist.adj_nbr,   ");
			sql.append("     bhr_pay_distr_hist.acct_typ,   ");
			sql.append("     bhr_pay_distr_hist.extra_duty_cd,   ");
			sql.append("     bthr_extra_duty.extra_duty_descr");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
			String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
			
			params.addValue("is_emp_nbr", employeeNumber);
			params.addValue("is_pay_freq", tempFreq);
			params.addValue("is_dt_of_pay", tempDate);
			params.addValue("is_chk_nbr", checkNumber);
			params.addValue("is_void", payDate.getVoidIssue());
			params.addValue("is_adj_nbr", payDate.getAdjNumber());
	
			List <EarningsSupplemental> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new EarningsSupplementalMapper());
			
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No Employee Supplemental Earnings Info Found");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List <EarningsNonTrsTax> getEarningsNonTrsTax(String employeeNumber, PayDate payDate, String checkNumber)
	{
		try{
			StringBuilder sql = new StringBuilder();		
			sql.append("SELECT   sum(bhr_pay_distr_hist.acct_amt) as ed_amt,  ");
			sql.append("		case when trim(bhr_pay_distr_hist.extra_duty_cd) = ''");
			sql.append("               then 'ZZZ' ");
			sql.append("               else bhr_pay_distr_hist.extra_duty_cd end as ed_cd,");
			sql.append("		short_descr = ed_cd || ' - ' || isnull(bthr_extra_duty.extra_duty_descr, 'Not in file'),");
			sql.append("     bhr_pay_distr_hist.emp_nbr as bhr_pay_deduct_hist_emp_nbr,  ");
			sql.append("     bhr_pay_distr_hist.chk_nbr as bhr_pay_distr_hist_chk_nbr,");
			sql.append("     bhr_pay_distr_hist.void_or_iss as bhr_pay_distr_hist_void_or_iss,");
			sql.append("     bhr_pay_distr_hist.adj_nbr as bhr_pay_distr_hist_adj_nbr,");
			sql.append("     bhr_pay_distr_hist.dt_of_pay as bhr_pay_distr_hist_dt_of_pay ");
			sql.append("FROM {oj bhr_pay_distr_hist left outer join bthr_extra_duty ");
			sql.append("                            on bhr_pay_distr_hist.cyr_nyr_flg = bthr_extra_duty.cyr_nyr_flg and ");
			sql.append("                               bhr_pay_distr_hist.extra_duty_cd = bthr_extra_duty.extra_duty_cd} ");
			sql.append("where bhr_pay_distr_hist.emp_nbr = :is_emp_nbr and");
			sql.append("     bhr_pay_distr_hist.pay_freq = :is_pay_freq and");
			sql.append("     bhr_pay_distr_hist.dt_of_pay = :is_dt_of_pay AND " +
					   "     bhr_pay_distr_hist.chk_nbr = :is_chk_nbr AND " +
					   "     bhr_pay_distr_hist.void_or_iss = :is_void AND " +
					   "     bhr_pay_distr_hist.adj_nbr = :is_adj_nbr and ");
			sql.append("     bhr_pay_distr_hist.acct_typ in ('B', 'I') ");
			sql.append("group by bhr_pay_distr_hist.pay_freq,   ");
			sql.append("     bhr_pay_distr_hist.emp_nbr,   ");
			sql.append("     bhr_pay_distr_hist.cyr_nyr_flg,   ");
			sql.append("     bhr_pay_distr_hist.dt_of_pay,   ");
			sql.append("     bhr_pay_distr_hist.chk_nbr,   ");
			sql.append("     bhr_pay_distr_hist.void_or_iss,   ");
			sql.append("     bhr_pay_distr_hist.adj_nbr,   ");
			sql.append("     bhr_pay_distr_hist.acct_typ,   ");
			sql.append("     bhr_pay_distr_hist.extra_duty_cd,   ");
			sql.append("     bthr_extra_duty.extra_duty_descr");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
			String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
			
			params.addValue("is_emp_nbr", employeeNumber);
			params.addValue("is_pay_freq", tempFreq);
			params.addValue("is_dt_of_pay", tempDate);
			params.addValue("is_chk_nbr", checkNumber);
			params.addValue("is_void", payDate.getVoidIssue());
			params.addValue("is_adj_nbr", payDate.getAdjNumber());
	
			List <EarningsNonTrsTax> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new EarningsNonTrsTaxMapper());
			
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No Employee Non TRS Tax Earnings Info Found");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List <EarningsNonTrsNonTax> getEarningsNonTrsNonTax(String employeeNumber, PayDate payDate, String checkNumber)
	{
		try{
			StringBuilder sql = new StringBuilder();		
			sql.append("SELECT   sum(bhr_pay_distr_hist.acct_amt) as ed_amt,  ");
			sql.append("		case when trim(bhr_pay_distr_hist.extra_duty_cd) = ''");
			sql.append("               then 'ZZZ' ");
			sql.append("               else bhr_pay_distr_hist.extra_duty_cd end as ed_cd,");
			sql.append("		short_descr = ed_cd || ' - ' || isnull(bthr_extra_duty.extra_duty_descr, 'Not in file'),");
			sql.append("     bhr_pay_distr_hist.emp_nbr as bhr_pay_deduct_hist_emp_nbr,  ");
			sql.append("     bhr_pay_distr_hist.chk_nbr as bhr_pay_distr_hist_chk_nbr,");
			sql.append("     bhr_pay_distr_hist.void_or_iss as bhr_pay_distr_hist_void_or_iss,");
			sql.append("     bhr_pay_distr_hist.adj_nbr as bhr_pay_distr_hist_adj_nbr,");
			sql.append("     bhr_pay_distr_hist.dt_of_pay as bhr_pay_distr_hist_dt_of_pay ");
			sql.append("FROM {oj bhr_pay_distr_hist left outer join bthr_extra_duty ");
			sql.append("                            on bhr_pay_distr_hist.cyr_nyr_flg = bthr_extra_duty.cyr_nyr_flg and");
			sql.append("                               bhr_pay_distr_hist.extra_duty_cd = bthr_extra_duty.extra_duty_cd} ");
			sql.append("where bhr_pay_distr_hist.emp_nbr = :is_emp_nbr and");
			sql.append("     bhr_pay_distr_hist.pay_freq = :is_pay_freq and");
			sql.append("     bhr_pay_distr_hist.dt_of_pay = :is_dt_of_pay AND bhr_pay_distr_hist.chk_nbr = :is_chk_nbr AND bhr_pay_distr_hist.void_or_iss = :is_void AND bhr_pay_distr_hist.adj_nbr = :is_adj_nbr and");
			sql.append("     bhr_pay_distr_hist.acct_typ = 'T' ");
			sql.append("group by bhr_pay_distr_hist.pay_freq,   ");
			sql.append("     bhr_pay_distr_hist.emp_nbr,   ");
			sql.append("     bhr_pay_distr_hist.cyr_nyr_flg,   ");
			sql.append("     bhr_pay_distr_hist.dt_of_pay,   ");
			sql.append("     bhr_pay_distr_hist.chk_nbr,   ");
			sql.append("     bhr_pay_distr_hist.void_or_iss,   ");
			sql.append("     bhr_pay_distr_hist.adj_nbr,   ");
			sql.append("     bhr_pay_distr_hist.acct_typ,   ");
			sql.append("     bhr_pay_distr_hist.extra_duty_cd,   ");
			sql.append("     bthr_extra_duty.extra_duty_descr");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
			String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
			
			params.addValue("is_emp_nbr", employeeNumber);
			params.addValue("is_pay_freq", tempFreq);
			params.addValue("is_dt_of_pay", tempDate);
			params.addValue("is_chk_nbr", checkNumber);
			params.addValue("is_void", payDate.getVoidIssue());
			params.addValue("is_adj_nbr", payDate.getAdjNumber());
	
			List <EarningsNonTrsNonTax> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new EarningsNonTrsNonTaxMapper());
			
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No Employee Non TRS Non-Tax Earnings Info Found");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List <EarningsBank> getEarningsBank(String employeeNumber, PayDate payDate, String checkNumber)
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT bhr_bank_deposit_hist.bank_cd, bthr_bank_codes.bank_name, bhr_bank_deposit_hist.bank_acct_typ,   bthr_bank_acct_typ.bank_acct_typ_descr, isnull(bhr_bank_deposit_hist.bank_acct_nbr,'') as bank_acct_nbr, bhr_bank_deposit_hist.bank_acct_amt ");
			sql.append("FROM bhr_bank_deposit_hist ");
			sql.append("LEFT JOIN bthr_bank_codes ON bthr_bank_codes.bank_cd = bhr_bank_deposit_hist.bank_cd ");
			sql.append("LEFT JOIN bthr_bank_acct_typ ON bthr_bank_acct_typ.bank_acct_typ = bhr_bank_deposit_hist.bank_acct_typ ");
			sql.append("WHERE bhr_bank_deposit_hist.emp_nbr=:is_emp_nbr AND bhr_bank_deposit_hist.pay_freq = :is_pay_freq AND bhr_bank_deposit_hist.dt_of_pay = :is_dt_of_pay AND bhr_bank_deposit_hist.chk_nbr = :is_chk_nbr AND bhr_bank_deposit_hist.void_or_iss = :is_void AND bhr_bank_deposit_hist.adj_nbr = :is_adj_nbr");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
			String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
			
			params.addValue("is_emp_nbr", employeeNumber);
			params.addValue("is_pay_freq", tempFreq);
			params.addValue("is_dt_of_pay", tempDate);
			params.addValue("is_chk_nbr", checkNumber);
			params.addValue("is_void", payDate.getVoidIssue());
			params.addValue("is_adj_nbr", payDate.getAdjNumber());
	
			List <EarningsBank> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new EarningsBankMapper());
			
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No Employee Bank Info Found");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List <EarningsLeave> getEarningsLeave(String employeeNumber, PayDate payDate, String checkNumber)
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT bhr_emp_lv.lv_typ, bthr_lv_typ_descr.long_descr, sum(isnull(bhr_emp_lv_dock_hist.lv_used,0)) as lv_taken, isnull(bhr_emp_lv_dock_hist.void_or_iss,'') as void_or_iss, isnull(bhr_emp_lv_dock_hist.adj_nbr,0) as adj_nbr, bhr_emp_lv.lv_end_bal, bthr_lv_typ.chk_stub_pos  ");
			sql.append("FROM bhr_emp_lv ");
			sql.append("LEFT JOIN bthr_lv_typ_descr ON bthr_lv_typ_descr.lv_typ = bhr_emp_lv.lv_typ ");
			sql.append("LEFT JOIN bthr_lv_typ ON bthr_lv_typ.lv_typ = bhr_emp_lv.lv_typ AND  bthr_lv_typ.pay_freq = :is_pay_freq ");
			sql.append("LEFT JOIN bhr_emp_lv_dock_hist ON bhr_emp_lv_dock_hist.pay_freq = :is_pay_freq AND bhr_emp_lv_dock_hist.emp_nbr = :is_emp_nbr AND bhr_emp_lv_dock_hist.chk_nbr = :is_chk_nbr AND  ");
			sql.append("bhr_emp_lv_dock_hist.dt_of_pay = :is_dt_of_pay AND bhr_emp_lv_dock_hist.lv_alt_typ  = bhr_emp_lv.lv_typ and ");
			sql.append("bhr_emp_lv_dock_hist.void_or_iss = :is_void and bhr_emp_lv_dock_hist.adj_nbr = :is_adj_nbr ");
			sql.append("WHERE bhr_emp_lv.emp_nbr=:is_emp_nbr AND bhr_emp_lv.pay_freq = :is_pay_freq AND bthr_lv_typ.chk_stub_pos != '' AND bthr_lv_typ.chk_stub_pos is not null ");
			sql.append("group by bhr_emp_lv.lv_typ, bthr_lv_typ_descr.long_descr, void_or_iss, adj_nbr, bhr_emp_lv.lv_end_bal, bthr_lv_typ.chk_stub_pos ");
			sql.append("ORDER BY bthr_lv_typ.chk_stub_pos, bhr_emp_lv.lv_typ");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
			String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
			
			params.addValue("is_emp_nbr", employeeNumber);
			params.addValue("is_pay_freq", tempFreq);
			params.addValue("is_dt_of_pay", tempDate);
			params.addValue("is_chk_nbr", checkNumber);
			params.addValue("is_void", payDate.getVoidIssue());
			params.addValue("is_adj_nbr", payDate.getAdjNumber());
	
			List <EarningsLeave> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new EarningsLeaveMapper());
			
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No Employee Leave Info Found");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EarningsOvertime> getEarningsOvertime(String employeeNumber, PayDate payDate)
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT distinct bhr_suppl_xmital.job_cd as job_cd, bthr_job_cd.job_cd_descr,");
			sql.append("	         sum(bhr_suppl_xmital.suppl_amt) as tot_suppl_amt, ");
			sql.append("	         sum(bhr_suppl_xmital.ovtm_hrs_wrk) as tot_hrs_wrk, ");
			sql.append("	         case when ovtm_hrs_wrk > 0 then round(suppl_amt / ovtm_hrs_wrk,2) else 0 end as ovtm_rate ");
			sql.append("FROM bhr_suppl_xmital, bthr_job_cd ");
			sql.append("	   where bhr_suppl_xmital.emp_nbr = :is_emp_nbr and bhr_suppl_xmital.job_cd = bthr_job_cd.job_cd and bhr_suppl_xmital.gl_file_id = bthr_job_cd.gl_file_id and " );
			sql.append("	         bhr_suppl_xmital.pay_freq = :is_pay_freq and ");
			sql.append("	         bhr_suppl_xmital.dt_of_pay = :is_dt_of_pay and ");
			sql.append("	         bhr_suppl_xmital.transmittal_type = 'O' and " +
					   "		     bthr_job_cd.cyr_nyr_flg = 'C' ");
			sql.append("	group by bhr_suppl_xmital.job_Cd, ");
			sql.append("	         bhr_suppl_xmital.suppl_amt, ");
			sql.append("	 			bhr_suppl_xmital.ovtm_hrs_wrk, " );
			sql.append("bthr_job_cd.job_cd_descr");

			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String tempFreq = StringUtil.right(payDate.getDateFreq(), 1);
			String tempDate = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
			
			params.addValue("is_emp_nbr", employeeNumber);
			params.addValue("is_pay_freq", tempFreq);
			params.addValue("is_dt_of_pay", tempDate);
	
			List <EarningsOvertime> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new EarningsOvertimeMapper());
			
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No Employee Overtime Info Found");
			return null;
		}
	}
	
	private static class PayDateMapper implements RowMapper
	{
		public PayDate mapRow(ResultSet rs, int rows) throws SQLException
		{
			PayDate tempPayDate = new PayDate();
			tempPayDate.setDateFreq(StringUtil.trim(rs.getString("dt_of_pay"))+ StringUtil.trim(rs.getString("pay_freq")));
			tempPayDate.setDateFreqVoidAdjChk(StringUtil.trim(rs.getString("dt_of_pay"))+ StringUtil.trim(rs.getString("pay_freq")) + StringUtil.trim(rs.getString("void_or_iss")) + StringUtil.trim(rs.getString("adj_nbr"))+StringUtil.trim(rs.getString("chk_nbr")));
			tempPayDate.setVoidIssue(StringUtil.trim(rs.getString("void_or_iss")));
			tempPayDate.setAdjNumber(StringUtil.trim(rs.getString("adj_nbr")));
			tempPayDate.setCheckNumber(StringUtil.trim(rs.getString("chk_nbr")));
			return tempPayDate;
		}
	}

	private static class EarningsInfoMapper implements RowMapper
	{
		public EarningsInfo mapRow(ResultSet rs, int rows) throws SQLException
		{
			EarningsInfo tempEarnings = new EarningsInfo();
			
			tempEarnings.setCampusId(StringUtil.trim(rs.getString("pay_campus")));
			tempEarnings.setCampusName(StringUtil.trim(rs.getString("campus_name")));
			tempEarnings.setCheckNumber(StringUtil.trim(rs.getString("chk_nbr")));
			tempEarnings.setNumExceptions(StringUtil.trim(rs.getString("nbr_tax_exempts")));
			tempEarnings.setPeriodEndingDate(StringUtil.trim(rs.getString("dt_payper_end")));
			tempEarnings.setWithholdingStatus(StringUtil.trim(rs.getString("marital_stat_tax")));
			tempEarnings.setPeriodBeginningDate(StringUtil.trim(rs.getString("dt_payper_beg")));
			
			return tempEarnings;
		}
	}

	private static class EarningsDeductionsMapper implements RowMapper {
		public EarningsDeductions mapRow(ResultSet rs, int rows) throws SQLException {
			EarningsDeductions tempDeductions = new EarningsDeductions();

			//cs20151203 get amount from pay hist for total coded absence refund on coded abs ded page
			BigDecimal value = rs.getBigDecimal("abs_ded_coded").setScale(2, BigDecimal.ROUND_HALF_UP);

//			BigDecimal absenceRefund = NumberUtil.value(rs.getBigDecimal("abs_ded_refund")).add(NumberUtil.value(rs.getBigDecimal("abs_ded_coded")));
			//cs20151203 if total coded absence refund on coded abs ded page
			BigDecimal absenceRefund = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
			if (value.compareTo(BigDecimal.ZERO) < 0) {
				absenceRefund = rs.getBigDecimal("abs_ded_refund").subtract(rs.getBigDecimal("abs_ded_coded"));
			}
			else {
				absenceRefund = rs.getBigDecimal("abs_ded_refund");
			}
			absenceRefund = absenceRefund.setScale(2, BigDecimal.ROUND_HALF_UP);

			BigDecimal nonTrsTax = NumberUtil.value(rs.getBigDecimal("non_trs_tax_pymt_amt")).add(NumberUtil.value(rs.getBigDecimal("nontrs_suppl")));

//			BigDecimal total = NumberUtil.value(rs.getBigDecimal("gross_pay_tot")).add(NumberUtil.value(rs.getBigDecimal("abs_ded_amt"))).add(NumberUtil.value(rs.getBigDecimal("abs_ded_coded"))).add(NumberUtil.value(rs.getBigDecimal("nontrs_nonpay_bus_allow")));
			//cs20151203 if total absence deduct on coded abs ded page
			BigDecimal total = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
			if (value.compareTo(BigDecimal.ZERO) > 0) {
				total = rs.getBigDecimal("gross_pay_tot").add(rs.getBigDecimal("abs_ded_amt")).add(rs.getBigDecimal("nontrs_nonpay_bus_allow")).add(rs.getBigDecimal("abs_ded_coded"));
			}
			else {
				total = rs.getBigDecimal("gross_pay_tot").add(rs.getBigDecimal("abs_ded_amt")).add(rs.getBigDecimal("nontrs_nonpay_bus_allow"));
			}
			total = total.setScale(2, BigDecimal.ROUND_HALF_UP);

//			BigDecimal absenceDeduct = NumberUtil.value(rs.getBigDecimal("abs_ded_amt")).add(NumberUtil.value(rs.getBigDecimal("abs_ded_coded")));
			//cs20151203 if total absence deduct on coded abs ded page
			BigDecimal absenceDeduct = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
			if (value.compareTo(BigDecimal.ZERO) > 0) {
				absenceDeduct = rs.getBigDecimal("abs_ded_amt").add(rs.getBigDecimal("abs_ded_coded"));
			}
			else {
				absenceDeduct = NumberUtil.value(rs.getBigDecimal("abs_ded_amt"));
			}
			absenceDeduct = absenceDeduct.setScale(2, BigDecimal.ROUND_HALF_UP);

//			BigDecimal absenceCoded = NumberUtil.value(rs.getBigDecimal("abs_ded_coded"));
			BigDecimal totalDed = NumberUtil.value(rs.getBigDecimal("med_tax")).add(NumberUtil.value(rs.getBigDecimal("fica_tax"))).add(NumberUtil.value(rs.getBigDecimal("wh_tax"))).add(NumberUtil.value(rs.getBigDecimal("trs_salary_red"))).add(NumberUtil.value(rs.getBigDecimal("trs_ins_amt"))).add(NumberUtil.value(rs.getBigDecimal("tot_addl_ded")));

			if (absenceRefund.doubleValue() < 0) {
				absenceRefund = absenceRefund.multiply(new BigDecimal(-1));
			}

			if (absenceDeduct.doubleValue() < 0) {
				absenceDeduct = absenceDeduct.multiply(new BigDecimal(-1));
			}

//			if (absenceCoded.doubleValue() > 0) {
//				totalDed = totalDed.add(absenceCoded);
//			}
			//cs20151203
			if (absenceDeduct.doubleValue() > 0) {
				totalDed = totalDed.add(absenceDeduct);
			}

			int decimalPlaces= 2;

			tempDeductions.setStandardGross(NumberUtil.value(rs.getBigDecimal("std_gross")));
			tempDeductions.setSupplementalPay(NumberUtil.value(rs.getBigDecimal("extra_duty_gross")));
			tempDeductions.setOvertimePay(NumberUtil.value(rs.getBigDecimal("ovtm_gross")));
			tempDeductions.setAbsenceRefund(absenceRefund.setScale(decimalPlaces,BigDecimal.ROUND_UP));
			tempDeductions.setTaxedFringe(NumberUtil.value(rs.getBigDecimal("taxed_benefits")));
			tempDeductions.setEarnedIncomeCred(NumberUtil.value(rs.getBigDecimal("eic_amt")));
			tempDeductions.setNonTrsTax(nonTrsTax.setScale(decimalPlaces,BigDecimal.ROUND_UP));
			tempDeductions.setNonTrsNonTax(NumberUtil.value(rs.getBigDecimal("non_trs_nontax_pymt_amt")));
			tempDeductions.setTrsSupplemental(NumberUtil.value(rs.getBigDecimal("trs_suppl_comp")));
			tempDeductions.setTotalEarnings(total.setScale(decimalPlaces,BigDecimal.ROUND_UP));
			tempDeductions.setAbsenceDed(absenceDeduct.setScale(decimalPlaces,BigDecimal.ROUND_UP));
			tempDeductions.setWithholdingTax(NumberUtil.value(rs.getBigDecimal("wh_tax")));
			tempDeductions.setFicaTax(NumberUtil.value(rs.getBigDecimal("fica_tax")));
			tempDeductions.setTrsSalaryRed(NumberUtil.value(rs.getBigDecimal("trs_salary_red")));
			tempDeductions.setTrsInsurance(NumberUtil.value(rs.getBigDecimal("trs_ins_amt")));
			tempDeductions.setMedicareTax(NumberUtil.value(rs.getBigDecimal("med_tax")));
			tempDeductions.setTotOtherDed(NumberUtil.value(rs.getBigDecimal("tot_addl_ded")));
			tempDeductions.setTotDed(totalDed.setScale(decimalPlaces,BigDecimal.ROUND_UP));
			tempDeductions.setNetPay(NumberUtil.value(rs.getBigDecimal("net_pay")));
			tempDeductions.setNonTrsNonPayTax(NumberUtil.value(rs.getBigDecimal("nontrs_nonpay_bus_allow")));
			tempDeductions.setNonTrsNonPayNonTax(NumberUtil.value(rs.getBigDecimal("nontrs_nontax_nonpay_allow")));

			return tempDeductions;
		}
	}

	private static class EarningsOtherMapper implements RowMapper
	{
		public EarningsOther mapRow(ResultSet rs, int rows) throws SQLException
		{
			EarningsOther tempEarnings = new EarningsOther();
			
			tempEarnings.setDescription(StringUtil.trim(rs.getString("short_descr")));
			tempEarnings.setCode(StringUtil.trim(rs.getString("ded_cd")));
			tempEarnings.setAmt(NumberUtil.value(rs.getBigDecimal("ded_amt")));
			tempEarnings.setCafe_flg(StringUtil.trim(rs.getString("cafe_flg")));
			tempEarnings.setContrib(NumberUtil.value(rs.getBigDecimal("emplr_contrib")));
			tempEarnings.setDepCareMax(rs.getInt("dep_care_over_max"));
			tempEarnings.setHsaCareMax(rs.getInt("hsa_emplr_over_max"));
			return tempEarnings;
		}
	}
	
	private static class EarningsJobMapper implements RowMapper
	{
		public EarningsJob mapRow(ResultSet rs, int rows) throws SQLException
		{
			EarningsJob tempJob = new EarningsJob();
			BigDecimal totalPay;
			BigDecimal transHoursWorked = NumberUtil.value(rs.getBigDecimal("xmital_hrs_wrk"));
			BigDecimal payRate = NumberUtil.value(rs.getBigDecimal("pay_rate"));
			BigDecimal hoursWorked;
			
			if(transHoursWorked.doubleValue() > 0)
			{
				totalPay = transHoursWorked.multiply(payRate);
				hoursWorked = transHoursWorked;
			}
			else
			{
				totalPay = NumberUtil.value(rs.getBigDecimal("reg_hrs_wrk")).multiply(payRate);
				hoursWorked = NumberUtil.value(rs.getBigDecimal("reg_hrs_wrk"));
			}
			
			String payType = StringUtil.trim(rs.getString("pay_type"));
			
			if(!"3".equals(payType) && !"4".equals(payType))
			{
				totalPay = payRate;
			}
			
			tempJob.setAmt(totalPay);
			tempJob.setCode(StringUtil.trim(rs.getString("job_cd")));
			tempJob.setPayRate(NumberUtil.value(rs.getBigDecimal("pay_rate")));
			tempJob.setDescription(StringUtil.trim(rs.getString("job_cd_descr")));
			tempJob.setUnits(hoursWorked);
			
			return tempJob;
		}
	}
	
	private static class EarningsSupplementalMapper implements RowMapper
	{
		public EarningsSupplemental mapRow(ResultSet rs, int rows) throws SQLException
		{
			EarningsSupplemental tempSup = new EarningsSupplemental();
				
			tempSup.setCode(StringUtil.trim(rs.getString("ed_cd")));
			tempSup.setAmt(NumberUtil.value(rs.getBigDecimal("ed_amt")));
			tempSup.setDescription(StringUtil.trim(rs.getString("short_descr")));
			
			return tempSup;
		}
	}
	
	private static class EarningsNonTrsTaxMapper implements RowMapper
	{
		public EarningsNonTrsTax mapRow(ResultSet rs, int rows) throws SQLException
		{
			EarningsNonTrsTax tempSup = new EarningsNonTrsTax();
				
			tempSup.setCode(StringUtil.trim(rs.getString("ed_cd")));
			tempSup.setAmt(NumberUtil.value(rs.getBigDecimal("ed_amt")));
			tempSup.setDescription(StringUtil.trim(rs.getString("short_descr")));
			
			return tempSup;
		}
	}
	
	private static class EarningsNonTrsNonTaxMapper implements RowMapper
	{
		public EarningsNonTrsNonTax mapRow(ResultSet rs, int rows) throws SQLException
		{
			EarningsNonTrsNonTax tempSup = new EarningsNonTrsNonTax();
				
			tempSup.setCode(StringUtil.trim(rs.getString("ed_cd")));
			tempSup.setAmt(NumberUtil.value(rs.getBigDecimal("ed_amt")));
			tempSup.setDescription(StringUtil.trim(rs.getString("short_descr")));
			
			return tempSup;
		}
	}
	
	private static class EarningsLeaveMapper implements RowMapper
	{
		public EarningsLeave mapRow(ResultSet rs, int rows) throws SQLException
		{
			EarningsLeave tempLv = new EarningsLeave();
				
			tempLv.setCode(StringUtil.trim(rs.getString("lv_typ")));
			//tempLv.setBalance(NumberUtil.value(rs.getBigDecimal("lv_end_bal")));
			tempLv.setBalance(StringUtil.trim(rs.getString("lv_end_bal")));
			tempLv.setDescription(StringUtil.trim(rs.getString("long_descr")));
			tempLv.setUnitsPrior(StringUtil.trim(rs.getString("lv_taken")));
			tempLv.setVoidIssue(StringUtil.trim(rs.getString("void_or_iss")));
			tempLv.setAdjNumber(StringUtil.trim(rs.getString("adj_nbr")));
			
			return tempLv;
		}
	}
	
	private static class EarningsOvertimeMapper implements RowMapper
	{
		public EarningsOvertime mapRow(ResultSet rs, int rows) throws SQLException
		{
			EarningsOvertime tempOvertime = new EarningsOvertime();
			Code code = new Code();
			code.setDescription(StringUtil.trim(rs.getString("job_cd_descr")));
			code.setCode(StringUtil.trim(rs.getString("job_cd")));
			tempOvertime.setCode(code);
			tempOvertime.setOvertimeUnits(NumberUtil.value(rs.getBigDecimal("tot_hrs_wrk")));
			tempOvertime.setOvertimeRate(NumberUtil.value(rs.getBigDecimal("ovtm_rate")));
			tempOvertime.setThisPeriod(NumberUtil.value(rs.getBigDecimal("tot_suppl_amt")));
			
			return tempOvertime;
		}
	}
	
	private static class EarningsBankMapper implements RowMapper
	{
		public EarningsBank mapRow(ResultSet rs, int rows) throws SQLException
		{
			EarningsBank tempBank = new EarningsBank();
			
			String tempAcctNum = StringUtil.trim(rs.getString("bank_acct_nbr"));
			tempAcctNum = StringUtil.fill("*", tempAcctNum.length()-4) + StringUtil.right(tempAcctNum, 4);
				
			tempBank.setCode(StringUtil.trim(rs.getString("bank_cd")));
			tempBank.setName(StringUtil.trim(rs.getString("bank_name")));
			tempBank.setAcctNum(tempAcctNum);
			tempBank.setAcctType(StringUtil.trim(rs.getString("bank_acct_typ_descr")));
			tempBank.setAcctTypeCode(StringUtil.trim(rs.getString("bank_acct_typ")));
			tempBank.setAmt(NumberUtil.value(rs.getBigDecimal("bank_acct_amt")));
			
			return tempBank;
		}
	}

}
