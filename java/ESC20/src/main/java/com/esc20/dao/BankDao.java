package com.esc20.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BthrBankCodes;
import com.esc20.nonDBModels.Bank;
import com.esc20.nonDBModels.BankRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.Criteria;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Money;
import com.esc20.nonDBModels.Page;
import com.esc20.util.StringUtil;
import com.esc20.util.TimestampConverter;

@Repository
public class BankDao {
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private ReferenceDao referenceDao;
    
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    public List<BthrBankCodes> getAll() {
    	Session session = this.getSession();
        String hql = "from BthrBankCodes order by bankName ASC" ;
        Query q = session.createQuery(hql);
        List<BthrBankCodes> result = q.list();
        if(result== null || result.isEmpty()) {
        	return null;
        }
    	return result;
    }
    
    public List<BthrBankCodes> getAll(Page p) {
    	Session session = this.getSession();
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
    
    public List<BthrBankCodes> getAll(Criteria c, Page p) {
    	Session session = this.getSession();
        String hql = "from BthrBankCodes where 1=1 ";
        
        if(c.getSearchCode() !=null && !c.getSearchCode().isEmpty()) {
        	hql = hql + " and transitRoute like '%" + c.getSearchCode()+"%'";
        }
        if(c.getSearchDescription() !=null && !c.getSearchDescription().isEmpty()) {
        	hql = hql + " and bankName like '%" + c.getSearchDescription()+"%'";
        }
        
        hql = hql +" order by bankName ASC" ;
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
    	Session session = this.getSession();
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
    
   
    
    public List<BankRequest> getAccountRequests(String employeeNumber, String frequency){
    	List<BankRequest> bankList = new ArrayList<BankRequest>();
    	Session session = this.getSession();
		Query q;
		
		StringBuilder sql = new StringBuilder();
    	sql.append("SELECT DISTINCT BEA_DRCT_DPST_BNK_ACCT.bnk_cd, BTHR_BANK_CODES.bank_name, BTHR_BANK_CODES.transit_route, BEA_DRCT_DPST_BNK_ACCT.bnk_acct_nbr, ");
		sql.append("BEA_DRCT_DPST_BNK_ACCT.bnk_acct_typ, BTHR_BANK_ACCT_TYP.bank_acct_typ_descr, BEA_DRCT_DPST_BNK_ACCT.bnk_acct_amt, ");
		sql.append(" BEA_DRCT_DPST_BNK_ACCT.bnk_cd_new, BEA_DRCT_DPST_BNK_ACCT.bnk_acct_nbr_new, ");
		sql.append("BEA_DRCT_DPST_BNK_ACCT.bnk_acct_typ_new, BTHR_BANK_ACCT_TYP.bank_acct_typ_descr as bank_acct_typ_descr_new, BEA_DRCT_DPST_BNK_ACCT.bnk_acct_amt_new ");
		sql.append("FROM BEA_DRCT_DPST_BNK_ACCT ");
		sql.append("LEFT JOIN BTHR_BANK_CODES ON  BTHR_BANK_CODES.bank_cd = BEA_DRCT_DPST_BNK_ACCT.bnk_cd ");
		sql.append("LEFT JOIN BTHR_BANK_ACCT_TYP ON  BTHR_BANK_ACCT_TYP.bank_acct_typ = BEA_DRCT_DPST_BNK_ACCT.bnk_acct_typ ");
		
		sql.append("WHERE emp_nbr = :employeeNumber AND pay_freq =:frequency AND STAT_CD = 'P'");
		q = session.createSQLQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
		q.setParameter("frequency", frequency);
		
		List<Object[]> res = q.list();
		
		for(Object[] item: res) {
			
			BankRequest tempBank = new BankRequest();
			
			tempBank.setCode(getBank(StringUtil.trim(item[0])));
			tempBank.setAccountNumber(StringUtil.trim(item[3]));
			tempBank.setAccountType(getDdAccountType(StringUtil.trim(item[4])));
			tempBank.setDepositAmount(new Money(new Double (item[6].toString()).doubleValue(), Currency.getInstance(Locale.US)));
			
			tempBank.setCodeNew(getBank(StringUtil.trim(item[7])));
			tempBank.setAccountNumberNew(StringUtil.trim(item[8]));
			tempBank.setAccountTypeNew(getDdAccountType(StringUtil.trim(item[9])));
			tempBank.setDepositAmountNew(new Money(new Double (item[11].toString()).doubleValue(), Currency.getInstance(Locale.US)));
			if(tempBank.getAccountNumberNew().equals("") && tempBank.getAccountTypeNew().getCode().equals("") 
					&& tempBank.getCodeNew().getCode().equals("") && tempBank.getDepositAmountNew().getAmount() == 0)
			{
				tempBank.setIsDelete(true);
			}
			bankList.add(tempBank);
			
		}
		
		return bankList;
		
		
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
		q = session.createSQLQuery(sql.toString());
		q.setParameter("employeeNumber", employeeNumber);
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
    
  public Code getDdAccountType(String t) {
    	
    	List<Code> types = referenceDao.getDdAccountTypes();
    	for(Code c:types) {
    		if(c.getCode().equals(t)) {
    			return c;
    		}
    	}
    	
    	return new Code();
    }
    
    public Code getBank(String b) {
    	List<Code> banks = referenceDao.getAvailableBanks();
    	for(Code c:banks) {
    		if(c.getCode().equals(b)) {
    			return c;
    		}
    	}
    	
    	return new Code();
    }
    
    public Code getFreq(String b) {
    	List<Code> freqs = referenceDao.getAvailableBanks();
    	for(Code c:freqs) {
    		if(c.getCode().equals(b)) {
    			return c;
    		}
    	}
    	
    	return new Code();
    }
    
    public Code getPayrollFrequencies(String empNbr, String code) {
    	List<Code> freqs = referenceDao.getPayrollFrequencies(empNbr);
    	for(Code c:freqs) {
    		if(c.getCode().equals(code)) {
    			return c;
    		}
    	}
    	return new Code();
    }
    
	public int insertNextYearAccounts(String employeeNumber)
	{
		Session session = this.getSession();
		Query q;
		StringBuilder sql = new StringBuilder();
		
		sql.append("insert into bhr_bank_deposit(cyr_nyr_flg, pay_freq, emp_nbr, bank_cd, bank_acct_nbr, ");
		sql.append("bank_acct_typ, bank_stat, bank_acct_amt) ");
		sql.append("select 'N', bhr_emp_pay.pay_freq, bhr_bank_deposit.emp_nbr, bhr_bank_deposit.bank_cd, bhr_bank_deposit.bank_acct_nbr, ");
		sql.append("bhr_bank_deposit.bank_acct_typ, bhr_bank_deposit.bank_stat, bhr_bank_deposit.bank_acct_amt ");
		sql.append("from bhr_bank_deposit, bhr_emp_pay "); 
		sql.append("where bhr_bank_deposit.emp_nbr = :emp_nbr and bhr_bank_deposit.cyr_nyr_flg = 'C' and bhr_emp_pay.cyr_nyr_flg = 'N' ");
		sql.append("and bhr_bank_deposit.emp_nbr = bhr_emp_pay.emp_nbr and ");
		sql.append("bhr_emp_pay.pay_freq = case when (bhr_bank_deposit.pay_freq = '4') then 'D' when (bhr_bank_deposit.pay_freq = '5') then 'E' when (bhr_bank_deposit.pay_freq = '6') then 'F' end");
		
		q = session.createSQLQuery(sql.toString());
		q.setParameter("emp_nbr", employeeNumber);
		
		System.out.println(sql.toString());
		
		int res = q.executeUpdate();
		session.flush();
		return res;
	}
	
	public int deleteNextYearAccounts(String employeeNumber)
	{
		Session session = this.getSession();
		Query q;
		StringBuilder sql = new StringBuilder();
		
		//Delete all next year records
		sql.delete(0, sql.length());
		sql.append("delete from bhr_bank_deposit ");
		sql.append("where cyr_nyr_flg = 'N' and emp_nbr = :emp_nbr ");
		
		q = session.createSQLQuery(sql.toString());
		q.setParameter("emp_nbr", employeeNumber);
		
		int res =  q.executeUpdate();
		session.flush();
		return res;
	}
	
	public int insertAccountRequest(Boolean autoApprove, String employeeNumber, String frequency, Bank payrollAccountInfo, Bank accountInfo)
	{
		java.sql.Timestamp requestTime = new java.sql.Timestamp(new Date().getTime());
		String statusCode = "P";
		String timestamp ="";
		Integer approverId = -1;
		
		if(autoApprove)
		{
			statusCode = "A";
			approverId = 0;
			timestamp = TimestampConverter.convertTimestamp(requestTime);
		}	
		
		Session session = this.getSession();
		Query q;
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO BEA_DRCT_DPST_BNK_ACCT (bnk_cd, emp_nbr, bnk_acct_nbr, req_dts, pay_freq, bnk_acct_typ, bnk_acct_amt, "); 
		sql.append("bnk_cd_new, bnk_acct_nbr_new, apprvd_dts, apprvr_usr_id, bnk_acct_typ_new, bnk_acct_amt_new, stat_cd) ");
		sql.append("VALUES ('");
		sql.append(accountInfo.getCode().getCode());
		sql.append("', '");
		sql.append(employeeNumber);
		sql.append("', '");
		sql.append(accountInfo.getAccountNumber());
		sql.append("', '");
		sql.append(TimestampConverter.convertTimestamp(requestTime));
		sql.append("', '");
		sql.append(frequency);
		sql.append("', '");
		sql.append(accountInfo.getAccountType().getCode());
		sql.append("', '");
		sql.append(accountInfo.getDepositAmount().getAmount());
		sql.append("', '");
		sql.append(payrollAccountInfo.getCode().getCode());
		sql.append("', '");
		sql.append(payrollAccountInfo.getAccountNumber());
		sql.append("', '");
		sql.append(timestamp);
		sql.append("', '");
		sql.append(approverId);
		sql.append("', '");
		sql.append(payrollAccountInfo.getAccountType().getCode());
		sql.append("', '");
		sql.append(payrollAccountInfo.getDepositAmount().getAmount());
		sql.append("', '");
		sql.append(statusCode);
		sql.append("' ) ");
		System.out.println(sql.toString());
		q = session.createSQLQuery(sql.toString());
		int res =  q.executeUpdate();
		session.flush();
		return res;
	}

	public int updateAccountRequest(Boolean autoApprove, String employeeNumber, String frequency, Bank payrollAccountInfo, Bank accountInfo,  Bank accountInfoPending)
	{
		java.sql.Timestamp requestTime = new java.sql.Timestamp(new Date().getTime());
		String statusCode = "P";
		String timestamp ="";
		Integer approverId = -1;
		
		if(autoApprove)
		{
			statusCode = "A";
			approverId = 0;
			timestamp = TimestampConverter.convertTimestamp(requestTime);
		}
		
		Session session = this.getSession();
		Query q;
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BEA_DRCT_DPST_BNK_ACCT SET req_dts = '");
		sql.append( TimestampConverter.convertTimestamp(requestTime));
		sql.append("', bnk_cd_new = '");
		sql.append(payrollAccountInfo.getCode().getCode());
		sql.append("', bnk_acct_nbr_new = '");
		sql.append(payrollAccountInfo.getAccountNumber());
		sql.append("', apprvd_dts = '");
		sql.append(timestamp);
		sql.append("', apprvr_usr_id = '");
		sql.append(approverId);
		sql.append("', bnk_acct_typ_new = '");
		sql.append(payrollAccountInfo.getAccountType().getCode());
		sql.append("', bnk_acct_amt_new = '");
		sql.append(payrollAccountInfo.getDepositAmount().getAmount());
		sql.append("', stat_cd = '");
		sql.append(statusCode);
		sql.append("' WHERE pay_freq = '");
		sql.append(frequency);
		sql.append("' AND bnk_cd = '");
		sql.append(accountInfo.getCode().getCode());
		sql.append("' AND emp_nbr = '");
		sql.append(employeeNumber);
		sql.append("' AND bnk_acct_nbr = '");
		sql.append(accountInfo.getAccountNumber());
		sql.append("' AND bnk_cd_new = '");
		sql.append(accountInfoPending.getCode().getCode());
		sql.append("' AND bnk_acct_nbr_new = '");
		sql.append(accountInfoPending.getAccountNumber());
		sql.append("' AND stat_cd ='P'");
		
		
		q = session.createSQLQuery(sql.toString());
		int res = q.executeUpdate();
		session.flush();
		return res;
	}

	public int deleteAccountRequest(String employeeNumber, String frequency, Bank accountInfo, Bank accountInfoPending)
	{	
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BEA_DRCT_DPST_BNK_ACCT WHERE pay_freq = '");
		sql.append(frequency); 
		sql.append("' AND bnk_cd = '");
		sql.append(accountInfo.getCode().getCode());
		sql.append("' AND emp_nbr = '");
		sql.append(employeeNumber);
		sql.append("' AND bnk_acct_nbr = '");
		sql.append(accountInfo.getAccountNumber());
		sql.append("' AND bnk_cd_new = '");
		sql.append(accountInfoPending.getCode().getCode());
		sql.append("' AND bnk_acct_nbr_new = '");
		sql.append(accountInfoPending.getAccountNumber());
		sql.append("' AND stat_cd = 'P' ");
		

		Session session = this.getSession();
		Query q = session.createSQLQuery(sql.toString());
		int res = q.executeUpdate();
		session.flush();
		return res;
	}
	
	public int insertAccountApprove(String employeeNumber, String frequency, String prenote, Bank payrollAccountInfo)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO BHR_BANK_DEPOSIT (EMP_NBR, BANK_CD, CYR_NYR_FLG, ");
		sql.append("PAY_FREQ, BANK_ACCT_NBR, BANK_ACCT_TYP, BANK_ACCT_AMT, BANK_STAT ) ");
		sql.append("Values ( '");
		sql.append(employeeNumber);
		sql.append("', '");
		sql.append(payrollAccountInfo.getCode().getCode());
		sql.append("', 'C' , '");
		sql.append(frequency);
		sql.append("', '");
		sql.append(payrollAccountInfo.getAccountNumber());
		sql.append("', '");
		sql.append(payrollAccountInfo.getAccountType().getCode());
		sql.append("', '");
		sql.append(payrollAccountInfo.getDepositAmount().getAmount());
		sql.append("', '");
		sql.append(prenote);
		sql.append("' ) ");
		
		Session session = this.getSession();
		Query q = session.createSQLQuery(sql.toString());
		int res = q.executeUpdate();
		session.flush();
		return res;
	}

	public int updateAccountApprove(String employeeNumber, String frequency, String prenote, Bank payrollAccountInfo, Bank accountInfo)
	{	
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  BHR_BANK_DEPOSIT SET BANK_CD = '");
		sql.append(payrollAccountInfo.getCode().getCode());
		sql.append("' , BANK_ACCT_NBR = '");
		sql.append(payrollAccountInfo.getAccountNumber());
		sql.append("' , BANK_ACCT_TYP = '");
		sql.append(payrollAccountInfo.getAccountType().getCode());
		sql.append("', BANK_ACCT_AMT = '");
		sql.append(payrollAccountInfo.getDepositAmount().getAmount());
		if(!"".equals(prenote))
		{
			sql.append("', BANK_STAT = '");
			sql.append(prenote);
		}
		sql.append("' WHERE EMP_NBR = '");
		sql.append(employeeNumber);
		sql.append("' AND BANK_CD = '");
		sql.append(accountInfo.getCode().getCode());
		sql.append("' AND CYR_NYR_FLG='C' AND PAY_FREQ = '");
		sql.append(frequency);
		sql.append("' AND BANK_ACCT_NBR = '");
		sql.append(accountInfo.getAccountNumber());
		sql.append("' AND BANK_ACCT_TYP= '");
		sql.append(accountInfo.getAccountType().getCode());
		sql.append("' AND BANK_ACCT_AMT = '");
		sql.append(accountInfo.getDepositAmount().getAmount());
		sql.append("'");
	
		Session session = this.getSession();
		Query q = session.createSQLQuery(sql.toString());
		int res = q.executeUpdate();
		session.flush();
		return res;
	}

	public int deleteAccountApprove(String employeeNumber, String frequency, Bank accountInfo)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BHR_BANK_DEPOSIT WHERE EMP_NBR = '");
		sql.append(employeeNumber);  
		sql.append("' AND PAY_FREQ = '");
		sql.append(frequency);
		sql.append("' AND  BANK_CD = '");
		sql.append(accountInfo.getCode().getCode());
		sql.append("' AND CYR_NYR_FLG = 'C' AND BANK_ACCT_NBR = '");
		sql.append(accountInfo.getAccountNumber());
		sql.append("' AND  BANK_ACCT_TYP = '");
		sql.append(accountInfo.getAccountType().getCode());
		sql.append("' AND BANK_ACCT_AMT = '");
		sql.append(accountInfo.getDepositAmount().getAmount());
		sql.append("'");
		
		Session session = this.getSession();
		Query q = session.createSQLQuery(sql.toString());
		int res = q.executeUpdate();
		session.flush();
		return res;
	}
	
	
	public Boolean getAutoApproveAccountInfo(String frequency)
	{
		Session session = this.getSession();
		Query q;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT auto_apprv FROM BHR_EAP_PAY_ASSGN_GRP WHERE grp_name = 'BEA_DRCT_DPST_BNK_ACCT' AND pay_freq =:frequency");
		q = session.createSQLQuery(sql.toString());
		q.setParameter("frequency", frequency);
		Character res = (Character) q.uniqueResult();
	    
		
		return ("Y").equals(res.toString());
		
	}
	
	public Integer getDirectDepositLimit()
	{
		
		Session session = this.getSession();
		Query q;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DD_ACCT FROM BHR_EAP_OPT ");
		
		q = session.createSQLQuery(sql.toString());
		
		return q.getFirstResult();
	}
}
