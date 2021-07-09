package com.esc20.model;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.esc20.nonDBModels.TravelRequest;
import com.esc20.nonDBModels.TravelRequestInfo;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "BEA_EMP_TRVL", schema = "rsccc", catalog = "rsccc")
public class BeaEmpTrvl implements java.io.Serializable {

	private static final long serialVersionUID = -7480196382781381413L;

    private BeaEmpTrvlId id;
	private String empNbr;
	private String vendorNbr;
	private String entryDt;
	private int fromTmHr;
	private int fromTmMin;
	private char fromTmAp;
	private int toTmHr;
	private int toTmMin;
	private char toTmAp;
	private char overnight;
	private char roundTrip;
	private String travelDt;
	private char useEmpTrvlCommuteDist;
	private String contact;
	private String purpose;
	private double begOdometer;
	private double endOdometer;
	private double mapMiles;
	private double mileageAmt;
	private double breakfastAmt;
	private double lunchAmt;
	private double dinnerAmt;
	private double fullDayAmt;
	private char altMealAmt;
	private String altMealRsn;
	private double parkAmt;
	private double taxiAmt;
	private double miscAmt;
	private String miscAmtRsn;
	private double accomAmt;
	private String accomDesc;
	private String directBillNbr;
	private String origLocId;
	private String destLocId;
	private String origLocName;
	private String origLocAddr;
	private String origLocCity;
	private String origLocSt;
	private String origLocZip;
	private String origLocZip4;
	private String destLocName;
	private String destLocAddr;
	private String destLocCity;
	private String destLocSt;
	private String destLocZip;
	private String destLocZip4;
	private char trvlReqStat;
	private String campusId;

