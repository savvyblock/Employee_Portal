package com.esc20.dao;

import java.math.BigDecimal;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BeaW4;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrEmpPay;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.PayInfo;

@Repository
public class PayDao {
    @Autowired
    private SessionFactory sessionFactory;
    
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }


	public PayInfo getPayInfo(String empNbr, String frequency) {
        Session session = this.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT maritalStatTax,");
		sql.append(" nbrTaxExempts, w4FileStat, w4MultiJob, w4NbrChldrn, ");
		sql.append(" w4NbrOthrDep, w4OthrIncAmt, w4OthrDedAmt, w4OthrExmptAmt  ");
		sql.append(" FROM BhrEmpPay");
		sql.append(" WHERE id.cyrNyrFlg = 'C'");
		sql.append(" AND id.payFreq = :frequency");
		sql.append(" AND id.empNbr = :empNbr");
		Query q = session.createQuery(sql.toString());
		q.setParameter("empNbr", empNbr);
		Frequency freq ;
		freq = Frequency.getFrequency(frequency);
        q.setParameter("frequency", freq.getCode().charAt(0));
        
        Object[] res =  (Object[]) q.uniqueResult();
        
        PayInfo payInfo = new PayInfo(res[0], res[1], res[2], res[3], res[4], res[5], res[6], res[7], res[8]);
       
        
        return payInfo;
	}
	
	public BeaW4 getW4(String empNbr, String frequency) {
		Session session = this.getSession();
		Query q;
		StringBuilder sql = new StringBuilder();
		sql.append("FROM BeaW4 ");
		sql.append("WHERE id.empNbr = :empNbr AND id.payFreq =:frequency AND STAT_CD = 'P'");
        q = session.createQuery(sql.toString());
        q.setParameter("empNbr", empNbr);
        Frequency freq ;
		freq = Frequency.getFrequency(frequency);
        q.setParameter("frequency", freq.getCode().charAt(0));
        BeaW4 res = (BeaW4) q.uniqueResult();
        
        return res;
	}

	public BeaW4 getW4ByEmpNbr(String empNbr) {
		Session session = this.getSession();
		Query q;
		StringBuilder sql = new StringBuilder();
		sql.append("FROM BeaW4 ");
		sql.append("WHERE id.empNbr = :empNbr");
        q = session.createQuery(sql.toString());
        q.setParameter("empNbr", empNbr);
      
        BeaW4 res = (BeaW4) q.uniqueResult();
        
        return res;
	}

	public boolean getBhrEapPayAssgnGrp(String tableName) {
		Session session = this.getSession();
		Query q;
		String sql= " SELECT DISTINCT autoApprv FROM BhrEapPayAssgnGrp where id.grpName = :tableName";
        q = session.createQuery(sql);
        q.setParameter("tableName", tableName);
        Character res = (Character) q.uniqueResult();
        
        return ("Y").equals(res.toString());
	}

	public void savew4Request(BeaW4 w4Request) {
		Session session = this.getSession();
		session.saveOrUpdate(w4Request);
        session.flush();
        session.clear();
	}
	
	public void updatePayInfo(BhrEmpDemo demo, BhrEmpPay pay, Character payFreq, Character maritalStatTaxNew,  
			Integer nbrTaxExemptsNew, String w4FileStatNew, String w4MultiJobNew, Integer w4NbrChldrnNew, Integer w4NbrOthrDepNew, 
			Double w4OthrIncAmtNew, Double w4OthrDedAmtNew, Double w4OthrExmptAmtNew) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpPay SET maritalStatTax = :maritalStatTaxNew, ");
		sql.append("nbrTaxExempts = :nbrTaxExemptsNew, ");
		sql.append("w4FileStat = :w4FileStatNew, w4MultiJob = :w4MultiJobNew, w4NbrChldrn = :w4NbrChldrnNew, ");
		sql.append("w4NbrOthrDep = :w4NbrOthrDepNew, w4OthrIncAmt = :w4OthrIncAmtNew, w4OthrDedAmt = :w4OthrDedAmtNew, ");
		sql.append("w4OthrExmptAmt = :w4OthrExmptAmtNew");
		sql.append(" WHERE id.cyrNyrFlg = 'C' ");
		sql.append(" AND id.payFreq = :payFreq");
		sql.append(" AND id.empNbr = :empNbr");
		sql.append(" AND maritalStatTax = :maritalStatTax");
		sql.append(" AND nbrTaxExempts= :nbrTaxExempts and w4FileStat = :w4FileStat and w4MultiJob = :w4MultiJob and ");
		sql.append(" w4NbrChldrn = :w4NbrChldrn and w4NbrOthrDep = :w4NbrOthrDep and w4OthrIncAmt = :w4OthrIncAmt and ");
		sql.append(" w4OthrDedAmt = :w4OthrDedAmt and w4OthrExmptAmt = :w4OthrExmptAmt ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("maritalStatTaxNew", maritalStatTaxNew);
		q.setParameter("nbrTaxExemptsNew", nbrTaxExemptsNew);
		q.setParameter("w4FileStatNew", w4FileStatNew);
		q.setParameter("w4MultiJobNew", w4MultiJobNew);
		q.setParameter("w4NbrChldrnNew", w4NbrChldrnNew);
		q.setParameter("w4NbrOthrDepNew", w4NbrOthrDepNew);
		q.setParameter("w4OthrIncAmtNew", w4OthrIncAmtNew);
		q.setParameter("w4OthrDedAmtNew", w4OthrDedAmtNew);
		q.setParameter("w4OthrExmptAmtNew", w4OthrExmptAmtNew);
		q.setParameter("maritalStatTax", pay.getMaritalStatTax());
		q.setParameter("nbrTaxExempts", pay.getNbrTaxExempts());
		q.setParameter("w4FileStat", pay.getW4FileStat());
		q.setParameter("w4MultiJob", pay.getW4MultiJob());
		q.setParameter("w4NbrChldrn", pay.getW4NbrChldrn());
		q.setParameter("w4NbrOthrDep", pay.getW4NbrOthrDep());
		q.setParameter("w4OthrIncAmt", pay.getW4OthrIncAmt());
		q.setParameter("w4OthrDedAmt", pay.getW4OthrDedAmt());
		q.setParameter("w4OthrExmptAmt", pay.getW4OthrExmptAmt());
		q.setParameter("empNbr", demo.getEmpNbr());
		q.setParameter("payFreq", payFreq);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
    	session.flush();
	}
	
		
	public void deleteW4request(String empNbr,String payFreq,Character maritalStatTax, Integer nbrTaxExempts ) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaW4 WHERE id.empNbr = :empNbr");
		sql.append(" AND id.payFreq =  :payFreq");
		sql.append(" AND maritalStatTax = :maritalStatTax");
		sql.append(" AND nbrTaxExempts = :nbrTaxExempts");
		sql.append(" AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("empNbr", empNbr);
		Frequency freq ;
		freq = Frequency.getFrequency(payFreq);
		q.setParameter("payFreq", freq.getCode().charAt(0));
		q.setParameter("maritalStatTax", maritalStatTax);
		q.setParameter("nbrTaxExempts", nbrTaxExempts);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
		session.flush();
	}
	
}
