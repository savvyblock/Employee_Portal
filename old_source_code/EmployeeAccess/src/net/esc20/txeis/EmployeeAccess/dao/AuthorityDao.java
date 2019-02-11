package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.dao.api.IAuthorityDao;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveEmployeePMISData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public class AuthorityDao extends NamedParameterJdbcDaoSupport implements IAuthorityDao{

	private static Log log = LogFactory.getLog(AuthorityDao.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List <Integer> retrieveMenuPermissions()
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT enbl_eap, enbl_earn, enbl_lv, enbl_w2, enbl_cal_ytd, enbl_cpi, enbl_ded, enbl_demo, enbl_pay, enbl_1095, enbl_elec_consnt_w2, enbl_elec_consnt_1095, enbl_lv_req "); 
			sql.append("FROM bhr_eap_opt");
	
			MapSqlParameterSource params = new MapSqlParameterSource();
	
			List <String> result = (List <String> )getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new MenuPermissionsMapper());
			
			List <Integer> intResult = new ArrayList <Integer>();
			
			for(String s: result)
			{
				if("Y".equals(s))
				{
					intResult.add(1);
				}
				else
				{
					intResult.add(0);
				}
			}
			
			return intResult;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("EAP HR Options not found.");
			return null;
		}
	}
	
	@Override
	public void updatePassCount(int passCount, String userName) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE bea_users SET  bea_users.pswd_cnt = :is_pass_count, ");	
		if(passCount == 3)
		{
			sql.append("bea_users.lk_pswd = 'Y' ");
		}
		else
		{
			sql.append("bea_users.lk_pswd = 'N' ");
		}
		sql.append("where bea_users.usrname= :is_username");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("is_pass_count", passCount);	
		params.addValue("is_username", userName);	
		
		getNamedParameterJdbcTemplate().update(sql.toString(), params);
		
	}
	
	@Override
	public void deletePassword(String userName)
	{
		StringBuilder sql = new StringBuilder();
		
		sql.append("UPDATE bea_users SET  bea_users.usrpswd='', bea_users.tmp_dts = '' where bea_users.usrname= :is_username");	
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("is_username", userName);		
		
		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}
	
	@Override
	public boolean isTempPassword(String userName)
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT bea_users.tmp_dts FROM bea_users where bea_users.usrname= :is_username");  
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("is_username", userName);
	
			String result = (String)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);
			
			if(result == null || "".equals(result.trim()))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No timestamp found for chosen employee");
			return false;
		}
	}
	@Override
	public int retrievePassCount(String userName) {
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT bea_users.pswd_cnt FROM bea_users where bea_users.usrname= :is_username");  
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("is_username", userName);
	
			int result = (Integer)getNamedParameterJdbcTemplate().queryForInt(sql.toString(), params);
			
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No User found");
			return -1;
		}
	}
	
	
	
	@Override
	public String retrieveTempLock(String userName)
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT lk_fnl "); 
			sql.append("FROM bea_users ");
			sql.append("WHERE usrname=:is_username");
	
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("is_username", userName);
			
			String lock = (String)getNamedParameterJdbcTemplate().queryForObject (sql.toString(), params, String.class);
		
			return lock;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("No User found");
			return "";
		}
	}
	
	private static class MenuPermissionsMapper implements RowMapper
	{
		public List <String> mapRow(ResultSet rs, int rows) throws SQLException
		{

			List <String>  menuPermissions = new ArrayList <String>();
			menuPermissions.add(rs.getString("enbl_eap").trim());
			menuPermissions.add(rs.getString("enbl_earn").trim());
			menuPermissions.add(rs.getString("enbl_lv").trim());
			menuPermissions.add(rs.getString("enbl_w2").trim());
			menuPermissions.add(rs.getString("enbl_cal_ytd").trim());
			menuPermissions.add(rs.getString("enbl_cpi").trim());
			menuPermissions.add(rs.getString("enbl_ded").trim());
			menuPermissions.add(rs.getString("enbl_demo").trim());
			menuPermissions.add(rs.getString("enbl_pay").trim());
			menuPermissions.add(rs.getString("enbl_1095").trim());
			menuPermissions.add(rs.getString("enbl_elec_consnt_w2").trim());
			menuPermissions.add(rs.getString("enbl_elec_consnt_1095").trim());
			menuPermissions.add(rs.getString("enbl_lv_req").trim());

			return menuPermissions;
		}
	}
	
	@Override
	public Integer employeePayCampusLeaveCampusCount(String employeeNumber) {
		String sql = "select count(*) from bhr_emp_pay bep, bthr_eap_campus btec "
				+ "where bep.emp_nbr = :employeeNumber and bep.cyr_nyr_flg = 'C' and bep.pay_campus=btec.campus_id and bep.pay_dept=btec.dept";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		return (Integer)getNamedParameterJdbcTemplate().queryForObject (sql, params, Integer.class);
	}

	@Override
	public boolean isLeaveSupervisor(String employeeNumber) {
		String sql = "SELECT COUNT(*) FROM BHR_EAP_EMP_TO_SPVSR E2S WHERE E2S.SPVSR_EMP_NBR=:employeeNumber";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		Integer count = (Integer)getNamedParameterJdbcTemplate().queryForObject (sql, params, Integer.class);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isLeavePMISSupervisor(String employeeNumber) {
		LeaveEmployeePMISData empPMISData=null;
		String sqlPMISData = "SELECT TOP 1 PPC.BILLET_NBR, PPC.POS_NBR FROM BHR_PMIS_POS_CTRL PPC WHERE PPC.OCC_EMP_NBR=:employeeNumber AND PPC.POS_TYP='P' AND PPC.CYR_NYR_FLG='C' ORDER BY PPC.PAY_FREQ DESC";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		try {
			empPMISData = (LeaveEmployeePMISData) getNamedParameterJdbcTemplate().query(sqlPMISData, params,
				new ResultSetExtractor<LeaveEmployeePMISData>() {
					@Override
					public LeaveEmployeePMISData extractData(final ResultSet rs) throws SQLException, DataAccessException {
						LeaveEmployeePMISData result = new LeaveEmployeePMISData();
						rs.next();
						result.setBilletNumber(rs.getString("BILLET_NBR").trim());
						result.setPosNumber(rs.getString("POS_NBR").trim());
						return result;
					}
				});
		} catch (DataAccessException e) {	
		}
		
		if (empPMISData==null) {
			return false;
		} else {
			String sqlCount = "SELECT COUNT(*) FROM BHR_PMIS_POS_CTRL PPC WHERE PPC.SPVSR_BILLET_NBR=:billetNumber AND PPC.SPVSR_POS_NBR=:posNumber " +
					"AND PPC.PAY_FREQ=(SELECT MAX(PPC2.PAY_FREQ) FROM BHR_PMIS_POS_CTRL PPC2 WHERE PPC2.OCC_EMP_NBR=PPC.OCC_EMP_NBR AND PPC2.POS_TYP='P' AND PPC2.CYR_NYR_FLG='C') " +
					"AND PPC.POS_TYP='P' AND PPC.CYR_NYR_FLG='C'";
			params = new MapSqlParameterSource();
			params.addValue("billetNumber", empPMISData.getBilletNumber());
			params.addValue("posNumber", empPMISData.getPosNumber());
			Integer count = (Integer)getNamedParameterJdbcTemplate().queryForObject (sqlCount, params, Integer.class);
				
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		}		
	}
}
