package com.esc20.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.SearchUser;
import com.esc20.security.CustomSHA256Encoder;
import com.esc20.service.IndexService;
import com.esc20.util.CommonResult;
import com.esc20.util.MailUtil;

@Controller
@RequestMapping("/resetPassword")
public class ResetPasswordController {
	
	private Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);

	@Autowired
	private MailUtil mailUtil;
	
	@Autowired
	private IndexService indexService;

	@Autowired
	private CustomSHA256Encoder encoder;

	private final String module = "Reset Password";

	@RequestMapping("retrieveUserName")
	public ModelAndView retrieveUserName(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		if (req.getParameter("dateDay") == null || req.getParameter("dateMonth") == null
				|| req.getParameter("dateYear") == null || (req.getParameter("empNumber") == null && req.getParameter("ssn") == null)
				|| req.getParameter("zipCode") == null) {
			mav.setViewName("visitFailedUnAuth");
			mav.addObject("module", module);
			mav.addObject("action", "Retrieve user information");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
			
		Options options = this.indexService.getOptions();
		if(options.getIdType().equals(Options.IdType.Ssn)) {
			mav.addObject("idType", "S");
		} else {
			mav.addObject("idType", "E");
		}
		SearchUser searchUser = new SearchUser();
		searchUser.setDateDay(req.getParameter("dateDay"));
		searchUser.setDateMonth(req.getParameter("dateMonth"));
		searchUser.setDateYear(req.getParameter("dateYear"));
		searchUser.setEmpNumber(req.getParameter("empNumber"));
		searchUser.setSsn(req.getParameter("ssn"));
		searchUser.setZipCode(req.getParameter("zipCode"));

		BhrEmpDemo bed = this.indexService.retrieveEmployee(searchUser);
		if (bed == null) {
			mav.addObject("retrieve", "false");
			mav.setViewName("forgetPassword");
			return mav;
		} else {

			BeaUsers user = this.indexService.getUserByEmpNbr(bed.getEmpNbr());
			if (user == null) {
				mav.addObject("retrieve", "false");
				mav.setViewName("forgetPassword");
				mav.addObject("userNotRegistered", true);
				return mav;
			} else if (user.getLkHint() == 'Y') {
				mav.addObject("retrieve", "false");
				mav.setViewName("forgetPassword");
				mav.addObject("resetLocked", true);
				return mav;
			} else {
				searchUser.setUsername(user.getUsrname());
				searchUser.setUserEmail(bed.getEmail());
				searchUser.setUserHomeEmail(bed.getHmEmail());
				searchUser.setNameF(bed.getNameF());
				searchUser.setNameL(bed.getNameL());
				searchUser.setHintQuestion(user.getHint());
				mav.addObject("retrieve", "true");
			}

		}
		mav.setViewName("forgetPassword2");
		mav.addObject("user", searchUser);

		return mav;
	}
	
	//ALC-13 add new page called forgot username
	@RequestMapping(value = "recoverUserNameStep1", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CommonResult> recoverUserNameStep1(@RequestParam(required = false) String empNumber,@RequestParam(required = false) String ssn, @RequestParam String dateMonth, @RequestParam String dateDay, @RequestParam String dateYear, @RequestParam String zipCode) {
	 
		SearchUser searchUser = new SearchUser();
		searchUser.setDateDay(dateDay);
		searchUser.setDateMonth(dateMonth);
		searchUser.setDateYear(dateYear);
		searchUser.setEmpNumber(empNumber);
		searchUser.setZipCode(zipCode);
		searchUser.setSsn(ssn);
		
		CommonResult result = new CommonResult(0,"");
		BhrEmpDemo bed = this.indexService.retrieveEmployee(searchUser);
		if (bed != null) {
			BeaUsers user = this.indexService.getUserByEmpNbr(bed.getEmpNbr());
			if (user == null) {
				result.setCode(0);
			} else if (user.getLkHint() == 'Y') {
				result.setCode(1);
			} else {
				searchUser.setUsername(user.getUsrname());
				searchUser.setUserEmail(bed.getEmail());
				searchUser.setUserHomeEmail(bed.getHmEmail());
				searchUser.setNameF(bed.getNameF());
				searchUser.setNameL(bed.getNameL());
				searchUser.setHintQuestion(user.getHint());
				searchUser.setHintAnswer(user.getHintAns());
				result.setData(searchUser);
				result.setCode(2);
			}
		}
        
        return new ResponseEntity(result, HttpStatus.OK);
	 }
	
	//ALC-13 add new page called forgot username
	@RequestMapping(value = "recoverUserNameStep2", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CommonResult> recoverUserNameStep2(@RequestParam(required = false) String answer,  @RequestParam(required = false)  String hintAns) {
	 
		
		boolean match = false;
		try {
			match = encoder.matches(answer, hintAns) || BCrypt.checkpw(answer,hintAns);
		} catch (Exception e) {
			match = false;
		}
		
		CommonResult result = new CommonResult(0,"");
		if(match) {
			result.setCode(2);
		}
		
        return new ResponseEntity(result, HttpStatus.OK);
	 }

	@RequestMapping("answerHintQuestion")
	public ModelAndView answerHintQuestion(HttpServletRequest req, String answer, String empNbr, String socialSn, String email) {
		ModelAndView mav = new ModelAndView();
		if (answer == null || empNbr == null) {
			mav.setViewName("visitFailedUnAuth");
			mav.addObject("module", module);
			mav.addObject("action", "Answer hint question");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		if(empNbr.length() == 0){
		BhrEmpDemo userInQuestion = this.indexService.getUserNameFromSsn(socialSn);
		empNbr = userInQuestion.getEmpNbr();
		}
		SearchUser searchUser = new SearchUser();
		BeaUsers beaUser = this.indexService.getUserByEmpNbr(empNbr);
		Integer count = beaUser.getTmpCnt();
		BeaUsers user = this.indexService.getUserByEmpNbr(empNbr);
		BhrEmpDemo bed = this.indexService.getUserDetail(empNbr);
		searchUser.setDateDay(bed.getDob().substring(6, 8));
		searchUser.setDateMonth(bed.getDob().substring(4, 6));
		searchUser.setDateYear(bed.getDob().substring(0, 4));
		searchUser.setEmpNumber(empNbr);
		searchUser.setUsername(user.getUsrname());
		searchUser.setUserEmail(bed.getEmail());
		searchUser.setUserHomeEmail(bed.getHmEmail());
		searchUser.setNameF(bed.getNameF());
		searchUser.setNameL(bed.getNameL());
		searchUser.setHintQuestion(user.getHint());
		searchUser.setZipCode(bed.getAddrZip());
		boolean match = false;
		try {
			match = encoder.matches(answer, user.getHintAns()) || BCrypt.checkpw(answer, user.getHintAns());
		} catch (IllegalArgumentException e) {
			match = false;
		}
		if (match) {
			// TODO: send email
			boolean passwordSuccess = false;
			String password = "";
			boolean hasUpper = false;
			boolean hasLower = false;
			boolean hasDigit = false;
			while (!passwordSuccess) {
				password = generateTempPassword();

				for (char c : password.toCharArray()) {
					if (Character.isUpperCase(c)) {
						hasUpper = true;
					}
					if (Character.isLowerCase(c)) {
						hasLower = true;
					}
					if (Character.isDigit(c)) {
						hasDigit = true;
					}
					if (hasUpper && hasLower && hasDigit) {
						passwordSuccess = true;
						break;
					}
				}
			}
			int emailSuccess = sendEmail(searchUser, email, password);
			if (emailSuccess == 1) {
				emailSuccess = sendEmail(searchUser, email, password);
			}
			if (emailSuccess == 1) {
				mav.setViewName("login");
				mav.addObject("resetSuccess", false);
			} else {
				mav.setViewName("login");
				user.setUsrpswd(encoder.encode(password));
				//set temp dts so that password expires after one day
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				user.setTmpDts(sdf.format(new Date()));
				this.indexService.updateUser(user);
				mav.addObject("resetSuccess", true);
			}
		} else {
			count++;
			user.setTmpDts(user.getTmpDts() == null ? "" : user.getTmpDts());
			user.setTmpCnt(count);
			this.indexService.updateUser(user);
			if (count >= 3) {
				mav.setViewName("login");
				mav.addObject("times3", true);
				user.setLkHint('Y');
				this.indexService.updateUser(user);
				return mav;
			}
			mav.setViewName("forgetPassword2");
			mav.addObject("errorMessage", "The answer is wrong!");
			mav.addObject("count", count);
		}
		mav.addObject("user", searchUser);
		return mav;
	}

	@RequestMapping("forgetPassword")
	public ModelAndView forgetPassword(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forgetPassword");
		//ALC-26 update EP password to get settings from DB
		Map<String, String> preferences = indexService.getTxeisPreferences();
		req.getSession().setAttribute("txeisPreferences", preferences);
		
		Options options = this.indexService.getOptions();
		if(options.getIdType().equals(Options.IdType.Ssn)) {
			mav.addObject("idType", "S");
		} else {
			mav.addObject("idType", "E");
		}
		return mav;
	}
	
	//ALC-13 add new page called forgot username
	@RequestMapping("forgetUsername")
	public ModelAndView forgetUsername(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forgetUsername");
		Map<String, String> preferences = indexService.getTxeisPreferences();
		req.getSession().setAttribute("txeisPreferences", preferences);
		
		Options options = this.indexService.getOptions();
		if(options.getIdType().equals(Options.IdType.Ssn)) {
			mav.addObject("idType", "S");
		} else {
			mav.addObject("idType", "E");
		}
		return mav;
	}

	@RequestMapping("updatePassword")
	public ModelAndView updatePassword(HttpServletRequest req, String password, String id) {
		ModelAndView mav = new ModelAndView();
		//ALC-26 update EP password to get settings from DB
		Map<String, String> preferences = indexService.getTxeisPreferences();
		req.getSession().setAttribute("txeisPreferences", preferences);
		
		if (password == null || id == null) {
			mav.setViewName("visitFailedUnAuth");
			mav.addObject("module", module);
			mav.addObject("action", "Update user password");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("login");

		try {
			BeaUsers user = this.indexService.getUserByEmpNbr(id);
			user.setUsrpswd(encoder.encode(password));
			user.setTmpDts(user.getTmpDts() == null ? "" : user.getTmpDts());
			user.setTmpCnt(0);
			user.setLkHint('N');
			user.setHintCnt(0);
			this.indexService.updateUser(user);

		} catch (Exception e) {
			logger.info("resetPswFailed : "+ e.getMessage());
			mav.addObject("resetPsw", "resetPswFaild");
		}
		mav.addObject("resetPsw", "resetPswSuccess");
		return mav;
	}

	public String generateTempPassword() {
		String lowerCharSet = "abcdefghijklmnopqrstuvwxyz";
		String upperCharSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numericSet = "0123456789";
		StringBuffer typeSet = new StringBuffer("");

		Random randGenerator = new Random();

		StringBuffer tempPassword = new StringBuffer("");

		int length = (randGenerator.nextInt(4)) + 6;

		for (int i = 0; i < length; i++) {
			int tempNum = (randGenerator.nextInt(3));
			if (tempNum == 0) {
				typeSet.append("u");
			} else if (tempNum == 1) {
				typeSet.append("l");
			} else if (tempNum == 2) {
				typeSet.append("n");
			}

		}

		for (int i = 0; i < length; i++) {

			if (typeSet.charAt(i) == 'u') {
				int tempNum = (randGenerator.nextInt((upperCharSet.length())));
				tempPassword.append(upperCharSet.charAt(tempNum));
			} else if (typeSet.charAt(i) == 'l') {
				int tempNum = (randGenerator.nextInt((lowerCharSet.length())));
				tempPassword.append(lowerCharSet.charAt(tempNum));
			} else if (typeSet.charAt(i) == 'n') {
				int tempNum = (randGenerator.nextInt((numericSet.length())));
				tempPassword.append(numericSet.charAt(tempNum));
			}

		}
		return tempPassword.toString();

	}

	public Integer sendEmail(SearchUser user, String emailSelected, String password) {
		StringBuilder messageContents = new StringBuilder();
		messageContents.append("Temporary Password: " + password + "\n");
		messageContents.append("<p>Your temporary password will expire within 24 hours.</p>");
		
		/*DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		messageContents.append(dateFormat.format(cal.getTime()) + "\n");*/
		messageContents.append("<p>*****THIS IS AN AUTOMATED MESSAGE. PLEASE DO NOT REPLY*****</p>");

		String subject = "Temporary Password Generated";
		try {
			mailUtil.sendEmail(emailSelected, subject, messageContents.toString());
		} catch (Exception ex) {
			//ex.printStackTrace();
			logger.info("send Email Failed when Reset Password : "+ ex.getMessage());
			return 1;
		}
		return 0;
	}
}
