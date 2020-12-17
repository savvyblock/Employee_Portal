package com.esc20.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaEmail;
import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.SearchUser;
import com.esc20.security.CustomSHA256Encoder;
import com.esc20.service.IndexService;
import com.esc20.util.MailUtil;

@Controller
@RequestMapping("/createUser")
public class CreateUserController {
	
	private Logger logger = LoggerFactory.getLogger(CreateUserController.class);

	@Autowired
	private MailUtil mailUtil;
	
	@Autowired
	private IndexService indexService;

	@Autowired
	private CustomSHA256Encoder encoder;

	private final String module = "Create New User";

	@RequestMapping("searchUser")
	public ModelAndView searchUser(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("searchUser");
		Options options = this.indexService.getOptions();
		if (options.getIdType().equals(Options.IdType.Ssn)) {
			mav.addObject("idType", "S");
		} else {
			mav.addObject("idType", "E");
		}
		return mav;
	}

	@RequestMapping("createNewUser")
	public ModelAndView createNewUser(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		//ALC-26 update EP password to get settings from DB
		Map<String, String> preferences = indexService.getTxeisPreferences();
		req.getSession().setAttribute("txeisPreferences", preferences);
		
		mav.setViewName("createNewUser");
		return mav;
	}

	@RequestMapping(value = "saveNewUser", method = RequestMethod.POST)
    @ResponseBody
	public Map<String, String> saveNewUser(HttpServletRequest req) {
		Map<String, String> res = new HashMap<>();
		if (req.getParameter("empNbr") == null || req.getParameter("username") == null
				|| req.getParameter("hintQuestion") == null || req.getParameter("hintAnswer") == null
				|| req.getParameter("password") == null) {
			res.put("success", "false");
			return res;
		}
		BeaUsers newUser = new BeaUsers();
		newUser.setEmpNbr(req.getParameter("empNbr"));
		newUser.setUsrname(req.getParameter("username"));// username
		newUser.setHint(req.getParameter("hintQuestion"));// hintQuestion
		newUser.setHintAns(encoder.encode(req.getParameter("hintAnswer")));// hintAnswer
		// newUser.setUserEmail(req.getParameter("workEmail"));//workEmail
		newUser.setUsrpswd(encoder.encode(req.getParameter("password")));

		newUser.setLkPswd('N');
		newUser.setPswdCnt(0);
		newUser.setLkFnl('N');
		newUser.setTmpDts("");
		newUser.setTmpCnt(0);
		newUser.setHintCnt(0);

		BhrEmpDemo bed = this.indexService.getUserDetail(req.getParameter("empNbr"));
		SearchUser searchUser = new SearchUser();
		searchUser.setDateDay(bed.getDob().substring(6, 8));
		searchUser.setDateMonth(bed.getDob().substring(4, 6));
		searchUser.setDateYear(bed.getDob().substring(0, 4));
		searchUser.setEmpNumber(req.getParameter("empNbr"));
		searchUser.setUserEmail(bed.getEmail());
		searchUser.setNameF(bed.getNameF());
		searchUser.setNameL(bed.getNameL());
		searchUser.setHintQuestion(req.getParameter("hintQuestion"));
		searchUser.setZipCode(bed.getAddrZip());
		BeaUsers user = indexService.getUserByUsername(req.getParameter("username"));
		if (user != null) {
			res.put("isUserExist", "true");
			res.put("success", "false");
			return res;
		} else {
			try {
				this.indexService.updateEmailEmployee(newUser.getEmpNbr(), req.getParameter("workEmail"),
						req.getParameter("homeEmail"));
				indexService.saveBeaUsers(newUser);
				//Send Out Emails
				sendEmail(newUser.getUsrname(),bed.getHmEmail(),bed.getEmail());
				
				res.put("isUserExist", "true");
				res.put("success", "true");
				res.put("username", req.getParameter("username"));
				res.put("password", req.getParameter("password"));
			}
			catch (Exception ex) {
				ex.printStackTrace();
				res.put("success", "false");
				return res;
			}
		}

		return res;
	}

	@RequestMapping(value = "retrieveEmployee", method = RequestMethod.POST)
	public ModelAndView retrieveEmployee(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		if (req.getParameter("dateDay") == null || req.getParameter("dateMonth") == null
				|| req.getParameter("dateYear") == null
				|| (req.getParameter("empNumber") == null && req.getParameter("ssn") == null)
				|| req.getParameter("zipCode") == null) {
			mav.setViewName("visitFailedUnAuth");
			mav.addObject("module", module);
			mav.addObject("action", "Retrieve user information");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		//ALC-13 remove useless codes
		/*Options options = this.indexService.getOptions();
		if (options.getIdType().equals(Options.IdType.Ssn)) {
			mav.addObject("idType", "S");
		} else {
			mav.addObject("idType", "E");
		}*/
		SearchUser searchUser = new SearchUser();
		searchUser.setDateDay(req.getParameter("dateDay"));
		searchUser.setDateMonth(req.getParameter("dateMonth"));
		searchUser.setDateYear(req.getParameter("dateYear"));
		searchUser.setEmpNumber(req.getParameter("empNumber"));
		searchUser.setSsn(req.getParameter("ssn"));
		searchUser.setZipCode(req.getParameter("zipCode"));
		BhrEmpDemo bed = this.indexService.retrieveEmployee(searchUser);
		
		if (bed == null) {
			//mav.setViewName("searchUser");
			mav.addObject("isSuccess", "false");
			mav.addObject("newUser", searchUser);
			return mav;
		}
		BeaUsers user = this.indexService.getUserByEmpNbr(bed.getEmpNbr());
		if (user != null) {
		//	mav.setViewName("searchUser");
			mav.addObject("isExistUser", "true");
			mav.addObject("newUser", searchUser);
			return mav;
		} else {
			searchUser.setEmpNumber(bed.getEmpNbr());
			BeaEmail emailRequest = this.indexService.getBeaEmail(bed);
			searchUser.setNameF(bed.getNameF());
			searchUser.setNameL(bed.getNameL());
			searchUser.setUserEmail(bed.getEmail());
			searchUser.setUserHomeEmail(bed.getHmEmail());
			mav.setViewName("createNewUser");
			mav.addObject("user", searchUser);
			mav.addObject("emailShowRequest", emailRequest);
			return mav;
		}
	}

	private void sendEmail(String userName, String userHomeEmail, String userWorkEmail)
	{
		String subject ="New User Created";
		StringBuilder messageContents = new StringBuilder();
		messageContents.append("<p>Thank you for Registering for EmployeePortal.  Your User ID is: "+userName +" </p>");		
		messageContents.append("<p>*****THIS IS AN AUTOMATED MESSAGE. PLEASE DO NOT REPLY*****</p>");
		
		String toEmail ="";
		if (!"".equals(userWorkEmail)) {
			toEmail = userWorkEmail;
		} else if (!"".equals(userHomeEmail)) {
			toEmail = userHomeEmail;
		}
		
		if (toEmail!=null && toEmail.trim().length() > 0) {
			try{
				mailUtil.sendEmail(toEmail, subject, messageContents.toString());
			} 
			catch(Exception ex) {
				logger.info("An exception has occured with mailing the user.");
			} 
		} else {
			logger.info("Create new user: Unable to send an email.  No email address is avaiable for user "+userName+".");
		}
	}
	
}
