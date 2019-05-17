package com.esc20.nonDBModels.report;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReportParameterConnection extends ReportParameter {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
