package com.esc20.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.query.spi.sql.NativeSQLQueryReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.esc20.model.BhrCalYtd;
import com.esc20.model.BhrCalYtdId;
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

	public List<BhrCalYtd> getCalenderYTD(String employeeNumber, String year) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("select * FROM bhr_cal_ytd ");
		sql.append("WHERE emp_nbr = :employeeNumber ");
		sql.append("AND CYR_NYR_FLG = 'C' ");
		sql.append("AND cal_yr = :year ");
		SQLQuery q = session.createSQLQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		q.setParameter("year", year);
		List<Object[]> results = (List<Object[]>) q.list();
		List<BhrCalYtd> ret = new ArrayList<BhrCalYtd>();
		results.forEach(res -> {
		BhrCalYtdId id = new BhrCalYtdId((char) res[1], (String) res[2], (char) res[0], (String) res[3]);
			ret.add(new BhrCalYtd(id, (char) res[4], (BigDecimal) res[5], (BigDecimal) res[6], (BigDecimal) res[7],
				(BigDecimal) res[8], (BigDecimal) res[9], (BigDecimal) res[10], (BigDecimal) res[11],
				(BigDecimal) res[12], (BigDecimal) res[13], (BigDecimal) res[14], (BigDecimal) res[15],
				(BigDecimal) res[16], (BigDecimal) res[17], (BigDecimal) res[18], (BigDecimal) res[19],
				(BigDecimal) res[20], (BigDecimal) res[21], (BigDecimal) res[22], (BigDecimal) res[23],
				(BigDecimal) res[24], (BigDecimal) res[25], (BigDecimal) res[26], (BigDecimal) res[27],
				(BigDecimal) res[28], (BigDecimal) res[29], (BigDecimal) res[30], (BigDecimal) res[31],
				(BigDecimal) res[32], (BigDecimal) res[33], (BigDecimal) res[34], (BigDecimal) res[35],
				(BigDecimal) res[36], (BigDecimal) res[37], (BigDecimal) res[38], (BigDecimal) res[39],
				(BigDecimal) res[40], (BigDecimal) res[41], (BigDecimal) res[42], (BigDecimal) res[43],
				(BigDecimal) res[44], (BigDecimal) res[45], (BigDecimal) res[46], (BigDecimal) res[47],
					(String) res[48], (BigDecimal) res[49], (BigDecimal) res[50]));
		});
		return ret;
	}

	public String getLatestPayDate(String employeeNumber, Frequency freq) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct dt_of_pay from bhr_pay_hist where dt_of_pay in (select max(dt_of_pay) from bhr_pay_hist ");
		sql.append("where emp_nbr = :employeeNumber and pay_freq = :frequency)");
		Query q = session.createSQLQuery(sql.toString());
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
