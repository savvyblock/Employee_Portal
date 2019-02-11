package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.dao.api.IReferenceDataDao;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public class ReferenceDataDao extends NamedParameterJdbcDaoSupport implements IReferenceDataDao 
{
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<Code> getTitles() 
//	{
//		StringBuilder sql = new StringBuilder();
//		sql.append("SELECT DISTINCT CODE, DESCRIPTION FROM BTHR_TITLE_CD");
//		MapSqlParameterSource params = new MapSqlParameterSource();
//		
//		List <Code> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new CodeMapper());
//		result.add(0, new Code("",""));
//		return result;	
//	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List <Code> getGenerations()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT GEN_CD as code, GEN_DESCR as description, '' as subCode FROM ET_C012_GEN");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		List <Code> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new CodeMapper());
		//this table doesn't need a blank row as its got a default row which the mapper handles.
		//result.add(0, new Code("",""));
		return result;	
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List <Code> getStates()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT abbrev as code, state as description, '' as subCode FROM BTHR_STATE_ABBREV");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		List <Code> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new CodeMapper());
		result.add(0, new Code("",""));
		return result;	
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List <Code> getRestrictions()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT type as code, descr as description, '' as subCode FROM BTHR_RESTRICT_TYP");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		List <Code> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new CodeMapper());
		
		return result;	
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List <Code> getMaritalStatuses()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT stat as code, descr as description, '' as subCode FROM BTHR_MARITAL_TAX_STAT");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		List <Code> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new CodeMapper());
		
		return result;	
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List <Code> getDdAccountTypes()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT bank_acct_typ as code, bank_acct_typ_descr as description, '' as subCode FROM BTHR_BANK_ACCT_TYP");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		List <Code> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new CodeMapper());
		
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List <Code> getAvailableBanks()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT transit_route as subCode, bank_name as description, bank_cd as code  FROM BTHR_BANK_CODES ORDER BY bank_name ASC");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		List <Code> result;
		
		try
		{
			result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new CodeMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			result = new ArrayList <Code> ();
		}
		return result;
	}
	
	private static class CodeMapper implements RowMapper
	{
		public Code mapRow(ResultSet rs, int rows) throws SQLException
		{
			Code tempCode = new Code();
			tempCode.setCode(StringUtil.trim(rs.getString("code")));
			tempCode.setDescription(StringUtil.trim(rs.getString("description")));
			if(tempCode.getDescription().equals("default"))
			{
				tempCode.setDescription("");
				
			}
			if(rs.getString("subCode") != null && !StringUtil.trim(rs.getString("subCode")).equals(""))
			{
				tempCode.setSubCode(StringUtil.trim(rs.getString("subCode")));
				
			}
			
			return tempCode;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List <Code> getMaritalActualStatuses()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT stat as code, descr as description, '' as subCode FROM BTHR_MARITAL_ACTUAL_STATUS");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		List <Code> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new CodeMapper());
		
		return result;	
	}

	
//	private static HashMap<String, Integer> approverIds = new HashMap<String, Integer>();
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public HashMap<String, Integer> getApproverIds() 
//	{
//		StringBuilder demoSql = new StringBuilder();
//		demoSql.append("SELECT grp_name, apprvr_usr_id FROM BHR_EAP_DEMO_ASSGN_GRP");
//		
//		StringBuilder paySql = new StringBuilder();
//		paySql.append("SELECT grp_name, apprvr_usr_id FROM BHR_EAP_PAY_ASSGN_GRP");
//		MapSqlParameterSource params = new MapSqlParameterSource();
//		
//		HashMap<String, Integer> result;
//		
//		try
//		{
//			getNamedParameterJdbcTemplate().query(demoSql.toString(), params, new ApproverIdMapper());
//			getNamedParameterJdbcTemplate().query(paySql.toString(), params, new ApproverIdMapper());
//		}
//		catch(EmptyResultDataAccessException e)
//		{
//			result = new HashMap<String, Integer> ();
//		}
//		return approverIds;
//	}
//	
//	private static class ApproverIdMapper implements RowMapper
//	{
//		public Boolean mapRow(ResultSet rs, int rows) throws SQLException
//		{
//			if( !approverIds.containsKey(rs.getString("grp_name")) )
//			{
//				approverIds.put(rs.getString("grp_name"), rs.getInt("apprvr_usr_id"));
//			}
//			return true;
//		}
//	}
}
