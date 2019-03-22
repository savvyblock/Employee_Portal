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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
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
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Money;
import com.esc20.nonDBModels.Page;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.SearchCriteria;
import com.esc20.security.CustomSHA256Encoder;
import com.esc20.service.BankService;
import com.esc20.service.IndexService;
import com.esc20.service.ReferenceService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

@Controller
@RequestMapping("/profile")
public class ProfileController{

    @Autowired
    private IndexService indexService;

    @Autowired
    private ReferenceService referenceService;
    
    @Autowired
    private BankService bankService;
    
    @Autowired
    private CustomSHA256Encoder encoder;
    
    private final String module = "Profile";
    
    @RequestMapping("profile")
    public ModelAndView getProfile(HttpServletRequest req,String freq){
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        getProfileDetails(session, mav,freq);
        mav.addObject("decryptedPwd",user.getUsrpswd());
        return mav;
    }
    
    @RequestMapping("getAllBanks")
    @ResponseBody
    public JSONObject getAllBanks(HttpServletRequest req,@RequestBody Page page){
    	
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
    
    @RequestMapping("searchBanks")
    @ResponseBody
    public JSONObject searchBanks(HttpServletRequest req,@RequestBody SearchCriteria searchCriteria){
    	
    	
    	Page p = searchCriteria.getPage();
    	
    	p.setPerPageRows(10);
    	
    	List<BthrBankCodes> allbanks = bankService.getAllBanks();
    	
    	p.setTotalRows(allbanks.size());
    	p.setTotalPages((int) Math.ceil(p.getTotalRows()/p.getPerPageRows()));
    	
    	List<BthrBankCodes> banks = bankService.getAllBanks(searchCriteria.getCriteria(), p);
    	JSONArray json = JSONArray.fromObject(banks);
	    JSONObject result=new JSONObject();
	    result.put("result", json);
	    result.put("page", p);
	    result.put("isSuccess", "true");
	    
        return result;
    }
    
    @RequestMapping("saveBank")
    public ModelAndView saveBank(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        
		if(req.getParameter("freq")==null||req.getParameter("displayAmount")==null||
				req.getParameter("displayLabel")==null || req.getParameter("accountNumber")==null || 
				req.getParameter("subCode")==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save bank account request information");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
        
        String freq = req.getParameter("freq");
        String displayAmount = req.getParameter("displayAmount");
        String displayLabel = req.getParameter("displayLabel");
        String accountNumber = req.getParameter("accountNumber");
        String code = req.getParameter("subCode");
        
        String employeeNumber = demo.getEmpNbr();
        
        
        Boolean autoApprove = this.bankService.getAutoApproveAccountInfo(freq);
        
        Bank payrollAccountInfo = new Bank();
        Code c = new Code();
        c.setDisplayLabel(displayLabel);
        payrollAccountInfo.setAccountNumber(accountNumber);
        payrollAccountInfo.setAccountType(c);
        payrollAccountInfo.setCode(this.bankService.getBank(code));
        payrollAccountInfo.setDepositAmount(new Money(new Double (displayAmount).doubleValue(), Currency.getInstance(Locale.US)));
        payrollAccountInfo.setFrequency(Frequency.getFrequency(freq));
        
        Bank accountInfo = new Bank();
        accountInfo.setAccountNumber("");
        accountInfo.setAccountType(new Code());
        accountInfo.setCode(new Code());
        accountInfo.setDepositAmount(new Money(new Double (displayAmount).doubleValue(), Currency.getInstance(Locale.US)));
        accountInfo.setFrequency(Frequency.getFrequency(freq));
        
        
        this.bankService.insertAccountRequest(autoApprove, employeeNumber, freq, payrollAccountInfo, accountInfo);
        
        if(autoApprove) {
        	 this.bankService.insertAccountApprove(employeeNumber, freq, payrollAccountInfo);
        	 this.bankService.deleteNextYearAccounts(employeeNumber);
        	 this.bankService.insertNextYearAccounts(employeeNumber);
        }
        
        getProfileDetails(session, mav,freq);
        
//        mav.setViewName("profile");
        return mav;
    }
    
    @RequestMapping("updateBank")
    public ModelAndView updateBank(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        
        String freq = req.getParameter("freq");
        ModelAndView mav = new ModelAndView();
		if(req.getParameter("displayAmount")==null||req.getParameter("accountType")==null||
				req.getParameter("accountNumber")==null || req.getParameter("code")==null || 
				req.getParameter("displayAmountNew")==null || req.getParameter("accountTypeNew")==null
				|| req.getParameter("accountNumberNew")==null || req.getParameter("codeNew")==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Update bank account reuqest information");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
        
        String displayAmount = req.getParameter("displayAmount");
        String displayLabel = req.getParameter("accountType");
        String accountNumber = req.getParameter("accountNumber");
        String code = req.getParameter("code");
        
        String displayAmountNew = req.getParameter("displayAmountNew");
        String displayLabelNew = req.getParameter("accountTypeNew");
        String accountNumberNew = req.getParameter("accountNumberNew");
        String codeNew = req.getParameter("codeNew");
        
        String employeeNumber = demo.getEmpNbr();
        
        
        Boolean autoApprove = this.bankService.getAutoApproveAccountInfo(freq);
        
        Bank accountInfo = new Bank();
        
        accountInfo.setAccountNumber(accountNumber);
        Code c = new Code();
        c.setDisplayLabel(displayLabel);
        accountInfo.setAccountType(c);
        accountInfo.setCode(this.bankService.getBank(code));
        accountInfo.setDepositAmount(new Money(new Double (displayAmount).doubleValue(), Currency.getInstance(Locale.US)));
        accountInfo.setFrequency(Frequency.getFrequency(freq));
        
        Bank payrollAccountInfo = new Bank();
        c.setDisplayLabel(displayLabelNew);
        payrollAccountInfo.setAccountNumber(accountNumberNew);
        payrollAccountInfo.setAccountType(c);
        payrollAccountInfo.setCode(this.bankService.getBank(codeNew));
        payrollAccountInfo.setDepositAmount(new Money(new Double (displayAmountNew).doubleValue(), Currency.getInstance(Locale.US)));
        payrollAccountInfo.setFrequency(Frequency.getFrequency(freq));
        
       
        
        //TODO check if it is the same request, if yes, update the request
        // this.bankService.checkSameBank
        // 
        
        this.bankService.insertAccountRequest(autoApprove, employeeNumber, freq, payrollAccountInfo, accountInfo);
        
        if(autoApprove) {
        	 this.bankService.updateAccountApprove(employeeNumber, freq, payrollAccountInfo, accountInfo);
        }
        
        getProfileDetails(session, mav,freq);
        
//        mav.setViewName("profile");
        return mav;
    }
    
    @RequestMapping("deleteBank")
    public ModelAndView deleteBank(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        
        
        
        ModelAndView mav = new ModelAndView();
		if(req.getParameter("freq")==null||req.getParameter("displayAmount")==null||
				req.getParameter("accountType")==null || req.getParameter("accountNumber")==null || 
				req.getParameter("code")==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Delete bank account");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
        
		String freq = req.getParameter("freq");
        String displayAmount = req.getParameter("displayAmount");
        String displayLabel = req.getParameter("accountType");
        String accountNumber = req.getParameter("accountNumber");
        String code = req.getParameter("code");
        
        String employeeNumber = demo.getEmpNbr();
        
        
        Boolean autoApprove = this.bankService.getAutoApproveAccountInfo(freq);
        
        Bank payrollAccountInfo = new Bank();
        
        payrollAccountInfo.setAccountNumber(accountNumber);
        Code c = new Code();
        c.setDisplayLabel(displayLabel);
        payrollAccountInfo.setAccountType(c);
        payrollAccountInfo.setCode(this.bankService.getBank(code));
        payrollAccountInfo.setDepositAmount(new Money(new Double (displayAmount).doubleValue(), Currency.getInstance(Locale.US)));
        payrollAccountInfo.setFrequency(Frequency.getFrequency(freq));
        
        Bank accountInfo;

        if(("").equals(payrollAccountInfo.getCode().getCode())) {
        	this.bankService.deleteAccountRequest(employeeNumber, freq, payrollAccountInfo, null);
        }
        else {
            accountInfo = new Bank();
            accountInfo.setAccountNumber("");
            accountInfo.setAccountType(new Code());
            accountInfo.setCode(new Code());
            accountInfo.setDepositAmount(new Money(0d, Currency.getInstance(Locale.US)));
            accountInfo.setFrequency(Frequency.getFrequency(freq));
        	this.bankService.insertAccountRequest(autoApprove, employeeNumber, freq,accountInfo , payrollAccountInfo );
	        if(autoApprove) {
	        	 this.bankService.deleteAccountApprove(employeeNumber, freq, payrollAccountInfo);
	        }
        }
        getProfileDetails(session, mav,freq);
        
        mav.setViewName("profile");
        return mav;
    }
    
    @RequestMapping("undoBank")
    public ModelAndView undoBank(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        ModelAndView mav = new ModelAndView();
		if(req.getParameter("freq")==null||req.getParameter("accountNumber")==null || req.getParameter("code")==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Undo bank account request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
        String freq = req.getParameter("freq");
        String code = req.getParameter("code");
        String accountNumber = req.getParameter("accountNumber");
        String employeeNumber = demo.getEmpNbr();

        Bank payrollAccountInfo = new Bank();
        payrollAccountInfo.setAccountNumber(accountNumber);
        payrollAccountInfo.setCode(this.bankService.getBank(code));
        payrollAccountInfo.setFrequency(Frequency.getFrequency(freq)); 
        this.bankService.deleteAccountRequest(employeeNumber, freq, payrollAccountInfo, null);       
        getProfileDetails(session, mav,freq);
        return mav;
    }
    
    @RequestMapping("getBankLimit")
    @ResponseBody
    public Integer getBankLimit(HttpServletRequest req){
    	
    	Integer limit = bankService.getDirectDepositLimit();
        return limit;
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
    
    @RequestMapping("getAccounts")
    @ResponseBody
    public JSONArray getAccounts(HttpServletRequest req){
    	
    	String employeeNumber = req.getParameter("employeeNumber");
    	String frequency = req.getParameter("frequency");
    	
    	List<Bank> banks = bankService.getAccounts(employeeNumber, frequency);
    	JSONArray json = JSONArray.fromObject(banks); 
        return json;
    }
    
    
    @RequestMapping("getAccountRequests")
    @ResponseBody
    public JSONArray getAccountRequests(HttpServletRequest req){
    	
    	String employeeNumber = req.getParameter("employeeNumber");
    	String frequency = req.getParameter("frequency");
    	
    	List<BankRequest> banks = bankService.getAccountRequests(employeeNumber, frequency);
    	JSONArray json = JSONArray.fromObject(banks);
        return json;
    }

    @RequestMapping("updatePassword")
    public ModelAndView updatePassword(HttpServletRequest req, String password){
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
		if(password==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Update Password");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
        if(StringUtils.isEmpty(password)) {
        	mav = new ModelAndView("redirect:/profile");
        	return mav;
        }
    	user.setUsrpswd(encoder.encode(password));
    	user.setTmpDts(user.getTmpDts()==null?"":user.getTmpDts());
    	this.indexService.updateUser(user);
    	session.removeAttribute("user");
    	session.setAttribute("user", user);
        getProfileDetails(session, mav,null);
        return mav;
    }
    
    @RequestMapping("saveName")
    public ModelAndView saveName(HttpServletRequest req, 
    		String empNbr, String reqDts, String namePreNew, String nameFNew, String nameLNew, String nameMNew, String nameGenNew) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
        
		if(empNbr==null||reqDts==null||namePreNew==null||nameFNew==null||nameLNew==null||nameMNew==null||nameGenNew==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save Name Request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
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
        
        this.getProfileDetails(session, mav,null);
        mav.addObject("activeTab", "nameRequest");
        return mav;
    }
    
    @RequestMapping("deleteNameRequest")
    public ModelAndView deleteNameRequest(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteNameRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav,null);
        return mav;
    } 
    
    @RequestMapping("changeAvatar")
    public ModelAndView changeAvatar(HttpServletRequest req, String file, String fileName) {
    	 HttpSession session = req.getSession();
         ModelAndView mav = new ModelAndView();
 		if(file==null||fileName==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Change user profile picture");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
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
	         demo.setAvatar("/uploadFiles/"+demo.getEmpNbr()+".jpg"+"?uploadTime="+Calendar.getInstance().getTimeInMillis());
	         this.indexService.updateDemoAvatar(demo);
	         session.removeAttribute("userDetail");
	         session.setAttribute("userDetail", demo);
	         this.getProfileDetails(session, mav,null);
         } catch (IOException e) {
	         e.printStackTrace();
         }
         
         return mav;
    }
    
    @RequestMapping("saveMarital")
    public ModelAndView saveMarital(HttpServletRequest req, 
    		String empNbr, String reqDts, String maritalStatNew) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
 		if(empNbr==null||reqDts==null||maritalStatNew==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save marital status request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
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
        
        this.getProfileDetails(session, mav,null);
        mav.addObject("activeTab", "maritalStatusRequest");
        return mav;
    }
    
    @RequestMapping("deleteMaritalRequest")
    public ModelAndView deleteMaritalRequest(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteMaritalRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav,null);
        return mav;
    }
    
    @RequestMapping("saveDriversLicense")
    public ModelAndView saveDriversLicense(HttpServletRequest req, 
    		String empNbr, String reqDts, String driversLicNbrNew, String driversLicStNew) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
 		if(empNbr==null||reqDts==null||driversLicNbrNew==null||driversLicStNew==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save marital status request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
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
        this.getProfileDetails(session, mav,null);
        mav.addObject("activeTab", "driversLicenseRequest");
        return mav;
    }
    
    @RequestMapping("deleteDriversLicenseRequest")
    public ModelAndView deleteDriversLicenseRequest(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteDirversLicenseRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav,null);
        return mav;
    }
    
    @RequestMapping("saveRestrictionCodes")
    public ModelAndView saveRestrictionCodes(HttpServletRequest req, 
    		String empNbr, String reqDts, String restrictCdNew, String restrictCdPublicNew) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
 		if(empNbr==null||reqDts==null||restrictCdNew==null||restrictCdPublicNew==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save restriction codes request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
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
        this.getProfileDetails(session, mav,null);
        mav.addObject("activeTab", "restrictionCodesRequest");
        return mav;
    }
    
    @RequestMapping("deleteRestrictionCodesRequest")
    public ModelAndView deleteRestrictionCodesRequest(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteRestrictionCodesRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav,null);
        return mav;
    } 
    
    @RequestMapping("saveEmail")
    public ModelAndView saveEmail(HttpServletRequest req, 
    		String empNbr, String reqDts, String emailNew, String hmEmailNew) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
 		if(empNbr==null||reqDts==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save user email address request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
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
        
        this.getProfileDetails(session, mav,null);
        mav.addObject("activeTab", "emailRequest");
        return mav;
    }
    
    @RequestMapping("deleteEmail")
    public ModelAndView deleteEmail(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteEmailRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav,null);
        return mav;
    } 
    
    @RequestMapping("saveEmergencyContact")
    public ModelAndView saveEmergencyContact(HttpServletRequest req, 
    		String empNbr, String reqDts, String emerContactNew, String emerPhoneAcNew
    		, String emerPhoneNbrNew, String emerPhoneExtNew, String emerRelNew, String emerNoteNew) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
 		if(empNbr==null||reqDts==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save user emergency contact request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
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
       
        this.getProfileDetails(session, mav,null);
        mav.addObject("activeTab", "emergencyContactRequest");
        return mav;
    }
    
    @RequestMapping("deleteEmergencyContact")
    public ModelAndView deleteEmergencyContact(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteEmergencyContactRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav,null);
        return mav;
    }
    
    @RequestMapping("saveMailAddr")
    public ModelAndView saveMailAddr(HttpServletRequest req, 
    		String empNbr, String reqDts, String addrNbrNew, String addrStrNew,String addrAptNew,
			String addrCityNew, String addrStNew, String addrZipNew,String addrZip4New) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
 		if(empNbr==null||reqDts==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save user mailing address request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
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
       
        this.getProfileDetails(session, mav,null);
        mav.addObject("activeTab", "mailingAddressRequest");
        return mav;
    }
    
    @RequestMapping("deleteMailAddr")
    public ModelAndView deleteMailAddr(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteMailAddrRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav,null);
        return mav;
    }
    
