package com.esc20.nonDBModels.report;

public interface IDistrictCachedObject<T> {
	public T getItem();
	public boolean isReady();
}
