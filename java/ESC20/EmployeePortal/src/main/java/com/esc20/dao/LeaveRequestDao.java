package com.esc20.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BeaEmpLvComments;
import com.esc20.model.BeaEmpLvRqst;
import com.esc20.model.BeaEmpLvWorkflow;
import com.esc20.model.BhrEmpLvXmital;
import com.esc20.model.BhrPmisPosCtrl;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.LeaveEmployeeData;
import com.esc20.nonDBModels.LeaveInfo;
import com.esc20.nonDBModels.LeaveParameters;
import com.esc20.nonDBModels.LeaveRequest;
import com.esc20.nonDBModels.LeaveRequestComment;
import com.esc20.nonDBModels.LeaveUnitsConversion;
import com.esc20.util.StringUtil;

@Repository
public class LeaveRequestDao {
	private Logger logger = LoggerFactory.getLogger(LeaveRequestDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public BeaEmpLvRqst getleaveRequestById(int id) {
		Session session = this.getSession();
		String hql = "from BeaEmpLvRqst where id = ? ";
		Query q = session.createQuery(hql);
		q.setParameter(0, id);
		@SuppressWarnings("unchecked")
		List<BeaEmpLvRqst> res = q.list();

		return res.get(0);
	}

	public List<AppLeaveRequest> getLeaveRequests(AppLeaveRequest request, String empNbr, String freq) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder("");
		sql.append(
				"SELECT ELR.id, ELR.lvTyp, ELR.absRsn, ELR.datetimeSubmitted, ELR.datetimeFrom, ELR.datetimeTo, ELR.lvUnitsDaily, ");
		sql.append("  ELR.lvUnitsUsed, ELR.statusCd, ELSC.descr, ''," + "LT.daysHrs, LTD.longDescr, AR.absDescr ");
		sql.append("FROM BeaEmpLvRqst ELR, ");
		sql.append("  BteaEmpLvStatusCodes ELSC, BthrLvTyp LT, BthrLvTypDescr LTD, BthrAbsRsn AR ");
		sql.append("where ELR.empNbr=:employeeNumber "
				+ "AND ELR.payFreq=:payFrequency AND ELR.lvTyp=LT.id.lvTyp AND LT.stat='A' AND ELR.statusCd=ELSC.cd ");
		sql.append(
				"  AND LT.id.payFreq=:payFrequency AND ELR.lvTyp=LTD.lvTyp AND LTD.stat='A' AND ELR.absRsn=AR.absRsn AND AR.stat='A' ");
		sql.append(
				"  AND (ELR.statusCd IN ('A','P','L') OR (ELR.statusCd IN ('D') AND DAYS(ELR.datetimeSubmitted, GETDATE()) <=60)) ");
		if (request.getLvTyp() != null && !request.getLvTyp().equals(""))
			sql.append(" and ELR.lvTyp = :type ");
		if (request.getDatetimeFrom() != null)
			sql.append(" and ELR.datetimeFrom >= :startDate ");
		if (request.getDatetimeTo() != null)
			sql.append(" and ELR.datetimeTo <= :endDate ");
		sql.append("ORDER BY ELR.datetimeFrom DESC");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		q.setParameter("payFrequency", freq.charAt(0));
		if (request.getLvTyp() != null && !request.getLvTyp().equals(""))
			q.setParameter("type", request.getLvTyp());
		if (request.getDatetimeFrom() != null) {
			q.setParameter("startDate", request.getDatetimeFrom());
		}
		if (request.getDatetimeTo() != null) {
			q.setParameter("endDate", request.getDatetimeTo());
		}
		List<AppLeaveRequest> result = new ArrayList<AppLeaveRequest>();
		AppLeaveRequest req;
		@SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
		for (Object[] item : res) {
			req = new AppLeaveRequest((Integer) item[0], (String) item[1], (String) item[2], (Date) item[3],
					(Date) item[4], (Date) item[5], (BigDecimal) item[6], (BigDecimal) item[7], (Character) item[8],
					(String) item[9], (String) item[10], (String) item[11], (String) item[12], (String) item[13]);
			result.add(req);
		}

		return result;
	}

