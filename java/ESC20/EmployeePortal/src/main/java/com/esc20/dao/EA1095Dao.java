package com.esc20.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.controller.W2InformationController;
import com.esc20.model.BhrAca1095bCovrdHist;
import com.esc20.model.BhrAca1095cCovrdHist;
import com.esc20.model.BhrAca1095cEmpHist;
import com.esc20.model.BrRptngContact;
import com.esc20.nonDBModels.BCoveredHistory;
import com.esc20.nonDBModels.CCoveredHistory;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.EA1095CEmployerShare;
import com.esc20.util.ConstUtil;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;
@Repository
public class EA1095Dao {
	private Logger logger = LoggerFactory.getLogger(EA1095Dao.class);

    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    //set it in ConstUtil now
    //private final Integer pageSize = 20;
    
	public List<String> getAvailableYears(String employeeNumber) {
		Session session = this.getSession();
		String sql = "SELECT DISTINCT CAL_YR FROM BHR_ACA_1095B_EMP_HIST WHERE EMP_NBR = :empNbr UNION SELECT DISTINCT CAL_YR FROM BHR_ACA_1095C_EMP_HIST WHERE EMP_NBR = :empNbr ORDER BY CAL_YR DESC ";
        Query q = session.createSQLQuery(sql);
        q.setParameter("empNbr", employeeNumber);
        @SuppressWarnings("unchecked")
		List<String> years = (List<String>) q.list();
        
        return years;
	}

	public String get1095Consent(String employeeNumber) {
		Session session = this.getSession();
		String retrieveSQL = "SELECT ELEC_CONSNT_1095 FROM BHR_EMP_EMPLY where EMP_NBR = :employeeNumber";
        Query q = session.createSQLQuery(retrieveSQL);
        q.setParameter("employeeNumber", employeeNumber);
		Character result = ((Character)q.uniqueResult());
		String res = result==null?"":result.toString();
		
		return res.trim();
		
	}

	public boolean update1095ElecConsent(String employeeNumber, String elecConsnt1095) {
		Session session = this.getSession();
		String update1095ElecConsntSql = "UPDATE BhrEmpEmply SET elecConsnt1095 =:elecConsnt1095, module = 'Employee Portal' WHERE empNbr =:employeeNumber";
		Query q = session.createQuery(update1095ElecConsntSql);
		q.setParameter("employeeNumber", employeeNumber);
		q.setParameter("elecConsnt1095", elecConsnt1095.charAt(0));
		Integer res = q.executeUpdate();
		session.flush();
		return res>0;
	}
	
	public List<BCoveredHistory> retrieveEA1095BInfo(String employeeNumber, String year, String sortBy, String sortOrder, Integer bPageNo) {
		Session session = this.getSession();
		Integer pageSize = ConstUtil.getPageSize();
		String retrieveSQL = "FROM BhrAca1095bCovrdHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr order by NAME_F ASC ";
		Query q = session.createQuery(retrieveSQL);
        if(sortBy!=null && (!("").equals(sortBy))) {
        	retrieveSQL += ", :=sortBy :=sortOrder";
            q.setParameter("sortBy", sortBy);
            q.setParameter("sortOrder", sortOrder==null?"asc":sortOrder);
        }
      
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
        q.setFirstResult((bPageNo-1)*pageSize);  
        q.setMaxResults(pageSize);
        @SuppressWarnings("unchecked")
		List<BhrAca1095bCovrdHist> result = q.list();
        List<BCoveredHistory> res = new ArrayList<BCoveredHistory>();
        for(BhrAca1095bCovrdHist item : result){
        	res.add(new BCoveredHistory(item));
        }
        return res;
	}
	
	public List<BCoveredHistory> retrieveAll1095BInfo(String employeeNumber, String year) {
		Session session = this.getSession();
		String retrieveSQL = "FROM BhrAca1095bCovrdHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr order by NAME_F ASC ";
		Query q = session.createQuery(retrieveSQL);
        
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
      
        @SuppressWarnings("unchecked")
		List<BhrAca1095bCovrdHist> result = q.list();
        List<BCoveredHistory> res = new ArrayList<BCoveredHistory>();
        for(BhrAca1095bCovrdHist item : result){
        	res.add(new BCoveredHistory(item));
        }
        return res;
	}

	public List<CCoveredHistory> retrieveEA1095CInfo(String employeeNumber, String year, String sortBy, String sortOrder, Integer cPageNo) {
		Session session = this.getSession();
		Integer pageSize = ConstUtil.getPageSize();
		String retrieveSQL = "FROM BhrAca1095cCovrdHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr order by  EMP_FLG DESC, NAME_F ASC ";
        Query q = session.createQuery(retrieveSQL);
        if(sortBy!=null && (!("").equals(sortBy))) {
        	retrieveSQL += ", :=sortBy :=sortOrder";
            q.setParameter("sortBy", sortBy);
            q.setParameter("sortOrder", sortOrder==null?"asc":sortOrder);
        }
       
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
        q.setFirstResult((cPageNo-1)*pageSize);  
        q.setMaxResults(pageSize);
        @SuppressWarnings("unchecked")
		List<BhrAca1095cCovrdHist> result = q.list();
        List<CCoveredHistory> res = new ArrayList<CCoveredHistory>();
        for(BhrAca1095cCovrdHist item : result){
        	res.add(new CCoveredHistory(item));
        }
        return res;
	}
	
