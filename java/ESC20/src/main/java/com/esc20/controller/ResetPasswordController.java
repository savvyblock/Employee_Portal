package com.esc20.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.SearchUser;
import com.esc20.security.CustomSHA256Encoder;
import com.esc20.service.IndexService;

@Controller
@RequestMapping("/resetPassword")
public class ResetPasswordController{
	
    @Autowired
    private IndexService indexService;
    
    private static PasswordEncoder encoder = new CustomSHA256Encoder();
	
    @RequestMapping("retrieveUserName")
    public ModelAndView retrieveUserName(HttpServletRequest req){
    	ModelAndView mav = new ModelAndView();
    	SearchUser searchUser=new SearchUser();
    	searchUser.setDateDay(req.getParameter("dateDay"));
    	searchUser.setDateMonth(req.getParameter("dateMonth"));
    	searchUser.setDateYear(req.getParameter("dateYear"));
    	searchUser.setEmpNumber(req.getParameter("empNumber"));
    	searchUser.setZipCode(req.getParameter("zipCode"));
    	
    	BhrEmpDemo bed= this.indexService.retrieveEmployee(searchUser);
    	if(bed == null) {
    		mav.addObject("retrieve", "false");
    	}else {
    		
    		BeaUsers user = this.indexService.getUserByEmpNbr(searchUser.getEmpNumber());
    		if(user == null) {
    			mav.addObject("retrieve", "false");
        		mav.addObject("retrieveUserNameErrorMessage", "Email Does not exist");
        	}else {
        		searchUser.setUsername(user.getUsrname());
        		searchUser.setUserEmail(bed.getEmail());
        		mav.addObject("retrieve", "true");
        	}
        	
    	}
    	
        mav.setViewName("forgetPassword");
        mav.addObject("user", searchUser);
        return mav;
    }
    
	@RequestMapping("forgetPassword")
	public ModelAndView forgetPassword(HttpServletRequest req){
		ModelAndView mav = new ModelAndView();
	    mav.setViewName("forgetPassword");
	    return mav;
	}
	
	@RequestMapping("updatePassword")
	public ModelAndView updatePassword(HttpServletRequest req,String password, String id){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		
		try {
	    	BeaUsers user = this.indexService.getUserByEmpNbr(id);
	    	user.setUsrpswd(encoder.encode(password));
	    	user.setTmpDts(user.getTmpDts()==null?"":user.getTmpDts());
	    	this.indexService.updateUser(user);
	    	
		}catch(Exception e) {
			mav.addObject("resetPsw", "resetPswFaild");
		}
		mav.addObject("resetPsw", "resetPswSuccess");
	    return mav;
	}
	
    @RequestMapping("resetPassword")
    public ModelAndView resetPassword(HttpServletRequest req, String userName, String email) throws ParseException{
    	ModelAndView mav = new ModelAndView();
    	BeaUsers user = this.indexService.getUserPwd(userName);
    	if(user!=null) {
    		
    		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
    		if(email.endsWith(userDetail.getEmail())) {
    			mav.setViewName("resetPassword");
        		mav.addObject("id", user.getEmpNbr());
        		return mav;
    		}else {
    			 mav.setViewName("forgetPassword");
    		     mav.addObject("errorMessage", "User Does not exist");
    		}
    		
    	}
        mav.setViewName("forgetPassword");
        mav.addObject("errorMessage", "User Does not exist");
        return mav;
    }
    
    
}
