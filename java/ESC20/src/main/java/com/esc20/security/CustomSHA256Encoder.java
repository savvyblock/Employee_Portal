package com.esc20.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.esc20.service.BankService;

public class CustomSHA256Encoder implements PasswordEncoder{

	private Logger logger = LoggerFactory.getLogger(CustomSHA256Encoder.class);
	
	private static MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-256");
	
	@Override
    public String encode(CharSequence password) {
    	String hashed = BCrypt.hashpw(password.toString(), BCrypt.gensalt(12));
    	return hashed;
    }
    
	@Override
    public boolean matches(CharSequence password, String encoded) {
		try {
			return encoder.matches(password, encoded)||BCrypt.checkpw(password.toString(), encoded);
		}catch(IllegalArgumentException e) {
			logger.error("Password is in old format without SALT and is wrong: password provided " + password);
			return false;
		}
    }

}
