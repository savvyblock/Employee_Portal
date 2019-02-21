package com.esc20.nonDBModels;

public class SearchCriteria {

	public Criteria criteria;
	public Page page;
	public Criteria getCriteria() {
		return criteria;
	}
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}

	
}
