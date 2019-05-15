package com.esc20.nonDBModels.report;

import java.io.File;

import net.sf.jasperreports.engine.JRException;

public interface ICustomReport extends IReport {
	
	public File compileReport(String directoryPath) throws JRException;	
	
}
