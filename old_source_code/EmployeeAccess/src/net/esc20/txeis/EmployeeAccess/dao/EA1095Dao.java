package net.esc20.txeis.EmployeeAccess.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.dao.api.IEA1095Dao;
import net.esc20.txeis.EmployeeAccess.domain.EA1095BInfoCommand;
import net.esc20.txeis.EmployeeAccess.domain.EA1095CInfoCommand;
import net.esc20.txeis.EmployeeAccess.domainobject.District;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;
import net.esc20.txeis.common.dao.PaginationHelper;
import net.esc20.txeis.common.domainobjects.ReportingContact;
import net.esc20.txeis.common.util.Page;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class EA1095Dao extends NamedParameterJdbcDaoSupport implements IEA1095Dao {

	@Override
	public List<String> retrieveAvailableCalYrs(String empNbr) {
		String sql = "SELECT DISTINCT CAL_YR FROM BHR_ACA_1095B_EMP_HIST WHERE EMP_NBR = :empNbr UNION SELECT DISTINCT CAL_YR FROM BHR_ACA_1095C_EMP_HIST WHERE EMP_NBR = :empNbr ORDER BY CAL_YR DESC ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empNbr", empNbr);

		return getNamedParameterJdbcTemplate().query(sql, params, new ParameterizedRowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return StringUtil.trim(rs.getString("CAL_YR"));
			}
		});
	}

	@Override
	public List<String> retrieveEA1095BEmpInfo(String activeTab, String empNbr, String calYr) {

		String countSQL = "SELECT COUNT(*) FROM BHR_ACA_1095B_EMP_HIST A WHERE A.EMP_NBR = :empNbr AND A.CAL_YR = :calYr ";

		StringBuilder retrieveSQL = new StringBuilder();
		retrieveSQL.append("SELECT A.COVRG_TYP AS COVRG_TYP, COVRG_DESCR = (SELECT B.COVRG_TYP_DESC AS DESCR  FROM BTHR_ACA_1095B_COVRG_TYP B WHERE CAL_YR = :calYr AND B.COVRG_TYP = A.COVRG_TYP )");
		retrieveSQL.append("FROM BHR_ACA_1095B_EMP_HIST A ");
		retrieveSQL.append("WHERE A.EMP_NBR = :empNbr AND ");
		retrieveSQL.append("A.CAL_YR = :calYr ");

		List<String> covrgTyp = new ArrayList<String>();
		int rows = 0;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empNbr", empNbr);
		params.addValue("calYr", calYr);

		try {
			rows = (Integer) this.getNamedParameterJdbcTemplate().queryForObject(countSQL, params, Integer.class);
			if (rows > 0 ) {
				covrgTyp = (List<String>) this.getNamedParameterJdbcTemplate().queryForObject(retrieveSQL.toString(), params, new CovrgTypListRowMapper());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return covrgTyp;
	}

	private static class CovrgTypListRowMapper implements ParameterizedRowMapper<List<String>> {
		public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
			List<String> covrgTypList = new ArrayList<String>();
			// Code
			String value = rs.getString("COVRG_TYP");
			value = (value == null) ? "" : value;
			covrgTypList.add(value);
			// Code Description
			value = rs.getString("COVRG_DESCR");
			value = (value == null) ? "" : value;
			covrgTypList.add(value);

			return covrgTypList;
		}
	}

	private String retrieve1095BCovrgInfoSql(String empNbr, String calYr){

		StringBuilder retrieveSQL = new StringBuilder();
		retrieveSQL.append("SELECT B.EMP_NBR, ");
		retrieveSQL.append("B.CAL_YR, ");
		retrieveSQL.append("B.SEQ_NBR, ");
		retrieveSQL.append("B.NAME_F, ");
		retrieveSQL.append("B.NAME_M, ");
		retrieveSQL.append("B.NAME_L, ");
		retrieveSQL.append("B.NAME_GEN, ");
		retrieveSQL.append("NAME_GEN_DESCR = (SELECT ET_C012_GEN.GEN_DESCR FROM ET_C012_GEN, BHR_OPTIONS WHERE ET_C012_GEN.GEN_CD <> '' AND  ET_C012_GEN.GEN_CD = B.NAME_GEN AND ET_C012_GEN.SCH_YR = BHR_OPTIONS.PEIMS_CD_YR), ");
		retrieveSQL.append("B.SSN, ");
		retrieveSQL.append("B.DOB, ");
		retrieveSQL.append("B.MON_ALL, ");
		retrieveSQL.append("B.MON_01, ");
		retrieveSQL.append("B.MON_02, ");
		retrieveSQL.append("B.MON_03, ");
		retrieveSQL.append("B.MON_04, ");
		retrieveSQL.append("B.MON_05, ");
		retrieveSQL.append("B.MON_06, ");
		retrieveSQL.append("B.MON_07, ");
		retrieveSQL.append("B.MON_08, ");
		retrieveSQL.append("B.MON_09, ");
		retrieveSQL.append("B.MON_10, ");
		retrieveSQL.append("B.MON_11, ");
		retrieveSQL.append("B.MON_12 ");
		retrieveSQL.append("FROM BHR_ACA_1095B_COVRD_HIST B ");
		retrieveSQL.append("WHERE B.EMP_NBR = '" + empNbr + "' AND ");
		retrieveSQL.append("B.CAL_YR ='" + calYr + "' ");
		retrieveSQL.append("ORDER BY B.NAME_F, B.NAME_M, B.NAME_L, B.NAME_GEN, B.DOB, B.SSN, B.SEQ_NBR ");
		
		return retrieveSQL.toString();
	}

	@Override
	public Page<EA1095BInfoCommand> retrieveEA1095BCovrgInfo(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize) {

		String retrieveSQL = this.retrieve1095BCovrgInfoSql(empNbr, calYr);

		Object[] params = new Object[] {};
		PaginationHelper<EA1095BInfoCommand> ph = new PaginationHelper<EA1095BInfoCommand>();
		return ph.fetchPage(this.getJdbcTemplate(), retrieveSQL, params, pageNbr, pageSize, new ACAYTD1095BMapper());
	}

	@Override
	public List<EA1095BInfoCommand> retrieveEA1095BCovrgInfo(String activeTab, String empNbr, String calYr) {

		List<EA1095BInfoCommand> acaYTD1095BEmpData = new ArrayList<EA1095BInfoCommand>();
		String retrieveSQL = this.retrieve1095BCovrgInfoSql(empNbr, calYr);

		MapSqlParameterSource params = new MapSqlParameterSource();

		try {
			acaYTD1095BEmpData = this.getNamedParameterJdbcTemplate().query(retrieveSQL.toString(), params, new ACAYTD1095BMapper());
		}
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}

		return acaYTD1095BEmpData;
	}

	static class ACAYTD1095BMapper implements ParameterizedRowMapper<EA1095BInfoCommand> {

		public EA1095BInfoCommand mapRow(ResultSet rs, int rowNum) throws SQLException {
			EA1095BInfoCommand ea1095BData = new EA1095BInfoCommand();

			ea1095BData.setEmpNbr(rs.getString("EMP_NBR") == null ? "" : rs.getString("EMP_NBR").trim());
			ea1095BData.setCalYr(rs.getString("CAL_YR") == null ? "" : rs.getString("CAL_YR").trim());
			ea1095BData.setSeqNbr(rs.getString("SEQ_NBR") == null ? "" : rs.getString("SEQ_NBR"));
			ea1095BData.setfName(rs.getString("NAME_F") == null ? "" : rs.getString("NAME_F").trim());
			ea1095BData.setmName(rs.getString("NAME_M") == null ? "" : rs.getString("NAME_M").trim());
			ea1095BData.setlName(rs.getString("NAME_L") == null ? "" : rs.getString("NAME_L").trim());
			ea1095BData.setNameGen(((rs.getString("NAME_GEN") == null || rs.getString("NAME_GEN").trim().length() == 0) ? "" : (rs.getString("NAME_GEN").trim() + " - ")) + (rs.getString("NAME_GEN_DESCR") == null ? "" : rs.getString("NAME_GEN_DESCR").trim()));
			ea1095BData.setNameGenDescr(rs.getString("NAME_GEN_DESCR") == null ? "" : rs.getString("NAME_GEN_DESCR").trim());
			ea1095BData.setSsn(rs.getString("SSN") == null ? "" : rs.getString("SSN").trim());
			ea1095BData.setDob(rs.getString("DOB") == null ? "" : rs.getString("DOB").trim());
			ea1095BData.setAllMon((rs.getString("MON_ALL") == null || rs.getString("MON_ALL").trim().equals("Y")) ? true : false);
			ea1095BData.setJan((rs.getString("MON_01") == null || rs.getString("MON_01").trim().equals("Y")) ? true : false);
			ea1095BData.setFeb((rs.getString("MON_02") == null || rs.getString("MON_02").trim().equals("Y")) ? true : false);
			ea1095BData.setMar((rs.getString("MON_03") == null || rs.getString("MON_03").trim().equals("Y")) ? true : false);
			ea1095BData.setApr((rs.getString("MON_04") == null || rs.getString("MON_04").trim().equals("Y")) ? true : false);
			ea1095BData.setMay((rs.getString("MON_05") == null || rs.getString("MON_05").trim().equals("Y")) ? true : false);
			ea1095BData.setJun((rs.getString("MON_06") == null || rs.getString("MON_06").trim().equals("Y")) ? true : false);
			ea1095BData.setJul((rs.getString("MON_07") == null || rs.getString("MON_07").trim().equals("Y")) ? true : false);
			ea1095BData.setAug((rs.getString("MON_08") == null || rs.getString("MON_08").trim().equals("Y")) ? true : false);
			ea1095BData.setSep((rs.getString("MON_09") == null || rs.getString("MON_09").trim().equals("Y")) ? true : false);
			ea1095BData.setOct((rs.getString("MON_10") == null || rs.getString("MON_10").trim().equals("Y")) ? true : false);
			ea1095BData.setNov((rs.getString("MON_11") == null || rs.getString("MON_11").trim().equals("Y")) ? true : false);
			ea1095BData.setDec((rs.getString("MON_12") == null || rs.getString("MON_12").trim().equals("Y")) ? true : false);

			// Change SSN to ###-##-#### format
			ea1095BData.setSsn(this.formatSSN(ea1095BData.getSsn()));
			// Change DOB to MM-DD-YYYY format
			ea1095BData.setDob(this.formatDOB(ea1095BData.getDob()));

			ea1095BData.setNewRow(false);
			ea1095BData.setHasChanged(false);
			return ea1095BData;
		}

		private String formatSSN(String ssn) {
			if (ssn.length() == 9 && !ssn.contains("-")) {
				ssn = ssn.substring(0,3) + "-" + ssn.substring(3,5) + "-" + ssn.substring(5,9);
			} else if ("-  -".equals(ssn.trim())) {
				ssn = "";
			}
			return ssn;
		}

		private String formatDOB(String dob) {
			if (dob.length() == 8 && !dob.contains("-")) {
				dob = dob.substring(4,6) + "-" + dob.substring(6,8) + "-" + dob.substring(0,4);
			} else if ("-  -".equals(dob.trim())) {
				dob = "";
			}
			return dob;
		}
	}

	@Override
	public String retrieveEA1095ElecConsent(String empNbr) {

		String ea1095ElecConst = "";
		String retrieveSQL = "SELECT ELEC_CONSNT_1095 FROM BHR_EMP_EMPLY A WHERE A.EMP_NBR = :empNbr";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empNbr", empNbr);

		try {
			ea1095ElecConst = (String) this.getNamedParameterJdbcTemplate().queryForObject(retrieveSQL, params, String.class);
		}
		catch (Exception e){
			return "";
		}
		return ea1095ElecConst;
	}

	@Override
	public String retrieveEA1095ElecConsentMsg() {

		String ea1095ElecConstMsg = "";
		String retrieveSQL = "SELECT A.MSG_ELEC_CONSNT_1095 FROM BHR_EAP_OPT A ";

		try {
			ea1095ElecConstMsg = (String) this.getNamedParameterJdbcTemplate().queryForObject(retrieveSQL, new MapSqlParameterSource(), String.class);
		}
		catch (Exception e){
			return "";
		}
		return ea1095ElecConstMsg;
	}

	@Override
	public List<EA1095CInfoCommand> retrieveEA1095CEmpInfo(String activeTab, String empNbr, String calYr) {

		List<EA1095CInfoCommand> acaYTD1095CEmpData = new ArrayList<EA1095CInfoCommand>();
		StringBuilder retrieveSQL = new StringBuilder();
		retrieveSQL.append("SELECT A.EMP_NBR, ");
		retrieveSQL.append("A.CAL_YR, ");
		retrieveSQL.append("A.CAL_MON, ");
		retrieveSQL.append("A.OFFR_OF_COVRG, ");
		retrieveSQL.append("A.EMP_SHR, ");
		retrieveSQL.append("A.SAFE_HRBR, ");
		retrieveSQL.append("A.SELF_INS, ");
		retrieveSQL.append("A.PLAN_STRT_MON ");
		retrieveSQL.append("FROM BHR_ACA_1095C_EMP_HIST A ");
		retrieveSQL.append("WHERE A.EMP_NBR = '" + empNbr + "' AND " );
		retrieveSQL.append("A.CAL_YR ='" + calYr + "' ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empNbr", empNbr);
		params.addValue("calYr", calYr);

		try {
			acaYTD1095CEmpData = this.getNamedParameterJdbcTemplate().query(retrieveSQL.toString(), params, new EA1095CEmpMapper());
		}
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		return acaYTD1095CEmpData;
	}

	static class EA1095CEmpMapper implements ParameterizedRowMapper<EA1095CInfoCommand> {

		public EA1095CInfoCommand mapRow(ResultSet rs, int rowNum) throws SQLException {
			EA1095CInfoCommand ea1095CEmpData = new EA1095CInfoCommand();

			ea1095CEmpData.setEmpNbr(rs.getString("EMP_NBR") == null ? "" : rs.getString("EMP_NBR").trim());
			ea1095CEmpData.setCalYr(rs.getString("CAL_YR") == null ? "" : rs.getString("CAL_YR").trim());
			ea1095CEmpData.setCalMon(rs.getString("CAL_MON") == null ? "" : rs.getString("CAL_MON").trim());
			ea1095CEmpData.setOffrOfCovrg(rs.getString("OFFR_OF_COVRG") == null ? "" : rs.getString("OFFR_OF_COVRG").trim());
			ea1095CEmpData.setEmpShr(rs.getBigDecimal("EMP_SHR").setScale(2, BigDecimal.ROUND_HALF_UP));
			ea1095CEmpData.setSafeHrbr(rs.getString("SAFE_HRBR") == null ? "" : rs.getString("SAFE_HRBR").trim());
			ea1095CEmpData.setSelfIns((rs.getString("SELF_INS") == null || rs.getString("SELF_INS").trim().equals("N")) ? false : true);
			ea1095CEmpData.setPlanStrtMon(rs.getString("PLAN_STRT_MON") == null ? "" : rs.getString("PLAN_STRT_MON").trim());

			ea1095CEmpData.setNewRow(false);
			ea1095CEmpData.setHasEmpDataChanged(false);

			return ea1095CEmpData;
		}
	}

	private String retrieve1095CCovrgInfoSql(String empNbr, String calYr){
		StringBuilder retrieveSQL = new StringBuilder();
		retrieveSQL.append("SELECT B.EMP_NBR, ");
		retrieveSQL.append("B.CAL_YR, ");
		retrieveSQL.append("B.SEQ_NBR, ");
		retrieveSQL.append("B.NAME_F, ");
		retrieveSQL.append("B.NAME_M, ");
		retrieveSQL.append("B.NAME_L, ");
		retrieveSQL.append("B.NAME_GEN, ");
		retrieveSQL.append("NAME_GEN_DESCR = (SELECT ET_C012_GEN.GEN_DESCR FROM ET_C012_GEN, BHR_OPTIONS WHERE ET_C012_GEN.GEN_CD <> '' AND  ET_C012_GEN.GEN_CD = B.NAME_GEN AND ET_C012_GEN.SCH_YR = BHR_OPTIONS.PEIMS_CD_YR), ");
		retrieveSQL.append("B.SSN, ");
		retrieveSQL.append("B.DOB, ");
		retrieveSQL.append("B.MON_ALL, ");
		retrieveSQL.append("B.MON_01, ");
		retrieveSQL.append("B.MON_02, ");
		retrieveSQL.append("B.MON_03, ");
		retrieveSQL.append("B.MON_04, ");
		retrieveSQL.append("B.MON_05, ");
		retrieveSQL.append("B.MON_06, ");
		retrieveSQL.append("B.MON_07, ");
		retrieveSQL.append("B.MON_08, ");
		retrieveSQL.append("B.MON_09, ");
		retrieveSQL.append("B.MON_10, ");
		retrieveSQL.append("B.MON_11, ");
		retrieveSQL.append("B.MON_12 ");
		retrieveSQL.append("FROM BHR_ACA_1095C_COVRD_HIST B ");
		retrieveSQL.append("WHERE B.EMP_NBR = '" + empNbr + "' AND ");
		retrieveSQL.append("B.CAL_YR ='" + calYr + "' ");
		//ts20170601 TxEIS 35930 - include emp_flg as the first sort order item
		retrieveSQL.append("ORDER BY B.EMP_FLG DESC, B.NAME_F, B.NAME_M, B.NAME_L, B.NAME_GEN, B.SSN, B.DOB, B.SEQ_NBR");

		return retrieveSQL.toString();
	}

	@Override
	public Page<EA1095CInfoCommand> retrieveEA1095CCovrgInfo(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize) {
		Object[] params = new Object[] {};
		PaginationHelper<EA1095CInfoCommand> ph = new PaginationHelper<EA1095CInfoCommand>();
		return ph.fetchPage(this.getJdbcTemplate(), this.retrieve1095CCovrgInfoSql(empNbr, calYr), params, pageNbr, pageSize, new EA1095CCovrgMapper());
	}

	@Override
	public List<EA1095CInfoCommand> retrieveEA1095CCovrgInfo(String activeTab, String empNbr, String calYr) {

		List<EA1095CInfoCommand> acaYTD1095CEmpData = new ArrayList<EA1095CInfoCommand>();
		String retrieveSQL = this.retrieve1095CCovrgInfoSql(empNbr, calYr);
		
		MapSqlParameterSource params = new MapSqlParameterSource();

		try {
			acaYTD1095CEmpData = this.getNamedParameterJdbcTemplate().query(retrieveSQL.toString(), params, new EA1095CCovrgMapper());
		}
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}

		return acaYTD1095CEmpData;
	}

	static class EA1095CCovrgMapper implements ParameterizedRowMapper<EA1095CInfoCommand> {

		public EA1095CInfoCommand mapRow(ResultSet rs, int rowNum) throws SQLException {
			EA1095CInfoCommand ea1095CCovrgData = new EA1095CInfoCommand();

			ea1095CCovrgData.setEmpNbr(rs.getString("EMP_NBR") == null ? "" : rs.getString("EMP_NBR").trim());
			ea1095CCovrgData.setCalYr(rs.getString("CAL_YR") == null ? "" : rs.getString("CAL_YR").trim());
			ea1095CCovrgData.setSeqNbr(rs.getString("SEQ_NBR") == null ? "" : rs.getString("SEQ_NBR"));
			ea1095CCovrgData.setfName(rs.getString("NAME_F") == null ? "" : rs.getString("NAME_F").trim());
			ea1095CCovrgData.setmName(rs.getString("NAME_M") == null ? "" : rs.getString("NAME_M").trim());
			ea1095CCovrgData.setlName(rs.getString("NAME_L") == null ? "" : rs.getString("NAME_L").trim());
			ea1095CCovrgData.setNameGen(((rs.getString("NAME_GEN") == null || rs.getString("NAME_GEN").trim().length() == 0) ? "" : (rs.getString("NAME_GEN").trim() + " - ")) + (rs.getString("NAME_GEN_DESCR") == null ? "" : rs.getString("NAME_GEN_DESCR").trim()));
			ea1095CCovrgData.setNameGenDescr(rs.getString("NAME_GEN_DESCR") == null ? "" : rs.getString("NAME_GEN_DESCR").trim());
			ea1095CCovrgData.setSsn(rs.getString("SSN") == null ? "" : rs.getString("SSN").trim());
			ea1095CCovrgData.setDob(rs.getString("DOB") == null ? "" : rs.getString("DOB").trim());
			ea1095CCovrgData.setAllMon((rs.getString("MON_ALL") == null || rs.getString("MON_ALL").trim().equals("Y")) ? true : false);
			ea1095CCovrgData.setJan((rs.getString("MON_01") == null || rs.getString("MON_01").trim().equals("Y")) ? true : false);
			ea1095CCovrgData.setFeb((rs.getString("MON_02") == null || rs.getString("MON_02").trim().equals("Y")) ? true : false);
			ea1095CCovrgData.setMar((rs.getString("MON_03") == null || rs.getString("MON_03").trim().equals("Y")) ? true : false);
			ea1095CCovrgData.setApr((rs.getString("MON_04") == null || rs.getString("MON_04").trim().equals("Y")) ? true : false);
			ea1095CCovrgData.setMay((rs.getString("MON_05") == null || rs.getString("MON_05").trim().equals("Y")) ? true : false);
			ea1095CCovrgData.setJun((rs.getString("MON_06") == null || rs.getString("MON_06").trim().equals("Y")) ? true : false);
			ea1095CCovrgData.setJul((rs.getString("MON_07") == null || rs.getString("MON_07").trim().equals("Y")) ? true : false);
			ea1095CCovrgData.setAug((rs.getString("MON_08") == null || rs.getString("MON_08").trim().equals("Y")) ? true : false);
			ea1095CCovrgData.setSep((rs.getString("MON_09") == null || rs.getString("MON_09").trim().equals("Y")) ? true : false);
			ea1095CCovrgData.setOct((rs.getString("MON_10") == null || rs.getString("MON_10").trim().equals("Y")) ? true : false);
			ea1095CCovrgData.setNov((rs.getString("MON_11") == null || rs.getString("MON_11").trim().equals("Y")) ? true : false);
			ea1095CCovrgData.setDec((rs.getString("MON_12") == null || rs.getString("MON_12").trim().equals("Y")) ? true : false);

			// Change SSN to ###-##-#### format
			ea1095CCovrgData.setSsn(this.formatSSN(ea1095CCovrgData.getSsn()));
			// Change DOB to MM-DD-YYYY format
			ea1095CCovrgData.setDob(this.formatDOB(ea1095CCovrgData.getDob()));

			ea1095CCovrgData.setNewRow(false);
			ea1095CCovrgData.setHasCovrgDataChanged(false);
			return ea1095CCovrgData;
		}

		private String formatSSN(String ssn) {
			if (ssn.length() == 9 && !ssn.contains("-")) {
				ssn = ssn.substring(0,3) + "-" + ssn.substring(3,5) + "-" + ssn.substring(5,9);
			} else if ("-  -".equals(ssn.trim())) {
				ssn = "";
			}
			return ssn;
		}

		private String formatDOB(String dob) {
			if (dob.length() == 8 && !dob.contains("-")) {
				dob = dob.substring(4,6) + "-" + dob.substring(6,8) + "-" + dob.substring(0,4);
			} else if ("-  -".equals(dob.trim())) {
				dob = "";
			}
			return dob;
		}
	}

	// Functionality for Column Sort - Reserved for future Use to Sort by Columns
	private String setSortCol(String sortColumn, String sortOrder) {
		String sortCol = "";
		if (sortColumn.equals("FNAME")) {
			sortCol = " B.NAME_F "  + sortOrder + " ";
		} else if (sortColumn.equals("MNAME")) {
			sortCol = " B.NAME_M "  + sortOrder + " ";
		} else if (sortColumn.equals("LNAME")) {
			sortCol = " B.NAME_L "  + sortOrder + " ";
		} else if (sortColumn.equals("GEN")) {
			sortCol = " B.NAME_GEN "  + sortOrder + " ";
		} else if (sortColumn.equals("SSN")) {
			sortCol = " B.SSN "  + sortOrder + " ";
		} else if (sortColumn.equals("DOB")) {
			sortCol = " B.DOB "  + sortOrder + " ";
		} else if (sortColumn.equals("ALL")) {
			sortCol = " B.MON_ALL "  + sortOrder + " ";
		} else if (sortColumn.equals("JAN")) {
			sortCol = " B.MON_01 "  + sortOrder + " ";
		} else if (sortColumn.equals("FEB")) {
			sortCol = " B.MON_02 "  + sortOrder + " ";
		} else if (sortColumn.equals("MAR")) {
			sortCol = " B.MON_03 "  + sortOrder + " ";
		} else if (sortColumn.equals("APR")) {
			sortCol = " B.MON_04 "  + sortOrder + " ";
		} else if (sortColumn.equals("MAY")) {
			sortCol = " B.MON_05 "  + sortOrder + " ";
		} else if (sortColumn.equals("JUN")) {
			sortCol = " B.MON_06 "  + sortOrder + " ";
		} else if (sortColumn.equals("JUL")) {
			sortCol = " B.MON_07 "  + sortOrder + " ";
		} else if (sortColumn.equals("AUG")) {
			sortCol = " B.MON_08 "  + sortOrder + " ";
		} else if (sortColumn.equals("SEP")) {
			sortCol = " B.MON_09 "  + sortOrder + " ";
		} else if (sortColumn.equals("OCT")) {
			sortCol = " B.MON_10 "  + sortOrder + " ";
		} else if (sortColumn.equals("NOV")) {
			sortCol = " B.MON_11 "  + sortOrder + " ";
		} else if (sortColumn.equals("DEC")) {
			sortCol = " B.MON_12 "  + sortOrder + " ";
		}

		if ("".equals(sortCol.trim())) {
			sortCol = " B.NAME_F ASC ";
		}
		return sortCol;
	}

	@Override
	public boolean updateEA1095ElecConsent(String empNbr, String ea1095ElecConsnt) {
		int rows = 0;
		String updateEA1095ElecConsntSql = "UPDATE BHR_EMP_EMPLY SET ELEC_CONSNT_1095 =:ea1095ElecConsnt, MODULE = 'Employee Access' WHERE EMP_NBR =:empNbr";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empNbr", empNbr);
		params.addValue("ea1095ElecConsnt",ea1095ElecConsnt);

		try {
			rows = this.getNamedParameterJdbcTemplate().update(updateEA1095ElecConsntSql, params);
			return rows > 0 ;
		}
		catch(EmptyResultDataAccessException ex) {
			return false;
		}
	}

	@Override
	public String getEA1095BCovrgInfoSQL(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize) {
		StringBuilder retrieveSQL = new StringBuilder();
		retrieveSQL.append("SELECT B.EMP_NBR, ");
		retrieveSQL.append("B.CAL_YR, ");
		retrieveSQL.append("B.SEQ_NBR, ");
		retrieveSQL.append("B.NAME_F, ");
		retrieveSQL.append("B.NAME_M, ");
		retrieveSQL.append("B.NAME_L, ");
		retrieveSQL.append("B.NAME_GEN, ");
		retrieveSQL.append("B.SSN, ");
		retrieveSQL.append("B.DOB, ");
		retrieveSQL.append("B.MON_ALL, ");
		retrieveSQL.append("B.MON_01, ");
		retrieveSQL.append("B.MON_02, ");
		retrieveSQL.append("B.MON_03, ");
		retrieveSQL.append("B.MON_04, ");
		retrieveSQL.append("B.MON_05, ");
		retrieveSQL.append("B.MON_06, ");
		retrieveSQL.append("B.MON_07, ");
		retrieveSQL.append("B.MON_08, ");
		retrieveSQL.append("B.MON_09, ");
		retrieveSQL.append("B.MON_10, ");
		retrieveSQL.append("B.MON_11, ");
		retrieveSQL.append("B.MON_12 ");
		retrieveSQL.append("FROM BHR_ACA_1095B_COVRD_HIST B ");
		retrieveSQL.append("WHERE B.EMP_NBR = '" + empNbr + "' AND ");
		retrieveSQL.append("B.CAL_YR ='" + calYr + "' ");

		String sortCol = "ORDER BY " + setSortCol(sortColumn, sortOrder);
		retrieveSQL.append(sortCol);

		return retrieveSQL.toString();
	}

