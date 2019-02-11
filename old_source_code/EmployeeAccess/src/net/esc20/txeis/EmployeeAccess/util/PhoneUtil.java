package net.esc20.txeis.EmployeeAccess.util;

public class PhoneUtil {
	public static String formatPhoneNumber(String areaCode, String number, String extension) {
		String formattedPhone = "";

		if (number.trim().length() == 7) {
			if (areaCode != null && areaCode.length() > 0) {
				formattedPhone = "(" + areaCode + ") ";
			}

			String first = number.substring(0, 3);
			String last = number.substring(3, 7);

			formattedPhone += first + "-" + last;

			if (extension != null && extension.length() > 0) {
				formattedPhone += " Ext " + extension;
			}
		}

		return formattedPhone;
	}
}
