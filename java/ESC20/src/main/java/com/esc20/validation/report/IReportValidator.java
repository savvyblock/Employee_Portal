package com.esc20.validation.report;


import org.springframework.validation.Errors;

import com.esc20.nonDBModels.report.ParameterReport;

public interface IReportValidator {

	public void validate(ParameterReport report, Errors errors);
	
}