/*	@Override
	public String getEA1095CCovrgInfoSQL(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize) {
		StringBuilder retrieveSQL = new StringBuilder();
		retrieveSQL.append("SELECT B.EMP_NBR, ");
		retrieveSQL.append("B.CAL_YR, ");
		retrieveSQL.append("B.SEQ_NBR, ");
		retrieveSQL.append("B.NAME_F, ");
		retrieveSQL.append("B.NAME_M, ");
		retrieveSQL.append("B.NAME_L, ");
		retrieveSQL.append("B.NAME_GEN, ");
		retrieveSQL.append("B.SSN, ");
		retrieveSQL.append("B.DOB, ");
		retrieveSQL.append("B.MON_ALL, ");
		retrieveSQL.append("B.MON_01, ");
		retrieveSQL.append("B.MON_02, ");
		retrieveSQL.append("B.MON_03, ");
		retrieveSQL.append("B.MON_04, ");
		retrieveSQL.append("B.MON_05, ");
		retrieveSQL.append("B.MON_06, ");
		retrieveSQL.append("B.MON_07, ");
		retrieveSQL.append("B.MON_08, ");
		retrieveSQL.append("B.MON_09, ");
		retrieveSQL.append("B.MON_10, ");
		retrieveSQL.append("B.MON_11, ");
		retrieveSQL.append("B.MON_12 ");
		retrieveSQL.append("FROM BHR_ACA_1095C_COVRD_HIST B ");
		retrieveSQL.append("WHERE B.EMP_NBR = '" + empNbr + "' AND ");
		retrieveSQL.append("B.CAL_YR ='" + calYr + "' ");

		String sortCol = "ORDER BY " + setSortCol(sortColumn, sortOrder);
		retrieveSQL.append(sortCol);

		return retrieveSQL.toString();
	}
*/

	@Override
	public District get1095SHOPParams(String calYr){

		District shopDistrict = new District();

		StringBuilder retrieveSQL = new StringBuilder();

		String yr = "";

		if(calYr.length() == 4){
			yr = calYr.substring(2);
		}

		retrieveSQL.append("SELECT MIN(Case S.PARAMETER When 'esc_city' Then S.VALUE End) city,");
		retrieveSQL.append("MIN(Case S.PARAMETER When 'esc_ein' Then S.VALUE End) ein, ");
		retrieveSQL.append("MIN(Case S.PARAMETER When 'esc_name' Then S.VALUE End) escname,");
		retrieveSQL.append("MIN(Case S.PARAMETER When 'esc_st' Then S.VALUE End) st,            ");
		retrieveSQL.append("MIN(Case S.PARAMETER When 'esc_str1' Then S.VALUE End) str1,        ");
		retrieveSQL.append("MIN(Case S.PARAMETER When 'esc_str2' Then S.VALUE End) str2,        ");
		retrieveSQL.append("MIN(Case S.PARAMETER When 'esc_zip' Then S.VALUE End) zip,          ");
		retrieveSQL.append("MIN(Case S.PARAMETER When 'esc_zip_sf' Then S.VALUE End) zipsf      ");
		retrieveSQL.append("FROM BR_PARAMETER S WHERE REPORT_ID LIKE 'HRS5250' AND USER_NAME LIKE 'PAYPRCACA");
		retrieveSQL.append(yr);
		retrieveSQL.append("' ");

		MapSqlParameterSource params = new MapSqlParameterSource();

		try {
			shopDistrict = (District) this.getNamedParameterJdbcTemplate().queryForObject(retrieveSQL.toString(), params, new DistrictShopMapper());
		}
		catch (Exception e){
			return shopDistrict;
		}

		return shopDistrict;
	}

	static class DistrictShopMapper implements ParameterizedRowMapper<District> {

		public District mapRow(ResultSet rs, int rowNum) throws SQLException {
			District d = new District();

			d.setAddress(StringUtil.trim(rs.getString("str1")) + " " + StringUtil.trim(rs.getString("str2")));
			d.setName(StringUtil.trim(rs.getString("escname")));
			d.setCity(StringUtil.trim(rs.getString("city")));
			d.setState(StringUtil.trim(rs.getString("st")));
			d.setZip(StringUtil.trim(rs.getString("zip")));
			d.setZip4(StringUtil.trim(rs.getString("zipsf")));
			d.setNumber(StringUtil.trim(rs.getString("ein")));

			return d;
		}
	}

	@Override
	public ReportingContact getReportingContact() {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT	  A.TYPE, ");
		//sql.append(			" A.NAME, ");
		sql.append(			" A.PHONE_AREA, ");
		sql.append(			" A.PHONE_NBR, ");
		sql.append(			" A.PHONE_EXT, ");
		sql.append(			" A.FAX_AREA, ");
		sql.append(			" A.FAX_NBR, ");
		sql.append(			" A.EMAIL_ADDR, ");
		sql.append(			" A.TCC, ");
		sql.append(			" A.SHOP_NAME, ");
		sql.append(			" A.SHOP_EIN, ");
		sql.append(			" A.SHOP_Addr, ");
		sql.append(			" A.SHOP_City, ");
		sql.append(			" A.SHOP_St, ");
		sql.append(			" A.SHOP_ZIP, ");
		sql.append(			" A.SHOP_ZIP4, ");
		sql.append(			" A.NAME_L, ");
		sql.append(			" A.NAME_F, ");
		sql.append(			" A.NAME_M, ");
		sql.append(			" A.NAME_SUFFIX, ");
		sql.append(			" NAME_SUFFIX_DESCR = (CASE WHEN A.NAME_SUFFIX <> '' THEN ' ' END + F_HRS_GET_GENERATION_CODE(A.NAME_SUFFIX)) " );
		sql.append("FROM BR_RPTNG_CONTACT A ");
		sql.append("WHERE A.TYPE = '1095ELECTRONIC' ");

		try {
			return (ReportingContact) this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(), new MapSqlParameterSource(), new ReportingContactRowMapper());
		}
		catch (Exception e) {
			ReportingContact row = new ReportingContact();
			row.setType("1095ELECTRONIC");

			return row;
		}
	}

	private static class ReportingContactRowMapper implements ParameterizedRowMapper<ReportingContact> {
		public ReportingContact mapRow(ResultSet rs, int rowNum) throws SQLException {

			ReportingContact row = new ReportingContact();

			//Type.
			String value = (rs.getString("TYPE") == null ? "" : rs.getString("TYPE").trim());
			row.setType(value);

			//Last Name
			value = (rs.getString("NAME_L") == null ? "" : rs.getString("NAME_L").trim());
			row.setNameL(value);

			//First Name
			value = (rs.getString("NAME_F") == null ? "" : rs.getString("NAME_F").trim());
			row.setNameF(value);

			//Middle Name
			value = (rs.getString("NAME_M") == null ? "" : rs.getString("NAME_M").trim());
			row.setNameM(value);

			//Suffix/Generation Name
			value = (rs.getString("NAME_SUFFIX") == null ? "" : rs.getString("NAME_SUFFIX").trim());
			row.setNameSuffix(value);

			//Suffix/Generation Name Description
			value = (rs.getString("NAME_SUFFIX_DESCR") == null ? "" : rs.getString("NAME_SUFFIX_DESCR").trim());
			row.setNameSuffixDescr(value);

			//Phone Area
			value = (rs.getString("PHONE_AREA") == null ? "" : rs.getString("PHONE_AREA").trim());
			row.setPhoneArea(value);

			//Phone Nbr
			value = (rs.getString("PHONE_NBR") == null ? "" : rs.getString("PHONE_NBR").trim());
			row.setPhoneNbr(value);

			//Phone Ext
			value = (rs.getString("PHONE_EXT") == null ? "" : rs.getString("PHONE_EXT").trim());
			row.setPhoneExt(value);

			//Fax Area	
			value = (rs.getString("FAX_AREA") == null ? "" : rs.getString("FAX_AREA").trim());
			row.setFaxArea(value);

			//Fax Nbr
			value = (rs.getString("FAX_NBR") == null ? "" : rs.getString("FAX_NBR").trim());
			row.setFaxNbr(value);

			//Email Addr
			value = (rs.getString("EMAIL_ADDR") == null ? "" : rs.getString("EMAIL_ADDR").trim());
			row.setEmailAddr(value);
			
			//TCC
			value = (rs.getString("TCC") == null ? "" : rs.getString("TCC").trim());
			row.setTcc(value);

			//SHOP Name
			value = (rs.getString("SHOP_NAME") == null ? "" : rs.getString("SHOP_NAME").trim());
			row.setShopName(value);

			//SHOP EIN
			value = (rs.getString("SHOP_EIN") == null ? "" : rs.getString("SHOP_EIN").trim());
			row.setShopEIN(value);

			//SHOP Addr
			value = (rs.getString("SHOP_ADDR") == null ? "" : rs.getString("SHOP_ADDR").trim());
			row.setShopAddr(value);

			//SHOP City	
			value = (rs.getString("SHOP_CITY") == null ? "" : rs.getString("SHOP_CITY").trim());
			row.setShopCity(value);

			//SHOP St
			value = (rs.getString("SHOP_St") == null ? "" : rs.getString("SHOP_St").trim());
			row.setShopSt(value);

			//SHOP ZIP
			value = (rs.getString("SHOP_ZIP") == null ? "" : rs.getString("SHOP_ZIP").trim());
			row.setShopZip(value);

			//SHOP ZIP4
			value = (rs.getString("SHOP_ZIP4") == null ? "" : rs.getString("SHOP_ZIP4").trim());
			row.setShopZip4(value);

			return row;
		}
	}
}