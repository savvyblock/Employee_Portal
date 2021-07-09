package com.esc20.nonDBModels;

public class ResultUtil {

	public static Double getDouble(Object o) {
		try {
			return Double.parseDouble(o.toString());
		} catch (Exception ex) {
			return 0D;
		}
	}

	public static Integer getInt(Object o) {
		try {
			return Integer.parseInt(o.toString());
		} catch (Exception ex) {
			return 0;
		}
	}

	public static String getString(Object o) {
		try {
			return o.toString();
		} catch (Exception ex) {
			return "";
		}
	}

	public static Byte getByte(Object o) {
		try {
			return Byte.parseByte(o.toString());
		} catch (Exception ex) {
			return null;
		}
	}

	public static Boolean getBoolean(Object o) {
		try {
			String param = o.toString();
			if (o instanceof Integer || o instanceof Short) {
				if (Integer.parseInt(param) == 1) {
					param = "true";
				} else if (Integer.parseInt(param) == 0) {
					param = "false";
				}
			}
			return Boolean.parseBoolean(param);
		} catch (Exception ex) {
			return Boolean.FALSE;
		}
	}
}
