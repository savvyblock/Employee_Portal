package com.esc20.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaAlert;
import com.esc20.model.BeaAltMailAddr;
import com.esc20.model.BeaBusPhone;
import com.esc20.model.BeaCellPhone;
import com.esc20.model.BeaDrvsLic;
import com.esc20.model.BeaEmail;
import com.esc20.model.BeaEmerContact;
import com.esc20.model.BeaHmPhone;
import com.esc20.model.BeaLglName;
import com.esc20.model.BeaMailAddr;
import com.esc20.model.BeaMrtlStat;
import com.esc20.model.BeaRestrict;
import com.esc20.model.BeaUsers;
import com.esc20.model.BeaW4;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrEmpPay;
import com.esc20.model.BthrBankCodes;
import com.esc20.nonDBModels.Bank;
import com.esc20.nonDBModels.BankRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.Criteria;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Money;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.SearchCriteria;
import com.esc20.nonDBModels.Page;
import com.esc20.nonDBModels.SearchUser;
import com.esc20.service.BankService;
import com.esc20.service.IndexService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

@Controller
@RequestMapping("/")
public class IndexController {

	private static String key = "D3n!m!23R3gi0n20";
	
    @Autowired
    private IndexService indexService;

    @Autowired
    private ReferenceService referenceService;
    
    @Autowired
    private BankService bankService;
    
    private static ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
    
    @RequestMapping(value="", method=RequestMethod.GET)
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
            plainTextPwd = encoder.encodePassword(plainTextPwd,null);
            System.out.println("After SHA256 encoding "+plainTextPwd);
            System.out.println("User password from DB "+user.getUsrpswd());
            if(user != null && user.getUsrpswd().equals(plainTextPwd)){
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
    public ModelAndView getHome(HttpServletRequest req){
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
    public ModelAndView logout(HttpServletRequest req, String Id){
        HttpSession session = req.getSession();
        session.invalidate();
        ModelAndView mav = new ModelAndView();
        return this.getIndexPage(mav);
    }
}
