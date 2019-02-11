package net.esc20.txeis.EmployeeAccess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.conversion.TimestampConverter;
import net.esc20.txeis.EmployeeAccess.dao.api.IDemoDao;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.AltAddr;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.CellPhone;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DemoInfo;
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
import net.esc20.txeis.EmployeeAccess.service.ReferenceDataService;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
public class DemoDao extends NamedParameterJdbcDaoSupport implements IDemoDao {	
	
	String module = "Employee Access";
	@Override
	public DemoInfo getDemoInfo(String employeeNumber) {
		return getCurrentDemoInfo(employeeNumber);
	}

	@Override
	public HashMap<String,String> getApproverEmployeeNumbers() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT GRP_NAME, emp_nbr FROM SEC_USERS, BHR_EAP_DEMO_ASSGN_GRP ");
		sql.append("WHERE BHR_EAP_DEMO_ASSGN_GRP.apprvr_usr_id = usr_id");

		MapSqlParameterSource params = new MapSqlParameterSource();

		HashMap<String,String> result = new HashMap<String,String>();
		try
		{
			List<List<String>> temp = new ArrayList<List<String>>();
			temp = getNamedParameterJdbcTemplate().query(sql.toString(), params, new ApproverEmployeeNumbersMapper());
			for(List<String> kvp : temp)
			{
				result.put(kvp.get(0), kvp.get(1));
			}
		}
		catch(EmptyResultDataAccessException e)
		{
			result = null;
		}
		return result;
	}

	private static class ApproverEmployeeNumbersMapper implements RowMapper {
		public List<String> mapRow(ResultSet rs, int rows) throws SQLException
		{
			List<String> returnValue = new ArrayList<String>();

			returnValue.add(rs.getString("grp_name"));
			returnValue.add(rs.getString("emp_nbr"));
			return returnValue;
		}
	}

	private DemoInfo getCurrentDemoInfo(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT emp_nbr, name_pre, name_l, name_f, name_m, name_gen, ");
		sql.append("addr_nbr, addr_str, addr_apt, addr_city, addr_st, addr_zip, addr_zip4, ");
		sql.append("smr_addr_nbr, smr_addr_str, smr_addr_apt, smr_addr_city, smr_addr_st, smr_addr_zip, smr_addr_zip4, ");
		sql.append("phone_area, phone_nbr, phone_area_bus, phone_nbr_bus, bus_phone_ext, phone_area_cell, phone_nbr_cell, ");
		sql.append("email, hm_email, restrict_cd, restrict_cd_public, marital_stat, drivers_lic_nbr, drivers_lic_st, ");
		sql.append("emer_contact, emer_phone_ac, emer_phone_nbr, emer_phone_ext, emer_rel, emer_note, sex, dob, staff_id ");

		sql.append("FROM BHR_EMP_DEMO ");
		sql.append("WHERE EMP_NBR = :employeeNumber");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		DemoInfo result;

		try {
			result = (DemoInfo) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new CurrentDemoInfoMapper());
		} catch(EmptyResultDataAccessException e)
		{
			result = null;
		}
		return result;
	}

	private static class CurrentDemoInfoMapper implements RowMapper {
		public DemoInfo mapRow(ResultSet rs, int rows) throws SQLException {
			DemoInfo tempDemoInfo = new DemoInfo(rs.getString("emp_nbr").trim(), rs.getString("sex"), rs.getString("dob"), rs.getString("staff_id"));

			Name tempName = new Name();
			tempName.setTitleString(rs.getString("name_pre").trim());
			tempName.setLastName(rs.getString("name_l").trim());
			tempName.setFirstName(rs.getString("name_f").trim());
			tempName.setMiddleName(rs.getString("name_m").trim());
			tempName.setGenerationCode(rs.getString("name_gen").trim());

			MailAddr tempMailAddr = new MailAddr();
			tempMailAddr.setNumber(rs.getString("addr_nbr").trim());
			tempMailAddr.setStreet(rs.getString("addr_str").trim());
			tempMailAddr.setApartment(rs.getString("addr_apt").trim());
			tempMailAddr.setCity(rs.getString("addr_city").trim());
			tempMailAddr.setStateCode(rs.getString("addr_st").trim());
			tempMailAddr.setZip(rs.getString("addr_zip").trim());
			tempMailAddr.setZipPlusFour(rs.getString("addr_zip4").trim());

			AltAddr tempAltAddr = new AltAddr();
			tempAltAddr.setNumber(rs.getString("smr_addr_nbr").trim());
			tempAltAddr.setStreet(rs.getString("smr_addr_str").trim());
			tempAltAddr.setApartment(rs.getString("smr_addr_apt").trim());
			tempAltAddr.setCity(rs.getString("smr_addr_city").trim());
			tempAltAddr.setStateCode(rs.getString("smr_addr_st").trim());
			tempAltAddr.setZip(rs.getString("smr_addr_zip").trim());
			tempAltAddr.setZipPlusFour(rs.getString("smr_addr_zip4").trim());

			HomePhone tempHomePhone = new HomePhone();
			tempHomePhone.setAreaCode(rs.getString("phone_area").trim());
			tempHomePhone.setPhoneNumber(rs.getString("phone_nbr").trim());

			WorkPhone tempWorkPhone = new WorkPhone();
			tempWorkPhone.setAreaCode(rs.getString("phone_area_bus").trim());
			tempWorkPhone.setPhoneNumber(rs.getString("phone_nbr_bus").trim());
			tempWorkPhone.setExtention(rs.getString("bus_phone_ext").trim());

			CellPhone tempCellPhone = new CellPhone();
			tempCellPhone.setAreaCode(rs.getString("phone_area_cell").trim());
			tempCellPhone.setPhoneNumber(rs.getString("phone_nbr_cell").trim());

			Email tempEmail = new Email();
			tempEmail.setHomeEmail(rs.getString("hm_email").trim());
			tempEmail.setWorkEmail(rs.getString("email").trim());

			RestrictionCodes tempRestrictionCodes = new RestrictionCodes();
			tempRestrictionCodes.setLocalRestrictionCode(rs.getString("restrict_cd").trim());
			tempRestrictionCodes.setPublicRestrictionCode(rs.getString("restrict_cd_public").trim());

			Marital tempMarital = new Marital();
			tempMarital.setMaritalStatusCode(rs.getString("marital_stat").trim());

			DriversLicense tempDriversLicense = new DriversLicense();
			tempDriversLicense.setNumber(rs.getString("drivers_lic_nbr").trim());
			tempDriversLicense.setStateCode(rs.getString("drivers_lic_st").trim());

			EmergencyContact tempEmergencyContact = new EmergencyContact();
			tempEmergencyContact.setName(rs.getString("emer_contact").trim());
			tempEmergencyContact.setAreaCode(rs.getString("emer_phone_ac").trim());
			tempEmergencyContact.setPhoneNumber(rs.getString("emer_phone_nbr").trim());
			tempEmergencyContact.setExtention(rs.getString("emer_phone_ext").trim());
			tempEmergencyContact.setRelationship(rs.getString("emer_rel").trim());
			tempEmergencyContact.setEmergencyNotes(rs.getString("emer_note").trim());

			tempDemoInfo.setName(tempName);
			tempDemoInfo.setMailAddr(tempMailAddr);
			tempDemoInfo.setAltAddr(tempAltAddr);
			tempDemoInfo.setHomePhone(tempHomePhone);
			tempDemoInfo.setWorkPhone(tempWorkPhone);
			tempDemoInfo.setCellPhone(tempCellPhone);
			tempDemoInfo.setEmail(tempEmail);
			tempDemoInfo.setRestrictionCodes(tempRestrictionCodes);
			tempDemoInfo.setMarital(tempMarital);

			tempDemoInfo.setDriversLicense(tempDriversLicense);
			tempDemoInfo.setEmergencyContact(tempEmergencyContact);
			
			return tempDemoInfo;
		}
	}

	@Override
	public DemoInfo getPendingDemoInfo(String employeeNumber, String sex, String dob, String staffId) {
		DemoInfo demoInfo = new DemoInfo(employeeNumber, sex, dob, staffId);
		demoInfo.setName(getPendingName(employeeNumber));
		demoInfo.setMailAddr(getPendingMailAddr(employeeNumber));
		demoInfo.setAltAddr(getPendingAltAddr(employeeNumber));
		demoInfo.setHomePhone(getPendingHomePhone(employeeNumber));
		demoInfo.setWorkPhone(getPendingWorkPhone(employeeNumber));
		demoInfo.setCellPhone(getPendingCellPhone(employeeNumber));
		demoInfo.setEmail(getPendingEmail(employeeNumber));
		demoInfo.setRestrictionCodes(getPendingRestrictionCodes(employeeNumber));
		demoInfo.setMarital(getPendingMarital(employeeNumber));
		demoInfo.setDriversLicense(getPendingDriversLicense(employeeNumber));
		demoInfo.setEmergencyContact(getPendingEmergencyContact(employeeNumber));

		return demoInfo;
	}

	private Name getPendingName(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT NAME_PRE_NEW, NAME_L_NEW, NAME_F_NEW, NAME_M_NEW, NAME_GEN_NEW ");
		sql.append("FROM ");
		sql.append(ReferenceDataService.LEGAL_NAME_TABLE);
		sql.append(" WHERE emp_nbr = :employeeNumber AND STAT_CD='P'");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		Name result;

		try {
			result = (Name) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new NamePendingMapper());
		} catch(EmptyResultDataAccessException e) {
			result = null;
		}
		return result;
	}

	private static class NamePendingMapper implements RowMapper {
		public Name mapRow(ResultSet rs, int rows) throws SQLException {
			Name tempName = new Name();
			tempName.setTitleString(rs.getString("NAME_PRE_NEW").trim());
			tempName.setLastName(rs.getString("NAME_L_NEW").trim());
			tempName.setFirstName(rs.getString("NAME_F_NEW").trim());
			tempName.setMiddleName(rs.getString("NAME_M_NEW").trim());
			tempName.setGenerationCode(rs.getString("NAME_GEN_NEW").trim());

			return tempName;
		}
	}

	private EmergencyContact getPendingEmergencyContact(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT emer_contact_new, emer_phone_ac_new, emer_phone_nbr_new, emer_phone_ext_new, emer_rel_new, emer_note_new ");
		sql.append(String.format("FROM %s ", ReferenceDataService.EMERGENCY_CONTACT_TABLE));
		sql.append("WHERE emp_nbr = :employeeNumber AND STAT_CD='P'");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		EmergencyContact result;

		try {
			result = (EmergencyContact) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new EmergencyContactPendingMapper());
		} catch(EmptyResultDataAccessException e) {
			result = null;
		}
		return result;
	}

	private static class EmergencyContactPendingMapper implements RowMapper {
		public EmergencyContact mapRow(ResultSet rs, int rows) throws SQLException {
			EmergencyContact tempContact = new EmergencyContact();

			tempContact.setName(rs.getString("emer_contact_new").trim());
			tempContact.setAreaCode(rs.getString("emer_phone_ac_new").trim());
			tempContact.setPhoneNumber(rs.getString("emer_phone_nbr_new").trim());
			tempContact.setExtention(rs.getString("emer_phone_ext_new").trim());
			tempContact.setRelationship(rs.getString("emer_rel_new").trim());
			tempContact.setEmergencyNotes(rs.getString("emer_note_new").trim());

			return tempContact;
		}
	}

	private DriversLicense getPendingDriversLicense(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT drivers_lic_nbr_new, drivers_lic_st_new ");
		sql.append(String.format("FROM %s ", ReferenceDataService.DRIVERS_LICENSE_TABLE));
		sql.append("WHERE emp_nbr = :employeeNumber AND STAT_CD='P'");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		DriversLicense result;

		try {
			result = (DriversLicense) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new DriversLicensePendingMapper());
		} catch(EmptyResultDataAccessException e) {
			result = null;
		}
		return result;
	}

	private static class DriversLicensePendingMapper implements RowMapper {
		public DriversLicense mapRow(ResultSet rs, int rows) throws SQLException {
			DriversLicense tempLicense = new DriversLicense();

			tempLicense.setNumber(rs.getString("drivers_lic_nbr_new").trim());
			tempLicense.setStateCode(rs.getString("drivers_lic_st_new").trim());

			return tempLicense;
		}
	}

	private Marital getPendingMarital(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT marital_stat_new ");
		sql.append(String.format("FROM %s ", ReferenceDataService.MARITAL_STATUS_TABLE));
		sql.append("WHERE emp_nbr = :employeeNumber AND STAT_CD='P'");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		Marital result;

		try {
			result = (Marital) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new MaritalPendingMapper());
		} catch(EmptyResultDataAccessException e) {
			result = null;
		}
		return result;
	}

	private static class MaritalPendingMapper implements RowMapper {
		public Marital mapRow(ResultSet rs, int rows) throws SQLException {
			Marital tempMarital = new Marital();

			tempMarital.setMaritalStatusCode(rs.getString("marital_stat_new").trim());

			return tempMarital;
		}
	}

	private RestrictionCodes getPendingRestrictionCodes(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT RESTRICT_CD_NEW, RESTRICT_CD_PUBLIC_NEW ");
		sql.append(String.format("FROM %s ", ReferenceDataService.RESTRICTION_CODE_TABLE));
		sql.append("WHERE emp_nbr = :employeeNumber AND STAT_CD='P'");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		RestrictionCodes result;

		try {
			result = (RestrictionCodes) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new RestrictionCodesPendingMapper());
		} catch(EmptyResultDataAccessException e) {
			result = null;
		}
		return result;
	}

	private static class RestrictionCodesPendingMapper implements RowMapper {
		public RestrictionCodes mapRow(ResultSet rs, int rows) throws SQLException {
			RestrictionCodes tempCodes = new RestrictionCodes();

			tempCodes.setLocalRestrictionCode(rs.getString("RESTRICT_CD_NEW").trim());
			tempCodes.setPublicRestrictionCode(rs.getString("RESTRICT_CD_PUBLIC_NEW").trim());

			return tempCodes;
		}
	}

	@Override
	public Email getPendingEmail(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT EMAIL_NEW, HM_EMAIL_NEW ");
		sql.append(String.format("FROM %s ", ReferenceDataService.EMAIL_TABLE));
		sql.append("WHERE emp_nbr = :employeeNumber AND STAT_CD='P'");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		Email result;

		try {
			result = (Email) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new EmailPendingMapper());
		} catch(EmptyResultDataAccessException e) {
			result = null;
		}
		return result;
	}

	private static class EmailPendingMapper implements RowMapper {
		public Email mapRow(ResultSet rs, int rows) throws SQLException {
			Email tempEmail = new Email();

			tempEmail.setWorkEmail(rs.getString("EMAIL_NEW").trim());
			tempEmail.setHomeEmail(rs.getString("HM_EMAIL_NEW").trim());

			return tempEmail;
		}
	}

	@Override
	public Email getCurrentEmail(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT email, hm_email ");

		sql.append("FROM BHR_EMP_DEMO ");
		sql.append("WHERE EMP_NBR = :employeeNumber");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		Email result;

		try {
			result = (Email) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new EmailCurrentMapper());
		} catch(EmptyResultDataAccessException e) {
			result = null;
		}
		return result;
	}

	private static class EmailCurrentMapper implements RowMapper {
		public Email mapRow(ResultSet rs, int rows) throws SQLException {
			Email tempEmail = new Email();

			tempEmail.setWorkEmail(rs.getString("email").trim());
			tempEmail.setHomeEmail(rs.getString("hm_email").trim());

			return tempEmail;
		}
	}

	private CellPhone getPendingCellPhone(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT PHONE_AREA_CELL_NEW, PHONE_NBR_CELL_NEW ");
		sql.append(String.format("FROM %s ", ReferenceDataService.CELL_PHONE_TABLE));
		sql.append("WHERE emp_nbr = :employeeNumber AND STAT_CD='P'");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		CellPhone result;

		try {
			result = (CellPhone) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new CellPhonePendingMapper());
		} catch(EmptyResultDataAccessException e) {
			result = null;
		}
		return result;
	}

	private static class CellPhonePendingMapper implements RowMapper {
		public CellPhone mapRow(ResultSet rs, int rows) throws SQLException {
			CellPhone tempCell = new CellPhone();

			tempCell.setAreaCode(rs.getString("PHONE_AREA_CELL_NEW").trim());
			tempCell.setPhoneNumber(rs.getString("PHONE_NBR_CELL_NEW").trim());

			return tempCell;
		}
	}

	private WorkPhone getPendingWorkPhone(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT PHONE_AREA_BUS_NEW, PHONE_NBR_BUS_NEW, BUS_PHONE_EXT_NEW ");
		sql.append(String.format("FROM %s ", ReferenceDataService.WORK_PHONE_TABLE));
		sql.append("WHERE emp_nbr = :employeeNumber AND STAT_CD='P'");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		WorkPhone result;

		try {
			result = (WorkPhone) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new WorkPhonePendingMapper());
		} catch(EmptyResultDataAccessException e) {
			result = null;
		}
		return result;
	}

	private static class WorkPhonePendingMapper implements RowMapper {
		public WorkPhone mapRow(ResultSet rs, int rows) throws SQLException {
			WorkPhone tempWork = new WorkPhone();

			tempWork.setAreaCode(rs.getString("PHONE_AREA_BUS_NEW").trim());
			tempWork.setPhoneNumber(rs.getString("PHONE_NBR_BUS_NEW").trim());
			tempWork.setExtention(rs.getString("BUS_PHONE_EXT_NEW").trim());

			return tempWork;
		}
	}

	private HomePhone getPendingHomePhone(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT PHONE_AREA_NEW, PHONE_NBR_NEW ");
		sql.append(String.format("FROM %s ", ReferenceDataService.HOME_PHONE_TABLE));
		sql.append("WHERE emp_nbr = :employeeNumber AND STAT_CD='P'");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		HomePhone result;

		try {
			result = (HomePhone) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new HomePhonePendingMapper());
		} catch(EmptyResultDataAccessException e) {
			result = null;
		}
		return result;
	}

	private static class HomePhonePendingMapper implements RowMapper {
		public HomePhone mapRow(ResultSet rs, int rows) throws SQLException {
			HomePhone tempHome = new HomePhone();

			tempHome.setAreaCode(rs.getString("PHONE_AREA_NEW").trim());
			tempHome.setPhoneNumber(rs.getString("PHONE_NBR_NEW").trim());

			return tempHome;
		}
	}

	private AltAddr getPendingAltAddr(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT SMR_ADDR_NBR_NEW, SMR_ADDR_STR_NEW, SMR_ADDR_APT_NEW, SMR_ADDR_CITY_NEW, ");
		sql.append("SMR_ADDR_ST_NEW, SMR_ADDR_ZIP_NEW, SMR_ADDR_ZIP4_NEW ");
		sql.append(String.format("FROM %s ", ReferenceDataService.ALT_MAIL_TABLE));
		sql.append("WHERE emp_nbr = :employeeNumber AND STAT_CD='P'");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		AltAddr result;

		try {
			result = (AltAddr) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new AltAddrPendingMapper());
		} catch(EmptyResultDataAccessException e) {
			result = null;
		}
		return result;
	}

	private static class AltAddrPendingMapper implements RowMapper {
		public AltAddr mapRow(ResultSet rs, int rows) throws SQLException {
			AltAddr tempAltAddr = new AltAddr();

			tempAltAddr.setNumber(rs.getString("SMR_ADDR_NBR_NEW").trim());
			tempAltAddr.setStreet(rs.getString("SMR_ADDR_STR_NEW").trim());
			tempAltAddr.setApartment(rs.getString("SMR_ADDR_APT_NEW").trim());
			tempAltAddr.setCity(rs.getString("SMR_ADDR_CITY_NEW").trim());
			tempAltAddr.setStateCode(rs.getString("SMR_ADDR_ST_NEW").trim());
			tempAltAddr.setZip(rs.getString("SMR_ADDR_ZIP_NEW").trim());
			tempAltAddr.setZipPlusFour(rs.getString("SMR_ADDR_ZIP4_NEW").trim());

			return tempAltAddr;
		}
	}

	private MailAddr getPendingMailAddr(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT ADDR_NBR_NEW, ADDR_STR_NEW, ADDR_APT_NEW, ADDR_CITY_NEW, ");
		sql.append("ADDR_ST_NEW, ADDR_ZIP_NEW, ADDR_ZIP4_NEW ");
		sql.append(String.format("FROM %s ", ReferenceDataService.MAIL_ADDR_TABLE));
		sql.append("WHERE emp_nbr = :employeeNumber AND STAT_CD='P'");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		MailAddr result;

		try {
			result = (MailAddr) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new MailAddrPendingMapper());
		} catch(EmptyResultDataAccessException e) {
			result = null;
		}
		return result;
	}

	private static class MailAddrPendingMapper implements RowMapper {
		public MailAddr mapRow(ResultSet rs, int rows) throws SQLException {
			MailAddr tempMailAddr = new MailAddr();

			tempMailAddr.setNumber(rs.getString("ADDR_NBR_NEW").trim());
			tempMailAddr.setStreet(rs.getString("ADDR_STR_NEW").trim());
			tempMailAddr.setApartment(rs.getString("ADDR_APT_NEW").trim());
			tempMailAddr.setCity(rs.getString("ADDR_CITY_NEW").trim());
			tempMailAddr.setStateCode(rs.getString("ADDR_ST_NEW").trim());
			tempMailAddr.setZip(rs.getString("ADDR_ZIP_NEW").trim());
			tempMailAddr.setZipPlusFour(rs.getString("ADDR_ZIP4_NEW").trim());

			return tempMailAddr;
		}
	}

	@Override
	public String getAltAddrFieldDisplay() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT opt_cd FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.ALT_MAIL_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result;
	}

	@Override
	public Boolean getAutoApproveAltAddr() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT auto_apprv FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.ALT_MAIL_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result.equalsIgnoreCase("Y");
	}

	@Override
	public Boolean getAutoApproveCellPhone() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT auto_apprv FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.CELL_PHONE_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result.equalsIgnoreCase("Y");
	}

	@Override
	public Boolean getAutoApproveDriversLicense() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT auto_apprv FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.DRIVERS_LICENSE_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result.equalsIgnoreCase("Y");
	}

	@Override
	public Boolean getAutoApproveEmail() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT auto_apprv FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.EMAIL_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result.equalsIgnoreCase("Y");
	}

	@Override
	public Boolean getAutoApproveEmergencyContact() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT auto_apprv FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.EMERGENCY_CONTACT_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result.equalsIgnoreCase("Y");
	}

	@Override
	public Boolean getAutoApproveHomePhone() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT auto_apprv FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.HOME_PHONE_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result.equalsIgnoreCase("Y");
	}

	@Override
	public Boolean getAutoApproveMailAddr() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT auto_apprv FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.MAIL_ADDR_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result.equalsIgnoreCase("Y");
	}

	@Override
	public Boolean getAutoApproveMarital() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT auto_apprv FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.MARITAL_STATUS_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result.equalsIgnoreCase("Y");
	}

	@Override
	public Boolean getAutoApproveName() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT auto_apprv FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.LEGAL_NAME_TABLE ));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result.equalsIgnoreCase("Y");
	}

	@Override
	public Boolean getAutoApproveRestrictionCodes() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT auto_apprv FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.RESTRICTION_CODE_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result.equalsIgnoreCase("Y");
	}

	@Override
	public Boolean getAutoApproveWorkPhone() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT auto_apprv FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.WORK_PHONE_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result.equalsIgnoreCase("Y");
	}

	@Override
	public String getCellPhoneFieldDisplay() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT opt_cd FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.CELL_PHONE_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result;
	}

	@Override
	public String getDriversLicenseFieldDisplay() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT opt_cd FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.DRIVERS_LICENSE_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result;
	}

	@Override
	public String getEmailFieldDisplay() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT opt_cd FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.EMAIL_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result;
	}

	@Override
	public String getEmergencyContactFieldDisplay() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT opt_cd FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.EMERGENCY_CONTACT_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result;
	}

	@Override
	public String getHomePhoneFieldDisplay() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT opt_cd FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.HOME_PHONE_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result;
	}

	@Override
	public String getMailAddrFieldDisplay() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT opt_cd FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.MAIL_ADDR_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result;
	}

	@Override
	public String getMaritalFieldDisplay() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT opt_cd FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.MARITAL_STATUS_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result;
	}

	@Override
	public String getNameFieldDisplay() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT opt_cd FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.LEGAL_NAME_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result;
	}

	@Override
	public String getRestrictionCodesFieldDisplay() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT opt_cd FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.RESTRICTION_CODE_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result;
	}

	@Override
	public String getWorkPhoneFieldDisplay() {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT DISTINCT opt_cd FROM BHR_EAP_DEMO_ASSGN_GRP WHERE grp_name = '%s' ", ReferenceDataService.WORK_PHONE_TABLE));
		MapSqlParameterSource params = new MapSqlParameterSource();

		String result = (String) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);

		return result;
	}

	@Override
	public void deleteRequestIfExists(String employeeNumber, String tableName) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ");
		sql.append(tableName);
		sql.append(" WHERE emp_nbr = :employeeNumber AND stat_cd = 'P'");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("employeeNumber", employeeNumber);

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public List <List <String>> getRequiredFields() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM BHR_EAP_DEMO_ASSGN_MBR");
		MapSqlParameterSource params = new MapSqlParameterSource();

		List <List <String>> result;

		try
		{
			result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new RequiredMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			result = null;
		}
		return result;
	}

	private static class RequiredMapper implements RowMapper {
		public List <String> mapRow(ResultSet rs, int rows) throws SQLException {
			List <String> tempList = new ArrayList <String> ();
			tempList.add(StringUtil.trim(rs.getString("grp_name")));
			tempList.add(StringUtil.trim(rs.getString("mbr_name")));
			tempList.add(StringUtil.trim(rs.getString("doc_reqrd")));
			tempList.add(StringUtil.trim(rs.getString("reqrd_field")));

			return tempList;
		}
	}

	@Override
	public Boolean isNewRow(String employeeNumber, String tableName) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT (*) FROM ");
		sql.append(tableName);
		sql.append(" WHERE emp_nbr = :employeeNumber AND APPRVD_DTS =''");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		Integer result;

		try
		{
			result = getNamedParameterJdbcTemplate().queryForInt(sql.toString(), params);
		}
		catch(EmptyResultDataAccessException e)
		{
			result = null;
		}
		return (result == null || result < 1);
	}

	private MapSqlParameterSource buildCommonParametersMap(Boolean isAutoApprove, String employeeNumber) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		java.sql.Timestamp requestTime = new java.sql.Timestamp(new Date().getTime());

		String statusCode;
		int approverId;
		String approvedTime;
		if(isAutoApprove)
		{
			statusCode = "A";
			approverId = 0;
			approvedTime = TimestampConverter.convertTimestamp(requestTime);
		}
		else
		{
			statusCode = "P";
			approverId = -1;
			approvedTime = "";
		}

		params.addValue("employeeNumber", employeeNumber);
		params.addValue("requestTime", TimestampConverter.convertTimestamp(requestTime));
		params.addValue("approvedTime", approvedTime);
		params.addValue("approverId", approverId, java.sql.Types.INTEGER);
		params.addValue("statusCode", statusCode);

		return params;
	}

	@Override
	public void insertNameRequest(Boolean isAutoApprove, String employeeNumber, Name newName, Name name) {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("INSERT INTO %s (emp_nbr, req_dts, name_pre, name_f, name_l, ", ReferenceDataService.LEGAL_NAME_TABLE)); 
		sql.append("name_m, name_gen, name_pre_new, name_f_new, name_l_new, name_m_new, name_gen_new, apprvd_dts, apprvr_usr_id, stat_cd ) ");
		sql.append("VALUES (:employeeNumber, :requestTime, :title, :firstName, :lastName, ");
		sql.append(":middleName, :generation, :titleNew, :firstNameNew, :lastNameNew, ");
		sql.append(":middleNameNew, :generationNew, :approvedTime, :approverId, :statusCode)");

		MapSqlParameterSource params = buildCommonParametersMap(isAutoApprove, employeeNumber);

		params.addValue("title", name.getTitleString());
		params.addValue("firstName", name.getFirstName());
		params.addValue("lastName", name.getLastName());
		params.addValue("middleName", name.getMiddleName());
		params.addValue("generation", name.getGenerationCode());

		params.addValue("titleNew", newName.getTitleString());
		params.addValue("firstNameNew", newName.getFirstName());
		params.addValue("lastNameNew", newName.getLastName());
		params.addValue("middleNameNew", newName.getMiddleName());
		params.addValue("generationNew", newName.getGenerationCode());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateNameRequest(Boolean isAutoApprove, String employeeNumber, Name newName, Name name) {
		deleteRequestIfExists(employeeNumber, ReferenceDataService.LEGAL_NAME_TABLE);
		insertNameRequest(isAutoApprove, employeeNumber, newName, name);
	}

	@Override
	public void updateNameApprove(String employeeNumber, Name newName) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_EMP_DEMO SET DEMO_REV_CD = 1, NAME_PRE = :titleNew, NAME_F =:firstNameNew, NAME_L =:lastNameNew, NAME_M =:middleNameNew, NAME_GEN =:generationNew ");
		sql.append(", module = :module ");
		sql.append("WHERE EMP_NBR = :employeeNumber ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("titleNew", newName.getTitleString());
		params.addValue("firstNameNew", newName.getFirstName());
		params.addValue("lastNameNew", newName.getLastName());
		params.addValue("middleNameNew", newName.getMiddleName());
		params.addValue("generationNew", newName.getGenerationCode());
		params.addValue("module", module);
		params.addValue("employeeNumber", employeeNumber);
		
		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void insertMailAddrRequest(Boolean isAutoApprove, String employeeNumber, MailAddr newMailAddr, MailAddr mailAddr) {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("INSERT INTO %s (emp_nbr, req_dts, addr_nbr, addr_str, addr_apt, ", ReferenceDataService.MAIL_ADDR_TABLE)); 
		sql.append("addr_city, addr_st, addr_zip, addr_zip4, addr_nbr_new, addr_str_new, addr_apt_new, "); 
		sql.append("addr_city_new, addr_st_new, addr_zip_new, addr_zip4_new, "); 
		sql.append("apprvd_dts, apprvr_usr_id, stat_cd) ");
		sql.append("VALUES (:employeeNumber, :requestTime, :number, :street, :apartment, ");
		sql.append(":city, :state, :zip, :zip4, :numberNew, :streetNew, :apartmentNew, ");
		sql.append(":cityNew, :stateNew, :zipNew, :zip4New, ");
		sql.append(":approvedTime, :approverId, :statusCode)");

		MapSqlParameterSource params = buildCommonParametersMap(isAutoApprove, employeeNumber);

		params.addValue("number", mailAddr.getNumber());
		params.addValue("street", mailAddr.getStreet());
		params.addValue("apartment", mailAddr.getApartment());
		params.addValue("city", mailAddr.getCity());
		params.addValue("state", mailAddr.getStateCode());
		params.addValue("zip", mailAddr.getZip());
		params.addValue("zip4", mailAddr.getZipPlusFour());

		params.addValue("numberNew", newMailAddr.getNumber());
		params.addValue("streetNew", newMailAddr.getStreet());
		params.addValue("apartmentNew", newMailAddr.getApartment());
		params.addValue("cityNew", newMailAddr.getCity());
		params.addValue("stateNew", newMailAddr.getStateCode());
		params.addValue("zipNew", newMailAddr.getZip());
		params.addValue("zip4New", newMailAddr.getZipPlusFour());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateMailAddrApprove(String employeeNumber, MailAddr newMailAddr) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_EMP_DEMO SET addr_nbr = :number, addr_str =:street, addr_apt =:apartment, ");
		sql.append("addr_city =:city, addr_st =:state, addr_zip =:zip, addr_zip4 =:zip4 ");
		sql.append(", module = :module ");
		sql.append("WHERE EMP_NBR = :employeeNumber ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("number", newMailAddr.getNumber());
		params.addValue("street", newMailAddr.getStreet());
		params.addValue("apartment", newMailAddr.getApartment());
		params.addValue("city", newMailAddr.getCity());
		params.addValue("state", newMailAddr.getStateCode());
		params.addValue("zip", newMailAddr.getZip());
		params.addValue("zip4", newMailAddr.getZipPlusFour());
		params.addValue("module", module);

		params.addValue("employeeNumber", employeeNumber);

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateMailAddrRequest(Boolean isAutoApprove, String employeeNumber, MailAddr newMailAddr, MailAddr mailAddr) {
		deleteRequestIfExists(employeeNumber, ReferenceDataService.MAIL_ADDR_TABLE);
		insertMailAddrRequest(isAutoApprove, employeeNumber, newMailAddr, mailAddr);
	}

	@Override
	public void insertAltAddrRequest(Boolean isAutoApprove, String employeeNumber, AltAddr newAltAddr, AltAddr altAddr) {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("INSERT INTO %s (emp_nbr, req_dts, smr_addr_nbr, smr_addr_str, smr_addr_apt, ", ReferenceDataService.ALT_MAIL_TABLE)); 
		sql.append("smr_addr_city, smr_addr_st, smr_addr_zip, smr_addr_zip4, smr_addr_nbr_new, smr_addr_str_new, smr_addr_apt_new, "); 
		sql.append("smr_addr_city_new, smr_addr_st_new, smr_addr_zip_new, smr_addr_zip4_new, "); 
		sql.append("apprvd_dts, apprvr_usr_id, stat_cd) ");
		sql.append("VALUES (:employeeNumber, :requestTime, :number, :street, :apartment, ");
		sql.append(":city, :state, :zip, :zip4, :numberNew, :streetNew, :apartmentNew, ");
		sql.append(":cityNew, :stateNew, :zipNew, :zip4New, ");
		sql.append(":approvedTime, :approverId, :statusCode)");

		MapSqlParameterSource params = buildCommonParametersMap(isAutoApprove, employeeNumber);

		params.addValue("number", altAddr.getNumber());
		params.addValue("street", altAddr.getStreet());
		params.addValue("apartment", altAddr.getApartment());
		params.addValue("city", altAddr.getCity());
		params.addValue("state", altAddr.getStateCode());
		params.addValue("zip", altAddr.getZip());
		params.addValue("zip4", altAddr.getZipPlusFour());

		params.addValue("numberNew", newAltAddr.getNumber());
		params.addValue("streetNew", newAltAddr.getStreet());
		params.addValue("apartmentNew", newAltAddr.getApartment());
		params.addValue("cityNew", newAltAddr.getCity());
		params.addValue("stateNew", newAltAddr.getStateCode());
		params.addValue("zipNew", newAltAddr.getZip());
		params.addValue("zip4New", newAltAddr.getZipPlusFour());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateAltAddrApprove(String employeeNumber, AltAddr newAltAddr) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_EMP_DEMO SET smr_addr_nbr = :number, smr_addr_str =:street, smr_addr_apt =:apartment, ");
		sql.append("smr_addr_city =:city, smr_addr_st =:state, smr_addr_zip =:zip, smr_addr_zip4 =:zip4 ");
		sql.append(", module = :module ");
		sql.append("WHERE EMP_NBR = :employeeNumber ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("number", newAltAddr.getNumber());
		params.addValue("street", newAltAddr.getStreet());
		params.addValue("apartment", newAltAddr.getApartment());
		params.addValue("city", newAltAddr.getCity());
		params.addValue("state", newAltAddr.getStateCode());
		params.addValue("zip", newAltAddr.getZip());
		params.addValue("zip4", newAltAddr.getZipPlusFour());
		params.addValue("module", module);

		params.addValue("employeeNumber", employeeNumber);

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateAltAddrRequest(Boolean isAutoApprove, String employeeNumber, AltAddr newAltAddr, AltAddr altAddr) {
		deleteRequestIfExists(employeeNumber, ReferenceDataService.ALT_MAIL_TABLE);
		insertAltAddrRequest(isAutoApprove, employeeNumber, newAltAddr, altAddr);
	}

	@Override
	public void insertHomePhoneRequest(Boolean isAutoApprove, String employeeNumber, HomePhone newHomePhone, HomePhone homePhone) {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("INSERT INTO %s (emp_nbr, req_dts, phone_area, phone_nbr, phone_area_new, phone_nbr_new, ", ReferenceDataService.HOME_PHONE_TABLE)); 
		sql.append("apprvd_dts, apprvr_usr_id, stat_cd) ");
		sql.append("VALUES (:employeeNumber, :requestTime, :areaCode, :number, :areaCodeNew, :numberNew, ");
		sql.append(":approvedTime, :approverId, :statusCode)");

		MapSqlParameterSource params = buildCommonParametersMap(isAutoApprove, employeeNumber);

		params.addValue("areaCode", homePhone.getAreaCode());
		params.addValue("number", homePhone.getPhoneNumber());

		params.addValue("areaCodeNew", newHomePhone.getAreaCode());
		params.addValue("numberNew", newHomePhone.getPhoneNumber());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateHomePhoneApprove(String employeeNumber, HomePhone newHomePhone) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_EMP_DEMO SET phone_area = :areaCode, phone_nbr =:number ");
		sql.append(", module = :module ");
		sql.append("WHERE EMP_NBR = :employeeNumber ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("areaCode", newHomePhone.getAreaCode());
		params.addValue("number", newHomePhone.getPhoneNumber());
		params.addValue("module", module);
		params.addValue("employeeNumber", employeeNumber);

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateHomePhoneRequest(Boolean isAutoApprove, String employeeNumber, HomePhone newHomePhone, HomePhone homePhone) {
		deleteRequestIfExists(employeeNumber, ReferenceDataService.HOME_PHONE_TABLE);
		insertHomePhoneRequest(isAutoApprove, employeeNumber, newHomePhone, homePhone);
	}

	@Override
	public void insertWorkPhoneRequest(Boolean isAutoApprove, String employeeNumber, WorkPhone newWorkPhone, WorkPhone workPhone) {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("INSERT INTO %s (emp_nbr, req_dts, phone_area_bus, phone_nbr_bus, bus_phone_ext, ", ReferenceDataService.WORK_PHONE_TABLE));
		sql.append("phone_area_bus_new, phone_nbr_bus_new, bus_phone_ext_new, "); 
		sql.append("apprvd_dts, apprvr_usr_id, stat_cd) ");
		sql.append("VALUES (:employeeNumber, :requestTime, :areaCode, :number, :extention, :areaCodeNew, :numberNew, :extentionNew, ");
		sql.append(":approvedTime, :approverId, :statusCode)");

		MapSqlParameterSource params = buildCommonParametersMap(isAutoApprove, employeeNumber);

		params.addValue("areaCode", workPhone.getAreaCode());
		params.addValue("number", workPhone.getPhoneNumber());
		params.addValue("extention", workPhone.getExtention());

		params.addValue("areaCodeNew", newWorkPhone.getAreaCode());
		params.addValue("numberNew", newWorkPhone.getPhoneNumber());
		params.addValue("extentionNew", newWorkPhone.getExtention());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateWorkPhoneApprove(String employeeNumber, WorkPhone newWorkPhone) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_EMP_DEMO SET phone_area_bus = :areaCode, phone_nbr_bus =:number, bus_phone_ext =:extention ");
		sql.append(", module = :module ");
		sql.append("WHERE EMP_NBR = :employeeNumber ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("areaCode", newWorkPhone.getAreaCode());
		params.addValue("number", newWorkPhone.getPhoneNumber());
		params.addValue("extention", newWorkPhone.getPhoneNumber());
		params.addValue("module", module);

		params.addValue("employeeNumber", employeeNumber);

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateWorkPhoneRequest(Boolean isAutoApprove, String employeeNumber, WorkPhone newWorkPhone, WorkPhone workPhone) {
		deleteRequestIfExists(employeeNumber, ReferenceDataService.WORK_PHONE_TABLE);
		insertWorkPhoneRequest(isAutoApprove, employeeNumber, newWorkPhone, workPhone);
	}

	@Override
	public void insertCellPhoneRequest(Boolean isAutoApprove, String employeeNumber, CellPhone newCellPhone, CellPhone cellPhone) {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("INSERT INTO %s (emp_nbr, req_dts, phone_area_cell, phone_nbr_cell, ", ReferenceDataService.CELL_PHONE_TABLE));
		sql.append("phone_area_cell_new, phone_nbr_cell_new, ");
		sql.append("apprvd_dts, apprvr_usr_id, stat_cd) ");
		sql.append("VALUES (:employeeNumber, :requestTime, :areaCode, :number, :areaCodeNew, :numberNew, ");
		sql.append(":approvedTime, :approverId, :statusCode)");

		MapSqlParameterSource params = buildCommonParametersMap(isAutoApprove, employeeNumber);

		params.addValue("areaCode", cellPhone.getAreaCode());
		params.addValue("number", cellPhone.getPhoneNumber());

		params.addValue("areaCodeNew", newCellPhone.getAreaCode());
		params.addValue("numberNew", newCellPhone.getPhoneNumber());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateCellPhoneApprove(String employeeNumber, CellPhone newCellPhone) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_EMP_DEMO SET phone_area_cell = :areaCode, phone_nbr_cell =:number ");
		sql.append(", module = :module ");
		sql.append("WHERE EMP_NBR = :employeeNumber ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("areaCode", newCellPhone.getAreaCode());
		params.addValue("number", newCellPhone.getPhoneNumber());
		params.addValue("module", module);

		params.addValue("employeeNumber", employeeNumber);

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateCellPhoneRequest(Boolean isAutoApprove, String employeeNumber, CellPhone newCellPhone, CellPhone cellPhone) {
		deleteRequestIfExists(employeeNumber, ReferenceDataService.CELL_PHONE_TABLE);
		insertCellPhoneRequest(isAutoApprove, employeeNumber, newCellPhone, cellPhone);
	}

	@Override
	public void insertRestrictionCodesRequest(Boolean isAutoApprove, String employeeNumber, RestrictionCodes newRestrictionCodes, RestrictionCodes restrictionCodes) {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("INSERT INTO %s (emp_nbr, req_dts, restrict_cd, restrict_cd_public, ", ReferenceDataService.RESTRICTION_CODE_TABLE));
		sql.append("restrict_cd_new, restrict_cd_public_new, "); 
		sql.append("apprvd_dts, apprvr_usr_id, stat_cd) ");
		sql.append("VALUES (:employeeNumber, :requestTime, :restrictLocal, :restrictPublic, :restrictLocalNew, :restrictPublicNew, ");
		sql.append(":approvedTime, :approverId, :statusCode)");

		MapSqlParameterSource params = buildCommonParametersMap(isAutoApprove, employeeNumber);

		params.addValue("restrictLocal", restrictionCodes.getLocalRestrictionCode());
		params.addValue("restrictPublic", restrictionCodes.getPublicRestrictionCode());

		params.addValue("restrictLocalNew", newRestrictionCodes.getLocalRestrictionCode());
		params.addValue("restrictPublicNew", newRestrictionCodes.getPublicRestrictionCode());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateRestrictionCodesApprove(String employeeNumber, RestrictionCodes newRestrictionCodes) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_EMP_DEMO SET restrict_cd = :restrictLocal, restrict_cd_public =:restrictPublic ");
		sql.append(", module = :module ");
		sql.append("WHERE EMP_NBR = :employeeNumber ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("restrictLocal", newRestrictionCodes.getLocalRestrictionCode());
		params.addValue("restrictPublic", newRestrictionCodes.getPublicRestrictionCode());
		params.addValue("module", module);

		params.addValue("employeeNumber", employeeNumber);

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateRestrictionCodesRequest(Boolean isAutoApprove, String employeeNumber, 
												RestrictionCodes newRestrictionCodes, RestrictionCodes restrictionCodes) {
		deleteRequestIfExists(employeeNumber, ReferenceDataService.RESTRICTION_CODE_TABLE);
		insertRestrictionCodesRequest(isAutoApprove, employeeNumber, newRestrictionCodes, restrictionCodes);
	}

	@Override
	public void insertMaritalRequest(Boolean isAutoApprove, String employeeNumber, Marital newMarital, Marital marital) {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("INSERT INTO %s (emp_nbr, req_dts, marital_stat, marital_stat_new, ", ReferenceDataService.MARITAL_STATUS_TABLE));
		sql.append("apprvd_dts, apprvr_usr_id, stat_cd) ");
		sql.append("VALUES (:employeeNumber, :requestTime, :marital, :maritalNew, ");
		sql.append(":approvedTime, :approverId, :statusCode)");

		MapSqlParameterSource params = buildCommonParametersMap(isAutoApprove, employeeNumber);

		params.addValue("marital", marital.getMaritalStatusCode());

		params.addValue("maritalNew", newMarital.getMaritalStatusCode());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateMaritalApprove(String employeeNumber, Marital newMarital) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_EMP_DEMO SET marital_stat = :marital ");
		sql.append(", module = :module ");
		sql.append("WHERE EMP_NBR = :employeeNumber ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("marital", newMarital.getMaritalStatusCode());
		params.addValue("module", module);

		params.addValue("employeeNumber", employeeNumber);

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateMaritalRequest(Boolean isAutoApprove, String employeeNumber, Marital newMarital, Marital marital) {
		deleteRequestIfExists(employeeNumber, ReferenceDataService.MARITAL_STATUS_TABLE);
		insertMaritalRequest(isAutoApprove, employeeNumber, newMarital, marital);
	}

	@Override
	public void insertDriversLicenseRequest(Boolean isAutoApprove, String employeeNumber, DriversLicense newDriversLicense, DriversLicense driversLicense) {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("INSERT INTO %s (emp_nbr, req_dts, drivers_lic_nbr, drivers_lic_st, ", ReferenceDataService.DRIVERS_LICENSE_TABLE));
		sql.append("drivers_lic_nbr_new, drivers_lic_st_new, "); 
		sql.append("apprvd_dts, apprvr_usr_id, stat_cd) ");
		sql.append("VALUES (:employeeNumber, :requestTime, :number, :state, :numberNew, :stateNew, ");
		sql.append(":approvedTime, :approverId, :statusCode)");

		MapSqlParameterSource params = buildCommonParametersMap(isAutoApprove, employeeNumber);

		params.addValue("number", driversLicense.getNumber());
		params.addValue("state", driversLicense.getStateCode());

		params.addValue("numberNew", newDriversLicense.getNumber());
		params.addValue("stateNew", newDriversLicense.getStateCode());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateDriversLicenseApprove(String employeeNumber, DriversLicense newDriversLicense) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_EMP_DEMO SET drivers_lic_nbr = :number, drivers_lic_st =:state ");
		sql.append(", module = :module ");
		sql.append("WHERE EMP_NBR = :employeeNumber ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("number", newDriversLicense.getNumber());
		params.addValue("state", newDriversLicense.getStateCode());
		params.addValue("module", module);

		params.addValue("employeeNumber", employeeNumber);

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateDriversLicenseRequest(Boolean isAutoApprove, String employeeNumber, DriversLicense newDriversLicense, DriversLicense driversLicense) {
		deleteRequestIfExists(employeeNumber, ReferenceDataService.DRIVERS_LICENSE_TABLE);
		insertDriversLicenseRequest(isAutoApprove, employeeNumber, newDriversLicense, driversLicense);
	}

	@Override
	public void insertEmergencyContactRequest(Boolean isAutoApprove, String employeeNumber, EmergencyContact newEmergencyContact, EmergencyContact emergencyContact) {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("INSERT INTO %s (emp_nbr, req_dts, ", ReferenceDataService.EMERGENCY_CONTACT_TABLE));
		sql.append("emer_contact, emer_phone_ac, emer_phone_nbr, emer_phone_ext, emer_rel, emer_note, ");
		sql.append("emer_contact_new, emer_phone_ac_new, emer_phone_nbr_new, emer_phone_ext_new, emer_rel_new, emer_note_new, ");
		sql.append("apprvd_dts, apprvr_usr_id, stat_cd ) ");
		sql.append("VALUES (:employeeNumber, :requestTime, ");
		sql.append(":name, :phoneAreaCode, :phoneNumber, :phoneExtention, :relationship, :note, ");
		sql.append(":nameNew, :phoneAreaCodeNew, :phoneNumberNew, :phoneExtentionNew, :relationshipNew, :noteNew, ");
		sql.append(":approvedTime, :approverId, :statusCode)");

		MapSqlParameterSource params = buildCommonParametersMap(isAutoApprove, employeeNumber);

		params.addValue("name", emergencyContact.getName());
		params.addValue("phoneAreaCode", emergencyContact.getAreaCode());
		params.addValue("phoneNumber", emergencyContact.getPhoneNumber());
		params.addValue("phoneExtention", emergencyContact.getExtention());
		params.addValue("relationship", emergencyContact.getRelationship());
		params.addValue("note", emergencyContact.getEmergencyNotes());

		params.addValue("nameNew", newEmergencyContact.getName());
		params.addValue("phoneAreaCodeNew", newEmergencyContact.getAreaCode());
		params.addValue("phoneNumberNew", newEmergencyContact.getPhoneNumber());
		params.addValue("phoneExtentionNew", newEmergencyContact.getExtention());
		params.addValue("relationshipNew", newEmergencyContact.getRelationship());
		params.addValue("noteNew", newEmergencyContact.getEmergencyNotes());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateEmergencyContactApprove(String employeeNumber, EmergencyContact newEmergencyContact) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_EMP_DEMO SET emer_contact = :name, emer_phone_ac =:phoneAreaCode, emer_phone_nbr =:phoneNumber, ");
		sql.append("emer_phone_ext =:phoneExtention, emer_rel =:relationship, emer_note =:note ");
		sql.append(", module = :module ");
		sql.append("WHERE EMP_NBR = :employeeNumber ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("name", newEmergencyContact.getName());
		params.addValue("phoneAreaCode", newEmergencyContact.getAreaCode());
		params.addValue("phoneNumber", newEmergencyContact.getPhoneNumber());
		params.addValue("phoneExtention", newEmergencyContact.getExtention());
		params.addValue("relationship", newEmergencyContact.getRelationship());
		params.addValue("note", newEmergencyContact.getEmergencyNotes());
		params.addValue("module", module);

		params.addValue("employeeNumber", employeeNumber);

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateEmergencyContactRequest(Boolean isAutoApprove, String employeeNumber, EmergencyContact newEmergencyContact, EmergencyContact emergencyContact) {
		deleteRequestIfExists(employeeNumber, ReferenceDataService.EMERGENCY_CONTACT_TABLE);
		insertEmergencyContactRequest(isAutoApprove, employeeNumber, newEmergencyContact, emergencyContact);
	}

	@Override
	public void insertEmailRequest(Boolean isAutoApprove, String employeeNumber, Email newEmail, Email email) { 
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("INSERT INTO %s (emp_nbr, req_dts, ", ReferenceDataService.EMAIL_TABLE));
		sql.append("email, hm_email, email_new, hm_email_new, ");
		sql.append("apprvd_dts, apprvr_usr_id, stat_cd ) ");
		sql.append("VALUES (:employeeNumber, :requestTime, ");
		sql.append(":workEmail, :homeEmail, :workEmailNew, :homeEmailNew, ");
		sql.append(":approvedTime, :approverId, :statusCode)");

		MapSqlParameterSource params = buildCommonParametersMap(isAutoApprove, employeeNumber);

		params.addValue("workEmail", email.getWorkEmail());
		params.addValue("homeEmail", email.getHomeEmail());

		params.addValue("workEmailNew", newEmail.getWorkEmail());
		params.addValue("homeEmailNew", newEmail.getHomeEmail());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateEmailApprove(String employeeNumber, Email newEmail) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_EMP_DEMO SET email = :workEmail, hm_email =:homeEmail ");
		sql.append(", module = :module ");
		sql.append("WHERE EMP_NBR = :employeeNumber ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("workEmail", newEmail.getWorkEmail());
		params.addValue("homeEmail", newEmail.getHomeEmail());
		params.addValue("module", module);

		params.addValue("employeeNumber", employeeNumber);

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public void updateEmailRequest(Boolean isAutoApprove, String employeeNumber, Email newEmail, Email email) {
		deleteRequestIfExists(employeeNumber, ReferenceDataService.EMAIL_TABLE);
		insertEmailRequest(isAutoApprove, employeeNumber, newEmail, email);	
	}

	@Override
	public User getApproverById(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT emp_nbr, name_f, name_l, email, hm_email FROM BHR_EMP_DEMO ");
		sql.append("WHERE emp_nbr = :employeeNumber");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		User result;

		try
		{
			result = (User) getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new UserMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			result = null;
		}
		return result;
	}

	private static class UserMapper implements RowMapper {
		public User mapRow(ResultSet rs, int rows) throws SQLException {
			User tempUser = new User();

			tempUser.setEmployeeNumber(StringUtil.trim(rs.getString("emp_nbr")));
			String fName = StringUtil.trim(rs.getString("name_f"));
			tempUser.setFirstName(fName);
			String lName = StringUtil.trim(rs.getString("name_l"));
			tempUser.setLastName(lName);
			if(rs.getString("email") != null)
			{
				tempUser.setWorkEmail(StringUtil.trim(rs.getString("email")));
				tempUser.setWorkEmailVerification(tempUser.getWorkEmail());
			}
			if(rs.getString("hm_email") != null)
			{
				tempUser.setHomeEmail(StringUtil.trim(rs.getString("hm_email")));
				tempUser.setHomeEmailVerification(tempUser.getHomeEmail());
			}
			return tempUser;
		}
	}

	@Override
	public TraqsData getTraqsData() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT automate_traqs_rec = (CASE WHEN ( isNull(trim(automate_traqs_rec), '') = '' ) THEN 'N' ELSE automate_traqs_rec END )," +
				"automate_trs_rpt_mon = (CASE WHEN ( isNull(trim(automate_trs_rpt_mon), '') = '' ) THEN '00' ELSE automate_trs_rpt_mon END ), " +
				"automate_trs_rpt_yr = (CASE WHEN ( isNull(trim(automate_trs_rpt_yr), '') = '' ) THEN '0' ELSE automate_trs_rpt_yr END ) " +
				"FROM BHR_OPTIONS ");
		sql.append("WHERE gl_file_id = 'C'" );
		MapSqlParameterSource params = new MapSqlParameterSource();

		return (TraqsData)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, new TraqsMapper());
	}

	private static class TraqsMapper implements RowMapper {
		public TraqsData mapRow(ResultSet rs, int rows) throws SQLException {
			TraqsData traqs = new TraqsData(StringUtil.trim(rs.getString("automate_traqs_rec")), rs.getInt("automate_trs_rpt_yr"), rs.getInt("automate_trs_rpt_mon"));
			return traqs;
		}
	}

	@Override
	public Boolean isTrsEmployee(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM BHR_EMP_PAY ");
		sql.append("WHERE emp_nbr = :employeeNumber AND cyr_nyr_flg = 'C' AND stat_cd = '1' AND trs_stat_cd = '1'" );
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);

		int result = getNamedParameterJdbcTemplate().queryForInt(sql.toString(), params);

		return result > 0;
	}

	@Override
	public String getEmployeeStaffId(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT staff_id FROM BHR_EMP_DEMO ");
		sql.append("WHERE emp_nbr = :employeeNumber" );
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		return (String)getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);
	}

	@Override
	public void createMD25Record(String staffId, TraqsData traqs, Name oldName, Name newName) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO BHR_TRS_MD25 (staff_id, rpt_yr, rpt_mon, orig_name_f, orig_name_l, " ); 
		sql.append("orig_name_m, orig_name_gen, new_name_f, new_name_l, new_name_m, new_name_gen, ");
		sql.append("adj_rsn_cd, new_dob, new_sex, new_staff_id, new_staff_id_cd, new_us_citizen_cd, orig_dob, orig_sex, orig_staff_id, orig_staff_id_cd )");
		sql.append("VALUES (:staffId, :year, :month, :firstName, :lastName, ");
		sql.append(":middleName, :generation, :firstNameNew, :lastNameNew, ");
		sql.append(":middleNameNew, :generationNew,'','','','','','','','','','')");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("staffId", staffId);
		params.addValue("year", traqs.getTrsReportYear());
		params.addValue("month", String.format("%02d", traqs.getTrsReportMonth()));

		params.addValue("firstName", oldName.getFirstName());
		params.addValue("lastName", oldName.getLastName());
		params.addValue("middleName", oldName.getMiddleName());
		params.addValue("generation", oldName.getGenerationCode());

		params.addValue("firstNameNew", getNewMDEntry(oldName.getFirstName(), newName.getFirstName()));
		params.addValue("lastNameNew", getNewMDEntry(oldName.getLastName(), newName.getLastName()));
		params.addValue("middleNameNew", getNewMDEntry(oldName.getMiddleName(), newName.getMiddleName()));
		params.addValue("generationNew", getNewMDEntry(oldName.getGenerationCode(), newName.getGenerationCode()));

		getNamedParameterJdbcTemplate().update(sql.toString(), params);	
	}

	@Override
	public void updateMD25Record(String staffId, TraqsData traqs, Name oldName, Name newName) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_TRS_MD25 SET new_name_f =:firstName, new_name_gen =:generation, new_name_l =:lastName, new_name_m =:middleName ");
		sql.append("WHERE staff_id =:staffId AND rpt_mon =:month AND rpt_yr =:year");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("firstName", getNewMDEntry(oldName.getFirstName(), newName.getFirstName()));
		params.addValue("generation", getNewMDEntry(oldName.getGenerationCode(), newName.getGenerationCode()));
		params.addValue("lastName", getNewMDEntry(oldName.getLastName(), newName.getLastName()));
		params.addValue("middleName", getNewMDEntry(oldName.getMiddleName(), newName.getMiddleName()));

		params.addValue("staffId", staffId);
		params.addValue("month", String.format("%02d", traqs.getTrsReportMonth()));
		params.addValue("year", traqs.getTrsReportYear().toString());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	private Object getNewMDEntry(String oldEntry, String newEntry) {
		if(oldEntry.equals(newEntry))
			return "";
		else if( !oldEntry.equals("") && newEntry.equals(""))
			return "xxxxxxxxxxxxxxxxxxxxxxxxx";
		else
			return newEntry;
	}

	@Override
	public Boolean md25EntryExists(String staffId, TraqsData traqs) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM BHR_TRS_MD25 ");
		sql.append("WHERE staff_id =:staffId AND rpt_yr =:year AND rpt_mon =:month" );
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);
		params.addValue("year", traqs.getTrsReportYear());
		params.addValue("month", traqs.getTrsReportMonth());

		int result = getNamedParameterJdbcTemplate().queryForInt(sql.toString(), params);

		return result > 0;
	}

	@Override
	public void updateMD20Record(String staffId, TraqsData oldEntry, Name newName) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BHR_TRS_MD20 SET name_f = :firstName, name_gen =:generation, name_l =:lastName, name_m =:middleName ");
		sql.append("WHERE staff_id =:staffId AND rpt_mon =:month AND rpt_yr =:year");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("firstName", newName.getFirstName());
		params.addValue("generation", newName.getGenerationCode());
		params.addValue("lastName", newName.getLastName());
		params.addValue("middleName", newName.getMiddleName());

		params.addValue("staffId", staffId);
		params.addValue("month", String.format("%02d", oldEntry.getTrsReportMonth()));
		params.addValue("year", oldEntry.getTrsReportYear().toString());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public List<TraqsData> getMD20EntriesForEmployee(String staffId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT staff_id, rpt_mon, rpt_yr FROM BHR_TRS_MD20 ");
		sql.append("WHERE staff_id =:staffId" );
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);
		return (List<TraqsData>)getNamedParameterJdbcTemplate().query(sql.toString(), params, new MD20Mapper());
	}

	private static class MD20Mapper implements RowMapper {
		public TraqsData mapRow(ResultSet rs, int rows) throws SQLException
		{
			TraqsData traqs = new TraqsData("Y", rs.getInt("rpt_yr"), rs.getInt("rpt_mon"));

			return traqs;
		}
	}

	@Override
	public void createMD30Record(String staffId, TraqsData traqs, MailAddr newMailAddr, HomePhone newHomePhone) { 

		StringBuilder sql = new StringBuilder();

		MapSqlParameterSource params = new MapSqlParameterSource();

		if (newMailAddr != null) {
			sql.append("INSERT INTO BHR_TRS_MD30_MD31 (staff_id, rpt_yr, rpt_mon, addr_apt, addr_city, " );
			sql.append("addr_nbr, addr_st, addr_str, addr_zip, addr_zip4, phone_area, phone_nbr, suppl_addr, suppl_cntry, suppl_deliv_name) ");
			sql.append("VALUES (:staffId, :year, :month, :apartment, :city, ");
			sql.append(":number, :state, :street, :zip, ");
			sql.append(":zip4, '','','','','')");

			params.addValue("apartment", newMailAddr.getApartment());
			params.addValue("city", newMailAddr.getCity());
			params.addValue("number", newMailAddr.getNumber());
			params.addValue("state", newMailAddr.getStateCode());
			params.addValue("street", newMailAddr.getStreet());
			params.addValue("zip", newMailAddr.getZip());
			params.addValue("zip4", newMailAddr.getZipPlusFour());
		}
		//cs20140430  Insert MD30/31 for home phone changes
		if (newHomePhone != null) {
			sql.append("INSERT INTO BHR_TRS_MD30_MD31 (staff_id, rpt_yr, rpt_mon, phone_area, phone_nbr) ");
			sql.append("VALUES (:staffId, :year, :month, :phone_area, :phone_nbr )");

			params.addValue("phone_area", newHomePhone.getAreaCode());
			params.addValue("phone_nbr", newHomePhone.getPhoneNumber());

		}

		params.addValue("staffId", staffId);
		params.addValue("year", traqs.getTrsReportYear());
		params.addValue("month", String.format("%02d", traqs.getTrsReportMonth()));

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	@Override
	public Boolean md30EntryExists(String staffId, TraqsData traqs) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM BHR_TRS_MD30_MD31 ");
		sql.append("WHERE staff_id =:staffId AND rpt_yr =:year AND rpt_mon =:month" );
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);
		params.addValue("year", traqs.getTrsReportYear());
		params.addValue("month", traqs.getTrsReportMonth());

		int result = getNamedParameterJdbcTemplate().queryForInt(sql.toString(), params);

		return result > 0;
	}

	@Override
	public void updateMD30Record(String staffId, TraqsData traqs, MailAddr newMailAddr, HomePhone newHomePhone) {
		StringBuilder sql = new StringBuilder();

		MapSqlParameterSource params = new MapSqlParameterSource();

		if (newMailAddr != null) {
			sql.append("UPDATE BHR_TRS_MD30_MD31 SET addr_apt = :apartment, addr_city =:city, addr_nbr =:number, addr_st =:state, addr_str =:street, addr_zip =:zip, addr_zip4 =:zip4 ");
			sql.append("WHERE staff_id =:staffId AND rpt_mon =:month AND rpt_yr =:year");

			params.addValue("apartment", newMailAddr.getApartment());
			params.addValue("city", newMailAddr.getCity());
			params.addValue("number", newMailAddr.getNumber());
			params.addValue("state", newMailAddr.getStateCode());
			params.addValue("street", newMailAddr.getStreet());
			params.addValue("zip", newMailAddr.getZip());
			params.addValue("zip4", newMailAddr.getZipPlusFour());
		}
		//cs20140430  Update home phone changes
		if (newHomePhone != null) {
			sql.append("UPDATE BHR_TRS_MD30_MD31 SET phone_area = :phone_area, phone_nbr =:phone_nbr ");
			sql.append("WHERE staff_id =:staffId AND rpt_mon =:month AND rpt_yr =:year");

			params.addValue("phone_area", newHomePhone.getAreaCode());
			params.addValue("phone_nbr", newHomePhone.getPhoneNumber());
		}

		params.addValue("staffId", staffId);
		params.addValue("month", String.format("%02d", traqs.getTrsReportMonth()));
		params.addValue("year", traqs.getTrsReportYear().toString());

		getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

/*	public boolean updateTEAMRecords(Map<String,String> modifiedFields) {
		MapSqlParameterSource params = new MapSqlParameterSource();

		// Get the Demographic Information for the Employee
		Map<String, String> demoInfo = getDemographicInfo(modifiedFields.get("emp_nbr"));
		String currentYr = "", currentMon = "";
		Map<String, String> ed20Fields = new HashMap<String, String> ();
		Map<String, String> ed25Fields = new HashMap<String, String> ();
		boolean lb_updEd25Only = true;
			
		// Get current Month and Year
		Calendar now = Calendar.getInstance();
		currentYr =  String.valueOf(now.get(Calendar.YEAR));
		currentMon = String.valueOf(now.get(Calendar.MONTH) + 1);
		if (Integer.parseInt(currentMon) < 10) {
			currentMon = "0" + currentMon;
		}
		
		//need to check to see if there already is an ed20 and ed25 records.
		//if there is an ed20 and ed25 record for the same month, then only the ed25 record is updated
		//If there is, then we need to capture the values that are already in the ed25 so that they are not lost when the record is changed for a different col
		//for example, if the ed25 was originally inserted to change the employee address, and now being changed to update the phone number,
		//when the record was being updated, the address change previously inserted was being lost and the ed25 record would contain only contain the phone number change.
		//program has been changed to capture the original record and now update with only the changed values.
		
//		if (ed20Exists(currentYr, currentMon, modifiedFields.get("emp_nbr"))) {
//			ed20Fields = getED20Rec(currentYr, currentMon, modifiedFields.get("emp_nbr"));
//		}
		
//		if (ed25Exists(currentYr, currentMon, modifiedFields.get("emp_nbr"))) {
//			ed25Fields = getED25Rec(currentYr, currentMon, modifiedFields.get("emp_nbr"));
//		}
		
		if (ed20Fields.size() > 0 &&
			ed25Fields.size() > 0) {
			lb_updEd25Only = true;
		}

		params.addValue("rptYr", currentYr);
		params.addValue("rptMon", currentMon);
		params.addValue("rptYrMon", currentYr + currentMon);
		params.addValue("empNbr", modifiedFields.get("emp_nbr"));
		
		
//		params.addValue("staffID", demoInfo.get("STAFF_ID"));
//		params.addValue("dob", demoInfo.get("DOB"));
//		params.addValue("sex", demoInfo.get("SEX"));
//		params.addValue("lName", (modifiedFields.get("name_l") == null) ? demoInfo.get("NAME_L") : modifiedFields.get("name_l"));
//		params.addValue("fName", (modifiedFields.get("name_f") == null) ? demoInfo.get("NAME_F") : modifiedFields.get("name_f"));
//		params.addValue("mName", (modifiedFields.get("name_m") == null) ? demoInfo.get("NAME_M") : modifiedFields.get("name_m"));
//		params.addValue("nameGen", (modifiedFields.get("name_gen") == null) ? demoInfo.get("NAME_GEN") : modifiedFields.get("name_gen"));
//		params.addValue("addrNbr", (modifiedFields.get("addr_nbr") == null) ? demoInfo.get("ADDR_NBR") : modifiedFields.get("addr_nbr"));
//		params.addValue("addrStr", (modifiedFields.get("addr_str") == null) ? demoInfo.get("ADDR_STR") : modifiedFields.get("addr_str"));
//		params.addValue("addrApt", (modifiedFields.get("addr_apt") == null) ? demoInfo.get("ADDR_APT") : modifiedFields.get("addr_apt"));
//		params.addValue("addrCity", (modifiedFields.get("addr_city") == null) ? demoInfo.get("ADDR_CITY") : modifiedFields.get("addr_city"));
//		params.addValue("addrSt", (modifiedFields.get("addr_st") == null) ? demoInfo.get("ADDR_ST") : modifiedFields.get("addr_st"));
//		params.addValue("addrZip", (modifiedFields.get("addr_zip") == null) ? demoInfo.get("ADDR_ZIP") : modifiedFields.get("addr_zip"));
//		params.addValue("addrZip4", (modifiedFields.get("addr_zip4") == null) ? demoInfo.get("ADDR_ZIP4") : modifiedFields.get("addr_zip4"));
//		params.addValue("addrProvince", "");
//		params.addValue("addrCtry", "");
//		params.addValue("postalCD", "");
//		params.addValue("email", (modifiedFields.get("email") == null) ? demoInfo.get("EMAIL") : modifiedFields.get("email"));
//		params.addValue("phoneArea", (modifiedFields.get("phone_area") == null) ? demoInfo.get("PHONE_AREA") : modifiedFields.get("phone_area"));
//		params.addValue("phoneNbr", (modifiedFields.get("phone_nbr") == null) ? demoInfo.get("PHONE_NBR") : modifiedFields.get("phone_nbr"));
		params.addValue("origStaffID", demoInfo.get("STAFF_ID"));
		params.addValue("origDob", demoInfo.get("DOB"));
		params.addValue("origSex", demoInfo.get("SEX"));
		params.addValue("origLName", (modifiedFields.get("orig_name_l") == null) ? demoInfo.get("NAME_L") : modifiedFields.get("orig_name_l"));
		params.addValue("origFName", (modifiedFields.get("orig_name_f") == null) ? demoInfo.get("NAME_F") : modifiedFields.get("orig_name_f"));
		params.addValue("origMName", (modifiedFields.get("orig_name_m") == null) ? demoInfo.get("NAME_M") : modifiedFields.get("orig_name_m"));
		params.addValue("origNameGen", (modifiedFields.get("orig_name_gen") == null) ? demoInfo.get("NAME_GEN") : modifiedFields.get("orig_name_gen"));
//		params.addValue("origAddrNbr", (modifiedFields.get("orig_addr_nbr") == null) ? demoInfo.get("ADDR_NBR") : modifiedFields.get("orig_addr_nbr"));
//		params.addValue("origAddrStr", (modifiedFields.get("orig_addr_str") == null) ? demoInfo.get("ADDR_STR") : modifiedFields.get("orig_addr_str"));
//		params.addValue("origAddrApt", (modifiedFields.get("orig_addr_apt") == null) ? demoInfo.get("ADDR_APT") : modifiedFields.get("orig_addr_apt"));
//		params.addValue("origAddrCity", (modifiedFields.get("orig_addr_city") == null) ? demoInfo.get("ADDR_CITY") : modifiedFields.get("orig_addr_city"));
//		params.addValue("origAddrSt", (modifiedFields.get("orig_addr_st") == null) ? demoInfo.get("ADDR_ST") : modifiedFields.get("orig_addr_st"));
//		params.addValue("origAddrZip", (modifiedFields.get("orig_addr_zip") == null) ? demoInfo.get("ADDR_ZIP") : modifiedFields.get("orig_addr_zip"));
//		params.addValue("origAddrZip4", (modifiedFields.get("orig_addr_zip4") == null) ? demoInfo.get("ADDR_ZIP4") : modifiedFields.get("orig_addr_zip4"));
//		params.addValue("origAddrProvince", "");
//		params.addValue("origAddrCtry", "");
//		params.addValue("origPostalCD", "");
//		params.addValue("origEmail", (modifiedFields.get("orig_email") == null) ? demoInfo.get("EMAIL") : modifiedFields.get("orig_email"));
//		params.addValue("origPhoneArea", (modifiedFields.get("orig_phone_area") == null) ? demoInfo.get("PHONE_AREA") : modifiedFields.get("orig_phone_area"));
//		params.addValue("origPhoneNbr", (modifiedFields.get("orig_phone_nbr") == null) ? demoInfo.get("PHONE_NBR") : modifiedFields.get("orig_phone_nbr"));

		params.addValue("newStaffID", (ed25Fields.get("new_staff_id") == null) ? "" : ed25Fields.get("new_staff_id"));
		params.addValue("newDob", (ed25Fields.get("new_dob") == null) ? "" : ed25Fields.get("new_dob"));
		params.addValue("newSex", (ed25Fields.get("new_sex") == null) ? "" : ed25Fields.get("new_sex"));
		String ls_data = "";
		if (ed25Fields.get("new_name_l") == null ||
			"".equals(ed25Fields.get("new_name_l"))) {
			ls_data = modifiedFields.get("new_name_l");
		} else if ("".equals(modifiedFields.get("new_name_l")) &&
				   !"".equals(ed25Fields.get("new_name_l"))) {
			ls_data = ed25Fields.get("new_name_l");
		} else {
			ls_data = modifiedFields.get("new_name_l");
		}
		params.addValue("newLName", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_name_f") == null ||
			"".equals(ed25Fields.get("new_name_f"))) {
			ls_data = modifiedFields.get("new_name_f");
		} else if ("".equals(modifiedFields.get("new_name_f")) &&
				   !"".equals(ed25Fields.get("new_name_f"))) {
			ls_data = ed25Fields.get("new_name_f");
		} else {
			ls_data = modifiedFields.get("new_name_f");
		}
		params.addValue("newFName", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_name_m") == null ||
			"".equals(ed25Fields.get("new_name_m"))) {
			ls_data = modifiedFields.get("new_name_m");
		} else if ("".equals(modifiedFields.get("new_name_m")) &&
				   !"".equals(ed25Fields.get("new_name_m"))) {
			ls_data = ed25Fields.get("new_name_m");
		} else {
			ls_data = modifiedFields.get("new_name_m");
		}
		params.addValue("newMName", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_name_gen") == null ||
			"".equals(ed25Fields.get("new_name_gen"))) {
			ls_data = modifiedFields.get("new_name_gen");
		} else if ("".equals(modifiedFields.get("new_name_gen")) &&
				   !"".equals(ed25Fields.get("new_name_gen"))) {
			ls_data = ed25Fields.get("new_name_gen");
		} else {
			ls_data = modifiedFields.get("new_name_gen");
		}
		params.addValue("newNameGen", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_addr_nbr") == null ||
			"".equals(ed25Fields.get("new_addr_nbr"))) {
			ls_data = modifiedFields.get("new_addr_nbr");
		} else if ("".equals(modifiedFields.get("new_addr_nbr")) &&
				   !"".equals(ed25Fields.get("new_addr_nbr"))) {
			ls_data = ed25Fields.get("new_addr_nbr");
		} else {
			ls_data = modifiedFields.get("new_addr_nbr");
		}
		params.addValue("newAddrNbr", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_addr_str") == null ||
			"".equals(ed25Fields.get("new_addr_str"))) {
			ls_data = modifiedFields.get("new_addr_str");
		} else if ("".equals(modifiedFields.get("new_addr_str")) &&
				   !"".equals(ed25Fields.get("new_addr_str"))) {
			ls_data = ed25Fields.get("new_addr_str");
		} else {
			ls_data = modifiedFields.get("new_addr_str");
		}
		params.addValue("newAddrStr", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_addr_apt") == null ||
			"".equals(ed25Fields.get("new_addr_apt"))) {
			ls_data = modifiedFields.get("new_addr_apt");
		} else if ("".equals(modifiedFields.get("new_addr_apt")) &&
				   !"".equals(ed25Fields.get("new_addr_apt"))) {
			ls_data = ed25Fields.get("new_addr_apt");
		} else {
			ls_data = modifiedFields.get("new_addr_apt");
		}
		params.addValue("newAddrApt", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_addr_city") == null ||
			"".equals(ed25Fields.get("new_addr_city"))) {
			ls_data = modifiedFields.get("new_addr_city");
		} else if ("".equals(modifiedFields.get("new_addr_city")) &&
				   !"".equals(ed25Fields.get("new_addr_city"))) {
			ls_data = ed25Fields.get("new_addr_city");
		} else {
			ls_data = modifiedFields.get("new_addr_city");
		}
		params.addValue("newAddrCity", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_addr_st") == null ||
			"".equals(ed25Fields.get("new_addr_st"))) {
			ls_data = modifiedFields.get("new_addr_st");
		} else if ("".equals(modifiedFields.get("new_addr_st")) &&
				   !"".equals(ed25Fields.get("new_addr_st"))) {
			ls_data = ed25Fields.get("new_addr_st");
		} else {
			ls_data = modifiedFields.get("new_addr_st");
		}
		params.addValue("newAddrSt", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_addr_zip") == null ||
			"".equals(ed25Fields.get("new_addr_zip"))) {
			ls_data = modifiedFields.get("new_addr_zip");
		} else if ("".equals(modifiedFields.get("new_addr_zip")) &&
				   !"".equals(ed25Fields.get("new_addr_zip"))) {
			ls_data = ed25Fields.get("new_addr_zip");
		} else {
			ls_data = modifiedFields.get("new_addr_zip");
		}
		params.addValue("newAddrZip", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_addr_zip4") == null ||
			"".equals(ed25Fields.get("new_addr_zip4"))) {
			ls_data = modifiedFields.get("new_addr_zip4");
		} else if ("".equals(modifiedFields.get("new_addr_zip4")) &&
				   !"".equals(ed25Fields.get("new_addr_zip4"))) {
			ls_data = ed25Fields.get("new_addr_zip4");
		} else {
			ls_data = modifiedFields.get("new_addr_zip4");
		}
		params.addValue("newAddrZip4", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_addr_province") == null ||
			"".equals(ed25Fields.get("new_addr_province"))) {
			ls_data = "";
		} else if ("".equals(modifiedFields.get("new_addr_province")) &&
				   !"".equals(ed25Fields.get("new_addr_province"))) {
			ls_data = ed25Fields.get("new_addr_province");
		} else {
			ls_data = "";
		}
		params.addValue("newAddrProvince", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_addr_ctry") == null ||
			"".equals(ed25Fields.get("new_addr_ctry"))) {
			ls_data = "";
		} else if ("".equals(modifiedFields.get("new_addr_ctry")) &&
				   !"".equals(ed25Fields.get("new_addr_ctry"))) {
			ls_data = ed25Fields.get("new_addr_ctry");
		} else {
			ls_data = "";
		}
		params.addValue("newAddrCtry", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_addr_postal_cd") == null ||
			"".equals(ed25Fields.get("new_addr_postal_cd"))) {
			ls_data = "";
		} else if ("".equals(modifiedFields.get("new_addr_postal_cd")) &&
				   !"".equals(ed25Fields.get("new_addr_postal_cd"))) {
			ls_data = ed25Fields.get("new_addr_postal_cd");
		} else {
			ls_data = "";
		}
		params.addValue("newPostalCD", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_email") == null ||
			"".equals(ed25Fields.get("new_email"))) {
			ls_data = modifiedFields.get("new_email");
		} else if ("".equals(modifiedFields.get("new_email")) &&
				   !"".equals(ed25Fields.get("new_email"))) {
			ls_data = ed25Fields.get("new_email");
		} else {
			ls_data = modifiedFields.get("new_email");
		}
		params.addValue("newEmail", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_phone_area") == null ||
			"".equals(ed25Fields.get("new_phone_are"))) {
			ls_data = modifiedFields.get("new_phone_area");
		} else if ("".equals(modifiedFields.get("new_phone_area")) &&
				   !"".equals(ed25Fields.get("new_phone_area"))) {
			ls_data = ed25Fields.get("new_phone_area");
		} else {
			ls_data = modifiedFields.get("new_phone_area");
		}
		params.addValue("newPhoneArea", ls_data);
		
		ls_data = "";
		if (ed25Fields.get("new_phone_nbr") == null ||
			"".equals(ed25Fields.get("new_phone_nbr"))) {
			ls_data = modifiedFields.get("new_phone_nbr");
		} else if ("".equals(modifiedFields.get("new_phone_nbr")) &&
				   !"".equals(ed25Fields.get("new_phone_nbr"))) {
			ls_data = ed25Fields.get("new_phone_nbr");
		} else {
			ls_data = modifiedFields.get("new_phone_nbr");
		}
		params.addValue("newPhoneNbr",ls_data);
		params.addValue("module", "");

		StringBuilder updateED20 = new StringBuilder();

		updateED20.append("UPDATE "); 
		updateED20.append("BHR_TEAM_ED20 ");
		updateED20.append("SET ");
		updateED20.append("NAME_L = :newLName, ");
		updateED20.append("NAME_F = :newFName, ");
		updateED20.append("NAME_M = :newMName, ");
		updateED20.append("NAME_GEN = :newNameGen, ");
		updateED20.append("ADDR_NBR = :newAddrNbr, ");
		updateED20.append("ADDR_STR = :newAddrStr, ");
		updateED20.append("ADDR_APT = :newAddrApt, ");
		updateED20.append("ADDR_CITY = :newAddrCity, ");
		updateED20.append("ADDR_ST = :newAddrSt, ");
		updateED20.append("ADDR_ZIP = :newAddrZip, ");
		updateED20.append("ADDR_ZIP4 = :newAddrZip4, ");
		updateED20.append("EMAIL = :newEmail, ");
		updateED20.append("PHONE_AREA = :newPhoneArea, ");
		updateED20.append("PHONE_NBR = :newPhoneNbr, ");
		updateED20.append("MODULE = :module ");
		updateED20.append("WHERE ");
		updateED20.append("(EMP_NBR = :empNbr) AND ");
		updateED20.append("(RPT_YR || RPT_MON >= :rptYrMon) ");

		StringBuilder updateED25 = new StringBuilder();
		updateED25.append("UPDATE "); 
		updateED25.append("BHR_TEAM_ED25 ");
		updateED25.append("SET ");
// ts2017025 - comment - should not be changed after the initial record insert
//		updateED25.append("ORIG_STAFF_ID = :origStaffID, ");
//		updateED25.append("ORIG_DOB = :origDob, ");
//		updateED25.append("ORIG_SEX = :origSex, ");
		updateED25.append("ORIG_NAME_L = :origLName, ");
		updateED25.append("ORIG_NAME_F = :origFName, ");
		updateED25.append("ORIG_NAME_M = :origMName, ");
		updateED25.append("ORIG_NAME_GEN = :origNameGen, ");
		
		updateED25.append("NEW_STAFF_ID = :newStaffID, ");
		updateED25.append("NEW_DOB = :newDob, ");
		updateED25.append("NEW_SEX = :newSex, ");
		updateED25.append("NEW_NAME_L = :newLName, ");
		updateED25.append("NEW_NAME_F = :newFName, ");
		updateED25.append("NEW_NAME_M = :newMName, ");
		updateED25.append("NEW_NAME_GEN = :newNameGen, ");
		updateED25.append("NEW_ADDR_NBR = :newAddrNbr, ");
		updateED25.append("NEW_ADDR_STR = :newAddrStr, ");
		updateED25.append("NEW_ADDR_APT = :newAddrApt, ");
		updateED25.append("NEW_ADDR_CITY = :newAddrCity, ");
		updateED25.append("NEW_ADDR_ST = :newAddrSt, ");
		updateED25.append("NEW_ADDR_ZIP = :newAddrZip, ");
		updateED25.append("NEW_ADDR_ZIP4 = :newAddrZip4, ");
		updateED25.append("NEW_ADDR_PROVINCE = :newAddrProvince, ");
		updateED25.append("NEW_ADDR_CTRY = :newAddrCtry, ");
		updateED25.append("NEW_POSTAL_CD = :newPostalCD, ");
		updateED25.append("NEW_EMAIL = :newEmail, ");
		updateED25.append("NEW_PHONE_AREA = :newPhoneArea, ");
		updateED25.append("NEW_PHONE_NBR = :newPhoneNbr, ");
		updateED25.append("MODULE = :module ");
		updateED25.append("WHERE ");
		updateED25.append("(EMP_NBR = :empNbr) AND ");
		updateED25.append("(RPT_YR || RPT_MON >= :rptYrMon) ");

		
		int count = 0;
		
		//if the employee has an ed20 and ed25 record - then only update the ED25 record
		if (!lb_updEd25Only && ed20Fields.size() > 0) {
			count = this.getNamedParameterJdbcTemplate().update(updateED20.toString(), params);
		}

//		if (count > 0) {

			// Also update ED25 record, if exists
//			count = this.getNamedParameterJdbcTemplate().update(updateED25.toString(), params);

//			if (count > 0) {
				// Update was Successful
//				return true;
//			}
//		}
//		else 
		if (count == 0) {

			// Update ED25 record, if exists or insert a ED25 record
			count = this.getNamedParameterJdbcTemplate().update(updateED25.toString(), params);

			if (count > 0) {
				// Update was Successful
				return true;
			} else if (count == 0){

				StringBuilder insertED25 = new StringBuilder();
				insertED25.append("INSERT "); 
				insertED25.append("INTO BHR_TEAM_ED25 ");
				insertED25.append("(RPT_YR, ");
				insertED25.append("RPT_MON, ");
				insertED25.append("EMP_NBR, ");
				insertED25.append("ORIG_STAFF_ID, ");
				insertED25.append("ORIG_DOB, ");
				insertED25.append("ORIG_SEX, ");
				insertED25.append("ORIG_NAME_L, ");
				insertED25.append("ORIG_NAME_F, ");
				insertED25.append("ORIG_NAME_M, ");
				insertED25.append("ORIG_NAME_GEN, ");
				insertED25.append("NEW_STAFF_ID, ");
				insertED25.append("NEW_DOB, ");
				insertED25.append("NEW_SEX, ");
				insertED25.append("NEW_NAME_L, ");
				insertED25.append("NEW_NAME_F, ");
				insertED25.append("NEW_NAME_M, ");
				insertED25.append("NEW_NAME_GEN, ");
				insertED25.append("NEW_ADDR_NBR, ");
				insertED25.append("NEW_ADDR_STR, ");
				insertED25.append("NEW_ADDR_APT, ");
				insertED25.append("NEW_ADDR_CITY, ");
				insertED25.append("NEW_ADDR_ST, ");
				insertED25.append("NEW_ADDR_ZIP, ");
				insertED25.append("NEW_ADDR_ZIP4, ");
				insertED25.append("NEW_ADDR_PROVINCE, ");
				insertED25.append("NEW_ADDR_CTRY, ");
				insertED25.append("NEW_POSTAL_CD, ");
				insertED25.append("NEW_EMAIL, ");
				insertED25.append("NEW_PHONE_AREA, ");
				insertED25.append("NEW_PHONE_NBR, ");
				insertED25.append("MODULE) ");
				insertED25.append("VALUES ");
				insertED25.append("(:rptYr, ");
				insertED25.append(":rptMon, ");
				insertED25.append(":empNbr, ");
				insertED25.append(":origStaffID, ");
				insertED25.append(":origDob, ");
				insertED25.append(":origSex, ");
				insertED25.append(":origLName, ");
				insertED25.append(":origFName, ");
				insertED25.append(":origMName, ");
				insertED25.append(":origNameGen, ");
				insertED25.append(":newStaffID, ");
				insertED25.append(":newDob, ");
				insertED25.append(":newSex, ");
				insertED25.append(":newLName, ");
				insertED25.append(":newFName, ");
				insertED25.append(":newMName, ");
				insertED25.append(":newNameGen, ");
				insertED25.append(":newAddrNbr, ");
				insertED25.append(":newAddrStr, ");
				insertED25.append(":newAddrApt, ");
				insertED25.append(":newAddrCity, ");
				insertED25.append(":newAddrSt, ");
				insertED25.append(":newAddrZip, ");
				insertED25.append(":newAddrZip4, ");
				insertED25.append(":newAddrProvince, ");
				insertED25.append(":newAddrCtry, ");
				insertED25.append(":newPostalCD, ");
				insertED25.append(":newEmail, ");
				insertED25.append(":newPhoneArea, ");
				insertED25.append(":newPhoneNbr, ");
				insertED25.append(":module) ");

				// Execute the Query
				this.getNamedParameterJdbcTemplate().update(insertED25.toString(), params);
			}
		}
		else {
			// Database Access Error
			return false;
		}
		return true;
	}
*/

	public Map<String,String> getDemographicInfo(String empNbr) {

		Map<String, String> demoInfo = new HashMap<String, String> ();

		StringBuilder retrieveDemoInfoSQL = new StringBuilder();

		retrieveDemoInfoSQL.append("SELECT ");
		retrieveDemoInfoSQL.append("A.EMP_NBR, ");
		retrieveDemoInfoSQL.append("A.STAFF_ID, ");
		retrieveDemoInfoSQL.append("A.DOB, ");
		retrieveDemoInfoSQL.append("A.SEX, ");
		retrieveDemoInfoSQL.append("A.NAME_L, ");
		retrieveDemoInfoSQL.append("A.NAME_F, ");
		retrieveDemoInfoSQL.append("A.NAME_M, ");
		retrieveDemoInfoSQL.append("A.NAME_GEN, ");
		retrieveDemoInfoSQL.append("A.ADDR_NBR, ");
		retrieveDemoInfoSQL.append("A.ADDR_STR, ");
		retrieveDemoInfoSQL.append("A.ADDR_APT, ");
		retrieveDemoInfoSQL.append("A.ADDR_CITY, ");
		retrieveDemoInfoSQL.append("A.ADDR_ST, ");
		retrieveDemoInfoSQL.append("A.ADDR_ZIP, ");
		retrieveDemoInfoSQL.append("A.ADDR_ZIP4, ");
		retrieveDemoInfoSQL.append("A.ADDR_CTRY, ");
		retrieveDemoInfoSQL.append("A.EMAIL, ");
		retrieveDemoInfoSQL.append("A.PHONE_AREA, ");
		retrieveDemoInfoSQL.append("A.PHONE_NBR ");
		retrieveDemoInfoSQL.append("FROM BHR_EMP_DEMO A ");
		retrieveDemoInfoSQL.append("WHERE (A.EMP_NBR = :empNbr) ");

		MapSqlParameterSource params = new MapSqlParameterSource(); 
		params.addValue("empNbr", empNbr);

		try {
			demoInfo = this.getNamedParameterJdbcTemplate().queryForObject(retrieveDemoInfoSQL.toString(), params, new AutoPopulateDemoInfoMapper());
		}
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		return demoInfo;
	}

	static class AutoPopulateDemoInfoMapper implements ParameterizedRowMapper<Map<String, String>> {

		public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String, String> demoInfo = new HashMap<String, String>();

			demoInfo.put("EMP_NBR", rs.getString("EMP_NBR") == null ? "" : rs.getString("EMP_NBR").trim());
			demoInfo.put("STAFF_ID", rs.getString("STAFF_ID") == null ? "" : rs.getString("STAFF_ID").trim());
			demoInfo.put("DOB", rs.getString("DOB") == null ? "" : rs.getString("DOB").trim());
			demoInfo.put("SEX", rs.getString("SEX") == null ? "" : rs.getString("SEX").trim());
			demoInfo.put("NAME_L", rs.getString("NAME_L") == null ? "" : rs.getString("NAME_L").trim());
			demoInfo.put("NAME_F", rs.getString("NAME_F") == null ? "" : rs.getString("NAME_F").trim());
			demoInfo.put("NAME_M", rs.getString("NAME_M") == null ? "" : rs.getString("NAME_M").trim());
			demoInfo.put("NAME_GEN", rs.getString("NAME_GEN") == null ? "" : rs.getString("NAME_GEN").trim());
			demoInfo.put("ADDR_NBR", rs.getString("ADDR_NBR") == null ? "" : rs.getString("ADDR_NBR").trim());
			demoInfo.put("ADDR_STR", rs.getString("ADDR_STR") == null ? "" : rs.getString("ADDR_STR").trim());
			demoInfo.put("ADDR_APT", rs.getString("ADDR_APT") == null ? "" : rs.getString("ADDR_APT").trim());
			demoInfo.put("ADDR_CITY", rs.getString("ADDR_CITY") == null ? "" : rs.getString("ADDR_CITY").trim());
			demoInfo.put("ADDR_ST", rs.getString("ADDR_ST") == null ? "" : rs.getString("ADDR_ST").trim());
			demoInfo.put("ADDR_CTRY", rs.getString("ADDR_CTRY") == null ? "" : rs.getString("ADDR_CTRY").trim());
			demoInfo.put("ADDR_ZIP", rs.getString("ADDR_ZIP") == null ? "" : rs.getString("ADDR_ZIP").trim());
			demoInfo.put("ADDR_ZIP4", rs.getString("ADDR_ZIP4") == null ? "" : rs.getString("ADDR_ZIP4").trim());
			demoInfo.put("PHONE_AREA", rs.getString("PHONE_AREA") == null ? "" : rs.getString("PHONE_AREA").trim());
			demoInfo.put("PHONE_NBR", rs.getString("PHONE_NBR") == null ? "" : rs.getString("PHONE_NBR").trim());
			demoInfo.put("EMAIL", rs.getString("EMAIL") == null ? "" : rs.getString("EMAIL").trim());

			return demoInfo;
		}
	}
	
	public Boolean ed20Exists(String rptYr, String rptMo, String empNbr) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM bhr_team_ed20 ");
		sql.append("where rpt_yr = :rptYr and rpt_mon >= :rptMon and emp_nbr = :empNbr " );
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("rptYr", rptYr);
		params.addValue("rptMo", rptMo);
		params.addValue("empNbr", empNbr);

		int result =  getNamedParameterJdbcTemplate().queryForInt(sql.toString(), params);

		return result > 0;
	}
	
	public List<Ed20Command> getEd20Records(String empNbr, String rptYr, String rptMo) {

		List<Ed20Command> ed20Recs = new ArrayList<Ed20Command> ();

		StringBuilder ed20SQL = new StringBuilder();
		
		ed20SQL.append("Select emp_nbr, rpt_mon, rpt_yr, name_l, name_f, name_m, name_gen, ");
		ed20SQL.append("addr_nbr, addr_str, addr_apt, addr_city, addr_st, addr_zip, addr_zip4, ");
		ed20SQL.append("email, phone_area, phone_nbr ");
		ed20SQL.append("from bhr_team_ed20 ");
		ed20SQL.append("where rpt_yr || rpt_mon >= :rptYr || :rptMo and emp_nbr = :empNbr ");
		ed20SQL.append("order by rpt_yr, rpt_mon  ");

		MapSqlParameterSource params = new MapSqlParameterSource(); 
		params.addValue("rptYr", rptYr);
		params.addValue("rptMo", rptMo);
		params.addValue("empNbr", empNbr);

		try {
			ed20Recs = this.getNamedParameterJdbcTemplate().query(ed20SQL.toString(), params, new ed20Mapper());
		} catch (Exception e){
			e.printStackTrace();
			throw e;
		}

		return ed20Recs;
	}
	
	static class ed20Mapper implements ParameterizedRowMapper<Ed20Command> {

		public Ed20Command mapRow(ResultSet rs, int rowNum) throws SQLException {
			Ed20Command ed20Rec = new Ed20Command();

			ed20Rec.setEmpNbr(rs.getString("emp_nbr"));
			ed20Rec.setRptMon(rs.getString("rpt_mon"));
			ed20Rec.setRptYr(rs.getString("rpt_yr"));
			ed20Rec.setNameL(rs.getString("name_l") == null ? "" : rs.getString("name_l").trim());
			ed20Rec.setNameF(rs.getString("name_f") == null ? "" : rs.getString("name_f").trim());
			ed20Rec.setNameM(rs.getString("name_m") == null ? "" : rs.getString("name_m").trim());
			ed20Rec.setNameGen(rs.getString("name_gen") == null ? "" : rs.getString("name_gen").trim());
			ed20Rec.setAddrNbr(rs.getString("addr_nbr") == null ? "" : rs.getString("addr_nbr").trim());
			ed20Rec.setAddrStr(rs.getString("addr_str") == null ? "" : rs.getString("addr_str").trim());
			ed20Rec.setAddrApt(rs.getString("addr_apt") == null ? "" : rs.getString("addr_apt").trim());
			ed20Rec.setAddrCity(rs.getString("addr_city") == null ? "" : rs.getString("addr_city").trim());
			ed20Rec.setAddrSt(rs.getString("addr_st") == null ? "" : rs.getString("addr_st").trim());
			ed20Rec.setAddrZip(rs.getString("addr_zip") == null ? "" : rs.getString("addr_zip").trim());
			ed20Rec.setAddrZip4(rs.getString("addr_zip4") == null ? "" : rs.getString("addr_zip4").trim());
			ed20Rec.setEmail(rs.getString("email") == null ? "" : rs.getString("email").trim());
			ed20Rec.setPhoneArea(rs.getString("phone_area") == null ? "" : rs.getString("phone_area").trim());
			ed20Rec.setPhoneNbr(rs.getString("phone_nbr") == null ? "" : rs.getString("phone_nbr").trim());

			return ed20Rec;
		}
	}
	
	public Boolean ed25Exists(String rptYr, String rptMo, String empNbr) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM bhr_team_ed25 ");
		sql.append("where rpt_yr = :rptYr and rpt_mon = :rptMo and emp_nbr = :empNbr " );
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("rptYr", rptYr);
		params.addValue("rptMo", rptMo);
		params.addValue("empNbr", empNbr);

		int result =  getNamedParameterJdbcTemplate().queryForInt(sql.toString(), params);

		return result > 0;
	}
	
	public List<Ed25Command> getEd25Records( String empNbr, String rptYr, String rptMo) {

		List<Ed25Command> ed25Recs = new ArrayList<Ed25Command> ();

		StringBuilder ed25SQL = new StringBuilder();
		
		ed25SQL.append("Select emp_nbr, rpt_mon, rpt_yr, new_name_l, new_name_f, new_name_m, new_name_gen, ");
		ed25SQL.append("new_addr_nbr, new_addr_str, new_addr_apt, new_addr_city, new_addr_st, new_addr_zip, new_addr_zip4, ");
		ed25SQL.append("new_email, new_phone_area, new_phone_nbr ");
		ed25SQL.append("from bhr_team_ed25 ");
		ed25SQL.append("where rpt_yr || rpt_mon >= :rptYr || :rptMo and emp_nbr = :empNbr ");
		ed25SQL.append("order by rpt_yr, rpt_mon  ");


		MapSqlParameterSource params = new MapSqlParameterSource(); 
		params.addValue("rptYr", rptYr);
		params.addValue("rptMo", rptMo);
		params.addValue("empNbr", empNbr);
		try {
			ed25Recs = this.getNamedParameterJdbcTemplate().query(ed25SQL.toString(), params, new ed25Mapper());
		} catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		return ed25Recs;
	}

	static class ed25Mapper implements ParameterizedRowMapper<Ed25Command> {

		public Ed25Command mapRow(ResultSet rs, int rowNum) throws SQLException {
			Ed25Command ed25Rec = new Ed25Command();
			
			ed25Rec.setEmpNbr(rs.getString("emp_nbr"));
			ed25Rec.setRptMon(rs.getString("rpt_mon"));
			ed25Rec.setRptYr(rs.getString("rpt_yr"));
			ed25Rec.setNewNameL(rs.getString("new_name_l") == null ? "" : rs.getString("new_name_l").trim());
			ed25Rec.setNewNameF(rs.getString("new_name_f") == null ? "" : rs.getString("new_name_f").trim());
			ed25Rec.setNewNameM(rs.getString("new_name_m") == null ? "" : rs.getString("new_name_m").trim());
			ed25Rec.setNewNameGen(rs.getString("new_name_gen") == null ? "" : rs.getString("new_name_gen").trim());
			ed25Rec.setNewAddrNbr(rs.getString("new_addr_nbr") == null ? "" : rs.getString("new_addr_nbr").trim());
			ed25Rec.setNewAddrStr(rs.getString("new_addr_str") == null ? "" : rs.getString("new_addr_str").trim());
			ed25Rec.setNewAddrApt(rs.getString("new_addr_apt") == null ? "" : rs.getString("new_addr_apt").trim());
			ed25Rec.setNewAddrCity(rs.getString("new_addr_city") == null ? "" : rs.getString("new_addr_city").trim());
			ed25Rec.setNewAddrSt(rs.getString("new_addr_st") == null ? "" : rs.getString("new_addr_st").trim());
			ed25Rec.setNewAddrZip(rs.getString("new_addr_zip") == null ? "" : rs.getString("new_addr_zip").trim());
			ed25Rec.setNewAddrZip4(rs.getString("new_addr_zip4") == null ? "" : rs.getString("new_addr_zip4").trim());
			ed25Rec.setNewEmail(rs.getString("new_email") == null ? "" : rs.getString("new_email").trim());
			ed25Rec.setNewPhoneArea(rs.getString("new_phone_area") == null ? "" : rs.getString("new_phone_area").trim());
			ed25Rec.setNewPhoneNbr(rs.getString("new_phone_nbr") == null ? "" : rs.getString("new_phone_nbr").trim());

			return ed25Rec;
		}
	}
	
	
	public boolean updEd20Recs(List<Ed20Command> ed20List) {
		boolean lb_rc = true;
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuilder updateED20 = new StringBuilder();
		int count = 0;
		
		updateED20.append("UPDATE BHR_TEAM_ED20 ");
		updateED20.append("SET NAME_L = :newLName, NAME_F = :newFName, ");
		updateED20.append("NAME_M = :newMName, NAME_GEN = :newNameGen, ");
		updateED20.append("ADDR_NBR = :newAddrNbr, ADDR_STR = :newAddrStr, ");
		updateED20.append("ADDR_APT = :newAddrApt,  ADDR_CITY = :newAddrCity, ");
		updateED20.append("ADDR_ST = :newAddrSt, ADDR_ZIP = :newAddrZip, ");
		updateED20.append("ADDR_ZIP4 = :newAddrZip4, EMAIL = :newEmail, ");
		updateED20.append("PHONE_AREA = :newPhoneArea, PHONE_NBR = :newPhoneNbr, ");
		updateED20.append("MODULE = :module ");
		updateED20.append("where EMP_NBR = :empNbr AND ");
		updateED20.append("RPT_YR = :rptYr and RPT_MON = :rptMon ");
		
		
		for (Ed20Command row : ed20List) {
			params = new MapSqlParameterSource();
			params.addValue("empNbr", row.getEmpNbr());
			params.addValue("rptYr", row.getRptYr());
			params.addValue("rptMon", row.getRptMon());
			params.addValue("newLName", row.getNameL());
			params.addValue("newFName", row.getNameF());
			params.addValue("newMName", row.getNameM());
			params.addValue("newNameGen", row.getNameGen());
			params.addValue("newAddrNbr", row.getAddrNbr());
			params.addValue("newAddrStr", row.getAddrStr());
			params.addValue("newAddrApt", row.getAddrApt());
			params.addValue("newAddrCity", row.getAddrCity());
			params.addValue("newAddrSt", row.getAddrSt());
			params.addValue("newAddrZip", row.getAddrZip());
			params.addValue("newAddrZip4", row.getAddrZip4());
			params.addValue("newEmail", row.getEmail());
			params.addValue("newPhoneArea", row.getPhoneArea());
			params.addValue("newPhoneNbr", row.getPhoneNbr());
			params.addValue("module", module);
			try {
				count = this.getNamedParameterJdbcTemplate().update(updateED20.toString(), params);
			} catch (Exception e) {
				lb_rc = false;
			}
		}

		return lb_rc;
	}
	
	public boolean insUpdEd25Recs(List<Ed25Command> ed25List) {
		boolean lb_rc = true;
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuilder updateED25 = new StringBuilder();
		updateED25.append("UPDATE BHR_TEAM_ED25 ");
		updateED25.append("SET NEW_NAME_L = :newLName, NEW_NAME_F = :newFName, ");
		updateED25.append("NEW_NAME_M = :newMName, NEW_NAME_GEN = :newNameGen, ");
		updateED25.append("NEW_ADDR_NBR = :newAddrNbr, NEW_ADDR_STR = :newAddrStr, ");
		updateED25.append("NEW_ADDR_APT = :newAddrApt, NEW_ADDR_CITY = :newAddrCity, ");
		updateED25.append("NEW_ADDR_ST = :newAddrSt, NEW_ADDR_ZIP = :newAddrZip, ");
		updateED25.append("NEW_ADDR_ZIP4 = :newAddrZip4, ");
		updateED25.append("NEW_EMAIL = :newEmail, NEW_PHONE_AREA = :newPhoneArea, ");
		updateED25.append("NEW_PHONE_NBR = :newPhoneNbr, MODULE = :module ");
		updateED25.append("WHERE EMP_NBR = :empNbr AND ");
		updateED25.append("RPT_YR = :rptYr and  RPT_MON = :rptMon ");
		int count = 0;
		
		StringBuilder insertED25 = new StringBuilder();
		insertED25.append("INSERT INTO BHR_TEAM_ED25 ");
		insertED25.append("(RPT_YR, RPT_MON, ");
		insertED25.append("EMP_NBR, ORIG_STAFF_ID, ");
		insertED25.append("ORIG_DOB, ORIG_SEX, NEW_STAFF_ID, NEW_DOB, NEW_SEX, ");
		insertED25.append("ORIG_NAME_L, ORIG_NAME_F, ");
		insertED25.append("ORIG_NAME_M, ORIG_NAME_GEN, NEW_NAME_L, NEW_NAME_F, ");
		insertED25.append("NEW_NAME_M, NEW_NAME_GEN, NEW_ADDR_NBR, ");
		insertED25.append("NEW_ADDR_STR, NEW_ADDR_APT, NEW_ADDR_CITY, ");
		insertED25.append("NEW_ADDR_ST, NEW_ADDR_ZIP, ");
		insertED25.append("NEW_ADDR_ZIP4, NEW_EMAIL, ");
		insertED25.append("NEW_PHONE_AREA, NEW_PHONE_NBR, MODULE) ");
		insertED25.append("VALUES (:rptYr, :rptMon, :empNbr, :origStaffId, :origDob, ");
		insertED25.append(":origSex, '', '', '', :origLName, :origFName, :origMName, :origNameGen,  ");
		insertED25.append(":newLName, :newFName, :newMName, :newNameGen, :newAddrNbr, ");
		insertED25.append(":newAddrStr, :newAddrApt, :newAddrCity, :newAddrSt, :newAddrZip, ");
		insertED25.append(":newAddrZip4, :newEmail, :newPhoneArea, :newPhoneNbr, :module) ");
		for (Ed25Command row : ed25List) {
			params = new MapSqlParameterSource();
			if (row.isNewRow()) {
				params.addValue("empNbr", row.getEmpNbr());
				params.addValue("rptYr", row.getRptYr());
				params.addValue("rptMon", row.getRptMon());
				params.addValue("origStaffId", row.getOrigStaffID());
				params.addValue("origSex", row.getOrigSex());
				params.addValue("origDob", row.getOrigDOB());
				params.addValue("origLName", row.getOrigNameL());
				params.addValue("origFName", row.getOrigNameF());
				params.addValue("origMName", row.getOrigNameM());
				params.addValue("origNameGen", row.getOrigNameGen());
				params.addValue("newLName", row.getNewNameL());
				params.addValue("newFName", row.getNewNameF());
				params.addValue("newMName", row.getNewNameM());
				params.addValue("newNameGen", row.getNewNameGen());
				params.addValue("newAddrNbr", row.getNewAddrNbr());
				params.addValue("newAddrStr", row.getNewAddrStr());
				params.addValue("newAddrApt", row.getNewAddrApt());
				params.addValue("newAddrCity", row.getNewAddrCity());
				params.addValue("newAddrSt", row.getNewAddrSt());
				params.addValue("newAddrZip", row.getNewAddrZip());
				params.addValue("newAddrZip4", row.getNewAddrZip4());
				params.addValue("newEmail", row.getNewEmail());
				params.addValue("newPhoneArea", row.getNewPhoneArea());
				params.addValue("newPhoneNbr", row.getNewPhoneNbr());
				params.addValue("module", module);
			//	try {
					this.getNamedParameterJdbcTemplate().update(insertED25.toString(), params);
			//	} catch (Exception e) {
			//		lb_rc = false;
			//	}
			} else {
				params.addValue("empNbr", row.getEmpNbr());
				params.addValue("rptYr", row.getRptYr());
				params.addValue("rptMon", row.getRptMon());
				params.addValue("newLName", row.getNewNameL());
				params.addValue("newFName", row.getNewNameF());
				params.addValue("newMName", row.getNewNameM());
				params.addValue("newNameGen", row.getNewNameGen());
				params.addValue("newAddrNbr", row.getNewAddrNbr());
				params.addValue("newAddrStr", row.getNewAddrStr());
				params.addValue("newAddrApt", row.getNewAddrApt());
				params.addValue("newAddrCity", row.getNewAddrCity());
				params.addValue("newAddrSt", row.getNewAddrSt());
				params.addValue("newAddrZip", row.getNewAddrZip());
				params.addValue("newAddrZip4", row.getNewAddrZip4());
				params.addValue("newEmail", row.getNewEmail());
				params.addValue("newPhoneArea", row.getNewPhoneArea());
				params.addValue("newPhoneNbr", row.getNewPhoneNbr());
				params.addValue("module", module);
				try {
					count = this.getNamedParameterJdbcTemplate().update(updateED25.toString(), params);
				} catch (Exception e) {
					lb_rc = false;
				}
			}
		}
		
		try {
			this.getNamedParameterJdbcTemplate().update(insertED25.toString(), params);
		} catch (Exception e) {
			lb_rc = false;
		}

		return lb_rc;
	}

}