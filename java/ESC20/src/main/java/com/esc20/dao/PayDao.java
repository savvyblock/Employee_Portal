package com.esc20.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.AppUser;
import com.esc20.model.BeaAltMailAddr;
import com.esc20.model.BeaBusPhone;
import com.esc20.model.BeaCellPhone;
import com.esc20.model.BeaDrvsLic;
import com.esc20.model.BeaEmail;
import com.esc20.model.BeaEmerContact;
import com.esc20.model.BeaHmPhone;
import com.esc20.model.BeaLglName;
import com.esc20.model.BeaMailAddr;
import com.esc20.model.BeaMrtlStat;
import com.esc20.model.BeaRestrict;
import com.esc20.model.BeaUsers;
import com.esc20.model.BeaW4;
import com.esc20.model.BhrEapDemoAssgnGrp;
import com.esc20.model.BhrEapPayAssgnMbr;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrEmpPayId;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.SearchUser;

import com.esc20.nonDBModels.Frequency;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class PayDao {
    @Autowired
    private SessionFactory sessionFactory;
    
    private Session getSession(){
        return sessionFactory.openSession();
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
       
        session.close();
        return payInfo;
	}

	
	
}
