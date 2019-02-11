package net.esc20.txeis.EmployeeAccess.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.dao.api.IDemoDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IOptionsDao;
import net.esc20.txeis.EmployeeAccess.domainobject.Options;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.AltAddr;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.CellPhone;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DemoDomainObject;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DemoInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DemoInfoFields;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DriversLicense;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.Ed20Command;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.Ed25Command;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.Email;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.EmergencyContact;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.HomePhone;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.MailAddr;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.Marital;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.Name;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.RestrictionCodes;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.TraqsData;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.WorkPhone;
import net.esc20.txeis.EmployeeAccess.service.api.IDemoService;
import net.esc20.txeis.EmployeeAccess.service.api.IMailUtilService;
import net.esc20.txeis.EmployeeAccess.validation.ContextValidationUtil;
import net.esc20.txeis.EmployeeAccess.web.view.Demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.binding.message.MessageContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.webflow.core.collection.LocalSharedAttributeMap;

@Service
public class DemoService implements IDemoService {
	private IDemoDao demoDao;
	private IOptionsDao optionsDao;
	private DemoInfoFields requiredFields;
	private DemoInfoFields docRequiredFields;
	private IMailUtilService mailUtilService;
	boolean updateTEAMFlag = false;
	private Map<String, String> modifiedFields = new HashMap<String, String> ();
	private Map<String, String> modFldsBoolean = new HashMap<String, String> ();

	private static Log log = LogFactory.getLog(UserService.class);

	public IDemoDao getDemoDao() {
		return demoDao;
	}

	public void setDemoDao(IDemoDao demoDao) {
		this.demoDao = demoDao;
	}

	public IOptionsDao getOptionsDao() {
		return optionsDao;
	}

	public void setOptionsDao(IOptionsDao optionsDao) {
		this.optionsDao = optionsDao;
	}

	@Override
	public DemoInfo getDemoInfo(String employeeNumber) {
		return demoDao.getDemoInfo(employeeNumber);
	}

	@Override
	public List<String> getAvailableMaritalStatuses() {
		return ReferenceDataService.getMaritalStatusesDisplay();
	}

	@Override
	public List<String> getAvailableMaritalActualStatuses() {
		return ReferenceDataService.getMaritalActualStatusDisplay();
	}

	@Override
	public List<String> getAvailableStates() {
		return ReferenceDataService.getStatesDisplay();
	}

	@Override
	public List<String> getAvailableRestrictionCodes() {
		return ReferenceDataService.getRestrictionDisplay();
	}

	@Override
	public List<String> getAvailableTitles() {
		return ReferenceDataService.getTitlesDisplay();
	}

	@Override
	public List<String> getAvailableGenerations() {
		return ReferenceDataService.getGenerationsDisplay();
	}

	@Override
	public DemoDomainObject revertDomainObject( DemoDomainObject currentDemoDomainObject ) {
		return currentDemoDomainObject.clone();
	}

	@Override
	public Boolean saveDemo(MessageContext messageContext, Demo demo, DemoInfo demoInfoPending, DemoInfo demoInfo ) throws Exception {
		DemoInfo newDemoInfo = demo.getDemoInfo();
		String employeeNumber = demoInfo.getEmployeeNumber();
		Boolean success = true;
		StringBuilder errors = new StringBuilder();

		TraqsData traqs = null;
		try {
			traqs = demoDao.getTraqsData();
		} catch(Exception e) {
			success = false;
		}
		//only save each group if a change has been made to the current pending entry in the DB
		if(!newDemoInfo.getName().equals(demoInfoPending.getName())) {
			if(!saveName(employeeNumber, newDemoInfo.getName(), demoInfo.getName(), demo.getAutoApproveName(), traqs)) {
				success = false;
				errors.append("Legal Name, ");
			}
			updateTEAMFlag = true;
		}
		if (!newDemoInfo.getMailAddr().equals(demoInfoPending.getMailAddr())) {
			if (!saveMailAddr(employeeNumber, newDemoInfo.getMailAddr(), demoInfo.getMailAddr(), demo.getAutoApproveMailAddr(), traqs)) {
				success = false;
				errors.append("Mailing Address, ");
			}
			updateTEAMFlag = true;
		}
		if(!newDemoInfo.getAltAddr().equals(demoInfoPending.getAltAddr())) {
			if( !saveAltAddr(employeeNumber, newDemoInfo.getAltAddr(), demoInfo.getAltAddr(), demo.getAutoApproveAltAddr())) {
				success = false;
				errors.append("Alternate Address, ");
			}
		}
		if (!newDemoInfo.getHomePhone().equals(demoInfoPending.getHomePhone())) {
			if (!saveHomePhone(employeeNumber, newDemoInfo.getHomePhone(), demoInfo.getHomePhone(), demo.getAutoApproveHomePhone(), traqs)) {
				success = false;
				errors.append("Home Phone, ");
			}
		}
		if(!newDemoInfo.getWorkPhone().equals(demoInfoPending.getWorkPhone())) {
			if( !saveWorkPhone(employeeNumber, newDemoInfo.getWorkPhone(), demoInfo.getWorkPhone(), demo.getAutoApproveWorkPhone()) ) {
				success = false;
				errors.append("Work Phone, ");
			}
		}
		if(!newDemoInfo.getCellPhone().equals(demoInfoPending.getCellPhone())) {
			if( !saveCellPhone(employeeNumber, newDemoInfo.getCellPhone(), demoInfo.getCellPhone(), demo.getAutoApproveCellPhone())) {
				success = false;
				errors.append("Cell Phone, ");
			}
			updateTEAMFlag = true;
		}
		if(!newDemoInfo.getRestrictionCodes().equals(demoInfoPending.getRestrictionCodes())) {
			if( !saveRestrictionCodes(employeeNumber, newDemoInfo.getRestrictionCodes(), demoInfo.getRestrictionCodes(), demo.getAutoApproveRestrictionCodes())) {
				success = false;
				errors.append("Restriction Codes, ");
			}
		}
		if(!newDemoInfo.getMarital().equals(demoInfoPending.getMarital())) {
			if( !saveMarital(employeeNumber, newDemoInfo.getMarital(), demoInfo.getMarital(), demo.getAutoApproveMarital())) {
				success = false;
				errors.append("Marital Info, ");
			}
		}
		if(!newDemoInfo.getDriversLicense().equals(demoInfoPending.getDriversLicense())) {
			if( !saveDriversLicense(employeeNumber, newDemoInfo.getDriversLicense(), demoInfo.getDriversLicense(), demo.getAutoApproveDriversLicense())) {
				success = false;
				errors.append("Driver's License, ");
			}
		}
		if(!newDemoInfo.getEmergencyContact().equals(demoInfoPending.getEmergencyContact())) {
			if(!saveEmergencyContact(employeeNumber, newDemoInfo.getEmergencyContact(), demoInfo.getEmergencyContact(), demo.getAutoApproveEmergencyContact())){
				success = false;
				errors.append("Emergency Contact Info, ");
			}
		}
		if(!newDemoInfo.getEmail().equals(demoInfoPending.getEmail())) {
			if( !saveEmail(employeeNumber, newDemoInfo.getEmail(), demoInfo.getEmail(), demo.getAutoApproveEmail())) {
				success = false;
				errors.append("Email, ");
			}
			updateTEAMFlag = true;
		}

		if( success ) {
			messageContext.addMessage(ContextValidationUtil.buildSaveMessage(messageContext.toString(), "Save successful"));
		}
		if( !success ) {
			String errorsString = errors.toString();

			messageContext.addMessage(ContextValidationUtil.buildSaveMessage(messageContext.toString(), "No new requests have been created. " + 
						"The following group(s) experienced errors while saving: " + errorsString.replaceFirst(", $", ".")));
			//this exception is caught by the flow, but is necessary to trigger a rollback
			//the rollback behavior is handled by the transactionManager bean 
			throw new Exception();
		}

		// ***************************************************************************************************************
		// Check if any Demographic Information has changed
		boolean hasDemoInfoChanged = hasDemoInfoChanged(demo, demoInfoPending, newDemoInfo);

		// If Demographic Information was changed then check the below conditions
		/*
		 *  If record exists in ED20 only - Update ED20 record
		 *  If record exists in ED20 & ED25 - Update ED20 record & Update ED25 record
		 *  If record exists in ED25 only - Update ED25 record
		 *  If record does not exist in ED20 & ED25 - Insert ED25 record
		 */
		if (updateTEAMFlag && hasDemoInfoChanged) {
//			modifiedFields.put("emp_nbr", demoInfo.getEmployeeNumber());
//			boolean updateTEAMSuccess = false;
			List<Ed20Command> ed20List = new ArrayList<Ed20Command>();
			List<Ed25Command> ed25List = new ArrayList<Ed25Command>();
			String currentYr = "", currentMon = "";
			Calendar now = Calendar.getInstance();
			currentYr =  String.valueOf(now.get(Calendar.YEAR));
			currentMon = String.valueOf(now.get(Calendar.MONTH) + 1);
			if (Integer.parseInt(currentMon) < 10) {
				currentMon = "0" + currentMon;
			}
			ed20List = demoDao.getEd20Records(demoInfo.getEmployeeNumber(), currentYr, currentMon);
			ed25List = demoDao.getEd25Records(demoInfo.getEmployeeNumber(), currentYr, currentMon);
			updateTEAMLists(ed20List, ed25List);
			if ((null == ed20List ||
				 ed20List.size() == 0) &&
				(null == ed25List ||
				 ed25List.size() == 0)) {
				insertEd25List(ed25List, currentYr, currentMon);
			}
			if (null != ed20List &&
				ed20List.size() > 0) {
				demoDao.updEd20Recs(ed20List);
			}
			if (null != ed25List &&
				ed25List.size() >  0) {
				demoDao.insUpdEd25Recs(ed25List);
			}
		}
		return success;
	}

