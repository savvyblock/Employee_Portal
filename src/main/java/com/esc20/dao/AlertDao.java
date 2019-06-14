package com.esc20.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BeaAlert;

@Repository
public class AlertDao {
    @Autowired
    private SessionFactory sessionFactory;
    
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    public List<BeaAlert> getAlerts(String empNbr) {
    	Session session = this.getSession();
        String hql = "from BeaAlert where msgTo = :empNbr order by id desc" ;
        Query q = session.createQuery(hql);
        q.setParameter("empNbr", empNbr);
        @SuppressWarnings("unchecked")
		List<BeaAlert> result = q.list();
        if(result== null || result.isEmpty()) {
        	return null;
        }
    	return result;
    }

    public Integer getAlertCount(String empNbr) {
    	List<BeaAlert> result =  this.getUnreadAlerts(empNbr);
        if(result== null || result.isEmpty()) {
        	return 0;
        }
    	return result.size();
    }

    public List<BeaAlert> getTop5UnreadAlerts(String empNbr) {
    	Session session = this.getSession();
        String hql = "from BeaAlert where msgTo = :empNbr and status = 'UR' order by id desc" ;
        Query q = session.createQuery(hql);
        q.setParameter("empNbr", empNbr);
        q.setMaxResults(5);
        @SuppressWarnings("unchecked")
		List<BeaAlert> result = q.list();
        if(result== null || result.isEmpty()) {
        	return new ArrayList<BeaAlert>();
        }
    	return result;
    }

    public List<BeaAlert> getUnreadAlerts(String empNbr) {
    	Session session = this.getSession();
        String hql = "from BeaAlert where msgTo = :empNbr and status = 'UR' order by id desc" ;
        Query q = session.createQuery(hql);
        q.setParameter("empNbr", empNbr);
        @SuppressWarnings("unchecked")
		List<BeaAlert> result = q.list();
        if(result== null || result.isEmpty()) {
        	return new ArrayList<BeaAlert>();
        }
    	return result;
    }
    
	public void createAlert(String empNbr, String apprvrEmpNbr, String message) {
		Session session = this.getSession();
		BeaAlert alert = new BeaAlert();
		alert.setMsgFrom(empNbr);
		alert.setMsgTo(apprvrEmpNbr);
		alert.setMsgContent(message);
		alert.setStatus("UR");
		session.save(alert);
		session.flush();
	}
	
	public void markRead(Integer id) {
		Session session = this.getSession();
		BeaAlert alert = (BeaAlert) session.get(BeaAlert.class, id);
		alert.setStatus("R");
		session.update(alert);
		session.flush();
	}

	public void deleteAlert(String id) {
		Session session = this.getSession();
		BeaAlert alert = (BeaAlert) session.get(BeaAlert.class, Long.parseLong(id));
		session.delete(alert);
		session.flush();
	}
}
