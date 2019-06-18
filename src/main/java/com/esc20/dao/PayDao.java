package com.esc20.dao;

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
		sql.append(" nbrTaxExempts");
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
        
        PayInfo payInfo = new PayInfo(res[0],res[1]);
       
        
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
	}
	
	public void updatePayInfo(BhrEmpDemo demo, BhrEmpPay pay, Character payFreq, Character maritalStatTaxNew,  Integer nbrTaxExemptsNew) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpPay SET maritalStatTax = :maritalStatTaxNew ");
		sql.append(", nbrTaxExempts = :nbrTaxExemptsNew");
		sql.append(" WHERE id.cyrNyrFlg = 'C' ");
		sql.append(" AND id.payFreq = :payFreq");
		sql.append(" AND id.empNbr = :empNbr");
		sql.append(" AND maritalStatTax = :maritalStatTax");
		sql.append(" AND nbrTaxExempts= :nbrTaxExempts");
		Query q = session.createQuery(sql.toString());
		q.setParameter("maritalStatTaxNew", maritalStatTaxNew);
		q.setParameter("nbrTaxExemptsNew", nbrTaxExemptsNew);
		q.setParameter("maritalStatTax", pay.getMaritalStatTax());
		q.setParameter("nbrTaxExempts", pay.getNbrTaxExempts());
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
