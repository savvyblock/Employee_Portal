package com.esc20.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BhrAca1095bCovrdHist;
import com.esc20.model.BhrAca1095cCovrdHist;
import com.esc20.model.BhrAca1095cEmpHist;
@Repository
public class EA1095Dao {

    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession(){
        return sessionFactory.openSession();
    }
    
    private final Integer pageSize = 20;
    
	public List<String> getAvailableYears(String employeeNumber) {
		Session session = this.getSession();
		String sql = "SELECT DISTINCT bHist.id.calYr FROM BhrAca1095bEmpHist bHist WHERE bHist.id.empNbr = :employeeNumber "
				+ "UNION SELECT DISTINCT cHist.id.calYr FROM BhrAca1095cEmpHist cHist WHERE cHist.id.empNbr = :employeeNumber ORDER BY cHist.id.calYr DESC ";
        Query q = session.createQuery(sql);
        q.setParameter("employeeNumber", employeeNumber);
        List<String> years = (List<String>) q.list();
        session.close();
        return years;
	}

	public String get1095Consent(String employeeNumber) {
		Session session = this.getSession();
		String retrieveSQL = "SELECT elecConsnt1095 FROM BhrEmpEmply A WHERE A.empNbr = :employeeNumber";
        Query q = session.createQuery(retrieveSQL);
        q.setParameter("employeeNumber", employeeNumber);
        String result = (String) q.uniqueResult();
        session.close();
        return result;
	}

	public List<BhrAca1095bCovrdHist> retrieveEA1095BInfo(String employeeNumber, String year, String sortBy, String sortOrder, Integer bPageNo) {
		Session session = this.getSession();
		String retrieveSQL = "FROM BhrAca1095bCovrdHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr ";
        Query q = session.createQuery(retrieveSQL);
        if(sortBy!=null) {
        	retrieveSQL += "order by :=sortBy :=sortOrder";
            q.setParameter("sortBy", sortBy);
            q.setParameter("sortOrder", sortOrder==null?"asc":sortOrder);
        }
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
        q.setFirstResult(bPageNo*pageSize);  
        q.setMaxResults(pageSize);
        List<BhrAca1095bCovrdHist> result = q.list();
        session.close();
        return result;
	}

	public List<BhrAca1095cCovrdHist> retrieveEA1095CInfo(String employeeNumber, String year, String sortBy, String sortOrder, Integer cPageNo) {
		Session session = this.getSession();
		String retrieveSQL = "FROM BhrAca1095cCovrdHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr ";
        Query q = session.createQuery(retrieveSQL);
        if(sortBy!=null) {
        	retrieveSQL += "order by :=sortBy :=sortOrder";
            q.setParameter("sortBy", sortBy);
            q.setParameter("sortOrder", sortOrder==null?"asc":sortOrder);
        }
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
        q.setFirstResult(cPageNo*pageSize);  
        q.setMaxResults(pageSize);  
        List<BhrAca1095cCovrdHist> result = q.list();
        session.close();
        return result;
	}

	public List<String> retrieveEA1095BEmpInfo(String employeeNumber, String year) {
		Session session = this.getSession();
		StringBuilder retrieveSQL = new StringBuilder();
		retrieveSQL.append("SELECT A.covrgTyp , (SELECT B.covrgTypDesc FROM BthrAca1095bCovrgTyp B WHERE B.id.calYr = :calYr AND B.id.covrgTyp = A.covrgTyp )");
		retrieveSQL.append("FROM BhrAca1095bEmpHist A ");
		retrieveSQL.append("WHERE A.id.empNbr = :employeeNumber AND ");
		retrieveSQL.append("A.id.calYr = :calYr ");
        Query q = session.createQuery(retrieveSQL.toString());
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
        List<String> result = q.list();
        session.close();
		return result;
	}

	public List<BhrAca1095cEmpHist> retrieveEA1095CEmpInfo(String employeeNumber, String year) {
		Session session = this.getSession();
		String retrieveSQL = "FROM BhrAca1095cEmpHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr ";
        Query q = session.createQuery(retrieveSQL);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year); 
        List<BhrAca1095cEmpHist> result = q.list();
        session.close();
        return result;
	}

	public Integer getBInfoTotal(String employeeNumber, String year) {
		Session session = this.getSession();
		String retrieveSQL = "select count(*) FROM BhrAca1095bCovrdHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr";
        Query q = session.createQuery(retrieveSQL);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
        Integer result = ((Long) q.iterate().next()).intValue();
        session.close();
        return result;
	}

	public Integer getCInfoTotal(String employeeNumber, String year) {
		Session session = this.getSession();
		String retrieveSQL = "select count(*) FROM BhrAca1095cCovrdHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr";
        Query q = session.createQuery(retrieveSQL);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
        Integer result = ((Long) q.iterate().next()).intValue();
        session.close();
        return result;
	}
}
