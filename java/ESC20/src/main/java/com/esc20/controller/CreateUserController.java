package com.esc20.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaEmail;
import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.SearchUser;
import com.esc20.security.CustomSHA256Encoder;
import com.esc20.service.BankService;
import com.esc20.service.IndexService;
import com.esc20.service.ReferenceService;

@Controller
@RequestMapping("/createUser")
public class CreateUserController{
	
    @Autowired
    private IndexService indexService;
    
    private static PasswordEncoder encoder = new CustomSHA256Encoder();
	
    @RequestMapping("searchUser")
    public ModelAndView searchUser(HttpServletRequest req){
    	ModelAndView mav = new ModelAndView();
        mav.setViewName("searchUser");
        return mav;
    }
    
    @RequestMapping("createNewUser")
    public ModelAndView createNewUser(HttpServletRequest req){
    	ModelAndView mav = new ModelAndView();
        mav.setViewName("createNewUser");
        return mav;
    }
    
    @RequestMapping("saveNewUser")
    public ModelAndView saveNewUser(HttpServletRequest req){
    	ModelAndView mav = new ModelAndView();
    	BeaUsers newUser=new BeaUsers();
    	newUser.setEmpNbr(req.getParameter("empNumber"));
    	newUser.setUsrname(req.getParameter("username"));//username
    	newUser.setHint(req.getParameter("hintQuestion"));//hintQuestion
    	newUser.setHintAns(encoder.encode(req.getParameter("hintAnswer")));//  hintAnswer
    	//newUser.setUserEmail(req.getParameter("workEmail"));//workEmail
    	newUser.setUsrpswd(encoder.encode(req.getParameter("password")));
    	
    	newUser.setLkPswd('N');
    	newUser.setPswdCnt(0);
    	newUser.setLkFnl('N');
    	newUser.setTmpDts("");
    	newUser.setTmpCnt(0);
    	newUser.setHintCnt(0);
    	newUser.setCmpId(0);
    	
    	BeaUsers user=indexService.getUserByUsername(req.getParameter("username"));
    	if(user!=null) {
    		mav.setViewName("createNewUser");
    	    mav.addObject("user", user);
    	    mav.addObject("newUser", newUser);
    	    mav.addObject("isUserExist", "true");
    	}else {
    		this.indexService.updateEmailEmployee(newUser.getEmpNbr(),req.getParameter("workEmail"),req.getParameter("homeEmail"));
    		indexService.saveBeaUsers(newUser);
    		mav.setViewName("index");
    	    mav.addObject("user", user);
    	    mav.addObject("newUser", newUser);
    	    mav.addObject("isSuccess", "true");
    	}
    	
        return mav;
    }
	
    @RequestMapping(value="retrieveEmployee",method=RequestMethod.POST)
    public ModelAndView retrieveEmployee(HttpServletRequest req){
    	ModelAndView mav = new ModelAndView();
    	
    	SearchUser searchUser=new SearchUser();
    	searchUser.setDateDay(req.getParameter("dateDay"));
    	searchUser.setDateMonth(req.getParameter("dateMonth"));
    	searchUser.setDateYear(req.getParameter("dateYear"));
    	searchUser.setEmpNumber(req.getParameter("empNumber"));
    	searchUser.setZipCode(req.getParameter("zipCode"));
    	
    	BeaUsers user = this.indexService.getUserByEmpNbr(searchUser.getEmpNumber());
    	
    	if(user !=null) {
    		mav.setViewName("searchUser");
        	mav.addObject("isExistUser", "true");
        	mav.addObject("newUser", searchUser);
    	}else {
    		BhrEmpDemo bed= this.indexService.retrieveEmployee(searchUser);
        	if(bed == null) {
        		mav.setViewName("searchUser");
            	mav.addObject("isSuccess", "false");
            	mav.addObject("newUser", searchUser);
        	}else {
        		BeaEmail emailRequest = this.indexService.getBeaEmail(bed);
        		searchUser.setNameF(bed.getNameF());
        		searchUser.setNameL(bed.getNameL());
        		mav.setViewName("createNewUser");
            	mav.addObject("newUser", searchUser);
            	mav.addObject("emailShowRequest", emailRequest);
        	}
    	}
    	
        return mav;
    }
    
}
