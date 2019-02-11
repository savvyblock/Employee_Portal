package net.esc20.txeis.EmployeeAccess.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.dao.api.IReferenceDataDao;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;

import org.springframework.stereotype.Service;

@Service
public class ReferenceDataService
{

	private static IReferenceDataDao referenceDataDao;

	private static List <Code> titles = new ArrayList<Code>(Arrays.asList(new Code("0", ""),
																		  new Code("1", "Mr."), 
																		  new Code("2", "Mrs."), 
																		  new Code("3", "Miss"), 
																		  new Code("4", "Ms.")));
	
	public static String DEFAULT_STATE_CODE = "TX";
	
	public static String LEGAL_NAME_TABLE = "BEA_LGL_NAME";
	public static String MAIL_ADDR_TABLE = "BEA_MAIL_ADDR";
	public static String ALT_MAIL_TABLE = "BEA_ALT_MAIL_ADDR";
	public static String HOME_PHONE_TABLE = "BEA_HM_PHONE";
	public static String WORK_PHONE_TABLE = "BEA_BUS_PHONE";
	public static String CELL_PHONE_TABLE = "BEA_CELL_PHONE";
	public static String EMAIL_TABLE = "BEA_EMAIL";
	public static String RESTRICTION_CODE_TABLE = "BEA_RESTRICT";
	public static String MARITAL_STATUS_TABLE = "BEA_MRTL_STAT";
	public static String DRIVERS_LICENSE_TABLE = "BEA_DRVS_LIC";
	public static String EMERGENCY_CONTACT_TABLE = "BEA_EMER_CONTACT";
	
	public IReferenceDataDao getReferenceDataDao() 
	{
		return referenceDataDao;
	}

	public void setReferenceDataDao(IReferenceDataDao referenceDataDao) 
	{
		ReferenceDataService.referenceDataDao = referenceDataDao;
	}
	
	public static List <Code> getTitles() 
	{
		return titles;
	}
	
	public static List<String> getTitlesDisplay() 
	{
		List <String> titlesDisplay = new ArrayList <String>();
		for (Code code: titles)
		{
			titlesDisplay.add(code.getDescription());
		}
		return titlesDisplay;
	}
	
	public static Code getTitleFromCode(String code)
	{
		for(Code c: titles)
		{
			if(c.getCode().equals(code))
				return c;
		}
		return new Code();
	}
	
	public static Code getTitleFromDisplayLabel(String displayLabel)
	{
		for(Code code: titles)
		{
			if(code.getDescription().equals(displayLabel))
				return code;
		}
		return new Code();
	}

	public static List <Code> getGenerations() 
	{
		return referenceDataDao.getGenerations();
	}
	
	public static List<String> getGenerationsDisplay() 
	{
		List <String> generationsDisplay = new ArrayList <String>();
		for (Code code: getGenerations())
		{
			generationsDisplay.add(code.getDescription());
		}
		return generationsDisplay;
	}
	
	public static Code getGenerationFromCode(String code)
	{
		for(Code c: getGenerations())
		{
			if(c.getCode().equals(code))
				return c;
		}
		return new Code();
	}

	public static Code getGenerationFromDisplayLabel(String displayLabel)
	{
		for(Code code: getGenerations())
		{
			if(code.getDescription().equals(displayLabel))
				return code;
		}
		return new Code();
	}
	
	public static List<Code> getStates() 
	{
		return referenceDataDao.getStates();
	}
	
	public static List<String> getStatesDisplay() 
	{
		List <String> statesDisplay = new ArrayList <String>();
		for (Code code: getStates())
		{
			statesDisplay.add(code.getDisplayLabel());
		}
		return statesDisplay;
	}
	
	public static String getStateCode(String displayLabel)
	{
		for(Code code: getStates())
		{
			if(code.getDisplayLabel().equals(displayLabel))
				return code.getCode();
		}
		return "";
	}
	
	public static Code getStateFromDisplayLabel(String displayLabel)
	{
		for(Code code: getStates())
		{
			if(code.getDisplayLabel().equals(displayLabel))
				return code;
		}
		return new Code();
	}
	
	public static Code getStateFromCode(String code)
	{
		for(Code c: getStates())
		{
			if(c.getCode().equals(code))
				return c;
		}
		return new Code();
	}
	
	public static List<Code> getRestrictions()
	{
		return referenceDataDao.getRestrictions();
	}
	
	public static List<String> getRestrictionDisplay() 
	{
		List <String> restrictionsDisplay = new ArrayList <String>();
		for (Code code: getRestrictions())
		{
			restrictionsDisplay.add(code.getDisplayLabel());
		}
		return restrictionsDisplay;
	}
	
	public static String getRestrictionCode(String displayLabel)
	{
		for(Code code: getRestrictions())
		{
			if(code.getDisplayLabel().equals(displayLabel))
				return code.getCode();
		}
		return "";
	}
	
