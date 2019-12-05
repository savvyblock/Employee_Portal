package com.esc20.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.ReferenceDao;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.DemoInfoFields;
import com.esc20.util.StringUtil;

@Service
public class ReferenceService {
    @Autowired
    private ReferenceDao referenceDao;
	
	private static List <Code> titles = new ArrayList<Code>(Arrays.asList(new Code("", ""),
			  new Code("1", "Mr."), 
			  new Code("2", "Mrs."), 
			  new Code("3", "Miss"), 
			  new Code("4", "Ms.")));
    
	
	public String LEGAL_NAME_TABLE = "BEA_LGL_NAME";
	public String MAIL_ADDR_TABLE = "BEA_MAIL_ADDR";
	public String ALT_MAIL_TABLE = "BEA_ALT_MAIL_ADDR";
	public String HOME_PHONE_TABLE = "BEA_HM_PHONE";
	public String WORK_PHONE_TABLE = "BEA_BUS_PHONE";
	public String CELL_PHONE_TABLE = "BEA_CELL_PHONE";
	public String EMAIL_TABLE = "BEA_EMAIL";
	public String RESTRICTION_CODE_TABLE = "BEA_RESTRICT";
	public String MARITAL_STATUS_TABLE = "BEA_MRTL_STAT";
	public String DRIVERS_LICENSE_TABLE = "BEA_DRVS_LIC";
	public String EMERGENCY_CONTACT_TABLE = "BEA_EMER_CONTACT";
	
	public List<Code> getMaritalActualStatuses() {
		return referenceDao.getMaritalActualStatuses();
	}
	
	public List<Code> getW4MaritalActualStatuses() {
		return referenceDao.getW4MaritalActualStatuses();
	}
	
	public List<Code> getMaritalTaxStatuses() {
		return referenceDao.getMaritalStatuses();
	}

	public List<Code> getGenerations() {
		return referenceDao.getGenerations();
	}
	
	public List<Code> getTitles() {
		return titles;
	}
	
	public List<Code> getStates() {
		return referenceDao.getStates();
	}
	
	public List<Code> getRestrictions() {
		return referenceDao.getRestrictions();
	}
	
	public List<Code> getAbsRsns(){
		return referenceDao.getAbsRsns();
	}
	
	public List<Code> getLeaveTypes(){
		return referenceDao.getLeaveTypes();
	}

	public List<Code> getLeaveStatus() {
		List<Code> codes  = referenceDao.getLeaveStatus();
		Code notProcessed = new Code();
		notProcessed.setCode("N");
		notProcessed.setSubCode("");
		notProcessed.setDescription("Not Processed");
		codes.add(notProcessed);
		return codes;
	}
	
	public List<Code> getPayFreq() {
		//return referenceDao.getPayFreq();
		
		List<Code> codes  = referenceDao.getPayFreq();
		Code notProcessed = new Code();
		notProcessed.setCode("0");
		notProcessed.setSubCode("");
		notProcessed.setDescription("");
		codes.add(notProcessed);
		return codes;
	}
	
	public List<Code> getPayrollFrequencies(String empNbr) {
		List<Code> payrollFrequencies = referenceDao.getPayrollFrequencies(empNbr);
		
		return payrollFrequencies;
	}

	public List<Code> getDdAccountTypes() {
		return referenceDao.getDdAccountTypes();
	}
	
