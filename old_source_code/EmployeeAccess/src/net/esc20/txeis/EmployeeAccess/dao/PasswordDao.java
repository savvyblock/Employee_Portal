package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.Timestamp;

import net.esc20.txeis.EmployeeAccess.dao.api.IPasswordDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;

public class PasswordDao extends NamedParameterJdbcDaoSupport implements IPasswordDao {

	private static Log log = LogFactory.getLog(PasswordDao.class);
	
	@Override
	public void updatePassword(int tempCount, String userName, String password) {
		
		StringBuilder sql = new StringBuilder();
		
		Timestamp currentTime = new Timestamp (System.currentTimeMillis());
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		
		sql.append("UPDATE bea_users SET  bea_users.usrpswd= :is_userpass, bea_users.lk_pswd='N', bea_users.lk_hint ='N', ");
		sql.append("bea_users.pswd_cnt = '0', bea_users.hint_cnt = '0', bea_users.tmp_cnt = :is_temp_count, ");
		if(tempCount == 3)
		{
			sql.append("bea_users.lk_fnl = 'Y', ");
		}
		else
		{
			sql.append("bea_users.lk_fnl = 'N', ");
		}
		sql.append("bea_users.tmp_dts =:is_currentTime where bea_users.usrname= :is_username");	
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("is_currentTime", currentTime);
		params.addValue("is_userpass", encoder.encodePassword(password,null));
		params.addValue("is_temp_count", tempCount);
		params.addValue("is_username", userName);		
		
		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}
	
	@Override
	public void updateHintCount(int hintCount, String userName) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE bea_users SET  bea_users.hint_cnt = :is_hint_count, ");	
		if(hintCount == 3)
		{
			sql.append("bea_users.lk_hint = 'Y' ");
		}
		else
		{
			sql.append("bea_users.lk_hint = 'N' ");
		}
		sql.append("WHERE bea_users.usrname= :is_username");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("is_hint_count",hintCount);	
		params.addValue("is_username", userName);	
		
		getNamedParameterJdbcTemplate().update(sql.toString(), params);
		
	}
	
	@Override
	public int retrieveTempCount(String userName) {
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT bea_users.tmp_cnt FROM bea_users WHERE bea_users.usrname= :is_username");  
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("is_username", userName);
	
			Integer result = (Integer)getNamedParameterJdbcTemplate().queryForInt(sql.toString(), params);
			
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("User not found.");
			return -1;
		}
	}
	
	@Override
	public int retrieveHintCount(String userName) {
		
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT  bea_users.hint_cnt FROM bea_users where bea_users.usrname= :is_username");  
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("is_username", userName);
	
			Integer result = (Integer)getNamedParameterJdbcTemplate().queryForInt(sql.toString(), params);
			
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("User not found.");
			return -1;
		}
	}
	
	@Override
	public String retrieveHintLock(String userName)
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT lk_hint "); 
			sql.append("FROM bea_users ");
			sql.append("WHERE usrname=:is_username");
	
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("is_username", userName);
			
			String lock = (String)getNamedParameterJdbcTemplate().queryForObject (sql.toString(), params, String.class);
		
			return lock;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("User not found.");
			return "";
		}
	}
	
	@Override
	public String retrievePassword(String userName)
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT usrpswd "); 
			sql.append("FROM bea_users ");
			sql.append("WHERE usrname=:is_username");
	
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("is_username", userName);
			
			String pass = (String)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);
		
			return pass;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("User not found.");
			return null;
		}
	}
	
	@Override
	public void resetLocks(String userName) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("UPDATE bea_users SET  bea_users.lk_pswd='N', bea_users.lk_hint ='N', bea_users.lk_fnl='N', ");
		sql.append("bea_users.pswd_cnt = '0', bea_users.hint_cnt = '0', bea_users.tmp_cnt = '0', ");
		sql.append("bea_users.tmp_dts = '' where bea_users.usrname= :is_username");	
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("is_username", userName);		
		
		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

}