	public static Code getRestrictionFromDisplayLabel(String displayLabel)
	{
		for(Code code: getRestrictions())
		{
			if(code.getDisplayLabel().equals(displayLabel))
				return code;
		}
		return new Code();
	}
	
	public static Code getRestrictionFromCode(String code)
	{
		for(Code c: getRestrictions())
		{
			if(c.getCode().equals(code))
				return c;
		}
		return new Code();
	}

	public static List<Code> getMaritalStatuses() 
	{
		return referenceDataDao.getMaritalStatuses();
	}
	
	public static List<String> getMaritalStatusesDisplay() 
	{
		List <String> maritalStatusesDisplay = new ArrayList <String>();
		for (Code code: getMaritalStatuses())
		{
			maritalStatusesDisplay.add(code.getDisplayLabel());
		}
		return maritalStatusesDisplay;
	}
	
	public static String getMaritalStatusCode(String displayLabel)
	{
		for(Code code: getMaritalStatuses())
		{
			if(code.getDisplayLabel().equals(displayLabel))
				return code.getCode();
		}
		return "";
	}
	
	public static Code getMaritalStatusFromDisplayLabel(String displayLabel)
	{
		for(Code code: getMaritalStatuses())
		{
			if(code.getDisplayLabel().equals(displayLabel))
				return code;
		}
		return new Code();
	}
	
	public static Code getMaritalStatusFromCode(String code)
	{
		for(Code c: getMaritalStatuses())
		{
			if(c.getCode().equals(code))
				return c;
		}
		return new Code();
	}
	
	public static List<Code> getDdAccountTypes() 
	{
		return referenceDataDao.getDdAccountTypes();
	}
	
	public static List<String> getDdAccountTypesDisplay() 
	{
		List <String> ddAccountTypesDisplay = new ArrayList <String>();
		for (Code code: getDdAccountTypes())
		{
			ddAccountTypesDisplay.add(code.getDisplayLabel());
		}
		return ddAccountTypesDisplay;
	}
	
	public static String getDdAccountTypeCode(String displayLabel)
	{
		for(Code code: getDdAccountTypes())
		{
			if(code.getDisplayLabel().equals(displayLabel))
				return code.getCode();
		}
		return "";
	}
	
	public static Code getDdAccountTypeFromDisplayLabel(String displayLabel)
	{
		for(Code code: getDdAccountTypes())
		{
			if(code.getDisplayLabel().equals(displayLabel))
				return code;
		}
		return new Code();
	}
	
	public static Code getDdAccountTypeFromCode(String code)
	{
		for(Code c: getDdAccountTypes())
		{
			if(c.getCode().equals(code))
				return c;
		}
		return new Code();
	}
	
	public static List<Code> getBankCodes() 
	{
			return referenceDataDao.getAvailableBanks();
	}
	
	public static List<String> getBankCodesDisplay() 
	{
		List <String> bankCodesDisplay = new ArrayList <String>();
		for (Code code: getBankCodes())
		{
			bankCodesDisplay.add(code.getDisplayLabel());
		}
		return bankCodesDisplay;
	}
	
	public static String getDdBankCodesCode(String displayLabel)
	{
		for(Code code: getBankCodes())
		{
			if(code.getDisplayLabel().equals(displayLabel))
				return code.getCode();
		}
		return "";
	}
	
	public static Code getBankCodesFromDisplayLabel(String displayLabel)
	{
		for(Code code: getBankCodes())
		{
			if(code.getDisplayLabel().equals(displayLabel))
				return code;
		}
		return new Code();
	}
	
	public static Code getBankCodesFromCode(String code)
	{
		for(Code c: getBankCodes())
		{
			if(c.getCode().equals(code))
				return c;
		}
		return new Code();
	}
	
	public static List <Code> getMaritalActualStatuses() 
	{
		return referenceDataDao.getMaritalActualStatuses();		
	}

	public static List<String> getMaritalActualStatusDisplay()
	{
		List <String> maritalActualStatusesDisplay = new ArrayList <String>();
		for (Code code: getMaritalActualStatuses())
		{
			maritalActualStatusesDisplay.add(code.getDisplayLabel());
		}
		return maritalActualStatusesDisplay;
	}
	
	public static Code getMaritalActualStatusFromDisplayLabel(String displayLabel)
	{
		for(Code code: getMaritalActualStatuses())
		{
			if(code.getDisplayLabel().equals(displayLabel))
				return code;
		}
		return new Code();
	}
	
	public static Code getMaritalActualStatusFromCode(String code)
	{
		for(Code c: getMaritalActualStatuses())
		{
			if(c.getCode().equals(code))
				return c;
		}
		return new Code();
	}
	
//	public static HashMap<String, Integer> getApproverIds() 
//	{
//		if(approverIds == null)
//			approverIds = referenceDataDao.getApproverIds();
//		return approverIds;
//	}
	
}
