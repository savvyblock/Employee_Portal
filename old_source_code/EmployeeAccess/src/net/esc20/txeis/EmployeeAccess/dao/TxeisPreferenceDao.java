package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.dao.api.ITxeisPreferenceDAO;
import net.esc20.txeis.EmployeeAccess.domainobject.TxeisPreference;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public class TxeisPreferenceDao extends NamedParameterJdbcDaoSupport implements
		ITxeisPreferenceDAO {

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, String> getPreferences() {
		HashMap<String,String> txpref = new HashMap<String,String>();
		
		StringBuilder query = new StringBuilder("select PREF_NAME, trim(PREF_VALUE) as PREF_VALUE from TXEIS_PREFERENCES");
		MapSqlParameterSource params = new MapSqlParameterSource();
		List<TxeisPreference> _preflist = null;
		try {
			_preflist = getNamedParameterJdbcTemplate().query(query.toString(), params, new preferencesMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			 _preflist = null;
		}
		
		for(TxeisPreference pref : _preflist){
			txpref.put(pref.getPrefname(),pref.getPrefvalue());
		}
		return txpref;
	}
	
	private static class preferencesMapper implements RowMapper {
	
		@Override
		public TxeisPreference mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new TxeisPreference(rs.getString("PREF_NAME"), rs.getString("PREF_VALUE"));
		}
	}
}