	public BeaEmpTrvl(TravelRequest travelRequest, BeaEmpTrvlId id, List<TravelRequestInfo> travelInfo,
			String reimburseCampus, String employeeNumber) {
		this.id = id;
		this.trvlReqStat = travelRequest.getTrvlReqStat().charAt(0);
		this.empNbr = employeeNumber;
		this.campusId = reimburseCampus;
		if (travelInfo.size() > 0)
			this.vendorNbr = travelInfo.get(0).getVendorNbr();
		this.entryDt = travelRequest.getEntryDt();
		this.travelDt = travelRequest.getTravelDt();
		this.fromTmHr = (travelRequest.getFromTmHr() == null || travelRequest.getFromTmHr().trim().equals("")) ? 0
				: Integer.parseInt(travelRequest.getFromTmHr());
		this.fromTmMin = (travelRequest.getFromTmMin() == null || travelRequest.getFromTmMin().trim().equals("")) ? 0
				: Integer.parseInt(travelRequest.getFromTmMin());
		this.fromTmAp = (travelRequest.getFromTmAp() == null || travelRequest.getFromTmAp().trim().equals(""))
				? '\u0000'
				: travelRequest.getFromTmAp().charAt(0);
		this.toTmHr = (travelRequest.getToTmHr() == null || travelRequest.getToTmHr().trim().contentEquals("")) ? 0
				: Integer.parseInt(travelRequest.getToTmHr());
		this.toTmMin = (travelRequest.getToTmMin() == null || travelRequest.getToTmMin().equals("")) ? 0
				: Integer.parseInt(travelRequest.getToTmMin());
		this.toTmAp = (travelRequest.getToTmAp() == null || travelRequest.getToTmAp().equals("")) ? '\u0000'
				: travelRequest.getToTmAp().charAt(0);
		this.overnight = (travelRequest.getOvernight() == null || travelRequest.getOvernight().equals("")) ? '\u0000'
				: travelRequest.getOvernight().charAt(0);
		this.roundTrip = (travelRequest.getRoundTrip() == null || travelRequest.getRoundTrip().equals("")) ? '\u0000'
				: travelRequest.getRoundTrip().charAt(0);
		this.useEmpTrvlCommuteDist = (travelRequest.getUseEmpTrvlCommuteDist() == null
				|| travelRequest.getUseEmpTrvlCommuteDist().equals("")) ? '\u0000'
						: travelRequest.getUseEmpTrvlCommuteDist().charAt(0);
		this.contact = travelRequest.getContact();
		this.purpose = travelRequest.getPurpose();
		if (StringUtils.isNotEmpty(travelRequest.getBegOdometer())
				&& StringUtils.isNotBlank(travelRequest.getBegOdometer())) {
			this.begOdometer = Double.parseDouble(travelRequest.getBegOdometer());
		}
		if (StringUtils.isNotEmpty(travelRequest.getEndOdometer())
				&& StringUtils.isNotBlank(travelRequest.getEndOdometer())) {
			this.endOdometer = Double.parseDouble(travelRequest.getEndOdometer());
		}
		if (StringUtils.isNotEmpty(travelRequest.getMapMiles())
				&& StringUtils.isNotBlank(travelRequest.getMapMiles())) {
			this.mapMiles = Double.parseDouble(travelRequest.getMapMiles());
		}
		if (StringUtils.isEmpty(travelRequest.getAccomDesc()) || StringUtils.isBlank(travelRequest.getAccomDesc())) {
			this.accomDesc = "";
		} else {
			this.accomDesc = travelRequest.getAccomDesc();
		}
		if (StringUtils.isEmpty(travelRequest.getAltMealRsn())
				|| StringUtils.isBlank(travelRequest.getAltMealRsn())) {
			this.altMealRsn = "";
		} else {
			this.altMealRsn = travelRequest.getAltMealRsn();
		}
		if (StringUtils.isEmpty(travelRequest.getMiscAmtRsn()) || StringUtils.isBlank(travelRequest.getMiscAmtRsn())) {
			this.miscAmtRsn = "";
		} else {
			this.miscAmtRsn = travelRequest.getMiscAmtRsn();
		}
		if (StringUtils.isEmpty(travelRequest.getDirectBillNbr())
				|| StringUtils.isBlank(travelRequest.getDirectBillNbr())) {
			this.directBillNbr = "";
		} else {
			this.directBillNbr = travelRequest.getDirectBillNbr();
		}
		this.mileageAmt = travelRequest.getMileageAmt();
		this.accomAmt = travelRequest.getAccomAmt();
		this.breakfastAmt = travelRequest.getBreakfastAmt();
		this.lunchAmt = travelRequest.getLunchAmt();
		this.dinnerAmt = travelRequest.getDinnerAmt();
		this.fullDayAmt = travelRequest.getFullDayAmt();
		this.altMealAmt = travelRequest.getAltMealAmt();
		this.parkAmt = travelRequest.getParkAmt();
		this.taxiAmt = travelRequest.getTaxiAmt();
		this.miscAmt = travelRequest.getMiscAmt();
		this.origLocId = travelRequest.getOrigLocId();
		this.destLocId = travelRequest.getDestLocId();
		this.origLocName = travelRequest.getOrigLocName();
		this.origLocAddr = travelRequest.getOrigLocAddr();
		this.origLocCity = travelRequest.getOrigLocCity();
		this.origLocSt = travelRequest.getOrigLocSt();
		this.origLocZip = travelRequest.getOrigLocZip();
		this.origLocZip4 = travelRequest.getOrigLocZip4();
		this.destLocName = travelRequest.getDestLocName();
		this.destLocAddr = travelRequest.getDestLocAddr();
		this.destLocCity = travelRequest.getDestLocCity();
		this.destLocSt = travelRequest.getDestLocSt();
		this.destLocZip = travelRequest.getDestLocZip();
		this.destLocZip4 = travelRequest.getDestLocZip4();
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "tripNbr", column = @Column(name = "TRIP_NBR", nullable = false, length = 9, columnDefinition = "long default 0")),
			@AttributeOverride(name = "tripSeqNbr", column = @Column(name = "TRIP_SEQ_NBR", nullable = false, length = 2, columnDefinition = "long default 0 ")) })
	public BeaEmpTrvlId getId() {
		return id;
	}

	public void setId(BeaEmpTrvlId id) {
		this.id = id;
	}

	@Column(name = "TRVL_DT", nullable = false, length = 8)
	public String getTravelDt() {
		return travelDt;
	}

	public void setTravelDt(String travelDt) {
		this.travelDt = travelDt;
	}

	@Column(name = "EMP_NBR", nullable = false, length = 6)
	public String getEmpNbr() {
		return empNbr;
	}

	public void setEmpNbr(String empNbr) {
		this.empNbr = empNbr;
	}

	@Column(name = "VENDOR_NBR", nullable = false, length = 5)
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name = "ENTRY_DT", nullable = false, length = 8)
	public String getEntryDt() {
		return entryDt;
	}

	public void setEntryDt(String entryDt) {
		this.entryDt = entryDt;
	}

	@Column(name = "FROM_TM_HR", nullable = false, length = 2)
	public int getFromTmHr() {
		return fromTmHr;
	}

	public void setFromTmHr(int fromTmHr) {
		this.fromTmHr = fromTmHr;
	}

	@Column(name = "FROM_TM_MIN", nullable = false, length = 2)
	public int getFromTmMin() {
		return fromTmMin;
	}

	public void setFromTmMin(int fromTmMin) {
		this.fromTmMin = fromTmMin;
	}

	@Column(name = "FROM_TM_AP", nullable = false, length = 1)
	public char getFromTmAp() {
		return fromTmAp;
	}

	public void setFromTmAp(char fromTmAp) {
		this.fromTmAp = fromTmAp;
	}

	@Column(name = "TO_TM_HR", nullable = false, length = 2)
	public int getToTmHr() {
		return toTmHr;
	}

	public void setToTmHr(int toTmHr) {
		this.toTmHr = toTmHr;
	}

	@Column(name = "TO_TM_MIN", nullable = false, length = 2)
	public int getToTmMin() {
		return toTmMin;
	}

	public void setToTmMin(int toTmMin) {
		this.toTmMin = toTmMin;
	}

	@Column(name = "TO_TM_AP", nullable = false, length = 1)
	public char getToTmAp() {
		return toTmAp;
	}

	public void setToTmAp(char toTmAp) {
		this.toTmAp = toTmAp;
	}

	@Column(name = "OVRNIGHT", nullable = false, length = 1)
	public char getOvernight() {
		return overnight;
	}

	public void setOvernight(char overnight) {
		this.overnight = overnight;
	}

	@Column(name = "ROUND_TRIP", nullable = false, length = 1)
	public char getRoundTrip() {
		return roundTrip;
	}

	public void setRoundTrip(char roundTrip) {
		this.roundTrip = roundTrip;
	}

	@Column(name = "USE_EMP_TRVL_COMMUTE_DIST", nullable = false, length = 1)
	public char getUseEmpTrvlCommuteDist() {
		return useEmpTrvlCommuteDist;
	}

	public void setUseEmpTrvlCommuteDist(char useEmpTrvlCommuteDist) {
		this.useEmpTrvlCommuteDist = useEmpTrvlCommuteDist;
	}

	@Column(name = "CONTACT", nullable = false, length = 20)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "PURPOSE", nullable = false, length = 40)
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@Column(name = "BEG_ODOMETER", nullable = false, length = 8)
	public double getBegOdometer() {
		return begOdometer;
	}

	public void setBegOdometer(double begOdometer) {
		this.begOdometer = begOdometer;
	}

	@Column(name = "END_ODOMETER", nullable = false, length = 8)
	public double getEndOdometer() {
		return endOdometer;
	}

	public void setEndOdometer(double endOdometer) {
		this.endOdometer = endOdometer;
	}

	@Column(name = "MAP_MILES", nullable = false, length = 5)
	public double getMapMiles() {
		return mapMiles;
	}

	public void setMapMiles(double mapMiles) {
		this.mapMiles = mapMiles;
	}

	@Column(name = "MILEAGE_AMT", nullable = false, length = 8)
	public double getMileageAmt() {
		return mileageAmt;
	}

	public void setMileageAmt(double meileageAmt) {
		this.mileageAmt = meileageAmt;
	}

	@Column(name = "BRKFST_AMT", nullable = false, length = 8)
	public double getBreakfastAmt() {
		return breakfastAmt;
	}

	public void setBreakfastAmt(double breakfastAmt) {
		this.breakfastAmt = breakfastAmt;
	}

	@Column(name = "LUNCH_AMT", nullable = false, length = 8)
	public double getLunchAmt() {
		return lunchAmt;
	}

	public void setLunchAmt(double lunchAmt) {
		this.lunchAmt = lunchAmt;
	}

	@Column(name = "DIN_AMT", nullable = false, length = 8)
	public double getDinnerAmt() {
		return dinnerAmt;
	}

	public void setDinnerAmt(double dinnerAmt) {
		this.dinnerAmt = dinnerAmt;
	}

	@Column(name = "FULL_DAY_AMT", nullable = false, length = 8)
	public double getFullDayAmt() {
		return fullDayAmt;
	}

	public void setFullDayAmt(double fullDayAmt) {
		this.fullDayAmt = fullDayAmt;
	}

	@Column(name = "ALT_MEAL_AMT", nullable = false, length = 8)
	public char getAltMealAmt() {
		return altMealAmt;
	}

	public void setAltMealAmt(char altMealAmt) {
		this.altMealAmt = altMealAmt;
	}

	@Column(name = "ALT_MEAL_RSN", nullable = false, length = 40)
	public String getAltMealRsn() {
		return altMealRsn;
	}

	public void setAltMealRsn(String altMealRsn) {
		this.altMealRsn = altMealRsn;
	}

	@Column(name = "PARK_AMT", nullable = false, length = 8)
	public double getParkAmt() {
		return parkAmt;
	}

	public void setParkAmt(double parkAmt) {
		this.parkAmt = parkAmt;
	}

	@Column(name = "TAXI_AMT", nullable = false, length = 8)
	public double getTaxiAmt() {
		return taxiAmt;
	}

	public void setTaxiAmt(double taxiAmt) {
		this.taxiAmt = taxiAmt;
	}

	@Column(name = "MISC_AMT", nullable = false, length = 8)
	public double getMiscAmt() {
		return miscAmt;
	}

	public void setMiscAmt(double miscAmt) {
		this.miscAmt = miscAmt;
	}

	@Column(name = "MISC_AMT_RSN", nullable = false, length = 40)
	public String getMiscAmtRsn() {
		return miscAmtRsn;
	}

	public void setMiscAmtRsn(String miscAmtRsn) {
		this.miscAmtRsn = miscAmtRsn;
	}

	@Column(name = "ACCOM_AMT", nullable = false, length = 8)
	public double getAccomAmt() {
		return accomAmt;
	}

	public void setAccomAmt(double accomAmt) {
		this.accomAmt = accomAmt;
	}

	@Column(name = "ACCOM_DESC", nullable = false, length = 40)
	public String getAccomDesc() {
		return accomDesc;
	}

	public void setAccomDesc(String accomDesc) {
		this.accomDesc = accomDesc;
	}

	@Column(name = "DIRECT_BILL_NBR", nullable = false, length = 20)
	public String getDirectBillNbr() {
		return directBillNbr;
	}

	public void setDirectBillNbr(String directBillNbr) {
		this.directBillNbr = directBillNbr;
	}

	@Column(name = "ORIG_LOC_ID", nullable = false, length = 20)
	public String getOrigLocId() {
		return origLocId;
	}

	public void setOrigLocId(String origLocId) {
		this.origLocId = origLocId;
	}

	@Column(name = "DEST_LOC_ID", nullable = false, length = 20)
	public String getDestLocId() {
		return destLocId;
	}

	public void setDestLocId(String destLocId) {
		this.destLocId = destLocId;
	}

	@Column(name = "ORIG_LOC_NAME", nullable = false, length = 35)
	public String getOrigLocName() {
		return origLocName;
	}

	public void setOrigLocName(String origLocName) {
		this.origLocName = origLocName;
	}

	@Column(name = "ORIG_LOC_ADDR", nullable = false, length = 30)
	public String getOrigLocAddr() {
		return origLocAddr;
	}

	public void setOrigLocAddr(String origLocAddr) {
		this.origLocAddr = origLocAddr;
	}

	@Column(name = "ORIG_LOC_CITY", nullable = false, length = 30)
	public String getOrigLocCity() {
		return origLocCity;
	}

	public void setOrigLocCity(String origLocCity) {
		this.origLocCity = origLocCity;
	}

	@Column(name = "ORIG_LOC_ST", nullable = false, length = 2)
	public String getOrigLocSt() {
		return origLocSt;
	}

	public void setOrigLocSt(String origLocSt) {
		this.origLocSt = origLocSt;
	}

	@Column(name = "ORIG_LOC_ZIP", nullable = false, length = 5)
	public String getOrigLocZip() {
		return origLocZip;
	}

	public void setOrigLocZip(String origLocZip) {
		this.origLocZip = origLocZip;
	}

	@Column(name = "ORIG_LOC_ZIP4", nullable = false, length = 4)
	public String getOrigLocZip4() {
		return origLocZip4;
	}

	public void setOrigLocZip4(String origLocZip4) {
		this.origLocZip4 = origLocZip4;
	}

	@Column(name = "DEST_LOC_NAME", nullable = false, length = 35)
	public String getDestLocName() {
		return destLocName;
	}

	public void setDestLocName(String destLocName) {
		this.destLocName = destLocName;
	}

	@Column(name = "DEST_LOC_ADDR", nullable = false, length = 30)
	public String getDestLocAddr() {
		return destLocAddr;
	}

	public void setDestLocAddr(String destLocAddr) {
		this.destLocAddr = destLocAddr;
	}

	@Column(name = "DEST_LOC_CITY", nullable = false, length = 30)
	public String getDestLocCity() {
		return destLocCity;
	}

	public void setDestLocCity(String destLocCity) {
		this.destLocCity = destLocCity;
	}

	@Column(name = "DEST_LOC_ST", nullable = false, length = 2)
	public String getDestLocSt() {
		return destLocSt;
	}

	public void setDestLocSt(String destLocSt) {
		this.destLocSt = destLocSt;
	}

	@Column(name = "DEST_LOC_ZIP", nullable = false, length = 5)
	public String getDestLocZip() {
		return destLocZip;
	}

	public void setDestLocZip(String destLocZip) {
		this.destLocZip = destLocZip;
	}

	@Column(name = "DEST_LOC_ZIP4", nullable = false, length = 4)
	public String getDestLocZip4() {
		return destLocZip4;
	}

	public void setDestLocZip4(String destLocZip4) {
		this.destLocZip4 = destLocZip4;
	}

	@Column(name = "TRVL_REQ_STAT", nullable = false, length = 1)
	public char getTrvlReqStat() {
		return trvlReqStat;
	}

	public void setTrvlReqStat(char trvlReqStat) {
		this.trvlReqStat = trvlReqStat;
	}

	@Column(name = "CAMPUS_ID", nullable = false, length = 3)
	public String getCampusId() {
		return campusId;
	}

	public void setCampusId(String campusId) {
		this.campusId = campusId;
	}
	
}
