package com.esc20.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.util.Log;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

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
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.LeaveEmployeePMISData;
import com.esc20.nonDBModels.SearchUser;
import com.esc20.util.StringUtil;

@Repository
public class AppUserDao extends HibernateDaoSupport{
    
    @Resource  
    public void setSessionFacotry(SessionFactory sessionFacotry) {
        super.setSessionFactory(sessionFacotry);  
    }
    
    private Session getSession(){
        return super.getSessionFactory().getCurrentSession();
    }

	public BeaUsers getUserByName(String name) throws ParseException{
        Session session = this.getSession();
        String sql= "from BeaUsers where usrname =:name";
        Query q = session.createQuery(sql);
        q.setParameter("name", name.toUpperCase());
        BeaUsers res = (BeaUsers) q.uniqueResult();
        
        
        if(res == null) {
        	return null;
        }
        
        if(res.getLkPswd() == 'Y' || ("").equals(res.getUsrpswd())) {
        	return null;
        } else if (res.getLkPswd() == 'N') {
        	//lk_pswd = 'N' AND (bea_users.tmp_dts ='' OR DATEDIFF (HH, convert(datetime, bea_users.tmp_dts), GETDATE()) &lt; 24 )
        	if(res.getTmpDts()!= null && !("").equals(res.getTmpDts().trim())) {
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        		Calendar tmpDts = Calendar.getInstance();
        		tmpDts.setTime(sdf.parse(res.getTmpDts()));
        		Calendar now = Calendar.getInstance();
        		if(tmpDts.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR)>=1) {
        			return null;
        		}
        	}
        }else {
        	return null;
        }
        return res;
    }

	public BeaUsers getUserByEmail(String email) {
        Session session = this.getSession();
        String hql = "select empNbr from BhrEmpDemo where email = :email" ;
        Query q = session.createQuery(hql);
        q.setParameter("email", email);
        
        if(q.list() == null || q.list().isEmpty()) {
        	return null;
        }
        
        String empNbr = (String) q.list().get(0);
        
        return getUserByEmpNbr(empNbr);
	}

	public BeaUsers getUserByEmpNbr(String empNbr) {
		Session session = this.getSession();
		Query q;
		String sql= "from BeaUsers where empNbr =:empNbr";
        q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        BeaUsers res = (BeaUsers) q.uniqueResult();
        
        return res;
	}
	
	public BhrEmpDemo getUserNameFromSsn(String ssn){
			Session session = this.getSession();
			Query q;
			String sql= "from BhrEmpDemo where staffId =:ssn";
	        q = session.createQuery(sql);
	        q.setParameter("ssn", ssn);
	        BhrEmpDemo res = (BhrEmpDemo) q.uniqueResult();
	        
	        return res;
	}
	
	public BeaUsers getUserByUsername(String username) {
		Session session = this.getSession();
		Query q;
		String sql= "from BeaUsers where usrname =:usrname";
        q = session.createQuery(sql);
        q.setParameter("usrname", username);
        BeaUsers res = (BeaUsers) q.uniqueResult();
        
        return res;
	}
	
	public BeaUsers saveBeaUsers(BeaUsers user) {
		Session session = this.getSession();
		session.saveOrUpdate(user);
        session.flush();
        
        return user;
	}

	public void updateUser(BeaUsers user) {
		Session session = this.getSession();
        session.update(user);
        session.flush();
	}

	public BhrEmpDemo getUserDetail(String empNbr) {
        Session session = this.getSession();
        BhrEmpDemo result = session.get(BhrEmpDemo.class, empNbr);
        return result;
	}
	
	//ALC-13 do changes so that user can use ssn to registrater
	public BhrEmpDemo getUserDetailBySSN(String staffId) {
		Session session = this.getSession();
		Query q;
		String sql= "from BhrEmpDemo where staffId =:staffId";
        q = session.createQuery(sql);
        q.setParameter("staffId", staffId);
        BhrEmpDemo res = (BhrEmpDemo) q.uniqueResult();
        
        return res;
	}
	
	public District getDistrict(String district) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT dr.distName, dr.strNbrDist, dr.strNameDist, ");
		sql.append("dr.cityNameDist, dr.stateCd, dr.zipDist, dr.zip4Dist, dr.areaCdDist, dr.phoneNbrDist ");
		sql.append(" FROM DrDemo dr");
		sql.append(" WHERE dr.id.schYr = (SELECT MAX(dr2.id.schYr) from DrDemo dr2)");
		// changed to remove the dist id param
        Query q = session.createQuery(sql.toString());
        Object[] res =  (Object[]) q.uniqueResult();
        District dis = new District(res[0],res[1],res[2],res[3],res[4],res[5],res[6],res[7],res[8]);
        String einSql = "SELECT DIST_EIN FROM BFN_OPTIONS WHERE GL_FILE_ID='C'";
        q = session.createSQLQuery(einSql);
        String ein = (String) q.uniqueResult();
        dis.setEin(ein);
        //setting EIN
        return dis;
	}

	public BeaLglName getBeaLglName(String empNbr) {
		Session session = this.getSession();
		Query q;
		String sql= "from BeaLglName where id.empNbr =:empNbr and statCd='P'";
        q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        BeaLglName res = (BeaLglName) q.uniqueResult();
        if(res!=null) {
        	if(res.getNamePre()!=null)
        		res.setNamePre(res.getNamePre().trim());
        	if(res.getNamePreNew()!=null)
        		res.setNamePreNew(res.getNamePreNew().trim());
        }
        return res;
	}

	public void saveNameRequest(BeaLglName nameRequest) {
		Session session = this.getSession();
		session.saveOrUpdate(nameRequest);
        session.flush();
        
	}
	
	public void saveMaritalRequest(BeaMrtlStat maritalStatusRequest) {
		Session session = this.getSession();
		session.saveOrUpdate(maritalStatusRequest);
        session.flush();
        
	}
	
	public void saveDriversLicenseRequest(BeaDrvsLic driversLicenseRequest) {
		Session session = this.getSession();
		session.saveOrUpdate(driversLicenseRequest);
        session.flush();
        
	}
	
	public void saveRestrictionCodesRequest(BeaRestrict restrictionCodesRequest) {
		Session session = this.getSession();
		session.saveOrUpdate(restrictionCodesRequest);
        session.flush();
        
	}
	
	public void saveEmailRequest(BeaEmail emailRequest) {
		Session session = this.getSession();
		session.saveOrUpdate(emailRequest);
        session.flush();
        
	}
	
	public void saveEmergencyContactRequest(BeaEmerContact emergencyContactRequest) {
		Session session = this.getSession();
		session.saveOrUpdate(emergencyContactRequest);
        session.flush();
        
	}
	
	public void saveMailAddrRequest(BeaMailAddr maillingAddressRequest) {
		Session session = this.getSession();
		session.saveOrUpdate(maillingAddressRequest);
        session.flush();
        
	}
	
	public void saveAltMailAddrRequest(BeaAltMailAddr altMaillingAddressRequest) {
		Session session = this.getSession();
		session.saveOrUpdate(altMaillingAddressRequest);
        session.flush();
        
	}
	public void saveHomePhoneRequest(BeaHmPhone homePhoneRequest) {
		Session session = this.getSession();
		session.saveOrUpdate(homePhoneRequest);
        session.flush();
        
	}
	public void saveCellPhoneRequest(BeaCellPhone cellPhoneRequest) {
		Session session = this.getSession();
		session.saveOrUpdate(cellPhoneRequest);
        session.flush();
        
	}
	public void saveBusinessPhoneRequest(BeaBusPhone businessPhoneRequest) {
		Session session = this.getSession();
		session.saveOrUpdate(businessPhoneRequest);
        session.flush();
        
	}

	public BeaEmerContact getBeaEmerContact(String empNbr) {
		Session session = this.getSession();
		Query q;
		String sql= "from BeaEmerContact where id.empNbr =:empNbr and statCd='P'";
        q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        BeaEmerContact res = (BeaEmerContact) q.uniqueResult();
        
        return res;
	}

	public BeaDrvsLic getBeaDrvsLic(String empNbr) {
		Session session = this.getSession();
		Query q;
		String sql= "from BeaDrvsLic where id.empNbr =:empNbr and statCd='P'";
        q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        BeaDrvsLic res = (BeaDrvsLic) q.uniqueResult();
        
        return res;
	}

	public BeaMrtlStat getBeaMrtlStat(String empNbr) {
		Session session = this.getSession();
		Query q;
		String sql= "from BeaMrtlStat where id.empNbr =:empNbr and statCd='P'";
        q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        BeaMrtlStat res = (BeaMrtlStat) q.uniqueResult();
        
        return res;
	}

	public BeaRestrict getBeaRestrict(String empNbr) {
		Session session = this.getSession();
		Query q;
		String sql= "from BeaRestrict where id.empNbr =:empNbr and statCd='P'";
        q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        BeaRestrict res = (BeaRestrict) q.uniqueResult();
        
        return res;
	}

	public BeaEmail getBeaEmail(String empNbr) {
		Session session = this.getSession();
		Query q;
		String sql= "from BeaEmail where id.empNbr =:empNbr and statCd='P'";
        q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        BeaEmail res = (BeaEmail) q.uniqueResult();
        
        return res;
	}

	public BeaCellPhone getBeaCellPhone(String empNbr) {
		Session session = this.getSession();
		Query q;
		String sql= "from BeaCellPhone where id.empNbr =:empNbr and statCd='P'";
        q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        BeaCellPhone res = (BeaCellPhone) q.uniqueResult();
        
        return res;
	}

	public BeaBusPhone getBeaBusPhone(String empNbr) {
		Session session = this.getSession();
		Query q;
		String sql= "from BeaBusPhone where id.empNbr =:empNbr and statCd='P'";
        q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        BeaBusPhone res = (BeaBusPhone) q.uniqueResult();
        
        return res;
	}

	public BeaHmPhone getBeaHmPhone(String empNbr) {
		Session session = this.getSession();
		Query q;
		String sql= "from BeaHmPhone where id.empNbr =:empNbr and statCd='P'";
        q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        BeaHmPhone res = (BeaHmPhone) q.uniqueResult();
        
        return res;
	}

	public BeaAltMailAddr getBeaAltMailAddr(String empNbr) {
		Session session = this.getSession();
		Query q;
		String sql= "from BeaAltMailAddr where id.empNbr =:empNbr and statCd='P'";
        q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        BeaAltMailAddr res = (BeaAltMailAddr) q.uniqueResult();
        
        return res;
	}

	public BeaMailAddr getBeaMailAddr(String empNbr) {
		Session session = this.getSession();
		Query q;
		String sql= "from BeaMailAddr where id.empNbr =:empNbr and statCd='P'";
        q = session.createQuery(sql);
        q.setParameter("empNbr", empNbr);
        BeaMailAddr res = (BeaMailAddr) q.uniqueResult();
        return res;
	}

	public boolean getBhrEapDemoAssgnGrp(String tableName) {
		Session session = this.getSession();
		Query q;
		String sql= " SELECT DISTINCT autoApprv FROM BhrEapDemoAssgnGrp where grpName = :tableName";
        q = session.createQuery(sql);
        q.setParameter("tableName", tableName);
        Character res = (Character) q.uniqueResult();
        
        return ("Y").equals(res.toString());
	}

	public void updateDemoName(BhrEmpDemo demo) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpDemo SET demoRevCd = 1, namePre = :titleNew, nameF =:firstNameNew, nameL =:lastNameNew, nameM =:middleNameNew, nameFLng =:firstNameLNGNew, nameLLng =:lastNameLNGNew, nameMLng =:middleNameLNGNew, nameGen =:generationNew ");
		sql.append(", module ='Employee Portal'");
		sql.append(" WHERE empNbr = :employeeNumber ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("titleNew", demo.getNamePre());
		q.setParameter("firstNameNew", demo.getNameF());
		q.setParameter("lastNameNew", demo.getNameL());
		q.setParameter("middleNameNew", demo.getNameM());
		q.setParameter("firstNameLNGNew", demo.getNameFLng());
		q.setParameter("lastNameLNGNew", demo.getNameLLng());
		q.setParameter("middleNameLNGNew", demo.getNameMLng());
		q.setParameter("generationNew", demo.getNameGen());
		q.setParameter("employeeNumber", demo.getEmpNbr());
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
    	session.flush();
	}
	
	public void updateDemoMaritalStatus(BhrEmpDemo demo) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpDemo SET maritalStat = :maritalStat ");
		sql.append(", module ='Employee Acces'");
		sql.append(" WHERE empNbr = :employeeNumber ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("maritalStat", demo.getMaritalStat());
		q.setParameter("employeeNumber", demo.getEmpNbr());
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
    	session.flush();
	}
	
	public void updateDemoDriversLicense(BhrEmpDemo demo) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpDemo SET driversLicNbr = :driversLicNbr, driversLicSt =:driversLicSt ");
		sql.append(", module ='Employee Acces'");
		sql.append(" WHERE empNbr = :employeeNumber ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("driversLicNbr", demo.getDriversLicNbr());
		q.setParameter("driversLicSt", demo.getDriversLicSt());
		q.setParameter("employeeNumber", demo.getEmpNbr());
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
    	session.flush();
	}
	
	public void updateDemoRestrictionCodes(BhrEmpDemo demo) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpDemo SET restrictCd = :restrictCd, restrictCdPublic =:restrictCdPublic ");
		sql.append(", module ='Employee Acces'");
		sql.append(" WHERE empNbr = :employeeNumber ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("restrictCd", demo.getRestrictCd());
		q.setParameter("restrictCdPublic", demo.getRestrictCdPublic());
		q.setParameter("employeeNumber", demo.getEmpNbr());
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
    	session.flush();
	}
	
	public void updateDemoEmail(BhrEmpDemo demo) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpDemo SET email = :email, hmEmail =:hmEmail ");
		sql.append(", module ='Employee Acces'");
		sql.append(" WHERE empNbr = :employeeNumber ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("email", demo.getEmail());
		q.setParameter("hmEmail", demo.getHmEmail());
		q.setParameter("employeeNumber", demo.getEmpNbr());
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
    	session.flush();
	}
	
	public void updateDemoEmergencyContact(BhrEmpDemo demo) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpDemo SET emerContact = :emerContact, emerPhoneAc =:emerPhoneAc, emerPhoneNbr =:emerPhoneNbr, ");
		sql.append("emerPhoneExt = :emerPhoneExt, emerRel =:emerRel, emerNote =:emerNote");
		sql.append(", module ='Employee Acces'");
		sql.append(" WHERE empNbr = :employeeNumber ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("emerContact", demo.getEmerContact());
		q.setParameter("emerPhoneAc", demo.getEmerPhoneAc());
		q.setParameter("emerPhoneNbr", demo.getEmerPhoneNbr());
		q.setParameter("emerPhoneExt", demo.getEmerPhoneExt());
		q.setParameter("emerRel", demo.getEmerRel());
		q.setParameter("emerNote", demo.getEmerNote());
		q.setParameter("employeeNumber", demo.getEmpNbr());
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
    	session.flush();
	}
	
	public void updateDemoMailAddr(BhrEmpDemo demo) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpDemo SET addrNbr = :addrNbr, addrStr =:addrStr, addrApt =:addrApt, ");
		sql.append("addrCity = :addrCity, addrSt =:addrSt, addrZip =:addrZip, addrZip4 =:addrZip4");
		sql.append(", module ='Employee Acces'");
		sql.append(" WHERE empNbr = :employeeNumber ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("addrNbr", demo.getAddrNbr());
		q.setParameter("addrStr", demo.getAddrStr());
		q.setParameter("addrApt", demo.getAddrApt());
		q.setParameter("addrCity", demo.getAddrCity());
		q.setParameter("addrSt", demo.getAddrSt());
		q.setParameter("addrZip", demo.getAddrZip());
		q.setParameter("addrZip4", demo.getAddrZip4());
		q.setParameter("employeeNumber", demo.getEmpNbr());
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
    	session.flush();
	}
	
	public void updateDemoAltMailAddr(BhrEmpDemo demo) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpDemo SET smrAddrNbr = :smrAddrNbr, smrAddrStr =:smrAddrStr, smrAddrApt =:smrAddrApt, ");
		sql.append("smrAddrCity = :smrAddrCity, smrAddrSt =:smrAddrSt, smrAddrZip =:smrAddrZip, smrAddrZip4 =:smrAddrZip4");
		sql.append(", module ='Employee Acces'");
		sql.append(" WHERE empNbr = :employeeNumber ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("smrAddrNbr", demo.getSmrAddrNbr());
		q.setParameter("smrAddrStr", demo.getSmrAddrStr());
		q.setParameter("smrAddrApt", demo.getSmrAddrApt());
		q.setParameter("smrAddrCity", demo.getSmrAddrCity());
		q.setParameter("smrAddrSt", demo.getSmrAddrSt());
		q.setParameter("smrAddrZip", demo.getSmrAddrZip());
		q.setParameter("smrAddrZip4", demo.getSmrAddrZip4());
		q.setParameter("employeeNumber", demo.getEmpNbr());
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
    	session.flush();
	}
	
	public void updateDemoHomePhone(BhrEmpDemo demo) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpDemo SET phoneArea =:phoneArea, phoneNbr =:phoneNbr");
		sql.append(", module ='Employee Acces'");
		sql.append(" WHERE empNbr = :employeeNumber ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("phoneArea", demo.getPhoneArea());
		q.setParameter("phoneNbr", demo.getPhoneNbr());
		q.setParameter("employeeNumber", demo.getEmpNbr());
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
    	session.flush();
	}
	
	public void updateDemoCellPhone(BhrEmpDemo demo) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpDemo SET phoneAreaCell = :phoneAreaCell, phoneNbrCell =:phoneNbrCell");
		sql.append(", module ='Employee Acces'");
		sql.append(" WHERE empNbr = :employeeNumber ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("phoneAreaCell", demo.getPhoneAreaCell());
		q.setParameter("phoneNbrCell", demo.getPhoneNbrCell());
		q.setParameter("employeeNumber", demo.getEmpNbr());
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
    	session.flush();
	}
	
	public void updateDemoBusinessPhone(BhrEmpDemo demo) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpDemo SET phoneAreaBus = :phoneAreaBus, phoneNbrBus =:phoneNbrBus, busPhoneExt =:busPhoneExt");
		sql.append(", module ='Employee Acces'");
		sql.append(" WHERE empNbr = :employeeNumber ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("phoneAreaBus", demo.getPhoneAreaBus());
		q.setParameter("phoneNbrBus", demo.getPhoneNbrBus());
		q.setParameter("busPhoneExt", demo.getBusPhoneExt());
		q.setParameter("employeeNumber", demo.getEmpNbr());
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
    	session.flush();
	}

	public void deleteNamerequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaLglName WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
		session.flush();
	}
	
	public void deleteMaritalrequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaMrtlStat WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
		session.flush();
	}
	
	public void deleteDriversLicenserequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaDrvsLic WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
		session.flush();
	}
	
	public void deleteRestrictionCodesrequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaRestrict WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
		session.flush();
	}
	
	public void deleteEmailrequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaEmail WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
		session.flush();
	}
	
	public void deleteEmergencyContactrequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaEmerContact WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
		session.flush();
	}
	public void deleteMailAddrrequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaMailAddr WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
		session.flush();
	}
	public void deleteAltMailAddrrequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaAltMailAddr WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
		session.flush();
	}
	public void deleteHomePhonerequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaHmPhone WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
		session.flush();
	}
	public void deleteCellPhonerequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaCellPhone WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
		session.flush();
	}
	public void deleteBusinessPhonerequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaBusPhone WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
		session.flush();
	}
	
	public BhrEmpDemo retrieveEmployee(SearchUser searchUser) 
	{
		BhrEmpDemo demo = new BhrEmpDemo();
		Session session = this.getSession();
		Query q;
		String sql= "select email,nameF,nameL,hmEmail,empNbr, staffId from BhrEmpDemo where dob =:dob  and addrZip =:addrZip and ";
		if(searchUser.getEmpNumber()!=null && !("").equals(searchUser.getEmpNumber())) {
			sql += "empNbr =:empNbr";
		}else if(searchUser.getSsn()!=null && !("").equals(searchUser.getSsn())) {
			sql += "staffId =:staffId";
		}
        q = session.createQuery(sql);
        q.setParameter("dob", searchUser.getDateYear()+searchUser.getDateMonth()+searchUser.getDateDay());
        q.setParameter("addrZip", searchUser.getZipCode());
		if(searchUser.getEmpNumber()!=null && !("").equals(searchUser.getEmpNumber())) {
			q.setParameter("empNbr", searchUser.getEmpNumber());
		}else if(searchUser.getSsn()!=null && !("").equals(searchUser.getSsn())) {
			q.setParameter("staffId", searchUser.getSsn());
		}
        Object[] res = (Object[]) q.uniqueResult();
        if(res==null)
        	return null;
        else {
        	demo.setEmail((String) res[0]);
        	demo.setNameF((String) res[1]);
        	demo.setNameL((String) res[2]);
        	demo.setHmEmail((String) res[3]);
        	demo.setEmpNbr((String) res[4]);
        	demo.setStaffId((String) res[5]);
        }
        
        return demo;
	}
	
	public void updateEmailEmployee(String employeeNumber,String workEmail, String homeEmail) 
	{
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		if(StringUtil.isNullOrEmpty(workEmail) && (!StringUtil.isNullOrEmpty(workEmail))) {
			sql.append("UPDATE BhrEmpDemo SET hmEmail = :homeEmail ");
			sql.append(" WHERE empNbr = :employeeNumber ");
		}
		else if((!StringUtil.isNullOrEmpty(workEmail)) && StringUtil.isNullOrEmpty(workEmail)) {
		
			sql.append("UPDATE BhrEmpDemo SET email = :workEmail ");
			sql.append(" WHERE empNbr = :employeeNumber ");
		}
		else if((!StringUtil.isNullOrEmpty(workEmail)) && (!StringUtil.isNullOrEmpty(workEmail))) {
			sql.append("UPDATE BhrEmpDemo SET email = :workEmail, hmEmail = :homeEmail ");
			sql.append(" WHERE empNbr = :employeeNumber ");
		}
		else {
			return;
		}
		
		/*sql.append("UPDATE BhrEmpDemo SET email = :workEmail, hmEmail = :homeEmail ");
		sql.append(" WHERE empNbr = :employeeNumber ");*/
		
		Query q = session.createQuery(sql.toString());
		q.setParameter("workEmail", workEmail);
		q.setParameter("homeEmail", homeEmail);
		q.setParameter("employeeNumber", employeeNumber);
		Integer res = q.executeUpdate();
		Log.debug("Result of update: "+ res);
    	session.flush();
	}

	public Boolean isSupervisor(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM BhrEapEmpToSpvsr E2S2 WHERE E2S2.spvsrEmpNbr=:empNbr");
		Query q = session.createQuery(sql.toString());
		q.setParameter("empNbr", empNbr);
		Long result = (Long) q.uniqueResult();
		return result>0L;
	}
	
	public Boolean isLeavePMISSupervisor(String employeeNumber) {
		Session session = this.getSession();
		LeaveEmployeePMISData empPMISData=null;
		String sqlPMISData = "SELECT TOP 1 PPC.BILLET_NBR, PPC.POS_NBR FROM BHR_PMIS_POS_CTRL PPC WHERE PPC.OCC_EMP_NBR= :employeeNumber AND PPC.POS_TYP='P' AND PPC.CYR_NYR_FLG='C' ORDER BY PPC.PAY_FREQ DESC";
		Query q = session.createSQLQuery(sqlPMISData);
		q.setParameter("employeeNumber", employeeNumber);
		
		 Object[] res = (Object[]) q.uniqueResult();
        if(res!=null) {
        	empPMISData = new LeaveEmployeePMISData();
        	empPMISData.setBilletNumber((String) res[0]);
			empPMISData.setPosNumber((String) res[1]);
        }
		
		if (empPMISData==null) {
			return false;
		} else {
			String sqlCount = "SELECT COUNT(*) FROM BHR_PMIS_POS_CTRL PPC WHERE PPC.SPVSR_BILLET_NBR=:billetNumber AND PPC.SPVSR_POS_NBR= :posNumber " +
					"AND PPC.PAY_FREQ=(SELECT MAX(PPC2.PAY_FREQ) FROM BHR_PMIS_POS_CTRL PPC2 WHERE PPC2.OCC_EMP_NBR=PPC.OCC_EMP_NBR AND PPC2.POS_TYP='P' AND PPC2.CYR_NYR_FLG='C') " +
					"AND PPC.POS_TYP='P' AND PPC.CYR_NYR_FLG='C'";
			
			Query q1 = session.createSQLQuery(sqlCount);
			q1.setParameter("billetNumber", empPMISData.getBilletNumber().trim());
			q1.setParameter("posNumber", empPMISData.getPosNumber().trim());
			Integer count = (Integer) q1.uniqueResult();
			return count>0;
		}		
	}

	public Boolean isTempApprover(String empNbr) {
		Session session = this.getSession();
		String sql = "SELECT ISNULL((SELECT DISTINCT 1 FROM BEA_EMP_LV_TMP_APPROVERS WHERE TMP_APPRVR_EMP_NBR=:employeeNumber AND GETDATE() >= DATETIME_FROM AND GETDATE() <= DATETIME_TO), " +
				"(SELECT COUNT(*) FROM BEA_EMP_LV_RQST ELR,  BEA_EMP_LV_WORKFLOW ELW WHERE ELW.APPRVR_EMP_NBR=:employeeNumber AND ELW.LV_ID = ELR.ID  AND ELR.STATUS_CD = 'P' " + 
					"AND ELW.INSERT_DATETIME = (SELECT MAX(ELW2.INSERT_DATETIME) FROM BEA_EMP_LV_WORKFLOW ELW2 WHERE ELW2.LV_ID=ELW.LV_ID) )) AS ACCESS";
		Query q = session.createSQLQuery(sql);
		q.setParameter("employeeNumber", empNbr);
		Integer result = (Integer) q.uniqueResult();
		return result>0;
	}
	
	public Integer employeePayCampusLeaveCampusCount(String employeeNumber) {
		Session session = this.getSession();
		String sql = "select count(*) from bhr_emp_pay bep, bthr_eap_campus btec "
				+ "where bep.emp_nbr = :employeeNumber and bep.cyr_nyr_flg = 'C' and bep.pay_campus=btec.campus_id and bep.pay_dept=btec.dept";
		Query q = session.createSQLQuery(sql);
		q.setParameter("employeeNumber", employeeNumber);
		Integer result = (Integer) q.uniqueResult();
		return result;
	}
	
	// ALC-26 Lock account on the 5th login failed
    public int getLockTries(){
    	try {
    		Query query = this.getSession()
                    .createSQLQuery("SELECT PREF_VALUE FROM TXEIS_PREFERENCES where PREF_NAME ='lock_tries'");
    		Integer res = query.uniqueResult()==null?5:Integer.parseInt(((String) query.uniqueResult()).toString());
    		return   res;
    	}
    	catch(Exception ex) {
    		return 5; //default as 5 times
    	}
        
    }
    public int getLockTimes(){
    	try {
    		Query query = this.getSession()
                    .createSQLQuery("SELECT PREF_VALUE FROM TXEIS_PREFERENCES where PREF_NAME ='lock_timeout'");
    		Integer res = query.uniqueResult()==null?15:Integer.parseInt(((String) query.uniqueResult()).toString());
    		return   res;
    	}
    	catch(Exception ex) {
    		return 15; //default as 15 minutes
    	}
        
    }

    public Integer getUserPWDFailed(String userName) {
        Query query = this.getSession()
                          .createSQLQuery("SELECT USR_LOG_TRIES FROM BEA_USERS bcp WHERE bcp.USRNAME=? " );
        query.setParameter(0, userName);
        Integer res = (Integer) query.uniqueResult();
        return res;
    }

    public int updateUserPWDFailed(String userName) {
        Query query = this.getSession()
                          .createSQLQuery("Update  BEA_USERS  set USR_LOG_TRIES = case when USR_LOG_TRIES is null then 1 else USR_LOG_TRIES+1 end WHERE USRNAME=  ? ");
        query.setParameter(0, userName);
       return query.executeUpdate();
    }

    public Integer clearUserPWDFailed(String userName) {
        Query query = this.getSession()
                          .createSQLQuery("Update  BEA_USERS bcp set bcp.USR_LOG_TRIES = 0 WHERE bcp.USRNAME= ? ");
        query.setParameter(0, userName);
        Integer res = query.executeUpdate();
        return res;
    }
    
    public int lockedSPUsers(String userName){
        Query query = this.getSession()
                          .createSQLQuery("Update  BEA_USERS bcp set bcp.USR_TIMEOUT_DT=:lockDate WHERE bcp.USRNAME= :userName ");
        query.setParameter("userName", userName);
        query.setParameter("lockDate", new Date());
      return   query.executeUpdate();
    }
    
    public void UpdateSessionToken(String username, String eSessionID) {
        StringBuilder sb = new StringBuilder();
        sb.append("Update ");
        sb.append(" DS_DOC_STOR_TOKEN ");
        sb.append(" Set ACCESS_L = GetDate() ");
        sb.append(" Where token = ? ");
        sb.append(" And usr_name = ? ; ");

        SQLQuery q = this.getSession().createSQLQuery(sb.toString());
        q.setString(0, eSessionID);
        q.setString(1, username);

        try {
            int result = q.executeUpdate();
            if (result == 0) {
                StringBuilder insert = new StringBuilder();
                insert.append("Insert Into ");
                insert.append(" DS_DOC_STOR_TOKEN ");
                insert.append(" (TOKEN, USR_NAME, ACCESS_L) ");
                insert.append(" Values (?, ?, GetDate()) ;");

                SQLQuery inQ = this.getSession().createSQLQuery(insert.toString());
                inQ.setString(0, eSessionID);
                inQ.setString(1, username);
                inQ.executeUpdate();
            }
        } catch (Exception e) {
        	logger.error("Exception", e);
        }
    }

    public void DeleteSessionToken(String eSessionID) {
        StringBuilder delete = new StringBuilder();
        delete.append("Delete From ");
        delete.append(" DS_DOC_STOR_TOKEN ");
        delete.append(" Where token = ? ");
        SQLQuery q = this.getSession().createSQLQuery(delete.toString());
        q.setString(0, eSessionID);

        try {
            q.executeUpdate();
        } catch (Exception e) {
        	logger.error("Exception", e);
        }

    }
    
	public Integer getSecUserForEmpNbr(String empNbr) throws Exception{
		Integer secUserNbr = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("Select USR_ID FROM SEC_USERS WHERE EMP_NBR=:empNbr");
			Query q = this.getSession().createSQLQuery(sql.toString());
			
			q.setParameter("empNbr", empNbr);
			
			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object rs : res) {
				secUserNbr = (Integer) rs;
			}
		} catch (Exception e) {
			logger.error("Error occurred on getSecUserFromEmpNbr method while retrieving :", e);
		}
		return secUserNbr;
	}
    
    
}
