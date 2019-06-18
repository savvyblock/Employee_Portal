package com.esc20.nonDBModels.report;

public class SessionTimeoutPreferences {

	private static final int DEFAULT_SESSION_TIMEOUT = 20;
	private static final int OVERLAP_TIME = 1;
	
	public static SessionTimeoutPreferences create(String pTimeoutStringMinutes) {
		try {
			return new SessionTimeoutPreferences(Integer.parseInt(pTimeoutStringMinutes));
		}
		catch (NumberFormatException e) {
			return new SessionTimeoutPreferences(DEFAULT_SESSION_TIMEOUT);
		}
		
	}
	
	private int _sessionTimeoutMinutes;
	
	SessionTimeoutPreferences(int pSessionTimeoutMinutes) {
		_sessionTimeoutMinutes = pSessionTimeoutMinutes;
	}
	
	public int getAuthenticationTimeoutMinutes() {
		return _sessionTimeoutMinutes;
	}

	public int getSessionTimeoutMinutes() {
		return _sessionTimeoutMinutes + OVERLAP_TIME;
	}
}
