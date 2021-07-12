package com.esc20.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class LicenseAgreementDao {
    @Autowired
    private SessionFactory sessionFactory;
    
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    public boolean getLicense(String userName) {
		boolean flag = false;
		StringBuilder sql = new StringBuilder("SELECT LIC_ACCPT FROM BEA_USERS WHERE USRNAME =:userName AND LIC_ACCPT = 'Y'");

		SQLQuery q = this.getSession().createSQLQuery(sql.toString());
		q.setParameter("userName", userName);
		List<Object[]> res = q.list();
		if(res != null && !res.isEmpty()) {
			flag = true;
		}
		return flag;
	}


	public Date getLicenseDate(String userName) {
		Date LIC_ACCPT_DT = null;
		StringBuilder sql = new StringBuilder("SELECT LIC_ACCPT_DT FROM BEA_USERS WHERE USRNAME =:userName AND LIC_ACCPT = 'Y'");

		SQLQuery q = this.getSession().createSQLQuery(sql.toString());
		q.setParameter("userName", userName);
		List<Map<String, Object>> result;
		try {
			List<Object[]> res = q.list();
			for(Object o : res) {
				LIC_ACCPT_DT = (Date)o;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LIC_ACCPT_DT;
	}

	public boolean setLicense(String userName) {
		StringBuilder sql = new StringBuilder("UPDATE BEA_USERS SET LIC_ACCPT='Y' , LIC_ACCPT_DT = getdate()  WHERE USRNAME=:userName ");

		SQLQuery q = this.getSession().createSQLQuery(sql.toString());
		q.setParameter("userName", userName);
		try {
			int i = q.executeUpdate();
			return i > 0;
		} catch (Exception e) {
			return false;
		}
	}
}
