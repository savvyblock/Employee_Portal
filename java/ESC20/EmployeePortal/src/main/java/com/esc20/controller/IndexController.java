package com.esc20.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaEmail;
import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.SearchUser;
import com.esc20.security.CustomSHA256Encoder;
import com.esc20.service.IndexService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;
import com.esc20.util.FileDownloadUtil;
import com.esc20.util.FileUtil;
import com.esc20.util.StringUtil;
import com.google.inject.spi.Message;
import com.esc20.util.BrowserInfoService;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/")
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
//	@Value("${portal.help.url}")
    private String helpUrl;
	
	@Autowired
	private IndexService indexService;

	@Autowired
	private ReferenceService referenceService;
	
	@Autowired
	private CustomSHA256Encoder encoder;
	
	@Autowired
	private BrowserInfoService browserService;	

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView getIndexPage(HttpServletRequest req, String Id, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		
		//ALC-26 update EP password to get settings from DB
		Map<String, String> preferences = indexService.getTxeisPreferences();
		req.getSession().setAttribute("txeisPreferences", preferences);
		
		Boolean isUserLoginFailure = (Boolean) req.getSession().getAttribute("isUserLoginFailure");
		if (isUserLoginFailure != null && isUserLoginFailure) {
			req.getSession().removeAttribute("isUserLoginFailure");
			mav.addObject("isUserLoginFailure", "true");
		}

		//ALC-13 add iType to Login page for search
		Options options = this.indexService.getOptions();
		if (options.getIdType().equals(Options.IdType.Ssn)) {
			mav.addObject("idType", "S");
		} else {
			mav.addObject("idType", "E");
		}
		
		//alert message
        Properties properties = new Properties();
        Scanner s = null;
        String line = "";
        StringBuffer sbf  = new StringBuffer();
        try {
                properties.load(this.getClass().getResourceAsStream("/txeis.properties"));
                String messageDir = properties.getProperty("messageDir");
                String alertFileName = messageDir + "/alert.txt";
                BufferedReader br = new BufferedReader(new FileReader(alertFileName));
                {
                    while ((line = br.readLine()) != null) 
                    {
                        sbf.append(line);
                        logger.debug(line);
                    }
                }
        }
        catch (IOException e) {
            logger.debug("Login", e);
        }finally{
            if(s!=null){
                s.close();
            }
        }
        mav.addObject("alertMsg", sbf.toString());
        req.getSession().setAttribute("helpLinkFromProperties", helpUrl+"employeeportal/doku.php");
        return mav;
    }

	@RequestMapping("markTimeout")
	@ResponseBody
	public JSONObject markTimeout(HttpServletRequest req, String Id, HttpServletResponse response) {
		HttpSession session = req.getSession();
		JSONObject result = new JSONObject();
		session.setAttribute("isTimeOut", true);
		result.put("isSuccess", "true");
		return result;
	}

	@RequestMapping("home")
	public ModelAndView getHome(HttpServletRequest req, HttpServletResponse response) throws Exception {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
		Options options = this.indexService.getOptions();

		Boolean isSupervisor = this.indexService.isSupervisor(user.getEmpNbr(), options.getUsePMISSpvsrLevels());
		Boolean isTempApprover = this.indexService.isTempApprover(user.getEmpNbr());
		session.setAttribute("isSupervisor", isSupervisor);
		session.setAttribute("isTempApprover", isTempApprover);
		String districtId = (String)session.getAttribute("districtId"); 
		// District  districtInfo = this.indexService.getDistrict(district);
		
		userDetail.setEmpNbr(user.getEmpNbr());
		userDetail.setDob(DateUtil.formatDate(userDetail.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
		List<Code> gens = referenceService.getGenerations();
		for (Code gen : gens) {
			if (userDetail.getNameGen() != null
					&& gen.getCode().trim().equals(userDetail.getNameGen().toString().trim())) {
				userDetail.setGenDescription(gen.getDescription());
			}
		}
		/*
		 * String phone = districtInfo.getPhone();
		 * districtInfo.setPhone(StringUtil.left(phone, 3)+"-"+StringUtil.mid(phone, 4,
		 * 3)+"-"+StringUtil.right(phone, 4));
		 */

		session.setAttribute("options", options);
		session.setAttribute("userDetail", userDetail);
		
		// BhrEmpDemo userDetail = (BhrEmpDemo)session.getAttribute("userDetail");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		mav.addObject("userDetail", userDetail);
		
		String changePSW = req.getParameter("changePSW");
		if(!StringUtil.isNullOrEmpty(changePSW)) {
			mav.addObject("changePSW", changePSW);
		}
		
		//version info		
		Properties prop = new Properties();
		prop.load(req.getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF"));		
		mav.addObject("districtId", districtId);
		mav.addObject("versionNumber", prop.getProperty("Project-Version"));
		mav.addObject("timeStamp", prop.getProperty("Timestamp"));
		mav.addObject("release", prop.getProperty("Build_To_Be_Released"));
		
		//browser info
		String userAgent = (String)req.getHeader("User-Agent");
		browserService.createBrowserInfo(userAgent);
		
		session.setAttribute("browserName", browserService.getBrowserName());
		session.setAttribute("browserVersion", browserService.getBrowserVersion());
		
//		mav.addObject("userName", SecurityContextHolder.getContext().getAuthentication().getName());
		String localHostAddress = InetAddress.getLocalHost().getHostAddress();
		String maskedLocalHostAddress = "XXX.XXX.XXX." + localHostAddress.substring(localHostAddress.length()-2);
		session.setAttribute("hostAddress", maskedLocalHostAddress);
		
		return mav;
	}
	
	@RequestMapping("updatePassword")
	public ModelAndView updatePassword(HttpServletRequest req, String password) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		
		//ALC-26 update EP password to get settings from DB
		Map<String, String> preferences = indexService.getTxeisPreferences();
		req.getSession().setAttribute("txeisPreferences", preferences);
		
		ModelAndView mav = new ModelAndView();
		if (password == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", "Home");
			mav.addObject("action", "Update Password");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		if (StringUtils.isEmpty(password)) {
			mav = new ModelAndView("redirect:/home");
			return mav;
		}
		user.setUsrpswd(encoder.encode(password));
		user.setTmpDts("");
		user.setTmpCnt(0);
		user.setLkHint('N');
		user.setHintCnt(0);
		this.indexService.updateUser(user);
		//Send out Email to User
		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
		this.indexService.passwordChangeSendEmailConfirmation(user.getUsrname(),userDetail.getNameF(),userDetail.getNameL(),userDetail.getHmEmail(),userDetail.getEmail());

		mav = new ModelAndView("redirect:/home");
		return mav;
	}
	
	@RequestMapping(value = "changeLanguage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> changeLanguage(HttpServletRequest req, String language) throws IOException {
		Map<String, Boolean> res = new HashMap<>();
		req.getSession().setAttribute("language", language);
		String path = req.getSession().getServletContext().getRealPath("/") + "/static/js/lang/text-" + language
				+ ".json";
		File file = new File(path);
		String input = FileUtils.readFileToString(file, "UTF-8");
		JSONObject jsonObject = JSONObject.fromObject(input);
		req.getSession().setAttribute("languageJSON", jsonObject);

		// these strings should never be translated per esc20
		String path1 = req.getSession().getServletContext().getRealPath("/")
				+ "/static/js/constant/text-non-translate.json";
		File file1 = new File(path1);
		String input1 = FileUtils.readFileToString(file1, "UTF-8");
		JSONObject jsonObject1 = JSONObject.fromObject(input1);
		req.getSession().setAttribute("constantJSON", jsonObject1);

		res.put("success", true);
		return res;
	}

	 //ALC-13 added the picture in login page
    @RequestMapping("/getDistrictPicture/{districtId}")
	public void getDistrictPicture(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String districtId) {
		String picturePath = FileUtil.getDistrictPicPhysicalPath(request);
		//ALC-13  if there is not pic in folder then did not show up the picture
		if(!StringUtil.isNullOrEmpty(picturePath)) {
			FileDownloadUtil.downloadPictureFile(request, picturePath, response);
		}	
	}
    
    //ALC-13 add method to check if the user exist or not
	@RequestMapping(value = "isUserExisted", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject isUserExisted(String username) {
		JSONObject res = new JSONObject();
		Boolean isExisted = false;
		BeaUsers user = this.indexService.getUserByUsername(username);
		if (user != null) {
			isExisted = true;
		} 
		res.put("isExisted", isExisted);	
		return res;
	}
	
}
