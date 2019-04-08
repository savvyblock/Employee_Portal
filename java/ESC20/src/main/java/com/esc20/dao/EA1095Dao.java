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
        return sessionFactory.getCurrentSession();
    }
    
    private final Integer pageSize = 20;
    
	public List<String> getAvailableYears(String employeeNumber) {
		Session session = this.getSession();
		String sql = "SELECT DISTINCT CAL_YR FROM BHR_ACA_1095B_EMP_HIST WHERE EMP_NBR = :empNbr UNION SELECT DISTINCT CAL_YR FROM BHR_ACA_1095C_EMP_HIST WHERE EMP_NBR = :empNbr ORDER BY CAL_YR DESC ";
        Query q = session.createSQLQuery(sql);
        q.setParameter("empNbr", employeeNumber);
        List<String> years = (List<String>) q.list();
        
        return years;
	}

	public String get1095Consent(String employeeNumber) {
		Session session = this.getSession();
		String retrieveSQL = "SELECT elecConsnt1095 FROM BhrEmpEmply A WHERE A.empNbr = :employeeNumber";
        Query q = session.createQuery(retrieveSQL);
        q.setParameter("employeeNumber", employeeNumber);
		Character result = ((Character)q.uniqueResult());
		String res = result==null?"N":result.toString();
		
		if(res!=null)
			return res.trim();
		else
			return "";
	}

	public boolean update1095ElecConsent(String employeeNumber, String elecConsnt1095) {
		Session session = this.getSession();
		String update1095ElecConsntSql = "UPDATE BhrEmpEmply SET elecConsnt1095 =:elecConsnt1095, module = 'Employee Access' WHERE empNbr =:employeeNumber";
		Query q = session.createQuery(update1095ElecConsntSql);
		q.setParameter("employeeNumber", employeeNumber);
		q.setParameter("elecConsnt1095", elecConsnt1095.charAt(0));
		Integer res = q.executeUpdate();
		session.flush();
		return res>0;
	}
	
	public List<BhrAca1095bCovrdHist> retrieveEA1095BInfo(String employeeNumber, String year, String sortBy, String sortOrder, Integer bPageNo) {
		Session session = this.getSession();
		String retrieveSQL = "FROM BhrAca1095bCovrdHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr ";
        Query q = session.createQuery(retrieveSQL);
        if(sortBy!=null && (!("").equals(sortBy))) {
        	retrieveSQL += "order by :=sortBy :=sortOrder";
            q.setParameter("sortBy", sortBy);
            q.setParameter("sortOrder", sortOrder==null?"asc":sortOrder);
        }
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
        q.setFirstResult(bPageNo*pageSize);  
        q.setMaxResults(pageSize);
        List<BhrAca1095bCovrdHist> result = q.list();
        
        return result;
	}

	public List<BhrAca1095cCovrdHist> retrieveEA1095CInfo(String employeeNumber, String year, String sortBy, String sortOrder, Integer cPageNo) {
		Session session = this.getSession();
		String retrieveSQL = "FROM BhrAca1095cCovrdHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr ";
        Query q = session.createQuery(retrieveSQL);
        if(sortBy!=null && (!("").equals(sortBy))) {
        	retrieveSQL += "order by :=sortBy :=sortOrder";
            q.setParameter("sortBy", sortBy);
            q.setParameter("sortOrder", sortOrder==null?"asc":sortOrder);
        }
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
        q.setFirstResult(cPageNo*pageSize);  
        q.setMaxResults(pageSize);  
        List<BhrAca1095cCovrdHist> result = q.list();
        
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
        
		return result;
	}

	public List<BhrAca1095cEmpHist> retrieveEA1095CEmpInfo(String employeeNumber, String year) {
		Session session = this.getSession();
		String retrieveSQL = "FROM BhrAca1095cEmpHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr ";
        Query q = session.createQuery(retrieveSQL);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year); 
        List<BhrAca1095cEmpHist> result = q.list();
        
        return result;
	}

	public Integer getBInfoTotal(String employeeNumber, String year) {
		Session session = this.getSession();
		String retrieveSQL = "select count(*) FROM BhrAca1095bCovrdHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr";
        Query q = session.createQuery(retrieveSQL);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
        Integer result = ((Long) q.iterate().next()).intValue();
        
        return result;
	}

	public Integer getCInfoTotal(String employeeNumber, String year) {
		Session session = this.getSession();
		String retrieveSQL = "select count(*) FROM BhrAca1095cCovrdHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr";
        Query q = session.createQuery(retrieveSQL);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
        Integer result = ((Long) q.iterate().next()).intValue();
        
        return result;
	}
}
