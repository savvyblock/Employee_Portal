package net.esc20.txeis.EmployeeAccess.util;

public class GradeUtil {
	public static int convertGradeLevel(String gradeLevel) throws NumberFormatException
	{
		if("EX".equals(gradeLevel))
		{
			return -3;
		}
		else if("EE".equals(gradeLevel))
		{
			return -2;
		}
		else if("PK".equals(gradeLevel))
		{
			return -1;
		}
		else if("KG".equals(gradeLevel))
		{
			return 0;
		}
		else
		{
			return Integer.parseInt(gradeLevel);
		}
	}
	
	public static String convertGradeCode(int grade)
	{
		if(grade == -3)
		{
			return "EX";
		} 
		else if(grade == -2)
		{
			return "EE";
		}
		else if(grade == -1)
		{
			return "PK";
		}
		else if(grade == 0)
		{
			return "KG";
		}
		else
		{
			String gradeLevel = String.valueOf(grade);
			if(gradeLevel.length() == 1)
			{
				gradeLevel = "0" + gradeLevel;
			}
			
			return gradeLevel;
		}
	}
}
 