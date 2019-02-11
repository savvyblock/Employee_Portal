package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.conversion.YesNoStringToBoolean;
import net.esc20.txeis.EmployeeAccess.dao.api.IDeductionDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Deduction;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.util.NumberUtil;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class DeductionDao extends NamedParameterJdbcDaoSupport implements IDeductionDao
{
	@Override
	@SuppressWarnings("unchecked")
	public List<Frequency> getAvailableFrequencies(String employeeNumber)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT PAY_FREQ FROM BHR_EMP_DEDUCT");
		sql.append(" WHERE CYR_NYR_FLG = 'C'");
		sql.append(" AND EMP_NBR = :employeeNumber");
		sql.append(" UNION ");
		sql.append("SELECT DISTINCT PAY_FREQ FROM BHR_EMP_PAY");
		sql.append(" WHERE CYR_NYR_FLG = 'C'");
		sql.append(" AND EMP_NBR = :employeeNumber");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		
		List<Frequency> f = getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<Frequency>()
		{
			@Override
			public Frequency mapRow(ResultSet rs, int row)
					throws SQLException {
				return Frequency.getFrequency(StringUtil.trim(rs.getString("PAY_FREQ")));
			}
		});
		
		Collections.sort(f);
		
		return f;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Deduction> getDeductions(String employeeNumber, final Frequency frequency)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT *, (SELECT SHORT_DESCR FROM BTHR_DEDUC_CD WHERE DED_CD = BHR_EMP_DEDUCT.DED_CD) AS SHORT_DESCR ");
		sql.append(" FROM BHR_EMP_DEDUCT");
		sql.append(" WHERE CYR_NYR_FLG = 'C'");
		sql.append(" AND PAY_FREQ = :frequency");
		sql.append(" AND EMP_NBR = :employeeNumber");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("frequency", frequency.getCode());
		params.addValue("employeeNumber", employeeNumber);
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<Deduction>()
		{
			@Override
			public Deduction mapRow(ResultSet rs, int row)
					throws SQLException {
				Deduction d = new Deduction();
				d.setFrequency(frequency);
				Code c = new Code();
				c.setCode(StringUtil.trim(rs.getString("DED_CD")));
				c.setDescription(StringUtil.trim(rs.getString("SHORT_DESCR")));
				d.setCode(c);
				d.setAmount(NumberUtil.value(rs.getBigDecimal("EMP_AMT")));
				d.setCafeteria(YesNoStringToBoolean.convertToBoolean(StringUtil.trim(rs.getString("CAFE_FLG"))));
				d.setEmployerContributionAmount(NumberUtil.value(rs.getBigDecimal("EMPLR_AMT")));
				return d;
			}
		});
	}
}