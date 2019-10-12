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
import com.esc20.util.StringUtil;

@Repository
public class SupervisorDao {
    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
	public List<LeaveEmployeeData> getPMISSupervisorDirectReports(String billetNbr, String posNbr) {
		Session session = this.getSession();
		String sql = "SELECT PPC.OCC_EMP_NBR, PPC.PAY_FREQ, ED.NAME_F AS FIRST_NAME, ED.NAME_L AS LAST_NAME, ED.NAME_M AS MIDDLE_NAME, " +
				"(SELECT COUNT(*) FROM BHR_PMIS_POS_CTRL PPC3 WHERE PPC3.SPVSR_BILLET_NBR=PPC.BILLET_NBR " +
					"AND PPC3.SPVSR_POS_NBR=PPC.POS_NBR " +
					"AND PPC3.PAY_FREQ=(SELECT MAX(PPC4.PAY_FREQ) FROM BHR_PMIS_POS_CTRL PPC4 WHERE PPC4.OCC_EMP_NBR=PPC3.OCC_EMP_NBR AND PPC4.POS_TYP='P' AND PPC4.CYR_NYR_FLG='C') " +
					"AND PPC3.POS_TYP='P' AND PPC3.CYR_NYR_FLG='C') AS NUM_DIRECT_REPORTS " +
				"FROM BHR_PMIS_POS_CTRL PPC, BHR_EMP_DEMO ED WHERE PPC.SPVSR_BILLET_NBR=:billetNumber " +
					"AND PPC.SPVSR_POS_NBR=:spvsrPosNbr " +
					"AND PPC.PAY_FREQ=(SELECT MAX(PPC2.PAY_FREQ) FROM BHR_PMIS_POS_CTRL PPC2 WHERE PPC2.OCC_EMP_NBR=PPC.OCC_EMP_NBR AND PPC2.POS_TYP='P' AND PPC2.CYR_NYR_FLG='C') " +
					"AND PPC.POS_TYP='P' AND PPC.CYR_NYR_FLG='C' AND PPC.OCC_EMP_NBR=ED.EMP_NBR ORDER BY LAST_NAME ASC, FIRST_NAME ASC, MIDDLE_NAME ASC";
        Query q = session.createSQLQuery(sql);
        q.setParameter("billetNumber", billetNbr);
        q.setParameter("spvsrPosNbr", posNbr);
        @SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
        
        List<LeaveEmployeeData> result = new ArrayList<LeaveEmployeeData>();
        LeaveEmployeeData data;
        for(Object[] item: res) {
        	data = new LeaveEmployeeData((String) item[0], (Character) item[1], (String) item[2] ,(String) item[3],(String)item[4],(Integer)item[5]);
        	result.add(data);
        }
		return result;
	}
	public List<LeaveEmployeeData> getSupervisorDirectReports(String empNbr) {
		Session session = this.getSession();
		String sql = "SELECT E2S.EMP_EMP_NBR, ED.NAME_F AS FIRST_NAME, ED.NAME_L AS LAST_NAME, ED.NAME_M AS MIDDLE_NAME, " +
				"(SELECT COUNT(*) FROM BHR_EAP_EMP_TO_SPVSR E2S WHERE E2S.SPVSR_EMP_NBR=ED.EMP_NBR) AS NUM_DIRECT_REPORTS " +
				"FROM BHR_EAP_EMP_TO_SPVSR E2S, BHR_EMP_DEMO ED WHERE E2S.SPVSR_EMP_NBR=:empNbr AND E2S.EMP_EMP_NBR=ED.EMP_NBR ORDER BY LAST_NAME ASC, FIRST_NAME ASC, MIDDLE_NAME ASC";
        Query q = session.createSQLQuery(sql);
        q.setParameter("empNbr", empNbr);
        @SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
        
        List<LeaveEmployeeData> result = new ArrayList<LeaveEmployeeData>();
        LeaveEmployeeData data;
        for(Object[] item: res) {
        	data = new LeaveEmployeeData((String) item[0], '\0', (String) item[1] ,(String) item[2],(String)item[3],(Integer)item[4]);
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
        	request.setApprover(this.getApproverXmital(item.getUserId()));
        	request.setDaysHrs(this.getDaysHrs(payFreq,item.getLvTyp()));
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
	
	public String getDaysHrs(String payFreq,String LvType) {
		Session session = this.getSession();
		payFreq = payFreq==null?"":payFreq.trim();
		LvType = LvType==null?"":LvType.trim();
		String sql = "SELECT DAYS_HRS FROM BTHR_LV_TYP where PAY_FREQ=:payFreq and LV_TYP=:LvType" ;
		Query q = session.createSQLQuery(sql);
		q.setParameter("payFreq", payFreq);
		q.setParameter("LvType", LvType);
		@SuppressWarnings("unchecked")
		List<String> res = q.list();
		if(res !=null && res.size()>0) {
			String result = res.get(0);
			if(StringUtil.isNullOrEmpty(result)) {
				return "D";
			}
			else {
				return result.trim();
			}
			
		} else
			return "D";
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
			return result.getUsrNameL()+", "+result.getUsrNameF();
		} else
			return "";
	}
	
	public String getApproverXmital(String userId) {
		Session session = this.getSession();
		userId = userId==null?"":userId.trim();
		String sql = "select  isnull(U.USR_NAME_L + ', ' + U.USR_NAME_F, '') as approver  from sec_users U where USR_LOG_NAME=:userId" ;
		Query q = session.createSQLQuery(sql);
		q.setParameter("userId", userId);
		@SuppressWarnings("unchecked")
		List<String> res = q.list();
		if(res !=null && res.size()>0) {
			String result = res.get(0);
			if(StringUtil.isNullOrEmpty(result)) {
				return "";
			}
			else {
				return result.trim();
			}
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
