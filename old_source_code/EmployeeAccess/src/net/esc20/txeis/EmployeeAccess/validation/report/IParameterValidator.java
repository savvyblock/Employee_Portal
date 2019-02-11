package net.esc20.txeis.EmployeeAccess.validation.report;

import net.esc20.txeis.EmployeeAccess.domainobject.report.ReportParameter;

import org.springframework.validation.Errors;

public interface IParameterValidator {
	
	public void validate(ReportParameter parameter, Errors errors, String parameterName);

}
