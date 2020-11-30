package com.esc20.dao;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.esc20.model.TxeisPreferences;
import com.esc20.model.TxeisPreferencesId;

@Repository
@Transactional
public class PreferencesDao extends HibernateDaoSupport{
    
    @Resource  
    public void setSessionFacotry(SessionFactory sessionFacotry) {
        super.setSessionFactory(sessionFacotry);  
    }
    
    private Session getSession(){
        return super.getSessionFactory().getCurrentSession();
    }
    
	public TxeisPreferencesId getPrefenceByPrefName(String prefName) {
		Session session = this.getSession();
		Query q;
		String sql= "from TxeisPreferences where PREF_NAME =:prefName";
        q = session.createQuery(sql);
        q.setParameter("prefName", prefName);
        TxeisPreferencesId res = ((TxeisPreferences) q.uniqueResult()).getId();
        
        return res;
	}

}
