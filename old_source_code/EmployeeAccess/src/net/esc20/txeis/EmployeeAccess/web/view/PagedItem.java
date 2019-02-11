package net.esc20.txeis.EmployeeAccess.web.view;

import java.io.Serializable;

public class PagedItem<T> implements Serializable {

	private static final long serialVersionUID = 5582221240882842566L;
	
	private final T item;
	private final int actualIndex;
	
	public PagedItem(T item, int actualIndex) {
		this.item = item;
		this.actualIndex = actualIndex;
	}

	public T getItem() {
		return item;
	}

	public int getActualIndex() {
		return actualIndex;
	}
}
