package com.esc20.nonDBModels;

import java.math.BigDecimal;

public class TravelRequestCalendar {

	private String tripNbr;

	private BigDecimal tripSeqNbr;

	private String empNbr;

	private String vendorNbr;

	private String trvlDt;
	
	private String entryDt;
	
	private BigDecimal fromTmHr;
	
	private BigDecimal fromTmMin;
	
	private String fromTmAp;
	
	private BigDecimal toTmHr;
	
	private BigDecimal toTmMin;
	
	private String toTmAp;

	private String ovrnight;
	
	private String roundTrip;
	
	private String useEmpTrvlCommuteDist;
	
	private String contact;
	
	private String purpose;
	
	private BigDecimal begOdometer;
	
	private BigDecimal endOdometer;

	private BigDecimal mapMiles;
	
	private BigDecimal mileageAmt;
	
	private BigDecimal brkfstAmt;
	
	private BigDecimal lunchAmt;
	
	private BigDecimal dinAmt;
	
	private BigDecimal fullDayAmt;
	
	private String altMealAmt;
	
	private String altMealRsn;
	
	private BigDecimal parkAmt;
	
	private BigDecimal taxiAmt;
	
	private BigDecimal miscAmt;
	
	private String miscAmtRsn;
	
	private BigDecimal accomAmt;
	
	private String accomDesc;
	
	private String directBillNbr;
	
	private String origLocId;
	
	private String descLocId;
	
	private String origLocName;
	
	private String origLocAddr;
	
	private String origLocCity;
	
	private String origLocSt;
	
	private String origLocZip;
	
	private String origLocZip4;
	
	private String descLocName;
	
	private String descLocAddr;
	
	private String descLocCity;
	
	private String descLocSt;
	
	private String descLocZip;
	
	private String descLocZip4;
	
	private String trvlReqStat;
	
	private String campusId;
		
	private int tripColspan;
	
	private String title;
	
	private String start;
	
	private String end;
		
	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getTripColspan() {
		return tripColspan;
	}

