package com.esc20.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BhrThirdPartySickPay;
import com.esc20.model.BhrW2;

@Repository
public class W2InformationDao {
    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
	public List<String> getAvailableYears(String employeeNumber) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT w2.id.calYr FROM BhrW2 w2 WHERE");
		sql.append(" w2.id.empNbr = :employeeNumber");
		sql.append(" ORDER BY w2.id.calYr DESC");
        Query q = session.createQuery(sql.toString());
        q.setParameter("employeeNumber", employeeNumber);
        @SuppressWarnings("unchecked")
		List<String> years = (List<String>) q.list();
        
        return years;
	}
	
	public String retrieveEA1095ElecConsent(String empNbr) {
		Session session = this.getSession();
		String retrieveSQL = "SELECT A.elecConsntW2 FROM BhrEmpEmply A WHERE A.empNbr = :empNbr";
		Query q = session.createQuery(retrieveSQL);
		q.setParameter("empNbr", empNbr);
		Character result = ((Character)q.uniqueResult());
		String res = result==null?"":result.toString();
		
		if(res!=null)
			return res.trim();
		else
			return "";
	}
	
	
	public BhrW2 getW2Info(String employeeNumber, String year) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append(" FROM BhrW2 w2");
		sql.append(" WHERE w2.id.empNbr = :employeeNumber");
		sql.append(" AND w2.id.calYr = :year");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		q.setParameter("year", year);
		BhrW2 w2 = (BhrW2)q.uniqueResult();
		return w2;
	}
	
	public List<BhrThirdPartySickPay> getSickPayInfo(String employeeNumber, String year)
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append(" FROM BhrThirdPartySickPay pay");
		sql.append(" WHERE pay.id.empNbr = :employeeNumber");
		sql.append(" AND pay.id.calYr = :year");
		sql.append(" AND pay.id.cyrNyrFlg = 'C'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		q.setParameter("year", year);
		@SuppressWarnings("unchecked")
		List<BhrThirdPartySickPay> res = q.list();
		
		return res;
	}
	
	public boolean updateW2ElecConsent(String employeeNumber, String elecConsntW2) {
		Session session = this.getSession();
		String updateW2ElecConsntSql = "UPDATE BhrEmpEmply SET elecConsntW2 =:elecConsntW2, module = 'EmployeePortal' WHERE empNbr =:employeeNumber";
		Query q = session.createQuery(updateW2ElecConsntSql);
		q.setParameter("employeeNumber", employeeNumber);
		q.setParameter("elecConsntW2", elecConsntW2.charAt(0));
		Integer res = q.executeUpdate();
		session.flush();
		return res>0;
	}
	
	public BigDecimal getThirdPartySickPay(String employeeNumber, String year)
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("select(ISNULL( sum(bhr_third_party_sick_pay.sick_pay_wh_gross +   ");
		sql.append("		bhr_third_party_sick_pay.sick_pay_wh_tax +   ");
		sql.append("		bhr_third_party_sick_pay.sick_pay_fica_gross +   ");
		sql.append("		bhr_third_party_sick_pay.sick_pay_fica_tax +  ");
		sql.append("		bhr_third_party_sick_pay.sick_pay_med_gross +  ");
		sql.append("		bhr_third_party_sick_pay.sick_pay_med_tax +  ");
		sql.append("		bhr_third_party_sick_pay.sick_nontax ), 0.00))");
		sql.append("      from bhr_third_party_sick_pay ");
		sql.append("     where (bhr_third_party_sick_pay.emp_nbr = :as_emp_nbr) AND");
		//sql.append("		   (bhr_third_party_sick_pay.emp_nbr = bhr_cal_ytd.emp_nbr) AND");
		//sql.append("           (bhr_third_party_sick_pay.emp_nbr = bhr_emp_pay.emp_nbr) AND");
		sql.append("           (bhr_third_party_sick_pay.cyr_nyr_flg = :as_cyr_nyr) AND");
		sql.append("           (bhr_third_party_sick_pay.cal_yr = :as_yr OR TRIM(:as_yr) = '')");
		//sql.append("           AND ( :an_pay_nbr = 0 OR bhr_third_party_sick_pay.pay_freq IN ( :as_payfreq ) ) )");
		
		Query q = session.createSQLQuery(sql.toString());
		q.setParameter("as_cyr_nyr","C");
		q.setParameter("as_emp_nbr",employeeNumber);
		q.setParameter("as_yr",year);
		return (BigDecimal)q.uniqueResult();
	}
}
