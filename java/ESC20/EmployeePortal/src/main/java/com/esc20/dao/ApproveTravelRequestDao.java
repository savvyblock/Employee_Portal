package com.esc20.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;

import com.esc20.model.CheckTrans;
import com.esc20.nonDBModels.ApproveTravelRequest;
import com.esc20.nonDBModels.TravelRequestCalendar;

import net.esc20.txeis.common.util.CommonBusinessUtil;


@Repository
public class ApproveTravelRequestDao {

	private Logger logger = LoggerFactory.getLogger(TravelRequestDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
    
    private DataSource getDataSource() throws Exception {
    	return SessionFactoryUtils.getDataSource(sessionFactory);
    }
	
	final int RLZD_REVNU = 1;
	final int EXPEND = 3;
	final int BALANCE = 6;
	
	//*************************************************************************************************************************************
	// Used for Audit Module
	private final String auditModule = "Approve Travel Request";

	private StringBuffer getApproveTravelRequestQuery() {
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT WORKFLOW.WORKFLOW_TYPE_ID    ");
		sql.append(",  WORKFLOW.CURRENT_APPROVER_ID ");
		sql.append(" ,  APPROVER_EMP_NBR = APPROVER.EMP_NBR   ");
		sql.append(" ,  TRVL_RQST.VENDOR_NBR  ");
		sql.append(" ,  TRVL_RQST.EMP_NBR   ");
		sql.append(" ,  TRVL_RQST.EMP_NAME  ");
		sql.append(" ,  TRVL_RQST.TRIP_NBR  ");
		sql.append(" ,  ENTRY_DATE = convert(varchar, CAST(TRVL_RQST.ENTRY_DT as date), 110) ");
		sql.append(" ,  TRVL_RQST.RQST_TOT  ");
		sql.append(" ,  TRVL_RQST.OVRNIGHT     ");
		sql.append(" FROM ( SELECT * FROM WFL_WORKFLOW WHERE  WORKFLOW_TYPE_ID = 14 ) WORKFLOW ");
		sql.append(" JOIN ( SELECT SEC_USERS.USR_ID ");
		sql.append("  , BEA_USERS.EMP_NBR ");
		sql.append(" FROM SEC_USERS ");
		sql.append(" JOIN BEA_USERS ");
		sql.append("   ON SEC_USERS.EMP_NBR = BEA_USERS.EMP_NBR  ");
		sql.append(" WHERE BEA_USERS.EMP_NBR = :empNbr ");
		sql.append("  )APPROVER ");
		sql.append(" ON WORKFLOW.CURRENT_APPROVER_ID = APPROVER.USR_ID ");
		sql.append("  JOIN  ");
		sql.append("  ( SELECT  TR_REQUEST.TRIP_NBR ");
		sql.append("        ,  TR_REQUEST.VENDOR_NBR ");
		sql.append("    ,  TR_REQUEST.EMP_NBR ");
		sql.append("  ,  BHR_EMP_DEMO.NAME_F||' '||BHR_EMP_DEMO.NAME_L AS EMP_NAME ");
		sql.append("  ,  TR_REQUEST.ENTRY_DT ");
		sql.append("  ,  TR_REQUEST.OVRNIGHT  ");
		sql.append("  ,  REQUEST_TOT.RQST_TOT   ");
		sql.append("  FROM    ");
		sql.append("  ( SELECT TRIP_NBR ");
		sql.append("    , VENDOR_NBR ");
		sql.append("   , EMP_NBR ");
		sql.append("  , OVRNIGHT ");
		sql.append(" , MIN(CAST(ENTRY_DT AS DATE)) AS ENTRY_DT ");
		sql.append(" FROM BEA_EMP_TRVL WHERE TRVL_REQ_STAT = 'P' ");
		sql.append(" GROUP BY TRIP_NBR, VENDOR_NBR, EMP_NBR, OVRNIGHT) TR_REQUEST  ");
		sql.append(" JOIN BHR_EMP_DEMO ");
		sql.append("  ON TR_REQUEST.EMP_NBR = BHR_EMP_DEMO.EMP_NBR  ");
		sql.append(" JOIN ( SELECT TRIP_NBR ");
		sql.append("  , SUM(ACCT_AMT) RQST_TOT ");
		sql.append(" FROM BEA_EMP_TRVL_ACCTS ");
		sql.append("  GROUP BY TRIP_NBR ) REQUEST_TOT ");
		sql.append(" ON  TR_REQUEST.TRIP_NBR = REQUEST_TOT.TRIP_NBR ) TRVL_RQST ");
		sql.append(" ON WORKFLOW.REQUEST_ID = TRVL_RQST.TRIP_NBR; ");

		return sql;

	}
	
	public Integer getSecUserForEmpNbr(String empNbr) throws Exception{
		Integer secUserNbr = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("Select USR_ID FROM SEC_USERS WHERE EMP_NBR=:empNbr");
			Query query = this.getSession().createSQLQuery(sql.toString());
			
			query.setParameter("empNbr", empNbr);
			
			@SuppressWarnings("unchecked")
			List<Object[]> res = query.list();
			for (Object rs : res) {
				secUserNbr = (Integer) rs;
			}
		} catch (Exception e) {
			logger.error("Error occurred on getSecUserFromEmpNbr method while retrieving :",
					e.getMessage());
		}
		return secUserNbr;
	}
	