	private boolean hasDemoInfoChanged(Demo demo, DemoInfo currentDemoInfo, DemoInfo newDemoInfo) {
		boolean lb_changed = false;
		modifiedFields = new HashMap<String, String> ();
		modFldsBoolean = new HashMap<String, String> ();

		// Legal Name Changes
		if(newDemoInfo.getName() != null && demo.getAutoApproveName()) {
			if (!currentDemoInfo.getName().getLastName().trim().equalsIgnoreCase(newDemoInfo.getName().getLastName().trim())) {
				modifiedFields.put("name_l", newDemoInfo.getName().getLastName().trim());
				modifiedFields.put("orig_name_l", currentDemoInfo.getName().getLastName().trim());
				modifiedFields.put("new_name_l", newDemoInfo.getName().getLastName().trim());
				modFldsBoolean.put("name_l", "true");
				modFldsBoolean.put("orig_name_l", "true");
				modFldsBoolean.put("new_name_l", "true");
				lb_changed = true;	
			} else {
				modifiedFields.put("name_l", currentDemoInfo.getName().getLastName().trim());
				modifiedFields.put("orig_name_l", currentDemoInfo.getName().getLastName().trim());
				modifiedFields.put("new_name_l", "");
				modFldsBoolean.put("name_l", "false");
				modFldsBoolean.put("orig_name_l", "false");
				modFldsBoolean.put("new_name_l", "false");
			}
			if (!currentDemoInfo.getName().getFirstName().trim().equalsIgnoreCase(newDemoInfo.getName().getFirstName().trim())) {
				modifiedFields.put("name_f", newDemoInfo.getName().getFirstName().trim());
				modifiedFields.put("orig_name_f", currentDemoInfo.getName().getFirstName().trim());
				modifiedFields.put("new_name_f", newDemoInfo.getName().getFirstName().trim());
				modFldsBoolean.put("name_f", "true");
				modFldsBoolean.put("orig_name_f", "true");
				modFldsBoolean.put("new_name_f", "true");
				lb_changed = true;
			} else {
				modifiedFields.put("name_f", currentDemoInfo.getName().getFirstName().trim());
				modifiedFields.put("orig_name_f", currentDemoInfo.getName().getFirstName().trim());
				modifiedFields.put("new_name_f", "");
				modFldsBoolean.put("name_f", "false");
				modFldsBoolean.put("orig_name_f", "false");
				modFldsBoolean.put("new_name_f", "false");
			}
			if (!currentDemoInfo.getName().getMiddleName().trim().equalsIgnoreCase(newDemoInfo.getName().getMiddleName().trim())) {
				modifiedFields.put("name_m", newDemoInfo.getName().getMiddleName().trim());
				modifiedFields.put("orig_name_m", currentDemoInfo.getName().getMiddleName().trim());
				modifiedFields.put("new_name_m", newDemoInfo.getName().getMiddleName().trim());
				modFldsBoolean.put("name_m", "true");
				modFldsBoolean.put("orig_name_m", "true");
				modFldsBoolean.put("new_name_m", "true");
				lb_changed = true;
			} else {
				modifiedFields.put("name_m", currentDemoInfo.getName().getMiddleName().trim());
				modifiedFields.put("orig_name_m", currentDemoInfo.getName().getMiddleName().trim());
				modifiedFields.put("new_name_m", "");
				modFldsBoolean.put("name_m", "false");
				modFldsBoolean.put("orig_name_m", "false");
				modFldsBoolean.put("new_name_m", "false");
			}
			if (!currentDemoInfo.getName().getGenerationCode().trim().equalsIgnoreCase(newDemoInfo.getName().getGenerationCode().trim())) {
				modifiedFields.put("name_gen", newDemoInfo.getName().getGenerationCode().trim());
				modifiedFields.put("orig_name_gen", currentDemoInfo.getName().getGenerationCode().trim());
				modifiedFields.put("new_name_gen", newDemoInfo.getName().getGenerationCode().trim());
				modFldsBoolean.put("name_gen", "true");
				modFldsBoolean.put("orig_name_gen", "true");
				modFldsBoolean.put("new_name_gen", "true");
				lb_changed = true;
			} else {
				modifiedFields.put("name_gen", currentDemoInfo.getName().getGenerationCode().trim());
				modifiedFields.put("orig_name_gen", currentDemoInfo.getName().getGenerationCode().trim());
				modifiedFields.put("new_name_gen", "");
				modFldsBoolean.put("name_gen", "true");
				modFldsBoolean.put("orig_name_gen", "true");
				modFldsBoolean.put("new_name_gen", "true");
			}
		} else {
			modifiedFields.put("new_name_l", "");
			modifiedFields.put("new_name_f", "");
			modifiedFields.put("new_name_m", "");
			modifiedFields.put("new_name_gen", "");
			modFldsBoolean.put("new_name_l", "false");
			modFldsBoolean.put("new_name_f", "false");
			modFldsBoolean.put("new_name_m", "false");
			modFldsBoolean.put("new_name_gen", "false");
		}

		// Mailing Address Changes
		// 20170215 if any piece of the address is changed, then the include the unchanged pieces of the address to the new address portion 
		//			of the ed25 record.  So if only the zip code is changed, then the entire address will be entered into the new portion of the record
		if (newDemoInfo.getMailAddr() != null && demo.getAutoApproveMailAddr()) {
			if (currentDemoInfo.getMailAddr().getNumber().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getNumber().trim()) &&
				currentDemoInfo.getMailAddr().getStreet().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getStreet().trim()) &&
				currentDemoInfo.getMailAddr().getApartment().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getApartment().trim()) &&
				currentDemoInfo.getMailAddr().getCity().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getCity().trim()) &&
				currentDemoInfo.getMailAddr().getStateCode().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getStateCode().trim()) &&
				currentDemoInfo.getMailAddr().getZip().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getZip().trim()) &&
				currentDemoInfo.getMailAddr().getZipPlusFour().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getZipPlusFour().trim())) {
				modifiedFields.put("new_addr_nbr", "");
				modifiedFields.put("new_addr_str", "");
				modifiedFields.put("new_addr_apt", "");
				modifiedFields.put("new_addr_city", "");
				modifiedFields.put("new_addr_st", "");
				modifiedFields.put("new_addr_zip", "");
				modifiedFields.put("new_addr_zip4", "");
				modFldsBoolean.put("new_addr_nbr", "false");
				modFldsBoolean.put("new_addr_str", "false");
				modFldsBoolean.put("new_addr_apt", "false");
				modFldsBoolean.put("new_addr_city", "false");
				modFldsBoolean.put("new_addr_st", "false");
				modFldsBoolean.put("new_addr_zip", "false");
				modFldsBoolean.put("new_addr_zip4", "false");
			} else {
				if (!currentDemoInfo.getMailAddr().getNumber().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getNumber().trim())) {
					modifiedFields.put("addr_nbr", newDemoInfo.getMailAddr().getNumber().trim());
					modifiedFields.put("orig_addr_nbr", currentDemoInfo.getMailAddr().getNumber().trim());
					modifiedFields.put("new_addr_nbr", newDemoInfo.getMailAddr().getNumber().trim());
					modFldsBoolean.put("addr_nbr", "true");
					modFldsBoolean.put("orig_addr_nbr", "true");
					modFldsBoolean.put("new_addr_nbr", "true");
					lb_changed = true;
				} else {
					modifiedFields.put("addr_nbr", currentDemoInfo.getMailAddr().getNumber().trim());
					modifiedFields.put("orig_addr_nbr", currentDemoInfo.getMailAddr().getNumber().trim());
					modifiedFields.put("new_addr_nbr", currentDemoInfo.getMailAddr().getNumber().trim());
					modFldsBoolean.put("addr_nbr", "true");
					modFldsBoolean.put("orig_addr_nbr", "true");
					modFldsBoolean.put("new_addr_nbr", "true");
				}
				if (!currentDemoInfo.getMailAddr().getStreet().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getStreet().trim())) {
					modifiedFields.put("addr_str", newDemoInfo.getMailAddr().getStreet().trim());
					modifiedFields.put("orig_addr_str", currentDemoInfo.getMailAddr().getStreet().trim());
					modifiedFields.put("new_addr_str", newDemoInfo.getMailAddr().getStreet().trim());
					modFldsBoolean.put("addr_str", "true");
					modFldsBoolean.put("orig_addr_str", "true");
					modFldsBoolean.put("new_addr_str", "true");
					lb_changed = true;
				} else {
					modifiedFields.put("addr_str", currentDemoInfo.getMailAddr().getStreet().trim());
					modifiedFields.put("orig_addr_str", currentDemoInfo.getMailAddr().getStreet().trim());
					modifiedFields.put("new_addr_str", currentDemoInfo.getMailAddr().getStreet().trim());
					modFldsBoolean.put("addr_str", "true");
					modFldsBoolean.put("orig_addr_str", "true");
					modFldsBoolean.put("new_addr_str", "true");
				}
				if (!currentDemoInfo.getMailAddr().getApartment().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getApartment().trim())) {
					modifiedFields.put("addr_apt", newDemoInfo.getMailAddr().getApartment().trim());
					modifiedFields.put("orig_addr_apt", currentDemoInfo.getMailAddr().getApartment().trim());
					modifiedFields.put("new_addr_apt", newDemoInfo.getMailAddr().getApartment().trim());
					modFldsBoolean.put("addr_apt", "true");
					modFldsBoolean.put("orig_addr_apt", "true");
					modFldsBoolean.put("new_addr_apt", "true");
					lb_changed = true;
				} else {
					modifiedFields.put("addr_apt", currentDemoInfo.getMailAddr().getApartment().trim());
					modifiedFields.put("orig_addr_apt", currentDemoInfo.getMailAddr().getApartment().trim());
					modifiedFields.put("new_addr_apt", currentDemoInfo.getMailAddr().getApartment().trim());
					modFldsBoolean.put("addr_apt", "true");
					modFldsBoolean.put("orig_addr_apt", "true");
					modFldsBoolean.put("new_addr_apt", "true");
				}
				if (!currentDemoInfo.getMailAddr().getCity().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getCity().trim())) {
					modifiedFields.put("addr_city", newDemoInfo.getMailAddr().getCity().trim());
					modifiedFields.put("orig_addr_city", currentDemoInfo.getMailAddr().getCity().trim());
					modifiedFields.put("new_addr_city", newDemoInfo.getMailAddr().getCity().trim());
					modFldsBoolean.put("addr_city", "true");
					modFldsBoolean.put("orig_addr_city", "true");
					modFldsBoolean.put("new_addr_city", "true");
					lb_changed = true;
				} else {
					modifiedFields.put("addr_city", currentDemoInfo.getMailAddr().getCity().trim());
					modifiedFields.put("orig_addr_city", currentDemoInfo.getMailAddr().getCity().trim());
					modifiedFields.put("new_addr_city", currentDemoInfo.getMailAddr().getCity().trim());
					modFldsBoolean.put("addr_city", "true");
					modFldsBoolean.put("orig_addr_city", "true");
					modFldsBoolean.put("new_addr_city", "true");
				}
				if (!currentDemoInfo.getMailAddr().getStateCode().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getStateCode().trim())) {
					modifiedFields.put("addr_st", newDemoInfo.getMailAddr().getStateCode().trim());
					modifiedFields.put("orig_addr_st", currentDemoInfo.getMailAddr().getStateCode().trim());
					modifiedFields.put("new_addr_st", newDemoInfo.getMailAddr().getStateCode().trim());
					modFldsBoolean.put("addr_st", "true");
					modFldsBoolean.put("orig_addr_st", "true");
					modFldsBoolean.put("new_addr_st", "true");
					lb_changed = true;
				} else {
					modifiedFields.put("addr_st", currentDemoInfo.getMailAddr().getStateCode().trim());
					modifiedFields.put("orig_addr_st", currentDemoInfo.getMailAddr().getStateCode().trim());
					modifiedFields.put("new_addr_st", currentDemoInfo.getMailAddr().getStateCode().trim());
					modFldsBoolean.put("addr_st", "true");
					modFldsBoolean.put("orig_addr_st", "true");
					modFldsBoolean.put("new_addr_st", "true");
				}
				if (!currentDemoInfo.getMailAddr().getZip().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getZip().trim())) {
					modifiedFields.put("addr_zip", newDemoInfo.getMailAddr().getZip().trim());
					modifiedFields.put("orig_addr_zip", currentDemoInfo.getMailAddr().getZip().trim());
					modifiedFields.put("new_addr_zip", newDemoInfo.getMailAddr().getZip().trim());
					modFldsBoolean.put("addr_zip", "true");
					modFldsBoolean.put("orig_addr_zip", "true");
					modFldsBoolean.put("new_addr_zip", "true");
					lb_changed = true;
				} else {
					modifiedFields.put("addr_zip", currentDemoInfo.getMailAddr().getZip().trim());
					modifiedFields.put("orig_addr_zip", currentDemoInfo.getMailAddr().getZip().trim());
					modifiedFields.put("new_addr_zip", currentDemoInfo.getMailAddr().getZip().trim());
					modFldsBoolean.put("addr_zip", "true");
					modFldsBoolean.put("orig_addr_zip", "true");
					modFldsBoolean.put("new_addr_zip", "true");
				}
				if (!currentDemoInfo.getMailAddr().getZipPlusFour().trim().equalsIgnoreCase(newDemoInfo.getMailAddr().getZipPlusFour().trim())) {
					modifiedFields.put("addr_zip4", newDemoInfo.getMailAddr().getZipPlusFour().trim());
					modifiedFields.put("orig_addr_zip4", currentDemoInfo.getMailAddr().getZipPlusFour().trim());
					modifiedFields.put("new_addr_zip4", newDemoInfo.getMailAddr().getZipPlusFour().trim());
					modFldsBoolean.put("addr_zip4", "true");
					modFldsBoolean.put("orig_addr_zip4", "true");
					modFldsBoolean.put("new_addr_zip4", "true");
					lb_changed = true;
				} else {
					modifiedFields.put("addr_zip4", currentDemoInfo.getMailAddr().getZipPlusFour().trim());
					modifiedFields.put("orig_addr_zip4", currentDemoInfo.getMailAddr().getZipPlusFour().trim());
					modifiedFields.put("new_addr_zip4", currentDemoInfo.getMailAddr().getZipPlusFour().trim());
					modFldsBoolean.put("addr_zip4", "true");
					modFldsBoolean.put("orig_addr_zip4", "true");
					modFldsBoolean.put("new_addr_zip4", "true");
				}
			}
		} else {
			modifiedFields.put("new_addr_nbr", "");
			modifiedFields.put("new_addr_str", "");
			modifiedFields.put("new_addr_apt", "");
			modifiedFields.put("new_addr_city", "");
			modifiedFields.put("new_addr_st", "");
			modifiedFields.put("new_addr_zip", "");
			modifiedFields.put("new_addr_zip4", "");
			modFldsBoolean.put("new_addr_nbr", "false");
			modFldsBoolean.put("new_addr_str", "false");
			modFldsBoolean.put("new_addr_apt", "false");
			modFldsBoolean.put("new_addr_city", "false");
			modFldsBoolean.put("new_addr_st", "false");
			modFldsBoolean.put("new_addr_zip", "false");
			modFldsBoolean.put("new_addr_zip4", "false");
		}

		// Home Phone Number Changes
		if (currentDemoInfo.getHomePhone() != null && demo.getAutoApproveHomePhone()) {
			if (currentDemoInfo.getHomePhone().getAreaCode().trim().equalsIgnoreCase(newDemoInfo.getHomePhone().getAreaCode().trim()) &&
				currentDemoInfo.getHomePhone().getPhoneNumber().trim().equalsIgnoreCase(newDemoInfo.getHomePhone().getPhoneNumber().trim())) {
					modifiedFields.put("new_phone_area", "");
					modifiedFields.put("new_phone_nbr", "");
					modFldsBoolean.put("new_phone_area", "false");
					modFldsBoolean.put("new_phone_nbr", "false");
			} else {
				if (!currentDemoInfo.getHomePhone().getAreaCode().trim().equalsIgnoreCase(newDemoInfo.getHomePhone().getAreaCode().trim())) {
					modifiedFields.put("phone_area", newDemoInfo.getHomePhone().getAreaCode().trim());
					modifiedFields.put("orig_phone_area", currentDemoInfo.getHomePhone().getAreaCode().trim());
					modifiedFields.put("new_phone_area", newDemoInfo.getHomePhone().getAreaCode().trim());
					modFldsBoolean.put("phone_area", "true");
					modFldsBoolean.put("orig_phone_area", "true");
					modFldsBoolean.put("new_phone_area", "true");
					lb_changed = true;
				} else {
					modifiedFields.put("phone_area", currentDemoInfo.getHomePhone().getAreaCode().trim());
					modifiedFields.put("orig_phone_area", currentDemoInfo.getHomePhone().getAreaCode().trim());
					modifiedFields.put("new_phone_area", currentDemoInfo.getHomePhone().getAreaCode().trim());
					modFldsBoolean.put("phone_area", "true");
					modFldsBoolean.put("orig_phone_area", "true");
					modFldsBoolean.put("new_phone_area", "true");
				}
				if (!currentDemoInfo.getHomePhone().getPhoneNumber().trim().equalsIgnoreCase(newDemoInfo.getHomePhone().getPhoneNumber().trim())) {
					modifiedFields.put("phone_nbr", newDemoInfo.getHomePhone().getPhoneNumber().trim());
					modifiedFields.put("orig_phone_nbr", currentDemoInfo.getHomePhone().getPhoneNumber().trim());
					modifiedFields.put("new_phone_nbr", newDemoInfo.getHomePhone().getPhoneNumber().trim());
					modFldsBoolean.put("phone_nbr", "true");
					modFldsBoolean.put("orig_phone_nbr", "true");
					modFldsBoolean.put("new_phone_nbr", "true");
					lb_changed = true;
				} else {
					modifiedFields.put("phone_nbr", currentDemoInfo.getHomePhone().getPhoneNumber().trim());
					modifiedFields.put("orig_phone_nbr", currentDemoInfo.getHomePhone().getPhoneNumber().trim());
					modifiedFields.put("new_phone_nbr", currentDemoInfo.getHomePhone().getPhoneNumber().trim());
					modFldsBoolean.put("phone_nbr", "true");
					modFldsBoolean.put("orig_phone_nbr", "true");
					modFldsBoolean.put("new_phone_nbr", "true");
				}
			}
		} else {
			modifiedFields.put("new_phone_area", "");
			modifiedFields.put("new_phone_nbr", "");
			modFldsBoolean.put("new_phone_area", "false");
			modFldsBoolean.put("new_phone_nbr", "false");
		}

		// Email Changes
		if (currentDemoInfo.getEmail() != null && demo.getAutoApproveEmail()) {
			if (!currentDemoInfo.getEmail().getWorkEmail().trim().equalsIgnoreCase(newDemoInfo.getEmail().getWorkEmail().trim())) {
				modifiedFields.put("email", newDemoInfo.getEmail().getWorkEmail().trim());
				modifiedFields.put("orig_email", currentDemoInfo.getEmail().getWorkEmail().trim());
				modifiedFields.put("new_email", newDemoInfo.getEmail().getWorkEmail().trim());
				modFldsBoolean.put("email", "true");
				modFldsBoolean.put("orig_email", "true");
				modFldsBoolean.put("new_email", "true");
				lb_changed = true;
			} else {
				modifiedFields.put("email", currentDemoInfo.getEmail().getWorkEmail().trim());
				modifiedFields.put("orig_email", currentDemoInfo.getEmail().getWorkEmail().trim());
				modifiedFields.put("new_email", "");
				modFldsBoolean.put("email", "false");
				modFldsBoolean.put("orig_email", "false");
				modFldsBoolean.put("new_email", "false");
			}
		} else {
			modifiedFields.put("new_email", "");
			modFldsBoolean.put("new_email", "false");
		}
		
		if (lb_changed) {
			modifiedFields.put("staff_id", currentDemoInfo.getStaffId().trim());
			modifiedFields.put("dob", currentDemoInfo.getDob());
			modifiedFields.put("sex", currentDemoInfo.getSex());
			modifiedFields.put("empNbr", currentDemoInfo.getEmployeeNumber());
			// also need these fields for call to insertEd25List() to insert a record into the BHR_TEAM_ED25 table
			// these fields have not been modified but are the current values
			modifiedFields.put("orig_name_l", currentDemoInfo.getName().getLastName());
			modifiedFields.put("orig_name_f", currentDemoInfo.getName().getFirstName());
			modifiedFields.put("orig_name_m", currentDemoInfo.getName().getMiddleName());
			modifiedFields.put("orig_name_gen", currentDemoInfo.getName().getGenerationCode());
		}
		return lb_changed;
	}

	private void updateMD2025Records(String staffId, TraqsData traqs, Name oldName, Name newName) {
		List<TraqsData> md20Entries = demoDao.getMD20EntriesForEmployee(staffId);
		Boolean hasMD20 = false;

		for (TraqsData entry : md20Entries) {
			if (entry.getTrsReportYear() >= traqs.getTrsReportYear() && entry.getTrsReportMonth() >= traqs.getTrsReportMonth()) {
				demoDao.updateMD20Record(staffId, entry, newName);
				hasMD20 = true;
			}
		}
		if (!hasMD20) {
			if( demoDao.md25EntryExists(staffId, traqs) )
				demoDao.updateMD25Record(staffId, traqs, oldName, newName);
			else
				demoDao.createMD25Record(staffId, traqs, oldName, newName);
		}
	}

	private void updateMD30Records(String staffId, TraqsData traqs, MailAddr newMailAddr, HomePhone newHomePhone) {
		//cs20140430  Update MD30/31 basing on the address changes or home phone changes
		if (newMailAddr != null) {
			if (demoDao.md30EntryExists(staffId, traqs) ) {
				demoDao.updateMD30Record(staffId, traqs, newMailAddr, null);
			}
			else {
				demoDao.createMD30Record(staffId, traqs, newMailAddr, null);
			}
		}
		if (newHomePhone != null) {
			if (demoDao.md30EntryExists(staffId, traqs) ) {
				demoDao.updateMD30Record(staffId, traqs, null, newHomePhone);
			}
			else {
				demoDao.createMD30Record(staffId, traqs, null, newHomePhone);
			}
		}
	}

	private Boolean saveName(String employeeNumber, Name newName, Name name, Boolean isAutoApprove, TraqsData traqs) {
		try {
			String tableName =  ReferenceDataService.LEGAL_NAME_TABLE;
			//check if row is marked for deletion, set all values to empty. Check if new row or not to insert/update. Then check if auto approve to insert/update.
			Boolean deleteRequest = newName.equals(name);
			Boolean isNewRow = demoDao.isNewRow(employeeNumber, tableName); 

			if (!deleteRequest) {
				if (isNewRow)
					demoDao.insertNameRequest(isAutoApprove, employeeNumber, newName, name);
				else
					demoDao.updateNameRequest(isAutoApprove, employeeNumber, newName, name);

				if (isAutoApprove) {
					demoDao.updateNameApprove(employeeNumber, newName);

					if (demoDao.isTrsEmployee(employeeNumber) && traqs != null && traqs.getAutomate().equalsIgnoreCase("Y")) {
						//cs20140430 Excluding the title changed for MD20/MD25
						if (!name.getLastName().trim().equalsIgnoreCase(newName.getLastName().trim()) ||
							!name.getFirstName().trim().equalsIgnoreCase(newName.getFirstName().trim()) ||
							!name.getMiddleName().trim().equalsIgnoreCase(newName.getMiddleName().trim()) ||
							!name.getGenerationCode().trim().equalsIgnoreCase(newName.getGenerationCode().trim()) ) {
							updateMD2025Records(demoDao.getEmployeeStaffId(employeeNumber), traqs, name, newName);
						}
					}
				}
			}
			else {
				demoDao.deleteRequestIfExists(employeeNumber, tableName);
			}
		}
		catch (Exception e) {
			log.error("An exception has occured saving Legal Name.", e);
			return false;
		}
		return true;
	}

	private boolean saveMailAddr(String employeeNumber, MailAddr newMailAddr, MailAddr mailAddr, Boolean isAutoApprove, TraqsData traqs) {
		try {
			String tableName =  ReferenceDataService.MAIL_ADDR_TABLE;
			//check if row is marked for deletion, set all values to empty. Check if new row or not to insert/update. Then check if auto approve to insert/update.
			Boolean deleteRequest = newMailAddr.equals(mailAddr);
			Boolean isNewRow = demoDao.isNewRow(employeeNumber, tableName); 

			if (!deleteRequest) {
				if (isNewRow)
					demoDao.insertMailAddrRequest(isAutoApprove, employeeNumber, newMailAddr, mailAddr);
				else
					demoDao.updateMailAddrRequest(isAutoApprove, employeeNumber, newMailAddr, mailAddr);

				if (isAutoApprove) {
					demoDao.updateMailAddrApprove(employeeNumber, newMailAddr);

					if (demoDao.isTrsEmployee(employeeNumber) && traqs != null && traqs.getAutomate().equalsIgnoreCase("Y")) {
						updateMD30Records(demoDao.getEmployeeStaffId(employeeNumber), traqs, newMailAddr, null);
					}
				}
			}
			else {
				demoDao.deleteRequestIfExists(employeeNumber, tableName);
			}
		}
		catch (Exception e) {
			log.error("An exception has occured saving Mailing Address.", e);
			return false;
		}
		return true;
	}

	private boolean saveAltAddr(String employeeNumber, AltAddr newAltAddr, AltAddr altAddr, Boolean isAutoApprove) {
		try {
			String tableName =  ReferenceDataService.ALT_MAIL_TABLE;
			//check if row is marked for deletion, set all values to empty. Check if new row or not to insert/update. Then check if auto approve to insert/update.
			Boolean deleteRequest = newAltAddr.equals(altAddr);
			Boolean isNewRow = demoDao.isNewRow(employeeNumber, tableName); 

			if (!deleteRequest) {
				if(isNewRow)
					demoDao.insertAltAddrRequest(isAutoApprove, employeeNumber, newAltAddr, altAddr);
				else
					demoDao.updateAltAddrRequest(isAutoApprove, employeeNumber, newAltAddr, altAddr);

				if (isAutoApprove)
					demoDao.updateAltAddrApprove(employeeNumber, newAltAddr);
			}
			else {
				demoDao.deleteRequestIfExists(employeeNumber, tableName);
			}
		}
		catch (Exception e) {
			log.error("An exception has occured saving Alternate Address.", e);
			return false;
		}
		return true;
	}

	private boolean saveHomePhone(String employeeNumber, HomePhone newHomePhone, HomePhone homePhone, Boolean isAutoApprove, TraqsData traqs) {
		try {
			String tableName =  ReferenceDataService.HOME_PHONE_TABLE;
			//check if row is marked for deletion, set all values to empty. Check if new row or not to insert/update. Then check if auto approve to insert/update.
			Boolean deleteRequest = newHomePhone.equals(homePhone);
			Boolean isNewRow = demoDao.isNewRow(employeeNumber, tableName); 

			if (!deleteRequest) {
				if (isNewRow)
					demoDao.insertHomePhoneRequest(isAutoApprove, employeeNumber, newHomePhone, homePhone);
				else
					demoDao.updateHomePhoneRequest(isAutoApprove, employeeNumber, newHomePhone, homePhone);

				if (isAutoApprove) {
					demoDao.updateHomePhoneApprove(employeeNumber, newHomePhone);

					//cs20140430 Work on MD30/MD31
					if( demoDao.isTrsEmployee(employeeNumber) && traqs != null && traqs.getAutomate().equalsIgnoreCase("Y")) {
						updateMD30Records(demoDao.getEmployeeStaffId(employeeNumber), traqs, null, newHomePhone);
					}
				}
			}
			else {
				demoDao.deleteRequestIfExists(employeeNumber, tableName);
			}
		}
		catch (Exception e) {
			log.error("An exception has occured saving Home Phone.", e);
			return false;
		}
		return true;
	}

	private boolean saveWorkPhone(String employeeNumber, WorkPhone newWorkPhone, WorkPhone workPhone, Boolean isAutoApprove) {
		try {
			String tableName =  ReferenceDataService.WORK_PHONE_TABLE;
			//check if row is marked for deletion, set all values to empty. Check if new row or not to insert/update. Then check if auto approve to insert/update.
			Boolean deleteRequest = newWorkPhone.equals(workPhone);
			Boolean isNewRow = demoDao.isNewRow(employeeNumber, tableName); 

			if(!deleteRequest)
			{
				if(isNewRow)
					demoDao.insertWorkPhoneRequest(isAutoApprove, employeeNumber, newWorkPhone, workPhone);
				else
					demoDao.updateWorkPhoneRequest(isAutoApprove, employeeNumber, newWorkPhone, workPhone);

				if(isAutoApprove)
					demoDao.updateWorkPhoneApprove(employeeNumber, newWorkPhone);
			}
			else
			{
				demoDao.deleteRequestIfExists(employeeNumber, tableName);
			}
		}
		catch(Exception e) {
			log.error("An exception has occured saving Business Phone.", e);
			return false;
		}
		return true;
	}

	private boolean saveCellPhone(String employeeNumber, CellPhone newCellPhone, CellPhone cellPhone, Boolean isAutoApprove) {
		try {
			String tableName =  ReferenceDataService.CELL_PHONE_TABLE;
			//check if row is marked for deletion, set all values to empty. Check if new row or not to insert/update. Then check if auto approve to insert/update.
			Boolean deleteRequest = newCellPhone.equals(cellPhone);
			Boolean isNewRow = demoDao.isNewRow(employeeNumber, tableName); 

			if(!deleteRequest) {
				if(isNewRow)
					demoDao.insertCellPhoneRequest(isAutoApprove, employeeNumber, newCellPhone, cellPhone);
				else
					demoDao.updateCellPhoneRequest(isAutoApprove, employeeNumber, newCellPhone, cellPhone);

				if(isAutoApprove)
					demoDao.updateCellPhoneApprove(employeeNumber, newCellPhone);
			} else {
				demoDao.deleteRequestIfExists(employeeNumber, tableName);
			}
		} catch(Exception e) {
			log.error("An exception has occured saving Cell Phone.", e);
			return false;
		}
		return true;
	}

	private boolean saveRestrictionCodes(String employeeNumber, RestrictionCodes newRestrictionCodes, RestrictionCodes restrictionCodes, Boolean isAutoApprove)  {
		try {
			String tableName =  ReferenceDataService.RESTRICTION_CODE_TABLE;
			//check if row is marked for deletion, set all values to empty. Check if new row or not to insert/update. Then check if auto approve to insert/update.
			Boolean deleteRequest = newRestrictionCodes.equals(restrictionCodes);
			Boolean isNewRow = demoDao.isNewRow(employeeNumber, tableName); 

			if(!deleteRequest) {
				if(isNewRow)
					demoDao.insertRestrictionCodesRequest(isAutoApprove, employeeNumber, newRestrictionCodes, restrictionCodes);
				else
					demoDao.updateRestrictionCodesRequest(isAutoApprove, employeeNumber, newRestrictionCodes, restrictionCodes);

				if(isAutoApprove)
					demoDao.updateRestrictionCodesApprove(employeeNumber, newRestrictionCodes);
			} else {
				demoDao.deleteRequestIfExists(employeeNumber, tableName);
			}
		} catch(Exception e) {
			log.error("An exception has occured saving Restriction Codes.", e);
			return false;
		}
		return true;
	}

	private boolean saveMarital(String employeeNumber, Marital newMarital, Marital marital, Boolean isAutoApprove)  {
		try {
			String tableName =  ReferenceDataService.MARITAL_STATUS_TABLE;
			//check if row is marked for deletion, set all values to empty. Check if new row or not to insert/update. Then check if auto approve to insert/update.
			Boolean deleteRequest = newMarital.equals(marital);
			Boolean isNewRow = demoDao.isNewRow(employeeNumber, tableName); 

			if(!deleteRequest) {
				if(isNewRow)
					demoDao.insertMaritalRequest(isAutoApprove, employeeNumber, newMarital, marital);
				else
					demoDao.updateMaritalRequest(isAutoApprove, employeeNumber, newMarital, marital);

				if(isAutoApprove)
					demoDao.updateMaritalApprove(employeeNumber, newMarital);
			} else {
				demoDao.deleteRequestIfExists(employeeNumber, tableName);
			}
		} catch(Exception e) {
			log.error("An exception has occured saving Marital Status.", e);
			return false;
		}
		return true;
	}

	private boolean saveDriversLicense(String employeeNumber, DriversLicense newDriversLicense, DriversLicense driversLicense, Boolean isAutoApprove) {
		try {
			String tableName =  ReferenceDataService.DRIVERS_LICENSE_TABLE;
			//check if row is marked for deletion, set all values to empty. Check if new row or not to insert/update. Then check if auto approve to insert/update.
			Boolean deleteRequest = newDriversLicense.equals(driversLicense);
			Boolean isNewRow = demoDao.isNewRow(employeeNumber, tableName); 

			if(!deleteRequest) {
				if(isNewRow)
					demoDao.insertDriversLicenseRequest(isAutoApprove, employeeNumber, newDriversLicense, driversLicense);
				else
					demoDao.updateDriversLicenseRequest(isAutoApprove, employeeNumber, newDriversLicense, driversLicense);

				if(isAutoApprove)
					demoDao.updateDriversLicenseApprove(employeeNumber, newDriversLicense);
			} else {
				demoDao.deleteRequestIfExists(employeeNumber, tableName);
			}
		} catch(Exception e) {
			log.error("An exception has occured saving Drivers License.", e);
			return false;
		}
		return true;
	}

	private boolean saveEmergencyContact(String employeeNumber, EmergencyContact newEmergencyContact, EmergencyContact emergencyContact, Boolean isAutoApprove) {
		try {
			String tableName =  ReferenceDataService.EMERGENCY_CONTACT_TABLE;
			//check if row is marked for deletion, set all values to empty. Check if new row or not to insert/update. Then check if auto approve to insert/update.
			Boolean deleteRequest = newEmergencyContact.equals(emergencyContact);
			Boolean isNewRow = demoDao.isNewRow(employeeNumber, tableName); 

			if(!deleteRequest) {
				if(isNewRow)
					demoDao.insertEmergencyContactRequest(isAutoApprove, employeeNumber, newEmergencyContact, emergencyContact);
				else
					demoDao.updateEmergencyContactRequest(isAutoApprove, employeeNumber, newEmergencyContact, emergencyContact);

				if(isAutoApprove)
					demoDao.updateEmergencyContactApprove(employeeNumber, newEmergencyContact);
			} else  {
				demoDao.deleteRequestIfExists(employeeNumber, tableName);
			}
		} catch(Exception e) {
			log.error("An exception has occured saving Emergency Contact.", e);
			return false;
		}
		return true;
	}

	private boolean saveEmail(String employeeNumber, Email newEmail, Email email, Boolean isAutoApprove) {
		try {
			String tableName =  ReferenceDataService.EMAIL_TABLE;
			//check if row is marked for deletion, set all values to empty. Check if new row or not to insert/update. Then check if auto approve to insert/update.
			Boolean deleteRequest = newEmail.equals(email);
			Boolean isNewRow = demoDao.isNewRow(employeeNumber, tableName);

			if(!deleteRequest) {
				if(isNewRow)
					demoDao.insertEmailRequest(isAutoApprove, employeeNumber, newEmail, email);
				else
					demoDao.updateEmailRequest(isAutoApprove, employeeNumber, newEmail, email);

				if(isAutoApprove)
					demoDao.updateEmailApprove(employeeNumber, newEmail);
			} else 	{
				demoDao.deleteRequestIfExists(employeeNumber, tableName); 
			}
		} catch(Exception e) {
			log.error("An exception has occured saving Email.", e);
			return false;
		}
		return true;
	}

	@Override
	public Boolean sendEmail(LocalSharedAttributeMap employeeInfo, Demo demo, DemoInfo demoInfoPending, DemoInfo demoInfoCurrent) {	
		StringBuilder employeeMessageContents = new StringBuilder();
		StringBuilder employeeMessageAutoApprove = new StringBuilder();
		StringBuilder employeeMessageDocRequired = new StringBuilder();
		StringBuilder employeeMessageRequestReview = new StringBuilder();			//cs20140220 Add a section to list all changed items

		//Employee Undid any changes, or there are no changes
		if (demo.getDemoInfo().equals(demoInfoCurrent))
			return true;

		DemoInfoFields demoInfoChanges = getDemoInfoChanges(demo.getDemoInfo(), demoInfoPending);
		boolean hasDocChanges = false;
		boolean hasApprovChanges = false;
		boolean hasRequestReview = false;
		Boolean autoApprove;
		String fieldName;
		HashMap<String, String> groupApproverNumbers = demoDao.getApproverEmployeeNumbers();
		List<String> approversToEmail = new ArrayList<String>();

		autoApprove = demo.getAutoApproveName();
		if (demoInfoChanges.getNameTitle()) {
			fieldName = "Title\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.LEGAL_NAME_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getNameLast()) {
			fieldName = "Last Name\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.LEGAL_NAME_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getNameFirst()) {
			fieldName = "First Name\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.LEGAL_NAME_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getNameMiddle()) {
			fieldName = "Middle Name\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.LEGAL_NAME_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getNameGeneration()) {
			fieldName = "Generation\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.LEGAL_NAME_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}

		autoApprove = demo.getAutoApproveMailAddr();
		if(demoInfoChanges.getMailingAddress()) {
			fieldName = "Main Address Number\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.MAIL_ADDR_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if(demoInfoChanges.getMailingPoBox()) {
			fieldName = "Main Address Street/P.O. Box\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.MAIL_ADDR_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getMailingApt()) {
			fieldName = "Main Address Apt\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.MAIL_ADDR_TABLE);
				if( !approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getMailingCity()) {
			fieldName = "Main Address City\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.MAIL_ADDR_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getMailingState()) {
			fieldName = "Main Address State\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.MAIL_ADDR_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getMailingZip()) {
			fieldName = "Main Address Zip\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.MAIL_ADDR_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getMailingZip4()) {
			fieldName = "Main Address Zip+4\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.MAIL_ADDR_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}

		autoApprove = demo.getAutoApproveAltAddr();
		if (demoInfoChanges.getAlternateAddress()) {
			fieldName = "Alt Address Number\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getAlternatePoBox()) {
			fieldName = "Alt Address Street/P.O. Box\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getAlternateApt()) {
			fieldName = "Alt Address Apt\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getAlternateCity()) {
			fieldName = "Alt Address City\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getAlternateState()) {
			fieldName = "Alt Address State\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getAlternateZip()) {
			fieldName = "Alt Address Zip\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getAlternateZip4()) {
			fieldName = "Alt Address Zip+4\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.ALT_MAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}

		autoApprove = demo.getAutoApproveHomePhone();
		if (demoInfoChanges.getPhoneHomeArea()) {
			fieldName = "Home Phone Area Code\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.HOME_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getPhoneHomeNum()) {
			fieldName = "Home Phone Number\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.HOME_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}

		autoApprove = demo.getAutoApproveWorkPhone();
		if (demoInfoChanges.getPhoneBusArea()) {
			fieldName = "Business Phone Area Code\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.WORK_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getPhoneBusNum()) {
			fieldName = "Business Phone Number\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.WORK_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getPhoneBusExt()) {
			fieldName = "Business Extention\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.WORK_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}

		autoApprove = demo.getAutoApproveCellPhone();
		if (demoInfoChanges.getPhoneCellArea()) {
			fieldName = "Cell Phone Area Code\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.CELL_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getPhoneCellNum()) {
			fieldName = "Cell Phone Number\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.CELL_PHONE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}

		autoApprove = demo.getAutoApproveEmail();
		String newHomeEmail = "";
		String newWorkEmail = "";
		if (demoInfoChanges.getEmailHome()) {
			newHomeEmail = demo.getDemoInfo().getEmail().getHomeEmail();
			fieldName = "Home E-mail\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.EMAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getEmailWork()) {
			newWorkEmail = demo.getDemoInfo().getEmail().getWorkEmail();
			fieldName = "Work E-mail\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.EMAIL_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}

		autoApprove = demo.getAutoApproveMarital();
		if (demoInfoChanges.getMaritalLocal()) {
			fieldName = "Marital Status\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.MARITAL_STATUS_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}

		autoApprove = demo.getAutoApproveDriversLicense();
		if (demoInfoChanges.getDriversNum()) {
			fieldName = "Driver's License Number\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.DRIVERS_LICENSE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getDriversState()) {
			fieldName = "Driver's License State\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.DRIVERS_LICENSE_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}

		//Restriction Codes
		autoApprove = demo.getAutoApproveRestrictionCodes();
		if (demoInfoChanges.getRestrictionLocal()) {
			fieldName = "Local Restriction Code\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.RESTRICTION_CODE_TABLE);
				if (!approversToEmail.contains(approverNumber)){
					approversToEmail.add(approverNumber);
				}
			}
		}
		if (demoInfoChanges.getRestrictionPublic()) {
			fieldName = "Public Restriction Code\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.RESTRICTION_CODE_TABLE);
				if (!approversToEmail.contains(approverNumber)){
					approversToEmail.add(approverNumber);
				}
			}
		}

		autoApprove = demo.getAutoApproveEmergencyContact();
		if (demoInfoChanges.getEmergencyName()) {
			fieldName = "Emergency Contact Name\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.EMERGENCY_CONTACT_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getEmergencyAreaCode()) {
			fieldName = "Emergency Contact Area Code\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.EMERGENCY_CONTACT_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getEmergencyPhoneNum()) {
			fieldName = "Emergency Contact Phone Number\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.EMERGENCY_CONTACT_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getEmergencyPhoneExt()) {
			fieldName = "Emergency Contact Phone Extention\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.EMERGENCY_CONTACT_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getEmergencyRelationship()) {
			fieldName = "Emergency Contact Relationship\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.EMERGENCY_CONTACT_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}
		if (demoInfoChanges.getEmergencyNotes()) {
			fieldName = "Emergency Contact Notes\n";
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
				String approverNumber = groupApproverNumbers.get(ReferenceDataService.EMERGENCY_CONTACT_TABLE);
				if (!approversToEmail.contains(approverNumber) )
					approversToEmail.add(approverNumber);
			}
		}

		//*********************SEND USER EMAIL***********************************
		String userWorkEmail = employeeInfo.getString("currentUser_workEmail");
		String userHomeEmail = employeeInfo.getString("currentUser_homeEmail");
		String userFirstName = employeeInfo.getString("currentUser_firstName");
		String userMiddleName = employeeInfo.getString("currentUser_middleName");
		String userLastName = employeeInfo.getString("currentUser_lastName");
		String userGeneration = employeeInfo.getString("currentUser_generation");

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setSubject("A MESSAGE FROM SELF SERVICE");
		employeeMessageContents.append((userFirstName == null || userFirstName.trim().equals("")) ? "" : userFirstName.trim());
		employeeMessageContents.append((userMiddleName == null || userMiddleName.trim().equals("")) ? "" : " " + userMiddleName.trim());
		employeeMessageContents.append((userLastName == null || userLastName.trim().equals("")) ? "" : " " + userLastName.trim());
		employeeMessageContents.append((userGeneration == null || userGeneration.trim().equals("")) ? "" : " " + userGeneration.trim());

		employeeMessageContents.append(",\n\nYour request for changes to personnel data has been submitted. \n");

		if (hasDocChanges || hasApprovChanges || hasRequestReview) {
			if (hasApprovChanges) {
				employeeMessageContents.append("\nThe following data was automatically approved and updated:\n");
				employeeMessageContents.append(employeeMessageAutoApprove.toString());
			}
			if (hasDocChanges) {
				employeeMessageContents.append("\nThe following request(s) requires documentation be provided:\n");
				employeeMessageContents.append(employeeMessageDocRequired.toString());
			}
			if (hasRequestReview) {
				employeeMessageContents.append("\nThe following request(s) is pending to be reviewed:\n");
				employeeMessageContents.append(employeeMessageRequestReview.toString()); 
			}
		}

		String userEmail = (userWorkEmail == null || userWorkEmail.equals("")) ? userHomeEmail : userWorkEmail ;
		String userNewEmail = (newWorkEmail == null || newWorkEmail.equals("")) ? newHomeEmail : newWorkEmail;
		try {
			msg.setText(employeeMessageContents.toString());

			if (!userEmail.equals("")) {
				msg.setTo(userEmail);
				mailUtilService.sendMail(msg);
			}
			if(!userNewEmail.equals("")) {
				msg.setTo(userNewEmail);
				mailUtilService.sendMail(msg);
			}
		} catch(Exception ex) {
			log.error("An exception has occured with mailing the user.", ex);
			return false;
		}

		//*********************SEND APPROVER EMAIL***********************************
		StringBuilder approverEmailMessage = new StringBuilder();

		approverEmailMessage.append((userFirstName == null || userFirstName.trim().equals("")) ? "" : userFirstName.trim());
		approverEmailMessage.append((userMiddleName == null || userMiddleName.trim().equals("")) ? "" : " " + userMiddleName.trim());
		approverEmailMessage.append((userLastName == null || userLastName.trim().equals("")) ? "" : " " + userLastName.trim());
		approverEmailMessage.append((userGeneration == null || userGeneration.trim().equals("")) ? "" : " " + userGeneration.trim());

		approverEmailMessage.append(" has submitted a request to change personnel information.\n");
		approverEmailMessage.append("The request is ready for your approval. \n");
		approverEmailMessage.append("Login to HR to approve.");

		for(String approverNumber : approversToEmail) {
			try {
				User approver = demoDao.getApproverById(approverNumber);

				String approverWorkEmail = approver.getWorkEmail();
				String approverHomeEmail = approver.getHomeEmail();

				msg.setText(approver.getFirstName() + " " + approver.getLastName() + ",\n\n" + approverEmailMessage.toString());
				msg.setTo( (approverWorkEmail == null || approverWorkEmail.equals("")) ? approverHomeEmail : approverWorkEmail );
				mailUtilService.sendMail(msg);
			} catch(Exception ex) {
				log.error("An exception has occured with mailing the approver.", ex);
				return false;
			}
		}
		return true;
	}

	@Override
	public Boolean populateRequiredFields() {
		List <List <String>> demoInfoFieldsList = demoDao.getRequiredFields();

		DemoInfoFields requriedFields = new DemoInfoFields();
		DemoInfoFields docRequriedFields = new DemoInfoFields();

		for(List <String> listItem: demoInfoFieldsList) {
			//BEA_LGL_NAME
			if(ReferenceDataService.LEGAL_NAME_TABLE.equals(listItem.get(0))) {
				if("Title".equals(listItem.get(1))) {
					docRequriedFields.setNameTitle(listItem.get(2));
					requriedFields.setNameTitle(listItem.get(3));
				} else if("Last".equals(listItem.get(1))) {
					docRequriedFields.setNameLast(listItem.get(2));
					requriedFields.setNameLast(listItem.get(3));
				} else if("First".equals(listItem.get(1))) {
					docRequriedFields.setNameFirst(listItem.get(2));
					requriedFields.setNameFirst(listItem.get(3));
				} else if("Middle".equals(listItem.get(1))) {
					docRequriedFields.setNameMiddle(listItem.get(2));
					requriedFields.setNameMiddle(listItem.get(3));
				} else if("Generation".equals(listItem.get(1))) {
					docRequriedFields.setNameGeneration(listItem.get(2));
					requriedFields.setNameGeneration(listItem.get(3));
				}
			}

			//BEA_DRVS_LIC
			else if(ReferenceDataService.DRIVERS_LICENSE_TABLE.equals(listItem.get(0))) {
				if("Number".equals(listItem.get(1))) {
					docRequriedFields.setDriversNum(listItem.get(2));
					requriedFields.setDriversNum(listItem.get(3));
				} else if("State".equals(listItem.get(1))) {
					docRequriedFields.setDriversState(listItem.get(2));
					requriedFields.setDriversState(listItem.get(3));
				}
			}

			//BEA_RESTRICT
			else if(ReferenceDataService.RESTRICTION_CODE_TABLE.equals(listItem.get(0))) {
				if("Local".equals(listItem.get(1))) {
					docRequriedFields.setRestrictionLocal(listItem.get(2));
					requriedFields.setRestrictionLocal(listItem.get(3));
				} else if("Public".equals(listItem.get(1))) {
					docRequriedFields.setRestrictionPublic(listItem.get(2));
					requriedFields.setRestrictionPublic(listItem.get(3));
				}
			}

			//BEA_MRTL_STAT
			else if(ReferenceDataService.MARITAL_STATUS_TABLE.equals(listItem.get(0))) {
				if("Status".equals(listItem.get(1))) {
					docRequriedFields.setMaritalLocal(listItem.get(2));
					requriedFields.setMaritalLocal(listItem.get(3));
				}
			}

			//BEA_EMER_CONTACT
			else if(ReferenceDataService.EMERGENCY_CONTACT_TABLE.equals(listItem.get(0))) {
				if("Name".equals(listItem.get(1))) {
					docRequriedFields.setEmergencyName(listItem.get(2));
					requriedFields.setEmergencyName(listItem.get(3));
				} else if("Area Code".equals(listItem.get(1))) {
					docRequriedFields.setEmergencyAreaCode(listItem.get(2));
					requriedFields.setEmergencyAreaCode(listItem.get(3));
				} else if("Phone Number".equals(listItem.get(1))) {
					docRequriedFields.setEmergencyPhoneNum(listItem.get(2));
					requriedFields.setEmergencyPhoneNum(listItem.get(3));
				} else if("Ext".equals(listItem.get(1))) {
					docRequriedFields.setEmergencyPhoneExt(listItem.get(2));
					requriedFields.setEmergencyPhoneExt(listItem.get(3));
				} else if("Relationship".equals(listItem.get(1))) {
					docRequriedFields.setEmergencyRelationship(listItem.get(2));
					requriedFields.setEmergencyRelationship(listItem.get(3));
				} else if("Emergency Notes".equals(listItem.get(1))) {
					docRequriedFields.setEmergencyNotes(listItem.get(2));
					requriedFields.setEmergencyNotes(listItem.get(3));
				}
			}

			//BEA_MAIL_ADDR
			else if(ReferenceDataService.MAIL_ADDR_TABLE.equals(listItem.get(0))) {
				if("Number".equals(listItem.get(1))) {
					docRequriedFields.setMailingAddress(listItem.get(2));
					requriedFields.setMailingAddress(listItem.get(3));
				} else if("Street/P.O. Box".equals(listItem.get(1))){
					docRequriedFields.setMailingPoBox(listItem.get(2));
					requriedFields.setMailingPoBox(listItem.get(3));
				} else if("Apt".equals(listItem.get(1))){
					docRequriedFields.setMailingApt(listItem.get(2));
					requriedFields.setMailingApt(listItem.get(3));
				} else if("City".equals(listItem.get(1))) {
					docRequriedFields.setMailingCity(listItem.get(2));
					requriedFields.setMailingCity(listItem.get(3));
				} else if("State".equals(listItem.get(1))) {
					docRequriedFields.setMailingState(listItem.get(2));
					requriedFields.setMailingState(listItem.get(3));
				} else if("Zip".equals(listItem.get(1))) {
					docRequriedFields.setMailingZip(listItem.get(2));
					requriedFields.setMailingZip(listItem.get(3));
				} else if("Zip+4".equals(listItem.get(1))) {
					docRequriedFields.setMailingZip4(listItem.get(2));
					requriedFields.setMailingZip4(listItem.get(3));
				}
			}
			//BEA_ALT_MAIL_ADDR
			else if(ReferenceDataService.ALT_MAIL_TABLE.equals(listItem.get(0))) {
				if("Number".equals(listItem.get(1))) {
					docRequriedFields.setAlternateAddress(listItem.get(2));
					requriedFields.setAlternateAddress(listItem.get(3));
				} else if("Street/P.O. Box".equals(listItem.get(1))) {
					docRequriedFields.setAlternatePoBox(listItem.get(2));
					requriedFields.setAlternatePoBox(listItem.get(3));
				} else if("Apt".equals(listItem.get(1))) {
					docRequriedFields.setAlternateApt(listItem.get(2));
					requriedFields.setAlternateApt(listItem.get(3));
				} else if("City".equals(listItem.get(1))) {
					docRequriedFields.setAlternateCity(listItem.get(2));
					requriedFields.setAlternateCity(listItem.get(3));
				} else if("State".equals(listItem.get(1))) {
					docRequriedFields.setAlternateState(listItem.get(2));
					requriedFields.setAlternateState(listItem.get(3));
				} else if("Zip".equals(listItem.get(1))) {
					docRequriedFields.setAlternateZip(listItem.get(2));
					requriedFields.setAlternateZip(listItem.get(3));
				} else if("Zip+4".equals(listItem.get(1))) {
					docRequriedFields.setAlternateZip4(listItem.get(2));
					requriedFields.setAlternateZip4(listItem.get(3));
				}
			}

			//BEA_HM_PHONE
			else if(ReferenceDataService.HOME_PHONE_TABLE.equals(listItem.get(0))) {
				if("Area Code".equals(listItem.get(1))) {
					docRequriedFields.setPhoneHomeArea(listItem.get(2));
					requriedFields.setPhoneHomeArea(listItem.get(3));
				} else if("Phone Number".equals(listItem.get(1))) {
					docRequriedFields.setPhoneHomeNum(listItem.get(2));
					requriedFields.setPhoneHomeNum(listItem.get(3));
				}
			}

			//BEA_BUS_PHONE
			else if(ReferenceDataService.WORK_PHONE_TABLE.equals(listItem.get(0))) {
				if("Area Code".equals(listItem.get(1))) {
					docRequriedFields.setPhoneBusArea(listItem.get(2));
					requriedFields.setPhoneBusArea(listItem.get(3));
				} else if("Phone Number".equals(listItem.get(1))) {
					docRequriedFields.setPhoneBusNum(listItem.get(2));
					requriedFields.setPhoneBusNum(listItem.get(3));
				} else if("Ext".equals(listItem.get(1))) {
					docRequriedFields.setPhoneBusExt(listItem.get(2));
					requriedFields.setPhoneBusExt(listItem.get(3));
				}
			}

			//BEA_CELL_PHONE
			else if(ReferenceDataService.CELL_PHONE_TABLE.equals(listItem.get(0))) {
				if("Area Code".equals(listItem.get(1))) {
					docRequriedFields.setPhoneCellArea(listItem.get(2));
					requriedFields.setPhoneCellArea(listItem.get(3));
				} else if("Phone Number".equals(listItem.get(1))) {
					docRequriedFields.setPhoneCellNum(listItem.get(2));
					requriedFields.setPhoneCellNum(listItem.get(3));
				}
			}

			//BEA_EMAIL
			else if(ReferenceDataService.EMAIL_TABLE.equals(listItem.get(0))) {
				if("Work".equals(listItem.get(1))) {
					docRequriedFields.setEmailWork(listItem.get(2));
					requriedFields.setEmailWork(listItem.get(3));
				} else if("Home".equals(listItem.get(1))) {
					docRequriedFields.setEmailHome(listItem.get(2));
					requriedFields.setEmailHome(listItem.get(3));
				}
			}
		}

		this.docRequiredFields = docRequriedFields;
		this.requiredFields = requriedFields;
		return true;
	}

	@Override
	public DemoInfoFields getRequiredFields() {
		return requiredFields;
	}

	@Override
	public DemoInfoFields getDocRequiredFields() {
		return docRequiredFields;
	}

	@Override
	public Demo setOptions(Demo demo) {
		demo.setFieldDisplayOptionName(demoDao.getNameFieldDisplay());
		demo.setFieldDisplayOptionMailAddr(demoDao.getMailAddrFieldDisplay());
		demo.setFieldDisplayOptionAltAddr(demoDao.getAltAddrFieldDisplay());
		demo.setFieldDisplayOptionHomePhone(demoDao.getHomePhoneFieldDisplay());
		demo.setFieldDisplayOptionWorkPhone(demoDao.getWorkPhoneFieldDisplay());
		demo.setFieldDisplayOptionCellPhone(demoDao.getCellPhoneFieldDisplay());
		demo.setFieldDisplayOptionEmail(demoDao.getEmailFieldDisplay());
		demo.setFieldDisplayOptionRestrictionCodes(demoDao.getRestrictionCodesFieldDisplay());
		demo.setFieldDisplayOptionMarital(demoDao.getMaritalFieldDisplay());
		demo.setFieldDisplayOptionDriversLicense(demoDao.getDriversLicenseFieldDisplay());
		demo.setFieldDisplayOptionEmergencyContact(demoDao.getEmergencyContactFieldDisplay());

		demo.setAutoApproveName(demoDao.getAutoApproveName());
		demo.setAutoApproveMailAddr(demoDao.getAutoApproveMailAddr());
		demo.setAutoApproveAltAddr(demoDao.getAutoApproveAltAddr());
		demo.setAutoApproveHomePhone(demoDao.getAutoApproveHomePhone());
		demo.setAutoApproveWorkPhone(demoDao.getAutoApproveWorkPhone());
		demo.setAutoApproveCellPhone(demoDao.getAutoApproveCellPhone());
		demo.setAutoApproveEmail(demoDao.getAutoApproveEmail());
		demo.setAutoApproveRestrictionCodes(demoDao.getAutoApproveRestrictionCodes());
		demo.setAutoApproveMarital(demoDao.getAutoApproveMarital());
		demo.setAutoApproveDriversLicense(demoDao.getAutoApproveDriversLicense());
		demo.setAutoApproveEmergencyContact(demoDao.getAutoApproveEmergencyContact());
		return demo;
	}

	@Override
	public DemoInfoFields getDemoInfoChanges(DemoInfo demoInfo, DemoInfo demoInfoPending) {
		DemoInfoFields demoInfoChanges = new DemoInfoFields();

		demoInfoChanges.setNameTitle(!demoInfo.getName().getTitle().equals(demoInfoPending.getName().getTitle()));
		demoInfoChanges.setNameFirst(!demoInfo.getName().getFirstName().equals(demoInfoPending.getName().getFirstName()));
		demoInfoChanges.setNameMiddle(!demoInfo.getName().getMiddleName().equals(demoInfoPending.getName().getMiddleName()));
		demoInfoChanges.setNameLast(!demoInfo.getName().getLastName().equals(demoInfoPending.getName().getLastName()));
		demoInfoChanges.setNameGeneration(!demoInfo.getName().getGeneration().equals(demoInfoPending.getName().getGeneration()));

		demoInfoChanges.setMaritalLocal(!demoInfo.getMarital().getMaritalStatusCode().equals(demoInfoPending.getMarital().getMaritalStatusCode()));

		demoInfoChanges.setDriversNum(!demoInfo.getDriversLicense().getNumber().equals(demoInfoPending.getDriversLicense().getNumber()));
		demoInfoChanges.setDriversState(!demoInfo.getDriversLicense().getStateCode().equals(demoInfoPending.getDriversLicense().getStateCode()));

		demoInfoChanges.setRestrictionLocal(!demoInfo.getRestrictionCodes().getLocalRestrictionCode().equals(demoInfoPending.getRestrictionCodes().getLocalRestrictionCode()));
		demoInfoChanges.setRestrictionPublic(!demoInfo.getRestrictionCodes().getPublicRestrictionCode().equals(demoInfoPending.getRestrictionCodes().getPublicRestrictionCode()));

		demoInfoChanges.setEmergencyName(!demoInfo.getEmergencyContact().getName().equals(demoInfoPending.getEmergencyContact().getName()));
		demoInfoChanges.setEmergencyAreaCode(!demoInfo.getEmergencyContact().getAreaCode().equals(demoInfoPending.getEmergencyContact().getAreaCode()));
		demoInfoChanges.setEmergencyPhoneNum(!demoInfo.getEmergencyContact().getPhoneNumber().equals(demoInfoPending.getEmergencyContact().getPhoneNumber()));
		demoInfoChanges.setEmergencyPhoneExt(!demoInfo.getEmergencyContact().getExtention().equals(demoInfoPending.getEmergencyContact().getExtention()));
		demoInfoChanges.setEmergencyRelationship(!demoInfo.getEmergencyContact().getRelationship().equals(demoInfoPending.getEmergencyContact().getRelationship()));
		demoInfoChanges.setEmergencyNotes(!demoInfo.getEmergencyContact().getEmergencyNotes().equals(demoInfoPending.getEmergencyContact().getEmergencyNotes()));

		demoInfoChanges.setMailingAddress(!demoInfo.getMailAddr().getNumber().equals(demoInfoPending.getMailAddr().getNumber()));
		demoInfoChanges.setMailingPoBox(!demoInfo.getMailAddr().getStreet().equals(demoInfoPending.getMailAddr().getStreet()));
		demoInfoChanges.setMailingApt(!demoInfo.getMailAddr().getApartment().equals(demoInfoPending.getMailAddr().getApartment()));
		demoInfoChanges.setMailingCity(!demoInfo.getMailAddr().getCity().equals(demoInfoPending.getMailAddr().getCity()));
		demoInfoChanges.setMailingState(!demoInfo.getMailAddr().getStateCode().equals(demoInfoPending.getMailAddr().getStateCode()));
		demoInfoChanges.setMailingZip(!demoInfo.getMailAddr().getZip().equals(demoInfoPending.getMailAddr().getZip()));
		demoInfoChanges.setMailingZip4(!demoInfo.getMailAddr().getZipPlusFour().equals(demoInfoPending.getMailAddr().getZipPlusFour()));

		demoInfoChanges.setAlternateAddress(!demoInfo.getAltAddr().getNumber().equals(demoInfoPending.getAltAddr().getNumber()));
		demoInfoChanges.setAlternatePoBox(!demoInfo.getAltAddr().getStreet().equals(demoInfoPending.getAltAddr().getStreet()));
		demoInfoChanges.setAlternateApt(!demoInfo.getAltAddr().getApartment().equals(demoInfoPending.getAltAddr().getApartment()));
		demoInfoChanges.setAlternateCity(!demoInfo.getAltAddr().getCity().equals(demoInfoPending.getAltAddr().getCity()));
		demoInfoChanges.setAlternateState(!demoInfo.getAltAddr().getStateCode().equals(demoInfoPending.getAltAddr().getStateCode()));
		demoInfoChanges.setAlternateZip(!demoInfo.getAltAddr().getZip().equals(demoInfoPending.getAltAddr().getZip()));
		demoInfoChanges.setAlternateZip4(!demoInfo.getAltAddr().getZipPlusFour().equals(demoInfoPending.getAltAddr().getZipPlusFour()));

		demoInfoChanges.setPhoneHomeArea(!demoInfo.getHomePhone().getAreaCode().equals(demoInfoPending.getHomePhone().getAreaCode()));
		demoInfoChanges.setPhoneHomeNum(!demoInfo.getHomePhone().getPhoneNumber().equals(demoInfoPending.getHomePhone().getPhoneNumber()));

		demoInfoChanges.setPhoneBusArea(!demoInfo.getWorkPhone().getAreaCode().equals(demoInfoPending.getWorkPhone().getAreaCode()));
		demoInfoChanges.setPhoneBusNum(!demoInfo.getWorkPhone().getPhoneNumber().equals(demoInfoPending.getWorkPhone().getPhoneNumber()));
		demoInfoChanges.setPhoneBusExt(!demoInfo.getWorkPhone().getExtention().equals(demoInfoPending.getWorkPhone().getExtention()));

		demoInfoChanges.setPhoneCellArea(!demoInfo.getCellPhone().getAreaCode().equals(demoInfoPending.getCellPhone().getAreaCode()));
		demoInfoChanges.setPhoneCellNum(!demoInfo.getCellPhone().getPhoneNumber().equals(demoInfoPending.getCellPhone().getPhoneNumber()));

		demoInfoChanges.setEmailWork(!demoInfo.getEmail().getWorkEmail().equals(demoInfoPending.getEmail().getWorkEmail()));
		demoInfoChanges.setEmailHome(!demoInfo.getEmail().getHomeEmail().equals(demoInfoPending.getEmail().getHomeEmail()));

		return demoInfoChanges;
	}

	@Override
	public DemoInfo getDemoInfoPending(DemoInfo demoInfo) {
		String employeeNumber = demoInfo.getEmployeeNumber();
		DemoInfo pending = demoDao.getPendingDemoInfo(employeeNumber, demoInfo.getSex(), demoInfo.getDob(), demoInfo.getStaffId());

		if(pending.getName() == null) {
			pending.setName(demoInfo.getName().clone());
		}
		if(pending.getMailAddr() == null) {
			pending.setMailAddr(demoInfo.getMailAddr().clone());
		}
		if(pending.getAltAddr() == null) {
			pending.setAltAddr(demoInfo.getAltAddr().clone());
		}
		if(pending.getHomePhone() == null) {
			pending.setHomePhone(demoInfo.getHomePhone().clone());
		}
		if(pending.getWorkPhone() == null) {
			pending.setWorkPhone(demoInfo.getWorkPhone().clone());
		}
		if(pending.getCellPhone() == null) {
			pending.setCellPhone(demoInfo.getCellPhone().clone());
		}
		if(pending.getEmail() == null) {
			pending.setEmail(demoInfo.getEmail().clone());
		}
		if(pending.getRestrictionCodes() == null) {
			pending.setRestrictionCodes(demoInfo.getRestrictionCodes().clone());
		}
		if(pending.getMarital() == null) {
			pending.setMarital(demoInfo.getMarital().clone());
		}
		if(pending.getDriversLicense() == null) {
			pending.setDriversLicense(demoInfo.getDriversLicense().clone());
		}
		if(pending.getEmergencyContact() == null) {
			pending.setEmergencyContact(demoInfo.getEmergencyContact().clone());
		}

		return pending;
	}

	@Override
	public Email getPendingEmailRequests(String employeeNumber) {
		return demoDao.getPendingEmail(employeeNumber);
	}

	@Override
	public Email getCurrentEmail(String employeeNumber) {
		return demoDao.getCurrentEmail(employeeNumber);
	}

	@Override
	public String getMessage() {
		Options o = optionsDao.getOptions();
		return o.getMessageSelfServiceDemographic();
	}

	public IMailUtilService getMailUtilService() {
		return mailUtilService;
	}

	public void setMailUtilService(IMailUtilService mailUtilService) {
		this.mailUtilService = mailUtilService;
	}
	
	private boolean updateTEAMLists(List<Ed20Command> ed20List, List<Ed25Command> ed25List) {
		boolean lb_chgs = false;
		if (null != ed20List && 
			ed20List.size() > 0) {
			for (Ed20Command row : ed20List) {
				if ("true".equals(modFldsBoolean.get("name_l"))) {
					row.setNameL(modifiedFields.get("name_l"));
				}
				if ("true".equals(modFldsBoolean.get("name_f"))) {
					row.setNameF(modifiedFields.get("name_f"));
				}
				if ("true".equals(modFldsBoolean.get("name_m"))) {
					row.setNameM(modifiedFields.get("name_m"));
				}
				if ("true".equals(modFldsBoolean.get("name_gen"))) {
					row.setNameGen(modifiedFields.get("name_gen"));
				}
				if ("true".equals(modFldsBoolean.get("addr_nbr"))) {
					row.setAddrNbr(modifiedFields.get("addr_nbr"));
				}
				if ("true".equals(modFldsBoolean.get("addr_str"))) {
					row.setAddrStr(modifiedFields.get("addr_str"));
				}
				if ("true".equals(modFldsBoolean.get("addr_apt"))) {
					row.setAddrApt(modifiedFields.get("addr_apt"));
				}
				if ("true".equals(modFldsBoolean.get("addr_city"))) {
					row.setAddrCity(modifiedFields.get("addr_city"));
				}
				if ("true".equals(modFldsBoolean.get("addr_st"))) {
					row.setAddrSt(modifiedFields.get("addr_st"));
				}
				if ("true".equals(modFldsBoolean.get("addr_zip"))) {
					row.setAddrZip(modifiedFields.get("addr_zip"));
				}
				if ("true".equals(modFldsBoolean.get("addr_zip4"))) {
					row.setAddrZip4(modifiedFields.get("addr_zip4"));
				}
				if ("true".equals(modFldsBoolean.get("email"))) {
					row.setEmail(modifiedFields.get("email"));
				}
				if ("true".equals(modFldsBoolean.get("phone_area"))) {
					row.setPhoneArea(modifiedFields.get("phone_area"));
				}
				if ("true".equals(modFldsBoolean.get("phone_nbr"))) {
					row.setPhoneNbr(modifiedFields.get("phone_nbr"));
				}
			}
		}
		if (null != ed25List && 
			ed25List.size() > 0) {
			for (Ed25Command row : ed25List) {
				if ("true".equals(modFldsBoolean.get("new_name_l"))) {
					row.setNewNameL(modifiedFields.get("new_name_l"));
				}
				if ("true".equals(modFldsBoolean.get("new_name_f"))) {
					row.setNewNameF(modifiedFields.get("new_name_f"));
				}
				if ("true".equals(modFldsBoolean.get("new_name_m"))) {
					row.setNewNameM(modifiedFields.get("new_name_m"));
				}
				if ("true".equals(modFldsBoolean.get("new_name_gen"))) {
					row.setNewNameGen(modifiedFields.get("new_name_gen"));
				}
				if ("true".equals(modFldsBoolean.get("new_addr_nbr"))) {
					row.setNewAddrNbr(modifiedFields.get("new_addr_nbr"));
				}
				if ("true".equals(modFldsBoolean.get("new_addr_str"))) {
					row.setNewAddrStr(modifiedFields.get("new_addr_str"));
				}
				if ("true".equals(modFldsBoolean.get("new_addr_apt"))) {
					row.setNewAddrApt(modifiedFields.get("new_addr_apt"));
				}
				if ("true".equals(modFldsBoolean.get("new_addr_city"))) {
					row.setNewAddrCity(modifiedFields.get("new_addr_city"));
				}
				if ("true".equals(modFldsBoolean.get("new_addr_st"))) {
					row.setNewAddrSt(modifiedFields.get("new_addr_st"));
				}
				if ("true".equals(modFldsBoolean.get("new_addr_zip"))) {
					row.setNewAddrZip(modifiedFields.get("new_addr_zip"));
				}
				if ("true".equals(modFldsBoolean.get("new_addr_zip4"))) {
					row.setNewAddrZip4(modifiedFields.get("new_addr_zip4"));
				}
				if ("true".equals(modFldsBoolean.get("new_email"))) {
					row.setNewEmail(modifiedFields.get("new_email"));
				}
				if ("true".equals(modFldsBoolean.get("new_phone_area"))) {
					row.setNewPhoneArea(modifiedFields.get("new_phone_area"));
				}
				if ("true".equals(modFldsBoolean.get("new_phone_nbr"))) {
					row.setNewPhoneNbr(modifiedFields.get("new_phone_nbr"));
				}
			}
		}
		
		return lb_chgs;
	}
	
	private void insertEd25List(List<Ed25Command> ed25List, String rptYr, String rptMon) {
		Ed25Command ed25Rec = new Ed25Command();
		ed25Rec.setNewRow(true);
		ed25Rec.setRptMon(rptMon);
		ed25Rec.setRptYr(rptYr);
		ed25Rec.setEmpNbr(modifiedFields.get("empNbr"));
		ed25Rec.setOrigStaffID(modifiedFields.get("staff_id"));
		ed25Rec.setOrigDOB(modifiedFields.get("dob"));
		ed25Rec.setOrigSex(modifiedFields.get("sex"));
		ed25Rec.setOrigNameL(modifiedFields.get("orig_name_l"));
		ed25Rec.setOrigNameF(modifiedFields.get("orig_name_f"));
		ed25Rec.setOrigNameM(modifiedFields.get("orig_name_m"));
		ed25Rec.setOrigNameGen(modifiedFields.get("orig_name_gen"));
		if ("true".equals(modFldsBoolean.get("new_name_l"))) {
			ed25Rec.setNewNameL(modifiedFields.get("new_name_l"));
		}
		if ("true".equals(modFldsBoolean.get("new_name_f"))) {
			ed25Rec.setNewNameF(modifiedFields.get("new_name_f"));
		}
		if ("true".equals(modFldsBoolean.get("new_name_m"))) {
			ed25Rec.setNewNameM(modifiedFields.get("new_name_m"));
		}
		if ("true".equals(modFldsBoolean.get("new_name_gen"))) {
			ed25Rec.setNewNameGen(modifiedFields.get("new_name_gen"));
		}
		if ("true".equals(modFldsBoolean.get("new_addr_nbr"))) {
			ed25Rec.setNewAddrNbr(modifiedFields.get("new_addr_nbr"));
		}
		if ("true".equals(modFldsBoolean.get("new_addr_str"))) {
			ed25Rec.setNewAddrStr(modifiedFields.get("new_addr_str"));
		}
		if ("true".equals(modFldsBoolean.get("new_addr_apt"))) {
			ed25Rec.setNewAddrApt(modifiedFields.get("new_addr_apt"));
		}
		if ("true".equals(modFldsBoolean.get("new_addr_city"))) {
			ed25Rec.setNewAddrCity(modifiedFields.get("new_addr_city"));
		}
		if ("true".equals(modFldsBoolean.get("new_addr_st"))) {
			ed25Rec.setNewAddrSt(modifiedFields.get("new_addr_st"));
		}
		if ("true".equals(modFldsBoolean.get("new_addr_zip"))) {
			ed25Rec.setNewAddrZip(modifiedFields.get("new_addr_zip"));
		}
		if ("true".equals(modFldsBoolean.get("new_addr_zip4"))) {
			ed25Rec.setNewAddrZip4(modifiedFields.get("new_addr_zip4"));
		}
		if ("true".equals(modFldsBoolean.get("new_email"))) {
			ed25Rec.setNewEmail(modifiedFields.get("new_email"));
		}
		if ("true".equals(modFldsBoolean.get("new_phone_area"))) {
			ed25Rec.setNewPhoneArea(modifiedFields.get("new_phone_area"));
		}
		if ("true".equals(modFldsBoolean.get("new_phone_nbr"))) {
			ed25Rec.setNewPhoneNbr(modifiedFields.get("new_phone_nbr"));
		}
		
		ed25List.add(ed25Rec);
		
	}
}