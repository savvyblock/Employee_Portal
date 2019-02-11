package net.esc20.txeis.EmployeeAccess.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import net.esc20.txeis.EmployeeAccess.domainobject.CalendarDetail;
import net.esc20.txeis.EmployeeAccess.domainobject.CalendarEvents;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveEmployeeData;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveEmployeePMISData;
import net.esc20.txeis.EmployeeAccess.domainobject.LeavePayDate;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequestComment;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveTemporaryApprover;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.AbsenceReason;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveParameters;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveType;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveUnitsConversion;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.PayFrequency;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class LeaveRequestDao extends NamedParameterJdbcDaoSupport {

	public static final String PENDING_PAYROLL_COUNTS_HASH_KEY_REQUEST_ID = "REQUESTID";
	public static final String PENDING_PAYROLL_COUNTS_HASH_KEY_REQUEST_NUM_DAYS = "REQUESTNUMDAYS";
	public static final String PENDING_PAYROLL_COUNTS_HASH_KEY_NUM_PROCESSED = "NUMPROCESSED";
	public static final String PENDING_PAYROLL_COUNTS_HASH_KEY_NUM_PENDING = "NUMPENDING";
	

	private String sqlInsertLeaveComment = "INSERT INTO BEA_EMP_LV_COMMENTS (LV_ID, LV_COMMENT_TYP, LV_COMMENT_EMP_NBR, LV_COMMENT) VALUES (:leaveId, :commentType, :employeeNumber, :requestComment)";
	private String sqlUpdateRequestStatus = "UPDATE BEA_EMP_LV_RQST set STATUS_CD=:status WHERE ID=:leaveId";
	private String sqlUpdateRequestCommentTypeToPriorWF = "UPDATE BEA_EMP_LV_COMMENTS set LV_COMMENT_TYP='P' WHERE LV_ID=:leaveId AND LV_COMMENT_TYP='C' AND LV_COMMENT_EMP_NBR=:employeeNumber";

/* * leave request are no longer deleted but marked as deleted by setting the status_cd to 'X'
	private String sqlDeleteRequest = "DELETE FROM BEA_EMP_LV_RQST WHERE ID=:leaveId";
	private String sqlDeleteRequestComments = "DELETE FROM BEA_EMP_LV_COMMENTS WHERE LV_ID=:leaveId";
	private String sqlDeleteRequestWorkflow = "DELETE FROM BEA_EMP_LV_WORKFLOW WHERE LV_ID=:leaveId";
 * 
 */
	private String sqlInsertLeaveWorkflowItem = "INSERT INTO BEA_EMP_LV_WORKFLOW (LV_ID, SEQ_NUM, APPRVR_EMP_NBR) " +
			" VALUES (:leaveId, :sequenceNumber, :approverEmployeeNumber)";
	

	private String sqlSelectSupervisorDirectReports = "SELECT E2S.EMP_EMP_NBR, ED.NAME_F AS FIRST_NAME, ED.NAME_L AS LAST_NAME, ED.NAME_M AS MIDDLE_NAME, " +
			"(SELECT COUNT(*) FROM BHR_EAP_EMP_TO_SPVSR E2S WHERE E2S.SPVSR_EMP_NBR=ED.EMP_NBR) AS NUM_DIRECT_REPORTS " +
			"FROM BHR_EAP_EMP_TO_SPVSR E2S, BHR_EMP_DEMO ED WHERE E2S.SPVSR_EMP_NBR=:employeeNumber AND E2S.EMP_EMP_NBR=ED.EMP_NBR ORDER BY LAST_NAME ASC, FIRST_NAME ASC, MIDDLE_NAME ASC";
	private String PMIS_sqlSelectSupervisorDirectReports = "SELECT PPC.OCC_EMP_NBR, PPC.PAY_FREQ, ED.NAME_F AS FIRST_NAME, ED.NAME_L AS LAST_NAME, ED.NAME_M AS MIDDLE_NAME, " +
			"(SELECT COUNT(*) FROM BHR_PMIS_POS_CTRL PPC3 WHERE PPC3.SPVSR_BILLET_NBR=PPC.BILLET_NBR " +
				"AND PPC3.SPVSR_POS_NBR=PPC.POS_NBR " +
				"AND PPC3.PAY_FREQ=(SELECT MAX(PPC4.PAY_FREQ) FROM BHR_PMIS_POS_CTRL PPC4 WHERE PPC4.OCC_EMP_NBR=PPC3.OCC_EMP_NBR AND PPC4.POS_TYP='P' AND PPC4.CYR_NYR_FLG='C') " +
				"AND PPC3.POS_TYP='P' AND PPC3.CYR_NYR_FLG='C') AS NUM_DIRECT_REPORTS " +
			"FROM BHR_PMIS_POS_CTRL PPC, BHR_EMP_DEMO ED WHERE PPC.SPVSR_BILLET_NBR=:billetNumber " +
				"AND PPC.SPVSR_POS_NBR=:posNumber " +
				"AND PPC.PAY_FREQ=(SELECT MAX(PPC2.PAY_FREQ) FROM BHR_PMIS_POS_CTRL PPC2 WHERE PPC2.OCC_EMP_NBR=PPC.OCC_EMP_NBR AND PPC2.POS_TYP='P' AND PPC2.CYR_NYR_FLG='C') " +
				"AND PPC.POS_TYP='P' AND PPC.CYR_NYR_FLG='C' AND PPC.OCC_EMP_NBR=ED.EMP_NBR ORDER BY LAST_NAME ASC, FIRST_NAME ASC, MIDDLE_NAME ASC";

	private String sqlSelectFirstLineApproverDemo = "SELECT ED.EMP_NBR, ED.NAME_F, ED.NAME_M, ED.NAME_L, ED.EMAIL " + 
				"FROM BHR_EMP_DEMO ED " + 
				"WHERE ED.EMP_NBR= " + 
					"(SELECT ISNULL(TA.TMP_APPRVR_EMP_NBR, E2S.SPVSR_EMP_NBR) AS APPROVER " +
						"FROM BHR_EAP_EMP_TO_SPVSR E2S " +
							"LEFT OUTER JOIN BEA_EMP_LV_TMP_APPROVERS TA ON TA.SPVSR_EMP_NBR=E2S.SPVSR_EMP_NBR AND TA.DATETIME_FROM <= GETDATE() AND GETDATE() <= TA.DATETIME_TO " +
						"WHERE E2S.EMP_EMP_NBR=:employeeNumber )";
	private String sqlSelectFirstLineApproverDemoNoTemp = "SELECT ED.EMP_NBR, ED.NAME_F, ED.NAME_M, ED.NAME_L, ED.EMAIL " + 
			"FROM BHR_EMP_DEMO ED " + 
			"WHERE ED.EMP_NBR= " + 
				"(SELECT E2S.SPVSR_EMP_NBR " +
					"FROM BHR_EAP_EMP_TO_SPVSR E2S " +
					"WHERE E2S.EMP_EMP_NBR=:employeeNumber )";

	/* using employee's supervisor's billet and pos numbers .... */
	private String 	PMIS_sqlSelectFirstLineApproverDemo = "SELECT ED.EMP_NBR, ED.NAME_F, ED.NAME_M, ED.NAME_L, ED.EMAIL " +
			"FROM BHR_EMP_DEMO ED  " +
			"WHERE ED.EMP_NBR=( " +
				"SELECT ISNULL( " +
					"(SELECT TA.TMP_APPRVR_EMP_NBR FROM BEA_EMP_LV_TMP_APPROVERS TA WHERE TA.SPVSR_EMP_NBR=(SELECT PPC2.OCC_EMP_NBR FROM BHR_PMIS_POS_CTRL PPC2 WHERE PPC2.BILLET_NBR=:supervisorBilletNumber AND PPC2.POS_NBR=:supervisorPosNumber AND PPC2.POS_TYP='P' AND PPC2.CYR_NYR_FLG='C') AND TA.DATETIME_FROM <= GETDATE() AND GETDATE() <= TA.DATETIME_TO)," +
					"(SELECT PPC2.OCC_EMP_NBR FROM BHR_PMIS_POS_CTRL PPC2 WHERE PPC2.BILLET_NBR=:supervisorBilletNumber AND PPC2.POS_NBR=:supervisorPosNumber AND PPC2.POS_TYP='P' AND PPC2.CYR_NYR_FLG='C')" +
				             ") " +
			") ";
	private String 	PMIS_sqlSelectFirstLineApproverDemoNoTemp = "SELECT ED.EMP_NBR, ED.NAME_F, ED.NAME_M, ED.NAME_L, ED.EMAIL " +
			"FROM BHR_EMP_DEMO ED  " +
			"WHERE ED.EMP_NBR=( " +
					"SELECT PPC2.OCC_EMP_NBR FROM BHR_PMIS_POS_CTRL PPC2 WHERE PPC2.BILLET_NBR=:supervisorBilletNumber AND PPC2.POS_NBR=:supervisorPosNumber AND PPC2.POS_TYP='P' AND PPC2.CYR_NYR_FLG='C' " +      
			") ";

	private String sqlInsertXmital = "INSERT INTO BHR_EMP_LV_XMITAL " +
			"(ABS_RSN, BEG_TIME, CYR_NYR_FLG, DT_OF_ABS, DT_OF_PAY, EMP_NBR, LV_ID, LV_SEQ, LV_TYP, LV_UNITS_USED, LV_UNITS_EARNED, MODULE, PAY_FREQ, PROCESS_DT, RSN, SUBST, USER_ID) " +
			"VALUES (:absenceReason, '', 'C', :dateOfAbsence, :payDate, :employeeNumber, :leaveId, " + 
				"(SELECT (ISNULL(MAX(LV_SEQ),0)+1) FROM BHR_EMP_LV_XMITAL WHERE PAY_FREQ=:payFrequency AND EMP_NBR=:employeeNumber AND CYR_NYR_FLG='C' AND DT_OF_PAY=:payDate), " + 
				":leaveType, :leaveUnitsDaily, 0, 'EA Online Leave System', " +
				":payFrequency, '', '', '', :approverLoginName)";
		
	// removed AND TA.DATETIME_TO > GETDATE() condition ... go ahead and see expired ones for now
	private String sqlSelectTemporaryApprovers  = "SELECT TA.ID, TA.DATETIME_FROM, TA.DATETIME_TO, TA.TMP_APPRVR_EMP_NBR, ED.NAME_F, ED.NAME_M, ED.NAME_L " +
			"FROM BEA_EMP_LV_TMP_APPROVERS TA, BHR_EMP_DEMO ED WHERE SPVSR_EMP_NBR=:supervisorEmployeeNumber AND TA.TMP_APPRVR_EMP_NBR=ED.EMP_NBR ORDER BY DATETIME_FROM ASC";  
	private String sqlInsertTemporaryApprover = "INSERT INTO BEA_EMP_LV_TMP_APPROVERS (DATETIME_FROM, DATETIME_TO, SPVSR_EMP_NBR, TMP_APPRVR_EMP_NBR) VALUES (:fromDateTime, :toDateTime, :supervisorEmployeeNumber, :tempApproverEmployeeNumber)";
	private String sqlUpdateTemporaryApprover = "UPDATE BEA_EMP_LV_TMP_APPROVERS set DATETIME_FROM=:fromDateTime, DATETIME_TO=:toDateTime, TMP_APPRVR_EMP_NBR=:tempApproverEmployeeNumber WHERE ID=:temporaryApproverRowId";
	private String sqlDeleteTemporaryApprover = "DELETE FROM BEA_EMP_LV_TMP_APPROVERS WHERE ID=:temporaryApproverRowId";

	private String sqlSelectLeaveTimePeriodsPayFreq = "SELECT ID, DATETIME_FROM, DATETIME_TO " +
		"FROM BEA_EMP_LV_RQST WHERE STATUS_CD IN ('A','P','L','C') AND PAY_FREQ=:payFrequency AND EMP_NBR=:employeeNumber AND DATETIME_TO > DATEADD(YY, -1, GETDATE())";
	
	private String sqlSelectLeaveTimePeriods = "SELECT ID, DATETIME_FROM, DATETIME_TO " +
			"FROM BEA_EMP_LV_RQST WHERE STATUS_CD IN ('A','P','L','C') AND EMP_NBR=:employeeNumber AND DATETIME_TO > DATEADD(YY, -1, GETDATE())";
	
	private String sqlSelectNonSpvsrLeaveRequestApprovalAccess = 
		"SELECT ISNULL((SELECT DISTINCT 1 FROM BEA_EMP_LV_TMP_APPROVERS WHERE TMP_APPRVR_EMP_NBR=:employeeNumber AND GETDATE() >= DATETIME_FROM AND GETDATE() <= DATETIME_TO), " +
			"(SELECT COUNT(*) FROM BEA_EMP_LV_RQST ELR,  BEA_EMP_LV_WORKFLOW ELW WHERE ELW.APPRVR_EMP_NBR=:employeeNumber AND ELW.LV_ID = ELR.ID  AND ELR.STATUS_CD = 'P' " + 
				"AND ELW.INSERT_DATETIME = (SELECT MAX(ELW2.INSERT_DATETIME) FROM BEA_EMP_LV_WORKFLOW ELW2 WHERE ELW2.LV_ID=ELW.LV_ID) )) AS ACCESS";
			
	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM-dd-yyyy hh:mmaa");
	private SimpleDateFormat dateTimeFormat24hr = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mmaa");
	private SimpleDateFormat bhrDateFormat = new SimpleDateFormat ("yyyyMMdd");

	@Autowired
	public LeaveRequestDao(@Qualifier("datasource") DataSource datasource) {
		this.setDataSource(datasource);
	}

	public LeaveParameters getLeaveParams() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT BO.STANDARD_HRS, BEO.LV_MEAL_BREAK_HRS, BEO.REQ_LV_HRS_REQSTD, BEO.USE_PMIS_SPVSR_LEVELS, BEO.IGN_CUTOFF_DT, ");
		sql.append("	BEO.MSG_LV_REQ, BEO.URL_HM  ");
		sql.append("FROM BHR_OPTIONS BO, BHR_EAP_OPT BEO");
		
		LeaveParameters leaveParameters=null;
		try {
			leaveParameters = (LeaveParameters) getNamedParameterJdbcTemplate().query(sql.toString(), 
				new ResultSetExtractor<LeaveParameters>() {
					@Override
					public LeaveParameters extractData(final ResultSet rs) throws SQLException, DataAccessException {
						LeaveParameters result = new LeaveParameters();
						rs.next();
						result.setStandardHours(rs.getDouble("STANDARD_HRS"));
						result.setMealBreakHours(rs.getDouble("LV_MEAL_BREAK_HRS"));
						result.setRequireLeaveHoursRequestedEntry((rs.getString("REQ_LV_HRS_REQSTD").trim().toUpperCase().equals("Y") ? true : false));
						result.setUsePMIS((rs.getString("USE_PMIS_SPVSR_LEVELS").trim().toUpperCase().equals("Y") ? true : false));
						result.setIgnoreCutoffDates((rs.getString("IGN_CUTOFF_DT").trim().toUpperCase().equals("Y") ? true : false));
						result.setMessageLeaveRequest(rs.getString("MSG_LV_REQ"));
						result.setUrlEAHome(rs.getString("URL_HM"));
						return result;
					}
				});
		} catch (DataAccessException e) {	
			e.printStackTrace();
		}
		return leaveParameters;
	}

	public String getLeaveTypeNotes(String leaveType) {
		String notes="";
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT NOTES FROM BTHR_LV_TYP_NOTES WHERE LV_TYP=:leaveType");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("leaveType", leaveType);
		try {
			notes = (String) getNamedParameterJdbcTemplate().query(sql.toString(), params,
				new ResultSetExtractor<String>() {
					@Override
					public String extractData(final ResultSet rs) throws SQLException, DataAccessException {
						rs.next();
						String result = rs.getString("NOTES").trim();
						return result;
					}
				});
		} catch (DataAccessException e) {	
		}
		return notes;
	}

	public LeaveEmployeeData getEmployeeData(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ED.EMP_NBR, ED.NAME_F, ED.NAME_M, ED.NAME_L, ED.EMAIL, ISNULL(SU.USR_LOG_NAME,'') AS USR_LOG_NAME ");
		sql.append("FROM BHR_EMP_DEMO ED LEFT OUTER JOIN SEC_USERS SU ON ED.EMP_NBR=SU.EMP_NBR AND SU.USR_DELETED=0, ");
		sql.append("WHERE ED.EMP_NBR= :employeeNumber");
		
		LeaveEmployeeData employeeData=null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		try {
			employeeData = (LeaveEmployeeData) getNamedParameterJdbcTemplate().query(sql.toString(), params,
				new ResultSetExtractor<LeaveEmployeeData>() {
					@Override
					public LeaveEmployeeData extractData(final ResultSet rs) throws SQLException, DataAccessException {
						LeaveEmployeeData result = new LeaveEmployeeData();
						rs.next();
						result.setEmployeeNumber(rs.getString("EMP_NBR").trim());
						result.setFirstName(rs.getString("NAME_F").trim());
						result.setMiddleName(rs.getString("NAME_M").trim());
						result.setLastName(rs.getString("NAME_L").trim());
						result.setEmailAddress(rs.getString("EMAIL").trim());
						result.setUserLoginName(rs.getString("USR_LOG_NAME").trim());
						result.setAutoCompleteString(result.getSelectOptionLabel());
						return result;
					}
				});
		} catch (DataAccessException e) {	
		}
		return employeeData;
	}

	public List<PayFrequency> getEmployeePayFrequencies(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT EL.PAY_FREQ, PF.PAY_FREQ_DESCR ");
		sql.append("FROM BHR_EMP_LV EL, BTHR_PAY_FREQ PF ");
		sql.append("WHERE EL.CYR_NYR_FLG='C' AND EL.EMP_NBR=:employeeNumber AND EL.PAY_FREQ=PF.PAY_FREQ ORDER BY EL.PAY_FREQ DESC");
		

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<PayFrequency>()
		{
			@Override
			public PayFrequency mapRow(ResultSet rs, int rowNum) throws SQLException {
				PayFrequency payFrequency = new PayFrequency();
				payFrequency.setCode(StringUtil.trim(rs.getString("PAY_FREQ")));
				payFrequency.setDescription(StringUtil.trim(rs.getString("PAY_FREQ_DESCR")));
				return payFrequency;
			}
		});
	}

	public List<PayFrequency> getEmployeeLeavePayFrequencies(String employeeNumber) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT EL.PAY_FREQ, PF.PAY_FREQ_DESCR ");
		sql.append("FROM BHR_EMP_LV EL, BTHR_PAY_FREQ PF ");
		sql.append("WHERE EL.CYR_NYR_FLG='C' AND EL.EMP_NBR=:employeeNumber AND EL.PAY_FREQ=PF.PAY_FREQ ");
		sql.append("	AND EXISTS (SELECT 1 ");
		sql.append("				FROM BTHR_ABS_RSN_TO_LV_TYP AR2LT, BTHR_LV_TYP_DESCR LTD, BTHR_LV_TYP LT, BTHR_ABS_RSN AR ");
		sql.append("				WHERE EL.PAY_FREQ=LT.PAY_FREQ AND EL.LV_TYP=LT.LV_TYP AND LT.STAT='A' AND LT.LV_TYP=AR2LT.LV_TYP ");
		sql.append("					AND LTD.LV_TYP=AR2LT.LV_TYP AND LTD.STAT='A' AND AR.ABS_RSN=AR2LT.ABS_RSN AND AR.STAT='A' ");
		sql.append("	) ");
		sql.append("ORDER BY EL.PAY_FREQ DESC");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<PayFrequency>()
		{
			@Override
			public PayFrequency mapRow(ResultSet rs, int rowNum) throws SQLException {
				PayFrequency payFrequency = new PayFrequency();
				payFrequency.setCode(StringUtil.trim(rs.getString("PAY_FREQ")));
				payFrequency.setDescription(StringUtil.trim(rs.getString("PAY_FREQ_DESCR")));
				return payFrequency;
			}
		});
	}

	public List<LeaveRequest> getEmployeeUnprocessedLeaveRequests(String employeeNumber, String payFrequency) {
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT ELR.ID, ELR.LV_TYP, ELR.ABS_RSN, ELR.DATETIME_SUBMITTED, ELR.DATETIME_FROM, ELR.DATETIME_TO, ELR.LV_UNITS_DAILY, ");
		sql.append("  ELR.LV_UNITS_USED, ELR.STATUS_CD, ELSC.DESCR AS STATUS_DESCR, ISNULL(ELC.LV_COMMENT,'') AS REQUEST_COMMENT, LT.DAYS_HRS, LTD.LONG_DESCR, AR.ABS_DESCR ");
		sql.append("FROM BEA_EMP_LV_RQST ELR ");
		sql.append("  LEFT OUTER JOIN BEA_EMP_LV_COMMENTS ELC ON ELR.ID=ELC.LV_ID AND ELC.LV_COMMENT_TYP='C', ");
		sql.append("  BTEA_EMP_LV_STATUS_CODES ELSC, BTHR_LV_TYP LT, BTHR_LV_TYP_DESCR LTD, BTHR_ABS_RSN AR ");
		sql.append("WHERE ELR.EMP_NBR=:employeeNumber AND ELR.PAY_FREQ=:payFrequency AND ELR.LV_TYP=LT.LV_TYP AND LT.STAT='A' AND ELR.STATUS_CD=ELSC.CD ");
		sql.append("  AND LT.PAY_FREQ=:payFrequency AND ELR.LV_TYP=LTD.LV_TYP AND LTD.STAT='A' AND ELR.ABS_RSN=AR.ABS_RSN AND AR.STAT='A' ");
		sql.append("  AND (ELR.STATUS_CD IN ('A','P','L') OR (ELR.STATUS_CD IN ('D') AND DAYS(ELR.DATETIME_SUBMITTED, GETDATE()) <=60)) ");
		sql.append("ORDER BY DATETIME_FROM DESC");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("payFrequency", payFrequency);
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<LeaveRequest>()
		{
			@Override
			public LeaveRequest mapRow(ResultSet rs, int rowNum) throws SQLException {

				LeaveRequest leaveRequest = new LeaveRequest();
				leaveRequest.setId(rs.getInt("ID"));
				leaveRequest.setLeaveType(rs.getString("LV_TYP").trim());
				leaveRequest.setAbsenceReason(rs.getString("ABS_RSN").trim());
				leaveRequest.setSubmittedDateTime(rs.getTimestamp("DATETIME_SUBMITTED"));
				leaveRequest.setSubmittedDateTimeString(dateTimeFormat.format(rs.getTimestamp("DATETIME_SUBMITTED")));
				
				leaveRequest.setFromDateTime(rs.getTimestamp("DATETIME_FROM"));
				
				leaveRequest.setFromDateString(dateFormat.format(rs.getTimestamp("DATETIME_FROM")));
				leaveRequest.setFromTimeString(timeFormat.format(rs.getTimestamp("DATETIME_FROM")));
				leaveRequest.setToDateTime(rs.getTimestamp("DATETIME_TO"));
				leaveRequest.setToDateString(dateFormat.format(rs.getTimestamp("DATETIME_TO")));
				leaveRequest.setToTimeString(timeFormat.format(rs.getTimestamp("DATETIME_TO")));
				leaveRequest.setLeaveHoursDaily(rs.getBigDecimal("LV_UNITS_DAILY").setScale(3, BigDecimal.ROUND_HALF_UP));
				leaveRequest.setLeaveRequested(rs.getBigDecimal("LV_UNITS_USED").setScale(3, BigDecimal.ROUND_HALF_UP));
				leaveRequest.setLeaveUnits(rs.getString("DAYS_HRS").trim().toUpperCase());
				leaveRequest.setRequestComment(rs.getString("REQUEST_COMMENT"));
				leaveRequest.setLeaveTypeDescription(leaveRequest.getLeaveType()+" - "+rs.getString("LONG_DESCR").trim());
				leaveRequest.setAbsenceReasonDescription(rs.getString("ABS_DESCR").trim());
				leaveRequest.setStatus(rs.getString("STATUS_CD").trim());
				leaveRequest.setStatusDescription(rs.getString("STATUS_DESCR").trim());

				return leaveRequest;
			}
		});
	}

	public List<LeaveRequest> getEmployeeLeaveRequests(String employeeNumber, String payFrequency, String filterFromDate, String filterToDate) {
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT ELR.ID, ELR.LV_TYP, ELR.ABS_RSN, ELR.DATETIME_SUBMITTED, ELR.DATETIME_FROM, ELR.DATETIME_TO, ELR.LV_UNITS_DAILY,  ");
		sql.append("    ELR.LV_UNITS_USED, ELR.STATUS_CD, ELSC.DESCR AS STATUS_DESCR, ISNULL(ELC.LV_COMMENT,'') AS REQUEST_COMMENT, LT.DAYS_HRS, LTD.LONG_DESCR, AR.ABS_DESCR  ");
		sql.append("FROM BEA_EMP_LV_RQST ELR  ");
		sql.append("	LEFT OUTER JOIN BEA_EMP_LV_COMMENTS ELC ON ELR.ID=ELC.LV_ID AND ELC.LV_COMMENT_TYP='C',  ");
		sql.append("	BTEA_EMP_LV_STATUS_CODES ELSC, BTHR_LV_TYP LT, BTHR_LV_TYP_DESCR LTD, BTHR_ABS_RSN AR  ");
		sql.append("WHERE ELR.EMP_NBR=:employeeNumber AND ELR.PAY_FREQ=:payFrequency AND ELR.LV_TYP=LT.LV_TYP AND LT.STAT='A' AND ELR.STATUS_CD=ELSC.CD AND LT.PAY_FREQ=:payFrequency ");
		sql.append("    AND ELR.LV_TYP=LTD.LV_TYP AND LTD.STAT='A' AND ELR.ABS_RSN=AR.ABS_RSN AND AR.STAT='A' AND ELR.STATUS_CD IN ('A','P','L','C')  ");
		sql.append("    AND ELR.DATETIME_FROM >= CONVERT(DATETIME, :filterFromDate, 110) AND ELR.DATETIME_FROM < DATEADD (DD, 1, CONVERT(DATETIME, :filterToDate, 110)) ");
		sql.append("UNION ");
		sql.append("SELECT ELX.LV_ID as ID, ELX.LV_TYP, ELX.ABS_RSN, GETDATE() AS DATETIME_SUBMITTED, convert(datetime, elx.dt_of_abs, 112) AS DATETIME_FROM,   ");
		sql.append("	convert(datetime, elx.dt_of_abs, 112) AS DATETIME_TO, 0 as LV_UNITS_DAILY,  elx.lv_units_used, 'U' as STATUS_CD, 'Unprocessed' AS STATUS_DESCR,  ");
		sql.append("	'' AS REQUEST_COMMENT, LT.DAYS_HRS, LTD.LONG_DESCR, AR.ABS_DESCR ");
		sql.append("FROM BHR_EMP_LV_XMITAL ELX, BTEA_EMP_LV_STATUS_CODES ELSC, BTHR_LV_TYP LT, BTHR_LV_TYP_DESCR LTD, BTHR_ABS_RSN AR ");
		sql.append("WHERE ELX.EMP_NBR=:employeeNumber AND ELX.PAY_FREQ=:payFrequency AND ELX.LV_TYP=LT.LV_TYP AND LT.STAT='A' AND ELSC.CD='P'  ");
		sql.append("	AND LT.PAY_FREQ=:payFrequency AND ELX.LV_TYP=LTD.LV_TYP AND LTD.STAT='A'  ");
		sql.append("	AND ELX.ABS_RSN=AR.ABS_RSN AND AR.STAT='A' AND ELX.PROCESS_DT='' AND ELX.LV_ID=0 ");
		sql.append("   AND CONVERT(DATETIME, ELX.DT_OF_ABS, 112) >= CONVERT(DATETIME, :filterFromDate, 110) AND CONVERT(DATETIME, ELX.DT_OF_ABS, 112) < DATEADD (DD, 1, CONVERT(DATETIME, :filterToDate, 110)) ");
		sql.append("UNION ");
		sql.append("SELECT ELX.LV_ID as ID, ELX.LV_TYP, ELX.ABS_RSN, GETDATE() AS DATETIME_SUBMITTED, convert(datetime, elx.dt_of_abs, 112) AS DATETIME_FROM,  ");
		sql.append("	convert(datetime, elx.dt_of_abs, 112) AS DATETIME_to, 0 as LV_UNITS_DAILY,  elx.lv_units_used, 'C' as STATUS_CD, ELSC.DESCR AS STATUS_DESCR,  ");
		sql.append("	'' AS REQUEST_COMMENT, LT.DAYS_HRS, LTD.LONG_DESCR, AR.ABS_DESCR ");
		sql.append("FROM BHR_EMP_LV_XMITAL ELX, BTEA_EMP_LV_STATUS_CODES ELSC, BTHR_LV_TYP LT, BTHR_LV_TYP_DESCR LTD, BTHR_ABS_RSN AR  ");
		sql.append("WHERE ELX.EMP_NBR=:employeeNumber AND ELX.PAY_FREQ=:payFrequency AND ELX.LV_TYP=LT.LV_TYP AND LT.STAT='A' AND ELSC.CD='C'  ");
		sql.append("	AND LT.PAY_FREQ=:payFrequency AND ELX.LV_TYP=LTD.LV_TYP AND LTD.STAT='A'  ");
		sql.append("	AND ELX.ABS_RSN=AR.ABS_RSN AND AR.STAT='A' AND ELX.PROCESS_DT!='' AND ELX.LV_ID=0 ");
		sql.append("   AND CONVERT(DATETIME, ELX.DT_OF_ABS, 112) >= CONVERT(DATETIME, :filterFromDate, 110) AND CONVERT(DATETIME, ELX.DT_OF_ABS, 112) < DATEADD (DD, 1, CONVERT(DATETIME, :filterToDate, 110)) ");
		sql.append("ORDER BY DATETIME_FROM DESC");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("payFrequency", payFrequency);
		
		params.addValue("filterFromDate", filterFromDate);
		params.addValue("filterToDate", filterToDate);
		
		NamedParameterJdbcTemplate template = getNamedParameterJdbcTemplate();
		JdbcOperations ops = template.getJdbcOperations();
		ops.execute("set rowcount 100");
		List<LeaveRequest> result = getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<LeaveRequest>()
		{
			@Override
			public LeaveRequest mapRow(ResultSet rs, int rowNum) throws SQLException {

				LeaveRequest leaveRequest = new LeaveRequest();
				leaveRequest.setId(rs.getInt("ID"));
				leaveRequest.setLeaveType(rs.getString("LV_TYP").trim());
				leaveRequest.setAbsenceReason(rs.getString("ABS_RSN").trim());
				leaveRequest.setSubmittedDateTime(rs.getTimestamp("DATETIME_SUBMITTED"));
				leaveRequest.setSubmittedDateTimeString(dateTimeFormat.format(rs.getTimestamp("DATETIME_SUBMITTED")));
				leaveRequest.setFromDateTime(rs.getTimestamp("DATETIME_FROM"));
				leaveRequest.setFromDateString(dateFormat.format(rs.getTimestamp("DATETIME_FROM")));
				leaveRequest.setFromTimeString(timeFormat.format(rs.getTimestamp("DATETIME_FROM")));
				leaveRequest.setToDateTime(rs.getTimestamp("DATETIME_TO"));
				leaveRequest.setToDateString(dateFormat.format(rs.getTimestamp("DATETIME_TO")));
				leaveRequest.setToTimeString(timeFormat.format(rs.getTimestamp("DATETIME_TO")));
				leaveRequest.setLeaveHoursDaily(rs.getBigDecimal("LV_UNITS_DAILY").setScale(3, BigDecimal.ROUND_HALF_UP));
				leaveRequest.setLeaveRequested(rs.getBigDecimal("LV_UNITS_USED").setScale(3, BigDecimal.ROUND_HALF_UP));
				leaveRequest.setLeaveUnits(rs.getString("DAYS_HRS").trim().toUpperCase());
				leaveRequest.setRequestComment(rs.getString("REQUEST_COMMENT"));
				leaveRequest.setLeaveTypeDescription(leaveRequest.getLeaveType()+" - "+rs.getString("LONG_DESCR").trim());
				leaveRequest.setAbsenceReasonDescription(rs.getString("ABS_DESCR").trim());
				leaveRequest.setStatus(rs.getString("STATUS_CD").trim());
				leaveRequest.setStatusDescription(rs.getString("STATUS_DESCR").trim());

				return leaveRequest;
			}
		});
		ops.execute("set rowcount 0");
		return result;
	}

	public List<LeaveRequest> getEmployeeLeaveRequestsPeriodsPayFreq(String employeeNumber, String payFrequency) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		params.addValue("payFrequency", payFrequency);
		
		return getNamedParameterJdbcTemplate().query(sqlSelectLeaveTimePeriodsPayFreq, params, new ParameterizedRowMapper<LeaveRequest>()
		{
			@Override
			public LeaveRequest mapRow(ResultSet rs, int rowNum) throws SQLException {

				LeaveRequest leaveRequest = new LeaveRequest();
				leaveRequest.setId(rs.getInt("ID"));
				leaveRequest.setFromDateTime(rs.getTimestamp("DATETIME_FROM"));
				leaveRequest.setFromDateString(dateFormat.format(rs.getTimestamp("DATETIME_FROM")));
				leaveRequest.setFromTimeString(timeFormat.format(rs.getTimestamp("DATETIME_FROM")));
				leaveRequest.setToDateTime(rs.getTimestamp("DATETIME_TO"));
				leaveRequest.setToDateString(dateFormat.format(rs.getTimestamp("DATETIME_TO")));
				leaveRequest.setToTimeString(timeFormat.format(rs.getTimestamp("DATETIME_TO")));

				return leaveRequest;
			}
		});
	}

	public List<LeaveRequest> getEmployeeLeaveRequestsPeriods(String employeeNumber) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		
		return getNamedParameterJdbcTemplate().query(sqlSelectLeaveTimePeriods, params, new ParameterizedRowMapper<LeaveRequest>()
		{
			@Override
			public LeaveRequest mapRow(ResultSet rs, int rowNum) throws SQLException {

				LeaveRequest leaveRequest = new LeaveRequest();
				leaveRequest.setId(rs.getInt("ID"));
				leaveRequest.setFromDateTime(rs.getTimestamp("DATETIME_FROM"));
				leaveRequest.setFromDateString(dateFormat.format(rs.getTimestamp("DATETIME_FROM")));
				leaveRequest.setFromDate(dateFormat.format(rs.getTimestamp("DATETIME_FROM")));
				leaveRequest.setFromTimeString(timeFormat.format(rs.getTimestamp("DATETIME_FROM")));
				leaveRequest.setToDateTime(rs.getTimestamp("DATETIME_TO"));
				leaveRequest.setToDateString(dateFormat.format(rs.getTimestamp("DATETIME_TO")));
				leaveRequest.setToDate(dateFormat.format(rs.getTimestamp("DATETIME_TO")));
				leaveRequest.setToTimeString(timeFormat.format(rs.getTimestamp("DATETIME_TO")));

				return leaveRequest;
			}
		});
	}

	public List<LeaveRequestComment> getLeaveRequestComments(int leaveId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ELC.ID, ELC.LV_ID, ISNULL(ELC.LV_COMMENT,'') AS REQUEST_COMMENT, ELC.LV_COMMENT_DATETIME, ELC.LV_COMMENT_TYP, ELC.LV_COMMENT_EMP_NBR, ");
		sql.append("	ED.NAME_F, ED.NAME_M, ED.NAME_L ");
		sql.append("FROM BEA_EMP_LV_COMMENTS ELC, BHR_EMP_DEMO ED ");
		sql.append("WHERE ELC.LV_ID=:leaveId AND ELC.LV_COMMENT_EMP_NBR=ED.EMP_NBR AND LENGTH(ISNULL(ELC.LV_COMMENT,''))>0 ");
		sql.append("ORDER BY ELC.LV_COMMENT_DATETIME ASC");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("leaveId", leaveId, java.sql.Types.INTEGER);
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<LeaveRequestComment>()
		{
			@Override
			public LeaveRequestComment mapRow(ResultSet rs, int rowNum) throws SQLException {
				LeaveRequestComment leaveRequestComment = new LeaveRequestComment();
				leaveRequestComment.setId(rs.getInt("ID"));
				leaveRequestComment.setLeaveId(rs.getInt("LV_ID"));
				leaveRequestComment.setComment(rs.getString("REQUEST_COMMENT"));
				leaveRequestComment.setCommentDateTime(rs.getTimestamp("LV_COMMENT_DATETIME"));
				leaveRequestComment.setCommentDateString(dateFormat.format(rs.getTimestamp("LV_COMMENT_DATETIME")));
				leaveRequestComment.setCommentTimeString(timeFormat.format(rs.getTimestamp("LV_COMMENT_DATETIME")));
				leaveRequestComment.setCommentType(rs.getString("LV_COMMENT_TYP").trim());
				LeaveEmployeeData empData = new LeaveEmployeeData();
				empData.setEmployeeNumber(rs.getString("LV_COMMENT_EMP_NBR").trim());
				empData.setFirstName(rs.getString("NAME_F").trim());
				empData.setMiddleName(rs.getString("NAME_M").trim());
				empData.setLastName(rs.getString("NAME_L").trim());;
				leaveRequestComment.setCommentEmp(empData);
				return leaveRequestComment;
			}
		});
	}
	
	public List<AbsenceReason> getAbsenceReasons(String payFrequency, String employeeNumber, String leaveType) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT AR2LT.ABS_RSN, AR.ABS_DESCR ");
		sql.append("FROM BTHR_ABS_RSN_TO_LV_TYP AR2LT, BTHR_LV_TYP LT, BTHR_ABS_RSN AR, BHR_EMP_LV EL ");
		sql.append("WHERE LT.LV_TYP=AR2LT.LV_TYP AND LT.STAT='A' AND LT.PAY_FREQ = :payFrequency AND AR.ABS_RSN=AR2LT.ABS_RSN AND AR.STAT='A' ");
		sql.append("	AND EL.CYR_NYR_FLG='C' AND EL.EMP_NBR=:employeeNumber AND EL.PAY_FREQ=:payFrequency AND EL.LV_TYP=LT.LV_TYP ");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("payFrequency", payFrequency);
		params.addValue("employeeNumber", employeeNumber);
		if (leaveType!=null && leaveType.length()>0) {
			sql.append(" AND AR2LT.LV_TYP = :leaveType");
			params.addValue("leaveType", leaveType);
		}
		sql.append(" ORDER BY AR.ABS_DESCR ASC ");
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<AbsenceReason>()
		{
			@Override
			public AbsenceReason mapRow(ResultSet rs, int rowNum) throws SQLException {
				AbsenceReason absenceReason = new AbsenceReason();
				absenceReason.setAbsRsn(rs.getString("ABS_RSN").trim());
				absenceReason.setAbsRsnDescription(rs.getString("ABS_DESCR").trim());
				return absenceReason;
			}
		});
	}

	public List<LeaveType> getLeaveTypes(String payFrequency, String employeeNumber, String absenceReason) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT AR2LT.LV_TYP, LTD.LONG_DESCR, LT.DAYS_HRS, LT.ADD_SUBTRACT_BAL, LTD.TEXT_REQ, LTD.POST_AGNST_ZERO_BAL, LTD.EOY_CUTOFF_DT, LTD.EOY_ENTRY_CUTOFF_DT ");
		sql.append("FROM BTHR_ABS_RSN_TO_LV_TYP AR2LT, BTHR_LV_TYP_DESCR LTD, BTHR_LV_TYP LT, BTHR_ABS_RSN AR, BHR_EMP_LV EL ");
		sql.append("WHERE LTD.LV_TYP=AR2LT.LV_TYP AND LTD.STAT='A' AND LT.LV_TYP=AR2LT.LV_TYP AND LT.STAT='A' AND LT.PAY_FREQ = :payFrequency ");
		sql.append("	AND AR.ABS_RSN=AR2LT.ABS_RSN AND AR.STAT='A' ");
		sql.append("	AND EL.CYR_NYR_FLG='C' AND EL.EMP_NBR=:employeeNumber AND EL.PAY_FREQ=:payFrequency AND EL.LV_TYP=LT.LV_TYP ");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("payFrequency", payFrequency);
		params.addValue("employeeNumber", employeeNumber);
		if (absenceReason!=null && absenceReason.length()>0) {
			sql.append(" AND AR2LT.ABS_RSN = :absenceReason ");
			params.addValue("absenceReason", absenceReason);
		}
		sql.append(" ORDER BY AR2LT.LV_TYP ASC ");
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<LeaveType>()
		{
			@Override
			public LeaveType mapRow(ResultSet rs, int rowNum) throws SQLException {
				LeaveType leaveType = new LeaveType();
				leaveType.setLvType(rs.getString("LV_TYP").trim());
				leaveType.setLvTypeDescription(leaveType.getLvType() + " - " + rs.getString("LONG_DESCR").trim());
				leaveType.setLvTypeUnits(rs.getString("DAYS_HRS").trim());
				boolean booleanValue = rs.getString("ADD_SUBTRACT_BAL").trim().toUpperCase().equals("S") ? true : false;
				leaveType.setSubtractFromBalance(booleanValue);
				
				booleanValue = rs.getString("TEXT_REQ").trim().toUpperCase().equals("Y") ? true : false;
				leaveType.setCommentRequired(booleanValue);
				booleanValue = rs.getString("POST_AGNST_ZERO_BAL").trim().toUpperCase().equals("Y") ? true : false;
				leaveType.setPostAgainstZeroBalance(booleanValue);
				try {
					leaveType.setAbsenceDateCutoff(bhrDateFormat.parse(rs.getString("EOY_CUTOFF_DT").trim()));
					leaveType.setSubmissionDateCutoff(bhrDateFormat.parse(rs.getString("EOY_ENTRY_CUTOFF_DT").trim()));
				} catch (Exception e) {
				}
				return leaveType;
			}
		});
	}
	
	public int addLeaveRequest (LeaveRequest leaveRequest) {
		int numRows=0;
		// build from and to timestamps 
		StringBuilder fromDateTimeSB = new StringBuilder(leaveRequest.getFromDate().substring(0,2)).append("-")
			.append(leaveRequest.getFromDate().substring(3,5)).append("-").append(leaveRequest.getFromDate().substring(6,10))
			.append(" ").append(leaveRequest.getFromHour()).append(":").append(leaveRequest.getFromMinute()).append(leaveRequest.getFromAmPm());
		StringBuilder toDateTimeSB = new StringBuilder(leaveRequest.getToDate().substring(0,2)).append("-")
			.append(leaveRequest.getToDate().substring(3,5)).append("-").append(leaveRequest.getToDate().substring(6,10))
			.append(" ").append(leaveRequest.getToHour()).append(":").append(leaveRequest.getToMinute()).append(leaveRequest.getToAmPm());
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = dateTimeFormat.parse(fromDateTimeSB.toString());
			toDate = dateTimeFormat.parse(toDateTimeSB.toString());
		} catch (Exception e) {
			
		}
		if (fromDate != null && toDate != null) {
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO BEA_EMP_LV_RQST ");
			sql.append("  (LV_TYP, ABS_RSN, DATETIME_FROM, DATETIME_TO, EMP_NBR, PAY_FREQ, LV_UNITS_DAILY, LV_UNITS_USED, STATUS_CD) ");
			sql.append("  VALUES (:leaveType, :absenceReason, :fromDateTime, :toDateTime, :employeeNumber, :payFrequency, :leaveHoursDaily, :leaveRequested, :status )");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("leaveType", leaveRequest.getLeaveType());
			params.addValue("absenceReason", leaveRequest.getAbsenceReason());
			Timestamp fromTimeStamp = new Timestamp(fromDate.getTime());
			params.addValue("fromDateTime", fromTimeStamp, java.sql.Types.TIMESTAMP);
			Timestamp toTimeStamp = new Timestamp(toDate.getTime());
			params.addValue("toDateTime", toTimeStamp, java.sql.Types.TIMESTAMP);
			params.addValue("employeeNumber", leaveRequest.getEmployeeNumber());
			params.addValue("payFrequency", leaveRequest.getPayFrequency());
			params.addValue("leaveHoursDaily", leaveRequest.getLeaveHoursDaily(), java.sql.Types.DECIMAL);
			params.addValue("leaveRequested", leaveRequest.getLeaveRequested(), java.sql.Types.DECIMAL);
			params.addValue("status", leaveRequest.getStatus());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			numRows = getNamedParameterJdbcTemplate().update(sql.toString(), params, keyHolder);
			// always insert a row for the requestor's comment even when no comment was supplied
			leaveRequest.setId(keyHolder.getKey().intValue());
			
			if (numRows > 0 && leaveRequest.getRequestComment() != null && leaveRequest.getRequestComment().trim().length() > 0) {
				params = new MapSqlParameterSource();
				params.addValue("leaveId", leaveRequest.getId(), java.sql.Types.INTEGER);
				params.addValue("commentType", LeaveRequest.COMMENT_TYP_CURRENT_WF_REQUEST);
				
				// the commentEmployeeNumber indicates the author of the comment;
				// need to make this distinction since the request could have been updated 
				// by the employee or a supervisor
				String commentEmployeeNumber = leaveRequest.getEmployeeNumber();
				/* don't do this for now... causes duplicate rows on leave request list
				 * 
				 *
				if (leaveRequest.getRequestCommentEmpNumber()!=null && leaveRequest.getRequestCommentEmpNumber().length()>0) {
					commentEmployeeNumber = leaveRequest.getRequestCommentEmpNumber();
				} 
				 */
				params.addValue("employeeNumber", commentEmployeeNumber);
				params.addValue("requestComment", leaveRequest.getRequestComment());
				numRows = getNamedParameterJdbcTemplate().update(sqlInsertLeaveComment, params);
			}
		}
		return numRows;
	}
	
	private String getCurrentWFLeaveRequestComment(int leaveId) {
		String comment=null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ISNULL(ELC.LV_COMMENT,'') AS REQUEST_COMMENT FROM BEA_EMP_LV_COMMENTS ELC WHERE ELC.LV_ID=:leaveId AND ELC.LV_COMMENT_TYP='C'"); 
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("leaveId", leaveId, java.sql.Types.INTEGER);
		try {
			comment = (String) getNamedParameterJdbcTemplate().query(sql.toString(), params,
				new ResultSetExtractor<String>() {
					@Override
					public String extractData(final ResultSet rs) throws SQLException, DataAccessException {
						rs.next();
						String result = rs.getString("REQUEST_COMMENT").trim();
						return result;
					}
				});
		} catch (DataAccessException e) {
			comment = null;
		}
		
		return comment;
	}
	
	public int updateLeaveRequest (LeaveRequest leaveRequest) {
		int numLeaveRowsUpdated=0;
		// build from and to timestamps 
		StringBuilder fromDateTimeSB = new StringBuilder(leaveRequest.getFromDate().substring(0,2)).append("-")
			.append(leaveRequest.getFromDate().substring(3,5)).append("-").append(leaveRequest.getFromDate().substring(6,10))
			.append(" ").append(leaveRequest.getFromHour()).append(":").append(leaveRequest.getFromMinute()).append(leaveRequest.getFromAmPm());
		StringBuffer toDateTimeSB = new StringBuffer(leaveRequest.getToDate().substring(0,2)).append("-")
			.append(leaveRequest.getToDate().substring(3,5)).append("-").append(leaveRequest.getToDate().substring(6,10))
			.append(" ").append(leaveRequest.getToHour()).append(":").append(leaveRequest.getToMinute()).append(leaveRequest.getToAmPm());
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = dateTimeFormat.parse(fromDateTimeSB.toString());
			toDate = dateTimeFormat.parse(toDateTimeSB.toString());
		} catch (Exception e) {
			
		}
		if (fromDate != null && toDate != null) {
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE BEA_EMP_LV_RQST set LV_TYP=:leaveType, ABS_RSN=:absenceReason, DATETIME_FROM=:fromDateTime, DATETIME_TO=:toDateTime, ");
			sql.append("  LV_UNITS_DAILY=:leaveHoursDaily, LV_UNITS_USED=:leaveRequested, STATUS_CD=:status ");
			sql.append("WHERE ID=:leaveId");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("leaveType", leaveRequest.getLeaveType());
			params.addValue("absenceReason", leaveRequest.getAbsenceReason());
			Timestamp fromTimeStamp = new Timestamp(fromDate.getTime());
			params.addValue("fromDateTime", fromTimeStamp, java.sql.Types.TIMESTAMP);
			Timestamp toTimeStamp = new Timestamp(toDate.getTime());
			params.addValue("toDateTime", toTimeStamp, java.sql.Types.TIMESTAMP);
			params.addValue("leaveHoursDaily", leaveRequest.getLeaveHoursDaily(), java.sql.Types.DECIMAL);
			params.addValue("leaveRequested", leaveRequest.getLeaveRequested(), java.sql.Types.DECIMAL);
			params.addValue("status", leaveRequest.getStatus());
			params.addValue("leaveId", leaveRequest.getId(), java.sql.Types.INTEGER);
			numLeaveRowsUpdated = getNamedParameterJdbcTemplate().update(sql.toString(), params);
			if (numLeaveRowsUpdated > 0) {
				// process comment by first retrieving existing comment, if any
				int numRows=0;
				String currentComment = getCurrentWFLeaveRequestComment(leaveRequest.getId());
				if ((currentComment==null && leaveRequest.getRequestComment().trim().length()>0) || 
					(currentComment!=null && !leaveRequest.getRequestComment().trim().equals(currentComment))) {
					// the commentEmployeeNumber indicates the author of the comment;
					// need to make this distinction since the request could have been updated 
					// by the employee or a supervisor
					String commentEmployeeNumber = leaveRequest.getEmployeeNumber();
					/* don't do this for now... causes duplicate rows on leave request list
					 * 
					 *
					if (leaveRequest.getRequestCommentEmpNumber()!=null && leaveRequest.getRequestCommentEmpNumber().length()>0) {
						commentEmployeeNumber = leaveRequest.getRequestCommentEmpNumber();
					} 
					 */
					
					// if ...
					// (1) there was no comment and there is one now (a non-empty comment) then insert the new comment
					// (2) if there was a comment and there is a different one now (or it is now empty,
					//   then mark the old comment as belonging to the prior workflow and insert the new non-empty comment
					
					if (currentComment!=null) {
						params = new MapSqlParameterSource();
						params.addValue("leaveId", leaveRequest.getId(), java.sql.Types.INTEGER);
						params.addValue("employeeNumber", commentEmployeeNumber);
						numRows = getNamedParameterJdbcTemplate().update(sqlUpdateRequestCommentTypeToPriorWF, params);						
					}
					if (leaveRequest.getRequestComment().trim().length()>0) {
						// insert comment associated with this request
						params = new MapSqlParameterSource();
						params.addValue("leaveId", leaveRequest.getId(), java.sql.Types.INTEGER);
						params.addValue("commentType", LeaveRequest.COMMENT_TYP_CURRENT_WF_REQUEST);
						params.addValue("employeeNumber", commentEmployeeNumber);
						params.addValue("requestComment", leaveRequest.getRequestComment().trim());
						numRows = getNamedParameterJdbcTemplate().update(sqlInsertLeaveComment, params);						
					}
				}
			}
		}
		return numLeaveRowsUpdated;
	}

	public int deleteLeaveRequestTransmittal (LeaveRequest leaveRequest) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BHR_EMP_LV_XMITAL "); 
		sql.append("WHERE LV_ID=:leaveId ");
		sql.append("	AND (SELECT COUNT(*) FROM BHR_EMP_LV_XMITAL WHERE LV_ID=:leaveId)=(SELECT COUNT(*) FROM BHR_EMP_LV_XMITAL WHERE LV_ID=:leaveId AND PROCESS_DT='') ");
		// ... for now don't check that the number of days in the leave request match the number of rows in the xmital table ... that test is true on a delete request
		// ... but may not be true on an edit request ... should not be any issue anyways as long as the status of the leave request is accurate
		//		sql.append("	AND (SELECT COUNT(*) FROM BHR_EMP_LV_XMITAL WHERE LV_ID=:leaveId)=:leaveNumberDays");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("leaveId", leaveRequest.getId(), java.sql.Types.INTEGER);
		params.addValue("leaveNumberDays", leaveRequest.getLeaveNumberDays(), java.sql.Types.INTEGER);
		int numRows = getNamedParameterJdbcTemplate().update(sql.toString(), params);
		return numRows;
	}
	
	public int deleteLeaveRequest (LeaveRequest leaveRequest) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("status", LeaveRequest.REQUEST_STATUS_DELETED);
		params.addValue("leaveId", leaveRequest.getId(), java.sql.Types.INTEGER);
		int numRows = getNamedParameterJdbcTemplate().update(sqlUpdateRequestStatus, params);
