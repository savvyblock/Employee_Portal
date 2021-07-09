package com.esc20.model;

import java.io.Serializable;
import java.math.BigDecimal;

import net.esc20.txeis.common.domainobjects.ISort;

/***
 * Object shared between Print Checks and Void Checks
 * @author igarcia
 *
 */
public class CheckTrans implements Serializable{
	private static final long serialVersionUID = 114269437999082210L;

	private String glFileId = "";
	private String dts = "";
	private String seqNbr = "";
	private String acctPer = "";
	private String typeCd = "";
	private String fund = "";
	private String fsclYr = "";
	private String func = "";
	private String obj = "";
	private String sobj = "";
	private String org = "";
	private String pgm = "";
	private String edSpan = "";
	private String projDtl = "";
	private String transDt = "";
	private String invoiceNbr = "";
	private String poPaNbr = "";
	private String poPaType = "";
	private String vendorNbr = "";
	private String vendorName = "";
	private String vendorSortKey = "";
	private String dba = "";
	private String vendorPhoneNbr = "";
	private String checkNbr = "";
	private String offsetFund = "";
	private String offsetFsclYr = "";
	private String offsetFunc = "";
	private String offsetObj = "";
	private String offsetSobj = "";
	private String offsetOrg = "";
	private String offsetPgm = "";
	private String offsetEdSpan = "";
	private String offsetProjDtl = "";
	private String checkDt = "";
	private BigDecimal netExpendAmt = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
	private BigDecimal liquidatedAmt = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
	private BigDecimal disburAmt = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
	private String dueFromAcct = "";
	private String userId = "";
	private String applictnId = "";
	private String processDt = "";
	private String transCorrectionFlg = "";
	private String checkVoidFlg = "";
	private String separatePymtFlg = "";
	private String invoiceDt = "";
	private String dueDt = "";
	private String rsn = "";
	private String prtChkFlg = "";
	private String reconInd = "";
	private String crMemoNbr = "";
	private String bankAcctCashObj = "";
	private String bankAcctCashSobj = "";
	private String origDts = "";
	private String micrCheckNbr = "";
	private String reconDts = "";
	private String eftPymtFlg = "";
	private String crdtCrdCd = "";
	private String crdtCrdReconInd = "";
	private String crdtCrdReconDts = "";
	private String entryDt = "";
	private String typCdNew = "";
	private String bankCd = "";
	private String bankAcctNbr = "";
	private String bankAcctType = "";
	private String tripNbr = "";
	private String crMemoDts = "";
	private String crMemoSeqNbr = "";
	private String deleteRec = "";
	private String checkNotes = "";
	private BigDecimal checkAmt = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
	private String currentNextFlag = "";

	public String getGlFileId() {
		return glFileId;
	}

	public void setGlFileId(String glFileId) {
		this.glFileId = glFileId;
	}

	public String getDts() {
		return dts;
	}

	public void setDts(String dts) {
		this.dts = dts;
	}

	public String getSeqNbr() {
		return seqNbr;
	}

	public void setSeqNbr(String seqNbr) {
		this.seqNbr = seqNbr;
	}

	public String getAcctPer() {
		return acctPer;
	}

	public void setAcctPer(String acctPer) {
		this.acctPer = acctPer;
	}

