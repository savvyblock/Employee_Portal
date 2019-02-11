package net.esc20.txeis.EmployeeAccess.domainobject;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class JndiRoutingDataSource extends AbstractRoutingDataSource {

	DatabaseCachedObject<DataSource> _factory;
	
	public void setDataSourceFactory(DatabaseCachedObject<DataSource> pFactory) {
		_factory = pFactory;
	}
	
	// Overridden to disable AbstractRoutingDataSource's check for the
	// targetDataSources property, which is no longer used.
	@Override
	public void afterPropertiesSet() {	
	}

	// Not used in favor of determinTargetDataSource, which already takes into account
	// the current database ID.
	@Override
	protected Object determineCurrentLookupKey() {
		return null;
	}

	@Override
	protected DataSource determineTargetDataSource() { 
		return _factory.getItem();
	}

	public Logger getParentLogger() {
		// TODO Auto-generated method stub
		return null;
	}
}
