package com.esc20.nonDBModels;

public class Page {

	private Integer currentPage;
	private Integer perPageRows;
	private Integer totalRows;
	private Integer totalPages;
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPerPageRows() {
		return perPageRows;
	}
	public void setPerPageRows(Integer perPageRows) {
		this.perPageRows = perPageRows;
	}
	public Integer getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	public Integer getTotalPages() {
		return totalPages;
		//Math.ceil(totalRows/perPageRows)
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	 
	 
}
