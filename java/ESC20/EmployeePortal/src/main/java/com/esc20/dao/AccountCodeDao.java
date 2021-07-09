package com.esc20.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;

import com.esc20.model.BeaEmpTrvlAcct;
import com.esc20.nonDBModels.Fund;

@Transactional (readOnly = true)

@Repository
public class AccountCodeDao {
	private Logger logger = LoggerFactory.getLogger(AccountCodeDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private static final String getWhereQuery() {
		StringBuffer sql = new StringBuffer();
		sql.append("BFN_GL A, ( SELECT DISTINCT C.FUND, C.FSCL_YR, C.FUNC, C.OBJ, C.SOBJ, C.ORG, C.PGM, C.ED_SPAN, C.PROJ_DTL "); 
		sql.append(			   "FROM BFN_OPTIONS B, BFN_GL C, WFL_USER_ACCOUNTS D, WFL_USER_ACCOUNTS_APPS E ");
		sql.append(			   "WHERE ( B.GL_FILE_ID = :fileId ) AND ");
		sql.append(					 "( C.ACTIVE_ID = 'A' ) AND ");
		sql.append(		 			 "( C.GL_FILE_ID = B.GL_FILE_ID ) AND ");
		sql.append(		 			 "( D.EMP_NBR = :empNbr ) AND ");
		sql.append(		 			 "( E.USER_ACCOUNT_ID = D.USER_ACCOUNT_ID ) AND ");
		sql.append(		 			 "( E.APP_ID = 'TRVL' ) AND ");

		sql.append(					 "( ( ( ( ( LOCATE((D.FUND || D.FUNC || D.OBJ || D.SOBJ || D.ORG || (CASE C.GL_FILE_ID WHEN 'C' THEN D.FSCL_YR ELSE 'X' END) || D.PGM || D.ED_SPAN || D.PROJ_DTL), 'X') = 0 ) AND ");
		sql.append(					 "( C.FUND = D.FUND ) AND ");
		sql.append(					 "( C.FSCL_YR = D.FSCL_YR ) AND ");
		sql.append(					 "( C.FUNC = D.FUNC ) AND ");
		sql.append(					 "( C.OBJ = D.OBJ ) AND ");
		sql.append(					 "( C.SOBJ = D.SOBJ ) AND ");
		sql.append(					 "( C.ORG = D.ORG ) AND ");
		sql.append(					 "( C.PGM = D.PGM ) AND ");
		sql.append(					 "( C.ED_SPAN = D.ED_SPAN ) AND ");
		sql.append(					 "( C.PROJ_DTL = D.PROJ_DTL ) ) OR ");
		sql.append(					 "( ( LOCATE((D.FUND || D.FUNC || D.OBJ || D.SOBJ || D.ORG || (CASE C.GL_FILE_ID WHEN 'C' THEN D.FSCL_YR ELSE 'X' END) || D.PGM || D.ED_SPAN || D.PROJ_DTL), 'X') > 0 ) AND ");
		sql.append(					 "( (C.FUND || C.FUNC || C.OBJ || C.SOBJ || C.ORG || C.FSCL_YR || C.PGM || C.ED_SPAN || C.PROJ_DTL) LIKE REPLACE((D.FUND || D.FUNC || D.OBJ || D.SOBJ || D.ORG || (CASE C.GL_FILE_ID WHEN 'C' THEN D.FSCL_YR ELSE 'X' END) || D.PGM || D.ED_SPAN || D.PROJ_DTL), 'X', '_') ) ) ) AND ");
		sql.append(					 "( 1 = (CASE WHEN (isNull(trim(B.ACTFND), '') = '') THEN 1 WHEN ((isNull(trim(B.ACTFND), '') <> '') AND (C.FUND <> B.ACTFND)) THEN 1 ELSE 2 END ) ) AND ");
		sql.append(					 "( 1 = (CASE WHEN (isNull(trim(B.PO_OBJ_CD_RESTRICT), '') = 'R') AND (SUBSTRING(C.OBJ, 1, 2) IN ('62', '63', '64', '66')) THEN 1 ");
		sql.append(					 			 "WHEN (isNull(trim(B.PO_OBJ_CD_RESTRICT), '') = 'E') AND (SUBSTRING(C.OBJ, 1, 1) IN ('6', '8')) THEN 1 ");
		sql.append(					 			 "WHEN (isNull(trim(B.PO_OBJ_CD_RESTRICT), '') = 'A') AND (SUBSTRING(C.OBJ, 1, 1) IN ('1', '2', '6', '8')) THEN 1 ");
		sql.append(					 			 "WHEN (isNull(trim(B.PO_OBJ_CD_RESTRICT), '') = 'C') AND ((SUBSTRING(C.OBJ, 1, 1) IN ('1', '2')) OR (SUBSTRING(C.OBJ, 1, 2) IN ('62', '63', '64', '66'))) THEN 1 ");
		sql.append(					 			 "ELSE 2 END) ) ) OR ");
		
		sql.append(					 "( ( ( ( LOCATE((D.FUND || D.FUNC || D.OBJ || D.SOBJ || D.ORG || ( CASE C.GL_FILE_ID WHEN 'C' THEN D.FSCL_YR ELSE 'X' END ) || D.PGM || D.ED_SPAN || D.PROJ_DTL), 'X') = 0 ) AND ");
		sql.append(					 "( C.FUND = D.FUND ) AND ");
		sql.append(					 "( C.FSCL_YR = D.FSCL_YR ) AND ");
		sql.append(					 "( C.FUNC = D.FUNC ) AND ");
		sql.append(					 "( C.OBJ = D.OBJ ) AND ");
		sql.append(					 "( C.SOBJ = D.SOBJ ) AND ");
		sql.append(					 "( C.ORG = D.ORG ) AND ");
		sql.append(					 "( C.PGM = D.PGM ) AND ");
		sql.append(					 "( C.ED_SPAN = D.ED_SPAN ) AND ");
		sql.append(					 "( C.PROJ_DTL = D.PROJ_DTL ) ) OR ");
		sql.append(					 "( ( LOCATE((D.FUND || D.FUNC || D.OBJ || D.SOBJ || D.ORG || (CASE C.GL_FILE_ID WHEN 'C' THEN D.FSCL_YR ELSE 'X' END) || D.PGM || D.ED_SPAN || D.PROJ_DTL), 'X') > 0 ) AND ");
		sql.append(					 "( (C.FUND || C.FUNC || C.OBJ || C.SOBJ || C.ORG || C.FSCL_YR || C.PGM || C.ED_SPAN || C.PROJ_DTL) LIKE REPLACE((D.FUND || D.FUNC || D.OBJ || D.SOBJ || D.ORG || (CASE C.GL_FILE_ID WHEN 'C' THEN D.FSCL_YR ELSE 'X' END) || D.PGM || D.ED_SPAN || D.PROJ_DTL), 'X', '_') ) ) ) AND ");
		sql.append(					 "( SUBSTRING(C.OBJ, 1, 1) = '2' ) AND ");
		sql.append(					 "( 1 = (CASE WHEN ( isNull(trim(B.ACTFND), '') <> '' ) AND ( C.FUND = B.ACTFND ) THEN 1 ELSE 2 END ) ) AND ");
		sql.append(					 "( 1 = (CASE WHEN ( isNull(trim(B.ACTFND_YR), '') IN ('', 'X') ) THEN 1 ");
		sql.append(					 			 "WHEN ( isNull(trim(B.ACTFND_YR), '') NOT IN ('', 'X') ) AND ( C.FSCL_YR = B.ACTFND_YR ) THEN 1 ELSE 2 END ) ) ) ) ");
		sql.append(				"ORDER BY C.FUND ASC, C.FUNC ASC, C.FSCL_YR ASC, C.OBJ ASC, C.SOBJ ASC, C.ORG ASC, C.PGM ASC, C.ED_SPAN ASC, C.PROJ_DTL ASC ) USER_PROFILE ");
		sql.append("WHERE ");
		sql.append(		"( A.GL_FILE_ID = :fileId ) AND ");
		sql.append(		"( A.FUND = USER_PROFILE.FUND ) AND ");
		sql.append(		"( A.FSCL_YR = USER_PROFILE.FSCL_YR ) AND ");
		sql.append(		"( A.FUNC = USER_PROFILE.FUNC ) AND ");
		sql.append(		"( A.OBJ = USER_PROFILE.OBJ ) AND ");
		sql.append(		"( A.SOBJ = USER_PROFILE.SOBJ ) AND ");
		sql.append(		"( A.ORG = USER_PROFILE.ORG ) AND ");
		sql.append(		"( A.PGM = USER_PROFILE.PGM ) AND ");
		sql.append(		"( A.ED_SPAN = USER_PROFILE.ED_SPAN ) AND ");
		sql.append(		"( A.PROJ_DTL = USER_PROFILE.PROJ_DTL ) ");
		
		return sql.toString();
	}

	//********************************************************************************************************************
	public List<Fund> getAccountCodeAutoComplete(String fileId, String empNbr, String search) {
		List<Fund> result = new ArrayList<>();
		try {
			
			final String SELECT_FUND_BY_FILEID = "SELECT DISTINCT TOP 20 ACCOUNT_CODE = A.FUND || '-' || A.FUNC || '-' || A.OBJ || '.' || A.SOBJ || '-' || A.ORG || '-' || A.FSCL_YR || A.PGM || A.ED_SPAN || A.PROJ_DTL, A.DESCR FROM ";
			StringBuffer sql = new StringBuffer(SELECT_FUND_BY_FILEID + getWhereQuery());
			sql.append("AND ( ( A.FUND || A.FUNC || A.OBJ || A.SOBJ || A.ORG || A.FSCL_YR || A.PGM || A.ED_SPAN || A.PROJ_DTL ) LIKE :search ) ");
			sql.append("ORDER BY ACCOUNT_CODE ASC");

			// Remove standard account code formatting characters.
			search = (search == null) ? "" : search.trim();
			search = search.replace("-", "");
			search = search.replace(".", "");
			search = search.trim() + "%";

			Session session = this.getSession();
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("fileId", fileId);
			q.setParameter("empNbr", empNbr);
			q.setParameter("search", search);

			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object[] i : res) {
				Fund fund = new Fund();
				fund.setFund((String) i[0].toString());
				fund.setDescription((String) i[1].toString());
				result.add(fund);
			}
		}
		catch (Exception e) {
			logger.error("Error occurred on while getting fund information from getFundInformation method :", e.getMessage());
		}
		return result;
	}

