package com.esc20.nonDBModels;

import java.util.List;

public class TravelRequest {
	private String tripNbr;
	private long tripSeqNbr;
	private String entryDt;
	private String travelDt;
	private String endOdometer;
	private String fromTmAp;
	private String purpose;
	private String origLocSt;
	private String toTmHr;
	private double mileageAmt;
	private String accomDesc;
	private String directBillNbr;
	private double breakfastAmt;
	private double lunchAmt;
	private double dinnerAmt;
	private double fullDayAmt;
	private char altMealAmt;
	private String altMealRsn;
	private double accomAmt;
	private double parkAmt;
	private double taxiAmt;
	private double miscAmt;
	private String miscAmtRsn;
	private String origLocName;
	private String origLocAddr;
	private String overnight;
	private String toTmMin;
	private String toTmAp;
	private String origLocCity;
	private String contact;
	private String destLocName;
	private String roundTrip;
	private String destLocAddr;
	private String destLocCity;
	private String begOdometer;
	private String destLocZip;
	private String destLocZip4;
	private String trvlReqStat;
	private String destLocId;
	private String mapMiles;
	private String origLocZip;
	private String destLocSt;
	private String origLocZip4;
	private String origLocId;
	private String fromTmHr;
	private String useEmpTrvlCommuteDist;
	private String fromTmMin;
	private List<ChildCode> accountCodes;
	
	public String getTripNbr() {
		return tripNbr;
	}

	public void setTripNbr(String tripNbr) {
		this.tripNbr = tripNbr;
	}

	public long getTripSeqNbr() {
		return tripSeqNbr;
	}

	public void setTripSeqNbr(long tripSeqNbr) {
		this.tripSeqNbr = tripSeqNbr;
	}

	public String getEntryDt() {
		return entryDt;
	}

	public void setEntryDt(String entryDt) {
		this.entryDt = entryDt;
	}

	public String getTravelDt() {
		return travelDt;
	}

	public void setTravelDt(String travelDt) {
		this.travelDt = travelDt;
	}

	public String getEndOdometer() {
		return endOdometer;
	}

	public void setEndOdometer(String endOdometer) {
		this.endOdometer = endOdometer;
	}

	public String getFromTmAp() {
		return fromTmAp;
	}

