package com.esc20.util;

import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;

public class DynamicDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSourceType();
	}
	
	@Override
	protected DataSource determineTargetDataSource() {
		Object lookupKey = DataSourceContextHolder.getDataSourceType();
		Context ic = null;
		DataSource dataSource = null;
		try {
			if (dataSource == null && (lookupKey == null)) {
				ic = new InitialContext();
				dataSource = (DataSource)ic.lookup("java:jboss/DB001901");
				return dataSource;
			}else {
				ic = new InitialContext();
				if(lookupKey != null)
					dataSource = (DataSource)ic.lookup((String) lookupKey);

				if (dataSource == null) {
					throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
				}
				return dataSource;
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
}