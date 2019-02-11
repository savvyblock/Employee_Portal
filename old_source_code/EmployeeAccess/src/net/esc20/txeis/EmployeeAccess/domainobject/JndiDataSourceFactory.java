package net.esc20.txeis.EmployeeAccess.domainobject;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jndi.JndiTemplate;
import org.springframework.ldap.NameNotFoundException;

public class JndiDataSourceFactory extends DatabaseCachedObject<DataSource> {

	private String _jndiFormatString;
	
	public String getJndiFormatString() {
		return _jndiFormatString;
	}
	
	public void setJndiFormatString(String pValue) {
		_jndiFormatString = pValue;
	}
	
	@Override
	protected DataSource createItem(String database) {
		String jndiName = getJndiName(database);
		try {
			return (DataSource)new JndiTemplate().lookup(jndiName, DataSource.class);
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Can not find database: " + jndiName);
		} catch (NamingException e) {
			throw new RuntimeException("Can not find database: " + jndiName);
		}
	}
	
	private String getJndiName(String database) {
		return String.format(_jndiFormatString, database);
	}
	
}