	public void setFromTmAp(String fromTmAp) {
		this.fromTmAp = fromTmAp;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getOrigLocSt() {
		return origLocSt;
	}

	public void setOrigLocSt(String origLocSt) {
		this.origLocSt = origLocSt;
	}

	public String getToTmHr() {
		return toTmHr;
	}

	public void setToTmHr(String toTmHr) {
		this.toTmHr = toTmHr;
	}

	public double getMiscAmt() {
		return miscAmt;
	}

	public void setMiscAmt(double miscAmt) {
		this.miscAmt = miscAmt;
	}

	public double getMileageAmt() {
		return mileageAmt;
	}

	public void setMileageAmt(double mileageAmt) {
		this.mileageAmt = mileageAmt;
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

	public double getBreakfastAmt() {
		return breakfastAmt;
	}

	public void setBreakfastAmt(double breakfastAmt) {
		this.breakfastAmt = breakfastAmt;
	}

	public double getLunchAmt() {
		return lunchAmt;
	}

	public void setLunchAmt(double lunchAmt) {
		this.lunchAmt = lunchAmt;
	}

	public double getDinnerAmt() {
		return dinnerAmt;
	}

	public void setDinnerAmt(double dinnerAmt) {
		this.dinnerAmt = dinnerAmt;
	}

	public double getFullDayAmt() {
		return fullDayAmt;
	}

	public void setFullDayAmt(double fullDayAmt) {
		this.fullDayAmt = fullDayAmt;
	}

	public char getAltMealAmt() {
		return altMealAmt;
	}

	public void setAltMealAmt(char altMealAmt) {
		this.altMealAmt = altMealAmt;
	}

	public String getAltMealRsn() {
		return altMealRsn;
	}

	public void setAltMealAmtRsn(String altMealRsn) {
		this.altMealRsn = altMealRsn;
	}

	public double getAccomAmt() {
		return accomAmt;
	}

	public void setAccomAmt(double accomAmt) {
		this.accomAmt = accomAmt;
	}

	public void setAltMealRsn(String altMealRsn) {
		this.altMealRsn = altMealRsn;
	}

	public double getParkAmt() {
		return parkAmt;
	}

	public void setParkAmt(double parkAmt) {
		this.parkAmt = parkAmt;
	}

	public double getTaxiAmt() {
		return taxiAmt;
	}

	public void setTaxiAmt(double taxiAmt) {
		this.taxiAmt = taxiAmt;
	}

	public String getMiscAmtRsn() {
		return miscAmtRsn;
	}

	public void setMiscAmtRsn(String miscAmtRsn) {
		this.miscAmtRsn = miscAmtRsn;
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

	public String getOvernight() {
		return overnight;
	}

	public void setOvernight(String overnight) {
		this.overnight = overnight;
	}

	public String getToTmMin() {
		return toTmMin;
	}

	public void setToTmMin(String toTmMin) {
		this.toTmMin = toTmMin;
	}

	public String getToTmAp() {
		return toTmAp;
	}

	public void setToTmAp(String toTmAp) {
		this.toTmAp = toTmAp;
	}

	public String getOrigLocCity() {
		return origLocCity;
	}

	public void setOrigLocCity(String origLocCity) {
		this.origLocCity = origLocCity;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDestLocName() {
		return destLocName;
	}

	public void setDestLocName(String destLocName) {
		this.destLocName = destLocName;
	}

	public String getRoundTrip() {
		return roundTrip;
	}

	public void setRoundTrip(String roundTrip) {
		this.roundTrip = roundTrip;
	}

	public String getDestLocAddr() {
		return destLocAddr;
	}

	public void setDestLocAddr(String destLocAddr) {
		this.destLocAddr = destLocAddr;
	}

	public String getDestLocCity() {
		return destLocCity;
	}

	public void setDestLocCity(String destLocCity) {
		this.destLocCity = destLocCity;
	}

	public String getBegOdometer() {
		return begOdometer;
	}

	public void setBegOdometer(String begOdometer) {
		this.begOdometer = begOdometer;
	}

	public String getDestLocZip() {
		return destLocZip;
	}

	public void setDestLocZip(String destLocZip) {
		this.destLocZip = destLocZip;
	}

	public String getDestLocZip4() {
		return destLocZip4;
	}

	public void setDestLocZip4(String destLocZip4) {
		this.destLocZip4 = destLocZip4;
	}

	public String getTrvlReqStat() {
		return trvlReqStat;
	}

	public void setTrvlReqStat(String trvlReqStat) {
		this.trvlReqStat = trvlReqStat;
	}

	public String getDestLocId() {
		return destLocId;
	}

	public void setDestLocId(String destLocId) {
		this.destLocId = destLocId;
	}

	public String getMapMiles() {
		return mapMiles;
	}

	public void setMapMiles(String mapMiles) {
		this.mapMiles = mapMiles;
	}

	public String getOrigLocZip() {
		return origLocZip;
	}

	public void setOrigLocZip(String origLocZip) {
		this.origLocZip = origLocZip;
	}

	public String getDestLocSt() {
		return destLocSt;
	}

	public void setDestLocSt(String destLocSt) {
		this.destLocSt = destLocSt;
	}

	public String getOrigLocZip4() {
		return origLocZip4;
	}

	public void setOrigLocZip4(String origLocZip4) {
		this.origLocZip4 = origLocZip4;
	}

	public String getOrigLocId() {
		return origLocId;
	}

	public void setOrigLocId(String origLocId) {
		this.origLocId = origLocId;
	}

	public String getFromTmHr() {
		return fromTmHr;
	}

	public void setFromTmHr(String fromTmHr) {
		this.fromTmHr = fromTmHr;
	}

	public String getUseEmpTrvlCommuteDist() {
		return useEmpTrvlCommuteDist;
	}

	public void setUseEmpTrvlCommuteDist(String useEmpTrvlCommuteDist) {
		this.useEmpTrvlCommuteDist = useEmpTrvlCommuteDist;
	}

	public String getFromTmMin() {
		return fromTmMin;
	}

	public void setFromTmMin(String fromTmMin) {
		this.fromTmMin = fromTmMin;
	}

	public List<ChildCode> getAccountCodes() {
		return accountCodes;
	}

	public void setAccountCodes(List<ChildCode> accountCodes) {
		this.accountCodes = accountCodes;
	}
}
