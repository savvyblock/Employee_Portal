package com.esc20.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
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
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BthrBankCodes;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Options;
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
            
            plainTextPwd = encoder.encodePassword(plainTextPwd,null);
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
    
//    @RequestMapping("retrieveUserName")
//    public ModelAndView retrieveUserName(HttpServletRequest req, String email){
//    	ModelAndView mav = new ModelAndView();
//    	BeaUsers user = this.indexService.getUserByEmail(email);
//    	
//    	if(user == null) {
//    		mav.addObject("retrieveUserNameErrorMessage", "Email Does not exist");
//    	}else {
//    		user.setUserEmail(email);
//    	}
//    	
//        mav.setViewName("forgetPassword");
//        mav.addObject("user", user);
//        mav.addObject("email", email);
//        return mav;
//    }
    
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
    
    @RequestMapping("saveNewUser")
    public ModelAndView saveNewUser(HttpServletRequest req){
    	ModelAndView mav = new ModelAndView();
    	BeaUsers newUser=new BeaUsers();
    	ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
    	newUser.setEmpNbr(req.getParameter("empNumber"));
    	newUser.setUsrname(req.getParameter("username"));//username
    	newUser.setHint(req.getParameter("hintQuestion"));//hintQuestion
    	newUser.setHintAns(encoder.encodePassword(req.getParameter("hintAnswer"),null));//  hintAnswer
    	//newUser.setUserEmail(req.getParameter("workEmail"));//workEmail
    	newUser.setUsrpswd(encoder.encodePassword(req.getParameter("password"),null));
    	
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
        		searchUser.setNameF(bed.getNameF());
        		searchUser.setNameL(bed.getNameL());
        		mav.setViewName("createNewUser");
            	mav.addObject("newUser", searchUser);
        	}
    	}
    	
        return mav;
    }
    
    @RequestMapping("getAllBanks")
    @ResponseBody
    public JSONObject getAllBanks(HttpServletRequest req,@RequestBody Page page){
    	
    	System.out.println(page);
    	
    	Page p = new Page();
    	p.setCurrentPage(1);
    	p.setPerPageRows(10);
    	
    	List<BthrBankCodes> allbanks = bankService.getAllBanks();
    	
    	p.setTotalRows(allbanks.size());
    	p.setTotalPages((int) Math.ceil(p.getTotalRows()/p.getPerPageRows()));
    	
    	List<BthrBankCodes> banks = bankService.getAllBanks(p);
    	JSONArray json = JSONArray.fromObject(banks);
	    JSONObject result=new JSONObject();
	    result.put("result", json);
	    result.put("page", p);
	    result.put("isSuccess", "true");
	    
        return result;
    }
    
    @RequestMapping("getBanks")
    @ResponseBody
    public JSONArray getBanks(HttpServletRequest req){
    	
    	String routingNumber = req.getParameter("routingNumber");
    	String bankName = req.getParameter("bankName");
    	
    	BthrBankCodes bbc= new BthrBankCodes();
    	bbc.setTransitRoute(routingNumber);
    	bbc.setBankName(bankName);
    	
    	List<BthrBankCodes> banks = bankService.getBanksByEntity(bbc);
    	JSONArray json = JSONArray.fromObject(banks); 
        return json;
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
	    	user.setUsrpswd(this.encrypt(password));
	    	user.setTmpDts(user.getTmpDts()==null?"":user.getTmpDts());
	    	this.indexService.updateUser(user);
	    	
    	}catch(Exception e) {
    		mav.addObject("resetPsw", "resetPswFaild");
    	}
    	mav.addObject("resetPsw", "resetPswSuccess");
        return mav;
    }
    
    @RequestMapping("changePassword")
    public ModelAndView changePassword(HttpServletRequest req, String password, String id){
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        if(StringUtils.isEmpty(password)) {
        	mav = new ModelAndView("redirect:/profile");
        	return mav;
        }
    	user.setUsrpswd(encoder.encodePassword(this.encrypt(password),null));
    	user.setTmpDts(user.getTmpDts()==null?"":user.getTmpDts());
    	this.indexService.updateUser(user);
    	session.removeAttribute("user");
    	session.setAttribute("user", user);
        getProfileDetails(session, mav);
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
    
    @RequestMapping("profile")
    public ModelAndView getProfile(HttpServletRequest req){
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        getProfileDetails(session, mav);
        mav.addObject("decryptedPwd",user.getUsrpswd());
        return mav;
    }

	private void getProfileDetails(HttpSession session, ModelAndView mav) {
		BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
//		BhrEmpPay pay = this.indexService.getW4(demo.getEmpNbr(),freq);
        BeaLglName nameRequest = this.indexService.getBeaLglName(demo);
        BeaEmerContact emerRequest = this.indexService.getBeaEmerContact(demo);
        BeaDrvsLic licRequest = this.indexService.getBeaDrvsLic(demo);
        BeaMrtlStat mrtlRequest = this.indexService.getBeaMrtlStat(demo);
//        BeaW4 w4Request = this.indexService.getW4(pay);
        BeaRestrict restrictRequest = this.indexService.getBeaRestrict(demo);
        BeaEmail emailRequest = this.indexService.getBeaEmail(demo);
        BeaCellPhone cellRequest = this.indexService.getBeaCellPhone(demo);
        BeaBusPhone busRequest = this.indexService.getBeaBusPhone(demo);
        BeaHmPhone hmRequest = this.indexService.getBeaHmPhone(demo);
        BeaAltMailAddr altMailAddrRequest = this.indexService.getBeaAltMailAddr(demo);
        BeaMailAddr mailAddrRequest = this.indexService.getBeaMailAddr(demo);
        List<Code> maritalOptions = this.referenceService.getMaritalActualStatuses();
        List<Code> maritalTaxOptions = this.referenceService.getMaritalTaxStatuses();
        List<Code> generationOptions = this.referenceService.getGenerations();
        List<Code> titleOptions = this.referenceService.getTitles();
        List<Code> statesOptions = this.referenceService.getStates();
        List<Code> restrictionsOptions = this.referenceService.getRestrictions();
        mav.setViewName("profile");
        mav.addObject("nameRequest", nameRequest);
        mav.addObject("mrtlRequest", mrtlRequest);
        mav.addObject("licRequest", licRequest);
        mav.addObject("restrictRequest", restrictRequest);
        mav.addObject("emailRequest", emailRequest);
        mav.addObject("emerRequest", emerRequest);
        mav.addObject("mailAddrRequest", mailAddrRequest);
        mav.addObject("altMailAddrRequest", altMailAddrRequest);
        mav.addObject("hmRequest", hmRequest);
        mav.addObject("cellRequest", cellRequest);
        mav.addObject("busRequest", busRequest);
        mav.addObject("maritalOptions", maritalOptions);
        mav.addObject("maritalTaxOptions", maritalTaxOptions);
        mav.addObject("generationOptions", generationOptions);
        mav.addObject("titleOptions", titleOptions);
        mav.addObject("statesOptions", statesOptions);
        mav.addObject("restrictionsOptions", restrictionsOptions);
//        mav.addObject("w4Request", w4Request);
	}

    @RequestMapping("saveName")
    public ModelAndView saveName(HttpServletRequest req, 
    		String empNbr, String reqDts, String namePreNew, String nameFNew, String nameLNew, String nameMNew, String nameGenNew) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        BeaLglName nameRequest;
        
        if(this.indexService.getBhrEapDemoAssgnGrp("BEA_LGL_NAME")) {
        	nameRequest = new BeaLglName(demo, empNbr, reqDts, namePreNew, nameFNew, nameLNew, nameMNew, (nameGenNew==null||("").equals(nameGenNew))?'\0':nameGenNew.charAt(0), 'A');
        	this.indexService.saveNameRequest(nameRequest);
        	demo.setNamePre(namePreNew);
         	demo.setNameF(nameFNew);
         	demo.setNameL(nameLNew);
         	demo.setNameM(nameMNew);
         	demo.setNameGen((nameGenNew==null||("").equals(nameGenNew))?'\0':nameGenNew.charAt(0));
         	this.indexService.updateDemoName(demo);
            session.removeAttribute("userDetail");
        	session.setAttribute("userDetail", demo);
        }else {
        	nameRequest = new BeaLglName(demo, empNbr, reqDts, namePreNew, nameFNew, nameLNew, nameMNew, (nameGenNew==null||("").equals(nameGenNew))?'\0':nameGenNew.charAt(0), 'P');
        	this.indexService.saveNameRequest(nameRequest);
        }
        
        this.getProfileDetails(session, mav);
        mav.addObject("activeTab", "nameRequest");
        return mav;
    }
    
    @RequestMapping("changeAvatar")
    public ModelAndView changeAvatar(HttpServletRequest req, String file, String fileName) {
    	 HttpSession session = req.getSession();
         BeaUsers user = (BeaUsers)session.getAttribute("user");
         ModelAndView mav = new ModelAndView();
         if(null == user){
         	return this.getIndexPage(mav);
         }
         BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        
         BASE64Decoder decoder = new BASE64Decoder();
         file = file.replace("data:image/jpeg;base64,", "");
         file = file.replace("data:image/png;base64,", "");
         file = file.replace("data:image/gif;base64,", "");
         file = file.replace("data:image/bmp;base64,", "");
         try {
        	 byte[] b = decoder.decodeBuffer(file);
         
	         for (int i = 0; i < b.length; ++i) {
		         if (b[i] < 0) {
		        	 b[i] += 256;
		         }
	         }
	         
	         
	         //for local path
	         String path= System.getProperty("user.dir").replace("bin", "standalone/data/images/")+demo.getEmpNbr()+".jpg";
	         System.out.println(path);
	         OutputStream out = new FileOutputStream(path);
	         out.write(b);
	         out.flush();
	         out.close();
	         
	         
	         mav.setViewName("profile");
	        
	         	
	         demo.setAvatar("/uploadFiles/"+demo.getEmpNbr()+".jpg");
	         this.indexService.updateDemoAvatar(demo);
	         session.removeAttribute("userDetail");
	         session.setAttribute("userDetail", demo);
	         
	         this.getProfileDetails(session, mav);
	       
	         
         } catch (IOException e) {
	         e.printStackTrace();
         }
         
         return mav;
    }
    
    @RequestMapping("deleteNameRequest")
    public ModelAndView deleteNameRequest(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteNameRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav);
        return mav;
    } 
    
    
    @RequestMapping("saveMarital")
    public ModelAndView saveMarital(HttpServletRequest req, 
    		String empNbr, String reqDts, String maritalStatNew) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        BeaMrtlStat maritalStatusRequest;
        if(this.indexService.getBhrEapDemoAssgnGrp("BEA_MRTL_STAT")) {
        	maritalStatusRequest = new BeaMrtlStat(demo, empNbr, reqDts,maritalStatNew,'A');
        	this.indexService.saveMaritalRequest(maritalStatusRequest);
        	demo.setMaritalStat(maritalStatNew.charAt(0));
        	this.indexService.updateDemoMaritalStatus(demo);
        	session.removeAttribute("userDetail");
        	session.setAttribute("userDetail", demo);
        }else {
        	maritalStatusRequest = new BeaMrtlStat(demo, empNbr, reqDts,maritalStatNew,'P');
        	this.indexService.saveMaritalRequest(maritalStatusRequest);
        }
        
        this.getProfileDetails(session, mav);
        mav.addObject("activeTab", "maritalStatusRequest");
        return mav;
    }
    
    @RequestMapping("deleteMaritalRequest")
    public ModelAndView deleteMaritalRequest(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteMaritalRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav);
        return mav;
    } 
    
    @RequestMapping("saveDriversLicense")
    public ModelAndView saveDriversLicense(HttpServletRequest req, 
    		String empNbr, String reqDts, String driversLicNbrNew, String driversLicStNew) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        BeaDrvsLic driversLicenseRequest;
        
        if(this.indexService.getBhrEapDemoAssgnGrp("BEA_DRVS_LIC")) {
        	driversLicenseRequest = new BeaDrvsLic(demo, empNbr, reqDts,driversLicNbrNew,driversLicStNew,'A');
        	this.indexService.saveDriversLicenseRequest(driversLicenseRequest);
        	demo.setDriversLicNbr(driversLicNbrNew);
        	demo.setDriversLicSt(driversLicStNew);
        	this.indexService.updateDemoDriversLicense(demo);
            session.removeAttribute("userDetail");
        	session.setAttribute("userDetail", demo);
        }else {
        	driversLicenseRequest = new BeaDrvsLic(demo, empNbr, reqDts,driversLicNbrNew,driversLicStNew,'P');
        	this.indexService.saveDriversLicenseRequest(driversLicenseRequest);
        }
        this.getProfileDetails(session, mav);
        mav.addObject("activeTab", "driversLicenseRequest");
        return mav;
    }
    
    @RequestMapping("deleteDriversLicenseRequest")
    public ModelAndView deleteDriversLicenseRequest(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteDirversLicenseRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav);
        return mav;
    } 
    
    @RequestMapping("saveRestrictionCodes")
    public ModelAndView saveRestrictionCodes(HttpServletRequest req, 
    		String empNbr, String reqDts, String restrictCdNew, String restrictCdPublicNew) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        BeaRestrict restrictionCodesRequest;
    	
        if(this.indexService.getBhrEapDemoAssgnGrp("BEA_RESTRICT")) {
        	restrictionCodesRequest = new BeaRestrict(demo, empNbr, reqDts, restrictCdNew, restrictCdPublicNew, 'A');
        	this.indexService.saveRestrictionCodesRequest(restrictionCodesRequest);
        	demo.setRestrictCd(restrictCdNew.charAt(0));
        	demo.setRestrictCdPublic(restrictCdPublicNew.charAt(0));
        	this.indexService.updateDemoRestrictionCodes(demo);
            session.removeAttribute("userDetail");
        	session.setAttribute("userDetail", demo);
        }else {
        	restrictionCodesRequest = new BeaRestrict(demo, empNbr, reqDts, restrictCdNew, restrictCdPublicNew, 'P');
        	this.indexService.saveRestrictionCodesRequest(restrictionCodesRequest);
        }
        this.getProfileDetails(session, mav);
        mav.addObject("activeTab", "restrictionCodesRequest");
        return mav;
    }
    
    @RequestMapping("deleteRestrictionCodesRequest")
    public ModelAndView deleteRestrictionCodesRequest(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteRestrictionCodesRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav);
        return mav;
    } 
    
    @RequestMapping("saveEmail")
    public ModelAndView saveEmail(HttpServletRequest req, 
    		String empNbr, String reqDts, String emailNew, String hmEmailNew) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        BeaEmail emailRequest;
    	
        if(this.indexService.getBhrEapDemoAssgnGrp("BEA_EMAIL")) {
        	emailRequest = new BeaEmail(demo, empNbr, reqDts, emailNew, hmEmailNew, 'A');
        	this.indexService.saveEmailRequest(emailRequest);
        	demo.setEmail(emailNew);
        	demo.setHmEmail(hmEmailNew);
        	this.indexService.updateDemoEmail(demo);
            session.removeAttribute("userDetail");
        	session.setAttribute("userDetail", demo);
        }else {
        	emailRequest = new BeaEmail(demo, empNbr, reqDts, emailNew, hmEmailNew, 'P');
        	this.indexService.saveEmailRequest(emailRequest);
        }
        
        this.getProfileDetails(session, mav);
        mav.addObject("activeTab", "emailRequest");
        return mav;
    }
    
    @RequestMapping("deleteEmail")
    public ModelAndView deleteEmail(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteEmailRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav);
        return mav;
    } 
    
    @RequestMapping("saveEmergencyContact")
    public ModelAndView saveEmergencyContact(HttpServletRequest req, 
    		String empNbr, String reqDts, String emerContactNew, String emerPhoneAcNew
    		, String emerPhoneNbrNew, String emerPhoneExtNew, String emerRelNew, String emerNoteNew) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        BeaEmerContact emergencyContactRequest;
    	
        if(this.indexService.getBhrEapDemoAssgnGrp("BEA_EMER_CONTACT")) {
        	emergencyContactRequest = new BeaEmerContact(demo, empNbr, reqDts, emerContactNew, emerPhoneAcNew,emerPhoneNbrNew,emerPhoneExtNew,emerRelNew,emerNoteNew, 'A');
        	this.indexService.saveEmergencyContactRequest(emergencyContactRequest);
        	demo.setEmerContact(emerContactNew);
        	demo.setEmerPhoneAc(emerPhoneAcNew);
        	demo.setEmerPhoneNbr(emerPhoneNbrNew);
        	demo.setEmerPhoneExt(emerPhoneExtNew);
        	demo.setEmerRel(emerRelNew);
        	demo.setEmerNote(emerNoteNew);
        	this.indexService.updateDemoEmergencyContact(demo);
            session.removeAttribute("userDetail");
         	session.setAttribute("userDetail", demo);
        }else {
        	emergencyContactRequest = new BeaEmerContact(demo, empNbr, reqDts, emerContactNew, emerPhoneAcNew,emerPhoneNbrNew,emerPhoneExtNew,emerRelNew,emerNoteNew, 'P');
        	this.indexService.saveEmergencyContactRequest(emergencyContactRequest);
        }
       
        this.getProfileDetails(session, mav);
        mav.addObject("activeTab", "emergencyContactRequest");
        return mav;
    }
    
    @RequestMapping("deleteEmergencyContact")
    public ModelAndView deleteEmergencyContact(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteEmergencyContactRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav);
        return mav;
    } 
    
    @RequestMapping("saveMailAddr")
    public ModelAndView saveMailAddr(HttpServletRequest req, 
    		String empNbr, String reqDts, String addrNbrNew, String addrStrNew,String addrAptNew,
			String addrCityNew, String addrStNew, String addrZipNew,String addrZip4New) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        BeaMailAddr mailingAddressRequest;
    	
        if(this.indexService.getBhrEapDemoAssgnGrp("BEA_MAIL_ADDR")) {
        	mailingAddressRequest = new BeaMailAddr(demo, empNbr, reqDts, addrNbrNew, addrStrNew,addrAptNew,addrCityNew,addrStNew,addrZipNew,addrZip4New, 'A');
        	this.indexService.saveMailAddrRequest(mailingAddressRequest);
        	demo.setAddrNbr(addrNbrNew);
        	demo.setAddrStr(addrStrNew);
        	demo.setAddrApt(addrAptNew);
        	demo.setAddrCity(addrCityNew);
        	demo.setAddrSt(addrStNew);
        	demo.setAddrZip(addrZipNew);
        	demo.setAddrZip4(addrZip4New);
        	this.indexService.updateDemoMailAddr(demo);
            session.removeAttribute("userDetail");
         	session.setAttribute("userDetail", demo);
        }else {
        	mailingAddressRequest = new BeaMailAddr(demo, empNbr, reqDts,  addrNbrNew, addrStrNew,addrAptNew,addrCityNew,addrStNew,addrZipNew,addrZip4New, 'P');
        	this.indexService.saveMailAddrRequest(mailingAddressRequest);
        }
       
        this.getProfileDetails(session, mav);
        mav.addObject("activeTab", "mailingAddressRequest");
        return mav;
    }
    
    @RequestMapping("deleteMailAddr")
    public ModelAndView deleteMailAddr(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteMailAddrRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav);
        return mav;
    } 
    
    @RequestMapping("saveAltMailAddr")
    public ModelAndView saveAltMailAddr(HttpServletRequest req, 
    		String empNbr, String reqDts, String smrAddrNbrNew, String smrAddrStrNew,String smrAddrAptNew,
			String smrAddrCityNew, String smrAddrStNew, String smrAddrZipNew,String smrAddrZip4New) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        BeaAltMailAddr altMailingAddressRequest;
    	
        if(this.indexService.getBhrEapDemoAssgnGrp("BEA_ALT_MAIL_ADDR")) {
        	altMailingAddressRequest = new BeaAltMailAddr(demo, empNbr, reqDts, smrAddrNbrNew, smrAddrStrNew,smrAddrAptNew,smrAddrCityNew,smrAddrStNew,smrAddrZipNew,smrAddrZip4New, 'A');
        	this.indexService.saveAltMailAddrRequest(altMailingAddressRequest);
        	demo.setSmrAddrNbr(smrAddrNbrNew);
        	demo.setSmrAddrStr(smrAddrStrNew);
        	demo.setSmrAddrApt(smrAddrAptNew);
        	demo.setSmrAddrCity(smrAddrCityNew);
        	demo.setSmrAddrSt(smrAddrStNew);
        	demo.setSmrAddrZip(smrAddrZipNew);
        	demo.setSmrAddrZip4(smrAddrZip4New);
        	this.indexService.updateDemoAltMailAddr(demo);
            session.removeAttribute("userDetail");
        	session.setAttribute("userDetail", demo);
        }else {
        	altMailingAddressRequest = new BeaAltMailAddr(demo, empNbr, reqDts, smrAddrNbrNew, smrAddrStrNew,smrAddrAptNew,smrAddrCityNew,smrAddrStNew,smrAddrZipNew,smrAddrZip4New, 'P');
        	this.indexService.saveAltMailAddrRequest(altMailingAddressRequest);
        }
        
        this.getProfileDetails(session, mav);
        mav.addObject("activeTab", "altMailingAddressRequest");
        return mav;
    }
    
    @RequestMapping("deleteAltMailAddr")
    public ModelAndView deleteAltMailAddr(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteAltMailAddrRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav);
        return mav;
    } 
    
    @RequestMapping("savePhone")
    public ModelAndView savePhone(HttpServletRequest req, 
    		String empNbr, String reqDts, String phoneAreaNew, String phoneNbrNew,String phoneAreaCellNew,
			String phoneNbrCellNew, String phoneAreaBusNew, String phoneNbrBusNew,String busPhoneExtNew) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        
        BeaHmPhone homePhoneRequest;
        BeaCellPhone cellPhoneRequest;
        BeaBusPhone businessPhoneRequest;
        
        if(this.indexService.getBhrEapDemoAssgnGrp("BEA_HM_PHONE")) {
        	homePhoneRequest = new BeaHmPhone(demo, empNbr, reqDts, phoneAreaNew, phoneNbrNew, 'A');
        	this.indexService.saveHomePhoneRequest(homePhoneRequest);
        	demo.setPhoneArea(phoneAreaNew);
        	demo.setPhoneNbr(phoneNbrNew);
        	this.indexService.updateDemoHomePhone(demo);
        	session.removeAttribute("userDetail");
        	session.setAttribute("userDetail", demo);
        }else {
        	homePhoneRequest = new BeaHmPhone(demo, empNbr, reqDts, phoneAreaNew, phoneNbrNew, 'P');
        	this.indexService.saveHomePhoneRequest(homePhoneRequest);
        }
        this.getProfileDetails(session, mav);
        mav.addObject("activeTab", "homePhoneRequest");
        
        if(this.indexService.getBhrEapDemoAssgnGrp("BEA_CELL_PHONE")) {
        	cellPhoneRequest = new BeaCellPhone(demo, empNbr, reqDts, phoneAreaCellNew, phoneNbrCellNew, 'A');
        	this.indexService.saveCellPhoneRequest(cellPhoneRequest);
        	demo.setPhoneAreaCell(phoneAreaCellNew);
        	demo.setPhoneNbrCell(phoneNbrCellNew);
        	this.indexService.updateDemoCellPhone(demo);
        	session.removeAttribute("userDetail");
        	session.setAttribute("userDetail", demo);
        }else {
        	cellPhoneRequest = new BeaCellPhone(demo, empNbr, reqDts, phoneAreaCellNew, phoneNbrCellNew, 'P');
        	this.indexService.saveCellPhoneRequest(cellPhoneRequest);
        }
        this.getProfileDetails(session, mav);
        mav.addObject("activeTab", "cellPhoneRequest");
        
        if(this.indexService.getBhrEapDemoAssgnGrp("BEA_BUS_PHONE")) {
        	businessPhoneRequest = new BeaBusPhone(demo, empNbr, reqDts, phoneAreaBusNew, phoneNbrBusNew,busPhoneExtNew, 'A');
        	this.indexService.saveBusinessPhoneRequest(businessPhoneRequest);
        	demo.setPhoneAreaBus(phoneAreaBusNew);
        	demo.setPhoneNbrBus(phoneNbrBusNew);
        	demo.setBusPhoneExt(busPhoneExtNew);
        	this.indexService.updateDemoBusinessPhone(demo);
        	session.removeAttribute("userDetail");
        	session.setAttribute("userDetail", demo);
        }else {
        	businessPhoneRequest = new BeaBusPhone(demo, empNbr, reqDts, phoneAreaBusNew, phoneNbrBusNew, busPhoneExtNew, 'P');
        	this.indexService.saveBusinessPhoneRequest(businessPhoneRequest);
        }
        this.getProfileDetails(session, mav);
        mav.addObject("activeTab", "businessPhoneRequest");
        
        return mav;
    }
    
    @RequestMapping("deletePhone")
    public ModelAndView deletePhone(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteHomePhoneRequest(demo.getEmpNbr());
        this.indexService.deleteCellPhoneRequest(demo.getEmpNbr());
        this.indexService.deleteBusinessPhoneRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav);
        return mav;
    } 
    
    @RequestMapping("notifications")
    public ModelAndView getNotifications(HttpServletRequest req){
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        List<BeaAlert> unReadList = this.indexService.getUnReadAlert(demo.getEmpNbr());
        mav.setViewName("notifications");
        mav.addObject("user", user);
        mav.addObject("unReadList",unReadList);
        return mav;
    }

    @RequestMapping("markAsRead")
    public ModelAndView markAsRead(HttpServletRequest req,String id){
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        Long alertId = Long.parseLong(id);
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteAlert(id);
        List<BeaAlert> unReadList = this.indexService.getUnReadAlert(demo.getEmpNbr());
        mav.setViewName("notifications");
        mav.addObject("user", user);
        mav.addObject("unReadList",unReadList);
        return mav;
    }
    
    @RequestMapping(value = "getBudgeCount", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> getBudgeCount(HttpServletRequest req) throws Exception{
    	HttpSession session = req.getSession();
    	Map<String, String> res = new HashMap<>();
    	BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
    	Integer count = this.indexService.getBudgeCount(demo.getEmpNbr());
        res.put("count", count.toString());
        return res;
    }

    @RequestMapping(value = "getTop5Alerts", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, JSONArray> getTop5Alerts(HttpServletRequest req) throws Exception{
    	HttpSession session = req.getSession();
    	Map<String, JSONArray> result = new HashMap<>();
    	BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
    	List<BeaAlert> top5 = this.indexService.getTop5Alerts(demo.getEmpNbr());
        JSONArray res = new JSONArray();
        JSONObject obj = new JSONObject();
        for(BeaAlert item: top5) {
        	obj = new JSONObject();
        	obj.put("msgContent", item.getMsgContent());
        	res.add(obj);
        }
        result.put("list", res);
        return result;
    }
    
//    @RequestMapping("saveW4")
//    public ModelAndView saveW4(HttpServletRequest req, 
//    		String empNbr, String reqDts, String maritalStatTaxNew,String nbrTaxExemptsNew) {
//        HttpSession session = req.getSession();
//        BeaUsers user = (BeaUsers)session.getAttribute("user");
//        ModelAndView mav = new ModelAndView();
//        if(null == user){
//        	return this.getIndexPage(mav);
//        }
//        mav.setViewName("profile");
//        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
//        BeaW4 w4Request;
//        
//        if(this.indexService.getBhrEapDemoAssgnGrp("BEA_W4")) {
//        	w4Request = new BeaW4(demo, empNbr, reqDts, maritalStatTaxNew, nbrTaxExemptsNew, 'A');
//        	this.indexService.saveAltMailAddrRequest(w4Request);
//        	demo.setSmrAddrNbr(smrAddrNbrNew);
//        	demo.setSmrAddrStr(smrAddrStrNew);
//        	this.indexService.updateDemoAltMailAddr(demo);
//        	session.removeAttribute("userDetail");
//        	session.setAttribute("userDetail", demo);
//        }else {
//        	w4Request = new BeaAltMailAddr(demo, empNbr, reqDts, maritalStatTaxNew, nbrTaxExemptsNew, 'P');
//        	this.indexService.saveAltMailAddrRequest(w4Request);
//        }
//        this.getProfileDetails(session, mav);
//        mav.addObject("activeTab", "altMailingAddressRequest");
//        return mav;
//    }
    
    @RequestMapping("deleteW4")
    public ModelAndView deleteW4(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteAltMailAddrRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav);
        return mav;
    } 
    

     @RequestMapping("selfService/changePassword")
    public ModelAndView getChangePassword(HttpServletRequest req) throws ParseException{
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        BeaUsers users = this.indexService.getUserPwd(user.getUsrname());
        mav.setViewName("changePassword");
        mav.addObject("id", users.getEmpNbr());
        return mav;
    }
    
    @RequestMapping("logout")
    public ModelAndView logout(HttpServletRequest req, String Id){
        HttpSession session = req.getSession();
        session.invalidate();
        ModelAndView mav = new ModelAndView();
        return this.getIndexPage(mav);
    }
    
    public String encrypt(String pPlainText) {
    	return new String(Base64.encodeBase64(doCipher(Cipher.ENCRYPT_MODE, pPlainText.getBytes())));
    }
    
    public static String failBlankDesrypt(String pCipherText) {
    	try {
    		return decrypt(pCipherText);
    	}catch(Exception e) {
    		return "";
    	}
    }
    
    public static String decrypt(String pCipherText) {
    	if(null == pCipherText || 0 == pCipherText.length()) {
    		return pCipherText;
    	}
    	return new String(doCipher(Cipher.DECRYPT_MODE, Base64.decodeBase64(pCipherText.getBytes())));
    }
    
    private static byte[] doCipher(int operation, byte[] pInput) {
    	try {
    		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    		cipher.init(operation, new SecretKeySpec(key.getBytes(),"AES"));
    		return cipher.doFinal(pInput);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
}
