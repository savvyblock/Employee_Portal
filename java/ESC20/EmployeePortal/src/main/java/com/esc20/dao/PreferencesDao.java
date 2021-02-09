package com.esc20.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.esc20.model.TxeisPreferences;
import com.esc20.model.TxeisPreferencesId;
import com.esc20.util.StringUtil;

@Repository
@Transactional
public class PreferencesDao extends HibernateDaoSupport {

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	private Session getSession() {
		return super.getSessionFactory().getCurrentSession();
	}

	public TxeisPreferencesId getPrefenceByPrefName(String prefName) {
		Session session = this.getSession();
		Query q;
		String sql = "from TxeisPreferences where PREF_NAME =:prefName";
		q = session.createQuery(sql);
		q.setParameter("prefName", prefName);
		TxeisPreferencesId res = ((TxeisPreferences) q.uniqueResult()).getId();

		return res;
	}

	// ALC-26 update EP password to get settings from DB
	public Map<String, String> getTxeisPreferences() {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put(" ", "Select");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PREF_NAME, PREF_VALUE FROM ");
		sql.append(" TXEIS_PREFERENCES ");
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		String value;
		String name;
		for (Map<String, Object> map : list) {
			value = StringUtil.getString(map.get("PREF_VALUE"));
			name = StringUtil.getString(map.get("PREF_NAME"));
			ret.put(name, value);
		}
		return ret;
	}

	// ALC-26 get TXEIS_PREFERENCES value from setting DB
	public String getTXEISPreferencebyName(String pPreferenceName) {

		String sql = "Select top  1 PREF_VALUE From TXEIS_PREFERENCES Where PREF_NAME = :prefName";

		Query q = this.getSession().createSQLQuery(sql);
		q.setParameter("prefName", pPreferenceName);

		return (String) q.uniqueResult();
	}

}
