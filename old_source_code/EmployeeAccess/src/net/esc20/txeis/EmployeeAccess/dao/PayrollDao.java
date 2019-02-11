package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.esc20.txeis.EmployeeAccess.conversion.TimestampConverter;
import net.esc20.txeis.EmployeeAccess.dao.api.IPayrollDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Bank;
import net.esc20.txeis.EmployeeAccess.domainobject.Money;
import net.esc20.txeis.EmployeeAccess.domainobject.PayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.service.ReferenceDataService;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public class PayrollDao extends NamedParameterJdbcDaoSupport implements IPayrollDao
{
	private Connection connection;
	private static Log log = LogFactory.getLog(PayrollDao.class);
	
	@Override
	public PayInfo getPendingPayInfo(String employeeNumber, Frequency frequency)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT marital_stat_tax_new, nbr_tax_exempts_new ");
		sql.append("FROM BEA_W4 ");
		sql.append("WHERE emp_nbr = :employeeNumber AND pay_freq =:frequency AND STAT_CD = 'P'");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("frequency", frequency.getCode());
		
		PayInfo result;
		
		try
		{
			result = (PayInfo) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new PayInfoPendingMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			result = null;
			log.info("No pending pay information found.");
		}
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<List <Bank>> getAccounts(String employeeNumber, Frequency frequency)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT BEA_DRCT_DPST_BNK_ACCT.bnk_cd, BTHR_BANK_CODES.bank_name, BTHR_BANK_CODES.transit_route, BEA_DRCT_DPST_BNK_ACCT.bnk_acct_nbr, ");
		sql.append("BEA_DRCT_DPST_BNK_ACCT.bnk_acct_typ, BTHR_BANK_ACCT_TYP.bank_acct_typ_descr, BEA_DRCT_DPST_BNK_ACCT.bnk_acct_amt, ");
		sql.append(" BEA_DRCT_DPST_BNK_ACCT.bnk_cd_new, BEA_DRCT_DPST_BNK_ACCT.bnk_acct_nbr_new, ");
		sql.append("BEA_DRCT_DPST_BNK_ACCT.bnk_acct_typ_new, BTHR_BANK_ACCT_TYP.bank_acct_typ_descr, BEA_DRCT_DPST_BNK_ACCT.bnk_acct_amt_new ");
		sql.append("FROM BEA_DRCT_DPST_BNK_ACCT ");
		sql.append("LEFT JOIN BTHR_BANK_CODES ON  BTHR_BANK_CODES.bank_cd = BEA_DRCT_DPST_BNK_ACCT.bnk_cd ");
		sql.append("LEFT JOIN BTHR_BANK_ACCT_TYP ON  BTHR_BANK_ACCT_TYP.bank_acct_typ = BEA_DRCT_DPST_BNK_ACCT.bnk_acct_typ ");
		
		sql.append("WHERE emp_nbr = :employeeNumber AND pay_freq =:frequency AND STAT_CD = 'P'");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("frequency", frequency.getCode());
		
		List <List<Bank>> result;
		List <List<Bank>> resultReturn =  new ArrayList <List <Bank>>();
		resultReturn.add(new ArrayList <Bank>());
		resultReturn.add(new ArrayList <Bank>());
		
		try
		{
			result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new BankAccountMapper());
			
			for(List<Bank> list : result)
			{
				resultReturn.get(0).add(list.get(0));
				resultReturn.get(1).add(list.get(1));
			}
		}
		catch(EmptyResultDataAccessException e)
		{
			resultReturn = new ArrayList <List <Bank>>();
			
			List <Bank> tempList= new ArrayList <Bank>();
			List <Bank> tempList2= new ArrayList <Bank>();
			
			resultReturn.add(tempList);
			resultReturn.add(tempList2);
			log.info("No direct deposit account change requests found.", e);
			return resultReturn;
		}
		
		if(result.size() < 1)
		{
			resultReturn = new ArrayList <List <Bank>>();
			
			List <Bank> tempList= new ArrayList <Bank>();
			List <Bank> tempList2= new ArrayList <Bank>();
			
			resultReturn.add(tempList);
			resultReturn.add(tempList2);
			return resultReturn;
		}
		
		return resultReturn;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Bank> getCurrentAccounts(String employeeNumber, Frequency frequency)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT BEA_DRCT_DPST_BNK_ACCT.bnk_cd, BTHR_BANK_CODES.bank_name, BTHR_BANK_CODES.transit_route, BEA_DRCT_DPST_BNK_ACCT.bnk_acct_nbr, ");
		sql.append("BEA_DRCT_DPST_BNK_ACCT.bnk_acct_typ, BTHR_BANK_ACCT_TYP.bank_acct_typ_descr, BEA_DRCT_DPST_BNK_ACCT.bnk_acct_amt ");
		sql.append("FROM BEA_DRCT_DPST_BNK_ACCT ");
		sql.append("LEFT JOIN BTHR_BANK_CODES ON  BTHR_BANK_CODES.bank_cd = BEA_DRCT_DPST_BNK_ACCT.bnk_cd ");
		sql.append("LEFT JOIN BTHR_BANK_ACCT_TYP ON  BTHR_BANK_ACCT_TYP.bank_acct_typ = BEA_DRCT_DPST_BNK_ACCT.bnk_acct_typ ");
		
		sql.append("WHERE emp_nbr = :employeeNumber AND pay_freq =:frequency AND STAT_CD = 'P'");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("frequency", frequency.getCode());
		
		List <Bank> result;
		
		try
		{
			result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new BankCurrentMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			result = new ArrayList <Bank>();
			log.info("No direct deposit account change requests found.", e);
		}
		
		return result;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Bank> getPendingAccounts(String employeeNumber, Frequency frequency)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT BEA_DRCT_DPST_BNK_ACCT.bnk_cd_new, BTHR_BANK_CODES.bank_name, BTHR_BANK_CODES.transit_route, BEA_DRCT_DPST_BNK_ACCT.bnk_acct_nbr_new, ");
		sql.append("BEA_DRCT_DPST_BNK_ACCT.bnk_acct_typ_new, BTHR_BANK_ACCT_TYP.bank_acct_typ_descr, BEA_DRCT_DPST_BNK_ACCT.bnk_acct_amt_new ");
		sql.append("FROM BEA_DRCT_DPST_BNK_ACCT ");
		sql.append("LEFT JOIN BTHR_BANK_CODES ON  BTHR_BANK_CODES.bank_cd = BEA_DRCT_DPST_BNK_ACCT.bnk_cd_new ");
		sql.append("LEFT JOIN BTHR_BANK_ACCT_TYP ON  BTHR_BANK_ACCT_TYP.bank_acct_typ = BEA_DRCT_DPST_BNK_ACCT.bnk_acct_typ_new ");
		
		sql.append("WHERE emp_nbr = :employeeNumber AND pay_freq =:frequency AND STAT_CD = 'P'");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("frequency", frequency.getCode());
		
		List <Bank> result;
		
		try
		{
			result= getNamedParameterJdbcTemplate().query(sql.toString(), params, new BankPendingMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			result = new ArrayList <Bank>();
			log.info("No direct deposit account change requests found.", e);
		}
		return result;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Frequency> getPayrollFrequencies(String employeeNumber)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT pay_freq FROM BHR_EMP_PAY WHERE emp_nbr = :employeeNumber AND CYR_NYR_FLG ='C' ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		
		List <Frequency> result;
		
		try
		{
			result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new FrequencyMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			result = new ArrayList <Frequency> ();
			log.error("No payroll frequencies found.", e);
		}
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List <Code> getAvailableBanks()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT transit_route, bank_name, bank_cd  FROM BTHR_BANK_CODES ORDER BY bank_name ASC");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		List <Code> result;
		
		try
		{
			result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new BankNameMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			result = new ArrayList <Code> ();
			log.error("No bank account codes found.", e);
		}
		return result;
	}
	
	@Override
	public Integer getDirectDepositLimit()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DD_ACCT FROM BHR_EAP_OPT ");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		Integer result;
		
		try
		{
			result = getNamedParameterJdbcTemplate().queryForInt(sql.toString(), params);
		}
		catch(EmptyResultDataAccessException e)
		{
			result = 0;
			log.error("No employee access options found.", e);
		}
		return result;
	}
	
	@Override
	public String getPayInfoFieldDisplay(String frequency)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT isnull(opt_cd, 'N') FROM BHR_EAP_PAY_ASSGN_GRP WHERE grp_name = 'BEA_W4' AND pay_freq =:frequency ");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("frequency", frequency);
		
		String result; 
		
		try
		{
			result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);
		}
		catch(EmptyResultDataAccessException e)
		{
			result = "N";
			log.error("No BEA_W4 pay assign group found.", e);
		}
		
		return result;
	}
	
	@Override
	public String getAccountInfoFieldDisplay(String frequency)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT isnull(opt_cd,'N') FROM BHR_EAP_PAY_ASSGN_GRP WHERE grp_name = 'BEA_DRCT_DPST_BNK_ACCT' AND pay_freq =:frequency");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("frequency", frequency);
		
		String result;
	
		try
		{
			result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);
		}
		catch(EmptyResultDataAccessException e)
		{
			result = "N";
			log.error("No BEA_DRCT_DPST_BNK_ACCT pay assign group found.", e);
		}	
		return result;
	}
	
	@Override
	public Boolean getAutoApprovePayInfo(String frequency)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT auto_apprv FROM BHR_EAP_PAY_ASSGN_GRP WHERE grp_name = 'BEA_W4' AND pay_freq =:frequency ");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("frequency", frequency);
		String result;
		Boolean resultBoolean;
		
		try
		{
			result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);
			if(result.equals("Y"))
				resultBoolean = true;
			else
				resultBoolean = false;
		}
		catch(EmptyResultDataAccessException e)
		{
			resultBoolean = false;
			log.error("No BEA_W4 pay assign group found.", e);
		}	
		
		return resultBoolean;
	}
	
	@Override
	public Boolean getAutoApproveAccountInfo(String frequency)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT auto_apprv FROM BHR_EAP_PAY_ASSGN_GRP WHERE grp_name = 'BEA_DRCT_DPST_BNK_ACCT' AND pay_freq =:frequency");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("frequency", frequency);
		String result;
		Boolean resultBoolean;
		
		try
		{
			result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);
			if(result.equals("Y"))
				resultBoolean = true;
			else
				resultBoolean = false;
		}
		catch(EmptyResultDataAccessException e)
		{
			resultBoolean = false;
			log.error("No BEA_DRCT_DPST_BNK_ACCT pay assign group found.", e);
		}	
		
		return resultBoolean;
	}
	
	@Override
	public boolean insertPayInfoRequest(Boolean autoApprove, String employeeNumber, String frequency, PayInfo payrollPayInfo, PayInfo payInfo)
	{	
		java.sql.Timestamp requestTime = new java.sql.Timestamp(new Date().getTime());
		String statusCode = "P";
		String timestamp ="";
		Integer approverId = -1;
		
		if(autoApprove) 
		{
			statusCode = "A";
			approverId = 0;
			timestamp = TimestampConverter.convertTimestamp(requestTime);
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO BEA_W4 (emp_nbr, req_dts, pay_freq, marital_stat_tax, marital_stat_tax_new, "); 
		sql.append("nbr_tax_exempts, nbr_tax_exempts_new, apprvd_dts, apprvr_usr_id, stat_cd) ");
		sql.append("VALUES ('");
		sql.append(employeeNumber);
		sql.append("', '");
		sql.append(TimestampConverter.convertTimestamp(requestTime));
		sql.append("', '");
		sql.append(frequency);
		sql.append("', '");
		sql.append(payInfo.getMaritalStatus().getCode());
		sql.append("', '");
		sql.append(payrollPayInfo.getMaritalStatus().getCode());
		sql.append("', '");
		sql.append(payInfo.getNumberOfExemptions());
		sql.append("', '");
		sql.append(payrollPayInfo.getNumberOfExemptions());
		sql.append("', '");
		sql.append(timestamp);
		sql.append("', '");
		sql.append(approverId);
		sql.append("', '");
		sql.append(statusCode);
		sql.append("' ) ");
		
		try 
		{
			int li_rc = connection.createStatement().executeUpdate(sql.toString());
			if (li_rc == 0) {
				connection.rollback();
				return false;
			}
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			try {
				connection.rollback();
				return false;
			} 
			catch (CannotGetJdbcConnectionException e) {
				log.error("Insert pay info request failed.", e);
				return false;
			} 
			catch (SQLException e) {
				log.error("Insert pay info request failed.", e);
				return false;
			}
		}
		
		return true;
		
	}
	
	@Override
	public boolean updatePayInfoRequest(Boolean autoApprove, String employeeNumber, String frequency, PayInfo payrollPayInfo, PayInfo payInfo)
	{

		java.sql.Timestamp requestTime = new java.sql.Timestamp(new Date().getTime());
		String statusCode = "P";
		String timestamp ="";
		Integer approverId = -1;
		
		if(autoApprove)
		{
			statusCode = "A";
			approverId = 0;
			timestamp = TimestampConverter.convertTimestamp(requestTime);
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BEA_W4 SET req_dts = '");
		sql.append(TimestampConverter.convertTimestamp(requestTime));
		sql.append("', marital_stat_tax_new = '"); 
		sql.append(payrollPayInfo.getMaritalStatus().getCode());
		sql.append("', nbr_tax_exempts_new = '");
		sql.append( payrollPayInfo.getNumberOfExemptions());
		sql.append("', apprvd_dts = '");
		sql.append(timestamp);
		sql.append("', apprvr_usr_id = '");
		sql.append(approverId);
		sql.append("', stat_cd = '");
		sql.append(statusCode);
		sql.append("' WHERE emp_nbr = '");
		sql.append(employeeNumber);
		sql.append("' AND pay_freq =  '");
		sql.append(frequency);
		sql.append("' AND marital_stat_tax = '");
		sql.append(payInfo.getMaritalStatus().getCode());
		sql.append("' AND nbr_tax_exempts = '");
		sql.append(payInfo.getNumberOfExemptions());
		sql.append("' AND stat_cd ='P'");
		
		try 
		{
			int li_rc = connection.createStatement().executeUpdate(sql.toString());
			if (li_rc == 0) {
				connection.rollback();
				return false;
			}
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			try {
				connection.rollback();
				return false;
			} 
			catch (CannotGetJdbcConnectionException e) {
				log.error("Update pay info request failed.", e);
				return false;
			} 
			catch (SQLException e) {
				log.error("Update pay info request failed.", e);
				return false;
			}
		}
		
		return true;
	}
	@Override
	public boolean deletePayInfoRequest(String employeeNumber, String frequency, PayInfo payrollPayInfo, PayInfo payInfo)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BEA_W4 WHERE emp_nbr = '");
		sql.append(employeeNumber);
		sql.append("' AND pay_freq =  '");
		sql.append(frequency);
		sql.append("' AND marital_stat_tax = '");
		sql.append(payInfo.getMaritalStatus().getCode());
		sql.append("' AND nbr_tax_exempts = '");
		sql.append(payInfo.getNumberOfExemptions());
		sql.append("' AND stat_cd = 'P'");
		
		try 
		{
			int li_rc = connection.createStatement().executeUpdate(sql.toString());
			if (li_rc == 0) {
				connection.rollback();
				return false;
			}
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			try {
				connection.rollback();
				return false;
			} 
			catch (CannotGetJdbcConnectionException e) {
				log.error("Delete pay info request failed.", e);
				return false;
			} 
			catch (SQLException e) {
				log.error("Delete pay info request failed.", e);
				return false;
			}
		}
		
		return true;
	}
	@Override
	public boolean insertAccountRequest(Boolean autoApprove, String employeeNumber, String frequency, Bank payrollAccountInfo, Bank accountInfo)
	{
		java.sql.Timestamp requestTime = new java.sql.Timestamp(new Date().getTime());
		String statusCode = "P";
		String timestamp ="";
		Integer approverId = -1;
		
		if(autoApprove)
		{
			statusCode = "A";
			approverId = 0;
			timestamp = TimestampConverter.convertTimestamp(requestTime);
		}	
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO BEA_DRCT_DPST_BNK_ACCT (bnk_cd, emp_nbr, bnk_acct_nbr, req_dts, pay_freq, bnk_acct_typ, bnk_acct_amt, "); 
		sql.append("bnk_cd_new, bnk_acct_nbr_new, apprvd_dts, apprvr_usr_id, bnk_acct_typ_new, bnk_acct_amt_new, stat_cd) ");
		sql.append("VALUES ('");
		sql.append(accountInfo.getCode().getCode());
		sql.append("', '");
		sql.append(employeeNumber);
		sql.append("', '");
		sql.append(accountInfo.getAccountNumber());
		sql.append("', '");
		sql.append(TimestampConverter.convertTimestamp(requestTime));
		sql.append("', '");
		sql.append(frequency);
		sql.append("', '");
		sql.append(accountInfo.getAccountType().getCode());
		sql.append("', '");
		sql.append(accountInfo.getDepositAmount().getAmount());
		sql.append("', '");
		sql.append(payrollAccountInfo.getCode().getCode());
		sql.append("', '");
		sql.append(payrollAccountInfo.getAccountNumber());
		sql.append("', '");
		sql.append(timestamp);
		sql.append("', '");
		sql.append(approverId);
		sql.append("', '");
		sql.append(payrollAccountInfo.getAccountType().getCode());
		sql.append("', '");
		sql.append(payrollAccountInfo.getDepositAmount().getAmount());
		sql.append("', '");
		sql.append(statusCode);
		sql.append("' ) ");
		
		try 
		{
			int li_rc = connection.createStatement().executeUpdate(sql.toString());
			if (li_rc == 0) {
				connection.rollback();
				return false;
			}
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			try {
				connection.rollback();
				return false;
			} 
			catch (CannotGetJdbcConnectionException e) {
				log.error("Insert account info request failed.", e);
				return false;
			} 
			catch (SQLException e) {
				log.error("Insert account info request failed.", e);
				return false;
			}
		}
		
		return true;
	}
	@Override
	public boolean updateAccountRequest(Boolean autoApprove, String employeeNumber, String frequency, Bank payrollAccountInfo, Bank accountInfo,  Bank accountInfoPending)
	{
		java.sql.Timestamp requestTime = new java.sql.Timestamp(new Date().getTime());
		String statusCode = "P";
		String timestamp ="";
		Integer approverId = -1;
		
		if(autoApprove)
		{
			statusCode = "A";
			approverId = 0;
			timestamp = TimestampConverter.convertTimestamp(requestTime);
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BEA_DRCT_DPST_BNK_ACCT SET req_dts = '");
		sql.append( TimestampConverter.convertTimestamp(requestTime));
		sql.append("', bnk_cd_new = '");
		sql.append(payrollAccountInfo.getCode().getCode());
		sql.append("', bnk_acct_nbr_new = '");
		sql.append(payrollAccountInfo.getAccountNumber());
		sql.append("', apprvd_dts = '");
		sql.append(timestamp);
		sql.append("', apprvr_usr_id = '");
		sql.append(approverId);
		sql.append("', bnk_acct_typ_new = '");
		sql.append(payrollAccountInfo.getAccountType().getCode());
		sql.append("', bnk_acct_amt_new = '");
		sql.append(payrollAccountInfo.getDepositAmount().getAmount());
		sql.append("', stat_cd = '");
		sql.append(statusCode);
		sql.append("' WHERE pay_freq = '");
		sql.append(frequency);
		sql.append("' AND bnk_cd = '");
		sql.append(accountInfo.getCode().getCode());
		sql.append("' AND emp_nbr = '");
		sql.append(employeeNumber);
		sql.append("' AND bnk_acct_nbr = '");
		sql.append(accountInfo.getAccountNumber());
		sql.append("' AND bnk_cd_new = '");
		sql.append(accountInfoPending.getCode().getCode());
		sql.append("' AND bnk_acct_nbr_new = '");
		sql.append(accountInfoPending.getAccountNumber());
		sql.append("' AND stat_cd ='P'");
		
		
		try 
		{
			int li_rc = connection.createStatement().executeUpdate(sql.toString());
			if (li_rc == 0) {
				connection.rollback();
				return false;
			}
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			try {
				connection.rollback();
				return false;
			} 
			catch (CannotGetJdbcConnectionException e) {
				log.error("Update account info request failed.", e);
				return false;
			} 
			catch (SQLException e) {
				log.error("Update account info request failed.", e);
				return false;
			}
		}
		
		return true;
	}
	@Override
	public boolean deleteAccountRequest(String employeeNumber, String frequency, Bank payrollAccountInfo, Bank accountInfo, Bank accountInfoPending)
	{	
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BEA_DRCT_DPST_BNK_ACCT WHERE pay_freq = '");
		sql.append(frequency); 
		sql.append("' AND bnk_cd = '");
		sql.append(accountInfo.getCode().getCode());
		sql.append("' AND emp_nbr = '");
		sql.append(employeeNumber);
		sql.append("' AND bnk_acct_nbr = '");
		sql.append(accountInfo.getAccountNumber());
		sql.append("' AND bnk_cd_new = '");
		sql.append(accountInfoPending.getCode().getCode());
		sql.append("' AND bnk_acct_nbr_new = '");
		sql.append(accountInfoPending.getAccountNumber());
		sql.append("' AND stat_cd = 'P' ");
		
		try 
		{
			int li_rc = connection.createStatement().executeUpdate(sql.toString());
			if (li_rc == 0) {
				connection.rollback();
				return false;
			}
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			try {
				connection.rollback();
				return false;
			} 
			catch (CannotGetJdbcConnectionException e) {
				log.error("Delete account info request failed.", e);
				return false;
			} 
			catch (SQLException e) {
				log.error("Delete account info request failed.", e);
				return false;
			}
		}
		
		return true;
	}
	@Override
	public boolean updatePayInfoApprove(String employeeNumber, String frequency, PayInfo payrollPayInfo, PayInfo payInfo)
	{	
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_EMP_PAY SET MARITAL_STAT_TAX = '");
		sql.append(payrollPayInfo.getMaritalStatus().getCode());
		sql.append("', NBR_TAX_EXEMPTS = '");
		sql.append(payrollPayInfo.getNumberOfExemptions());
		sql.append("' WHERE CYR_NYR_FLG = 'C' AND PAY_FREQ = '");
		sql.append(frequency);
		sql.append("' AND EMP_NBR = '");
		sql.append(employeeNumber);
		sql.append("' AND MARITAL_STAT_TAX = '");
		sql.append(payInfo.getMaritalStatus().getCode());
		sql.append("' AND NBR_TAX_EXEMPTS= '");
		sql.append(payInfo.getNumberOfExemptions());
		sql.append("'");

		try 
		{
			int li_rc = connection.createStatement().executeUpdate(sql.toString());
			if (li_rc == 0) {
				connection.rollback();
				return false;
			}
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			try {
				connection.rollback();
				return false;
			} 
			catch (CannotGetJdbcConnectionException e) {
				log.error("Update pay info failed.", e);
				return false;
			} 
			catch (SQLException e) {
				log.error("Update pay info failed.", e);
				return false;
			}
		}
		
		return true;
	}
	@Override
	public boolean insertAccountApprove(String employeeNumber, String frequency, String prenote, Bank payrollAccountInfo)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO BHR_BANK_DEPOSIT (EMP_NBR, BANK_CD, CYR_NYR_FLG, ");
		sql.append("PAY_FREQ, BANK_ACCT_NBR, BANK_ACCT_TYP, BANK_ACCT_AMT, BANK_STAT ) ");
		sql.append("Values ( '");
		sql.append(employeeNumber);
		sql.append("', '");
		sql.append(payrollAccountInfo.getCode().getCode());
		sql.append("', 'C' , '");
		sql.append(frequency);
		sql.append("', '");
		sql.append(payrollAccountInfo.getAccountNumber());
		sql.append("', '");
		sql.append(payrollAccountInfo.getAccountType().getCode());
		sql.append("', '");
		sql.append(payrollAccountInfo.getDepositAmount().getAmount());
		sql.append("', '");
		sql.append(prenote);
		sql.append("' ) ");
		
		try 
		{
			int li_rc = connection.createStatement().executeUpdate(sql.toString());
			if (li_rc == 0) {
				connection.rollback();
				return false;
			}
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			try {
				connection.rollback();
				return false;
			} 
			catch (CannotGetJdbcConnectionException e) {
				log.error("Insert account info failed.", e);
				return false;
			} 
			catch (SQLException e) {
				log.error("Insert account info failed.", e);
				return false;
			}
		}
		
		return true;
	}
	@Override
	public boolean updateAccountApprove(String employeeNumber, String frequency, String prenote, Bank payrollAccountInfo, Bank accountInfo)
	{	
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  BHR_BANK_DEPOSIT SET BANK_CD = '");
		sql.append(payrollAccountInfo.getCode().getCode());
		sql.append("' , BANK_ACCT_NBR = '");
		sql.append(payrollAccountInfo.getAccountNumber());
		sql.append("' , BANK_ACCT_TYP = '");
		sql.append(payrollAccountInfo.getAccountType().getCode());
		sql.append("', BANK_ACCT_AMT = '");
		sql.append(payrollAccountInfo.getDepositAmount().getAmount());
		if(!"".equals(prenote))
		{
			sql.append("', BANK_STAT = '");
			sql.append(prenote);
		}
		sql.append("' WHERE EMP_NBR = '");
		sql.append(employeeNumber);
		sql.append("' AND BANK_CD = '");
		sql.append(accountInfo.getCode().getCode());
		sql.append("' AND CYR_NYR_FLG='C' AND PAY_FREQ = '");
		sql.append(frequency);
		sql.append("' AND BANK_ACCT_NBR = '");
		sql.append(accountInfo.getAccountNumber());
		sql.append("' AND BANK_ACCT_TYP= '");
		sql.append(accountInfo.getAccountType().getCode());
		sql.append("' AND BANK_ACCT_AMT = '");
		sql.append(accountInfo.getDepositAmount().getAmount());
		sql.append("'");
	
		try 
		{
			int li_rc = connection.createStatement().executeUpdate(sql.toString());
			if (li_rc == 0) {
				connection.rollback();
				return false;
			}
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			try {
				connection.rollback();
				return false;
			} 
			catch (CannotGetJdbcConnectionException e) {
				log.error("Update account info failed.", e);
				return false;
			} 
			catch (SQLException e) {
				log.error("Update account info failed.", e);
				return false;
			}
		}
		
		return true;
	}
	@Override
	public boolean deleteAccountApprove(String employeeNumber, String frequency, Bank accountInfo)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BHR_BANK_DEPOSIT WHERE EMP_NBR = '");
		sql.append(employeeNumber);  
		sql.append("' AND PAY_FREQ = '");
		sql.append(frequency);
		sql.append("' AND  BANK_CD = '");
		sql.append(accountInfo.getCode().getCode());
		sql.append("' AND CYR_NYR_FLG = 'C' AND BANK_ACCT_NBR = '");
		sql.append(accountInfo.getAccountNumber());
		sql.append("' AND  BANK_ACCT_TYP = '");
		sql.append(accountInfo.getAccountType().getCode());
		sql.append("' AND BANK_ACCT_AMT = '");
		sql.append(accountInfo.getDepositAmount().getAmount());
		sql.append("'");
		
		try 
		{
			int li_rc = connection.createStatement().executeUpdate(sql.toString());
			if (li_rc == 0) {
				connection.rollback();
				return false;
			}
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			try {
				connection.rollback();
				return false;
			} 
			catch (CannotGetJdbcConnectionException e) {
				log.error("Delete account info failed.", e);
				return false;
			} 
			catch (SQLException e) {
				log.error("Update account info failed.", e);
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public int insertNextYearAccounts(String employeeNumber)
	{
		StringBuilder sql = new StringBuilder();
		
		sql.append("insert into bhr_bank_deposit(cyr_nyr_flg, pay_freq, emp_nbr, bank_cd, bank_acct_nbr, ");
		sql.append("bank_acct_typ, bank_stat, bank_acct_amt) ");
		sql.append("select 'N', bhr_emp_pay.pay_freq, bhr_bank_deposit.emp_nbr, bhr_bank_deposit.bank_cd, bhr_bank_deposit.bank_acct_nbr, ");
		sql.append("bhr_bank_deposit.bank_acct_typ, bhr_bank_deposit.bank_stat, bhr_bank_deposit.bank_acct_amt ");
		sql.append("from bhr_bank_deposit, bhr_emp_pay "); 
		sql.append("where bhr_bank_deposit.emp_nbr = ? and bhr_bank_deposit.cyr_nyr_flg = 'C' and bhr_emp_pay.cyr_nyr_flg = 'N' ");
		sql.append("and bhr_bank_deposit.emp_nbr = bhr_emp_pay.emp_nbr and ");
		sql.append("bhr_emp_pay.pay_freq = case when (bhr_bank_deposit.pay_freq = '4') then 'D' when (bhr_bank_deposit.pay_freq = '5') then 'E' when (bhr_bank_deposit.pay_freq = '6') then 'F' end");
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql.toString());

			ps.clearParameters();
			ps.setString(1, employeeNumber);
	
			if (ps.executeUpdate() < 0) {
				return 0;
			}
		} catch (SQLException e) {
			log.error("Copy to Next Year Accounts Failed.", e);
			return -1;
		}
		
		return 1;
		
	}
	
	@Override
	public int deleteNextYearAccounts(String employeeNumber)
	{
		StringBuilder sql = new StringBuilder();
		
		//Delete all next year records
		sql.delete(0, sql.length());
		sql.append("delete from bhr_bank_deposit ");
		sql.append("where cyr_nyr_flg = 'N' and emp_nbr = ? ");
		PreparedStatement ps;
		try 
		{
			ps = (PreparedStatement) connection.prepareStatement(sql.toString());


			ps.clearParameters();
			ps.setString(1, employeeNumber);

			//Okay if no records are found to be deleted
			if (ps.executeUpdate() < 0) {
			return 0;
			}
		} catch (SQLException e) {
			log.error("Delete Next Year Accounts Failed.", e);
			return -1;
		}
		return 1;
		
	}
	
	@Override
	public int getActiveEmployee(String employeeNumber, String frequency)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT STAT_CD FROM BHR_EMP_PAY WHERE emp_nbr = :employeeNumber AND pay_freq =:frequency AND CYR_NYR_FLG = 'C'");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("frequency", frequency);
		
		Integer result;
		
		try
		{
			result = getNamedParameterJdbcTemplate().queryForInt(sql.toString(), params);
		}
		catch(EmptyResultDataAccessException e)
		{
			result = null;
			log.info("No employee found.", e);
		}
		
		return result;
	}
	
	@Override
	public boolean isNewAccountInfoRow(String employeeNumber, Frequency frequency, Bank accountInfo, Bank accountInfoPending)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT (*) FROM BEA_DRCT_DPST_BNK_ACCT ");
		sql.append("WHERE emp_nbr = :employeeNumber AND pay_freq =:frequency AND BEA_DRCT_DPST_BNK_ACCT.bnk_cd_new = :bankCode ");
		sql.append("AND BEA_DRCT_DPST_BNK_ACCT.bnk_acct_nbr_new = :accountNumber AND BEA_DRCT_DPST_BNK_ACCT.bnk_cd = :bankCodeOld ");
		sql.append("AND BEA_DRCT_DPST_BNK_ACCT.bnk_acct_nbr = :accountNumberOld AND STAT_CD = 'P'");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("frequency", frequency.getCode());
		params.addValue("bankCode", accountInfoPending.getCode().getCode());
		params.addValue("accountNumber", accountInfoPending.getAccountNumber());
		params.addValue("bankCodeOld", accountInfo.getCode().getCode());
		params.addValue("accountNumberOld", accountInfo.getAccountNumber());
		
		Integer result;
		boolean resultBool = false;
		
		try
		{
			result = getNamedParameterJdbcTemplate().queryForInt(sql.toString(), params);
		}
		catch(EmptyResultDataAccessException e)
		{
			result = null;
			log.info("No direct deposit account change requests found.", e);
		}
		
		if(result == null || result < 1)
		{
			resultBool = true;
		}
		return resultBool;
	}
	
	@Override
	public boolean isNewPayInfoRow(String employeeNumber, Frequency frequency, PayInfo payInfo)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT (*) FROM BEA_W4 WHERE emp_nbr = :employeeNumber AND pay_freq =:frequency AND STAT_CD = 'P'");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("frequency", frequency.getCode());
		
		Integer result;
		boolean resultBool = false;
		
		try
		{
			result = getNamedParameterJdbcTemplate().queryForInt(sql.toString(), params);
		}
		catch(EmptyResultDataAccessException e)
		{
			result = null;
			log.info("No pay info requests found.", e);
		}
		
		if(result == null || result < 1)
		{
			resultBool = true;
		}
		return resultBool;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List <List <String>> getRequiredFields(String payFreq)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM BHR_EAP_PAY_ASSGN_MBR WHERE pay_freq = :frequency");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("frequency", Frequency.getFrequency(payFreq).getCode());
		
		List <List <String>> result;
		
		try
		{
			result= getNamedParameterJdbcTemplate().query(sql.toString(), params, new RequiredMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			result = new ArrayList <List <String>> ();
			log.info("No direct deposit account change requests found.", e);
		}
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<List <String>> getDocRequiredFields(String payFreq)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM BHR_EAP_PAY_ASSGN_MBR WHERE pay_freq = :frequency");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("frequency", Frequency.getFrequency(payFreq).getCode());
		
		List <List <String>> result;
		
		try
		{
			result= getNamedParameterJdbcTemplate().query(sql.toString(), params, new DocRequiredMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			result = new ArrayList <List <String>> ();
			log.error("No BHR_EAP_PAY_ASSGN_MBR rows found.", e);
		}
		return result;
	}
	
	//Need to fix for marital
	@Override
	public String getApproverEmployeeNumber(String frequency, String table)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT emp_nbr FROM SEC_USERS, BHR_EAP_PAY_ASSGN_GRP ");
		sql.append("WHERE BHR_EAP_PAY_ASSGN_GRP.grp_name=:table AND BHR_EAP_PAY_ASSGN_GRP.apprvr_usr_id = usr_id ");
		sql.append("AND BHR_EAP_PAY_ASSGN_GRP.pay_freq = :frequency");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("frequency", Frequency.getFrequency(frequency).getCode());
		params.addValue("table", table);
		
		String result;
		try
		{
			result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);
		}
		catch(EmptyResultDataAccessException e)
		{
			result = "";
			log.info("No approver employee number found.", e);
		}
		return result;
	}
	
	@Override
	public User getApprover(String employeeNumber)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT emp_nbr, name_f, name_l, email, hm_email FROM BHR_EMP_DEMO ");
		sql.append("WHERE emp_nbr = :employeeNumber");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		
		User result;
		
		try
		{
			result = (User) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new UserMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			result = null;
			log.error("No approver found.", e);
		}
		return result;
	}
	
	@Override
	public boolean createNewConnection()
	{
		try {
			connection = this.getConnection();
			connection.setAutoCommit(false);
			return true;
		}
		catch (CannotGetJdbcConnectionException e) {
			log.error("Could not create new connection.", e);
			return false;
		}
		catch (SQLException e) {
			log.error("Could not create new connection.", e);
			return false;
		}
	}
	
	@Override
	public boolean commitUpdate() {
		try {
			connection.commit();
			return true;
		}
		catch (CannotGetJdbcConnectionException e) {
			log.error("Could not commit changes.", e);
			return false;
		}
		catch (SQLException e) {
			log.error("Could not commit changes.", e);
			return false;
		}
	}
	
	@Override
	public void rollbackUpdate() {
		try {
			connection.rollback();
		} 
		catch (CannotGetJdbcConnectionException e) {
			log.error("Could not rollback changes.", e);
		} 
		catch (SQLException e) {
			log.error("Could not rollback changes.", e);
		}
	}
	
	@Override
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			log.error("Could not close connection.", e);
		}
	}
	
	/**
	 *
	 * Mappers
	 *
	 */
	
	private static class FrequencyMapper implements RowMapper
	{
		public Frequency mapRow(ResultSet rs, int rows) throws SQLException
		{
			Frequency tempFrequency = Frequency.getFrequency(StringUtil.trim(rs.getString("pay_freq")));
			
			return tempFrequency;
		}
	}
