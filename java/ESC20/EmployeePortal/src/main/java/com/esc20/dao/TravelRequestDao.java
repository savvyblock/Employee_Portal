package com.esc20.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esc20.model.BeaEmpTrvl;
import com.esc20.nonDBModels.BfnOptionsTrvl;
import com.esc20.nonDBModels.CalculateMileageDao;
import com.esc20.nonDBModels.ResultUtil;
import com.esc20.nonDBModels.TravelCheckDetails;
import com.esc20.nonDBModels.TravelInfo;
import com.esc20.nonDBModels.TravelRequestCalendar;
import com.esc20.nonDBModels.TravelRequestInfo;
import com.esc20.nonDBModels.TravelType;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional (readOnly = true)

@Repository
public class TravelRequestDao {
	private Logger logger = LoggerFactory.getLogger(TravelRequestDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public List<TravelRequestInfo> getTravelRequestInfo(String employeeNumber) {
		List<TravelRequestInfo> addressList = new ArrayList<TravelRequestInfo>();
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append("BV.vendorNbr, ");
			sql.append("BA.addressAtn, ");
			sql.append("BA.addressStreet, ");
			sql.append("BA.addressCity, ");
			sql.append("BA.addressSt, ");
			sql.append("BA.addressAddrZip, ");
			sql.append("BA.addressAddrZip4, ");
			sql.append("ED.trvlCommuteDist ");
			sql.append("FROM BhrEmpDemo ED ");
			sql.append("JOIN BfnVendor BV ON BV.einSsn = ED.staffId ");
			sql.append("JOIN BfnAddress BA ON BV.vendorNbr = BA.vendorNbr ");
			sql.append("AND BV.vendorNbr = (SELECT min(BV.vendorNbr) FROM BfnVendor BV, BhrEmpDemo ED JOIN BfnVendor BV ON BV.einSsn = ED.staffId ");
			sql.append("WHERE ED.empNbr = :employeeNumber ");
			sql.append("AND BV.statusFlg = 'A' AND BV.einSsnFlg = 'S' ");
			sql.append("AND isNull(trim(ED.staffId), '') <> '')");
			Query q = session.createQuery(sql.toString());
			q.setParameter("employeeNumber", employeeNumber);
			@SuppressWarnings("unchecked")
			List<Object[]> list = q.list();

			for (Object[] i : list) {
				TravelRequestInfo temp = new TravelRequestInfo((String) i[0], (String) i[1], (String) i[2],
						(String) i[3], (String) i[4], (String) i[5], (String) i[6], (BigDecimal) i[7]);
				addressList.add(temp);
			}
		} catch (Exception e) {
			logger.error("Error occurred on while getting getTravelRequestInfo :", e.getMessage());
		}
		return addressList;
	}

