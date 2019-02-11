package net.esc20.txeis.EmployeeAccess.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberUtil {
	
	private static java.util.Random random = new java.util.Random();

	public static BigDecimal value(BigDecimal o)
	{
		if(o == null)
		{
			return new BigDecimal(BigInteger.ZERO,2);
		}
		
		return o;
	}
	
    /**
     * Combines two unsigned integers into a long value.
     * @param lowword	An UnsignedInteger to be the low word in the long
     * @return highword	An UnsignedInteger to be the high word in the long
     * @return Long. Returns the long if it succeeds and -1 if an error occurs. If any
     * argument's value is NULL, Long returns NULL.
     */
    public static int toLong(int lowword, int highword) {
        return lowword + (highword << 16);
    }

    /**
     * Converts a string whose value is a number into a long.
     * @param string The string you want returned as a long.
     * @return Long. Returns the value of string as a long if it succeeds and 0 if
     * string is not a valid PowerScript number or if it is an incompatible datatype.
     * If string is NULL, Long returns NULL.
     */
    public static int toLong(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (Exception ex) {
            return 0;
        }
    }

    /**
     * Converts a string whose value is a number into a long.
     * @param string The string you want returned as a long.
     * @return Long. Returns the value of string as a long if it succeeds and 0 if
     * string is not a valid PowerScript number or if it is an incompatible datatype.
     * If string is NULL, Long returns NULL.
     */
    public static long parseLong(String string) {
        try {
            return Long.parseLong(string);
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    /**
     * Reports whether a number is negative, zero, or positive.
     * @param n	The int for which you want to find out the sign
     * @return Integer. Returns a number (-1, 0, or 1) indicating the sign of n.
     */
    public static int sign(int n) {
        if (n == 0)
            return 0;
        else if (n > 0)
            return 1;
        else
            return -1;
    }

    /**
     * Reports whether a number is negative, zero, or positive.
     * @param n	The long for which you want to find out the sign
     * @return Integer. Returns a number (-1, 0, or 1) indicating the sign of n.
     */
    public static int sign(long n) {
        if (n == 0)
            return 0;
        else if (n > 0)
            return 1;
        else
            return -1;
    }

    /**
     * Reports whether a number is negative, zero, or positive.
     * @param n	The double for which you want to find out the sign
     * @return Integer. Returns a number (-1, 0, or 1) indicating the sign of n.
     */
    public static int sign(double n) {
        if (n == 0)
            return 0;
        else if (n > 0)
            return 1;
        else
            return -1;
    }

    /**
     * Reports whether a number is negative, zero, or positive.
     * @param n	The float for which you want to find out the sign
     * @return Integer. Returns a number (-1, 0, or 1) indicating the sign of n.
     */
    public static int sign(float n) {
        if (n == 0)
            return 0;
        else if (n > 0)
            return 1;
        else
            return -1;
    }

    /**
     * Rounds a number to the specified number of decimal places.
     * @param x The double you want to round.
     * @param n The number of decimal places to which you want to round x. Valid values
     * are 0 through 18.
     * @return Decimal. Returns x rounded to the specified number of decimal places if
     * it succeeds, and null if it fails.
     */
    public static double round(double x, int n) {
        if (Double.isNaN(x) || n < 0 || n > 18)
            return Double.NaN;
        //ts20090521  if zero, then return
        if (x == 0.0) 
        	return x;       
        boolean b = x >= 0.0;
        if (!b)
            x = -1 * x;       
        x = Math.round(x * Math.pow(10, n));
        if (!b)
            x = -1 * x;
        return x / Math.pow(10, n);
		// Below is the rounding code that was introduced in Phase III
		// which is commented out because it's benefits are unproven. 
		//double tmp = (double) ((long)(x * Math.pow(10, n+3)) + 1);
    	//tmp = (double)((long)(tmp /100));
    	//tmp = tmp/Math.pow(10, n+1);
    	//tmp = (double) ((long)(tmp * Math.pow(10, n+1)) + 5);
    	//tmp = (double)((long)(tmp /10));
    	//tmp = tmp/Math.pow(10, n);
		//x = tmp;

    	//if (!b)
        //    x = -1 * tmp;
        //return x;
    }

    /**
     * Rounds a number to the specified number of decimal places.
     * @param x The float you want to round.
     * @param n The number of decimal places to which you want to round x. Valid values
     * are 0 through 18.
     * @return Decimal. Returns x rounded to the specified number of decimal places if
     * it succeeds, and null if it fails.
     */
    public static double round(float x, int n) {
        if (Float.isNaN(x) || n < 0 || n > 18)
            return Double.NaN;

        boolean b = x >= 0.0;
        if (!b)
            x = -1 * x;
        x = Math.round(x * Math.pow(10, n));
        if (!b)
            x = -1 * x;
        return x / Math.pow(10, n);
    }

    /**
     * Converts a string to a decimal number.
     * @param string A string whose value you want returned as a decimal value.
     * @return BigDecimal. Returns the value of string as a decimal. If string is not
     * a valid PowerScript number or if it contains an incompatible datatype,
     * returns 0. If string is NULL, returns NULL.
     */
    public static double dec(String string) {
        if (string == null)
            return Double.NaN;
        try {
            return Double.parseDouble(string);
        }
        catch (Exception ex) {
            return 0;
        }
    }

    /**
     * Converts the value of a string to an integer.
     * @param string A string whose value you want returned as an integer.
     * @return int. Returns the value of string as an integer if it succeeds and 0 if not.
     */
    public static int integer(String string) {
        if (string == null || string.trim().length() == 0)
            return Integer.MIN_VALUE;
        try {
            return (int) Double.parseDouble(string.trim());
        }
        catch (Exception ex) {
            return 0;
        }
    }

    /**
     * integer
     * @param n
     * @return
     */
    public static int integer(double n) {
        if (Double.isNaN(n))
            return Integer.MIN_VALUE;
        if (n >= 0)
            return (int) n;
        return ( (int) n > n) ? (int) n - 1 : (int) n;
    }

    /**
     * Reports whether the value of a string is a number.
     * @param string A string whose value you want to test to determine whether it is
     * a valid PowerScript number
     * @return Boolean. Returns TRUE if string is a valid PowerScript number and FALSE
     * if it is not. If string is NULL, IsNumber returns NULL.
     */
    public static boolean isNumber(String string) {
        try {
            Double.parseDouble(string);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    /**
     * Calculates the absolute value of a number.
     * @param n	The number for which you want the absolute value
     * @return The datatype of n. Returns the absolute value of n. If n is NULL, abs
     * returns NULL.
     */
    public static int abs(int n) {
        return (n < 0) ? -1 * n : n;
    }

    public static long abs(long n) {
        return (n < 0) ? -1 * n : n;
    }

    public static double abs(double n) {
        return (n < 0) ? -1 * n : n;
    }

    /**
     * Determines the largest whole number less than or equal to a number.
     * @param n	The number for which you want the largest whole number that is less than
     * or equal to it
     * @return Integer. Returns the largest whole number less than or equal to n. If n is
     * too small or too large to be represented as an integer, Int returns 0. If n is NULL,
     * Int returns NULL.
     */
    public static int toInt(double n) {
        return integer(n);
    }

    /**
     * Converts a string to a double or obtains a double value that is stored in a blob.
     * @param stringorblob A string whose value you want returned as a double or a blob
     * in which the first value is the double value. The rest of the contents of the blob
     * is ignored. Stringorblob can also be an Any variable containing a double or blob.
     * @return Double. Returns the contents of stringorblob as a double. If stringorblob
         * is not a valid PowerScript number or if it contains a non-numeric datatype,
     * Double returns 0. If stringorblob is NULL, Double returns NULL.
     */
    public static double toDouble(String string) {
        if (string == null)
            return Double.NaN;
        try {
            return Double.parseDouble(string);
        }
        catch (Exception ex) {
            return Double.NaN;
        }
    }

    /**
     * Truncates a number to the specified number of decimal places.
     * @param x	The number you want to truncate
     * @param n	The number of decimal places to which you want to truncate x (valid
     * values are 0 through 18)
     * @return Decimal. Returns the result of the truncation if it succeeds and NULL
     * if it fails or if any argument is NULL.
     */
    public static double truncate(double x, int n) {
        if (Double.isNaN(x) || n < 0 || n > 18)
            return Double.NaN;

        boolean b = x >= 0.0;
        if (!b)
            x = -1 * x;
        x = (long) (x * Math.pow(10, n));
        if (!b)
            x = -1 * x;
        return x / Math.pow(10, n);
    }

    /**
     * Determines the natural logarithm of a number.
     * @param n	The number for which you want the natural logarithm (base e).
     * The value of n must be greater than 0.
     * @return Double. Returns the natural logarithm of n. An execution error occurs
     * if n is negative or zero. If n is NULL, Log returns NULL.
     */
    public static double log(double n) {
        return (Double.isNaN(n)) ? n : Math.log(n);
    }

    /**
     * convert red, green and blue values into on integer color value
     * @param red
     * @param green
     * @param blue
     * @return
     */
    public static int rgb(int red, int green, int blue) {
        return 65536 * blue+ 256 * green+ red;
    }
    
    public static void randomize(long seed) {
    	random = new java.util.Random(seed);
    }
    
    /**
     * create a random number between 1 and limit.
     * @param limit
     * @return
     */
    public static int rand(int limit) { 
    	return random.nextInt(limit) + 1;
    }
    
    /**
     * add two double values that may be NaN.
     * @param d1
     * @param d2
     * @return
     */
    public static double add(double d1, double d2) {
    	if (Double.isNaN(d1))
    		return Double.NaN;
    	else if (Double.isNaN(d2))
    		return d1;
    	return d1 + d2;
    }
    
    /**
     * Returns a double 
     * @param d1
     * @param d2
     * @param scale1
     * @param scale2
     * @return 
     */  
    public static double multiply(double d1, double d2, int scale1, int scale2) {
    	if (Double.isNaN(d1) || Double.isNaN(d2))
    		return Double.NaN;
    	
    	BigDecimal dec1 = new BigDecimal(d1);
    	BigDecimal dec2 = new BigDecimal(d2);
    	dec1.setScale(scale1, BigDecimal.ROUND_HALF_UP);
    	dec2.setScale(scale2, BigDecimal.ROUND_HALF_UP);
    	
    	return dec1.multiply(dec2).doubleValue();
    }

    /**
     * Returns a double 
     * @param d1
     * @param d2
     * @param scale1
     * @param scale2
     * @param scaleReturn
     * @return 
     */  
    public static double multiply(double d1, double d2, int scale1, int scale2 ,int scaleReturn ) {
    	if (Double.isNaN(d1) || Double.isNaN(d2))
    		return Double.NaN;
    	
    	BigDecimal dec1 = new BigDecimal(d1);
    	BigDecimal dec2 = new BigDecimal(d2);
    	dec1.setScale(scale1, BigDecimal.ROUND_HALF_UP);
    	dec2.setScale(scale2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal decRet = dec1.multiply(dec2);
    	decRet.setScale(scaleReturn, BigDecimal.ROUND_HALF_UP);
    	double tmp = (double) ((long)(decRet.doubleValue() * Math.pow(10, scaleReturn+3)) + 1);
    	tmp = (double)((long)(tmp /100));
    	tmp = tmp/Math.pow(10, scaleReturn+1);
    	tmp = (double) ((long)(tmp * Math.pow(10, scaleReturn+1)) + 5);
    	tmp = (double)((long)(tmp /10));
    	tmp = tmp/Math.pow(10, scaleReturn);
    	return tmp;
    }
    
    /**
     * Returns a double 
     * @param d1
     * @param d2
     * @param scale1
     * @param scale2
     * @return 
     */  
    public static double divide(double d1, double d2, int scale1, int scale2) {
    	if (Double.isNaN(d1) || Double.isNaN(d2))
    		return Double.NaN;
    	
    	BigDecimal dec1 = new BigDecimal(d1);
    	BigDecimal dec2 = new BigDecimal(d2);
    	dec1.setScale(scale1, BigDecimal.ROUND_HALF_UP);
    	dec2.setScale(scale2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal dec = dec1.divide(dec2,BigDecimal.ROUND_HALF_UP);
    	return dec.doubleValue();
    }

    /**
     * Returns a double 
     * @param d1
     * @param d2
     * @param scale1
     * @param scale2
     * @return 
     */  
    public static double subtract(double d1, double d2, int scale1, int scale2) {
    	if (Double.isNaN(d1) || Double.isNaN(d2))
    		return Double.NaN;
    	
    	BigDecimal dec1 = new BigDecimal(d1);
    	BigDecimal dec2 = new BigDecimal(d2);
    	dec1.setScale(scale1, BigDecimal.ROUND_HALF_UP);
    	dec2.setScale(scale2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal dec = dec1.subtract(dec2);
    	int scale = scale2;
    	if (scale1 > scale)
    		scale = scale1;
    	dec = dec.setScale(scale,BigDecimal.ROUND_HALF_UP);
    	return dec.doubleValue();
    }
}