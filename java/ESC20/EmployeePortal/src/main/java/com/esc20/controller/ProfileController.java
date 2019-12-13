package com.esc20.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
import com.esc20.nonDBModels.BankChanges;
import com.esc20.nonDBModels.BankRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.DemoInfoFields;
import com.esc20.nonDBModels.DemoOption;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Money;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.Page;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.PayInfoChanges;
import com.esc20.nonDBModels.PayrollFields;
import com.esc20.nonDBModels.PayrollOption;
import com.esc20.nonDBModels.SearchCriteria;
import com.esc20.nonDBModels.W4Info;
import com.esc20.security.CustomSHA256Encoder;
import com.esc20.service.BankService;
import com.esc20.service.IndexService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;
import com.esc20.util.MailUtil;
import com.esc20.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	private Logger logger = LoggerFactory.getLogger(ProfileController.class);

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
	public ModelAndView getProfile(HttpServletRequest req, String freq) {
		HttpSession session = req.getSession();

		BeaUsers user = (BeaUsers) session.getAttribute("user");

		ModelAndView mav = new ModelAndView();
		getProfileDetails(session, mav, freq);
		mav.addObject("decryptedPwd", user.getUsrpswd());

		return mav;
	}

	@RequestMapping("getAllBanks")
	@ResponseBody
	public JSONObject getAllBanks(HttpServletRequest req, @RequestBody Page page) {

		Page p = new Page();
		p.setCurrentPage(page.getCurrentPage());
		p.setPerPageRows(10);

		List<BthrBankCodes> allbanks = bankService.getAllBanks();

		p.setTotalRows(allbanks.size());
		p.setTotalPages((int) Math.ceil(p.getTotalRows() / p.getPerPageRows()));

		List<BthrBankCodes> banks = bankService.getAllBanks(p);
		JSONArray json = JSONArray.fromObject(banks);
		JSONObject result = new JSONObject();
		result.put("result", json);
		result.put("page", p);
		result.put("isSuccess", "true");

		return result;
	}

	@RequestMapping("searchBanks")
	@ResponseBody
	public JSONObject searchBanks(HttpServletRequest req, @RequestBody SearchCriteria searchCriteria) {

		Page p = searchCriteria.getPage();

		p.setPerPageRows(10);

		List<BthrBankCodes> allbanks = bankService.getAllBanks();

		p.setTotalRows(allbanks.size());
		p.setTotalPages((int) Math.ceil(p.getTotalRows() / p.getPerPageRows()));

		List<BthrBankCodes> banks = bankService.getAllBanks(searchCriteria.getCriteria(), p);
		JSONArray json = JSONArray.fromObject(banks);
		JSONObject result = new JSONObject();
		result.put("result", json);
		result.put("page", p);
		result.put("isSuccess", "true");

		return result;
	}

	@RequestMapping("saveBank")
	public ModelAndView saveBank(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));

		if (req.getParameter("freq") == null || req.getParameter("displayAmount") == null
				|| req.getParameter("displayLabel") == null || req.getParameter("accountNumber") == null
				|| req.getParameter("subCode") == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save bank account request information");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}

		String freq = req.getParameter("freq");
		String subCode = req.getParameter("subCode");
		/*String displayAmount = req.getParameter("displayAmount");
		String displayLabel = req.getParameter("displayLabel");
		String accountNumber = req.getParameter("accountNumber");
		String subCode = req.getParameter("subCode");
		String code = req.getParameter("code");*/
		String bankArray = req.getParameter("bankArray");
		JSONArray bankArrs=JSONArray.fromObject(bankArray);
		String employeeNumber = demo.getEmpNbr();

		Boolean autoApprove = this.bankService.getAutoApproveAccountInfo(freq);
		boolean accountSame = true;
		List <BankChanges> currentAccountInfoChanges = new ArrayList<>();
		
		for (int i =0; i < bankArrs.size(); i++){
			JSONObject bankJson = bankArrs.getJSONObject(i); 
			String isNew = bankJson.getString("isnew"); 
			
			String test = bankJson.getOrDefault("freq", "").toString();
			String displayAmount = bankJson.getString("displayAmount");
			String displayLabel = bankJson.getString("accountType");
			String accountNumber = bankJson.getString("accountNumber");
			String code = bankJson.getString("code");

			String displayAmountNew = bankJson.getString("displayAmountNew");
			String displayLabelNew = bankJson.getString("accountTypeNew");
			String accountNumberNew = bankJson.getString("accountNumberNew");
			String codeNew = bankJson.getString("codeNew");
			
			String displayAmountPending = bankJson.getString("displayAmountPending");
			String displayLabelPending = bankJson.getString("accountTypePending");
			String accountNumberPending = bankJson.getString("accountNumberPending");
			String codePending = bankJson.getString("codePending");
			BankChanges bc = new BankChanges();

			// Compare current and new value so to decide if need to send out email
			if (!displayAmountNew.equals(displayAmountPending)) {
				accountSame = false;
				bc.setDepositAmountChanged(true);
			}
			if (!displayLabelNew.equals(displayLabelPending)) {
				accountSame = false;
				bc.setAccountTypeChanged(true);
			}
			if (!accountNumberNew.equals(accountNumberPending)) {
				accountSame = false;
				bc.setAccountNumberChanged(true);
			}
			if (!codeNew.equals(codePending)) {
				accountSame = false;
				bc.setCodeChanged(true);
			}
			
			
			if(isNew.equals("true")) {
				displayAmount = req.getParameter("displayAmount");
				displayLabel = req.getParameter("displayLabel");
				accountNumber = req.getParameter("accountNumber");
				subCode = req.getParameter("subCode");
				code = req.getParameter("code");
				Bank payrollAccountInfo = new Bank();
				Code c = new Code();
				c.setDisplayLabel(displayLabel);
				payrollAccountInfo.setAccountNumber(accountNumber);
				payrollAccountInfo.setAccountType(c);
				payrollAccountInfo.setCode(this.bankService.getBank(subCode));
				payrollAccountInfo
						.setDepositAmount(new Money(new Double(displayAmount).doubleValue(), Currency.getInstance(Locale.US)));
				payrollAccountInfo.setFrequency(Frequency.getFrequency(freq));

				Bank accountInfo = new Bank();
				accountInfo.setAccountNumber("");
				accountInfo.setAccountType(new Code());
				//accountInfo.setCode(this.bankService.getBank(code));
				Code bcode = new Code();
				accountInfo.setCode(bcode);
				accountInfo.setDepositAmount(new Money(new Double(displayAmount).doubleValue(), Currency.getInstance(Locale.US)));
				accountInfo.setFrequency(Frequency.getFrequency(freq));

				this.bankService.insertAccountRequest(autoApprove, employeeNumber, freq, payrollAccountInfo, accountInfo);

				if (autoApprove) {
					this.bankService.insertAccountApprove(employeeNumber, freq, payrollAccountInfo);
					this.bankService.deleteNextYearAccounts(employeeNumber);
					this.bankService.insertNextYearAccounts(employeeNumber);
				}
				
				bc.setBank(payrollAccountInfo);
			}
			else { //is update part
				Bank accountInfo = new Bank();

				accountInfo.setAccountNumber(accountNumber);
				Code c = new Code();
				c.setDisplayLabel(displayLabel);
				accountInfo.setAccountType(c);
				accountInfo.setCode(this.bankService.getBank(code));
				accountInfo.setDepositAmount(new Money(new Double(displayAmount).doubleValue(), Currency.getInstance(Locale.US)));
				accountInfo.setFrequency(Frequency.getFrequency(freq));
				
				
				Bank pendingAccountInfo = new Bank();
				Code pc = new Code();
				pc.setDisplayLabel(displayLabelPending);
				pendingAccountInfo.setAccountNumber(accountNumberPending);
				pendingAccountInfo.setAccountType(pc);
				pendingAccountInfo.setCode(this.bankService.getBank(codePending));
				pendingAccountInfo.setDepositAmount(new Money(new Double(displayAmountPending).doubleValue(), Currency.getInstance(Locale.US)));
				pendingAccountInfo.setFrequency(Frequency.getFrequency(freq));
				

				Bank payrollAccountInfo = new Bank();
				Code nc = new Code();
				nc.setDisplayLabel(displayLabelNew);
				payrollAccountInfo.setAccountNumber(accountNumberNew);
				payrollAccountInfo.setAccountType(nc);
				payrollAccountInfo.setCode(this.bankService.getBank(codeNew));
				payrollAccountInfo.setDepositAmount(
						new Money(new Double(displayAmountNew).doubleValue(), Currency.getInstance(Locale.US)));
				payrollAccountInfo.setFrequency(Frequency.getFrequency(freq));
				
				bc.setBank(payrollAccountInfo);

				this.bankService.deleteAccountRequest(employeeNumber, freq, accountInfo, pendingAccountInfo);
				this.bankService.insertAccountRequest(autoApprove, employeeNumber, freq, payrollAccountInfo, accountInfo);

				if (autoApprove) {
					this.bankService.updateAccountApprove(employeeNumber, freq, payrollAccountInfo, accountInfo);
				}
				
			}
			
			currentAccountInfoChanges.add(bc);
			
		}

		//Send Out Email
		if (!accountSame) {		
			PayrollFields docRequiredFields = this.referenceService.populatePayrollDocRequiredFields(freq);
			//When only update Bank account then W4 will always same
			W4Info w4Info = new W4Info();
			PayInfoChanges currentPayInfoChanges = new PayInfoChanges();
			autoApprove = false;
			this.indexService.payrollDataChangeSendEmailConfirmation(demo,freq,true,accountSame,currentPayInfoChanges,w4Info,autoApprove,currentAccountInfoChanges,docRequiredFields);
		}

		getProfileDetails(session, mav, freq);

//        mav.setViewName("profile");
		return mav;
	}

	@RequestMapping(value = "updateBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> updateBank(@RequestBody JSONObject json, HttpServletRequest req) {
		HttpSession session = req.getSession();
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		Map<String, Boolean> res = new HashMap<>();
		//String bankArray = json.getString("bankArray");
		JSONArray bankArrs = json.getJSONArray("bankArray");
		String freq = "";
		Boolean autoApprove = false;
		boolean accountSame = true;
		List <BankChanges> currentAccountInfoChanges = new ArrayList<>();
		
		for (int i =0; i < bankArrs.size(); i++){
			JSONObject bankJson = bankArrs.getJSONObject(i); 
			freq = bankJson.getString("freq");
			
			String displayAmount = bankJson.getString("displayAmount");
			String displayLabel = bankJson.getString("accountType");
			String accountNumber = bankJson.getString("accountNumber");
			String code = bankJson.getString("code");

			String displayAmountNew = bankJson.getString("displayAmountNew");
			String displayLabelNew = bankJson.getString("accountTypeNew");
			String accountNumberNew = bankJson.getString("accountNumberNew");
			String codeNew = bankJson.getString("codeNew");
			
			String displayAmountPending = bankJson.getString("displayAmountPending");
			String displayLabelPending = bankJson.getString("accountTypePending");
			String accountNumberPending = bankJson.getString("accountNumberPending");
			String codePending = bankJson.getString("codePending");
			BankChanges bc = new BankChanges();

			// Compare current and new value so to decide if need to send out email
			if (!displayAmountNew.equals(displayAmountPending)) {
				accountSame = false;
				bc.setDepositAmountChanged(true);
			}
			if (!displayLabelNew.equals(displayLabelPending)) {
				accountSame = false;
				bc.setAccountTypeChanged(true);
			}
			if (!accountNumberNew.equals(accountNumberPending)) {
				accountSame = false;
				bc.setAccountNumberChanged(true);
			}
			if (!codeNew.equals(codePending)) {
				accountSame = false;
				bc.setCodeChanged(true);
			}
			
			if (displayAmount == null || displayLabel == null || accountNumber == null || code == null
					|| displayAmountNew == null || displayLabelNew == null || accountNumberNew == null || codeNew == null) {
				res.put("success", false);
				return res;
			}

			String employeeNumber = demo.getEmpNbr();

			autoApprove = this.bankService.getAutoApproveAccountInfo(freq);

			Bank accountInfo = new Bank();

			accountInfo.setAccountNumber(accountNumber);
			Code c = new Code();
			c.setDisplayLabel(displayLabel);
			accountInfo.setAccountType(c);
			accountInfo.setCode(this.bankService.getBank(code));
			accountInfo.setDepositAmount(new Money(new Double(displayAmount).doubleValue(), Currency.getInstance(Locale.US)));
			accountInfo.setFrequency(Frequency.getFrequency(freq));
			
			Bank pendingAccountInfo = new Bank();
			Code pc = new Code();
			pc.setDisplayLabel(displayLabelPending);
			pendingAccountInfo.setAccountNumber(accountNumberPending);
			pendingAccountInfo.setAccountType(pc);
			pendingAccountInfo.setCode(this.bankService.getBank(codePending));
			pendingAccountInfo.setDepositAmount(new Money(new Double(displayAmountPending).doubleValue(), Currency.getInstance(Locale.US)));
			pendingAccountInfo.setFrequency(Frequency.getFrequency(freq));
			

			Bank payrollAccountInfo = new Bank();
			Code nc = new Code();
			nc.setDisplayLabel(displayLabelNew);
			payrollAccountInfo.setAccountNumber(accountNumberNew);
			payrollAccountInfo.setAccountType(nc);
			payrollAccountInfo.setCode(this.bankService.getBank(codeNew));
			payrollAccountInfo.setDepositAmount(
					new Money(new Double(displayAmountNew).doubleValue(), Currency.getInstance(Locale.US)));
			payrollAccountInfo.setFrequency(Frequency.getFrequency(freq));
			
			bc.setBank(payrollAccountInfo);

			this.bankService.deleteAccountRequest(employeeNumber, freq, accountInfo, pendingAccountInfo);
			this.bankService.insertAccountRequest(autoApprove, employeeNumber, freq, payrollAccountInfo, accountInfo);

			if (autoApprove) {
				this.bankService.updateAccountApprove(employeeNumber, freq, payrollAccountInfo, accountInfo);
			}
			
			currentAccountInfoChanges.add(bc);
		}
		
		//Send Out Email
		if (!accountSame) {		
			PayrollFields docRequiredFields = this.referenceService.populatePayrollDocRequiredFields(freq);
			//When only update Bank account then W4 will always same
			W4Info w4Info = new W4Info();
			PayInfoChanges currentPayInfoChanges = new PayInfoChanges();
			autoApprove = false;
			this.indexService.payrollDataChangeSendEmailConfirmation(demo,freq,true,accountSame,currentPayInfoChanges,w4Info,autoApprove,currentAccountInfoChanges,docRequiredFields);
		}

		res.put("success", true);
		
		return res;
	}

	@RequestMapping("deleteBank")
	public ModelAndView deleteBank(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));

		ModelAndView mav = new ModelAndView();
		if (req.getParameter("freq") == null || req.getParameter("displayAmount") == null
				|| req.getParameter("accountType") == null || req.getParameter("accountNumber") == null
				|| req.getParameter("code") == null) {
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
		payrollAccountInfo
				.setDepositAmount(new Money(new Double(displayAmount).doubleValue(), Currency.getInstance(Locale.US)));
		payrollAccountInfo.setFrequency(Frequency.getFrequency(freq));

		Bank accountInfo;

		if (("").equals(payrollAccountInfo.getCode().getCode())) {
			this.bankService.deleteAccountRequest(employeeNumber, freq, payrollAccountInfo, null);
		} else {
			accountInfo = new Bank();
			accountInfo.setAccountNumber("");
			accountInfo.setAccountType(new Code());
			accountInfo.setCode(new Code());
			accountInfo.setDepositAmount(new Money(0d, Currency.getInstance(Locale.US)));
			accountInfo.setFrequency(Frequency.getFrequency(freq));
			this.bankService.insertAccountRequest(autoApprove, employeeNumber, freq, accountInfo, payrollAccountInfo);
			if (autoApprove) {
				this.bankService.deleteAccountApprove(employeeNumber, freq, payrollAccountInfo);
			}
		}
		getProfileDetails(session, mav, freq);

		mav.setViewName("profile");
		return mav;
	}

	@RequestMapping("undoBank")
	public ModelAndView undoBank(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		ModelAndView mav = new ModelAndView();
		if (req.getParameter("freq") == null || req.getParameter("accountNumber") == null
				|| req.getParameter("code") == null) {
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
		String bankArray = req.getParameter("bankArray");
		JSONArray bankArrs=JSONArray.fromObject(bankArray);
		for (int i =0; i < bankArrs.size(); i++){
			JSONObject bankJson = bankArrs.getJSONObject(i); 
			//String isNew = bankJson.getString("isnew"); 
			
			String displayAmount = bankJson.getString("displayAmount");
			String displayLabel = bankJson.getString("accountType");
			accountNumber = bankJson.getString("accountNumber");
			code = bankJson.getString("code");

//			String displayAmountNew = bankJson.getString("displayAmountNew");
//			String displayLabelNew = bankJson.getString("accountTypeNew");
//			String accountNumberNew = bankJson.getString("accountNumberNew");
//			String codeNew = bankJson.getString("codeNew");
			
			String displayAmountPending = bankJson.getString("displayAmountPending");
			String displayLabelPending = bankJson.getString("accountTypePending");
			String accountNumberPending = bankJson.getString("accountNumberPending");
			String codePending = bankJson.getString("codePending");
			
			Bank accountInfo = new Bank();
			accountInfo.setAccountNumber(accountNumber);
			Code c = new Code();
			c.setDisplayLabel(displayLabel);
			accountInfo.setAccountType(c);
			accountInfo.setCode(this.bankService.getBank(code));
			accountInfo.setDepositAmount(new Money(new Double(displayAmount).doubleValue(), Currency.getInstance(Locale.US)));
			accountInfo.setFrequency(Frequency.getFrequency(freq));
			
			Bank pendingAccountInfo = new Bank();
			Code pc = new Code();
			pc.setDisplayLabel(displayLabelPending);
			pendingAccountInfo.setAccountNumber(accountNumberPending);
			pendingAccountInfo.setAccountType(pc);
			pendingAccountInfo.setCode(this.bankService.getBank(codePending));
			pendingAccountInfo.setDepositAmount(new Money(new Double(displayAmountPending).doubleValue(), Currency.getInstance(Locale.US)));
			pendingAccountInfo.setFrequency(Frequency.getFrequency(freq));
			
			Bank payrollAccountInfo = new Bank();
			payrollAccountInfo.setAccountNumber(accountNumber);
			payrollAccountInfo.setCode(this.bankService.getBank(code));
			payrollAccountInfo.setFrequency(Frequency.getFrequency(freq));
			this.bankService.deleteAccountRequest(employeeNumber, freq, accountInfo, pendingAccountInfo);
		}
		
		/*Bank payrollAccountInfo = new Bank();
		payrollAccountInfo.setAccountNumber(accountNumber);
		payrollAccountInfo.setCode(this.bankService.getBank(code));
		payrollAccountInfo.setFrequency(Frequency.getFrequency(freq));
		this.bankService.deleteAccountRequest(employeeNumber, freq, payrollAccountInfo, null);
*/
		
		getProfileDetails(session, mav, freq);
		return mav;
	}

	@RequestMapping("getBankLimit")
	@ResponseBody
	public Integer getBankLimit(HttpServletRequest req) {

		Integer limit = bankService.getDirectDepositLimit();
		return limit;
	}

	@RequestMapping("getBanks")
	@ResponseBody
	public JSONArray getBanks(HttpServletRequest req) {

		String routingNumber = req.getParameter("routingNumber");
		String bankName = req.getParameter("bankName");

		BthrBankCodes bbc = new BthrBankCodes();
		bbc.setTransitRoute(routingNumber);
		bbc.setBankName(bankName);

		List<BthrBankCodes> banks = bankService.getBanksByEntity(bbc);
		JSONArray json = JSONArray.fromObject(banks);
		return json;
	}

	@RequestMapping("getAccounts")
	@ResponseBody
	public JSONArray getAccounts(HttpServletRequest req) {

		String employeeNumber = req.getParameter("employeeNumber");
		String frequency = req.getParameter("frequency");

		List<Bank> banks = bankService.getAccounts(employeeNumber, frequency);
		JSONArray json = JSONArray.fromObject(banks);
		return json;
	}

	@RequestMapping("getAccountRequests")
	@ResponseBody
	public JSONArray getAccountRequests(HttpServletRequest req) {

		String employeeNumber = req.getParameter("employeeNumber");
		String frequency = req.getParameter("frequency");

		List<BankRequest> banks = bankService.getAccountRequests(employeeNumber, frequency);
		JSONArray json = JSONArray.fromObject(banks);
		return json;
	}

	@RequestMapping("updatePassword")
	public ModelAndView updatePassword(HttpServletRequest req, String password) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (password == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Update Password");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		if (StringUtils.isEmpty(password)) {
			mav = new ModelAndView("redirect:/profile");
			return mav;
		}
		user.setUsrpswd(encoder.encode(password));
		user.setTmpDts("");
		user.setTmpCnt(0);
		user.setLkHint('N');
		user.setHintCnt(0);
		this.indexService.updateUser(user);
		// Send out Email to User
		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
		this.indexService.passwordChangeSendEmailConfirmation(user.getUsrname(), userDetail.getNameF(),
				userDetail.getNameL(), userDetail.getHmEmail(), userDetail.getEmail());
		session.removeAttribute("user");
		session.setAttribute("user", user);
		getProfileDetails(session, mav, null);
		return mav;
	}

	@RequestMapping("saveAll")
	public ModelAndView saveAll(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));//this is old value before change, we need the old info when sending email

		ModelAndView mav = new ModelAndView();

		DemoOption demoOptions = this.indexService.getDemoOption();

		Enumeration<String> names = req.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			System.out.println(name + ":" + req.getParameter(name));
		}

		String undoName = req.getParameter("undoName");

		Boolean isAnyChanges = false; // Use this to see if we need to send out email,if true then send out emails
		session.setAttribute("hasDemoChanged", isAnyChanges);

		DemoInfoFields demoInfoChanges = new DemoInfoFields();
		session.setAttribute("demoInfoChanges", demoInfoChanges);

		if (!"deleteNameRequest".equalsIgnoreCase(undoName)) {
			if (demoOptions.getFieldDisplayOptionName().trim().equals("U")) {
				saveName(req, mav);
			}
		}

		if (!"deleteMaritalRequest".equalsIgnoreCase(undoName)) {
			if (demoOptions.getFieldDisplayOptionMarital().trim().equals("U")) {
				saveMarital(req, mav);
			}
		}

		if (!"deleteDriversLicenseRequest".equalsIgnoreCase(undoName)) {
			if (demoOptions.getFieldDisplayOptionDriversLicense().trim().equals("U")) {
				saveDriversLicense(req, mav);
			}
		}

		if (!"deleteRestrictionCodesRequest".equalsIgnoreCase(undoName)) {
			if (demoOptions.getFieldDisplayOptionRestrictionCodes().trim().equals("U")) {
				saveRestrictionCodes(req, mav);
			}
		}

		if (!"deleteEmail".equalsIgnoreCase(undoName)) {
			if (demoOptions.getFieldDisplayOptionEmail().trim().equals("U")) {
				saveEmail(req, mav);
			}
		}

		if (!"deleteEmergencyContact".equalsIgnoreCase(undoName)) {
			if (demoOptions.getFieldDisplayOptionEmergencyContact().trim().equals("U")) {
				saveEmergencyContact(req, mav);
			}
		}

		if (!"deleteMailAddr".equalsIgnoreCase(undoName)) {
			if (demoOptions.getFieldDisplayOptionMailAddr().trim().equals("U")) {
				saveMailAddr(req, mav);
			}
		}

		if (!"deleteAltMailAddr".equalsIgnoreCase(undoName)) {
			if (demoOptions.getFieldDisplayOptionMailAddr().trim().equals("U")) {
				saveAltMailAddr(req, mav);
			}
		}

		if (!"deletePhone".equalsIgnoreCase(undoName)) {
			if (demoOptions.getFieldDisplayOptionCellPhone().trim().equals("U")
					|| demoOptions.getFieldDisplayOptionHomePhone().trim().equals("U")
					|| demoOptions.getFieldDisplayOptionWorkPhone().trim().equals("U")) {
				savePhone(req, mav);
			}
		}

		// undo check --need to undo changes for the ones with Pending to approve
		undoHandle(req);

		// Send out Email to User
		isAnyChanges = (Boolean) session.getAttribute("hasDemoChanged");
		if (isAnyChanges) {		
			BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
			demoInfoChanges = ((DemoInfoFields) session.getAttribute("demoInfoChanges"));
			DemoInfoFields docRequiredFields = this.referenceService.populateDocRequiredFields();
			this.indexService.personDataChangeSendEmailConfirmation(demo,userDetail,demoInfoChanges,docRequiredFields);
		}

		this.getProfileDetails(req.getSession(), mav, null);
		return mav;
	}

	public void undoHandle(HttpServletRequest req) {

		String undoName = req.getParameter("undoName");
		if ("deleteNameRequest".equalsIgnoreCase(undoName)) {
			deleteNameRequest(req);
		}
		if ("deleteMaritalRequest".equalsIgnoreCase(undoName)) {
			deleteMaritalRequest(req);
		}
		if ("deleteDriversLicenseRequest".equalsIgnoreCase(undoName)) {
			deleteDriversLicenseRequest(req);
		}
		if ("deleteRestrictionCodesRequest".equalsIgnoreCase(undoName)) {
			deleteRestrictionCodesRequest(req);
		}
		if ("deleteEmail".equalsIgnoreCase(undoName)) {
			deleteEmail(req);
		}
		if ("deleteEmergencyContact".equalsIgnoreCase(undoName)) {
			deleteEmergencyContact(req);
		}
		if ("deleteMailAddr".equalsIgnoreCase(undoName)) {
			deleteMailAddr(req);
		}
		if ("deleteAltMailAddr".equalsIgnoreCase(undoName)) {
			deleteAltMailAddr(req);
		}
		if ("deletePhone".equalsIgnoreCase(undoName)) {
			deletePhone(req);
		}
		if ("deleteW4".equalsIgnoreCase(undoName)) {
//    		deleteW4(req);
		}
//    	deleteW4(req);
	}

	public ModelAndView saveName(HttpServletRequest req, ModelAndView mav) {

		String empNbr = req.getParameter("empNbr");
		String reqDts = req.getParameter("nameReqDts");
		String namePreNew = req.getParameter("namePreNew");
		String nameFNew = req.getParameter("nameFNew");
		String nameLNew = req.getParameter("nameLNew");
		String nameMNew = req.getParameter("nameMNew");
		String nameGenNew = req.getParameter("nameGenNew");
		HttpSession session = req.getSession();
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		Boolean isAnyChanges = ((Boolean) session.getAttribute("hasDemoChanged"));
		DemoInfoFields demoInfoChanges = ((DemoInfoFields) session.getAttribute("demoInfoChanges"));

		String nameLLNGNew = req.getParameter("nameLNew");
		String nameMLNGNew = req.getParameter("nameMNew");
		String nameFLNGNew = req.getParameter("nameFNew");

		if ((!StringUtil.isNullOrEmpty(nameFNew)) && nameFNew.trim().length() > 17) {
			nameFNew = nameFLNGNew.trim().substring(0, 17);
		}
		if ((!StringUtil.isNullOrEmpty(nameLNew)) && nameLNew.trim().length() > 25) {
			nameLNew = nameLLNGNew.trim().substring(0, 25);
		}
		if ((!StringUtil.isNullOrEmpty(nameMNew)) && nameMNew.trim().length() > 14) {
			nameMNew = nameMLNGNew.trim().substring(0, 14);
		}

		if (empNbr == null || reqDts == null || namePreNew == null || nameFNew == null || nameLNew == null
				|| nameMNew == null || nameGenNew == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save Name Request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("profile");

		// Compare current and new value so to decide if need to send out email
		BeaLglName nameOldRequest = this.indexService.getBeaLglName(demo);
		
		if (!namePreNew.equals(nameOldRequest.getNamePreNew())) {
			isAnyChanges = true;
			demoInfoChanges.setNameTitle(true);
		}
		if (!nameFNew.equals(nameOldRequest.getNameFNew())) {
			isAnyChanges = true;
			demoInfoChanges.setNameFirst(true);
		}
		if (!nameLNew.equals(nameOldRequest.getNameLNew())) {
			isAnyChanges = true;
			demoInfoChanges.setNameLast(true);
		}
		if (!nameMNew.equals(nameOldRequest.getNameMNew())) {
			isAnyChanges = true;
			demoInfoChanges.setNameMiddle(true);
		}

		if(nameOldRequest.getNameGenNew()==null) {
			if(nameGenNew !="") {
				isAnyChanges = true;
				demoInfoChanges.setNameGeneration(true);
			}
		}
		else  {
			if(!nameGenNew.trim().equals(nameOldRequest.getNameGenNew().toString().trim())) {
				isAnyChanges = true;
				demoInfoChanges.setNameGeneration(true);
			}
		}

		session.setAttribute("hasDemoChanged", isAnyChanges);
		session.setAttribute("demoInfoChanges", demoInfoChanges);

		BeaLglName nameRequest;

		if (this.indexService.getBhrEapDemoAssgnGrp("BEA_LGL_NAME")) {
			nameRequest = new BeaLglName(demo, empNbr, reqDts, namePreNew, nameFLNGNew, nameLLNGNew, nameMLNGNew,
					(nameGenNew == null || ("").equals(nameGenNew)) ? '\0' : nameGenNew.charAt(0), 'A');
			this.indexService.saveNameRequest(nameRequest);
			demo.setNamePre(namePreNew);
			demo.setNameF(nameFNew);
			demo.setNameL(nameLNew);
			demo.setNameM(nameMNew);
			demo.setNameFLng(nameFLNGNew);
			demo.setNameLLng(nameLLNGNew);
			demo.setNameMLng(nameMLNGNew);
			demo.setNameGen((nameGenNew == null || ("").equals(nameGenNew)) ? '\0' : nameGenNew.charAt(0));
			this.indexService.updateDemoName(demo);
			List<Code> gens = referenceService.getGenerations();
			for (Code gen : gens) {
				if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
					demo.setGenDescription(gen.getDescription());
				}
			}

			session.removeAttribute("userDetail");
			session.setAttribute("userDetail", demo);
		} else {
			nameRequest = new BeaLglName(demo, empNbr, reqDts, namePreNew, nameFLNGNew, nameLLNGNew, nameMLNGNew,
					(nameGenNew == null || ("").equals(nameGenNew)) ? '\0' : nameGenNew.charAt(0), 'P');
			this.indexService.saveNameRequest(nameRequest);
		}

//        this.getProfileDetails(session, mav,null);
		mav.addObject("activeTab", "nameRequest");
		return mav;
	}

	public ModelAndView deleteNameRequest(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		this.indexService.deleteNameRequest(demo.getEmpNbr());
//        this.getProfileDetails(session, mav,null);
		return mav;
	}

	public ModelAndView saveMarital(HttpServletRequest req, ModelAndView mav) {
		HttpSession session = req.getSession();

		String empNbr = req.getParameter("empNbr");
		String reqDts = req.getParameter("mrtlReqDts");
		String maritalStatNew = req.getParameter("maritalStatNew");

		if (empNbr == null || reqDts == null || maritalStatNew == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save marital status request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		Boolean isAnyChanges = ((Boolean) session.getAttribute("hasDemoChanged"));
		DemoInfoFields demoInfoChanges = ((DemoInfoFields) session.getAttribute("demoInfoChanges"));

		// Compare current and new value so to decide if need to send out email
		BeaMrtlStat mrtlRequest = this.indexService.getBeaMrtlStat(demo);
		
		if(mrtlRequest.getMaritalStatNew()==null) {
			if(maritalStatNew !="") {
				isAnyChanges = true;
				demoInfoChanges.setMaritalLocal(true);
			}
		}else {
			if (!maritalStatNew.trim().equals(mrtlRequest.getMaritalStatNew().toString().trim())) {
				isAnyChanges = true;
				demoInfoChanges.setMaritalLocal(true);
			}
		}
		

		session.setAttribute("hasDemoChanged", isAnyChanges);
		session.setAttribute("demoInfoChanges", demoInfoChanges);
		BeaMrtlStat maritalStatusRequest;
		if (this.indexService.getBhrEapDemoAssgnGrp("BEA_MRTL_STAT")) {
			maritalStatusRequest = new BeaMrtlStat(demo, empNbr, reqDts, maritalStatNew, 'A');
			this.indexService.saveMaritalRequest(maritalStatusRequest);
		
			demo.setMaritalStat((maritalStatNew == null || ("").equals(maritalStatNew)) ? '\0' : maritalStatNew.charAt(0));
			this.indexService.updateDemoMaritalStatus(demo);
			session.removeAttribute("userDetail");

			List<Code> gens = referenceService.getGenerations();
			for (Code gen : gens) {
				if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
					demo.setGenDescription(gen.getDescription());
				}
			}

			session.setAttribute("userDetail", demo);
		} else {
			maritalStatusRequest = new BeaMrtlStat(demo, empNbr, reqDts, maritalStatNew, 'P');
			this.indexService.saveMaritalRequest(maritalStatusRequest);
		}

//        this.getProfileDetails(session, mav,null);
		mav.addObject("activeTab", "maritalStatusRequest");
		return mav;
	}

	public ModelAndView deleteMaritalRequest(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		this.indexService.deleteMaritalRequest(demo.getEmpNbr());
//        this.getProfileDetails(session, mav,null);
		return mav;
	}

	public ModelAndView saveDriversLicense(HttpServletRequest req, ModelAndView mav) {
		HttpSession session = req.getSession();
		String empNbr = req.getParameter("empNbr");
		String reqDts = req.getParameter("licReqDts");
		String driversLicNbrNew = req.getParameter("driversLicNbrNew");
		String driversLicStNew = req.getParameter("driversLicStNew");

		if (empNbr == null || reqDts == null || driversLicNbrNew == null || driversLicStNew == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save marital status request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		Boolean isAnyChanges = ((Boolean) session.getAttribute("hasDemoChanged"));
		DemoInfoFields demoInfoChanges = ((DemoInfoFields) session.getAttribute("demoInfoChanges"));
		BeaDrvsLic driversLicenseRequest;

		// Compare current and new value so to decide if need to send out email
		BeaDrvsLic licRequest = this.indexService.getBeaDrvsLic(demo);
	
		if (!driversLicNbrNew.equals(licRequest.getDriversLicNbrNew())) {
			isAnyChanges = true;
			demoInfoChanges.setDriversNum(true);
		}
		if (!driversLicStNew.equals(licRequest.getDriversLicStNew())) {
			isAnyChanges = true;
			demoInfoChanges.setDriversState(true);
		}

		session.setAttribute("hasDemoChanged", isAnyChanges);
		session.setAttribute("demoInfoChanges", demoInfoChanges);

		if (this.indexService.getBhrEapDemoAssgnGrp("BEA_DRVS_LIC")) {
			driversLicenseRequest = new BeaDrvsLic(demo, empNbr, reqDts, driversLicNbrNew, driversLicStNew, 'A');
			this.indexService.saveDriversLicenseRequest(driversLicenseRequest);
			demo.setDriversLicNbr(driversLicNbrNew);
			demo.setDriversLicSt(driversLicStNew);
			this.indexService.updateDemoDriversLicense(demo);
			List<Code> gens = referenceService.getGenerations();
			for (Code gen : gens) {
				if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
					demo.setGenDescription(gen.getDescription());
				}
			}

			session.removeAttribute("userDetail");
			session.setAttribute("userDetail", demo);
		} else {
			driversLicenseRequest = new BeaDrvsLic(demo, empNbr, reqDts, driversLicNbrNew, driversLicStNew, 'P');
			this.indexService.saveDriversLicenseRequest(driversLicenseRequest);
		}
//        this.getProfileDetails(session, mav,null);
		mav.addObject("activeTab", "driversLicenseRequest");
		return mav;
	}

	public ModelAndView deleteDriversLicenseRequest(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		this.indexService.deleteDirversLicenseRequest(demo.getEmpNbr());
//        this.getProfileDetails(session, mav,null);
		return mav;
	}

	public ModelAndView saveRestrictionCodes(HttpServletRequest req, ModelAndView mav) {
		HttpSession session = req.getSession();
		String empNbr = req.getParameter("empNbr");
		String reqDts = req.getParameter("restrictReqDts");
		String restrictCdNew = req.getParameter("restrictCdNew");
		String restrictCdPublicNew = req.getParameter("restrictCdPublicNew");

		if (empNbr == null || reqDts == null || restrictCdNew == null || restrictCdPublicNew == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save restriction codes request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		Boolean isAnyChanges = ((Boolean) session.getAttribute("hasDemoChanged"));
		DemoInfoFields demoInfoChanges = ((DemoInfoFields) session.getAttribute("demoInfoChanges"));
		BeaRestrict restrictionCodesRequest;

		// Compare current and new value so to decide if need to send out email
		BeaRestrict restrictRequest = this.indexService.getBeaRestrict(demo);
		
		if(restrictRequest.getRestrictCdNew()==null) {
			if(restrictCdNew !="") {
				isAnyChanges = true;
				demoInfoChanges.setRestrictionLocal(true);
			}
		}
		else {
			if (!restrictCdNew.trim().equals(restrictRequest.getRestrictCdNew().toString().trim())) {
				isAnyChanges = true;
				demoInfoChanges.setRestrictionLocal(true);
			}
		}
		
		if(restrictRequest.getRestrictCdPublicNew()==null) {
			if(restrictCdPublicNew !="") {
				isAnyChanges = true;
				demoInfoChanges.setRestrictionPublic(true);
			}
		}  else {
			if (!restrictCdPublicNew.trim().equals(restrictRequest.getRestrictCdPublicNew().toString().trim())) {
				isAnyChanges = true;
				demoInfoChanges.setRestrictionPublic(true);
			}
		}
		
		

		session.setAttribute("hasDemoChanged", isAnyChanges);
		session.setAttribute("demoInfoChanges", demoInfoChanges);

		if (this.indexService.getBhrEapDemoAssgnGrp("BEA_RESTRICT")) {
			restrictionCodesRequest = new BeaRestrict(demo, empNbr, reqDts, restrictCdNew, restrictCdPublicNew, 'A');
			this.indexService.saveRestrictionCodesRequest(restrictionCodesRequest);
			demo.setRestrictCd((restrictCdNew == null || ("").equals(restrictCdNew)) ? '\0' : restrictCdNew.charAt(0));
			demo.setRestrictCdPublic((restrictCdPublicNew == null || ("").equals(restrictCdPublicNew)) ? '\0' : restrictCdPublicNew.charAt(0));
			this.indexService.updateDemoRestrictionCodes(demo);
			List<Code> gens = referenceService.getGenerations();
			for (Code gen : gens) {
				if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
					demo.setGenDescription(gen.getDescription());
				}
			}

			session.removeAttribute("userDetail");
			session.setAttribute("userDetail", demo);
		} else {
			restrictionCodesRequest = new BeaRestrict(demo, empNbr, reqDts, restrictCdNew, restrictCdPublicNew, 'P');
			this.indexService.saveRestrictionCodesRequest(restrictionCodesRequest);
		}
//        this.getProfileDetails(session, mav,null);
		mav.addObject("activeTab", "restrictionCodesRequest");
		return mav;
	}

	public ModelAndView deleteRestrictionCodesRequest(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		this.indexService.deleteRestrictionCodesRequest(demo.getEmpNbr());
//        this.getProfileDetails(session, mav,null);
		return mav;
	}

	public ModelAndView saveEmail(HttpServletRequest req, ModelAndView mav) {
		HttpSession session = req.getSession();

		String empNbr = req.getParameter("empNbr");
		String reqDts = req.getParameter("emailReqDts");
		String emailNew = req.getParameter("emailNew");
		String hmEmailNew = req.getParameter("hmEmailNew");

		if (empNbr == null || reqDts == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save user email address request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		Boolean isAnyChanges = ((Boolean) session.getAttribute("hasDemoChanged"));
		DemoInfoFields demoInfoChanges = ((DemoInfoFields) session.getAttribute("demoInfoChanges"));
		BeaEmail emailRequest;

		// Compare current and new value so to decide if need to send out email
		BeaEmail emailPendingRequest = this.indexService.getBeaEmail(demo);
		
		
		if (!emailNew.equals(emailPendingRequest.getEmailNew())) {
			isAnyChanges = true;
			demoInfoChanges.setEmailWork(true);
		}
		if (!hmEmailNew.equals(emailPendingRequest.getHmEmailNew())) {
			isAnyChanges = true;
			demoInfoChanges.setEmailHome(true);
		}

		session.setAttribute("hasDemoChanged", isAnyChanges);
		session.setAttribute("demoInfoChanges", demoInfoChanges);

		if (this.indexService.getBhrEapDemoAssgnGrp("BEA_EMAIL")) {
			emailRequest = new BeaEmail(demo, empNbr, reqDts, emailNew, hmEmailNew, 'A');
			this.indexService.saveEmailRequest(emailRequest);
			demo.setEmail(emailNew);
			demo.setHmEmail(hmEmailNew);
			this.indexService.updateDemoEmail(demo);
			List<Code> gens = referenceService.getGenerations();
			for (Code gen : gens) {
				if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
					demo.setGenDescription(gen.getDescription());
				}
			}

			session.removeAttribute("userDetail");
			session.setAttribute("userDetail", demo);
		} else {
			emailRequest = new BeaEmail(demo, empNbr, reqDts, emailNew, hmEmailNew, 'P');
			this.indexService.saveEmailRequest(emailRequest);
		}

//        this.getProfileDetails(session, mav,null);
		mav.addObject("activeTab", "emailRequest");
		return mav;
	}

	public ModelAndView deleteEmail(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		this.indexService.deleteEmailRequest(demo.getEmpNbr());
//        this.getProfileDetails(session, mav,null);
		return mav;
	}

	public ModelAndView saveEmergencyContact(HttpServletRequest req, ModelAndView mav) {
		HttpSession session = req.getSession();

		String empNbr = req.getParameter("empNbr");
		String reqDts = req.getParameter("emerReqDts");
		String emerContactNew = req.getParameter("emerContactNew");
		String emerPhoneAcNew = req.getParameter("emerPhoneAcNew");
		String emerPhoneNbrNew = req.getParameter("emerPhoneNbrNew");
		String emerPhoneExtNew = req.getParameter("emerPhoneExtNew");
		emerPhoneExtNew = emerPhoneExtNew==null?emerPhoneExtNew:emerPhoneExtNew.trim();
		String emerRelNew = req.getParameter("emerRelNew");
		String emerNoteNew = req.getParameter("emerNoteNew");
		emerPhoneNbrNew = emerPhoneNbrNew.replaceAll("-", "");

		if (empNbr == null || reqDts == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save user emergency contact request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		Boolean isAnyChanges = ((Boolean) session.getAttribute("hasDemoChanged"));
		DemoInfoFields demoInfoChanges = ((DemoInfoFields) session.getAttribute("demoInfoChanges"));
		BeaEmerContact emergencyContactRequest;

		// Compare current and new value so to decide if need to send out email
		BeaEmerContact emerRequest = this.indexService.getBeaEmerContact(demo);
	
		if (!emerContactNew.equals(emerRequest.getEmerContactNew())) {
			isAnyChanges = true;
			demoInfoChanges.setEmergencyName(true);
		}
		if (!emerPhoneAcNew.equals(emerRequest.getEmerPhoneAcNew())) {
			isAnyChanges = true;
			demoInfoChanges.setEmergencyAreaCode(true);
		}
		if (!emerPhoneNbrNew.equals(emerRequest.getEmerPhoneNbrNew())) {
			isAnyChanges = true;
			demoInfoChanges.setEmergencyPhoneNum(true);
		}
		if (!emerPhoneExtNew.equals(emerRequest.getEmerPhoneExtNew())) {
			isAnyChanges = true;
			demoInfoChanges.setEmergencyPhoneExt(true);
		}
		if (!emerRelNew.equals(emerRequest.getEmerRelNew())) {
			isAnyChanges = true;
			demoInfoChanges.setEmergencyRelationship(true);
		}
		if (!emerNoteNew.equals(emerRequest.getEmerNoteNew())) {
			isAnyChanges = true;
			demoInfoChanges.setEmergencyNotes(true);
		}

		session.setAttribute("hasDemoChanged", isAnyChanges);
		session.setAttribute("demoInfoChanges", demoInfoChanges);

		if (this.indexService.getBhrEapDemoAssgnGrp("BEA_EMER_CONTACT")) {
			emergencyContactRequest = new BeaEmerContact(demo, empNbr, reqDts, emerContactNew, emerPhoneAcNew,
					emerPhoneNbrNew, emerPhoneExtNew, emerRelNew, emerNoteNew, 'A');
			this.indexService.saveEmergencyContactRequest(emergencyContactRequest);
			demo.setEmerContact(emerContactNew);
			demo.setEmerPhoneAc(emerPhoneAcNew);
			demo.setEmerPhoneNbr(emerPhoneNbrNew);
			demo.setEmerPhoneExt(emerPhoneExtNew);
			demo.setEmerRel(emerRelNew);
			demo.setEmerNote(emerNoteNew);
			this.indexService.updateDemoEmergencyContact(demo);
			session.removeAttribute("userDetail");
			List<Code> gens = referenceService.getGenerations();
			for (Code gen : gens) {
				if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
					demo.setGenDescription(gen.getDescription());
				}
			}

			session.setAttribute("userDetail", demo);
		} else {
			emergencyContactRequest = new BeaEmerContact(demo, empNbr, reqDts, emerContactNew, emerPhoneAcNew,
					emerPhoneNbrNew, emerPhoneExtNew, emerRelNew, emerNoteNew, 'P');
			this.indexService.saveEmergencyContactRequest(emergencyContactRequest);
		}

//        this.getProfileDetails(session, mav,null);
		mav.addObject("activeTab", "emergencyContactRequest");
		return mav;
	}

	public ModelAndView deleteEmergencyContact(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		this.indexService.deleteEmergencyContactRequest(demo.getEmpNbr());
//        this.getProfileDetails(session, mav,null);
		return mav;
	}

	public ModelAndView saveMailAddr(HttpServletRequest req, ModelAndView mav) {
		HttpSession session = req.getSession();
		String empNbr = req.getParameter("empNbr");
		String reqDts = req.getParameter("mailAddrReqDts");
		String addrNbrNew = req.getParameter("addrNbrNew");
		String addrStrNew = req.getParameter("addrStrNew");
		String addrAptNew = req.getParameter("addrAptNew");
		String addrCityNew = req.getParameter("addrCityNew");
		String addrStNew = req.getParameter("addrStNew");
		String addrZipNew = req.getParameter("addrZipNew");
		String addrZip4New = req.getParameter("addrZip4New");

		if (empNbr == null || reqDts == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save user mailing address request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		Boolean isAnyChanges = ((Boolean) session.getAttribute("hasDemoChanged"));
		DemoInfoFields demoInfoChanges = ((DemoInfoFields) session.getAttribute("demoInfoChanges"));
		BeaMailAddr mailingAddressRequest;
		// Compare current and new value so to decide if need to send out email
		BeaMailAddr mailAddrRequest = this.indexService.getBeaMailAddr(demo);
		if (!addrNbrNew.equals(mailAddrRequest.getAddrNbrNew())) {
			isAnyChanges = true;
			demoInfoChanges.setMailingAddress(true);
		}
		if (!addrStrNew.equals(mailAddrRequest.getAddrStrNew())) {
			isAnyChanges = true;
			demoInfoChanges.setMailingPoBox(true);
		}
		if (!addrCityNew.equals(mailAddrRequest.getAddrCityNew())) {
			isAnyChanges = true;
			demoInfoChanges.setMailingCity(true);
		}
		if (!addrAptNew.equals(mailAddrRequest.getAddrAptNew())) {
			isAnyChanges = true;
			demoInfoChanges.setMailingApt(true);
		}
		if (!addrStNew.equals(mailAddrRequest.getAddrStNew())) {
			isAnyChanges = true;
			demoInfoChanges.setMailingState(true);
		}
		if (!addrZipNew.equals(mailAddrRequest.getAddrZipNew())) {
			isAnyChanges = true;
			demoInfoChanges.setMailingZip(true);
		}
		if (!addrZip4New.equals(mailAddrRequest.getAddrZip4New())) {
			isAnyChanges = true;
			demoInfoChanges.setMailingZip4(true);
		}

		session.setAttribute("hasDemoChanged", isAnyChanges);
		session.setAttribute("demoInfoChanges", demoInfoChanges);

		if (this.indexService.getBhrEapDemoAssgnGrp("BEA_MAIL_ADDR")) {
			mailingAddressRequest = new BeaMailAddr(demo, empNbr, reqDts, addrNbrNew, addrStrNew, addrAptNew,
					addrCityNew, addrStNew, addrZipNew, addrZip4New, 'A');
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
			List<Code> gens = referenceService.getGenerations();
			for (Code gen : gens) {
				if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
					demo.setGenDescription(gen.getDescription());
				}
			}

			session.setAttribute("userDetail", demo);
		} else {
			mailingAddressRequest = new BeaMailAddr(demo, empNbr, reqDts, addrNbrNew, addrStrNew, addrAptNew,
					addrCityNew, addrStNew, addrZipNew, addrZip4New, 'P');
			this.indexService.saveMailAddrRequest(mailingAddressRequest);
		}

//        this.getProfileDetails(session, mav,null);
		mav.addObject("activeTab", "mailingAddressRequest");
		return mav;
	}

	public ModelAndView deleteMailAddr(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		this.indexService.deleteMailAddrRequest(demo.getEmpNbr());
//        this.getProfileDetails(session, mav,null);
		return mav;
	}

	public ModelAndView saveAltMailAddr(HttpServletRequest req, ModelAndView mav) {
		HttpSession session = req.getSession();
		String empNbr = req.getParameter("empNbr");
		String reqDts = req.getParameter("altMailAddrReqDts");
		String smrAddrNbrNew = req.getParameter("smrAddrNbrNew");
		String smrAddrStrNew = req.getParameter("smrAddrStrNew");
		String smrAddrAptNew = req.getParameter("smrAddrAptNew");
		String smrAddrCityNew = req.getParameter("smrAddrCityNew");
		String smrAddrStNew = req.getParameter("smrAddrStNew");
		String smrAddrZipNew = req.getParameter("smrAddrZipNew");
		String smrAddrZip4New = req.getParameter("smrAddrZip4New");

		if (empNbr == null || reqDts == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save user alternative mailing address request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		Boolean isAnyChanges = ((Boolean) session.getAttribute("hasDemoChanged"));
		DemoInfoFields demoInfoChanges = ((DemoInfoFields) session.getAttribute("demoInfoChanges"));
		BeaAltMailAddr altMailingAddressRequest;
		// Compare current and new value so to decide if need to send out email
		BeaAltMailAddr altMailAddrRequest = this.indexService.getBeaAltMailAddr(demo);
		
		if (!smrAddrNbrNew.equals(altMailAddrRequest.getSmrAddrNbrNew())) {
			isAnyChanges = true;
			demoInfoChanges.setAlternateAddress(true);
		}
		if (!smrAddrStrNew.equals(altMailAddrRequest.getSmrAddrStrNew())) {
			isAnyChanges = true;
			demoInfoChanges.setAlternatePoBox(true);
		}
		if (!smrAddrAptNew.equals(altMailAddrRequest.getSmrAddrAptNew())) {
			isAnyChanges = true;
			demoInfoChanges.setAlternateApt(true);
		}
		if (!smrAddrCityNew.equals(altMailAddrRequest.getSmrAddrCityNew())) {
			isAnyChanges = true;
			demoInfoChanges.setAlternateCity(true);
		}
		if (!smrAddrStNew.equals(altMailAddrRequest.getSmrAddrStNew())) {
			isAnyChanges = true;
			demoInfoChanges.setAlternateState(true);
		}
		if (!smrAddrZipNew.equals(altMailAddrRequest.getSmrAddrZipNew())) {
			isAnyChanges = true;
			demoInfoChanges.setAlternateZip(true);
		}
		if (!smrAddrZip4New.equals(altMailAddrRequest.getSmrAddrZip4New())) {
			isAnyChanges = true;
			demoInfoChanges.setAlternateZip4(true);
		}

		session.setAttribute("hasDemoChanged", isAnyChanges);
		session.setAttribute("demoInfoChanges", demoInfoChanges);

		if (this.indexService.getBhrEapDemoAssgnGrp("BEA_ALT_MAIL_ADDR")) {
			altMailingAddressRequest = new BeaAltMailAddr(demo, empNbr, reqDts, smrAddrNbrNew, smrAddrStrNew,
					smrAddrAptNew, smrAddrCityNew, smrAddrStNew, smrAddrZipNew, smrAddrZip4New, 'A');
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
			List<Code> gens = referenceService.getGenerations();
			for (Code gen : gens) {
				if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
					demo.setGenDescription(gen.getDescription());
				}
			}

			session.setAttribute("userDetail", demo);
		} else {
			altMailingAddressRequest = new BeaAltMailAddr(demo, empNbr, reqDts, smrAddrNbrNew, smrAddrStrNew,
					smrAddrAptNew, smrAddrCityNew, smrAddrStNew, smrAddrZipNew, smrAddrZip4New, 'P');
			this.indexService.saveAltMailAddrRequest(altMailingAddressRequest);
		}

//        this.getProfileDetails(session, mav,null);
		mav.addObject("activeTab", "altMailingAddressRequest");
		return mav;
	}

	public ModelAndView deleteAltMailAddr(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		this.indexService.deleteAltMailAddrRequest(demo.getEmpNbr());
//        this.getProfileDetails(session, mav,null);
		return mav;
	}

	public ModelAndView savePhone(HttpServletRequest req, ModelAndView mav) {
		HttpSession session = req.getSession();
		String empNbr = req.getParameter("empNbr");
		String reqDts = req.getParameter("hmReqDts");
		String phoneAreaNew = req.getParameter("phoneAreaNew");
		String phoneNbrNew = req.getParameter("phoneNbrNew");
		String phoneAreaCellNew = req.getParameter("phoneAreaCellNew");
		String phoneNbrCellNew = req.getParameter("phoneNbrCellNew");
		String phoneAreaBusNew = req.getParameter("phoneAreaBusNew");
		String phoneNbrBusNew = req.getParameter("phoneNbrBusNew");
		String busPhoneExtNew = req.getParameter("busPhoneExtNew");

		if (empNbr == null || reqDts == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save user phone number request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		Boolean isAnyChanges= ((Boolean) session.getAttribute("hasDemoChanged"));
		DemoInfoFields demoInfoChanges =((DemoInfoFields)session.getAttribute("demoInfoChanges"));

		BeaHmPhone homePhoneRequest;
		BeaCellPhone cellPhoneRequest;
		BeaBusPhone businessPhoneRequest;
		

		DemoOption demoOptions = this.indexService.getDemoOption();

		if (demoOptions.getFieldDisplayOptionHomePhone().trim().equals("U")) {

			phoneNbrNew = phoneNbrNew.replaceAll("-", "");
			BeaHmPhone hmRequest = this.indexService.getBeaHmPhone(demo);
			if(!phoneAreaNew.equals(hmRequest.getPhoneAreaNew())) {
				isAnyChanges = true;
				demoInfoChanges.setPhoneHomeArea(true);
			}
			if(!phoneNbrNew.equals(hmRequest.getPhoneNbrNew())) {
				isAnyChanges = true;
				demoInfoChanges.setPhoneHomeNum(true);
			}
			if (this.indexService.getBhrEapDemoAssgnGrp("BEA_HM_PHONE")) {
				homePhoneRequest = new BeaHmPhone(demo, empNbr, reqDts, phoneAreaNew, phoneNbrNew, 'A');
				this.indexService.saveHomePhoneRequest(homePhoneRequest);
				demo.setPhoneArea(phoneAreaNew);
				demo.setPhoneNbr(phoneNbrNew);
				this.indexService.updateDemoHomePhone(demo);
				session.removeAttribute("userDetail");
				List<Code> gens = referenceService.getGenerations();
				for (Code gen : gens) {
					if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
						demo.setGenDescription(gen.getDescription());
					}
				}

				session.setAttribute("userDetail", demo);
			} else {
				homePhoneRequest = new BeaHmPhone(demo, empNbr, reqDts, phoneAreaNew, phoneNbrNew, 'P');
				this.indexService.saveHomePhoneRequest(homePhoneRequest);
			}
//        this.getProfileDetails(session, mav,null);
			mav.addObject("activeTab", "homePhoneRequest");

		}

		if (demoOptions.getFieldDisplayOptionCellPhone().trim().equals("U")) {

			phoneNbrCellNew = phoneNbrCellNew.replaceAll("-", "");
			BeaCellPhone cellRequest = this.indexService.getBeaCellPhone(demo);
			if(!phoneAreaCellNew.equals(cellRequest.getPhoneAreaCellNew())) {
				isAnyChanges = true;
				demoInfoChanges.setPhoneCellArea(true);
			}
			if(!phoneNbrCellNew.equals(cellRequest.getPhoneNbrCellNew())) {
				isAnyChanges = true;
				demoInfoChanges.setPhoneCellNum(true);
			}
			if (this.indexService.getBhrEapDemoAssgnGrp("BEA_CELL_PHONE")) {
				cellPhoneRequest = new BeaCellPhone(demo, empNbr, reqDts, phoneAreaCellNew, phoneNbrCellNew, 'A');
				this.indexService.saveCellPhoneRequest(cellPhoneRequest);
				demo.setPhoneAreaCell(phoneAreaCellNew);
				demo.setPhoneNbrCell(phoneNbrCellNew);
				this.indexService.updateDemoCellPhone(demo);
				session.removeAttribute("userDetail");
				List<Code> gens = referenceService.getGenerations();
				for (Code gen : gens) {
					if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
						demo.setGenDescription(gen.getDescription());
					}
				}

				session.setAttribute("userDetail", demo);
			} else {
				cellPhoneRequest = new BeaCellPhone(demo, empNbr, reqDts, phoneAreaCellNew, phoneNbrCellNew, 'P');
				this.indexService.saveCellPhoneRequest(cellPhoneRequest);
			}
//        this.getProfileDetails(session, mav,null);

			session.setAttribute("hasDemoChanged", isAnyChanges);
			session.setAttribute("demoInfoChanges", demoInfoChanges);
			mav.addObject("activeTab", "cellPhoneRequest");
		}

		if (demoOptions.getFieldDisplayOptionWorkPhone().trim().equals("U")) {

			phoneNbrBusNew = phoneNbrBusNew.replaceAll("-", "");
			BeaBusPhone busRequest = this.indexService.getBeaBusPhone(demo);
			if(!phoneAreaBusNew.equals(busRequest.getPhoneAreaBusNew())) {
				isAnyChanges = true;
				demoInfoChanges.setPhoneBusArea(true);
			}
			if(!phoneNbrBusNew.equals(busRequest.getPhoneNbrBusNew())) {
				isAnyChanges = true;
				demoInfoChanges.setPhoneBusNum(true);
			}
			if(!busPhoneExtNew.equals(busRequest.getBusPhoneExtNew())) {
				isAnyChanges = true;
				demoInfoChanges.setPhoneBusExt(true);
			}
			if (this.indexService.getBhrEapDemoAssgnGrp("BEA_BUS_PHONE")) {
				businessPhoneRequest = new BeaBusPhone(demo, empNbr, reqDts, phoneAreaBusNew, phoneNbrBusNew,
						busPhoneExtNew, 'A');
				this.indexService.saveBusinessPhoneRequest(businessPhoneRequest);
				demo.setPhoneAreaBus(phoneAreaBusNew);
				demo.setPhoneNbrBus(phoneNbrBusNew);
				demo.setBusPhoneExt(busPhoneExtNew);
				this.indexService.updateDemoBusinessPhone(demo);
				session.removeAttribute("userDetail");
				List<Code> gens = referenceService.getGenerations();
				for (Code gen : gens) {
					if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
						demo.setGenDescription(gen.getDescription());
					}
				}

				session.setAttribute("userDetail", demo);
			} else {
				businessPhoneRequest = new BeaBusPhone(demo, empNbr, reqDts, phoneAreaBusNew, phoneNbrBusNew,
						busPhoneExtNew, 'P');
				this.indexService.saveBusinessPhoneRequest(businessPhoneRequest);
			}
