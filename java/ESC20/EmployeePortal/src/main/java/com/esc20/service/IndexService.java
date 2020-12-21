package com.esc20.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.AlertDao;
import com.esc20.dao.AppUserDao;
import com.esc20.dao.OptionsDao;
import com.esc20.dao.PayDao;
import com.esc20.dao.PreferencesDao;
import com.esc20.dao.ReferenceDao;
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
import com.esc20.nonDBModels.BankChanges;
import com.esc20.nonDBModels.DemoInfoFields;
import com.esc20.nonDBModels.DemoOption;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.PayInfoChanges;
import com.esc20.nonDBModels.PayrollFields;
import com.esc20.nonDBModels.PayrollOption;
import com.esc20.nonDBModels.SearchUser;
import com.esc20.nonDBModels.W2Option;
import com.esc20.nonDBModels.W4Info;
import com.esc20.util.MailUtil;
import com.esc20.util.NumberUtil;
import com.esc20.util.StringUtil;


@Service
public class IndexService {

    private Logger logger = LoggerFactory.getLogger(IndexService.class);

	@Autowired
	private MailUtil mailUtil;
	
    @Autowired
    private AppUserDao userDao;
    
    @Autowired
    private OptionsDao optionsDao;

    @Autowired
    private AlertDao alertDao;
    
    @Autowired
    private PayDao payDao;
    
    @Autowired
    private ReferenceDao referenceDao;
	
	 @Autowired
    private PreferencesDao preferencesDao;
    
    public String getMessage() throws ParseException{
        String message = "Hello, JBoss has started!";
        logger.info("<<===========================  Logback Testing  ===========================>>");
        logger.info(message);
        BeaUsers user = userDao.getUserByName("admin");
        return message+user.getUsrpswd();
    }
    public BeaUsers getUserPwd(String Name) throws ParseException{
        return userDao.getUserByName(Name);
    }

