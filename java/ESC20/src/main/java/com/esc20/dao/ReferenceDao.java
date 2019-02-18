package com.esc20.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.esc20.model.BhrEmpPay;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.Frequency;


@Repository
public class ReferenceDao {

    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession(){
        return sessionFactory.openSession();
    }
    
	public List<Code> getGenerations()
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT id.genCd, '', genDescr FROM EtC012Gen");
		Query q = session.createQuery(sql.toString());
		List<Object[]> res = q.list();
		session.close();
		List<Code> result = new ArrayList<Code>();
		Code code;
		for(Object[] item: res) {
			code = new Code(((Character)item[0]==null?"":((Character)item[0]).toString()),((Character)item[1]==null?"":((Character)item[1]).toString()),(String)item[2]);
			if(code.getDescription().equals("default")) {
				code.setCode("");
				code.setSubCode("");
				code.setDescription("");
			}
			result.add(code);		
		}
		return result;	
	}
	
	public List <Code> getStates()
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT abbrev, '',state FROM BthrStateAbbrev");
		Query q = session.createQuery(sql.toString());
		List<Object[]> res = q.list();
		session.close();
		List<Code> result = new ArrayList<Code>();
		Code code;
		for(Object[] item: res) {
			code = new Code((item[0]==null?"":(item[0]).toString()),(item[1]==null?"":(item[1]).toString()),(String)item[2]);
			result.add(code);		
		}
		return result;		
	}

	public List <Code> getRestrictions()
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT type, '', descr FROM BthrRestrictTyp");
		Query q = session.createQuery(sql.toString());
		List<Object[]> res = q.list();
		session.close();
		List<Code> result = new ArrayList<Code>();
		Code code;
		for(Object[] item: res) {
			code = new Code(((Character)item[0]==null?"":((Character)item[0]).toString()),((Character)item[1]==null?"":((Character)item[1]).toString()),(String)item[2]);
			result.add(code);		
		}
		return result;	
	}
	
	public List <Code> getMaritalStatuses()
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT stat,'',descr as subCode FROM BthrMaritalTaxStat");
		Query q = session.createQuery(sql.toString());
		List<Object[]> res = q.list();
		session.close();
		List<Code> result = new ArrayList<Code>();
		Code code;
		for(Object[] item: res) {
			code = new Code(((Character)item[0]==null?"":((Character)item[0]).toString()),((Character)item[1]==null?"":((Character)item[1]).toString()),(String)item[2]);
			result.add(code);		
		}
		return result;		
	}
	
	public List <Code> getDdAccountTypes()
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT bankAcctTyp, '',bankAcctTypDescr FROM BthrBankAcctTyp");
		Query q = session.createQuery(sql.toString());
		List<Object[]> res = q.list();
		session.close();
		List<Code> result = new ArrayList<Code>();
		Code code;
		for(Object[] item: res) {
			code = new Code(((Character)item[0]==null?"":((Character)item[0]).toString()),((Character)item[1]==null?"":((Character)item[1]).toString()),(String)item[2]);
			result.add(code);		
		}
		return result;	
	}
	
	public List <Code> getAvailableBanks()
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT bankCd,transitRoute,bankName FROM BthrBankCodes ORDER BY bankName ASC");
		Query q = session.createQuery(sql.toString());
		List<Object[]> res = q.list();
		session.close();
		List<Code> result = new ArrayList<Code>();
		Code code;
		for(Object[] item: res) {
			code = new Code(((Character)item[0]==null?"":((Character)item[0]).toString()),((Character)item[1]==null?"":((Character)item[1]).toString()),(String)item[2]);
			result.add(code);		
		}
		return result;	
	}

	public List <Code> getMaritalActualStatuses()
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT stat, '', descr FROM BthrMaritalActualStatus");
		Query q = session.createQuery(sql.toString());
		List<Object[]> res = q.list();
		session.close();
		List<Code> result = new ArrayList<Code>();
		Code code;
		for(Object[] item: res) {
			code = new Code(((Character)item[0]==null?"":((Character)item[0]).toString()),((Character)item[1]==null?"":((Character)item[1]).toString()),(String)item[2]);
			result.add(code);		
		}
		return result;
	}

	public List<Code> getAbsRsns() {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT absRsn, '', absDescr FROM BthrAbsRsn");
		Query q = session.createQuery(sql.toString());
		List<Object[]> res = q.list();
		session.close();
		List<Code> result = new ArrayList<Code>();
		Code code;
		for(Object[] item: res) {
			code = new Code((String)item[0],((Character)item[1]==null?"":((Character)item[1]).toString()),(String)item[2]);
			result.add(code);		
		}
		return result;
	}

	public List<Code> getLeaveTypes() {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT LTD.lvTyp, LT.daysHrs, LTD.longDescr FROM BthrLvTypDescr LTD, BthrLvTyp LT WHERE LTD.lvTyp=LT.id.lvTyp");
		Query q = session.createQuery(sql.toString());
		List<Object[]> res = q.list();
		session.close();
		List<Code> result = new ArrayList<Code>();
		Code code;
		for(Object[] item: res) {
			code = new Code((String)item[0],(String)item[1],(String)item[2]);
			result.add(code);		
		}
		return result;
	}

	public List<Code> getLeaveStatus() {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT cd, '', descr FROM BteaEmpLvStatusCodes");
		Query q = session.createQuery(sql.toString());
		List<Object[]> res = q.list();
		session.close();
		List<Code> result = new ArrayList<Code>();
		Code code;
		for(Object[] item: res) {
			code = new Code(((Character)item[0]==null?"":((Character)item[0]).toString()),((Character)item[1]==null?"":((Character)item[1]).toString()),(String)item[2]);
			result.add(code);		
		}
		return result;
	}
	
	public List<Code> getPayrollFrequencies(String empNbr) {
		Session session = this.getSession();
		String sql= "SELECT DISTINCT pay.id.payFreq FROM BhrEmpPay pay where pay.id.empNbr = :empNbr AND pay.id.cyrNyrFlg ='C'";
        Query q = session.createQuery(sql.toString());
        q.setParameter("empNbr", empNbr);
        List <Character> result = q.list();
		List<Code> payrollFrequencies = new ArrayList <Code>();
		Frequency freq;
		Code code;
		for(int i=0; i < result.size(); i++ )
		{
			code = new Code();
			code.setCode(result.get(i).toString());
			freq = Frequency.getFrequency(result.get(i).toString());
			code.setDescription(freq.getLabel());
			code.setSubCode("");
			payrollFrequencies.add(code);
		}
		return payrollFrequencies;
	}

}
