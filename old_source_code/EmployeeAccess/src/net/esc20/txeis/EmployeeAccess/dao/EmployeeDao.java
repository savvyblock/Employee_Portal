package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.dao.api.IEmployeeDao;
import net.esc20.txeis.EmployeeAccess.domainobject.EmployeeInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class EmployeeDao extends NamedParameterJdbcDaoSupport implements IEmployeeDao
{
	@SuppressWarnings("unchecked")
	@Override
	public EmployeeInfo getEmployeeInfo(String employeeNumber)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT B.HIGH_DEGREE, et_c015_highdeg.highdeg_descr,");
		sql.append("B.YRS_PRO_EXPER,");
		sql.append("B.YRS_PRO_EXPER_LOC,");
		sql.append("B.YRS_EXP_DIST,");
		sql.append("B.YRS_EXP_DIST_LOC");
		sql.append(" FROM BHR_EMP_EMPLY B, et_c015_highdeg, bhr_options");
		sql.append(" WHERE EMP_NBR = :employeeNumber");
		
		if(checkHighDegree(employeeNumber) != null && !checkHighDegree(employeeNumber).isEmpty()){
			sql.append(" AND B.HIGH_DEGREE = et_c015_highdeg.highdeg_cd");
		} else {
			sql.append(" AND 1 = et_c015_highdeg.highdeg_cd");
		}
	
		sql.append(" AND et_c015_highdeg.sch_yr = bhr_options.peims_cd_yr");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		
		try
		{
			List<EmployeeInfo> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<EmployeeInfo>()
			{
				@Override
				public EmployeeInfo mapRow(ResultSet rs, int row)
						throws SQLException {
					EmployeeInfo e = new EmployeeInfo();
					Code degree = new Code();
					degree.setCode(StringUtil.trim(rs.getString("HIGH_DEGREE")));
					if(degree.getCode().equals("")){
						degree.setDescription("");
					}else {
						degree.setDescription(StringUtil.trim(rs.getString("HIGHDEG_DESCR")));
					}
					e.setDegree(degree);
					String proExperience = StringUtil.trim(rs.getString("YRS_PRO_EXPER"));
					proExperience = proExperience == null ? "" : proExperience.trim();							//cs20160827
					e.setProExperience(proExperience);
//					if(!StringUtil.isNullOrEmpty(proExperience))
//					{
//						e.setProExperience(proExperience);
//					} 
					String proExperienceDistrict = StringUtil.trim(rs.getString("YRS_PRO_EXPER_LOC"));
					proExperienceDistrict = proExperienceDistrict == null ? "" : proExperienceDistrict.trim();	//cs20160827
					e.setProExperienceDistrict(proExperienceDistrict);
//					if(!StringUtil.isNullOrEmpty(proExperienceDistrict))
//					{
//						e.setProExperienceDistrict(proExperienceDistrict);
//					}
					String nonProExperience = StringUtil.trim(rs.getString("YRS_EXP_DIST"));
					nonProExperience = nonProExperience == null ? "" : nonProExperience.trim();					//cs20160827
					e.setNonProExperience(nonProExperience);
//					if(!StringUtil.isNullOrEmpty(nonProExperience))
//					{
//						e.setNonProExperience(nonProExperience);
//					}
					String nonProExperienceDistrict = StringUtil.trim(rs.getString("YRS_EXP_DIST_LOC"));
					nonProExperienceDistrict = nonProExperienceDistrict == null ? "" : nonProExperienceDistrict.trim();	//cs20160827
					e.setNonProExperienceDistrict(nonProExperienceDistrict);
//					if(!StringUtil.isNullOrEmpty(nonProExperienceDistrict))
//					{
//						e.setNonProExperienceDistrict(nonProExperienceDistrict);
//					}
					return e;
				}
			});
			
			if(result != null && result.size() > 0)
			{
				return result.get(0);
			}
			else
			{
				return new EmployeeInfo();
			}
		}
		catch(EmptyResultDataAccessException ex)
		{
			return null;
		}
	}
	
	@Override
	public Map<Frequency,String> getPayCampuses(String employeeNumber)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PAY_FREQ, PAY_CAMPUS FROM BHR_EMP_PAY ");
		sql.append("WHERE CYR_NYR_FLG = 'C'");
		sql.append(" AND EMP_NBR = :employeeNumber");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		
		final Map<Frequency,String> result = new HashMap<Frequency,String>();
		
		getNamedParameterJdbcTemplate().query(sql.toString(), params, new RowCallbackHandler()
		{
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				String frequency = StringUtil.trim(rs.getString("PAY_FREQ"));
				String payCampus = StringUtil.trim(rs.getString("PAY_CAMPUS"));
				result.put(Frequency.getFrequency(frequency), payCampus);
			}
		});
		
		return result;
	}
	//Check if bhr_emp has HighDegree for the specific emp nbr
	public String checkHighDegree(String empNbr) {
		String ls_return = "";
		String ls_sql = "select HIGH_DEGREE from bhr_emp_emply where emp_nbr = :empNbr ";
				
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empNbr", empNbr);
				
		try {
			ls_return  = (String) this.getNamedParameterJdbcTemplate().queryForObject(ls_sql, params, String.class);
			ls_return = (ls_return == null) ? "" : ls_return.trim();
		}
		catch (Exception e) {
			ls_return = "";
		}
		return ls_return;

	}

}