	public BeaUsers getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}
	public BeaUsers getUserByEmpNbr(String id) {
		
		return userDao.getUserByEmpNbr(id);
	}
	
	public BhrEmpDemo getUserNameFromSsn(String ssn){
		
		return userDao.getUserNameFromSsn(ssn);
	}
	
	public BeaUsers getUserByUsername(String username) {
		
		return userDao.getUserByUsername(username);
	}
	
	public BeaUsers saveBeaUsers(BeaUsers user) {
		
		return userDao.saveBeaUsers(user);
	}
	
	public void updateUser(BeaUsers user) {
		userDao.updateUser(user);
	}
	public BhrEmpDemo getUserDetail(String empNbr) {
		return userDao.getUserDetail(empNbr);
	}
	public Options getOptions() {
		return optionsDao.getOptions();
	}
	
	public DemoOption getDemoOption() {
		return optionsDao.getDemoOption();
	}
	
	public W2Option getW2Option() {
		return optionsDao.getW2Option();
	}
	
	public PayrollOption getPayrollOption(String employeeNumber, String frequency) {
		PayrollOption option = new PayrollOption();
		option.setFieldDisplayOptionInfo(optionsDao.getPayInfoFieldDisplay(frequency));
		option.setFieldDisplayOptionBank(optionsDao.getAccountInfoFieldDisplay(frequency));
		
		String payInactive = optionsDao.getActiveEmployee(employeeNumber,frequency);
		
		if((!StringUtil.isNullOrEmpty(payInactive)) && payInactive.trim().equals("2"))
		{
			option.setFieldDisplayOptionInfo("I");
			option.setFieldDisplayOptionBank("I");
		}
		
		return option;
	}
	
	public District getDistrict(String district) {
		return userDao.getDistrict(district);
	}
	public BeaLglName getBeaLglName(BhrEmpDemo demo) {
		BeaLglName result = userDao.getBeaLglName(demo.getEmpNbr());
		if(result == null) {
			result = new BeaLglName(demo);
		}
		return result;
	}
	public void saveNameRequest(BeaLglName nameRequest) {
		userDao.saveNameRequest(nameRequest);
	}
	public void saveMaritalRequest(BeaMrtlStat maritalStatusRequest) {
		userDao.saveMaritalRequest(maritalStatusRequest);
	}
	public void saveDriversLicenseRequest(BeaDrvsLic driversLicenseRequest) {
		userDao.saveDriversLicenseRequest(driversLicenseRequest);
	}
	public void saveRestrictionCodesRequest(BeaRestrict restrictionCodesRequest) {
		userDao.saveRestrictionCodesRequest(restrictionCodesRequest);
	}
	public void saveEmailRequest(BeaEmail emailRequest) {
		userDao.saveEmailRequest(emailRequest);
	}
	public void saveEmergencyContactRequest(BeaEmerContact emergencyContactRequest) {
		userDao.saveEmergencyContactRequest(emergencyContactRequest);
	}
	public void saveMailAddrRequest(BeaMailAddr mailingAddressRequest) {
		userDao.saveMailAddrRequest(mailingAddressRequest);
	}
	public void saveAltMailAddrRequest(BeaAltMailAddr altMailingAddressRequest) {
		userDao.saveAltMailAddrRequest(altMailingAddressRequest);
	}
	public void saveHomePhoneRequest(BeaHmPhone homePhoneRequest) {
		userDao.saveHomePhoneRequest(homePhoneRequest);
	}
	public void saveCellPhoneRequest(BeaCellPhone cellPhoneRequest) {
		userDao.saveCellPhoneRequest(cellPhoneRequest);
	}
	public void saveBusinessPhoneRequest(BeaBusPhone businessPhoneRequest) {
		userDao.saveBusinessPhoneRequest(businessPhoneRequest);
	}
	public void saveW4Request(BeaW4 w4Request) {
		checkNullParamaterString(w4Request);
		checkNullParamaterNumber(w4Request);
		payDao.savew4Request(w4Request);
	}
	
	private void checkNullParamaterString(BeaW4 w4Request) {
		if(w4Request.getW4FileStat() == null) {
			w4Request.setW4FileStat("N");
		}
		if(w4Request.getW4FileStatNew() == null) {
			w4Request.setW4FileStatNew("N");
		}
		if(w4Request.getW4MultiJob() == null) {
			w4Request.setW4MultiJob("N");
		}
		if(w4Request.getW4MultiJobNew() == null) {
			w4Request.setW4MultiJobNew("N");
		}
	}
	private void checkNullParamaterNumber(BeaW4 w4Request) {
		if(w4Request.getW4NbrChldrn() == null) {
			w4Request.setW4NbrChldrn(0);
		}
		if(w4Request.getW4NbrChldrnNew() == null) {
			w4Request.setW4NbrChldrnNew(0);
		}
		if(w4Request.getW4NbrOthrDep() == null) {
			w4Request.setW4NbrOthrDep(0);
		}
		if(w4Request.getW4NbrOthrDepNew() == null) {
			w4Request.setW4NbrOthrDepNew(0);
		}
		
		if(w4Request.getW4OthrDedAmt() == null) {
			w4Request.setW4OthrDedAmt(0.00);
		}
		if(w4Request.getW4OthrDedAmtNew() == null) {
			w4Request.setW4OthrDedAmtNew(0.00);
		}
		
		if(w4Request.getW4OthrExmptAmt() == null) {
			w4Request.setW4OthrExmptAmt(0.00);
		}
		if(w4Request.getW4OthrExmptAmtNew() == null) {
			w4Request.setW4OthrExmptAmtNew(0.00);
		}
		
		if(w4Request.getW4OthrIncAmt() == null) {
			w4Request.setW4OthrIncAmt(0.00);
		}
		if(w4Request.getW4OthrIncAmtNew() == null) {
			w4Request.setW4OthrIncAmtNew(0.00);
		}
	}

	public BeaEmerContact getBeaEmerContact(BhrEmpDemo demo) {
		BeaEmerContact result = userDao.getBeaEmerContact(demo.getEmpNbr());
		if(result == null) {
			result = new BeaEmerContact(demo);
		}
		return result;
	}
	public BeaDrvsLic getBeaDrvsLic(BhrEmpDemo demo) {
		BeaDrvsLic result = userDao.getBeaDrvsLic(demo.getEmpNbr());
		if(result == null) {
			result = new BeaDrvsLic(demo);
		}
		return result;
	}
	public BeaMrtlStat getBeaMrtlStat(BhrEmpDemo demo) {
		BeaMrtlStat result = userDao.getBeaMrtlStat(demo.getEmpNbr());
		if(result == null) {
			result = new BeaMrtlStat(demo);
		}
		return result;
	}
	
	public BeaRestrict getBeaRestrict(BhrEmpDemo demo) {
		BeaRestrict result = userDao.getBeaRestrict(demo.getEmpNbr());
		if(result == null) {
			result = new BeaRestrict(demo);
		}
		return result;
	}
	public BeaEmail getBeaEmail(BhrEmpDemo demo) {
		BeaEmail result = userDao.getBeaEmail(demo.getEmpNbr());
		if(result == null) {
			result = new BeaEmail(demo);
		}
		return result;
	}
	public BeaCellPhone getBeaCellPhone(BhrEmpDemo demo) {
		BeaCellPhone result = userDao.getBeaCellPhone(demo.getEmpNbr());
		if(result == null) {
			result = new BeaCellPhone(demo);
		}
		return result;
	}
	public BeaBusPhone getBeaBusPhone(BhrEmpDemo demo) {
		BeaBusPhone result = userDao.getBeaBusPhone(demo.getEmpNbr());
		if(result == null) {
			result = new BeaBusPhone(demo);
		}
		return result;
	}
	public BeaHmPhone getBeaHmPhone(BhrEmpDemo demo) {
		BeaHmPhone result = userDao.getBeaHmPhone(demo.getEmpNbr());
		if(result == null) {
			result = new BeaHmPhone(demo);
		}
		return result;
	}
	public BeaAltMailAddr getBeaAltMailAddr(BhrEmpDemo demo) {
		BeaAltMailAddr result = userDao.getBeaAltMailAddr(demo.getEmpNbr());
		if(result == null) {
			result = new BeaAltMailAddr(demo);
		}
		return result;
	}
	public BeaMailAddr getBeaMailAddr(BhrEmpDemo demo) {
		BeaMailAddr result = userDao.getBeaMailAddr(demo.getEmpNbr());
		if(result == null) {
			result = new BeaMailAddr(demo);
		}
		return result;
	}
	
	public PayInfo getPayInfo(BhrEmpDemo demo, String frequency)
	{
		PayInfo result = payDao.getPayInfo(demo.getEmpNbr(), frequency);
		if(result == null) {
			result = new PayInfo(demo.getEmpNbr(),"", "", "", 0, 0, 0.00, 0.00, 0.00);
		}
		return result; 
	}
	
	public BeaW4 getBeaW4(BhrEmpDemo demo, String frequency)
	{
		BeaW4 result = payDao.getW4(demo.getEmpNbr(), frequency);
		if(result == null) {
			System.out.println("freq  ="+ frequency);
			PayInfo info = payDao.getPayInfo(demo.getEmpNbr(), frequency);
			result = new BeaW4(info,demo,frequency);
		}
		return result; 
	}

	public Map<Frequency, BeaW4> getBeaW4Info(String employeeNumber, List<Frequency> frequencies) {
		Map<Frequency, BeaW4> w4 = new HashMap<Frequency, BeaW4>();

		for (Frequency frequency : frequencies) {
			try {
				BeaW4 result = payDao.getW4(employeeNumber, frequency.getCode());
				if(result == null) {
        		BhrEmpDemo demo = userDao.getUserDetail(employeeNumber);
					PayInfo info = payDao.getPayInfo(employeeNumber, frequency.getLabel());
					result = new BeaW4(info,demo,frequency.getLabel());
				}
				w4.put(frequency, result);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return w4;
	}
	
	public boolean getBhrEapDemoAssgnGrp(String tableName) {
		return userDao.getBhrEapDemoAssgnGrp(tableName);
	}
	public boolean getBhrEapPayAssgnGrp(String tableName) {
		return payDao.getBhrEapPayAssgnGrp(tableName);
	}
	public void updateDemoName(BhrEmpDemo demo) {
		userDao.updateDemoName(demo);
	}

	public void updateDemoMaritalStatus(BhrEmpDemo demo) {
		userDao.updateDemoMaritalStatus(demo);
	}
	public void updateDemoDriversLicense(BhrEmpDemo demo) {
		userDao.updateDemoDriversLicense(demo);
	}
	public void updateDemoRestrictionCodes(BhrEmpDemo demo) {
		userDao.updateDemoRestrictionCodes(demo);
	}
	public void updateDemoEmail(BhrEmpDemo demo) {
		userDao.updateDemoEmail(demo);
	}
	public void updateDemoEmergencyContact(BhrEmpDemo demo) {
		userDao.updateDemoEmergencyContact(demo);
	}
	public void updateDemoMailAddr(BhrEmpDemo demo) {
		userDao.updateDemoMailAddr(demo);
	}
	public void updateDemoAltMailAddr(BhrEmpDemo demo) {
		userDao.updateDemoAltMailAddr(demo);
	}
	public void updateDemoHomePhone(BhrEmpDemo demo) {
		userDao.updateDemoHomePhone(demo);
	}
	public void updateDemoCellPhone(BhrEmpDemo demo) {
		userDao.updateDemoCellPhone(demo);
	}
	public void updateDemoBusinessPhone(BhrEmpDemo demo) {
		userDao.updateDemoBusinessPhone(demo);
	}
	public void updatePayInfo(BhrEmpDemo demo, BhrEmpPay pay, Character payFreq, Character maritalStatTaxNew, 
			Integer nbrTaxExemptsNew, String w4FileStatNew, String w4MultiJobNew, Integer w4NbrChldrnNew, Integer w4NbrOthrDepNew, 
			Double w4OthrIncAmtNew, Double w4OthrDedAmtNew, Double w4OthrExmptAmtNew) {
		payDao.updatePayInfo(demo,pay,payFreq,maritalStatTaxNew,nbrTaxExemptsNew, 
				w4FileStatNew, w4MultiJobNew, w4NbrChldrnNew, w4NbrOthrDepNew, w4OthrIncAmtNew,
				w4OthrDedAmtNew, w4OthrExmptAmtNew);
	}
	
	public void deleteNameRequest(String empNbr) {
		userDao.deleteNamerequest(empNbr);
	}
	public void deleteMaritalRequest(String empNbr) {
		userDao.deleteMaritalrequest(empNbr);
	}
	public void deleteDirversLicenseRequest(String empNbr) {
		userDao.deleteDriversLicenserequest(empNbr);
	}
	public void deleteRestrictionCodesRequest(String empNbr) {
		userDao.deleteRestrictionCodesrequest(empNbr);
	}
	public void deleteEmailRequest(String empNbr) {
		userDao.deleteEmailrequest(empNbr);
	}
	public void deleteEmergencyContactRequest(String empNbr) {
		userDao.deleteEmergencyContactrequest(empNbr);
	}
	public void deleteMailAddrRequest(String empNbr) {
		userDao.deleteMailAddrrequest(empNbr);
	}
	public void deleteAltMailAddrRequest(String empNbr) {
		userDao.deleteAltMailAddrrequest(empNbr);
	}
	public void deleteHomePhoneRequest(String empNbr) {
		userDao.deleteHomePhonerequest(empNbr);
	}
	public void deleteCellPhoneRequest(String empNbr) {
		userDao.deleteCellPhonerequest(empNbr);
	}
	public void deleteBusinessPhoneRequest(String empNbr) {
		userDao.deleteBusinessPhonerequest(empNbr);
	}
	public void deleteW4Request(String empNbr,String payFreq,Character maritalStatTax, Integer nbrTaxExempts) {
		payDao.deleteW4request(empNbr,payFreq,maritalStatTax,nbrTaxExempts);
	}
	public List<BeaAlert> getUnReadAlert(String empNbr) {
		return alertDao.getUnreadAlerts(empNbr);
	}
	public Integer getBudgeCount(String empNbr) {
		return alertDao.getAlertCount(empNbr);
	}
	public List<BeaAlert> getTop5Alerts(String empNbr) {
		return alertDao.getTop5UnreadAlerts(empNbr);
	}
	public void deleteAlert(String id) {
		alertDao.deleteAlert(id);
	}
	
	public BhrEmpDemo retrieveEmployee(SearchUser searchUser) {
		return userDao.retrieveEmployee(searchUser);
		
	}
	
	public void updateEmailEmployee(String employeeNumber, String workEmail, String hmEmail) {
		 userDao.updateEmailEmployee(employeeNumber,workEmail,hmEmail);
		
	}
	
/*	public Boolean isSupervisor(String empNbr) {
		return userDao.isSupervisor(empNbr);
	}
	*/
	public boolean isSupervisor(String employeeNumber, boolean usePMISSupervisorLevels) {
		if (usePMISSupervisorLevels) {
			return userDao.isLeavePMISSupervisor(employeeNumber);
		} else {
			return userDao.isSupervisor(employeeNumber);
		}
	}
	
	public Boolean isTempApprover(String empNbr) {
		return userDao.isTempApprover(empNbr);
	}
	public boolean isEmployeePayCampusLeaveCampus(String empNbr) {
		return userDao.employeePayCampusLeaveCampusCount(empNbr)>0;
	}
	
	public void passwordChangeSendEmailConfirmation (String userName, String userFirstName, String userLastName, String userHomeEmail, String userWorkEmail) {
		String subject ="A MESSAGE FROM SELF SERVICE";
		StringBuilder messageContents = new StringBuilder();
		userFirstName = userFirstName== null ? "" : userFirstName.trim();
		userLastName = userLastName== null ? "" : userLastName.trim();
		messageContents.append("<p>"+userFirstName + " " +userLastName + ", </p>");
		messageContents.append("<p>Your request to change your password was successful. </p>");		
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
				logger.info("Self Service Change Password: An exception has occured with mailing the user "+userName+".");
			} 
		} else {
			logger.info("Self Service Change Password: Unable to send an email confirmation.  No email address is avaiable for user "+userName+".");
		}
		
	}
	
	public void personDataChangeSendEmailConfirmation (BhrEmpDemo oldValue,BhrEmpDemo newValue,DemoInfoFields demoInfoChanges,DemoInfoFields docRequiredFields) {
		String subject ="A MESSAGE FROM SELF SERVICE";
		StringBuilder messageContents = new StringBuilder();
		StringBuilder employeeMessageAutoApprove = new StringBuilder();
		StringBuilder employeeMessageDocRequired = new StringBuilder();
		StringBuilder employeeMessageRequestReview = new StringBuilder();
		String userFirstName = oldValue.getNameF();
		String userLastName = oldValue.getNameL();
		String userHomeEmail = oldValue.getHmEmail();
		String userWorkEmail = oldValue.getEmail();
		
		boolean hasDocChanges = false;
		boolean hasApprovChanges = false;
		boolean hasRequestReview = false;
		
		Boolean autoApprove;
		String fieldName;
		ReferenceService rs = new ReferenceService();
		Map<String, String> groupApproverNumbers = referenceDao.getApproverEmployeeNumbers();
		List<String> approversToEmail = new ArrayList<String>();
		autoApprove = this.getBhrEapDemoAssgnGrp(rs.LEGAL_NAME_TABLE);
		
		if (demoInfoChanges.getNameTitle()==null?false:demoInfoChanges.getNameTitle()) {
			fieldName = "Title<br/>";
			if (docRequiredFields.getNameTitle()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			}
			else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.LEGAL_NAME_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getNameLast()==null?false:demoInfoChanges.getNameLast()) {
			fieldName = "Last Name<br/>";
			if (docRequiredFields.getNameLast()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.LEGAL_NAME_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getNameFirst()==null?false:demoInfoChanges.getNameFirst()) {
			fieldName = "First Name<br/>";
			if (docRequiredFields.getNameFirst()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				
				
				String approverNumber = groupApproverNumbers.get(rs.LEGAL_NAME_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getNameMiddle()==null?false:demoInfoChanges.getNameMiddle()) {
			fieldName = "Middle Name<br/>";
			if (docRequiredFields.getNameMiddle()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.LEGAL_NAME_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getNameGeneration()==null?false:demoInfoChanges.getNameGeneration()) {
			fieldName = "Generation<br/>";
			if (docRequiredFields.getNameGeneration()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.LEGAL_NAME_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		
		
		autoApprove = this.getBhrEapDemoAssgnGrp(rs.MAIL_ADDR_TABLE);
		if(demoInfoChanges.getMailingAddress()==null?false:demoInfoChanges.getMailingAddress()) {
			fieldName = "Main Address Number<br/>";
			if (docRequiredFields.getMailingAddress()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.MAIL_ADDR_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if(demoInfoChanges.getMailingPoBox()==null?false:demoInfoChanges.getMailingPoBox()) {
			fieldName = "Main Address Street/P.O. Box <br/>";
			if (docRequiredFields.getMailingPoBox()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.MAIL_ADDR_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getMailingApt()==null?false:demoInfoChanges.getMailingApt()) {
			fieldName = "Main Address Apt<br/>";
			if (docRequiredFields.getMailingApt()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.MAIL_ADDR_TABLE);
				if( !approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getMailingCity()==null?false:demoInfoChanges.getMailingCity()) {
			fieldName = "Main Address City<br/>";
			if (docRequiredFields.getMailingCity()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.MAIL_ADDR_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getMailingState()==null?false:demoInfoChanges.getMailingState()) {
			fieldName = "Main Address State<br/>";
			if (docRequiredFields.getMailingState()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.MAIL_ADDR_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getMailingZip()==null?false:demoInfoChanges.getMailingZip()) {
			fieldName = "Main Address Zip<br/>";
			if(docRequiredFields.getMailingZip()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.MAIL_ADDR_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getMailingZip4()==null?false:demoInfoChanges.getMailingZip4()) {
			fieldName = "Main Address Zip+4<br/>";
			if (docRequiredFields.getMailingZip4()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.MAIL_ADDR_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}

		
		autoApprove = this.getBhrEapDemoAssgnGrp(rs.ALT_MAIL_TABLE);
		if (demoInfoChanges.getAlternateAddress()==null?false:demoInfoChanges.getAlternateAddress()) {
			fieldName = "Alt Address Number<br/>";
			if (docRequiredFields.getAlternateAddress()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getAlternatePoBox()==null?false:demoInfoChanges.getAlternatePoBox()) {
			fieldName = "Alt Address Street/P.O. Box<br/>";
			if (docRequiredFields.getAlternatePoBox()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getAlternateApt()==null?false:demoInfoChanges.getAlternateApt()) {
			fieldName = "Alt Address Apt<br/>";
			if (docRequiredFields.getAlternateApt()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			} 
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getAlternateCity()==null?false:demoInfoChanges.getAlternateCity()) {
			fieldName = "Alt Address City<br/>";
			if (docRequiredFields.getAlternateCity()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getAlternateState()==null?false:demoInfoChanges.getAlternateState()) {
			fieldName = "Alt Address State<br/>";
			if (docRequiredFields.getAlternateState()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getAlternateZip()==null?false:demoInfoChanges.getAlternateZip()) {
			fieldName = "Alt Address Zip<br/>";
			if (docRequiredFields.getAlternateZip()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getAlternateZip4()==null?false:demoInfoChanges.getAlternateZip4()) {
			fieldName = "Alt Address Zip+4<br/>";
			if (docRequiredFields.getAlternateZip4()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}

	
		autoApprove = this.getBhrEapDemoAssgnGrp(rs.HOME_PHONE_TABLE);
		if (demoInfoChanges.getPhoneHomeArea()==null?false:demoInfoChanges.getPhoneHomeArea()) {
			fieldName = "Home Phone Area Code<br/>";
			if (docRequiredFields.getPhoneHomeArea()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.HOME_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getPhoneHomeNum()==null?false:demoInfoChanges.getPhoneHomeNum()) {
			fieldName = "Home Phone Number<br/>";
			if (docRequiredFields.getPhoneHomeNum()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.HOME_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		
		autoApprove = this.getBhrEapDemoAssgnGrp(rs.WORK_PHONE_TABLE);
		if (demoInfoChanges.getPhoneBusArea()==null?false:demoInfoChanges.getPhoneBusArea()) {
			fieldName = "Business Phone Area Code<br/>";
			if (docRequiredFields.getPhoneBusArea()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.WORK_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getPhoneBusNum()==null?false:demoInfoChanges.getPhoneBusNum()) {
			fieldName = "Business Phone Number<br/>";
			if (docRequiredFields.getPhoneBusNum()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.WORK_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getPhoneBusExt()==null?false:demoInfoChanges.getPhoneBusExt()) {
			fieldName = "Business Extension<br/>";
			if (docRequiredFields.getPhoneBusExt()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.WORK_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		
		autoApprove = this.getBhrEapDemoAssgnGrp(rs.CELL_PHONE_TABLE);
		if (demoInfoChanges.getPhoneCellArea()==null?false:demoInfoChanges.getPhoneCellArea()) {
			fieldName = "Cell Phone Area Code<br/>";
			if (docRequiredFields.getPhoneCellArea()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.CELL_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getPhoneCellNum()==null?false:demoInfoChanges.getPhoneCellNum()) {
			fieldName = "Cell Phone Number<br/>";
			if (docRequiredFields.getPhoneCellNum()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.CELL_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		
		autoApprove = this.getBhrEapDemoAssgnGrp(rs.EMAIL_TABLE);
		String newHomeEmail = "";
		String newWorkEmail = "";
		if (demoInfoChanges.getEmailHome()==null?false:demoInfoChanges.getEmailHome()) {
			newHomeEmail = newValue.getHmEmail();
			fieldName = "Home E-mail<br/>";
			if (docRequiredFields.getEmailHome()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.EMAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getEmailWork()==null?false:demoInfoChanges.getEmailWork()) {
			newWorkEmail = newValue.getEmail();
			fieldName = "Work E-mail<br/>";
			if (docRequiredFields.getEmailWork()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.EMAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		
		
		autoApprove = this.getBhrEapDemoAssgnGrp(rs.RESTRICTION_CODE_TABLE);
		if (demoInfoChanges.getRestrictionLocal()==null?false:demoInfoChanges.getRestrictionLocal()) {
			fieldName = "Local Restriction Code<br/>";
			if (docRequiredFields.getRestrictionLocal()){
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.RESTRICTION_CODE_TABLE);
				if (!approversToEmail.contains(approverNumber)){
					approversToEmail.add(approverNumber);
				}
			}
		}
		if (demoInfoChanges.getRestrictionPublic()==null?false:demoInfoChanges.getRestrictionPublic()) {
			fieldName = "Public Restriction Code<br/>";
			if (docRequiredFields.getRestrictionPublic()){
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.RESTRICTION_CODE_TABLE);
				if (!approversToEmail.contains(approverNumber)){
					approversToEmail.add(approverNumber);
				}
			}
		}
		
		autoApprove = this.getBhrEapDemoAssgnGrp(rs.MARITAL_STATUS_TABLE);
		if (demoInfoChanges.getMaritalLocal()==null?false:demoInfoChanges.getMaritalLocal()) {
			fieldName = "Marital Status<br/>";
			if (docRequiredFields.getMaritalLocal()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.MARITAL_STATUS_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		
		autoApprove = this.getBhrEapDemoAssgnGrp(rs.DRIVERS_LICENSE_TABLE);
		if (demoInfoChanges.getDriversNum()==null?false:demoInfoChanges.getDriversNum()) {
			fieldName = "Driver's License Number<br/>";
			if (docRequiredFields.getDriversNum()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.DRIVERS_LICENSE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getDriversState()==null?false:demoInfoChanges.getDriversState()) {
			fieldName = "Driver's License State<br/>";
			if (docRequiredFields.getDriversState()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.DRIVERS_LICENSE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		autoApprove = this.getBhrEapDemoAssgnGrp(rs.EMERGENCY_CONTACT_TABLE);
		if (demoInfoChanges.getEmergencyName()==null?false:demoInfoChanges.getEmergencyName()) {
			fieldName = "Emergency Contact Name<br/>";
			if (docRequiredFields.getEmergencyName()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.EMERGENCY_CONTACT_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getEmergencyAreaCode()==null?false:demoInfoChanges.getEmergencyAreaCode()) {
			fieldName = "Emergency Contact Area Code<br/>";
			if (docRequiredFields.getEmergencyAreaCode()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if(autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.EMERGENCY_CONTACT_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getEmergencyPhoneNum()==null?false:demoInfoChanges.getEmergencyPhoneNum()) {
			fieldName = "Emergency Contact Phone Number<br/>";
			if (docRequiredFields.getEmergencyPhoneNum()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.EMERGENCY_CONTACT_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getEmergencyPhoneExt()==null?false:demoInfoChanges.getEmergencyPhoneExt()) {
			fieldName = "Emergency Contact Phone Extension<br/>";
			if (docRequiredFields.getEmergencyPhoneExt()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.EMERGENCY_CONTACT_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getEmergencyRelationship()==null?false:demoInfoChanges.getEmergencyRelationship()) {
			fieldName = "Emergency Contact Relationship<br/><br/>";
			if (docRequiredFields.getEmergencyRelationship()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.EMERGENCY_CONTACT_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getEmergencyNotes()==null?false:demoInfoChanges.getEmergencyNotes()) {
			fieldName = "Emergency Contact Notes<br/><br/>";
			if (docRequiredFields.getEmergencyNotes()) {
				employeeMessageDocRequired.append(fieldName);
				hasDocChanges = true;
			}
			if (autoApprove) {
				employeeMessageAutoApprove.append(fieldName);
				hasApprovChanges = true;
			} else {
				employeeMessageRequestReview.append(fieldName);
				hasRequestReview = true;
				String approverNumber = groupApproverNumbers.get(rs.EMERGENCY_CONTACT_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		
		
		
		//*********************SEND USER EMAIL***********************************
		
		userFirstName = userFirstName== null ? "" : userFirstName.trim();
		userLastName = userLastName== null ? "" : userLastName.trim();
		/*employeeMessageContents.append((userFirstName == null || userFirstName.trim().equals("")) ? "" : userFirstName.trim());
		employeeMessageContents.append((userMiddleName == null || userMiddleName.trim().equals("")) ? "" : " " + userMiddleName.trim());
		employeeMessageContents.append((userLastName == null || userLastName.trim().equals("")) ? "" : " " + userLastName.trim());
		employeeMessageContents.append((userGeneration == null || userGeneration.trim().equals("")) ? "" : " " + userGeneration.trim());*/

		messageContents.append("<p>"+userFirstName + " " +userLastName + ", </p>");
		messageContents.append("<p>Your request for changes to personnel data has been submitted. </p>");	
		if (hasDocChanges || hasApprovChanges || hasRequestReview) {
			if (hasApprovChanges) {
				messageContents.append("The following data was automatically approved and updated:<br/>");
				messageContents.append(employeeMessageAutoApprove.toString()+"<br/>");
			}
			if (hasDocChanges) {
				messageContents.append("The following request(s) requires documentation be provided:<br/>");
				messageContents.append(employeeMessageDocRequired.toString()+"<br/>");
			}
			if (hasRequestReview) {
				messageContents.append("The following request(s) is pending to be reviewed:<br/>");
				messageContents.append(employeeMessageRequestReview.toString()+"<br/>"); 
			}
		}

		
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
				logger.info("Self Service Change Demo Info: An exception has occured with mailing ");
			} 
		} else {
			logger.info("Self Service Change Demo Info: Unable to send an email confirmation.  No email address is avaiable");
		}
		
		String toNewEmail ="";
		
		if (!"".equals(newWorkEmail)) {
			toNewEmail = newWorkEmail;
		} else if (!"".equals(newHomeEmail)) {
			toNewEmail = newHomeEmail;
		}
		if (toNewEmail!=null && toNewEmail.trim().length() > 0) {
			try{
				mailUtil.sendEmail(toNewEmail, subject, messageContents.toString());
			} 
			catch(Exception ex) {
				logger.info("Self Service Change Demo Info: An exception has occured with mailing ");
			} 
		} else {
			logger.info("Self Service Change Demo Info: Unable to send an email confirmation.  No email address is avaiable");
		}
		
		//*********************SEND APPROVER EMAIL***********************************
		StringBuilder approverEmailMessage = new StringBuilder();

		approverEmailMessage.append((userFirstName == null || userFirstName.trim().equals("")) ? "" : userFirstName.trim());
		//approverEmailMessage.append((userMiddleName == null || userMiddleName.trim().equals("")) ? "" : " " + userMiddleName.trim());
		approverEmailMessage.append((userLastName == null || userLastName.trim().equals("")) ? "" : " " + userLastName.trim());
		//approverEmailMessage.append((userGeneration == null || userGeneration.trim().equals("")) ? "" : " " + userGeneration.trim());

		approverEmailMessage.append(" has submitted a request to change personnel information.<br/>");
		approverEmailMessage.append("The request is ready for your approval. <br/>");
		approverEmailMessage.append("Login to HR to approve.");
		
		approverEmailMessage.append("<p>*****THIS IS AN AUTOMATED MESSAGE. PLEASE DO NOT REPLY*****</p>");

		for(String approverNumber : approversToEmail) {
			try {
				BhrEmpDemo approver = userDao.getUserDetail(approverNumber);

				String approverWorkEmail = approver.getEmail();
				String approverHomeEmail = approver.getHmEmail();
				StringBuilder appMessageContents = new StringBuilder();
				appMessageContents.append(approver.getNameF()+ " " + approver.getNameL()+ ",<br/><br/>" + approverEmailMessage.toString());
				String toApproverEmail =(approverWorkEmail == null || approverWorkEmail.equals("")) ? approverHomeEmail : approverWorkEmail;
				if (toNewEmail!=null && toNewEmail.trim().length() > 0) {
					mailUtil.sendEmail(toApproverEmail, subject, appMessageContents.toString());
				}
				
			} catch(Exception ex) {
				logger.info("Self Service Change Demo Info: An exception has occured with mailing the approver "+ex.toString());
			}
		}
		
	}

    public void payrollDataChangeSendEmailConfirmation(BhrEmpDemo userDemo,String payFreq, boolean payrollSame, boolean accountSame,PayInfoChanges currentPayInfoChanges,W4Info w4Info,Boolean autoApproveBank,List <BankChanges> currentAccountInfoChanges,PayrollFields docRequired) {
		boolean hasDocChanges = false;
		boolean hasApprovChanges = false;
		boolean hasPayInfoChanges = false;
		boolean hasAccountChanges = false;
		
		String userFirstName = userDemo.getNameF();
		String userLastName = userDemo.getNameL();
		String userHomeEmail = userDemo.getHmEmail();
		String userWorkEmail = userDemo.getEmail();
		userFirstName = userFirstName== null ? "" : userFirstName;
		userLastName = userLastName== null ? "" : userLastName;
		
		String subject ="A MESSAGE FROM SELF SERVICE";
		StringBuilder messageContents = new StringBuilder();
		messageContents.append(userFirstName + " " +userLastName + ", <br/><br/>");
		messageContents.append("Your request for changes to payroll data has been submitted. <br/>");
		
		String header = "<p>The following data was automatically approved and updated: </p>";
		StringBuilder contents = new StringBuilder();
		StringBuilder bankContents = new StringBuilder();
		Boolean autoApprove;
		autoApprove = this.getBhrEapPayAssgnGrp("BEA_W4");
		if (currentPayInfoChanges.getFilingStatusChanged() && autoApprove && !payrollSame) {
			contents.append("Filing Status: \t\t" +w4Info.getW4FileStat()+"<br/>");
			hasApprovChanges = true;
			hasPayInfoChanges = true;
		}
		if (currentPayInfoChanges.getMultiJobChanged() && autoApprove && !payrollSame) {
			contents.append("Multi Jobs:\t\t" +w4Info.getW4MultiJob()+"<br/>");
			hasApprovChanges = true;
			hasPayInfoChanges = true;
		}
		if (currentPayInfoChanges.getNumberOfChildrenChanged() && autoApprove && !payrollSame) {
			contents.append("Children under 17: \t\t" +NumberUtil.getDoubleString(w4Info.getW4NbrChldrn()) +"<br/>");
			hasApprovChanges = true;
			hasPayInfoChanges = true;
		}
		if (currentPayInfoChanges.getNumberOfOtherDependChanged() && autoApprove && !payrollSame) {
			contents.append("Other dependents: \t\t" +NumberUtil.getDoubleString(w4Info.getW4NbrOthrDep())+"<br/>");
			hasApprovChanges = true;
			hasPayInfoChanges = true;
		}
		if (currentPayInfoChanges.getOtherIncomeAmtChanged() && autoApprove && !payrollSame) {
			contents.append("Other Income:\t\t" +NumberUtil.getDoubleString(w4Info.getW4OthrIncAmt().doubleValue())+"<br/>");
			hasApprovChanges = true;
			hasPayInfoChanges = true;
		}
		if (currentPayInfoChanges.getOtherDeductAmtChanged() && autoApprove && !payrollSame) {
			contents.append("Deductions: \t\t" +NumberUtil.getDoubleString(w4Info.getW4OthrDedAmt().doubleValue())+"<br/>");
			hasApprovChanges = true;
			hasPayInfoChanges = true;
		}
		if (currentPayInfoChanges.getOtherExemptAmtChanged() && autoApprove && !payrollSame) {
			contents.append("Other Exemptions:\t\t" +NumberUtil.getDoubleString(w4Info.getW4OthrExmptAmt().doubleValue())+"<br/>");
			hasApprovChanges = true;
			hasPayInfoChanges = true;
		}
		
		int index =0;
		for (BankChanges b: currentAccountInfoChanges) {
			boolean tempChanges = false;
			bankContents = new StringBuilder();
			
			if (b.getCodeChanged() && autoApproveBank && !accountSame) {
				bankContents.append("Bank Account Information: \t\t" +b.getBank().getCode().getDescription()+"\t"+b.getBank().getCode().getSubCode()+"<br/>");
				hasApprovChanges = true;
				hasAccountChanges = true;
				tempChanges = true;
			}
			if (b.getAccountNumberChanged() && autoApproveBank && !accountSame) {
				bankContents.append("Bank Account Number:\t\t" +b.getBank().getAccountNumberLabel()+"<br/>");
				hasApprovChanges = true;
				hasAccountChanges = true;
				tempChanges = true;
			}
			if (b.getAccountTypeChanged() && autoApproveBank && !accountSame) {
				bankContents.append("Bank Account Type:\t\t" +b.getBank().getAccountType().getDisplayLabel()+"<br/>");
				hasApprovChanges = true;
				hasAccountChanges = true;
				tempChanges = true;
			}
			if (b.getDepositAmountChanged() && autoApproveBank && !accountSame) {
				bankContents.append("Bank Account Deposit Amount:\t\t" +b.getBank().getDepositAmount().getDisplayAmount()+"<br/>");
				hasApprovChanges = true;
				hasAccountChanges = true;
				tempChanges = true;
			}
			
			if (tempChanges) {
				contents.append("Bank Account " + b.getBank().getCode().getDescription()+": <br/>");
				contents.append(bankContents);
			}
			
			index++;
			
		}
		if (hasApprovChanges) {
			messageContents.append(header);
			messageContents.append(contents);
		}
		
		header = "<br/>The following request(s) requires documentation be provided: <br/>";
		contents = new StringBuilder();
		StringBuilder employeeMessageRequestReview = new StringBuilder();
		
		if (currentPayInfoChanges.getFilingStatusChanged() && !payrollSame) {
			hasPayInfoChanges = true;
			employeeMessageRequestReview.append("Filing Status<br/>");
			if (docRequired.getFilingStatus()) {
				contents.append("Filing Status: \t\t" +w4Info.getW4FileStat()+"<br/>");
				hasDocChanges = true;
			}
		}
		if (currentPayInfoChanges.getMultiJobChanged() && !payrollSame) {
			hasPayInfoChanges = true;
			employeeMessageRequestReview.append("Multi Jobs<br/>");
			if (docRequired.getMultiJob()) {
				contents.append("Multi Jobs:\t\t" +w4Info.getW4MultiJob()+"<br/>");
				hasDocChanges = true;
			}
		}
		if (currentPayInfoChanges.getNumberOfChildrenChanged() && !payrollSame) {
			hasPayInfoChanges = true;
			employeeMessageRequestReview.append("Children under 17<br/>");
			if (docRequired.getNumberOfChildren()) {
				contents.append("Children under 17: \t\t" +w4Info.getW4NbrChldrn()+"<br/>");
				hasDocChanges = true;
			}
		}
		if (currentPayInfoChanges.getNumberOfOtherDependChanged() && !payrollSame) {
			hasPayInfoChanges = true;
			employeeMessageRequestReview.append("Other dependents<br/>");
			if (docRequired.getNumberOfOtherDepend()) {
				contents.append("Other dependents: \t\t" +w4Info.getW4NbrOthrDep()+"<br/>");
				hasDocChanges = true;
			}
		}
		if (currentPayInfoChanges.getOtherIncomeAmtChanged() && !payrollSame) {
			hasPayInfoChanges = true;
			employeeMessageRequestReview.append("Other Income<br/>");
			if (docRequired.getOtherIncomeAmt()) {
				contents.append("Other Income:\t\t" +w4Info.getW4OthrIncAmt()+"<br/>");
				hasDocChanges = true;
			}
		}
		if (currentPayInfoChanges.getOtherDeductAmtChanged() && !payrollSame) {
			hasPayInfoChanges = true;
			employeeMessageRequestReview.append("Deductions<br/>");
			if (docRequired.getOtherDeductAmt()) {
				contents.append("Deductions: \t\t" +w4Info.getW4OthrDedAmt()+"<br/>");
				hasDocChanges = true;
			}
		}
		if (currentPayInfoChanges.getOtherExemptAmtChanged() && !payrollSame) {
			hasPayInfoChanges = true;
			employeeMessageRequestReview.append("Other Exemptions<br/>");
			if (docRequired.getOtherExemptAmt()) {
				contents.append("Other Exemptions:\t\t" +w4Info.getW4OthrExmptAmt()+"<br/>");
				hasDocChanges = true;
			}
		}
		
		index = 0;
		
		for (BankChanges b: currentAccountInfoChanges) {
			boolean tempChanges = false;
			bankContents = new StringBuilder();
			
			if (b.getCodeChanged() && !accountSame) {
				hasAccountChanges = true;
				tempChanges = true;
				employeeMessageRequestReview.append("Bank Account Information<br/>");
				if (docRequired.getCode()) {
					bankContents.append("Bank Account Information: \t\t" + b.getBank().getCode().getDescription()+"\t"+ b.getBank().getCode().getSubCode()+"<br/>");
					hasDocChanges = true;
				}
			}
			if (b.getAccountNumberChanged() && !accountSame) {
				hasAccountChanges = true;
				tempChanges = true;
				employeeMessageRequestReview.append("Bank Account Number<br/>");
				if (docRequired.getAccountNumber()) {
					bankContents.append("Bank Account Number: \t\t" + b.getBank().getAccountNumberLabel()+"<br/>");
					hasDocChanges = true;
				}
			}
			if (b.getAccountTypeChanged() && !accountSame) {
				hasAccountChanges = true;
				tempChanges = true;
				employeeMessageRequestReview.append("Bank Account Type<br/>");
				if (docRequired.getAccountType()) {
					bankContents.append("Bank Account Type:\t\t" +b.getBank().getAccountType().getDisplayLabel()+"<br/>");
					hasDocChanges = true;
				}
			}
			if (b.getDepositAmountChanged() && !accountSame) {
				double amount = b.getBank().getDepositAmount().getAmount();
				boolean newprim = false;
				if (amount == 0) {
					newprim = true;
				}

				hasAccountChanges = true;
				tempChanges = true;
				if (docRequired.getDepositAmount()) {
					if (newprim) {
						bankContents.append("Bank Account Deposit Amount:\t\t" + b.getBank().getDepositAmount().getDisplayAmount()+" (New Primary Account)<br/>");
					} else {
						bankContents.append("Bank Account Deposit Amount:\t\t" + b.getBank().getDepositAmount().getDisplayAmount()+"<br/>");
					}
					hasDocChanges = true;
				}
			}
			if (tempChanges) {
				contents.append("Bank Account  	" + b.getBank().getCode().getDescription()+": <br/>");
				contents.append(bankContents);
			}
			
			index++;
		}
		
		if (hasDocChanges) {
			messageContents.append(header);
		}
		if (hasPayInfoChanges || hasAccountChanges) {
			messageContents.append(contents);

			messageContents.append("<br/>The following request(s) is pending to be reviewed:<br/>");
			messageContents.append(employeeMessageRequestReview.toString()); 
		}
		
		messageContents.append("<p>*****THIS IS AN AUTOMATED MESSAGE. PLEASE DO NOT REPLY*****</p>");
		
		
		if (hasPayInfoChanges || hasAccountChanges) {
			String toEmail ="";
			if (!"".equals(userWorkEmail)) {
				toEmail = userWorkEmail;
			}
			else if (!"".equals(userHomeEmail)) {
				toEmail = userHomeEmail;
			}	
			
			if (toEmail!=null && toEmail.trim().length() > 0) {
				try{
					mailUtil.sendEmail(toEmail, subject, messageContents.toString());
				} 
				catch(Exception ex) {
					logger.info("Self Service Change Payroll Info: An exception has occured with mailing ");
				} 
			} else {
				logger.info("Self Service Change Payroll Info: Unable to send an email confirmation.  No email address is avaiable");
			}
		}
		Integer emailChanges =0;
		if (!payrollSame && accountSame)
			emailChanges =1;
		else if (payrollSame && !accountSame)
			emailChanges = 2;
		else if (!payrollSame && !accountSame)
			emailChanges = 3;
		
		
		//Send ApproverEmail
		autoApprove = this.getBhrEapPayAssgnGrp("BEA_W4");
		if((emailChanges == 1 || emailChanges == 3) && !payrollSame)
		{
			if(!autoApprove)
			{
				String employeeNumber  = referenceDao.getApproverEmployeeNumber(payFreq, "BEA_W4");
				if(employeeNumber == null || "".equals(employeeNumber))
				{
					return;
				}
				BhrEmpDemo approver = userDao.getUserDetail(employeeNumber);
				
				if(approver == null)
				{
					return;
				}
				
				String approverFirstName = approver.getNameF()== null ? "" : approver.getNameF();
				String approverLastName = approver.getNameL()== null ? "" : approver.getNameL();
				
				messageContents = new StringBuilder();
				messageContents.append(approverFirstName + " " + approverLastName + ", <br/><br/>");
				messageContents.append(userFirstName + " " + userLastName + " has submitted a request to change payroll information. <br/>");
				messageContents.append("The request is ready for your approval. <br/>");
				messageContents.append("Login to Human Resources to approve. <br/>");
				
				messageContents.append("<p>*****THIS IS AN AUTOMATED MESSAGE. PLEASE DO NOT REPLY*****</p>");
				
				String toApproverEmail =(approver.getEmail() == null || approver.getEmail().equals("")) ? approver.getHmEmail() : approver.getHmEmail();
	
				if (toApproverEmail!=null && toApproverEmail.trim().length() > 0) {
					try {
						mailUtil.sendEmail(toApproverEmail, subject, messageContents.toString());
					}

					catch(Exception ex) {
						logger.info("Self Service Change Payroll Pay Info: An exception has occured with mailing the approver "+ex.toString());
					}
					
				}
			}
		}
		
		else if((emailChanges == 2 || emailChanges == 3) && !accountSame)
		{
			if(!autoApproveBank)
			{
				String employeeNumber  = referenceDao.getApproverEmployeeNumber(payFreq, "BEA_DRCT_DPST_BNK_ACCT");
				
				if(employeeNumber == null || "".equals(employeeNumber))
				{
					return;
				}
				BhrEmpDemo approver = userDao.getUserDetail(employeeNumber);
				
				if(approver == null)
				{
					return;
				}
	
				String approverFirstName = approver.getNameF()== null ? "" : approver.getNameF();
				String approverLastName = approver.getNameL()== null ? "" : approver.getNameL();
				
				messageContents = new StringBuilder();
				messageContents.append(approverFirstName == null ? "" : approverFirstName + " " + approverLastName == null ? "" : approverLastName + ", <br/><br/>");
				messageContents.append(userFirstName + " " + userLastName+ "\t\t has submitted a request<br/>");
				messageContents.append("to change payroll information. <br/>");
				messageContents.append("The request is ready for your approval. <br/>");
				messageContents.append("Login to Human Resources to approve. <br/>");
				messageContents.append("<p>*****THIS IS AN AUTOMATED MESSAGE. PLEASE DO NOT REPLY*****</p>");
				
				String toApproverEmail =(approver.getEmail() == null || approver.getEmail().equals("")) ? approver.getHmEmail() : approver.getHmEmail();
				
				if (toApproverEmail!=null && toApproverEmail.trim().length() > 0) {
					try {
						mailUtil.sendEmail(toApproverEmail, subject, messageContents.toString());
					}

					catch(Exception ex) {
						logger.info("Self Service Change Payroll Bank Info: An exception has occured with mailing the approver "+ex.toString());
					}
					
				}
			}
		}
		
    }
	
    //ALC-26 update EP password to get settings from DB
    public Map<String, String> getTxeisPreferences() {
		return preferencesDao.getTxeisPreferences();
	}
}
