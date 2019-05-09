package com.esc20.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
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
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrEmpPayId;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.SearchUser;
import com.esc20.util.DataSourceContextHolder;
import com.esc20.util.DynamicDataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

@Repository
public class AppUserDao extends HibernateDaoSupport{
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private ReferenceDao referenceDao;
    
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
        		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
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
        StringBuilder sql = new StringBuilder();
		sql.append("SELECT demo.staffId, demo.nameF, demo.nameM, demo.nameL, demo.nameGen, demo.dob, demo.sex, ");
		sql.append("demo.addrNbr, demo.addrStr, demo.addrApt, demo.addrCity, demo.addrSt, demo.addrZip, demo.addrZip4, ");
		sql.append("demo.phoneArea, demo.phoneNbr, demo.email, demo.hmEmail, '', ");
		sql.append("demo.namePre, demo.smrAddrNbr, demo.smrAddrStr, demo.smrAddrApt, demo.smrAddrCity, demo.smrAddrSt, demo.smrAddrZip, demo.smrAddrZip4, ");
		sql.append("demo.phoneAreaBus, demo.phoneNbrBus, demo.busPhoneExt, demo.phoneAreaCell, demo.phoneNbrCell, ");
		sql.append("demo.restrictCd, demo.restrictCdPublic, demo.maritalStat, demo.driversLicNbr, demo.driversLicSt, ");
		sql.append("demo.emerContact, demo.emerPhoneAc, demo.emerPhoneNbr, demo.emerPhoneExt, demo.emerRel, demo.emerNote,demo.avatar ");
		sql.append("FROM BhrEmpDemo demo WHERE demo.empNbr=:empNbr");
        Query q = session.createQuery(sql.toString());
        q.setParameter("empNbr", empNbr);
        Object[] res =  (Object[]) q.uniqueResult();
        BhrEmpDemo userInfo = new BhrEmpDemo(
        		empNbr, (String) res[0],(String) res[1], (String) res[2],        		
        		(String) res[3],(Character) res[4], (String) res[5], (Character) res[6], (String) res[7], 
        		(String) res[8], (String) res[9], (String) res[10], (String) res[11], (String) res[12], (String) res[13],
    			 (String) res[14],(String) res[15],(String) res[16],(String) res[17],
    			 (String) res[19],(String) res[20],(String) res[21],(String) res[22],(String) res[23],(String) res[24],(String) res[25],(String) res[26],
    			 (String) res[27],(String) res[28],(String) res[29],(String) res[30],(String) res[31],
    			 (Character) res[32],(Character) res[33],(Character) res[34],(String) res[35],(String) res[36],
    			 (String) res[37],(String) res[38],(String) res[39],(String) res[40],(String) res[41],(String) res[42],(String) res[43]);
        List<Code> gens = referenceDao.getGenerations();
        userInfo.setGenDescription("");
        for(Code gen: gens) {
        	if(userInfo.getNameGen()!=null && gen.getCode().equals(userInfo.getNameGen().toString())) {
        		userInfo.setGenDescription(gen.getDescription());
        	}
        }
        return userInfo;
	}
	
	public District getDistrict(String district) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT dr.distName, dr.strNbrDist, dr.strNameDist, ");
		sql.append("dr.cityNameDist, dr.stateCd, dr.zipDist, dr.zip4Dist, dr.areaCdDist, dr.phoneNbrDist ");
		sql.append(" FROM DrDemo dr");
		sql.append(" WHERE dr.id.schYr = (SELECT MAX(dr2.id.schYr) from DrDemo dr2 where dr2.id.distId =:district) AND");
		sql.append(" dr.id.distId =:district");
        Query q = session.createQuery(sql.toString());
        q.setParameter("district", district);
        Object[] res =  (Object[]) q.uniqueResult();
        District dis = new District(res[0],res[1],res[2],res[3],res[4],res[5],res[6],res[7],res[8]);
        
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
		sql.append("UPDATE BhrEmpDemo SET demoRevCd = 1, namePre = :titleNew, nameF =:firstNameNew, nameL =:lastNameNew, nameM =:middleNameNew, nameGen =:generationNew ");
		sql.append(", module ='Employee Acces'");
		sql.append(" WHERE empNbr = :employeeNumber ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("titleNew", demo.getNamePre());
		q.setParameter("firstNameNew", demo.getNameF());
		q.setParameter("lastNameNew", demo.getNameL());
		q.setParameter("middleNameNew", demo.getNameM());
		q.setParameter("generationNew", demo.getNameGen());
		q.setParameter("employeeNumber", demo.getEmpNbr());
		Integer res = q.executeUpdate();
    	session.flush();
	}
	
	public void updateDemoAvatar(BhrEmpDemo demo) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpDemo SET avatar = :avatar ");
		sql.append(", module ='Employee Acces'");
		sql.append(" WHERE empNbr = :employeeNumber ");
		Query q = session.createQuery(sql.toString());
		q.setParameter("avatar", demo.getAvatar());
		q.setParameter("employeeNumber", demo.getEmpNbr());
		Integer res = q.executeUpdate();
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
    	session.flush();
	}

	public void deleteNamerequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaLglName WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		session.flush();
	}
	
	public void deleteMaritalrequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaMrtlStat WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		session.flush();
	}
	
	public void deleteDriversLicenserequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaDrvsLic WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		session.flush();
	}
	
	public void deleteRestrictionCodesrequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaRestrict WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		session.flush();
	}
	
	public void deleteEmailrequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaEmail WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		session.flush();
	}
	
	public void deleteEmergencyContactrequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaEmerContact WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		session.flush();
	}
	public void deleteMailAddrrequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaMailAddr WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		session.flush();
	}
	public void deleteAltMailAddrrequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaAltMailAddr WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		session.flush();
	}
	public void deleteHomePhonerequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaHmPhone WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		session.flush();
	}
	public void deleteCellPhonerequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaCellPhone WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		session.flush();
	}
	public void deleteBusinessPhonerequest(String empNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BeaBusPhone WHERE id.empNbr = :employeeNumber AND statCd = 'P'");
		Query q = session.createQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		Integer res = q.executeUpdate();
		session.flush();
	}
	
	public BhrEmpDemo retrieveEmployee(SearchUser searchUser) 
	{
		BhrEmpDemo demo = new BhrEmpDemo();
		Session session = this.getSession();
		Query q;
		String sql= "select email,nameF,nameL,hmEmail from BhrEmpDemo where empNbr =:empNbr and dob =:dob  and addrZip =:addrZip";
        q = session.createQuery(sql);
        q.setParameter("empNbr", searchUser.getEmpNumber());
        q.setParameter("dob", searchUser.getDateYear()+searchUser.getDateMonth()+searchUser.getDateDay());
        q.setParameter("addrZip", searchUser.getZipCode());
        Object[] res = (Object[]) q.uniqueResult();
        if(res==null)
        	return null;
        else {
        	demo.setEmail((String) res[0]);
        	demo.setNameF((String) res[1]);
        	demo.setNameL((String) res[2]);
        	demo.setHmEmail((String) res[3]);
        }
        
        return demo;
	}
	
	public void updateEmailEmployee(String employeeNumber,String workEmail, String homeEmail) 
	{
		BhrEmpDemo demo = new BhrEmpDemo();
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BhrEmpDemo SET email = :workEmail, hmEmail = :homeEmail ");
		sql.append(" WHERE empNbr = :employeeNumber ");
		
		Query q = session.createQuery(sql.toString());
		q.setParameter("workEmail", workEmail);
		q.setParameter("homeEmail", homeEmail);
		q.setParameter("employeeNumber", employeeNumber);
		Integer res = q.executeUpdate();
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

	public Object TestDemo(String empNbr) {
		Session session = this.getSession();
        Query query = session.createSQLQuery("select * from Contacts");
        List<Object[]> res = query.list();
		return null;
	}
	
}
