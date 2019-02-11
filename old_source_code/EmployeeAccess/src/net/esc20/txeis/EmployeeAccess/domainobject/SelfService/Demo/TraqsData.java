package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

public class TraqsData
{
	private final String automate;
	private final Integer trsReportYear;
	private final Integer trsReportMonth;
	public TraqsData( String automate, Integer trsReportYear, Integer trsReportMonth)
	{
		this.automate = automate;
		this.trsReportYear = trsReportYear;
		this.trsReportMonth = trsReportMonth;
	}
	
	public String getAutomate() {
		return automate;
	}
	public Integer getTrsReportYear() {
		return trsReportYear;
	}
	public Integer getTrsReportMonth() {
		return trsReportMonth;
	}
}