//	
//	private static class BankTypeMapper implements RowMapper
//	{
//		public Code mapRow(ResultSet rs, int rows) throws SQLException
//		{
//			Code tempCode = new Code();
//			tempCode.setCode(StringUtil.trim(rs.getString("bank_acct_typ")));
//			tempCode.setDescription(StringUtil.trim(rs.getString("bank_acct_typ_descr")));
//			tempCode.setDisplayLabel(StringUtil.trim(rs.getString("bank_acct_typ")) +" - " + StringUtil.trim(rs.getString("bank_acct_typ_descr")));
//			
//			if(tempCode.getDescription().equals("default"))
//			{
//				tempCode.setDescription("");
//				tempCode.setDisplayLabel("");
//				
//			}
//			
//			return tempCode;
//		}
//	}
	
	private static class BankNameMapper implements RowMapper
	{
		public Code mapRow(ResultSet rs, int rows) throws SQLException
		{
			Code tempCode = new Code();
			tempCode.setSubCode(StringUtil.trim(rs.getString("bank_cd")));
			tempCode.setDescription(StringUtil.trim(rs.getString("bank_name")));
			tempCode.setCode(StringUtil.trim(rs.getString("transit_route")));
			return tempCode;
		}
	}
	
	private static class BankAccountMapper implements RowMapper
	{
		public List <Bank> mapRow(ResultSet rs, int rows) throws SQLException
		{
			List <Bank> bankList = new ArrayList <Bank> ();
			Bank tempBank = new Bank();
			
			tempBank.setCode((Code)ReferenceDataService.getBankCodesFromCode(StringUtil.trim(rs.getString("bnk_cd"))).clone());
			tempBank.setAccountNumber(rs.getString("bnk_acct_nbr"));
			tempBank.setAccountType((Code)ReferenceDataService.getDdAccountTypeFromCode(StringUtil.trim(rs.getString("bnk_acct_typ"))).clone());
			tempBank.setDepositAmount(new Money(new Double (rs.getString("bnk_acct_amt").trim()).doubleValue(), Currency.getInstance(Locale.US)));
			
			bankList.add(tempBank);
			
			tempBank = new Bank();
			
			tempBank.setCode((Code)ReferenceDataService.getBankCodesFromCode(StringUtil.trim(rs.getString("bnk_cd_new"))).clone());
			tempBank.setAccountNumber(rs.getString("bnk_acct_nbr_new"));
			tempBank.setAccountType((Code)ReferenceDataService.getDdAccountTypeFromCode(StringUtil.trim(rs.getString("bnk_acct_typ_new"))).clone());
			tempBank.setDepositAmount(new Money(new Double(rs.getString("bnk_acct_amt_new").trim()).doubleValue(), Currency.getInstance(Locale.US)));
			
			bankList.add(tempBank);
			
			return bankList;
		}
	}
	
	private static class BankCurrentMapper implements RowMapper
	{
		public Bank mapRow(ResultSet rs, int rows) throws SQLException
		{
			Bank tempBank = new Bank();
			Code tempBankCd = new Code();
			Code tempAcctTyp = new Code();
			
			tempBankCd.setCode(StringUtil.trim(rs.getString("bnk_cd")));
			tempBankCd.setDescription(StringUtil.trim(rs.getString("bank_name")));
			tempBankCd.setSubCode(StringUtil.trim(rs.getString("transit_route")));
			
			
			tempAcctTyp.setCode(StringUtil.trim(rs.getString("bnk_acct_typ")));
			tempAcctTyp.setDescription(StringUtil.trim(rs.getString("bank_acct_typ_descr")));
			
			tempBank.setCode(tempBankCd);
			tempBank.setAccountNumber(rs.getString("bnk_acct_nbr"));
			tempBank.setAccountType(tempAcctTyp);
			tempBank.setDepositAmount(new Money(new Double (rs.getString("bnk_acct_amt").trim()).doubleValue(), Currency.getInstance(Locale.US)));
			
			return tempBank;
		}
	}
	
	private static class BankPendingMapper implements RowMapper
	{
		public Bank mapRow(ResultSet rs, int rows) throws SQLException
		{
			Bank tempBank = new Bank();
			Code tempBankCd = new Code();
			Code tempAcctTyp = new Code();
			
			tempBankCd.setCode(StringUtil.trim(rs.getString("bnk_cd_new")));
			tempBankCd.setDescription(StringUtil.trim(rs.getString("bank_name")));
			tempBankCd.setSubCode(StringUtil.trim(rs.getString("transit_route")));
			
			
			tempAcctTyp.setCode(StringUtil.trim(rs.getString("bnk_acct_typ_new")));
			tempAcctTyp.setDescription(StringUtil.trim(rs.getString("bank_acct_typ_descr")));
			
			
			tempBank.setCode(tempBankCd);
			tempBank.setAccountNumber(rs.getString("bnk_acct_nbr_new"));
			tempBank.setAccountType(tempAcctTyp);
			tempBank.setDepositAmount(new Money(new Double(rs.getString("bnk_acct_amt_new").trim()).doubleValue(), Currency.getInstance(Locale.US)));
			
			return tempBank;
		}
	}
	
	private static class PayInfoPendingMapper implements RowMapper
	{
		public PayInfo mapRow(ResultSet rs, int rows) throws SQLException
		{
			PayInfo tempPayInfo = new PayInfo();
			
			Code tempMaritalStatus = ((Code)ReferenceDataService.getMaritalStatusFromCode(rs.getString("marital_stat_tax_new").trim()).clone());
			
			tempPayInfo.setMaritalStatus(tempMaritalStatus);
			tempPayInfo.setNumberOfExemptions(new Integer(rs.getString("nbr_tax_exempts_new").trim()));
			
			return tempPayInfo;
		}
	}
	
	private static class DocRequiredMapper implements RowMapper
	{
		public List <String> mapRow(ResultSet rs, int rows) throws SQLException
		{
 			List <String> tempList = new ArrayList <String> ();
 			tempList.add(StringUtil.trim(rs.getString("grp_name")));
			tempList.add(StringUtil.trim(rs.getString("mbr_name")));
			tempList.add(StringUtil.trim(rs.getString("doc_reqrd")));
			
			return tempList;
		}
	}
	
	private static class RequiredMapper implements RowMapper
	{
		public List <String> mapRow(ResultSet rs, int rows) throws SQLException
		{
 			List <String> tempList = new ArrayList <String> ();
 			tempList.add(StringUtil.trim(rs.getString("grp_name")));
			tempList.add(StringUtil.trim(rs.getString("mbr_name")));
			tempList.add(StringUtil.trim(rs.getString("reqrd_field")));
			
			return tempList;
		}
	}
	
	private static class UserMapper implements RowMapper
	{
		public User mapRow(ResultSet rs, int rows) throws SQLException
		{
			User tempUser = new User();
			
			tempUser.setEmployeeNumber(StringUtil.trim(rs.getString("emp_nbr")));
			String fName = StringUtil.trim(rs.getString("name_f"));
			tempUser.setFirstName(fName);
			String lName = StringUtil.trim(rs.getString("name_l"));
			tempUser.setLastName(lName);
			if(rs.getString("email") != null)
			{
				tempUser.setWorkEmail(StringUtil.trim(rs.getString("email")));
				tempUser.setWorkEmailVerification(tempUser.getWorkEmail());
			}
			if(rs.getString("hm_email") != null)
			{
				tempUser.setHomeEmail(StringUtil.trim(rs.getString("hm_email")));
				tempUser.setHomeEmailVerification(tempUser.getHomeEmail());
			}

			return tempUser;
		}
	}
	
}