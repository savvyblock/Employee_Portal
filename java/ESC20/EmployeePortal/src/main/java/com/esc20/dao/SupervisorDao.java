package com.esc20.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BeaEmpLvRqst;
import com.esc20.model.BeaEmpLvTmpApprovers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrEmpLvXmital;
import com.esc20.model.SecUsers;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.LeaveEmployeeData;

@Repository
public class SupervisorDao {
    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
	public List<LeaveEmployeeData> getPMISSupervisorDirectReports(String spvsrBilletNbr, String spvsrPosNbr) {
		Session session = this.getSession();
		String sql = "SELECT PPC.occEmpNbr, PPC.payFreq, ED.nameF, ED.nameL, ED.nameM, " +
				"(SELECT COUNT(*) FROM BhrPmisPosCtrl PPC3 WHERE PPC3.spvsrBilletNbr=PPC.id.billetNbr " +
					"AND PPC3.spvsrPosNbr=PPC.id.posNbr " +
					"AND PPC3.payFreq=(SELECT MAX(PPC4.payFreq) FROM BhrPmisPosCtrl PPC4 WHERE PPC4.occEmpNbr=PPC3.occEmpNbr AND PPC4.id.posTyp='P' AND PPC4.id.cyrNyrFlg='C') " +
					"AND PPC3.id.posTyp='P' AND PPC3.id.cyrNyrFlg='C')" +
				"FROM BhrPmisPosCtrl PPC, BhrEmpDemo ED WHERE PPC.spvsrBilletNbr=:billetNumber " +
					"AND PPC.spvsrPosNbr=:posNumber " +
					"AND PPC.payFreq=(SELECT MAX(PPC2.payFreq) FROM BhrPmisPosCtrl PPC2 WHERE PPC2.occEmpNbr=PPC.occEmpNbr AND PPC2.id.posTyp='P' AND PPC2.id.cyrNyrFlg='C') " +
					"AND PPC.id.posTyp='P' AND PPC.id.cyrNyrFlg='C' AND PPC.occEmpNbr=ED.empNbr ORDER BY ED.nameL ASC, ED.nameF ASC, ED.nameM ASC";
        Query q = session.createQuery(sql);
        q.setParameter("billetNumber", spvsrBilletNbr);
        q.setParameter("spvsrPosNbr", spvsrPosNbr);
        @SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
        
        List<LeaveEmployeeData> result = new ArrayList<LeaveEmployeeData>();
        LeaveEmployeeData data;
        for(Object[] item: res) {
        	data = new LeaveEmployeeData((String) item[0], (Character) item[1], (String) item[2] ,(String) item[3],(String)item[4],(Long)item[5]);
        	result.add(data);
        }
		return result;
	}
	public List<LeaveEmployeeData> getSupervisorDirectReports(String empNbr) {
		Session session = this.getSession();
		String sql = "SELECT E2S.empEmpNbr, ED.nameF, ED.nameL, ED.nameM, " +
				"(SELECT COUNT(*) FROM BhrEapEmpToSpvsr E2S2 WHERE E2S2.spvsrEmpNbr=ED.empNbr) " +
				"FROM BhrEapEmpToSpvsr E2S, BhrEmpDemo ED WHERE E2S.spvsrEmpNbr=:empNbr AND E2S.empEmpNbr=ED.empNbr ORDER BY ED.nameL ASC, ED.nameF ASC, ED.nameM ASC";
        Query q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        @SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
        
        List<LeaveEmployeeData> result = new ArrayList<LeaveEmployeeData>();
        LeaveEmployeeData data;
        for(Object[] item: res) {
        	data = new LeaveEmployeeData((String) item[0], '\0', (String) item[1] ,(String) item[2],(String)item[3],(Long)item[4]);
        	result.add(data);
        }
		return result;
	}
	public List<AppLeaveRequest> getLeaveRequestSummital(String empNbr, String payFreq, String start, String end, BhrEmpDemo demo) throws ParseException {
		Session session = this.getSession();
		String sql = "FROM BhrEmpLvXmital xmi WHERE xmi.id.empNbr=:empNbr and xmi.id.cyrNyrFlg='C'";
        if(start!=null)
        	sql+=" AND xmi.dtOfAbs >=:start";
        if(end!=null)
        	sql+=" AND xmi.dtOfAbs <=:end";
        if(payFreq!=null)
        	sql+=" AND xmi.id.payFreq <=:payFreq";
        Query q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        if(start!=null)
        	q.setParameter("start", start);
        if(end!=null)
        	q.setParameter("end", end);
        if(payFreq!=null)
        	q.setParameter("payFreq", payFreq.charAt(0));
        @SuppressWarnings("unchecked")
		List<BhrEmpLvXmital> result = q.list();
        
        List<AppLeaveRequest> res = new ArrayList<AppLeaveRequest>();
        AppLeaveRequest request;
        for(BhrEmpLvXmital item : result) {
        	request = new AppLeaveRequest(item,demo);
        	res.add(request);
        }
		return res;
	}
	public List<AppLeaveRequest> getLeaveRequestPending(String empNbr, String payFreq, String start, String end,
			BhrEmpDemo demo) throws ParseException {
		Session session = this.getSession();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		String sql = "FROM BeaEmpLvRqst rqst WHERE rqst.empNbr=:empNbr and rqst.statusCd in ('A','P')";
        if(start!=null)
        	sql+=" AND rqst.datetimeFrom >=:start";
        if(end!=null)
        	sql+=" AND rqst.datetimeFrom <=:end";
        if(payFreq!=null)
        	sql+=" AND rqst.payFreq <=:payFreq";
        Query q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        if(start!=null)
        	q.setParameter("start", sdf2.parse(start));
        if(end!=null)
        	q.setParameter("end", sdf2.parse(end));
        if(payFreq!=null)
        	q.setParameter("payFreq", payFreq.charAt(0));
        @SuppressWarnings("unchecked")
		List<BeaEmpLvRqst> result = q.list();
        
        List<AppLeaveRequest> res = new ArrayList<AppLeaveRequest>();
        AppLeaveRequest request;
        for(BeaEmpLvRqst item : result) {
        	request = new AppLeaveRequest(item,demo);
        	res.add(request);
        }
        
		return res;
	}
	public String getApprover(Integer id) {
		Session session = this.getSession();
		String sql = "FROM SecUsers sec WHERE sec.empNbr in (select flow.apprvrEmpNbr from BeaEmpLvWorkflow flow where flow.apprvrEmpNbr = sec.empNbr and "
				+ " flow.lvId =:id) and sec.empNbr is not null" ;
		Query q = session.createQuery(sql);
		q.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<SecUsers> res = q.list();
		SecUsers result;
		if(res !=null && res.size()>0) {
			result = res.get(0);
			return result.getUsrNameF()+" "+ result.getUsrNameL();
		} else
			return "";
	}
	
	public List<BeaEmpLvTmpApprovers> getBeaEmpLvTmpApprovers(String empNbr) {
		Session session = this.getSession();
		String sql = "FROM BeaEmpLvTmpApprovers appr WHERE appr.spvsrEmpNbr=:empNbr order by datetimeFrom asc" ;
		Query q = session.createQuery(sql);
		q.setParameter("empNbr", empNbr);
		@SuppressWarnings("unchecked")
		List<BeaEmpLvTmpApprovers> res =  q.list();
		
		return res;
	}
	public void saveTempApprover(BeaEmpLvTmpApprovers tempApprover, boolean isUpdate) {
        Session session = this.getSession();
        try{
        	if(isUpdate) {
        		session.update(tempApprover);
        	}else {
        		session.save(tempApprover);
        	}
        	session.flush();
        	
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
	public void deleteTempApprover(BeaEmpLvTmpApprovers tempApprover) {
    	Session session = this.getSession();
        try {
        	session.delete(tempApprover);
        	session.flush();
        	
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}

}