	public List<ApproveTravelRequest> getApproveTravelRequest(String empNbr) throws Exception {

		StringBuffer sql = getApproveTravelRequestQuery();

		Query query = this.getSession().createSQLQuery(sql.toString());
		
		query.setParameter("empNbr", empNbr);

		@SuppressWarnings("unchecked")
		List<Object[]> queryList = query.list();

		List<ApproveTravelRequest> approveTravelRequestList = new ArrayList<ApproveTravelRequest>();

		for (Object[] row : queryList) {
			ApproveTravelRequest approveTravelRequest = new ApproveTravelRequest();
			int i = 0;
			approveTravelRequest.setWorkflowType((Integer) row[i++]);
			approveTravelRequest.setWorkflowCurrentApprover((Integer) row[i++]);
			approveTravelRequest.setApproverEmplNumber((String) row[i++]);
			approveTravelRequest.setVendorNbr((String) row[i++]);
			approveTravelRequest.setEmpNbr((String) row[i++]);
			approveTravelRequest.setEmployeeName((String) row[i++]);
			approveTravelRequest.setTravelRequestNumber((String) row[i++]);
			approveTravelRequest.setDateRequested((String) row[i++]);
			approveTravelRequest.setRequestTotal(((BigDecimal) row[i++]).setScale(2, BigDecimal.ROUND_HALF_UP));
			approveTravelRequest.setOvernight((String) row[i++]);

			approveTravelRequestList.add(approveTravelRequest);
		}

		return approveTravelRequestList;
	}

