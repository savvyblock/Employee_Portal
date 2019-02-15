package com.esc20.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BthrBankCodes;

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
    
}
