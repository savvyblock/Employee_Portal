package com.esc20.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Options;
import com.esc20.service.IndexService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/")
public class IndexController {
	@Autowired
	private IndexService indexService;
	
	@Autowired
	private ReferenceService referenceService;
    
    @RequestMapping(value="login", method=RequestMethod.GET)
    public ModelAndView getIndexPage(HttpServletRequest req, String Id,HttpServletResponse response){
    	ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
		Boolean isUserLoginFailure = (Boolean) req.getSession().getAttribute("isUserLoginFailure");
		if(isUserLoginFailure!=null && isUserLoginFailure) {
			req.getSession().removeAttribute("isUserLoginFailure");
			mav.addObject("isUserLoginFailure", "true");
		}
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
        BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
        Options options = this.indexService.getOptions();
        /*String district = (String)session.getAttribute("districtId");
        District districtInfo = this.indexService.getDistrict(district);*/
        userDetail.setEmpNbr(user.getEmpNbr());
        userDetail.setDob(DateUtil.formatDate(userDetail.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
        List<Code> gens = referenceService.getGenerations();
		 	for(Code gen: gens) {
		    	if(userDetail.getNameGen() != null && gen.getCode().trim().equals(userDetail.getNameGen().toString().trim())) {
		    		userDetail.setGenDescription(gen.getDescription());
		    	}
		    }
       /* String phone = districtInfo.getPhone();
        districtInfo.setPhone(StringUtil.left(phone, 3)+"-"+StringUtil.mid(phone, 4, 3)+"-"+StringUtil.right(phone, 4));*/

		session.setAttribute("options", options);
		session.setAttribute("userDetail", userDetail);
		
       // BhrEmpDemo userDetail = (BhrEmpDemo)session.getAttribute("userDetail");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        mav.addObject("userDetail", userDetail);
        return mav;
    }

    @RequestMapping(value = "changeLanguage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Boolean> changeLanguage(HttpServletRequest req, String language) throws IOException{
    	Map<String, Boolean> res = new HashMap<>();
    	req.getSession().setAttribute("language",language);
		String path = req.getSession().getServletContext().getRealPath("/") +"/static/js/lang/text-"+language+".json";
		File file = new File(path);
		String input = FileUtils.readFileToString(file, "UTF-8");
		JSONObject jsonObject = JSONObject.fromObject(input);
		req.getSession().setAttribute("languageJSON", jsonObject);
		
		//these strings should never be translated per esc20
		String path1 = req.getSession().getServletContext().getRealPath("/") +"/static/js/constant/text-non-translate.json";
		File file1 = new File(path1);
		String input1 = FileUtils.readFileToString(file1, "UTF-8");
		JSONObject jsonObject1 = JSONObject.fromObject(input1);
		req.getSession().setAttribute("constantJSON", jsonObject1);
		
		res.put("success", true);
	    return res;
    }
}
