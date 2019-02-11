/**
 * 
 */
package net.esc20.txeis.EmployeeAccess.domainobject.report;

/**
 * @author bbeverly
 *
 */
public class ReportFilter {
	
	private String column;
	private String value;
	private String logicalOperator;
	private String comparator;
	
	
	/**
	 * @return the column
	 */
	public String getColumn() {
		return column;
	}
	/**
	 * @param column the column to set
	 */
	public void setColumn(String column) {
		this.column = column;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the logicalOperator
	 */
	public String getLogicalOperator() {
		return logicalOperator;
	}
	/**
	 * @param logicalOperator the logicalOperator to set
	 */
	public void setLogicalOperator(String logicalOperator) {
		this.logicalOperator = logicalOperator;
	}
	/**
	 * @return the comparator
	 */
	public String getComparator() {
		return comparator;
	}
	/**
	 * @param comparator the comparator to set
	 */
	public void setComparator(String comparator) {
		this.comparator = comparator;
	}
}
