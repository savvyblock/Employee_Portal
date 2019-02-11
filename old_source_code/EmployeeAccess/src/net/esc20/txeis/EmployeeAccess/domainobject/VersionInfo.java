package net.esc20.txeis.EmployeeAccess.domainobject;

public final class VersionInfo {

	private final String _versionNumber;
	private final String _buildNumber;
	private final boolean _showBuildNumber;
	
	public VersionInfo(String pVersionNumber, String pBuildNumber, boolean pShowBuildNumber) {
		_versionNumber = pVersionNumber;
		_buildNumber = pBuildNumber;
		_showBuildNumber = pShowBuildNumber;
	}
	
	public String getVersionNumber() {
		return _versionNumber;
	}
	
	public String getBuildNumber() {
		return _buildNumber;
	}
	
	public boolean isShowBuildNumber() {
		return _showBuildNumber;
	}
}