	public String getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public String getTypeCdFormatted() {
		String retval = "";
		switch (this.typeCd) {
			case "C":
				retval = "Computer";
				break;
			case "D":
				retval = "District";
				break;
			case "M":
				retval = "Credit Memo";
				break;
			case "Y":
				retval = "Computer";
				break;
			default: 
				retval = this.typeCd;
				break;
		}

		return retval;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getFsclYr() {
		return fsclYr;
	}

	public void setFsclYr(String fsclYr) {
		this.fsclYr = fsclYr;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public String getSobj() {
		return sobj;
	}

	public void setSobj(String sobj) {
		this.sobj = sobj;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getPgm() {
		return pgm;
	}

	public void setPgm(String pgm) {
		this.pgm = pgm;
	}

	public String getEdSpan() {
		return edSpan;
	}

	public void setEdSpan(String edSpan) {
		this.edSpan = edSpan;
	}

	public String getProjDtl() {
		return projDtl;
	}

	public void setProjDtl(String projDtl) {
		this.projDtl = projDtl;
	}

	public String getObjClass() {
		return this.obj.substring(0, 1);
	}

	public String getOffsetObjClass() {
		return this.offsetObj.substring(0, 1);
	}

	public String getAccountCd() {
		return this.fund + this.func + this.obj + this.sobj + this.org + this.fsclYr + this.pgm + this.edSpan + this.projDtl;
	}

	public String getAccountCdFormatted() {
		return this.fund + "-" + this.func + "-" + this.obj +  "." + this.sobj + "-" + this.org + "-" + this.fsclYr + this.pgm + this.edSpan + this.projDtl;
	}

	public String getAccountCdFormattedDeferredCheckRsn() {
		return this.func + "-" + this.obj +  "." + this.sobj + "-" + this.org + "-" + this.fsclYr + this.pgm + this.edSpan + this.projDtl;
	}

	public String getTransDt() {
		return transDt;
	}

	public void setTransDt(String transDt) {
		this.transDt = transDt;
	}

	public String getTransDtFormatted() {
		return ( transDt.length() == 8 ) ? ( transDt.substring( 4, 6 ) + "-" + transDt.substring( 6, 8 ) + "-" + transDt.substring( 0, 4 ) ) : transDt;
	}

	public String getInvoiceNbr() {
		return invoiceNbr;
	}

	public void setInvoiceNbr(String invoiceNbr) {
		this.invoiceNbr = invoiceNbr;
	}

	public String getPoPaNbr() {
		return poPaNbr;
	}

	public void setPoPaNbr(String poPaNbr) {
		this.poPaNbr = poPaNbr;
	}

	public String getPoPaType() {
		return poPaType;
	}

	public void setPoPaType(String poPaType) {
		this.poPaType = poPaType;
	}

	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorSortKey() {
		return vendorSortKey;
	}

	public void setVendorSortKey(String vendorSortKey) {
		this.vendorSortKey = vendorSortKey;
	}

	public String getDba() {
		return dba;
	}

	public void setDba(String dba) {
		this.dba = dba;
	}

	public String getVendorPhoneNbr() {
		return vendorPhoneNbr;
	}

	public void setVendorPhoneNbr(String vendorPhoneNbr) {
		this.vendorPhoneNbr = vendorPhoneNbr;
	}

	public String getCheckNbr() {
		return checkNbr;
	}

	public void setCheckNbr(String checkNbr) {
		this.checkNbr = checkNbr;
	}

	public String getOffsetFund() {
		return offsetFund;
	}

	public void setOffsetFund(String offsetFund) {
		this.offsetFund = offsetFund;
	}

	public String getOffsetFsclYr() {
		return offsetFsclYr;
	}

	public void setOffsetFsclYr(String offsetFsclYr) {
		this.offsetFsclYr = offsetFsclYr;
	}

	public String getOffsetFunc() {
		return offsetFunc;
	}

	public void setOffsetFunc(String offsetFunc) {
		this.offsetFunc = offsetFunc;
	}

	public String getOffsetObj() {
		return offsetObj;
	}

	public void setOffsetObj(String offsetObj) {
		this.offsetObj = offsetObj;
	}

	public String getOffsetSobj() {
		return offsetSobj;
	}

	public void setOffsetSobj(String offsetSobj) {
		this.offsetSobj = offsetSobj;
	}

	public String getOffsetOrg() {
		return offsetOrg;
	}

	public void setOffsetOrg(String offsetOrg) {
		this.offsetOrg = offsetOrg;
	}

	public String getOffsetPgm() {
		return offsetPgm;
	}

	public void setOffsetPgm(String offsetPgm) {
		this.offsetPgm = offsetPgm;
	}

	public String getOffsetEdSpan() {
		return offsetEdSpan;
	}

	public void setOffsetEdSpan(String offsetEdSpan) {
		this.offsetEdSpan = offsetEdSpan;
	}

	public String getOffsetProjDtl() {
		return offsetProjDtl;
	}

	public void setOffsetProjDtl(String offsetProjDtl) {
		this.offsetProjDtl = offsetProjDtl;
	}

	public String getOffsetAccountCd() {
		return this.offsetFund + this.offsetFunc + this.offsetObj + this.offsetSobj + this.offsetOrg + this.offsetFsclYr + this.offsetPgm + this.offsetEdSpan + this.offsetProjDtl;
	}

	public String getOffsetAccountCdFormatted() {
		return this.offsetFund + "-" + this.offsetFunc + "-" + this.offsetObj + "." + this.offsetSobj + "-" + this.offsetOrg + "-" + this.offsetFsclYr + this.offsetPgm + this.offsetEdSpan + this.offsetProjDtl;
	}

	public String getCheckDt() {
		return checkDt;
	}

	public void setCheckDt(String checkDt) {
		this.checkDt = checkDt;
	}

	public BigDecimal getNetExpendAmt() {
		return netExpendAmt;
	}

	public void setNetExpendAmt(BigDecimal netExpendAmt) {
		this.netExpendAmt = netExpendAmt;
	}

	public BigDecimal getLiquidatedAmt() {
		return liquidatedAmt;
	}

	public void setLiquidatedAmt(BigDecimal liquidatedAmt) {
		this.liquidatedAmt = liquidatedAmt;
	}

	public BigDecimal getDisburAmt() {
		return disburAmt;
	}

	public void setDisburAmt(BigDecimal disburAmt) {
		this.disburAmt = disburAmt;
	}

	public String getDueFromAcct() {
		return dueFromAcct;
	}

	public void setDueFromAcct(String dueFromAcct) {
		this.dueFromAcct = dueFromAcct;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplictnId() {
		return applictnId;
	}

	public void setApplictnId(String applictnId) {
		this.applictnId = applictnId;
	}

	public String getProcessDt() {
		return processDt;
	}

	public void setProcessDt(String processDt) {
		this.processDt = processDt;
	}

	public String getTransCorrectionFlg() {
		return transCorrectionFlg;
	}

	public void setTransCorrectionFlg(String transCorrectionFlg) {
		this.transCorrectionFlg = transCorrectionFlg;
	}

	public String getCheckVoidFlg() {
		return checkVoidFlg;
	}

	public void setCheckVoidFlg(String checkVoidFlg) {
		this.checkVoidFlg = checkVoidFlg;
	}

	public String getSeparatePymtFlg() {
		return separatePymtFlg;
	}

	public void setSeparatePymtFlg(String separatePymtFlg) {
		this.separatePymtFlg = separatePymtFlg;
	}

	public String getInvoiceDt() {
		return invoiceDt;
	}

	public void setInvoiceDt(String invoiceDt) {
		this.invoiceDt = invoiceDt;
	}

	public String getInvoiceDtFormatted() {
		return ( invoiceDt.length() == 8 ) ? ( invoiceDt.substring( 4, 6 ) + "-" + invoiceDt.substring( 6, 8 ) + "-" + invoiceDt.substring( 0, 4 ) ) : invoiceDt;
	}

	public String getDueDt() {
		return dueDt;
	}

	public void setDueDt(String dueDt) {
		this.dueDt = dueDt;
	}

	public String getDueDtFormatted() {
		return ( dueDt.length() == 8 ) ? ( dueDt.substring( 4, 6 ) + "-" + dueDt.substring( 6, 8 ) + "-" + dueDt.substring( 0, 4 ) ) : dueDt;
	}

	public String getRsn() {
		return rsn;
	}

	public void setRsn(String rsn) {
		this.rsn = rsn;
	}

	public String getPrtChkFlg() {
		return prtChkFlg;
	}

	public void setPrtChkFlg(String prtChkFlg) {
		this.prtChkFlg = prtChkFlg;
	}

	public String getReconInd() {
		return reconInd;
	}

	public void setReconInd(String reconInd) {
		this.reconInd = reconInd;
	}

	public String getCrMemoNbr() {
		return crMemoNbr;
	}

	public void setCrMemoNbr(String crMemoNbr) {
		this.crMemoNbr = crMemoNbr;
	}

	public String getBankAcctCashObj() {
		return bankAcctCashObj;
	}

	public void setBankAcctCashObj(String bankAcctCashObj) {
		this.bankAcctCashObj = bankAcctCashObj;
	}

	public String getBankAcctCashSobj() {
		return bankAcctCashSobj;
	}

	public void setBankAcctCashSobj(String bankAcctCashSobj) {
		this.bankAcctCashSobj = bankAcctCashSobj;
	}

	public String getOrigDts() {
		return origDts;
	}

	public void setOrigDts(String origDts) {
		this.origDts = origDts;
	}

	public String getMicrCheckNbr() {
		return micrCheckNbr;
	}

	public void setMicrCheckNbr(String micrCheckNbr) {
		this.micrCheckNbr = micrCheckNbr;
	}

	public String getReconDts() {
		return reconDts;
	}

	public void setReconDts(String reconDts) {
		this.reconDts = reconDts;
	}

	public String getEftPymtFlg() {
		return eftPymtFlg;
	}

	public void setEftPymtFlg(String eftPymtFlg) {
		this.eftPymtFlg = eftPymtFlg;
	}

	public String getCrdtCrdCd() {
		return crdtCrdCd;
	}

	public void setCrdtCrdCd(String crdtCrdCd) {
		this.crdtCrdCd = crdtCrdCd;
	}

	public String getCrdtCrdReconInd() {
		return crdtCrdReconInd;
	}

	public void setCrdtCrdReconInd(String crdtCrdReconInd) {
		this.crdtCrdReconInd = crdtCrdReconInd;
	}

	public String getCrdtCrdReconDts() {
		return crdtCrdReconDts;
	}

	public void setCrdtCrdReconDts(String crdtCrdReconDts) {
		this.crdtCrdReconDts = crdtCrdReconDts;
	}

	public String getEntryDt() {
		return entryDt;
	}

	public void setEntryDt(String entryDt) {
		this.entryDt = entryDt;
	}

	public String getTypCdNew() {
		return typCdNew;
	}

	public void setTypCdNew(String typCdNew) {
		this.typCdNew = typCdNew;
	}

	public String getBankCd() {
		return bankCd;
	}

	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}

	public String getBankAcctNbr() {
		return bankAcctNbr;
	}

	public void setBankAcctNbr(String bankAcctNbr) {
		this.bankAcctNbr = bankAcctNbr;
	}

	public String getBankAcctType() {
		return bankAcctType;
	}

	public void setBankAcctType(String bankAcctType) {
		this.bankAcctType = bankAcctType;
	}

	public String getTripNbr() {
		return tripNbr;
	}

	public void setTripNbr(String tripNbr) {
		this.tripNbr = tripNbr;
	}

	public String getCrMemoDts() {
		return crMemoDts;
	}

	public void setCrMemoDts(String crMemoDts) {
		this.crMemoDts = crMemoDts;
	}

	public String getCrMemoSeqNbr() {
		return crMemoSeqNbr;
	}

	public void setCrMemoSeqNbr(String crMemoSeqNbr) {
		this.crMemoSeqNbr = crMemoSeqNbr;
	}

	public String getDeleteRec() {
		return deleteRec;
	}

	public void setDeleteRec(String deleteRec) {
		this.deleteRec = deleteRec;
	}

	public String getCheckNotes() {
		return checkNotes;
	}

	public void setCheckNotes(String checkNotes) {
		this.checkNotes = checkNotes;
	}

	public BigDecimal getCheckAmt() {
		return checkAmt;
	}

	public void setCheckAmt(BigDecimal checkAmt) {
		this.checkAmt = checkAmt;
	}
	
	public String getCurrentNextFlag() {
		return currentNextFlag;
	}

	public void setCurrentNextFlag(String currentNextFlag) {
		this.currentNextFlag = currentNextFlag;
	}


}