    @RequestMapping("saveAltMailAddr")
    public ModelAndView saveAltMailAddr(HttpServletRequest req, 
    		String empNbr, String reqDts, String smrAddrNbrNew, String smrAddrStrNew,String smrAddrAptNew,
			String smrAddrCityNew, String smrAddrStNew, String smrAddrZipNew,String smrAddrZip4New) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
 		if(empNbr==null||reqDts==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save user alternative mailing address request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
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
        
        this.getProfileDetails(session, mav,null);
        mav.addObject("activeTab", "altMailingAddressRequest");
        return mav;
    }
    
    @RequestMapping("deleteAltMailAddr")
    public ModelAndView deleteAltMailAddr(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteAltMailAddrRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav,null);
        return mav;
    } 
    
    @RequestMapping("savePhone")
    public ModelAndView savePhone(HttpServletRequest req, 
    		String empNbr, String reqDts, String phoneAreaNew, String phoneNbrNew,String phoneAreaCellNew,
			String phoneNbrCellNew, String phoneAreaBusNew, String phoneNbrBusNew,String busPhoneExtNew) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
 		if(empNbr==null||reqDts==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save user phone number request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
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
        this.getProfileDetails(session, mav,null);
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
        this.getProfileDetails(session, mav,null);
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
        this.getProfileDetails(session, mav,null);
        mav.addObject("activeTab", "businessPhoneRequest");
        
        return mav;
    }
    
    @RequestMapping("deletePhone")
    public ModelAndView deletePhone(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteHomePhoneRequest(demo.getEmpNbr());
        this.indexService.deleteCellPhoneRequest(demo.getEmpNbr());
        this.indexService.deleteBusinessPhoneRequest(demo.getEmpNbr());
        this.getProfileDetails(session, mav,null);
        return mav;
    }
    
    @RequestMapping("saveW4")
    public ModelAndView saveW4(HttpServletRequest req,String empNbr, String reqDts,  String payFreq,Character maritalStatTax, Character maritalStatTaxNew, Integer nbrTaxExempts, Integer nbrTaxExemptsNew) {
    	
    	 HttpSession session = req.getSession();
         ModelAndView mav = new ModelAndView();
  		if(empNbr==null||reqDts==null) {
 			mav.setViewName("visitFailed");
 			mav.addObject("module", module);
 			mav.addObject("action", "Save user W4 martial status request");
 			mav.addObject("errorMsg", "Not all mandotary fields provided.");
 			return mav;
 		}
         mav.setViewName("profile");
         BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
         BhrEmpPay pay = new BhrEmpPay();
         pay.setMaritalStatTax(maritalStatTax);
         pay.setNbrTaxExempts(nbrTaxExempts);
         BeaW4 w4Request;
     	 Frequency freq ;
		 freq = Frequency.getFrequency(payFreq);
         if(this.indexService.getBhrEapPayAssgnGrp("BEA_W4")) {
        	w4Request = new BeaW4(pay, empNbr,freq.getCode().charAt(0), reqDts,maritalStatTaxNew,nbrTaxExemptsNew,'A');
         	this.indexService.saveW4Request(w4Request);
         	this.indexService.updatePayInfo(demo, pay,freq.getCode().charAt(0),maritalStatTaxNew,nbrTaxExemptsNew);
         	
         }else {
        	w4Request = new BeaW4(pay, empNbr,freq.getCode().charAt(0), reqDts,maritalStatTaxNew,nbrTaxExemptsNew,'P');
         	this.indexService.saveW4Request(w4Request);
         }
         
         this.getProfileDetails(session, mav,freq.getCode());
         mav.addObject("activeTab", "w4Request");
         return mav;
    
    }
    
    
    @RequestMapping("deleteW4")
    public ModelAndView deleteW4(HttpServletRequest req,String empNbr, String reqDts,  String payFreq,Character maritalStatTax, Integer nbrTaxExempts) {
        HttpSession session = req.getSession();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("profile");
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteW4Request(demo.getEmpNbr(), payFreq, maritalStatTax, nbrTaxExempts);
        
        Frequency freq ;
		freq = Frequency.getFrequency(payFreq);
        this.getProfileDetails(session, mav,freq.getCode());
        return mav;
    }
    

    @RequestMapping("changePassword")
    public ModelAndView getChangePassword(HttpServletRequest req) throws ParseException{
       HttpSession session = req.getSession();
       BeaUsers user = (BeaUsers)session.getAttribute("user");
       ModelAndView mav = new ModelAndView();
       BeaUsers users = this.indexService.getUserPwd(user.getUsrname());
       mav.setViewName("changePassword");
       mav.addObject("id", users.getEmpNbr());
       return mav;
   }
    
	private void getProfileDetails(HttpSession session, ModelAndView mav, String freq) {
		BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        BeaLglName nameRequest = this.indexService.getBeaLglName(demo);
        BeaEmerContact emerRequest = this.indexService.getBeaEmerContact(demo);
        BeaDrvsLic licRequest = this.indexService.getBeaDrvsLic(demo);
        BeaMrtlStat mrtlRequest = this.indexService.getBeaMrtlStat(demo);
        BeaRestrict restrictRequest = this.indexService.getBeaRestrict(demo);
        BeaEmail emailRequest = this.indexService.getBeaEmail(demo);
        BeaCellPhone cellRequest = this.indexService.getBeaCellPhone(demo);
        BeaBusPhone busRequest = this.indexService.getBeaBusPhone(demo);
        BeaHmPhone hmRequest = this.indexService.getBeaHmPhone(demo);
        BeaAltMailAddr altMailAddrRequest = this.indexService.getBeaAltMailAddr(demo);
        BeaMailAddr mailAddrRequest = this.indexService.getBeaMailAddr(demo);
        
        List<Code> payRollFrequenciesOptions = this.referenceService.getPayrollFrequencies(demo.getEmpNbr());
    	
        PayInfo payInfo;
        BeaW4 w4Request;
        if (freq != null && !("").equals(freq)) {
        	mav.addObject("selectedFreq", freq);
        	payInfo = this.indexService.getPayInfo(demo,freq);
        	w4Request = this.indexService.getBeaW4(demo,freq);
        }
        else {
        	mav.addObject("selectedFreq", payRollFrequenciesOptions.get(0).getCode());
        	payInfo = this.indexService.getPayInfo(demo,payRollFrequenciesOptions.get(0).getDescription());
        	w4Request = this.indexService.getBeaW4(demo,payRollFrequenciesOptions.get(0).getDescription());
        }
        List<Code> maritalOptions = this.referenceService.getMaritalActualStatuses();
        List<Code> maritalTaxOptions = this.referenceService.getMaritalTaxStatuses();
        List<Code> generationOptions = this.referenceService.getGenerations();
        List<Code> titleOptions = this.referenceService.getTitles();
        List<Code> statesOptions = this.referenceService.getStates();
        List<Code> restrictionsOptions = this.referenceService.getRestrictions();
        
        String freqCode = freq;
        if (freq != null && !("").equals(freq)) {
        	freqCode = payRollFrequenciesOptions.get(0).getCode(); //TODO 
        }else {
        	freqCode = payRollFrequenciesOptions.get(0).getCode();
        }
        List<Bank> banks = this.bankService.getAccounts(demo.getEmpNbr(), freqCode);
        List<BankRequest> banksRequest = this.bankService.getAccountRequests(demo.getEmpNbr(), freqCode);
        List<BankRequest> allBanks = new ArrayList<BankRequest>();
        for(Bank b:banks) {
        	BankRequest br = new BankRequest();
        	br.setAccountNumber(b.getAccountNumber());
        	br.setAccountNumberNew(b.getAccountNumber());
        	br.setAccountType(b.getAccountType());
        	br.setAccountTypeNew(b.getAccountType());
        	br.setCode(b.getCode());
        	br.setCodeNew(b.getCode());
        	br.setDepositAmount(b.getDepositAmount());
        	br.setDepositAmountNew(b.getDepositAmount());
        	br.setFrequency(b.getFrequency());
        	allBanks.add(br);
        }
        for(BankRequest b:banksRequest) {
        	boolean isNewBank = true;
        	for(BankRequest ab:allBanks) {
        		if(this.bankService.checkSameRequest(b, ab)) {
        			ab.setAccountNumberNew(b.getAccountNumberNew());
        			ab.setAccountTypeNew(b.getAccountTypeNew());
        			ab.setDepositAmountNew(b.getDepositAmountNew());
        			ab.setCodeNew(b.getCodeNew());
        			ab.setIsDelete(b.getIsDelete());
        			isNewBank= false;
        		}
        	}
        	if(isNewBank) {
        		if(b.getCodeNew() != null && !b.getAccountNumberNew().isEmpty()) {
        		
	        		b.setAccountNumber("");
	        		b.setAccountType(new Code());
	        		b.setCode(new Code());
	        		b.setDepositAmount(new Money(0d,Currency.getInstance(Locale.US)));
	            	allBanks.add(b);
        		}
        	}
        	
        }
        
        List<Code> bankAccountTypes = this.referenceService.getDdAccountTypes();
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
        mav.addObject("payRollFrequenciesOptions", payRollFrequenciesOptions);
        mav.addObject("payInfo",payInfo);

        mav.addObject("banks", allBanks);
        mav.addObject("w4Request", w4Request);
        mav.addObject("bankAccountTypes",bankAccountTypes);
	}
	
    @RequestMapping(value = "validatePassword", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Boolean> validatePassword(HttpServletRequest req,String password) throws Exception{
    	HttpSession session = req.getSession();
    	Map<String, Boolean> result = new HashMap<>();
    	BeaUsers user = (BeaUsers) session.getAttribute("user");
    	if(encoder.matches(password, user.getUsrpswd()))
    		result.put("success", true);
    	else
    		result.put("success", false);
        return result;
    }
}
