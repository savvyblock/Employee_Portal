package com.esc20.nonDBModels;

public enum AuthenticationResponseType {
	Success,
    Failure,
    Locked,
    WillLocked,
    Inactive,
    Expired,
    BadPassword
}