	public void updateBeaEmpTrvlStatus(String travelRequestNumber, String status) throws Exception {

		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE BEA_EMP_TRVL SET  TRVL_REQ_STAT = :status WHERE  BEA_EMP_TRVL.TRIP_NBR = :travelRequestNumber ");

			Query query = this.getSession().createSQLQuery(sql.toString());
			
			query.setParameter("status", status);

			query.setParameter("travelRequestNumber", travelRequestNumber);

			query.executeUpdate();

		} catch (Exception e) {
			String message = "Unable to update record to BEA_EMP_TRVL table";
			String errorMessage = e.getMessage() == null ? "" : e.getMessage().trim();
			logger.error((errorMessage + " " + message).trim(), e);

			throw new RuntimeException(message);
		}

	}
	
	public void isAutoAssignPaNbrEnabled(BindingResult errors) throws Exception {
		
		if(!this.getAutoAssignPaNbr()) {
			errors.rejectValue("ApproveButton", "", "Use Automatic PA Number Assignment must be selected in Finance > Tables > District Finance Options");
			throw new RuntimeException("Use Automatic PA Number Assignment must be selected in Finance > Tables > District Finance Options");
		}
		
	}
	
	public void createDistrictChecks(String empNbr ,String travelRequestNumber, String systemDts, String transDt) throws Exception {
		
		int seqNbr = 1;
		
		

		List<CheckTrans> checkTransList = getCheckTransInformation(travelRequestNumber);

		try {
			
			for (CheckTrans checkTransRecord : checkTransList) {
				
				this.verifyGeneralLedgerAccountCode( checkTransRecord.getGlFileId(),  checkTransRecord.getAccountCd());
				
				this.verifyGeneralLedgerAccountCode( checkTransRecord.getGlFileId(),  checkTransRecord.getOffsetAccountCd());
				
				this.UpdateAccountCode(checkTransRecord);
				
				this.UpdateContraAccountCode(checkTransRecord);
				
				checkTransRecord.setSeqNbr(String.format("%05d", seqNbr));
				
				checkTransRecord.setDts(systemDts);
				
				checkTransRecord.setTransDt(transDt);
				
				checkTransRecord.setPoPaNbr( this.getNextPaNbr(checkTransRecord.getGlFileId()));
				
				seqNbr ++;
				
				this.insertCheckTransRecordDB(checkTransRecord);

			}


		} catch (Exception e) {
			String message = "Unable to update record to BFN_CHK_TRANS table";
			String errorMessage = e.getMessage() == null ? "" : e.getMessage().trim();
			logger.error((errorMessage + " " + message).trim(), e);

			throw new RuntimeException(message);
		}

	}
	
	private void verifyGeneralLedgerAccountCode(String glFileId, String accountCd) throws Exception {
		// Verify the Account Code Exists in the General Ledger
		if ( !CommonBusinessUtil.isAccountCodeExistInGeneralLedger(glFileId, accountCd, new NamedParameterJdbcTemplate( getDataSource()) ) ) {
			CommonBusinessUtil.isAccountCodeComponentsValid(glFileId, accountCd, new NamedParameterJdbcTemplate( getDataSource()));
			CommonBusinessUtil.insertAccountCodeToGeneralLedger(glFileId, accountCd, new NamedParameterJdbcTemplate( getDataSource()));
		}

	}

				

	
	
	private void UpdateAccountCode(CheckTrans checkTrans) throws Exception {
		
		if (("1234").contains(checkTrans.getObjClass())) {
			CommonBusinessUtil.updateGL(checkTrans.getGlFileId(), checkTrans.getAccountCd(), BALANCE, checkTrans.getCurrentNextFlag(), checkTrans.getNetExpendAmt(), new NamedParameterJdbcTemplate( getDataSource() ));
		}
		else if (("57").contains(checkTrans.getObjClass())) {
			CommonBusinessUtil.updateGL(checkTrans.getGlFileId(), checkTrans.getAccountCd(), RLZD_REVNU, checkTrans.getCurrentNextFlag(), checkTrans.getNetExpendAmt(), new NamedParameterJdbcTemplate( getDataSource() ));
		}
		else if (("68").contains(checkTrans.getObjClass())) {
			CommonBusinessUtil.updateGL(checkTrans.getGlFileId(), checkTrans.getAccountCd(), EXPEND, checkTrans.getCurrentNextFlag(), checkTrans.getNetExpendAmt(), new NamedParameterJdbcTemplate( getDataSource() ));
		}
		
	}
	
	private void UpdateContraAccountCode(CheckTrans checkTrans) throws Exception {
		
		// Update Contra Account Code
		if (("1234").contains(checkTrans.getOffsetObjClass())) {
			CommonBusinessUtil.updateGL(checkTrans.getGlFileId(), checkTrans.getOffsetAccountCd(), BALANCE, checkTrans.getCurrentNextFlag(), checkTrans.getNetExpendAmt().negate().setScale(2, BigDecimal.ROUND_HALF_UP), new NamedParameterJdbcTemplate( getDataSource() ));
		}
		else if (("57").contains(checkTrans.getOffsetObjClass())) {
			CommonBusinessUtil.updateGL(checkTrans.getGlFileId(), checkTrans.getOffsetAccountCd(), RLZD_REVNU, checkTrans.getCurrentNextFlag(), checkTrans.getNetExpendAmt().negate().setScale(2, BigDecimal.ROUND_HALF_UP), new NamedParameterJdbcTemplate( getDataSource() ) );
		}
		else if (("68").contains(checkTrans.getOffsetObjClass())) {
			CommonBusinessUtil.updateGL(checkTrans.getGlFileId(), checkTrans.getOffsetAccountCd(), EXPEND, checkTrans.getCurrentNextFlag(), checkTrans.getNetExpendAmt().negate().setScale(2, BigDecimal.ROUND_HALF_UP), new NamedParameterJdbcTemplate( getDataSource() ));
		}
	}
	
	//**************************************************************************************************************************************
	private boolean getAutoAssignPaNbr() throws Exception {

		 boolean autoAssignPA = false;
		
		try {
			StringBuilder sql =  new StringBuilder();
			sql.append("SELECT ASSGN_PA_NBR FROM BFN_OPTIONS WHERE ( GL_FILE_ID = 'C' ) ");
			
			Query query = this.getSession().createSQLQuery(sql.toString());

			
			@SuppressWarnings("unchecked")
			List<Object[]> res = query.list();
			
			for (Object rs : res) {
				autoAssignPA =  ((Character)rs).equals('Y');
			}


			return autoAssignPA;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			String msg = e.getMessage();
			msg = (msg == null) ? "Unable to retrieve information from BFN_OPTIONS table." : msg.trim();
			throw new RuntimeException(msg);
		}
	}
	
	
	public void updateTransLock() throws Exception {
		try {
			String sql = "UPDATE TRANS_LOCK SET NEXT_KEY = ( NEXT_KEY + 1  )";
			Query query = this.getSession().createSQLQuery(sql.toString());

			query.executeUpdate();

		} catch (Exception e) {
			String message = "Unable to update record to TRANS_LOCK table";
			String errorMessage = e.getMessage() == null ? "" : e.getMessage().trim();
			logger.error((errorMessage + " " + message).trim(), e);

			throw new RuntimeException(message);
		}

		return;

	}
	
	//**************************************************************************************************************************************
	// Update those vendors that have printed checks.
	public void updateVendorTable(ApproveTravelRequest approveTravelRequest, String checkDt) throws Exception {

		try {

			StringBuilder sql =  new StringBuilder();
			sql.append("UPDATE BFN_VENDOR A SET A.DT_LAST_TRANS = :checkDt, A.DT_LAST_CHECK = :checkDt, A.MODULE = :auditModule WHERE ( A.VENDOR_NBR = :vendorNbr )");

				Query query = this.getSession().createSQLQuery(sql.toString());
				
				query.setParameter("checkDt", checkDt);
				query.setParameter("auditModule", this.auditModule);
				query.setParameter("vendorNbr", approveTravelRequest.getVendorNbr());

				query.executeUpdate();

		}catch ( Exception e ) {
			String msg = e.getMessage();
			msg = ( msg == null || msg.trim().isEmpty() ) ? "Unable to update vendor information to BFN_VENDOR table." : msg.trim();
			logger.error(e.getMessage(), e);
			throw new RuntimeException(msg);
		}
	}
	
	//**************************************************************************************************************************************
		private String getNextPaNbr(String fileId) throws Exception {
			int li_counter = 0, li_max = 999999;
			// Get Next Available PA Number.
			String paNbr = this.getNextAvailPaNbr(fileId);
			paNbr = (paNbr.equals("")) ? "000001" : String.format("%1$06d", Long.parseLong(paNbr));
			// Check if Next Available PA Nbr has already been used.
			boolean exists = this.isHasPaNbrBeenUsed(fileId, paNbr);
			// If PA Number already used, then get the next available PA Number.
			int iPaNbr = Integer.valueOf(paNbr);
			while (exists) {
				li_counter ++;
				if (li_counter > li_max) {break;} // If already looped through all the PA Numbers then exit.
				iPaNbr ++;
				if (iPaNbr > li_max) {iPaNbr = 1;}	// Start back at 1 if PA Nbr passes the Max Nbr 999999
				exists = this.isHasPaNbrBeenUsed(fileId, String.format("%1$06d", iPaNbr));
			}
			// If all PA Nbrs have been used, then throw an Error Message
			if (li_counter > li_max) {throw new Exception("All available PA Numbers have been assigned.");}
			// Increment PaNbr to update Finance
			int nextPaNbrPlus1 = (iPaNbr + 1);
			// Start back at 1 if PA Nbr passes the Max Nbr 999999
			if (nextPaNbrPlus1 > li_max) {nextPaNbrPlus1 = 1;}
			// Update Finance table with Next Available PA Number.
			this.setNextAvailPaNbr(fileId, String.format("%1$06d", nextPaNbrPlus1));
			// Return Next Available PA Nbr.
			return String.format("%1$06d", iPaNbr);
		}
		
		//**************************************************************************************************************************************
		private String getNextAvailPaNbr(String fileId) throws Exception {

			String nextAvailNumber = "";
			
			try {
				StringBuilder sql =  new StringBuilder();
				sql.append("SELECT A.NXT_AVAIL_PA_NBR FROM BFN_OPTIONS A WHERE ( A.GL_FILE_ID = :fileId )");
				
				Query query = this.getSession().createSQLQuery(sql.toString());

				query.setParameter("fileId", fileId);
				
				@SuppressWarnings("unchecked")
				List<Object[]> res = query.list();
				
				for (Object rs : res) {
					nextAvailNumber = (((BigDecimal)rs)).toString();  
				}


				return nextAvailNumber;
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				String msg = e.getMessage();
				msg = (msg == null) ? "Unable to retrieve information from BFN_OPTIONS table." : msg.trim();
				throw new RuntimeException(msg);
			}
		}
	
	//**************************************************************************************************************************************
	private boolean isHasPaNbrBeenUsed(String fileId, String paNbr) {
		int count = 0;
		try {
			
			
			// Verify Credit Card Trans PA Nbr has been used.
			StringBuilder sql =  new StringBuilder();
			sql.append("SELECT COUNT(*) FROM BFN_CRDT_CRD_TRANS A WHERE ( A.GL_FILE_ID = :fileId ) AND ( A.PO_PA_NBR = :paNbr ) AND  ( A.APPLICTN_ID = :applictnId )");
			
			Query query = this.getSession().createSQLQuery(sql.toString());

			query.setParameter("fileId", fileId);
			query.setParameter("paNbr", paNbr);
			query.setParameter("applictnId", "PACC");
			
			@SuppressWarnings("unchecked")
			List<Object[]> res = query.list();
			
			for (Object rs : res) {
				count = (Integer) rs;
			}
			
			// Verify Check Trans PA Nbr has been used.
			if (count == 0) {
				sql.delete(0, sql.length());
				sql.append("SELECT COUNT(*) FROM BFN_CHK_TRANS A WHERE ( A.GL_FILE_ID = :fileId ) AND ( A.PO_PA_NBR = :paNbr ) AND ( A.APPLICTN_ID = :applictnId )");
				query = this.getSession().createSQLQuery(sql.toString());
				query.setParameter("fileId", fileId);
				query.setParameter("paNbr", paNbr);
				query.setParameter("applictnId", "PACK");
				
				@SuppressWarnings("unchecked")
				List<Object[]> res2 = query.list();
				
				for (Object rs : res2) {
					count = (Integer) rs;
				}
			}
			return (count > 0);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			String msg = e.getMessage();
			msg = (msg == null) ? "Unable to retrieve information to determine Next Available PA Nbr." : msg.trim();
			throw new RuntimeException(msg);
		}
	}


	//**************************************************************************************************************************************
	private void setNextAvailPaNbr(String fileId, String paNbr) throws Exception {
		try {
			StringBuilder sql =  new StringBuilder();
			sql.append("UPDATE BFN_OPTIONS A SET A.NXT_AVAIL_PA_NBR = :paNbr, A.MODULE = '"+ this.auditModule +"' WHERE ( A.GL_FILE_ID = :fileId )");
			
			Query query = this.getSession().createSQLQuery(sql.toString());

			query.setParameter("fileId", fileId);
			query.setParameter("paNbr", paNbr);
			
			query.executeUpdate();
			
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			String msg = e.getMessage();
			msg = (msg == null) ? "Unable to update Next Available PA Nbr in BFN_OPTIONS table." : msg.trim();
			throw new RuntimeException(msg);
		}
	}
	
	
	private List<CheckTrans> getCheckTransInformation(String trvlNbr) throws Exception {

		StringBuffer sql = getCheckTransQuery();

		Query query = this.getSession().createSQLQuery(sql.toString());
		
		query.setParameter("trvlNbr", trvlNbr);

		@SuppressWarnings("unchecked")
		List<Object[]> queryList = query.list();

		List<CheckTrans> CheckTransList = new ArrayList<CheckTrans>();
		

		for (Object[] row : queryList) {
			CheckTrans checkTrans = new CheckTrans();
			int i = 0;
			checkTrans.setGlFileId(((Character) row[i++]).toString());
			checkTrans.setDts(((Character) row[i++]).toString());
			checkTrans.setSeqNbr(((Character) row[i++]).toString());
			checkTrans.setAcctPer((String) row[i++]);
			checkTrans.setTypeCd(((Character) row[i++]).toString());
			checkTrans.setFund((String) row[i++]);
			checkTrans.setFsclYr((String) row[i++]);
			checkTrans.setFunc((String) row[i++]);
			checkTrans.setObj((String) row[i++]);
			checkTrans.setSobj((String) row[i++]);
			checkTrans.setOrg((String) row[i++]);
			checkTrans.setPgm((String) row[i++]);
			checkTrans.setEdSpan((String) row[i++]);
			checkTrans.setProjDtl((String) row[i++]);
			checkTrans.setTransDt(((Character) row[i++]).toString());
			checkTrans.setInvoiceNbr((String) row[i++]);
			checkTrans.setPoPaNbr(((BigDecimal) row[i++]).toString());
			checkTrans.setPoPaType(((Character) row[i++]).toString());
			checkTrans.setVendorNbr((String) row[i++]);
			checkTrans.setVendorName((String) row[i++]);
			checkTrans.setCheckNbr(((Character) row[i++]).toString());
			checkTrans.setOffsetFund((String) row[i++]);
			checkTrans.setOffsetFsclYr((String) row[i++]);
			checkTrans.setOffsetFunc((String) row[i++]);
			checkTrans.setOffsetObj((String) row[i++]);
			checkTrans.setOffsetSobj((String) row[i++]);
			checkTrans.setOffsetOrg((String) row[i++]);
			checkTrans.setOffsetPgm((String) row[i++]);
			checkTrans.setOffsetEdSpan(((Character) row[i++]).toString());
			checkTrans.setOffsetProjDtl((String) row[i++]);
			checkTrans.setCheckDt(((Character) row[i++]).toString());
			checkTrans.setNetExpendAmt(((BigDecimal) row[i++]).setScale(2, BigDecimal.ROUND_HALF_UP));
			checkTrans.setLiquidatedAmt(((BigDecimal) row[i++]).setScale(2, BigDecimal.ROUND_HALF_UP));
			checkTrans.setDisburAmt(((BigDecimal) row[i++]).setScale(2, BigDecimal.ROUND_HALF_UP));
			checkTrans.setDueFromAcct(((Character) row[i++]).toString());
			checkTrans.setUserId(String.valueOf((Integer) row[i++]));
			checkTrans.setApplictnId((String) row[i++]);
			checkTrans.setProcessDt(((Character) row[i++]).toString());
			checkTrans.setTransCorrectionFlg(((Character) row[i++]).toString());
			checkTrans.setCheckVoidFlg(((Character) row[i++]).toString());
			checkTrans.setSeparatePymtFlg(((Character) row[i++]).toString());
			checkTrans.setInvoiceDt((String) row[i++]);
			checkTrans.setDueDt(((Character) row[i++]).toString());
			checkTrans.setRsn((String) row[i++]);
			checkTrans.setPrtChkFlg(((Character) row[i++]).toString());
			checkTrans.setReconInd(((Character) row[i++]).toString());
			checkTrans.setCrMemoNbr(((Character) row[i++]).toString()); //validate
			checkTrans.setBankAcctCashObj(((Character) row[i++]).toString());
			checkTrans.setBankAcctCashSobj(((Character) row[i++]).toString());
			checkTrans.setOrigDts(((Character) row[i++]).toString());
			checkTrans.setMicrCheckNbr(((Character) row[i++]).toString());
			checkTrans.setReconDts(((Character) row[i++]).toString());
			checkTrans.setEftPymtFlg(((Character) row[i++]).toString());
			checkTrans.setCrdtCrdCd(((Character) row[i++]).toString());
			checkTrans.setCrdtCrdReconInd(((Character) row[i++]).toString());
			checkTrans.setCrdtCrdReconDts(((Character) row[i++]).toString());
			checkTrans.setTripNbr((String) row[i++]);
			checkTrans.setCurrentNextFlag(((Character) row[i++]).toString());


			CheckTransList.add(checkTrans);
		}

		return CheckTransList;
	}
	
	private StringBuffer getCheckTransQuery() {
		
		StringBuffer sql = new StringBuffer();
		
		
		sql.append(" SELECT   ");
		sql.append(" GL_FILE_ID = 'C',  "); 
		sql.append(" DTS = '',  ");
		sql.append(" SEQ_NBR = '',  ");
		sql.append(" ACCT_PER = J.PER_TRVL,  ");
		sql.append(" TYP_CD = 'C',   ");
		sql.append(" TRVL_ACCNTS.FUND, ");
		sql.append(" TRVL_ACCNTS.FSCL_YR,   ");
		sql.append(" TRVL_ACCNTS.FUNC,   ");
		sql.append(" TRVL_ACCNTS.OBJ,   ");
		sql.append(" TRVL_ACCNTS.SOBJ,   ");
		sql.append(" TRVL_ACCNTS.ORG,    ");
		sql.append(" TRVL_ACCNTS.PGM,  ");
		sql.append(" TRVL_ACCNTS.ED_SPAN,  ");
		sql.append(" TRVL_ACCNTS.PROJ_DTL,   ");
		sql.append(" TRANS_DT = '', ");
		sql.append(" INVOICE_NBR = 'TRVL' || TRVL.TRIP_NBR,   ");
		sql.append(" PO_PA_NBR = J.NXT_AVAIL_PA_NBR, ");
		sql.append(" PO_PA_TYP = 'A',  ");
		sql.append(" VENDOR_NBR = TRVL.VENDOR_NBR,  ");
		sql.append(" VENDOR_NAME = (CASE WHEN (isNull(trim(C.VENDOR_NAME), '') <> '') THEN isNull(trim(C.VENDOR_NAME), '') ELSE isNull(trim(VENDOR.VENDOR_NAME), '') END),   ");
		sql.append(" CHECK_NBR = '',   ");
		sql.append(" OFFSET_FUND = TRVL_ACCNTS.FUND,  ");
		sql.append(" OFFSET_FSCL_YR = TRVL_ACCNTS.FSCL_YR,  ");
		sql.append(" OFFSET_FUNC = '00',  ");
		sql.append(" OFFSET_OBJ = H.FIN_PAYABLE_OBJ,   ");
		sql.append(" OFFSET_SOBJ = H.FIN_PAYABLE_SOBJ,    ");
		sql.append(" OFFSET_ORG = '000',   ");
		sql.append(" OFFSET_PGM = '00',    ");
		sql.append(" OFFSET_ED_SPAN = '0',   ");
		sql.append(" OFFSET_PROJ_DTL = '00',   ");
		sql.append(" CHECK_DT = '',   ");
		sql.append(" NET_EXPEND_AMT = SUM(TRVL_ACCNTS.ACCT_AMT),   ");
		sql.append(" LIQ_ENCUM_AMT = 0.00,  ");
		sql.append(" DISBUR_AMT = 0.00,   ");
		sql.append(" DUE_FROM_ACCT = '',   ");
		sql.append(" USER_ID = WFL_HIST.APPROVER_ID,  ");
		sql.append(" APPLICTN_ID = 'PACK',  ");
		sql.append(" PROCESS_DT = '',  ");
		sql.append(" TRANS_CORRECTION_FLG = 'N',   ");
		sql.append(" CHECK_VOID_FLG = '',   ");
		sql.append(" SEPARATE_PYMT_FLG = 'N',  ");
		sql.append(" INVOICE_DT = TRVL.ENTRY_DT,  ");
		sql.append(" DUE_DT = '',    ");
		sql.append(" RSN = 'TRVL REQUEST' || ' ' || TRVL.TRIP_NBR,   ");
		sql.append(" PRT_CHK_FLG = 'Y',    ");
		sql.append(" RECON_IND = '',     ");
		sql.append(" CREMO_NBR = '',    ");
		sql.append(" BANK_ACCT_CASH_OBJ = '',  ");
		sql.append(" BANK_ACCT_CASH_SOBJ = '',    ");
		sql.append(" ORIG_DTS = '',     ");
		sql.append(" MICR_CHK_NBR = '',    ");
		sql.append(" RECON_DTS = '',      "); 
		sql.append(" EFT_PYMT_FLG = ( CASE WHEN ( (isNull(trim(VENDOR_EFT.BNK_CD ), '') != '')  ");
		sql.append("                  AND  (isNull(trim(VENDOR_EFT.BNK_ACCT_NBR ), '') != '') ");
		sql.append("                  AND  (isNull(trim(VENDOR_EFT.BNK_ACCT_TYP ), '') != '') ) THEN 'Y' ELSE 'N' END ),    ");    
		sql.append(" CRDT_CRD_CD = '',    ");     
		sql.append(" CRDT_CRD_RECON_IND = '',  ");       
		sql.append(" CRDT_CRD_RECON_DTS = '',  ");       
		sql.append(" TRIP_NBR = TRVL.TRIP_NBR, ");
		sql.append(" CURRENT_NEXT_FLAG = (CASE WHEN (J.PER_CURRENT = J.PER_TRVL) THEN 'C' ELSE 'N' END ) ");

		sql.append(" FROM (SELECT DISTINCT A.TRIP_NBR, A.VENDOR_NBR, A.ENTRY_DT FROM BEA_EMP_TRVL A ) TRVL  ");
		sql.append(" LEFT OUTER JOIN BFN_ADDRESS C ON ( C.VENDOR_NBR = TRVL.VENDOR_NBR ) AND ( C.ADDRESS_TYPE_CD = 'PR' )      ");    
		sql.append(" LEFT OUTER JOIN BFN_VENDOR_EFT VENDOR_EFT ON ( VENDOR_EFT.VENDOR_NBR = TRVL.VENDOR_NBR ),    ");      
		sql.append(" BEA_EMP_TRVL_ACCTS TRVL_ACCNTS, BFN_VENDOR VENDOR, BFN_CLEAR_FUND H, ");
		sql.append(" BFN_OPTIONS  J,  (SELECT TOP 1 APPROVER_ID FROM WFL_WORKFLOW_HISTORY WHERE WORKFLOW_TYPE_ID = 14 AND REQUEST_ID = :trvlNbr ORDER BY APPROVAL_ORDER DESC) WFL_HIST ");
		sql.append(" WHERE ( TRVL.TRIP_NBR = TRVL_ACCNTS.TRIP_NBR ) AND ");
		sql.append(" ( VENDOR.VENDOR_NBR = TRVL.VENDOR_NBR ) AND ");
		sql.append(" ( H.GL_FILE_ID = 'C' ) AND ");
		sql.append(" ( J.GL_FILE_ID = 'C' ) AND ");
		sql.append(" ( H.FSCL_YR = TRVL_ACCNTS.FSCL_YR AND H.FSCL_YR = J.ACCT_YR  ) AND ");
		sql.append(" (  TRVL.TRIP_NBR = :trvlNbr ) ");

		sql.append(" GROUP BY TRIP_NBR, VENDOR_NBR, VENDOR_NAME, VENDOR.VENDOR_SORT_KEY, VENDOR.DOING_BUS_AS_NAME,VENDOR_EFT.BNK_ACCT_TYP,VENDOR_EFT.BNK_ACCT_NBR,VENDOR_EFT.BNK_CD, ");
		sql.append(" TRVL_ACCNTS.FUND, TRVL_ACCNTS.FUNC, TRVL_ACCNTS.OBJ, TRVL_ACCNTS.SOBJ, TRVL_ACCNTS.ORG, TRVL_ACCNTS.FSCL_YR, TRVL_ACCNTS.PGM, TRVL_ACCNTS.ED_SPAN, TRVL_ACCNTS.PROJ_DTL, ");
		sql.append(" OFFSET_FUND, OFFSET_FSCL_YR, OFFSET_FUNC, OFFSET_OBJ, OFFSET_SOBJ, OFFSET_ORG, OFFSET_PGM, OFFSET_ED_SPAN, OFFSET_PROJ_DTL,ACCT_PER, PO_PA_NBR, USER_ID, ENTRY_DT,CURRENT_NEXT_FLAG ");
		
		return sql;
	}
	
	private void insertCheckTransRecordDB(CheckTrans checkTransRecord) {
		
		StringBuffer sql = this.getCheckTransInsertQueryStatement();
		
		Query query = this.getSession().createSQLQuery(sql.toString());
		
		
		query.setParameter("glFileId", checkTransRecord.getGlFileId());
		query.setParameter("dts", checkTransRecord.getDts());
		query.setParameter("seqNbr", checkTransRecord.getSeqNbr());
		query.setParameter("acctPer", checkTransRecord.getAcctPer());
		query.setParameter("typCd", checkTransRecord.getTypeCd());
		query.setParameter("fund", checkTransRecord.getFund());
		query.setParameter("fsclYr", checkTransRecord.getFsclYr());
		query.setParameter("func", checkTransRecord.getFunc());
		query.setParameter("obj", checkTransRecord.getObj());
		query.setParameter("sobj", checkTransRecord.getSobj());
		query.setParameter("org", checkTransRecord.getOrg());
		query.setParameter("pgm", checkTransRecord.getPgm());
		query.setParameter("edSpan", checkTransRecord.getEdSpan());
		query.setParameter("projDtl", checkTransRecord.getProjDtl());
		query.setParameter("transDt", checkTransRecord.getTransDt());
		query.setParameter("invoiceNbr", checkTransRecord.getInvoiceNbr());
		query.setParameter("poPaNbr", checkTransRecord.getPoPaNbr());
		query.setParameter("poPaType", checkTransRecord.getPoPaType());
		query.setParameter("vendorNbr", checkTransRecord.getVendorNbr() );
		query.setParameter("vendorName", checkTransRecord.getVendorName());
		query.setParameter("checkNbr", checkTransRecord.getCheckNbr());
		query.setParameter("offsetFund", checkTransRecord.getOffsetFund());
		query.setParameter("offsetFsclYr", checkTransRecord.getOffsetFsclYr());
		query.setParameter("offsetFunc", checkTransRecord.getOffsetFunc());
		query.setParameter("offsetObj", checkTransRecord.getOffsetObj());
		query.setParameter("offsetSobj", checkTransRecord.getOffsetSobj());
		query.setParameter("offsetOrg", checkTransRecord.getOffsetOrg());
		query.setParameter("offsetPgm", checkTransRecord.getOffsetPgm());
		query.setParameter("offsetEdSpan", checkTransRecord.getOffsetEdSpan());
		query.setParameter("offsetProjDtl", checkTransRecord.getOffsetProjDtl());
		query.setParameter("checkDt", checkTransRecord.getCheckDt());
		query.setParameter("netExpendAmt", checkTransRecord.getNetExpendAmt());
		query.setParameter("liquidatedAmt", checkTransRecord.getLiquidatedAmt());
		query.setParameter("disburAmt", checkTransRecord.getDisburAmt());
		query.setParameter("dueFromAcct", checkTransRecord.getDueFromAcct());
		query.setParameter("userId", checkTransRecord.getUserId());
		query.setParameter("applictnId", checkTransRecord.getApplictnId());
		query.setParameter("processDt", checkTransRecord.getProcessDt());
		query.setParameter("transCorrectionFlg", checkTransRecord.getTransCorrectionFlg());
		query.setParameter("checkVoidFlg", checkTransRecord.getCheckVoidFlg());
		query.setParameter("separatePymtFlg", checkTransRecord.getSeparatePymtFlg());
		query.setParameter("invoiceDt", checkTransRecord.getInvoiceDt());
		query.setParameter("dueDt", checkTransRecord.getDueDt());
		query.setParameter("rsn", checkTransRecord.getRsn());
		query.setParameter("prtChkFlg", checkTransRecord.getPrtChkFlg());
		query.setParameter("reconInd", checkTransRecord.getReconInd());
		query.setParameter("crdtMemoNbr", checkTransRecord.getCrMemoNbr());
		query.setParameter("bankAcctCashObj", checkTransRecord.getBankAcctCashObj());
		query.setParameter("bankAcctCashSobj", checkTransRecord.getBankAcctCashSobj());
		query.setParameter("orig_dts", checkTransRecord.getOrigDts());
		query.setParameter("micrChkNbr", checkTransRecord.getMicrCheckNbr());
		query.setParameter("reconDts", checkTransRecord.getReconDts());
		query.setParameter("eftPymtFlg", checkTransRecord.getEftPymtFlg());
		query.setParameter("crdtCrdCd", checkTransRecord.getCrdtCrdCd());
		query.setParameter("crdtCrdReconInd", checkTransRecord.getCrdtCrdReconInd());
		query.setParameter("crdCrdReconDts", checkTransRecord.getCrdtCrdReconDts());
		query.setParameter("tripNbr", checkTransRecord.getTripNbr());
		
		query.executeUpdate();

		
	}
	
	
	private StringBuffer getCheckTransInsertQueryStatement() {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO BFN_CHK_TRANS ( GL_FILE_ID, DTS, SEQ_NBR, ACCT_PER, TYP_CD, FUND, FSCL_YR, FUNC, OBJ, SOBJ, ORG, PGM, ");
		sql.append("							ED_SPAN, PROJ_DTL, TRANS_DT, INVOICE_NBR, PO_PA_NBR, PO_PA_TYP, VENDOR_NBR, VENDOR_NAME, ");
		sql.append("							CHECK_NBR, OFFSET_FUND, OFFSET_FSCL_YR, OFFSET_FUNC, OFFSET_OBJ, OFFSET_SOBJ, OFFSET_ORG, ");
		sql.append("							OFFSET_PGM, OFFSET_ED_SPAN, OFFSET_PROJ_DTL, CHECK_DT, NET_EXPEND_AMT, LIQ_ENCUM_AMT, ");
		sql.append("							DISBUR_AMT, DUE_FROM_ACCT, USER_ID, APPLICTN_ID, PROCESS_DT, TRANS_CORRECTION_FLG, ");
		sql.append("							CHECK_VOID_FLG, SEPARATE_PYMT_FLG, INVOICE_DT, DUE_DT, RSN, PRT_CHK_FLG, RECON_IND, ");
		sql.append("							CREMO_NBR, BANK_ACCT_CASH_OBJ, BANK_ACCT_CASH_SOBJ, ORIG_DTS, MICR_CHK_NBR, RECON_DTS, ");
		sql.append("							EFT_PYMT_FLG, CRDT_CRD_CD, CRDT_CRD_RECON_IND, CRDT_CRD_RECON_DTS, TRIP_NBR ) ");

		sql.append("VALUES( :glFileId, :dts, :seqNbr, :acctPer, :typCd, :fund, :fsclYr, :func, :obj, :sobj, :org, :pgm, ");
		sql.append("		:edSpan, :projDtl, :transDt, :invoiceNbr, :poPaNbr, :poPaType,  :vendorNbr, :vendorName, ");
		sql.append("		:checkNbr, :offsetFund, :offsetFsclYr, :offsetFunc, :offsetObj, :offsetSobj, :offsetOrg, ");
		sql.append("		:offsetPgm, :offsetEdSpan, :offsetProjDtl, :checkDt, :netExpendAmt, :liquidatedAmt, ");
		sql.append("		:disburAmt, :dueFromAcct, :userId, :applictnId, :processDt, :transCorrectionFlg, ");
		sql.append("		:checkVoidFlg, :separatePymtFlg, :invoiceDt, :dueDt, :rsn, :prtChkFlg, :reconInd, ");
		sql.append("		:crdtMemoNbr, :bankAcctCashObj, :bankAcctCashSobj, :orig_dts, :micrChkNbr, :reconDts, ");
		sql.append("		:eftPymtFlg, :crdtCrdCd, :crdtCrdReconInd, :crdCrdReconDts, :tripNbr )");
		
		return sql;
	}
	


}
