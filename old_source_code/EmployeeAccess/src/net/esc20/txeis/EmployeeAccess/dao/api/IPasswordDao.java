package net.esc20.txeis.EmployeeAccess.dao.api;

public interface IPasswordDao {

	void updatePassword(int tempCount, String userName, String password);
	void updateHintCount(int hintCount, String userName);
	String retrieveHintLock(String userName);
	int retrieveTempCount(String userName);
	int retrieveHintCount(String userName);
	String retrievePassword(String userName);
	void resetLocks(String userName);
}
