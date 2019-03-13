package com.esc20.security;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Options;
import com.esc20.service.IndexService;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter{

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    
    private static MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-256");
    
    @Autowired
    private IndexService indexService;
    
    @Autowired
    LoginUrlAuthenticationEntryPoint entryPoint;
    
    public CustomLoginFilter() {
        super();
    }
    
    public String encode(String password) {
    	String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
    	return hashed;
    }
    
    public Boolean matches(String password, String encoded) {
    	return encoder.matches(password, encoded);
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }
        try {
        	HttpSession session = request.getSession();
			BeaUsers user = this.indexService.getUserPwd(username);
	        UsernamePasswordAuthenticationToken authRequest;
	        if(user!=null && this.matches(password, user.getUsrpswd())) {
	        	List<GrantedAuthority> resultAuths = new ArrayList<GrantedAuthority>();
	        	resultAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
                BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
                Options options = this.indexService.getOptions();
                District districtInfo = this.indexService.getDistrict();
                userDetail.setEmpNbr(user.getEmpNbr());
                userDetail.setDob(DateUtil.formatDate(userDetail.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
                String phone = districtInfo.getPhone();
                districtInfo.setPhone(StringUtil.left(phone, 3)+"-"+StringUtil.mid(phone, 4, 3)+"-"+StringUtil.left(phone, 4));
                Boolean isSupervisor = this.indexService.isSupervisor(user.getEmpNbr());
                Boolean isTempApprover = this.indexService.isTempApprover(user.getEmpNbr());
                if(isSupervisor) 
                	resultAuths.add(new SimpleGrantedAuthority("ROLE_SUPERVISOR"));
                if(isTempApprover)
                	resultAuths.add(new SimpleGrantedAuthority("ROLE_TEMPAPPROVER"));
                session.setAttribute("isSupervisor", isSupervisor);
                session.setAttribute("isTempApprover", isTempApprover);
                session.setAttribute("user", user);
                session.setAttribute("userDetail", userDetail);
                session.setAttribute("companyId", user.getCmpId());
                session.setAttribute("options", options);
                session.setAttribute("district", districtInfo);
	        	authRequest = new UsernamePasswordAuthenticationToken(username, password, resultAuths);
	        }
	        else {
	        	authRequest = new UsernamePasswordAuthenticationToken(username, password);
	        	authRequest.setAuthenticated(false);
	        }
	        setDetails(request, authRequest);
	        return this.getAuthenticationManager().authenticate(authRequest);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

    }
}
