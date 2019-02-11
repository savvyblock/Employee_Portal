package com.esc20.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String DEFAULT_DATE_FORMAT     = "MM/dd/yyyy";
    public static String DEFAULT_TIME_FORMAT     = "HH:mm:ss";
    public static String DEFAULT_DATETIME_FORMAT = "MM/dd/yyyy";
	
	static public String leftZeroPad(int count, String value) {
		if (value != null && value.length() < count && isInteger(value)) {
			return String.format("%0" + count + "d", Integer.parseInt(value)); // zero not oh
		} else {
			return value;
		}
	}
	
	public static boolean isInteger(String value)
	{
		boolean isValidate = true;

		try
		{

		Integer val = Integer.parseInt(value);
		}
		catch (NumberFormatException ex)
		{
			return false;
		}



	     return isValidate;


	}
	
	public static boolean isDouble(String input)
	{
		try
		{
			Double.parseDouble(input);
		}
		catch(NumberFormatException ex)
		{
			return false;
		}

		return true;
	}
	
	public static boolean isNullOrEmpty(String input)
	{
		if (input == null) {
			return true;
		}

		return isStringEmpty(input);
	}
	
	public static boolean isStringEmpty(String input)
	{
		if (input.trim().length() == 0)
		{
			return true;
		}

		return false;
	}
	
	static public String formatName(String firstName, String middleName, String lastName) {
		return formatName(firstName, middleName, lastName, null);
	}
	
	static public String formatName(String firstName, String middleName, String lastName, String generation) {
		String name = "";

		if (firstName != null && lastName != null && (firstName.trim().length() > 0 || lastName.trim().length() > 0)) {
			StringBuffer sb = new StringBuffer();

			if (firstName.trim().length() > 0 && lastName.trim().length() > 0) {
				sb.append(firstName.trim());
								
				if (middleName != null && middleName.length() > 0) {
					sb.append(" ");
					sb.append(middleName.trim());
				}
				
				sb.append(" ");
				sb.append(lastName.trim());
				
				if(generation != null && generation.trim().length() > 0)
				{
					sb.append(" " + generation);
				}
			} else if (firstName.trim().length() > 0) {
				sb.append(firstName.trim());
									
				if (middleName != null && middleName.length() > 0) {
					sb.append(" ");
					sb.append(middleName.trim());
				}					
			} else if (lastName.trim().length() > 0) {
				sb.append(lastName.trim());
				
				if(generation != null && generation.trim().length() > 0)
				{
					sb.append(" " + generation);
				}
			}				
			name = sb.toString();
		} else if (lastName != null && lastName.trim().length() > 0) {
			name = lastName;
		}
		
		return name;
	}

	public static final char[] MASKS = new char[]{'!', '#', '0', '@', 'A', 'X', 'a', 'x'};
	

    /**
     * Obtains a specified number of characters from the beginning of a string.
     * @param s   The string containing the characters you want
     * @param n	A long specifying the number of characters you want
     * @return String. Returns the leftmost n characters in string if
     * it succeeds and the empty string ("") if an error occurs.
     * If any argument's value is NULL, Left returns NULL.
     * If n is greater than or equal to the length of the string,
     * Left returns the entire string. It does not add spaces to make the
     * return value's length equal to n.
     */
    public static String left(String s, int n) {
        if (s == null)
            return null;
        else if (n <= 0)
            return "";
        else if (n >= s.length())
            return s;
        else
            return s.substring(0, n);
    }

    public static String padRight(String source, int length) {
    	if (source == null)
    		return null;
    	if (length <= source.length()) {
    		return source;
    	}
    	source = source + space(length - source.length());
    	return source;
    }
    
    public static String padLeft(String source, int length) {
    	if (source == null)
    		return null;
    	if (length <= source.length()) {
    		return source;
    	}
    	source = space(length - source.length()) + source;
    	return source;
    }
    
    /**
     * Obtains a specified number of characters from the end of a string.
     * @param s The string from which you want characters returned
     * @param n A long whose value is the number of characters you want returned
     * from the right end of string
     * @return String. Returns the rightmost n characters in string if
     * it succeeds and the empty string ("") if an error occurs.
     * If any argument's value is NULL, Right returns NULL. If n is greater than
     * or equal to the length of the string, Right returns the entire string.
     * It does not add spaces to make the return value's length equal to n.
     */
    public static String right(String s, int n) {
        if (s == null)
            return null;
        else if (n <= 0)
            return "";
        else if (n > s.length())
            return s;
        else
            return s.substring(s.length() - n);
    }

    /**
     * Obtains a specified number of characters from a specified position in a string.
     * @param s The string from which you want characters returned.
     * @param start A long specifying the position of the first character you
     * want returned. (The position of the first character of the string is 1).
     * @param length A long whose value is the number of characters you want
     * returned. If you do not enter length or if length is greater than
     * the number of characters to the right of start, Mid returns the remaining
     * characters in the string.
     * @return String. Returns characters specified in length of string starting
     * at character  start. If start is greater than the number of characters in
     * string, the Mid function returns the empty string (""). If length is greater
     * than the number of characters remaining after the start character,
     * Mid returns the remaining characters. The return string is not filled with
     * spaces to make it the specified length. If any argument's value is NULL,
     * Mid returns NULL.
     */
    public static String mid(String s, int start, int length) {
        if (s == null)
            return null;
        else if (start > s.length() || start <= 0 || length <= 0)
            return "";
        else if (start + length > s.length())
            return s.substring(start - 1); //java from zero
        else
            return s.substring(start - 1, start + length - 1);
    }

    /**
     * Obtains a specified number of characters from a specified position in a string.
     * @param s - The string from which you want characters returned.
     * @param start - A long specifying the position of the first character you want
     * returned. (The position of the first character of the string is 1).
     * @return String. Returns characters specified in length of string starting at
     * character  start. If start is greater than the number of characters in string,
     * the Mid function returns the empty string (""). If length is greater than the
     * number of characters remaining after the start character, Mid returns the
     * remaining characters. The return string is not filled with spaces to make it
     * the specified length. If any argument's value is NULL, Mid returns NULL.
     */
    public static String mid(String s, int start) {
        if (s == null)
            return null;
        else if (start > s.length() || start <= 0)
            return "";
        else
            return s.substring(start - 1); 
    }

    /**
     * Reports the length of a string or a blob.
     * @param s The string or blob for which you want the length in number of
     * characters or in number of bytes
     * @return a long whose value is the length of stringorblob if it succeeds and
     * -1 if an error occurs.
     */
    public static int len(String s) {
        return (s == null) ? 0 : s.length();
    }

    /**
     * Finds one string within another string.
     * @param string1 The string in which you want to find string2.
     * @param string2 The string you want to find in string1.
     * @param start A long indicating where the search will begin in string1. The default is 1.
     * @return A long whose value is the starting position of the first occurrence of string2
     * in string1 after the position specified in start. If string2 is not found in string1
     * or if start is not within string1, Pos returns 0.
     */
    public static int pos(String string1, String string2) {
        if ( (string1 == null) || (string2 == null))
            return Integer.MIN_VALUE;
        return string1.indexOf(string2) + 1;
    }

    public static int pos(String string1, String string2, int start) {
        if ( (string1 == null) || (string2 == null))
            return Integer.MIN_VALUE;
        if (start < 1 || start > string1.length())
            return 0;
        return string1.indexOf(string2, start - 1) + 1;
    }

    /**
     * Determines whether a string's value contains a particular pattern of characters.
     * @param s The string in which you want to look for a pattern of characters
     * @param textpattern A string whose value is the text pattern
     * @return Boolean. Returns TRUE if string matches textpattern and FALSE
     * if it does not.
     * Match also returns FALSE if either argument has not
     *been assigned a value
     * or the pattern is invalid.
     */
    public static boolean match(String s, String textpattern) {
        if (s == null)
            return false;
        if (textpattern == null)
            return false;
        try {
            Pattern p = Pattern.compile(textpattern);
            Matcher m = p.matcher(s);
            return m.find(0);
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Removes leading and trailing spaces from a string.
     * @param string The string you want returned with leading and trailing spaces deleted
     * @return String. Returns a copy of string with all leading and trailing spaces deleted
     * if it succeeds and the empty string ("") if an error occurs. If string is NULL,
     * Trim returns "".
     */
    public static String trim(String s) {
        if (s != null)
            return s.trim();
        else
            return "";
    }

    /**
     * Converts all the characters in a string to lowercase.
     * @param string The string you want to convert to lowercase letters
     * @return String. Returns string with uppercase letters changed to lowercase
     * if it succeeds and the empty string ("") if an error occurs. If string is NULL,
     * Lower returns NULL.
     */
    public static String lower(String s) {
        if (s != null)
            return s.toLowerCase();
        else
            return null;
    }

    /**
     * Converts all the characters in a string to uppercase.
     * @param string The string you want to convert to uppercase letters
     * @return String. Returns string with lowercase letters changed to uppercase
     * if it succeeds and the empty string ("") if an error occurs.
     * If string is NULL, Upper returns NULL.
     */
    public static String upper(String s) {
        if (s != null)
            return s.toUpperCase();
        else
            return "";
    }

    /**
     * Removes spaces from the end of a string.
     * @param string	The string you want returned with trailing blanks deleted
     * @return String. Returns a copy of string with trailing blanks deleted if it succeeds
     * and the empty string ("") if an error occurs. If any argument's value is NULL,
     * RightTrim returns NULL.
     */
    public static String rightTrim(String string) {
        if (string == null)
            return null;
        while (string.length() > 0 && string.endsWith(" "))
            string =  string.substring(0, string.length() - 1);
        return string;
    }

    /**
     * Converts the first character of a string to its ASCII integer value.
     * @param string	The string for which you want the ASCII value of the first character
     * @return Integer. Returns the ASCII value of the first character in string. If string
     * is NULL, Asc returns NULL.
     */
    public static int asc(String string) {
        if (string == null && string.length() == 0)
            return -1;
        return (int) string.charAt(0);
    }

    public static String string(int data) {
        String retStr;
        if (data != Integer.MIN_VALUE)
            retStr = Integer.toString(data);
        else
            retStr = "";
        return retStr;
    }

    /**
     * string
     * @param data
     * @param format
     * @return
     */
    public static String string(int data, String format) {
        if (format == null)
            return Integer.toString(data);
        StringTokenizer stk = new StringTokenizer(format, ";");
        String f1 = "";
        String f2 = "";
        String f3 = "";
        String f4 = "";
        if (stk.hasMoreTokens())
            f1 = stk.nextToken();
        if (stk.hasMoreTokens())
            f2 = stk.nextToken();
        else
            f2 = f1;
        if (stk.hasMoreTokens())
            f3 = stk.nextToken();
        else
            f3 = f1;
        if (stk.hasMoreTokens())
            f4 = stk.nextToken();
        else
            f4 = f1;

        if (Double.isNaN(data)) {
            if (f4.indexOf("0") < 0)
                return "";
            return new java.text.DecimalFormat(f4).format(0);
        }
        if (data > 0)
            return new java.text.DecimalFormat(f1).format(data);
        else if (data < 0) {
            String ret = new java.text.DecimalFormat(f2).format(data);
            return ret.substring(1, ret.length());
        }
        else
            return new java.text.DecimalFormat(f3).format(0);
    }

    /**
     * string
     * @param data
     * @return
     */
    public static String string(long data) {
        return new Long(data).toString();
    }

    /**
     * string
     * @param data
     * @param format
     * @return
     */
    public static String string(long data, String format) {
        if (format == null)
            return "";
        StringTokenizer stk = new StringTokenizer(format, ";");
        String f1 = "";
        String f2 = "";
        String f3 = "";
        if (stk.hasMoreTokens())
            f1 = stk.nextToken();
        if (stk.hasMoreTokens())
            f2 = stk.nextToken();
        else
            f2 = f1;
        if (stk.hasMoreTokens())
            f3 = stk.nextToken();
        else
            f3 = f1;

        if (data > 0)
            return new java.text.DecimalFormat(f1).format(data);
        else if (data < 0) {
            String ret = new java.text.DecimalFormat(f2).format(data);
            return ret.substring(1, ret.length());
        }
        else
            return f3;
    }

    /**
     * string
     * @param data
     * @return
     */
    public static String string(double data) {
        String result = "";
        try {
            if (!Double.isNaN(data) && !Double.isInfinite(data)) {
                DecimalFormat df = new DecimalFormat("0.############");
                df.setGroupingUsed(false);
                result = df.format(data);
            }
        }
        catch (Exception e) {
        }
        return result;
    }

    /**
     * string
     * @param data
     * @param format
     * @return
     */
    public static String string(double data, String format) {
        if (format == null)
            return string(data);
        StringTokenizer stk = new StringTokenizer(format, ";");
        String f1 = "";
        String f2 = "";
        String f3 = "";
        String f4 = "";
        boolean hasMinusFormat = false;
        if (stk.hasMoreTokens())
            f1 = stk.nextToken();
        if (stk.hasMoreTokens()) {
            f2 = stk.nextToken();
            hasMinusFormat = true;
        }
        else
            f2 = f1;
        if (stk.hasMoreTokens())
            f3 = stk.nextToken();
        else
            f3 = f1;
        if (stk.hasMoreTokens())
            f4 = stk.nextToken();
        else
            f4 = f1;

        if (Double.isNaN(data))
            return new java.text.DecimalFormat(f4).format(data);
        if (data > 0)
            return new java.text.DecimalFormat(f1).format(data);
        else if (data < 0) {
            if (hasMinusFormat)
                data = -data;
            return new java.text.DecimalFormat(f2).format(data);
        }
        else
            return new java.text.DecimalFormat(f3).format(data);
    }

    /**
     * string
     * @param data
     * @param maxFractionDigits
     * @return
     */
    public static String string(double data, int maxFractionDigits) {
        DecimalFormat df;
        String result = "";
        try {
            if (!Double.isNaN(data) && !Double.isInfinite(data)) {
                df = new DecimalFormat();
                df.setMaximumFractionDigits(maxFractionDigits);
                df.setGroupingUsed(false);
                result = df.format(data);
            }
        }
        catch (Exception e) {
        }
        return result;
    }

    /**
     * string
     * @param data
     * @param format
     * @return
     */
    public static String string(String data, String format) {
        if (format == null)
            return "";
        StringTokenizer stk = new StringTokenizer(format, ";");
        String f1 = "";
        String f2 = "";
        if (stk.hasMoreTokens())
            f1 = stk.nextToken();
        if (stk.hasMoreTokens())
            f2 = stk.nextToken();
        if (data == null)
            return f2;
        int j = 0;
        String ret = "";
        for (int t = 0; t < f1.length(); t++) {
            if (f1.charAt(t) == '@') {
                if (j < data.length()) {
                    ret = ret + data.charAt(j);
                    j++;
                }
                else
                    return ret; 
            }
            else
                ret = ret + f1.charAt(t);
        }
        return ret;
    }

    /**
     * string
     * @param data
     * @return
     */
    public static String string(java.util.Date data) {
        if (data == null)
            return "";
        if (data instanceof java.sql.Date)
            return new java.text.SimpleDateFormat(DEFAULT_DATE_FORMAT).format(
                data);
        else if (data instanceof java.sql.Time)
            return new java.text.SimpleDateFormat(DEFAULT_TIME_FORMAT).format(
                data);
        else
            return new java.text.SimpleDateFormat(DEFAULT_DATETIME_FORMAT).
                format(data);
    }

    /**
     * string
     * @param timestamp
     * @param format
     * @return
     */
    public static String string(java.sql.Timestamp timestamp, String format) {
        SimpleDateFormat formatter;
        //set defaults if needed
        if (timestamp == null)
            return "";
        if (format == null)
            format = DEFAULT_DATE_FORMAT;
        try {
            formatter = new SimpleDateFormat(format);
        }
        catch (IllegalArgumentException ex) {
        	ex.printStackTrace();
            format = DEFAULT_DATE_FORMAT;
            formatter = new SimpleDateFormat(format);
        }
        return formatter.format(timestamp);
    }

    /**
     * string
     * @param data
     * @param format
     * @return
     */
    public static String string(java.util.Date data, String format) {
        if (format == null)
            return "";
        StringTokenizer stk = new StringTokenizer(format, ";");
        String f1 = "";
        String f2 = "";
        if (stk.hasMoreTokens())
            f1 = stk.nextToken();
        if (stk.hasMoreTokens())
            f2 = stk.nextToken();
        if (data == null)
            return f2;
        if (data == null || format == null)
            return "";
        else
            return new java.text.SimpleDateFormat(f1).format(data);
    }

    /**
     * Extracts the first character of a string or converts an integer to a char.
     * @param n	A string that begins with the character you want, an integer you want to
     * convert to a character, or a blob in which the first value is a string or integer.
     * The rest of the contents of the string or blob is ignored. N can also be an Any
     * variable containing a string, integer, or blob.
     * @return har. Returns the first character of n. If n is NULL, Char returns NULL.
     */
    public static char Char(String n) {
        if (n == null || n.length() == 0)
            return (char) 0; // NOTE: can't return null
        else
            return n.charAt(0);
    }

    /**
     * Extracts the first character of a string or converts an integer to a char.
     * @param n	A string that begins with the character you want, an integer you want to
     * convert to a character, or a blob in which the first value is a string or integer.
     * The rest of the contents of the string or blob is ignored. N can also be an Any
     * variable containing a string, integer, or blob.
     * @return har. Returns the first character of n. If n is NULL, Char returns NULL.
     */
    public static char Char(int n) {
        return (char) n;
    }

    /**
     * Builds a string of the specified length whose value consists of spaces.
     * @param n A int whose value is the length of the string you want filled
     * with spaces.
     * @return A string filled with n spaces if it succeeds and the empty
     * string ("") if an error occurs
     */
    public static String space(int n) {
        if (n <= 0)
            return "";
        byte[] b = new byte[n];
        for (int t = 0; t < n; t++)
            b[t] = ' ';
        return new String(b);
    }

    /**
     * Replaces a portion of one string with another.
     * @param string1	The string in which you want to replace characters with string2.
     * @param start	A long whose value is the number of the first character you want replaced.
     * (The first character in the string is number 1.)
     * @param n	A long whose value is the number of characters you want to replace.
     * @param string2	The string that will replace characters in string1. The number of
     * characters in string2 can be greater than, equal to, or less than the number of
     * characters you are replacing.
     * @return String. Returns the string with the characters replaced if it succeeds and
     * the empty string if it fails. If any argument's value is NULL, Replace returns NULL.
     */
    public static String replace(String string1, int start, int n, String string2) {
        if (string1 == null || string2 == null)
            return null;
        if (start <= 0 || n < 0 || start + n > string1.length() + 1)
            return "";
        return string1.substring(0, start - 1) + string2 +
            string1.substring(start + n - 1);
    }

    /**
     * concatenate
     * @param str
     * @param ar
     * @return
     */
    public static String concatenate(String str, char[] ar) {
        for (int i = 0; i < ar.length; i++) {
            char ch = ar[i];
            if (ch != 0) {
                str += ch;
            }
            else {
                break;
            }
        }
        return str;
    }
    
    public static String append(String str1, String str2) {
    	if (str1 == null || str2 == null)
    		return null;
    	return str1 + str2;
    }

    /**
     * equals
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null || str2 == null)
            return false;
        else
            return str1.equals(str2);

    }

    /**
     * notEqual
     * @param str1
     * @param str2
     * @return
     */
    public static boolean notEqual(String str1, String str2) {
        if (str1 == null || str2 == null)
            return false;
        else
            return!str1.equals(str2);
    }

    /**
     * fill
     * @param chars
     * @param n
     * @return
     */
    public static String fill(String chars, int n) {
        String ret = "";
        for (int t = 0; t < n; t++)
            ret += chars;
        return ret;
    }

    /**
     * left trim
     * @param str
     * @return
     */
    public static String leftTrim(String str) {
        while (str != null && str.length() > 0 && str.charAt(0) == ' ')
            str = str.substring(1);
        return str;
    }
    
    /**
     * toJavaName
     * @param name
     * @return
     */
    public static String toJavaName(String name) {
    	String[] temp = name.toLowerCase().split("_");
    	name = "";
    	for (int t = 0; t < temp.length; t++) 
    		name += temp[t].substring(0, 1).toUpperCase() + temp[t].substring(1);
    	return name;
    }
    
    /**
     * convert a string to lower case with "_" seoerator.
     * @param name
     * @return
     */
    public static String toLowerCaseName(String name) {
    	String temp = "";
    	for (int t = 0; t < name.length(); t++) {
    		if ((name.charAt(t) == '2' || name.charAt(t) == '3' || name.charAt(t) == '4') && name.substring(t+1).equalsIgnoreCase("sig")) {
    			temp += "_" + name.charAt(t);
    		}else if (name.charAt(t) >= 'A' && name.charAt(t) <= 'Z')
    			temp += "_" + name.charAt(t);
    		else
    			temp += "" + name.charAt(t);
    	}
    	if (temp.charAt(0) == '_')
    		temp = temp.substring(1);
    	return temp.toLowerCase();
    }
    
    /**
     * check if a string array contains a string.
     * @param string
     * @param array
     * @return
     */
    public static boolean contains(String string, String[] array) {
    	if (string == null)
    		return false;
    	for (int t = 0; t < array.length; t++)
    		if (string.equals(array[t]))
    			return true;
    	return false;
    }

    /**
     * format string.
     * @param text
     * @param format
     * @return
     */
    public static String format(String text, String format) {
    	String ret = "";
    	int index = 0;
    	for (int t = 0; t < format.length(); t++) {
    		if (index >= text.length())
    			break;
    		if (Arrays.binarySearch(MASKS, format.charAt(t)) >= 0) {
    				ret += text.charAt(index);
    				index++;
    		}
			else {
				ret += format.charAt(t);
			}
    	}
    	return ret;
    }

    /**
     * unformat a string.
     * @param text
     * @param format
     * @return
     */
    public static String unformat(String text, String format) {
    	String ret = "";
    	try{
	    	for (int t = 0; t < text.length(); t++) {
	    		if ((t < format.length()) && Arrays.binarySearch(MASKS, format.charAt(t)) >= 0)
	    			ret += text.charAt(t);
	    	}
    	} catch (Exception e) {
    		return text;
    	}
    	return ret;
    }
    
    /**
     * Capitalizes the first letter of each word in a passed script.
     * It sets the remaining letters in each word to lowercase.
     * @param s String to be modified
     * @return String. If it succeeds, returns the text passed in the function
     * argument with the first letter of each word in uppercase and the remaining
     * letters in lowercase. Returns NULL if an error occurs.
     */
    public static String wordCap(String string) {
    	if (string != null && string.trim().length() > 0) {
    		String[] temp = string.toLowerCase().split(" ");
    		string = "";
    		for (int t = 0; t < temp.length; t++) {
    			if (temp[t].length() > 0)
    				temp[t] = temp[t].substring(0, 1).toUpperCase() + temp[t].substring(1);
    			string += (t > 0 ? " " : "") + temp[t];
    		}
    	}
    	return string;
    }
    
    public static int compareTo(String s1, String s2) {
    	if(s1 == null && s2 == null) {
    		return 0;
    	}
    	else if(s1 == null) {
    		return 0;
    	}
    	else if(s2 == null) {
    		return 0;
    	}
    	else {
    		return s1.compareTo(s2);
    	}
    }
    
    public static boolean hasCharBetween(String str, char start, char end) {
    	if (str == null)
    		return false;
    	for (int t = 0; t < str.length(); t++)
    		if (str.charAt(t) >= start && str.charAt(t) <= end)
    			return true;
    	return false;
    }
    
	public static boolean isNumeric(String str) {
		if (str == null)
			return false;
		
		boolean isNumeric = false;
		try {
			Integer.parseInt(str);
			isNumeric = true;
		} catch (NumberFormatException nfe) {
			isNumeric = false;
		}
		return isNumeric; 
		
		
	}
	
	//Check if a String representing an alphabet String
	public static boolean isAlphabet(String str){
		int strLen = str.length();
		 for (int t = 0; t < strLen; t++) {
			 if (!Character.isLetter(str.charAt(t)))  {
				 return false;
			 }
	     }
		
		return true;
	}
	
	//Check if a String representing an alphabet String
	public static boolean isAlphaNumeric(String str){
		int strLen = str.length();
		 for (int t = 0; t < strLen; t++) {
			 if (!Character.isLetter(str.charAt(t)))  {
				 
				 if(!isNumeric(Character.toString(str.charAt(t))))
				 return false;
			 }
	     }
		
		return true;
	}
	
	public static boolean convertToBoolean(String string)
	{
		String clean = string.trim().toUpperCase();
		
		if(clean.equals("Y"))
		{
			return true;
		}
		else if(clean.equals("N") || StringUtil.isNullOrEmpty(clean))
		{
			return false;
		}
		
		throw new IllegalArgumentException("A boolean string equivalent must be 'Y' or 'N'. (" + clean + ")");
	}
}