//		if (numRows>0) {
//			int numXmitalRows = getNamedParameterJdbcTemplate().update(sqlDeleteXmital, params);
//		}
		return numRows;
	}

	public int updateLeaveRequestStatus (int leaveId, String status) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("status", status);
		params.addValue("leaveId", leaveId, java.sql.Types.INTEGER);
		int numRows = getNamedParameterJdbcTemplate().update(sqlUpdateRequestStatus, params);
		return numRows;
	}

	public int getWorkflowSequenceNumber(int leaveId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TOP 1 SEQ_NUM FROM BEA_EMP_LV_WORKFLOW WHERE LV_ID=:leaveId ORDER BY INSERT_DATETIME DESC");
		Integer seqNum = new Integer(0);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("leaveId", leaveId, java.sql.Types.INTEGER);
		
		try {
			seqNum = (Integer) getNamedParameterJdbcTemplate().query(sql.toString(), params,
				new ResultSetExtractor<Integer>() {
					@Override
					public Integer extractData(final ResultSet rs) throws SQLException, DataAccessException {
						rs.next();
						int seqNumIntValue = rs.getInt("SEQ_NUM");
						return new Integer(seqNumIntValue);
					}
				});
		} catch (DataAccessException e) {	
		}
		return seqNum.intValue();
	}

	public BigDecimal getAbsenceReasonDocRequiredConsecutiveUnitsThreshold (String absenceReason) {
		BigDecimal consecutiveUnits=null;		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("absenceReason", absenceReason);
		try {
			consecutiveUnits = (BigDecimal) getNamedParameterJdbcTemplate().query("SELECT CONSEC_UNTS_REQR_DOC FROM BTHR_ABS_RSN WHERE ABS_RSN=:absenceReason", 
				params,
				new ResultSetExtractor<BigDecimal>() {
					@Override
					public BigDecimal extractData(final ResultSet rs) throws SQLException, DataAccessException {
						rs.next();
						return rs.getBigDecimal(1).setScale(3, BigDecimal.ROUND_HALF_UP);
					}
				});
		} catch (DataAccessException e) {	
		}
		return consecutiveUnits;
	}

	public BigDecimal getAbsenceReasonConsecutiveUnitsThreshold (String absenceReason, int sequenceNumber) {
		BigDecimal consecutiveUnits=null;
		String column="";
		if (sequenceNumber==2) {
			column = "CONSEC_UNTS_NXT_APPRVR";
		} else if (sequenceNumber==3) {
			column = "CONSEC_UNTS_APPRV3";
		} else if (sequenceNumber==4) {
			column = "CONSEC_UNTS_APPRV4";
		} else if (sequenceNumber==5) {
			column = "CONSEC_UNTS_APPRV5";
		}
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append(column).append(" ");
		sql.append("FROM BTHR_ABS_RSN WHERE ABS_RSN=:absenceReason");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("absenceReason", absenceReason);
		
		try {
			consecutiveUnits = (BigDecimal) getNamedParameterJdbcTemplate().query(sql.toString(), params,
				new ResultSetExtractor<BigDecimal>() {
					@Override
					public BigDecimal extractData(final ResultSet rs) throws SQLException, DataAccessException {
						rs.next();
						return rs.getBigDecimal(1).setScale(3, BigDecimal.ROUND_HALF_UP);
					}
				});
		} catch (DataAccessException e) {	
		}
		return consecutiveUnits;
	}

	public LeaveEmployeeData getAbsenceReasonNextLineSupervisorData(String absenceReason, int sequenceNumber) {
		LeaveEmployeeData supervisorData=null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ED.EMP_NBR, ED.NAME_F, ED.NAME_M, ED.NAME_L, ED.EMAIL, ISNULL(SU.USR_LOG_NAME,'') AS USR_LOG_NAME ");
		sql.append("FROM BHR_EMP_DEMO ED LEFT OUTER JOIN SEC_USERS SU ON ED.EMP_NBR=SU.EMP_NBR AND SU.USR_DELETED=0, BTHR_ABS_RSN AR ");
		sql.append("WHERE AR.ABS_RSN=:absenceReason AND ED.EMP_NBR=");
		String column="";
		if (sequenceNumber==3) {
			column = "AR.APPRV3_EMP_NBR";
		} else if (sequenceNumber==4) {
			column = "AR.APPRV4_EMP_NBR";
		} else if (sequenceNumber==5) {
			column = "AR.APPRV5_EMP_NBR";
		}
		sql.append(column);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("absenceReason", absenceReason);
		try {
			supervisorData = (LeaveEmployeeData) getNamedParameterJdbcTemplate().query(sql.toString(), params,
				new ResultSetExtractor<LeaveEmployeeData>() {
					@Override
					public LeaveEmployeeData extractData(final ResultSet rs) throws SQLException, DataAccessException {
						LeaveEmployeeData result = new LeaveEmployeeData();
						rs.next();
						result.setEmployeeNumber(rs.getString("EMP_NBR").trim());
						result.setFirstName(rs.getString("NAME_F").trim());
						result.setMiddleName(rs.getString("NAME_M").trim());
						result.setLastName(rs.getString("NAME_L").trim());
						result.setEmailAddress(rs.getString("EMAIL").trim());
						result.setUserLoginName(rs.getString("USR_LOG_NAME").trim());
						return result;
					}
				});
		} catch (DataAccessException e) {	
		}
		return supervisorData;
	}

	public LeaveEmployeeData getSecondLineSupervisorData(String payFrequency, String employeeNumber) {
		LeaveEmployeeData supervisorData=null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ED.EMP_NBR, ED.NAME_F, ED.NAME_M, ED.NAME_L, ED.EMAIL, ISNULL(SU.USR_LOG_NAME,'') AS USR_LOG_NAME ");
		sql.append("FROM BHR_EMP_DEMO ED LEFT OUTER JOIN SEC_USERS SU ON ED.EMP_NBR=SU.EMP_NBR AND SU.USR_DELETED=0, BHR_EAP_CAMPUS_NXT_SPVSR CNS, BHR_EMP_PAY EP ");
		sql.append("WHERE EP.PAY_FREQ=:payFrequency AND EP.EMP_NBR=:employeeNumber AND EP.CYR_NYR_FLG='C' ");
		sql.append("	AND CNS.CAMPUS_ID=EP.PAY_CAMPUS AND CNS.DEPT=EP.PAY_DEPT AND ED.EMP_NBR=CNS.SPVSR_EMP_NBR");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("payFrequency", payFrequency);
		params.addValue("employeeNumber", employeeNumber);
		try {
			supervisorData = (LeaveEmployeeData) getNamedParameterJdbcTemplate().query(sql.toString(), params,
				new ResultSetExtractor<LeaveEmployeeData>() {
					@Override
					public LeaveEmployeeData extractData(final ResultSet rs) throws SQLException, DataAccessException {
						LeaveEmployeeData result = new LeaveEmployeeData();
						rs.next();
						result.setEmployeeNumber(rs.getString("EMP_NBR").trim());
						result.setFirstName(rs.getString("NAME_F").trim());
						result.setMiddleName(rs.getString("NAME_M").trim());
						result.setLastName(rs.getString("NAME_L").trim());
						result.setEmailAddress(rs.getString("EMAIL").trim());
						result.setUserLoginName(rs.getString("USR_LOG_NAME").trim());
						return result;
					}
				});
		} catch (DataAccessException e) {	
		}
		return supervisorData;
	}

	public List<LeaveEmployeeData> getSupervisorDirectReports(String supervisorEmployeeNumber) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", supervisorEmployeeNumber);
		
		return getNamedParameterJdbcTemplate().query(sqlSelectSupervisorDirectReports, params, new ParameterizedRowMapper<LeaveEmployeeData>()
		{
			@Override
			public LeaveEmployeeData mapRow(ResultSet rs, int rowNum) throws SQLException {
				LeaveEmployeeData leaveEmployeeData = new LeaveEmployeeData();
				
				leaveEmployeeData.setEmployeeNumber(rs.getString("EMP_EMP_NBR").trim());
				leaveEmployeeData.setFirstName(rs.getString("FIRST_NAME").trim());
				leaveEmployeeData.setMiddleName(rs.getString("MIDDLE_NAME").trim());
				leaveEmployeeData.setLastName(rs.getString("LAST_NAME").trim());
				leaveEmployeeData.setNumDirectReports(rs.getInt("NUM_DIRECT_REPORTS"));
				
				return leaveEmployeeData;
			}
		});
	}

	public List<LeaveEmployeeData> getPMISSupervisorDirectReports(String billetNumber, String posNumber) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("billetNumber", billetNumber);
		params.addValue("posNumber", posNumber);
		
		return getNamedParameterJdbcTemplate().query(PMIS_sqlSelectSupervisorDirectReports, params, new ParameterizedRowMapper<LeaveEmployeeData>()
		{
			@Override
			public LeaveEmployeeData mapRow(ResultSet rs, int rowNum) throws SQLException {
				LeaveEmployeeData leaveEmployeeData = new LeaveEmployeeData();
				
				leaveEmployeeData.setEmployeeNumber(rs.getString("OCC_EMP_NBR").trim());
				leaveEmployeeData.setFirstName(rs.getString("FIRST_NAME").trim());
				leaveEmployeeData.setMiddleName(rs.getString("MIDDLE_NAME").trim());
				leaveEmployeeData.setLastName(rs.getString("LAST_NAME").trim());
				leaveEmployeeData.setNumDirectReports(rs.getInt("NUM_DIRECT_REPORTS"));
				
				return leaveEmployeeData;
			}
		});
	}

	public LeaveEmployeeData getFirstLineSupervisor(String employeeNumber, boolean checkTemporaryApprover) {
		String sql=null;
		if (checkTemporaryApprover) {
			sql = sqlSelectFirstLineApproverDemo;
		} else {
			sql = sqlSelectFirstLineApproverDemoNoTemp;
		}
		LeaveEmployeeData employeeData=null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		try {
			employeeData = (LeaveEmployeeData) getNamedParameterJdbcTemplate().query(sql, params,
				new ResultSetExtractor<LeaveEmployeeData>() {
					@Override
					public LeaveEmployeeData extractData(final ResultSet rs) throws SQLException, DataAccessException {
						LeaveEmployeeData result = new LeaveEmployeeData();
						rs.next();
						result.setEmployeeNumber(rs.getString("EMP_NBR").trim());
						result.setFirstName(rs.getString("NAME_F").trim());
						result.setMiddleName(rs.getString("NAME_M").trim());
						result.setLastName(rs.getString("NAME_L").trim());
						result.setEmailAddress(rs.getString("EMAIL"));
						return result;
					}
				});
		} catch (DataAccessException e) {	
		}		
		return employeeData;
	}

	public LeaveEmployeeData getPMISFirstLineSupervisor(String supervisorBilletNumber, String supervisorPosNumber, boolean checkTemporaryApprover) {
		String sql=null;
		if (checkTemporaryApprover) {
			sql = PMIS_sqlSelectFirstLineApproverDemo;
		} else {
			sql = PMIS_sqlSelectFirstLineApproverDemoNoTemp;
		}
		LeaveEmployeeData employeeData=null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("supervisorBilletNumber", supervisorBilletNumber);
		params.addValue("supervisorPosNumber", supervisorPosNumber);
		try {
			employeeData = (LeaveEmployeeData) getNamedParameterJdbcTemplate().query(sql, params,
				new ResultSetExtractor<LeaveEmployeeData>() {
					@Override
					public LeaveEmployeeData extractData(final ResultSet rs) throws SQLException, DataAccessException {
						LeaveEmployeeData result = new LeaveEmployeeData();
						rs.next();
						result.setEmployeeNumber(rs.getString("EMP_NBR").trim());
						result.setFirstName(rs.getString("NAME_F").trim());
						result.setMiddleName(rs.getString("NAME_M").trim());
						result.setLastName(rs.getString("NAME_L").trim());
						result.setEmailAddress(rs.getString("EMAIL"));
						return result;
					}
				});
		} catch (DataAccessException e) {	
		}
		return employeeData;
	}

	public LeaveEmployeePMISData getEmployeePMISData(String employeeNumber) {
		StringBuilder sql=new StringBuilder("");
		sql.append("SELECT TOP 1 PPC.BILLET_NBR, PPC.POS_NBR FROM BHR_PMIS_POS_CTRL PPC ");
		sql.append("WHERE PPC.OCC_EMP_NBR=:employeeNumber AND PPC.POS_TYP='P' AND PPC.CYR_NYR_FLG='C' ");
		sql.append("ORDER BY PPC.PAY_FREQ DESC");

		LeaveEmployeePMISData employeePMISData=null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		try {
			employeePMISData = (LeaveEmployeePMISData) getNamedParameterJdbcTemplate().query(sql.toString(), params,
				new ResultSetExtractor<LeaveEmployeePMISData>() {
					@Override
					public LeaveEmployeePMISData extractData(final ResultSet rs) throws SQLException, DataAccessException {
						LeaveEmployeePMISData result = new LeaveEmployeePMISData();
						rs.next();
						result.setBilletNumber(rs.getString("BILLET_NBR").trim());
						result.setPosNumber(rs.getString("POS_NBR").trim());
						return result;
					}
				});
		} catch (DataAccessException e) {	
		}
		return employeePMISData;
	}

	public LeaveEmployeePMISData getEmployeeSupervisorPMISData(String employeeNumber) {
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT TOP 1 PPC.SPVSR_BILLET_NBR, PPC.SPVSR_POS_NBR ");
		sql.append("FROM BHR_PMIS_POS_CTRL PPC ");
		sql.append("WHERE PPC.OCC_EMP_NBR=:employeeNumber AND PPC.POS_TYP='P' AND PPC.CYR_NYR_FLG='C' ");
		sql.append("ORDER BY PPC.PAY_FREQ DESC");
		
		LeaveEmployeePMISData employeeSupervisorPMISData = null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		try {
			employeeSupervisorPMISData = (LeaveEmployeePMISData) getNamedParameterJdbcTemplate().query(sql.toString(), params,
				new ResultSetExtractor<LeaveEmployeePMISData>() {
					@Override
					public LeaveEmployeePMISData extractData(final ResultSet rs) throws SQLException, DataAccessException {
						LeaveEmployeePMISData result = new LeaveEmployeePMISData();
						rs.next();
						result.setBilletNumber(rs.getString("SPVSR_BILLET_NBR").trim());
						result.setPosNumber(rs.getString("SPVSR_POS_NBR").trim());
						return result;
					}
				});
		} catch (DataAccessException e) {	
		}
		return employeeSupervisorPMISData;
	}

	public void addLeaveRequestWorkflow (String approverEmployeeNumber, int leaveId, int sequence) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("leaveId", leaveId, java.sql.Types.INTEGER);
		params.addValue("sequenceNumber", sequence, java.sql.Types.INTEGER);
		params.addValue("approverEmployeeNumber", approverEmployeeNumber);		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int numRows = getNamedParameterJdbcTemplate().update(sqlInsertLeaveWorkflowItem, params, keyHolder);	
	}

	public List<LeaveRequest> getSupervisorSumittedLeaveRequests(String supervisorEmployeeNumber) {
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT ELW.SEQ_NUM, ELR.ID, ELR.PAY_FREQ, ELR.EMP_NBR, ELR.LV_TYP, ELR.ABS_RSN, ELR.DATETIME_SUBMITTED, ELR.DATETIME_FROM, ELR.DATETIME_TO, ELR.LV_UNITS_DAILY, ELR.LV_UNITS_USED, ELR.STATUS_CD, ");
		sql.append("  ELSC.DESCR AS STATUS_DESCR, ISNULL(ELC.LV_COMMENT,'') AS REQUEST_COMMENT, LT.DAYS_HRS, LTD.LONG_DESCR, AR.ABS_DESCR, ED.NAME_F, ED.NAME_M, ED.NAME_L ");
		sql.append("FROM BEA_EMP_LV_RQST ELR LEFT OUTER JOIN BEA_EMP_LV_COMMENTS ELC ON ELR.ID=ELC.LV_ID AND ELC.LV_COMMENT_TYP='C', ");
		sql.append("  BEA_EMP_LV_WORKFLOW ELW, BTEA_EMP_LV_STATUS_CODES ELSC, BTHR_LV_TYP LT, BTHR_LV_TYP_DESCR LTD, BTHR_ABS_RSN AR, BHR_EMP_DEMO ED ");
		sql.append("WHERE ELW.APPRVR_EMP_NBR = :employeeNumber AND ELR.EMP_NBR = ED.EMP_NBR ");
		sql.append("  AND ELW.LV_ID = ELR.ID ");
		sql.append("  AND ELR.LV_TYP=LT.LV_TYP AND LT.STAT='A' AND ELR.STATUS_CD = 'P' ");
		sql.append("  AND ELW.INSERT_DATETIME = (SELECT MAX(ELW2.INSERT_DATETIME) FROM BEA_EMP_LV_WORKFLOW ELW2 WHERE ELW2.LV_ID=ELW.LV_ID) ");
		sql.append("  AND ELR.STATUS_CD = ELSC.CD  ");
		sql.append("  AND LT.PAY_FREQ =  ELR.PAY_FREQ  ");
		sql.append("  AND ELR.LV_TYP=LTD.LV_TYP AND LTD.STAT='A' AND ELR.ABS_RSN=AR.ABS_RSN AND AR.STAT='A' ");
		sql.append("ORDER BY DATETIME_FROM ASC ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", supervisorEmployeeNumber);
		
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<LeaveRequest>()
		{
			@Override
			public LeaveRequest mapRow(ResultSet rs, int rowNum) throws SQLException {

				LeaveRequest leaveRequest = new LeaveRequest();
				leaveRequest.setWorkflowSequenceNumber(rs.getInt("SEQ_NUM"));
				leaveRequest.setPayFrequency(rs.getString("PAY_FREQ").trim());
				leaveRequest.setId(rs.getInt("ID"));
				leaveRequest.setEmployeeNumber(rs.getString("EMP_NBR").trim());
				leaveRequest.setEmployeeFirstName(rs.getString("NAME_F").trim());
				leaveRequest.setEmployeeMiddleName(rs.getString("NAME_M").trim());
				leaveRequest.setEmployeeLastName(rs.getString("NAME_L").trim());
				leaveRequest.setLeaveType(rs.getString("LV_TYP").trim());
				leaveRequest.setAbsenceReason(rs.getString("ABS_RSN").trim());
				leaveRequest.setSubmittedDateTime(rs.getTimestamp("DATETIME_SUBMITTED"));
				leaveRequest.setSubmittedDateTimeString(dateTimeFormat.format(rs.getTimestamp("DATETIME_SUBMITTED")));
				
				leaveRequest.setFromDateTime(rs.getTimestamp("DATETIME_FROM"));
				leaveRequest.setFromDateString(dateFormat.format(rs.getTimestamp("DATETIME_FROM")));
				leaveRequest.setFromTimeString(timeFormat.format(rs.getTimestamp("DATETIME_FROM")));
				
				leaveRequest.setToDateTime(rs.getTimestamp("DATETIME_TO"));
				leaveRequest.setToDateString(dateFormat.format(rs.getTimestamp("DATETIME_TO")));
				leaveRequest.setToTimeString(timeFormat.format(rs.getTimestamp("DATETIME_TO")));
				
				leaveRequest.setLeaveHoursDaily(rs.getBigDecimal("LV_UNITS_DAILY").setScale(3, BigDecimal.ROUND_HALF_UP));
				leaveRequest.setLeaveRequested(rs.getBigDecimal("LV_UNITS_USED").setScale(3, BigDecimal.ROUND_HALF_UP));
				leaveRequest.setLeaveUnits(rs.getString("DAYS_HRS").trim().toUpperCase());
				leaveRequest.setRequestComment(rs.getString("REQUEST_COMMENT"));
				leaveRequest.setLeaveTypeDescription(leaveRequest.getLeaveType()+" - "+rs.getString("LONG_DESCR").trim());
				leaveRequest.setAbsenceReasonDescription(rs.getString("ABS_DESCR").trim());
				leaveRequest.setStatus(rs.getString("STATUS_CD").trim());
				leaveRequest.setStatusDescription(rs.getString("STATUS_DESCR").trim());

				return leaveRequest;
			}
		});
	}

	public List<LeaveUnitsConversion> getMinutesToHoursConversionRecs(String payFrequency, String leaveType) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT LV_TYP, UP_TO_MIN, PCT_OF_HR_DAY FROM BTHR_LV_UNTS_CONV ");
		sql.append("WHERE PAY_FREQ=:payFrequency AND LV_TYP=:leaveType AND UP_TO_MIN>0 AND UP_TO_HR=0.0 ORDER BY UP_TO_MIN ASC");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("payFrequency", payFrequency);
		params.addValue("leaveType", leaveType);
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<LeaveUnitsConversion>()
		{
			@Override
			public LeaveUnitsConversion mapRow(ResultSet rs, int rowNum) throws SQLException {
				LeaveUnitsConversion rec = new LeaveUnitsConversion();
				rec.setUnitType(LeaveUnitsConversion.UNIT_TYPE_HOURS);
				rec.setLeaveType(rs.getString("LV_TYP").trim());
				rec.setToUnit(rs.getBigDecimal("UP_TO_MIN").setScale(3, BigDecimal.ROUND_HALF_UP));
				rec.setFractionalAmount(rs.getBigDecimal("PCT_OF_HR_DAY").setScale(3, BigDecimal.ROUND_HALF_UP));
				return rec;
			}
		});
	}

	public List<LeaveUnitsConversion> getHoursToDaysConversionRecs(String payFrequency, String leaveType) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT LV_TYP, UP_TO_HR, PCT_OF_HR_DAY FROM BTHR_LV_UNTS_CONV ");
		sql.append("WHERE PAY_FREQ=:payFrequency AND LV_TYP=:leaveType AND UP_TO_HR>0.0 AND UP_TO_MIN=0 ORDER BY UP_TO_HR ASC");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("payFrequency", payFrequency);
		params.addValue("leaveType", leaveType);
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<LeaveUnitsConversion>()
		{
			@Override
			public LeaveUnitsConversion mapRow(ResultSet rs, int rowNum) throws SQLException {
				LeaveUnitsConversion rec = new LeaveUnitsConversion();
				rec.setUnitType(LeaveUnitsConversion.UNIT_TYPE_DAYS);
				rec.setLeaveType(rs.getString("LV_TYP").trim());
				rec.setToUnit(rs.getBigDecimal("UP_TO_HR").setScale(3, BigDecimal.ROUND_HALF_UP));
				rec.setFractionalAmount(rs.getBigDecimal("PCT_OF_HR_DAY").setScale(3, BigDecimal.ROUND_HALF_UP));
				return rec;
			}
		});
	}

	public List<LeaveTemporaryApprover> getTemporaryApprovers(String supervisorEmployeeNumber) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("supervisorEmployeeNumber", supervisorEmployeeNumber);
		
		return getNamedParameterJdbcTemplate().query(sqlSelectTemporaryApprovers, params, new ParameterizedRowMapper<LeaveTemporaryApprover>()
		{
			@Override
			public LeaveTemporaryApprover mapRow(ResultSet rs, int rowNum) throws SQLException {

				LeaveTemporaryApprover leaveTemporaryApprover = new LeaveTemporaryApprover();
				
				leaveTemporaryApprover.setId(rs.getInt("ID"));
				leaveTemporaryApprover.setFromDateTime(rs.getTimestamp("DATETIME_FROM"));
				leaveTemporaryApprover.setFromDateString(dateFormat.format(rs.getTimestamp("DATETIME_FROM")));
				leaveTemporaryApprover.setToDateTime(rs.getTimestamp("DATETIME_TO"));
				leaveTemporaryApprover.setToDateString(dateFormat.format(rs.getTimestamp("DATETIME_TO")));
				
				LeaveEmployeeData temporaryApproverData = new LeaveEmployeeData();
				temporaryApproverData.setFirstName(rs.getString("NAME_F").trim());
				temporaryApproverData.setMiddleName(rs.getString("NAME_M").trim());
				temporaryApproverData.setLastName(rs.getString("NAME_L").trim());
				temporaryApproverData.setEmployeeNumber(rs.getString("TMP_APPRVR_EMP_NBR").trim());
				
				// not sure if this field is staying
				temporaryApproverData.setAutoCompleteString(temporaryApproverData.getSelectOptionLabel());
				
				
				leaveTemporaryApprover.setTemporaryApprover(temporaryApproverData);

				return leaveTemporaryApprover;
			}
		});
	}

	public void addTemporaryApprover (LeaveTemporaryApprover leaveTemporaryApprover, String supervisorEmployeeNumber) {
		// build from and to timestamps 
		StringBuffer fromDateTimeSB = new StringBuffer(leaveTemporaryApprover.getFromDateString().substring(0,2)).append("-")
			.append(leaveTemporaryApprover.getFromDateString().substring(3,5)).append("-").append(leaveTemporaryApprover.getFromDateString().substring(6,10))
			.append(" 00:00:00");
		StringBuffer toDateTimeSB = new StringBuffer(leaveTemporaryApprover.getToDateString().substring(0,2)).append("-")
			.append(leaveTemporaryApprover.getToDateString().substring(3,5)).append("-").append(leaveTemporaryApprover.getToDateString().substring(6,10))
			.append(" 23:59:59");
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = dateTimeFormat24hr.parse(fromDateTimeSB.toString());
			toDate = dateTimeFormat24hr.parse(toDateTimeSB.toString());
		} catch (Exception e) {
			
		}
		if (fromDate != null && toDate != null) {
			Timestamp fromTimeStamp = new Timestamp(fromDate.getTime());
			Timestamp toTimeStamp = new Timestamp(toDate.getTime());
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("fromDateTime", fromTimeStamp, java.sql.Types.TIMESTAMP);
			params.addValue("toDateTime", toTimeStamp, java.sql.Types.TIMESTAMP);
			
			params.addValue("supervisorEmployeeNumber", supervisorEmployeeNumber);
			params.addValue("tempApproverEmployeeNumber", leaveTemporaryApprover.getTemporaryApprover().getEmployeeNumber());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int numRows = getNamedParameterJdbcTemplate().update(sqlInsertTemporaryApprover, params, keyHolder);
// 			int id = keyHolder.getKey().intValue();  ... don't need to know the id
		}
	}
	
	public void updateTemporaryApprover (LeaveTemporaryApprover leaveTemporaryApprover) {

		// build from and to timestamps 
		StringBuffer fromDateTimeSB = new StringBuffer(leaveTemporaryApprover.getFromDateString().substring(0,2)).append("-")
			.append(leaveTemporaryApprover.getFromDateString().substring(3,5)).append("-").append(leaveTemporaryApprover.getFromDateString().substring(6,10))
			.append(" 00:00:00");
		StringBuffer toDateTimeSB = new StringBuffer(leaveTemporaryApprover.getToDateString().substring(0,2)).append("-")
			.append(leaveTemporaryApprover.getToDateString().substring(3,5)).append("-").append(leaveTemporaryApprover.getToDateString().substring(6,10))
			.append(" 23:59:59");
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = dateTimeFormat24hr.parse(fromDateTimeSB.toString());
			toDate = dateTimeFormat24hr.parse(toDateTimeSB.toString());
		} catch (Exception e) {
			
		}
		if (fromDate != null && toDate != null) {
			Timestamp fromTimeStamp = new Timestamp(fromDate.getTime());
			Timestamp toTimeStamp = new Timestamp(toDate.getTime());

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("fromDateTime", fromTimeStamp, java.sql.Types.TIMESTAMP);
			params.addValue("toDateTime", toTimeStamp, java.sql.Types.TIMESTAMP);
			params.addValue("tempApproverEmployeeNumber", leaveTemporaryApprover.getTemporaryApprover().getEmployeeNumber());
			params.addValue("temporaryApproverRowId", leaveTemporaryApprover.getId(), java.sql.Types.INTEGER);			
			int numRows = getNamedParameterJdbcTemplate().update(sqlUpdateTemporaryApprover, params);
		}
	}

	public void deleteTemporaryApprover (LeaveTemporaryApprover leaveTemporaryApprover) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("temporaryApproverRowId", leaveTemporaryApprover.getId(), java.sql.Types.INTEGER);
		getNamedParameterJdbcTemplate().update(sqlDeleteTemporaryApprover, params);
	}

	public int addLeaveRequestTransmittal (String dtOfAbs, String dtOfPay, String approverLoginName, LeaveRequest leaveRequest, BigDecimal leaveUnitsDaily) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("absenceReason", leaveRequest.getAbsenceReason());
		params.addValue("dateOfAbsence", dtOfAbs);
		params.addValue("payDate", dtOfPay);
		params.addValue("employeeNumber", leaveRequest.getEmployeeNumber());
		params.addValue("leaveId", leaveRequest.getId(), java.sql.Types.INTEGER);
		params.addValue("leaveType", leaveRequest.getLeaveType());			
	    params.addValue("leaveUnitsDaily", leaveUnitsDaily, java.sql.Types.DECIMAL);	    	
		params.addValue("payFrequency", leaveRequest.getPayFrequency());
		params.addValue("approverLoginName", approverLoginName);
		int numRows = getNamedParameterJdbcTemplate().update(sqlInsertXmital, params);	
		return numRows;
	}
	
	public void updateLeaveRequestApproverActionComment (LeaveRequest leaveRequest, String supervisorEmpNumber) {
		// insert approver comment if any
		if (leaveRequest.getApproverComment().trim().length() > 0) {
			MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("leaveId", leaveRequest.getId(), java.sql.Types.INTEGER);
				if (leaveRequest.getApproverAction().equals(LeaveRequest.APPROVER_ACTION_APPROVE)) {
					params.addValue("commentType", LeaveRequest.COMMENT_TYP_APPROVAL_COMMENT);
				} else {  // disapproved
					params.addValue("commentType", LeaveRequest.COMMENT_TYP_DISAPPROVAL_COMMENT);
				}
				params.addValue("employeeNumber", supervisorEmpNumber);
				params.addValue("requestComment", leaveRequest.getApproverComment());
				int numRows = getNamedParameterJdbcTemplate().update(sqlInsertLeaveComment, params);
		}
	}
	
	public void updateLeaveRequestRequesterCommentTypeToPriorWF (LeaveRequest leaveRequest) {
		// update requester's comment (if any) ... set the comment type to prior workflow since this workflow is complete
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("leaveId", leaveRequest.getId(), java.sql.Types.INTEGER);
		params.addValue("employeeNumber", leaveRequest.getEmployeeNumber());
		int numRows = getNamedParameterJdbcTemplate().update(sqlUpdateRequestCommentTypeToPriorWF, params);
	}
	
	public List<LeavePayDate> getLeaveRequestPayDates(String payFrequency, Date fromDate, Date toDate) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PD.DT_OF_PAY, PD.DT_PAYPER_BEG, PD.DT_PAYPER_END, PD.LV_REQ_CUTOFF_DT, PD.LV_LAST_APRV_DT ");
		sql.append("FROM BTHR_PAY_DATES PD, BTHR_PAY_DATES_STATUS PDS ");
		sql.append("WHERE PD.PAY_FREQ=:payFrequency AND PDS.PAY_FREQ=:payFrequency AND PD.DT_OF_PAY=PDS.DT_OF_PAY AND PDS.ADJ_NBR=0 AND DT_PAY_RUN='' ");
		sql.append("  AND CONVERT(CHAR(8),PD.DT_PAYPER_END,112) >= CONVERT(CHAR(8),:fromDate ,112) ");
		sql.append("ORDER BY DT_PAYPER_END ASC");
		
		String fromDateString = bhrDateFormat.format(fromDate);
		String toDateString = bhrDateFormat.format(toDate);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("payFrequency", payFrequency);
		params.addValue("fromDate", fromDateString);
		params.addValue("toDate", toDateString);
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<LeavePayDate>()
		{
			@Override
			public LeavePayDate mapRow(ResultSet rs, int rowNum) throws SQLException {
				DateFormat dateFormat = new SimpleDateFormat ("yyyyMMdd");
				LeavePayDate leavePayDate = new LeavePayDate();
				try {
					leavePayDate.setPayDate(bhrDateFormat.parse(rs.getString("DT_OF_PAY").trim()));
					leavePayDate.setPayPeriodBeginDate(bhrDateFormat.parse(rs.getString("DT_PAYPER_BEG").trim()));
					leavePayDate.setPayPeriodEndDate(bhrDateFormat.parse(rs.getString("DT_PAYPER_END").trim()));
					leavePayDate.setSkip(false);
					if (rs.getString("LV_REQ_CUTOFF_DT").trim().length()>0) {
						if (rs.getString("LV_REQ_CUTOFF_DT").trim().toUpperCase().equals("XXXXXXXX")) {
							leavePayDate.setSkip(true);
						} else {
							leavePayDate.setLeaveRequestCutoffDate(bhrDateFormat.parse(rs.getString("LV_REQ_CUTOFF_DT").trim()));
						}
					}
					if (rs.getString("LV_LAST_APRV_DT").trim().length()>0) {
						if (rs.getString("LV_LAST_APRV_DT").trim().toUpperCase().equals("XXXXXXXX")) {
							leavePayDate.setSkip(true);
						} else {
							leavePayDate.setLeaveLastApprovalDate(dateFormat.parse(rs.getString("LV_LAST_APRV_DT").trim()));
						}
					}
				} catch (Exception e) {
					leavePayDate.setSkip(true);
				}
				return leavePayDate;
			}
		});
	}
	
	public List<HashMap<String, Integer>> getPendingPayrollLeaveCounts(String employeeNumber) {
		// check on leave requests where the most recent status update indicated that 
		// (1) none of the leave request transmittal records had been processed 'P'
		// (2) some but not all of the leave request transmittal records had been processed 'I'
		// (3) the number of transmittal records for the leave request did not match the number of days of the leave request 'V'
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ELR.ID, (DATEDIFF( DAY, ELR.DATETIME_FROM, ELR.DATETIME_TO)+1) AS NUM_REQUEST_DAYS, ");
		sql.append("	(SELECT COUNT(*) FROM BHR_EMP_LV_XMITAL ELX WHERE ELX.LV_ID=ELR.ID AND ELX.PROCESS_DT!='') AS NUM_PROCESSED, ");
		sql.append("	(SELECT COUNT(*) FROM BHR_EMP_LV_XMITAL ELX WHERE ELX.LV_ID=ELR.ID AND ELX.PROCESS_DT='') AS NUM_PENDING_PAYROLL ");
		sql.append("FROM BEA_EMP_LV_RQST ELR ");
		sql.append("WHERE ELR.STATUS_CD IN ('A') AND ELR.EMP_NBR=:employeeNumber");  

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		return getNamedParameterJdbcTemplate().query(sql.toString(), params, new ParameterizedRowMapper<HashMap<String, Integer>>()
		{
			@Override
			public HashMap<String, Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
				HashMap<String, Integer> pendingPayrollCountsHash = new HashMap<String, Integer>();
				pendingPayrollCountsHash.put(PENDING_PAYROLL_COUNTS_HASH_KEY_REQUEST_ID, new Integer(rs.getInt("ID")));
				pendingPayrollCountsHash.put(PENDING_PAYROLL_COUNTS_HASH_KEY_REQUEST_NUM_DAYS, new Integer(rs.getInt("NUM_REQUEST_DAYS")));
				pendingPayrollCountsHash.put(PENDING_PAYROLL_COUNTS_HASH_KEY_NUM_PROCESSED, new Integer(rs.getInt("NUM_PROCESSED")));
				pendingPayrollCountsHash.put(PENDING_PAYROLL_COUNTS_HASH_KEY_NUM_PENDING, new Integer(rs.getInt("NUM_PENDING_PAYROLL")));
				return pendingPayrollCountsHash;
			}
		});
	}

	public boolean isLeaveApprovalAccessGranted (String employeeNumber) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeNumber", employeeNumber);
		
		boolean leaveApprovalAccessGranted = (Boolean) getNamedParameterJdbcTemplate().query(sqlSelectNonSpvsrLeaveRequestApprovalAccess, params,
				new ResultSetExtractor<Boolean>() {
					@Override
					public Boolean extractData(final ResultSet rs) throws SQLException, DataAccessException {
						Boolean accessGranted=null;
						rs.next();
						int accessValue = rs.getInt("ACCESS");
						if (accessValue > 0) {
							accessGranted = new Boolean(true);
						} else {
							accessGranted = new Boolean(false);
						}
						return accessGranted;
					}
				});

		return leaveApprovalAccessGranted;
	}
	
	//Retrieves list of calendar events for the this employee and subordinates
	public List<CalendarEvents> getCalendarEvents(String empNbr, String start, String end) {
		List<CalendarEvents> events = new ArrayList<CalendarEvents>();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empNbr", empNbr);
		params.addValue("startDt", start);
		params.addValue("endDt", end);
		
		StringBuilder sql = new StringBuilder();
		sql.append("select c.NAME_F + ' ' + (CASE when (c.name_m > '') then c.name_m + ' ' else '' END) + c.NAME_L as title, "); 
		sql.append("substring(b.dt_of_abs,1, 4) + '-' + substring(b.dt_of_abs,5,2) + '-' + substring(b.dt_of_abs,7,2) + 'T' + isnull(substring(d.datetime_from, 12, 8), '08:00:00') as 'start', ");
		sql.append("substring(b.dt_of_abs,1, 4) + '-' + substring(b.dt_of_abs,5,2) + '-' + substring(b.dt_of_abs,7,2) + 'T' + isnull(substring(d.datetime_to, 12, 8), '08:00:00') as 'end', ");
		sql.append("isnull(convert(char(20), D.id), ('X' || B.pay_freq || b.emp_nbr || b.dt_of_pay || convert(char(3), lv_seq)))  as id ");
		sql.append("from BHR_EAP_EMP_TO_SPVSR A, BHR_EMP_LV_XMITAL B LEFT OUTER JOIN bea_emp_lv_rqst D on b.lv_id = D.id, BHR_EMP_DEMO C ");
		sql.append("where A.spvsr_emp_nbr = :empNbr and B.CYR_NYR_FLG = 'C' and B.emp_nbr = A.emp_EMP_NBR ");
		sql.append("and a.EMP_EMP_NBR = c.EMP_NBR and b.dt_of_abs >= :startDt and B.DT_OF_ABS <= :endDt ");
		sql.append("UNION  ");
		sql.append("select c.NAME_F + ' ' + (CASE when (c.name_m > '') then c.name_m + ' ' else '' END) + c.NAME_L as title, "); 
		sql.append("substring(b.dt_of_abs,1, 4) + '-' + substring(b.dt_of_abs,5,2) + '-' + substring(b.dt_of_abs,7,2) + 'T' + isnull(substring(d.datetime_from, 12, 8), '08:00:00') as 'start', "); 
		sql.append("substring(b.dt_of_abs,1, 4) + '-' + substring(b.dt_of_abs,5,2) + '-' + substring(b.dt_of_abs,7,2) + 'T' + isnull(substring(d.datetime_to, 12, 8), '08:00:00') as 'end',  ");
		sql.append("isnull(convert(char(20), D.id), ('X' || B.pay_freq || b.emp_nbr || b.dt_of_pay || convert(char(3), lv_seq)))  as id ");
		sql.append("from BHR_EMP_LV_XMITAL B LEFT OUTER JOIN bea_emp_lv_rqst D on b.lv_id = D.id, BHR_EMP_DEMO C ");
		sql.append("where B.CYR_NYR_FLG = 'C' and B.emp_nbr = :empNbr and b.EMP_NBR = c.EMP_NBR and b.dt_of_abs >= :startDt and B.DT_OF_ABS <= :endDt ");
		sql.append("UNION  ");
		sql.append("select c.NAME_F + ' ' + (CASE when (c.name_m > '') then c.name_m + ' ' else '' END) + c.NAME_L + (CASE when (D.status_cd = 'P') then ' (P)' else '' END) as title, "); 
		sql.append("substring(datetime_from, 1, 10) + 'T' + substring(datetime_from, 12, 8) as 'start',  ");
		sql.append("substring(datetime_to, 1, 10) + 'T' + substring(datetime_to, 12, 8) as 'end',  ");
		sql.append("convert(char(20), D.id) as id ");
		sql.append("from bea_emp_lv_rqst D, BHR_EMP_DEMO C ");
		sql.append("where D.emp_nbr = c.emp_nbr and D.datetime_from >= :startDt and D.datetime_from <= :endDt and D.status_cd in ('A','P') ");
		sql.append("and exists (select 1 from BHR_EAP_EMP_TO_SPVSR A where A.spvsr_emp_nbr = :empNbr and A.emp_emp_nbr = D.emp_nbr) ");
		sql.append("UNION  ");
		sql.append("select c.NAME_F + ' ' + (CASE when (c.name_m > '') then c.name_m + ' ' else '' END) + c.NAME_L + (CASE when (D.status_cd = 'P') then ' (P)' else '' END) as title, "); 
		sql.append("substring(datetime_from, 1, 10) + 'T' + substring(datetime_from, 12, 8) as 'start', "); 
		sql.append("substring(datetime_to, 1, 10) + 'T' + substring(datetime_to, 12, 8) as 'end',  ");
		sql.append("convert(char(20), D.id) as id ");
		sql.append("from bea_emp_lv_rqst D, BHR_EMP_DEMO C ");
		sql.append("where d.emp_nbr = :empNbr and D.emp_nbr = c.emp_nbr and D.datetime_from >= :startDt and D.datetime_from <= :endDt and D.status_cd in ('A','P') ");



		events = (List<CalendarEvents>) getNamedParameterJdbcTemplate().query(sql.toString(), params,  new EventMapper());
		
		return events;
	}
	
	private class EventMapper implements RowMapper<CalendarEvents> {
		@Override
		public CalendarEvents mapRow(ResultSet rs, int row) throws SQLException {
			return new CalendarEvents(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
		}
	}

	public CalendarDetail getCalendarDetail(String id) {
		CalendarDetail detail=null;
		StringBuilder sql = new StringBuilder();
		
		sql.append("select substring(convert(char(20), R.datetime_from, 0), 13, 7) as fromTime, ");
		sql.append("substring(convert(char(20), R.datetime_to, 0), 13, 7) as toTime, ");
		sql.append("convert(char(7), R.lv_units_used) as units, R.lv_typ as leaveCd, L.LONG_DESCR as descr, ");
		sql.append("A.ABS_DESCR as reason, S.DESCR status, isnull(sec_users.USR_NAME_F + ' ' + sec_users.USR_NAME_L, '') as approver, ");
		
		sql.append("isnull(C.LV_COMMENT, '') as comments, isnull(C2.LV_COMMENT, '') as appComments, "); 
		sql.append(" isnull(C2.LV_COMMENT_EMP_NBR,'') AS appCommentsEmpNbr, W.APPRVR_EMP_NBR as approverEmpNbr, '' as payrollProcessedDate ");
		
		sql.append("from BEA_EMP_LV_RQST R ");
		sql.append("  left outer join BEA_EMP_LV_COMMENTS C on r.id = c.lv_id and c.lv_comment_datetime = (select max(lv_comment_datetime) from BEA_EMP_LV_COMMENTS where lv_id = C.lv_id and (LV_COMMENT_TYP = 'C' or LV_COMMENT_TYP = 'P'))  ");
		sql.append("  left outer join BEA_EMP_LV_COMMENTS C2 on r.id = c2.lv_id and c2.lv_comment_datetime = (select max(lv_comment_datetime) from BEA_EMP_LV_COMMENTS where lv_id = c2.lv_id and LV_COMMENT_TYP = 'A'), ");
		sql.append("BTHR_LV_TYP_DESCR L, BTHR_ABS_RSN A, BTEA_EMP_LV_STATUS_CODES S, "); 
		sql.append("BEA_EMP_LV_WORKFLOW W "); 
		sql.append("  left outer join sec_users on W.APPRVR_EMP_NBR = sec_users.EMP_NBR ");
		sql.append("where r.id = :id and R.lv_typ = L.lv_typ and R.abs_rsn = A.ABS_RSN and R.STATUS_CD = S.CD and W.ID = (select max(id) from BEA_EMP_LV_WORKFLOW where lv_id=r.id) ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		try {
			detail = (CalendarDetail) getNamedParameterJdbcTemplate().query(sql.toString(), params, new CalenderDetailExtractor());
		} catch (DataAccessException e) {
		}
		return detail;
	}
	
	public CalendarDetail getCalendarDetailXmital(String payFreq, String empNbr, String dtPay, String seq) {
		CalendarDetail detail=null;
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT '08:00AM' as fromTime, '08:00AM' as toTime, convert(char(7), X.lv_units_used) as units, X.lv_typ as leaveCd, ");
		sql.append("L.LONG_DESCR as descr, A.ABS_DESCR as reason, '' as status, isnull(U.USR_NAME_F + ' ' + U.USR_NAME_L, '') as approver, ");
		sql.append("'' as comments, '' as appComments, '' as appCommentsEmpNbr,  '' as approverEmpNbr, X.process_dt as payrollProcessedDate ");
		sql.append("from BHR_EMP_LV_XMITAL X left outer join sec_users U on X.user_id = U.USR_LOG_NAME, BTHR_LV_TYP_DESCR L, BTHR_ABS_RSN A ");
		sql.append("where X.cyr_nyr_flg = 'C' and X.pay_freq = :payFreq and x.emp_nbr = :empNbr and x.DT_OF_PAY = :dtPay and x.LV_SEQ = :seq and X.lv_typ = L.lv_typ and X.abs_rsn = A.ABS_RSN ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("payFreq", payFreq);
		params.addValue("empNbr", empNbr);
		params.addValue("dtPay", dtPay);
		params.addValue("seq", seq);
		try {
			detail = (CalendarDetail) getNamedParameterJdbcTemplate().query(sql.toString(), params, new CalenderDetailExtractor());
		} catch (DataAccessException e) {
		}
		return detail;
	}
	
	private class CalenderDetailExtractor implements ResultSetExtractor<CalendarDetail> {
		@Override
		public CalendarDetail extractData(final ResultSet rs) throws SQLException, DataAccessException {
			CalendarDetail detail = new CalendarDetail();
			rs.next();
			detail.setFromTime(rs.getString("fromTime").trim());
			detail.setToTime(rs.getString("toTime").trim());
			detail.setUnits(rs.getString("units").trim());
			detail.setLeaveCd(rs.getString("leaveCd").trim());
			detail.setDescr(rs.getString("descr").trim());
			detail.setReason(rs.getString("reason").trim());
			detail.setStatus(rs.getString("status").trim());
			detail.setApprover(rs.getString("approver").trim());
			detail.setComments(rs.getString("comments").trim());
			
			String appCommentsEmpNbr = rs.getString("appCommentsEmpNbr").trim();
			String approverEmpNbr = rs.getString("approverEmpNbr").trim();
			if (appCommentsEmpNbr.equals(approverEmpNbr)) {
				// only include the approver comments if the comments were entered by the final approver of the workflow
				detail.setAppComments(rs.getString("appComments").trim());
			} else {
				detail.setAppComments("");
			}
			if (detail.getStatus().equals("")) {
				// this is from the transmittal table... set status based on processed date
				String payrollProcessedDate = rs.getString("payrollProcessedDate").trim();
				if (payrollProcessedDate.equals("")) {
					detail.setStatus("Pending Payroll");
				} else {
					detail.setStatus("Processed");
				}
			}
			return detail;
		}
		
	}
	
	public String getLeaveReqStatus(int id) {
		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		String value = "";
		
		sql.append("SELECT status_cd FROM bea_emp_lv_rqst WHERE id = :id ");
				
		params.addValue("id", id);
		
		try {
			value = getNamedParameterJdbcTemplate().queryForObject(sql.toString(), params, String.class);
		} catch (DataAccessException e) {
			
		}
		
		
		return value;
	}
}