//        this.getProfileDetails(session, mav,null);
			mav.addObject("activeTab", "businessPhoneRequest");
		}

		return mav;
	}

	public ModelAndView deletePhone(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		DemoOption demoOptions = this.indexService.getDemoOption();

		if (demoOptions.getFieldDisplayOptionHomePhone().trim().equals("U")) {
			this.indexService.deleteHomePhoneRequest(demo.getEmpNbr());
		}

		if (demoOptions.getFieldDisplayOptionCellPhone().trim().equals("U")) {
			this.indexService.deleteCellPhoneRequest(demo.getEmpNbr());
		}

		if (demoOptions.getFieldDisplayOptionWorkPhone().trim().equals("U")) {
			this.indexService.deleteBusinessPhoneRequest(demo.getEmpNbr());
		}

//        this.getProfileDetails(session, mav,null);
		return mav;
	}

	@RequestMapping("saveW4")
	public ModelAndView saveW4(HttpServletRequest req, String empNbr, String reqDts, String payFreq,
			Character maritalStatTax, Character maritalStatTaxNew, Integer nbrTaxExempts, Integer nbrTaxExemptsNew,
			String w4FileStat, String w4MultiJob, Double w4NbrChldrn, Double w4NbrOthrDep, Double w4OthrIncAmt,
			Double w4OthrDedAmt, Double w4OthrExmptAmt, String w4FileStatNew, String w4MultiJobNew,
			Double w4NbrChldrnNew, Double w4NbrOthrDepNew, Double w4OthrIncAmtNew, Double w4OthrDedAmtNew,
			Double w4OthrExmptAmtNew) {

		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		if (empNbr == null || reqDts == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save user W4 martial status request");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		BhrEmpPay pay = new BhrEmpPay();
		pay.setMaritalStatTax(maritalStatTax);
		pay.setNbrTaxExempts(nbrTaxExempts);
		BeaW4 w4Request;
		Frequency freq;
		freq = Frequency.getFrequency(payFreq);
		boolean payrollSame = true;
		PayInfoChanges currentPayInfoChanges = new PayInfoChanges();
		// Compare current and new value so to decide if need to send out email
		BeaW4 w4Pending;
		if (payFreq != null && !("").equals(payFreq)) {
			w4Pending = this.indexService.getBeaW4(demo, payFreq);
		} else {
			List<Code> payRollFrequenciesOptions = this.referenceService.getPayrollFrequencies(demo.getEmpNbr());
			w4Pending = this.indexService.getBeaW4(demo, payRollFrequenciesOptions.get(0).getDescription());
		}
		
		if (!w4FileStatNew.equals(w4Pending.getW4FileStatNew())) {
			payrollSame = false;
			currentPayInfoChanges.setFilingStatusChanged(true);
		}
		if (!w4MultiJobNew.equals(w4Pending.getW4MultiJobNew())) {
			payrollSame = false;
			currentPayInfoChanges.setMultiJobChanged(true);
		}
		if (!w4NbrChldrnNew.equals(w4Pending.getW4NbrChldrnNew())) {
			payrollSame = false;
			currentPayInfoChanges.setNumberOfChildrenChanged(true);
		}
		
		if (!w4NbrOthrDepNew.equals(w4Pending.getW4NbrOthrDepNew())) {
			payrollSame = false;
			currentPayInfoChanges.setNumberOfOtherDependChanged(true);
		}
		if (!w4OthrIncAmtNew.equals(w4Pending.getW4OthrIncAmtNew())) {
			payrollSame = false;
			currentPayInfoChanges.setOtherIncomeAmtChanged(true);
		}
		if (!w4OthrDedAmtNew.equals(w4Pending.getW4OthrDedAmtNew())) {
			payrollSame = false;
			currentPayInfoChanges.setOtherDeductAmtChanged(true);
		}
		if (!w4OthrExmptAmtNew.equals(w4Pending.getW4OthrExmptAmtNew())) {
			payrollSame = false;
			currentPayInfoChanges.setOtherExemptAmtChanged(true);
		}
		
		if (this.indexService.getBhrEapPayAssgnGrp("BEA_W4")) {
			w4Request = new BeaW4(pay, empNbr, freq.getCode().charAt(0), reqDts, maritalStatTaxNew, nbrTaxExemptsNew,
					'A', w4FileStatNew, w4MultiJobNew, w4NbrChldrnNew, w4NbrOthrDepNew, w4OthrIncAmtNew,
					w4OthrDedAmtNew, w4OthrExmptAmtNew, w4FileStatNew, w4MultiJobNew, w4NbrChldrnNew, w4NbrOthrDepNew,
					w4OthrIncAmtNew, w4OthrDedAmtNew, w4OthrExmptAmtNew);
			this.indexService.saveW4Request(w4Request);
			this.indexService.updatePayInfo(demo, pay, freq.getCode().charAt(0), maritalStatTaxNew, nbrTaxExemptsNew);

		} else {
			w4Request = new BeaW4(pay, empNbr, freq.getCode().charAt(0), reqDts, maritalStatTaxNew, nbrTaxExemptsNew,
					'P', w4FileStat, w4MultiJob, w4NbrChldrn, w4NbrOthrDep, w4OthrIncAmt, w4OthrDedAmt, w4OthrExmptAmt,
					w4FileStatNew, w4MultiJobNew, w4NbrChldrnNew, w4NbrOthrDepNew, w4OthrIncAmtNew, w4OthrDedAmtNew,
					w4OthrExmptAmtNew);
			this.indexService.saveW4Request(w4Request);
		}
		
		//Send Out Email
		if (!payrollSame) {		
			W4Info w4Info = new W4Info();
			w4Info.setW4FileStat(w4FileStatNew);
			w4Info.setW4MultiJob(w4MultiJobNew);
			w4Info.setW4NbrChldrn(w4NbrChldrnNew);
			w4Info.setW4NbrOthrDep(w4NbrOthrDepNew);
			w4Info.setW4OthrDedAmt(w4OthrDedAmtNew);
			w4Info.setW4OthrExmptAmt(w4OthrExmptAmtNew);
			w4Info.setW4OthrIncAmt(w4OthrIncAmtNew);
			Boolean autoApproveBank = true; //Since we do not need to update Bank here we make it as true;
			//autoApproveBank = this.bankService.getAutoApproveAccountInfo(freq.getCode()); // will use when save or Update Bank
			PayrollFields docRequiredFields = this.referenceService.populatePayrollDocRequiredFields(payFreq);
			//When only update W4 then Bank account will always same
			List <BankChanges> currentAccountInfoChanges = new ArrayList<>();
			this.indexService.payrollDataChangeSendEmailConfirmation(demo, freq.getCode(),payrollSame,true,currentPayInfoChanges,w4Info,autoApproveBank,currentAccountInfoChanges,docRequiredFields);
		}

		this.getProfileDetails(session, mav, freq.getCode());
		mav.addObject("activeTab", "w4Request");
		return mav;

	}

	@RequestMapping("deleteW4")
	public ModelAndView deleteW4(HttpServletRequest req, String empNbr, String reqDts, String payFreq,
			Character maritalStatTax, Integer nbrTaxExempts) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("profile");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		this.indexService.deleteW4Request(demo.getEmpNbr(), payFreq, maritalStatTax, nbrTaxExempts);

		Frequency freq;
		freq = Frequency.getFrequency(payFreq);
		this.getProfileDetails(session, mav, freq.getCode());
		return mav;
	}

	@RequestMapping("changePassword")
	public ModelAndView getChangePassword(HttpServletRequest req) throws ParseException {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		BeaUsers users = this.indexService.getUserPwd(user.getUsrname());
		mav.setViewName("changePassword");
		mav.addObject("id", users.getEmpNbr());
		return mav;
	}

	private void getProfileDetails(HttpSession session, ModelAndView mav, String freq) {
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo demo = this.indexService.getUserDetail(user.getEmpNbr());
		String emerPhone = demo.getEmerPhoneNbr();
		if ((!StringUtil.isNullOrEmpty(emerPhone)) && emerPhone.trim().length() >= 7) {
			emerPhone = emerPhone.trim();
			demo.setEmerPhoneNbr(StringUtil.left(emerPhone, 3) + "-" + StringUtil.right(emerPhone, 4));
		}

		String cellPhone = demo.getPhoneNbrCell();
		if ((!StringUtil.isNullOrEmpty(cellPhone)) && cellPhone.trim().length() >= 7) {
			cellPhone = cellPhone.trim();
			demo.setPhoneNbrCell(StringUtil.left(cellPhone, 3) + "-" + StringUtil.right(cellPhone, 4));
		}

		String busPhone = demo.getPhoneNbrBus();
		if ((!StringUtil.isNullOrEmpty(busPhone)) && busPhone.trim().length() >= 7) {
			busPhone = busPhone.trim();
			demo.setPhoneNbrBus(StringUtil.left(busPhone, 3) + "-" + StringUtil.right(busPhone, 4));
		}
		String homePhone = demo.getPhoneNbr();
		if ((!StringUtil.isNullOrEmpty(homePhone)) && homePhone.trim().length() >= 7) {
			homePhone = homePhone.trim();
			demo.setPhoneNbr(StringUtil.left(homePhone, 3) + "-" + StringUtil.right(homePhone, 4));
		}

		Options options = this.indexService.getOptions();
		Boolean isSupervisor = this.indexService.isSupervisor(user.getEmpNbr(), options.getUsePMISSpvsrLevels());
		Boolean isTempApprover = this.indexService.isTempApprover(user.getEmpNbr());
		session.setAttribute("isSupervisor", isSupervisor);
		session.setAttribute("isTempApprover", isTempApprover);
		String district = (String) session.getAttribute("districtId");
		District districtInfo = this.indexService.getDistrict(district);
		demo.setEmpNbr(user.getEmpNbr());
		demo.setDob(DateUtil.formatDate(demo.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
		List<Code> gens = referenceService.getGenerations();
		for (Code gen : gens) {
			if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
				demo.setGenDescription(gen.getDescription());
			}
		}

		String phone = districtInfo == null ? "" : districtInfo.getPhone();
		districtInfo.setPhone(
				StringUtil.left(phone, 3) + "-" + StringUtil.mid(phone, 4, 3) + "-" + StringUtil.right(phone, 4));

		session.setAttribute("userDetail", demo);
		session.setAttribute("companyId", district);
		session.setAttribute("options", options);
		session.setAttribute("district", districtInfo);

		// BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
		BeaLglName nameRequest = this.indexService.getBeaLglName(demo);
		BeaEmerContact emerRequest = this.indexService.getBeaEmerContact(demo);
		emerPhone = emerRequest.getEmerPhoneNbr();
		if ((!StringUtil.isNullOrEmpty(emerPhone)) && emerPhone.trim().length() >= 7) {
			emerPhone = emerPhone.trim();
			emerRequest.setEmerPhoneNbr(StringUtil.left(emerPhone, 3) + "-" + StringUtil.right(emerPhone, 4));
		}

		String emerPhoneNew = emerRequest.getEmerPhoneNbrNew();
		if ((!StringUtil.isNullOrEmpty(emerPhoneNew)) && emerPhoneNew.trim().length() >= 7) {
			emerPhoneNew = emerPhoneNew.trim();
			emerRequest.setEmerPhoneNbrNew(StringUtil.left(emerPhoneNew, 3) + "-" + StringUtil.right(emerPhoneNew, 4));
		}

		BeaDrvsLic licRequest = this.indexService.getBeaDrvsLic(demo);
		BeaMrtlStat mrtlRequest = this.indexService.getBeaMrtlStat(demo);
		BeaRestrict restrictRequest = this.indexService.getBeaRestrict(demo);
		BeaEmail emailRequest = this.indexService.getBeaEmail(demo);
		BeaCellPhone cellRequest = this.indexService.getBeaCellPhone(demo);
		cellPhone = cellRequest.getPhoneNbrCell();
		if ((!StringUtil.isNullOrEmpty(cellPhone)) && cellPhone.trim().length() >= 7) {
			cellPhone = cellPhone.trim();
			cellRequest.setPhoneNbrCell(StringUtil.left(cellPhone, 3) + "-" + StringUtil.right(cellPhone, 4));
		}

		String cellPhoneNew = cellRequest.getPhoneNbrCellNew();
		if ((!StringUtil.isNullOrEmpty(cellPhoneNew)) && cellPhoneNew.trim().length() >= 7) {
			cellPhoneNew = cellPhoneNew.trim();
			cellRequest.setPhoneNbrCellNew(StringUtil.left(cellPhoneNew, 3) + "-" + StringUtil.right(cellPhoneNew, 4));
		}

		BeaBusPhone busRequest = this.indexService.getBeaBusPhone(demo);
		busPhone = busRequest.getPhoneNbrBus();
		if ((!StringUtil.isNullOrEmpty(busPhone)) && busPhone.trim().length() >= 7) {
			busPhone = busPhone.trim();
			busRequest.setPhoneNbrBus(StringUtil.left(busPhone, 3) + "-" + StringUtil.right(busPhone, 4));
		}

		String busPhoneNew = busRequest.getPhoneNbrBusNew();
		if ((!StringUtil.isNullOrEmpty(busPhoneNew)) && busPhoneNew.trim().length() >= 7) {
			busPhoneNew = busPhoneNew.trim();
			busRequest.setPhoneNbrBusNew(StringUtil.left(busPhoneNew, 3) + "-" + StringUtil.right(busPhoneNew, 4));
		}

		BeaHmPhone hmRequest = this.indexService.getBeaHmPhone(demo);
		homePhone = hmRequest.getPhoneNbr();
		if ((!StringUtil.isNullOrEmpty(homePhone)) && homePhone.trim().length() >= 7) {
			homePhone = homePhone.trim();
			hmRequest.setPhoneNbr(StringUtil.left(homePhone, 3) + "-" + StringUtil.right(homePhone, 4));
		}

		String homePhoneNew = hmRequest.getPhoneNbrNew();
		if ((!StringUtil.isNullOrEmpty(homePhoneNew)) && homePhoneNew.trim().length() >= 7) {
			homePhoneNew = homePhoneNew.trim();
			hmRequest.setPhoneNbrNew(StringUtil.left(homePhoneNew, 3) + "-" + StringUtil.right(homePhoneNew, 4));
		}

		BeaAltMailAddr altMailAddrRequest = this.indexService.getBeaAltMailAddr(demo);
		BeaMailAddr mailAddrRequest = this.indexService.getBeaMailAddr(demo);
		if (mailAddrRequest != null) {
			mailAddrRequest.setAddrApt(mailAddrRequest.getAddrApt() == null ? "" : mailAddrRequest.getAddrApt().trim());
			mailAddrRequest.setAddrAptNew(
					mailAddrRequest.getAddrAptNew() == null ? "" : mailAddrRequest.getAddrAptNew().trim());
			mailAddrRequest.setAddrNbr(mailAddrRequest.getAddrNbr() == null ? "" : mailAddrRequest.getAddrNbr().trim());
			mailAddrRequest.setAddrNbrNew(
					mailAddrRequest.getAddrNbrNew() == null ? "" : mailAddrRequest.getAddrNbrNew().trim());
			mailAddrRequest
					.setAddrZip4(mailAddrRequest.getAddrZip4() == null ? "" : mailAddrRequest.getAddrZip4().trim());
			mailAddrRequest.setAddrZip4New(
					mailAddrRequest.getAddrZip4New() == null ? "" : mailAddrRequest.getAddrZip4New().trim());
		}
		List<Code> payRollFrequenciesOptions = this.referenceService.getPayrollFrequencies(demo.getEmpNbr());

		PayInfo payInfo;
		BeaW4 w4Request;
		if (freq != null && !("").equals(freq)) {
			mav.addObject("selectedFreq", freq);
			payInfo = this.indexService.getPayInfo(demo, freq);
			w4Request = this.indexService.getBeaW4(demo, freq);
		} else {
			mav.addObject("selectedFreq", payRollFrequenciesOptions.get(0).getCode());
			payInfo = this.indexService.getPayInfo(demo, payRollFrequenciesOptions.get(0).getDescription());
			w4Request = this.indexService.getBeaW4(demo, payRollFrequenciesOptions.get(0).getDescription());
		}
		List<Code> maritalOptions = this.referenceService.getMaritalActualStatuses();
		List<Code> w4FileStatOptions = this.referenceService.getW4MaritalActualStatuses();
		List<Code> maritalTaxOptions = this.referenceService.getMaritalTaxStatuses();
		List<Code> generationOptions = this.referenceService.getGenerations();
		List<Code> titleOptions = this.referenceService.getTitles();
		List<Code> statesOptions = this.referenceService.getStates();
		List<Code> restrictionsOptions = this.referenceService.getRestrictions();

		String freqCode = freq;
		if (freq != null && !("").equals(freq)) {
			freqCode = payRollFrequenciesOptions.get(0).getCode(); // TODO
		} else {
			freqCode = payRollFrequenciesOptions.get(0).getCode();
		}
		List<Bank> banks = this.bankService.getAccounts(demo.getEmpNbr(), freqCode);
		List<BankRequest> banksRequest = this.bankService.getAccountRequests(demo.getEmpNbr(), freqCode);
		List<BankRequest> allBanks = new ArrayList<BankRequest>();
		for (Bank b : banks) {
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
			br.setIsFromAccount(true);
			allBanks.add(br);
		}
		for (BankRequest b : banksRequest) {
			boolean isNewBank = true;
			for (BankRequest ab : allBanks) {
				if(ab.getIsFromAccount()) {
					if (this.bankService.checkSameRequest(b, ab)) {
						ab.setAccountNumberNew(b.getAccountNumberNew());
						ab.setAccountTypeNew(b.getAccountTypeNew());
						ab.setDepositAmountNew(b.getDepositAmountNew());
						ab.setCodeNew(b.getCodeNew());
						ab.setIsDelete(b.getIsDelete());
						isNewBank = false;
					}
				}
				else
				{
					if (this.bankService.checkSameAddedRequest(b, ab)) {
						ab.setAccountNumberNew(b.getAccountNumberNew());
						ab.setAccountTypeNew(b.getAccountTypeNew());
						ab.setDepositAmountNew(b.getDepositAmountNew());
						ab.setCodeNew(b.getCodeNew());
						ab.setIsDelete(b.getIsDelete());
						isNewBank = false;
					}
				}
				
			}
			if (isNewBank) {
				if (b.getCodeNew() != null && !b.getAccountNumberNew().isEmpty()) {

					b.setAccountNumber("");
					b.setAccountType(new Code());
					b.setCode(new Code());
					b.setDepositAmount(new Money(0d, Currency.getInstance(Locale.US)));
					allBanks.add(b);
				}
			}

		}

		DemoOption demoOption = this.indexService.getDemoOption();
		PayrollOption payrollOption = this.indexService.getPayrollOption(user.getEmpNbr(), freqCode);

		List<Code> bankAccountTypes = this.referenceService.getDdAccountTypes();
		mav.setViewName("profile");
		System.out.println("New name request person's title: " + nameRequest.getNamePreNew());
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
		mav.addObject("w4FileStatOptions", w4FileStatOptions);
		mav.addObject("maritalTaxOptions", maritalTaxOptions);
		mav.addObject("generationOptions", generationOptions);
		mav.addObject("titleOptions", titleOptions);
		mav.addObject("statesOptions", statesOptions);
		mav.addObject("restrictionsOptions", restrictionsOptions);
		mav.addObject("payRollFrequenciesOptions", payRollFrequenciesOptions);
		mav.addObject("payInfo", payInfo);

		mav.addObject("banks", allBanks);
		mav.addObject("w4Request", w4Request);
		mav.addObject("bankAccountTypes", bankAccountTypes);
		mav.addObject("demoOptions", demoOption);
		mav.addObject("payrollOption", payrollOption);
	}

	@RequestMapping(value = "validatePassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> validatePassword(HttpServletRequest req, String password) throws Exception {
		HttpSession session = req.getSession();
		Map<String, Boolean> result = new HashMap<>();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		if (encoder.matches(password, user.getUsrpswd()))
			result.put("success", true);
		else
			result.put("success", false);
		return result;
	}

	/*
	 * private void passwordChangeSendEmailConfirmation (String userName, String
	 * userFirstName, String userLastName, String userHomeEmail, String
	 * userWorkEmail) { String subject ="A MESSAGE FROM SELF SERVICE"; StringBuilder
	 * messageContents = new StringBuilder(); userFirstName = userFirstName== null ?
	 * "" : userFirstName.trim(); userLastName = userLastName== null ? "" :
	 * userLastName.trim(); messageContents.append("<p>"+userFirstName + " "
	 * +userLastName + ", </p>"); messageContents.
	 * append("<p>Your request to change your password was successful. </p>");
	 * messageContents.
	 * append("<p>*****THIS IS AN AUTOMATED MESSAGE. PLEASE DO NOT REPLY*****</p>");
	 * 
	 * String toEmail =""; if (!"".equals(userWorkEmail)) { toEmail = userWorkEmail;
	 * } else if (!"".equals(userHomeEmail)) { toEmail = userHomeEmail; } if
	 * (toEmail!=null && toEmail.trim().length() > 0) { try{
	 * MailUtil.sendEmail(toEmail, subject, messageContents.toString()); }
	 * catch(Exception ex) { logger.
	 * info("Self Service Change Password: An exception has occured with mailing the user "
	 * +userName+"."); } } else { logger.
	 * info("Self Service Change Password: Unable to send an email confirmation.  No email address is avaiable for user "
	 * +userName+"."); }
	 * 
	 * }
	 */
}
