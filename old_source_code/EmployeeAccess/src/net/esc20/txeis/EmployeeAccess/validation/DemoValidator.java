package net.esc20.txeis.EmployeeAccess.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DemoInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DemoInfoFields;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.Email;
import net.esc20.txeis.EmployeeAccess.service.api.IDemoService;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;
import net.esc20.txeis.EmployeeAccess.web.view.Demo;
import net.esc20.txeis.common.constants.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.stereotype.Component;

@Component
public class DemoValidator extends AbstractValidator {

	private static final long serialVersionUID = -10232495288199999L;

	@Autowired
	@Qualifier("demoServiceTx")
	private IDemoService demoService;
		
	@Override
	protected Map<String, String> createTypeValidationMap() {
		return new HashMap<String,String>();
	}
	
	public void validateDemoStartView(Demo o, ValidationContext context) 
	{
		super.validateView(context);
		
		requiredValidation(o, context);
		lengthValidation(o, context);
		numericValidation (o, context);
		emailValidation(o, context);
		
	}
	
	public void requiredValidation(Demo o, ValidationContext context)
	{
		DemoInfoFields required = demoService.getRequiredFields(); 
		DemoInfo demoInfo = o.getDemoInfo();
		
		if(demoInfo.getName().getTitleString().equals("") && required.getNameTitle())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.name.title.description", "Title may not be blank. Please enter a valid title.");
		}
		
