package com.esc20.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomSHA256Encoder implements PasswordEncoder{

	private static MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-256");
	
	@Override
    public String encode(CharSequence password) {
    	String hashed = BCrypt.hashpw(password.toString(), BCrypt.gensalt(12));
    	return hashed;
    }
    
	@Override
    public boolean matches(CharSequence password, String encoded) {
    	return encoder.matches(password, encoded);
    }

}
