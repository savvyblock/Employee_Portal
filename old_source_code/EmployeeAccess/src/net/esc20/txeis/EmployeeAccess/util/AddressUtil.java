package net.esc20.txeis.EmployeeAccess.util;


public class AddressUtil {
	
	public static String formatStreet(String streetNumber, String streetName, String apartmentNumber) {
		String result = "";
		
		if (!StringUtil.isNullOrEmpty(streetNumber)) {
			result = streetNumber.trim();
		}
		
		if (!StringUtil.isNullOrEmpty(streetName)) {
			if (result.length() > 0) {
				result += " ";
			}
			
			result += streetName;
		}
		
		if (!StringUtil.isNullOrEmpty(apartmentNumber)) {
			if (result.length() > 0) {
				result += " ";
			}
			
			result += apartmentNumber;
		}
		
		return result;
	}
	
	public static String formatCityState(String city, String state, String zip, String zip4) {
		String result = city + ", " + state + " " + zip; 
		
		if (!StringUtil.isNullOrEmpty(zip4)) {
			result += "-" + zip4;
		}
		
		return result;
	}	
}