	public void setTripColspan(int tripColspan) {
		this.tripColspan = tripColspan;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TravelRequestCalendar() {
	}

	public TravelRequestCalendar(String tripNbr, BigDecimal tripSeqNbr, String empNbr, String vendorNbr,
			String trvlDt, String entryDt, BigDecimal fromTmHr, BigDecimal fromTmMin, String fromTmAp,
			BigDecimal toTmHr, BigDecimal toTmMin, String toTmAp, String ovrnight, String roundTrip,
			String useEmpTrvlCommuteDist, String contact, String purpose, BigDecimal begOdometer,
			BigDecimal endOdometer, BigDecimal mapMiles, BigDecimal mileageAmt, BigDecimal brkfstAmt,
			BigDecimal lunchAmt, BigDecimal dinAmt, BigDecimal fullDayAmt, String altMealAmt, String altMealRsn,
			BigDecimal parkAmt, BigDecimal taxiAmt, BigDecimal miscAmt, String miscAmtRsn, BigDecimal accomAmt,
			String accomDesc, String directBillNbr, String origLocId, String descLocId, String origLocName,
			String origLocAddr, String origLocCity, String origLocSt, String origLocZip, String origLocZip4,
			String descLocName, String descLocAddr, String descLocCity, String descLocSt, String descLocZip,
			String descLocZip4, String trvlReqStat, String campusId) {
		super();
		this.tripNbr = tripNbr;
		this.tripSeqNbr = tripSeqNbr;
		this.empNbr = empNbr;
		this.vendorNbr = vendorNbr;
		this.trvlDt = trvlDt;
		this.entryDt = entryDt;
		this.fromTmHr = fromTmHr;
		this.fromTmMin = fromTmMin;
		this.fromTmAp = fromTmAp;
		this.toTmHr = toTmHr;
		this.toTmMin = toTmMin;
		this.toTmAp = toTmAp;
		this.ovrnight = ovrnight;
		this.roundTrip = roundTrip;
		this.useEmpTrvlCommuteDist = useEmpTrvlCommuteDist;
		this.contact = contact;
		this.purpose = purpose;
		this.begOdometer = begOdometer;
		this.endOdometer = endOdometer;
		this.mapMiles = mapMiles;
		this.mileageAmt = mileageAmt;
		this.brkfstAmt = brkfstAmt;
		this.lunchAmt = lunchAmt;
		this.dinAmt = dinAmt;
		this.fullDayAmt = fullDayAmt;
		this.altMealAmt = altMealAmt;
		this.altMealRsn = altMealRsn;
		this.parkAmt = parkAmt;
		this.taxiAmt = taxiAmt;
		this.miscAmt = miscAmt;
		this.miscAmtRsn = miscAmtRsn;
		this.accomAmt = accomAmt;
		this.accomDesc = accomDesc;
		this.directBillNbr = directBillNbr;
		this.origLocId = origLocId;
		this.descLocId = descLocId;
		this.origLocName = origLocName;
		this.origLocAddr = origLocAddr;
		this.origLocCity = origLocCity;
		this.origLocSt = origLocSt;
		this.origLocZip = origLocZip;
		this.origLocZip4 = origLocZip4;
		this.descLocName = descLocName;
		this.descLocAddr = descLocAddr;
		this.descLocCity = descLocCity;
		this.descLocSt = descLocSt;
		this.descLocZip = descLocZip;
		this.descLocZip4 = descLocZip4;
		this.trvlReqStat = trvlReqStat;
		this.campusId = campusId;
	}

	public String getTripNbr() {
		return tripNbr;
	}

	public void setTripNbr(String tripNbr) {
		this.tripNbr = tripNbr;
	}

	public BigDecimal getTripSeqNbr() {
		return tripSeqNbr;
	}

	public void setTripSeqNbr(BigDecimal tripSeqNbr) {
		this.tripSeqNbr = tripSeqNbr;
	}

	public String getEmpNbr() {
		return empNbr;
	}

	public void setEmpNbr(String empNbr) {
		this.empNbr = empNbr;
	}

	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	public String getTrvlDt() {
		return trvlDt;
	}

	public void setTrvlDt(String trvlDt) {
		this.trvlDt = trvlDt;
	}

	public String getEntryDt() {
		return entryDt;
	}

	public void setEntryDt(String entryDt) {
		this.entryDt = entryDt;
	}

	public BigDecimal getFromTmHr() {
		return fromTmHr;
	}

	public void setFromTmHr(BigDecimal fromTmHr) {
		this.fromTmHr = fromTmHr;
	}

	public BigDecimal getFromTmMin() {
		return fromTmMin;
	}

	public void setFromTmMin(BigDecimal fromTmMin) {
		this.fromTmMin = fromTmMin;
	}

	public String getFromTmAp() {
		return fromTmAp;
	}

	public void setFromTmAp(String fromTmAp) {
		this.fromTmAp = fromTmAp;
	}

	public BigDecimal getToTmHr() {
		return toTmHr;
	}

	public void setToTmHr(BigDecimal toTmHr) {
		this.toTmHr = toTmHr;
	}

	public BigDecimal getToTmMin() {
		return toTmMin;
	}

	public void setToTmMin(BigDecimal toTmMin) {
		this.toTmMin = toTmMin;
	}

	public String getToTmAp() {
		return toTmAp;
	}

	public void setToTmAp(String toTmAp) {
		this.toTmAp = toTmAp;
	}

	public String getOvrnight() {
		return ovrnight;
	}

	public void setOvrnight(String ovrnight) {
		this.ovrnight = ovrnight;
	}

	public String getRoundTrip() {
		return roundTrip;
	}

	public void setRoundTrip(String roundTrip) {
		this.roundTrip = roundTrip;
	}

	public String getUseEmpTrvlCommuteDist() {
		return useEmpTrvlCommuteDist;
	}

	public void setUseEmpTrvlCommuteDist(String useEmpTrvlCommuteDist) {
		this.useEmpTrvlCommuteDist = useEmpTrvlCommuteDist;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public BigDecimal getBegOdometer() {
		return begOdometer;
	}

	public void setBegOdometer(BigDecimal begOdometer) {
		this.begOdometer = begOdometer;
	}

	public BigDecimal getEndOdometer() {
		return endOdometer;
	}

	public void setEndOdometer(BigDecimal endOdometer) {
		this.endOdometer = endOdometer;
	}

	public BigDecimal getMapMiles() {
		return mapMiles;
	}

	public void setMapMiles(BigDecimal mapMiles) {
		this.mapMiles = mapMiles;
	}

	public BigDecimal getMileageAmt() {
		return mileageAmt;
	}

	public void setMileageAmt(BigDecimal mileageAmt) {
		this.mileageAmt = mileageAmt;
	}

	public BigDecimal getBrkfstAmt() {
		return brkfstAmt;
	}

	public void setBrkfstAmt(BigDecimal brkfstAmt) {
		this.brkfstAmt = brkfstAmt;
	}

	public BigDecimal getLunchAmt() {
		return lunchAmt;
	}

	public void setLunchAmt(BigDecimal lunchAmt) {
		this.lunchAmt = lunchAmt;
	}

	public BigDecimal getDinAmt() {
		return dinAmt;
	}

	public void setDinAmt(BigDecimal dinAmt) {
		this.dinAmt = dinAmt;
	}

	public BigDecimal getFullDayAmt() {
		return fullDayAmt;
	}

	public void setFullDayAmt(BigDecimal fullDayAmt) {
		this.fullDayAmt = fullDayAmt;
	}

	public String getAltMealAmt() {
		return altMealAmt;
	}

	public void setAltMealAmt(String altMealAmt) {
		this.altMealAmt = altMealAmt;
	}

	public String getAltMealRsn() {
		return altMealRsn;
	}

	public void setAltMealRsn(String altMealRsn) {
		this.altMealRsn = altMealRsn;
	}

	public BigDecimal getParkAmt() {
		return parkAmt;
	}

	public void setParkAmt(BigDecimal parkAmt) {
		this.parkAmt = parkAmt;
	}

	public BigDecimal getTaxiAmt() {
		return taxiAmt;
	}

	public void setTaxiAmt(BigDecimal taxiAmt) {
		this.taxiAmt = taxiAmt;
	}

	public BigDecimal getMiscAmt() {
		return miscAmt;
	}

	public void setMiscAmt(BigDecimal miscAmt) {
		this.miscAmt = miscAmt;
	}

	public String getMiscAmtRsn() {
		return miscAmtRsn;
	}

	public void setMiscAmtRsn(String miscAmtRsn) {
		this.miscAmtRsn = miscAmtRsn;
	}

	public BigDecimal getAccomAmt() {
		return accomAmt;
	}

	public void setAccomAmt(BigDecimal accomAmt) {
		this.accomAmt = accomAmt;
	}

	public String getAccomDesc() {
		return accomDesc;
	}

	public void setAccomDesc(String accomDesc) {
		this.accomDesc = accomDesc;
	}

	public String getDirectBillNbr() {
		return directBillNbr;
	}

	public void setDirectBillNbr(String directBillNbr) {
		this.directBillNbr = directBillNbr;
	}

	public String getOrigLocId() {
		return origLocId;
	}

	public void setOrigLocId(String origLocId) {
		this.origLocId = origLocId;
	}

	public String getDescLocId() {
		return descLocId;
	}

	public void setDescLocId(String descLocId) {
		this.descLocId = descLocId;
	}

	public String getOrigLocName() {
		return origLocName;
	}

	public void setOrigLocName(String origLocName) {
		this.origLocName = origLocName;
	}

	public String getOrigLocAddr() {
		return origLocAddr;
	}

	public void setOrigLocAddr(String origLocAddr) {
		this.origLocAddr = origLocAddr;
	}

	public String getOrigLocCity() {
		return origLocCity;
	}

	public void setOrigLocCity(String origLocCity) {
		this.origLocCity = origLocCity;
	}

	public String getOrigLocSt() {
		return origLocSt;
	}

	public void setOrigLocSt(String origLocSt) {
		this.origLocSt = origLocSt;
	}

	public String getOrigLocZip() {
		return origLocZip;
	}

	public void setOrigLocZip(String origLocZip) {
		this.origLocZip = origLocZip;
	}

	public String getOrigLocZip4() {
		return origLocZip4;
	}

	public void setOrigLocZip4(String origLocZip4) {
		this.origLocZip4 = origLocZip4;
	}

	public String getDescLocName() {
		return descLocName;
	}

	public void setDescLocName(String descLocName) {
		this.descLocName = descLocName;
	}

	public String getDescLocAddr() {
		return descLocAddr;
	}

	public void setDescLocAddr(String descLocAddr) {
		this.descLocAddr = descLocAddr;
	}

	public String getDescLocCity() {
		return descLocCity;
	}

	public void setDescLocCity(String descLocCity) {
		this.descLocCity = descLocCity;
	}

	public String getDescLocSt() {
		return descLocSt;
	}

	public void setDescLocSt(String descLocSt) {
		this.descLocSt = descLocSt;
	}

	public String getDescLocZip() {
		return descLocZip;
	}

	public void setDescLocZip(String descLocZip) {
		this.descLocZip = descLocZip;
	}

	public String getDescLocZip4() {
		return descLocZip4;
	}

	public void setDescLocZip4(String descLocZip4) {
		this.descLocZip4 = descLocZip4;
	}

	public String getTrvlReqStat() {
		return trvlReqStat;
	}

	public void setTrvlReqStat(String trvlReqStat) {
		this.trvlReqStat = trvlReqStat;
	}

	public String getCampusId() {
		return campusId;
	}

	public void setCampusId(String campusId) {
		this.campusId = campusId;
	}

}
