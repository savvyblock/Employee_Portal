package net.esc20.txeis.EmployeeAccess.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.dao.api.ISickPayDao;
import net.esc20.txeis.EmployeeAccess.domainobject.SickPayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.util.NumberUtil;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class SickPayDao extends NamedParameterJdbcDaoSupport implements ISickPayDao
{
	@Override
	public BigDecimal getThirdPartySickPay(String employeeNumber, String year)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select(ISNULL( sum(bhr_third_party_sick_pay.sick_pay_wh_gross +   ");
		sql.append("		bhr_third_party_sick_pay.sick_pay_wh_tax +   ");
		sql.append("		bhr_third_party_sick_pay.sick_pay_fica_gross +   ");
		sql.append("		bhr_third_party_sick_pay.sick_pay_fica_tax +  ");
		sql.append("		bhr_third_party_sick_pay.sick_pay_med_gross +  ");
		sql.append("		bhr_third_party_sick_pay.sick_pay_med_tax +  ");
		sql.append("		bhr_third_party_sick_pay.sick_nontax ), 0.00))");
		sql.append("      from bhr_third_party_sick_pay ");
		sql.append("     where (bhr_third_party_sick_pay.emp_nbr = :as_emp_nbr) AND");
		//sql.append("		   (bhr_third_party_sick_pay.emp_nbr = bhr_cal_ytd.emp_nbr) AND");
		//sql.append("           (bhr_third_party_sick_pay.emp_nbr = bhr_emp_pay.emp_nbr) AND");
		sql.append("           (bhr_third_party_sick_pay.cyr_nyr_flg = :as_cyr_nyr) AND");
		sql.append("           (bhr_third_party_sick_pay.cal_yr = :as_yr OR TRIM(:as_yr) = '')");
		//sql.append("           AND ( :an_pay_nbr = 0 OR bhr_third_party_sick_pay.pay_freq IN ( :as_payfreq ) ) )");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("as_cyr_nyr","C");
		params.addValue("as_emp_nbr",employeeNumber);
		params.addValue("as_yr",year);
		
		return (BigDecimal)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, BigDecimal.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<SickPayInfo> getSickPayInfo(String employeeNumber, String year)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SICK_PAY_WH_GROSS,");
		sql.append("SICK_PAY_WH_TAX,");
		sql.append("SICK_PAY_FICA_GROSS,");
		sql.append("SICK_PAY_FICA_TAX,");
		sql.append("SICK_PAY_MED_GROSS,");
		sql.append("SICK_PAY_MED_TAX,");
		sql.append("SICK_NONTAX,");
		sql.append("PAY_FREQ");
		sql.append(" FROM BHR_THIRD_PARTY_SICK_PAY");
		sql.append(" WHERE EMP_NBR = :employeeNumber");
		sql.append(" AND CAL_YR = :year");
		sql.append(" AND CYR_NYR_FLG = 'C'");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("year",year);

		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<SickPayInfo>()
		{
			@Override
			public SickPayInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SickPayInfo i = new SickPayInfo();
				i.setFrequency(Frequency.getFrequency(StringUtil.trim(rs.getString("PAY_FREQ"))));
				i.setWithholdingGross(NumberUtil.value(rs.getBigDecimal("SICK_PAY_WH_GROSS")));
				i.setWithholdingTax(NumberUtil.value(rs.getBigDecimal("SICK_PAY_WH_TAX")));
				i.setFicaGross(NumberUtil.value(rs.getBigDecimal("SICK_PAY_FICA_GROSS")));
				i.setFicaTax(NumberUtil.value(rs.getBigDecimal("SICK_PAY_FICA_TAX")));
				i.setMedicareGross(NumberUtil.value(rs.getBigDecimal("SICK_PAY_MED_GROSS")));
				i.setMedicareTax(NumberUtil.value(rs.getBigDecimal("SICK_PAY_MED_TAX")));
				i.setNonTaxablePay(NumberUtil.value(rs.getBigDecimal("SICK_NONTAX")));
				
				return i;
			}
		});
	}
}