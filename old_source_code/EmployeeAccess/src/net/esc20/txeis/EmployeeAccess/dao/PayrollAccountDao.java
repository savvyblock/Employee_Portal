package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import net.esc20.txeis.EmployeeAccess.dao.api.IPayrollAccountDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Bank;
import net.esc20.txeis.EmployeeAccess.domainobject.Money;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.util.NumberUtil;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PayrollAccountDao extends NamedParameterJdbcDaoSupport implements IPayrollAccountDao
{
	@Override
	@SuppressWarnings("unchecked")
	public List<Bank> getAccounts(String employeeNumber)
	{
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
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<Bank>()
		{
			@Override
			public Bank mapRow(ResultSet rs, int row) throws SQLException 
			{
				Bank b = new Bank();
				b.setFrequency(Frequency.getFrequency(StringUtil.trim(rs.getString("PAY_FREQ"))));
				
				Code code = new Code();
				code.setCode(StringUtil.trim(rs.getString("BANK_CD")));
				code.setDescription(StringUtil.trim(rs.getString("BANK_NAME")));
				code.setSubCode(StringUtil.trim(rs.getString("TRANSIT_ROUTE")));
				
				b.setCode(code);
				b.setAccountNumber(StringUtil.trim(rs.getString("BANK_ACCT_NBR")));
				
				Code accountType = new Code();
				accountType.setCode(StringUtil.trim(rs.getString("BANK_ACCT_TYP")));
				accountType.setDescription(StringUtil.trim(rs.getString("BANK_ACCT_TYP_DESCR")));
				
				b.setAccountType(accountType);
				b.setDepositAmount(new Money(NumberUtil.value(rs.getBigDecimal("BANK_ACCT_AMT")).doubleValue(), Currency.getInstance(Locale.US)));

				return b;
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Bank> getAccounts(String employeeNumber, Frequency frequency)
	{
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
		sql.append(" WHERE EMP_NBR = :employeeNumber AND pay_freq= :freq");
		sql.append(" AND CYR_NYR_FLG = 'C'");
		sql.append(" AND BHR_BANK_DEPOSIT.BANK_CD = BTHR_BANK_CODES.BANK_CD");
		sql.append(" AND BHR_BANK_DEPOSIT.BANK_ACCT_TYP = BTHR_BANK_ACCT_TYP.BANK_ACCT_TYP ORDER BY BTHR_BANK_CODES.BANK_NAME ASC ");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("freq", frequency.getCode());
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<Bank>()
		{
			@Override
			public Bank mapRow(ResultSet rs, int row) throws SQLException 
			{
				Bank b = new Bank();
				b.setFrequency(Frequency.getFrequency(StringUtil.trim(rs.getString("PAY_FREQ"))));
				
				Code code = new Code();
				code.setCode(StringUtil.trim(rs.getString("BANK_CD")));
				code.setDescription(StringUtil.trim(rs.getString("BANK_NAME")));
				code.setSubCode(StringUtil.trim(rs.getString("TRANSIT_ROUTE")));
				
				b.setCode(code);
				b.setAccountNumber(StringUtil.trim(rs.getString("BANK_ACCT_NBR")));
				
				Code accountType = new Code();
				accountType.setCode(StringUtil.trim(rs.getString("BANK_ACCT_TYP")));
				accountType.setDescription(StringUtil.trim(rs.getString("BANK_ACCT_TYP_DESCR")));
				
				b.setAccountType(accountType);
				b.setDepositAmount(new Money(NumberUtil.value(rs.getBigDecimal("BANK_ACCT_AMT")).doubleValue(), Currency.getInstance(Locale.US)));

				return b;
			}
		});
	}
}