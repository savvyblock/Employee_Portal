package com.esc20.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.esc20.model.BeaEmpWrkJrnl;

@Repository
public class WrkjlDao extends HibernateDaoSupport{
    @Resource  
    public void setSessionFacotry(SessionFactory sessionFacotry) {
        super.setSessionFactory(sessionFacotry);  
    }
    
    private Session getSession(){
        return super.getSessionFactory().getCurrentSession();
    }
    
	public void save(BeaEmpWrkJrnl wrkjl) {
		Session session = this.getSession();
		session.saveOrUpdate(wrkjl);
        session.flush();
	}
	
	public Object getMaxSeq(String date, String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("select id.seqNbr from BeaEmpWrkJrnl where id.wrkDate = :date and id.empNbr = :empNbr");
		

		Query q = session.createQuery(sql.toString());
		q.setParameter("date", date);
		q.setParameter("empNbr", empNbr);


		@SuppressWarnings("unchecked")
		List<String> list = q.list();
		if(list.isEmpty()) {
			list.add("");
		}
		return list.get(0);
	}
	
	public String getOptionsStartDay() {
			Session session = this.getSession();

			String sql= "SELECT "
					+ "START_DY_OF_WK "
					+ "FROM BHR_WRKJL_OPT ";
		
	        Query q = session.createSQLQuery(sql);
	        @SuppressWarnings("unchecked")
	        List<BigDecimal> res = q.list();
	        String result = "";
	        if(res.size()>0) {
	        	result = res.get(0).toString();
	        }
			return result;

	}
	
	public String getPrcHr(String freq,String min) {
		Session session = this.getSession();

		String sql= "SELECT PCT_OF_HR_DAY FROM BTHR_LV_UNTS_CONV "
				+ "WHERE FOR_WRK_JRNL = 'Y' and  "
				+ "PAY_FREQ = :freq and UP_TO_MIN >= :min ";
		
        Query q = session.createSQLQuery(sql);
        q.setParameter("freq", freq);
        q.setParameter("min", min);
 
        
        @SuppressWarnings("unchecked")
        List<BigDecimal> res = q.list();
		return res.get(0).toString();

	}

	public List<BeaEmpWrkJrnl> getWrkjl(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("from BeaEmpWrkJrnl w where id.empNbr = :empNbr");
		

		Query q = session.createQuery(sql.toString());
		q.setParameter("empNbr", empNbr);


		@SuppressWarnings("unchecked")
		List<BeaEmpWrkJrnl> list = q.list();
	
		return list;
	}

}
