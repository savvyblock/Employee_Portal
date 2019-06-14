package com.esc20.dao;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.esc20.model.BhrCalYtd;
import com.esc20.model.BhrEmpJob;
import com.esc20.nonDBModels.Frequency;
import com.esc20.util.StringUtil;

@Repository
public class CalendarYearToDateDao {
    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    public Connection getConn() throws Exception {
    	return SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
    }

	public Date getLastPostedPayDate(String employeeNumber, Frequency frequency) throws ParseException
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct dtofPay from BHRPAYHIST where dtofPay in (select max(dtofPay) from BHRPAYHIST ");
		sql.append("where empNbr = :employeeNumber and payFreq = :frequency)");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		q.setParameter("frequency", frequency.getCode());
		String payDate = (String) q.list().get(0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.parse(payDate);
	}
	
	public BhrEmpJob getBhrEmpJobByIds(String cyrNyrFlg, String payFreq, String empNbr, String jobCd){
        Session session = this.getSession();
        String hql = "from BhrEmpJob where cyrNyrflg = :cyrNyrflg and payFreq = :payFreq and empNbr = :empNbr and jobCd = :jobCd";
        Query q = session.createQuery(hql);
        q.setParameter("cyrNyrflg", cyrNyrFlg);
        q.setParameter("payFreq", payFreq);
        q.setParameter("empNbr", empNbr);
        q.setParameter("jobCd", jobCd);
        @SuppressWarnings("unchecked")
		List<BhrEmpJob> res = q.list();
        
        return res.get(0);
    }
	
	public List<String> getAvailableYears(String employeeNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT bcy.id.calYr FROM BhrCalYtd bcy WHERE");
		sql.append(" bcy.id.empNbr = :employeeNumber");
		sql.append(" AND bcy.id.cyrNyrFlg = 'C'");
		sql.append(" ORDER BY bcy.id.calYr DESC");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		@SuppressWarnings("unchecked")
		List<String> years = q.list();
		
		return years;
	}

	public BhrCalYtd getCalenderYTD(String employeeNumber, String year) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("FROM BhrCalYtd bcy WHERE");
		sql.append(" bcy.id.empNbr = :employeeNumber");
		sql.append(" AND bcy.id.cyrNyrFlg = 'C'");
		sql.append(" AND bcy.id.calYr = :year");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		q.setParameter("year", year);
		BhrCalYtd res = (BhrCalYtd) q.list().get(0);
		return res;
	}

	public String getLatestPayDate(String employeeNumber, Frequency freq) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct hist.id.dtOfPay from BhrPayHist hist where hist.id.dtOfPay in (select max(hist2.id.dtOfPay) from BhrPayHist hist2 ");
		sql.append("where hist.id.empNbr = :employeeNumber and hist.id.payFreq = :frequency)");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		q.setParameter("frequency", freq.getCode().charAt(0));
		String result = (String) q.uniqueResult();
		
		if(result!=null) {
			return StringUtil.mid(result, 5, 2) + "-" + StringUtil.right(result, 2) + "-" + StringUtil.left(result, 4);
		} else {
			return "no pay date";
		}
	}
}
