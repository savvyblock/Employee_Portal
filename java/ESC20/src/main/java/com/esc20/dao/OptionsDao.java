package com.esc20.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BhrEapOpt;
import com.esc20.nonDBModels.Options;

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
}