	public DemoInfoFields populateDocRequiredFields() {
		List<List<String>> demoInfoFieldsList = referenceDao.getDemoRequiredFields();
		DemoInfoFields docRequriedFields = new DemoInfoFields();
		for(List <String> listItem: demoInfoFieldsList) {
			
			//BEA_LGL_NAME
			if(this.LEGAL_NAME_TABLE.equals(listItem.get(0))) {
				if("Title".equals(listItem.get(1))) {
					docRequriedFields.setNameTitle(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Last".equals(listItem.get(1))) {
					docRequriedFields.setNameLast(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("First".equals(listItem.get(1))) {
					docRequriedFields.setNameFirst(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Middle".equals(listItem.get(1))) {
					docRequriedFields.setNameMiddle(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Generation".equals(listItem.get(1))) {
					docRequriedFields.setNameGeneration(StringUtil.convertToBoolean(listItem.get(2)));
				}
			}
			//BEA_DRVS_LIC
			else if(this.DRIVERS_LICENSE_TABLE.equals(listItem.get(0))) {
				if("Number".equals(listItem.get(1))) {
					docRequriedFields.setDriversNum(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("State".equals(listItem.get(1))) {
					docRequriedFields.setDriversState(StringUtil.convertToBoolean(listItem.get(2)));
				}
			}
			
			//BEA_RESTRICT
			else if(this.RESTRICTION_CODE_TABLE.equals(listItem.get(0))) {
				if("Local".equals(listItem.get(1))) {
					docRequriedFields.setRestrictionLocal(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Public".equals(listItem.get(1))) {
					docRequriedFields.setRestrictionPublic(StringUtil.convertToBoolean(listItem.get(2)));
				}
			}

			//BEA_MRTL_STAT
			else if(this.MARITAL_STATUS_TABLE.equals(listItem.get(0))) {
				if("Status".equals(listItem.get(1))) {
					docRequriedFields.setMaritalLocal(StringUtil.convertToBoolean(listItem.get(2)));
				}
			}

			//BEA_EMER_CONTACT
			else if(this.EMERGENCY_CONTACT_TABLE.equals(listItem.get(0))) {
				if("Name".equals(listItem.get(1))) {
					docRequriedFields.setEmergencyName(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Area Code".equals(listItem.get(1))) {
					docRequriedFields.setEmergencyAreaCode(StringUtil.convertToBoolean(listItem.get(2)));			
				} else if("Phone Number".equals(listItem.get(1))) {
					docRequriedFields.setEmergencyPhoneNum(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Ext".equals(listItem.get(1))) {
					docRequriedFields.setEmergencyPhoneExt(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Relationship".equals(listItem.get(1))) {
					docRequriedFields.setEmergencyRelationship(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Emergency Notes".equals(listItem.get(1))) {
					docRequriedFields.setEmergencyNotes(StringUtil.convertToBoolean(listItem.get(2)));
				}
			}

			//BEA_MAIL_ADDR
			else if(this.MAIL_ADDR_TABLE.equals(listItem.get(0))) {
				if("Number".equals(listItem.get(1))) {
					docRequriedFields.setMailingAddress(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Street/P.O. Box".equals(listItem.get(1))){
					docRequriedFields.setMailingPoBox(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Apt".equals(listItem.get(1))){
					docRequriedFields.setMailingApt(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("City".equals(listItem.get(1))) {
					docRequriedFields.setMailingCity(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("State".equals(listItem.get(1))) {
					docRequriedFields.setMailingState(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Zip".equals(listItem.get(1))) {
					docRequriedFields.setMailingZip(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Zip+4".equals(listItem.get(1))) {
					docRequriedFields.setMailingZip4(StringUtil.convertToBoolean(listItem.get(2)));
				}
			}
			//BEA_ALT_MAIL_ADDR
			else if(this.ALT_MAIL_TABLE.equals(listItem.get(0))) {
				if("Number".equals(listItem.get(1))) {
					docRequriedFields.setAlternateAddress(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Street/P.O. Box".equals(listItem.get(1))) {
					docRequriedFields.setAlternatePoBox(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Apt".equals(listItem.get(1))) {
					docRequriedFields.setAlternateApt(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("City".equals(listItem.get(1))) {
					docRequriedFields.setAlternateCity(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("State".equals(listItem.get(1))) {
					docRequriedFields.setAlternateState(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Zip".equals(listItem.get(1))) {
					docRequriedFields.setAlternateZip(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Zip+4".equals(listItem.get(1))) {
					docRequriedFields.setAlternateZip4(StringUtil.convertToBoolean(listItem.get(2)));
				}
			}

			//BEA_HM_PHONE
			else if(this.HOME_PHONE_TABLE.equals(listItem.get(0))) {
				if("Area Code".equals(listItem.get(1))) {
					docRequriedFields.setPhoneHomeArea(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Phone Number".equals(listItem.get(1))) {
					docRequriedFields.setPhoneHomeNum(StringUtil.convertToBoolean(listItem.get(2)));
				}
			}

			//BEA_BUS_PHONE
			else if(this.WORK_PHONE_TABLE.equals(listItem.get(0))) {
				if("Area Code".equals(listItem.get(1))) {
					docRequriedFields.setPhoneBusArea(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Phone Number".equals(listItem.get(1))) {
					docRequriedFields.setPhoneBusNum(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Ext".equals(listItem.get(1))) {
					docRequriedFields.setPhoneBusExt(StringUtil.convertToBoolean(listItem.get(2)));
				}
			}

			//BEA_CELL_PHONE
			else if(this.CELL_PHONE_TABLE.equals(listItem.get(0))) {
				if("Area Code".equals(listItem.get(1))) {
					docRequriedFields.setPhoneCellArea(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Phone Number".equals(listItem.get(1))) {
					docRequriedFields.setPhoneCellNum(StringUtil.convertToBoolean(listItem.get(2)));
				}
			}

			//BEA_EMAIL
			else if(this.EMAIL_TABLE.equals(listItem.get(0))) {
				if("Work".equals(listItem.get(1))) {
					docRequriedFields.setEmailWork(StringUtil.convertToBoolean(listItem.get(2)));
				} else if("Home".equals(listItem.get(1))) {
					docRequriedFields.setEmailHome(StringUtil.convertToBoolean(listItem.get(2)));
				}
			}
		}
		
		return docRequriedFields;
	}
}
