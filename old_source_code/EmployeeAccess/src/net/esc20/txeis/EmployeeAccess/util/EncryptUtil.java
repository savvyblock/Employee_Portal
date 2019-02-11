package net.esc20.txeis.EmployeeAccess.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class EncryptUtil
{
	private static final String cs_alphanum="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final String cs_swap="ZGMFH5ST1IBYKQRCW302VAOP789N4DEXLUJ6";
	private static int ii_dither[] = {2, 5, 1, 6, 3, 8, 4, 7, 5, 1, 6, 4, 9, 1, 3} ;
	
	public static String encrypt(String thestr)
	{
		//code for inv_encrypt.of_set:
		//thestr is the String you sent to the function 								
		//ids_data.getitemString(li_row,li_cod))
		String is_raw=null, is_encrypted, is_key="CGI";
		String retVal, tempStr, tStr;
		int sourcePtr, keyPtr, keyLen, sourceLen, tempVal, tempKey;

		retVal = is_raw;
		is_raw = thestr;		//String sent to be encrypted
		keyPtr = 1;
		keyLen = is_key.length();
		sourceLen = is_raw.length();
		is_encrypted = "";
		for(sourcePtr = 1; sourcePtr <= sourceLen; sourcePtr++)
		{
			tempVal = StringUtil.asc(StringUtil.right(is_raw, sourceLen - sourcePtr + 1));
			tempKey = StringUtil.asc(StringUtil.right(is_key, keyLen - keyPtr + 1));
			tempVal += tempKey;
			// Added this section to ensure that ASCII Values stay within 0 to 255 range
			while( tempVal > 255)
			{
				if( tempVal > 255)
				{
					tempVal = tempVal - 255;
				}
			}
			// End of Section
			tStr = "" + tempVal;
			is_encrypted += tStr;
			keyPtr ++;
			if (keyPtr > is_key.length()) keyPtr = 1;
		}
		
		return is_encrypted;
	//end code for inv_encrypt.of_set
	}
	
	public static String encryptPassword(String password)
	{
	    MessageDigest md = null;
	    
	    try
	    {
	    	md = MessageDigest.getInstance("SHA");
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	    	throw new IllegalStateException("Cannot encrypt password.",e);
	    }
	    try
	    {
	    	md.update(password.getBytes("UTF-8"));
	    }
	    catch(UnsupportedEncodingException e)
	    {
	    	throw new IllegalStateException("Cannot encrypt password.",e);
	    }
	    
	    byte raw[] = md.digest();
	    String hash = (new BASE64Encoder()).encode(raw);

	    return hash;
	}
}