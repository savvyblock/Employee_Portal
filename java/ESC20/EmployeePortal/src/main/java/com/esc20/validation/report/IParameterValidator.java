package com.esc20.validation.report;


import org.springframework.validation.Errors;

import com.esc20.nonDBModels.report.ReportParameter;

public interface IParameterValidator {
	
	public void validate(ReportParameter parameter, Errors errors, String parameterName);

}
