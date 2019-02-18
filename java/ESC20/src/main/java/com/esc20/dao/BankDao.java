package com.esc20.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BthrBankCodes;
import com.esc20.nonDBModels.Bank;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Money;
import com.esc20.nonDBModels.Page;
import com.esc20.util.NumberUtil;
import com.esc20.util.StringUtil;

@Repository
public class BankDao {
    @Autowired
    private SessionFactory sessionFactory;
    
    private Session getSession(){
        return sessionFactory.openSession();
    }
    
    public List<BthrBankCodes> getAll() {
    	Session session = this.sessionFactory.getCurrentSession();
        String hql = "from BthrBankCodes order by bankName ASC" ;
        Query q = session.createQuery(hql);
        List<BthrBankCodes> result = q.list();
        if(result== null || result.isEmpty()) {
        	return null;
        }
    	return result;
    }
    
    public List<BthrBankCodes> getAll(Page p) {
    	Session session = this.sessionFactory.getCurrentSession();
        String hql = "from BthrBankCodes order by bankName ASC" ;
        Query q = session.createQuery(hql);
        q.setFirstResult((p.getCurrentPage()-1)*p.getPerPageRows());
        q.setMaxResults(p.getPerPageRows());
        
        List<BthrBankCodes> result = q.list();
        if(result== null || result.isEmpty()) {
        	return null;
        }
    	return result;
    }
    
    public List<BthrBankCodes> getBanksByEntity(BthrBankCodes bbc) {
    	Session session = this.sessionFactory.getCurrentSession();
        String hql = "from BthrBankCodes where 1=1 " ;
        
        if(bbc.getBankName()!=null && !bbc.getBankName().isEmpty()) {
        	hql = hql + "and bankName=" + bbc.getBankName();
        }
        if(bbc.getTransitRoute()!=null && !bbc.getBankName().isEmpty()) {
        	hql = hql + "and transitRoute=" + bbc.getTransitRoute();
        }
        hql = hql + " order by bankName ASC";
        
        Query q = session.createQuery(hql);
        List<BthrBankCodes> result = q.list();
        if(result== null || result.isEmpty()) {
        	return null;
        }
    	return result;
    }
    
    public Boolean getAutoApproveAccountInfo(String tableName) {
		Session session = this.getSession();
		Query q;
		String sql= " SELECT DISTINCT auto_apprv FROM BHR_EAP_PAY_ASSGN_GRP WHERE grp_name = 'BEA_DRCT_DPST_BNK_ACCT'"; // AND pay_freq =:frequency
        q = session.createQuery(sql);
        //q.setParameter("tableName", tableName);
        Character res = (Character) q.uniqueResult();
        session.close();
        return ("Y").equals(res.toString());
	}
    
    public List<Bank> getAccounts(String employeeNumber, String frequency)
	{
    	List<Bank> bankList = new ArrayList<Bank>();
    	Session session = this.getSession();
		Query q;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT BHR_BANK_DEPOSIT.BANK_CD,");
		sql.append("BTHR_BANK_CODES.BANK_NAME,");
		sql.append("BTHR_BANK_CODES.TRANSIT_ROUTE,");
		sql.append("BHR_BANK_DEPOSIT.PAY_FREQ,");
		sql.append("BHR_BANK_DEPOSIT.BANK_ACCT_NBR,"); //4
		sql.append("BHR_BANK_DEPOSIT.BANK_ACCT_TYP,");
		sql.append("BTHR_BANK_ACCT_TYP.BANK_ACCT_TYP_DESCR,");
		sql.append("BHR_BANK_DEPOSIT.BANK_ACCT_AMT");
		sql.append(" FROM BHR_BANK_DEPOSIT,");
		sql.append("BTHR_BANK_CODES,");
		sql.append("BTHR_BANK_ACCT_TYP");
		sql.append(" WHERE EMP_NBR = :employeeNumber AND pay_freq= :freq");
		sql.append(" AND CYR_NYR_FLG = 'C'");
		sql.append(" AND BHR_BANK_DEPOSIT.BANK_CD = BTHR_BANK_CODES.BANK_CD");
		sql.append(" AND BHR_BANK_DEPOSIT.BANK_ACCT_TYP = BTHR_BANK_ACCT_TYP.BANK_ACCT_TYP ORDER BY BTHR_BANK_CODES.BANK_NAME ASC ");
		q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
//		q.setParameter("freq", frequency.getCode());
		q.setParameter("freq", frequency);

		List<Object[]> res = q.list();
		
		for(Object[] item: res) {
			Bank b = new Bank();
			b.setFrequency(Frequency.getFrequency(StringUtil.trim(item[3])));
			
			Code code = new Code();
			code.setCode(StringUtil.trim(item[0]));
			code.setDescription(StringUtil.trim(item[1]));
			code.setSubCode(StringUtil.trim(item[2]));
			b.setCode(code);
			b.setAccountNumber(StringUtil.trim(item[4]));
			
			Code accountType = new Code();
			accountType.setCode(StringUtil.trim(item[5]));
			accountType.setDescription(StringUtil.trim(item[6]));
			b.setAccountType(accountType);
			b.setDepositAmount(new Money(Double.valueOf((item[7]).toString()), Currency.getInstance(Locale.US)));
			
			bankList.add(b);
		}
		
		return bankList;
		
	}
	
}