	public List<CCoveredHistory> retrieveALL1095CInfo(String employeeNumber, String year) {
		Session session = this.getSession();
		String retrieveSQL = "FROM BhrAca1095cCovrdHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr order by  EMP_FLG DESC, NAME_F ASC ";
        Query q = session.createQuery(retrieveSQL);
       
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
        @SuppressWarnings("unchecked")
		List<BhrAca1095cCovrdHist> result = q.list();
        List<CCoveredHistory> res = new ArrayList<CCoveredHistory>();
        for(BhrAca1095cCovrdHist item : result){
        	res.add(new CCoveredHistory(item));
        }
        return res;
	}

	public List<Code> retrieveEA1095BEmpInfo(String employeeNumber, String year) {
		Session session = this.getSession();
		StringBuilder retrieveSQL = new StringBuilder();
		retrieveSQL.append("SELECT A.covrgTyp , (SELECT B.covrgTypDesc FROM BthrAca1095bCovrgTyp B WHERE B.id.calYr = :calYr AND B.id.covrgTyp = A.covrgTyp )");
		retrieveSQL.append("FROM BhrAca1095bEmpHist A ");
		retrieveSQL.append("WHERE A.id.empNbr = :employeeNumber AND ");
		retrieveSQL.append("A.id.calYr = :calYr ");
        Query q = session.createQuery(retrieveSQL.toString());
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year);
        @SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
		List<Code> result = new ArrayList<Code>();
		Code code;
		for(Object[] item: res) {
			code = new Code(((Character)item[0]==null?"":((Character)item[0]).toString()),"",(String)item[1]);
			result.add(code);		
		}
		return result;
	}

	public List<EA1095CEmployerShare> retrieveEA1095CEmpInfo(String employeeNumber, String year) {
		Session session = this.getSession();
		String retrieveSQL = "FROM BhrAca1095cEmpHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr order by A.id.empNbr desc, A.id.calYr desc, A.id.calMon desc";
        Query q = session.createQuery(retrieveSQL);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year); 
        @SuppressWarnings("unchecked")
		List<BhrAca1095cEmpHist> result = q.list();
        List<EA1095CEmployerShare> shareResult = new ArrayList<EA1095CEmployerShare>();
        EA1095CEmployerShare share;
        String empNbr = "";
        for(int i=0;i<result.size();i++) {
        	if(!empNbr.equals(result.get(i).getId().getEmpNbr().trim())) {
        		empNbr = result.get(i).getId().getEmpNbr().trim();
        		//logger.info("1095 C empNbr: " + empNbr);
        		share = new EA1095CEmployerShare();
        		share.setYear(result.get(i).getId().getCalYr().trim());
        		for(int j=0;j<result.size();j++) {
        			if(result.get(i).getId().getEmpNbr().trim().equals(result.get(j).getId().getEmpNbr().trim()) && 
        					result.get(i).getId().getCalYr().trim().equals(result.get(j).getId().getCalYr().trim())) {
        				//logger.info("1095 C Month: '" + result.get(j).getId().getCalMon()+"'");
        				if(result.get(j).getId().getCalMon().trim().equals("01"))
        					share.setMon01(result.get(j));
        				if(result.get(j).getId().getCalMon().trim().equals("02"))
        					share.setMon02(result.get(j));
        				if(result.get(j).getId().getCalMon().trim().equals("03"))
        					share.setMon03(result.get(j));
        				if(result.get(j).getId().getCalMon().trim().equals("04"))
        					share.setMon04(result.get(j));
        				if(result.get(j).getId().getCalMon().trim().equals("05"))
        					share.setMon05(result.get(j));
        				if(result.get(j).getId().getCalMon().trim().equals("06"))
        					share.setMon06(result.get(j));
        				if(result.get(j).getId().getCalMon().trim().equals("07"))
        					share.setMon07(result.get(j));
        				if(result.get(j).getId().getCalMon().trim().equals("08"))
        					share.setMon08(result.get(j));
        				if(result.get(j).getId().getCalMon().trim().equals("09"))
        					share.setMon09(result.get(j));
        				if(result.get(j).getId().getCalMon().trim().equals("10"))
        					share.setMon10(result.get(j));
        				if(result.get(j).getId().getCalMon().trim().equals("11"))
        					share.setMon11(result.get(j));
        				if(result.get(j).getId().getCalMon().trim().equals("12")) {
        					share.setMon12(result.get(j));
        					//logger.info("1095 C Mon12 Amount : '" + share.getMon12().getEmpShr()+"'");
        				}
        					
        				if(result.get(j).getId().getCalMon().trim().equals("ALL"))
        					share.setMonAll(result.get(j));
        			}
        		}
        		shareResult.add(share);
        	}
        }
        return shareResult;
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
	
	public BrRptngContact getReportingContact() {
		Session session = this.getSession();
		StringBuffer sql = new StringBuffer();
		sql.append("FROM BrRptngContact A ");
		sql.append("WHERE A.type = '1095ELECTRONIC' ");
		Query q = session.createQuery(sql.toString());
		BrRptngContact res = (BrRptngContact) q.uniqueResult();
		return res;
	}

	public List<BhrAca1095cEmpHist> retrieveEA1095CEmpInfoPrint(String employeeNumber, String year) {
		Session session = this.getSession();
		String retrieveSQL = "FROM BhrAca1095cEmpHist A WHERE A.id.empNbr = :employeeNumber and A.id.calYr= :calYr order by A.id.empNbr desc, A.id.calYr desc, A.id.calMon";
        Query q = session.createQuery(retrieveSQL);
        q.setParameter("employeeNumber", employeeNumber);
        q.setParameter("calYr", year); 
        @SuppressWarnings("unchecked")
		List<BhrAca1095cEmpHist> result = q.list();
        return result;
	}
}
