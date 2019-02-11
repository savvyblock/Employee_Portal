package net.esc20.txeis.EmployeeAccess.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.conversion.RscccDate;
import net.esc20.txeis.EmployeeAccess.dao.api.ICalendarDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Calendar;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.util.NumberUtil;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class CalendarDao extends NamedParameterJdbcDaoSupport implements ICalendarDao
{
	private static Log log = LogFactory.getLog(CalendarDao.class);
	
	@Override
	public Date getLastPostedPayDate(String employeeNumber, Frequency frequency)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct dt_of_pay from bhr_pay_hist where dt_of_pay in (select max(dt_of_pay) from bhr_pay_hist ");
		sql.append("where emp_nbr = :employeeNumber and pay_freq = :frequency)");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("frequency", frequency.getCode());
		String payDate = (String)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);
		return RscccDate.convertToDate(payDate);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getAvailableYears(String employeeNumber)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT CAL_YR FROM BHR_CAL_YTD WHERE");
		sql.append(" EMP_NBR = :employeeNumber");
		sql.append(" AND CYR_NYR_FLG = 'C'");
		sql.append(" ORDER BY CAL_YR DESC");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<String>()
		{
			@Override
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return StringUtil.trim(rs.getString("CAL_YR"));
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Calendar> getCalendars(String employeeNumber, String year) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM BHR_CAL_YTD WHERE");
		sql.append(" EMP_NBR = :employeeNumber");
		sql.append(" AND CYR_NYR_FLG = 'C'");
		sql.append(" AND CAL_YR = :year");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber",employeeNumber);
		params.addValue("year",year);

		List<Calendar> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<Calendar>() {
			@Override
			public Calendar mapRow(ResultSet rs, int row) throws SQLException {
				Calendar c = new Calendar();

				c.setCalYr(rs.getString("CAL_YR"));	//ig20100930 - Added.
				c.setFrequency(Frequency.getFrequency(StringUtil.trim(rs.getString("PAY_FREQ"))));
				c.setContractPay(NumberUtil.value(rs.getBigDecimal("CONTR_AMT")));
				c.setNonContractPay(NumberUtil.value(rs.getBigDecimal("NONCONTR_AMT")));
				c.setSupplementalPay(NumberUtil.value(rs.getBigDecimal("SUPPL_PAY_AMT")));
				c.setWithholdingGross(NumberUtil.value(rs.getBigDecimal("WH_GROSS")));
				c.setWithholdingTax(NumberUtil.value(rs.getBigDecimal("WH_TAX")));
				c.setEarnedIncomeCredit(NumberUtil.value(rs.getBigDecimal("EIC_AMT")));
				c.setFicaGross(NumberUtil.value(rs.getBigDecimal("FICA_GROSS")));
				c.setFicaTax(NumberUtil.value(rs.getBigDecimal("FICA_TAX")));
				c.setDependentCare(NumberUtil.value(rs.getBigDecimal("DEPEND_CARE")));
				c.setDependentCareEmployer(NumberUtil.value(rs.getBigDecimal("EMPLR_DEPEND_CARE")));
				c.setDependentCareExceeds(NumberUtil.value(rs.getBigDecimal("EMPLR_DEPEND_CARE_TAX")));
				c.setMedicareGross(NumberUtil.value(rs.getBigDecimal("MED_GROSS")));
				c.setMedicareTax(NumberUtil.value(rs.getBigDecimal("MED_TAX")));

				c.setAnnuityDeduction(NumberUtil.value(rs.getBigDecimal("ANNUITY_DED")));
				c.setAnnuity457Employee(NumberUtil.value(rs.getBigDecimal("EMP_457_CONTRIB")));
				c.setAnnuity457Employer(NumberUtil.value(rs.getBigDecimal("EMPLR_457_CONTRIB")));
				c.setAnnuity457Withdraw(NumberUtil.value(rs.getBigDecimal("WITHDRAW_457")));
				c.setRoth403BAfterTax(NumberUtil.value(rs.getBigDecimal("ANNUITY_ROTH")));
				c.setTaxableBenefits(NumberUtil.value(rs.getBigDecimal("TAXED_BENEFITS")));

				c.setNonTrsBusinessExpense(NumberUtil.value(rs.getBigDecimal("NONTRS_BUS_ALLOW")));
				c.setNonTrsReimbursementBase(NumberUtil.value(rs.getBigDecimal("NONTRS_REIMBR_BASE")));
				c.setNonTrsReimbursementExcess(NumberUtil.value(rs.getBigDecimal("NONTRS_REIMBR_EXCESS")));
				c.setMovingExpenseReimbursement(NumberUtil.value(rs.getBigDecimal("MOVING_EXP_REIMBR")));
				c.setNonTrsNonTaxBusinessAllow(NumberUtil.value(rs.getBigDecimal("NONTRS_NONTAX_BUS_ALLOW")));
				c.setNonTrsNonTaxNonPayAllow(NumberUtil.value(rs.getBigDecimal("NONTRS_NONTAX_NONPAY_ALLOW")));

				c.setTrsDeposit(NumberUtil.value(rs.getBigDecimal("TRS_DEPOSIT")));
				c.setSalaryReduction(NumberUtil.value(rs.getBigDecimal("TRS_SALARY_RED")));
				c.setHsaEmployerContribution(NumberUtil.value(rs.getBigDecimal("HSA_EMPLR_CONTRIB")));
				c.setHsaEmployeeSalaryReductionContribution(NumberUtil.value(NumberUtil.value(rs.getBigDecimal("HSA_EMP_SAL_REDCTN_CONTRIB"))));

				c.setTaxedLifeContribution(NumberUtil.value(rs.getBigDecimal("TAX_EMPLR_LIFE")));
				c.setTaxedGroupContribution(NumberUtil.value(rs.getBigDecimal("TAX_EMPLR_LIFE_GRP")));
				c.setHealthInsuranceDeduction(NumberUtil.value(rs.getBigDecimal("HLTH_INS_DED")));

				//For calendar year >= 2011
				c.setEmplrPrvdHlthcare(NumberUtil.value(rs.getBigDecimal("EMPLR_PRVD_HLTHCARE")));

				//For calendar year >= 2010
				if (c.getCalYr() != null && !c.getCalYr().trim().equals("") && Integer.valueOf(c.getCalYr()) >= 2010) {
					c.setHireExemptWgs(NumberUtil.value(rs.getBigDecimal("HIRE_EXEMPT_WGS")));
				}
				else {
					c.setHireExemptWgs(BigDecimal.valueOf(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				
				//For calendar year >= 2016
				c.setAnnuityRoth457b((NumberUtil.value(rs.getBigDecimal("ANNUITY_ROTH_457B"))));
				return c;
			}
		});

		for(Calendar c : result) {
			try {
				Date d = getLastPostedPayDate(employeeNumber,c.getFrequency());

				if(year != null && year.equals(new SimpleDateFormat("yyyy").format(d))) {
					c.setLastPostedPayDate(d);
				}
			}
			catch(Exception ex) {
				// no last posted pay date
				log.info("No last posted pay date.");
			}
		}

		return result;
	}
}