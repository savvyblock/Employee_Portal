package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.esc20.txeis.EmployeeAccess.dao.api.IPayDao;
import net.esc20.txeis.EmployeeAccess.domainobject.PayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.MaritalStatus;
import net.esc20.txeis.EmployeeAccess.service.ReferenceDataService;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PayDao extends NamedParameterJdbcDaoSupport implements IPayDao
{
	private static Log log = LogFactory.getLog(PayDao.class);
	
	public PayInfo getPayInfo(String employeeNumber, Frequency frequency)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MARITAL_STAT_TAX,");
		sql.append(" NBR_TAX_EXEMPTS");
		sql.append(" FROM BHR_EMP_PAY");
		sql.append(" WHERE CYR_NYR_FLG = 'C'");
		sql.append(" AND PAY_FREQ = :frequency");
		sql.append(" AND EMP_NBR = :employeeNumber");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("frequency", frequency.getCode());
		params.addValue("employeeNumber", employeeNumber);
		
		try
		{
			return (PayInfo)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new ParameterizedRowMapper<PayInfo>()
			{
				@Override
				public PayInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
					PayInfo p = new PayInfo();
					p.setMaritalStatus(ReferenceDataService.getMaritalStatusFromCode(StringUtil.trim(rs.getString("MARITAL_STAT_TAX"))));
					p.setNumberOfExemptions(rs.getInt("NBR_TAX_EXEMPTS"));
					return p;
				}
			});
		}
		catch(EmptyResultDataAccessException ex)
		{
			log.info("No pay info found.");
			return null;
		}
	}
}