	//********************************************************************************************************************
	public List<Fund> getFundInformation(String fileId, String empNbr) {
		List<Fund> result = new ArrayList<>();
		try {
			
			final String SELECT_FUND_BY_FILEID = "SELECT DISTINCT FUND.FUND, FUND.FSCL_YR, FUND.FUND_DESCR FROM BTFN_FUND FUND, ";
			StringBuffer sql = new StringBuffer(SELECT_FUND_BY_FILEID + getWhereQuery());
			sql.append("AND ( FUND.FUND = A.FUND ) AND ( FUND.FSCL_YR = A.FSCL_YR ) ORDER BY FUND.FUND ASC, FUND.FSCL_YR ASC");

			Session session = this.getSession();
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("fileId", fileId);
			q.setParameter("empNbr", empNbr);

			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object[] i : res) {
				Fund fund = new Fund();
				fund.setFund((String) i[0].toString());
				fund.setFiscalYr((String) i[1].toString());
				fund.setDescription((String) i[2].toString());
				result.add(fund);
			}
		}
		catch (Exception e) {
			logger.error("Error occurred on while getting fund information from getFundInformation method :", e.getMessage());
		}
		return result;
	}

	//********************************************************************************************************************
	public List<Fund> getFunctionInformation(String fileId, String empNbr) {
		List<Fund> result = new ArrayList<>();
		try {
			
			final String SELECT_FUNCTION_BY_FILEID = "SELECT DISTINCT FUNC.FUNC, FUNC.FUNC_DESCR FROM BTFN_FUNC FUNC, ";
			StringBuffer sql = new StringBuffer(SELECT_FUNCTION_BY_FILEID + getWhereQuery());
			sql.append("AND ( FUNC.FUNC = A.FUNC ) ORDER BY FUNC.FUNC ASC");

			Session session = this.getSession();
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("fileId", fileId);
			q.setParameter("empNbr", empNbr);

			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object[] i : res) {
				Fund fund = new Fund();
				fund.setFund((String) i[0].toString());
				fund.setDescription((String) i[1].toString());
				result.add(fund);
			}
		}
		catch (Exception e) {
			logger.error("Error occurred on while getting function information from getFunctionInformation method :", e.getMessage());
		}
		return result;
	}

	//********************************************************************************************************************
	public List<Fund> getObjectInformation(String fileId, String empNbr) {
		List<Fund> result = new ArrayList<>();
		try {
			final String SELECT_OBJECT_BY_FILEID = "SELECT DISTINCT OBJ.OBJ, OBJ.OBJ_DESCR FROM BTFN_OBJ OBJ, ";
			StringBuffer sql = new StringBuffer(SELECT_OBJECT_BY_FILEID + getWhereQuery());
			sql.append("AND ( OBJ.OBJ = A.OBJ ) ORDER BY OBJ.OBJ ASC");

			Session session = this.getSession();
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("fileId", fileId);
			q.setParameter("empNbr", empNbr);

			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object[] i : res) {
				Fund fund = new Fund();
				fund.setFund((String) i[0].toString());
				fund.setDescription((String) i[1].toString());
				result.add(fund);
			}
		}
		catch (Exception e) {
			logger.error("Error occurred on while getting function information from getFunctionInformation method :", e.getMessage());
		}
		return result;
	}

	//********************************************************************************************************************
	public List<Fund> getSubobjectInformation(String fileId, String empNbr) {
		List<Fund> result = new ArrayList<>();
		try {
			final String SELECT_SUBOBJECT_BY_FILEID = "SELECT DISTINCT SOBJ.SOBJ, SOBJ.SOBJ_DESCR FROM BTFN_SOBJ SOBJ, ";
			StringBuffer sql = new StringBuffer(SELECT_SUBOBJECT_BY_FILEID + getWhereQuery());
			sql.append("AND ( SOBJ.SOBJ = A.SOBJ ) ORDER BY SOBJ.SOBJ ASC");

			Session session = this.getSession();
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("fileId", fileId);
			q.setParameter("empNbr", empNbr);

			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object[] i : res) {
				Fund fund = new Fund();
				fund.setFund((String) i[0].toString());
				fund.setDescription((String) i[1].toString());
				result.add(fund);
			}
		}
		catch (Exception e) {
			logger.error("Error occurred on while getting subject by file id information from getSubobjectInformation method :", e.getMessage());
		}
		return result;
	}

	//********************************************************************************************************************
	public List<Fund> getOrganizationInformation(String fileId, String empNbr) {
		List<Fund> result = new ArrayList<>();
		try {
			final String SELECT_ORGANIZATION_BY_FILEID = "SELECT DISTINCT ORG.ORG, ORG.ORG_DESCR FROM BTFN_ORG ORG, ";
			StringBuffer sql = new StringBuffer(SELECT_ORGANIZATION_BY_FILEID + getWhereQuery());
			sql.append("AND ( ORG.ORG = A.ORG ) ORDER BY ORG.ORG ASC");

			Session session = this.getSession();
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("fileId", fileId);
			q.setParameter("empNbr", empNbr);

			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object[] i : res) {
				Fund fund = new Fund();
				fund.setFund((String) i[0].toString());
				fund.setDescription((String) i[1].toString());
				result.add(fund);
			}
		}
		catch (Exception e) {
			logger.error("Error occurred on while getting organization by file id information from getOrganizationInformation method :", e.getMessage());
		}
		return result;
	}

	//********************************************************************************************************************
	public List<Fund> getProgramInformation(String fileId, String empNbr) {
		List<Fund> result = new ArrayList<>();
		try {
			final String SELECT_PROGRAM_BY_FILEID = "SELECT DISTINCT PGM.PGM, PGM.PGM_DESCR FROM BTFN_PGM PGM, ";
			StringBuffer sql = new StringBuffer(SELECT_PROGRAM_BY_FILEID + getWhereQuery());
			sql.append("AND ( PGM.PGM = A.PGM ) ORDER BY PGM.PGM ASC");

			Session session = this.getSession();
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("fileId", fileId);
			q.setParameter("empNbr", empNbr);

			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object[] i : res) {
				Fund fund = new Fund();
				fund.setFund((String) i[0].toString());
				fund.setDescription((String) i[1].toString());
				result.add(fund);
			}
		}
		catch (Exception e) {
			logger.error("Error occurred on while getting program by file id information from getProgramInformation method :", e.getMessage());
		}
		return result;
	}

	//********************************************************************************************************************
	public List<Fund> getEducationalSpanInformation(String fileId, String empNbr) {
		List<Fund> result = new ArrayList<>();
		try {
			final String SELECT_EDUCATIONALSPAN_BY_FILEID = "SELECT DISTINCT ED_SPAN.ED_SPAN, ED_SPAN.ED_SPAN_DESCR FROM BTFN_ED_SPAN ED_SPAN, ";
			StringBuffer sql = new StringBuffer(SELECT_EDUCATIONALSPAN_BY_FILEID + getWhereQuery());
			sql.append("AND ( ED_SPAN.ED_SPAN = A.ED_SPAN ) ORDER BY ED_SPAN.ED_SPAN ASC");

			Session session = this.getSession();
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("fileId", fileId);
			q.setParameter("empNbr", empNbr);

			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object[] i : res) {
				Fund fund = new Fund();
				fund.setFund((String) i[0].toString());
				fund.setDescription((String) i[1].toString());
				result.add(fund);
			}
		}
		catch (Exception e) {
			logger.error("Error occurred on while getting educational span by file id from getEducationalSpanInformation method :", e.getMessage());
		}
		return result;
	}

	//********************************************************************************************************************
	public List<Fund> getProjectDetailInformation(String fileId, String empNbr) {
		List<Fund> result = new ArrayList<>();
		try {
			final String SELECT_PROJECTDETAIL_BY_FILEID = "SELECT DISTINCT PROJ_DTL.PROJ_DTL, PROJ_DTL.PROJ_DESCR FROM BTFN_PROJ_DTL PROJ_DTL, ";
			StringBuffer sql = new StringBuffer(SELECT_PROJECTDETAIL_BY_FILEID + getWhereQuery());
			sql.append("AND ( PROJ_DTL.PROJ_DTL = A.PROJ_DTL ) ORDER BY PROJ_DTL.PROJ_DTL ASC");

			Session session = this.getSession();
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("fileId", fileId);
			q.setParameter("empNbr", empNbr);

			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object[] i : res) {
				Fund fund = new Fund();
				fund.setFund((String) i[0].toString());
				fund.setDescription((String) i[1].toString());
				result.add(fund);
			}
		}
		catch (Exception e) {
			logger.error("Error occurred on while getting project detail by file id from getProjectDetailInformation method :", e.getMessage());
		}
		return result;
	}

	//********************************************************************************************************************
	@Transactional (propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public void saveAccountCodes(List<BeaEmpTrvlAcct> beaEmpTrvlAccts) {
		try {
			for (BeaEmpTrvlAcct request : beaEmpTrvlAccts) {
				Session session = this.getSession();
				session.beginTransaction();
				session.saveOrUpdate(request);
				session.flush();
			}
		} catch (Exception e) {
			logger.error("Error in while saving account codes on saveAccountCodes method :", e.getMessage());
		}
	}

	//********************************************************************************************************************
	public List<Map<String, String>> getAccountCodes(String empNbr) {
		List<Map<String, String>> result = new ArrayList<>();
		try {
			final String fileId = "C";
			final String SELECT_ACCOUNTCODE_INFO = "SELECT DISTINCT A.FUND, A.FUNC, A.OBJ, A.SOBJ, A.ORG, A.FSCL_YR, A.PGM, A.ED_SPAN, A.PROJ_DTL, C_ACCOUNT_CODE = ( A.FUND || '-' || A.FUNC || '-' || A.OBJ || '.' || A.SOBJ || '-' || A.ORG || '-' || A.FSCL_YR || A.PGM || A.ED_SPAN || A.PROJ_DTL ), A.DESCR FROM ";
			StringBuffer sql = new StringBuffer(SELECT_ACCOUNTCODE_INFO + getWhereQuery());
			sql.append("ORDER BY A.FUND ASC, A.FUNC ASC, A.OBJ ASC, A.SOBJ ASC, A.ORG ASC, A.FSCL_YR ASC, A.PGM ASC, A.ED_SPAN ASC, A.PROJ_DTL ASC");

			Session session = this.getSession();
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("fileId", fileId);
			q.setParameter("empNbr", empNbr);

			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object[] i : res) {
				Map<String, String> map = new HashMap<>();
				map.put("fund", (String) i[0].toString());
				map.put("func", (String) i[1].toString());
				map.put("obj", (String) i[2].toString());
				map.put("sobj", (String) i[3].toString());
				map.put("org", (String) i[4].toString());
				map.put("fscl_yr", (String) i[5].toString());
				map.put("pgm", (String) i[6].toString());
				map.put("ed_span", (String) i[7].toString());
				map.put("proj_dtl", (String) i[8].toString());
				map.put("c_account_code", (String) i[9].toString());
				map.put("descr", (String) i[10].toString());
				result.add(map);
			}
		}
		catch (Exception e) {
			logger.error("Error in persisting account codes from getAccountCodes method :", e.getMessage());
		}
		return result;
	}

	//********************************************************************************************************************
	public List<Map<String, String>> getAccountCodesTrip(String employeeNumber, String tripNbr) {
		List<Map<String, String>> result = new ArrayList<>();
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT T.EMP_NBR, A.TRIP_NBR, A.TRIP_SEQ_NBR, A.DISTR_SEQ_NBR, A.FUND, A.FUNC, A.OBJ, A.SOBJ, A.ORG, A.FSCL_YR, A.PGM, A.ED_SPAN, A.PROJ_DTL, A.ACCT_AMT, A.ACCT_PCT ");
			sql.append("FROM BEA_EMP_TRVL_ACCTS A, BEA_EMP_TRVL T ");
			sql.append("WHERE A.TRIP_NBR=T.TRIP_NBR ");
			sql.append("AND A.TRIP_SEQ_NBR = T.TRIP_SEQ_NBR ");
			sql.append("AND A.TRIP_NBR = :tripNbr ");
			sql.append("AND T.EMP_NBR = :employeeNumber");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("tripNbr", tripNbr);
			q.setParameter("employeeNumber", employeeNumber);
			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object[] i : res) {
				Map<String, String> map = new HashMap<>();
				map.put("tripNbr", (String) i[1].toString());
				map.put("tripSeqNbr", (String) i[2].toString());
				map.put("distrSeqNbr", (String) i[3].toString());
				map.put("fund", (String) i[4].toString());
				map.put("func", (String) i[5].toString());
				map.put("obj", (String) i[6].toString());
				map.put("sobj", (String) i[7].toString());
				map.put("org", (String) i[8].toString());
				map.put("fscl_yr", (String) i[9].toString());
				map.put("pgm", (String) i[10].toString());
				map.put("ed_span", (String) i[11].toString());
				map.put("proj_dtl", (String) i[12].toString());
				map.put("acct_amt", (String) i[13].toString());
				map.put("acct_pct", (String) i[14].toString());
				result.add(map);
			}
		}
		catch (Exception e) {
			logger.error("Error in persisting getting account codes information from getAccountCodesTrip method :", e.getMessage());
		}
		return result;
	}

	//********************************************************************************************************************
	public List<Map<String, String>> getAccountCodesTripSeqWise(String employeeNumber, String tripNbr,
			Long tripSeqNbr) {
		List<Map<String, String>> result = new ArrayList<>();
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT T.EMP_NBR, A.TRIP_NBR, A.TRIP_SEQ_NBR, A.DISTR_SEQ_NBR, A.FUND, A.FUNC, A.OBJ, A.SOBJ, A.ORG, A.FSCL_YR, A.PGM, A.ED_SPAN, A.PROJ_DTL, A.ACCT_AMT, A.ACCT_PCT ");
			sql.append("FROM BEA_EMP_TRVL_ACCTS A, BEA_EMP_TRVL T ");
			sql.append("WHERE A.TRIP_NBR=T.TRIP_NBR ");
			sql.append("AND A.TRIP_SEQ_NBR = T.TRIP_SEQ_NBR ");
			sql.append("AND A.TRIP_SEQ_NBR = :tripSeqNbr ");
			sql.append("AND A.TRIP_NBR = :tripNbr ");
			sql.append("AND T.EMP_NBR = :employeeNumber");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("tripSeqNbr", tripSeqNbr);
			q.setParameter("tripNbr", tripNbr);
			q.setParameter("employeeNumber", employeeNumber);
			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object[] i : res) {
				Map<String, String> map = new HashMap<>();
				map.put("tripNbr", (String) i[1].toString());
				map.put("tripSeqNbr", (String) i[2].toString());
				map.put("distrSeqNbr", (String) i[3].toString());
				map.put("fund", (String) i[4].toString());
				map.put("func", (String) i[5].toString());
				map.put("obj", (String) i[6].toString());
				map.put("sobj", (String) i[7].toString());
				map.put("org", (String) i[8].toString());
				map.put("fscl_yr", (String) i[9].toString());
				map.put("pgm", (String) i[10].toString());
				map.put("ed_span", (String) i[11].toString());
				map.put("proj_dtl", (String) i[12].toString());
				map.put("acct_amt", (String) i[13].toString());
				map.put("acct_pct", (String) i[14].toString());
				result.add(map);
			}
		} catch (Exception e) {
			logger.error("Error in persisting getting account codes trip wise from getAccountCodesTripSeqWise method :",
					e.getMessage());
		}
		return result;
	}

	//********************************************************************************************************************
	public String getAccountDescTrip(String fund, String func, String obj, String sobj, String org, String fscl_yr,
			String pgm, String ed_span, String proj_dtl) {
		String accCodeDesc = "";
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DESCR ");
			sql.append("FROM BFN_GL ");
			sql.append("WHERE FUND= :fund ");
			sql.append("AND FUNC = :func ");
			sql.append("AND OBJ = :obj ");
			sql.append("AND SOBJ = :sobj ");
			sql.append("AND ORG = :org ");
			sql.append("AND FSCL_YR = :fscl_yr ");
			sql.append("AND PGM = :pgm ");
			sql.append("AND ED_SPAN = :ed_span ");
			sql.append("AND PROJ_DTL = :proj_dtl");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("fund", fund);
			q.setParameter("func", func);
			q.setParameter("obj", obj);
			q.setParameter("sobj", sobj);
			q.setParameter("org", org);
			q.setParameter("fscl_yr", fscl_yr);
			q.setParameter("pgm", pgm);
			q.setParameter("ed_span", ed_span);
			q.setParameter("proj_dtl", proj_dtl);
			accCodeDesc = (String) q.uniqueResult();
		} catch (Exception e) {
			logger.error("Error in persisting getting account codes description from getAccountDescTrip method :",
					e.getMessage());
		}
		return accCodeDesc;
	}

	//********************************************************************************************************************
	public boolean accountCodeExists(String accountCd, String fileId, String empNbr) {
		StringBuffer sql = new StringBuffer( "SELECT COUNT(*) FROM " + getWhereQuery());
		sql.append("AND ((A.FUND || A.FUNC || A.OBJ || A.SOBJ || A.ORG || A.FSCL_YR || A.PGM || A.ED_SPAN || A.PROJ_DTL) = :accountCd)");
		Session session = this.getSession();
		int result = 0;
		try {
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("accountCd", accountCd);
			q.setParameter("fileId", fileId);
			q.setParameter("empNbr", empNbr);
			result = (int) q.uniqueResult();
		}
		catch (DataAccessException e) {
			logger.error("Error in persisting getting account accountCodeExists method :",
					e.getMessage());
			return false;
		}
		return result > 0 ? true : false;
	}
}