		if(demoInfo.getName().getLastName().equals("") && required.getNameLast())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.name.lastName", "Last name may not be blank. Please enter a valid last name.");
		}
		
		if(demoInfo.getName().getFirstName().equals("") && required.getNameFirst())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.name.firstName", "First name  may not be blank. Please enter a valid first name.");
		}
		
		if(demoInfo.getName().getMiddleName().equals("") && required.getNameMiddle())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.name.middleName", "Middle name may not be blank. Please enter a valid middle name.");
		}
		
		if(demoInfo.getName().getGenerationString().equals("") && required.getNameGeneration())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.name.generation.description", "Generation may not be blank. Please enter a valid generation.");
		}
		
		if(demoInfo.getMarital().getMaritalStatusCode().equals("") && required.getMaritalLocal())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.marital.maritalStatus.displayLabel", "Marital status may not be blank. Please enter a valid marital status.");
		}
		
		if(demoInfo.getDriversLicense().getNumber().equals("") && required.getDriversNum())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.driversLicense.number", "Driver's license number may not be blank. Please enter a valid driver's license number.");
		}
		
		if(demoInfo.getDriversLicense().getStateCode().equals("") && required.getDriversState())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.driversLicense.state.displayLabel", "Driver's license state may not be blank. Please enter a valid driver's license state.");
		}
		
		if(demoInfo.getRestrictionCodes().getLocalRestrictionCode().equals("") && required.getRestrictionLocal())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.restrictionCodes.localRestriction.displayLabel", "Local restriction code may not be blank. Please enter a valid local restriction code.");
		}
		
		if(demoInfo.getRestrictionCodes().getPublicRestrictionCode().equals("") && required.getRestrictionPublic())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.restrictionCodes.publicRestriction.displayLabel", "Public restriction code may not be blank. Please enter a valid public restriction code.");
		}
		
		if(demoInfo.getEmergencyContact().getName().equals("") && required.getEmergencyName())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.name", "Emergency contact name may not be blank. Please enter a valid emergency contact name.");
		}
		
		if(demoInfo.getEmergencyContact().getAreaCode().equals("") && required.getEmergencyAreaCode())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.areaCode", "Emergency contact phone area may not be blank. Please enter a valid emergency contact phone area.");
		}
		
		if(demoInfo.getEmergencyContact().getPhoneNumber().equals("") && required.getEmergencyPhoneNum())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.phoneNumber", "Emergency contact phone number may not be blank. Please enter a valid emergency contact phone number.");
		}
		
		if(demoInfo.getEmergencyContact().getExtention().equals("") && required.getEmergencyPhoneExt())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.extention", "Emergency contact phone extension may not be blank. Please enter a valid emergency contact phone extension.");
		}
		
		if(demoInfo.getEmergencyContact().getRelationship().equals("") && required.getEmergencyRelationship())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.relationship", "Emergency contact relationship may not be blank. Please enter a valid emergency contact relationship.");
		}
		
		if(demoInfo.getEmergencyContact().getEmergencyNotes().equals("") && required.getEmergencyNotes())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.emergencyNotes", "Emergency contact notes may not be blank. Please enter valid emergency contact notes.");
		}
		
		if(demoInfo.getMailAddr().getNumber().equals("") && required.getMailingAddress())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.number", "Mailing address number may not be blank. Please enter a valid mailing address number.");
		}
		
		if(demoInfo.getMailAddr().getStreet().equals("") && required.getMailingPoBox())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.street", "Mailing address street may not be blank. Please enter a valid mailing address street.");
		}
		
		if(demoInfo.getMailAddr().getApartment().equals("") && required.getMailingApt() )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.apartment", "Mailing address apartment may not be blank. Please enter a valid mailing address apartment.");
		}
		
		if(demoInfo.getMailAddr().getCity().equals("") && required.getMailingCity())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.city", "Mailing address city may not be blank. Please enter a valid mailing address city.");
		}
		
		if(demoInfo.getMailAddr().getStateCode().equals("") && required.getMailingState())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.state.displayLabel", "Mailing address state may not be blank. Please enter a valid mailing address state.");
		}
		
		if(demoInfo.getMailAddr().getZip().equals("") && required.getMailingZip() )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.zip", "Mailing address zip may not be blank. Please enter a valid mailing address zip.");
		}
		
		if(demoInfo.getMailAddr().getZipPlusFour().equals("") && required.getMailingZip4())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.zipPlusFour", "Mailing address zip plus four may not be blank. Please enter a valid mailing address zip plus four.");
		}
		
		if(demoInfo.getAltAddr().getNumber().equals("") && required.getAlternateAddress())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.number", "Alternate address number may not be blank. Please enter a valid alternate address number.");
		}
		
		if(demoInfo.getAltAddr().getStreet().equals("") && required.getAlternatePoBox())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.street", "Alternate address street may not be blank. Please enter a valid alternate address street.");
		}
		
		if(demoInfo.getAltAddr().getApartment().equals("") && required.getAlternateApt())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.apartment", "Alternate address apartment may not be blank. Please enter a valid alternate address apartment.");
		}
		
		if(demoInfo.getAltAddr().getCity().equals("") && required.getAlternateCity())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.city", "Alternate address city may not be blank. Please enter a valid alternate address city.");
		}
		if(demoInfo.getAltAddr().getStateCode().equals("") && required.getAlternateState())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.state.displayLabel", "Alternate address state may not be blank. Please enter a valid alternate address state.");
		}
		
		if(demoInfo.getAltAddr().getZip().equals("") && required.getAlternateZip())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.zip", "Alternate address zip may not be blank. Please enter a valid alternate address zip.");
		}
		
		if(demoInfo.getAltAddr().getZipPlusFour().equals("") && required.getAlternateZip4())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.zipPlusFour", "Alternate address zip plus four may not be blank. Please enter a valid alternate address zip plus four.");
		}
		
		if(demoInfo.getHomePhone().getAreaCode().equals("") && required.getPhoneHomeArea() && o.getFieldDisplayOptionHomePhone().equals("U"))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.homePhone.areaCode", "Home area code may not be blank. Please enter a valid home area code.");
		}
		
		if(demoInfo.getHomePhone().getPhoneNumber().equals("") && required.getPhoneHomeNum() && o.getFieldDisplayOptionHomePhone().equals("U"))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.homePhone.phoneNumber", "Home phone number may not be blank. Please enter a valid home phone number.");
		}
		
		if(demoInfo.getWorkPhone().getAreaCode().equals("") && required.getPhoneBusArea() && o.getFieldDisplayOptionWorkPhone().equals("U"))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.workPhone.areaCode", "Business area code may not be blank. Please enter a valid business area code.");
		}
		
		if(demoInfo.getWorkPhone().getPhoneNumber().equals("") && required.getPhoneBusNum() && o.getFieldDisplayOptionWorkPhone().equals("U"))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.workPhone.phoneNumber", "Business phone number may not be blank. Please enter a valid business phone number.");
		}
		
		if(demoInfo.getWorkPhone().getExtention().equals("") && required.getPhoneBusExt() &&  o.getFieldDisplayOptionWorkPhone().equals("U"))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.workPhone.extention", "Business phone extension may not be blank. Please enter a valid business phone extension.");
		}
		
		if(demoInfo.getCellPhone().getAreaCode().equals("") && required.getPhoneCellArea() && o.getFieldDisplayOptionCellPhone().equals("U"))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.cellPhone.areaCode", "Cellular area code may not be blank. Please enter a valid cellular area code.");
		}
		
		if(demoInfo.getCellPhone().getPhoneNumber().equals("") && required.getPhoneCellNum() && o.getFieldDisplayOptionCellPhone().equals("U"))
		{
			
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.cellPhone.phoneNumber", "Cellular phone number may not be blank. Please enter a valid cellular phone number.");
		}
		
		if(demoInfo.getEmail().getWorkEmail().equals("") && required.getEmailWork() )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.email.workEmail", "Work email may not be blank. Please enter a valid work email address.");
		}
		
		if(demoInfo.getEmail().getWorkEmailVerify().equals("") && required.getEmailWork())
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.email.workEmailVerify", "Work email verification may not be blank. Please enter a valid work email address.");
		}
		
		if(demoInfo.getEmail().getHomeEmail().equals("")&& required.getEmailHome() )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.email.homeEmail", "Home email may not be blank. Please enter a valid home email address.");
		}
		
		if(demoInfo.getEmail().getHomeEmailVerify().equals("") && required.getEmailHome() )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.email.homeEmailVerify", "Home email verification may not be blank. Please enter a valid home email address. ");
		}
	}
	
	public void lengthValidation(Demo o, ValidationContext context)
	{
		DemoInfo demoInfo = o.getDemoInfo();
		if(demoInfo.getName().getLastName().length() > 25 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.name.lastName", "Last name cannot be more than 25 characters in length.");
		}
		
		if(demoInfo.getName().getFirstName().length() > 17 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.name.firstName", "First name cannot be more than 17 characters in length.");
		}
		
		if(demoInfo.getName().getMiddleName().length() > 14 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.name.middleName", "Middle name cannot be more than 14 characters in length.");
		}
		
		if(demoInfo.getDriversLicense().getNumber().length() > 19 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.driversLicense.number", "Driver's license number cannot be more than 19 characters in length.");
		}
		
		if(demoInfo.getEmergencyContact().getName().length() > 26 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.name", "Emergency contact name cannot be more than 26 characters in length.");
		}
		
		if(demoInfo.getEmergencyContact().getAreaCode().length() > 3 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.areaCode", "Emergency contact phone area code cannot be more than 3 characters in length.");
		}
		
		if(demoInfo.getEmergencyContact().getPhoneNumber().length() > 7 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.phoneNumber", "Emergency contact phone number cannot be more than 7 characters in length.");
		}
		
		if(demoInfo.getEmergencyContact().getExtention().length() > 4 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.extention", "Emergency contact phone extension cannot be more than 4 characters in length.");
		}
		
		if(demoInfo.getEmergencyContact().getRelationship().length() > 25 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.relationship", "Emergency contact relationship cannot be more than 25 characters in length.");
		}
		
		if(demoInfo.getEmergencyContact().getEmergencyNotes().length() > 25 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.emergencyNotes", "Emergency contact notes cannot be more than 25 characters in length.");
		}
		
		if(demoInfo.getMailAddr().getNumber().length() > 8 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.number", "Mailing address number cannot be more than 8 characters in length.");
		}
		
		if(demoInfo.getMailAddr().getStreet().length() > 20 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.street", "Mailing address street cannot be more than 20 characters in length.");
		}
		
		if(demoInfo.getMailAddr().getApartment().length() > 7 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.apartment", "Mailing address apartment cannot be more than 7 characters in length.");
		}
		
		if(demoInfo.getMailAddr().getCity().length() > 17 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.city", "Mailing address city cannot be more than 17 characters in length.");
		}
		
		if(demoInfo.getMailAddr().getZip().length() > 5 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.zip", "Mailing address zip cannot be more than 5 characters in length.");
		}
		
		if(demoInfo.getMailAddr().getZipPlusFour().length() > 4 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.zipPlusFour", "Mailing address zip plus four cannot be more than 4 characters in length.");
		}
		
		if (demoInfo.getMailAddr().getZipPlusFour().length() > 0 &&  
		   demoInfo.getMailAddr().getZipPlusFour().length() < 4 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.zipPlusFour", "Mailing address zip plus four cannot be less than 4 characters in length, if entered.");
		}
		
		if(demoInfo.getAltAddr().getNumber().length() > 8 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.number", "Alternate address number cannot be more than 8 characters in length.");
		}
		
		if(demoInfo.getAltAddr().getStreet().length() > 20 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.street", "Alternate address street cannot be more than 20 characters in length.");
		}
		
		if(demoInfo.getAltAddr().getApartment().length() > 7 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.apartment", "Alternate address apartment cannot be more than 7 characters in length.");
		}
		
		if(demoInfo.getAltAddr().getCity().length() > 17 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.city", "Alternate address city cannot be more than 17 characters in length.");
		}
		
		if(demoInfo.getAltAddr().getZip().length() > 5 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.zip", "Alternate address zip cannot be more than 5 characters in length.");
		}
		
		if(demoInfo.getAltAddr().getZipPlusFour().length() > 4 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.zipPlusFour", "Alternate address zip plus four cannot be more than 4 characters in length.");
		}
		
		if (demoInfo.getAltAddr().getZipPlusFour().length() > 0 && 
			demoInfo.getAltAddr().getZipPlusFour().length() < 4)
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.zipPlusFour", "Alternate address zip plus four cannot be less than 4 characters in length, if entered.");
		}
		
		if(demoInfo.getHomePhone().getAreaCode().length() > 3 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.homePhone.areaCode", "Home phone area code cannot be more than 3 characters in length.");
		}
		
		if(demoInfo.getHomePhone().getAreaCode().length() < 3 && demoInfo.getHomePhone().getAreaCode().length() > 0 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.homePhone.areaCode", "Home phone area code cannot be less than 3 characters in length.");
		}
		
		if(demoInfo.getHomePhone().getPhoneNumber().length() > 7 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.homePhone.phoneNumber", "Home phone number cannot be more than 7 characters in length.");
		}
		
		if(demoInfo.getHomePhone().getPhoneNumber().length() < 7 && demoInfo.getHomePhone().getPhoneNumber().length() > 0)
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.homePhone.phoneNumber", "Home phone number cannot be less than 7 characters in length.");
		}
		
		if(demoInfo.getWorkPhone().getAreaCode().length() > 3 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.workPhone.areaCode", "Business phone area code cannot be more than 3 characters in length.");
		}
		
		if(demoInfo.getWorkPhone().getAreaCode().length() < 3 && demoInfo.getWorkPhone().getAreaCode().length() > 0 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.workPhone.areaCode", "Business phone area code cannot be less than 3 characters in length.");
		}
		
		if(demoInfo.getWorkPhone().getPhoneNumber().length() > 7 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.workPhone.phoneNumber", "Business phone number cannot be more than 7 characters in length.");
		}
		
		if(demoInfo.getWorkPhone().getPhoneNumber().length() < 7 && demoInfo.getWorkPhone().getPhoneNumber().length() > 0)
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.workPhone.phoneNumber", "Business phone number cannot be less than 7 characters in length.");
		}
		
		if(demoInfo.getWorkPhone().getExtention().length() > 4 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.workPhone.extention", "Business phone extension cannot be more than 7 characters in length.");
		}
		
		if(demoInfo.getCellPhone().getAreaCode().length() > 3 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.cellPhone.areaCode", "Cellular area code cannot be more than 3 characters in length.");
		}
		
		if(demoInfo.getCellPhone().getAreaCode().length() < 3 && demoInfo.getCellPhone().getAreaCode().length() > 0 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.cellPhone.areaCode", "Cellular area code cannot be less than 3 characters in length.");
		}
		
		if(demoInfo.getCellPhone().getPhoneNumber().length() > 7 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.cellPhone.phoneNumber", "Cellular phone number cannot be more than 7 characters in length.");
		}
		
		if(demoInfo.getCellPhone().getPhoneNumber().length() < 7 && demoInfo.getCellPhone().getPhoneNumber().length() > 0)
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.cellPhone.phoneNumber", "Cellular phone number cannot be less than 7 characters in length.");
		}
		
		if(demoInfo.getEmail().getWorkEmail().length() > 45 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.email.workEmail", "Work email cannot be more than 45 characters in length.");
		}
		
		if(demoInfo.getEmail().getWorkEmailVerify().length() > 45 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.email.workEmailVerify", "Work email verification cannot be more than 45 characters in length.");
		}
		
		if(demoInfo.getEmail().getHomeEmail().length() > 45 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.email.homeEmail", "Home email cannot be more than 45 characters in length.");
		}
		
		if(demoInfo.getEmail().getHomeEmailVerify().length() > 45 )
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.email.homeEmailVerify", "Home email verification cannot be more than 45 characters in length.");
		}
	}
	
	public void numericValidation(Demo o,ValidationContext context)
	{
		//Still need to add "" checks
		
		int driversLicenseLength = o.getDemoInfo().getDriversLicense().getNumber().length();
		
		if(!"".equals(o.getDemoInfo().getDriversLicense().getNumber().trim()) && (!StringUtil.isAlphaNumeric(o.getDemoInfo().getDriversLicense().getNumber().substring(0, driversLicenseLength/2).trim()) || !StringUtil.isAlphaNumeric(o.getDemoInfo().getDriversLicense().getNumber().substring(driversLicenseLength/2, driversLicenseLength).trim())))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.driversLicense.number", "Driver's license number must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getEmergencyContact().getAreaCode().trim()) && !StringUtil.isInteger(o.getDemoInfo().getEmergencyContact().getAreaCode().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.areaCode", "Emergency contact phone area code must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getEmergencyContact().getPhoneNumber().trim()) && !StringUtil.isInteger(o.getDemoInfo().getEmergencyContact().getPhoneNumber().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.phoneNumber", "Emergency contact phone number must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getEmergencyContact().getExtention().trim()) && !StringUtil.isInteger(o.getDemoInfo().getEmergencyContact().getExtention().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.emergencyContact.extention", "Emergency contact phone extension must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getMailAddr().getNumber().trim()) && !StringUtil.isInteger(o.getDemoInfo().getMailAddr().getNumber().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.number", "Mailing address number must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getMailAddr().getZip().trim()) && !StringUtil.isInteger(o.getDemoInfo().getMailAddr().getZip().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.zip", "Mailing address zip code must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getMailAddr().getZipPlusFour().trim()) && !StringUtil.isInteger(o.getDemoInfo().getMailAddr().getZipPlusFour().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.mailAddr.zipPlusFour", "Mailing address zip code plus four must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getAltAddr().getNumber().trim()) && !StringUtil.isInteger(o.getDemoInfo().getAltAddr().getNumber().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.number", "Alternate address number must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getAltAddr().getZip().trim()) && !StringUtil.isInteger(o.getDemoInfo().getAltAddr().getZip().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.zip", "Alternate address zip code must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getAltAddr().getZipPlusFour().trim()) && !StringUtil.isInteger(o.getDemoInfo().getAltAddr().getZipPlusFour().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.altAddr.zipPlusFour", "Alternate address zip code plus four must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getHomePhone().getAreaCode().trim()) && !StringUtil.isInteger(o.getDemoInfo().getHomePhone().getAreaCode().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.homePhone.areaCode", "Home phone area code must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getHomePhone().getPhoneNumber().trim()) && !StringUtil.isInteger(o.getDemoInfo().getHomePhone().getPhoneNumber().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.homePhone.phoneNumber", "Home phone number must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getWorkPhone().getAreaCode().trim()) && !StringUtil.isInteger(o.getDemoInfo().getWorkPhone().getAreaCode().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.workPhone.areaCode", "Work phone area code must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getWorkPhone().getPhoneNumber().trim()) && !StringUtil.isInteger(o.getDemoInfo().getWorkPhone().getPhoneNumber().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.workPhone.phoneNumber", "Work phone number must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getWorkPhone().getExtention().trim()) && !StringUtil.isInteger(o.getDemoInfo().getWorkPhone().getExtention().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.workPhone.extension", "Work phone extension must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getCellPhone().getAreaCode().trim()) && !StringUtil.isInteger(o.getDemoInfo().getCellPhone().getAreaCode().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.cellPhone.areaCode", "Cellular phone area code must be numeric.");
		}
		
		if(!"".equals(o.getDemoInfo().getCellPhone().getPhoneNumber().trim()) && !StringUtil.isInteger(o.getDemoInfo().getCellPhone().getPhoneNumber().trim()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.cellPhone.phoneNumber", "Cellular phone number must be numeric.");
		}
		
	}
	
	public void emailValidation(Demo o, ValidationContext context)
	{
		Email email = o.getDemoInfo().getEmail();
		//if there's a pending request, compare the new entry with that
		Email comparisonEmail = demoService.getPendingEmailRequests(o.getDemoInfo().getEmployeeNumber());
		
		
		//if there is NOT a pending request, compare the new entry with the currently saved value for the employee
		if( comparisonEmail == null )
		{
			comparisonEmail = demoService.getCurrentEmail(o.getDemoInfo().getEmployeeNumber());
		}
		
		//validate verify field matches new field if the saved value differs from the new value
		//OR anytime there's something typed in the verify field
		if(!comparisonEmail.getWorkEmail().equals(email.getWorkEmail()) || !email.getWorkEmailVerify().equals(""))
		{
			if(!email.getWorkEmail().equals(email.getWorkEmailVerify()))
			{
				ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.email.workEmailVerify", "Work Email Verification field does not match Work Email field.");
			}
		}
		if(!comparisonEmail.getHomeEmail().equals(email.getHomeEmail()) || !email.getHomeEmailVerify().equals(""))
		{
			if(!email.getHomeEmail().equals(email.getHomeEmailVerify()))
			{
				ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.email.homeEmailVerify", "Home Email Verification field does not match Home Email field.");
			}
		}
		
		// this regex is derived from IETF RFC 2822 email address standard
		// the standard does not allow capital letters, but the RSCCC DB
		// had all-caps emails in it, so to be consistent, the regex is
		// modified to allow caps
		
		if(email.getWorkEmail() != null && !"".equals(email.getWorkEmail()))
		{
			Pattern p = Pattern.compile("[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?");
			Matcher m = p.matcher(email.getWorkEmail());
			boolean found = m.matches();

			if (!found || ( !StringUtil.right(StringUtil.upper(email.getWorkEmail()), 3).equals("COM") && !StringUtil.right(StringUtil.upper(email.getWorkEmail()), 3).equals("NET") 
				&& !StringUtil.right(StringUtil.upper(email.getWorkEmail()), 3).equals("ORG") && !StringUtil.right(StringUtil.upper(email.getWorkEmail()), 3).equals("EDU")  &&
				!StringUtil.right(StringUtil.upper(email.getWorkEmail()), 3).equals("BIZ")	&& !StringUtil.right(StringUtil.upper(email.getWorkEmail()), 2).equals("TV")  &&
				!StringUtil.right(StringUtil.upper(email.getWorkEmail()), 2).equals("US") && !StringUtil.right(StringUtil.upper(email.getWorkEmail()), 2).equals("CC") && 
				!StringUtil.right(StringUtil.upper(email.getWorkEmail()), 2).equals("WS") && !StringUtil.right(StringUtil.upper(email.getWorkEmail()), 2).equals("BZ") &&
				!StringUtil.right(StringUtil.upper(email.getWorkEmail()), 2).equals("VG") && !StringUtil.right(StringUtil.upper(email.getWorkEmail()), 3).equals("GOV") &&
				!StringUtil.right(StringUtil.upper(email.getWorkEmail()), 2).equals("MS") && !StringUtil.right(StringUtil.upper(email.getWorkEmail()), 2).equals("TC") &&
				!StringUtil.right(StringUtil.upper(email.getWorkEmail()), 2).equals("GS") && !StringUtil.right(StringUtil.upper(email.getWorkEmail()), 4).equals("INFO") &&
				!StringUtil.right(StringUtil.upper(email.getWorkEmail()), 4).equals("NAME")))
				
				ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.email.workEmail", "Work email is an invalid email format. Please re-enter.");
		}

		if (email.getHomeEmail() != null && !"".equals(email.getHomeEmail())) {
			//cs20151206  allow any email domains
			if (!email.getHomeEmail().matches(Constant.emailRegEx)) {
				ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.email.homeEmail", "Home email is an invalid email format. Please re-enter.");
			}

//			Pattern p = Pattern.compile("[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?");
//			Matcher m = p.matcher(email.getHomeEmail());
//			boolean found = m.matches();
//			if (!found || ( !StringUtil.right(StringUtil.upper(email.getHomeEmail()), 3).equals("COM") && !StringUtil.right(StringUtil.upper(email.getHomeEmail()), 3).equals("NET") 
//					&& !StringUtil.right(StringUtil.upper(email.getHomeEmail()), 3).equals("ORG") && !StringUtil.right(StringUtil.upper(email.getHomeEmail()), 3).equals("EDU")  &&
//					!StringUtil.right(StringUtil.upper(email.getHomeEmail()), 3).equals("BIZ")	&& !StringUtil.right(StringUtil.upper(email.getHomeEmail()), 2).equals("TV")  &&
//					!StringUtil.right(StringUtil.upper(email.getHomeEmail()), 2).equals("US") && !StringUtil.right(StringUtil.upper(email.getHomeEmail()), 2).equals("CC") && 
//					!StringUtil.right(StringUtil.upper(email.getHomeEmail()), 2).equals("WS") && !StringUtil.right(StringUtil.upper(email.getHomeEmail()), 2).equals("BZ") &&
//					!StringUtil.right(StringUtil.upper(email.getHomeEmail()), 2).equals("VG") && !StringUtil.right(StringUtil.upper(email.getHomeEmail()), 3).equals("GOV") &&
//					!StringUtil.right(StringUtil.upper(email.getHomeEmail()), 2).equals("MS") && !StringUtil.right(StringUtil.upper(email.getHomeEmail()), 2).equals("TC") &&
//					!StringUtil.right(StringUtil.upper(email.getHomeEmail()), 2).equals("GS") && !StringUtil.right(StringUtil.upper(email.getHomeEmail()), 4).equals("INFO") &&
//					!StringUtil.right(StringUtil.upper(email.getHomeEmail()), 4).equals("NAME")))
//					
//				ContextValidationUtil.addMessage(context.getMessageContext(), "demoInfo.email.homeEmail", "Home email is an invalid email format. Please re-enter.");
		}

	}

}