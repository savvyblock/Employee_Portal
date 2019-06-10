package com.esc20.service;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.AlertDao;
import com.esc20.dao.AppUserDao;
import com.esc20.dao.OptionsDao;
import com.esc20.dao.PayDao;
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
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.SearchUser;


@Service
public class IndexService {

    private Logger logger = LoggerFactory.getLogger(IndexService.class);

    @Autowired
    private AppUserDao userDao;
    
    @Autowired
    private OptionsDao optionsDao;

    @Autowired
    private AlertDao alertDao;
    
    @Autowired
    private PayDao payDao;
    
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
		payDao.savew4Request(w4Request);
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
			result = new PayInfo(demo.getEmpNbr(),"");
		}
		return result; 
	}
	
	public BeaW4 getBeaW4(BhrEmpDemo demo, String frequency)
	{
		BeaW4 result = payDao.getW4(demo.getEmpNbr(), frequency);
		if(result == null) {
			PayInfo info = payDao.getPayInfo(demo.getEmpNbr(), frequency);
			result = new BeaW4(info,demo,frequency);
		}
		return result; 
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
	public void updatePayInfo(BhrEmpDemo demo, BhrEmpPay pay, Character payFreq, Character maritalStatTaxNew, Integer nbrTaxExemptsNew) {
		payDao.updatePayInfo(demo,pay,payFreq,maritalStatTaxNew,nbrTaxExemptsNew);
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
	
	public Boolean isSupervisor(String empNbr) {
		return userDao.isSupervisor(empNbr);
	}
	public Boolean isTempApprover(String empNbr) {
		return userDao.isTempApprover(empNbr);
	}
}
