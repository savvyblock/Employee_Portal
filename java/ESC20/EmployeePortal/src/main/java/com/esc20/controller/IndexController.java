package com.esc20.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaEmpLvComments;
import com.esc20.model.BeaEmpLvRqst;
import com.esc20.model.BeaEmpLvWorkflow;
import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEapOpt;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.CurrentPayInformation;
import com.esc20.nonDBModels.LeaveEmployeeData;
import com.esc20.nonDBModels.LeaveInfo;
import com.esc20.nonDBModels.LeaveParameters;
import com.esc20.nonDBModels.LeaveRequestModel;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.TravelRequestCalendar;
import com.esc20.nonDBModels.TravelRequestInfo;
import com.esc20.security.CustomSHA256Encoder;
import com.esc20.service.IndexService;
import com.esc20.service.InquiryService;
import com.esc20.service.LeaveRequestService;
import com.esc20.service.LicenseAgreementService;
import com.esc20.service.ReferenceService;
import com.esc20.service.TravelRequestService;
import com.esc20.service.WrkjlService;
import com.esc20.util.BrowserInfoService;
import com.esc20.util.DateUtil;
import com.esc20.util.FileDownloadUtil;
import com.esc20.util.FileUtil;
import com.esc20.util.SessionKeys;
import com.esc20.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.esc20.txeis.WorkflowLibrary.domain.WorkflowType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/")
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
//	@Value("${portal.help.url}")
//	@Value("https://help.ascendertx.com/test/")
    private String helpUrl;
	
	@Autowired
	private IndexService indexService;

	@Autowired
	private ReferenceService referenceService;
	
	@Autowired
	private CustomSHA256Encoder encoder;
	
	@Autowired
	private BrowserInfoService browserService;
	
	@Autowired
	private LeaveRequestService leaveRequestService;
	
	@Autowired
	private TravelRequestService travelRequestService;

	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private WrkjlService wrkjlService;
	
	// ALC-35
	@Autowired
	LicenseAgreementService licenseAgreementService;
	@Value("${portal.help.url}")
	private String helpSettingUrl;
	
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView getIndexPage(HttpServletRequest req, String Id, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		//ALC-26 update EP password to get settings from DB
		Map<String, String> preferences = indexService.getTxeisPreferences();
		req.getSession().setAttribute("txeisPreferences", preferences);
		
		
		Boolean isUserLoginFailure = (Boolean) req.getSession().getAttribute("isUserLoginFailure");
		if (isUserLoginFailure != null && isUserLoginFailure) {
			//ALC-26 Lock account on the 5th login failed
			String userLoginErrorMsg = (String) req.getSession().getAttribute(SessionKeys.USER_LOGIN_ERROR_MSG);
			req.getSession().removeAttribute("isUserLoginFailure");
			mav.addObject("isUserLoginFailure", "true");			
			req.getSession().removeAttribute(SessionKeys.IS_USER_LOGIN_FAILURE);
			req.getSession().removeAttribute("userLoginErrorMsg");
			mav.addObject("userLoginErrorMsg", userLoginErrorMsg);
		}

		//ALC-13 add iType to Login page for search
		try {
			Options options = this.indexService.getOptions();
			if (options.getIdType().equals(Options.IdType.Ssn)) {
				mav.addObject("idType", "S");
			} else {
				mav.addObject("idType", "E");
			}
		}
		catch(Exception ex) { //ALC-26 fixed error issue when districtid did not correct
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
                    	//ALC-13 formatted text
						line+='\n';
                    	sbf.append(line);
                        logger.debug(line);
                    }
                }
				br.close();
        }
        catch (IOException e) {
            logger.debug("Login", e);
        }finally{
            if(s!=null){
                s.close();
            }
        }
        mav.addObject("alertMsg", sbf.toString());
        helpUrl = properties.getProperty("portal.help.url");
        req.getSession().setAttribute("helpLinkFromProperties", helpUrl +"employeeportal/doku.php");
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
	public ModelAndView getHome(HttpServletRequest req, HttpServletResponse response, String freq, Boolean isAdd) throws Exception {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
		Options options = this.indexService.getOptions();

		Boolean isSupervisor = this.indexService.isSupervisor(user.getEmpNbr(), options.getUsePMISSpvsrLevels());
		Boolean isTempApprover = this.indexService.isTempApprover(user.getEmpNbr());
		Boolean isTravelApprover = this.travelRequestService.isTravelApprover(user.getEmpNbr(), WorkflowType.TRAVEL.getId());
		session.setAttribute("isSupervisor", isSupervisor);
		session.setAttribute("isTempApprover", isTempApprover);
		session.setAttribute("isTravelApprover", isTravelApprover);
		String districtId = (String)session.getAttribute("srvcId"); 
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
		
		// Appended Leave Request Calendar View Start
		if (isAdd == null) {
			isAdd = false;
		}
		if (isAdd) {
			mav.addObject("addRow", true);
		} else {
			mav.addObject("addRow", false);
		}
		AppLeaveRequest request = new AppLeaveRequest();
		// BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		List<Code> availableFreqs = leaveRequestService.getAvailableFrequencies(userDetail.getEmpNbr());
		mav.addObject("availableFreqs", availableFreqs);
		LeaveParameters params = leaveRequestService.getLeaveParameters();
		mav.addObject("params", params);
		String supervisorEmpNbr = leaveRequestService.getFirstLineSupervisor(userDetail.getEmpNbr(),
				params.isUsePMIS()) == null ? null
						: leaveRequestService.getFirstLineSupervisor(userDetail.getEmpNbr(), params.isUsePMIS())
								.getEmployeeNumber();
		if (supervisorEmpNbr == null) {
			supervisorEmpNbr = "";
			mav.addObject("haveSupervisor", false);
		} else {
			mav.addObject("haveSupervisor", true);
		}
		List<AppLeaveRequest> requests = new ArrayList<AppLeaveRequest>();
		if (freq == null || ("").equals(freq)) {
			if (availableFreqs.size() > 0) {
				freq = availableFreqs.get(0).getCode();
				requests = leaveRequestService.getLeaveRequests(request, userDetail.getEmpNbr(), freq);
				List<LeaveRequestModel> requestModels = new ArrayList<LeaveRequestModel>();
				List<Code> leaveStatus = this.referenceService.getLeaveStatus();
				// List<Code> gens = referenceService.getGenerations();
				LeaveRequestModel model;
				JSONArray json = new JSONArray();
				AppLeaveRequest temp;
				for (int i = 0; i < requests.size(); i++) {
					temp = requests.get(i);
					temp.setFirstName(userDetail.getNameF());
					temp.setLastName(userDetail.getNameL());
					model = new LeaveRequestModel(temp);
					requestModels.add(model);
				}
				List<LeaveInfo> leaveInfo = leaveRequestService.getLeaveInfo(userDetail.getEmpNbr(), freq, false);
				Code emptyCode = new Code();
				// List<Code> absRsns = this.service.getAbsRsns(demo.getEmpNbr(), freq, "");
				List<Code> absRsns = new ArrayList<Code>();
				// Add a blank for absRsns for default shown
				emptyCode = new Code();
				emptyCode.setDescription(" ");
				absRsns.add(emptyCode);
				absRsns.addAll(leaveRequestService.getAbsRsns(userDetail.getEmpNbr(), freq, ""));

				JSONArray absRsnsJson = new JSONArray();
				for (int i = 0; i < absRsns.size(); i++) {
					absRsnsJson.add(absRsns.get(i).toJSON());
				}
				List<Code> leaveTypes = new ArrayList<Code>();

				// Add a blank for leave Types for default shown
				emptyCode = new Code();
				emptyCode.setDescription(" ");
				leaveTypes.add(emptyCode);
				leaveTypes.addAll(leaveRequestService.getLeaveTypes(userDetail.getEmpNbr(), freq, ""));
				JSONArray leaveTypesJson = new JSONArray();
				for (int i = 0; i < leaveTypes.size(); i++) {
					leaveTypesJson.add(leaveTypes.get(i).toJSON());
				}
				for (int i = 0; i < requestModels.size(); i++) {
					json.add(requestModels.get(i).toJSON(leaveStatus, leaveTypes, null, gens));
				}
				
				//add a blank for job code for default shown
				List<CurrentPayInformation> empJobCd = new ArrayList<CurrentPayInformation>();
				CurrentPayInformation emptyJobCd = new CurrentPayInformation();
				empJobCd.add(emptyJobCd);
				empJobCd.addAll(inquiryService.getEmpJobs(userDetail.getEmpNbr()));
				

				List<String[]> map = leaveRequestService.mapReasonsAndLeaveTypes();
				JSONArray mapJson = new JSONArray();
				JSONObject tempMap;
				for (int i = 0; i < map.size(); i++) {
					tempMap = new JSONObject();
					tempMap.put("absRsn", map.get(i)[0]);
					for (int j = 0; j < absRsns.size(); j++) {
						if (absRsns.get(j).getCode().equals(map.get(i)[0])) {
							tempMap.put("absRsnDescrption", absRsns.get(j).getDescription());
						}
					}
					tempMap.put("leaveType", map.get(i)[1]);
					mapJson.add(tempMap);
				}
				mav.addObject("selectedFreq", freq);
				mav.addObject("absRsns", absRsnsJson);
				mav.addObject("leaveTypes", leaveTypesJson);
				mav.addObject("leaveTypesAbsrsnsMap", mapJson);
				mav.addObject("leaveInfo", leaveInfo);
				mav.addObject("leaves", json);
				mav.addObject("empJobCd", empJobCd);

			}
		} else {
			requests = leaveRequestService.getLeaveRequests(request, userDetail.getEmpNbr(), freq);
			List<LeaveRequestModel> requestModels = new ArrayList<LeaveRequestModel>();
			List<Code> leaveStatus = this.referenceService.getLeaveStatus();
			// List<Code> gens = referenceService.getGenerations();
			LeaveRequestModel model;
			JSONArray json = new JSONArray();
			AppLeaveRequest temp;
			for (int i = 0; i < requests.size(); i++) {
				temp = requests.get(i);
				temp.setFirstName(userDetail.getNameF());
				temp.setLastName(userDetail.getNameL());
				model = new LeaveRequestModel(temp);
				requestModels.add(model);
			}
			List<LeaveInfo> leaveInfo = leaveRequestService.getLeaveInfo(userDetail.getEmpNbr(), freq, false);
			Code emptyCode = new Code();
			// List<Code> absRsns = this.service.getAbsRsns(demo.getEmpNbr(), freq, "");
			List<Code> absRsns = new ArrayList<Code>();
			// Add a blank for absRsns for default shown
			emptyCode = new Code();
			emptyCode.setDescription(" ");
			absRsns.add(emptyCode);
			absRsns.addAll(leaveRequestService.getAbsRsns(userDetail.getEmpNbr(), freq, ""));
			JSONArray absRsnsJson = new JSONArray();
			for (int i = 0; i < absRsns.size(); i++) {
				absRsnsJson.add(absRsns.get(i).toJSON());
			}
			List<Code> leaveTypes = new ArrayList<Code>();

			// Add a blank for leave Types for default shown
			emptyCode = new Code();
			emptyCode.setDescription(" ");
			leaveTypes.add(emptyCode);
			leaveTypes.addAll(leaveRequestService.getLeaveTypes(userDetail.getEmpNbr(), freq, ""));
			JSONArray leaveTypesJson = new JSONArray();
			for (int i = 0; i < leaveTypes.size(); i++) {
				leaveTypesJson.add(leaveTypes.get(i).toJSON());
			}
			for (int i = 0; i < requestModels.size(); i++) {
				json.add(requestModels.get(i).toJSON(leaveStatus, leaveTypes, null, gens));
			}
			List<String[]> map = leaveRequestService.mapReasonsAndLeaveTypes();
			JSONArray mapJson = new JSONArray();
			JSONObject tempMap;
			for (int i = 0; i < map.size(); i++) {
				tempMap = new JSONObject();
				tempMap.put("absRsn", map.get(i)[0]);
				for (int j = 0; j < absRsns.size(); j++) {
					if (absRsns.get(j).getCode().equals(map.get(i)[0])) {
						tempMap.put("absRsnDescrption", absRsns.get(j).getDescription());
					}
				}
				tempMap.put("leaveType", map.get(i)[1]);
				mapJson.add(tempMap);
			}
			mav.addObject("selectedFreq", freq);
			mav.addObject("absRsns", absRsnsJson);
			mav.addObject("leaveTypes", leaveTypesJson);
			mav.addObject("leaveTypesAbsrsnsMap", mapJson);
			mav.addObject("leaveInfo", leaveInfo);
			mav.addObject("leaves", json);
		}
		// Appended Leave Request Calendar View End
		
		// Appended Travel Request Calendar View Start
		TravelRequestInfo travelRequestInfo = new TravelRequestInfo();
		List<TravelRequestInfo> travelcampuses = travelRequestService.getCampusPay(userDetail.getEmpNbr());
		if (travelcampuses.size() > 1) {
			mav.addObject("travelcampuses", travelcampuses);
		}
		
		travelRequestInfo.setCampus(travelRequestService.getReimburseCampus(userDetail.getEmpNbr()));
		mav.addObject("travelRequestInfo", travelRequestInfo);

		List<TravelRequestCalendar> travelList = new ArrayList<TravelRequestCalendar>();
		List<String> distinctTripNbr = travelRequestService.getDistinctTripNumber(userDetail.getEmpNbr());
		
		for (String tripNums : distinctTripNbr) {
			String firstName = userDetail.getNameF();
			String lastName = userDetail.getNameL();
			int tripColspan = travelRequestService.getTripCount(userDetail.getEmpNbr(), tripNums);
			if (tripColspan > 1) {
				List<TravelRequestCalendar> travelRequestExtended = new ArrayList<TravelRequestCalendar>();
				travelRequestExtended = travelRequestService.getTravelRequestCalendarParameters(userDetail.getEmpNbr(), tripNums);
				TravelRequestCalendar firstList = travelRequestExtended.get(0);
				TravelRequestCalendar lastList = travelRequestExtended.get(travelRequestExtended.size() - 1);
				String purposeTitle = compareTravelPurpose(travelRequestExtended);
				String title = firstName + " " + lastName + " : " + purposeTitle;
				String firstDate = firstList.getTrvlDt();
				String lastDate = getNextDate(lastList.getTrvlDt(), 1);
				travelRequestExtended.get(0).setTitle(title);
				travelRequestExtended.get(0).setStart(firstDate);
				travelRequestExtended.get(0).setEnd(lastDate);
				travelList.addAll(travelRequestExtended);
			} else {
				List<TravelRequestCalendar> travelRequestMileage = new ArrayList<TravelRequestCalendar>();
				travelRequestMileage = travelRequestService.getTravelRequestCalendarParameters(userDetail.getEmpNbr(), tripNums);
				for (TravelRequestCalendar itemsMileage : travelRequestMileage) {
					String title = firstName + " " + lastName + " : " + itemsMileage.getPurpose();
					itemsMileage.setTitle(title);
					itemsMileage.setStart(itemsMileage.getTrvlDt());
					itemsMileage.setEnd(itemsMileage.getTrvlDt());
					travelList.addAll(travelRequestMileage);
				}
			}
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(travelList);
		} catch (JsonProcessingException e) {
			logger.error("Error in parsing json object parsing :", e);
		}
		mav.addObject("trvlReqCalParams", json);
		
		//wrkjl
		//Gillian: Need to check back when check in to Bitbucket
		/*List<BeaEmpWrkJrnl> wrkjlList= wrkjlService.getWrkjl(userDetail.getEmpNbr());
		mav.addObject("wrkjlList", objectMapper.writeValueAsString(wrkjlList));
*/
//		EAP OPTION FOR CALENDAR OPTIONS ENABLE
		BhrEapOpt o = indexService.getEapOptCal();
		mav.addObject("eapOption", o);

		// Appended Travel Request Calendar End	
		
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
		String[] octets =  InetAddress.getLocalHost().getHostAddress().split("\\.");
		String maskedLocalHostAddress = "XXX.XXX.XXX." + octets[3];
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
			mav.addObject("errorMsg", "Not all mandatory fields provided.");
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
		//ALC-13 the name "valid" must be hard code according to the boostrap validator
		//and returning true indicates user name is available, otherwise it's not available
		res.put("valid", !isExisted);
		return res;
	}
	
	@RequestMapping("submitLeaveRequestFromCalendar")
	public ModelAndView submitLeaveRequestFromCalendar(HttpServletRequest req, HttpServletResponse response, String leaveId, String leaveType,
			String absenseReason, String LeaveStartDate, String startTimeValue, String LeaveEndDate,
			String endTimeValue, String lvUnitsDaily, String lvUnitsUsed, String Remarks, String freq, Boolean isAdd,
			Long token) throws Exception {
		HttpSession session = req.getSession();
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		ModelAndView mav = new ModelAndView();
		if (leaveType == null || absenseReason == null || LeaveStartDate == null || startTimeValue == null
				|| LeaveEndDate == null || endTimeValue == null || lvUnitsDaily == null || lvUnitsUsed == null
				|| freq == null) {
			mav.setViewName("visitFailed");
			mav.addObject("action", "Create or update leave information from leave request calendar view");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		Long sessionToken = (Long) session.getAttribute("token");
		if (!sessionToken.equals(token)) {
			return this.getHome(req, response, freq, isAdd);
		}
		this.saveLeaveRequest(leaveId, leaveType, absenseReason, LeaveStartDate, startTimeValue, LeaveEndDate,
				endTimeValue, lvUnitsDaily, lvUnitsUsed, Remarks, freq, demo);
		return this.getHome(req, response, freq, isAdd);
	}

	@RequestMapping("deleteLeaveRequestFromCalendar")
	public ModelAndView deleteLeaveRequestFromCalendar(HttpServletRequest req, HttpServletResponse response, String id, String freq, Long token) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = req.getSession();
		if (id == null) {
			mav.setViewName("visitFailed");
			mav.addObject("action", "Delete leave information from leave overview");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		Long sessionToken = (Long) session.getAttribute("token");
		if (!sessionToken.equals(token)) {
			return this.getHome(req, response, freq, false);
		}
		deleteLeaveRequest(id);
		return this.getHome(req, response, freq, false);
	}
	
	protected void saveLeaveRequest(String leaveId, String leaveType, String absenseReason, String LeaveStartDate,
			String startTimeValue, String LeaveEndDate, String endTimeValue, String lvUnitsDaily, String lvUnitsUsed,
			String Remarks, String freq, BhrEmpDemo demo) throws ParseException, MessagingException {
		BeaEmpLvRqst request;
		if (leaveId == null || ("").equals(leaveId))
			request = new BeaEmpLvRqst();
		else
			request = leaveRequestService.getleaveRequestById(Integer.parseInt(leaveId + ""));
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm a", Locale.ENGLISH);
		request.setEmpNbr(demo.getEmpNbr());
		request.setPayFreq(freq.charAt(0));
		request.setLvTyp(leaveType);
		request.setAbsRsn(absenseReason);
		request.setDatetimeFrom(DateUtil.getUTCTime(formatter.parse(LeaveStartDate + " " + startTimeValue)));
		request.setDatetimeTo(DateUtil.getUTCTime(formatter.parse(LeaveEndDate + " " + endTimeValue)));
		request.setDatetimeSubmitted(new Date());
		request.setLvUnitsDaily(BigDecimal.valueOf(Double.parseDouble(lvUnitsDaily)));
		request.setLvUnitsUsed(BigDecimal.valueOf(Double.parseDouble(lvUnitsUsed)));
		Boolean isDisapproveUpdate = false;
		if (leaveId == null || ("").equals(leaveId) || 'A'==request.getStatusCd()) {
			request.setStatusCd('P');
			request.setDtOfPay("");
		} else {
			if('D'==request.getStatusCd()) {
				request.setStatusCd('P');
				isDisapproveUpdate = true;
			}
			request.setDtOfPay(request.getDtOfPay() == null ? "" : request.getDtOfPay());
		}
		Integer id = leaveRequestService.saveLeaveRequest(request, (leaveId != null && !("").equals(leaveId)));
		// Create Comments
		if (Remarks != null && !("").equals(Remarks)) {
			BeaEmpLvComments comments = new BeaEmpLvComments();
			comments.setLvId(id);
			comments.setLvCommentEmpNbr(demo.getEmpNbr());
			comments.setLvCommentDatetime(DateUtil.getUTCTime(new Date()));
			comments.setLvComment(Remarks);
			comments.setLvCommentTyp('C');
			leaveRequestService.saveLvComments(comments);
		}
		// Create Workflow upon first creation or modify disapproved leave
		if ((leaveId == null || ("").equals(leaveId)) || ((leaveId != null && !("").equals(leaveId) && isDisapproveUpdate))) {
			LeaveParameters params = leaveRequestService.getLeaveParameters();
			LeaveEmployeeData supervisorData = leaveRequestService.getFirstLineSupervisor(demo.getEmpNbr(), params.isUsePMIS());
			String supervisorEmpNbr = supervisorData ==null?null:supervisorData.getEmployeeNumber();
			if (!StringUtils.isEmpty(supervisorEmpNbr)) {
				BeaEmpLvWorkflow flow = new BeaEmpLvWorkflow();
				flow.setLvId(id);
				flow.setInsertDatetime(DateUtil.getUTCTime(new Date()));
				flow.setSeqNum(1);
				flow.setApprvrEmpNbr(supervisorEmpNbr == null ? "" : supervisorEmpNbr);
				flow.setTmpApprvrExpDatetime(null);
				//String supervisorEmail = supervisorData ==null?null:supervisorData.getEmailAddress();
				com.esc20.nonDBModels.Options option = this.indexService.getOptions();
				String urlHM = option.getUrl() ==null? "":option.getUrl().trim();
				leaveRequestService.saveLvWorkflow(flow, demo);
				leaveRequestService.sendEmail(request, demo, supervisorData,urlHM);
			}
		}
	}

	protected void deleteLeaveRequest(String id) {
		BeaEmpLvComments comments = new BeaEmpLvComments();
		comments.setLvId(Integer.parseInt(id));
		leaveRequestService.deleteLeaveComments(Integer.parseInt(id));
		leaveRequestService.deleteLeaveFlow(Integer.parseInt(id));
		leaveRequestService.deleteLeaveRequest(Integer.parseInt(id));
	}
	
	private String getNextDate(String givenDate, int noOfDays) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String nextDaysDate = null;
		try {
			cal.setTime(dateFormat.parse(givenDate));
			cal.add(Calendar.DATE, noOfDays);

			nextDaysDate = dateFormat.format(cal.getTime());

		} catch (ParseException ex) {
		} finally {
			dateFormat = null;
			cal = null;
		}
		return nextDaysDate;
	}
	
	private String compareTravelPurpose(List<TravelRequestCalendar> list) {
		String purpose = null;
		for (int i = 0; i < list.size(); i++) {
			TravelRequestCalendar itemI = list.get(i);
			String purposeI = itemI.getPurpose().trim();
			for (int j = 0; j < list.size(); j++) {
				TravelRequestCalendar itemJ = list.get(j);
				String purposeJ = itemJ.getPurpose().trim();
				if (i != j) {
					if (purposeI.compareTo(purposeJ) == 0) {
						purpose = purposeI;
					} else {
						purpose = "multi";
					}
				}
			}
		}
		return purpose;
	}
	
	
	// ALC-35 EP: Display License Agreement at sign-on
	@RequestMapping(value = "acceptLicense", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject acceptLicense(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		String userId = user.getUsrname();
		boolean acceptLicense = licenseAgreementService.setLicense(userId);
		result.put("acceptLicense", acceptLicense);
		request.getSession().setAttribute("isLicense", "Y");
		return result;
	}

	@RequestMapping(value = "getLicenseContent", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getLicenseContent(HttpServletRequest req) throws Exception {
		JSONObject result = new JSONObject();
		//URL url = new URL("https://help.ascendertx.com/documents/doku.php/ascender/eula");
		URL url = new URL(helpSettingUrl+"documents/doku.php/ascender/eula");

		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		String line;
		List<String> licenseContent = new ArrayList();
		while ((line = reader.readLine()) != null) {
			licenseContent.add(line);
		}
		reader.close();
		result.put("data", licenseContent);
		return result;
	}
}
