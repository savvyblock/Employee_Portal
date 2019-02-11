package net.esc20.txeis.EmployeeAccess.domainobject.report;

import net.sf.jasperreports.engine.JRDataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReportParameterDataSource extends ReportParameter {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	JRDataSource dataSource;

	public void setDataSource(JRDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public JRDataSource getDataSource() {
		return dataSource;
	}
		
}
