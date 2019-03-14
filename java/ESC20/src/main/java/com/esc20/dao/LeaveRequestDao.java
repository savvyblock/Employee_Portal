package com.esc20.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BeaEmpLvComments;
import com.esc20.model.BeaEmpLvRqst;
import com.esc20.model.BeaEmpLvWorkflow;
import com.esc20.model.BhrEmpLvXmital;
import com.esc20.model.BhrPmisPosCtrl;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.LeaveInfo;
import com.esc20.nonDBModels.LeaveParameters;
import com.esc20.nonDBModels.LeaveRequestComment;
import com.esc20.util.StringUtil;

@Repository
public class LeaveRequestDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM-dd-yyyy hh:mmaa");
	private SimpleDateFormat dateTimeFormat24hr = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mmaa");
	private SimpleDateFormat bhrDateFormat = new SimpleDateFormat("yyyyMMdd");

	public BeaEmpLvRqst getleaveRequestById(int id) {
		Session session = this.getSession();
		String hql = "from BeaEmpLvRqst where id = ? ";
		Query q = session.createQuery(hql);
		q.setParameter(0, id);
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
		List<Object[]> res = q.list();
		for (Object[] item : res) {
			req = new AppLeaveRequest((Integer) item[0], (String) item[1], (String) item[2], (Date) item[3],
					(Date) item[4], (Date) item[5], (BigDecimal) item[6], (BigDecimal) item[7], (Character) item[8],
					(String) item[9], (String) item[10], (String) item[11], (String) item[12], (String) item[13]);
			result.add(req);
		}
		
		return result;
	}

	public BeaEmpLvRqst saveLeaveRequest(BeaEmpLvRqst request, boolean isUpdate) {
		Session session = this.getSession();
		BeaEmpLvRqst req;
		try {
			if (isUpdate) {
				session.update(request);
				req = request;
			} else {
				Integer id = (Integer) session.save(request);
				req = (BeaEmpLvRqst) session.get(BeaEmpLvRqst.class, id);
			}
			session.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return req;
	}

	public boolean DeleteLeaveRequest(BeaEmpLvRqst request) {
		Session session = this.getSession();
		try {
			session.delete(request);
			session.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
		sql.append("SELECT DISTINCT el.id.payFreq, '', PF.payFreqDescr ");
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
		List<Object[]> res = q.list();
		
		List<Code> result = new ArrayList<Code>();
		Code code;
		for (Object[] item : res) {
			code = new Code(((Character) item[0] == null ? "" : ((Character) item[0]).toString()),
					((Character) item[1] == null ? "" : ((Character) item[1]).toString()), (String) item[2]);
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

	public String getPMISFirstLineSupervisor(String spvsrBilletNbr, String spvsrPosNbr,
			boolean checkTemporaryApprover) {
		Session session = this.getSession();
		String sql = null;
		if (checkTemporaryApprover) {
			sql = "SELECT ED.empNbr, ED.nameF, ED.nameM, ED.nameL, ED.email " + "FROM BhrEmpDemo ED  "
					+ "WHERE ED.empNbr=( " + "SELECT ISNULL( "
					+ "(SELECT TA.tmpApprvrEmpNbr FROM BeaEmpLvTmpApprovers TA WHERE TA.spvsrEmpNbr=(SELECT PPC2.occEmpNbr FROM BhrPmisPosCtrl PPC2 WHERE PPC2.id.billetNbr=:supervisorBilletNumber AND PPC2.id.posNbr=:supervisorPosNumber AND PPC2.id.posTyp='P' AND PPC2.id.cyrNyrFlg='C') AND TA.datetimeFrom <= GETDATE() AND GETDATE() <= TA.datetimeTo),"
					+ "(SELECT PPC2.occEmpNbr FROM BhrPmisPosCtrl PPC2 WHERE PPC2.id.billetNbr=:supervisorBilletNumber AND PPC2.id.posNbr=:supervisorPosNumber AND PPC2.id.posTyp='P' AND PPC2.id.cyrNyrFlg='C')"
					+ ") " + ") ";
		} else {
			sql = "SELECT ED.empNbr, ED.nameF, ED.nameM, ED.nameL, ED.email " + "FROM BhrEmpDemo ED  "
					+ "WHERE ED.empNbr=( "
					+ "SELECT PPC2.occEmpNbr FROM BhrPmisPosCtrl PPC2 WHERE PPC2.id.billetNbr=:supervisorBilletNumber AND PPC2.id.posNbr=:supervisorPosNumber AND PPC2.id.posTyp='P' AND PPC2.id.cyrNyrFlg='C'"
					+ ") ";
		}
		Query q = session.createQuery(sql);
		q.setParameter("supervisorBilletNumber", spvsrBilletNbr);
		q.setParameter("supervisorPosNumber", spvsrPosNbr);
		Object[] res = (Object[]) q.uniqueResult();
		
		String empNbr = res == null ? null : (String) res[1];
		return empNbr;
	}

	public String getFirstLineSupervisor(String directReportEmployeeNumber, boolean checkTemporaryApprover) {
		Session session = this.getSession();
		String sql = null;
		if (checkTemporaryApprover) {
			String tempApprover = getTempApprover(directReportEmployeeNumber);
			if (tempApprover != null)
				return tempApprover;
		}
		sql = "SELECT ED.empNbr, ED.nameF, ED.nameM, ED.nameL, ED.email " + "FROM BhrEmpDemo ED  " + "WHERE ED.empNbr= "
				+ "(SELECT E2S.spvsrEmpNbr " + "FROM BhrEapEmpToSpvsr E2S " + "WHERE E2S.empEmpNbr=:employeeNumber )";
		Query q = session.createQuery(sql);
		q.setParameter("employeeNumber", directReportEmployeeNumber);
		Object[] res = (Object[]) q.uniqueResult();
		
		String empNbr = res == null ? null : (String) res[0];
		return empNbr;
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
		sql.append("SELECT DISTINCT AR2LT.id.absRsn, '', AR.absDescr ");
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
		List<Object[]> res = q.list();
		
		List<Code> result = new ArrayList<Code>();
		Code code;
		for (Object[] item : res) {
			code = new Code((String) item[0], ((Character) item[1] == null ? "" : ((Character) item[1]).toString()),
					(String) item[2]);
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
		sql.append("SELECT LV.id.payFreq, LV.id.lvTyp, LV.lvBeginBal, LV.lvEarned, LV.lvUsed, ");
		sql.append("		DES.longDescr, DES.postAgnstZeroBal, TP.daysHrs, TP.addSubtractBal, ");
		//PENDING_EARNED
		sql.append("		(SELECT SUM(ISNULL(xmi.lvUnitsEarned,0)) ");
		sql.append("			FROM BhrEmpLvXmital xmi ");
		sql.append("			WHERE xmi.id.cyrNyrFlg = 'C' AND ");
		sql.append("				xmi.id.empNbr = :employeeNumber AND ");
		sql.append("				xmi.id.payFreq = LV.id.payFreq AND ");
		sql.append("				xmi.lvTyp = LV.id.lvTyp AND ");
		sql.append("				xmi.processDt is null), ");
		//PENDING_APPROVAL
		sql.append("		(SELECT SUM(ISNULL(req.lvUnitsUsed,0)) ");
		sql.append("			FROM BeaEmpLvRqst req ");
		sql.append("			WHERE req.statusCd = 'P' AND ");
		sql.append("				req.empNbr = :employeeNumber AND ");
		sql.append("				req.payFreq = LV.id.payFreq AND ");
		sql.append("				req.lvTyp = LV.id.lvTyp), ");
		//PENDING_PAYROLL
		sql.append("		(SELECT SUM(ISNULL(req2.lvUnitsUsed,0)) ");
		sql.append("			FROM BeaEmpLvRqst req2 ");
		sql.append("			WHERE req2.statusCd IN ('A','L') AND ");
		sql.append("				req2.empNbr = :employeeNumber AND ");
		sql.append("				req2.payFreq = LV.id.payFreq AND ");
		sql.append("				req2.lvTyp = LV.id.lvTyp), ");
		//PENDING_USED
		sql.append("		(SELECT SUM(ISNULL(xmi2.lvUnitsUsed,0))");
		sql.append("			FROM BhrEmpLvXmital xmi2 ");
		sql.append("			WHERE xmi2.id.cyrNyrFlg = 'C' AND  ");
		sql.append("				xmi2.id.empNbr = :employeeNumber AND  ");
		sql.append("				xmi2.id.payFreq = LV.id.payFreq AND ");
		sql.append("				xmi2.lvTyp = LV.id.lvTyp AND ");
		sql.append("				xmi2.processDt is null) ");

		sql.append("	FROM BhrEmpLv LV, BthrLvTypDescr DES, BthrLvTyp TP");
		sql.append("	WHERE LV.id.cyrNyrFlg = 'C' ");
		sql.append("		AND LV.id.empNbr = :employeeNumber ");
		sql.append("		AND LV.id.payFreq = :payFrequency ");
		sql.append("		AND LV.id.lvTyp = DES.lvTyp ");
		sql.append("		AND LV.id.lvTyp = TP.id.lvTyp ");
		sql.append("		AND LV.id.payFreq = TP.id.payFreq ");
		sql.append("		AND TP.stat='A' ");
		sql.append("		AND DES.stat='A' ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		q.setParameter("payFrequency", freq.charAt(0));
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
		Session session = this.getSession();
		List<LeaveRequestComment> prevComs = getLeaveComments(comments.getBeaEmpLvRqst().getId());
		BeaEmpLvComments comm;
		for (int i = 0; i < prevComs.size(); i++) {
			if (prevComs.get(i).getCommentType().equals("C")) {
				comm = (BeaEmpLvComments) session.get(BeaEmpLvComments.class, prevComs.get(i).getId());
				comm.setLvCommentTyp('P');
				session.update(comm);
			}
		}
		session.save(comments);
		session.flush();
		
	}

	public void saveLvWorkflow(BeaEmpLvWorkflow flow) {
		Session session = this.getSession();
		session.save(flow);
		session.flush();
		
	}

	public List<LeaveRequestComment> getLeaveComments(Integer id) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT ELC.id, ELC.beaEmpLvRqst.id, ISNULL(ELC.lvComment,''), ELC.lvCommentDatetime, ELC.lvCommentTyp, ELC.lvCommentEmpNbr, ");
		sql.append("	ED.nameF, ED.nameM, ED.nameL ");
		sql.append("FROM BeaEmpLvComments ELC, BhrEmpDemo ED ");
		sql.append(
				"WHERE ELC.beaEmpLvRqst.id=:leaveId AND ELC.lvCommentEmpNbr=ED.empNbr AND LENGTH(ISNULL(ELC.lvComment,''))>0 ");
		sql.append("ORDER BY ELC.lvCommentDatetime ASC");
		Query q = session.createQuery(sql.toString());
		q.setParameter("leaveId", id);
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

	public void deleteLeaveComments(BeaEmpLvComments comments) {
		Session session = this.getSession();
		try {
			String sql = "from BeaEmpLvComments where beaEmpLvRqst=:beaEmpLvRqst";
			Query q = session.createQuery(sql);
			q.setParameter("beaEmpLvRqst", comments.getBeaEmpLvRqst());
			List<BeaEmpLvComments> res = q.list();
			for (int i = 0; i < res.size(); i++) {
				session.delete(res.get(i));
			}
			session.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteLeaveFlow(BeaEmpLvWorkflow flow) {
		Session session = this.getSession();
		try {
			String sql = "from BeaEmpLvWorkflow where beaEmpLvRqst=:beaEmpLvRqst";
			Query q = session.createQuery(sql);
			q.setParameter("beaEmpLvRqst", flow.getBeaEmpLvRqst());
			List<BeaEmpLvWorkflow> res = q.list();
			for (int i = 0; i < res.size(); i++) {
				session.delete(res.get(i));
			}
			session.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BeaEmpLvRqst getBeaEmpLvRqstById(int id) {
		Session session = this.getSession();
		BeaEmpLvRqst res = (BeaEmpLvRqst) session.get(BeaEmpLvRqst.class, id);
		if(res.getDtOfPay()==null)
			res.setDtOfPay("");
		return res;
	}

	public List<AppLeaveRequest> getSupervisorSumittedLeaveRequests(String employeeNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder("");
		sql.append(
				"SELECT ELW.seqNum, ELR.id, ELR.payFreq, ELR.empNbr, ELR.lvTyp, ELR.absRsn, ELR.datetimeSubmitted, ELR.datetimeFrom, ELR.datetimeTo, ELR.lvUnitsDaily, ELR.lvUnitsUsed, ELR.statusCd, ");
		sql.append("  ELSC.descr, LT.daysHrs, LTD.longDescr, AR.absDescr, ED.nameF, ED.nameM, ED.nameL ");
		sql.append("FROM BeaEmpLvRqst ELR, ");
		sql.append(
				"  BeaEmpLvWorkflow ELW, BteaEmpLvStatusCodes ELSC, BthrLvTyp LT, BthrLvTypDescr LTD, BthrAbsRsn AR, BhrEmpDemo ED ");
		sql.append("WHERE ELW.apprvrEmpNbr = :employeeNumber AND ELR.empNbr = ED.empNbr ");
		sql.append("  AND ELW.beaEmpLvRqst.id = ELR.id ");
		sql.append("  AND ELR.lvTyp=LT.id.lvTyp AND LT.stat='A' AND ELR.statusCd = 'P' ");
		sql.append(
				"  AND ELW.insertDatetime = (SELECT MAX(ELW2.insertDatetime) FROM BeaEmpLvWorkflow ELW2 WHERE ELW2.beaEmpLvRqst.id=ELW.beaEmpLvRqst.id) ");
		sql.append("  AND ELR.statusCd = ELSC.cd  ");
		sql.append("  AND LT.id.payFreq =  ELR.payFreq  ");
		sql.append("  AND ELR.lvTyp=LTD.lvTyp AND LTD.stat='A' AND ELR.absRsn=AR.absRsn AND AR.stat='A' ");
		sql.append("ORDER BY ELR.datetimeFrom ASC ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		List<Object[]> res = q.list();

		List<AppLeaveRequest> requests = new ArrayList<AppLeaveRequest>();
		AppLeaveRequest request;
		for (Object[] item : res) {
			request = new AppLeaveRequest((Integer) item[0], (Integer) item[1], (Character) item[2], (String) item[3],
					(String) item[4], (String) item[5], (Date) item[6], (Date) item[7], (Date) item[8],
					(BigDecimal) item[9], (BigDecimal) item[10], (Character) item[11], (String) item[12],
					(String) item[13], (String) item[14], (String) item[15], (String) item[16], (String) item[17],
					(String) item[18]);
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
		List<BhrEmpLvXmital> res = q.list();
		
		return res;
	}
}
