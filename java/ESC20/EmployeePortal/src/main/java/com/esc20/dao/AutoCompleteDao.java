package com.esc20.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BhrEapOpt;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.Options;

@Repository
public class AutoCompleteDao {
    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    
    // Employee Portal Temporary Approver
	public List<Code> getEmployeeTempApproverSearch(String excludeEmpNumber, String search) {
		Session session = this.getSession();
		
		boolean lb_emp_nbr = true;
		String search1 = "", search2 = "", search3 = "", search4 = "", search5 = "";
		
		StringBuilder sql = new StringBuilder();
		search = (search == null) ? "" : search.trim();
		if (search.indexOf(",") > 0) {
			search = search.replaceAll(",", "%,_");
		}
		
		try {
			Double.parseDouble(search);

			// Validate for special letters that are valid for as numbers.
			if ((search.indexOf(".") != -1) || (search.toUpperCase().indexOf("E") != -1) || (search.toUpperCase().indexOf("D") != -1) || (search.toUpperCase().indexOf("F") != -1)) {
				lb_emp_nbr = false;
			}
		}
		catch (NumberFormatException ex) {
			lb_emp_nbr = false;
		}
		
		if(lb_emp_nbr) {
			search5 = String.format("%1$05d", Long.parseLong(search)) + "_";
			search4 = String.format("%1$04d", Long.parseLong(search)) + "__";
			search3 = String.format("%1$03d", Long.parseLong(search)) + "___";
			search2 = String.format("%1$02d", Long.parseLong(search)) + "____";
			search1 = String.format("%1$01d", Long.parseLong(search)) + "_____";
			search = String.format("%06d", Long.parseLong(search));
		}
		
		search = search.trim() + "%";
		
		sql.append("select distinct TOP 30 ");
		sql.append("code = bhr_emp_pay.emp_nbr, ");
		sql.append("descr = name_l + ', ' + name_f + ' ' + name_m, ");
		sql.append("display_value =  bhr_emp_pay.emp_nbr || ' : ' || name_l + ', ' + name_f + ' ' + name_m, ");
		sql.append("column_value = bhr_emp_pay.emp_nbr || ' - ' || name_l + ', ' + name_f + ' ' + name_m ");
		sql.append("FROM bhr_emp_demo, bhr_emp_pay, sec_users ");
		sql.append("WHERE bhr_emp_demo.non_emp = 'N' AND bhr_emp_pay.emp_nbr != :excludeEmpNumber AND ");
		sql.append("(bhr_emp_pay.emp_nbr=bhr_emp_demo.emp_nbr and sec_users.emp_nbr=bhr_emp_demo.emp_nbr and sec_users.usr_deleted=0) and ");
		sql.append("((name_l + ', ' + name_f + ' ' + name_m) like :search or bhr_emp_demo.emp_nbr like :search or bhr_emp_demo.emp_nbr like :search5 or bhr_emp_demo.emp_nbr like :search4 or bhr_emp_demo.emp_nbr like :search3 or bhr_emp_demo.emp_nbr like :search2 or bhr_emp_demo.emp_nbr like :search1) ");
		if (lb_emp_nbr) {
			sql.append("order by code, descr ");
		} else {
			sql.append("order by descr, code ");
		}
		Query q = session.createSQLQuery(sql.toString());
		 q.setParameter("excludeEmpNumber", excludeEmpNumber);
		 q.setParameter("search", search);
		 q.setParameter("search1", search1);
		 q.setParameter("search2", search2);
		 q.setParameter("search3", search3);
		 q.setParameter("search4", search4);
		 q.setParameter("search5", search5);
		 
		@SuppressWarnings("unchecked")
		List<Object[]> res = q.list();
		
		List<Code> result = new ArrayList<Code>();
		Code code;
		for(Object[] item: res) {
			code = new Code((item[0]==null?"":(item[0]).toString()),"",(String)item[1]);
			
			result.add(code);		
		}
		
		return result;
	}
}
