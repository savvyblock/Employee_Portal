package com.esc20.service;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.AppUserDao;
import com.esc20.dao.OptionsDao;
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
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.SearchUser;

@Service
public class IndexService {

    private Logger logger = LoggerFactory.getLogger(IndexService.class);

    @Autowired
    private AppUserDao userDao;
    
    @Autowired
    private OptionsDao optionsDao;
    
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
	public void updateUser(BeaUsers user) {
		userDao.updateUser(user);
	}
	public BhrEmpDemo getUserDetail(String empNbr) {
		return userDao.getUserDetail(empNbr);
	}
	public Options getOptions() {
		return optionsDao.getOptions();
	}
	public District getDistrict() {
		return userDao.getDistrict();
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
	
	public BeaW4 getW4(BhrEmpPay pay) {
		BeaW4 result = userDao.getW4(pay.getId());
		if(result == null) {
			result = new BeaW4(pay);
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
	public boolean getBhrEapDemoAssgnGrp(String tableName) {
		return userDao.getBhrEapDemoAssgnGrp(tableName);
	}
	public void updateDemoName(BhrEmpDemo demo) {
		userDao.updateDemoName(demo);
	}
	public void updateDemoAvatar(BhrEmpDemo demo) {
		userDao.updateDemoAvatar(demo);
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
	
	public BhrEmpDemo retrieveEmployee(SearchUser searchUser) {
		return userDao.retrieveEmployee(searchUser);
		
	}
	
}
