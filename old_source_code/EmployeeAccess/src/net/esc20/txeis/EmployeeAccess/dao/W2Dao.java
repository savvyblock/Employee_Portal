package net.esc20.txeis.EmployeeAccess.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.conversion.YesNoStringToBoolean;
import net.esc20.txeis.EmployeeAccess.dao.api.IW2Dao;
import net.esc20.txeis.EmployeeAccess.domainobject.W2Info;
import net.esc20.txeis.EmployeeAccess.util.NumberUtil;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class W2Dao extends NamedParameterJdbcDaoSupport implements IW2Dao {
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getAvailableYears(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT CAL_YR FROM BHR_W2 WHERE");
		sql.append(" EMP_NBR = :employeeNumber");
		sql.append(" ORDER BY CAL_YR DESC");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return StringUtil.trim(rs.getString("CAL_YR"));
			}
		});
	}

	@Override
	public String retrieveEA1095ElecConsent(String empNbr) {

		String elecConstW2 = "";
		String retrieveSQL = "SELECT ELEC_CONSNT_W2 FROM BHR_EMP_EMPLY A WHERE A.EMP_NBR = :empNbr";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empNbr", empNbr);

		try {
			elecConstW2 = (String) this.getNamedParameterJdbcTemplate().queryForObject(retrieveSQL, params, String.class);
		}
		catch (Exception e){
			return "";
		}
		return elecConstW2.trim();
	}

	@Override
	public String retrieveEA1095ElecConsentMsg() {

		String elecConstMsgW2 = "";
		String retrieveSQL = "SELECT A.MSG_ELEC_CONSNT_W2 FROM BHR_EAP_OPT A ";

		try {
			elecConstMsgW2 = (String) this.getNamedParameterJdbcTemplate().queryForObject(retrieveSQL, new MapSqlParameterSource(), String.class);
		}
		catch (Exception e){
			return "";
		}
		return elecConstMsgW2.trim();
	}

	@Override
	public W2Info getW2Info(String employeeNumber, String year) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CAL_YR,");
		sql.append("WH_GROSS,");
		sql.append("WH_TAX,");
		sql.append("PENSION,");
		sql.append("FICA_GROSS,");
		sql.append("FICA_TAX,");
		sql.append("MED_GROSS,");
		sql.append("MED_TAX,");
		sql.append("EIC_AMT,");
		sql.append("DEPEND_CARE,");
		sql.append("ANNUITY_DED,");
		sql.append("WITHDRAW_457,");
		sql.append("EMP_457_CONTRIB,");
		sql.append("EMPLR_CONTRIB_457,");
		sql.append("CAFE_AMT,");
		sql.append("ANNUITY_ROTH,");
		sql.append("NONTRS_NONTAX_BUS_ALLOW,");
		sql.append("NONTRS_BUS_ALLOW,");
		sql.append("EMP_BUSINESS_EXPENSE,");
		sql.append("MOVING_EXP_REIMBR,");
		sql.append("TRS_DEPOSIT,");
		sql.append("TAX_EMPLR_LIFE_GRP,");
		sql.append("HLTH_INS_DED,");
		sql.append("TAXED_BENEFITS,");
		sql.append("HSA_CONTRIB,");
		sql.append("SICK_PAY_NONTAX,");
		sql.append("HIRE_EXEMPT_WGS,");		//For calendar year >= 2010
		sql.append("EMPLR_PRVD_HLTHCARE, ");		//for calendar year >= 2012
		sql.append("ANNUITY_ROTH_457B ");		//for calendar year >= 2016
		sql.append(" FROM BHR_W2");
		sql.append(" WHERE EMP_NBR = :employeeNumber");
		sql.append(" AND CAL_YR = :year");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("year",year);

		try {
			return (W2Info) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new ParameterizedRowMapper<W2Info>() {
				@Override
				public W2Info mapRow(ResultSet rs, int rowNum) throws SQLException {
					W2Info i = new W2Info();

					i.setCalYr(rs.getString("CAL_YR"));	//ig20101209 - Added to capture what calendar year we are working with.
					i.setTaxableGrossPay(NumberUtil.value(rs.getBigDecimal("WH_GROSS")));
					i.setWithholdingTax(NumberUtil.value(rs.getBigDecimal("WH_TAX")));
					i.setPension(YesNoStringToBoolean.convertToBoolean(StringUtil.trim(rs.getString("PENSION"))));
					i.setFicaGross(NumberUtil.value(rs.getBigDecimal("FICA_GROSS")));
					i.setFicaTax(NumberUtil.value(rs.getBigDecimal("FICA_TAX")));
					i.setMedicareGross(NumberUtil.value(rs.getBigDecimal("MED_GROSS")));
					i.setMedicareTax(NumberUtil.value(rs.getBigDecimal("MED_TAX")));
					i.setEarnedIncomeCredit(NumberUtil.value(rs.getBigDecimal("EIC_AMT")));
					i.setDependentCare(NumberUtil.value(rs.getBigDecimal("DEPEND_CARE")));
					i.setAnnuityDeduction(NumberUtil.value(rs.getBigDecimal("ANNUITY_DED")));
					i.setAnnuity457Withdraw(NumberUtil.value(rs.getBigDecimal("WITHDRAW_457")));
					i.setAnnuity457Employee(NumberUtil.value(rs.getBigDecimal("EMP_457_CONTRIB")));
					i.setAnnuity457Employer(NumberUtil.value(rs.getBigDecimal("EMPLR_CONTRIB_457")));
					i.setCafeteria125(NumberUtil.value(rs.getBigDecimal("CAFE_AMT")));
					i.setRoth403BAfterTax(NumberUtil.value(rs.getBigDecimal("ANNUITY_ROTH")));
					i.setNonTrsBusinessExpense(NumberUtil.value(rs.getBigDecimal("NONTRS_NONTAX_BUS_ALLOW")));
					i.setTaxableAllowance(NumberUtil.value(rs.getBigDecimal("NONTRS_BUS_ALLOW")));
					i.setEmployeeBusinessExpense(NumberUtil.value(rs.getBigDecimal("EMP_BUSINESS_EXPENSE")));
					i.setMovingExpenseReimbursement(NumberUtil.value(rs.getBigDecimal("MOVING_EXP_REIMBR")));
					i.setSalaryReduction(NumberUtil.value(rs.getBigDecimal("TRS_DEPOSIT")));
					i.setTaxedLifeContribution(NumberUtil.value(rs.getBigDecimal("TAX_EMPLR_LIFE_GRP")));
					i.setHealthInsuranceDeduction(NumberUtil.value(rs.getBigDecimal("HLTH_INS_DED")));
					i.setTaxableBenefits(NumberUtil.value(rs.getBigDecimal("TAXED_BENEFITS")));
					i.setHealthSavingsAccount(NumberUtil.value(rs.getBigDecimal("HSA_CONTRIB")));
					i.setNonTaxSickPay(NumberUtil.value(rs.getBigDecimal("SICK_PAY_NONTAX")));

					//For calendar year >= 2010
					if (i.getCalYr() != null && !i.getCalYr().trim().equals("") && Integer.valueOf(i.getCalYr()) >= 2010) {
						i.setHireExemptWgs(NumberUtil.value(rs.getBigDecimal("HIRE_EXEMPT_WGS")));
					}
					else {
						i.setHireExemptWgs(BigDecimal.valueOf(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
					}

					//For calendar year >= 2012
					if (i.getCalYr() != null && !i.getCalYr().trim().equals("") && Integer.valueOf(i.getCalYr()) >= 2012) {
						i.setEmplrPrvdHlthcare(NumberUtil.value(rs.getBigDecimal("EMPLR_PRVD_HLTHCARE")));
					}
					else {
						i.setEmplrPrvdHlthcare(BigDecimal.valueOf(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					
					//For calendar year >= 2016
					if (i.getCalYr() != null && !i.getCalYr().trim().equals("") && Integer.valueOf(i.getCalYr()) >= 2016) {
						i.setAnnuityRoth457b(NumberUtil.value(rs.getBigDecimal("ANNUITY_ROTH_457B")));
					}
					else {
						i.setAnnuityRoth457b(BigDecimal.valueOf(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
					}

					return i;
				}
			});
		}
		catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public boolean updateW2ElecConsent(String employeeNumber, String elecConsntW2) {
		int rows = 0;
		String updateW2ElecConsntSql = "UPDATE BHR_EMP_EMPLY SET ELEC_CONSNT_W2 =:elecConsntW2, MODULE = 'Employee Access' WHERE EMP_NBR =:employeeNumber";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("elecConsntW2",elecConsntW2);

		try {
			rows = this.getNamedParameterJdbcTemplate().update(updateW2ElecConsntSql, params);
			return rows > 0 ;
		}
		catch(EmptyResultDataAccessException ex) {
			return false;
		}
	}
}