	public Integer saveLeaveRequest(BeaEmpLvRqst request, boolean isUpdate) {
		Session session = this.getSession();
		try {
			if (isUpdate) {
				String sql = "Update BEA_EMP_LV_RQST set PAY_FREQ=:payFreq, EMP_NBR=:empNbr, DATETIME_SUBMITTED=:datetimeSubmitted, "
						+ "DATETIME_FROM =:datetimeFrom, DATETIME_TO=:datetimeTo, LV_UNITS_DAILY=:lvUnitsDaily, LV_UNITS_USED=:lvUnitsUsed, "
						+ "LV_TYP=:lvTyp,ABS_RSN=:absRsn,STATUS_CD=:statusCd,DT_OF_PAY=:dtOfPay where ID=:id";
				Query q = session.createSQLQuery(sql);
				q.setParameter("payFreq", request.getPayFreq());
				q.setParameter("empNbr", request.getEmpNbr());
				q.setParameter("datetimeSubmitted", request.getDatetimeSubmitted());
				q.setParameter("datetimeFrom", request.getDatetimeFrom());
				q.setParameter("datetimeTo", request.getDatetimeTo());
				q.setParameter("lvUnitsDaily", request.getLvUnitsDaily());
				q.setParameter("lvUnitsUsed", request.getLvUnitsUsed());
				q.setParameter("lvTyp", request.getLvTyp());
				q.setParameter("absRsn", request.getAbsRsn());
				q.setParameter("statusCd", request.getStatusCd());
				q.setParameter("dtOfPay", request.getDtOfPay());
				q.setParameter("id", request.getId());
				q.executeUpdate();
				return request.getId();
			} else {
				String sql = "INSERT INTO BEA_EMP_LV_RQST (PAY_FREQ, EMP_NBR, DATETIME_SUBMITTED, DATETIME_FROM, DATETIME_TO, LV_UNITS_DAILY, LV_UNITS_USED, LV_TYP,ABS_RSN,STATUS_CD,DT_OF_PAY)"
						+ " VALUES (:payFreq, :empNbr, :datetimeSubmitted, :datetimeFrom, :datetimeTo, :lvUnitsDaily, :lvUnitsUsed, :lvTyp, :absRsn, :statusCd, :dtOfPay)";
				Query q = session.createSQLQuery(sql);
				q.setParameter("payFreq", request.getPayFreq());
				q.setParameter("empNbr", request.getEmpNbr());
				q.setParameter("datetimeSubmitted", request.getDatetimeSubmitted());
				q.setParameter("datetimeFrom", request.getDatetimeFrom());
				q.setParameter("datetimeTo", request.getDatetimeTo());
				q.setParameter("lvUnitsDaily", request.getLvUnitsDaily());
				q.setParameter("lvUnitsUsed", request.getLvUnitsUsed());
				q.setParameter("lvTyp", request.getLvTyp());
				q.setParameter("absRsn", request.getAbsRsn());
				q.setParameter("statusCd", request.getStatusCd());
				q.setParameter("dtOfPay", request.getDtOfPay());
				q.executeUpdate();
				String sb = "SELECT NEWID = @@IDENTITY;";
				SQLQuery q2 = session.createSQLQuery(sb);
				Object res = q2.uniqueResult();
				return Integer.parseInt(res.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void saveLeaveRequest(Integer lvId, String status, boolean isUpdate) {
		Session session = sessionFactory.openSession();
		String sql = "update BEA_EMP_LV_RQST set STATUS_CD=:status, DT_OF_PAY='' where ID=:lvId";
		Query q = session.createSQLQuery(sql);
		q.setParameter("status", status);
		q.setParameter("lvId", lvId);
		q.executeUpdate();
		session.flush();
		session.close();
	}

	public void DeleteLeaveRequest(Integer lvId) {
		Session session = sessionFactory.openSession();
		String sql = "delete from BEA_EMP_LV_RQST where ID=:lvId";
		Query q = session.createSQLQuery(sql);
		q.setParameter("lvId", lvId);
		q.executeUpdate();
		session.flush();
		session.close();
	}

	public void deleteLeaveFlow(Integer lvId) {
		Session session = sessionFactory.openSession();
		String sql = "delete from BEA_EMP_LV_WORKFLOW where LV_ID=:lvId";
		Query q = session.createSQLQuery(sql);
		q.setParameter("lvId", lvId);
		q.executeUpdate();
		session.flush();
		session.close();
	}

	public void deleteLeaveComments(Integer lvId) {
		Session session = sessionFactory.openSession();
		String sql = "DELETE from BEA_EMP_LV_COMMENTS where LV_ID=:lvId";
		Query q = session.createSQLQuery(sql);
		q.setParameter("lvId", lvId);
		q.executeUpdate();
		session.flush();
		session.close();
	}

	public LeaveParameters getLeaveParameters() {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT BO.standardHrs, BEO.lvMealBreakHrs, BEO.reqLvHrsReqstd, BEO.usePmisSpvsrLevels, BEO.ignCutoffDt, ");
		sql.append("	BEO.msgLvReq, BEO.urlHm  ");
		sql.append("FROM BhrOptions BO, BhrEapOpt BEO");
		Query q = session.createQuery(sql.toString());
		Object[] res = (Object[]) q.uniqueResult();

		LeaveParameters result = new LeaveParameters((BigDecimal) res[0], (BigDecimal) res[1], (Character) res[2],
				(Character) res[3], (Character) res[4], (String) res[5], (String) res[6]);
		return result;
	}

	public List<Code> getAvailableFrequencies(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT el.id.payFreq, PF.payFreqDescr ");
		sql.append("FROM BhrEmpLv el, BthrPayFreq PF ");
		sql.append("WHERE el.id.cyrNyrFlg='C' AND el.id.empNbr=:employeeNumber AND el.id.payFreq=PF.payFreq ");
		sql.append("	AND EXISTS (SELECT 1 ");
		sql.append("				FROM BthrAbsRsnToLvTyp AR2LT, BthrLvTypDescr LTD, BthrLvTyp LT, BthrAbsRsn AR ");
		sql.append(
				"				WHERE el.id.payFreq=LT.id.payFreq AND el.id.lvTyp=LT.id.lvTyp AND LT.stat='A' AND LT.id.lvTyp=AR2LT.id.lvTyp ");
		sql.append(
				"					AND LTD.lvTyp=AR2LT.id.lvTyp AND LTD.stat='A' AND AR.absRsn=AR2LT.id.absRsn AND AR.stat='A' ");
		sql.append("	) ");
		sql.append("ORDER BY el.id.payFreq DESC");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		@SuppressWarnings("unchecked")
		List<Object[]> res = q.list();

		List<Code> result = new ArrayList<Code>();
		Code code;
		for (Object[] item : res) {
			code = new Code((StringUtil.convertToCharacter(item[0]) == null ? ""
					: (StringUtil.convertToCharacter(item[0])).toString()), "", (String) item[1]);
			result.add(code);
		}
		return result;
	}

	public BhrPmisPosCtrl getEmployeeSupervisorPMISData(String directReportEmployeeNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder("");
		sql.append("FROM BhrPmisPosCtrl PPC ");
		sql.append("WHERE PPC.occEmpNbr=:employeeNumber AND PPC.id.posTyp='P' AND PPC.id.cyrNyrFlg='C' ");
		sql.append("ORDER BY PPC.payFreq DESC");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", directReportEmployeeNumber);
		BhrPmisPosCtrl res = (BhrPmisPosCtrl) q.list().get(0);

		return res;
	}

	public LeaveEmployeeData getPMISFirstLineSupervisor(String spvsrBilletNbr, String spvsrPosNbr,
			boolean checkTemporaryApprover) {
		Session session = this.getSession();
		String sql = null;
		if (checkTemporaryApprover) {
			sql = "SELECT ED.EMP_NBR, ED.NAME_F, ED.NAME_M, ED.NAME_L, ED.EMAIL " +
					"FROM BHR_EMP_DEMO ED  " +
					"WHERE ED.EMP_NBR=( " +
						"SELECT ISNULL( " +
							"(SELECT TA.TMP_APPRVR_EMP_NBR FROM BEA_EMP_LV_TMP_APPROVERS TA WHERE TA.SPVSR_EMP_NBR=(SELECT PPC2.OCC_EMP_NBR FROM BHR_PMIS_POS_CTRL PPC2 WHERE PPC2.BILLET_NBR=:supervisorBilletNumber AND PPC2.POS_NBR=:supervisorPosNumber AND PPC2.POS_TYP='P' AND PPC2.CYR_NYR_FLG='C') AND TA.DATETIME_FROM <= GETDATE() AND GETDATE() <= TA.DATETIME_TO)," +
							"(SELECT PPC2.OCC_EMP_NBR FROM BHR_PMIS_POS_CTRL PPC2 WHERE PPC2.BILLET_NBR=:supervisorBilletNumber AND PPC2.POS_NBR=:supervisorPosNumber AND PPC2.POS_TYP='P' AND PPC2.CYR_NYR_FLG='C')" +
						             ") " +
					") ";
		} else {
			sql = "SELECT ED.EMP_NBR, ED.NAME_F, ED.NAME_M, ED.NAME_L, ED.EMAIL " +
					"FROM BHR_EMP_DEMO ED  " +
					"WHERE ED.EMP_NBR=( " +
							"SELECT PPC2.OCC_EMP_NBR FROM BHR_PMIS_POS_CTRL PPC2 WHERE PPC2.BILLET_NBR=:supervisorBilletNumber AND PPC2.POS_NBR=:supervisorPosNumber AND PPC2.POS_TYP='P' AND PPC2.CYR_NYR_FLG='C' " +      
					") ";
		}
		Query q = session.createSQLQuery(sql);
		q.setParameter("supervisorBilletNumber", spvsrBilletNbr);
		q.setParameter("supervisorPosNumber", spvsrPosNbr);
		Object[] res = (Object[]) q.uniqueResult();
		

		LeaveEmployeeData result = new LeaveEmployeeData();
		if(res != null) {
			result = new LeaveEmployeeData((String) res[0], (String) res[1], (String) res[2],(String) res[3],(String) res[4]);
		}
		else
		{
			result = null;
		}
	
        return result;

		/*String empNbr = res == null ? null : (String) res[0];
		return empNbr;*/
	}

	public LeaveEmployeeData getFirstLineSupervisor(String directReportEmployeeNumber, boolean checkTemporaryApprover) {
		Session session = this.getSession();
		String sql = null;
		/*if (checkTemporaryApprover) {
			String tempApprover = getTempApprover(directReportEmployeeNumber);
			if (tempApprover != null)
				return tempApprover;
		}*/
	/*	sql = "SELECT ED.empNbr, ED.nameF, ED.nameM, ED.nameL, ED.email " + "FROM BhrEmpDemo ED  " + "WHERE ED.empNbr= "
				+ "(SELECT E2S.spvsrEmpNbr " + "FROM BhrEapEmpToSpvsr E2S " + "WHERE E2S.empEmpNbr=:employeeNumber )";*/
		
		if(checkTemporaryApprover) {
			sql = "SELECT ED.EMP_NBR, ED.NAME_F, ED.NAME_M, ED.NAME_L, ED.EMAIL " + 
					"FROM BHR_EMP_DEMO ED " + 
					"WHERE ED.EMP_NBR= " + 
						"(SELECT ISNULL(TA.TMP_APPRVR_EMP_NBR, E2S.SPVSR_EMP_NBR) AS APPROVER " +
							"FROM BHR_EAP_EMP_TO_SPVSR E2S " +
								"LEFT OUTER JOIN BEA_EMP_LV_TMP_APPROVERS TA ON TA.SPVSR_EMP_NBR=E2S.SPVSR_EMP_NBR AND TA.DATETIME_FROM <= GETDATE() AND GETDATE() <= TA.DATETIME_TO " +
							"WHERE E2S.EMP_EMP_NBR=:employeeNumber )";
		}
		else {
			sql = "SELECT ED.EMP_NBR, ED.NAME_F, ED.NAME_M, ED.NAME_L, ED.EMAIL " + 
					"FROM BHR_EMP_DEMO ED " + 
					"WHERE ED.EMP_NBR= " + 
						"(SELECT E2S.SPVSR_EMP_NBR " +
							"FROM BHR_EAP_EMP_TO_SPVSR E2S " +
							"WHERE E2S.EMP_EMP_NBR=:employeeNumber )";
		}
		Query q = session.createSQLQuery(sql);
		q.setParameter("employeeNumber", directReportEmployeeNumber);
		Object[] res = (Object[]) q.uniqueResult();
		
		LeaveEmployeeData result = new LeaveEmployeeData();
		if(res != null) {
			result = new LeaveEmployeeData((String) res[0], (String) res[1], (String) res[2],(String) res[3],(String) res[4]);
		}
		else {
			result = null;
		}
	
        return result;

/*
		String empNbr = res == null ? null : (String) res[0];
		return empNbr;*/
	}

	private String getTempApprover(String directReportEmployeeNumber) {
		Session session = this.getSession();
		String sql = "	select TA.tmpApprvrEmpNbr from BeaEmpLvTmpApprovers TA where TA.spvsrEmpNbr=:employeeNumber AND TA.datetimeFrom <= GETDATE() AND GETDATE() <= TA.datetimeTo";
		Query q = session.createQuery(sql);
		q.setParameter("employeeNumber", directReportEmployeeNumber);
		String tmpApprover = (String) q.uniqueResult();
		return tmpApprover;
	}

	public List<Code> getAbsenceReasons(String empNbr, String freq, String leaveType) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT AR2LT.id.absRsn, AR.absDescr ");
		sql.append("FROM BthrAbsRsnToLvTyp AR2LT, BthrLvTyp LT, BthrAbsRsn AR, BhrEmpLv EL ");
		sql.append(
				"WHERE LT.id.lvTyp=AR2LT.id.lvTyp AND LT.stat='A' AND LT.id.payFreq = :payFrequency AND AR.absRsn=AR2LT.id.absRsn AND AR.stat='A' ");
		sql.append(
				"	AND EL.id.cyrNyrFlg='C' AND EL.id.empNbr=:employeeNumber AND EL.id.payFreq=:payFrequency AND EL.id.lvTyp=LT.id.lvTyp ");
		if (leaveType != null && leaveType.length() > 0) {
			sql.append(" AND AR2LT.id.lvTyp = :leaveType");
		}
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		q.setParameter("payFrequency", freq.charAt(0));

		if (leaveType != null && leaveType.length() > 0) {
			q.setParameter("leaveType", leaveType);
		}
		sql.append(" ORDER BY AR.absDescr ASC ");
		@SuppressWarnings("unchecked")
		List<Object[]> res = q.list();

		List<Code> result = new ArrayList<Code>();
		Code code;
		for (Object[] item : res) {
			code = new Code((String) item[0], "", (String) item[1]);
			result.add(code);
		}
		return result;
	}

	public List<Code> getLeaveTypes(String empNbr, String freq, String leaveType) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT AR2LT.id.lvTyp, LT.daysHrs, LTD.longDescr ");
		sql.append("FROM BthrAbsRsnToLvTyp AR2LT, BthrLvTypDescr LTD, BthrLvTyp LT, BthrAbsRsn AR, BhrEmpLv EL ");
		sql.append(
				"WHERE LTD.lvTyp=AR2LT.id.lvTyp AND LTD.stat='A' AND LT.id.lvTyp=AR2LT.id.lvTyp AND LT.stat='A' AND LT.id.payFreq = :payFrequency ");
		sql.append("	AND AR.absRsn=AR2LT.id.absRsn AND AR.stat='A' ");
		sql.append(
				"	AND EL.id.cyrNyrFlg='C' AND EL.id.empNbr=:employeeNumber AND EL.id.payFreq=:payFrequency AND EL.id.lvTyp=LT.id.lvTyp ");
		if (leaveType != null && leaveType.length() > 0) {
			sql.append(" AND AR2LT.id.lvTyp = :leaveType");
		}
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		q.setParameter("payFrequency", freq.charAt(0));

		if (leaveType != null && leaveType.length() > 0) {
			q.setParameter("leaveType", leaveType);
		}
		sql.append(" ORDER BY AR2LT.id.lvTyp ASC ");
		@SuppressWarnings("unchecked")
		List<Object[]> res = q.list();

		List<Code> result = new ArrayList<Code>();
		Code code;
		for (Object[] item : res) {
			code = new Code((String) item[0], (String) item[1], (String) item[2]);
			result.add(code);
		}
		return result;
	}

	public List<LeaveInfo> getLeaveInfo(String empNbr, String freq) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT BHR_EMP_LV.PAY_FREQ, BHR_EMP_LV.LV_TYP, BHR_EMP_LV.LV_BEGIN_BAL, BHR_EMP_LV.LV_EARNED, BHR_EMP_LV.LV_USED, ");
		sql.append(
				"		BTHR_LV_TYP_DESCR.LONG_DESCR, BTHR_LV_TYP_DESCR.POST_AGNST_ZERO_BAL, BTHR_LV_TYP.DAYS_HRS, BTHR_LV_TYP.ADD_SUBTRACT_BAL, ");

		sql.append("		(SELECT ISNULL((SELECT SUM(ISNULL(BHR_EMP_LV_XMITAL.LV_UNITS_EARNED, 0))),0) ");
		sql.append("			FROM BHR_EMP_LV_XMITAL ");
		sql.append("			WHERE BHR_EMP_LV_XMITAL.CYR_NYR_FLG = 'C' AND ");
		sql.append("				BHR_EMP_LV_XMITAL.EMP_NBR = :employeeNumber AND ");
		sql.append("				BHR_EMP_LV_XMITAL.PAY_FREQ = BHR_EMP_LV.PAY_FREQ AND ");
		sql.append("				BHR_EMP_LV_XMITAL.LV_TYP = BHR_EMP_LV.LV_TYP AND ");
		sql.append("				LENGTH(TRIM(ISNULL(BHR_EMP_LV_XMITAL.PROCESS_DT, '')))=0) AS PENDING_EARNED, ");

		sql.append("		(SELECT ISNULL((SELECT SUM(ISNULL(BEA_EMP_LV_RQST.LV_UNITS_USED, 0))),0) ");
		sql.append("			FROM BEA_EMP_LV_RQST ");
		sql.append("			WHERE BEA_EMP_LV_RQST.STATUS_CD = 'P' AND ");
		sql.append("				BEA_EMP_LV_RQST.EMP_NBR = :employeeNumber AND ");
		sql.append("				BEA_EMP_LV_RQST.PAY_FREQ = BHR_EMP_LV.PAY_FREQ AND ");
		sql.append("				BEA_EMP_LV_RQST.LV_TYP = BHR_EMP_LV.LV_TYP) AS PENDING_APPROVAL, ");

		sql.append("		(SELECT ISNULL((SELECT SUM(ISNULL(BEA_EMP_LV_RQST.LV_UNITS_USED, 0))),0) ");
		sql.append("			FROM BEA_EMP_LV_RQST ");
		sql.append("			WHERE BEA_EMP_LV_RQST.STATUS_CD IN ('A','L') AND ");
		sql.append("				BEA_EMP_LV_RQST.EMP_NBR = :employeeNumber AND ");
		sql.append("				BEA_EMP_LV_RQST.PAY_FREQ = BHR_EMP_LV.PAY_FREQ AND ");
		sql.append("				BEA_EMP_LV_RQST.LV_TYP = BHR_EMP_LV.LV_TYP) AS PENDING_PAYROLL, ");

		sql.append("		(SELECT ISNULL((SELECT SUM(ISNULL(BHR_EMP_LV_XMITAL.LV_UNITS_USED, 0))),0) ");
		sql.append("			FROM BHR_EMP_LV_XMITAL ");
		sql.append("			WHERE BHR_EMP_LV_XMITAL.CYR_NYR_FLG = 'C' AND  ");
		sql.append("				BHR_EMP_LV_XMITAL.EMP_NBR = :employeeNumber AND  ");
		sql.append("				BHR_EMP_LV_XMITAL.PAY_FREQ = BHR_EMP_LV.PAY_FREQ AND ");
		sql.append("				BHR_EMP_LV_XMITAL.LV_TYP = BHR_EMP_LV.LV_TYP AND  ");
		sql.append("				LENGTH(TRIM(ISNULL(BHR_EMP_LV_XMITAL.PROCESS_DT, '')))=0) AS PENDING_USED ");

		sql.append("	FROM BHR_EMP_LV, BTHR_LV_TYP_DESCR, BTHR_LV_TYP ");
		sql.append("	WHERE CYR_NYR_FLG = 'C' ");
		sql.append("		AND EMP_NBR = :employeeNumber ");
		sql.append("		AND BHR_EMP_LV.LV_TYP = BTHR_LV_TYP_DESCR.LV_TYP ");
		sql.append("		AND BHR_EMP_LV.LV_TYP = BTHR_LV_TYP.LV_TYP ");
		sql.append("		AND BHR_EMP_LV.PAY_FREQ = BTHR_LV_TYP.PAY_FREQ ");
		sql.append("		AND BTHR_LV_TYP.STAT='A' ");
		//sql.append("		AND BTHR_LV_TYP.CHK_STUB_POS <>'' ");
		sql.append("		AND BTHR_LV_TYP_DESCR.STAT='A' ");
		Query q = session.createSQLQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		// q.setParameter("payFrequency", freq.charAt(0));
		@SuppressWarnings("unchecked")
		List<Object[]> res = q.list();

		List<LeaveInfo> leaveInfo = new ArrayList<LeaveInfo>();
		LeaveInfo info;
		for (Object[] item : res) {
			info = new LeaveInfo((Character) item[0], (String) item[1], (BigDecimal) item[2], (BigDecimal) item[3],
					(BigDecimal) item[4], (String) item[5], (Character) item[6], (String) item[7], (Character) item[8],
					(BigDecimal) item[9], (BigDecimal) item[10], (BigDecimal) item[11], (BigDecimal) item[12]);
			leaveInfo.add(info);
		}
		return leaveInfo;
	}

	public void saveLvComments(BeaEmpLvComments comments) {
		Session session = sessionFactory.openSession();
		String sql = "update BEA_EMP_LV_COMMENTS set LV_COMMENT_TYP='P' where LV_ID=:lvId";
		Query q = session.createSQLQuery(sql);
		q.setParameter("lvId", comments.getLvId());
		q.executeUpdate();
		sql = "INSERT INTO BEA_EMP_LV_COMMENTS (LV_ID, LV_COMMENT_TYP, LV_COMMENT_EMP_NBR, LV_COMMENT_DATETIME, LV_COMMENT)"
				+ " VALUES (:lvId, :lvCommentTyp, :lvCommentEmpNbr, :lvCommentDatetime, :lvComment)";
		q = session.createSQLQuery(sql);
		q.setParameter("lvId", comments.getLvId());
		q.setParameter("lvCommentTyp", comments.getLvCommentTyp());
		q.setParameter("lvCommentEmpNbr", comments.getLvCommentEmpNbr());
		q.setParameter("lvCommentDatetime", comments.getLvCommentDatetime());
		q.setParameter("lvComment", comments.getLvComment());
		q.executeUpdate();
		session.flush();
		session.close();
	}

	public void saveLvWorkflow(BeaEmpLvWorkflow flow) {
		Session session = sessionFactory.openSession();
        String sqAll = "INSERT INTO BEA_EMP_LV_WORKFLOW (LV_ID, SEQ_NUM, INSERT_DATETIME,  APPRVR_EMP_NBR)"
                + " VALUES (:lvId, :seqNum, :insertDatetime, :apprvrEmpNbr)";
        Query queryAll = session.createSQLQuery(sqAll);
        queryAll.setParameter("lvId", flow.getLvId());
        queryAll.setParameter("seqNum", flow.getSeqNum());
        queryAll.setParameter("insertDatetime", flow.getInsertDatetime());
        queryAll.setParameter("apprvrEmpNbr", flow.getApprvrEmpNbr());
        queryAll.executeUpdate();
		session.flush();
		session.close();
	}

	public List<LeaveRequestComment> getLeaveComments(Integer id) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT ELC.id, ELC.lvId, ISNULL(ELC.lvComment,''), ELC.lvCommentDatetime, ELC.lvCommentTyp, ELC.lvCommentEmpNbr, ");
		sql.append("	ED.nameF, ED.nameM, ED.nameL ");
		sql.append("FROM BeaEmpLvComments ELC, BhrEmpDemo ED ");
		sql.append("WHERE ELC.lvId=:leaveId AND ELC.lvCommentEmpNbr=ED.empNbr AND LENGTH(ISNULL(ELC.lvComment,''))>0 ");
		sql.append("ORDER BY ELC.lvCommentDatetime ASC");
		Query q = session.createQuery(sql.toString());
		q.setParameter("leaveId", id);
		@SuppressWarnings("unchecked")
		List<Object[]> res = q.list();

		List<LeaveRequestComment> comments = new ArrayList<LeaveRequestComment>();
		LeaveRequestComment comment;
		for (Object[] item : res) {
			comment = new LeaveRequestComment((Integer) item[0], (Integer) item[1], (String) item[2], (Date) item[3],
					(Character) item[4], (String) item[5], (String) item[6], (String) item[7], (String) item[8]);
			comments.add(comment);
		}
		return comments;
	}

