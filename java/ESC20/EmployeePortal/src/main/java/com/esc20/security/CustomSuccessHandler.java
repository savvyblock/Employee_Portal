package com.esc20.security;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Options;
import com.esc20.service.IndexService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

public class CustomSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private IndexService indexService;
    
    @Autowired
    private ReferenceService referenceService;
    
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		User loginUser = (User) authentication.getPrincipal();
		String userName = loginUser.getUsername();
		try {
			HttpSession session = request.getSession();
			BeaUsers user = this.indexService.getUserPwd(userName);
			BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
            Options options = this.indexService.getOptions();
            String district = (String)session.getAttribute("districtId");
            District districtInfo = this.indexService.getDistrict(district);
            userDetail.setEmpNbr(user.getEmpNbr());
            userDetail.setDob(DateUtil.formatDate(userDetail.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
            
        	List<Code> gens = referenceService.getGenerations();
   		 	for(Code gen: gens) {
   		    	if(userDetail.getNameGen() != null && gen.getCode().trim().equals(userDetail.getNameGen().toString().trim())) {
   		    		userDetail.setGenDescription(gen.getDescription());
   		    	}
   		    }
   		
            String phone = districtInfo.getPhone();
            districtInfo.setPhone(StringUtil.left(phone, 3)+"-"+StringUtil.mid(phone, 4, 3)+"-"+StringUtil.right(phone, 4));
            Boolean isSupervisor = this.indexService.isSupervisor(user.getEmpNbr());
            Boolean isTempApprover = this.indexService.isTempApprover(user.getEmpNbr());
            session.setAttribute("isSupervisor", isSupervisor);
            session.setAttribute("isTempApprover", isTempApprover);
            session.setAttribute("user", user);
            session.setAttribute("userDetail", userDetail);
            session.setAttribute("companyId", district);
            session.setAttribute("options", options);
            session.setAttribute("district", districtInfo);
            String returnURL= "/"+request.getContextPath().split("/")[1]+"/home";
            response.sendRedirect(returnURL);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
