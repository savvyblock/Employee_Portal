package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.esc20.txeis.EmployeeAccess.dao.api.IUserDao;
import net.esc20.txeis.EmployeeAccess.domainobject.District;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.User.Gender;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;

public class UserDao extends NamedParameterJdbcDaoSupport implements IUserDao
{	
	private static Log log = LogFactory.getLog(PayDao.class);
	
	@Override
	public User retrieveEmployee(String employeeNumber) 
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT emp_nbr, dob, addr_zip, addr_zip4, ");
			sql.append("addr_nbr, addr_str, addr_apt, addr_city, addr_st, ");
			sql.append("name_f, name_l, name_m, name_gen, ");
			sql.append("gen_descr, ");   //jf20150113 Display description instead of code fix
			sql.append("email, hm_email, staff_id, ");
			sql.append("sex, phone_area, phone_nbr ");
			sql.append("FROM bhr_emp_demo ");
			sql.append(" , et_c012_gen ");   //jf20150113 Display description instead of code fix
			sql.append("WHERE bhr_emp_demo.emp_nbr=:is_emp_nbr");
			sql.append("  AND name_gen = gen_cd ");   //jf20150113 Display description instead of code fix
			sql.append("  AND sch_yr = (select max(sch_yr) from ET_C012_GEN)");   //jf20150113 Display description instead of code fix
	
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("is_emp_nbr", employeeNumber);
	
