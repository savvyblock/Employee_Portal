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
import com.esc20.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/")
public class IndexController {
    
    @RequestMapping(value="login", method=RequestMethod.GET)
    public ModelAndView getIndexPage(HttpServletRequest req, String Id,HttpServletResponse response){
    	ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }
    
    @RequestMapping("markTimeout")
    @ResponseBody
    public JSONObject markTimeout(HttpServletRequest req, String Id,HttpServletResponse response){
    	HttpSession session = req.getSession();
    	JSONObject result=new JSONObject();
        session.setAttribute("isTimeOut", true);
        result.put("isSuccess", "true");
        return result;
    }
    
    @RequestMapping("home")
    public ModelAndView getHome(HttpServletRequest req,HttpServletResponse response){
        HttpSession session = req.getSession();
        BhrEmpDemo userDetail = (BhrEmpDemo)session.getAttribute("userDetail");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        mav.addObject("userDetail", userDetail);
        return mav;
    }
}
