package com.esc20.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.esc20.model.BeaUsers;
import com.esc20.service.IndexService;

public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	private static CustomSHA256Encoder encoder = new CustomSHA256Encoder();

    @Autowired
    private IndexService indexService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        BeaUsers user = this.indexService.getUserByUsername(userName);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if(user!=null && encoder.matches(password, user.getUsrpswd())) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        	return new UsernamePasswordAuthenticationToken(userName, password,authorities);
        }
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}
}
