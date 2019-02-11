package net.esc20.txeis.EmployeeAccess.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.conversion.RscccDate;
import net.esc20.txeis.EmployeeAccess.dao.api.ILeaveDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Leave;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;
import net.esc20.txeis.EmployeeAccess.util.NumberUtil;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class LeaveDao extends NamedParameterJdbcDaoSupport implements ILeaveDao
{
	@Override
	@SuppressWarnings("unchecked")
	public List<LeaveInfo> getLeaveInfo(String employeeNumber)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT BHR_EMP_LV.PAY_FREQ, BHR_EMP_LV.LV_TYP, BHR_EMP_LV.LV_BEGIN_BAL, BHR_EMP_LV.LV_EARNED, BHR_EMP_LV.LV_USED, ");
		sql.append("		BTHR_LV_TYP_DESCR.LONG_DESCR, BTHR_LV_TYP_DESCR.POST_AGNST_ZERO_BAL, BTHR_LV_TYP.DAYS_HRS, BTHR_LV_TYP.ADD_SUBTRACT_BAL, ");
				
		sql.append("		(SELECT ISNULL((SELECT SUM(ISNULL(BHR_EMP_LV_XMITAL.LV_UNITS_EARNED, 0))),0) ");
		sql.append("			FROM BHR_EMP_LV_XMITAL ");
		sql.append("			WHERE BHR_EMP_LV_XMITAL.CYR_NYR_FLG = 'C' AND ");
		sql.append("				BHR_EMP_LV_XMITAL.EMP_NBR = :employeeNumber AND ");
		sql.append("				BHR_EMP_LV_XMITAL.PAY_FREQ = BHR_EMP_LV.PAY_FREQ AND ");
		sql.append("				BHR_EMP_LV_XMITAL.LV_TYP = BHR_EMP_LV.LV_TYP AND ");
		sql.append("				LENGTH(TRIM(ISNULL(BHR_EMP_LV_XMITAL.PROCESS_DT, '')))=0) AS PENDING_EARNED, ");
				
		sql.append("		(SELECT ISNULL((SELECT SUM(ISNULL(BEA_EMP_LV_RQST.LV_UNITS_USED, 0))),0) ");
		sql.append("			FROM BEA_EMP_LV_RQST ");
		sql.append("			WHERE BEA_EMP_LV_RQST.STATUS_CD = 'P' AND ");
		sql.append("				BEA_EMP_LV_RQST.EMP_NBR = :employeeNumber AND ");
		sql.append("				BEA_EMP_LV_RQST.PAY_FREQ = BHR_EMP_LV.PAY_FREQ AND ");
		sql.append("				BEA_EMP_LV_RQST.LV_TYP = BHR_EMP_LV.LV_TYP) AS PENDING_APPROVAL, ");

		sql.append("		(SELECT ISNULL((SELECT SUM(ISNULL(BEA_EMP_LV_RQST.LV_UNITS_USED, 0))),0) ");
		sql.append("			FROM BEA_EMP_LV_RQST ");
		sql.append("			WHERE BEA_EMP_LV_RQST.STATUS_CD IN ('A','L') AND ");
		sql.append("				BEA_EMP_LV_RQST.EMP_NBR = :employeeNumber AND ");
		sql.append("				BEA_EMP_LV_RQST.PAY_FREQ = BHR_EMP_LV.PAY_FREQ AND ");
		sql.append("				BEA_EMP_LV_RQST.LV_TYP = BHR_EMP_LV.LV_TYP) AS PENDING_PAYROLL, ");

		sql.append("		(SELECT ISNULL((SELECT SUM(ISNULL(BHR_EMP_LV_XMITAL.LV_UNITS_USED, 0))),0) ");
		sql.append("			FROM BHR_EMP_LV_XMITAL ");
		sql.append("			WHERE BHR_EMP_LV_XMITAL.CYR_NYR_FLG = 'C' AND  ");
		sql.append("				BHR_EMP_LV_XMITAL.EMP_NBR = :employeeNumber AND  ");
		sql.append("				BHR_EMP_LV_XMITAL.PAY_FREQ = BHR_EMP_LV.PAY_FREQ AND ");
		sql.append("				BHR_EMP_LV_XMITAL.LV_TYP = BHR_EMP_LV.LV_TYP AND  ");
		sql.append("				LENGTH(TRIM(ISNULL(BHR_EMP_LV_XMITAL.PROCESS_DT, '')))=0) AS PENDING_USED ");
				
		sql.append("	FROM BHR_EMP_LV, BTHR_LV_TYP_DESCR, BTHR_LV_TYP ");
		sql.append("	WHERE CYR_NYR_FLG = 'C' ");
		sql.append("		AND EMP_NBR = :employeeNumber ");
		sql.append("		AND BHR_EMP_LV.LV_TYP = BTHR_LV_TYP_DESCR.LV_TYP ");
		sql.append("		AND BHR_EMP_LV.LV_TYP = BTHR_LV_TYP.LV_TYP ");
		sql.append("		AND BHR_EMP_LV.PAY_FREQ = BTHR_LV_TYP.PAY_FREQ ");
		sql.append("		AND BTHR_LV_TYP.STAT='A' ");
		sql.append("		AND BTHR_LV_TYP_DESCR.STAT='A' ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<LeaveInfo>()
		{
			@Override
			public LeaveInfo mapRow(ResultSet rs, int row) throws SQLException {
				LeaveInfo l = new LeaveInfo();
				
				l.setFrequency(Frequency.getFrequency(StringUtil.trim(rs.getString("PAY_FREQ"))));
				Code type = new Code();
				type.setCode(StringUtil.trim(rs.getString("LV_TYP")));
				type.setDescription(StringUtil.trim(rs.getString("LONG_DESCR")));
				l.setType(type);
				l.setBeginBalance(NumberUtil.value(rs.getBigDecimal("LV_BEGIN_BAL").setScale(3, BigDecimal.ROUND_HALF_UP)));
				l.setAdvancedEarned(NumberUtil.value(rs.getBigDecimal("LV_EARNED").setScale(3, BigDecimal.ROUND_HALF_UP)));
				l.setUsed(NumberUtil.value(rs.getBigDecimal("LV_USED").setScale(3, BigDecimal.ROUND_HALF_UP)));
				l.setPendingEarned(NumberUtil.value(rs.getBigDecimal("PENDING_EARNED").setScale(3, BigDecimal.ROUND_HALF_UP)));
				l.setPendingUsed(NumberUtil.value(rs.getBigDecimal("PENDING_USED").setScale(3, BigDecimal.ROUND_HALF_UP)));
				l.setPendingApproval(NumberUtil.value(rs.getBigDecimal("PENDING_APPROVAL").setScale(3, BigDecimal.ROUND_HALF_UP)));
				l.setPendingPayroll(NumberUtil.value(rs.getBigDecimal("PENDING_PAYROLL").setScale(3, BigDecimal.ROUND_HALF_UP)));
				
				l.setDaysHrs(rs.getString("DAYS_HRS").trim());
				l.setSubtractFromBalanceValue(rs.getString("ADD_SUBTRACT_BAL").trim().toUpperCase());
				boolean booleanValue = rs.getString("ADD_SUBTRACT_BAL").trim().toUpperCase().equals("S") ? true : false;
				l.setSubtractFromBalance(booleanValue);
				l.setPostAgainstZeroBalanceValue(rs.getString("POST_AGNST_ZERO_BAL").trim().toUpperCase());
				l.setPostAgainstZeroBalance(rs.getString("POST_AGNST_ZERO_BAL").trim().toUpperCase().equals("Y") ? true : false);
				return l;
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ICode> getAvailableLeaveTypes(String employeeNumber, Frequency frequency)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT BHR_EMP_LV_XMITAL.LV_TYP, BTHR_LV_TYP_DESCR.LONG_DESCR");
		sql.append(" FROM BHR_EMP_LV_XMITAL, BTHR_LV_TYP_DESCR");
		sql.append(" WHERE CYR_NYR_FLG = 'C'");
		sql.append(" AND PAY_FREQ = :frequency");
		sql.append(" AND EMP_NBR = :employeeNumber");
		sql.append(" AND BHR_EMP_LV_XMITAL.LV_TYP = BTHR_LV_TYP_DESCR.LV_TYP");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("frequency", frequency.getCode());
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<ICode>()
		{
			@Override
			public ICode mapRow(ResultSet rs, int rowNum) throws SQLException {
				Code code = new Code();
				code.setCode(StringUtil.trim(rs.getString("LV_TYP")));
				code.setDescription(StringUtil.trim(rs.getString("LONG_DESCR")));
				return code;
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Leave> getLeaves(String employeeNumber, Frequency frequency, Date from, Date to, String leaveType)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT *, BTHR_LV_TYP_DESCR.LONG_DESCR");
		sql.append(" FROM BHR_EMP_LV_XMITAL, BTHR_LV_TYP_DESCR");
		sql.append(" WHERE CYR_NYR_FLG = 'C'");
		sql.append(" AND PAY_FREQ = :frequency");
		sql.append(" AND EMP_NBR = :employeeNumber");
		
		if(!StringUtil.isNullOrEmpty(leaveType))
		{
			sql.append(" AND BHR_EMP_LV_XMITAL.LV_TYP = :leaveType");
		}
		
		sql.append(" AND BHR_EMP_LV_XMITAL.LV_TYP = BTHR_LV_TYP_DESCR.LV_TYP");
		sql.append(" AND DT_OF_ABS >= :dateFrom");
		if(to != null)
		{
			sql.append(" AND DT_OF_ABS <= :dateTo");
		}
		sql.append(" ORDER BY DT_OF_ABS DESC");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("frequency", frequency.getCode());
		
		if(!StringUtil.isNullOrEmpty(leaveType))
		{
			params.addValue("leaveType", leaveType);
		}
		
		params.addValue("dateFrom", RscccDate.convertFromDate(from));
		
		if(to != null)
		{
			params.addValue("dateTo",RscccDate.convertFromDate(to));
		}
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<Leave>()
		{
			@Override
			public Leave mapRow(ResultSet rs, int rowNum) throws SQLException {
				Leave l = new Leave();
				
				Code type = new Code();
				type.setCode(rs.getString("LV_TYP"));
				type.setDescription(rs.getString("LONG_DESCR"));
				l.setType(type);
				l.setDateOfPay(RscccDate.convertToDate(StringUtil.trim(rs.getString("DT_OF_PAY"))));
				l.setDateOfLeave(RscccDate.convertToDate(StringUtil.trim(rs.getString("DT_OF_ABS"))));
				l.setLeaveUsed(NumberUtil.value(rs.getBigDecimal("LV_UNITS_USED")));
				l.setLeaveEarned(NumberUtil.value(rs.getBigDecimal("LV_UNITS_EARNED")));
				String status = StringUtil.trim(rs.getString("PROCESS_DT"));
				
				if(status.length() > 0)
				{
					l.setStatus(Leave.Status.Processed);
				}
				else
				{
					l.setStatus(Leave.Status.NotProcessed);
				}
				
				return l;
			}
		});
	}
}