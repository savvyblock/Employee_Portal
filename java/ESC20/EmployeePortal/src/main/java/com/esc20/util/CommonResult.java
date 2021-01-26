package com.esc20.util;

import java.io.Serializable;

public class CommonResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private String message;
	private Object data;
	
	public CommonResult() {
		super();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public CommonResult(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public CommonResult(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
