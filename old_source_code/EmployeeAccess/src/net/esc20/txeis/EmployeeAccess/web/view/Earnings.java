package net.esc20.txeis.EmployeeAccess.web.view;

import java.io.Serializable;

import net.esc20.txeis.EmployeeAccess.domainobject.PayDate;

public class Earnings implements Serializable
{
	private static final long serialVersionUID = 8222009316886080791L;
	
	private PayDate payDate;
	
	public PayDate getPayDate() {
		return payDate;
	}

	public void setPayDate(PayDate payDate) {
		this.payDate = payDate;
	}
}