package net.esc20.txeis.EmployeeAccess.util;

import java.util.Calendar;
import java.util.Date;

public class AgeUtil {
	
	public static int getAgeInYears(Date dateOfBirth, Date dateOfCompare) {
		if (dateOfBirth == null) {
			return 0;
		} else {
			Calendar compareDate = Calendar.getInstance();
			Calendar birthDate = Calendar.getInstance();

			// Set the date of birth into a Calendar
			birthDate.setTime(dateOfBirth);
			compareDate.setTime(dateOfCompare);

			// Get age based on year
			int age = compareDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

			// Add the tentative age to the date of birth to get this year's birthday
			birthDate.add(Calendar.YEAR, age);

			// If this year's birthday has not happened yet, subtract one from age
			if (compareDate.before(birthDate)) {
				age--;
			}

			return age;
		}
	}
	
	public static int getAgeInMonths(Date dateOfBirth, Date dateOfCompare) {
		if (dateOfBirth == null) {
			return 0;
		} else {
			// get the age in years			
			Calendar compareDate = Calendar.getInstance();
			Calendar birthDate = Calendar.getInstance();

			// Set the date of birth into a Calendar
			birthDate.setTime(dateOfBirth);
			compareDate.setTime(dateOfCompare);

			// Get age based on year
			int ageInYears = compareDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

			// Add the tentative age to the date of birth to get this year's birthday
			birthDate.add(Calendar.YEAR, ageInYears);

			int ageInMonths =  0;
			// If this year's birthday has not happened yet, subtract one from age
			// Also determine how many months there are between the birth day and the current date
			if (compareDate.before(birthDate)) {
				ageInMonths = birthDate.get(Calendar.MONTH) - compareDate.get(Calendar.MONTH);
				ageInYears--;
			} else {
				ageInMonths = compareDate.get(Calendar.MONTH) - birthDate.get(Calendar.MONTH);				
			}
			
			ageInMonths += (ageInYears * 12);
			
			return ageInMonths;
		}
	}
}