			User result = (User)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new FullUserMapper());
			result.setEmployer(getDistrict());
			
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("Employee not found.");
			return null;
		}
	}
	
	@Override
	public District getDistrict()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT dist_name, str_nbr_dist, str_name_dist, ");
		sql.append("city_name_dist, state_cd, zip_dist, zip4_dist, area_cd_dist, phone_nbr_dist ");
		sql.append(" FROM dr_demo ");
		sql.append(" WHERE sch_yr = (SELECT MAX(sch_yr) from dr_demo)");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		District d = (District)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new ParameterizedRowMapper<District>()
		{
			@Override
			public District mapRow(ResultSet rs, int row)
					throws SQLException {
				District d = new District();
				
				d.setAddress(StringUtil.trim(rs.getString("str_nbr_dist")) + " " + StringUtil.trim(rs.getString("str_name_dist")));
				d.setName(StringUtil.trim(rs.getString("dist_name")));
				d.setCity(StringUtil.trim(rs.getString("city_name_dist")));
				d.setState(StringUtil.trim(rs.getString("state_cd")));
				d.setZip(StringUtil.trim(rs.getString("zip_dist")));
				d.setZip4(StringUtil.trim(rs.getString("zip4_dist")));
				d.setPhone(StringUtil.trim(rs.getString("area_cd_dist")) + StringUtil.trim(rs.getString("phone_nbr_dist")));
				
				return d;
			}
		});
		
		String ein = (String)getNamedParameterJdbcTemplate().queryForObject("SELECT DIST_EIN FROM BFN_OPTIONS WHERE GL_FILE_ID='C'", new MapSqlParameterSource(), String.class);  //jf20150115 get the most current ein
		d.setNumber(ein);
		
		return d;
	}
	
	@Override
	public User retrieveEmployee(String employeeNumber, String dob, String zip) 
	{
			try{
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT bhr_emp_demo.emp_nbr, bhr_emp_demo.dob, bhr_emp_demo.addr_zip, bhr_emp_demo.name_f, bhr_emp_demo.name_l, bhr_emp_demo.email, bhr_emp_demo.hm_email, bhr_emp_demo.staff_id "); 
				sql.append("FROM bhr_emp_demo ");
				sql.append("WHERE bhr_emp_demo.emp_nbr=:is_emp_nbr AND bhr_emp_demo.dob=:is_dob AND bhr_emp_demo.addr_zip=:is_addr_zip");
		
				MapSqlParameterSource params = new MapSqlParameterSource();
				
				params.addValue("is_emp_nbr", employeeNumber);
				params.addValue("is_dob", dob);
				params.addValue("is_addr_zip", zip);
		
				User result = (User)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new NewUserMapper());
			
				return result;
			}
			catch(EmptyResultDataAccessException e)
			{
				log.info("Employee not found.");
				return null;
			}
	}
	
	@Override
	public User retrieveExistingUserByEmpData(String employeeNumber, String dob, String zip)
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT bea_users.emp_nbr, bea_users.usrname, bea_users.hint, bea_users.hint_ans, bhr_emp_demo.dob, bhr_emp_demo.addr_zip, bhr_emp_demo.name_f, bhr_emp_demo.name_l, bhr_emp_demo.email, bhr_emp_demo.hm_email "); 
			sql.append("FROM bea_users ");
			sql.append("LEFT JOIN bhr_emp_demo ON ");
			sql.append("bea_users.emp_nbr = bhr_emp_demo.emp_nbr ");
			sql.append("WHERE bhr_emp_demo.emp_nbr=:is_emp_nbr AND bhr_emp_demo.dob=:is_dob AND bhr_emp_demo.addr_zip=:is_addr_zip");
	
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("is_emp_nbr", employeeNumber);
			params.addValue("is_dob", dob);
			params.addValue("is_addr_zip", zip);
	
			User result = (User)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new ExistingUserMapper());
		
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("User not found.");
			return null;
		}
	}
	
	@Override
	public User retrieveExistingUser(String userName)
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT bea_users.emp_nbr, bea_users.usrname, bea_users.hint, bea_users.hint_ans, bhr_emp_demo.dob, bhr_emp_demo.addr_zip, bhr_emp_demo.name_f, bhr_emp_demo.name_l, bhr_emp_demo.email, bhr_emp_demo.hm_email "); 
			sql.append("FROM bea_users ");
			sql.append("LEFT JOIN bhr_emp_demo ON ");
			sql.append("bea_users.emp_nbr = bhr_emp_demo.emp_nbr ");
			sql.append("WHERE usrname=:is_username");
	
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("is_username", userName);
	
			User result = (User)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new ExistingUserMapper());
		
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("User not found.");
			return null;
		}
	}
	
	@Override
	public User retrieveExistingUserByEmpNbr(String employeeNumber)
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT bea_users.emp_nbr, bea_users.usrname, bea_users.hint, bea_users.hint_ans, bhr_emp_demo.dob, bhr_emp_demo.addr_zip, bhr_emp_demo.name_f, bhr_emp_demo.name_l, bhr_emp_demo.email, bhr_emp_demo.hm_email "); 
			sql.append("FROM bea_users ");
			sql.append("LEFT JOIN bhr_emp_demo ON ");
			sql.append("bea_users.emp_nbr = bhr_emp_demo.emp_nbr ");
			sql.append("WHERE bea_users.emp_nbr=:is_emp_nbr");
	
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("is_emp_nbr", employeeNumber);
	
			User result = (User)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new ExistingUserMapper());
		
			return result;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("User not found.");
			return null;
		}
	}
	
	@Override
	public void insertUser(User user)
	{
		StringBuilder sql = new StringBuilder();
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		sql.append("INSERT INTO bea_users (emp_nbr, usrname, usrpswd, hint, hint_ans, lk_pswd, pswd_cnt, lk_fnl, tmp_cnt) ");
		sql.append("VALUES (:is_emp_nbr, :is_username, :is_userpass, :is_hint, :is_hint_ans, 'N', '0', 'N', '0' )");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("is_emp_nbr", user.getEmployeeNumber());
		params.addValue("is_username", user.getUserName().toUpperCase());
		params.addValue("is_userpass", encoder.encodePassword((user.getPassword()), null));
		params.addValue("is_hint", user.getHint());
		params.addValue("is_hint_ans", encoder.encodePassword(user.getHintAnswer(),null));
		
		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}
	
	@Override
	public void updateEmail(User user) {
		
		StringBuilder sql = new StringBuilder();
		
		//Timestamp currentTime = new Timestamp (System.currentTimeMillis());
		//ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		
		sql.append("UPDATE bhr_emp_demo SET  bhr_emp_demo.email= :is_email, bhr_emp_demo.hm_email = :is_hm_email ");
		sql.append("WHERE bhr_emp_demo.emp_nbr=:is_emp_nbr AND bhr_emp_demo.dob=:is_dob AND bhr_emp_demo.addr_zip=:is_addr_zip");	
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("is_email", user.getWorkEmail());
		params.addValue("is_hm_email", user.getHomeEmail());
		params.addValue("is_emp_nbr", user.getEmployeeNumber());
		params.addValue("is_dob", user.getDateOfBirth());	
		params.addValue("is_addr_zip", user.getZipCode());
		
		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}
	
	@Override
	public void deleteUser(User user)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM bea_users ");
		sql.append("WHERE (emp_nbr = :is_emp_nbr AND usrname = :is_username)");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("is_emp_nbr", user.getEmployeeNumber());
		params.addValue("is_username", user.getUserName());
		
		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}
	
	@Override
	public String employeeRetrievalMethod()
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id_typ "); 
			sql.append("FROM bhr_eap_opt");
	
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String method = (String)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);
		
			return method;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("User not found.");
			return null;
		}
	}
	
	@Override
	public String retrieveEmpNbrBySSN(String ssn)
	{
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT emp_nbr "); 
			sql.append("FROM bhr_emp_demo ");
			sql.append("WHERE staff_id = :is_ssn");
	
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("is_ssn", ssn);
			
			String method = (String)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);
		
			return method;
		}
		catch(EmptyResultDataAccessException e)
		{
			log.info("User not found.");
			return "";
		}
	}
	
	private static class FullUserMapper implements RowMapper
	{
		public User mapRow(ResultSet rs, int rows) throws SQLException
		{
			User tempUser = new User();
			
			tempUser.setEmployeeNumber(StringUtil.trim(rs.getString("emp_nbr")));
			tempUser.setSsn(StringUtil.trim(rs.getString("staff_id")));
			tempUser.setDateOfBirth(StringUtil.trim(rs.getString("dob")));
			tempUser.setAddress(StringUtil.trim(rs.getString("addr_nbr")) + " " + StringUtil.trim(rs.getString("addr_str")));
			String apt = StringUtil.trim(rs.getString("addr_apt"));
			if(apt.length() > 0)
			{
				tempUser.setAddress(tempUser.getAddress() + " " + apt);
			}
			tempUser.setCity(StringUtil.trim(rs.getString("addr_city")));
			tempUser.setState(StringUtil.trim(rs.getString("addr_st")));
			tempUser.setZipCode(StringUtil.trim(rs.getString("addr_zip")));
			tempUser.setZipCode4(StringUtil.trim(rs.getString("addr_zip4")));

			String fName = StringUtil.trim(rs.getString("name_f"));
			tempUser.setFirstName(fName);
			
			String mName = StringUtil.trim(rs.getString("name_m"));
			tempUser.setMiddleName(mName);
			
			String lName = StringUtil.trim(rs.getString("name_l"));
			tempUser.setLastName(lName);
			
			String gen = StringUtil.trim(rs.getString("name_gen"));
			tempUser.setGeneration(gen);
			
			//jf20150113 Display description instead of code fix
			String genDescr = StringUtil.trim(rs.getString("gen_descr"));
			if (gen.equals("")) {
				tempUser.setGenerationDescr("");
			} else {
				tempUser.setGenerationDescr(genDescr);
			}
			
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
			
			try
			{
				tempUser.setGender(Gender.getGender(StringUtil.trim(rs.getString("sex"))));
			}
			catch(IllegalArgumentException ex)
			{
				log.error("Invalid gender in data");
			}
			
			String pA = StringUtil.trim(rs.getString("phone_area"));
			String pN = StringUtil.trim(rs.getString("phone_nbr"));
			
			if(pA.length() > 0 && pN.length() == 7 )
			{
				tempUser.setPhoneNumber(pA + "-" + pN.substring(0,3) + "-" + pN.substring(3,pN.length()));
			}

			return tempUser;
		}
	}
	
	private static class NewUserMapper implements RowMapper
	{
		public User mapRow(ResultSet rs, int rows) throws SQLException
		{

			User tempUser = new User();
			
			tempUser.setEmployeeNumber(StringUtil.trim(rs.getString("emp_nbr")));
			tempUser.setSsn(StringUtil.trim(rs.getString("staff_id")));
			tempUser.setDateOfBirth(StringUtil.trim(rs.getString("dob")));
			tempUser.setZipCode(StringUtil.trim(rs.getString("addr_zip")));
			tempUser.setFirstName(StringUtil.trim(rs.getString("name_f")));
			tempUser.setLastName(StringUtil.trim(rs.getString("name_l")));
			if(rs.getString("email") != null && !"".equals(rs.getString("email")))
			{
				tempUser.setWorkEmail(StringUtil.trim(rs.getString("email")));
				tempUser.setWorkEmailVerification(tempUser.getWorkEmail());
				tempUser.setWorkEmailEmpty(false);
			}
			if(rs.getString("hm_email") != null && !"".equals(rs.getString("hm_email")))
			{
				tempUser.setHomeEmail(StringUtil.trim(rs.getString("hm_email")));
				tempUser.setHomeEmailVerification(tempUser.getHomeEmail());
				tempUser.setHomeEmailEmpty(false);
			}
			
			return tempUser;
		}
	}
	
	private static class ExistingUserMapper implements RowMapper
	{
		public User mapRow(ResultSet rs, int rows) throws SQLException
		{
			User tempUser = new User();
			
			tempUser.setEmployeeNumber(StringUtil.trim(rs.getString("emp_nbr")));
			tempUser.setUserName(StringUtil.trim(rs.getString("usrname")));
			tempUser.setHint(StringUtil.trim(rs.getString("hint")));
			tempUser.setHintAnswer(StringUtil.trim(rs.getString("hint_ans")));
			tempUser.setDateOfBirth(StringUtil.trim(rs.getString("dob")));
			tempUser.setFirstName(StringUtil.trim(rs.getString("name_f")));
			tempUser.setLastName(StringUtil.trim(rs.getString("name_l")));
			
			if(rs.getString("addr_zip") != null)
			{
				tempUser.setZipCode(StringUtil.trim(rs.getString("addr_zip")));
			}
			if(rs.getString("email") != null)
			{
				tempUser.setWorkEmail(StringUtil.trim(rs.getString("email")));
				tempUser.setWorkEmailEmpty(false);
			}
			if(rs.getString("hm_email") != null)
			{
				tempUser.setHomeEmail(StringUtil.trim(rs.getString("hm_email")));
				tempUser.setHomeEmailEmpty(false);
			}
			return tempUser;
		}
	}

}
