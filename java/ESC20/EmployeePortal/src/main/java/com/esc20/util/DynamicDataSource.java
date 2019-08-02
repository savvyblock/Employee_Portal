package com.esc20.util;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

public class DynamicDataSource extends AbstractRoutingDataSource {
	
	private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
	
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSourceType();
	}
	
	@Override
	protected DataSource determineTargetDataSource() {
		String lookupKey = (String)DataSourceContextHolder.getDataSourceType();
		DataSource dataSource = null;
		try {
			if (dataSource == null && (lookupKey == null)) {
				dataSource = (DataSource)dataSourceLookup.getDataSource("java:jboss/TxeisDB");
				return dataSource;
			}else {
				if(lookupKey != null)
					dataSource = (DataSource)dataSourceLookup.getDataSource(lookupKey);

				if (dataSource == null) {
					throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
				}
				return dataSource;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}