	@SuppressWarnings("null")
	public List<TravelRequestInfo> getVendorAddressInfo(String vendorNbr) {
		List<TravelRequestInfo> addressList = new ArrayList<TravelRequestInfo>();
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT C.ADDRESS_ATN, ");
			sql.append("C.ADDRESS_STREET, ");
			sql.append("C.ADDRESS_CITY, ");
			sql.append("C.ADDRESS_ST, ");
			sql.append("C.ADDRESS_ADDR_ZIP, ");
			sql.append("C.ADDRESS_ADDR_ZIP4, ");
			sql.append("C.ADDRESS_SEQ_NBR, ");
			sql.append("C.ADDRESS_TYPE_CD, ");
			sql.append("C.VENDOR_NBR, ");
			sql.append("C.PARTY_TYPE_CD, ");
			sql.append("C.PARTY_SEQ_NBR, ");
			sql.append("D.CON_MECH_AREA_CODE, ");
			sql.append("D.CON_MECH_NUMBER, ");
			sql.append("A.DOING_BUS_AS_NAME, ");
			sql.append("( CASE WHEN ( isNull( trim( C.VENDOR_NAME ), '' ) <> '' ) THEN C.VENDOR_NAME ELSE A.VENDOR_NAME END ) AS VENDOR_NAME, ");
			sql.append("C.COUNTRY ");
			sql.append("FROM BFN_VENDOR A LEFT OUTER JOIN BFN_PARTY B ON ( B.VENDOR_NBR = A.VENDOR_NBR ) AND ( B.PARTY_TYPE_CD = 'PV' ) ");
			sql.append("LEFT OUTER JOIN BFN_ADDRESS C ON ( C.VENDOR_NBR = B.VENDOR_NBR ) AND ( C.PARTY_SEQ_NBR = B.PARTY_SEQ_NBR ) AND ( C.PARTY_TYPE_CD = B.PARTY_TYPE_CD ) ");
			sql.append("LEFT OUTER JOIN BFN_CONTACT_MECHANISM D ON ( D.VENDOR_NBR = A.VENDOR_NBR ) AND ( D.PARTY_TYPE_CD = B.PARTY_TYPE_CD ) AND ( D.CON_MECH_TYPE_CD = B.PARTY_TYPE_CD ) ");
			sql.append("WHERE ( A.VENDOR_NBR = :vendorNbr ) AND ");
			sql.append("( C.ADDRESS_TYPE_CD = 'PR') AND ");
			sql.append("( ( isNull( trim( C.ADDRESS_ATN ), '' ) <> '' ) OR ");
			sql.append("( isNull( trim( C.ADDRESS_STREET ), '' ) <> '' ) OR ");
			sql.append("( isNull( trim( C.ADDRESS_CITY ), '' ) <> '' ) OR ");
			sql.append("( isNull( trim( C.ADDRESS_ST ), '' ) <> '' ) OR ");
			sql.append("( isNull( trim( C.ADDRESS_ADDR_ZIP ), '' ) <> '' ) OR ");
			sql.append("( isNull( trim( C.ADDRESS_ADDR_ZIP4 ), '' ) <> '' ) ) ");
			sql.append("UNION ");
			sql.append("SELECT C.ADDRESS_ATN, ");
			sql.append("C.ADDRESS_STREET, ");
			sql.append("C.ADDRESS_CITY, ");
			sql.append("C.ADDRESS_ST, ");
			sql.append("C.ADDRESS_ADDR_ZIP, ");
			sql.append("C.ADDRESS_ADDR_ZIP4, ");
			sql.append("C.ADDRESS_SEQ_NBR, ");
			sql.append("C.ADDRESS_TYPE_CD, ");
			sql.append("C.VENDOR_NBR, ");
			sql.append("C.PARTY_TYPE_CD, ");
			sql.append("C.PARTY_SEQ_NBR, ");
			sql.append("D.CON_MECH_AREA_CODE, ");
			sql.append("D.CON_MECH_NUMBER, ");
			sql.append("A.DOING_BUS_AS_NAME, ");
			sql.append("C.VENDOR_NAME, ");
			sql.append("C.COUNTRY ");
			sql.append("FROM BFN_VENDOR A LEFT OUTER JOIN BFN_PARTY B ON ( B.VENDOR_NBR = A.VENDOR_NBR ) AND ( B.PARTY_TYPE_CD = 'PV' ) ");
			sql.append("LEFT OUTER JOIN BFN_ADDRESS C ON ( C.VENDOR_NBR = B.VENDOR_NBR ) AND ( C.PARTY_SEQ_NBR = B.PARTY_SEQ_NBR ) AND ( C.PARTY_TYPE_CD = B.PARTY_TYPE_CD ) ");
			sql.append("LEFT OUTER JOIN BFN_CONTACT_MECHANISM D ON ( D.VENDOR_NBR = A.VENDOR_NBR ) AND ( D.PARTY_TYPE_CD = B.PARTY_TYPE_CD ) AND ( D.CON_MECH_TYPE_CD = B.PARTY_TYPE_CD ) ");
			sql.append("WHERE ( A.VENDOR_NBR = :vendorNbr ) AND ");
			sql.append("( C.ADDRESS_TYPE_CD = 'PV' ) ");
			sql.append("ORDER BY 9 ASC, 8 ASC");

			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("vendorNbr", vendorNbr);

			@SuppressWarnings("unchecked")
			List<Object[]> list = q.list();

			List<TravelRequestInfo> addressListPR = new ArrayList<TravelRequestInfo>();
			List<TravelRequestInfo> addressListPV = new ArrayList<TravelRequestInfo>();
			for (Object[] i : list) {
				TravelRequestInfo temp = new TravelRequestInfo();
				String addressCheck = ((String) i[7]);
				if (StringUtils.isNotEmpty(addressCheck) && StringUtils.isNotEmpty(addressCheck)) {
					if (addressCheck.equals("PV")) {
						temp.setAddressAtn((String) i[0]);
						temp.setAddressStreet((String) i[1]);
						temp.setAddressCity((String) i[2]);
						temp.setAddressState((String) i[3]);
						temp.setAddressZip((String) i[4]);
						temp.setAddressZip4((String) i[5]);
						addressListPV.add(temp);
					} else if (addressCheck.equals("PR")) {
						temp.setAddressAtn((String) i[0]);
						temp.setAddressStreet((String) i[1]);
						temp.setAddressCity((String) i[2]);
						temp.setAddressState((String) i[3]);
						temp.setAddressZip((String) i[4]);
						temp.setAddressZip4((String) i[5]);
						addressListPR.add(temp);
					}
				}
			}
			if (addressListPR != null && !addressListPR.isEmpty() && addressListPV != null
					&& !addressListPV.isEmpty()) {
				addressList = addressListPR;
			} else if (addressListPR != null && !addressListPR.isEmpty() && addressListPV == null
					&& addressListPV.isEmpty()) {
				addressList = addressListPR;
			} else {
				addressList = addressListPV;
			}
		} catch (Exception e) {
			logger.error("Error occurred on while getting getVendorAddressInfo :", e.getMessage());
		}
		return addressList;
	}

	@Transactional (propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public boolean updateTravelRequestCommuteDist(String employeeNumber, String changeCommuteDist) {
		Integer res = 0;
		try {
			Session session = this.getSession();
			String sql = "UPDATE BHR_EMP_DEMO SET TRVL_COMMUTE_DIST=:changeCommuteDist WHERE EMP_NBR=:employeeNumber";
			Query q = session.createSQLQuery(sql);
			q.setParameter("employeeNumber", employeeNumber);
			q.setParameter("changeCommuteDist", changeCommuteDist);
			res = q.executeUpdate();
			session.flush();
		} catch (Exception e) {
			logger.error("Error occurred on while updating updateTravelRequestCommuteDist :", e.getMessage());
		}
		return res > 0;
	}

	public List<TravelRequestInfo> getCampuses() {
		List<TravelRequestInfo> campusList = new ArrayList<TravelRequestInfo>();
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT trim(CAMPUS_NAME) as CAMPUS_NAME FROM CR_DEMO WHERE isNull(trim(CAMPUS_NAME), '') <> '' ORDER BY 1 ASC");
			Query q = session.createSQLQuery(sql.toString());
			@SuppressWarnings("unchecked")
			List<String> list = q.list();
			for (String i : list) {
				TravelRequestInfo temp = new TravelRequestInfo(i);
				campusList.add(temp);
			}
		} catch (Exception e) {
			logger.error("Error occurred on getCampuses method while getting campus name :", e.getMessage());
		}
		return campusList;
	}

	public List<TravelRequestInfo> getCampusPay(String employeeNumber) {
		List<TravelRequestInfo> campusPay = new ArrayList<TravelRequestInfo>();
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append(
					"SELECT DISTINCT CAMPUS = (pay.PAY_CAMPUS || (CASE WHEN isNull(trim(CR.CAMPUS_NAME), '') <> '' THEN ' : ' || CR.CAMPUS_NAME ELSE '' END )), pay.PAY_FREQ, pay.STAT_CD ");
			sql.append("FROM BHR_EMP_DEMO demo, BHR_EMP_EMPLY emply, BHR_EMP_PAY pay ");
			sql.append(
					"LEFT OUTER JOIN CR_DEMO CR ON (CR.CAMPUS_ID = pay.PAY_CAMPUS) AND (CR.SCH_YR = (SELECT MAX(CAMPUS.SCH_YR) FROM CR_DEMO CAMPUS)) WHERE (emply.EMP_NBR = demo.EMP_NBR) ");
			sql.append("AND demo.EMP_NBR= :employeeNumber ");
			sql.append("AND (pay.EMP_NBR = emply.EMP_NBR) AND (pay.CYR_NYR_FLG = (CASE ");
			sql.append(
					"WHEN (demo.NON_EMP = 'Y') THEN (SELECT A.CYR_NYR_FLG FROM BHR_EMP_PAY A WHERE A.EMP_NBR = demo.EMP_NBR) ");
			sql.append(
					"WHEN (demo.NON_EMP = 'N') AND ((SELECT COUNT(*) FROM BHR_EMP_PAY A WHERE (A.CYR_NYR_FLG = 'C') AND (A.PAY_FREQ IN ('4', '5', '6')) AND (A.EMP_NBR = demo.EMP_NBR) AND (A.STAT_CD = '1')) > 0) ");
			sql.append(
					"THEN (SELECT MAX(C.CYR_NYR_FLG) FROM BHR_EMP_PAY C WHERE (C.CYR_NYR_FLG = 'C') AND (C.PAY_FREQ IN ('4', '5', '6')) AND (C.EMP_NBR = demo.EMP_NBR) AND (C.STAT_CD = '1')) ");
			sql.append(
					"WHEN (demo.NON_EMP = 'N') AND ((SELECT COUNT(*) FROM BHR_EMP_PAY A WHERE (A.CYR_NYR_FLG = 'C') AND (A.PAY_FREQ IN ('4', '5', '6')) AND (A.EMP_NBR = demo.EMP_NBR) AND (A.STAT_CD = '2')) > 0) ");
			sql.append(
					"THEN (SELECT MAX(C.CYR_NYR_FLG) FROM BHR_EMP_PAY C WHERE (C.CYR_NYR_FLG = 'C') AND (C.PAY_FREQ IN ('4', '5', '6')) AND (C.EMP_NBR = demo.EMP_NBR) AND (C.STAT_CD = '2')) ");
			sql.append(
					"ELSE (SELECT MAX(C.CYR_NYR_FLG) FROM BHR_EMP_PAY C WHERE  (C.CYR_NYR_FLG = 'N') AND (C.PAY_FREQ IN ('D', 'E', 'F')) AND (C.EMP_NBR = demo.EMP_NBR)) END)) ");
			sql.append("ORDER BY pay.STAT_CD ASC, pay.PAY_FREQ DESC, CAMPUS ASC");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("employeeNumber", employeeNumber);
			@SuppressWarnings("unchecked")
			List<Object[]> list = q.list();
			for (Object[] i : list) {
				TravelRequestInfo temp = new TravelRequestInfo((String) i[0].toString());
				campusPay.add(temp);
			}
		} catch (Exception e) {
			logger.error("Error occurred on getCampusPay method while getting campus pay record :", e.getMessage());
		}
		return campusPay;
	}

	public List<TravelInfo> getTravelInfo(String employeeNumber, String SearchType, String StartDate, String EndDate) {
		List<TravelInfo> result = new ArrayList<TravelInfo>();
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT TRIP_NBR as trvlNbr, TRVL_DT as firstDate, OVRNIGHT as ovrnight, TRVL_REQ_STAT as status ");
			sql.append("FROM rsccc.BEA_EMP_TRVL ");
			sql.append("WHERE trim(TRIP_SEQ_NBR)= '1' ");
			sql.append("AND EMP_NBR=:employeeNumber ");
			if (StringUtils.isNotEmpty(SearchType) && StringUtils.isNotBlank(SearchType)) {
				if (SearchType.equals("L")) {
					sql.append("");
				} else if (SearchType.equals("S")) {
					sql.append("AND TRVL_REQ_STAT='S' ");
				} else if (SearchType.equals("P")) {
					sql.append("AND TRVL_REQ_STAT='P' ");
				} else if (SearchType.equals("A")) {
					sql.append("AND TRVL_REQ_STAT='A' ");
				} else if (SearchType.equals("R")) {
					sql.append("AND TRVL_REQ_STAT='R' ");
				}
			}
			if (StringUtils.isNotEmpty(StartDate) && StringUtils.isNotEmpty(EndDate)) {
				sql.append("AND TRVL_DT BETWEEN :StartDate AND :EndDate ");
			}
			if (StringUtils.isNotEmpty(StartDate) && StringUtils.isEmpty(EndDate)) {
				sql.append("AND TRVL_DT >= :StartDate ");
			}
			if (StringUtils.isEmpty(StartDate) && StringUtils.isNotEmpty(EndDate)) {
				sql.append("AND TRVL_DT <= :EndDate ");
			}
			if (StringUtils.isEmpty(SearchType) && StringUtils.isEmpty(StartDate) && StringUtils.isEmpty(EndDate)) {
				sql.append("");
			}
			sql.append("ORDER BY TRIP_NBR ASC");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("employeeNumber", employeeNumber);
			if (StringUtils.isNotEmpty(StartDate) && StringUtils.isNotEmpty(EndDate)) {
				q.setParameter("StartDate", StartDate);
				q.setParameter("EndDate", EndDate);
			}
			if (StringUtils.isNotEmpty(StartDate) && StringUtils.isEmpty(EndDate)) {
				q.setParameter("StartDate", StartDate);
			}
			if (StringUtils.isEmpty(StartDate) && StringUtils.isNotEmpty(EndDate)) {
				q.setParameter("EndDate", EndDate);
			}
			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();

			TravelInfo travelInfo;
			for (Object[] i : res) {
				travelInfo = new TravelInfo((String) i[0].toString(), (String) i[1].toString(),
						(String) i[2].toString(), (String) i[3].toString());
				result.add(travelInfo);
			}
		} catch (Exception e) {
			logger.error("Error occurred on getTravelInfo method while getting travel information record :",
					e.getMessage());
		}
		return result;
	}

	public List<TravelType> getTravelStatus() {
		List<TravelType> travelStatusList = new ArrayList<TravelType>();
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT TRVL_REQ_STAT, TRVL_REQ_STAT_DESC FROM BTHR_TRVL_REQ_STAT");
			Query q = session.createSQLQuery(sql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> list = q.list();

			for (Object[] i : list) {
				TravelType temp = new TravelType((String) i[0].toString(), (String) i[1].toString());
				travelStatusList.add(temp);
			}
		} catch (Exception e) {
			logger.error("Error occurred on getTravelStatus method while getting travel status record :",
					e.getMessage());
		}
		return travelStatusList;
	}
	
	public String getNxtTripNbr() {
		String nxtTripNbr = "";
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("select NXT_TRIP_NBR from BFN_OPTIONS_TRVL");
			Query q = session.createSQLQuery(sql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();

			for (Object rs : res) {
				nxtTripNbr = ResultUtil.getString(rs);
			}
		} catch (Exception e) {
			logger.error("Error occurred on getNxtTripNbr method while getting next trip number :", e.getMessage());
		}
		return nxtTripNbr;
	}
	
	public BigDecimal getCommuteDistance(String employeeNumber) {
		BigDecimal commuteDistance = BigDecimal.ZERO;
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("select TRVL_COMMUTE_DIST from BHR_EMP_DEMO where EMP_NBR=:employeeNumber");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("employeeNumber", employeeNumber);
			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object rs : res) {
				commuteDistance = (BigDecimal) rs;
			}
		} catch (Exception e) {
			logger.error("Error occurred on getCommuteDistance method while trvl commute distance from table :",
					e.getMessage());
		}
		return commuteDistance;
	}
	
	public BfnOptionsTrvl getBfnOptionsTrvl() {
		BfnOptionsTrvl bfnOptionsTrvl = new BfnOptionsTrvl();
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append(
					"select REQ_ST_END_TIMES, REQ_ODOMETER_RDG, MILEAGE_RT, LOC_LOCKING, BRKFST_RT, LUNCH_RT, DIN_RT from BFN_OPTIONS_TRVL");
			Query q = session.createSQLQuery(sql.toString());
			Object[] res = (Object[]) q.list().get(0);
			bfnOptionsTrvl = new BfnOptionsTrvl((String) res[0].toString(), (String) res[1].toString(),
					(BigDecimal) res[2], (String) res[3].toString(), (BigDecimal) res[4], (BigDecimal) res[5],
					(BigDecimal) res[6]);
		} catch (Exception e) {
			logger.error("Error occurred on getBfnOptionsTrvl method while getting bfn options trvl data :",
					e.getMessage());
		}
		return bfnOptionsTrvl;
	}
	
	@Transactional (propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public List<Map<String, Long>> saveTravelReimbursementRequest(String employeeNumber, String tripNbr,
			List<BeaEmpTrvl> empTrvls) {
		List<Map<String, Long>> ret = new ArrayList<>();
		try {
			if (tripNbr != null) {
				if (travelRowExistsBea(employeeNumber, tripNbr)) {
					deleteTravelReqBea(employeeNumber, tripNbr);
				}
				if (travelRowExistsBeaAcc(tripNbr)) {
					deleteTrvlReqAcc(tripNbr);
				}
			}
			if (empTrvls.size() > 0) {
				for (BeaEmpTrvl request : empTrvls) {
					Map<String, Long> result = new HashMap<>();
					Session session = this.getSession();
					session.beginTransaction();
					session.saveOrUpdate(request);
					result.put(request.getId().getTripNbr(), request.getId().getTripSeqNbr());
					session.flush();
					ret.add(result);
				}
			}
		} catch (Exception e) {
			logger.error("Error in saving or deleting travel request :", e.getMessage());
		}
		return ret;
	}
	
	@Transactional (propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public List<Map<String, Long>> deleteTravelReimbursementRequest(String employeeNumber, String tripNbr ) {
		List<Map<String, Long>> ret = new ArrayList<>();
		try {
			if (tripNbr != null) {
				if (travelRowExistsBea(employeeNumber, tripNbr)) {
					deleteTravelReqBea(employeeNumber, tripNbr);
				}
				if (travelRowExistsBeaAcc(tripNbr)) {
					deleteTrvlReqAcc(tripNbr);
				}
			}
		} catch (Exception e) {
			logger.error("Error in saving or deleting travel request :", e.getMessage());
		}
		return ret;
	}
	
	@Transactional (propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public void deleteTravelReqBea(String employeeNumber, String tripNum) {
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			String tripNbr = String.valueOf(tripNum);
			sql.append("delete from BEA_EMP_TRVL where EMP_NBR = :employeeNumber");
			sql.append(" and TRIP_NBR =  :tripNbr");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("employeeNumber", employeeNumber);
			q.setParameter("tripNbr", tripNbr);
			Integer res = q.executeUpdate();
			Log.debug("Result of update: " + res);
			session.flush();
		} catch (Exception e) {
			logger.error("Error in saving or deleting travel request :", e.getMessage());
		}
	}
	
	@Transactional (propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public void deleteTrvlReqAcc(String tripNum) {
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			String tripNbr = String.valueOf(tripNum);
			sql.append("delete from BEA_EMP_TRVL_ACCTS where TRIP_NBR = :tripNbr");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("tripNbr", tripNbr);
			Integer res = q.executeUpdate();
			Log.debug("Result of update: " + res);
			session.flush();
		} catch (Exception e) {
			logger.error("Error in saving or deleting travel request :", e.getMessage());
		}
	}
	
	public Boolean travelRowExistsBea(String employeeNumber, String tripNum) {
		int tripCount = 0;
		Boolean tripExists = false;
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			String tripNbr = String.valueOf(tripNum);
			sql.append("select COUNT(TRIP_NBR) from BEA_EMP_TRVL where TRIP_NBR=:tripNbr and EMP_NBR=:employeeNumber");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("tripNbr", tripNbr);
			q.setParameter("employeeNumber", employeeNumber);
			tripCount = (int) q.uniqueResult();
			if (tripCount>0) {
				tripExists = true;
			}
		} catch (Exception e) {
			logger.error("Error occurred while getting travel request row exists or not from travelRowExistsBea method :", e.getMessage());
		}
		return tripExists;
	}
	
	public Boolean travelRowExistsBeaAcc(String tripNum) {
		int tripCount = 0;
		Boolean tripExists = false;
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			String tripNbr = String.valueOf(tripNum);
			sql.append("select COUNT(TRIP_NBR) from BEA_EMP_TRVL_ACCTS where TRIP_NBR=:tripNbr");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("tripNbr", tripNbr);
			tripCount = (int) q.uniqueResult();
			if (tripCount>0) {
				tripExists = true;
			}
		} catch (Exception e) {
			logger.error("Error occurred while getting travel request row exists or not from travelRowExistsBeaAcc method :", e.getMessage());
		}
		return tripExists;
	}
	
	@Transactional (propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public void updateNxtTripNbr(String nxtTripNbr) {
		try {
		Session session = sessionFactory.openSession();
		String sql = "update BFN_OPTIONS_TRVL set NXT_TRIP_NBR=:nxtTripNbr";
		Query q = session.createSQLQuery(sql);
		q.setParameter("nxtTripNbr", nxtTripNbr);
		q.executeUpdate();
		session.flush();
		session.close();
		} catch (Exception e) {
			logger.error("Error occurred on updateNxtTripNbr method while updating next trip number :", e.getMessage());
		}
	}

	public String GetSISPreference(String pPreferenceName) {
		String val = "";
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("Select pref_value From TXEIS_PREFERENCES ");
		sql.append("  Where pref_name = ?; ");
		// sb.append(" Where pref_name = 'ds_client_token'; ");
		Query query = session.createSQLQuery(sql.toString());
		query.setParameter(0, pPreferenceName);

		try {
			@SuppressWarnings("unchecked")
			List<String> qList = query.list();

			for (String rs : qList) {
				val = rs;
			}
		} catch (Exception e) {
			logger.info("========DistrictInformationDao==========GetSISPreference==============ERROR==========>"
					+ e.getMessage());
		}

		if (val == null) {
			val = "";
		}

		return val;
	}

	public String GetSISTaskURL(String pTaskName) {
		String val = "";
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("Select app_url ");
		sql.append("    From SEC_TASKS a ");
		sql.append("    Inner Join SEC_APPLICATION_TASKS b ");
		sql.append("        On a.TASK_ID = b.task_id ");
		sql.append("    Where a.task_name = ?; ");

		Query query = session.createSQLQuery(sql.toString());
		query.setParameter(0, pTaskName);

		try {
			@SuppressWarnings("unchecked")
			List<Object> qList = query.list();

			for (Object rs : qList) {
				val = ResultUtil.getString(rs);
			}
		} catch (Exception e) {
			logger.info("========DistrictInformationDao==========GetSISTaskURL==============ERROR==========>"
					+ e.getMessage());
		}

		if (val == null) {
			val = "";
		}

		return val;
	}
	
	public List<TravelRequestCalendar> getTravelRequestCalendarParameters(String empNbr, String tripNbr) {
		List<TravelRequestCalendar> map = new ArrayList<TravelRequestCalendar>();
		try {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TRIP_NBR, TRIP_SEQ_NBR, EMP_NBR, VENDOR_NBR, TRVL_DT, ENTRY_DT, FROM_TM_HR, FROM_TM_MIN, FROM_TM_AP, TO_TM_HR, TO_TM_MIN, TO_TM_AP, ");
		sql.append("OVRNIGHT, ROUND_TRIP, USE_EMP_TRVL_COMMUTE_DIST, CONTACT, PURPOSE, BEG_ODOMETER, END_ODOMETER, MAP_MILES, MILEAGE_AMT, BRKFST_AMT, LUNCH_AMT, ");
		sql.append("DIN_AMT, FULL_DAY_AMT, ALT_MEAL_AMT, ALT_MEAL_RSN, PARK_AMT, TAXI_AMT, MISC_AMT, MISC_AMT_RSN, ACCOM_AMT, ACCOM_DESC, DIRECT_BILL_NBR, ");
		sql.append("ORIG_LOC_ID, DEST_LOC_ID, ORIG_LOC_NAME, ORIG_LOC_ADDR, ORIG_LOC_CITY, ORIG_LOC_ST, ORIG_LOC_ZIP, ORIG_LOC_ZIP4, DEST_LOC_NAME, ");
		sql.append("DEST_LOC_ADDR, DEST_LOC_CITY, DEST_LOC_ST, DEST_LOC_ZIP, DEST_LOC_ZIP4, TRVL_REQ_STAT, CAMPUS_ID ");
		sql.append("FROM BEA_EMP_TRVL WHERE EMP_NBR=:employeeNumber");	
		if (tripNbr !=null) {
		sql.append(" AND TRIP_NBR=:tripNbr");
		}
		Query q = session.createSQLQuery(sql.toString());
		q.setParameter("employeeNumber", empNbr);
		if (tripNbr!=null) {
			q.setParameter("tripNbr", tripNbr);
		}
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = q.list();
		for (Object[] res : resultList) {
			TravelRequestCalendar result = new TravelRequestCalendar((String) res[0].toString(), (BigDecimal) res[1], (String) res[2],
					(String) res[3], (String) res[4], (String) res[5], (BigDecimal) res[6],
					(BigDecimal) res[7], (String) res[8], (BigDecimal) res[9], (BigDecimal) res[10], (String) res[11],
					(String) res[12], (String) res[13], (String) res[14], (String) res[15], (String) res[16],
					(BigDecimal) res[17], (BigDecimal) res[18], (BigDecimal) res[19], (BigDecimal) res[20], (BigDecimal) res[21],
					(BigDecimal) res[22], (BigDecimal) res[23], (BigDecimal) res[24], (String) res[25].toString(), (String) res[26],
					(BigDecimal) res[27], (BigDecimal) res[28], (BigDecimal) res[29], (String) res[30], (BigDecimal) res[31],
					(String) res[32], (String) res[33], (String) res[34], (String) res[35], (String) res[36],
					(String) res[37], (String) res[38], (String) res[39], (String) res[40], (String) res[41],
					(String) res[42], (String) res[43], (String) res[44], (String) res[45], (String) res[46],
					(String) res[47], (String) res[48], (String) res[49]);
			map.add(result);
		}
		} catch (Exception e) {
			logger.error("Error occurred on getting getTravelRequestCalendarParameters :", e.getMessage());
		}
		return map;
	}
	
	public int getTripCount(String empNbr, String tripNbr) {
		int tripCount = 0;
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(DISTINCT TRVL_DT)");
			sql.append("FROM BEA_EMP_TRVL WHERE EMP_NBR=:employeeNumber AND TRIP_NBR=:tripNbr");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("employeeNumber", empNbr);
			q.setParameter("tripNbr", tripNbr);
			tripCount = (int) q.uniqueResult();
		} catch (Exception e) {
			logger.error("Error occurred on getting trip count on getTripCount method :", e.getMessage());
		}
		return tripCount;
	}
	
	public int getFirstApproverExists(String departId, int workFlowType) {
		int firstApproverCount = 0;
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(*) ");
			sql.append("FROM WFL_FIRST_APPROVERS WHERE DEPARTMENT_ID=:departId AND WORKFLOW_TYPE_ID=:workFlowType");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("departId", departId);
			q.setParameter("workFlowType", workFlowType);
			firstApproverCount = (int) q.uniqueResult();
		} catch (Exception e) {
			logger.error("Error occurred on getting workflow first approver count on getFirstApproverCount method :", e.getMessage());
		}
		return firstApproverCount;
	}
	
	public List<String> getDistinctTripNumber(String empNbr) {
		List<String> list = new ArrayList<>();
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT TRIP_NBR ");
			sql.append("FROM BEA_EMP_TRVL WHERE EMP_NBR=:employeeNumber");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("employeeNumber", empNbr);
			@SuppressWarnings("unchecked")
			List<Object[]> resultList = q.list();
			for (Object res : resultList) {
				list.add(res.toString());
			}
		} catch (Exception e) {
			logger.error("Error occurred on getting distince trip number on getDistinctTripNumber method :",
					e.getMessage());
		}
		return list;
	}
	
	public String getTripTotalAmount(String employeeNumber, String tripNbr) {
		String requestTotal = "";
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("Select BEG_ODOMETER, END_ODOMETER, MAP_MILES, ROUND_TRIP, USE_EMP_TRVL_COMMUTE_DIST, MISC_AMT, MILEAGE_AMT, BRKFST_AMT, LUNCH_AMT, DIN_AMT, PARK_AMT, TAXI_AMT, ACCOM_AMT ");
			sql.append("FROM BEA_EMP_TRVL WHERE TRIP_NBR=:tripNbr ");
			sql.append("AND EMP_NBR=:employeeNumber");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("tripNbr", tripNbr);
			q.setParameter("employeeNumber", employeeNumber);

			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();

			List<CalculateMileageDao> result = new ArrayList<CalculateMileageDao>();
			List<CalculateMileageDao> totalAmountList = new ArrayList<CalculateMileageDao>();
			CalculateMileageDao calculateMileage;
			BigDecimal totalReqAmount = BigDecimal.ZERO;
			for (Object[] i : res) {
				calculateMileage = new CalculateMileageDao((BigDecimal) i[0], (BigDecimal) i[1], (BigDecimal) i[2],
						(String) i[3].toString(), (String) i[4].toString(), (BigDecimal) i[5], (BigDecimal) i[6],
						(BigDecimal) i[7], (BigDecimal) i[8], (BigDecimal) i[9], (BigDecimal) i[10], (BigDecimal) i[11],
						(BigDecimal) i[12]);
				result.add(calculateMileage);
			}
			for (CalculateMileageDao items : result) {
				BfnOptionsTrvl bfnOptionsTrvl = getBfnOptionsTrvl();
				BigDecimal mileageRate = bfnOptionsTrvl.getMileageRate();
				if ((items.getBegOdometer().compareTo(BigDecimal.ZERO) > 0)
						&& (items.getEndOdometer().compareTo(BigDecimal.ZERO) > 0)) {
					BigDecimal diff = items.getEndOdometer().subtract(items.getBegOdometer());
					if (StringUtils.isNotEmpty(items.getRoundTrip()) && StringUtils.isNotBlank(items.getRoundTrip())) {
						if (items.getRoundTrip().equals("Y")) {
							BigDecimal two = new BigDecimal("2.0");
							diff = diff.multiply(two);
						}
					}
					if (StringUtils.isNotEmpty(items.getUseEmpTrvlCommuteDist())
							&& StringUtils.isNotBlank(items.getUseEmpTrvlCommuteDist())) {
						if (items.getUseEmpTrvlCommuteDist().equals("Y")) {
							BigDecimal commuteDist = getCommuteDistance(employeeNumber);
							BigDecimal difference = diff.subtract(commuteDist);
							if (difference.compareTo(BigDecimal.ZERO) > 0) {
								diff = difference;
							} else {
								diff = BigDecimal.ZERO;
							}
						}
					}
					BigDecimal amount = diff.multiply(mileageRate);
					BigDecimal extendedAmt = items.getBrkfstAmt().add(items.getDinAmt()).add(items.getLunchAmt())
							.add(items.getAccomAmt()).add(items.getMileageAmt()).add(items.getParkAmt())
							.add(items.getTaxiAmt());
					BigDecimal totalAmount = amount.add(items.getMiscAmt()).add(extendedAmt);
					items.setAmount(totalAmount);
					totalAmountList.add(items);
				}
				if ((items.getMapMiles().compareTo(BigDecimal.ZERO) > 0)) {
					BigDecimal diff = items.getMapMiles();
					if (items.getRoundTrip().equals("Y")) {
						BigDecimal two = new BigDecimal("2.0");
						diff = diff.multiply(two);
					}
					BigDecimal amount = diff.multiply(mileageRate);
					BigDecimal extendedAmt = items.getBrkfstAmt().add(items.getDinAmt()).add(items.getLunchAmt())
							.add(items.getAccomAmt()).add(items.getMileageAmt()).add(items.getParkAmt())
							.add(items.getTaxiAmt());
					BigDecimal totalAmount = amount.add(items.getMiscAmt()).add(extendedAmt);
					items.setAmount(totalAmount);
					totalAmountList.add(items);
				}
			}
			for (CalculateMileageDao items : totalAmountList) {
				BigDecimal amount = items.getAmount();
				totalReqAmount = totalReqAmount.add(amount);
			}
			requestTotal = String.format("%.2f", totalReqAmount);
		} catch (Exception e) {
			logger.error("Error occurred on getting trip total amount on getTripTotalAmount method :", e.getMessage());
		}
		return requestTotal;
	}
	
	public TravelCheckDetails getTripCheckNumDt(String tripNbr) {
		TravelCheckDetails travelCheckDetails = new TravelCheckDetails();
		try {
			Session session = this.getSession();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT CHECK_NBR, CHECK_DT ");
			sql.append("FROM BFN_CHK_TRANS WHERE GL_FILE_ID='C' AND NOT CHECK_VOID_FLG='V' ");
			sql.append("AND TRIP_NBR=:tripNbr");
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("tripNbr", tripNbr);
			@SuppressWarnings("unchecked")
			List<Object[]> res = q.list();
			for (Object[] i : res) {
				travelCheckDetails = new TravelCheckDetails((String) i[0].toString(), (String) i[1].toString());
			}
		} catch (Exception e) {
			logger.error("Error occurred on getting cheque number details on getTripCheckNumDt method :", e.getMessage());
		}
		return travelCheckDetails;
	}

	public Integer getSecUserId(String userName) {
		String sql = "SELECT A.USR_ID FROM SEC_USERS A WHERE (A.USR_PROFILE_NAME = :userProfileName)";
		Query q = getSession().createSQLQuery(sql);
		q.setParameter("userProfileName", userName);
		Integer ret = (Integer) q.uniqueResult();

		return  ret.equals(null) ? (Integer) 0 : ret;
	}

	// *************************************************************************************************************************************
	// Return District ID and Name
	public String getDistrictId() {
		String sql = "SELECT A.DIST_ID FROM DR_DEMO A WHERE A.SCH_YR = (SELECT MAX(B.SCH_YR) FROM DR_DEMO B)";
		String result = "";

		try {
			Query q = getSession().createSQLQuery(sql);
			result = StringUtils.defaultString((String) q.uniqueResult());
		} catch (DataAccessException e) {
			return "";
		}

		return result;
	}

	public String getReimburseCampus(String empNbr) {
		String sql = String.join("\n", 
		"SELECT p.pay_campus",
		"FROM BHR_EMP_JOB j, BHR_EMP_PAY p",
		"WHERE  j.EMP_NBR = p.EMP_NBR",
		"    AND j.PRIM_JOB = 'Y'",
		"    AND j.CYR_NYR_FLG = p.CYR_NYR_FLG",
		"    AND j.EMP_NBR = :empNbr",
		"    AND j.PAY_FREQ = (CASE",
		"                        WHEN (SELECT COUNT(DISTINCT j.CYR_NYR_FLG)",
		"                                        FROM BHR_EMP_job j, BHR_EMP_PAY p",
		"                                        WHERE  j.EMP_NBR = p.EMP_NBR",
		"                                          AND j.PRIM_JOB = 'Y'",
		"                                          AND j.CYR_NYR_FLG = p.CYR_NYR_FLG",
		"                                          AND CONVERT(date, getdate(), 112) BETWEEN CONVERT(date, CONTR_DT_BEGIN, 112) AND CONVERT(date, CONTR_DT_END, 112)",
		"                                          AND j.EMP_NBR = :empNbr) = 1",
		"                         THEN (SELECT j.PAY_FREQ",
		"                                         FROM BHR_EMP_job j, BHR_EMP_PAY p",
		"                                         WHERE  j.EMP_NBR = p.EMP_NBR ",
		"                                            AND j.PRIM_JOB = 'Y' ",
		"                                            AND j.CYR_NYR_FLG = p.CYR_NYR_FLG  ",
		"                                            AND CONVERT(date, getdate(), 112) BETWEEN CONVERT(date, CONTR_DT_BEGIN, 112) AND CONVERT(date, CONTR_DT_END, 112)",
		"                                            AND j.EMP_NBR = :empNbr",
		"                               )",
		"                        WHEN (SELECT COUNT(DISTINCT j.CYR_NYR_FLG)",
		"                                        FROM BHR_EMP_job j, BHR_EMP_PAY p",
		"                                        WHERE  j.EMP_NBR = p.EMP_NBR",
		"                                          AND j.PRIM_JOB = 'Y'",
		"                                          AND j.CYR_NYR_FLG = p.CYR_NYR_FLG",
		"                                          AND CONVERT(date, getdate(), 112) BETWEEN CONVERT(date, CONTR_DT_BEGIN, 112) AND CONVERT(date, CONTR_DT_END, 112)",
		"                                          AND j.EMP_NBR = :empNbr) = 0 ",
		"                        AND (SELECT COUNT(DISTINCT j.CYR_NYR_FLG)",
		"                                        FROM BHR_EMP_job j, BHR_EMP_PAY p",
		"                                        WHERE  j.EMP_NBR = p.EMP_NBR",
		"                                          AND j.PRIM_JOB = 'Y'",
		"                                          AND j.CYR_NYR_FLG = p.CYR_NYR_FLG",
		"                                          AND j.CYR_NYR_FLG = 'N'",
		"                                          AND CONVERT(date, getdate(), 112) < CONVERT(date, CONTR_DT_BEGIN, 112)",
		"                                          AND j.EMP_NBR = :empNbr) > 0",
		"                         THEN (SELECT MAX(j.PAY_FREQ)",
		"                                     FROM BHR_EMP_job j, BHR_EMP_PAY p",
		"                                     WHERE  j.EMP_NBR = p.EMP_NBR",
		"                                         AND j.PRIM_JOB = 'Y'",
		"                                         AND j.CYR_NYR_FLG = p.CYR_NYR_FLG",
		"                                         AND CONVERT(date, getdate(), 112) < CONVERT(date, j.CONTR_DT_BEGIN, 112)",
		"                                         AND j.CYR_NYR_FLG = 'N'",
		"                                         AND j.EMP_NBR = :empNbr",
		"                               )",
		"                        WHEN (SELECT COUNT(DISTINCT j.CYR_NYR_FLG)",
		"                                        FROM BHR_EMP_job j, BHR_EMP_PAY p",
		"                                        WHERE  j.EMP_NBR = p.EMP_NBR",
		"                                          AND j.PRIM_JOB = 'Y'",
		"                                          AND j.CYR_NYR_FLG = p.CYR_NYR_FLG",
		"                                          AND CONVERT(date, getdate(), 112) BETWEEN CONVERT(date, CONTR_DT_BEGIN, 112) AND CONVERT(date, CONTR_DT_END, 112)",
		"                                          AND j.EMP_NBR = :empNbr) = 0 AND (SELECT COUNT(DISTINCT j.CYR_NYR_FLG)",
		"                                        FROM BHR_EMP_job j, BHR_EMP_PAY p",
		"                                        WHERE  j.EMP_NBR = p.EMP_NBR",
		"                                          AND j.PRIM_JOB = 'Y'",
		"                                          AND j.CYR_NYR_FLG = p.CYR_NYR_FLG",
		"                                          AND j.CYR_NYR_FLG = 'N'",
		"                                          AND CONVERT(date, getdate(), 112) < CONVERT(date, CONTR_DT_BEGIN, 112)",
		"                                          AND j.EMP_NBR = :empNbr) < 1 ",
		"                         THEN (SELECT MAX(j.PAY_FREQ)",
		"                                     FROM BHR_EMP_job j, BHR_EMP_PAY p",
		"                                     WHERE  j.EMP_NBR = p.EMP_NBR",
		"                                         AND j.PRIM_JOB = 'Y'",
		"                                         AND j.CYR_NYR_FLG = p.CYR_NYR_FLG",
		"                                         AND CONVERT(date, j.CONTR_DT_END, 112) = (SELECT MAX(j.CONTR_DT_END)",
		"                                                                                    FROM BHR_EMP_job j, BHR_EMP_PAY p",
		"                                                                                    WHERE  j.EMP_NBR = p.EMP_NBR",
		"                                                                                        AND j.PRIM_JOB = 'Y'",
		"                                                                                        AND j.CYR_NYR_FLG = p.CYR_NYR_FLG",
		"                                                                                        AND j.CYR_NYR_FLG = 'C'",
		"                                                                                        AND j.EMP_NBR = :empNbr",
		"                                                                                    )",
		"                                         AND j.CYR_NYR_FLG = 'C'",
		"                                         AND j.EMP_NBR = :empNbr",
		"                               )",
		"                        WHEN (SELECT COUNT(DISTINCT j.CYR_NYR_FLG)",
		"                                        FROM BHR_EMP_job j, BHR_EMP_PAY p",
		"                                        WHERE  j.EMP_NBR = p.EMP_NBR",
		"                                          AND j.PRIM_JOB = 'Y'",
		"                                          AND j.CYR_NYR_FLG = p.CYR_NYR_FLG",
		"                                          AND CONVERT(date, getdate(), 112) BETWEEN CONVERT(date, CONTR_DT_BEGIN, 112) AND CONVERT(date, CONTR_DT_END, 112)",
		"                                          AND j.EMP_NBR = :empNbr) > 1",
		"                         THEN (SELECT MAX(j.PAY_FREQ)",
		"                                     FROM BHR_EMP_job j, BHR_EMP_PAY p",
		"                                     WHERE  j.EMP_NBR = p.EMP_NBR",
		"                                         AND j.PRIM_JOB = 'Y'",
		"                                         AND j.CYR_NYR_FLG = p.CYR_NYR_FLG",
		"                                         AND CONVERT(date, getdate(), 112) BETWEEN CONVERT(date, CONTR_DT_BEGIN, 112) AND CONVERT(date, CONTR_DT_END, 112)",
		"                                         AND j.CYR_NYR_FLG = 'C'",
		"                                         AND j.EMP_NBR = :empNbr",
		"                               )",
		"                         END);");
		String result = "";

		try {
			Query q = getSession().createSQLQuery(sql);
			q.setParameter("empNbr", empNbr);
			result = StringUtils.defaultString((String) q.uniqueResult());
		} catch (Exception e) {
			logger.error("There was a problem retrieving the Campus to be reimbursed for the user's travel request", e);
		}

		return result;
	}
	
	public boolean isTravelApprover(String empNbr, int workFlowType) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT APPROVER.EMP_NBR ");
		sql.append("FROM (SELECT DISTINCT APPROVER_USR_ID FROM WFL_FIRST_APPROVERS WHERE WORKFLOW_TYPE_ID = :workFlowType) WORKFLOW ");
		sql.append("JOIN ( SELECT SEC_USERS.USR_ID , BEA_USERS.EMP_NBR FROM SEC_USERS JOIN BEA_USERS ON BEA_USERS.EMP_NBR = SEC_USERS.EMP_NBR WHERE BEA_USERS.EMP_NBR = :empNbr ) APPROVER ");
		sql.append("ON WORKFLOW.APPROVER_USR_ID = APPROVER.USR_ID ");
		sql.append("UNION SELECT APPROVER.EMP_NBR ");
		sql.append("FROM (SELECT DISTINCT APPROVER_USR_ID FROM WFL_APPROVAL_CHAINS WHERE WORKFLOW_TYPE_ID = :workFlowType) WORKFLOW ");
		sql.append("JOIN ( SELECT SEC_USERS.USR_ID , BEA_USERS.EMP_NBR FROM SEC_USERS JOIN BEA_USERS ON BEA_USERS.EMP_NBR = SEC_USERS.EMP_NBR WHERE BEA_USERS.EMP_NBR = :empNbr ) APPROVER ");
		sql.append("ON WORKFLOW.APPROVER_USR_ID = APPROVER.USR_ID UNION SELECT APPROVER.EMP_NBR ");
		sql.append("FROM (SELECT DISTINCT ALTERNATE_APPROVER_USR_ID AS APPROVER_USR_ID FROM WFL_ALTERNATE_APPROVER WHERE WORKFLOW_TYPE_ID = :workFlowType) WORKFLOW ");
		sql.append("JOIN ( SELECT SEC_USERS.USR_ID , BEA_USERS.EMP_NBR FROM SEC_USERS JOIN BEA_USERS ON BEA_USERS.EMP_NBR = SEC_USERS.EMP_NBR WHERE BEA_USERS.EMP_NBR = :empNbr ) APPROVER ");
		sql.append("ON WORKFLOW.APPROVER_USR_ID = APPROVER.USR_ID");
		String result = "";
		try {
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("empNbr", empNbr);
			q.setParameter("workFlowType", workFlowType);
			result = StringUtils.defaultString((String) q.uniqueResult());
		}
		catch (DataAccessException e) {
			return false;
		}
		return result.equals("") ? false : true;
	}
	
	public boolean isDocoumentAttached(String tripNbr) {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) DocCount from DS_APPLCTN_FOLDER_FILE_INFO a left join DS_APPLCTN_FOLDER_INFO b on a.APPLCTN_FOLDER_ID = b.APPLCTN_FOLDER_ID ");
		sql.append("left join DS_FILE_INFO c on c.FILE_ID = a.FILE_ID left join DS_NAME_INFO d on d.name_id = a.name_id and d.APPLCTN_FOLDER_ID = a.APPLCTN_FOLDER_ID ");
		sql.append("where b.APPLCTN_NAME = 'EMPLOYEE PORTAL' and b.FOLDER_NAME = 'TRAVEL' ");
		sql.append("AND EXISTS ( SELECT * FROM DS_APPLCTN_FOLDER_INFO G WHERE G.APPLCTN_NAME = B.APPLCTN_NAME AND G.VIEW_FLG = '1' ");
		sql.append("and d.NAME_VAL = 'TRAVEL DOCUMENTS') and (Convert(varchar(20), a.FILE_ID) + '|' + Convert(varchar(20), a.APPLCTN_FOLDER_ID)) in ");
		sql.append("(select Convert(varchar(20), ds.file_id) + '|' + Convert(varchar(20),ds.applctn_folder_id) ");
		sql.append("from  DS_FILE_KEY_INFO ds where ds.file_id = a.file_id and ds.applctn_folder_id = a.applctn_folder_id ");
		sql.append("and ds.KEY_ID = 'TRIPNBR' AND ds.KEY_VAL = :tripNbr) ");
		sql.append("and a.DEL_FLG = 'N'");
		int result = 0;
		try {
			Query q = session.createSQLQuery(sql.toString());
			q.setParameter("tripNbr", tripNbr);
			result = ((int) q.uniqueResult());
		}
		catch (DataAccessException e) {
			logger.error("There was a problem retrieving the document count for the user's travel request", e);
			return false;
		}
		return result > 0 ? true : false;
	}
	
	public int getMaxTripNum() {
		Session session = this.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT isNull(MAX(CAST(isNull(TRIP_NBR, 0) AS INT)), 0) AS MAX_TRIP_NBR FROM BEA_EMP_TRVL");
		int result = 0;
		try {
			Query q = session.createSQLQuery(sql.toString());
			result = ((int) q.uniqueResult());
		}
		catch (DataAccessException e) {
			logger.error("There was a problem retrieving the getMaxTripNum", e);
		}
		return result;
	}
}
