package net.esc20.txeis.EmployeeAccess.dao;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class UserDataSource extends BasicDataSource {

	public UserDataSource(DataSource pBase) {
		super.dataSource = pBase;
	}
}
