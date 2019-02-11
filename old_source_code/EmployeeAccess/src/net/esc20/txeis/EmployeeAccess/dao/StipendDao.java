package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.dao.api.IStipendDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Stipend;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.util.NumberUtil;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class StipendDao extends NamedParameterJdbcDaoSupport implements IStipendDao
{
	@Override
	@SuppressWarnings("unchecked")
	public List<Stipend> getStipends(String employeeNumber)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT BHR_EMP_EXTRA_DUTY.EXTRA_DUTY_CD,");
		sql.append("BTHR_EXTRA_DUTY.EXTRA_DUTY_DESCR,");
		sql.append("BTHR_EXTRA_DUTY.DEFAULT_ACCT_TYP,");
		sql.append("BHR_EMP_EXTRA_DUTY.EXTRA_DUTY_AMT,");
		sql.append("BHR_EMP_EXTRA_DUTY.REMAIN_AMT,");
		sql.append("BHR_EMP_EXTRA_DUTY.REMAIN_PYMTS,");
		sql.append("BHR_EMP_EXTRA_DUTY.PAY_FREQ");
		sql.append(" FROM BHR_EMP_EXTRA_DUTY,");
		sql.append(" BTHR_EXTRA_DUTY");
		sql.append(" WHERE BHR_EMP_EXTRA_DUTY.EMP_NBR = :employeeNumber");
		sql.append(" AND BHR_EMP_EXTRA_DUTY.CYR_NYR_FLG = 'C'");
		sql.append(" AND BTHR_EXTRA_DUTY.CYR_NYR_FLG = 'C'");
		sql.append(" AND BHR_EMP_EXTRA_DUTY.EXTRA_DUTY_CD = BTHR_EXTRA_DUTY.EXTRA_DUTY_CD");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<Stipend>()
		{
			@Override
			public Stipend mapRow(ResultSet rs, int rowNum) throws SQLException {
				Stipend s = new Stipend();
				Code duty = new Code();
				duty.setCode(StringUtil.trim(rs.getString("EXTRA_DUTY_CD")));
				duty.setDescription(StringUtil.trim(rs.getString("EXTRA_DUTY_DESCR")));
				s.setExtraDuty(duty);
				s.setFrequency(Frequency.getFrequency(StringUtil.trim(rs.getString("PAY_FREQ"))));
				s.setType(StringUtil.trim(rs.getString("DEFAULT_ACCT_TYP")));
				s.setAmount(NumberUtil.value(rs.getBigDecimal("EXTRA_DUTY_AMT")));
				s.setRemainAmount(NumberUtil.value(rs.getBigDecimal("REMAIN_AMT")));
				s.setRemainPayments(NumberUtil.value(rs.getBigDecimal("REMAIN_PYMTS")));
				
				return s;
			}
		});
	}
}