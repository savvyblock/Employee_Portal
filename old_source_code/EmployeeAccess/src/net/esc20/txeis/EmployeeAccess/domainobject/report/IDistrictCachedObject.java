package net.esc20.txeis.EmployeeAccess.domainobject.report;

public interface IDistrictCachedObject<T> {
	public T getItem();
	public boolean isReady();
}
