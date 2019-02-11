package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.dao.api.IJobDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Job;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.util.NumberUtil;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class JobDao extends NamedParameterJdbcDaoSupport implements IJobDao
{
	@Override
	@SuppressWarnings("unchecked")
	public List<Job> getJobs(String employeeNumber)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT BHR_EMP_JOB.JOB_CD,");
		sql.append("BTHR_JOB_CD.JOB_CD_DESCR,");
		sql.append("BHR_EMP_JOB.NBR_ANNUAL_PYMTS,");
		sql.append("BHR_EMP_JOB.REG_HRS_WRK,");
		sql.append("BHR_EMP_JOB.NBR_REMAIN_PYMTS,");
		sql.append("BHR_EMP_JOB.CONTR_AMT,");
		sql.append("BHR_EMP_JOB.DLY_RATE_OF_PAY,");
		sql.append("BHR_EMP_JOB.PAY_RATE,");
		sql.append("BHR_EMP_JOB.OVTM_RATE,");
		sql.append("BHR_EMP_JOB.PRIM_JOB,");   //jf20140110 earnings report
		sql.append("BHR_EMP_JOB.CAMPUS_ID,");   //jf20140110 earnings report
		sql.append("ISNULL((SELECT DISTINCT B.CAMPUS_NAME ");   //jf20140110 earnings report campus name
		sql.append("          FROM CR_DEMO B ");   //jf20140110 get campus name
		sql.append("         WHERE B.CAMPUS_ID = BHR_EMP_JOB.CAMPUS_ID ");   //jf20140110 get campus name
		sql.append("           AND B.SCH_YR = (SELECT MAX(C.SCH_YR) ");   //jf20140110 get campus name
		sql.append("                             FROM CR_DEMO C)), '') AS CAMPUS_NAME, ");   //jf20140110 get campus name
		sql.append("BHR_EMP_JOB.PAY_FREQ");
		sql.append(" FROM BHR_EMP_JOB,");
		sql.append(" BTHR_JOB_CD");
		sql.append(" WHERE BHR_EMP_JOB.EMP_NBR = :employeeNumber");
		sql.append(" AND BHR_EMP_JOB.CYR_NYR_FLG = 'C'");
		sql.append(" AND BTHR_JOB_CD.CYR_NYR_FLG = 'C'");
		sql.append(" AND BHR_EMP_JOB.JOB_CD = BTHR_JOB_CD.JOB_CD");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<Job>()
		{
			@Override
			public Job mapRow(ResultSet rs, int row) throws SQLException {
				Job j = new Job();
				
				Code title = new Code();
				title.setCode(StringUtil.trim(rs.getString("JOB_CD")));
				title.setDescription(StringUtil.trim(rs.getString("JOB_CD_DESCR")));
				j.setTitle(title);
				j.setFrequency(Frequency.getFrequency(StringUtil.trim(rs.getString("PAY_FREQ"))));
				j.setAnnualPayments(rs.getInt("NBR_ANNUAL_PYMTS"));
				j.setRegularHours(NumberUtil.value(rs.getBigDecimal("REG_HRS_WRK")));   //jf20120724 fix decimal truncation
				j.setRemainPayments(rs.getInt("NBR_REMAIN_PYMTS"));
				j.setAnnualSalary(NumberUtil.value(rs.getBigDecimal("CONTR_AMT")));
				j.setDailyRate(NumberUtil.value(rs.getBigDecimal("DLY_RATE_OF_PAY")));
				j.setHourlyRate(NumberUtil.value(rs.getBigDecimal("PAY_RATE")));
				j.setOvertimeRate(NumberUtil.value(rs.getBigDecimal("OVTM_RATE")));
				j.setPrimaryJob(rs.getString("PRIM_JOB"));   //jf20140110 earnings report
				j.setCampusID(rs.getString("CAMPUS_ID"));   //jf20140110 get campus name
				j.setCampusName(rs.getString("CAMPUS_NAME"));   //jf20140110 get campus name
				
				return j;
			}
		});
	}
}