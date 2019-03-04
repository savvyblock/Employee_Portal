package com.esc20.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Options;
import com.esc20.service.BankService;
import com.esc20.service.IndexService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DataSourceContextHolder;
import com.esc20.util.DateUtil;
import com.esc20.util.PasswordEncoderFactories;
import com.esc20.util.StringUtil;

@Controller
@RequestMapping("/")
public class IndexController {

	private static String key = "D3n!m!23R3gi0n20";
	
    @Autowired
    private IndexService indexService;
    
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    
    @RequestMapping(value="index", method=RequestMethod.GET)
    public ModelAndView getIndexPage(ModelAndView mav){
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> login(@RequestBody Map<String, String> param, HttpServletRequest req) throws ParseException{
        Map<String, String> res = new HashMap<>();
        res.put("isSuccess","false");
        if(param != null){
            String uName = param.get("userName");
            BeaUsers user = this.indexService.getUserPwd(uName);
            String plainTextPwd = param.get("userPwd");
            System.out.println("User plain input "+plainTextPwd);
            //plainTextPwd = passwordEncoder.encode(plainTextPwd);
            String userPwd = "{sha256}"+user.getUsrpswd();
            System.out.println("After SHA256 encoding "+plainTextPwd);
            System.out.println("User password from DB "+user.getUsrpswd());
            System.out.println("Match? "+ passwordEncoder);
            if(user != null /*&& user.getUsrpswd().equals(plainTextPwd)*/){
                res.put("isSuccess","true");
                res.put("userName", uName);
                HttpSession session = req.getSession();
                BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
                Options options = this.indexService.getOptions();
                District districtInfo = this.indexService.getDistrict();
                userDetail.setEmpNbr(user.getEmpNbr());
                userDetail.setDob(DateUtil.formatDate(userDetail.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
                String phone = districtInfo.getPhone();
                districtInfo.setPhone(StringUtil.left(phone, 3)+"-"+StringUtil.mid(phone, 4, 3)+"-"+StringUtil.left(phone, 4));
                Boolean isSupervisor = this.indexService.isSupervisor(user.getEmpNbr());
                Boolean isTempApprover = this.indexService.isTempApprover(user.getEmpNbr());
                session.setAttribute("isSupervisor", isSupervisor);
                session.setAttribute("isTempApprover", isTempApprover);
                session.setAttribute("user", user);
                session.setAttribute("userDetail", userDetail);
                session.setAttribute("companyId", user.getCmpId());
                session.setAttribute("options", options);
                session.setAttribute("district", districtInfo);
            }else {
            	  res.put("isSuccess","false");
                  res.put("userName", uName);
            }
        }
        return res;
    }
    
    @RequestMapping("home")
    public ModelAndView getHome(HttpServletRequest req,HttpServletResponse response){
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        BhrEmpDemo userDetail = (BhrEmpDemo)session.getAttribute("userDetail");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        
        mav.setViewName("home");
        mav.addObject("userDetail", userDetail);
        
        return mav;
    }
    
    @RequestMapping("logout")
    public ModelAndView logout(HttpServletRequest req, String Id,HttpServletResponse response){
        HttpSession session = req.getSession();
        session.invalidate();
        ModelAndView mav = new ModelAndView();
        return this.getIndexPage(mav);
    }
}
