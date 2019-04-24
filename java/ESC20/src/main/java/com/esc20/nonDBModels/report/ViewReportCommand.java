package com.esc20.nonDBModels.report;

import java.util.List;

/**
 * @author dflint
 *
 */
public class ViewReportCommand {
	
	private int currentPage;
	private int lastPage;
	private List<ReportSort> sortCriteria;
	private List<ReportFilter> filterCriteria;
	private boolean errors;
	
	
	/**
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return
	 */
	public int getLastPage() {
		return lastPage;
	}

	/**
	 * @param lastPage
	 */
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	
	/**
	 * @return the filterCriteria
	 */
	public List<ReportFilter> getFilterCriteria() {
		return filterCriteria;
	}

	/**
	 * @param filterCriteria the filterCriteria to set
	 */
	public void setFilterCriteria(List<ReportFilter> filterCriteria) {
		this.filterCriteria = filterCriteria;
	}
	/**
	 * @return
	 */
	public boolean getErrors() {
		return errors;
	}

	/**
	 * @param errors
	 */
	public void setErrors(boolean errors) {
		this.errors = errors;
	}

	/**
	 * @return the sortCriteria
	 */
	public List<ReportSort> getSortCriteria() {
		return sortCriteria;
	}

	/**
	 * @param sortCriteria the sortCriteria to set
	 */
	public void setSortCriteria(List<ReportSort> sortCriteria) {
		this.sortCriteria = sortCriteria;
	}

}
