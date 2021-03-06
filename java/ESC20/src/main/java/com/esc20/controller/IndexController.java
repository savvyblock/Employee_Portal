package com.esc20.controller;

import java.io.File;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.AppUser;
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
import com.esc20.model.BhrEapDemoAssgnGrp;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrEmpPay;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Options;
import com.esc20.service.IndexService;
import com.esc20.service.ReferenceService;

import sun.misc.BASE64Decoder;

@Controller
@RequestMapping("/")
public class IndexController {

	private static String key = "D3n!m!23R3gi0n20";
	
    @Autowired
    private IndexService indexService;

    @Autowired
    private ReferenceService referenceService;
    
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
            if(this.decrypt(user.getUsrpswd()).equals(param.get("userPwd"))){
                res.put("isSuccess","true");
                res.put("userName", uName);
                HttpSession session = req.getSession();
                BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
                Options options = this.indexService.getOptions();
                District districtInfo = this.indexService.getDistrict();
                userDetail.setEmpNbr(user.getEmpNbr());
                session.setAttribute("user", user);
                session.setAttribute("userDetail", userDetail);
                session.setAttribute("companyId", user.getCmpId());
                session.setAttribute("options", options);
                session.setAttribute("district", districtInfo);
            }
        }
        return res;
    }
    
    @RequestMapping("retrieveUserName")
    public ModelAndView retrieveUserName(HttpServletRequest req, String email){
    	ModelAndView mav = new ModelAndView();
    	BeaUsers user = this.indexService.getUserByEmail(email);
    	user.setUserEmail(email);
        mav.setViewName("forgetPassword");
        mav.addObject("user", user);
        mav.addObject("email", email);
        return mav;
    }    
    @RequestMapping("forgetPassword")
    public ModelAndView retrieveUserName(HttpServletRequest req){
    	ModelAndView mav = new ModelAndView();
        mav.setViewName("forgetPassword");
        return mav;
    }

    @RequestMapping("updatePassword")
    public ModelAndView updatePassword(HttpServletRequest req,String password, String id){
    	ModelAndView mav = new ModelAndView();
    	BeaUsers user = this.indexService.getUserByEmpNbr(id);
    	user.setUsrpswd(this.encrypt(password));
    	user.setTmpDts(user.getTmpDts()==null?"":user.getTmpDts());
    	this.indexService.updateUser(user);
        return this.getIndexPage(mav);
    }
    
    @RequestMapping("changePassword")
    public ModelAndView changePassword(HttpServletRequest req, String password, String id){
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }

    	user.setUsrpswd(this.encrypt(password));
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
    		mav.setViewName("resetPassword");
    		mav.addObject("id", user.getEmpNbr());
    		return mav;
    	}
        mav.setViewName("forgetPassword");
        mav.addObject("errorMessage", "User Does not exist");
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
        mav.addObject("decryptedPwd",this.decrypt(user.getUsrpswd()));
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
//        List<Code> maritalTaxOptions = this.referenceService.getMaritalTaxStatuses();
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
//        mav.addObject("maritalTaxOptions", maritalTaxOptions);
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
        	//TODO: need to write own update method
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
	         String path= System.getProperty("user.dir").replace("bin", "standalone\\deployments\\ESC20.war\\static\\images\\avatar\\")+demo.getEmpNbr()+".jpg";
	
	         OutputStream out = new FileOutputStream(path);
	         out.write(b);
	         out.flush();
	         out.close();
	         
	         
	         mav.setViewName("profile");
	        
	         	
	         demo.setAvatar("/"+req.getContextPath().split("/")[1]+"/images/avatar/"+demo.getEmpNbr()+".jpg");
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
        
        mav.setViewName("notifications");
        mav.addObject("user", user);
        
        return mav;
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
