package com.esc20.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.nonDBModels.Deduction;
import com.esc20.nonDBModels.Frequency;

@Repository
public class DeductionsDao {

    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
	public List<Frequency> getAvailableFrequencies(String employeeNumber)
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT PAY_FREQ FROM BHR_EMP_DEDUCT");
		sql.append(" WHERE CYR_NYR_FLG = 'C'");
		sql.append(" AND EMP_NBR = :employeeNumber");
		sql.append(" UNION ");
		sql.append("SELECT DISTINCT PAY_FREQ FROM BHR_EMP_PAY");
		sql.append(" WHERE CYR_NYR_FLG = 'C'");
		sql.append(" AND EMP_NBR = :employeeNumber");
		Query q = session.createSQLQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		@SuppressWarnings("unchecked")
		List<Character> res = q.list();
		List<Frequency> result = new ArrayList<Frequency>();
		for(Character code: res) {
			result.add(Frequency.getFrequency((code.toString()).trim()));
		}
		
		return result;
	}

	public List<Deduction> getDeductions(String employeeNumber, Frequency frequency) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT deduct.id.dedCd, (SELECT cd.shortDescr FROM BthrDeducCd cd WHERE cd.dedCd = deduct.id.dedCd) AS SHORT_DESCR, ");
		sql.append(" deduct.empAmt, deduct.emplrAmt, deduct.cafeFlg");
		sql.append(" FROM BhrEmpDeduct deduct");
		sql.append(" WHERE deduct.id.cyrNyrFlg = 'C'");
		sql.append(" AND deduct.id.payFreq = :frequency");
		sql.append(" AND deduct.id.empNbr = :employeeNumber");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		q.setParameter("frequency", frequency.getCode().charAt(0));
		@SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
		List<Deduction> result = new ArrayList<Deduction>();
		Deduction deduct;
		for(Object[] item: res) {
			deduct= new Deduction(item[0],item[1],item[2],item[3],item[4]);
			result.add(deduct);
		}
		
		return result;
	}
}
