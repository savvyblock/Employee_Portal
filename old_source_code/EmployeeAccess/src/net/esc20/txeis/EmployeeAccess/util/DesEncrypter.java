package net.esc20.txeis.EmployeeAccess.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesEncrypter{
    Cipher ecipher;
    Cipher dcipher;

    public DesEncrypter() throws NoSuchAlgorithmException
    {
    	this(KeyGenerator.getInstance("DES").generateKey());
    }
    
    public DesEncrypter(String key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException
    {
    	SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
    	
    	String fullKey = key;
    	for(int i = key.length(); i <= 8; i++)
    	{
    		fullKey += " ";
    	}
    	
    	DESKeySpec spec = new DESKeySpec(fullKey.getBytes());
    	SecretKey secret = skf.generateSecret(spec);

        try 
        {
            ecipher = Cipher.getInstance("DES");
            dcipher = Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, secret);
            dcipher.init(Cipher.DECRYPT_MODE, secret);

        } 
        catch (javax.crypto.NoSuchPaddingException e)
        {
        } 
        catch (java.security.NoSuchAlgorithmException e) 
        {
        } 
        catch (java.security.InvalidKeyException e) 
        {
        }
    }
    
    public DesEncrypter(SecretKey key)
    {
        try 
        {
            ecipher = Cipher.getInstance("DES");
            dcipher = Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);

        } 
        catch (javax.crypto.NoSuchPaddingException e)
        {
        } 
        catch (java.security.NoSuchAlgorithmException e) 
        {
        } 
        catch (java.security.InvalidKeyException e) 
        {
        }
    }

    public String encrypt(String str) 
    {
        try 
        {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } 
        catch (javax.crypto.BadPaddingException e) 
        {
        } 
        catch (IllegalBlockSizeException e) 
        {
        } 
        catch (UnsupportedEncodingException e) 
        {
        }
        return null;
    }

    public String decrypt(String str) 
    {
        try 
        {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");
        } 
        catch (javax.crypto.BadPaddingException e) 
        {
        } 
        catch (IllegalBlockSizeException e) 
        {
        } 
        catch (UnsupportedEncodingException e) 
        {
        } 
        catch (java.io.IOException e) 
        {
        }
        return null;
    }
}