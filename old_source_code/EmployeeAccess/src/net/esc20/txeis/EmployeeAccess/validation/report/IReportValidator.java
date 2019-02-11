package net.esc20.txeis.EmployeeAccess.validation.report;

import net.esc20.txeis.EmployeeAccess.domainobject.report.ParameterReport;

import org.springframework.validation.Errors;

public interface IReportValidator {

	public void validate(ParameterReport report, Errors errors);
	
}