	public LeaveRequest getBeaEmpLvRqstById(int id) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder("");
		sql.append("select * from BEA_EMP_LV_RQST rqst where ID =:id");
		Query q = session.createSQLQuery(sql.toString());
		q.setParameter("id", id);
		Object[] res = (Object[]) q.uniqueResult();
		LeaveRequest result = new LeaveRequest((Integer) res[0], (Character) res[1], (String) res[2], (Date) res[3],
				(Date) res[4], (Date) res[5], (BigDecimal) res[6], (BigDecimal) res[7], (String) res[8],
				(String) res[9], (Character) res[10], (String) res[11]);
		return result;
	}

	public List<AppLeaveRequest> getSupervisorSumittedLeaveRequests(String employeeNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT ELW.SEQ_NUM, ELR.ID, ELR.PAY_FREQ, ELR.EMP_NBR, ELR.LV_TYP, ELR.ABS_RSN, ELR.DATETIME_SUBMITTED, ELR.DATETIME_FROM, ELR.DATETIME_TO, ELR.LV_UNITS_DAILY, ELR.LV_UNITS_USED, ELR.STATUS_CD, ");
		sql.append("  ELSC.DESCR AS STATUS_DESCR, ISNULL(ELC.LV_COMMENT,'') AS REQUEST_COMMENT, LT.DAYS_HRS, LTD.LONG_DESCR, AR.ABS_DESCR, ED.NAME_F, ED.NAME_M, ED.NAME_L, ED.NAME_GEN ");
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
		Query q = session.createSQLQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		@SuppressWarnings("unchecked")
		List<Object[]> res = q.list();

		List<AppLeaveRequest> requests = new ArrayList<AppLeaveRequest>();
		AppLeaveRequest request;
		for (Object[] item : res) {
			 String days_HRS = "D";
			 String name_Gen = "";
			 if(item[14]!=null) {
				 if(item[14] instanceof Character) days_HRS = item[14].toString().trim();
			 }
			if(item[20]!=null) {
				if(item[20] instanceof Character) name_Gen = item[20].toString().trim();
			}
		
			request = new AppLeaveRequest((Integer) item[0], (Integer) item[1], (Character) item[2], (String) item[3],
					(String) item[4], (String) item[5], (Date) item[6], (Date) item[7], (Date) item[8],
					(BigDecimal) item[9], (BigDecimal) item[10], (Character) item[11], (String) item[12],
					 days_HRS, (String) item[15], (String) item[16], (String) item[17], (String) item[18],
					(String) item[19], name_Gen);
			requests.add(request);
		}

		return requests;
	}

	public List<BhrEmpLvXmital> getApprovedLeaves(String empNbr, String leaveType, String searchStart, String searchEnd,
			String freq) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder("");
		sql.append(
				"from BhrEmpLvXmital xmi where xmi.id.cyrNyrFlg='C' and xmi.id.payFreq =:payFreq and xmi.id.empNbr=:empNbr");
		if (!StringUtil.isNullOrEmpty(leaveType))
			sql.append(" AND xmi.lvTyp = :leaveType");
		if (searchStart != null && !("").equals(searchStart))
			sql.append(" AND xmi.dtOfAbs >= :dateFrom");
		if (searchEnd != null && !("").equals(searchEnd))
			sql.append(" AND xmi.dtOfAbs <= :dateTo");
		sql.append(" ORDER BY xmi.dtOfAbs DESC");
		Query q = session.createQuery(sql.toString());
		q.setParameter("empNbr", empNbr);
		q.setParameter("payFreq", freq.charAt(0));
		if (!StringUtil.isNullOrEmpty(leaveType))
			q.setParameter("leaveType", leaveType);
		if (searchStart != null && !("").equals(searchStart))
			q.setParameter("dateFrom", searchStart);
		if (searchEnd != null && !("").equals(searchEnd))
			q.setParameter("dateTo", searchEnd);
		@SuppressWarnings("unchecked")
		List<BhrEmpLvXmital> res = q.list();

		return res;
	}

	public List<String[]> getAbsrsnsLeaveTypesMap() {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder("");
		sql.append("select ABS_RSN, LV_TYP from BTHR_ABS_RSN_TO_LV_TYP");
		Query q = session.createSQLQuery(sql.toString());
		@SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
		List<String[]> result = new ArrayList<String[]>();
		String[] temp;
		//Add a blank row for the map
		temp = new String[2];
		temp[0] = "";
		temp[1] = "";
		result.add(temp);
		
		for (int i = 0; i < res.size(); i++) {
			temp = new String[2];
			temp[0] = (String) (res.get(i)[0]);
			temp[1] = (String) (res.get(i)[1]);
			result.add(temp);
		}
		return result;
	}

	public LeaveEmployeeData  getEmployeeData(String employeeNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ED.EMP_NBR, ED.NAME_F, ED.NAME_M, ED.NAME_L, ED.EMAIL, ISNULL(SU.USR_LOG_NAME,'') AS USR_LOG_NAME ");
		sql.append("FROM BHR_EMP_DEMO ED LEFT OUTER JOIN SEC_USERS SU ON ED.EMP_NBR=SU.EMP_NBR AND SU.USR_DELETED=0, ");
		sql.append("WHERE ED.EMP_NBR= :employeeNumber");
		Query q = session.createSQLQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		Object[] res = (Object[]) q.uniqueResult();
		
		LeaveEmployeeData result = new LeaveEmployeeData();
		result = new LeaveEmployeeData((String) res[0], (String) res[1], (String) res[2],(String) res[3],(String) res[4]);
        return result;
	}
	
	public List<LeaveUnitsConversion> getMinutesToHoursConversionRecs(String payFrequency, String leaveType) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT LV_TYP, UP_TO_MIN, PCT_OF_HR_DAY FROM BTHR_LV_UNTS_CONV ");
		sql.append("WHERE PAY_FREQ=:payFrequency AND LV_TYP=:leaveType AND UP_TO_MIN>0 AND UP_TO_HR=0.0 ORDER BY UP_TO_MIN ASC");
		
		Query q = session.createSQLQuery(sql.toString());
		q.setParameter("payFrequency", payFrequency);
		q.setParameter("leaveType", leaveType);
		
		@SuppressWarnings("unchecked")
		List<Object[]> res = q.list();

		List<LeaveUnitsConversion> requests = new ArrayList<LeaveUnitsConversion>();
		LeaveUnitsConversion request;
		for (Object[] item : res) {
			request = new LeaveUnitsConversion();
			request.setUnitType(LeaveUnitsConversion.UNIT_TYPE_HOURS);
			request.setLeaveType(((String) item[0]).trim());
			request.setToUnit(((BigDecimal) item[1]).setScale(3, BigDecimal.ROUND_HALF_UP));
			BigDecimal fractionalAmount = ((BigDecimal) item[2]);
			request.setFractionalAmount(fractionalAmount.setScale(3, BigDecimal.ROUND_HALF_UP));
			requests.add(request);
		}

		return requests;
	
	}

	public List<LeaveUnitsConversion> getHoursToDaysConversionRecs(String payFrequency, String leaveType) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT LV_TYP, UP_TO_HR, PCT_OF_HR_DAY FROM BTHR_LV_UNTS_CONV ");
		sql.append("WHERE PAY_FREQ=:payFrequency AND LV_TYP=:leaveType AND UP_TO_HR>0.0 AND UP_TO_MIN=0 ORDER BY UP_TO_HR ASC");
		
		Query q = session.createSQLQuery(sql.toString());
		q.setParameter("payFrequency", payFrequency);
		q.setParameter("leaveType", leaveType);
		
		@SuppressWarnings("unchecked")
		List<Object[]> res = q.list();

		List<LeaveUnitsConversion> requests = new ArrayList<LeaveUnitsConversion>();
		LeaveUnitsConversion request;
		for (Object[] item : res) {
			request = new LeaveUnitsConversion();
			request.setUnitType(LeaveUnitsConversion.UNIT_TYPE_DAYS);
			request.setLeaveType(((String) item[0]).trim());
			request.setToUnit(((BigDecimal) item[1]).setScale(3, BigDecimal.ROUND_HALF_UP));
			BigDecimal fractionalAmount = ((BigDecimal) item[2]);
			request.setFractionalAmount(fractionalAmount.setScale(3, BigDecimal.ROUND_HALF_UP));
			requests.add(request);
		}
		return requests;
	}
	
	public List<AppLeaveRequest> getEmployeeLeaveRequestsPeriods(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder("");
		sql.append(
				"SELECT ID, DATETIME_FROM, DATETIME_TO, LV_UNITS_USED ");
		sql.append("  FROM BEA_EMP_LV_RQST  ");
		sql.append(" WHERE STATUS_CD IN ('A','P','L','C')  ");
		sql.append("  AND EMP_NBR=:employeeNumber ");
		sql.append(" AND DATETIME_TO > DATEADD(YY, -1, GETDATE()) ");
		
		Query q = session.createSQLQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		
		List<AppLeaveRequest> result = new ArrayList<AppLeaveRequest>();
		AppLeaveRequest request;
		List<Object[]> res = q.list();
		for (Object[] item : res) {
			request = new AppLeaveRequest();
			request.setId(((Integer) item[0]));
			request.setDatetimeFrom( (Date)item[1]);
			request.setDatetimeTo( (Date)item[2]);
			request.setLvUnitsUsed((BigDecimal)item[3]);
			result.add(request);
		}

		return result;
	}
}
