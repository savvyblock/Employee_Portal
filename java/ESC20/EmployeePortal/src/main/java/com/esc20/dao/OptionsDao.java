package com.esc20.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BhrEapOpt;
import com.esc20.nonDBModels.DemoOption;
import com.esc20.nonDBModels.EarningsOther;
import com.esc20.nonDBModels.Options;
import com.esc20.util.StringUtil;

@Repository
public class OptionsDao {
    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
	public Options getOptions() {
		Session session = this.getSession();
		String sql = "FROM BhrEapOpt";
		Query q = session.createQuery(sql);
		BhrEapOpt opt = (BhrEapOpt) q.uniqueResult();
		Options option = new Options(opt);
		return option;
	}
	
	
	public DemoOption getDemoOption() {
		Session session = this.getSession();
		DemoOption option = new DemoOption();
		String sql = "SELECT GRP_NAME, OPT_CD FROM BHR_EAP_DEMO_ASSGN_GRP";
		Query q = session.createSQLQuery(sql);
		List<Object[]> res = q.list();
	    for(Object[] item : res) {
	    	String name = (String) item[0];
	    	String opt =String.valueOf(item[1]);
        	if((!StringUtil.isNullOrEmpty(name)) && name.trim().equals("BEA_LGL_NAME")) {
        		if(!StringUtil.isNullOrEmpty(opt)) {
        			option.setFieldDisplayOptionName(opt);
        		}
        	}
        	
        	if((!StringUtil.isNullOrEmpty(name)) && name.trim().equals("BEA_MAIL_ADDR")) {
        		if(!StringUtil.isNullOrEmpty(opt)) {
        			option.setFieldDisplayOptionMailAddr(opt);
        		}
        	}
        	
        	if((!StringUtil.isNullOrEmpty(name)) && name.trim().equals("BEA_ALT_MAIL_ADDR")) {
        		if(!StringUtil.isNullOrEmpty(opt)) {
        			option.setFieldDisplayOptionAltAddr(opt);
        		}
        	}
        	
        	if((!StringUtil.isNullOrEmpty(name)) && name.trim().equals("BEA_HM_PHONE")) {
        		if(!StringUtil.isNullOrEmpty(opt)) {
        			option.setFieldDisplayOptionHomePhone(opt);
        		}
        	}
        	
        	if((!StringUtil.isNullOrEmpty(name)) && name.trim().equals("BEA_BUS_PHONE")) {
        		if(!StringUtil.isNullOrEmpty(opt)) {
        			option.setFieldDisplayOptionWorkPhone(opt);
        		}
        	}
        	
        	if((!StringUtil.isNullOrEmpty(name)) && name.trim().equals("BEA_CELL_PHONE")) {
        		if(!StringUtil.isNullOrEmpty(opt)) {
        			option.setFieldDisplayOptionCellPhone(opt);
        		}
        	}
        	
        	if((!StringUtil.isNullOrEmpty(name)) && name.trim().equals("BEA_EMAIL")) {
        		if(!StringUtil.isNullOrEmpty(opt)) {
        			option.setFieldDisplayOptionEmail(opt);
        		}
        	}
        	
        	if((!StringUtil.isNullOrEmpty(name)) && name.trim().equals("BEA_RESTRICT")) {
        		if(!StringUtil.isNullOrEmpty(opt)) {
        			option.setFieldDisplayOptionRestrictionCodes(opt);
        		}
        	}
        	
        	if((!StringUtil.isNullOrEmpty(name)) && name.trim().equals("BEA_MRTL_STAT")) {
        		if(!StringUtil.isNullOrEmpty(opt)) {
        			option.setFieldDisplayOptionMarital(opt);
        		}
        	}
        	
        	if((!StringUtil.isNullOrEmpty(name)) && name.trim().equals("BEA_DRVS_LIC")) {
        		if(!StringUtil.isNullOrEmpty(opt)) {
        			option.setFieldDisplayOptionDriversLicense(opt);
        		}
        	}
        	

        	if((!StringUtil.isNullOrEmpty(name)) && name.trim().equals("BEA_EMER_CONTACT")) {
        		if(!StringUtil.isNullOrEmpty(opt)) {
        			option.setFieldDisplayOptionEmergencyContact(opt);
        		}
        	}
        }
	
		return option;
	}


	public String getPayInfoFieldDisplay(String frequency)
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT isnull(opt_cd, 'N') FROM BHR_EAP_PAY_ASSGN_GRP WHERE grp_name = 'BEA_W4' AND pay_freq =:frequency ");
		Query q = session.createSQLQuery(sql.toString());
	    q.setParameter("frequency", frequency);
	    String result; 
	    
	    if(q.list() == null || q.list().isEmpty()) {
	    	result = "N";
        }
	    
        result = String.valueOf(q.list().get(0));
        
        return result;
	}
	
	
	public String getAccountInfoFieldDisplay(String frequency)
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT isnull(opt_cd,'N') FROM BHR_EAP_PAY_ASSGN_GRP WHERE grp_name = 'BEA_DRCT_DPST_BNK_ACCT' AND pay_freq =:frequency");
		Query q = session.createSQLQuery(sql.toString());
	    q.setParameter("frequency", frequency);
    	String result; 
	    
	    if(q.list() == null || q.list().isEmpty()) {
	    	result = "N";
        }
	    
        result = String.valueOf(q.list().get(0));
        
        return result;
	}
	
	public String getActiveEmployee(String employeeNumber, String frequency)
	{
		Session session = this.getSession();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT STAT_CD FROM BHR_EMP_PAY WHERE emp_nbr = :employeeNumber AND pay_freq =:frequency AND CYR_NYR_FLG = 'C'");
		
		Query q = session.createSQLQuery(sql.toString());
	    q.setParameter("employeeNumber", employeeNumber);
	    q.setParameter("frequency", frequency);
		
		String result = String.valueOf(q.list().get(0));
		return result;
	}
	
}
