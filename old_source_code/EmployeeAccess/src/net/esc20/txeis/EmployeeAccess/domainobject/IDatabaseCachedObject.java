package net.esc20.txeis.EmployeeAccess.domainobject;

public interface IDatabaseCachedObject<T> {
	public T getItem();
	public boolean isReady();
}
